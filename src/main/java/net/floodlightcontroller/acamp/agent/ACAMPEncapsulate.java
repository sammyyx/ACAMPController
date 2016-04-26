package net.floodlightcontroller.acamp.agent;

import net.floodlightcontroller.packet.ACAMP;
import net.floodlightcontroller.packet.ACAMPData;
import net.floodlightcontroller.packet.ACAMPMsgEle;

public class ACAMPEncapsulate {
	private short version;
	private int apid;
	private byte type;
	private long sequenceNum;
	private ACAMPProtocol.MsgType messageType;
	public byte[] createACAMPMessage(ACAMPMsgEle ...msgEle) {
		if(messageType == null) {
			System.out.println("Please initiate the Factory member");
			return null;
		}
		ACAMP acamp = new ACAMP();
		acamp.setVersion(version)
			 .setAPID(apid)
			 .setType(type)
			 .setSequenceNumber(sequenceNum)
			 .setMessageType(messageType);
		if(msgEle != null) {
			ACAMPData acampData = new ACAMPData();
			for(ACAMPMsgEle me: msgEle) {
				acampData.addMessageElement(me);
			}
			acamp.setPayload(acampData);
			return acamp.serialize();
		}
		acamp.setPayload(null);
		return acamp.serialize();
	}
	public short getVersion() {
		return version;
	}
	public ACAMPEncapsulate setVersion(short version) {
		this.version = version;
		return this;
	}
	public int getApid() {
		return apid;
	}
	public ACAMPEncapsulate setApid(int apid) {
		this.apid = apid;
		return this;
	}
	public byte getType() {
		return type;
	}
	public ACAMPEncapsulate setType(byte type) {
		this.type = type;
		return this;
	}
	public long getSequenceNum() {
		return sequenceNum;
	}
	public ACAMPEncapsulate setSequenceNum(long sequenceNum) {
		this.sequenceNum = sequenceNum;
		return this;
	}
	public ACAMPProtocol.MsgType getMessageType() {
		return messageType;
	}
	public ACAMPEncapsulate setMessageType(ACAMPProtocol.MsgType messageType) {
		this.messageType = messageType;
		return this;
	}
	
}
