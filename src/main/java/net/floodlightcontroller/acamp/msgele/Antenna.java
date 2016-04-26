package net.floodlightcontroller.acamp.msgele;

import java.nio.ByteBuffer;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class Antenna extends BasePacket implements IPacket {

	public static final byte DIVERSITY_DISENABLE = 0;
	public static final byte DIVERSITY_ENABLE = 1;
	public static final byte COMBINER_LEFT = 1;
	public static final byte COMBINER_RIGHT = 2;
	public static final byte COMBINER_OMNI = 3;
	public static final byte COMBINER_MIMO = 4;
	public static final byte INTERNAL_ANTENNA = 1;
	public static final byte EXTERNAL_ANTENNA = 2;

	private short radioId;
	private byte diversity;
	private byte combiner;
	private short antennaCount;
	private byte[] antennaSelection;
	
	@Override
	public byte[] serialize() {
		byte[] antennaBytes = new byte[4 + this.antennaCount];
		ByteBuffer bb = ByteBuffer.wrap(antennaBytes);
		bb.put((byte)this.radioId);
		bb.put(this.diversity);
		bb.put(this.combiner);
		bb.put((byte)this.antennaCount);
		if(this.antennaCount != 0) {
			for(int i = 0; i < this.antennaCount; i++) {
				bb.put(antennaSelection[i]);
			}
		}
		return antennaBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			ByteBuffer bb = ByteBuffer.wrap(data);
			this.radioId = (short)(bb.get() & 0x0ffff);
			this.diversity = bb.get();
			this.combiner = bb.get();;
			this.antennaCount = (short)(bb.get() & 0x0ffff);
			if(this.antennaCount != 0) {
				this.antennaSelection = new byte[this.antennaCount];
				for(int i = 0; i < this.antennaCount; i++) {
					this.antennaSelection[i] = bb.get();
				}
			}		
		}
		return this;
	}

	public short getRadioId() {
		return radioId;
	}

	public void setRadioId(short radioId) {
		this.radioId = radioId;
	}

	public byte getDiversity() {
		return diversity;
	}

	public void setDiversity(byte diversity) {
		this.diversity = diversity;
	}

	public byte getCombiner() {
		return combiner;
	}

	public void setCombiner(byte combiner) {
		this.combiner = combiner;
	}

	public short getAntennaCount() {
		return antennaCount;
	}

	public void setAntennaCount(short antennaCount) {
		this.antennaCount = antennaCount;
	}

	public byte[] getAntennaSelection() {
		return antennaSelection;
	}

	public void setAntennaSelection(byte[] antennaSelection) {
		this.antennaSelection = antennaSelection;
	}

}
