package net.floodlightcontroller.acamp.msgele;

import java.nio.ByteBuffer;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class WlanInformation extends BasePacket implements IPacket {

	private byte radioId;
	private byte wlanId;
	private short Capability;
	private byte keyIndex;
	private byte keyStatus;
	private short keyLength;
	private byte[] key;
	private byte[] groupTSC;	//length fixed to 6 bytes
	private byte qos;
	private byte authType;
	private byte suppressSSID;
	private String ssid;
	
	@Override
	public byte[] serialize() {
		byte[] wlanInfoBytes = new byte[17 + this.key.length + this.ssid.length()];
		ByteBuffer bb = ByteBuffer.wrap(wlanInfoBytes);
		bb.put(this.radioId);
		bb.put(this.wlanId);
		bb.putShort(this.Capability);
		bb.put(this.keyIndex);
		bb.put(this.keyStatus);
		bb.putShort(this.keyLength);
		if(this.key.length != 0) {
			bb.put(this.key);
		}
		if(this.groupTSC.length != 0) {
			bb.put(this.groupTSC);
		}
		bb.put(this.qos);
		bb.put(this.authType);
		bb.put(this.suppressSSID);
		if(ssid.length() != 0) {
			bb.put(ssid.getBytes());
		}
		return wlanInfoBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			ByteBuffer bb = ByteBuffer.wrap(data);
			this.radioId = bb.get();
			this.wlanId = bb.get();
			this.Capability = bb.getShort();
			this.keyIndex = bb.get();
			this.keyStatus = bb.get();
			this.keyLength = bb.getShort();
			if(this.keyLength != 0) {
				byte[] keyBytes = new byte[this.keyLength];
				bb.get(keyBytes);
				this.key = keyBytes;
			}
			byte[] groupTSCBytes = new byte[6];
			bb.get(groupTSCBytes);
			this.groupTSC = groupTSCBytes;
			this.qos = bb.get();
			this.authType = bb.get();
			this.suppressSSID = bb.get();
			byte[] stringBytes = new byte[bb.remaining()];
			bb.get(stringBytes);
			this.ssid = new String(stringBytes);
		}
		return this;
	}

	public byte getRadioId() {
		return radioId;
	}

	public WlanInformation setRadioId(byte radioId) {
		this.radioId = radioId;
		return this;
	}

	public byte getWlanId() {
		return wlanId;
	}

	public WlanInformation setWlanId(byte wlanId) {
		this.wlanId = wlanId;
		return this;
	}

	public short getCapability() {
		return Capability;
	}

	public WlanInformation setCapability(short capability) {
		Capability = capability;
		return this;
	}

	public byte getKeyIndex() {
		return keyIndex;
	}

	public WlanInformation setKeyIndex(byte keyIndex) {
		this.keyIndex = keyIndex;
		return this;
	}

	public byte getKeyStatus() {
		return keyStatus;
	}

	public WlanInformation setKeyStatus(byte keyStatus) {
		this.keyStatus = keyStatus;
		return this;
	}

	public short getKeyLength() {
		return keyLength;
	}

	public byte[] getKey() {
		return key;
	}

	public WlanInformation setKey(byte[] key) {
		this.key = key;
		return this;
	}

	public byte[] getGroupTSC() {
		return groupTSC;
	}

	public WlanInformation setGroupTSC(byte[] groupTSC) {
		this.groupTSC = groupTSC;
		return this;
	}

	public byte getQos() {
		return qos;
	}

	public WlanInformation setQos(byte qos) {
		this.qos = qos;
		return this;
	}

	public byte getAuthType() {
		return authType;
	}

	public WlanInformation setAuthType(byte authType) {
		this.authType = authType;
		return this;
	}

	public byte getSuppressSSID() {
		return suppressSSID;
	}

	public WlanInformation setSuppressSSID(byte suppressSSID) {
		this.suppressSSID = suppressSSID;
		return this;
	}

	public String getSsid() {
		return ssid;
	}

	public WlanInformation setSsid(String ssid) {
		this.ssid = ssid;
		return this;
	}
	

}
