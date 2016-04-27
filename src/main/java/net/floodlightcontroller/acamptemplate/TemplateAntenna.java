package net.floodlightcontroller.acamptemplate;

public class TemplateAntenna {
	private short radioId;
	private byte diversity;
	private byte combiner;
	private short antennaCount;
	private byte[] antennaSelection;
	public short getRadioId() {
		return radioId;
	}
	public void setRadioId(short radioId) {
		this.radioId = radioId;
	}
	public byte getDiversity() {
		return diversity;
	}
	public void setDiversity(byte diversity) {
		this.diversity = diversity;
	}
	public byte getCombiner() {
		return combiner;
	}
	public void setCombiner(byte combiner) {
		this.combiner = combiner;
	}
	public short getAntennaCount() {
		return antennaCount;
	}
	public void setAntennaCount(short antennaCount) {
		this.antennaCount = antennaCount;
	}
	public byte[] getAntennaSelection() {
		return antennaSelection;
	}
	public void setAntennaSelection(byte[] antennaSelection) {
		this.antennaSelection = antennaSelection;
	}
}
