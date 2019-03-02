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
		logger.debug("AP��Ϣͬ���ӿ�ȫ��������ʼ");
		String file = DataExportTools
				.parseFileName(EntityCardConfig.APExportFile);
		try {
			List data = EntityCardDao.getInstance().getAllAPData();
			String exportFile = EntityCardConfig.LOCALDIR + File.separator + file;
			DataExportTools.writeToTXTFile(exportFile, data,
					EntityCardConfig.lineSep, EntityCardConfig.ExperEncoding);
			copyFileToFTP(exportFile,file);
			sb.append("AP��Ϣͬ���ӿ�ȫ�������ɹ�������������"+data.size()+"��");
			sb.append("<br />");
		} catch (Exception e) {
			logger.error("AP��Ϣͬ���ӿ�ȫ������ʧ��",e);
			sb.append("AP��Ϣͬ���ӿ�ȫ������ʧ�ܣ�ԭ��:"+e.getMessage());
			sb.append("<br />");
		}
		logger.debug("AP��Ϣͬ���ӿ�ȫ����������");
	}

	public void exportAPOperData(StringBuffer sb) {
		logger.debug("ҵ����Ϣͬ���ӿ�ȫ��������ʼ");
		String file = DataExportTools
				.parseFileName(EntityCardConfig.APOperExportFile);
		try {
			List data = EntityCardDao.getInstance().getAllAPOperData();
			String exportFile = EntityCardConfig.LOCALDIR + File.separator + file;
			DataExportTools.writeToTXTFile(exportFile, data,
					EntityCardConfig.lineSep, EntityCardConfig.ExperEncoding);
			copyFileToFTP(exportFile,file);
			sb.append("ҵ����Ϣͬ���ӿ�ȫ�������ɹ�������������"+data.size()+"��");
			sb.append("<br />");
		} catch (Exception e) {
			logger.error("ҵ����Ϣͬ���ӿ�ȫ������ʧ��",e);
			sb.append("ҵ����Ϣͬ���ӿ�ȫ������ʧ�ܣ�ԭ��:"+e.getMessage());
			sb.append("<br />");
		}
		logger.debug("ҵ����Ϣͬ���ӿ�ȫ����������");		
	}

	/**
	 * ����������Ϣ
	 * @param sb
	 */
	public void exportIncrementAPData(StringBuffer sb) {
		logger.debug("AP��Ϣͬ���ӿ�����������ʼ");
		String file = DataExportTools
				.parseFileName(EntityCardConfig.APExportFile);
		try {
			List data = EntityCardDao.getInstance().getIncrementAPData();
			String exportFile = EntityCardConfig.LOCALDIR + File.separator + file;
			DataExportTools.writeToTXTFile(exportFile, data,
					EntityCardConfig.lineSep, EntityCardConfig.ExperEncoding);
			copyFileToFTP(exportFile,file);
			sb.append("AP��Ϣͬ���ӿ����������ɹ�������������"+data.size()+"��");
			sb.append("<br />");
		} catch (Exception e) {
			logger.error("AP��Ϣͬ���ӿ���������ʧ��",e);
			sb.append("AP��Ϣͬ���ӿ���������ʧ�ܣ�ԭ��:"+e.getMessage());
			sb.append("<br />");
		}
		logger.debug("AP��Ϣͬ���ӿ�������������");
	}

	/**
	 * ����������Ʒ��Ϣ
	 * @param sb
	 */
	public void exportIncrementAPOperData(StringBuffer sb) {
		logger.debug("ҵ����Ϣͬ���ӿ�����������ʼ");
		String file = DataExportTools
				.parseFileName(EntityCardConfig.APOperExportFile);
		try {
			List data = EntityCardDao.getInstance().getIncrementAPOperData();
			String exportFile = EntityCardConfig.LOCALDIR + File.separator + file;
			DataExportTools.writeToTXTFile(exportFile, data,
					EntityCardConfig.lineSep, EntityCardConfig.ExperEncoding);
			copyFileToFTP(exportFile,file);
			sb.append("ҵ����Ϣͬ���ӿ����������ɹ�������������"+data.size()+"��");
			sb.append("<br />");
		} catch (Exception e) {
			logger.error("ҵ����Ϣͬ���ӿ���������ʧ��",e);
			sb.append("ҵ����Ϣͬ���ӿ���������ʧ�ܣ�ԭ��:"+e.getMessage());
			sb.append("<br />");
		}
		logger.debug("ҵ����Ϣͬ���ӿ�������������");		
	}	
	
	/**
	 * д�ļ���FTPָ��Ŀ¼��
	 * 
	 * @throws BOException
	 * 
	 */
	protected void copyFileToFTP(String localFile, String ftpFile)
			throws BOException {

		FTPClient ftp = null;

		try {
			// ȡ��Զ��Ŀ¼���ļ��б�
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

		// ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
		ftp.setConnectMode(FTPConnectMode.PASV);

		// ʹ�ø������û����������½ftp
		ftp.login(user, password);

		// �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
		ftp.setType(FTPTransferType.BINARY);

		return ftp;
	}
}
