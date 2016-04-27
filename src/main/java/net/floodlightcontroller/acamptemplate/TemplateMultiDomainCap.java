package net.floodlightcontroller.acamptemplate;

public class TemplateMultiDomainCap {
	private short radioId;
	private byte reserve;
	private int firstChannel;
	private int numberOfChannels;
	private int maxTxPowerLevel;
	public short getRadioId() {
		return radioId;
	}
	public void setRadioId(short radioId) {
		this.radioId = radioId;
	}
	public byte getReserve() {
		return reserve;
	}
	public void setReserve(byte reserve) {
		this.reserve = reserve;
	}
	public int getFirstChannel() {
		return firstChannel;
	}
	public void setFirstChannel(int firstChannel) {
		this.firstChannel = firstChannel;
	}
	public int getNumberOfChannels() {
		return numberOfChannels;
	}
	public void setNumberOfChannels(int numberOfChannels) {
		this.numberOfChannels = numberOfChannels;
	}
	public int getMaxTxPowerLevel() {
		return maxTxPowerLevel;
	}
	public void setMaxTxPowerLevel(int maxTxPowerLevel) {
		this.maxTxPowerLevel = maxTxPowerLevel;
	}
}
