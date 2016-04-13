package net.floodlightcontroller.packet;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class ACAMPData extends BasePacket implements IPacket {
	private LinkedList<ACAMPMsgEle> msgEleList;
	private short length;
	public ACAMPData() {
		length = 0;
		msgEleList = new LinkedList<ACAMPMsgEle>();
	}
	
	public void addMessageElement(ACAMPMsgEle msgEle) {
		msgEleList.add(msgEle);
		length += (msgEle.messageElementLength + ACAMPMsgEle.ELE_HEADER_LEN);
	}
	
	@Override
	public byte[] serialize() {
		byte[] data = null;
		if(length != 0) {
			 data = new byte[length];
			 ByteBuffer bb = ByteBuffer.wrap(data);
			 for(ACAMPMsgEle msgEle:msgEleList) {
				 bb.put(msgEle.serialize());
			 }
		}
		return data;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		// TODO Auto-generated method stub
		return null;
	}

}
