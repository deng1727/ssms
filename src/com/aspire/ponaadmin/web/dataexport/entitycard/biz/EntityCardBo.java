package com.aspire.ponaadmin.web.dataexport.entitycard.biz;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.DataExportTools;
import com.aspire.ponaadmin.web.dataexport.entitycard.EntityCardConfig;
import com.aspire.ponaadmin.web.dataexport.entitycard.dao.EntityCardDao;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class EntityCardBo {
	private static JLogger logger = LoggerFactory.getLogger(EntityCardBo.class);

	private static EntityCardBo bo = new EntityCardBo();

	public static EntityCardBo getInstance() {
		return bo;
	}

	public void exportAPData(StringBuffer sb) {
		logger.debug("AP信息同步接口全量导出开始");
		String file = DataExportTools
				.parseFileName(EntityCardConfig.APExportFile);
		try {
			List data = EntityCardDao.getInstance().getAllAPData();
			String exportFile = EntityCardConfig.LOCALDIR + File.separator + file;
			DataExportTools.writeToTXTFile(exportFile, data,
					EntityCardConfig.lineSep, EntityCardConfig.ExperEncoding);
			copyFileToFTP(exportFile,file);
			sb.append("AP信息同步接口全量导出成功，共导出数据"+data.size()+"条");
			sb.append("<br />");
		} catch (Exception e) {
			logger.error("AP信息同步接口全量导出失败",e);
			sb.append("AP信息同步接口全量导出失败，原因:"+e.getMessage());
			sb.append("<br />");
		}
		logger.debug("AP信息同步接口全量导出结束");
	}

	public void exportAPOperData(StringBuffer sb) {
		logger.debug("业务信息同步接口全量导出开始");
		String file = DataExportTools
				.parseFileName(EntityCardConfig.APOperExportFile);
		try {
			List data = EntityCardDao.getInstance().getAllAPOperData();
			String exportFile = EntityCardConfig.LOCALDIR + File.separator + file;
			DataExportTools.writeToTXTFile(exportFile, data,
					EntityCardConfig.lineSep, EntityCardConfig.ExperEncoding);
			copyFileToFTP(exportFile,file);
			sb.append("业务信息同步接口全量导出成功，共导出数据"+data.size()+"条");
			sb.append("<br />");
		} catch (Exception e) {
			logger.error("业务信息同步接口全量导出失败",e);
			sb.append("业务信息同步接口全量导出失败，原因:"+e.getMessage());
			sb.append("<br />");
		}
		logger.debug("业务信息同步接口全量导出结束");		
	}

	/**
	 * 导出增量信息
	 * @param sb
	 */
	public void exportIncrementAPData(StringBuffer sb) {
		logger.debug("AP信息同步接口增量导出开始");
		String file = DataExportTools
				.parseFileName(EntityCardConfig.APExportFile);
		try {
			List data = EntityCardDao.getInstance().getIncrementAPData();
			String exportFile = EntityCardConfig.LOCALDIR + File.separator + file;
			DataExportTools.writeToTXTFile(exportFile, data,
					EntityCardConfig.lineSep, EntityCardConfig.ExperEncoding);
			copyFileToFTP(exportFile,file);
			sb.append("AP信息同步接口增量导出成功，共导出数据"+data.size()+"条");
			sb.append("<br />");
		} catch (Exception e) {
			logger.error("AP信息同步接口增量导出失败",e);
			sb.append("AP信息同步接口增量导出失败，原因:"+e.getMessage());
			sb.append("<br />");
		}
		logger.debug("AP信息同步接口增量导出结束");
	}

	/**
	 * 导出增量商品信息
	 * @param sb
	 */
	public void exportIncrementAPOperData(StringBuffer sb) {
		logger.debug("业务信息同步接口增量导出开始");
		String file = DataExportTools
				.parseFileName(EntityCardConfig.APOperExportFile);
		try {
			List data = EntityCardDao.getInstance().getIncrementAPOperData();
			String exportFile = EntityCardConfig.LOCALDIR + File.separator + file;
			DataExportTools.writeToTXTFile(exportFile, data,
					EntityCardConfig.lineSep, EntityCardConfig.ExperEncoding);
			copyFileToFTP(exportFile,file);
			sb.append("业务信息同步接口增量导出成功，共导出数据"+data.size()+"条");
			sb.append("<br />");
		} catch (Exception e) {
			logger.error("业务信息同步接口增量导出失败",e);
			sb.append("业务信息同步接口增量导出失败，原因:"+e.getMessage());
			sb.append("<br />");
		}
		logger.debug("业务信息同步接口增量导出结束");		
	}	
	
	/**
	 * 写文件至FTP指定目录中
	 * 
	 * @throws BOException
	 * 
	 */
	protected void copyFileToFTP(String localFile, String ftpFile)
			throws BOException {

		FTPClient ftp = null;

		try {
			// 取得远程目录中文件列表
			ftp = getFTPClient();

			if (!"".equals(EntityCardConfig.FTPPAHT)) {
				ftp.chdir(EntityCardConfig.FTPPAHT);
			}

			ftp.put(localFile, ftpFile);

		} catch (Exception e) {
			throw new BOException(e, DataSyncConstants.EXCEPTION_FTP);
		} finally {
			if (ftp != null) {
				try {
					ftp.quit();
				} catch (Exception e) {
				}
			}
		}
	}

	private FTPClient getFTPClient() throws IOException, FTPException {

		String ip = EntityCardConfig.FTPIP;
		int port = EntityCardConfig.FTPPORT;
		String user = EntityCardConfig.FTPNAME;
		String password = EntityCardConfig.FTPPAS;

		FTPClient ftp = new FTPClient(ip, port);

		// 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
		ftp.setConnectMode(FTPConnectMode.PASV);

		// 使用给定的用户名、密码登陆ftp
		ftp.login(user, password);

		// 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
		ftp.setType(FTPTransferType.BINARY);

		return ftp;
	}
}
