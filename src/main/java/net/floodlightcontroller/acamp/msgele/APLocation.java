package net.floodlightcontroller.acamp.msgele;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class APLocation extends BasePacket implements IPacket {

	String location;
	
	@Override
	public byte[] serialize() {
		byte[] locationBytes = null;
		if(this.location != null) {
			locationBytes = this.location.getBytes();
		}
		return locationBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		this.location = new String(data);
		return this;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
