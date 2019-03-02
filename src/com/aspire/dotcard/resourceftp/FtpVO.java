/**
 * 
 */
package com.aspire.dotcard.resourceftp;

import com.enterprisedt.net.ftp.FTPClient;

/**
 * @author dongke
 *
 */
public class FtpVO {

	private String Ipaddress;
	private String username;
	private int port;
	private String passWord;
	private String wwwUrl;
	private String resroot;
	private FTPClient ftp ;
	
	public FTPClient getFtp() {
		return ftp;
	}
	public void setFtp(FTPClient ftp) {
		this.ftp = ftp;
	}
	public String getIpaddress() {
		return Ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		Ipaddress = ipaddress;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getWwwUrl() {
		return wwwUrl;
	}
	public void setWwwUrl(String wwwUrl) {
		this.wwwUrl = wwwUrl;
	}
	public String getResroot() {
		return resroot;
	}
	public void setResroot(String resroot) {
		this.resroot = resroot;
	}
	
	
}
