package net.floodlightcontroller.acamp.message;

public class ConstAcampMe {
	//Header length
	public static final short HEADER_LENGTH					= 4;
	public static final short RESULT_CODE					= 0x0001;
	public static final short REASON_CODE					= 0x0002;
	public static final short ASSIGNED_APID					= 0x0003;
	public static final short CONTROLLER_NAME				= 0x0004;
	public static final short CONTROLLER_DESCRIPTOR			= 0x0005;
	public static final short CONTROLLER_IP_ADDR			= 0x0006;
	public static final short CONTROLLER_MAC_ADDR			= 0x0007;
	public static final short AP_NAME						= 0x0008;
	public static final short AP_DESCRIPTOR					= 0x0009;
	public static final short AP_IP_ADDR					= 0x000a;
	public static final short AP_MAC_ADDR					= 0x000b;
	public static final short SSID							= 0x0101;
	public static final short CHANNEL						= 0x0102;
	public static final short HARDWARE_MODE					= 0x0103;
	public static final short SUPPRESS_SSID					= 0x0104;
	public static final short SECURITY_SETTING				= 0x0105;
	public static final short WPA_VERSION					= 0x0201;
	public static final short WPA_PASSPHRASE				= 0x0202;
	public static final short WPA_KEY_MANAGEMENT			= 0x0203;
	public static final short WPA_PAIRWISE					= 0x0204;
	public static final short WPA_GROUP_REKEY				= 0x0205;
	public static final short WEP_DEFAULT_KEY				= 0x0301;
	public static final short WEP_KEY						= 0x0302;
	public static final short MAC_ACL_MODE					= 0x0401;
	public static final short MAC_ACCEPT_LIST				= 0x0402;
	public static final short MAC_DENY_LIST					= 0x0403;
	public static final short COUNTRY_CODE					= 0x0501;
	public static final short ENABLE_DOT11D					= 0x0502;
	public static final short ADD_STATION					= 0x0601;
	public static final short DELETE_STATION				= 0x0602;
	public static final short STATE_DESCRIPTOR				= 0x0701;
	public static final short TIMERS						= 0x0702;
	public static final short FLOW_STATISTIC				= 0x0801;
}
