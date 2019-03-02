package com.aspire.ponaadmin.web.datasync;

import com.aspire.common.exception.BOException;


public interface FtpProcessor extends DataSyncBuilder
{
	/**
	 * ��FTP��ȡ�ļ������浽����
	 * @return ������ļ��б�
	 */
	String[] process()throws BOException;
	
	/**
	 * ����ָ�����ļ�
	 * @param fileName
	 * @return
	 * @throws BOException
	 */
	boolean process(String[] fileName)throws BOException;

}
