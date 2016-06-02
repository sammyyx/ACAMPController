package net.floodlightcontroller.acamp.restapi;

import net.floodlightcontroller.acamp.entity.DeviceAp;
import net.floodlightcontroller.acamp.entity.DeviceStation;
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

public class AcampStatisticResource extends ServerResource{

    @Get("json")
    public String getApStatistic() {
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
//		JSONObject jsonObject = JSONObject.fromObject(json);
//		DeviceAp ap = (DeviceAp)JSONObject.toBean(jsonObject, DeviceAp.class);
		AcampMessage sendMessage = new AcampMessage();
		sendMessage.setAPID(ap.getApId());
		sendMessage.setMessageType(ConstAcampMessage.STATISTIC_REQUEST);
		sendMessage.setProtocolType(ConstAcampMessage.ACAMP_CONTROL_TYPE);
		sendMessage.setProtocolVersion(ConstAcampMessage.ACAMP_01);
		sendMessage.setSequenceNumber(54433221);
		sendMessage.addFlowStatistic();
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
        DeviceStation station = new DeviceStation();
        IPv4Address stationIpAddr = IPv4Address.of("10.10.10.1");
        MacAddress stationMacAddr = MacAddress.of("44:22:12:ff:12:11");
        station.setIpAddress(stationIpAddr.getBytes());
        station.setMacAddress(stationMacAddr.getBytes());
        station.setOverallPacket(2000);
        station.setOverallBytes(4800000);
        JSONObject jsonObject = JSONObject.fromObject(station);
        return jsonObject.toString();
    }
}
