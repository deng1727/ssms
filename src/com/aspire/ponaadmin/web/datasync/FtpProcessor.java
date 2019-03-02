package com.aspire.ponaadmin.web.datasync;

import com.aspire.common.exception.BOException;


public interface FtpProcessor extends DataSyncBuilder
{
	/**
	 * 从FTP获取文件并保存到本地
	 * @return 保存的文件列表
	 */
	String[] process()throws BOException;
	
	/**
	 * 下载指定的文件
	 * @param fileName
	 * @return
	 * @throws BOException
	 */
	boolean process(String[] fileName)throws BOException;

}
