package net.floodlightcontroller.acamp.msgele;

import java.nio.ByteBuffer;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class ReasonCode extends BasePacket implements IPacket {

	public static final int DEFAULT 			= 0x0000;
	public static final int AC_LONGTIME_NOREPLY = 0x0101;
	public static final int AP_LONGTIME_NOREPLY = 0x0102;
	
	private int reasonCode;
	
	@Override
	public byte[] serialize() {
		byte[] reasonCodeBytes = new byte[4];
		ByteBuffer bb = ByteBuffer.wrap(reasonCodeBytes);
		bb.putInt(this.reasonCode);
		return reasonCodeBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			ByteBuffer bb = ByteBuffer.wrap(data);
			this.reasonCode = bb.getInt();
		}
		return this;
	}

	public int getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}


}
