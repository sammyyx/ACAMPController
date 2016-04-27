package net.floodlightcontroller.acamptemplate;

public class TemplateWlanInfo {
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
