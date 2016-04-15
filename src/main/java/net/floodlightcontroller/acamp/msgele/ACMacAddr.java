package net.floodlightcontroller.acamp.msgele;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

import org.projectfloodlight.openflow.types.MacAddress;

public class ACMacAddr extends BasePacket implements IPacket {

	private MacAddress acMacAddr;
	@Override
	public byte[] serialize() {
		byte[] acMacAddrBytes = null;
		if(acMacAddr != null) {
			acMacAddrBytes = acMacAddr.getBytes();
		}
		return acMacAddrBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			acMacAddr = MacAddress.of(data);
		}
		return this;
	}

	public MacAddress getAcMacAddr() {
		return acMacAddr;
	}

	public void setAcMacAddr(MacAddress acMacAddr) {
		this.acMacAddr = acMacAddr;
	}

}
