package net.floodlightcontroller.acamp.agent;

import java.util.HashMap;
import java.util.Map;

import net.floodlightcontroller.acamp.msgele.ACInetAddr;
import net.floodlightcontroller.acamp.msgele.ACMacAddr;
import net.floodlightcontroller.acamp.msgele.APBoardData;
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
import net.floodlightcontroller.acamp.msgele.StationEvent;
import net.floodlightcontroller.acamp.msgele.SupportedRates;
import net.floodlightcontroller.acamp.msgele.Timestamp;
import net.floodlightcontroller.acamp.msgele.TxPower;
import net.floodlightcontroller.acamp.msgele.WlanInformation;
import net.floodlightcontroller.packet.IPacket;

public class ACAMPProtocol {
	/**********************前导符在此处定义***********************/
	public final static int PREAMBLE 				= 0x01;
	/********************所有长度常量在此处定义********************/
	public final static int LEN_ACAMP_HEADER 		= 16;
	public final static int LEN_ME_HEADER 			= 4;
	/*****************所有消息元素类型在此处定义*******************/
	private final static short VALUE_RESULT_CODE 			= 0x0001;
	private final static short VALUE_REASON_CODE 			= 0x0002;
	private final static short VALUE_ASSIGNED_APID	 		= 0x0003;
	private final static short VALUE_AP_MAC_ADDR 			= 0x0101;
	private final static short VALUE_AP_INET_ADDR  			= 0x0102;
	private final static short VALUE_AP_NAME 	  			= 0x0103;
	private final static short VALUE_AP_DESCRIPTION 		= 0x0104;
	private final static short VALUE_AP_LOCATION			= 0x0105;
	private final static short VALUE_AP_BOARD_DATA 			= 0x0106;
	private final static short VALUE_AC_MAC_ADDR 			= 0x0201;
	private final static short VALUE_AC_INET_ADDR 			= 0x0202;
	private final static short VALUE_TIME_STAMP 			= 0x0203;
	private final static short VALUE_WLAN_INFO		  		= 0x0301;
	private final static short VALUE_AP_RADIO_INFO			= 0x0302;
	private final static short VALUE_ANTENNA				= 0x0303;
	private final static short VALUE_TX_POWER 				= 0x0304;
	private final static short VALUE_MULTI_DOMAIN_CAP    	= 0x0305;
	private final static short VALUE_SUPPORTED_RATES    	= 0x0306;
	private final static short VALUE_ADD_MAC_ACL_ENTRY  	= 0x0401;
	private final static short VALUE_DEL_MAC_ACL_ENTRY  	= 0x0402;
	private final static short VALUE_ADD_STATION 			= 0x0403;
	private final static short VALUE_DEL_STATION 			= 0x0404;
	private final static short VALUE_STATION_EVENT 			= 0x0501;
	private final static short VALUE_INVALID_MSG_ELE		= 0x0000;
	/*********************************************************/
	private final static short VALUE_REGISTER_REQUEST 			= 0x0011;
	private final static short VALUE_REGISTER_RESPONSE 			= 0x0102;
	private final static short VALUE_DISCONNET_REQUEST 			= 0x0103;
	private final static short VALUE_DISCONNET_RESPONSE 		= 0x0104;
	private final static short VALUE_CONFIGURATION_REQUEST 		= 0x0201;
	private final static short VALUE_CONFIGURATION_RESPONSE 	= 0x0202;
	private final static short VALUE_CONFIGURATION_RESET_REQ 	= 0x0203;
	private final static short VALUE_CONFIGURATION_RESET_RSP 	= 0x0204;
	private final static short VALUE_STATISTIC_STAT_RP 			= 0x0301;
	private final static short VALUE_STATISTIC_STAT_QUERY 		= 0x0302;
	private final static short VALUE_STATISTIC_STAT_REPLY 		= 0x0303;
	private final static short VALUE_STAT_REQUEST 				= 0x0401;
	private final static short VALUE_STAT_RESPONSE 				= 0x0402;
	private final static short VALUE_INVALID_MSG				= 0x0000;
	
	public enum MsgType {
		
		REGISTER_REQUEST(VALUE_REGISTER_REQUEST),
		REGISTER_RESPONSE(VALUE_REGISTER_RESPONSE),
		DISCONNET_REQUEST(VALUE_DISCONNET_REQUEST),
		DISCONNET_RESPONSE(VALUE_DISCONNET_RESPONSE),
		CONFIGURATION_REQUEST(VALUE_CONFIGURATION_REQUEST),
		CONFIGURATION_RESPONSE(VALUE_CONFIGURATION_RESPONSE),
		CONFIGURATION_RESET_REQ(VALUE_CONFIGURATION_RESET_REQ),
		CONFIGURATION_RESET_RSP(VALUE_CONFIGURATION_RESET_RSP),
		STATISTIC_STAT_RP(VALUE_STATISTIC_STAT_RP),
		STATISTIC_STAT_QUERY(VALUE_STATISTIC_STAT_QUERY),
		STATISTIC_STAT_REPLY(VALUE_STATISTIC_STAT_REPLY),
		STAT_REQUEST(VALUE_STAT_REQUEST),
		STAT_RESPONSE(VALUE_STAT_RESPONSE),
		INVALID_MSG(VALUE_INVALID_MSG);
		public short value;
		private MsgType(short value) {
			this.value = value;
		}
		public static MsgType getMsgType(short value) {
			for(MsgType msgType: MsgType.values()) {
				if(msgType.value == value) {
					return msgType;
				}
			}
			return MsgType.INVALID_MSG;
		}
	}
	
	public enum MsgEleType {
		
		RESULT_CODE(VALUE_RESULT_CODE),
		REASON_CODE(VALUE_REASON_CODE),
		ASSIGNED_APID(VALUE_ASSIGNED_APID),
		AP_MAC_ADDR(VALUE_AP_MAC_ADDR),
		AP_INET_ADDR(VALUE_AP_INET_ADDR),
		AP_NAME(VALUE_AP_NAME),
		AP_DESCRIPTION(VALUE_AP_DESCRIPTION),
		AP_LOCATION(VALUE_AP_LOCATION),
		AP_BOARD_DATA(VALUE_AP_BOARD_DATA),
		AC_MAC_ADDR(VALUE_AC_MAC_ADDR),
		AC_INET_ADDR(VALUE_AC_INET_ADDR),
		TIME_STAMP(VALUE_TIME_STAMP),
		WLAN_INFO(VALUE_WLAN_INFO),
		AP_RADIO_INFO(VALUE_AP_RADIO_INFO),
		ANTENNA(VALUE_ANTENNA),
		TX_POWER(VALUE_TX_POWER),
		MULTI_DOMAIN_CAP(VALUE_MULTI_DOMAIN_CAP),
		SUPPORTED_RATES(VALUE_SUPPORTED_RATES),
		ADD_MAC_ACL_ENTRY(VALUE_ADD_MAC_ACL_ENTRY),
		DEL_MAC_ACL_ENTRY(VALUE_DEL_MAC_ACL_ENTRY),
		ADD_STATION(VALUE_ADD_STATION),
		DEL_STATION(VALUE_DEL_STATION),
		STATION_EVENT(VALUE_STATION_EVENT),
		INVALID_MSG_ELE(VALUE_INVALID_MSG_ELE);
		public short value;
		private MsgEleType(short value) {
			this.value = value;
		}
		public static MsgEleType getMsgEleType(short value) {
			for(MsgEleType msgEleType: MsgEleType.values()) {
				if(msgEleType.value == value) {
					return msgEleType;
				}
			}
			return MsgEleType.INVALID_MSG_ELE;
		}
	}
	
	public static Map<MsgEleType, Class<? extends IPacket>> msgEleClassMap;
	
	static {
		msgEleClassMap = new HashMap<MsgEleType, Class<? extends IPacket>>();
		msgEleClassMap.put(MsgEleType.RESULT_CODE, ResultCode.class);
		msgEleClassMap.put(MsgEleType.REASON_CODE, ReasonCode.class);
		msgEleClassMap.put(MsgEleType.ASSIGNED_APID, AssignedAPID.class);
		msgEleClassMap.put(MsgEleType.AP_MAC_ADDR, APMacAddr.class);
		msgEleClassMap.put(MsgEleType.AP_INET_ADDR, APInetAddr.class);
		msgEleClassMap.put(MsgEleType.AP_NAME, APName.class);
		msgEleClassMap.put(MsgEleType.AP_DESCRIPTION, APDescriptor.class);
		msgEleClassMap.put(MsgEleType.AP_LOCATION, APLocation.class);
		msgEleClassMap.put(MsgEleType.AP_BOARD_DATA, APBoardData.class);
		msgEleClassMap.put(MsgEleType.AC_MAC_ADDR, ACMacAddr.class);
		msgEleClassMap.put(MsgEleType.AC_INET_ADDR, ACInetAddr.class);
		msgEleClassMap.put(MsgEleType.TIME_STAMP, Timestamp.class);
		msgEleClassMap.put(MsgEleType.WLAN_INFO, WlanInformation.class);
		msgEleClassMap.put(MsgEleType.AP_RADIO_INFO, APRadioInformation.class);
		msgEleClassMap.put(MsgEleType.ANTENNA, Antenna.class);
		msgEleClassMap.put(MsgEleType.TX_POWER, TxPower.class);
		msgEleClassMap.put(MsgEleType.MULTI_DOMAIN_CAP, MultiDomainCapability.class);
		msgEleClassMap.put(MsgEleType.SUPPORTED_RATES, SupportedRates.class);
		msgEleClassMap.put(MsgEleType.ADD_MAC_ACL_ENTRY, AddMacACLEntry.class);
		msgEleClassMap.put(MsgEleType.DEL_MAC_ACL_ENTRY, DeleteMacACLEntry.class);
		msgEleClassMap.put(MsgEleType.ADD_STATION, AddStation.class);
		msgEleClassMap.put(MsgEleType.DEL_STATION, DeleteStation.class);
		msgEleClassMap.put(MsgEleType.STATION_EVENT, StationEvent.class);
	}

	
}
