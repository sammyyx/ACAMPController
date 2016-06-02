package net.floodlightcontroller.acamp.entity;
public class DeviceStation {
	private byte[] ipAddress;
	private byte[] macAddress;
	private long overallPacket;
	private long overallBytes;
	public byte[] getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(byte[] ipAddress) {
		this.ipAddress = ipAddress;
	}
	public byte[] getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(byte[] macAddress) {
		this.macAddress = macAddress;
	}
	public long getOverallPacket() {
		return overallPacket;
	}
	public void setOverallPacket(long overallPacket) {
		this.overallPacket = overallPacket;
	}
	public long getOverallBytes() {
		return overallBytes;
	}
	public void setOverallBytes(long overallBytes) {
		this.overallBytes = overallBytes;
	}
}