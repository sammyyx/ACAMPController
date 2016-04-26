package net.floodlightcontroller.packet;

import java.nio.ByteBuffer;

import net.floodlightcontroller.acamp.agent.ACAMPProtocol;

public class ACAMP extends BasePacket implements IPacket {

	private short version;							//版本号
	private byte type;								//消息类型
	private int apid;								//APID
	private long sequenceNumber;					//序	列号
	private ACAMPProtocol.MsgType  messageType;		//消息类型
	private int messageLength;						//不需要显性设置，解序列化自动填充

	@Override
	public byte[] serialize() {
		byte[] payloadData = null;	//ACAMP消息的载荷为ACAMPData
		if(this.payload != null) {
			this.payload.setParent(this);
			payloadData = this.payload.serialize();
		}
		/* 消息长度等于头部长度加上载荷长度 */
		this.messageLength = (short) (ACAMPProtocol.LEN_ACAMP_HEADER + ((payloadData == null) ? 
				0 : payloadData.length));
		byte[] data = new byte[this.messageLength];
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.putInt(ACAMPProtocol.PREAMBLE);
		bb.put((byte)this.version);
		bb.put((byte)this.type);
		bb.putShort((short)this.apid);
		bb.putInt((int)this.sequenceNumber);
		bb.putShort(this.messageType.value);
		bb.putShort((short)this.messageLength);
		if(payloadData != null) {
			bb.put(payloadData);
		}
		return data;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.getInt();
		this.version = (short)(bb.get() & 0x0ff);
		this.type = bb.get();
		this.apid = (int)(bb.getShort() & 0x0ffff);
		this.sequenceNumber = (long)(bb.getInt() & 0x0ffffffff);
		this.messageType = ACAMPProtocol.MsgType.getMsgType(bb.get());
		this.messageLength = (int)(bb.getShort() & 0x0ffff);
		if(bb.hasRemaining()) {
			ACAMPData acampData = new ACAMPData();
			int payloadLength = length - ACAMPProtocol.LEN_ACAMP_HEADER;
			byte[] payloadData = new byte[payloadLength];
			bb.get(payloadData);
			this.payload = acampData.deserialize(payloadData, 0, payloadLength);
		}
		return this;
	}
	
	public short getVersion() {
		return version;
	}
	public ACAMP setVersion(short version) {
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
	public int getAPID() {
		return apid;
	}
	public ACAMP setAPID(int aPID) {
		apid = aPID;
		return this;
	}
	public long getSequenceNumber() {
		return sequenceNumber;
	}
	public ACAMP setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
		return this;
	}
	public ACAMPProtocol.MsgType getMessageType() {
		return messageType;
	}
	public ACAMP setMessageType(ACAMPProtocol.MsgType messageType) {
		this.messageType = messageType;
		return this;
	}
	public int getMessageLength() {
		return messageLength;
	}
	public ACAMP setMessageLength(int messageLength) {
		this.messageLength = messageLength;
		return this;
	}

}
