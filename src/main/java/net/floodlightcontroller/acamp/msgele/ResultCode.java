package net.floodlightcontroller.acamp.msgele;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class ResultCode extends BasePacket implements IPacket {

	public static final byte FAILED 	= 0x00;
	public static final byte SUCCESS 	= 0x01;
	
	private byte resultCode;
	@Override
	public byte[] serialize() {
		byte[] resultCodeBytes = new byte[1];
		resultCodeBytes[0] = resultCode;
		return resultCodeBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			this.resultCode = data[0];
		}
		return this;
	}

	public byte getResultCode() {
		return resultCode;
	}

	public void setResultCode(byte resultCode) {
		this.resultCode = resultCode;
	}

}
