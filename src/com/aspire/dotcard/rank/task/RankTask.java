package com.aspire.dotcard.rank.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.db.DAOException;
import com.aspire.common.ftpfile.SFTPServer;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.StringUtils;
import com.aspire.dotcard.basecolorcomic.conf.BaseColorComicConfig;
import com.aspire.dotcard.rank.bo.RankBo;
import com.aspire.dotcard.rank.dao.RankDao;
import com.aspire.dotcard.rank.vo.RankVo;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;

public class RankTask extends TimerTask {
	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory.getLogger(RankTask.class);

	private static final String DIR = getConfigValue("dir");
	private String PASSWORD = getConfigValue("password");
	private static final String USERNAME = getConfigValue("username");
	private static final String IP = getConfigValue("ip");
	private static final String LOCALPATH = getConfigValue("localPath");// 文件存放的货架文件
	private static final String REMOTEFILE = getConfigValue("remoteFile");
	private static final String RANKCONFIG = "rankConfig";
	
	@Override
	public void run() {
		int date =Integer.valueOf(DateUtil.formatDate(new Date(), "yyyyMMdd"))-1;
		logger.debug("获取到的文件日期为:"+date);
		String remoteFile = REMOTEFILE + "_"
				+ date + ".txt";
		List<RankVo> vos = getVosFromFile(remoteFile);
		logger.debug("文件查出的vos有:"+vos.size()+"条");
		try {
			RankBo.getInstance().insertVos(vos);
		} catch (DAOException e) {
			logger.error("插入vos失败....", e);
		}
	}

	private static String getConfigValue(String value) {
		return ConfigFactory.getSystemConfig().getModuleConfig(RANKCONFIG)
				.getItemValue(value);
	}
	
	public SFTPServer getSFTPClient() throws JSchException
	{
		String pwd =PASSWORD.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		SFTPServer server = new SFTPServer(IP, USERNAME, pwd, 22);
		if (logger.isDebugEnabled())
		{
			logger
					.debug("login SFTPServer successfully,transfer type is binary");
		}
		return server;
	}


	private void getFileFromFtp(String remoteFile) {
		
		SFTPServer server = null;
		ChannelSftp sftp = null;
		try
		{
			server = this.getSFTPClient();
			sftp = server.login();
			sftp.cd(DIR);
			SFTPServer.downloadSingleFile(LOCALPATH+remoteFile, DIR+remoteFile,
					sftp);
		}
		catch (Exception e)
		{
			logger.error(e);
		}finally{
			server.close(sftp);
			sftp.disconnect();
			server.disconnect();
		}
	}

	private List<RankVo> getVosFromFile(String remoteFile) {

		getFileFromFtp(remoteFile);

		File file = new File(LOCALPATH + remoteFile);
		List<RankVo> vos = null;
		RankVo vo;
		int count = 0 ;
		try {
			vos = new ArrayList<RankVo>();
			InputStream is = new FileInputStream(file);
			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					is,"UTF-8")); 
			String line = "";
			while ((line = bReader.readLine()) != null) {
				String[] str = line.split("\\|\\|");
				int length =str.length;
				logger.debug("每行有"+length+"个参数");
				vo = new RankVo();
					vo.setStat_time(Integer.valueOf(str[0]));
					vo.setContent_id(str[1]);
					vo.setContent_name(str[2]);
					vo.setPrd_type_id(str[3]);
					vo.setAdd_dl_cnt(Float.valueOf(str[4]));
					vo.setAdd_fee(Float.valueOf(str[5]));
					vo.setDl_15days_cnt(Float.valueOf(str[6]));
					vo.setView_7days_cnt(Float.valueOf(str[7]));
					vo.setDl_7days_cnt(Float.valueOf(str[8]));
					vo.setFee_7days_cnt(Float.valueOf(str[9]));
					vo.setClass_dl_cnt(Float.valueOf(str[10]));
					vo.setSearch_dl_cnt(Float.valueOf(str[11]));
					vo.setHot_dl_cnt(Float.valueOf(str[12]));
					vo.setMan_dl_cnt(Float.valueOf(str[13]));
					vo.setDl_cnt(Float.valueOf(str[14]));
					vo.setView_cnt(Float.valueOf(str[15]));
					vo.setFee(Float.valueOf(str[16]));
					vo.setFee_cnt(Float.valueOf(str[17]));
					vo.setCom_cnt(Float.valueOf(str[18]));
					vo.setClass_dl_cnt1(Float.valueOf(str[19]));
					vo.setSearch_dl_cnt1(Float.valueOf(str[20]));
					vo.setHot_dl_cnt1(Float.valueOf(str[21]));
					vo.setMan_dl_cnt1(Float.valueOf(str[22]));
					vo.setFee1(Float.valueOf(str[23]));
					vo.setCom_cnt1(Float.valueOf(str[24]));
					vo.setClass_dl_cnt2(Float.valueOf(str[25]));
					vo.setSearch_dl_cnt2(Float.valueOf(str[26]));
					vo.setHot_dl_cnt2(Float.valueOf(str[27]));
					vo.setMan_dl_cnt2(Float.valueOf(str[28]));
					vo.setFee2(Float.valueOf(str[29]));
					vo.setCom_cnt2(Float.valueOf(str[30]));
					vos.add(vo);
				}
		} catch (Exception e) {
			logger.error("读取文件出错!", e);
		}
		return vos;
	}
	

}
