package net.floodlightcontroller.acamptemplate;

public class TemplateTxPower {
	private byte radioId;
	private byte numberLevel;
	private short[] powerLevel;
	public byte getRadioId() {
		return radioId;
	}
	public void setRadioId(byte radioId) {
		this.radioId = radioId;
	}
	public byte getNumberLevel() {
		return numberLevel;
	}
	public void setNumberLevel(byte numberLevel) {
		this.numberLevel = numberLevel;
	}
	public short[] getPowerLevel() {
		return powerLevel;
	}
	public void setPowerLevel(short[] powerLevel) {
		this.powerLevel = powerLevel;
	}
}
