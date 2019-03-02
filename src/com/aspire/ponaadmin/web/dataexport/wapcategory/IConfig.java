package com.aspire.ponaadmin.web.dataexport.wapcategory;

import java.io.IOException;
import java.util.Date;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;

public interface IConfig {

	public String getFrequency();

	public String getFtpDir();

	public String getFtpFileName();

	public String getIp();

	public String getLocalDir();

	public String getLocalFileName();

	public String[] getParentId();

	public int getPort();

	public String getPwd();

	public Date getStartTime();

	public String getTemplateDir();

	public String getUser();

	public String[] getEmails();

	public String getName();

	public FTPClient getFtpClient() throws IOException, FTPException;

	public long getInterval();

	public String getAppsFtpFileName();

	public String getAppsLocalFileName();
}
