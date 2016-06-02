package net.floodlightcontroller.acamp.message;

import java.nio.ByteBuffer;

import net.floodlightcontroller.acamp.process.ControllerAgent;

public class AcampMessage {
	private byte protocolVersion;
	private byte protocolType;
	private int APID;
	private long sequenceNumber;
	private short messageType;
	private int messageLength;
	private byte[] data;
	public AcampMessage() {
		this.protocolVersion = -1;
		this.protocolType = -1;
		this.APID = -1;
		this.sequenceNumber = -1;
		this.messageType = -1;
		this.messageLength = ConstAcampMessage.MESSAGE_HEADER_LEN;
		this.data = null;
	}
	public boolean isInitiate() {
		if(this.protocolType == -1 
				|| this.protocolVersion == -1 
				|| this.APID == -1 
				|| this.sequenceNumber == -1
				|| this.messageType == -1
				) {
			return false;
		}
		return true;
	}

	public int getAPID() {
		return APID;
	}
	public void setAPID(int APID) {
		this.APID = APID;
	}
	public byte getProtocolVersion() {
		return protocolVersion;
	}
	public void setProtocolVersion(byte protocolVersion) {
		this.protocolVersion = protocolVersion;
	}
	public byte getProtocolType() {
		return protocolType;
	}
	public void setProtocolType(byte protocolType) {
		this.protocolType = protocolType;
	}
	public long getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public short getMessageType() {
		return messageType;
	}
	public void setMessageType(short messageType) {
		this.messageType = messageType;
	}
	public int getMessageLength() {
		return messageLength;
	}
	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public void addResultCode(short resultCode) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 2;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.RESULT_CODE);
		bb.putShort((short)mesLength);
		bb.putShort(resultCode);
		this.data = Mebytes;
	}
	public void addReasonCode(short reasonCode) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 2;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.REASON_CODE);
		bb.putShort((short)mesLength);
		bb.putShort(reasonCode);
		this.data = Mebytes;
	}
	public void addAssignedApid(short Apid) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 2;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.ASSIGNED_APID);
		bb.putShort((short)mesLength);
		bb.putShort(Apid);
		this.data = Mebytes;
	}
	public void addControllerName(String controllerName) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		if(controllerName.length() > 32) {
			System.out.println("Error! Invalid length for this element");
			return;
		}
		byte[] Mebytes;
		int mesLength = controllerName.length();
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.CONTROLLER_NAME);
		bb.putShort((short)mesLength);
		bb.put(controllerName.getBytes());
		this.data = Mebytes;
	}
	public void addControllerDescriptor(String controllerDescriptor) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		if(controllerDescriptor.length() > 128) {
			System.out.println("Error! Invalid length for this element");
			return;
		}
		byte[] Mebytes;
		int mesLength = controllerDescriptor.length();
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.CONTROLLER_DESCRIPTOR);
		bb.putShort((short)mesLength);
		bb.put(controllerDescriptor.getBytes());
		this.data = Mebytes;
	}
	public void addControllerIpAddr() {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		byte[] ipv4Addr = ControllerAgent.controllerIpAddr.getBytes();
		int mesLength = ipv4Addr.length;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.CONTROLLER_IP_ADDR);
		bb.putShort((short)mesLength);
		bb.put(ipv4Addr);
		this.data = Mebytes;
	}
	public void addControllerMacAddr() {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		byte[] macAddr = ControllerAgent.controllerMacAddr.getBytes();
		int mesLength = macAddr.length;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.CONTROLLER_MAC_ADDR);
		bb.putShort((short)mesLength);
		bb.put(macAddr);
		this.data = Mebytes;
	}
	public void addSsid(String ssid) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		if(ssid.length() > 32) {
			System.out.println("Error! Invalid length for this element");
			return;
		}
		byte[] Mebytes;
		int mesLength = ssid.length();
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.SSID);
		bb.putShort((short)mesLength);
		bb.put(ssid.getBytes());
		this.data = Mebytes;
	}
	public void addChannel(byte channel) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 1;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.CHANNEL);
		bb.putShort((short)mesLength);
		bb.put(channel);
		this.data = Mebytes;
	}
	public void addHardwareMode(byte hwMode) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 1;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.HARDWARE_MODE);
		bb.putShort((short)mesLength);
		bb.put(hwMode);
		this.data = Mebytes;
	}
	public void addSuppressSsid(byte suppressSsid) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 1;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.SUPPRESS_SSID);
		bb.putShort((short)mesLength);
		bb.put(suppressSsid);
		this.data = Mebytes;
	}
	public void addSecuritySetting(byte securitySetting) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 1;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.SECURITY_SETTING);
		bb.putShort((short)mesLength);
		bb.put(securitySetting);
		this.data = Mebytes;
	}
	public void addWpaVersion(byte wpaVersion) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 1;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.WPA_VERSION);
		bb.putShort((short)mesLength);
		bb.put(wpaVersion);
		this.data = Mebytes;
	}
	public void addWpaPassphrase(String wpaPassphrase) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		if(wpaPassphrase.length() < 8 || wpaPassphrase.length() > 63) {
			System.out.println("Error! Invalid length for this element");
			return;
		}
		byte[] Mebytes;
		int mesLength = wpaPassphrase.length();
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.WPA_PASSPHRASE);
		bb.putShort((short)mesLength);
		bb.put(wpaPassphrase.getBytes());
		this.data = Mebytes;
	}
	public void addWpaKeyManagement(byte wpaKeyManagement) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 1;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.WPA_KEY_MANAGEMENT);
		bb.putShort((short)mesLength);
		bb.put(wpaKeyManagement);
		this.data = Mebytes;
	}
	public void addWpaPairwise(byte wpaPairwise) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 1;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.WPA_PAIRWISE);
		bb.putShort((short)mesLength);
		bb.put(wpaPairwise);
		this.data = Mebytes;
	}
	public void addWpaGroupRekey(int rekeyInterval) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 4;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.WPA_GROUP_REKEY);
		bb.putShort((short)mesLength);
		bb.putInt(rekeyInterval);
		this.data = Mebytes;
	}
	public void addWepDefaultKey(byte defaultKey) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		if(defaultKey < 0 || defaultKey > 3) {
			System.out.println("Error! Invalid length for this element");
			return;
		}
		byte[] Mebytes;
		int mesLength = 1;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.WEP_DEFAULT_KEY);
		bb.putShort((short)mesLength);
		bb.put(defaultKey);
		this.data = Mebytes;
	}
	public void addWepKey(String...keys) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		if(keys.length > 4) {
			System.out.println("Error! Invalid length for this element");
			return;
		}
		byte[] Mebytes;
		int mesLength = ConstAcampMe.HEADER_LENGTH;
		int count = 0;
		for(String key:keys) {
			count++;
			if(key == null) continue;
			if(key.length() == 5 || key.length() == 13 || key.length() == 16) {
				mesLength += key.length();
				mesLength += 2;
			}
			else {
				System.out.println("Number" + count + " key's length is not permitted");
				return;
			}
		}
		count = 0;
		this.messageLength += mesLength;
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.WEP_KEY);
		bb.putShort((short)mesLength);
		for(String key:keys) {
			count++;
			if(key == null) continue;
			switch(key.length()) {
			case 5: {
				bb.put((byte)count);
				bb.put((byte)5);
				bb.put(key.getBytes());
				break;
			}
			case 13: {
				bb.put((byte)count);
				bb.put((byte)13);
				bb.put(key.getBytes());
				break;
			}
			case 16: {
				bb.put((byte)count);
				bb.put((byte)16);
				bb.put(key.getBytes());
				break;
			}
			default: return;
			}
		}
		this.data = Mebytes;
	}
	public void addMacAclMode(byte macAclMode) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 1;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.MAC_ACL_MODE);
		bb.putShort((short)mesLength);
		bb.put(macAclMode);
		this.data = Mebytes;
	}
	public void addMacDenyList(byte[]...macAddresses) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		if(macAddresses == null) {
			System.out.println("Empty Mac Address");
			return;
		}
		int count = 0;
		byte[] Mebytes;
		int mesLength = ConstAcampMe.HEADER_LENGTH;
		for(byte[] macAddress:macAddresses) {
			count++;
			if(macAddress.length != 6) {
				System.out.println("Invalid Argument in " + count +" parameter");
				return;
			}
			mesLength += macAddress.length;
		}
		this.messageLength += mesLength;
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.MAC_DENY_LIST);
		bb.putShort((short)mesLength);
		for(byte[] macAddress:macAddresses) {
			bb.put(macAddress);
		}
		this.data = Mebytes;
	}
	public void addMacAcceptList(byte[]...macAddresses) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		if(macAddresses == null) {
			System.out.println("Empty Mac Address");
			return;
		}
		int count = 0;
		byte[] Mebytes;
		int mesLength = ConstAcampMe.HEADER_LENGTH;
		for(byte[] macAddress:macAddresses) {
			count++;
			if(macAddress.length != 6) {
				System.out.println("Invalid Argument in " + count +" parameter");
				return;
			}
			mesLength += macAddress.length;
		}
		this.messageLength += mesLength;
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.MAC_ACCEPT_LIST);
		bb.putShort((short)mesLength);
		for(byte[] macAddress:macAddresses) {
			bb.put(macAddress);
		}
		this.data = Mebytes;
	}
	public void addStation(byte[] macAddress) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		if(macAddress == null || macAddress.length != 6) {
			System.out.println("Invalid Mac Address");
			return;
		}
		byte[] Mebytes;
		int mesLength = 6;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.ADD_STATION);
		bb.putShort((short)mesLength);
		bb.put(macAddress);
		this.data = Mebytes;
	}
	public void delStation(byte[] macAddress) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		if(macAddress == null || macAddress.length != 6) {
			System.out.println("Invalid Mac Address");
			return;
		}
		byte[] Mebytes;
		int mesLength = 6;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.DELETE_STATION);
		bb.putShort((short)mesLength);
		bb.put(macAddress);
		this.data = Mebytes;
	}
	public void addFlowStatistic() {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 0;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.FLOW_STATISTIC);
		bb.putShort((short)mesLength);
		this.data = Mebytes;
	}
	public void addTimers(short keepAliveInterval, 
			short discoverInterval,
			short statisticTimers,
			short stateTimer,
			short idleTimeout,
			short discoverTimeout,
			short registerTimeout,
			short reportTimeout) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		int mesLength = 8;
		this.messageLength += (mesLength + ConstAcampMe.HEADER_LENGTH);
		Mebytes = new byte[this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.TIMERS);
		bb.putShort((short)mesLength);
		bb.put((byte)keepAliveInterval);
		bb.put((byte)discoverTimeout);
		bb.put((byte)stateTimer);
		bb.put((byte)stateTimer);
		bb.put((byte)idleTimeout);
		bb.put((byte)discoverTimeout);
		bb.put((byte)registerTimeout);
		bb.put((byte)reportTimeout);
		this.data = Mebytes;
	}
	public byte[] serialize() {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return null;
		}
		byte[] byteBuffer = new byte[messageLength];
		ByteBuffer bb = ByteBuffer.wrap(byteBuffer);
		bb.put(this.protocolVersion);
		bb.put(this.protocolType);
		bb.putShort((short)this.APID);
		bb.putInt((int)this.sequenceNumber);
		bb.putShort(this.messageType);
		bb.putShort((short)this.messageLength);
		if(this.data != null) {
			bb.put(this.data);
		}
		return byteBuffer;
	}
	public AcampMessage deserialize(byte[] rawBytes) {
		ByteBuffer bb = ByteBuffer.wrap(rawBytes);
		this.protocolVersion = bb.get();
		this.protocolType = bb.get();
		this.APID = bb.getShort() & 0x0ffff;
		this.sequenceNumber = bb.getInt() & 0x0ffffffff;
		this.messageType = bb.getShort();
		this.messageLength = bb.getShort() & 0x0ffff;
		if(bb.hasRemaining()) {
			int MesLength = this.messageLength - ConstAcampMessage.MESSAGE_HEADER_LEN;
			byte[] remainingData = new byte[MesLength];
			bb.get(remainingData);
			this.data = remainingData;
		}
		return this;
	}
}
