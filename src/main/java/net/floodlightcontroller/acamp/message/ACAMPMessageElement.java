package net.floodlightcontroller.acamp.message;

public class ACAMPMessageElement {
	
	/**
	 * undone
	 */
	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
//	public final static short RESULT_CODE = 0x0001;
	
	private short messageElementType;
	private short messageElementLength;
	private int messageElementValue;
	public short getMessageElementType() {
		return messageElementType;
	}
	public void setMessageElementType(short messageElementType) {
		this.messageElementType = messageElementType;
	}
	public short getMessageElementLength() {
		return messageElementLength;
	}
	public void setMessageElementLength(short messageElementLength) {
		this.messageElementLength = messageElementLength;
	}
	public int getMessageElementValue() {
		return messageElementValue;
	}
	public void setMessageElementValue(int messageElementValue) {
		this.messageElementValue = messageElementValue;
	}
	

}
