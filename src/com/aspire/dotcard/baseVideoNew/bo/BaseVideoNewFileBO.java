/*
 * �ļ�����BaseVideoFileBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideoNew.bo;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
import com.aspire.dotcard.baseVideoNew.dao.BaseVideoNewFileDAO;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseFileFactory;
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
public class BaseVideoNewFileBO
{
	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseVideoNewFileBO.class);
	
	private static BaseVideoNewFileBO bo = new BaseVideoNewFileBO();
	
	private BaseVideoNewFileBO()
	{}
	
	public static BaseVideoNewFileBO getInstance()
	{
		return bo;
	}
	
	/**
	 * ���ͽ���ʼ���
	 */
	public void sendResultMail(String mailTitle, StringBuffer mailText)
	{
		logger.info(mailText.toString());
		Mail.sendMail(mailTitle, mailText.toString(), BaseVideoConfig.mailTo);
	}
	
	/**
	 * ɾ��ͬ���м��ǰ����
	 * 
	 * @return
	 */
	public StringBuffer delMidTable(String tableName)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("delMidTable() strat");
		}
		StringBuffer text = new StringBuffer();
		text.append("ɾ������ͬ��_mid�м�����ݿ�ʼ��");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// ɾ������ͬ����ʱ������
			BaseVideoNewFileDAO.getInstance().truncateTempSync(tableName,
					BaseVideoNewConfig.midDefSuffix);
			text.append("ɾ������ͬ��_mid�м�����ݳɹ�<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("delMidTable() end");
		}
		text.append("mid�м������������ϣ�");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}
	
	/**
	 * ɾ��ͬ����Ƶȫ����ʱ��ǰ����
	 * 
	 * @return
	 */
	public StringBuffer delVideoFullTable(String tableName)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("delVideoFullTable() strat");
		}
		StringBuffer text = new StringBuffer();
		text.append("ɾ������ͬ����Ƶȫ����ʱ�����ݿ�ʼ��");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// ɾ��ͬ����Ƶȫ����ʱ������
			BaseVideoNewFileDAO.getInstance().truncateTempSync(tableName,BaseVideoNewConfig.fullDefSuffix);
			text.append("ɾ��ͬ����Ƶȫ����ʱ�����ݳɹ�<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("delVideoFullTable() end");
		}
		text.append("��Ƶȫ����ʱ������������ϣ�");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}
	
	/**
	 * ����ͬ�����������ļ�
	 */
	public void fileDataSync()
	{
		StringBuffer mailText = new StringBuffer();
		
		// ��Ƶ�ļ�������������
		mailText.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_VIDEO_ADD).execution(false));
		
		
		// ��Ŀ�ļ����ݵ���
		mailText.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_NODE).execution(false));
		
		// ��Ŀ���������ݵ���
		mailText.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_VIDEO_DETAIL).execution(false));
		
		//ɾ����Ƶ�ļ��м���ڴ���ʽ��Ƶ����
		mailText.append(deleteVideoMidData());
		//ɾ����Ƶ�ļ���ʽ���ڴ���ʽ��Ƶ����
		mailText.append(deleteVideoData());
		mailText.append("<br><br>");
		// ֱ����Ŀ�����ݵ���
		mailText.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_LIVE).execution(false));
		
		// ��Ʒ���ݵ���
		mailText.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_PRODUCT).execution(false));
		
		// ��Ƶ��Ŀͳ��
		mailText.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_VIDEODETAIL).execution(false));
		
		sendResultMail("������Ƶ����ͬ������ʼ�", mailText);
	}
	
	/**
	 * ���ڲ���Ԥɾ��������ָ���м��
	 * 
	 * @param sql
	 * @param key
	 * @return
	 */
	public boolean delDataByKey(String sql, String[] key)
	{
		boolean isTrue = true;
		try
		{
			BaseVideoNewFileDAO.getInstance().delDataByKey(sql, key);
		}
		catch (BOException e)
		{
			isTrue = false;
			logger.debug("ִ�в���Ԥɾ��������ָ���м��ʱ�������󣡣���", e);
		}
		return isTrue;
	}
	
	/**
	 * ���ô洢���� ����ִ���м������ʽ��������ת��
	 */
	public boolean syncVideoData()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("���ô洢���� ����ִ���м������ʽ��������ת��, ��ʼ");
		}
		
		StringBuffer mailText = new StringBuffer();
		int status = 0;
		
		status = BaseVideoNewFileDAO.getInstance().callSyncVideoData();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("ִ���м������ʽ��������ת��, ����,ִ�н��status=" + status);
		}
		if (status != 0)
		{
			mailText.append("������Ƶִ���м������ʽ��������ת�ƽ���ɹ�success��");
		}
		else
		{
			mailText.append("������Ƶִ���м������ʽ��������ת�ƽ��ʧ�ܣ���������ģ����ؽ�����ȡ����������鿴�洢������־");
		}
		
		sendResultMail("������Ƶִ���м������ʽ��������ת�ƽ���ʼ�", mailText);
		
		return status != 0 ? true : false;
	}
	
	/**
	 * ���ô洢���� ����ִ����Ƶȫ����ʱ�����м��������ת��
	 */
	public boolean syncVideoFullData()
	{
		//if (logger.isDebugEnabled())
		//{
			logger.info("���ô洢���� ����ִ����Ƶȫ����ʱ�����м��������ת��, ��ʼ");
		//}
		
		StringBuffer mailText = new StringBuffer();
		int status = 0;
		
		status = BaseVideoNewFileDAO.getInstance().callSyncVideoFullData();
		
		//if (logger.isDebugEnabled())
		//{
			logger.info("ִ����Ƶȫ����ʱ�����м��������ת��, ����,ִ�н��status=" + status);
		//}
		if (status != 0)
		{
			mailText.append("������Ƶִ����Ƶȫ����ʱ�����м��������ת�ƽ���ɹ�success��");
		}
		else
		{
			mailText.append("������Ƶִ����Ƶȫ����ʱ�����м��������ת�ƽ��ʧ�ܣ���������鿴�洢������־");
		}
		
		sendResultMail("������Ƶִ����Ƶȫ����ʱ�����м��������ת�ƽ���ʼ�", mailText);
		
		return status != 0 ? true : false;
	}
	
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
			ret = BaseVideoNewFileDAO.getInstance().cleanOldSimulationData();
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
			sf.append(BaseVideoNewFileDAO.getInstance().insertDataToTree());
			
			// ��װ�Զ���ĸ�֧�ṹ
			sf
					.append(BaseVideoNewFileDAO.getInstance()
							.insertDataToReference());
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
		
		status = BaseVideoNewFileDAO.getInstance().callUpdateCategoryNum();
		
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
	
	/**
	 * ��ѯ�м��洢������������ʼ���ʾ
	 */
	public StringBuffer getMailText()
	{
		StringBuffer sb = new StringBuffer();
		String[] tableName = new String[] { "T_VO_VIDEO_MID",
				"T_VO_VIDEODETAIL_MID", "t_vo_program_mid", "t_vo_product_mid",
				"t_vo_node_mid", "t_vo_live_mid" };
		logger.info("��ѯ�м��洢������������ʼ���ʾ, ��ʼ");
		
		sb.append("<br>ͳ���м�����ݷ������:<br>");
		
		// �õ���ͬ�м�������ݵķ�����Ϣ�����Է������ʼ�
		for (String name : tableName)
		{
			sb.append(name + "�����������Ϊ��").append("<br>");
			sb.append(BaseVideoNewFileDAO.getInstance()
					.getMidTableGroupBy(name));
			sb.append("<br>");
		}
		
		logger.info("��ѯ�м��洢������������ʼ���ʾ, ����");
		return sb;
	}
	
	/**
	 * ����ͬ����Ƶ���ݼ������ݼ��ڵ������ļ�
	 */
	public void syncCollectAndNodeData(){
		StringBuffer msgInfo = new StringBuffer();
		//����ͬ����Ƶ���ݼ��ڵ������ļ�
		msgInfo.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_COLLECT_NODE).execution(false));
		// ����ͬ����Ƶ���ݼ������ļ�
		msgInfo.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_COLLECT).execution(false));
		msgInfo.append("<br>");
		//ɾ�����ݼ���ĿΪ�յ����ݼ������ݼ��ڵ�,2015-03-18 add
		deleteCollectData(msgInfo);
		BaseVideoNewFileBO.getInstance().sendResultMail("������Ƶ���ݼ��ڵ㼰���ݼ�����ͬ������ʼ�", msgInfo);
		//������Ŀ������ݼ��ڵ������ݼ���ʶ�ֶ��趨Ϊ1
		BaseVideoNewFileDAO.getInstance().updateCollectflag();
	}
	
	public void costProductRelation(){
		StringBuffer msgInfo = new StringBuffer();
		//����ͬ����Ƶ��Ʒ���۹�ϵ��Ϣ�ӿ������ļ�
		msgInfo.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_COST).execution(false));
		msgInfo.append("<br>");
		BaseVideoNewFileBO.getInstance().sendResultMail("������Ƶ��Ʒ���۹�ϵ��Ϣ����ͬ������ʼ�", msgInfo);
	}
	
	/**
	 * ɾ����Ƶ�ļ���ʽ���ڴ���ʽ��Ƶ����
	 * 
	 * @return
	 */
	public StringBuffer deleteVideoData()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("deleteVideoData() strat");
		}
		StringBuffer text = new StringBuffer();
		text.append("ɾ����Ƶ�ļ���ʽ���ڴ���ʽ��Ƶ���ݿ�ʼ��");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// ɾ����Ƶ��ʽ���ڴ���ʽ��Ƶ����
			text.append(BaseVideoNewFileDAO.getInstance().deleteVideoData());
		}
		catch (BOException e)
		{
			logger.debug("�����Ƶ�ļ���ʽ���ڴ���ʽ��Ƶ����,ʧ�ܣ�" + e.getMessage());
			text.append("�����Ƶ�ļ���ʽ���ڴ���ʽ��Ƶ����,ʧ�ܣ�" + e.getMessage());
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("deleteVideoData() end");
		}
		text.append("��Ƶ�ļ���ʽ���ڴ���ʽ��Ƶ����������ϣ�");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}
	
	/**
	 * ɾ����Ƶ��Ŀ��ʽ���ڴ���ʽ��Ƶ����
	 * 
	 * @return
	 */
	public StringBuffer deleteProgramData()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("deleteProgramData() strat");
		}
		StringBuffer text = new StringBuffer();
		text.append("ɾ����Ƶ��Ŀ��ʽ���ڴ���ʽ��Ƶ���ݿ�ʼ��");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// ɾ����Ƶ��Ŀ��ʽ���ڴ���ʽ��Ƶ����
			text.append(BaseVideoNewFileDAO.getInstance().deleteProgramData());
		}
		catch (BOException e)
		{
			logger.debug("�����Ƶ��Ŀ��ʽ���ڴ���ʽ��Ƶ����,ʧ�ܣ�" + e.getMessage());
			text.append("�����Ƶ��Ŀ��ʽ���ڴ���ʽ��Ƶ����,ʧ�ܣ�" + e.getMessage());
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("deleteProgramData() end");
		}
		text.append("��Ƶ��Ŀ��ʽ���ڴ���ʽ��Ƶ����������ϣ�");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}
	
	/**
	 * ɾ����Ƶ�м���ڴ���ʽ��Ƶ����
	 * 
	 * @return
	 */
	public StringBuffer deleteVideoMidData()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("deleteVideoMidData() strat");
		}
		StringBuffer text = new StringBuffer();
		text.append("ɾ����Ƶ�ļ��м���ڴ���ʽ��Ƶ���ݿ�ʼ��");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// ɾ����Ƶ�ļ��м���ڴ���ʽ��Ƶ����
			text.append(BaseVideoNewFileDAO.getInstance().deleteVideoMidData());
		}
		catch (BOException e)
		{
			logger.debug("�����Ƶ�ļ��м���ڴ���ʽ��Ƶ����,ʧ�ܣ�" + e.getMessage());
			text.append("�����Ƶ�ļ��м���ڴ���ʽ��Ƶ����,ʧ�ܣ�" + e.getMessage());
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("deleteVideoMidData() end");
		}
		text.append("��Ƶ�ļ��м���ڴ���ʽ��Ƶ����������ϣ�");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}
	
	/**
	 * ���ô洢���� ����ִ��ɾ�����ݼ���ĿΪ�յ����ݼ������ݼ��ڵ�
	 */
	public void deleteCollectData(StringBuffer msgInfo)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("���ô洢���� ����ִ��ɾ�����ݼ���ĿΪ�յ����ݼ������ݼ��ڵ�, ��ʼ");
		}
		int status = 0;
		
		status = BaseVideoNewFileDAO.getInstance().deleteCollectData();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("ִ��ɾ�����ݼ���ĿΪ�յ����ݼ������ݼ��ڵ�, ����,ִ�н��status=" + status);
		}
		if (status != 0)
		{
			msgInfo.append("ɾ�����ݼ���ĿΪ�յ����ݼ������ݼ��ڵ�ɹ�success��");
		}
		else
		{
			msgInfo.append("ɾ�����ݼ���ĿΪ�յ����ݼ������ݼ��ڵ�ʧ�ܣ�����������鿴�洢������־");
		}

	}
}
