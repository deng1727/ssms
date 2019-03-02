package com.aspire.dotcard.syncAndroid.cssp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class FtpProcessor {
	private static JLogger logger = LoggerFactory.getLogger(FtpProcessor.class);

	/**
	 * 用于匹配当前任务的文件名的正则表达式
	 */
	// protected String []fNameRegex;
	/**
	 * 用于匹配当前任务的文件名的正则表达式
	 */
	protected String[] fName;

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

	FTPClient ftp = null;

	private void init() {
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"syncAndroid");
		this.ftpDir = module.getItemValue("FTPDir");
		this.localDir = module.getItemValue("localDir");
		this.ip = module.getItemValue("FTPServerIP");
		this.port = Integer.parseInt(module.getItemValue("FTPServerPort"));
		this.user = module.getItemValue("FTPServerUser");
		this.password = module.getItemValue("FTPServerPassword");

	}

	// private void reInit(){
	// this.ftpDir = "ftpumin/game";
	// this.localDir = "E:/var";
	// this.ip = "10.1.3.167";
	// this.port = 22;
	// this.user = "max";
	// this.password = "pps.167";
	// }

	public String[] getALLFileNames() {
		init();
		try {
			ftp = getFTPClient();
			return ftp.dir();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		return new String[0];// 默认值
	}

	public String[] process(String[] fileNames) throws BOException {
		try {
			// 存放获取到的文件列表的list
			ArrayList fileList = new ArrayList();
			// 取得远程目录中文件列表
			ftp = getFTPClient();
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
		// finally { //remove by aiyan 2013-01-15 希望调用完后调用close方法关闭FTP
		// if (ftp != null) {
		// try {
		// ftp.quit();
		// } catch (Exception e) {
		// }
		// }
		//
		// }
	}

	private FTPClient getFTPClient() throws IOException, FTPException {
		if (ftp == null) {

			init();
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

	// 这里要关闭本实例的ftp链接。主要要执行哦。。。add by aiyan 2013-01-15
	public void close() {
		if (ftp != null) {
			try {
				ftp.quit();
				logger.info("end a ftp instance:" + ftp);
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}
}
