package net.floodlightcontroller.packet;

import java.nio.ByteBuffer;

import net.floodlightcontroller.acamp.msgele.ACInetAddr;
import net.floodlightcontroller.acamp.msgele.ACMacAddr;
import net.floodlightcontroller.acamp.msgele.APBoardAddr;
import net.floodlightcontroller.acamp.msgele.APDescriptor;
import net.floodlightcontroller.acamp.msgele.APInetAddr;
import net.floodlightcontroller.acamp.msgele.APLocation;
import net.floodlightcontroller.acamp.msgele.APMacAddr;
import net.floodlightcontroller.acamp.msgele.APName;
import net.floodlightcontroller.acamp.msgele.APRadioInformation;
import net.floodlightcontroller.acamp.msgele.AddMacACLEntry;
import net.floodlightcontroller.acamp.msgele.AddStation;
import net.floodlightcontroller.acamp.msgele.Antenna;
import net.floodlightcontroller.acamp.msgele.AssignedAPID;
import net.floodlightcontroller.acamp.msgele.DeleteMacACLEntry;
import net.floodlightcontroller.acamp.msgele.DeleteStation;
import net.floodlightcontroller.acamp.msgele.MultiDomainCapability;
import net.floodlightcontroller.acamp.msgele.ReasonCode;
import net.floodlightcontroller.acamp.msgele.ResultCode;
import net.floodlightcontroller.acamp.msgele.SupportedRates;
import net.floodlightcontroller.acamp.msgele.TestMsgEle;
import net.floodlightcontroller.acamp.msgele.Timestamp;
import net.floodlightcontroller.acamp.msgele.TxPower;
import net.floodlightcontroller.acamp.msgele.WlanInformation;


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
		case ACAMPMsgEle.AC_INET_ADDR: {
			ACInetAddr msgEle = new ACInetAddr();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.AC_MAC_ADDR: {
			ACMacAddr msgEle = new ACMacAddr();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.ADD_MAC_ACL_ENTRY: {
			AddMacACLEntry msgEle = new AddMacACLEntry();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.ADD_STATION: {
			AddStation msgEle = new AddStation();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.ANTENNA: {
			Antenna msgEle = new Antenna();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.AP_BOARD_DATA: {
			APBoardAddr msgEle = new APBoardAddr();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.AP_DESCRIPTION: {
			APDescriptor apDescriptor = new APDescriptor();
			this.payload = apDescriptor.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.AP_INET_ADDR: {
			APInetAddr msgEle = new APInetAddr();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.AP_LOCATION: {
			APLocation msgEle = new APLocation();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.AP_MAC_ADDR: {
			APMacAddr msgEle = new APMacAddr();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.AP_NAME: {
			APName apName = new APName();
			this.payload = apName.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.AP_RADIO_INFO: {
			APRadioInformation msgEle = new APRadioInformation();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.ASSIGNED_APID: {
			AssignedAPID msgEle = new AssignedAPID();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.DEL_MAC_ACL_ENTRY: {
			DeleteMacACLEntry msgEle = new DeleteMacACLEntry();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.DEL_STATION: {
			DeleteStation msgEle = new DeleteStation();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.MULTI_DOMAIN_CAP: {
			MultiDomainCapability msgEle = new MultiDomainCapability();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.REASON_CODE: {
			ReasonCode msgEle = new ReasonCode();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.RESULT_CODE: {
			ResultCode msgEle = new ResultCode();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
//		case ACAMPMsgEle.STATION_EVENT: {
//			break;
//		}
		case ACAMPMsgEle.SUPPORTED_RATES: {
			SupportedRates msgEle = new SupportedRates();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.TIME_STAMP: {
			Timestamp msgEle = new Timestamp();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.TX_POWER: {
			TxPower msgEle = new TxPower();
			this.payload = msgEle.deserialize(data, offset, length);
			break;
		}
		case ACAMPMsgEle.WLAN_INFO: {
			WlanInformation msgEle = new WlanInformation();
			this.payload = msgEle.deserialize(data, offset, length);
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
