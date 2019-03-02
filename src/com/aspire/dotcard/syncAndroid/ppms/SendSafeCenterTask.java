package com.aspire.dotcard.syncAndroid.ppms;

import java.util.ArrayList;
import java.util.List;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.mm.common.client.httpsend.HttpSender;
import com.aspire.mm.common.client.httpsend.Resp;
import com.aspire.mm.common.client.mportal.req.SafeCenterNotifyReq;
import com.aspire.mm.common.client.mportal.resp.SafeCenterNotifyResp;
import com.aspire.ponaadmin.web.actionlog.ActionLogVO;
import com.aspire.ponaadmin.web.daemon.DaemonTask;

public class SendSafeCenterTask extends DaemonTask{

	JLogger LOG = LoggerFactory.getLogger(SendSafeCenterTask.class);
	
	/**
     * ����id
     */
	private String contentid;
	
	/**
     * �Ƿ��ϼܣ�1�ϼ�;0�¼�
     */
	private int online;

	/**
	 * ���췽��������һ��д������־���첽����
	 * @param log Ҫд�Ĳ�����־������
	 */
    public SendSafeCenterTask (String contentid,int online)
    {
        this.contentid = contentid;
        this.online = online;
    }
	
	public void execute() {
		PPMSDAO dao = new PPMSDAO();
		List<PackageVO> list = null;
		try {
			//��ȡ���ݰ���Ϣ�б�
			list = dao.getPackageInfoListByContentID(contentid, online);   
		} catch (Exception e) {
			LOG.error("getPackageInfoListByContentID error!!,contentid="+contentid, e);
		}
		
		if(null != list && list.size() > 0){
	    	List<PackageVO> packageVOList = new ArrayList<PackageVO>();
	    	for(int i = 0;i < list.size();i++){
	    		packageVOList.add(list.get(i));
	    		//�ﵽָ�����������Ƿ���
	    		if(packageVOList.size() == getCheckNum()){
	    			SafeCenterNotifyReq req = new SafeCenterNotifyReq();
	    			req.setPushtype("2");
	    			req.setPackageVOList(packageVOList);
	    			try {
	    				LOG.info("SendSafeCenterTask start,contentid="+contentid+",notifyUrl="+getSafeCenterNotifyUrl()+",xmlmsgreq="+req.toData());
						SafeCenterNotifyResp resp = (SafeCenterNotifyResp) HttpSender.sendRequestEncrypt(getSafeCenterNotifyUrl(), req);
						LOG.info("SendSafeCenterTask end,contentid="+contentid+",notifyUrl="+getSafeCenterNotifyUrl()+",xmlmsgresp="+resp.getAsXml());
					} catch (Exception e) {
						LOG.error("sendSafeCenterNotify error!!,contentid="+contentid, e);
					}
	    			packageVOList = new ArrayList<PackageVO>(); 
	    		}
	    	}
	    	//���ͳһ����δ���͵�����
	    	if(null != packageVOList && packageVOList.size() > 0){
	    		SafeCenterNotifyReq req = new SafeCenterNotifyReq();
    			req.setPushtype("2");
    			req.setPackageVOList(packageVOList);
    			try {
    				LOG.info("SendSafeCenterTask start,contentid="+contentid+",notifyUrl="+getSafeCenterNotifyUrl()+",xmlmsgreq="+req.toData());
					SafeCenterNotifyResp resp = (SafeCenterNotifyResp) HttpSender.sendRequestEncrypt(getSafeCenterNotifyUrl(), req);
					LOG.info("SendSafeCenterTask end,contentid="+contentid+",notifyUrl="+getSafeCenterNotifyUrl()+",xmlmsgresp="+resp.getAsXml());
				} catch (Exception e) {
					LOG.error("sendSafeCenterNotify error!!,contentid="+contentid, e);
				}
	    	}
	    }
	}
	
	private String getSafeCenterNotifyUrl(){
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
        return module.getItemValue("SafeCenterNotifyUrl");
	}
	
	private int getCheckNum(){
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
        return Integer.parseInt(module.getItemValue("SafeCenterNotifyCheckNum"));
	}
}
