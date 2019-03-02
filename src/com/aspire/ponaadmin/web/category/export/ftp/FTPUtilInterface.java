/**
 * <p>
 * FTP工具类接口
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 17, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.ponaadmin.web.category.export.ftp;

/**
 * @author dongke
 *
 */
public interface FTPUtilInterface
{

	/**
	 * ftp 下载文件
	 * @param ftpDir ftp数据路径
	 * @param localDir 本地文件路径
	 * @return
	 */
	public String[] getFiles(String ftpDir,String localDir) throws Exception;
	
	/**
	 * ftp 上传文件
	 * @param ftpDir ftp数据路径
	 * @param localDir 本地文件路径
	 */
	public void putFiles(String ftpDir,String localDir,String targetFileName) throws Exception; 
}
