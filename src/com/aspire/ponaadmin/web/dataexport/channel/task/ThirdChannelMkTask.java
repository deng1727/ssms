package com.aspire.ponaadmin.web.dataexport.channel.task;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.channel.ChannelConfig;
import com.aspire.ponaadmin.web.dataexport.channel.ZipUtil;
import com.aspire.ponaadmin.web.dataexport.channel.bo.ThirdChannelMkBo;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.DateUtil;

public class ThirdChannelMkTask extends TimerTask {
	protected static JLogger logger = LoggerFactory.getLogger(ThirdChannelMkTask.class);
	/**
	 * 运行压缩任务
	 */
	public void run() {
		
		//取配置
		logger.debug("开始导出第三方渠道营销数据");
		StringBuffer sb = new StringBuffer();
		String toDay = DateUtil.formatDate(new Date(), "yyyyMMdd");
		String localDir = ChannelConfig.LOCALDIR+File.separator+toDay;
		File f = new File(localDir);
		if(!f.exists()){
			f.mkdirs();
		}
		String begin = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		//导出WWW最新 应用数据信息
		ThirdChannelMkBo.getInstance().exportWwwZx(sb,localDir);
				
		//导出WWW最热 应用数据信息
		ThirdChannelMkBo.getInstance().exportWwwZr(sb,localDir);
		
		//导出WWW小编推荐  应用数据信息
		ThirdChannelMkBo.getInstance().exportWwwXbtj(sb,localDir);
		
		//导出WAP最新 应用数据信息
		ThirdChannelMkBo.getInstance().exportWapZx(sb,localDir);
		
		//导出WAP最热 应用数据信息
		ThirdChannelMkBo.getInstance().exportWapZr(sb,localDir);
		
		//导出WAP小编推荐  应用数据信息
		ThirdChannelMkBo.getInstance().exportWapXbtj(sb,localDir);
		
		//列出文件，压缩
		File[] filese = f.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if(pathname.getName().toLowerCase().endsWith(".csv")){
					return true;
				}
				return false;
			}
		});
		String end = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		logger.debug("导出第三方渠道营销数据开始时间："+begin+"，结束时间："+end);
		StringBuffer mailContent = new StringBuffer();
		mailContent.append("导出第三方渠道营销数据开始时间："+begin+"，结束时间："+end);
		mailContent.append("<br/>");
		mailContent.append(sb.toString());
		String mailTitle = "第三方渠道营销数据导出结果";		
		File zipFile  = null;
		if(filese.length!=0){
			//压缩
			zipFile = new File(localDir+File.separator+toDay+".zip");
			ZipUtil.compress(zipFile, localDir,"*.csv");
		
			Mail.sendMail(mailTitle, mailContent.toString(), ChannelConfig.mailTo, new File[]{zipFile});
		}else{
			//无附件
			Mail.sendMail(mailTitle, mailContent.toString(), ChannelConfig.mailTo);
		}
		logger.debug("结束导出第三方渠道营销数据");
	}

}
