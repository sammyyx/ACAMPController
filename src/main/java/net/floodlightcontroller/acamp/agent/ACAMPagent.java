package net.floodlightcontroller.acamp.agent;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

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

import net.floodlightcontroller.acamp.msgele.ResultCode;
import net.floodlightcontroller.acamp.msgele.WlanInformation;
import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.packet.ACAMP;
import net.floodlightcontroller.packet.ACAMPMsgEle;
import net.floodlightcontroller.packet.Data;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.packet.PacketParsingException;
import net.floodlightcontroller.packet.UDP;
import net.floodlightcontroller.restserver.IRestApiService;

public class ACAMPagent implements IOFMessageListener, IFloodlightModule {

	protected static Logger logger;
	
	//为一个AP所设的静态变量供其它类调用
	protected static IOFSwitch attachedSwitch;
	protected static OFPort inPort;
	protected static MacAddress srcMac;
	protected static MacAddress dstMac;
	protected static IPv4Address srcAddress;
	protected static IPv4Address dstAddress;
	protected static TransportPort srcPort;
	protected static TransportPort dstPort;
	protected static ACAMPFsm acampFsm1 = new ACAMPFsm();
	
	protected IFloodlightProviderService floodlightProvider;
	protected IRestApiService restApiService;
	
	//Send函数为所有需要发送Packet-out的报文设置
	private static void sendMessage(byte[] acamp_bytes) {
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
		OFPacketOut po = attachedSwitch.getOFFactory().buildPacketOut()
				.setData(serializeData)
				.setActions(Collections.singletonList((OFAction) attachedSwitch.getOFFactory().actions().output(inPort, 0xffFFffFF)))
				.setInPort(OFPort.CONTROLLER)
				.build();
		attachedSwitch.write(po);
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
		depList.add(IRestApiService.class);
		return depList;
	}

	@Override
	public void init(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
		restApiService = context.getServiceImpl(IRestApiService.class);
		logger = LoggerFactory.getLogger(ACAMPagent.class);
	}

	@Override
	public void startUp(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
		restApiService.addRestletRoutable(new ACAMPWebRoutable());
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
			if(eth.getEtherType() != EthType.IPv4) {
				return Command.CONTINUE;
			}
			IPv4 ipv4 = (IPv4)eth.getPayload();
			IPv4Address srcIpAddr = ipv4.getSourceAddress();
			IPv4Address dstIpAddr = ipv4.getDestinationAddress();
			if(ipv4.getProtocol().equals(IpProtocol.UDP)) {
				UDP udp = (UDP) ipv4.getPayload();
				TransportPort srcPort = udp.getSourcePort();
				TransportPort dstPort = udp.getDestinationPort();
				byte[] recvData = udp.getPayload().serialize();
				if(ByteBuffer.wrap(recvData).getInt() != ACAMPProtocol.PREAMBLE) {
					return Command.CONTINUE;
				}
				
				//初始化静态变量
				ACAMPagent.attachedSwitch = sw;
				ACAMPagent.inPort = inPort;
				ACAMPagent.srcMac = srcMacAddress;
				ACAMPagent.dstMac = dstMacAddress;
				ACAMPagent.srcAddress = srcIpAddr;
				ACAMPagent.dstAddress = dstIpAddr;
				ACAMPagent.srcPort = srcPort;
				ACAMPagent.dstPort = dstPort;
				
				ACAMP recv = new ACAMP();
				try {
					recv.deserialize(recvData, 0, recvData.length);
				} catch (PacketParsingException e) {
					e.printStackTrace();
				}
				
				switch(recv.getMessageType()) {
				case REGISTER_REQUEST:
				{
					acampFsm1.acceptRegistry();
					ACAMPMsgEle msgEle = new ACAMPMsgEle();
					ResultCode resultCode = (ResultCode)msgEle.createBuilder(ACAMPProtocol.MsgEleType.RESULT_CODE);
					resultCode.setResultCode((byte)0x01);
					ACAMPEncapsulate AP1 = new ACAMPEncapsulate();
					AP1.setVersion((short)0x0001)
					   .setApid(66)
					   .setMessageType(ACAMPProtocol.MsgType.REGISTER_RESPONSE)
					   .setVersion((short)0x0001)
					   .setType((byte)0x00)
					   .setSequenceNum(Integer.MAX_VALUE);
					byte[] data = AP1.createACAMPMessage(msgEle);
					ACAMPagent.sendMessage(data);
					break;
				}
				case DISCONNET_RESPONSE:
				{
					acampFsm1.releaseControl();
					break;	
				}
				case CONFIGURATION_REQUEST:
				{
					acampFsm1.recvConfigReq();
					ACAMPMsgEle msgEle = new ACAMPMsgEle();
					WlanInformation wlanInfo = (WlanInformation) msgEle.createBuilder(ACAMPProtocol.MsgEleType.WLAN_INFO);
					String key = new String("Hello world");
					wlanInfo.setAuthType((byte)0x11)
							.setCapability((short)0x2222)
							.setGroupTSC(new byte[6])
							.setKeyIndex((byte)0x33)
							.setKeyStatus((byte)0x44)
							.setQos((byte)0x55)
							.setRadioId((byte)0x66)
							.setWlanId((byte)0x77)
							.setSuppressSSID((byte)0x88)
							.setSsid("Helloworld")
							.setKey(key.getBytes());
					ACAMPEncapsulate AP1 = new ACAMPEncapsulate();
					AP1.setVersion((short)0x0001)
					   .setApid(66)
					   .setMessageType(ACAMPProtocol.MsgType.CONFIGURATION_RESPONSE)
					   .setVersion((short)0x0001)
					   .setType((byte)0x00)
					   .setSequenceNum(888888888888L);
					byte[] data = AP1.createACAMPMessage(msgEle);
					ACAMPagent.sendMessage(data);
					break;	
				}
				case CONFIGURATION_RESET_RSP:
					break;
				case STATISTIC_STAT_RP:
					break;
				case STATISTIC_STAT_REPLY:
					break;
				case STAT_RESPONSE:
					break;
				case INVALID_MSG:
					break;
				default:
					break;
				}
				return Command.STOP;
			}
			break;
		}
		default:
			break;
		}
		return Command.CONTINUE;
	}
}
