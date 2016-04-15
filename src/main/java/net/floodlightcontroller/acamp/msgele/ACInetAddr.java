package net.floodlightcontroller.acamp.msgele;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

import org.projectfloodlight.openflow.types.IPv4Address;

public class ACInetAddr extends BasePacket implements IPacket {

	private IPv4Address acInetAddr;
	@Override
	public byte[] serialize() {
		byte[] acInetAddrByte = null;
		if(acInetAddr != null) {
			acInetAddrByte = acInetAddr.getBytes();
		}
		return acInetAddrByte;
	}
	
	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			this.acInetAddr = IPv4Address.of(data);
		}
		return this;
	}

	public IPv4Address getAcInetAddr() {
		return acInetAddr;
	}

	public void setAcInetAddr(IPv4Address acInetAddr) {
		this.acInetAddr = acInetAddr;
	}


}
