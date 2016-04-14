package net.floodlightcontroller.acamp.agent;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import net.floodlightcontroller.acamp.msgele.TestMsgEle;
import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.packet.ACAMP;
import net.floodlightcontroller.packet.ACAMPData;
import net.floodlightcontroller.packet.ACAMPMsgEle;
import net.floodlightcontroller.packet.Data;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.packet.PacketParsingException;
import net.floodlightcontroller.packet.UDP;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFPacketOut;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.protocol.OFVersion;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.EthType;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.IpProtocol;
import org.projectfloodlight.openflow.types.MacAddress;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.TransportPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ACAMPagent implements IOFMessageListener, IFloodlightModule {

	protected static Logger logger;
	protected IFloodlightProviderService floodlightProvider;
	
	private static void sendMessage(IOFSwitch sw, OFPort inPort, MacAddress srcMac, MacAddress dstMac, 
			IPv4Address srcAddress, IPv4Address dstAddress, TransportPort srcPort, 
			TransportPort dstPort, byte[] acamp_bytes) {
		Ethernet l2 = new Ethernet();
		l2.setSourceMACAddress(srcMac);
		l2.setDestinationMACAddress(dstMac);
		l2.setEtherType(EthType.IPv4);
		IPv4 l3 = new IPv4();
		l3.setDestinationAddress(dstAddress);
		l3.setSourceAddress(srcAddress);
		l3.setTtl((byte)64);
		l3.setProtocol(IpProtocol.UDP);
		UDP l4 = new UDP();
		l4.setSourcePort(srcPort);
		l4.setDestinationPort(dstPort);
		Data l7 = new Data();
		l7.setData(acamp_bytes);
		l4.setPayload(l7);
		l3.setPayload(l4);
		l2.setPayload(l3);
		byte[] serializeData = l2.serialize();
		OFPacketOut po = sw.getOFFactory().buildPacketOut()
				.setData(serializeData)
				.setActions(Collections.singletonList((OFAction) sw.getOFFactory().actions().output(inPort, 0xffFFffFF)))
				.setInPort(OFPort.CONTROLLER)
				.build();
		sw.write(po);
	}
	
	@Override
	public String getName() {
		return ACAMPagent.class.getSimpleName();
	}

	@Override
	public boolean isCallbackOrderingPrereq(OFType type, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCallbackOrderingPostreq(OFType type, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
		Collection<Class<? extends IFloodlightService>> depList = new ArrayList<Class<? extends IFloodlightService>>();
		depList.add(IFloodlightProviderService.class);
		return depList;
	}

	@Override
	public void init(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
		logger = LoggerFactory.getLogger(ACAMPagent.class);
	}

	@Override
	public void startUp(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
	}

	@Override
	public net.floodlightcontroller.core.IListener.Command receive(
			IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {

		switch(msg.getType()) {
		case PACKET_IN:
		{
			OFPacketIn myPacketIn = (OFPacketIn) msg;
			OFPort inPort = (myPacketIn.getVersion().compareTo(OFVersion.OF_12) < 0)
					? myPacketIn.getInPort()
					: myPacketIn.getMatch().get(MatchField.IN_PORT);
			logger.info("A Packet-In Message has arrive Controller which comes from:" +
					"{}"+ " Port:{}", sw.getId().toString(), inPort.toString());
			Ethernet eth = IFloodlightProviderService.bcStore.get(cntx, IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
			MacAddress srcMacAddress = eth.getSourceMACAddress();
			MacAddress dstMacAddress = eth.getDestinationMACAddress();
			IPv4 ipv4 = (IPv4)eth.getPayload();
			IPv4Address srcIpAddr = ipv4.getSourceAddress();
			IPv4Address dstIpAddr = ipv4.getDestinationAddress();
			if(ipv4.getProtocol().equals(IpProtocol.UDP)) {
				UDP udp = (UDP) ipv4.getPayload();
				TransportPort srcPort = udp.getSourcePort();
				TransportPort dstPort = udp.getDestinationPort();
				ACAMP recv = new ACAMP();
				byte[] recvData = udp.getPayload().serialize();
				try {
					recv.deserialize(recvData, 0, recvData.length);
				} catch (PacketParsingException e) {
					e.printStackTrace();
				}
				ACAMP acmap = new ACAMP();
				ACAMPMsgEle msgEle = new ACAMPMsgEle();
				TestMsgEle testMsgEle = new TestMsgEle();
				testMsgEle.setTypeValue(0);
				msgEle.setMessageElementType(ACAMPMsgEle.RESULT_CODE)
					  .setPayload(testMsgEle);
				ACAMPMsgEle msgEle2 = new ACAMPMsgEle();
				TestMsgEle testMsgEle2 = new TestMsgEle();
				testMsgEle2.setTypeValue(2);
				msgEle2.setMessageElementType(ACAMPMsgEle.REASON_CODE)
					  .setPayload(testMsgEle2);
				ACAMPData acampData = new ACAMPData();
				acampData.addMessageElement(msgEle);
				acampData.addMessageElement(msgEle2);
				acmap.setVersion((short)1)
					 .setType((byte)0)
					 .setAPID(1)
					 .setSequenceNumber(888888888)
					 .setMessageType((short)ACAMP.CONFIGURATION_RESPONSE)
					 .setPayload(acampData);
				byte[] data = acmap.serialize();
				ACAMPagent.sendMessage(sw, inPort, dstMacAddress, srcMacAddress,
						dstIpAddr, srcIpAddr, dstPort, srcPort, data);
			}
			break;
		}
		default:
			break;
		}
		return Command.STOP;
	}

}
