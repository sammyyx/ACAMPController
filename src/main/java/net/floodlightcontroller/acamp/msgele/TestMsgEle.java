package net.floodlightcontroller.acamp.msgele;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class TestMsgEle extends BasePacket implements IPacket {
	protected byte[] data;
	
	
	@Override
	public byte[] serialize() {
		return data;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		this.data = data;
		return this;
	}
	
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
