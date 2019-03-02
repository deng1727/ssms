package com.aspire.ponaadmin.web.dataexport.wapcategory;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class FileConfigImpl implements IConfig {
	private static final JLogger logger = LoggerFactory
			.getLogger(FileConfigImpl.class);

	protected String startTime;

	protected String ip;

	protected int port;

	protected String user;

	protected String pwd;

	protected String ftpDir; // ftp服务器中存储excel的目录

	protected String localDir; // 本地生成excel存储的目录

	protected String templateDir; // 本地生成excel的模板文件路径(带文件名)

	protected String frequency;// 周期，每日/每周。

	private String parentId;

	private String localFileName;

	private String ftpFileName;

	protected String emails;

	protected String name;

	private String appsLocalFileName;

	private String appsFtpFileName;

	protected static final Hashtable HT = new Hashtable();

	public FileConfigImpl(String name) {
		this.name = name;
	}
	
	protected ModuleConfig module;

	protected void init(String name) {
		module = ConfigFactory.getSystemConfig().getModuleConfig(
				name);
		try{
			ip = module.getItemValue("FTPServerIP").trim();
			this.port = Integer.parseInt(module.getItemValue("FTPServerPort")
					.trim());
			this.user = module.getItemValue("FTPServerUser").trim();
			this.pwd = module.getItemValue("FTPServerPassword").trim();
			this.startTime = module.getItemValue("startTime").trim();
			this.ftpDir = module.getItemValue("FTPDir").trim();
			this.localDir = module.getItemValue("LocalDir").trim();
			this.frequency = module.getItemValue("frequency").trim();
			this.emails = module.getItemValue("mailTo");
			this.ftpFileName = module.getItemValue("ftpFileName");
			this.parentId = module.getItem("parentCategoryId") == null ? "-1"
					: module.getItemValue("parentCategoryId");
			this.localFileName = module.getItemValue("localFileName");
			this.appsLocalFileName = module.getItemValue("appsLocalFileName");
			this.appsFtpFileName = module.getItemValue("appsFtpFileName");
		}catch(NullPointerException e){
			
		}
		
	}

	public synchronized static IConfig getInstance(String name) {
		if (HT.get(name) == null) {
			FileConfigImpl config = new FileConfigImpl(name);
			config.init(name);
			HT.put(name, config);
		}
		return (IConfig) HT.get(name);
	}

	public FTPClient getFtpClient() throws IOException, FTPException {
		FTPClient ftp = null;
		try {
			ftp = new FTPClient(this.ip, this.port);

			// 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
			ftp.setConnectMode(FTPConnectMode.PASV);
			ftp.login(this.user, this.pwd);
			// 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
			ftp.setType(FTPTransferType.BINARY);
			ftp.chdir(this.ftpDir);
		} catch (IOException e) {
			logger.error("连接FTP服务器出错", e);
			throw e;
		} catch (FTPException e) {
			logger.error("连接FTP服务器出错", e);
			throw e;
		}
		return ftp;
	}

	public String[] getEmails() {
		if (this.emails == null) {
			return null;
		}
		return this.emails.split(",");
	}

	public String getFrequency() {
		return this.frequency;
	}

	public String getFtpDir() {
		return this.ftpDir;
	}

	public String getFtpFileName() {
		return this.ftpFileName;
	}

	public String getName() {
		return name;
	}

	public String getIp() {
		return this.ip;
	}

	public String getLocalDir() {
		return this.localDir;
	}

	public String getLocalFileName() {
		return this.localFileName;
	}

	public String[] getParentId() {
		if (this.parentId == null || this.parentId.equals("-1")) {
			return null;
		}

		return this.parentId.split(",");
	}

	public int getPort() {

		return this.port;
	}

	public String getPwd() {

		return this.pwd;
	}

	public Date getStartTime() {
		// 系统默认的基地音乐同步执行时间 05:00
		int hour = 5;
		int minute = 0;
		// 从配置项中获取配置值
		try {
			String rbStartTime = this.startTime;
			hour = Integer.parseInt(rbStartTime.split(":")[0]);
			minute = Integer.parseInt(rbStartTime.split(":")[1]);
		} catch (Exception ex) {
			logger.error("从配置项中获取基地图书同步的时间AppTopStarttime解析出错！");
		}
		if (hour < 0 || hour > 23) {
			// 配置值不正确，就使用默认值
			hour = 5;
		}
		if (minute < 0 || minute > 59) {
			// 配置值不正确，就使用默认值
			minute = 0;
		}
		Date firstStartTime = null;
		try {
			// 取当前时间
			Date date = new Date();
			// 构造第一次任务运行时间
			firstStartTime = getOneTimeByHourAndMinute(date, hour, minute);
			// 如果第一次运行时间早于当前时间，需要把第一次运行时间加一天
			if (firstStartTime.before(date)) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				Date tommorrow = calendar.getTime();
				firstStartTime = getOneTimeByHourAndMinute(tommorrow, hour,
						minute);
			}
		} catch (Exception ex) {
			// 设置时间有异常，就用当前的时间吧，不过如果程序没有bug，这个异常应该是不可能出现的
			firstStartTime = new Date();
		}
		return firstStartTime;
	}

	public String getTemplateDir() {

		return this.templateDir;
	}

	public String getUser() {

		return this.user;
	}

	public long getInterval() {
		if ("week".equals(this.frequency)) {
			return 7 * 24 * 60 * 60 * 1000l;
		}
		return 24 * 60 * 60 * 1000l;
	}

	private static Date getOneTimeByHourAndMinute(Date date, int hour,
			int minute) {
		String c = DateUtil.formatDate(date, "yyyyMMdd");
		if (hour < 10) {
			c = c + '0';
		}
		c = c + hour + minute + "00";
		return DateUtil.stringToDate(c, "yyyyMMddHHmmss");
	}

	public String getAppsFtpFileName() {
		return appsFtpFileName;
	}

	public String getAppsLocalFileName() {
		return appsLocalFileName;
	}

}
