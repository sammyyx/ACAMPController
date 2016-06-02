package net.floodlightcontroller.acamp.restapi;

import net.floodlightcontroller.acamp.entity.DeviceAp;
import net.floodlightcontroller.acamp.message.AcampMessage;
import net.floodlightcontroller.acamp.message.ConstAcampMessage;
import net.floodlightcontroller.acamp.process.ControllerAgent;

import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.MacAddress;
import org.projectfloodlight.openflow.types.TransportPort;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AcampStationResource extends ServerResource{
    @Get("json")
    public String stationOperation() {
    	String param1 = (String) getRequestAttributes().get("apid");
    	String param2 = (String) getRequestAttributes().get("operation");
    	String param3 = (String) getRequestAttributes().get("macAddress");
		int apid = Integer.valueOf(param1).intValue();
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
		sendMessage.setMessageType(ConstAcampMessage.STATION_CONFIGURATION_DELIVER);
		sendMessage.setProtocolType(ConstAcampMessage.ACAMP_CONTROL_TYPE);
		sendMessage.setProtocolVersion(ConstAcampMessage.ACAMP_01);
		sendMessage.setSequenceNumber(54433221);
		if(param2.equals("add")) {
			sendMessage.addStation(MacAddress.of(param3).getBytes());
		}
		if(param2.equals("del")) {
			sendMessage.delStation(MacAddress.of(param3).getBytes());
		}
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
		return null;
    }
}
