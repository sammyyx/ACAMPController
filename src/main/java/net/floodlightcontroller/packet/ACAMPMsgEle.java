package net.floodlightcontroller.packet;

import java.nio.ByteBuffer;

import net.floodlightcontroller.acamp.agent.ACAMPProtocol;


public class ACAMPMsgEle extends BasePacket implements IPacket{
	protected ACAMPProtocol.MsgEleType messageElementType;
	protected int messageElementLength;	

	@Override
	public byte[] serialize() {
		byte[] payloadData = null;
		if(this.messageElementLength != 0) {
			payloadData = this.payload.serialize();
			this.payload.setParent(this);
		}
		byte[] data = new byte[this.messageElementLength + ACAMPProtocol.LEN_ME_HEADER];
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.putShort(this.messageElementType.value);
		bb.putShort((short)messageElementLength);
		if(this.messageElementLength != 0) {
			bb.put(payloadData);
		}
		return data;
	}
	
	@Override
	public ACAMPMsgEle deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		ByteBuffer bb = ByteBuffer.wrap(data);
		Class<? extends IPacket> clazz = ACAMPProtocol.msgEleClassMap.get(this.messageElementType);
		try {
			this.payload = clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(this.messageElementLength != 0) {
			byte[] payloadData = new byte[this.messageElementLength];
			bb.get(payloadData);
			this.payload = this.payload.deserialize(payloadData, 0, payloadData.length);
		}
		return this;
	}
	
	public ACAMPProtocol.MsgEleType getMessageElementType() {
		return messageElementType;
	}
	
	
	public ACAMPMsgEle build() {
		if(payload == null) return null;
		this.messageElementLength = this.payload.serialize().length;
		return this;
	}
	
	/**
	 * 用于设置指定类型的MessageElement相关字段的值
	 * @param messageElementType
	 * @return
	 */
	public IPacket createBuilder(ACAMPProtocol.MsgEleType messageElementType) {
		this.messageElementType = messageElementType;
		Class<? extends IPacket> clazz = ACAMPProtocol.msgEleClassMap.get(this.messageElementType);
		try {
			this.payload = clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.payload;
	}
	
	public int getMessageElementLength() {
		return messageElementLength;
	}





	


}
