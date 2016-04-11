package net.floodlightcontroller.acamp.message;

public class ACAMPMessage {
	/**
	 * undone
	 */
	public final static byte REGISTER_REQUEST = 0x21;
	public final static byte REGISTER_RESPONSE = 0x22;
	public final static byte DISCONNET_REQUEST = 0x21;
	public final static byte DISCONNET_RESPONSE = 0x21;
	public final static byte CONFIGURATION_REQUEST = 0x21;
	public final static byte CONFIGURATION_RESPONSE = 0x21;
//	public final static byte REGISTER_REQUEST = 0x21;
//	public final static byte REGISTER_REQUEST = 0x21;
	
	private ACAMPHeader acampHeader;
	private ACAMPMessageElement acampMessageElement[];
	
	/**
	 * 描述：此函数将字节流构造为ACAMPMessage类型
	 * @param recv
	 */
	public void deSerialize(byte[] recv) {
		
	}
	
	/**
	 * 描述：此函数将头部和消息元素构造为ACAMPMessage类型
	 * @param header
	 * @param messageElement
	 */
	public ACAMPMessage(ACAMPHeader header, ACAMPMessageElement[] messageElement) {
		
	}
	
	/**
	 * 描述：此函数将ACAMP消息序列化
	 * @param acampMessage
	 * @return
	 */
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
