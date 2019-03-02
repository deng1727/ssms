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
	 * ����ƥ�䵱ǰ������ļ�����������ʽ
	 */
	// protected String []fNameRegex;
	/**
	 * ����ƥ�䵱ǰ������ļ�����������ʽ
	 */
	protected String[] fName;

	/**
	 * �����ļ����Ŀ¼
	 */
	protected String ftpDir;

	/**
	 * ���汾�������ļ��ľ���Ŀ¼
	 */
	protected String localDir;

	/**
	 * ������ftp��¼��������Ϣ
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
		return new String[0];// Ĭ��ֵ
	}

	public String[] process(String[] fileNames) throws BOException {
		try {
			// ��Ż�ȡ�����ļ��б��list
			ArrayList fileList = new ArrayList();
			// ȡ��Զ��Ŀ¼���ļ��б�
			ftp = getFTPClient();
			for (int j = 0; j < fileNames.length; j++) {
				String RemotefileName = fileNames[j];
				String absolutePath = localDir + File.separator
						+ RemotefileName;
				absolutePath = absolutePath.replace('\\', '/');
				ftp.get(absolutePath, fileNames[j]);
				fileList.add(absolutePath);
				if (logger.isDebugEnabled()) {
					logger.debug("�ɹ������ļ���" + absolutePath);
				}
			}

			String handleFileNames[] = new String[fileList.size()];
			return (String[]) fileList.toArray(handleFileNames);
		} catch (Exception e) {
			throw new BOException("�����ļ�����", e);
		}
		// finally { //remove by aiyan 2013-01-15 ϣ������������close�����ر�FTP
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
			// ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
			ftp.setConnectMode(FTPConnectMode.PASV);

			// ʹ�ø������û����������½ftp
			if (logger.isDebugEnabled()) {
				logger.debug("login to FTPServer...");
			}
			ftp.login(user, password);
			// �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
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

	// ����Ҫ�رձ�ʵ����ftp���ӡ���ҪҪִ��Ŷ������add by aiyan 2013-01-15
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
