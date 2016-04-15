package net.floodlightcontroller.acamp.msgele;

import java.nio.ByteBuffer;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class Timestamp extends BasePacket implements IPacket {

	private int timestamp;
	@Override
	public byte[] serialize() {
		byte[] timestampBytes = new byte[4];
		ByteBuffer bb = ByteBuffer.wrap(timestampBytes);
		bb.putInt(timestamp);
		return timestampBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			ByteBuffer bb = ByteBuffer.wrap(data);
			this.timestamp = bb.getInt();
		}
		return this;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	

}
