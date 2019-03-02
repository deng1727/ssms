package com.aspire.dotcard.iapMonitor.bo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.iapMonitor.config.IapMonitorConfig;
import com.aspire.dotcard.iapMonitor.dao.IapMonitorDAO;
import com.aspire.dotcard.iapMonitor.vo.ContentVO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.DateUtil;

public class IapMonitorBO {

	/**
	 * 日志对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(IapMonitorBO.class);
	
	private static IapMonitorBO bo = new IapMonitorBO();
	
	private IapMonitorBO()
	{}
	
	public static IapMonitorBO getInstance()
	{
		return bo;
	}
	
	public void execution(String type){
		boolean isSendMail = false;
		String[] mailTo = IapMonitorConfig.MAILTO;
		String categoryId = null;
		String deviceId = null;
		String mailTitle = null;
		Map<String,Integer> map = null;
		String date = DateUtil.formatDate(new Date(), "MM月dd日");
		StringBuffer mailContent = new StringBuffer();
		mailContent.append("截止").append(date);
		if("1".equals(type)){
			mailContent.append("，9折优惠区");
			categoryId = IapMonitorConfig.categoryId1;
			deviceId = IapMonitorConfig.deviceId1;
			mailTitle = IapMonitorConfig.mailTitle1;
		}else{
			mailContent.append("，MM币消费区");
			categoryId = IapMonitorConfig.categoryId2;
			deviceId = IapMonitorConfig.deviceId2;
			mailTitle = IapMonitorConfig.mailTitle2;
		}
		int countNum = 0;
		// 得到当前将要同步的任务
		try
		{
			countNum = IapMonitorDAO.getInstance().queryCountNumByCategoryId(categoryId);
			map = IapMonitorDAO.getInstance().queryCountNumByDeviceIds(categoryId, deviceId);
		}
		catch (DAOException e)
		{
			logger.error("重点系列指定时间（一周）内和娱乐IAP榜单数量时发生了错误 ！", e);
			return;
		}
		
		mailContent.append("适配重点平台系列数量少于").append(IapMonitorConfig.countNum)
		.append("个发出预警，其中整个区报名IAP数量为").append(countNum).append("。<br><br>");
		
		if(countNum > 0){
			String[] deviceIds = deviceId.split(",");
			StringBuffer sb = new StringBuffer();
			StringBuffer str = new StringBuffer();
			for(int i=0; i < deviceIds.length;i++){
				if(null != map && map.size() > 0){
					if(map.containsKey(deviceIds[i])){
						
						if(map.get(deviceIds[i]) < IapMonitorConfig.countNum){
							str.append(deviceIds[i]).append(",");
						}
					}else{
						sb.append("适配").append(deviceIds[i]).append("平台系列数量为0；<br><br>");
					}
				}else{
					sb.append("适配").append(deviceIds[i]).append("平台系列数量为0；<br><br>");
				}
				
			}
			if(str.length() > 0){
				String string = str.substring(0, str.length()-1);
				ArrayList<ContentVO> list = null;
				try {
					list = IapMonitorDAO.getInstance().queryContentVOByDeviceIds(categoryId,string);
				} catch (DAOException e) {
					logger.error("重点系列指定时间（一周）内和娱乐IAP榜单数量时发生了错误 ！", e);
					return;
				}
				if(null != list && list.size() > 0){
					String[] s = string.split(",");
					for(int i = 0;i  < s.length ;i++){
						sb.append("适配").append(s[i]).append("平台系列数量为").append(map.get(s[i])).append("，具体如下：");
						for(int j =0; j<list.size();j++){
							ContentVO content = list.get(j);
							if(s[i].equals(content.getDeviceId())){
								sb.append("应用ID：").append(content.getContentId()).append("，应用名称：")
								.append(content.getContentName()).append("；");
							}
						}
						sb.append("<br><br>");
					}
				}
			}
			if(sb.length() > 0){
				mailContent.append(sb.toString());
				isSendMail = true;
			}
		}else{
			isSendMail = true;
		}
		// 发送具体操作消息
		
		if(isSendMail){
			Mail.sendMail(mailTitle, mailContent.toString(), mailTo);
		}
	}
	
	public static void main(String[] args){
		StringBuffer sb = new StringBuffer();
		sb.append("skfjsdk,sdflsdkf,");
		System.out.println(sb.substring(0, sb.length()-1));
	}
}
