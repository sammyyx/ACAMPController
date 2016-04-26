package net.floodlightcontroller.acamp.msgele;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class APName extends BasePacket implements IPacket {

	String apName;
	
	@Override
	public byte[] serialize() {
		byte[] nameBytes = null;
		if(this.apName != null) {
			nameBytes = this.apName.getBytes();
		}
		return nameBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		this.apName = new String(data);
		return this;
	}

}
