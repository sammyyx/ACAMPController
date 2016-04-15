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
	private byte[] groupTSC = new byte[6];	//length fixed to 6 bytes
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
		if(ssid != null) {
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
				bb.get(keyBytes, 0, this.keyLength);
				this.key = keyBytes;
			}
			bb.get(this.groupTSC, 0, 6);
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

	public void setRadioId(byte radioId) {
		this.radioId = radioId;
	}

	public byte getWlanId() {
		return wlanId;
	}

	public void setWlanId(byte wlanId) {
		this.wlanId = wlanId;
	}

	public short getCapability() {
		return Capability;
	}

	public void setCapability(short capability) {
		Capability = capability;
	}

	public byte getKeyIndex() {
		return keyIndex;
	}

	public void setKeyIndex(byte keyIndex) {
		this.keyIndex = keyIndex;
	}

	public byte getKeyStatus() {
		return keyStatus;
	}

	public void setKeyStatus(byte keyStatus) {
		this.keyStatus = keyStatus;
	}

	public short getKeyLength() {
		return keyLength;
	}

	public void setKeyLength(short keyLength) {
		this.keyLength = keyLength;
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public byte[] getGroupTSC() {
		return groupTSC;
	}

	public void setGroupTSC(byte[] groupTSC) {
		this.groupTSC = groupTSC;
	}

	public byte getQos() {
		return qos;
	}

	public void setQos(byte qos) {
		this.qos = qos;
	}

	public byte getAuthType() {
		return authType;
	}

	public void setAuthType(byte authType) {
		this.authType = authType;
	}

	public byte getSuppressSSID() {
		return suppressSSID;
	}

	public void setSuppressSSID(byte suppressSSID) {
		this.suppressSSID = suppressSSID;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	

}
