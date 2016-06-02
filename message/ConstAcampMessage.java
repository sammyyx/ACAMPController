package net.floodlightcontroller.acamp.message;

public class ConstAcampMessage {
	//Protocol Version
	public static final byte ACAMP_01						= 0x01;
	//Protocol Type
	public static final byte ACAMP_CONTROL_TYPE				= 0;
	//Length
	public static final short MESSAGE_HEADER_LEN			= 12;
	//Message Type Definition
	public static final short KEEP_ALIVE_REQUEST 			= 0x01;
	public static final short KEEP_ALIVE_RESPONSE 			= 0x02;
	public static final short DISCOVER_REQUEST 				= 0x03;
	public static final short DISCOVER_RESPONSE 			= 0x04;
	public static final short REGISTER_REQUEST 				= 0x11;
	public static final short REGISTER_RESPONSE		 		= 0x12;
	public static final short UNREGISTER_REQUEST 			= 0x13;
	public static final short UNREGISTER_RESPONSE 			= 0x14;
	public static final short CONFIGURATION_REQUEST 		= 0x21;
	public static final short CONFIGURATION_REPORT 			= 0x22;
	public static final short CONFIGURATION_DELIVER 		= 0x23;
	public static final short STATION_CONFIGURATION_DELIVER = 0x24;
	public static final short STATISTIC_REQUEST 			= 0x31;
	public static final short STATISTIC_REPORT 				= 0x32;
	public static final short STATE_REQUEST 				= 0x41;
	public static final short STATE_REPORT 					= 0x42;
}
