package net.floodlightcontroller.acamp.message;

public class ConstAcampWpaSetting {
	public static final byte VERSION_WPA_ONE 	= (byte)1;
	public static final byte VERSION_WPA_TWO 	= (byte)2;
	public static final byte KM_WPA_PSK 	 	= (byte)0;
	public static final byte KM_WPA_EAP		 	= (byte)1;
	public static final byte KM_WPA_PSK_SHA256  = (byte)2;
	public static final byte KM_WPA_EAP_SHA256  = (byte)3;
	public static final byte PAIRWISE_TKIP		= (byte)0;
	public static final byte PAIRWISE_CCMP		= (byte)1;
}
