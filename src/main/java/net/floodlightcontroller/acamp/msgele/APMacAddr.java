package net.floodlightcontroller.acamp.msgele;

import org.projectfloodlight.openflow.types.MacAddress;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class APMacAddr extends BasePacket implements IPacket {

	private MacAddress apMacAddr;
	@Override
	public byte[] serialize() {
		byte[] apMacAddrBytes = null;
		if(apMacAddr != null) {
			apMacAddrBytes = apMacAddr.getBytes();
		}
		return apMacAddrBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			apMacAddr = MacAddress.of(data);
		}
		return this;
	}

	public MacAddress getAcMacAddr() {
		return apMacAddr;
	}

	public void setAcMacAddr(MacAddress apMacAddr) {
		this.apMacAddr = apMacAddr;
	}

}
