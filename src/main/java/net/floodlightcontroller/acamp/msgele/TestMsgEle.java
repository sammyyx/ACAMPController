package net.floodlightcontroller.acamp.msgele;

import java.nio.ByteBuffer;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class TestMsgEle extends BasePacket implements IPacket {
	protected String typeValue;
	
	
	@Override
	public byte[] serialize() {
		byte[] valueBytes = null;
		if(this.typeValue != null) {
			valueBytes = this.typeValue.getBytes();
		}
		return valueBytes;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		this.typeValue = new String(data);
		return this;
	}

}
