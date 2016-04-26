package net.floodlightcontroller.acamp.msgele;

import java.nio.ByteBuffer;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

import org.projectfloodlight.openflow.types.MacAddress;

public class AddMacACLEntry extends BasePacket implements IPacket {

	private short entryNumbers;
	private MacAddress[] aclMacAddress;
	@Override
	public byte[] serialize() {
		byte[] addMacACLEntryBytes = null;
		if(entryNumbers != 0 && aclMacAddress.length != 0) {
			addMacACLEntryBytes = new byte[6*entryNumbers + 1];
		}
		ByteBuffer bb = ByteBuffer.wrap(addMacACLEntryBytes);
		bb.put((byte)entryNumbers);
		for(int i = 0; i < entryNumbers; i++) {
			bb.put(aclMacAddress[i].getBytes());
		}
		return addMacACLEntryBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			ByteBuffer bb = ByteBuffer.wrap(data);
			this.entryNumbers = (short)(bb.get() & 0x0ffff);
			for(int i = 0; i < entryNumbers; i++) {
				byte[] macAddressbytes = new byte[6];
				bb.get(macAddressbytes, 0, 6);
				this.aclMacAddress[i] = MacAddress.of(macAddressbytes);
			}
		}
		return this;
	}

	public short getEntryNumbers() {
		return entryNumbers;
	}

	public void setEntryNumbers(short entryNumbers) {
		this.entryNumbers = entryNumbers;
	}

	public MacAddress[] getAclMacAddress() {
		return aclMacAddress;
	}

	public void setAclMacAddress(MacAddress[] aclMacAddress) {
		this.aclMacAddress = aclMacAddress;
	}

}
