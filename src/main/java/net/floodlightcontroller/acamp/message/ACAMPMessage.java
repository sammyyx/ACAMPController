package net.floodlightcontroller.acamp.message;

public class ACAMPMessage {
	
	/*****************所有消息类型在此处定义*********************/
	public final static byte REGISTER_REQUEST = 0x11;
	public final static byte REGISTER_RESPONSE = 0x12;
	public final static byte DISCONNET_REQUEST = 0x13;
	public final static byte DISCONNET_RESPONSE = 0x14;
	public final static byte CONFIGURATION_REQUEST = 0x21;
	public final static byte CONFIGURATION_RESPONSE = 0x22;
	public final static byte CONFIGURATION_RESET_REQ = 0x23;
	public final static byte CONFIGURATION_RESET_RSP = 0x24;
	public final static byte STATISTIC_STAT_RP = 0x31;
	public final static byte STATISTIC_STAT_QUERY = 0x32;
	public final static byte STATISTIC_STAT_REPLY = 0x33;
	public final static byte STAT_REQUEST = 0x41;
	public final static byte STAT_RESPONSE = 0x42;
	/*********************************************************/
	private ACAMPHeader acampHeader;					//ACAMP头部
	private ACAMPMessageElement acampMessageElement[];	//ACAMP消息元素数组
	/*********************************************************
	 * 描述：此函数将字节流构造为ACAMPMessage类型
	 * @param recv
	 *********************************************************/
	public void deSerialize(byte[] recv) {
		
	}
	
	/*********************************************************
	 * 描述：此函数将头部和消息元素构造为ACAMPMessage类型
	 * @param header
	 * @param messageElement
	 *********************************************************/
	public ACAMPMessage(ACAMPHeader header, ACAMPMessageElement[] messageElement) {
		
	}
	
	/*********************************************************
	 * 描述：此函数将ACAMP消息序列化
	 * @param acampMessage
	 * @return
	 *********************************************************/
	public byte[] serialize() {
		byte[] ret = null;
		return ret;
	}

	public ACAMPHeader getAcampHeader() {
		return acampHeader;
	}

	public void setAcampHeader(ACAMPHeader acampHeader) {
		this.acampHeader = acampHeader;
	}

	public ACAMPMessageElement[] getAcampMessageElement() {
		return acampMessageElement;
	}

	public void setAcampMessageElement(ACAMPMessageElement[] acampMessageElement) {
		this.acampMessageElement = acampMessageElement;
	}
	
	


}
