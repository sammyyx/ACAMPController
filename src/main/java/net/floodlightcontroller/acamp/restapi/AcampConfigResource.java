package net.floodlightcontroller.acamp.restapi;

import net.floodlightcontroller.acamp.entity.DeviceAp;
import net.floodlightcontroller.acamp.message.AcampMessage;
import net.floodlightcontroller.acamp.message.ConstAcampHwMode;
import net.floodlightcontroller.acamp.message.ConstAcampMessage;
import net.floodlightcontroller.acamp.message.ConstAcampSecurity;
import net.floodlightcontroller.acamp.message.ConstAcampSuppressSsid;
import net.floodlightcontroller.acamp.message.ConstAcampWpaSetting;
import net.floodlightcontroller.acamp.process.ControllerAgent;
import net.sf.json.JSONObject;

import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.MacAddress;
import org.projectfloodlight.openflow.types.TransportPort;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class AcampConfigResource extends ServerResource {
	
//	private void testRestApi() {
//		DeviceAp ap = new DeviceAp();
//		ap.setApId(1);
//		ap.setSsid("test-ap");
//		ap.setChannel((byte)3);
//		ap.setHwMode(ConstAcampHwMode.IEEE802DOT11b);
//		ap.setSuppressSsid(ConstAcampSuppressSsid.SUPPRESS_BY_SENDING_ASCII_ZERO);
//		ap.setSecuritySetting(ConstAcampSecurity.WPA_PSK_WPA2_PSK);
//		ap.setWpaVersion(ConstAcampWpaSetting.VERSION_WPA_TWO);
//		ap.setWpaPassphrase("openssesame");
//		ap.setWpaKeyManagement(ConstAcampWpaSetting.PAIRWISE_CCMP);
//		ap.setWpaGroupRekey(ConstAcampWpaSetting.KM_WPA_PSK);
//		ControllerAgent.apList.add(ap);
//	}
	
	@Get("json")
	public String getApConfiguration() {
		String param = (String) getRequestAttributes().get("apid");
		int apid = Integer.valueOf(param).intValue();
		DeviceAp ap = null;
		for(DeviceAp temp:ControllerAgent.apList) {
			if(temp.getApId() == apid) {
				ap = temp;
			}
		}
		if(ap == null) {
			System.out.println("Can not find ap with id = " + apid);
			return null;
		}
		AcampMessage sendMessage = new AcampMessage();
		sendMessage.setAPID(ap.getApId());
//		sendMessage.setMessageType(ConstAcampMessage.CONFIGURATION_REQUEST);
		sendMessage.setMessageType(ConstAcampMessage.UNREGISTER_REQUEST);
		sendMessage.setProtocolType(ConstAcampMessage.ACAMP_CONTROL_TYPE);
		sendMessage.setProtocolVersion(ConstAcampMessage.ACAMP_01);
		sendMessage.setSequenceNumber(54433221);
		byte[] acamp_bytes = sendMessage.serialize();
		ControllerAgent.sendMessage(
				ap.connectedSwitch, 
				ap.connectedSwitchPort, 
				ControllerAgent.controllerMacAddr, 
				MacAddress.of(ap.getApMacAddress()), 
				ControllerAgent.controllerIpAddr, 
				IPv4Address.of(ap.getApIpAddress()), 
				TransportPort.of(ControllerAgent.PROTOCOL_PORT), 
				ap.udpPort, 
				acamp_bytes
				);
		JSONObject jsonObject = JSONObject.fromObject(ap);
		return jsonObject.toString();
		
	}
	
	@Post
	public void setApConfiguration(String json) {
		String param = (String) getRequestAttributes().get("apid");
		int apid = Integer.valueOf(param).intValue();
		DeviceAp ap = null;
		for(DeviceAp temp:ControllerAgent.apList) {
			if(temp.getApId() == apid) {
				ap = temp;
			}
		}
		if(ap == null) {
			System.out.println("Can not find ap with id = " + apid);
			return;
		}
//		JSONObject jsonObject = JSONObject.fromObject(json);
//		DeviceAp ap = (DeviceAp)JSONObject.toBean(jsonObject, DeviceAp.class);
		AcampMessage sendMessage = new AcampMessage();
		sendMessage.setAPID(ap.getApId());
		sendMessage.setMessageType(ConstAcampMessage.CONFIGURATION_DELIVER);
		sendMessage.setProtocolType(ConstAcampMessage.ACAMP_CONTROL_TYPE);
		sendMessage.setProtocolVersion(ConstAcampMessage.ACAMP_01);
		sendMessage.setSequenceNumber(54433221);
		sendMessage.addSsid("wlan_change");
		sendMessage.addChannel((byte)3);
		sendMessage.addHardwareMode(ConstAcampHwMode.IEEE802DOT11b);
		sendMessage.addSuppressSsid(ConstAcampSuppressSsid.SUPPRESS_BY_SENDING_ASCII_ZERO);
		sendMessage.addSecuritySetting(ConstAcampSecurity.WPA_PSK_WPA2_PSK);
		sendMessage.addWpaVersion(ConstAcampWpaSetting.VERSION_WPA_TWO);
		sendMessage.addWpaPassphrase("openssesame");
		sendMessage.addWpaGroupRekey(3000);
		sendMessage.addWpaPairwise(ConstAcampWpaSetting.PAIRWISE_CCMP);
		sendMessage.addWpaKeyManagement(ConstAcampWpaSetting.KM_WPA_PSK);
		byte[] acamp_bytes = sendMessage.serialize();
		ControllerAgent.sendMessage(
				ap.connectedSwitch, 
				ap.connectedSwitchPort, 
				ControllerAgent.controllerMacAddr, 
				MacAddress.of(ap.getApMacAddress()), 
				ControllerAgent.controllerIpAddr, 
				IPv4Address.of(ap.getApIpAddress()), 
				TransportPort.of(ControllerAgent.PROTOCOL_PORT), 
				ap.udpPort, 
				acamp_bytes
				);
//		ControllerAgent.apList.add(ap);
	}

}
