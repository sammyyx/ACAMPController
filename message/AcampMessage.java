package net.floodlightcontroller.acamp.message;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Enumeration;

public class AcampMessage {
	private byte protocol_version;
	private byte protocol_type;
	private int APID;
	private long sequence_number;
	private short message_type;
	private int message_length;
	private byte[] data;
	public AcampMessage() {
		this.protocol_version = -1;
		this.protocol_type = -1;
		this.APID = -1;
		this.sequence_number = -1;
		this.message_type = -1;
		this.message_length = ConstAcampMessage.MESSAGE_HEADER_LEN;
		this.data = null;
	}
	public boolean isInitiate() {
		if(this.protocol_type == -1 
				|| this.protocol_version == -1 
				|| this.APID == -1 
				|| this.sequence_number == -1
				|| this.message_type == -1
				) {
			return false;
		}
		return true;
	}
	public byte getProtocol_version() {
		return protocol_version;
	}
	public void setProtocol_version(byte protocol_version) {
		this.protocol_version = protocol_version;
	}
	public byte getProtocol_type() {
		return protocol_type;
	}
	public void setProtocol_type(byte protocol_type) {
		this.protocol_type = protocol_type;
	}
	public int getAPID() {
		return APID;
	}
	public void setAPID(int APID) {
		this.APID = APID;
	}
	public long getSequence_number() {
		return sequence_number;
	}
	public void setSequence_number(long sequence_number) {
		this.sequence_number = sequence_number;
	}
	public short getMessage_type() {
		return message_type;
	}
	public void setMessage_type(short message_type) {
		this.message_type = message_type;
	}
	public int getMessage_length() {
		return message_length;
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 2;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 2;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 2;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + controllerName.length();
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + controllerDescriptor.length();
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		byte[] ipv4Addr;
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while(netInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface)netInterfaces.nextElement();
				if(netInterface.getName().equals("eth0")) {
					Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
					while(addresses.hasMoreElements()) {
						InetAddress ipAddress = (InetAddress)addresses.nextElement();
						if(ipAddress instanceof Inet4Address) {
							ipv4Addr = ipAddress.getAddress();
							int mesLength = ConstAcampMe.HEADER_LENGTH + ipv4Addr.length;
							this.message_length += mesLength;
							Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
							ByteBuffer bb = ByteBuffer.wrap(Mebytes);
							if(data != null) bb.put(this.data);
							bb.putShort(ConstAcampMe.CONTROLLER_IP_ADDR);
							bb.putShort((short)mesLength);
							bb.put(ipv4Addr);
							this.data = Mebytes;
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	public void addControllerMacAddr() {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		byte[] Mebytes;
		byte[] macAddr;
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while(netInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface)netInterfaces.nextElement();
				if(netInterface.getName().equals("eth0")) {
					macAddr = netInterface.getHardwareAddress();
					int mesLength = ConstAcampMe.HEADER_LENGTH + macAddr.length;
					this.message_length += mesLength;
					Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
					ByteBuffer bb = ByteBuffer.wrap(Mebytes);
					if(data != null) bb.put(this.data);
					bb.putShort(ConstAcampMe.CONTROLLER_MAC_ADDR);
					bb.putShort((short)mesLength);
					bb.put(macAddr);
					this.data = Mebytes;
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + ssid.length();
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 1;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 1;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 1;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 1;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 1;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + wpaPassphrase.length();
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 1;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 1;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 4;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.WPA_GROUP_REKEY);
		bb.putShort((short)mesLength);
		bb.putInt(rekeyInterval);
		this.data = Mebytes;
	}
	public void addWepDefaultKey(int defaultKey) {
		if(!this.isInitiate()) {
			System.out.println("Error! Operation is not initiated");
			return;
		}
		if(defaultKey < 0 || defaultKey > 3) {
			System.out.println("Error! Invalid length for this element");
			return;
		}
		byte[] Mebytes;
		int mesLength = ConstAcampMe.HEADER_LENGTH + 4;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
		ByteBuffer bb = ByteBuffer.wrap(Mebytes);
		if(data != null) bb.put(this.data);
		bb.putShort(ConstAcampMe.WEP_DEFAULT_KEY);
		bb.putShort((short)mesLength);
		bb.putInt(defaultKey);
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
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 1;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 6;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 6;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		int mesLength = ConstAcampMe.HEADER_LENGTH + 8;
		this.message_length += mesLength;
		Mebytes = new byte[this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN];
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
		byte[] byteBuffer = new byte[message_length];
		ByteBuffer bb = ByteBuffer.wrap(byteBuffer);
		bb.put(this.protocol_version);
		bb.put(this.protocol_type);
		bb.putShort((short)this.APID);
		bb.putInt((int)this.sequence_number);
		bb.putShort(this.message_type);
		bb.putShort((short)this.message_length);
		if(this.data != null) {
			bb.put(this.data);
		}
		return byteBuffer;
	}
	public AcampMessage deserialize(byte[] rawBytes) {
		ByteBuffer bb = ByteBuffer.wrap(rawBytes);
		this.protocol_version = bb.get();
		this.protocol_type = bb.get();
		this.APID = bb.getShort() & 0x0ffff;
		this.sequence_number = bb.getInt() & 0x0ffffffff;
		this.message_type = bb.getShort();
		this.message_length = bb.getShort() & 0x0ffff;
		if(bb.hasRemaining()) {
			int MesLength = this.message_length - ConstAcampMessage.MESSAGE_HEADER_LEN;
			byte[] remainingData = new byte[MesLength];
			bb.get(remainingData);
			this.data = remainingData;
		}
		return this;
	}
}
