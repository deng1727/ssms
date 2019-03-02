package com.aspire.ponaadmin.web.dissertation;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;

public class DisserConfig {
	private DisserConfig(){
		init();
	}
	public static DisserConfig getInstance(){
		return new DisserConfig();
	}
	private void init(){
		ModuleConfig config=ConfigFactory.getSystemConfig().getModuleConfig("DISSERTATION");
		this.ip=config.getItemValue("FTPServerIP");
		this.port=Integer.parseInt(config.getItemValue("FTPServerPort"));
		this.user=config.getItemValue("FTPServerUser");
		this.pwd=config.getItemValue("FTPServerPassword");
		this.ftpDir=config.getItemValue("FTPDir");
		this.localDir=config.getItemValue("LocalDir");
		this.picUrl=config.getItemValue("PicUrl");
		config=null;
	}
	private String ip;

	private int port;

	private String user;

	private String pwd;

	private String ftpDir; // ftp服务器中目录

	private String localDir; // 本地生成的目录
	
	private String picUrl;
	
	public String getPicUrl() {
		return picUrl;
	}
	public String getFtpDir() {
		return ftpDir;
	}

	public String getIp() {
		return ip;
	}

	public String getLocalDir() {
		return localDir;
	}

	public int getPort() {
		return port;
	}

	public String getPwd() {
		return pwd;
	}

	public String getUser() {
		return user;
	}
	
	
}
