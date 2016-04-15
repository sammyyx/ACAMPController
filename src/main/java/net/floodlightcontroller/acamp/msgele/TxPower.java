package net.floodlightcontroller.acamp.msgele;

import java.nio.ByteBuffer;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class TxPower extends BasePacket implements IPacket {

	private byte radioId;
	private byte numberLevel;
	private short[] powerLevel;
	
	@Override
	public byte[] serialize() {
		byte[] txPowerBytes = new byte[2 + this.powerLevel.length * 2];
		ByteBuffer bb = ByteBuffer.wrap(txPowerBytes);
		bb.put(this.radioId);
		bb.put(this.numberLevel);
		if(this.powerLevel.length != 0) {
			for(int i = 0; i < this.powerLevel.length; i++) {
				bb.putShort(this.powerLevel[i]);
			}
		}
		return txPowerBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			ByteBuffer bb = ByteBuffer.wrap(data);
			this.radioId = bb.get();
			this.numberLevel = bb.get();
			for(int i = 0; i < this.numberLevel; i++) {
				this.powerLevel[i] = bb.getShort();
			}
		}
		return this;
	}

	public byte getRadioId() {
		return radioId;
	}

	public void setRadioId(byte radioId) {
		this.radioId = radioId;
	}

	public byte getNumberLevel() {
		return numberLevel;
	}

	public void setNumberLevel(byte numberLevel) {
		this.numberLevel = numberLevel;
	}

	public short[] getPowerLevel() {
		return powerLevel;
	}

	public void setPowerLevel(short[] powerLevel) {
		this.powerLevel = powerLevel;
	}

}
