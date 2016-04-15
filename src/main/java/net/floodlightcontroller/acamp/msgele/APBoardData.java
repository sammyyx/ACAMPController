package net.floodlightcontroller.acamp.msgele;

import net.floodlightcontroller.packet.BasePacket;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.PacketParsingException;

public class APBoardData extends BasePacket implements IPacket {

	private String boardDiscription;
	@Override
	public byte[] serialize() {
		byte[] boardDataBytes = null;
		if(boardDiscription != null) {
			boardDataBytes = boardDiscription.getBytes();
		}
		return boardDataBytes;
	}

	@Override
	public IPacket deserialize(byte[] data, int offset, int length)
			throws PacketParsingException {
		if(data.length != 0) {
			this.boardDiscription = new String(data);
		}
		return this;
	}

	public String getBoardDiscription() {
		return boardDiscription;
	}

	public void setBoardDiscription(String boardDiscription) {
		this.boardDiscription = boardDiscription;
	}

}
