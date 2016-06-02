package net.floodlightcontroller.acamp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.floodlightcontroller.core.IOFSwitch;

import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.TransportPort;

//this entity used to describe the working parameter set of Access Point 
public class DeviceAp {
	public enum State {
		DOWN,
		REGISTER,
		CONFIGURE,
		RUN
	}
	public State apState;
	private String apName;
	private int apId;
	private byte[] apIpAddress;
	private byte[] apMacAddress;
	private String apDescription;
	private String ssid;
	private short channel;
	private byte hwMode;
	private byte suppressSsid;
	private byte securitySetting;
	private byte wpaVersion;
	private String wpaPassphrase;
	private byte wpaKeyManagement;
	private byte wpaPairwise;
	private int wpaGroupRekey;
	private byte wepDefaultKey;
	private Map<Integer, String> wepKey;
	private byte macAclMode;
	private List<byte[]> macAcceptList;
	private List<byte[]> macDenyList;
	private String countryCode;
	private byte enableDot11d;
	private short keepAliveInterval;
	private short discoverInterval;
	private short statisticTimer;
	private short stateTimer;
	private short idleTimeout;
	private short discoverTimeout;
	private short registerTimeout;
	private short reportInterval;
	private List<DeviceStation> stationList;
	public IOFSwitch connectedSwitch;
	public OFPort connectedSwitchPort;
	public TransportPort udpPort;
//	public IOFSwitch getConnectedSwitch() {
//		return connectedSwitch;
//	}
//	public void setConnectedSwitch(IOFSwitch connectedSwitch) {
//		this.connectedSwitch = connectedSwitch;
//	}
//	public OFPort getConnectedSwitchPort() {
//		return connectedSwitchPort;
//	}
//	public void setConnectedSwitchPort(OFPort connectedSwitchPort) {
//		this.connectedSwitchPort = connectedSwitchPort;
//	}
//	public TransportPort getUdpPort() {
//		return udpPort;
//	}
//	public void setUdpPort(TransportPort udpPort) {
//		this.udpPort = udpPort;
//	}
	public DeviceAp() {
		apState = State.DOWN;
		wepKey = new HashMap<Integer, String>();
		macAcceptList = new ArrayList<byte[]>();
		macDenyList = new ArrayList<byte[]>();
		stationList = new ArrayList<DeviceStation>();
	}
	public List<DeviceStation> getStationList() {
		return stationList;
	}
	public void addStation(DeviceStation station) {
		stationList.add(station);
	}
	public short getKeepAliveInterval() {
		return keepAliveInterval;
	}
	public void setKeepAliveInterval(short keepAliveInterval) {
		this.keepAliveInterval = keepAliveInterval;
	}
	public short getDiscoverInterval() {
		return discoverInterval;
	}
	public void setDiscoverInterval(short discoverInterval) {
		this.discoverInterval = discoverInterval;
	}
	public short getStatisticTimer() {
		return statisticTimer;
	}
	public void setStatisticTimer(short statisticTimer) {
		this.statisticTimer = statisticTimer;
	}
	public short getStateTimer() {
		return stateTimer;
	}
	public void setStateTimer(short stateTimer) {
		this.stateTimer = stateTimer;
	}
	public short getIdleTimeout() {
		return idleTimeout;
	}
	public void setIdleTimeout(short idleTimeout) {
		this.idleTimeout = idleTimeout;
	}
	public short getDiscoverTimeout() {
		return discoverTimeout;
	}
	public void setDiscoverTimeout(short discoverTimeout) {
		this.discoverTimeout = discoverTimeout;
	}
	public short getRegisterTimeout() {
		return registerTimeout;
	}
	public void setRegisterTimeout(short registerTimeout) {
		this.registerTimeout = registerTimeout;
	}
	public short getReportInterval() {
		return reportInterval;
	}
	public void setReportInterval(short reportInterval) {
		this.reportInterval = reportInterval;
	}
	public String getApName() {
		return apName;
	}
	public void setApName(String apName) {
		this.apName = apName;
	}
	public int getApId() {
		return apId;
	}
	public void setApId(int apId) {
		this.apId = apId;
	}
	public byte[] getApIpAddress() {
		return apIpAddress;
	}
	public void setApIpAddress(byte[] apIpAddress) {
		this.apIpAddress = apIpAddress;
	}
	public byte[] getApMacAddress() {
		return apMacAddress;
	}
	public void setApMacAddress(byte[] apMacAddress) {
		this.apMacAddress = apMacAddress;
	}
	public String getApDescription() {
		return apDescription;
	}
	public void setApDescription(String apDescription) {
		this.apDescription = apDescription;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public short getChannel() {
		return channel;
	}
	public void setChannel(short channel) {
		this.channel = channel;
	}
	public byte getHwMode() {
		return hwMode;
	}
	public void setHwMode(byte hwMode) {
		this.hwMode = hwMode;
	}
	public byte getSuppressSsid() {
		return suppressSsid;
	}
	public void setSuppressSsid(byte suppressSsid) {
		this.suppressSsid = suppressSsid;
	}
	public byte getSecuritySetting() {
		return securitySetting;
	}
	public void setSecuritySetting(byte securitySetting) {
		this.securitySetting = securitySetting;
	}
	public byte getWpaVersion() {
		return wpaVersion;
	}
	public void setWpaVersion(byte wpaVersion) {
		this.wpaVersion = wpaVersion;
	}
	public String getWpaPassphrase() {
		return wpaPassphrase;
	}
	public void setWpaPassphrase(String wpaPassphrase) {
		this.wpaPassphrase = wpaPassphrase;
	}
	public byte getWpaKeyManagement() {
		return wpaKeyManagement;
	}
	public void setWpaKeyManagement(byte wpaKeyManagement) {
		this.wpaKeyManagement = wpaKeyManagement;
	}
	public byte getWpaPairwise() {
		return wpaPairwise;
	}
	public void setWpaPairwise(byte wpaPairwise) {
		this.wpaPairwise = wpaPairwise;
	}
	public int getWpaGroupRekey() {
		return wpaGroupRekey;
	}
	public void setWpaGroupRekey(int wpaGroupRekey) {
		this.wpaGroupRekey = wpaGroupRekey;
	}
	public byte getWepDefaultKey() {
		return wepDefaultKey;
	}
	public void setWepDefaultKey(byte wepDefaultKey) {
		this.wepDefaultKey = wepDefaultKey;
	}
	public void setWepKey(Map<Integer, String> wepKey) {
		this.wepKey = wepKey;
	}
	public void setMacAcceptList(List<byte[]> macAcceptList) {
		this.macAcceptList = macAcceptList;
	}
	public void setMacDenyList(List<byte[]> macDenyList) {
		this.macDenyList = macDenyList;
	}
	public void setEnableDot11d(byte enableDot11d) {
		this.enableDot11d = enableDot11d;
	}
	public byte getMacAclMode() {
		return macAclMode;
	}
	public void setMacAclMode(byte macAclMode) {
		this.macAclMode = macAclMode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public byte getEnableDot11d() {
		return enableDot11d;
	}
	public Map<Integer, String> getWepKey() {
		return wepKey;
	}
	public void addWepKey(int keyNumber, String key) {
		wepKey.put(Integer.valueOf(keyNumber), key);
	}
	public List<byte[]> getMacAcceptList() {
		return macAcceptList;
	}
	public void addMacAcceptListEntry(byte[] macAddress) {
		this.macAcceptList.add(macAddress);
	}
	public List<byte[]> getMacDenyList() {
		return macDenyList;
	}
	public void addMacDenyListEntry(byte[] macAddress) {
		this.macDenyList.add(macAddress);
	}
}
