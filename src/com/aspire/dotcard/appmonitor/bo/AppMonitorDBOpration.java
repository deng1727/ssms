package com.aspire.dotcard.appmonitor.bo;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.appmonitor.config.AppMonitorConfig;
import com.aspire.dotcard.appmonitor.dao.AppMonitorDAO;
import com.aspire.dotcard.appmonitor.vo.MonitorContentVO;
import com.aspire.mm.common.client.httpsend.HttpUtils;

public class AppMonitorDBOpration {

	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory.getLogger(AppMonitorDBOpration.class);
	
	private MonitorContentVO mcVO = null;
	private AppMonitorBO bo;

	public AppMonitorDBOpration(MonitorContentVO vo,AppMonitorBO bo)
	{
		this.mcVO = vo;
		this.bo = bo;
	}
	
	/**
	 * 货架应用监控处理
	 */
	public void monitorCategory(){
		//1-MM应用
		if("1".equals(mcVO.getType())){
			//判断是否在货架存在
			if(bo.keyMapMM.containsKey(mcVO.getAppid())){
				//存在，判断监控结果是否有记录
				if(!bo.keyMapMMResult.containsKey(mcVO.getAppid())){
					//没记录，新增一条监控结果记录
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "HJ", "0");
					bo.keyMapMMResult.put(mcVO.getAppid(), new String[]{"0","0","0"});
				}else{
					//有记录，判断是否已输出至货架（是，否）状态
					if("1".equals(bo.keyMapMMResult.get(mcVO.getAppid())[0]))
						//状态不一致更新状态
						AppMonitorDAO.getInstance().updateMMAppMonitorResult(mcVO, "HJ", "0");
				}
			}else{
				//不存在，判断监控结果是否有记录
                if(bo.keyMapMMResult.containsKey(mcVO.getAppid())){
                	//有记录，判断是否已输出至货架（是，否）状态
                	if("0".equals(bo.keyMapMMResult.get(mcVO.getAppid())[0]))
                		AppMonitorDAO.getInstance().updateMMAppMonitorResult(mcVO, "HJ", "1");
				}else{
					//没记录，新增一条监控结果记录
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "HJ", "1");
					bo.keyMapMMResult.put(mcVO.getAppid(), new String[]{"1","0","0"});
				}
			}
		}//2-汇聚应用
		else if("2".equals(mcVO.getType())){
			//判断是否在货架存在
			if(bo.keyMapHJ.containsKey(mcVO.getPackagename())){
				//存在，判断监控结果是否有记录
				if(!bo.keyMapHJResult.containsKey(mcVO.getPackagename())){
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "HJ", "0");
					bo.keyMapHJResult.put(mcVO.getPackagename(), new String[]{"0","0","0"});
				}else{
					//有记录，判断是否已输出至货架（是，否）状态
					if("1".equals(bo.keyMapHJResult.get(mcVO.getPackagename())[0]))
						//状态不一致更新状态
						AppMonitorDAO.getInstance().updateHJAppMonitorResult(mcVO, "HJ", "0");
				}
			}else{
				//不存在，判断监控结果是否有记录
                if(bo.keyMapHJResult.containsKey(mcVO.getPackagename())){
                	//有记录，判断是否已输出至货架（是，否）状态
                	if("0".equals(bo.keyMapHJResult.get(mcVO.getPackagename())[0]))
                		AppMonitorDAO.getInstance().updateHJAppMonitorResult(mcVO, "HJ", "1");
				}else{
					//没记录，新增一条监控结果记录
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "HJ", "1");
					bo.keyMapHJResult.put(mcVO.getPackagename(), new String[]{"1","0","0"});
				}
			}
		}
	}
	
	/**
	 * 数据中心应用监控处理
	 */
	public void monitorDataCenter(){
		//调用数据中心查询应用接口监控应用是否存在
		int statusCode = isHasDataCenter(mcVO);
		//-1表示调用数据中心监控接口失败
		if(-1 == statusCode){
			logger.error("调用数据中心监控接口失败，appid="+mcVO.getAppid()+",Packagename="+mcVO.getPackagename());
			return;
		}
		//0 表示数据中心监控应用存在
		if(0 == statusCode){
			//1-MM应用
			if("1".equals(mcVO.getType())){
				//存在，判断监控结果是否有记录
				if(!bo.keyMapMMResult.containsKey(mcVO.getAppid())){
					//没记录，新增一条监控结果记录
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "DC", "0");
					bo.keyMapMMResult.put(mcVO.getAppid(), new String[]{"0","0","0"});
				}else{
					//有记录，判断是否已输出至货架（是，否）状态
					if("1".equals(bo.keyMapMMResult.get(mcVO.getAppid())[1]))
						//状态不一致更新状态
						AppMonitorDAO.getInstance().updateMMAppMonitorResult(mcVO, "DC", "0");
				}
			}//2-汇聚应用
			else if("2".equals(mcVO.getType())){
				//存在，判断监控结果是否有记录
				if(!bo.keyMapHJResult.containsKey(mcVO.getPackagename())){
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "DC", "0");
					bo.keyMapHJResult.put(mcVO.getPackagename(), new String[]{"0","0","0"});
				}else{
					//有记录，判断是否已输出至货架（是，否）状态
					if("1".equals(bo.keyMapHJResult.get(mcVO.getPackagename())[1]))
						//状态不一致更新状态
						AppMonitorDAO.getInstance().updateHJAppMonitorResult(mcVO, "DC", "0");
				}
			}
			return;
		}
		//1 表示数据中心监控应用不存在
		if(1 == statusCode){
			//1-MM应用
			if("1".equals(mcVO.getType())){
				//不存在，判断监控结果是否有记录
                if(bo.keyMapMMResult.containsKey(mcVO.getAppid())){
                	//有记录，判断是否已输出至货架（是，否）状态
                	if("0".equals(bo.keyMapMMResult.get(mcVO.getAppid())[1]))
                		AppMonitorDAO.getInstance().updateMMAppMonitorResult(mcVO, "DC", "1");
				}else{
					//没记录，新增一条监控结果记录
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "DC", "1");
					bo.keyMapMMResult.put(mcVO.getAppid(), new String[]{"0","1","0"});
				}
			}//2-汇聚应用
			else if("2".equals(mcVO.getType())){
				//不存在，判断监控结果是否有记录
                if(bo.keyMapHJResult.containsKey(mcVO.getPackagename())){
                	//有记录，判断是否已输出至货架（是，否）状态
                	if("0".equals(bo.keyMapHJResult.get(mcVO.getPackagename())[1]))
                		AppMonitorDAO.getInstance().updateHJAppMonitorResult(mcVO, "DC", "1");
				}else{
					//没记录，新增一条监控结果记录
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "DC", "1");
					bo.keyMapHJResult.put(mcVO.getPackagename(), new String[]{"0","1","0"});
				}
			}
		}
	}
	
	/**
	 * 搜索系统应用监控处理
	 */
	public void monitorMMSearch(){
		//调用搜索系统查询应用接口监控应用是否存在
		int statusCode = isHasMMSearch(mcVO);
		//-1表示搜索系统监控接口调用失败
		if(-1 == statusCode){
			logger.error("调用数据中心监控接口失败，appid="+mcVO.getAppid()+",Packagename="+mcVO.getPackagename());
			return;
		}
		//0 表示搜索系统监控应用存在
		if(0 == statusCode){
			//1-MM应用
			if("1".equals(mcVO.getType())){
				//存在，判断监控结果是否有记录
				if(!bo.keyMapMMResult.containsKey(mcVO.getAppid())){
					//没记录，新增一条监控结果记录
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "SS", "0");
					bo.keyMapMMResult.put(mcVO.getAppid(), new String[]{"0","0","0"});
				}else{
					//有记录，判断是否已输出至货架（是，否）状态
					if("1".equals(bo.keyMapMMResult.get(mcVO.getAppid())[2]))
						//状态不一致更新状态
						AppMonitorDAO.getInstance().updateMMAppMonitorResult(mcVO, "SS", "0");
				}
			}//2-汇聚应用
			else if("2".equals(mcVO.getType())){
				//存在，判断监控结果是否有记录
				if(!bo.keyMapHJResult.containsKey(mcVO.getPackagename())){
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "SS", "0");
					bo.keyMapHJResult.put(mcVO.getPackagename(), new String[]{"0","0","0"});
				}else{
					//有记录，判断是否已输出至货架（是，否）状态
					if("1".equals(bo.keyMapHJResult.get(mcVO.getPackagename())[2]))
						//状态不一致更新状态
						AppMonitorDAO.getInstance().updateHJAppMonitorResult(mcVO, "SS", "0");
				}
			}
			return;
		}
		//1 表示搜索系统监控应用不存在
		if(1 == statusCode){
	    	//1-MM应用
			if("1".equals(mcVO.getType())){
				//不存在，判断监控结果是否有记录
                if(bo.keyMapMMResult.containsKey(mcVO.getAppid())){
                	//有记录，判断是否已输出至货架（是，否）状态
                	if("0".equals(bo.keyMapMMResult.get(mcVO.getAppid())[2]))
                		AppMonitorDAO.getInstance().updateMMAppMonitorResult(mcVO, "SS", "1");
				}else{
					//没记录，新增一条监控结果记录
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "SS", "1");
					bo.keyMapMMResult.put(mcVO.getAppid(), new String[]{"0","0","1"});
				}
			}//2-汇聚应用
			else if("2".equals(mcVO.getType())){
				//不存在，判断监控结果是否有记录
                if(bo.keyMapHJResult.containsKey(mcVO.getPackagename())){
                	//有记录，判断是否已输出至货架（是，否）状态
                	if("0".equals(bo.keyMapHJResult.get(mcVO.getPackagename())[2]))
                		AppMonitorDAO.getInstance().updateHJAppMonitorResult(mcVO, "SS", "1");
				}else{
					//没记录，新增一条监控结果记录
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "SS", "1");
					bo.keyMapHJResult.put(mcVO.getPackagename(), new String[]{"0","0","1"});
				}
			}
	    }
	}
	
	/**
	 * 判断数据中心是否存在应用
	 * @param mcVO
	 * @return
	 */
	public int isHasDataCenter(MonitorContentVO mcVO){
		int statusCode = -1;
		if("1".equals(mcVO.getType())){
			//mm应用直接用appid作为contentid
			String type = "APP";
			statusCode = sendDataCenter(mcVO.getAppid(),type);
		}else if("2".equals(mcVO.getType())){
			//汇聚应用根据mcVO.getPackagename()到v_om_third_party_relation查contentid
			//getPackagename对应v_om_third_party_relation表的joincontentid
			List<String> list = AppMonitorDAO.getInstance().getContentIDByPackagename(mcVO.getPackagename());
			StringBuffer sb = null;
			if(null != list && list.size() > 0){
				String type = "THIRD";
				for(int i = 0;i < list.size();i++){
					String contentid = list.get(i);
					statusCode = sendDataCenter(contentid,type);
					//有一个存在就表示存在
					if(0 == statusCode)
						return statusCode;
				}
			}
		}
		return statusCode;
	}
	
	/**
	 * 监控搜索系统是否存在应用
	 * @param mcVO
	 * @return
	 */
	private int isHasMMSearch(MonitorContentVO mcVO){
		int statusCode = -1;
		if("1".equals(mcVO.getType())){
			//mm应用直接用appid作为contentid
			statusCode = sendMMSearch(mcVO.getAppid());
		}else if("2".equals(mcVO.getType())){
			//汇聚应用根据mcVO.getPackagename()到v_om_third_party_relation查contentid
			//getPackagename对应v_om_third_party_relation表的joincontentid
			List<String> list = AppMonitorDAO.getInstance().getContentIDByPackagename(mcVO.getPackagename());
			if(null != list && list.size() > 0){
				for(int i = 0;i < list.size();i++){
					String contentid = list.get(i);
					statusCode = sendMMSearch(contentid);
					//有一个存在就表示存在
					if(0 == statusCode)
						return statusCode;
				}
			}
		}
		return statusCode;
	}
	
	/**
	 * 调用搜索系统应用监控接口
	 * @param contentid
	 * @return
	 */
	private int sendMMSearch(String contentid){
		int statusCode = -1;
		try {
			HttpEntity entity= HttpUtils.get(AppMonitorConfig.MMSearchUrl+getParams(contentid), null);
			if(null != entity){
				String jsonString = HttpUtils.inputStream2String(entity.getContent(), "utf-8");
				JSONObject jsonObj = JSONObject.fromObject(jsonString);
				JSONArray obj = jsonObj.getJSONArray("result");
				if(null != obj){
					if(obj.isEmpty()){
						//返回为空{"result":[]}，则不存在
						statusCode = 1;
					}else{
						//不为空则有数据，表示存在
						statusCode = 0;
					}
				}
			}
		} catch (Exception e) {
			logger.error("调用搜索系统应用监控接口请求失败，contentid="+contentid, e);
		}
		return statusCode;
	}
	
	/**
	 * 调用数据中心应用监控接口
	 * @param contentid
	 * @return
	 */
	private int sendDataCenter(String contentid,String type){
		int statusCode = -1;
		try {
			HttpEntity entity= HttpUtils.postJson(AppMonitorConfig.DataCenterUrl, getJson(contentid,type), "utf-8");
			if(null != entity){
				String jsonString = HttpUtils.inputStream2String(entity.getContent(), "utf-8");
				JSONObject jsonObj = JSONObject.fromObject(jsonString);
				if(null != jsonObj && jsonObj.containsKey("Content")){
					statusCode = 0;					
				}else{
					statusCode = 1;	
				}
			}
		} catch (Exception e) {
			logger.error("调用数据中心应用监控接口请求失败，contentid="+contentid, e);
		}
		return statusCode;
	}
	
	private String getJson(String contentid,String type){
		StringBuffer bf = new StringBuffer();
		//http://10.1.5.50:18080/pkginfo.do
		//{"Portal": "M", "Contentid": ["300001509576"], "Deviceid": "512"}
		bf.append("{\"Portal\": \"M\",");
		bf.append("\"Contentid\": ");
		bf.append(contentid);
		bf.append(",\"Ctype\": ");
		bf.append(type);
		bf.append(",\"Fields\":[\"GOODSID\"], \"Deviceid\": \"512\"}");
		return bf.toString();
	}
	
	private String getParams(String contentid){
		StringBuffer bf = new StringBuffer();
		//http://192.168.45.1:10001/mmsearch/query?f=O&t= &field=contentid&value= 300008528501
		bf.append("?");
		bf.append("f=O");
		bf.append("&t=");
		bf.append("&field=contentid");
		bf.append("&value=");
		bf.append(contentid);
		return bf.toString();
	}
}
