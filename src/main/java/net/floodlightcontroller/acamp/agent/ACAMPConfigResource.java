package net.floodlightcontroller.acamp.agent;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ACAMPConfigResource extends ServerResource {
	protected static Logger log = LoggerFactory.getLogger(ACAMPConfigResource.class);
	
	//配置相关信息，全局变量
	protected static byte radioId;
	protected static byte wlanId;
	protected static short Capability;
	protected static byte keyIndex;
	protected static byte keyStatus;
	protected static short keyLength;
	protected static byte[] key;
	protected static byte[] groupTSC;	//length fixed to 6 bytes
	protected static byte qos;
	protected static byte authType;
	protected static byte suppressSSID;
	protected static String ssid;
	
	@Get("json")
	public String ShowAPConfiguration() {
		String json = null;
		
		return json;
	}
	
	@Post
	public void SetAPConfiguration(String json) {
		
	}
}
