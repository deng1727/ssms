package com.aspire.ponaadmin.web.dataexport.sqlexport.VO;

import java.io.IOException;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class DataExportFTPVO
{	
	private String ftpId;
	
	private String ftpIp;
	
	private String ftpPort;
	
	private String ftpName;
	
	private String ftpPassWord;
	
	private String ftpPath;

	public String getFtpId()
	{
		return ftpId;
	}

	public void setFtpId(String ftpId)
	{
		this.ftpId = ftpId;
	}

	public String getFtpIp()
	{
		return ftpIp;
	}

	public void setFtpIp(String ftpIp)
	{
		this.ftpIp = ftpIp;
	}

	public String getFtpPort()
	{
		return ftpPort;
	}

	public void setFtpPort(String ftpPort)
	{
		this.ftpPort = ftpPort;
	}

	public String getFtpName()
	{
		return ftpName;
	}

	public void setFtpName(String ftpName)
	{
		this.ftpName = ftpName;
	}

	public String getFtpPassWord()
	{
		return ftpPassWord;
	}

	public void setFtpPassWord(String ftpPassWord)
	{
		this.ftpPassWord = ftpPassWord;
	}

	public String getFtpPath()
	{
		return ftpPath;
	}

	public void setFtpPath(String ftpPath)
	{
		this.ftpPath = ftpPath;
	}
	
    public FTPClient getFTPClient() throws IOException, FTPException
    {
        String ip = this.ftpIp;
        int port = Integer.parseInt(this.ftpPort);
        String user = this.ftpName;
        String password = this.ftpPassWord;

        FTPClient ftp = new FTPClient(ip, port);
        // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
        ftp.setConnectMode(FTPConnectMode.PASV);
        // ʹ�ø������û����������½ftp
        ftp.login(user, password);
        // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
        ftp.setType(FTPTransferType.BINARY);

        return ftp;
    }
}
