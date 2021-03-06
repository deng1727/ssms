package com.aspire.dotcard.baseread;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.config.BaseReadConfig;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class BaseReadFtpProcess {
	private static JLogger logger = LoggerFactory
			.getLogger(BaseReadFtpProcess.class);

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

	public void init() {
		// this.fNameRegex =BaseMusicConfig.get("fNameRegex").split(",");
		// this.fName=new String[fNameRegex.length];
		this.ftpDir = BaseReadConfig.get("FTPDir");
		this.localDir = BaseReadConfig.get("localDir");
		this.ip = BaseReadConfig.get("FTPServerIP");
		this.port = Integer.parseInt(BaseReadConfig.get("FTPServerPort"));
		this.user = BaseReadConfig.get("FTPServerUser");
		this.password = BaseReadConfig.get("FTPServerPassword");

	}

	public String[] process(String[] fNameRegex) throws BOException {
		this.init();
		FTPClient ftp = null;
		this.fName = new String[fNameRegex.length];
		try {
			// 用于输出debug日志，保存本次任务同步的文件名
			String debugNameRegex = "";
			// 确定当前任务要同步的文件名
			for (int i = 0; i < fNameRegex.length; i++) {
				fName[i] = parseFileName(fNameRegex[i]);
				debugNameRegex += fName[i] + ",";
			}
			// 去除最后一个逗号
			debugNameRegex = debugNameRegex.substring(0, debugNameRegex
					.length() - 1);
			// 先确保本地目录已经创建了。
			IOUtil.checkAndCreateDir(this.localDir);

			// 存放获取到的文件列表的list
			ArrayList fileList = new ArrayList();

			// 取得远程目录中文件列表
			ftp = getFTPClient();
			if (!"".equals(ftpDir)) {
				ftp.chdir(ftpDir);
			}
			String[] Remotefiles = ftp.dir();
			// 根据正则表达式来获取匹配的文件，并保存。
			if (logger.isDebugEnabled()) {
				logger.debug("匹配文件名开始：" + debugNameRegex);
			}
			for (int j = 0; j < Remotefiles.length; j++) {
				String RemotefileName = Remotefiles[j];
				// Remotefiles[j].substring(Remotefiles[j].lastIndexOf("/") +
				// 1);
				// if (RemotefileName.matches(fNameRegex))
				if (isMatchFileName(RemotefileName)) {
					String absolutePath = localDir + File.separator
							+ RemotefileName;
					absolutePath = absolutePath.replace('\\', '/');
					ftp.get(absolutePath, Remotefiles[j]);
					fileList.add(absolutePath);
					if (logger.isDebugEnabled()) {
						logger.debug("成功下载文件：" + absolutePath);
					}
				}
			}

			String fileName[] = new String[fileList.size()];
			return (String[]) fileList.toArray(fileName);
		} catch (Exception e) {
			throw new BOException(e, 1);
		} finally {
			if (ftp != null) {
				try {
					ftp.quit();
				} catch (Exception e) {
				}
			}

		}
	}

	private FTPClient getFTPClient() throws IOException, FTPException {
		FTPClient ftp = new FTPClient(ip, port);
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
		return ftp;
	}

	/**
	 * 解析含有日期字符的文件名正则表达式，日期是以~d开始，以~结束
	 * 
	 * @param fNameRegex
	 * @return
	 */
	private String parseFileName(String fNameRegex) {
		if (fNameRegex == null) {
			return "";//
		}
		StringBuffer dateCharSequence = new StringBuffer();
		boolean dStart = false;
		boolean dEnd = false;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fNameRegex.length(); i++) {
			char c = fNameRegex.charAt(i);
			if (c == '~' && fNameRegex.charAt(i + 1) == 'D')// 只有字符为~D为开头的字符才表示日期
			{
				dStart = true;
				i++;// 需要跳过下一个字符
				continue;
			} else if (dStart && c == '~')// 匹配日期特殊字符结束。
			{
				Calendar nowTime=Calendar.getInstance();
				int index=dateCharSequence.indexOf("[");//查看是否有对日期的调整
				if(index!=-1)
				{
					int difference=Integer.parseInt(dateCharSequence.subSequence(index+1, dateCharSequence.lastIndexOf("]")).toString());
					nowTime.add(Calendar.DAY_OF_MONTH,difference);
					dateCharSequence.delete(index, dateCharSequence.length());
				}
				String date = PublicUtil.getDateString(nowTime.getTime(), dateCharSequence.toString());
				//getCurDateTime(dateCharSequence.toString());

				sb.append(date);
				dEnd = true;
				continue;
			}

			if (dStart && !dEnd)// 特殊日期字符
			{
				dateCharSequence.append(c);
			} else
			// 添加非特殊日期字符
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 判读是否是本次任务需要下载的文件名,支持多个文件，多个文件以英文逗号分隔
	 * 
	 * @param fName
	 *            当前ftp服务器上的文件
	 * @return true 是，false 否
	 */
	protected boolean isMatchFileName(String FtpFName) {

		for (int i = 0; i < fName.length; i++) {
			if (FtpFName.matches(fName[i])) {
				return true;
			}
		}
		return false;
	}

}
