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
	private final static byte VALUE_REGISTER_REQUEST 			= 0x11;
	private final static byte VALUE_REGISTER_RESPONSE 			= 0x12;
	private final static byte VALUE_DISCONNET_REQUEST 			= 0x13;
	private final static byte VALUE_DISCONNET_RESPONSE 			= 0x14;
	private final static byte VALUE_CONFIGURATION_REQUEST 		= 0x21;
	private final static byte VALUE_CONFIGURATION_RESPONSE 		= 0x22;
	private final static byte VALUE_CONFIGURATION_RESET_REQ 	= 0x23;
	private final static byte VALUE_CONFIGURATION_RESET_RSP 	= 0x24;
	private final static byte VALUE_STATISTIC_STAT_RP 			= 0x31;
	private final static byte VALUE_STATISTIC_STAT_QUERY 		= 0x32;
	private final static byte VALUE_STATISTIC_STAT_REPLY 		= 0x33;
	private final static byte VALUE_STAT_REQUEST 				= 0x41;
	private final static byte VALUE_STAT_RESPONSE 				= 0x42;
	private final static byte VALUE_INVALID_MSG					= 0x00;
	
	public static enum MsgType {
		
		REGISTER_REQUEST,
		REGISTER_RESPONSE,
		DISCONNET_REQUEST,
		DISCONNET_RESPONSE,
		CONFIGURATION_REQUEST,
		CONFIGURATION_RESPONSE,
		CONFIGURATION_RESET_REQ,
		CONFIGURATION_RESET_RSP,
		STATISTIC_STAT_RP,
		STATISTIC_STAT_QUERY,
		STATISTIC_STAT_REPLY,
		STAT_REQUEST,
		STAT_RESPONSE,
		INVALID_MSG;
		
	}
	
	public static enum MsgEleType {
		
		RESULT_CODE,
		REASON_CODE,
		ASSIGNED_APID,
		AP_MAC_ADDR,
		AP_INET_ADDR,
		AP_NAME,
		AP_DESCRIPTION,
		AP_LOCATION,
		AP_BOARD_DATA,
		AC_MAC_ADDR,
		AC_INET_ADDR,
		TIME_STAMP,
		WLAN_INFO,
		AP_RADIO_INFO,
		ANTENNA,
		TX_POWER,
		MULTI_DOMAIN_CAP,
		SUPPORTED_RATES,
		ADD_MAC_ACL_ENTRY,
		DEL_MAC_ACL_ENTRY,
		ADD_STATION,
		DEL_STATION,
		STATION_EVENT,
		INVALID_MSG_ELE;
		
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
	
	public static ACAMPProtocol.MsgType getMsgType(byte msgValue) {
		switch(msgValue) {
		case VALUE_REGISTER_REQUEST:
			return ACAMPProtocol.MsgType.REGISTER_REQUEST;
		case VALUE_REGISTER_RESPONSE:
			return ACAMPProtocol.MsgType.CONFIGURATION_RESPONSE;
		case VALUE_DISCONNET_REQUEST:
			return ACAMPProtocol.MsgType.DISCONNET_REQUEST;
		case VALUE_DISCONNET_RESPONSE:
			return ACAMPProtocol.MsgType.DISCONNET_RESPONSE;
		case VALUE_CONFIGURATION_REQUEST:
			return ACAMPProtocol.MsgType.CONFIGURATION_REQUEST;
		case VALUE_CONFIGURATION_RESPONSE:
			return ACAMPProtocol.MsgType.CONFIGURATION_RESPONSE;
		case VALUE_CONFIGURATION_RESET_REQ:
			return ACAMPProtocol.MsgType.CONFIGURATION_RESET_REQ;
		case VALUE_CONFIGURATION_RESET_RSP:
			return ACAMPProtocol.MsgType.CONFIGURATION_RESET_RSP;
		case VALUE_STATISTIC_STAT_RP:
			return ACAMPProtocol.MsgType.STATISTIC_STAT_RP;
		case VALUE_STATISTIC_STAT_QUERY:
			return ACAMPProtocol.MsgType.STATISTIC_STAT_QUERY;
		case VALUE_STATISTIC_STAT_REPLY:
			return ACAMPProtocol.MsgType.STATISTIC_STAT_REPLY;
		case VALUE_STAT_REQUEST:
			return ACAMPProtocol.MsgType.STAT_REQUEST;
		case VALUE_STAT_RESPONSE:
			return ACAMPProtocol.MsgType.STAT_RESPONSE;
		default:
			return ACAMPProtocol.MsgType.INVALID_MSG;
		}
	}

	public static byte getMsgValue(MsgType msgType) {
		switch(msgType) {
		case REGISTER_REQUEST:
			return VALUE_REGISTER_REQUEST;
		case REGISTER_RESPONSE:
			return VALUE_REGISTER_RESPONSE;
		case DISCONNET_REQUEST:
			return VALUE_DISCONNET_REQUEST;
		case DISCONNET_RESPONSE:
			return VALUE_DISCONNET_RESPONSE;
		case CONFIGURATION_REQUEST:
			return VALUE_CONFIGURATION_REQUEST;
		case CONFIGURATION_RESPONSE:
			return VALUE_CONFIGURATION_RESPONSE;
		case CONFIGURATION_RESET_REQ:
			return VALUE_CONFIGURATION_RESET_REQ;
		case CONFIGURATION_RESET_RSP:
			return VALUE_CONFIGURATION_RESET_RSP;
		case STATISTIC_STAT_RP:
			return VALUE_STATISTIC_STAT_RP;
		case STATISTIC_STAT_QUERY:
			return VALUE_STATISTIC_STAT_QUERY;
		case STATISTIC_STAT_REPLY:
			return VALUE_STATISTIC_STAT_REPLY;
		case STAT_REQUEST:
			return VALUE_STAT_REQUEST;
		case STAT_RESPONSE:
			return VALUE_STAT_RESPONSE;
		default:
			return VALUE_INVALID_MSG;
		}
	}
	
	public static ACAMPProtocol.MsgEleType getMsgEleType(short value) {
		switch(value) {
		case VALUE_RESULT_CODE:
			return ACAMPProtocol.MsgEleType.RESULT_CODE;
		case VALUE_REASON_CODE:
			return ACAMPProtocol.MsgEleType.REASON_CODE;
		case VALUE_ASSIGNED_APID:
			return ACAMPProtocol.MsgEleType.ASSIGNED_APID;
		case VALUE_AP_MAC_ADDR:
			return ACAMPProtocol.MsgEleType.AP_MAC_ADDR;
		case VALUE_AP_INET_ADDR:
			return ACAMPProtocol.MsgEleType.AP_INET_ADDR;
		case VALUE_AP_NAME:
			return ACAMPProtocol.MsgEleType.AP_NAME;
		case VALUE_AP_DESCRIPTION:
			return ACAMPProtocol.MsgEleType.AP_DESCRIPTION;
		case VALUE_AP_LOCATION:
			return ACAMPProtocol.MsgEleType.AP_LOCATION;
		case VALUE_AP_BOARD_DATA:
			return ACAMPProtocol.MsgEleType.AP_BOARD_DATA;
		case VALUE_AC_MAC_ADDR:
			return ACAMPProtocol.MsgEleType.AC_MAC_ADDR;
		case VALUE_AC_INET_ADDR:
			return ACAMPProtocol.MsgEleType.AC_INET_ADDR;
		case VALUE_TIME_STAMP:
			return ACAMPProtocol.MsgEleType.TIME_STAMP;
		case VALUE_WLAN_INFO:
			return ACAMPProtocol.MsgEleType.WLAN_INFO;
		case VALUE_AP_RADIO_INFO:
			return ACAMPProtocol.MsgEleType.AP_RADIO_INFO;
		case VALUE_ANTENNA:
			return ACAMPProtocol.MsgEleType.ANTENNA;
		case VALUE_TX_POWER:
			return ACAMPProtocol.MsgEleType.TX_POWER;
		case VALUE_SUPPORTED_RATES:
			return ACAMPProtocol.MsgEleType.SUPPORTED_RATES;
		case VALUE_ADD_MAC_ACL_ENTRY:
			return ACAMPProtocol.MsgEleType.ADD_MAC_ACL_ENTRY;
		case VALUE_DEL_MAC_ACL_ENTRY:
			return ACAMPProtocol.MsgEleType.DEL_MAC_ACL_ENTRY;
		case VALUE_ADD_STATION:
			return ACAMPProtocol.MsgEleType.ADD_STATION;
		case VALUE_STATION_EVENT:
			return ACAMPProtocol.MsgEleType.DEL_STATION;
		case VALUE_DEL_STATION:
			return ACAMPProtocol.MsgEleType.STATION_EVENT;
		default:
			return ACAMPProtocol.MsgEleType.INVALID_MSG_ELE;
		}
	}
	
	
	public static short getMsgEleValue(MsgEleType msgEleType) {
		switch(msgEleType) {
		case RESULT_CODE:
			return VALUE_RESULT_CODE;
		case REASON_CODE:
			return VALUE_REASON_CODE;
		case ASSIGNED_APID:
			return VALUE_ASSIGNED_APID;
		case AP_MAC_ADDR:
			return VALUE_AP_MAC_ADDR;
		case AP_INET_ADDR:
			return VALUE_AP_INET_ADDR;
		case AP_NAME:
			return VALUE_AP_NAME;
		case AP_DESCRIPTION:
			return VALUE_AP_DESCRIPTION;
		case AP_LOCATION:
			return VALUE_AP_LOCATION;
		case AP_BOARD_DATA:
			return VALUE_AP_BOARD_DATA;
		case AC_MAC_ADDR:
			return VALUE_AC_MAC_ADDR;
		case AC_INET_ADDR:
			return VALUE_AC_INET_ADDR;
		case TIME_STAMP:
			return VALUE_TIME_STAMP;
		case WLAN_INFO:
			return VALUE_WLAN_INFO;
		case AP_RADIO_INFO:
			return VALUE_AP_RADIO_INFO;
		case ANTENNA:
			return VALUE_ANTENNA;
		case TX_POWER:
			return VALUE_TX_POWER;
		case MULTI_DOMAIN_CAP:
			return VALUE_MULTI_DOMAIN_CAP;
		case SUPPORTED_RATES:
			return VALUE_SUPPORTED_RATES;
		case ADD_MAC_ACL_ENTRY:
			return VALUE_ADD_MAC_ACL_ENTRY;
		case DEL_MAC_ACL_ENTRY:
			return VALUE_DEL_MAC_ACL_ENTRY;
		case ADD_STATION:
			return VALUE_ADD_STATION;
		case DEL_STATION:
			return VALUE_DEL_STATION;
		case STATION_EVENT:
			return VALUE_STATION_EVENT;
		default:
			return VALUE_INVALID_MSG_ELE;
		}
	}
	
}
