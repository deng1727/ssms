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
	 * ��־����
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
	 * ����Ӧ�ü�ش���
	 */
	public void monitorCategory(){
		//1-MMӦ��
		if("1".equals(mcVO.getType())){
			//�ж��Ƿ��ڻ��ܴ���
			if(bo.keyMapMM.containsKey(mcVO.getAppid())){
				//���ڣ��жϼ�ؽ���Ƿ��м�¼
				if(!bo.keyMapMMResult.containsKey(mcVO.getAppid())){
					//û��¼������һ����ؽ����¼
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "HJ", "0");
					bo.keyMapMMResult.put(mcVO.getAppid(), new String[]{"0","0","0"});
				}else{
					//�м�¼���ж��Ƿ�����������ܣ��ǣ���״̬
					if("1".equals(bo.keyMapMMResult.get(mcVO.getAppid())[0]))
						//״̬��һ�¸���״̬
						AppMonitorDAO.getInstance().updateMMAppMonitorResult(mcVO, "HJ", "0");
				}
			}else{
				//�����ڣ��жϼ�ؽ���Ƿ��м�¼
                if(bo.keyMapMMResult.containsKey(mcVO.getAppid())){
                	//�м�¼���ж��Ƿ�����������ܣ��ǣ���״̬
                	if("0".equals(bo.keyMapMMResult.get(mcVO.getAppid())[0]))
                		AppMonitorDAO.getInstance().updateMMAppMonitorResult(mcVO, "HJ", "1");
				}else{
					//û��¼������һ����ؽ����¼
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "HJ", "1");
					bo.keyMapMMResult.put(mcVO.getAppid(), new String[]{"1","0","0"});
				}
			}
		}//2-���Ӧ��
		else if("2".equals(mcVO.getType())){
			//�ж��Ƿ��ڻ��ܴ���
			if(bo.keyMapHJ.containsKey(mcVO.getPackagename())){
				//���ڣ��жϼ�ؽ���Ƿ��м�¼
				if(!bo.keyMapHJResult.containsKey(mcVO.getPackagename())){
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "HJ", "0");
					bo.keyMapHJResult.put(mcVO.getPackagename(), new String[]{"0","0","0"});
				}else{
					//�м�¼���ж��Ƿ�����������ܣ��ǣ���״̬
					if("1".equals(bo.keyMapHJResult.get(mcVO.getPackagename())[0]))
						//״̬��һ�¸���״̬
						AppMonitorDAO.getInstance().updateHJAppMonitorResult(mcVO, "HJ", "0");
				}
			}else{
				//�����ڣ��жϼ�ؽ���Ƿ��м�¼
                if(bo.keyMapHJResult.containsKey(mcVO.getPackagename())){
                	//�м�¼���ж��Ƿ�����������ܣ��ǣ���״̬
                	if("0".equals(bo.keyMapHJResult.get(mcVO.getPackagename())[0]))
                		AppMonitorDAO.getInstance().updateHJAppMonitorResult(mcVO, "HJ", "1");
				}else{
					//û��¼������һ����ؽ����¼
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "HJ", "1");
					bo.keyMapHJResult.put(mcVO.getPackagename(), new String[]{"1","0","0"});
				}
			}
		}
	}
	
	/**
	 * ��������Ӧ�ü�ش���
	 */
	public void monitorDataCenter(){
		//�����������Ĳ�ѯӦ�ýӿڼ��Ӧ���Ƿ����
		int statusCode = isHasDataCenter(mcVO);
		//-1��ʾ�����������ļ�ؽӿ�ʧ��
		if(-1 == statusCode){
			logger.error("�����������ļ�ؽӿ�ʧ�ܣ�appid="+mcVO.getAppid()+",Packagename="+mcVO.getPackagename());
			return;
		}
		//0 ��ʾ�������ļ��Ӧ�ô���
		if(0 == statusCode){
			//1-MMӦ��
			if("1".equals(mcVO.getType())){
				//���ڣ��жϼ�ؽ���Ƿ��м�¼
				if(!bo.keyMapMMResult.containsKey(mcVO.getAppid())){
					//û��¼������һ����ؽ����¼
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "DC", "0");
					bo.keyMapMMResult.put(mcVO.getAppid(), new String[]{"0","0","0"});
				}else{
					//�м�¼���ж��Ƿ�����������ܣ��ǣ���״̬
					if("1".equals(bo.keyMapMMResult.get(mcVO.getAppid())[1]))
						//״̬��һ�¸���״̬
						AppMonitorDAO.getInstance().updateMMAppMonitorResult(mcVO, "DC", "0");
				}
			}//2-���Ӧ��
			else if("2".equals(mcVO.getType())){
				//���ڣ��жϼ�ؽ���Ƿ��м�¼
				if(!bo.keyMapHJResult.containsKey(mcVO.getPackagename())){
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "DC", "0");
					bo.keyMapHJResult.put(mcVO.getPackagename(), new String[]{"0","0","0"});
				}else{
					//�м�¼���ж��Ƿ�����������ܣ��ǣ���״̬
					if("1".equals(bo.keyMapHJResult.get(mcVO.getPackagename())[1]))
						//״̬��һ�¸���״̬
						AppMonitorDAO.getInstance().updateHJAppMonitorResult(mcVO, "DC", "0");
				}
			}
			return;
		}
		//1 ��ʾ�������ļ��Ӧ�ò�����
		if(1 == statusCode){
			//1-MMӦ��
			if("1".equals(mcVO.getType())){
				//�����ڣ��жϼ�ؽ���Ƿ��м�¼
                if(bo.keyMapMMResult.containsKey(mcVO.getAppid())){
                	//�м�¼���ж��Ƿ�����������ܣ��ǣ���״̬
                	if("0".equals(bo.keyMapMMResult.get(mcVO.getAppid())[1]))
                		AppMonitorDAO.getInstance().updateMMAppMonitorResult(mcVO, "DC", "1");
				}else{
					//û��¼������һ����ؽ����¼
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "DC", "1");
					bo.keyMapMMResult.put(mcVO.getAppid(), new String[]{"0","1","0"});
				}
			}//2-���Ӧ��
			else if("2".equals(mcVO.getType())){
				//�����ڣ��жϼ�ؽ���Ƿ��м�¼
                if(bo.keyMapHJResult.containsKey(mcVO.getPackagename())){
                	//�м�¼���ж��Ƿ�����������ܣ��ǣ���״̬
                	if("0".equals(bo.keyMapHJResult.get(mcVO.getPackagename())[1]))
                		AppMonitorDAO.getInstance().updateHJAppMonitorResult(mcVO, "DC", "1");
				}else{
					//û��¼������һ����ؽ����¼
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "DC", "1");
					bo.keyMapHJResult.put(mcVO.getPackagename(), new String[]{"0","1","0"});
				}
			}
		}
	}
	
	/**
	 * ����ϵͳӦ�ü�ش���
	 */
	public void monitorMMSearch(){
		//��������ϵͳ��ѯӦ�ýӿڼ��Ӧ���Ƿ����
		int statusCode = isHasMMSearch(mcVO);
		//-1��ʾ����ϵͳ��ؽӿڵ���ʧ��
		if(-1 == statusCode){
			logger.error("�����������ļ�ؽӿ�ʧ�ܣ�appid="+mcVO.getAppid()+",Packagename="+mcVO.getPackagename());
			return;
		}
		//0 ��ʾ����ϵͳ���Ӧ�ô���
		if(0 == statusCode){
			//1-MMӦ��
			if("1".equals(mcVO.getType())){
				//���ڣ��жϼ�ؽ���Ƿ��м�¼
				if(!bo.keyMapMMResult.containsKey(mcVO.getAppid())){
					//û��¼������һ����ؽ����¼
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "SS", "0");
					bo.keyMapMMResult.put(mcVO.getAppid(), new String[]{"0","0","0"});
				}else{
					//�м�¼���ж��Ƿ�����������ܣ��ǣ���״̬
					if("1".equals(bo.keyMapMMResult.get(mcVO.getAppid())[2]))
						//״̬��һ�¸���״̬
						AppMonitorDAO.getInstance().updateMMAppMonitorResult(mcVO, "SS", "0");
				}
			}//2-���Ӧ��
			else if("2".equals(mcVO.getType())){
				//���ڣ��жϼ�ؽ���Ƿ��м�¼
				if(!bo.keyMapHJResult.containsKey(mcVO.getPackagename())){
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "SS", "0");
					bo.keyMapHJResult.put(mcVO.getPackagename(), new String[]{"0","0","0"});
				}else{
					//�м�¼���ж��Ƿ�����������ܣ��ǣ���״̬
					if("1".equals(bo.keyMapHJResult.get(mcVO.getPackagename())[2]))
						//״̬��һ�¸���״̬
						AppMonitorDAO.getInstance().updateHJAppMonitorResult(mcVO, "SS", "0");
				}
			}
			return;
		}
		//1 ��ʾ����ϵͳ���Ӧ�ò�����
		if(1 == statusCode){
	    	//1-MMӦ��
			if("1".equals(mcVO.getType())){
				//�����ڣ��жϼ�ؽ���Ƿ��м�¼
                if(bo.keyMapMMResult.containsKey(mcVO.getAppid())){
                	//�м�¼���ж��Ƿ�����������ܣ��ǣ���״̬
                	if("0".equals(bo.keyMapMMResult.get(mcVO.getAppid())[2]))
                		AppMonitorDAO.getInstance().updateMMAppMonitorResult(mcVO, "SS", "1");
				}else{
					//û��¼������һ����ؽ����¼
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "SS", "1");
					bo.keyMapMMResult.put(mcVO.getAppid(), new String[]{"0","0","1"});
				}
			}//2-���Ӧ��
			else if("2".equals(mcVO.getType())){
				//�����ڣ��жϼ�ؽ���Ƿ��м�¼
                if(bo.keyMapHJResult.containsKey(mcVO.getPackagename())){
                	//�м�¼���ж��Ƿ�����������ܣ��ǣ���״̬
                	if("0".equals(bo.keyMapHJResult.get(mcVO.getPackagename())[2]))
                		AppMonitorDAO.getInstance().updateHJAppMonitorResult(mcVO, "SS", "1");
				}else{
					//û��¼������һ����ؽ����¼
					AppMonitorDAO.getInstance().addAppMonitorResult(mcVO, "SS", "1");
					bo.keyMapHJResult.put(mcVO.getPackagename(), new String[]{"0","0","1"});
				}
			}
	    }
	}
	
	/**
	 * �ж����������Ƿ����Ӧ��
	 * @param mcVO
	 * @return
	 */
	public int isHasDataCenter(MonitorContentVO mcVO){
		int statusCode = -1;
		if("1".equals(mcVO.getType())){
			//mmӦ��ֱ����appid��Ϊcontentid
			String type = "APP";
			statusCode = sendDataCenter(mcVO.getAppid(),type);
		}else if("2".equals(mcVO.getType())){
			//���Ӧ�ø���mcVO.getPackagename()��v_om_third_party_relation��contentid
			//getPackagename��Ӧv_om_third_party_relation���joincontentid
			List<String> list = AppMonitorDAO.getInstance().getContentIDByPackagename(mcVO.getPackagename());
			StringBuffer sb = null;
			if(null != list && list.size() > 0){
				String type = "THIRD";
				for(int i = 0;i < list.size();i++){
					String contentid = list.get(i);
					statusCode = sendDataCenter(contentid,type);
					//��һ�����ھͱ�ʾ����
					if(0 == statusCode)
						return statusCode;
				}
			}
		}
		return statusCode;
	}
	
	/**
	 * �������ϵͳ�Ƿ����Ӧ��
	 * @param mcVO
	 * @return
	 */
	private int isHasMMSearch(MonitorContentVO mcVO){
		int statusCode = -1;
		if("1".equals(mcVO.getType())){
			//mmӦ��ֱ����appid��Ϊcontentid
			statusCode = sendMMSearch(mcVO.getAppid());
		}else if("2".equals(mcVO.getType())){
			//���Ӧ�ø���mcVO.getPackagename()��v_om_third_party_relation��contentid
			//getPackagename��Ӧv_om_third_party_relation���joincontentid
			List<String> list = AppMonitorDAO.getInstance().getContentIDByPackagename(mcVO.getPackagename());
			if(null != list && list.size() > 0){
				for(int i = 0;i < list.size();i++){
					String contentid = list.get(i);
					statusCode = sendMMSearch(contentid);
					//��һ�����ھͱ�ʾ����
					if(0 == statusCode)
						return statusCode;
				}
			}
		}
		return statusCode;
	}
	
	/**
	 * ��������ϵͳӦ�ü�ؽӿ�
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
						//����Ϊ��{"result":[]}���򲻴���
						statusCode = 1;
					}else{
						//��Ϊ���������ݣ���ʾ����
						statusCode = 0;
					}
				}
			}
		} catch (Exception e) {
			logger.error("��������ϵͳӦ�ü�ؽӿ�����ʧ�ܣ�contentid="+contentid, e);
		}
		return statusCode;
	}
	
	/**
	 * ������������Ӧ�ü�ؽӿ�
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
			logger.error("������������Ӧ�ü�ؽӿ�����ʧ�ܣ�contentid="+contentid, e);
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
