package net.floodlightcontroller.acamp.agent;

import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.floodlightcontroller.acamp.msgele.WlanInformation;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

public class ACAMPConfigResource extends ServerResource {
	protected static Logger log = LoggerFactory.getLogger(ACAMPConfigResource.class);
	
	//配置相关信息，全局变量
	protected static WlanInformation wlanInfo = new WlanInformation();
	
	
	@Get("json")
	public String ShowAPConfiguration() {
		String key = new String("helloworld");
		wlanInfo.setAuthType((byte)0x11)
		.setCapability((short)0x2222)
		.setGroupTSC(new byte[6])
		.setKeyIndex((byte)0x33)
		.setKeyStatus((byte)0x44)
		.setQos((byte)0x55)
		.setRadioId((byte)0x66)
		.setWlanId((byte)0x77)
		.setSuppressSSID((byte)0x88)
		.setSsid("Helloworld")
		.setKey(key.getBytes());
		List<Object> jsonList = new ArrayList<Object>();
		jsonList.add(wlanInfo);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(
				) {

			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if(arg2 == null) {
					return true;
				}
				return false;
			}
		});
		String json = JSONArray.fromObject(jsonList, jsonConfig).toString();
		return json;
	}
	
	@Post
	public void SetAPConfiguration(String json) {
		
	}
}
