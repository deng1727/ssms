package com.aspire.ponaadmin.web.newmusic139;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class NewMusic139Config
{

	private static NewMusic139Config instance = null;
	/**
	 * 日志对象。
	 */
	private static final JLogger logger = LoggerFactory
			.getLogger(NewMusic139Config.class);

	private NewMusic139Config()
	{

	}

	/**
	 * 
	 * @desc 单例 配置类，
	 * @author dongke Apr 29, 2011
	 * @return
	 */
	public static NewMusic139Config getInstance()
	{
		if (instance == null)
		{
			instance = new NewMusic139Config();
		}
		instance.initInner();
		return instance;
	}

	// 下载FTP服务器的信息
	private String sourceFTPServerIP;
	private String sourceFTPServerPort;
	private String sourceFTPServerUser;
	private String sourceFTPServerPassword;

	// 上传FTP服务器的信息
	private String destFTPServerIP;
	private String destFTPServerPort;
	private String destFTPServerUser;
	private String destFTPServerPassword;

	private String startTime;
	private String frequency;
	private String encoding;

	private String baseAlbumCategoryId; // 基础专辑货架ID
	private String baseBillboardCategoryId; // 基础榜单货架ID

	// 基地音乐新音乐接入增加字段
	private String[] mailTo;
	private String newAlbumFileName; // 专辑名称
	private String newAlbumPicUrl; // 专辑图片URL前缀 URL+filename
	private String newBillboardFileName; // 榜单文件名称
	private String newBillboardPicUrl; // 榜单图片URL前缀
	private String newKeywordFileName; // 关键字文件名
	private String newTagFileName; // 标签文件名
	private String newMusicPicUrl; // 音乐图片URL前缀
	
	private String newFileFTPDir;	//榜单，专辑，关键字，标签文件FTP路径
	
	// 专辑的图片存放
	private String newSourceAlbumFTPDir; // 专辑图片FTP地址
	// 榜单的图片存放
	private String newSourceBillboardFTPDir; // 榜单图片FTP地址
	// 音乐的图片
	private String newSourceMusicFTPDir; // 音乐图片FTP地址

	// 专辑和歌曲的图片分开存
	private String newDestAlbumFTPDir; // 专辑图片资源服务器地址
	private String newDestBillboardFTPDir; // 榜单图片资源服务器地址
	private String newdestMusicFTPDir; // 音乐图片资源服务器地址

	private String cloumsplit; // 列分隔符
	private String localDir; // 本地临时文件目录

	protected void initInner()
	{
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"newmusic139");

		this.sourceFTPServerIP = module.getItemValue("sourceFTPServerIP");
		this.sourceFTPServerPort = module.getItemValue("sourceFTPServerPort");
		this.sourceFTPServerUser = module.getItemValue("sourceFTPServerUser");
		this.sourceFTPServerPassword = module.getItemValue("sourceFTPServerPassword");

		this.destFTPServerIP = module.getItemValue("destFTPServerIP");
		this.destFTPServerPort = module.getItemValue("destFTPServerPort");
		this.destFTPServerUser = module.getItemValue("destFTPServerUser");
		this.destFTPServerPassword = module.getItemValue("destFTPServerPassword");

		this.startTime = module.getItemValue("startTime");
		this.frequency = module.getItemValue("frequency");
		this.mailTo = module.getItemValue("mailTo").split(",");
		this.encoding = module.getItemValue("encoding");

		this.baseAlbumCategoryId = module.getItemValue("baseAlbumCategoryId");
		this.baseBillboardCategoryId = module.getItemValue("baseBillboardCategoryId");

		this.newAlbumFileName = module.getItemValue("newAlbumFileName");
		this.newAlbumPicUrl = module.getItemValue("newAlbumPicUrl");
		this.newBillboardFileName = module.getItemValue("newBillboardFileName");
		this.newBillboardPicUrl = module.getItemValue("newBillboardPicUrl");
		this.newKeywordFileName = module.getItemValue("newKeywordFileName");
		this.newTagFileName = module.getItemValue("newTagFileName");
		this.newMusicPicUrl = module.getItemValue("newMusicPicUrl");

		this.newSourceAlbumFTPDir = module.getItemValue("newSourceAlbumFTPDir");
		this.newSourceBillboardFTPDir = module.getItemValue("newSourceBillboardFTPDir");
		this.newSourceMusicFTPDir = module.getItemValue("newSourceMusicFTPDir");
		this.newDestAlbumFTPDir = module.getItemValue("newDestAlbumFTPDir");
		this.newDestBillboardFTPDir = module.getItemValue("newDestBillboardFTPDir");
		this.newdestMusicFTPDir = module.getItemValue("newdestMusicFTPDir");

		this.cloumsplit = module.getItemValue("cloumsplit");
		this.localDir = module.getItemValue("localDir");
		this.newFileFTPDir = module.getItemValue("newFileFTPDir");
		/*
		 * this.albumFileName = module.getItemValue("albumFileName");
		 * this.billboardFileName = module.getItemValue("billboardFileName");
		 * this.keywordFileName = module.getItemValue("keywordFileName");
		 * this.tagFileName = module.getItemValue("tagFileName"); this.picUrl =
		 * module.getItemValue("picUrl");
		 * 
		 * this.sourceAlbumFTPDir=module.getItemValue("SourceAlbumFTPDir").trim();
		 * this.sourceMusicFTPDir=module.getItemValue("SourceMusicFTPDir").trim();
		 * this.destFTPServerPort=Integer.parseInt(module.getItemValue("DestFTPServerPort").trim());
		 * this.destFTPServerIP=module.getItemValue("DestFTPServerIP").trim();
		 * this.destFTPServerUser=module.getItemValue("DestFTPServerUser").trim();
		 * this.destFTPServerPassword=module.getItemValue("DestFTPServerPassword").trim();
		 * this.destAlbumFTPDir=module.getItemValue("DestAlbumFTPDir").trim();
		 * this.destMusicFTPDir=module.getItemValue("DestMusicFTPDir").trim();
		 * this.musicPicUrl=module.getItemValue("MusicPicUrl").trim();
		 */

	}

	/**
	 * @return Returns the destFTPServerIP.
	 */
	public String getDestFTPServerIP()
	{
		return destFTPServerIP;
	}

	/**
	 * @param destFTPServerIP
	 *            The destFTPServerIP to set.
	 */
	public void setDestFTPServerIP(String destFTPServerIP)
	{
		this.destFTPServerIP = destFTPServerIP;
	}

	/**
	 * @return Returns the destFTPServerPassword.
	 */
	public String getDestFTPServerPassword()
	{
		return destFTPServerPassword;
	}

	/**
	 * @param destFTPServerPassword
	 *            The destFTPServerPassword to set.
	 */
	public void setDestFTPServerPassword(String destFTPServerPassword)
	{
		this.destFTPServerPassword = destFTPServerPassword;
	}

	/**
	 * @return Returns the destFTPServerUser.
	 */
	public String getDestFTPServerUser()
	{
		return destFTPServerUser;
	}

	/**
	 * @param destFTPServerUser
	 *            The destFTPServerUser to set.
	 */
	public void setDestFTPServerUser(String destFTPServerUser)
	{
		this.destFTPServerUser = destFTPServerUser;
	}

	/**
	 * @return Returns the frequency.
	 */
	public String getFrequency()
	{
		return frequency;
	}

	/**
	 * @param frequency
	 *            The frequency to set.
	 */
	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}

	

	/**
	 * @return Returns the mailTo.
	 */
	public String[] getMailTo()
	{
		return mailTo;
	}

	/**
	 * @param mailTo The mailTo to set.
	 */
	public void setMailTo(String[] mailTo)
	{
		this.mailTo = mailTo;
	}

	/**
	 * @return Returns the newAlbumFileName.
	 */
	public String getNewAlbumFileName()
	{
		return newAlbumFileName;
	}

	/**
	 * @param newAlbumFileName
	 *            The newAlbumFileName to set.
	 */
	public void setNewAlbumFileName(String newAlbumFileName)
	{
		this.newAlbumFileName = newAlbumFileName;
	}

	/**
	 * @return Returns the newAlbumPicUrl.
	 */
	public String getNewAlbumPicUrl()
	{
		return newAlbumPicUrl;
	}

	/**
	 * @param newAlbumPicUrl
	 *            The newAlbumPicUrl to set.
	 */
	public void setNewAlbumPicUrl(String newAlbumPicUrl)
	{
		this.newAlbumPicUrl = newAlbumPicUrl;
	}

	/**
	 * @return Returns the newBillboardFileName.
	 */
	public String getNewBillboardFileName()
	{
		return newBillboardFileName;
	}

	/**
	 * @param newBillboardFileName
	 *            The newBillboardFileName to set.
	 */
	public void setNewBillboardFileName(String newBillboardFileName)
	{
		this.newBillboardFileName = newBillboardFileName;
	}

	/**
	 * @return Returns the newBillboardPicUrl.
	 */
	public String getNewBillboardPicUrl()
	{
		return newBillboardPicUrl;
	}

	/**
	 * @param newBillboardPicUrl
	 *            The newBillboardPicUrl to set.
	 */
	public void setNewBillboardPicUrl(String newBillboardPicUrl)
	{
		this.newBillboardPicUrl = newBillboardPicUrl;
	}

	/**
	 * @return Returns the newDestAlbumFTPDir.
	 */
	public String getNewDestAlbumFTPDir()
	{
		return newDestAlbumFTPDir;
	}

	/**
	 * @param newDestAlbumFTPDir
	 *            The newDestAlbumFTPDir to set.
	 */
	public void setNewDestAlbumFTPDir(String newDestAlbumFTPDir)
	{
		this.newDestAlbumFTPDir = newDestAlbumFTPDir;
	}

	/**
	 * @return Returns the newDestBillboardFTPDir.
	 */
	public String getNewDestBillboardFTPDir()
	{
		return newDestBillboardFTPDir;
	}

	/**
	 * @param newDestBillboardFTPDir
	 *            The newDestBillboardFTPDir to set.
	 */
	public void setNewDestBillboardFTPDir(String newDestBillboardFTPDir)
	{
		this.newDestBillboardFTPDir = newDestBillboardFTPDir;
	}

	/**
	 * @return Returns the newdestMusicFTPDir.
	 */
	public String getNewdestMusicFTPDir()
	{
		return newdestMusicFTPDir;
	}

	/**
	 * @param newdestMusicFTPDir
	 *            The newdestMusicFTPDir to set.
	 */
	public void setNewdestMusicFTPDir(String newdestMusicFTPDir)
	{
		this.newdestMusicFTPDir = newdestMusicFTPDir;
	}

	/**
	 * @return Returns the newKeywordFileName.
	 */
	public String getNewKeywordFileName()
	{
		return newKeywordFileName;
	}

	/**
	 * @param newKeywordFileName
	 *            The newKeywordFileName to set.
	 */
	public void setNewKeywordFileName(String newKeywordFileName)
	{
		this.newKeywordFileName = newKeywordFileName;
	}

	/**
	 * @return Returns the newMusicPicUrl.
	 */
	public String getNewMusicPicUrl()
	{
		return newMusicPicUrl;
	}

	/**
	 * @param newMusicPicUrl
	 *            The newMusicPicUrl to set.
	 */
	public void setNewMusicPicUrl(String newMusicPicUrl)
	{
		this.newMusicPicUrl = newMusicPicUrl;
	}

	/**
	 * @return Returns the newSourceAlbumFTPDir.
	 */
	public String getNewSourceAlbumFTPDir()
	{
		return newSourceAlbumFTPDir;
	}

	/**
	 * @param newSourceAlbumFTPDir
	 *            The newSourceAlbumFTPDir to set.
	 */
	public void setNewSourceAlbumFTPDir(String newSourceAlbumFTPDir)
	{
		this.newSourceAlbumFTPDir = newSourceAlbumFTPDir;
	}

	/**
	 * @return Returns the newSourceBillboardFTPDir.
	 */
	public String getNewSourceBillboardFTPDir()
	{
		return newSourceBillboardFTPDir;
	}

	/**
	 * @param newSourceBillboardFTPDir
	 *            The newSourceBillboardFTPDir to set.
	 */
	public void setNewSourceBillboardFTPDir(String newSourceBillboardFTPDir)
	{
		this.newSourceBillboardFTPDir = newSourceBillboardFTPDir;
	}

	/**
	 * @return Returns the newSourceMusicFTPDir.
	 */
	public String getNewSourceMusicFTPDir()
	{
		return newSourceMusicFTPDir;
	}

	/**
	 * @param newSourceMusicFTPDir
	 *            The newSourceMusicFTPDir to set.
	 */
	public void setNewSourceMusicFTPDir(String newSourceMusicFTPDir)
	{
		this.newSourceMusicFTPDir = newSourceMusicFTPDir;
	}

	/**
	 * @return Returns the newTagFileName.
	 */
	public String getNewTagFileName()
	{
		return newTagFileName;
	}

	/**
	 * @param newTagFileName
	 *            The newTagFileName to set.
	 */
	public void setNewTagFileName(String newTagFileName)
	{
		this.newTagFileName = newTagFileName;
	}

	/**
	 * @return Returns the sourceFTPServerIP.
	 */
	public String getSourceFTPServerIP()
	{
		return sourceFTPServerIP;
	}

	/**
	 * @param sourceFTPServerIP
	 *            The sourceFTPServerIP to set.
	 */
	public void setSourceFTPServerIP(String sourceFTPServerIP)
	{
		this.sourceFTPServerIP = sourceFTPServerIP;
	}

	/**
	 * @return Returns the sourceFTPServerPassword.
	 */
	public String getSourceFTPServerPassword()
	{
		return sourceFTPServerPassword;
	}

	/**
	 * @param sourceFTPServerPassword
	 *            The sourceFTPServerPassword to set.
	 */
	public void setSourceFTPServerPassword(String sourceFTPServerPassword)
	{
		this.sourceFTPServerPassword = sourceFTPServerPassword;
	}

	/**
	 * @return Returns the destFTPServerPort.
	 */
	public String getDestFTPServerPort()
	{
		return destFTPServerPort;
	}

	/**
	 * @param destFTPServerPort
	 *            The destFTPServerPort to set.
	 */
	public void setDestFTPServerPort(String destFTPServerPort)
	{
		this.destFTPServerPort = destFTPServerPort;
	}

	/**
	 * @return Returns the sourceFTPServerPort.
	 */
	public String getSourceFTPServerPort()
	{
		return sourceFTPServerPort;
	}

	/**
	 * @param sourceFTPServerPort
	 *            The sourceFTPServerPort to set.
	 */
	public void setSourceFTPServerPort(String sourceFTPServerPort)
	{
		this.sourceFTPServerPort = sourceFTPServerPort;
	}

	/**
	 * @return Returns the sourceFTPServerUser.
	 */
	public String getSourceFTPServerUser()
	{
		return sourceFTPServerUser;
	}

	/**
	 * @param sourceFTPServerUser
	 *            The sourceFTPServerUser to set.
	 */
	public void setSourceFTPServerUser(String sourceFTPServerUser)
	{
		this.sourceFTPServerUser = sourceFTPServerUser;
	}

	/**
	 * @return Returns the startTime.
	 */
	public String getStartTime()
	{
		return startTime;
	}

	/**
	 * @param startTime
	 *            The startTime to set.
	 */
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * @return Returns the cloumsplit.
	 */
	public String getCloumsplit()
	{
		return cloumsplit;
	}

	/**
	 * @param cloumsplit
	 *            The cloumsplit to set.
	 */
	public void setCloumsplit(String cloumsplit)
	{
		this.cloumsplit = cloumsplit;
	}

	/**
	 * @return Returns the localDir.
	 */
	public String getLocalDir()
	{
		return localDir;
	}

	/**
	 * @param localDir
	 *            The localDir to set.
	 */
	public void setLocalDir(String localDir)
	{
		this.localDir = localDir;
	}

	/**
	 * @return Returns the encoding.
	 */
	public String getEncoding()
	{
		return encoding;
	}

	/**
	 * @param encoding
	 *            The encoding to set.
	 */
	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}

	/**
	 * @return Returns the baseAlbumCategoryId.
	 */
	public String getBaseAlbumCategoryId()
	{
		return baseAlbumCategoryId;
	}

	/**
	 * @param baseAlbumCategoryId The baseAlbumCategoryId to set.
	 */
	public void setBaseAlbumCategoryId(String baseAlbumCategoryId)
	{
		this.baseAlbumCategoryId = baseAlbumCategoryId;
	}

	/**
	 * @return Returns the baseBillboardCategoryId.
	 */
	public String getBaseBillboardCategoryId()
	{
		return baseBillboardCategoryId;
	}

	/**
	 * @param baseBillboardCategoryId The baseBillboardCategoryId to set.
	 */
	public void setBaseBillboardCategoryId(String baseBillboardCategoryId)
	{
		this.baseBillboardCategoryId = baseBillboardCategoryId;
	}

	/**
	 * @return Returns the newFileFTPDir.
	 */
	public String getNewFileFTPDir()
	{
		return newFileFTPDir;
	}

	/**
	 * @param newFileFTPDir The newFileFTPDir to set.
	 */
	public void setNewFileFTPDir(String newFileFTPDir)
	{
		this.newFileFTPDir = newFileFTPDir;
	}

}
