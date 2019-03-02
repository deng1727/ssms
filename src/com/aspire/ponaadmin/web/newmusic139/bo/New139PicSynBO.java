/**
 * com.aspire.ponaadmin.web.newmusic139.bo New139PicSynBO.java
 * May 1, 2011
 *<p>
 * Copyright (c) 2003-2011 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * @author dongke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.newmusic139.bo;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.category.export.ftp.FTPUtil;

import com.aspire.ponaadmin.web.newmusic139.NewMusic139Config;
import com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO;

/**
 * @author tungke
 *ͼƬ����ӿ���
 */
public class New139PicSynBO extends New139BaseSynBO
{

	private static final JLogger logger = LoggerFactory.getLogger(New139PicSynBO.class);
	private String[] filePaths;
	private StringBuffer sb = new StringBuffer();

	
	/**
	 * 
	 *@desc 139������ ר�����񵥣�����ͼƬ���� �����
	 *@author dongke
	 *May 1, 2011
	 */
	public void handDeal() {
		// TODO �Զ����ɷ������
		this.processAlbum();
		this.processBillboard();
		this.processMusic();
		mailto = NewMusic139Config.getInstance().getMailTo();
		
		//this.r.setMsg(sb.toString());
		sendMail(sb.toString(),getOperationName());
	}
	
	public void processMusic()
	{
		// 1.copy����ͼƬ�����أ���ɾ���������ϵ�Ŀ¼
		try
		{
			filePaths = this.copyDirFromFTP(
					NewMusic139Config.getInstance().getLocalDir(), NewMusic139Config
							.getInstance().getNewSourceMusicFTPDir());
			// 2.������ͼƬ�ϴ���ָ����ftp�������ļ�����
			sb.append("���ظ���ͼƬ�����سɹ�<br>");
			this.uploadFileToFTP(filePaths, NewMusic139Config.getInstance()
					.getNewdestMusicFTPDir(), "0", true);
		} catch (Exception e)
		{
			// TODO �Զ����� catch ��
			sb.append("��ftp���ظ���ͼƬ����<br>");

			logger.warn("��FTP"
					+ NewMusic139Config.getInstance().getNewSourceMusicFTPDir()
					+ "���ظ���ͼƬ������" + NewMusic139Config.getInstance().getLocalDir()
					+ "����<br>");
		}
		filePaths = null;
	}

	public void processAlbum()
	{
		// 1.copyר��ͼƬ�����أ���ɾ���������ϵ�Ŀ¼
		try
		{
			filePaths = this.copyDirFromFTP(
					NewMusic139Config.getInstance().getLocalDir(), NewMusic139Config
							.getInstance().getNewSourceAlbumFTPDir());
			// 2.������ͼƬ�ϴ���ָ����ftp�������ļ�����
			sb.append("����ר��ͼƬ�����سɹ�<br>");
			this.uploadFileToFTP(filePaths, NewMusic139Config.getInstance()
					.getNewDestAlbumFTPDir(), "1", false);
		} catch (Exception e)
		{
			// TODO �Զ����� catch ��
			sb.append("��ftp����ר��ͼƬ����<br>");
			logger.warn("��FTP"
					+ NewMusic139Config.getInstance().getNewSourceAlbumFTPDir()
					+ "����ר��ͼƬ������" + NewMusic139Config.getInstance().getLocalDir()
					+ "����<br>");
		}
		filePaths = null;
	}
	public void processBillboard()
	{
		// 1.copy��ͼƬ�����أ���ɾ���������ϵ�Ŀ¼
		try
		{
			filePaths = this.copyDirFromFTP(
					NewMusic139Config.getInstance().getLocalDir(), NewMusic139Config
							.getInstance().getNewSourceBillboardFTPDir());
			// 2.������ͼƬ�ϴ���ָ����ftp�������ļ�����
			sb.append("���ذ�ͼƬ�����سɹ�<br>");
			this.uploadFileToFTP(filePaths, NewMusic139Config.getInstance()
					.getNewDestBillboardFTPDir(), "2", false);
		} catch (Exception e)
		{
			// TODO �Զ����� catch ��
			sb.append("��ftp����ר��ͼƬ����<br>");
			logger.warn("��FTP"
					+ NewMusic139Config.getInstance().getNewSourceBillboardFTPDir()
					+ "���ذ�ͼƬ������" + NewMusic139Config.getInstance().getLocalDir()
					+ "����<br>");
		}
		filePaths = null;
	}
	/**
	 * ��Զ��FTP�������������ļ�������
	 * 
	 * @param localDir
	 *            ���ر��浽���ص�·��
	 * @param FtpDir
	 *            Զ��FTP���ݵ�·��
	 * @return �ļ����б���Ϣ
	 * @throws Exception
	 */
	public String[] copyDirFromFTP(String localDir, String ftpDir) throws Exception
	{
		// TODO �Զ����ɷ������
		FTPUtil fUtil = new FTPUtil(NewMusic139Config.getInstance()
				.getSourceFTPServerIP(), Integer.valueOf(
				NewMusic139Config.getInstance().getSourceFTPServerPort()).intValue(),
				NewMusic139Config.getInstance().getSourceFTPServerUser(),
				NewMusic139Config.getInstance().getSourceFTPServerPassword());
		String[] filePaths = null;
		filePaths = fUtil.getFilesNoRegex(ftpDir, localDir);
		return filePaths;
	}

	/**
	 * �������ļ��ϴ���ָ��FPT������·��
	 * 
	 * @param filePath
	 *            �ļ���ϸ·����c:/qqq.txt
	 * @param ftpDir
	 *            Ҫ�ϴ���Զ��FTP�������ĵ�ַ
	 * @param flag
	 *            �Ƿ�Ҫִ�����ݿ���²���
	 */
	public void uploadFileToFTP(String[] filePath, String ftpDir, String type,
			boolean flag)
	{
		// TODO �Զ����ɷ������
		if (filePaths != null && filePaths.length > 0)
		{
			FTPUtil fUtil = new FTPUtil(NewMusic139Config.getInstance()
					.getDestFTPServerIP(), Integer.valueOf(
					NewMusic139Config.getInstance().getDestFTPServerPort()).intValue(),
					NewMusic139Config.getInstance().getDestFTPServerUser(),
					NewMusic139Config.getInstance().getDestFTPServerPassword());
			String fileName = null;
			String path = null;
			StringBuffer msg = new StringBuffer();
			int count = 0;
			for (int i = 0; i < filePaths.length; i++)
			{
				path = filePaths[i];
				if (path.indexOf('/') == -1)
				{
					path.replace('\\', '/');
				}
				fileName = path.substring(path.lastIndexOf('/') + 1);
				try
				{
					fUtil.putFilesNoMkNewDateDir(ftpDir, path, fileName);
					count++;
					String picUrl = null;
					if (type.equals("1"))
					{// ר��
						picUrl = NewMusic139Config.getInstance().getNewAlbumPicUrl()
								+ fileName;
						updateAlbumPicDir(new Object[] { picUrl,
								fileName.substring(0, fileName.lastIndexOf('.')),NewMusic139Config.getInstance().getBaseAlbumCategoryId() });
					}else if(type.equals("2")){
//						��ͼƬ
						picUrl = NewMusic139Config.getInstance().getNewBillboardPicUrl()
						+ fileName;
						updateBillboardPicDir(new Object[] { picUrl,
								fileName.substring(0, fileName.lastIndexOf('.')),NewMusic139Config.getInstance().getBaseBillboardCategoryId() });
					}
					else if(type.equals("0"))
					{//����ͼƬ
						picUrl = NewMusic139Config.getInstance().getNewMusicPicUrl()
								+ fileName;
						updateMusicPicDir(new Object[] { picUrl,
								fileName.substring(0, fileName.lastIndexOf('.')) });
					}
					//if (flag)
						
				} catch (Exception e)
				{
					// TODO �Զ����� catch ��
					msg.append("ͼƬ" + fileName + "�ϴ���ftp����������<br>");
					logger.warn("�ӱ���" + fileName + "�ϴ��ļ���FTP������" + ftpDir + "ʧ��" + e);
				}
			}
			if (count == filePaths.length)
				sb.append("ͼƬ�ϴ�����Դ�������ɹ���<br>");
			else
			{
				sb.append("ͼƬ�ϴ�����Դ������ʧ�ܣ�<br>");
				sb.append(msg);
			}
			msg = null;
			fUtil = null;
		}
	}

	/**
	 * ͬ��ͼƬʱ��ͬʱ����ר���и�����Ӧ��ͼƬ·���ֶ�
	 * 
	 */
	public void updateMusicPicDir(Object[] paras) throws DAOException
	{
		New139BaseSyncDAO.getInstance().updateMusicPic(paras);
	}
	/**
	 * ͬ��ͼƬʱ��ͬʱ����ר����Ӧ��ͼƬ·���ֶ�
	 * 
	 */
	public void updateAlbumPicDir(Object[] paras) throws DAOException
	{
		New139BaseSyncDAO.getInstance().updateAlbumPic(paras);
	}
	/**
	 * ͬ��ͼƬʱ��ͬʱ���°���Ӧ��ͼƬ·���ֶ�
	 * 
	 */
	public void updateBillboardPicDir(Object[] paras) throws DAOException
	{
		New139BaseSyncDAO.getInstance().updateBillboardPic(paras);
	}

	public String getOperationName()
	{
		// TODO �Զ����ɷ������
		return "139���ֽӿ�--ר�����񵥣�����ͼƬͬ��";
	}

}
