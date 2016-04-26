package net.floodlightcontroller.acamp.msgele;

import java.nio.ByteBuffer;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

import org.projectfloodlight.openflow.types.MacAddress;

public class APRadioInformation extends BasePacket implements IPacket {

	private short radioId;
	private byte shortPreamble;
	private short numberOfBSSIDs;
	private short dtimPeriod;
	private MacAddress BSSID;
	private int beaconPeriod;
	private int countryString;
	
	@Override
	public byte[] serialize() {
		byte[] apRadioInfobytes = new byte[16];
		ByteBuffer bb = ByteBuffer.wrap(apRadioInfobytes);
		bb.put((byte)this.radioId);
		bb.put(this.shortPreamble);
		bb.put((byte)this.numberOfBSSIDs);
		bb.put((byte)this.dtimPeriod);
		if(BSSID != null) {
			bb.put(BSSID.getBytes());
		}
		bb.putInt(beaconPeriod);
		bb.putInt(countryString);
		return apRadioInfobytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			ByteBuffer bb = ByteBuffer.wrap(data);
			this.radioId = (short)(bb.get() & 0x0ffff);
			this.shortPreamble = bb.get();
			this.numberOfBSSIDs = (short)(bb.get() & 0x0ffff);
			this.dtimPeriod = (short)(bb.get() & 0x0ffff);
			byte[] bssidBytes = new byte[6];
			bb.get(bssidBytes, 0, 6);
			this.BSSID = MacAddress.of(bssidBytes);
			this.beaconPeriod = (int)(bb.getShort() & 0x0ffffffff);
			this.countryString = bb.getInt();
		}
		return this;
	}

	public short getRadioId() {
		return radioId;
	}

	public void setRadioId(short radioId) {
		this.radioId = radioId;
	}

	public short getShortPreamble() {
		return shortPreamble;
	}

	public void setShortPreamble(byte shortPreamble) {
		this.shortPreamble = shortPreamble;
	}

	public short getNumberOfBSSIDs() {
		return numberOfBSSIDs;
	}

	public void setNumberOfBSSIDs(short numberOfBSSIDs) {
		this.numberOfBSSIDs = numberOfBSSIDs;
	}

	public short getDtimPeriod() {
		return dtimPeriod;
	}

	public void setDtimPeriod(short dtimPeriod) {
		this.dtimPeriod = dtimPeriod;
	}

	public MacAddress getBSSID() {
		return BSSID;
	}

	public void setBSSID(MacAddress bSSID) {
		BSSID = bSSID;
	}

	public int getBeaconPeriod() {
		return beaconPeriod;
	}

	public void setBeaconPeriod(int beaconPeriod) {
		this.beaconPeriod = beaconPeriod;
	}

	public int getCountryString() {
		return countryString;
	}

	public void setCountryString(int countryString) {
		this.countryString = countryString;
	}
	

}
