package net.floodlightcontroller.acamp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//this entity used to describe the working parameter set of Access Point 
public class DeviceAp {
	private String apName;
	private String apId;
	private String apIpAddress;
	private String apMacAddress;
	private String apDescription;
	private String ssid;
	private String channel;
	private String hwMode;
	private String suppressSsid;
	private String securitySetting;
	private String wpaVersion;
	private String wpaPassphrase;
	private String wpaKeyManagement;
	private String wpaPairwise;
	private String wpaGroupRekey;
	private String wepDefaultKey;
	private Map<Integer, String> wepKey;
	private String macAclMode;
	private List<byte[]> macAcceptList;
	private List<byte[]> macDenyList;
	private String countryCode;
	private String enableDot11d;
	public DeviceAp() {
		wepKey = new HashMap<Integer, String>();
		macAcceptList = new ArrayList<byte[]>();
		macDenyList = new ArrayList<byte[]>();
	}
	public String getApName() {
		return apName;
	}
	public void setApName(String apName) {
		this.apName = apName;
	}
	public String getApId() {
		return apId;
	}
	public void setApId(String apId) {
		this.apId = apId;
	}
	public String getApIpAddress() {
		return apIpAddress;
	}
	public void setApIpAddress(String apIpAddress) {
		this.apIpAddress = apIpAddress;
	}
	public String getApMacAddress() {
		return apMacAddress;
	}
	public void setApMacAddress(String apMacAddress) {
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
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getHwMode() {
		return hwMode;
	}
	public void setHwMode(String hwMode) {
		this.hwMode = hwMode;
	}
	public String getSuppressSsid() {
		return suppressSsid;
	}
	public void setSuppressSsid(String suppressSsid) {
		this.suppressSsid = suppressSsid;
	}
	public String getSecuritySetting() {
		return securitySetting;
	}
	public void setSecuritySetting(String securitySetting) {
		this.securitySetting = securitySetting;
	}
	public String getWpaVersion() {
		return wpaVersion;
	}
	public void setWpaVersion(String wpaVersion) {
		this.wpaVersion = wpaVersion;
	}
	public String getWpaPassphrase() {
		return wpaPassphrase;
	}
	public void setWpaPassphrase(String wpaPassphrase) {
		this.wpaPassphrase = wpaPassphrase;
	}
	public String getWpaKeyManagement() {
		return wpaKeyManagement;
	}
	public void setWpaKeyManagement(String wpaKeyManagement) {
		this.wpaKeyManagement = wpaKeyManagement;
	}
	public String getWpaPairwise() {
		return wpaPairwise;
	}
	public void setWpaPairwise(String wpaPairwise) {
		this.wpaPairwise = wpaPairwise;
	}
	public String getWpaGroupRekey() {
		return wpaGroupRekey;
	}
	public void setWpaGroupRekey(String wpaGroupRekey) {
		this.wpaGroupRekey = wpaGroupRekey;
	}
	public String getWepDefaultKey() {
		return wepDefaultKey;
	}
	public void setWepDefaultKey(String wepDefaultKey) {
		this.wepDefaultKey = wepDefaultKey;
	}
	
	public String getMacAclMode() {
		return macAclMode;
	}
	public void setMacAclMode(String macAclMode) {
		this.macAclMode = macAclMode;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getEnableDot11d() {
		return enableDot11d;
	}
	public void setEnableDot11d(String enableDot11d) {
		this.enableDot11d = enableDot11d;
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
