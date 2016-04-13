package net.floodlightcontroller.packet;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class ACAMP extends BasePacket implements IPacket {

	/*****************所有消息常量在此处定义*********************/
	private static final int PREAMBLE = 0x01;
	private static final int HEADER_LENGTH = 16;
	private static final int MSG_ELE_LENGTH = 8;
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
	private byte version;
	private byte type;
	private short apid;
	private int sequenceNumber;
	private short messageType;
	private short messageLength;
	private int preamble;

	@Override
	public byte[] serialize() {
		byte[] payloadData = null;
		if(payload != null) {
			payload.setParent(this);
			payloadData = payload.serialize();
		}
		this.messageLength = (short) (HEADER_LENGTH + ((payloadData == null) ? 
				0 : payloadData.length));
		byte[] data = new byte[this.messageLength];
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.putInt(PREAMBLE);
		bb.put(version);
		bb.put(type);
		bb.putShort(apid);
		bb.putInt(sequenceNumber);
		bb.putShort(messageType);
		bb.putShort(messageLength);
		bb.put(payloadData);
		return data;
		
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		ByteBuffer bb = ByteBuffer.wrap(data);
		this.preamble = bb.getInt();
		this.version = bb.get();
		this.type = bb.get();
		this.apid = bb.getShort();
		this.sequenceNumber = bb.getInt();
		this.messageType = bb.getShort();
		this.messageLength = bb.getShort();
		return null;
	}
	
	public byte getVersion() {
		return version;
	}
	public ACAMP setVersion(byte version) {
		this.version = version;
		return this;
	}
	public byte getType() {
		return type;
	}
	public ACAMP setType(byte type) {
		this.type = type;
		return this;
	}
	public short getAPID() {
		return apid;
	}
	public ACAMP setAPID(short aPID) {
		apid = aPID;
		return this;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public ACAMP setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
		return this;
	}
	public short getMessageType() {
		return messageType;
	}
	public ACAMP setMessageType(short messageType) {
		this.messageType = messageType;
		return this;
	}
	public short getMessageLength() {
		return messageLength;
	}
	public ACAMP setMessageLength(short messageLength) {
		this.messageLength = messageLength;
		return this;
	}
	public static int getPreamble() {
		return PREAMBLE;
	}

	public void setPreamble(int preamble) {
		this.preamble = preamble;
	}


}
