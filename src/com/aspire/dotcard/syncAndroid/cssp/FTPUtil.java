package com.aspire.dotcard.syncAndroid.cssp;

import java.io.File;
import java.util.ArrayList;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

public class FTPUtil {
	private static JLogger logger = LoggerFactory.getLogger(FTPUtil.class);

	FTPClient ftp = null;

	/**
	 * 数据文件存放目录
	 */
	protected String ftpDir;

	/**
	 * 保存本地数据文件的绝对目录
	 */
	protected String localDir;

	/**
	 * 以下是ftp登录的配置信息
	 */
	private String ip;
	private int port;
	private String user;
	private String password;

	public FTPUtil(String ftpDir, String localDir, String ip, int port,
			String user, String password) {
		//this.ftpDir = "aiyan/others/down_log/syncFiles";
		this.ftpDir = ftpDir;//"aiyan/others/down_log";
		this.localDir = localDir;//"E:/var";
		this.ip = ip;//"10.1.3.167";
		this.port = port;//22;
		this.user = user;//"max";
		this.password = password;//"pps.167";

	}
	
	public boolean getFTP() {
		boolean flag = false;
		try {
			startFTP();
			flag = true;
		} catch (Exception e) {
			flag = false;
			logger.error("获取FTP连接出错！", e);
		}
		return flag;
	}

	public void startFTP() throws Exception {
		ftp = getFTPClient();

	}

	public void closeFTP() {
		if (ftp != null) {
			try {
				ftp.quit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("关闭FTP的时候出错！", e);
			}
		}
	}

	public String[] getFTPFileNames() {
		try {
			return ftp.dir();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		return new String[0];// 默认值
	}

	public String[] getFTPFileNames(MyFilenameFilter filter) {
		String names[] = getFTPFileNames();
		if ((names == null) || (filter == null)) {
			return names;
		}
		ArrayList<String> v = new ArrayList<String>();
		for (int i = 0; i < names.length; i++) {
			if (filter.accept(names[i])) {
				v.add(names[i]);
			}
		}
		return (String[]) (v.toArray(new String[v.size()]));
	}

	public String[] process(String[] fileNames) throws BOException {
		try {
			// 存放获取到的文件列表的list
			ArrayList<String>  fileList = new ArrayList<String>();
			// 取得远程目录中文件列表
			for (int j = 0; j < fileNames.length; j++) {
				String RemotefileName = fileNames[j];
				String absolutePath = localDir + File.separator
						+ RemotefileName;
				absolutePath = absolutePath.replace('\\', '/');

				ftp.get(absolutePath, fileNames[j]);
				fileList.add(absolutePath);
				if (logger.isDebugEnabled()) {
					logger.debug("成功下载文件：" + absolutePath);
				}
			}

			String handleFileNames[] = new String[fileList.size()];
			return (String[]) fileList.toArray(handleFileNames);
		} catch (Exception e) {
			throw new BOException("下载文件出错！", e);
		}
	}

	private FTPClient getFTPClient() throws Exception {
		if (ftp == null) {
			ftp = new FTPClient(ip, port);
			// 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
			ftp.setConnectMode(FTPConnectMode.PASV);

			// 使用给定的用户名、密码登陆ftp
			if (logger.isDebugEnabled()) {
				logger.debug("login to FTPServer...");
			}
			ftp.login(user, password);
			// 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
			ftp.setType(FTPTransferType.BINARY);
			if (logger.isDebugEnabled()) {
				logger
						.debug("login FTPServer successfully,transfer type is binary");
			}

			if (!"".equals(ftpDir)) {
				ftp.chdir(ftpDir);
			}
		}
		logger.info("begin a ftp instance:" + ftp);
		return ftp;
	}

}
