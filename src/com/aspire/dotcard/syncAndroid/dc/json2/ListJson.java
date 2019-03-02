package com.aspire.dotcard.syncAndroid.dc.json2;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.repository.web.CgyContentListAction;

import edu.emory.mathcs.backport.java.util.Arrays;

public class ListJson{
	

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory.getLogger(ListJson.class);
	
	public JSONObject getListJson(String parentid,String deviceid,int queryPage) throws JSONException{
		
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid_virtual_1");
		String portal =  module.getItemValue("Portal");
		String source = module.getItemValue("Source");
		String ispage = module.getItemValue("Ispage");
		String recordsPage = module.getItemValue("RecordsPage");
		String sorttype = module.getItemValue("Sorttype");
		String isfilter = module.getItemValue("Isfilter");
		String isneedsortchild = module.getItemValue("Isneedsortchild");
		String fields = module.getItemValue("Fields");
		String[] arr = fields.split("\\|");
		List<String> l = Arrays.asList(arr);
		JSONObject json = new JSONObject();
        json.put("Portal", portal);
        json.put("Source", source);
        json.put("Ispage", ispage);
        json.put("RecordsPage", recordsPage);  
        
        json.put("QueryPage", queryPage);
        json.put("Parentid", parentid);
        json.put("Deviceid", deviceid);
        json.put("Devicename", "");   
        
        json.put("Sorttype", sorttype);
        json.put("Isfilter", isfilter);
        json.put("Isneedsortchild", isneedsortchild);
        
        
        json.put("Fields", l); 
        //json.put("CFields", ""); 
        //String j = "{\"Fields\":[\"NAME\",\"SPNAME\",\"ICPCODE\",\"ICPSERVID\"],\"Portal\":\"W\",  \"Source\": \"MM\",  \"Parentid\": \"64307717\",  \"Sorttype\": \"lupddate_desc\",  \"Devicename\": \"\",  \"Ispage\": \"0\",  \"RecordsPage\": \"10\",  \"QueryPage\": \"1\",  \"Deviceid\": \"7709\",  \"Isneedsortchild\": \"-1\",  \"Isfilter\": \"-1\",  \"Servattr\"=\"G\"}";
        //return new JSONObject(j);
        LOG.debug("返回的json-->"+json.toString());
        return json;
        
        //return new JSONObject("{www:aa}");
        
	}
}
