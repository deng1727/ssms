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
     * 内容id
     */
	private String contentid;
	
	/**
     * 是否上架：1上架;0下架
     */
	private int online;

	/**
	 * 构造方法，构造一个写操作日志的异步任务
	 * @param log 要写的操作日志的内容
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
			//获取内容包信息列表
			list = dao.getPackageInfoListByContentID(contentid, online);   
		} catch (Exception e) {
			LOG.error("getPackageInfoListByContentID error!!,contentid="+contentid, e);
		}
		
		if(null != list && list.size() > 0){
	    	List<PackageVO> packageVOList = new ArrayList<PackageVO>();
	    	for(int i = 0;i < list.size();i++){
	    		packageVOList.add(list.get(i));
	    		//达到指定发的条数是发送
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
	    	//最后统一发送未发送的数据
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
