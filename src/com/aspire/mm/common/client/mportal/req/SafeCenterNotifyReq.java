package com.aspire.mm.common.client.mportal.req;

import java.util.List;

import org.apache.log4j.Logger;

import com.aspire.dotcard.syncAndroid.ppms.PackageVO;
import com.aspire.mm.common.client.httpsend.Req;
import com.aspire.mm.common.client.httpsend.Resp;
import com.aspire.mm.common.client.mportal.resp.SafeCenterNotifyResp;

public class SafeCenterNotifyReq implements Req {

	/**
     * 日志引用
     */
    private static Logger logger = Logger.getLogger(SafeCenterNotifyReq.class);
       
    /***渠道类型(数据源类型):1-电子流,2-货架,3-渠道报备,4-汇聚*/
    private String pushtype;
    
    /***应用包新信息列表*/
    private List<PackageVO> packageVOList;
    
	public String getPushtype() {
		return pushtype;
	}

	public void setPushtype(String pushtype) {
		this.pushtype = pushtype;
	}

	public List<PackageVO> getPackageVOList() {
		return packageVOList;
	}

	public void setPackageVOList(List<PackageVO> packageVOList) {
		this.packageVOList = packageVOList;
	}

	public String toData() {
		if(logger.isDebugEnabled()){
            logger.debug("SafeCenterNotifyReq   start");
        }
        
        StringBuffer strContent = new StringBuffer();
        strContent.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        strContent.append("<genuineinfodatapushreq>");
        strContent.append("<msgheader version=\"1.0.0\">");        
        strContent.append("<pushtype>"+this.pushtype+"</pushtype>");        
        strContent.append("</msgheader>");
        strContent.append("<msgbody>");
        strContent.append(this.getCheck());
        strContent.append("</msgbody>");
        strContent.append("</genuineinfodatapushreq>");        
        
        if(logger.isDebugEnabled()){
            logger.debug(strContent.toString());
            logger.debug("SafeCenterNotifyReq   end");
        }
        return strContent.toString();
	}

	private String getCheck() {
		StringBuffer stringBuffer = new StringBuffer();
		if (packageVOList != null && packageVOList.size() > 0) {
			for (int i =0 ;i<packageVOList.size() ;i++) {
				PackageVO packageVO = packageVOList.get(i);
				stringBuffer.append("<check>");
			    stringBuffer.append("<package>"+packageVO.getPackageName()+"</package>");
			    stringBuffer.append("<version>"+packageVO.getVersionCode()+"</version>");
			    stringBuffer.append("<cermd5>"+packageVO.getCermd5()+"</cermd5>");
			    stringBuffer.append("<online>"+packageVO.getOnline()+"</online>");
			    stringBuffer.append("<deduction>"+packageVO.getDeduction()+"</deduction>");
			    stringBuffer.append("</check>");
			}
		}
		return stringBuffer.toString();
	}
	
	public Resp getResp() {
		return new SafeCenterNotifyResp();
	}

}
