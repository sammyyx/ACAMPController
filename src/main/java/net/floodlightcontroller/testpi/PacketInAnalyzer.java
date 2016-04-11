package net.floodlightcontroller.testpi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import net.floodlightcontroller.acamp.message.ACAMPMessage;
import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.ICMP;
import net.floodlightcontroller.packet.IPv4;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.types.EthType;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.IpProtocol;
import org.projectfloodlight.openflow.types.MacAddress;
import org.projectfloodlight.openflow.types.VlanVid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PacketInAnalyzer implements IFloodlightModule, IOFMessageListener {
	
	/**
	 * 测试用，收取ACAMP消息函数
	 * @param eth
	 * @return
	 */
	protected  ACAMPMessage getACAMPMessage(Ethernet eth) {
		return null;
	}
	/**
	 * 测试用，发送ACAMP消息函数
	 */
	protected void sendACAMPMessage() {
		
	}
	
	protected IFloodlightProviderService floodlightProvider;
	protected static Logger logger;
	
	@Override
	public String getName() {
		return PacketInAnalyzer.class.getSimpleName();
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
	public net.floodlightcontroller.core.IListener.Command receive(
			IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
		
		/* 取得解序列化的以太帧 */
		Ethernet eth = IFloodlightProviderService.bcStore.get(cntx, IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
		
		/* 取得以太帧首部的相关信息 */
		MacAddress srcMac = eth.getSourceMACAddress();
		MacAddress dstMac = eth.getDestinationMACAddress();
		VlanVid id = VlanVid.ofVlan(eth.getVlanID());
		
		/* 取得IPv4的有效载荷 */
		if(eth.getEtherType() == EthType.IPv4) {
			IPv4 ipv4 = (IPv4)eth.getPayload();
			byte[] ipOptions = ipv4.getOptions();
			IPv4Address dstIP = ipv4.getDestinationAddress();
			IPv4Address srcIP = ipv4.getSourceAddress();
			
			/* 取得ICMP的有效载荷 */
			if(ipv4.getProtocol().equals(IpProtocol.ICMP)) {
				ICMP icmp = (ICMP)ipv4.getPayload();
				byte icmpType = icmp.getIcmpType();
				byte icmpCode = icmp.getIcmpCode();
				logger.info("Destination IP Address:{}, Source IP Address:{}, ICMP Type:{}, ICMP Code:{}",
							dstIP.toString(), srcIP.toString());
			}
		}
		return Command.STOP;
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
		Collection<Class<? extends IFloodlightService>> l = new ArrayList<Class<? extends IFloodlightService>>();
		l.add(IFloodlightProviderService.class);
		return l;
	}

	@Override
	public void init(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
		logger = LoggerFactory.getLogger(PacketInAnalyzer.class);

	}

	@Override
	public void startUp(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
	}

}
