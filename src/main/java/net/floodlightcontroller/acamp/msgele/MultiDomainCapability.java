package net.floodlightcontroller.acamp.msgele;

import java.nio.ByteBuffer;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class MultiDomainCapability extends BasePacket implements IPacket {

	private short radioIp;
	private byte reserve;
	private int firstChannel;
	private int numberOfChannels;
	private int maxTxPowerLevel;
	
	@Override
	public byte[] serialize() {
		byte[] multiDomainCapBytes = new byte[8];
		ByteBuffer bb = ByteBuffer.wrap(multiDomainCapBytes);
		bb.put((byte)(this.radioIp));
		bb.put(this.reserve);
		bb.putShort((short)(this.firstChannel));
		bb.putShort((short)(this.numberOfChannels));
		bb.putShort((short)(this.maxTxPowerLevel));
		return multiDomainCapBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			ByteBuffer bb = ByteBuffer.wrap(data);
			this.radioIp = (short)(bb.get() & 0x0ffff);
			this.reserve = bb.get();
			this.firstChannel = (int)(bb.getShort() & 0x0ffffffff);
			this.numberOfChannels = (int)(bb.getShort() & 0x0ffffffff);
			this.maxTxPowerLevel = (int)(bb.getShort() & 0x0ffffffff);
		}
		return this;
	}

	public short getRadioIp() {
		return radioIp;
	}

	public void setRadioIp(short radioIp) {
		this.radioIp = radioIp;
	}

	public byte getReserve() {
		return reserve;
	}

	public void setReserve(byte reserve) {
		this.reserve = reserve;
	}

	public int getFirstChannel() {
		return firstChannel;
	}

	public void setFirstChannel(int firstChannel) {
		this.firstChannel = firstChannel;
	}

	public int getNumberOfChannels() {
		return numberOfChannels;
	}

	public void setNumberOfChannels(int numberOfChannels) {
		this.numberOfChannels = numberOfChannels;
	}

	public int getMaxTxPowerLevel() {
		return maxTxPowerLevel;
	}

	public void setMaxTxPowerLevel(int maxTxPowerLevel) {
		this.maxTxPowerLevel = maxTxPowerLevel;
	}

	
}
