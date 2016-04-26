package net.floodlightcontroller.acamp.msgele;

import java.nio.ByteBuffer;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class SupportedRates extends BasePacket implements IPacket {

	private short radioIp;
	private byte[] supportedRates;
	
	@Override
	public byte[] serialize() {
		byte[] supportedRatesBytes = new byte[1 + supportedRates.length];
		ByteBuffer bb = ByteBuffer.wrap(supportedRatesBytes);
		bb.put((byte)radioIp);
		if(supportedRates.length != 0) {
			bb.put(supportedRates);
		}
		return supportedRatesBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			ByteBuffer bb = ByteBuffer.wrap(data);
			this.radioIp = (short)(bb.get() & 0x0ffff);
			bb.get(this.supportedRates);
		}
		return this;
	}

	public short getRadioIp() {
		return radioIp;
	}

	public void setRadioIp(short radioIp) {
		this.radioIp = radioIp;
	}

	public byte[] getSupportedRates() {
		return supportedRates;
	}

	public void setSupportedRates(byte[] supportedRates) {
		this.supportedRates = supportedRates;
	}

}
