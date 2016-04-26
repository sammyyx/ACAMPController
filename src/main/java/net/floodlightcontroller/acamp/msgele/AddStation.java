package net.floodlightcontroller.acamp.msgele;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

import org.projectfloodlight.openflow.types.MacAddress;

public class AddStation extends BasePacket implements IPacket {

	private MacAddress staMacAddress;
	@Override
	public byte[] serialize() {
		byte[] staMacAddressBytes = null;
		if(staMacAddress != null) {
			staMacAddressBytes = staMacAddress.getBytes();
		}
		return staMacAddressBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			this.staMacAddress = MacAddress.of(data);
		}
		return this;
	}

	public MacAddress getStaMacAddress() {
		return staMacAddress;
	}

	public void setStaMacAddress(MacAddress staMacAddress) {
		this.staMacAddress = staMacAddress;
	}
	

}
