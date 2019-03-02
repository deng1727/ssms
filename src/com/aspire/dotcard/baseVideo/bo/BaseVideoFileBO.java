/*
 * �ļ�����BaseVideoFileBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideo.bo;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO;
import com.aspire.dotcard.baseVideo.exportfile.BaseFileFactory;
import com.aspire.ponaadmin.web.category.export.ftp.FTPUtil;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class BaseVideoFileBO
{
	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseVideoFileBO.class);

	private static BaseVideoFileBO bo = new BaseVideoFileBO();

	private BaseVideoFileBO()
	{}

	public static BaseVideoFileBO getInstance()
	{
		return bo;
	}

	private Vector nodeFaild = new Vector();

	private Vector videoDetailFail = new Vector();

	private int nodeLogoTotal = 0;

	private int videoDetailTotal = 0;

	private static boolean syncLogopath = true;

	/**
	 * ������վɵ�ģ�������
	 */
	public boolean cleanOldSimulationData(StringBuffer sb)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��վɵ�ģ�������,��ʼ");
		}

		String ret;
		try
		{
			ret = BaseVideoFileDAO.getInstance().cleanOldSimulationData();
		}
		catch (BOException e)
		{
			logger.debug("��վɵ�ģ�������,ʧ�ܣ�" + e.getMessage());
			sb.append("��վɵ�ģ�������,ʧ�ܣ�" + e.getMessage());
			return false;
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("��վɵ�ģ�������,����");
		}
		logger.info(ret);
		sb.append(ret);		
		return true;
	}

	/**
	 * ����ͬ�����������ļ�
	 */
	public void fileDataSync()
    {
        StringBuffer mailText = new StringBuffer();
        
        // ɾ��ͬ����ʱ�������
        mailText.append(dropIndex());

        // ɾ��ͬ����ʱ��ǰ���ݣ�������ʽ������
        mailText.append(updateTable());

        // ��ǰϵͳ����Ϊ�ܼ�ִ��
        int confWeek = BaseVideoConfig.sysDayByWeek;
        
		if (confWeek > 6)
		{
			confWeek = 1;
		}
		else
		{
			confWeek++;
		}
		
		// ����ǵ�ǰ�ܶ�ִ��ȫ������һ��
		if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == confWeek)
		{
	        // ��Ƶ�ļ����ݵ���
	        mailText.append(BaseFileFactory.getInstance()
	                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_VIDEO)
	                                       .execution(false));
		}
        
		try
		{
			logger.info("������ʱ��������������Ƶ����Ӧ��ɾ������!");
			
			// ������ʱ��������������Ƶ����Ӧ��ɾ������
			BaseVideoFileDAO.getInstance().createVideoIndexByAdd();
		} 
		catch (DAOException e)
		{
			logger.error(e);
		}
		
        // ��Ƶ�ļ�������������
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_VIDEO_ADD)
                                       .execution(false));
        
		try
		{
			logger.info("ɾ����Ƶ������ʱ����!");
			
			// ɾ����Ƶ������ʱ����
			BaseVideoFileDAO.getInstance().delVideoIndexByAdd();
		} 
		catch (DAOException e)
		{
			logger.error(e);
		}

        // ��Ŀ�ļ����ݵ���
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_NODE)
                                       .execution(false));

        // �õ����еĵ�ǰlogopath
        Map allLogopath = new HashMap();
        logger.debug("�õ����еĵ�ǰlogopath���Ǻǣ�");
        allLogopath = BaseVideoFileBO.getInstance().getAllLogopath();

        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_VIDEO_DETAIL)
                                       .execution(false));
        // downAndUpPic();׼���������ʱ��Ĺ��̰ᵽ�߳��д����ˡ�

        logger.debug("��֮ǰ�ĸ���logopath���Ǻǣ�");
        BaseVideoFileBO.getInstance().updateLogopath(allLogopath);

        logger.debug("����logopath��ϣ�");

        // ֱ����Ŀ�����ݵ���
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_LIVE)
                                       .execution(false));

        // ��Ʒ���ݵ���
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_PRODUCT)
                                       .execution(false));

        // ��Ƶ��Ŀͳ��
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_VIDEODETAIL)
                                       .execution(false));

        sendResultMail("������Ƶ����ͬ������ʼ�", mailText);
    }

	public void downAndUpPic()
	{

		if (!syncLogopath)
		{
			logger
					.error("downAndUpPic��syncLogopathΪ��" + syncLogopath
							+ "����ͬ����");
			return;
		}

		syncLogopath = false;

		long start = System.currentTimeMillis();
		logger.info("aiyan_�Ϻ���Ƶ��ʼ�����ļ��ˡ�����");
		// ���д���ͼƬ�����ϴ�add by aiyan 2012-07-14
		StringBuffer mailText = new StringBuffer("��ʼʱ�䣺" + new Date());

		try
		{
			// rs.getString("programid")+"|"+rs.getString("logopath")
			List videoNodeList = getAllVideoNode();
			logger.debug("��Ҫ������ļ���" + videoNodeList.size());

			TaskRunner dataSynTaskRunner = new TaskRunner(
					BaseVideoConfig.taskRunnerNum,
					BaseVideoConfig.taskMaxReceivedNum);
			nodeLogoTotal = videoNodeList.size();
			for (int i = 0; i < videoNodeList.size(); i++)
			{
				FTPUtil fromFtp = new FTPUtil(BaseVideoConfig.FromFTPIP,
						BaseVideoConfig.FromFTPPort,
						BaseVideoConfig.FromFTPUser,
						BaseVideoConfig.FromFTPPassword,
						BaseVideoConfig.FromProgramFTPDir);
				logger.debug("fromFtp:" + fromFtp);

				FTPUtil toFtp = new FTPUtil(BaseVideoConfig.ToFTPIP,
						BaseVideoConfig.ToFTPPort, BaseVideoConfig.ToFTPUser,
						BaseVideoConfig.ToFTPPassword,
						BaseVideoConfig.ToProgramFTPDir);
				logger.debug("toFtp:" + toFtp);

				String line = (String) videoNodeList.get(i);
				logger.debug("�����" + i + "�У�" + line);
				// �����첽����
				//	        	
				// Object executeObj, String executeMethodName,
				// Object[] args, Class[] argsClass)
				ReflectedTask task = new ReflectedTask(this, "handleLineNode",
						new Object[]
						{ line, fromFtp, toFtp, }, new Class[]
						{ String.class, FTPUtil.class, FTPUtil.class });
				dataSynTaskRunner.addTask(task);

			}
			dataSynTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
			dataSynTaskRunner.end();// ����������
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			syncLogopath = true;

			logger.info("aiyan_�Ϻ���Ƶ��ʼ������Ŀ�ļ��ˡ�������������ʱ��"
					+ (System.currentTimeMillis() - start));
		}

		try
		{
			// rs.getString("programid")+"|"+rs.getString("logopath")
			List videoDetailList = getAllVideoDetail();
			logger.debug("��Ҫ������ļ���" + videoDetailList.size());
			videoDetailTotal = videoDetailList.size();
			TaskRunner dataSynTaskRunner = new TaskRunner(
					BaseVideoConfig.taskRunnerNum,
					BaseVideoConfig.taskMaxReceivedNum);

			for (int i = 0; i < videoDetailList.size(); i++)
			{
				FTPUtil fromFtp = new FTPUtil(BaseVideoConfig.FromFTPIP,
						BaseVideoConfig.FromFTPPort,
						BaseVideoConfig.FromFTPUser,
						BaseVideoConfig.FromFTPPassword,
						BaseVideoConfig.FromProgramFTPDir);
				logger.debug("fromFtp:" + fromFtp);

				FTPUtil toFtp = new FTPUtil(BaseVideoConfig.ToFTPIP,
						BaseVideoConfig.ToFTPPort, BaseVideoConfig.ToFTPUser,
						BaseVideoConfig.ToFTPPassword,
						BaseVideoConfig.ToProgramFTPDir);
				logger.debug("toFtp:" + toFtp);

				String line = (String) videoDetailList.get(i);
				logger.debug("�����" + i + "�У�" + line);
				// �����첽����
				//	        	
				// Object executeObj, String executeMethodName,
				// Object[] args, Class[] argsClass)
				ReflectedTask task = new ReflectedTask(this, "handleLine",
						new Object[]
						{ line, fromFtp, toFtp, }, new Class[]
						{ String.class, FTPUtil.class, FTPUtil.class });
				dataSynTaskRunner.addTask(task);

			}
			dataSynTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
			dataSynTaskRunner.end();// ����������
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			syncLogopath = true;

			logger.info("aiyan_�Ϻ���Ƶ��ʼ�����ļ��ˡ�������������ʱ��"
					+ (System.currentTimeMillis() - start));
		}
		StringBuffer nodeF = new StringBuffer("ʧ����ϸ�����");
		for (int s = 0; s < nodeFaild.size(); s++)
		{
			nodeF.append(nodeFaild.get(s));
			nodeF.append(",");

		}
		StringBuffer detailF = new StringBuffer("ʧ����ϸ�����");
		for (int h = 0; h < videoDetailFail.size(); h++)
		{
			nodeF.append(videoDetailFail.get(h));
			nodeF.append(",");

		}
		mailText.append("����ʱ��:" + new Date() + "�������ĿͼƬ����:" + nodeLogoTotal
				+ ";����ʧ����:" + nodeFaild.size() + nodeF + "<br>;��ĿͼƬ����:"
				+ videoDetailTotal + ";����ʧ����:" + videoDetailFail.size()
				+ detailF);
		sendResultMail("������ƵͼƬ����ͬ������ʼ�", mailText);
	}

	/**
	 * 
	 * @desc
	 * @author dongke Jul 21, 2012
	 * @param line
	 * @param fromFtp
	 * @param toFtp
	 */
	public void handleLine(String line, FTPUtil fromFtp, FTPUtil toFtp)
	{
		String ftpFilePath = "", ftpFileName = "", localFileName = "";
		String fileName = null;
		String[] arr = line.split("\\|");// programid = arr[0];logopath =
		// arr[1];
		if (arr.length == 2)
		{
			ftpFilePath = arr[1].substring(0, arr[1].lastIndexOf("/"));
			ftpFileName = arr[1].substring(arr[1].lastIndexOf("/") + 1, arr[1]
					.length());
			localFileName = arr[0]
					+ ftpFileName.substring(ftpFileName.indexOf("."),
							ftpFileName.length());
			try
			{
				fileName = fromFtp.getFtpFileByFileName2(ftpFilePath,
						ftpFileName, BaseVideoConfig.prologoTemplocalDir,
						localFileName);
			}
			catch (Exception e)
			{
				videoDetailFail.add(arr[0]);
				logger.error("�����ļ�������", e);

			}
		}
		logger.debug("fileName:" + fileName);
		if (fileName == null || "".equals(fileName))
		{
			logger.debug("û�����ص��ļ����š����� ���о�û�и���LOGOPATH�Ļ����ˡ�" + line);
			videoDetailFail.add(arr[0]);
			return;
		}
		String logoPath = BaseVideoConfig.logoPath;// Ĭ��ֵ��
		// �ϴ��������ļ�������
		try
		{
			if (fileName != null && !"".equals(fileName))
			{
				toFtp.putFiles2(BaseVideoConfig.ToProgramFTPDir, fileName,
						localFileName);
				// ��������ֶ���Ϣ
				logoPath = BaseVideoConfig.ProgramLogoPath + File.separator
						+ localFileName;
			}

		}
		catch (Exception e)
		{
			logger.error(" �������logoͼƬ������FTP���ó���FTP���ӳ�������<br> ", e);
			videoDetailFail.add(arr[0]);
		}

		if (!logoPath.equals(BaseVideoConfig.logoPath))
		{
			BaseVideoFileDAO.getInstance().updateLogoPath(arr[0], logoPath);
		}
		else
		{
			logger.error("ͼƬû���ϴ��ɹ���");
			videoDetailFail.add(arr[0]);
		}
	}

	public void handleLineNode(String line, FTPUtil fromFtp, FTPUtil toFtp)
	{
		String ftpFilePath = "", ftpFileName = "", localFileName = "";
		String fileName = null;
		String[] arr = line.split("\\|");// programid = arr[0];logopath =
		// arr[1];
		if (arr.length == 2)
		{
			ftpFilePath = arr[1].substring(0, arr[1].lastIndexOf("/"));
			ftpFileName = arr[1].substring(arr[1].lastIndexOf("/") + 1, arr[1]
					.length());
			localFileName = arr[0]
					+ ftpFileName.substring(ftpFileName.indexOf("."),
							ftpFileName.length());
			try
			{
				fileName = fromFtp.getFtpFileByFileName2(ftpFilePath,
						ftpFileName, BaseVideoConfig.nodelogoTemplocalDir,
						localFileName);
			}
			catch (Exception e)
			{
				logger.error("�����ļ�������", e);
				nodeFaild.add(arr[0]);
			}
		}
		logger.debug("fileName:" + fileName);
		if (fileName == null || "".equals(fileName))
		{
			logger.debug("û�����ص��ļ����š����� ���о�û�и���LOGOPATH�Ļ����ˡ�" + line);
			nodeFaild.add(arr[0]);
			return;
		}
		String logoPath = BaseVideoConfig.logoPath;// Ĭ��ֵ��
		// �ϴ��������ļ�������
		try
		{
			if (fileName != null && !"".equals(fileName))
			{
				toFtp.putFiles2(BaseVideoConfig.ToNodeFTPDir, fileName,
						localFileName);
				// ��������ֶ���Ϣ
				logoPath = BaseVideoConfig.NodeLogoPath + File.separator
						+ localFileName;
			}

		}
		catch (Exception e)
		{
			logger.error(" �������logoͼƬ������FTP���ó���FTP���ӳ�������<br> ", e);
			nodeFaild.add(arr[0]);
		}

		// if (!logoPath.equals(BaseVideoConfig.logoPath)) {
		BaseVideoFileDAO.getInstance().updateNodeLogoPath(arr[0], logoPath);
		// } else {
		// logger.error("ͼƬû���ϴ��ɹ���");
		// }
	}

	private List getAllVideoDetail()
	{
		// TODO Auto-generated method stub
		return BaseVideoFileDAO.getInstance().getAllVideoDetail();
	}

	private List getAllVideoNode()
	{
		// TODO Auto-generated method stub
		return BaseVideoFileDAO.getInstance().getAllVideoNode();
	}

	/**
	 * �Զ�����װģ���
	 */
	public boolean diyDataTable(StringBuffer sb)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�Զ�����װģ���,��ʼ");
		}

		StringBuffer sf = new StringBuffer();
		
		try
		{
			// ��װ�Զ������ṹ��
			sf.append(BaseVideoFileDAO.getInstance().insertDataToTree());

			// ��װ�Զ���ĸ�֧�ṹ
			sf.append(BaseVideoFileDAO.getInstance().insertDataToReference());
		}
		catch (BOException e)
		{
			logger.debug("�Զ�����װģ���,ʧ��" + e.getMessage());
			sb.append("�Զ�����װģ���,ʧ��" + e.getMessage());
			return false;
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("�Զ�����װģ���,����");
		}
		logger.info(sf);
		sb.append(sf);
		return true;
	}

	/**
	 * ���ͽ���ʼ���
	 */
	public void sendResultMail(String mailTitle, StringBuffer mailText)
	{
		logger.info(mailText.toString());
		Mail.sendMail(mailTitle, mailText.toString(), BaseVideoConfig.mailTo);
	}

	public Map getAllLogopath()
	{
		// TODO Auto-generated method stub
		return BaseVideoFileDAO.getInstance().getAllLogopath();
	}

	public void updateLogopath(Map allLogopath)
	{
		// TODO Auto-generated method stub
		Set set = allLogopath.entrySet();
		String key = "", value = "";
		for (Iterator it = set.iterator(); it.hasNext();)
		{
			Map.Entry entity = (Map.Entry) it.next();
			key = (String) entity.getKey();
			value = (String) entity.getValue();
			BaseVideoFileDAO.getInstance().updateLogoPath(key, value);
		}
	}
	
	/**
	 * ɾ�������ظ�������
	 */
	public void delRepeatData()
	{
		logger.info("delRepeatData start");
		try
		{
			logger.info("������ʱ����������ɾ�������ظ�����!");
			
			// ������ʱ����������ɾ�������ظ�����
			BaseVideoFileDAO.getInstance().createVideoIndex();
			
			logger.info("ɾ�������ظ�����!");
			
			// ɾ�������ظ�����
			BaseVideoFileDAO.getInstance().delRepeatData();
			
			logger.info("ɾ��������ʱ����!");
			
			// ɾ��������ʱ����
			BaseVideoFileDAO.getInstance().delVideoIndex();
		} 
		catch (DAOException e)
		{
			logger.error(e);
		}
		logger.info("delRepeatData end");
	}

	/**
	 * 
	 */
	public void renameDataSync()
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("renameDataSync start");
		}
		StringBuffer mailText = new StringBuffer();
		mailText.append("�ı�������ʼ��");
		mailText.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss")
				+ "<br>");
		boolean tag = false;

		// �����б��ݵı�����׺ _bak
		String bakSuffix = BaseVideoConfig.bakSuffix;
		// Ĭ�ϵı�����׺ _tra
		String defSuffix = BaseVideoConfig.defSuffix;
		String renameTables = BaseVideoConfig.renameTables;
		if (logger.isDebugEnabled())
		{
			logger.debug("bakSuffix=" + bakSuffix + " defSuffix=" + defSuffix
					+ " renameTables=" + renameTables);
		}
		try
		{
			tag = BaseVideoFileDAO.getInstance().isExist();

			if (logger.isDebugEnabled())
			{
				logger.debug("tag=" + tag);
			}
			if (tag)
			{

				// �ı���ʱ����ʱ����
				String tempSuffix = PublicUtil.getCurDateTime("HHmmssSSS");
				String[] renameTable = renameTables.split(",");
				String bakTable = "";
				String defTable = "";
				String tempTable = "";
				int len = renameTable.length;
				if (logger.isDebugEnabled())
				{
					logger.debug("tempSuffix=" + tempSuffix);
				}
				try
				{
					// t_vo_video,t_vo_node,t_vo_program,t_vo_live,t_vo_product,t_vo_videodetail,t_vo_reference,t_vo_category
					mailText.append("��Ҫ���������ı�" + renameTables + "<br>");
					int videoSync_ID = DB.getSeqValue("SEQ_VIDEO_SYNC_ID");
					for (int i = 0; i < len; i++)
					{
						mailText.append("����" + renameTable[i] + defSuffix
								+ "�������ʱ�䣺");
						mailText.append(PublicUtil
								.getCurDateTime("yyyy-MM-dd HH:mm:ss")
								+ "<br>");
						if ("t_vo_category".equals(renameTable[i]))
						{
							// ���ܱ���������������
							BaseVideoFileDAO.getInstance().createIndex(
									"t_vo_category_id", videoSync_ID);
							BaseVideoFileDAO.getInstance().createIndex(
									"t_vo_category_pid", videoSync_ID);

						}
						else if ("t_vo_reference".equals(renameTable[i]))
						{
							// ��Ʒ�����ӻ���ID����
							BaseVideoFileDAO.getInstance().createIndex(
									"t_vo_reference_cid", videoSync_ID);
						}
						else if ("t_vo_live".equals(renameTable[i]))
						{
							// ֱ������������������
//							BaseVideoFileDAO.getInstance().createIndex(
//									renameTable[i], videoSync_ID);
							BaseVideoFileDAO.getInstance().createIndex(
									"t_vo_live_id", videoSync_ID);
						}

						BaseVideoFileDAO.getInstance().createIndex(
								renameTable[i], videoSync_ID);

						mailText.append("�������ʱ�䣺");
						mailText.append(PublicUtil
								.getCurDateTime("yyyy-MM-dd HH:mm:ss")
								+ "<br>");
					}

				}
				catch (BOException e)
				{
					mailText.append(e);
					mailText.append(" ��������ʧ�ܣ�����<br>");
				}

				try
				{
					mailText.append("����ͬ��ԭ��ʼ��");
					mailText.append(PublicUtil
							.getCurDateTime("yyyy-MM-dd HH:mm:ss")
							+ "<br>");
					for (int i = 0; i < len; i++)
					{
						bakTable = renameTable[i] + bakSuffix;
						defTable = renameTable[i];
						BaseVideoFileDAO.getInstance().createBackupSql(
								bakTable, defTable);
						mailText.append("����ͬ��ԭ��:" + defTable);
						mailText.append("��Ϊbak��:" + bakTable);
						mailText.append(" �ɹ�������<br>");
					}

				}
				catch (BOException e)
				{
					mailText.append("ͬ��ԭ��" + defTable);
					mailText.append(" ����bak��:" + bakTable);
					mailText.append(" ʧ�ܣ�����<br>");
				}

				try
				{

					for (int i = 0; i < len; i++)
					{
						if (logger.isDebugEnabled())
						{
							logger.debug("renameTable[" + i + "]="
									+ renameTable[i]);
						}
						defTable = renameTable[i] + defSuffix;
						tempTable = renameTable[i] + tempSuffix;

						BaseVideoFileDAO.getInstance().renameTable(
								renameTable[i], tempTable, defTable);
						mailText.append("�޸�ͬ�������ɹ�: ");
						mailText.append("   ���ʱ�䣺"
								+ PublicUtil
										.getCurDateTime("yyyy-MM-dd HH:mm:ss")
								+ "<br>");
						mailText.append("   renameTable:" + renameTable[i]
								+ " tempTable:" + tempTable + " defTable:"
								+ defTable + "<br/>");

					}
					mailText.append("ɾ������bak��ʼ��");
					mailText.append(PublicUtil
							.getCurDateTime("yyyy-MM-dd HH:mm:ss")
							+ "<br>");
					// �����ɹ��� ɾ�����б��ݱ�
					for (int i = 0; i < len; i++)
					{
						bakTable = renameTable[i] + bakSuffix;

						BaseVideoFileDAO.getInstance().delBackupSql(bakTable);
						mailText.append(" ɾ������bak��:" + bakTable);
						mailText.append(" �ɹ�������<br>");
					}

				}
				catch (BOException e)
				{
					logger.error("renameDataSync error:", e);
					mailText.append(e.getMessage());
				}

			}
			else
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("�������ͬ��������Ϊ��");
				}
				mailText.append("�������ͬ��������Ϊ��<br>");
				mailText.append("�������ͬ����" + renameTables);
			}
		}
		catch (Exception e)
		{
			logger.error("renameDataSync error:", e);
			mailText.append("ͬ����ͳ�Ʋ�ѯʧ��");
			mailText.append(e);
		}
		finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("���͸ı����ʼ�");
			}

			sendResultMail("������Ƶ����ͬ����ı�������ʼ�", mailText);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("�ı����������");
		}

	}
	
	/**
	 * ������ѯӦ�õ����ű������
	 * @return
	 */
	public StringBuffer addIndex()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("addIndex() strat");
		}

		StringBuffer text = new StringBuffer();
		text.append("������ѯӦ�õ����ű��������ʼ��");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");

		// Ĭ�ϵı�����׺ _tra
		String defSuffix = BaseVideoConfig.defSuffix;
		String[] renameTable = new String[]
		{ "t_vo_node", "t_vo_category", "t_vo_program" };
		
		int len = renameTable.length;

		if (logger.isDebugEnabled())
		{
			logger.debug("defSuffix=" + defSuffix);
		}

		try
		{
			int videoSync_ID = DB.getSeqValue("SEQ_VIDEO_SYNC_ID");

			for (int i = 0; i < len; i++)
			{
				text.append("����" + renameTable[i] + defSuffix + "�������ʱ�䣺");
				text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss")
						+ "<br>");

				if ("t_vo_category".equals(renameTable[i]))
				{
					// ���ܱ���������������
					BaseVideoFileDAO.getInstance().createIndex(
							"t_vo_category_id", videoSync_ID);
					BaseVideoFileDAO.getInstance().createIndex(
							"t_vo_category_pid", videoSync_ID);

				}
				
				if ("t_vo_program".equals(renameTable[i]))
				{
					// ���ܱ���������������
					BaseVideoFileDAO.getInstance().createIndex(
							"t_vo_program_nodeid", videoSync_ID);
				}

				BaseVideoFileDAO.getInstance().createIndex(renameTable[i],
						videoSync_ID);

				text.append("�������ʱ�䣺");
				text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss")
						+ "<br>");
			}

		}
		catch (BOException e)
		{
			text.append(e);
			text.append(" ��������ʧ�ܣ�����<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("dropIndex() end");
		}
		text.append("������ѯӦ�õ����ű������������");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}
	

	public StringBuffer dropIndex()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("dropIndex() strat");
		}
		StringBuffer text = new StringBuffer();
		text.append("ɾ��ͬ����������ʼ��");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// ��ȡ����ͬ�����Ӧ����������
			List indexNames = BaseVideoFileDAO.getInstance().queryDropIndex(
					BaseVideoConfig.renameTables, BaseVideoConfig.defSuffix);
			// ɾ��ͬ��������
			BaseVideoFileDAO.getInstance().dropIndex(indexNames);
			text.append("ɾ��ͬ���������ɹ�<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("dropIndex() end");
		}
		text.append("ɾ��ͬ��������������");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}

	private StringBuffer updateTable()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateTable() strat");
		}
		StringBuffer text = new StringBuffer();
		text.append("ɾ������ͬ��_TRA��ʱ�����ݿ�ʼ��");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// ɾ������ͬ����ʱ������
			BaseVideoFileDAO.getInstance().truncateTempSync(
					BaseVideoConfig.renameTables, BaseVideoConfig.defSuffix);
			text.append("ɾ������ͬ��_TRA��ʱ�����ݳɹ�<br>");

			// ����ͬ����ʽ�����ݵ���ʱ��
			text.append("����ͬ����ʽ�����ݵ�_TRA��ʱ��ʼ��");
			text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss")
					+ "<br>");
			BaseVideoFileDAO.getInstance().insertTempSync(
					BaseVideoConfig.renameTables, BaseVideoConfig.defSuffix);
			text.append("����ͬ����ʽ�����ݵ�_TRA��ʱ��ɹ�<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("updateTable() end");
		}
		text.append("��ʱ�����ݸ�����ϣ�");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}

	public StringBuffer dropIndex(String tableType)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("dropIndex() strat");
		}
		StringBuffer text = new StringBuffer();
		text.append("ɾ��"+ getTableByVideoType(tableType) +"ͬ����������ʼ��");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// ��ȡ����ͬ�����Ӧ����������
			List indexNames = BaseVideoFileDAO.getInstance().queryDropIndex(
					getTableByVideoType(tableType), BaseVideoConfig.defSuffix);
			// ɾ��ͬ��������
			BaseVideoFileDAO.getInstance().dropIndex(indexNames);
			text.append("ɾ��"+ getTableByVideoType(tableType) +"ͬ���������ɹ�<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("dropIndex() end");
		}
		text.append("ɾ��"+ getTableByVideoType(tableType) +"ͬ��������������");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}

	public StringBuffer updateTable(String tableType)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateTable() strat");
		}
		StringBuffer text = new StringBuffer();
		text.append("ɾ��"+ getTableByVideoType(tableType) +"ͬ��_TRA��ʱ�����ݿ�ʼ��");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// ɾ������ͬ����ʱ������
			BaseVideoFileDAO.getInstance().truncateTempSync(
					getTableByVideoType(tableType), BaseVideoConfig.defSuffix);
			text.append("ɾ��"+ getTableByVideoType(tableType) +"ͬ��_TRA��ʱ�����ݳɹ�<br>");

			// ����ͬ����ʽ�����ݵ���ʱ��
			text.append("����ͬ��"+ getTableByVideoType(tableType) +"��ʽ�����ݵ�_TRA��ʱ��ʼ��");
			text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss")
					+ "<br>");
			BaseVideoFileDAO.getInstance().insertTempSync(
					getTableByVideoType(tableType), BaseVideoConfig.defSuffix);
			text.append("����"+ getTableByVideoType(tableType) +"ͬ����ʽ�����ݵ�_TRA��ʱ��ɹ�<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("updateTable() end");
		}
		text.append(getTableByVideoType(tableType) +"��ʱ�����ݸ�����ϣ�");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}
	
	/**
	 * ����ͬ�����ͷ���ָ������
	 * @param synVideoType
	 * @return
	 */
	private String getTableByVideoType(String synVideoType)
	{
		String temp = "";
		
		if (BaseVideoConfig.FILE_TYPE_VIDEO.equals(synVideoType)
				|| BaseVideoConfig.FILE_TYPE_VIDEO_ADD.equals(synVideoType))
		{
			temp = "t_vo_video";
		}
		else if (BaseVideoConfig.FILE_TYPE_VIDEO_DETAIL.equals(synVideoType))
		{
			temp = "t_vo_program";
		}
		else if (BaseVideoConfig.FILE_TYPE_NODE.equals(synVideoType))
		{
			temp = "t_vo_node";
		}
		else if (BaseVideoConfig.FILE_TYPE_LIVE.equals(synVideoType))
		{
			temp = "t_vo_live";
		}
		else if ("0".equals(synVideoType))
		{
			temp = "t_vo_reference,t_vo_category";
		}
			
		return temp;
	}
	
	
	/**
	 * ����ͳ������Ŀ������Ŀ�½�Ŀ��
	 */
	public void updateCategoryNodeNum()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("ͳ������Ŀ������Ŀ�½�Ŀ��, ��ʼ");
		}

		StringBuffer mailText = new StringBuffer();
		int status = 0;

		status = BaseVideoFileDAO.getInstance().callUpdateCategoryNum();

		if (logger.isDebugEnabled())
		{
			logger.debug("ͳ������Ŀ������Ŀ�½�Ŀ��, ����,ͳ�ƽ��status=" + status);
		}
		if (status == 0)
		{
			mailText.append("������Ƶͳ�������Ŀ������Ŀ�½�Ŀ����ɹ�success��");
		}
		else
		{
			mailText.append("������Ƶͳ�������Ŀ������Ŀ�½�Ŀ���ʧ�ܣ�failed����鿴�洢������־");
		}

		sendResultMail("������Ƶͳ������Ŀ������Ŀ�½�Ŀ������ʼ�", mailText);
	}
}
