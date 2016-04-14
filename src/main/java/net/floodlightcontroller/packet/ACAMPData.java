package net.floodlightcontroller.packet;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class ACAMPData extends BasePacket implements IPacket {
	private LinkedList<ACAMPMsgEle> msgEleList;
	private int length;
	public ACAMPData() {
		length = 0;
		msgEleList = new LinkedList<ACAMPMsgEle>();
	}
	
	//向ACAMPData里面添加Message Element，ACAMPData总长度在此函数中统计
	public void addMessageElement(ACAMPMsgEle msgEle) {
		msgEleList.add(msgEle);
		length += (msgEle.messageElementLength + ACAMPMsgEle.ELE_HEADER_LEN);
	}
	
	@Override
	public byte[] serialize() {
		byte[] data = null;
		//如果含有Message Element，此申请ACAMPData大小的空间并
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
		ByteBuffer bb = ByteBuffer.wrap(data);
		while(bb.hasRemaining()) {
			ACAMPMsgEle msgEle = new ACAMPMsgEle();
			msgEle.setMessageElementType(bb.getShort());
			msgEle.setMessageElementLength((int)(bb.getShort() & 0x0ffff));
			byte[] payloadData = new byte[msgEle.getMessageElementLength()];
			bb.get(payloadData, 0, msgEle.getMessageElementLength());
 			msgEle.deserialize(payloadData, 0, msgEle.getMessageElementLength());
 			msgEleList.add(msgEle);
		}
		return this;
	}

}
