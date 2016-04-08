package net.floodlightcontroller.acamp.message;

public class ACAMPHeader {

	private static final int preamble = 0x01;
	private byte version;
	private byte type;
	private short APID;
	private int sequenceNumber;
	private short messageType;
	private short messageLength;
	
	public byte getVersion() {
		return version;
	}
	public void setVersion(byte version) {
		this.version = version;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public short getAPID() {
		return APID;
	}
	public void setAPID(short aPID) {
		APID = aPID;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public short getMessageType() {
		return messageType;
	}
	public void setMessageType(short messageType) {
		this.messageType = messageType;
	}
	public short getMessageLength() {
		return messageLength;
	}
	public void setMessageLength(short messageLength) {
		this.messageLength = messageLength;
	}
	public static int getPreamble() {
		return preamble;
	}

}
