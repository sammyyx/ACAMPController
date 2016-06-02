package net.floodlightcontroller.acamp.message;

public class ConstAcampSuppressSsid {
	public static final byte NORMAL_BOARDCAST = (byte)0;
	public static final byte SUPPRESS_BY_SENDING_EMPTY_SSID = (byte)1;
	public static final byte SUPPRESS_BY_SENDING_ASCII_ZERO = (byte)2;
}
