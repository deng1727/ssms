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
	 * 构造方法，由singleton模式调用
	 */
	private ResourceFtp()
	{}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static ResourceFtp getInstance()
	{
		return instance;
	}
	/**
	 * 获取资源服务器ftp连接，并进入当前图片保存根目录
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
		// 从配置项读取ftp连接的地址
		String sourceServerIP = vo.getIpaddress();
		// 从配置项读取ftp连接传输的端口号
		int sourceServerPort = vo.getPort();
		// 从配置项读取FTP服务器的登录用户名
		String sourceServerUser = vo.getUsername();
		// 从配置项读取FTP服务器的登录密码
		String sourceServerPassword = vo.getPassWord();

		FTPClient ftp = new FTPClient(sourceServerIP, sourceServerPort);
		// 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
		ftp.setConnectMode(FTPConnectMode.PASV);
		ftp.login(sourceServerUser, sourceServerPassword);
		// 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
		ftp.setType(FTPTransferType.BINARY);

		if (!"".equals(vo.getResroot()))// 进入当前资源服务器目录。
		{
			ftp.chdir(vo.getResroot());
		}
		vo.setFtp(ftp);
		return vo;
}

}
