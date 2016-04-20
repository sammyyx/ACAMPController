package net.floodlightcontroller.packet;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import net.floodlightcontroller.acamp.agent.ACAMPProtocol;

public class ACAMPData extends BasePacket implements IPacket {
	private LinkedList<ACAMPMsgEle> msgEleList;
	private int length;
	public ACAMPData() {
		length = 0;
		msgEleList = new LinkedList<ACAMPMsgEle>();
	}
	
	public LinkedList<ACAMPMsgEle> getMsgEleList() {
		return msgEleList;
	}

	//向ACAMPData里面添加Message Element，ACAMPData总长度在此函数中统计
	public void addMessageElement(ACAMPMsgEle msgEle) {
		msgEleList.add(msgEle);
		length += (msgEle.messageElementLength + ACAMPProtocol.LEN_ME_HEADER);
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
			msgEle.messageElementType = ACAMPProtocol.getMsgEleType(bb.getShort());
			msgEle.messageElementLength = (int)(bb.getShort() & 0x0ffff);
			if(msgEle.messageElementLength != 0) {
				byte[] payloadData = new byte[msgEle.messageElementLength];
				bb.get(payloadData);
	 			msgEle.deserialize(payloadData, 0, payloadData.length);
			}
 			msgEleList.add(msgEle);
		}
		return this;
	}

}
