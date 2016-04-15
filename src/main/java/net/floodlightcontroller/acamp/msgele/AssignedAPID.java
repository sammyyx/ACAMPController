package net.floodlightcontroller.acamp.msgele;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class AssignedAPID extends BasePacket implements IPacket {

	private short apid;
	@Override
	public byte[] serialize() {
		byte[] assginedApid = new byte[1];
		assginedApid[0] = (byte)this.apid; 
		return assginedApid;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			this.apid = (short)(data[0] & 0x0ffff);
		}
		return this;
	}

	public short getApid() {
		return apid;
	}

	public void setApid(short apid) {
		this.apid = apid;
	}

}
