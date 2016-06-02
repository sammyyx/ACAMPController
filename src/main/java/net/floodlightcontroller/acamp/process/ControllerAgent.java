package net.floodlightcontroller.acamp.process;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import net.floodlightcontroller.acamp.entity.DeviceAp;
import net.floodlightcontroller.acamp.entity.DeviceAp.State;
import net.floodlightcontroller.acamp.entity.DeviceStation;
import net.floodlightcontroller.acamp.message.AcampMes;
import net.floodlightcontroller.acamp.message.AcampMessage;
import net.floodlightcontroller.acamp.message.ConstAcampHwMode;
import net.floodlightcontroller.acamp.message.ConstAcampMe;
import net.floodlightcontroller.acamp.message.ConstAcampMeRC;
import net.floodlightcontroller.acamp.message.ConstAcampMessage;
import net.floodlightcontroller.acamp.message.ConstAcampSecurity;
import net.floodlightcontroller.acamp.message.ConstAcampSuppressSsid;
import net.floodlightcontroller.acamp.message.ConstAcampWpaSetting;
import net.floodlightcontroller.acamp.restapi.AcampWebRoutable;
import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.packet.Data;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.packet.UDP;
import net.floodlightcontroller.restserver.IRestApiService;

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

public class ControllerAgent implements IFloodlightModule, IOFMessageListener {

	private IFloodlightProviderService floodlightProvider;
	private IRestApiService restApiService;
	private Logger logger;
	public static IPv4Address controllerIpAddr;
	public static MacAddress controllerMacAddr;
	public static List<DeviceAp> apList;
	private static final String CONTROLLER_NAME = "feimaofeimao";
	private static final String CONTROLLER_DESCRIPTION = "I'm control FREAK!";
	public static final int PROTOCOL_PORT = 6606;
	private static final IPv4Address BC_IPADDR = IPv4Address.of("255.255.255.255");
	private static byte protocolVersion;
	private static byte protocolType;
	private static long sequenceNumber;
	private static int apCount;
	
	private void initNetworkInterface() {
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while(netInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface)netInterfaces.nextElement();
				if(netInterface.getName().equals("eth0")) {
					Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
					while(addresses.hasMoreElements()) {
						InetAddress ipAddress = (InetAddress)addresses.nextElement();
						if(ipAddress instanceof Inet4Address) {
							controllerIpAddr = IPv4Address.of(ipAddress.getAddress());
						}
					}
					controllerMacAddr = MacAddress.of(netInterface.getHardwareAddress());
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	public static void sendMessage(IOFSwitch sw, OFPort inPort, MacAddress srcMac, MacAddress dstMac, 
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
		return ControllerAgent.class.getSimpleName();
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
	public net.floodlightcontroller.core.IListener.Command receive(IOFSwitch sw, OFMessage msg,
			FloodlightContext cntx) {
		switch(msg.getType()) {
		case PACKET_IN:
		{
			OFPacketIn myPacketIn = (OFPacketIn) msg;
			OFPort inPort = (myPacketIn.getVersion().compareTo(OFVersion.OF_12) < 0)
					? myPacketIn.getInPort()
					: myPacketIn.getMatch().get(MatchField.IN_PORT);
			Ethernet eth = IFloodlightProviderService.bcStore.get(cntx, IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
			MacAddress srcMacAddress = eth.getSourceMACAddress();
//			MacAddress dstMacAddress = eth.getDestinationMACAddress();
			if(eth.getEtherType() != EthType.IPv4) {
				return Command.CONTINUE;
			}
			IPv4 ipv4 = (IPv4)eth.getPayload();
			IPv4Address srcIpAddr = ipv4.getSourceAddress();
			IPv4Address dstIpAddr = ipv4.getDestinationAddress();
			if(!ipv4.getProtocol().equals(IpProtocol.UDP)) {
				return Command.CONTINUE;
			}
			UDP udp = (UDP) ipv4.getPayload();
			TransportPort srcPort = udp.getSourcePort();
			TransportPort dstPort = udp.getDestinationPort();
			if(dstPort.getPort() != PROTOCOL_PORT) {
				return Command.CONTINUE;
			}
			logger.info("A ACAMP Message has arrive Controller which comes from:" +
					"{}"+ " Port:{}", sw.getId().toString(), inPort.toString());
			if(dstIpAddr.equals(BC_IPADDR)) {
				AcampMessage recvMessage = new AcampMessage();
				recvMessage.deserialize(udp.getPayload().serialize());
				protocolVersion = recvMessage.getProtocolVersion();
				protocolType = recvMessage.getProtocolType();
				sequenceNumber = recvMessage.getSequenceNumber() & 0x0ffffffff;
				recvMessage.deserialize(udp.getPayload().serialize());
				if(recvMessage.getMessageType() == ConstAcampMessage.DISCOVER_REQUEST) {
					logger.info("Receive a discovery request from" + srcIpAddr);
				}
				AcampMessage sendMessage = new AcampMessage();
				sendMessage.setAPID(0);
				sendMessage.setMessageType(ConstAcampMessage.DISCOVER_RESPONSE);
				sendMessage.setProtocolType(protocolType);
				sendMessage.setProtocolVersion(protocolVersion);
				sendMessage.setSequenceNumber(sequenceNumber);
				sendMessage.addControllerIpAddr();
				sendMessage.addControllerMacAddr();
				sendMessage.addControllerName(CONTROLLER_NAME);
				sendMessage.addControllerDescriptor(CONTROLLER_DESCRIPTION);
				byte[] sendBytes = sendMessage.serialize();
				sendMessage(
						sw, 
						inPort, 
						controllerMacAddr, 
						srcMacAddress, 
						controllerIpAddr, 
						srcIpAddr, 
						TransportPort.of(PROTOCOL_PORT), 
						srcPort, 
						sendBytes
						);
			}
			else if(dstIpAddr.equals(controllerIpAddr)) {
				AcampMessage recvMessage = new AcampMessage();
				recvMessage.deserialize(udp.getPayload().serialize());
				int apid = recvMessage.getAPID() & 0x0ffff;
				protocolVersion = recvMessage.getProtocolVersion();
				protocolType = recvMessage.getProtocolType();
				sequenceNumber = recvMessage.getSequenceNumber() & 0x0ffffffff;
				switch(recvMessage.getMessageType()) {
				case ConstAcampMessage.KEEP_ALIVE_REQUEST: {
					if(apid == 0) break;
					AcampMessage sendMessage = new AcampMessage();
					sendMessage.setAPID(apid);
					sendMessage.setMessageType(ConstAcampMessage.KEEP_ALIVE_RESPONSE);
					sendMessage.setProtocolType(protocolType);
					sendMessage.setProtocolVersion(protocolVersion);
					sendMessage.setSequenceNumber(sequenceNumber);
					byte[] sendBytes = sendMessage.serialize();
					sendMessage(
							sw, 
							inPort, 
							controllerMacAddr, 
							srcMacAddress, 
							controllerIpAddr, 
							srcIpAddr, 
							TransportPort.of(PROTOCOL_PORT), 
							srcPort, 
							sendBytes
							);
					break;
				}
				case ConstAcampMessage.REGISTER_REQUEST: {
					AcampMessage sendMessage = new AcampMessage();
					if(apid == 0) {
						apCount++;
						DeviceAp ap = new DeviceAp();
						ap.apState = State.REGISTER;
						ap.connectedSwitch = sw;
						ap.connectedSwitchPort = inPort;
						ap.udpPort = srcPort;
						ap.setApId(apCount);
						ap.setApIpAddress(srcIpAddr.getBytes());
						ap.setApMacAddress(srcMacAddress.getBytes());
						apList.add(ap);
						sendMessage.setAPID(ap.getApId());
						sendMessage.setMessageType(ConstAcampMessage.REGISTER_RESPONSE);
						sendMessage.setProtocolType(protocolType);
						sendMessage.setProtocolVersion(protocolVersion);
						sendMessage.setSequenceNumber(sequenceNumber);
						sendMessage.addResultCode(ConstAcampMeRC.SUCCESS);
						ap.apState = State.CONFIGURE;
						sendMessage.addAssignedApid((short)ap.getApId());
						sendMessage.addControllerName(CONTROLLER_NAME);
						sendMessage.addControllerDescriptor(CONTROLLER_DESCRIPTION);
						sendMessage.addControllerIpAddr();
						sendMessage.addControllerMacAddr();
						byte[] sendBytes = sendMessage.serialize();
						sendMessage(
								sw, 
								inPort, 
								controllerMacAddr, 
								srcMacAddress, 
								controllerIpAddr, 
								srcIpAddr, 
								TransportPort.of(PROTOCOL_PORT), 
								srcPort, 
								sendBytes
								);
					}
					break;
				}
				case ConstAcampMessage.UNREGISTER_REQUEST: {
					AcampMessage sendMessage = new AcampMessage();
					DeviceAp ap = null;
					for(DeviceAp temp:apList) {
						if(temp.getApId() == apid) {
							ap = temp;
						}
					}
					if(ap == null) {
						System.out.println("Unregistered Devices");
						break;
					}
					sendMessage.setAPID(ap.getApId());
					sendMessage.setMessageType(ConstAcampMessage.UNREGISTER_RESPONSE);
					sendMessage.setProtocolType(protocolType);
					sendMessage.setProtocolVersion(protocolVersion);
					sendMessage.setSequenceNumber(sequenceNumber);
					sendMessage.addResultCode(ConstAcampMeRC.SUCCESS);
					byte[] sendBytes = sendMessage.serialize();
					sendMessage(
							sw, 
							inPort, 
							controllerMacAddr, 
							srcMacAddress, 
							controllerIpAddr, 
							srcIpAddr, 
							TransportPort.of(PROTOCOL_PORT), 
							srcPort, 
							sendBytes
							);
					apList.remove(ap);
					break;
				}
				case ConstAcampMessage.CONFIGURATION_REQUEST: {
					AcampMessage sendMessage = new AcampMessage();
					DeviceAp ap = null;
					for(DeviceAp temp:apList) {
						if(temp.getApId() == apid) {
							ap = temp;
						}
					}
					if(ap == null) {
						System.out.println("Unregistered Devices");
						break;
					}
					AcampMes mes = new AcampMes(recvMessage.getData());
					while(mes.hasRemaining()) {
						byte[] value = mes.getMe();
						ByteBuffer bb = ByteBuffer.wrap(value);
						switch(mes.getMeType()) {
						case ConstAcampMe.SSID: {
							byte[] meBytes = new byte[mes.getMeLength() - ConstAcampMe.HEADER_LENGTH];
							bb.get(meBytes);
							String ssid = new String(meBytes);
							ap.setSsid(ssid);
							break;
						}
						case ConstAcampMe.CHANNEL: {
							short channel = (short)(bb.get() & 0x0ffff);
							ap.setChannel(channel);
							break;
						}
						case ConstAcampMe.HARDWARE_MODE: {
							byte hwMode = bb.get();
							ap.setHwMode(hwMode);
							break;
						}
						case ConstAcampMe.SUPPRESS_SSID: {
							byte suppressSsid = bb.get();
							ap.setSuppressSsid(suppressSsid);
							break;
						}
						case ConstAcampMe.SECURITY_SETTING: {
							byte securitySetting = bb.get();
							ap.setSecuritySetting(securitySetting);
							break;
						}
						case ConstAcampMe.WPA_VERSION: {
							byte wpaVersion = bb.get();
							ap.setWpaVersion(wpaVersion);
							break;
						}
						case ConstAcampMe.WPA_PASSPHRASE: {
							byte[] meBytes = new byte[mes.getMeLength() - ConstAcampMe.HEADER_LENGTH];
							bb.get(meBytes);
							String wpaPassphrase = new String(meBytes);
							ap.setWpaPassphrase(wpaPassphrase);
							break;
						}
						case ConstAcampMe.WPA_KEY_MANAGEMENT: {
							byte wpaKeyManagement = bb.get();
							ap.setWpaKeyManagement(wpaKeyManagement);
							break;
						}
						case ConstAcampMe.WPA_GROUP_REKEY: {
							int wpaGroupRekey = bb.getInt();
							ap.setWpaGroupRekey(wpaGroupRekey);
							break;
						}
						case ConstAcampMe.WEP_DEFAULT_KEY: {
							byte wepDefaultKey = bb.get();
							ap.setWepDefaultKey(wepDefaultKey);
							break;
						}
						case ConstAcampMe.WEP_KEY: {
							while(bb.hasRemaining()) {
								int keyNumber = bb.get();
								int keyLength = bb.get();
								byte[] key = new byte[keyLength];
								bb.get(key);
								String stringKey = new String(key);
								ap.addWepKey(keyNumber, stringKey);
							}
							break;
						}
						case ConstAcampMe.MAC_ACL_MODE: {
							byte aclMode = bb.get();
							ap.setMacAclMode(aclMode);
							break;
						}
						case ConstAcampMe.MAC_ACCEPT_LIST: {
							byte[] macAddress = new byte[6];
							while(bb.hasRemaining()) {
								bb.get(macAddress);
								ap.addMacAcceptListEntry(macAddress);
							}
							break;
						}
						case ConstAcampMe.MAC_DENY_LIST: {
							byte[] macAddress = new byte[6];
							while(bb.hasRemaining()) {
								bb.get(macAddress);
								ap.addMacDenyListEntry(macAddress);
							}
							break;
						}
						}
					}
					sendMessage.setAPID(ap.getApId());
					sendMessage.setMessageType(ConstAcampMessage.CONFIGURATION_DELIVER);
					sendMessage.setProtocolType(protocolType);
					sendMessage.setProtocolVersion(protocolVersion);
					sendMessage.setSequenceNumber(sequenceNumber);
					sendMessage.addSsid("test_wlan");
					sendMessage.addChannel((byte)3);
					sendMessage.addHardwareMode(ConstAcampHwMode.IEEE802DOT11b);
					sendMessage.addSuppressSsid(ConstAcampSuppressSsid.SUPPRESS_BY_SENDING_ASCII_ZERO);
					sendMessage.addSecuritySetting(ConstAcampSecurity.WPA_PSK_WPA2_PSK);
					sendMessage.addWpaVersion(ConstAcampWpaSetting.VERSION_WPA_TWO);
					sendMessage.addWpaPassphrase("openssesame");
//					sendMessage.addWpaGroupRekey(3000);
					sendMessage.addWpaPairwise(ConstAcampWpaSetting.PAIRWISE_CCMP);
					sendMessage.addWpaKeyManagement(ConstAcampWpaSetting.KM_WPA_PSK);
					byte[] sendBytes = sendMessage.serialize();
					sendMessage(
							sw, 
							inPort, 
							controllerMacAddr, 
							srcMacAddress, 
							controllerIpAddr, 
							srcIpAddr, 
							TransportPort.of(PROTOCOL_PORT), 
							srcPort, 
							sendBytes
							);
					ap.apState = State.RUN;
					break;
				}
				case ConstAcampMessage.STATE_REPORT: {
					DeviceAp ap = null;
					for(DeviceAp temp:apList) {
						if(temp.getApId() == apid) {
							ap = temp;
						}
					}
					if(ap == null) {
						System.out.println("Unregistered Devices");
						break;
					}
					AcampMes mes = new AcampMes(recvMessage.getData());
					while(mes.hasRemaining()) {
						byte[] value = mes.getMe();
						ByteBuffer bb = ByteBuffer.wrap(value);
						switch(mes.getMeType()) {
						case ConstAcampMe.STATE_DESCRIPTOR: {
							break;
						}
						case ConstAcampMe.TIMERS: {
							short keepAliveInterval = (short)(bb.get() & 0x0ff);
							short discoverInterval = (short)(bb.get() & 0x0ff);
							short statisticTimer = (short)(bb.get() & 0x0ff);
							short stateTimer = (short)(bb.get() & 0x0ff);
							short idleTimeout = (short)(bb.get() & 0x0ff);
							short discoverTimeout = (short)(bb.get() & 0x0ff);
							short registerTimeout = (short)(bb.get() & 0x0ff);
							short reportInterval = (short)(bb.get() & 0x0ff);
							ap.setDiscoverInterval(discoverInterval);
							ap.setDiscoverTimeout(discoverTimeout);
							ap.setIdleTimeout(idleTimeout);
							ap.setRegisterTimeout(registerTimeout);
							ap.setReportInterval(reportInterval);
							ap.setStateTimer(stateTimer);
							ap.setStatisticTimer(statisticTimer);
							ap.setKeepAliveInterval(keepAliveInterval);
							break;
						}
						default:
							break;
						}
					}

					break;
				}
				case ConstAcampMessage.STATISTIC_REPORT: {
					DeviceAp ap = null;
					for(DeviceAp temp:apList) {
						if(temp.getApId() == apid) {
							ap = temp;
						}
					}
					if(ap == null) {
						System.out.println("Unregistered Devices");
						break;
					}
					AcampMes mes = new AcampMes(recvMessage.getData());
					while(mes.hasRemaining()) {
						byte[] value = mes.getMe();
						ByteBuffer bb = ByteBuffer.wrap(value);
						switch(mes.getMeType()) {
						case ConstAcampMe.FLOW_STATISTIC: {
							DeviceStation station = new DeviceStation();
							byte[] stationIpAddr = new byte[4];
							bb.get(stationIpAddr);
							byte[] stationMacAddr = new byte[6];
							bb.get(stationMacAddr);
							long overallPacket = bb.getInt() & 0x0ffffffff;
							long overallBytes = bb.getInt() & 0x0ffffffff;
							station.setIpAddress(stationIpAddr);
							station.setMacAddress(stationMacAddr);
							station.setOverallPacket(overallPacket);
							station.setOverallBytes(overallBytes);
							ap.addStation(station);
							break;
						}
						}
					}
					break;
				}
				case ConstAcampMessage.CONFIGURATION_REPORT: {
					DeviceAp ap = null;
					for(DeviceAp temp:apList) {
						if(temp.getApId() == apid) {
							ap = temp;
						}
					}
					if(ap == null) {
						System.out.println("Unregistered Devices");
						break;
					}
					AcampMes mes = new AcampMes(recvMessage.getData());
					while(mes.hasRemaining()) {
						byte[] value = mes.getMe();
						ByteBuffer bb = ByteBuffer.wrap(value);
						switch(mes.getMeType()) {
						case ConstAcampMe.SSID: {
							byte[] meBytes = new byte[mes.getMeLength() - ConstAcampMe.HEADER_LENGTH];
							bb.get(meBytes);
							String ssid = new String(meBytes);
							ap.setSsid(ssid);
							break;
						}
						case ConstAcampMe.CHANNEL: {
							short channel = (short)(bb.get() & 0x0ffff);
							ap.setChannel(channel);
							break;
						}
						case ConstAcampMe.HARDWARE_MODE: {
							byte hwMode = bb.get();
							ap.setHwMode(hwMode);
							break;
						}
						case ConstAcampMe.SUPPRESS_SSID: {
							byte suppressSsid = bb.get();
							ap.setSuppressSsid(suppressSsid);
							break;
						}
						case ConstAcampMe.SECURITY_SETTING: {
							byte securitySetting = bb.get();
							ap.setSecuritySetting(securitySetting);
							break;
						}
						case ConstAcampMe.WPA_VERSION: {
							byte wpaVersion = bb.get();
							ap.setWpaVersion(wpaVersion);
							break;
						}
						case ConstAcampMe.WPA_PASSPHRASE: {
							byte[] meBytes = new byte[mes.getMeLength() - ConstAcampMe.HEADER_LENGTH];
							bb.get(meBytes);
							String wpaPassphrase = new String(meBytes);
							ap.setWpaPassphrase(wpaPassphrase);
							break;
						}
						case ConstAcampMe.WPA_KEY_MANAGEMENT: {
							byte wpaKeyManagement = bb.get();
							ap.setWpaKeyManagement(wpaKeyManagement);
							break;
						}
						case ConstAcampMe.WPA_GROUP_REKEY: {
							int wpaGroupRekey = bb.getInt();
							ap.setWpaGroupRekey(wpaGroupRekey);
							break;
						}
						case ConstAcampMe.WEP_DEFAULT_KEY: {
							byte wepDefaultKey = bb.get();
							ap.setWepDefaultKey(wepDefaultKey);
							break;
						}
						case ConstAcampMe.WEP_KEY: {
							while(bb.hasRemaining()) {
								int keyNumber = bb.get();
								int keyLength = bb.get();
								byte[] key = new byte[keyLength];
								bb.get(key);
								String stringKey = new String(key);
								ap.addWepKey(keyNumber, stringKey);
							}
							break;
						}
						case ConstAcampMe.MAC_ACL_MODE: {
							byte aclMode = bb.get();
							ap.setMacAclMode(aclMode);
							break;
						}
						case ConstAcampMe.MAC_ACCEPT_LIST: {
							byte[] macAddress = new byte[6];
							while(bb.hasRemaining()) {
								bb.get(macAddress);
								ap.addMacAcceptListEntry(macAddress);
							}
							break;
						}
						case ConstAcampMe.MAC_DENY_LIST: {
							byte[] macAddress = new byte[6];
							while(bb.hasRemaining()) {
								bb.get(macAddress);
								ap.addMacDenyListEntry(macAddress);
							}
							break;
						}
						}
					}
					break;
				}
				}
			}
			break;
		}
		default: break;
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
		Collection<Class<? extends IFloodlightService>> depList = new ArrayList<Class<? extends IFloodlightService>>();
		depList.add(IFloodlightProviderService.class);
		depList.add(IRestApiService.class);
		return depList;
	}

	@Override
	public void init(FloodlightModuleContext context) throws FloodlightModuleException {
		floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
		logger = LoggerFactory.getLogger(ControllerAgent.class);
		restApiService = context.getServiceImpl(IRestApiService.class);
		apList = new ArrayList<DeviceAp>();
		initNetworkInterface();
		apCount = 0;

	}

	@Override
	public void startUp(FloodlightModuleContext context) throws FloodlightModuleException {
		floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
		restApiService.addRestletRoutable(new AcampWebRoutable());
	}

}
