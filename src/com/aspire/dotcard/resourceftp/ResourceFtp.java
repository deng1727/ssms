/**
 * 
 */
package com.aspire.dotcard.resourceftp;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

/**
 * @author dongke
 *
 */
public class ResourceFtp {

	
	private static ResourceFtp instance=new ResourceFtp();
	/**
	 * ���췽������singletonģʽ����
	 */
	private ResourceFtp()
	{}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static ResourceFtp getInstance()
	{
		return instance;
	}
	/**
	 * ��ȡ��Դ������ftp���ӣ������뵱ǰͼƬ�����Ŀ¼
	 * @return FTPClient
	 * @throws DAOException 
	 * @throws FTPException
	 * @throws IOException
	 * @throws DAOException 
	 * @throws FTPException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws BOException
	 */
public  FtpVO getResourceServerFtp() throws DAOException, IOException, FTPException
 {
		String sqlCode = "com.aspire.dotcard.resourceftp.getResourceServerFtp.select";
		//select * from cm_resource_server@dl_mm_ppms_new
		// select * from cm_resource_server 
		ResultSet rs = null;
		FtpVO vo = null;
		
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);

			if (rs.next()) {
				vo = new FtpVO();

				vo.setIpaddress(rs.getString("IPADDRESS"));
				vo.setUsername(rs.getString("USERNAME"));
				vo.setPort(rs.getInt("port"));
				vo.setPassWord(rs.getString("PASSWORD"));
				vo.setResroot(rs.getString("RESROOT"));
				vo.setWwwUrl(rs.getString("WWWURL"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("error", e);
		} finally {
			DB.close(rs);
		}
		// ���������ȡftp���ӵĵ�ַ
		String sourceServerIP = vo.getIpaddress();
		// ���������ȡftp���Ӵ���Ķ˿ں�
		int sourceServerPort = vo.getPort();
		// ���������ȡFTP�������ĵ�¼�û���
		String sourceServerUser = vo.getUsername();
		// ���������ȡFTP�������ĵ�¼����
		String sourceServerPassword = vo.getPassWord();

		FTPClient ftp = new FTPClient(sourceServerIP, sourceServerPort);
		// ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
		ftp.setConnectMode(FTPConnectMode.PASV);
		ftp.login(sourceServerUser, sourceServerPassword);
		// �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
		ftp.setType(FTPTransferType.BINARY);

		if (!"".equals(vo.getResroot()))// ���뵱ǰ��Դ������Ŀ¼��
		{
			ftp.chdir(vo.getResroot());
		}
		vo.setFtp(ftp);
		return vo;
}

}
