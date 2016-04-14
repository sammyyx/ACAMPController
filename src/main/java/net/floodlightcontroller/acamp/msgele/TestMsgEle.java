package net.floodlightcontroller.acamp.msgele;

import java.nio.ByteBuffer;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class TestMsgEle extends BasePacket implements IPacket {
	protected int typeValue;
	
	
	@Override
	public byte[] serialize() {
		byte[] data = new byte[4];
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.putInt(typeValue);
		return data;
	}

	public int getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(int typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
		this.typeValue = bb.getInt();
		return this;
	}

}
