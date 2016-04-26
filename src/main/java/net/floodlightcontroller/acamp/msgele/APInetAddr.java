package net.floodlightcontroller.acamp.msgele;

import org.projectfloodlight.openflow.types.IPv4Address;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class APInetAddr extends BasePacket implements IPacket {

	private IPv4Address apInetAddr;
	@Override
	public byte[] serialize() {
		byte[] apInetAddrByte = null;
		if(apInetAddr != null) {
			apInetAddrByte = apInetAddr.getBytes();
		}
		return apInetAddrByte;
	}
	
	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			this.apInetAddr = IPv4Address.of(data);
		}
		return this;
	}

	public IPv4Address getAcInetAddr() {
		return apInetAddr;
	}

	public void setAcInetAddr(IPv4Address apInetAddr) {
		this.apInetAddr = apInetAddr;
	}


}
