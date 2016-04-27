package net.floodlightcontroller.acamp.agent;

import java.util.HashMap;
import java.util.Map;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.floodlightcontroller.acamp.msgele.Antenna;
import net.floodlightcontroller.acamp.msgele.MultiDomainCapability;
import net.floodlightcontroller.acamp.msgele.TxPower;
import net.floodlightcontroller.acamp.msgele.WlanInformation;
import net.floodlightcontroller.acamptemplate.TemplateAntenna;
import net.floodlightcontroller.acamptemplate.TemplateMultiDomainCap;
import net.floodlightcontroller.acamptemplate.TemplateTxPower;
import net.floodlightcontroller.acamptemplate.TemplateWlanInfo;
import net.floodlightcontroller.packet.ACAMP;
import net.floodlightcontroller.packet.ACAMPData;
import net.floodlightcontroller.packet.ACAMPMsgEle;
import net.sf.json.JSONObject;

public class ACAMPConfigResource extends ServerResource {
	protected static Logger log = LoggerFactory.getLogger(ACAMPConfigResource.class);
	
	//配置相关信息，全局变量
	protected static TemplateWlanInfo templateWlanInfo = new TemplateWlanInfo();
	protected static TemplateAntenna templateAntenna = new TemplateAntenna();
	protected static TemplateTxPower templateTxPower = new TemplateTxPower();
	protected static TemplateMultiDomainCap templateMDC = new TemplateMultiDomainCap();
	
	@Get("json")
	public String ShowAPConfiguration() {
		String key = new String("helloworld");
		templateWlanInfo.setAuthType((byte)0x11);
		templateWlanInfo.setCapability((short)0x2222);
		templateWlanInfo.setGroupTSC(new byte[6]);
		templateWlanInfo.setKeyIndex((byte)0x33);
		templateWlanInfo.setKeyStatus((byte)0x44);
		templateWlanInfo.setQos((byte)0x55);
		templateWlanInfo.setRadioId((byte)0x66);
		templateWlanInfo.setWlanId((byte)0x77);
		templateWlanInfo.setSuppressSSID((byte)0x88);
		templateWlanInfo.setSsid("Helloworld");
		templateWlanInfo.setKey(key.getBytes());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("Wlan-Info", templateWlanInfo);
		jsonMap.put("Antenna", templateAntenna);
		jsonMap.put("Tx-Power", templateTxPower);
		jsonMap.put("MultiDomainCapability", templateMDC);
		String json = JSONObject.fromObject(jsonMap).toString();
		return json;
	}
	
	@Post
	public void SetAPConfiguration(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("Wlan-Info", TemplateWlanInfo.class);
		classMap.put("Antenna", TemplateAntenna.class);
		classMap.put("Tx-Power", TemplateTxPower.class);
		classMap.put("MultiDomainCapability", TemplateMultiDomainCap.class);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap = (Map)JSONObject.toBean(jsonObject, Map.class, classMap);
		if(jsonMap == null) return;
		ACAMPData acampData = new ACAMPData();
		byte[] configData = null;
		for(Map.Entry<String, Object> entry: jsonMap.entrySet()) {
		switch(entry.getKey()) {
		case "Wlan-Info":
		{
			ACAMPMsgEle msgEle = new ACAMPMsgEle();
			TemplateWlanInfo templateWlanInfo = (TemplateWlanInfo)entry.getValue();
			WlanInformation wlanInfo = (WlanInformation)msgEle.createBuilder(ACAMPProtocol.MsgEleType.WLAN_INFO);
			wlanInfo.setCapability(templateWlanInfo.getCapability())
					.setGroupTSC(templateWlanInfo.getGroupTSC())
					.setKey(templateWlanInfo.getKey())
					.setKeyIndex(templateWlanInfo.getKeyIndex())
					.setKeyStatus(templateWlanInfo.getKeyStatus())
					.setQos(templateWlanInfo.getQos())
					.setRadioId(templateWlanInfo.getRadioId())
					.setSsid(templateWlanInfo.getSsid())
					.setSuppressSSID(templateWlanInfo.getSuppressSSID())
					.setWlanId(templateWlanInfo.getWlanId())
					;
			acampData.addMessageElement(msgEle.build());
			break;
		}
		case "Antenna":
		{
			ACAMPMsgEle msgEle = new ACAMPMsgEle();
			TemplateAntenna templateAntenna = (TemplateAntenna)entry.getValue();
			Antenna antenna = (Antenna)msgEle.createBuilder(ACAMPProtocol.MsgEleType.ANTENNA);
			antenna.setAntennaCount(templateAntenna.getAntennaCount())
				   .setAntennaSelection(templateAntenna.getAntennaSelection())
				   .setCombiner(templateAntenna.getCombiner())
				   .setDiversity(templateAntenna.getDiversity())
				   .setRadioId(templateAntenna.getRadioId());
			acampData.addMessageElement(msgEle.build());
			break;
		}
		case "Tx-Power":
		{
			ACAMPMsgEle msgEle = new ACAMPMsgEle();
			TemplateTxPower templateTxPower = (TemplateTxPower)entry.getValue();
			TxPower txPower = (TxPower)msgEle.createBuilder(ACAMPProtocol.MsgEleType.TX_POWER);
			txPower.setNumberLevel(templateTxPower.getNumberLevel())
				   .setPowerLevel(templateTxPower.getPowerLevel())
				   .setRadioId(templateTxPower.getRadioId());
			acampData.addMessageElement(msgEle.build());
			break;
		}
		case "MultiDomainCapability":
		{
			ACAMPMsgEle msgEle = new ACAMPMsgEle();
			TemplateMultiDomainCap templateMDC = (TemplateMultiDomainCap)entry.getValue();
			MultiDomainCapability multiDC = (MultiDomainCapability)msgEle.createBuilder(ACAMPProtocol.MsgEleType.MULTI_DOMAIN_CAP);
			multiDC.setFirstChannel(templateMDC.getFirstChannel())
			   	   .setMaxTxPowerLevel(templateMDC.getMaxTxPowerLevel())
			   	   .setNumberOfChannels(templateMDC.getNumberOfChannels())
			   	   .setRadioID(templateMDC.getRadioId())
			   	   .setReserve(templateMDC.getReserve());
			acampData.addMessageElement(msgEle.build());
			break;
		}
		}
		}
		ACAMP acamp = new ACAMP();
		acamp.setVersion((short)0x0001)
		 	 .setAPID(144)
		 	 .setMessageType(ACAMPProtocol.MsgType.CONFIGURATION_RESPONSE)
		 	 .setVersion((short)0x0001)
		 	 .setType((byte)0x00)
		 	 .setSequenceNumber(Integer.MAX_VALUE);
		acamp.setPayload(acampData);
		configData = acamp.serialize();
		ACAMPagent.sendMessage(configData);
	}
}
