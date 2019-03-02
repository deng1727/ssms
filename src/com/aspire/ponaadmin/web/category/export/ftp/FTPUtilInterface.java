/**
 * <p>
 * FTP������ӿ�
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
	 * ftp �����ļ�
	 * @param ftpDir ftp����·��
	 * @param localDir �����ļ�·��
	 * @return
	 */
	public String[] getFiles(String ftpDir,String localDir) throws Exception;
	
	/**
	 * ftp �ϴ��ļ�
	 * @param ftpDir ftp����·��
	 * @param localDir �����ļ�·��
	 */
	public void putFiles(String ftpDir,String localDir,String targetFileName) throws Exception; 
}
