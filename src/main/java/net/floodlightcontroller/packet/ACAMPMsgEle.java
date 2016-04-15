package net.floodlightcontroller.packet;

import java.nio.ByteBuffer;

import net.floodlightcontroller.acamp.msgele.APDescriptor;
import net.floodlightcontroller.acamp.msgele.APName;
import net.floodlightcontroller.acamp.msgele.TestMsgEle;


public class ACAMPMsgEle extends BasePacket implements IPacket{
	protected final static int ELE_HEADER_LEN = 4;
	/*****************所有消息元素类型在此处定义*******************/
	public final static short RESULT_CODE 		= 0x0001;
	public final static short REASON_CODE 		= 0x0002;
	public final static short ASSIGNED_APID	 	= 0x0003;
	public final static short AP_MAC_ADDR 		= 0x0101;
	public final static short AP_INET_ADDR  	= 0x0102;
	public final static short AP_NAME 	  		= 0x0103;
	public final static short AP_DESCRIPTION 	= 0x0104;
	public final static short AP_LOCATION		= 0x0105;
	public final static short AP_BOARD_DATA 	= 0x0106;
	public final static short AC_MAC_ADDR 		= 0x0201;
	public final static short AC_INET_ADDR 		= 0x0202;
	public final static short TIME_STAMP 		= 0x0203;
	public final static short WLAN_INFO		  	= 0x0301;
	public final static short AP_RADIO_INFO		= 0x0302;
	public final static short ANTENNA			= 0x0303;
	public final static short TX_POWER 			= 0x0304;
	public final static short MULTI_DOMAIN_CAP  = 0x0305;
	public final static short SUPPORTED_RATES   = 0x0306;
	public final static short ADD_MAC_ACL_ENTRY = 0x0401;
	public final static short DEL_MAC_ACL_ENTRY = 0x0402;
	public final static short ADD_STATION 		= 0x0403;
	public final static short DEL_STATION 		= 0x0404;
	public final static short STATION_EVENT 	= 0x0501;
	/*********************************************************/
	protected short messageElementType;
	protected int messageElementLength;
	
	@Override
	public IPacket setPayload(IPacket payload) {
		this.payload = payload;
		if(payload != null) {
			this.messageElementLength = payload.serialize().length;
		}
		else {
			this.messageElementLength = 0;
		}
		return this;
	}
	

	@Override
	public byte[] serialize() {
		byte[] payloadData = null;
		if(this.messageElementLength != 0) {
			payloadData = payload.serialize();
			payload.setParent(this);
		}
		byte[] data = new byte[this.messageElementLength + ELE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.putShort(this.messageElementType);
		bb.putShort((short)messageElementLength);
		if(this.messageElementLength != 0) {
			bb.put(payloadData);
		}
		return data;
	}
	
	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		switch(this.messageElementType) {
		case ACAMPMsgEle.AP_NAME: {
			APName apName = new APName();
			this.payload = apName.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.AP_DESCRIPTION: {
			APDescriptor apDescriptor = new APDescriptor();
			this.payload = apDescriptor.deserialize(data, offset, length);
			break;
		}
		default: {
			TestMsgEle testMsgEle = new TestMsgEle();
			this.payload = testMsgEle.deserialize(data, offset, length);
			break;
		}
		}
		return this;
	}
	
	public short getMessageElementType() {
		return messageElementType;
	}
	public ACAMPMsgEle setMessageElementType(short messageElementType) {
		this.messageElementType = messageElementType;
		return this;
	}
	public int getMessageElementLength() {
		return messageElementLength;
	}
	public ACAMPMsgEle setMessageElementLength(int messageElementLength) {
		this.messageElementLength = messageElementLength;
		return this;
	}





	


}
