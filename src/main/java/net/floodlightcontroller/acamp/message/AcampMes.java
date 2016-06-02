package net.floodlightcontroller.acamp.message;

import java.nio.ByteBuffer;

public class AcampMes {
	private short meType;
	private int meLength;
	private int remainingMeLength;
	private byte[] AcampMesBytes;
	public AcampMes(byte[] AcampMes) {
		this.AcampMesBytes = AcampMes;
		this.meType = -1;
		this.meLength = -1;
		this.remainingMeLength = -1;
	}
	public boolean hasRemaining() {
		if(AcampMesBytes == null) {
			return false;
		}
		else {
			return true;
		}
	}
	public byte[] getMe() {
		if(!this.hasRemaining()) return null;
		ByteBuffer bb = ByteBuffer.wrap(this.AcampMesBytes);
		this.meType = bb.getShort();
		this.meLength = bb.getShort() & 0x0ffff;
		byte[] me = new byte[this.meLength];
		bb.get(me);
		this.remainingMeLength = this.AcampMesBytes.length - this.meLength - ConstAcampMe.HEADER_LENGTH;
		if(remainingMeLength == 0) {
			AcampMesBytes = null;
		}
		else {
			byte[] remainingMes = new byte[remainingMeLength];
			bb.get(remainingMes);
			this.AcampMesBytes = remainingMes;
		}
		return me;
	}
	public short getMeType() {
		return this.meType;
	}
	public int getMeLength() {
		return this.meLength;
	}
}
