/*
 * �ļ�����ContentExigenceBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.ponaadmin.web.repository.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.syncData.bo.DataSynOpration;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.dotcard.syncData.tactic.TacticBO;
import com.aspire.dotcard.syncData.tactic.TacticVO;
import com.aspire.dotcard.syncData.util.SyncDataConstant;
import com.aspire.dotcard.syncData.vo.ContentTmp;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.dataexport.basefile.impl.TempExigenceFile;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.system.Config;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class ContentExigenceBO
{
	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(ContentExigenceBO.class);
	private static ContentExigenceBO instance = new ContentExigenceBO();
	
	/**
	 * ��ǰ������Ƿ���ִ����
	 */
	private boolean isLock = false;
	
	private ContentExigenceBO()
	{}
	
	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static ContentExigenceBO getInstance()
	{
		return instance;
	}
	
	/**
	 * ͬ����ʼʱ��
	 */
	private Date startDate;
	
	/**
	 * ͬ������ʱ��
	 */
	private Date endDate;
	
	/**
	 * ����������
	 */
	private TaskRunner dataSynTaskRunner;
	
	/**
	 * ��ѯ����Ľ�Ҫ�������ߵ������б�
	 * 
	 * @param page
	 * @throws BOException
	 */
	public void queryContentExigenceList(PageResult page) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger
					.debug("ContentExigenceBO.queryContentExigenceList( ) is start...");
		}
		
		try
		{
			// ����ContentExigenceDAO���в�ѯ
			ContentExigenceDAO.getInstance().queryContentExigenceList(page);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("��ѯ����Ľ�Ҫ�������ߵ������б�ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * ɾ���������ߵ������б�
	 * 
	 * @param ids
	 * @throws BOException
	 */
	public void delContentExigence(String[] ids) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("ContentExigenceBO.delContentExigence( ) is start...");
		}
		
		try
		{
			// ����ContentExigenceDAO����ɾ��
			ContentExigenceDAO.getInstance().delContentExigence(ids);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("��ѯ����Ľ�Ҫ�������ߵ������б�ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * ����������ߵ������б�
	 * 
	 * @param dataFile
	 * @throws BOException
	 */
	public String importContentExigence(FormFile dataFile) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger
					.debug("ContentExigenceBO.importContentExigence( ) is start...");
		}
		
		StringBuffer ret = new StringBuffer();
		
		try
		{
			// ���ԭ�б�
			ContentExigenceDAO.getInstance().delAllContentExigence();
			
			// ����EXECL�ļ�����ȡ�ն�����汾�б�
			List list = this.paraseDataFile(dataFile);
			
			// У��ļ��Д����Ƿ���ҕ�D�д���
			String temp = ContentExigenceDAO.getInstance()
					.verifyContentExigence(list);
			
			// ����ContentExigenceDAO���е���
			ContentExigenceDAO.getInstance().importContentExigence(list);
			
			ret.append("�ɹ�����" + list.size() + "����¼.");
			
			if (!"".equals(temp))
			{
				ret.append("���벻�ɹ�idΪ").append(temp);
			}
			
			return ret.toString();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("����������ߵ������б�ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * ����EXECL�ļ�����ȡҪ��ӵ���Ʒ��Ϣ
	 * 
	 * @param dataFile
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	private List paraseDataFile(FormFile dataFile) throws BOException
	{
		logger.info("ContentExigenceBO.paraseDataFile() is start!");
		List list = new ArrayList();
		Workbook book = null;
		
		try
		{
			book = Workbook.getWorkbook(dataFile.getInputStream());
			Sheet[] sheets = book.getSheets();
			int sheetNum = sheets.length;
			if (logger.isDebugEnabled())
			{
				logger.debug("paraseSoftVersion.sheetNum==" + sheetNum);
			}
			
			for (int i = 0; i < sheetNum; i++)
			{
				int rows = sheets[i].getRows();
				
				// int columns = sheets[i].getColumns();
				int columns = 0;
				
				for (int j = 0; j < rows; j++)
				{
					String value = sheets[i].getCell(columns, j).getContents()
							.trim();
					// ����Օr
					if (!"".equals(value))
					{
						// ��������д��ڴ�����
						if (list.contains(value))
						{
							// ɾ��ԭ�����ڵ�����
							list.remove(value);
						}
						
						list.add(value);
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.error("���������ļ������쳣,fineName:" + dataFile.getFileName(), e);
			throw new BOException("���������ļ������쳣", e);
		}
		finally
		{
			book.close();
		}
		return list;
	}
	
	/**
	 * ͬ��������������.
	 * 
	 * @param exeContent
	 *            ��δ������ݱ����� 1�������������ݱ���Ϣ 0��ȫ���������ݱ���Ϣ��Ĭ�ϣ�
	 * @param isTimer
	 *            �Ƿ�Ϊ��������ʱ����
	 * @param exeDeviceType
	 *            ִ�������ϵ���� 1��ִ�� 0����ִ��
	 * 
	 * @throws BOException
	 */
	public void SyncContentExigence(String exeContent, boolean isTimer,
			String exeDeviceType) throws BOException
	{
		// ��
		isLock = true;
		
		// ��������
		String content = null;
		
		// ͬ�������������
		List[] mailInfo = new ArrayList[4];
		
		try
		{
			startDate = new Date();
			
			// ����Ƕ���������������Ҫ�����µ�������������������ʱ����
			if (isTimer)
			{
				logger.info("�Զ�������������߿�ʼ");
				
				// ���ԭ�б�
				ContentExigenceDAO.getInstance().delAllContentExigence();
				
				logger.info("�Զ����������������ս���");
				
				// �ɵ��������������������Ϣ
				ContentExigenceDAO.getInstance().sysContentExigence(
						ContentExigenceDAO.getInstance()
								.getSysContentExigenceDate());
				
				logger.info("�Զ�������������ߵ������ݽ���");
				
				// д��������ʱ��
				ContentExigenceDAO.getInstance().addExigenceLastTime();
				
				// ������δ���������������Ϊ��
				if (!ContentExigenceDAO.getInstance().hasExigenceContent())
				{
					endDate = new Date();
					logger.error("���δ���������������Ϊ�գ��жϽ��������߲���������");
					// ����쳣�͵����ʼ����ͽӿڷ��������Ա
					content = "���δ���������������Ϊ�գ��жϽ��������߲���������ϵ����Ա��";
					this.sendMail(content, SyncDataConstant.CONTENT_TYPE);
					throw new BOException("���δ���������������Ϊ�գ��жϽ��������߲���������");
				}
			}
			
			List<String> contentIdList = ContentExigenceDAO.getInstance()
					.queryExigenceIdListByType();
			
			// �����������ݱ���Ϣ
			if ("1".equals(exeContent))
			{
				// ����ͼv_cm_content��������Ϣ������������
				updateVcontentDate(contentIdList);
			}
			else
			{
				// ȫ��������ͼv_cm_content����
				DataSyncDAO.getInstance().initViewToTable();
			}
			
			// ��ս��������߲�����ʷ��������Ϣ
			delGoodsChangeHis();
			
			// ����������ǰ���v_service����������Ϣ
			updateServiceDate(contentIdList);
			
			// ����������ǰ���cm_ct_appgame��չ����������Ϣ
			updateCMCTAPPDate(contentIdList);
			
			// ͬ��ǰ������ʱ��
			addSyncContentTmp(false);
			
			// ͬ��
			mailInfo = this.syncContent(false, RepositoryConstants.SYN_HIS_YES,
					exeDeviceType);
			
			// ͬ������ʱ��
			endDate = new Date();
			
			// ���ʼ�
			listToMail(mailInfo);
			
			// ƴװ����ͬ�����Զ����� �������֪ͨ����Ϣ����
			content = this.assemblePhoneMsg("����", mailInfo);
			
			// ����ͬ�������
			DataSyncDAO.getInstance().insertSynResult(mailInfo);
			
			// ��д�û�������е�����ͬ������
			updateContentExigenceType(mailInfo);
			
			// ��ս�������Ӧ����ʱ������
			// ContentExigenceDAO.getInstance().delAllContentExigence();
			
			// ����Ƕ���������������Ҫ��ս�������Ӧ����ʱ������
			if (isTimer)
			{
				// ContentExigenceDAO.getInstance().delAllContentExigence();
			}
			
			// ���ɽ��������������ļ�
			createExigenceFile();
		}
		catch (BOException e)
		{
			logger.error("���Զ������׳����� �� ���δ���������������Ϊ�գ��жϽ��������߲���������", e);
		}
		catch (Exception e)
		{
			endDate = new Date();
			logger.error("����ͬ��������������ʧ�ܡ�", e);
			// ����쳣�͵����ʼ����ͽӿڷ��������Ա
			content = "����ͬ��������������ʧ�ܣ�����ϵ����Ա��";
			this.sendMail(content, SyncDataConstant.CONTENT_TYPE);
			throw new BOException("����ͬ��������������ʧ�ܡ���");
		}
		finally
		{
			this.sendPhoneMsg("��������ͬ��", content);
			isLock = false;
		}
	}
	
	/**
	 * ���ɽ��������������ļ�
	 */
	private void createExigenceFile() throws BOException
	{
		TempExigenceFile app = new TempExigenceFile();
		app.createFile();
	}
	
	/**
	 * ��ս��������߲�����ʷ��������Ϣ
	 * 
	 * @throws BOException
	 */
	private void delGoodsChangeHis() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("ContentExigenceBO.delGoodsChangeHis( ) is start...");
		}
		
		try
		{
			// ����ContentExigenceDAO���и���
			ContentExigenceDAO.getInstance().delGoodsChangeHis();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("��ս��������߲�����ʷ��������Ϣʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * ����������ǰ���v_service����������Ϣ
	 * 
	 * @throws BOException
	 */
	private void updateServiceDate(List<String> contentIdList) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("ContentExigenceBO.updateServiceDate( ) is start...");
		}
		
		try
		{
			// ����ContentExigenceDAO���и���
			ContentExigenceDAO.getInstance().updateServiceDate(contentIdList);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("����������ǰ���v_service����������Ϣʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * ����������ǰ����������ͼv_cm_content��������Ϣ
	 * 
	 * @throws BOException
	 */
	private void updateVcontentDate(List<String> contentIdList) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("ContentExigenceBO.updateVcontentDate( ) is start...");
		}
		
		try
		{
			// ����ContentExigenceDAO���и���
			ContentExigenceDAO.getInstance().updateVcontentDate(contentIdList);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("����������ǰ����������ͼv_cm_content��������Ϣʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * ����������ǰ���cm_ct_appgame��չ����������Ϣ
	 * 
	 * @throws BOException
	 */
	private void updateCMCTAPPDate(List<String> contentIdList) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("ContentExigenceBO.updateCMCTAPPDate( ) is start...");
		}
		
		try
		{
			// ����ContentExigenceDAO���и���
			ContentExigenceDAO.getInstance().updateCMCTAPPDate(contentIdList);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("����������ǰ���cm_ct_appgame��չ����������Ϣʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * ��д�û�������е�����ͬ������
	 * 
	 * @param mailInfo
	 *            ͬ�����������Ϣ�б�
	 */
	private void updateContentExigenceType(List[] mailInfo) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger
					.debug("ContentExigenceBO.updateContentExigenceType( ) is start...");
		}
		
		// �ɹ����¡�
		List updateList = mailInfo[0];
		// �ɹ�����
		List addList = mailInfo[1];
		// �ɹ�����
		List deleteList = mailInfo[2];
		// �ɹ�����
		List errorList = mailInfo[3];
		
		try
		{
			// ����ContentExigenceDAO���и���
			ContentExigenceDAO.getInstance().updateContentExigenceType(addList,
					"1");
			ContentExigenceDAO.getInstance().updateContentExigenceType(
					updateList, "2");
			ContentExigenceDAO.getInstance().updateContentExigenceType(
					deleteList, "3");
			ContentExigenceDAO.getInstance().updateContentExigenceType(
					errorList, "4");
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("��д�û�������е�����ͬ������ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * ����Ҫͬ���������б�������id������������ʱ����뵽���ݿ��С�
	 * 
	 * @param isFull
	 *            �Ƿ�Ϊȫ��ͬ����trueΪȫ����falseΪ����
	 * 
	 */
	private void addSyncContentTmp(boolean isFull) throws BOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("addSyncContentTmp()");
		}
		// �����������
		TransactionDB tdb = null;
		try
		{
			tdb = TransactionDB.getTransactionInstance();
			DataSyncDAO dao = DataSyncDAO.getTransactionInstance(tdb);
			
			// ����DataSyncDAO�е�addSyncContenTmpt������
			dao.addContentTmp();
			
			// ��־
			logger.info("��������Ӧ�ü�����ͬ������������ʱ���С�");
			// �ύ�������
			tdb.commit();
		}
		catch (Exception e)
		{
			// ִ�лع�
			tdb.rollback();
			throw new BOException("db error!", e);
		}
		finally
		{
			if (tdb != null)
			{
				tdb.close();
			}
		}
	}
	
	/**
	 * ��CMS�е�����ͬ����PAS���ݿ��У������ͽ���ʼ���
	 * 
	 * @param isFull
	 *            �����Ƿ���ȫ��ͬ��
	 * @param isSyn
	 *            �Ƿ��ǽ�������Ӧ��
	 * @param exeDeviceType
	 *            ִ�������ϵ���� 1��ִ�� 0����ִ��
	 */
	private List[] syncContent(boolean isFull, String isSyn,
			String exeDeviceType) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("syncContent()");
		}
		
		List tacticList = null;
		List list = new ArrayList();
		try
		{
			// ��ȡ���е�CMS����ͬ������
			tacticList = new TacticBO().queryAll();
			if (null == tacticList && logger.isDebugEnabled())
			{
				logger.debug("��ȡCMS����ͬ������Ϊ�գ���������ͬ��û�������ϼܵ������¡�");
			}
			else if (logger.isDebugEnabled())
			{
				logger.debug("CMS����ͬ������:");
				for (int i = 0; i < tacticList.size(); i++)
				{
					logger.debug("[" + (i + 1) + " ] "
							+ (TacticVO) tacticList.get(i));
				}
			}
		}
		catch (Exception e)
		{
			String result = "��ȡSMS����ͬ�������쳣����������ͬ������������ϼܵ������¡�";
			logger.error(result, e);
			throw new BOException(result, e);
		}
		
		try
		{
			// ��ȡ��Ҫͬ����������Ϣ�б�
			list = DataSyncDAO.getInstance().getSyncContentTmp();
		}
		catch (DAOException e)
		{
			throw new BOException("��ȡ��ʱ�������쳣", e);
		}
		
		try
		{
			// �Ƿ�Ҫִ��ͬ�������ϵ
			if ("1".equals(exeDeviceType))
			{
				// �������500��Ϊȫ�����������������ϵ
				if (list.size() > 500)
				{
					// ȫ�����������ϵ��ͼ���
					logger.debug("�˴�Ϊȫ��ͬ�����������ϵ��");
					DataSyncDAO.getInstance().syncVCmDeviceResource();
				}
				else
				{
					// �������������ϵ��
					logger.debug("�˴�Ϊ����ͬ�����������ϵ��");
					
					// �ύ�������
					TransactionDB tdb = TransactionDB.getTransactionInstance();
					DataSyncDAO.getTransactionInstance(tdb)
							.syncVCmDeviceResourceAdd(list);
					tdb.commit();
				}
			}
		}
		catch (Exception e)
		{
			throw new BOException("ͬ�����������ϵ��ʱ�������쳣", e);
		}
		
		// ��ʼ������
		DataSyncDAO.getInstance().prepareDate();
		// ���õ���list��Ϊ�������뷽��dealSyncContent��
		List[] mailInfo = this.dealSyncContent(list, tacticList, isSyn);
		
		// ��Ҫ�ٴ�ͬ�����Ѵ���gcontent�������� cm_content������ɾ����
		// list = this.againSynccontetTmp();// û�б�Ҫ�ٴ�ͬ�� if (list != null &
		// List[] againMailInfo = this.dealSyncContent(list, tacticList, isSyn);
		// mailInfo = DataSyncBO.addList(mailInfo, againMailInfo);
		
		// ��ջ��档
		DataSyncDAO.getInstance().clearDate();
		return mailInfo;
		
	}
	
	/**
	 * ����������ʱ���е����ݡ�
	 * 
	 * @param list
	 * @param isSyn
	 *            �Ƿ��ǽ�������Ӧ�á��Ƿ������ʷ��
	 * @return mailInfo String[] mail������Ϣ mail������Ϣ mail[0]Ϊ���µ�Ӧ����Ϣ mail[1]����Ӧ����Ϣ
	 *         mail[2]����Ӧ����Ϣ mail[3]��ʾ�������Ϣ
	 */
	private List[] dealSyncContent(List list, List tacticList, String isSyn)
			throws BOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("dealSyncContent()");
		}
		// �����б�
		int size = list.size();
		
		/**
		 * mail������Ϣ mail[0]Ϊ���µ�Ӧ����Ϣ mail[1]����Ӧ����Ϣ mail[2]����Ӧ����Ϣ mail[3]��ʾ�������Ϣ
		 */
		List[] mailInfoList = new List[4];
		mailInfoList[0] = new ArrayList();
		mailInfoList[1] = new ArrayList();
		mailInfoList[2] = new ArrayList();
		mailInfoList[3] = new ArrayList();
		String syncDataMaxNum = Config.getInstance().getModuleConfig()
				.getItemValue("syncDataMaxNum");// MM����ͬ�����߳�����
		
		int maxNum = Integer.valueOf(syncDataMaxNum).intValue();
		dataSynTaskRunner = new TaskRunner(maxNum, 0);
		
		List mttacticList = new TacticBO().queryMOTOAll();
		List htctacticList = new TacticBO().queryHTCAll();
		//2015-10-13 add,���㷺��������������������б�
        List channelstacticList = new TacticBO().queryChannelsCategoryAll();
		if (null == tacticList && logger.isDebugEnabled())
		{
			logger.debug("��ȡCMS����ͬ������Ϊ�գ���������ͬ��û�������ϼܵ������¡�");
		}
		logger.debug("׼��������̲߳��� list size =  " + size);
		
		if (size <= 20)
		{
			for (int i = 0; i < size; i++)
			{
				logger.debug("����ѭ��ͬ������");
				
				// �õ�ContentTmp����
				ContentTmp tmp = (ContentTmp) list.get(i);
				DataSynOpration cm = new DataSynOpration(tacticList,
						mttacticList, htctacticList,channelstacticList, tmp, mailInfoList, isSyn);
				cm.dataSynOp();
			}
		}
		else
		{
			for (int i = 0; i < size; i++)
			{
				logger.debug("������̲߳���");
				// �õ�ContentTmp����
				ContentTmp tmp = (ContentTmp) list.get(i);
				
				DataSynOpration cm = new DataSynOpration(tacticList,
						mttacticList, htctacticList,channelstacticList, tmp, mailInfoList, isSyn);
				// �����첽����
				ReflectedTask task = new ReflectedTask(cm, "dataSynOp", null,
						null);
				// ������ӵ���������
				dataSynTaskRunner.addTask(task);
			}
			logger.debug("�˳����̲߳���");
			dataSynTaskRunner.waitToFinished();
			dataSynTaskRunner.stop();
		}
		
		// ��ջ���
		DataSynOpration.cleanMap();
		
		return mailInfoList;
	}
	
	/*
	 * @throws BOException
	 */
	private List againSynccontetTmp() throws BOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("againSynccontetTmp()");
		}
		
		// ��ѯ����/��������ݣ����뵽��ʱ����
		this.againInsSyncContentTmp();
		
		// �˴�����õĲ�����ԭͬ���Ĳ�����ͬ
		// ����dao����getSyncContentTmp�õ�list;
		try
		{
			return DataSyncDAO.getInstance().getSyncContentTmp();
		}
		catch (DAOException e)
		{
			throw new BOException("�ٴ�ͬ������", e);
		}
		
	}
	
	/**
	 * ����Ҫͬ���������б���뵽t_synctime_tmp���С�
	 * 
	 * @author biran
	 */
	private void againInsSyncContentTmp() throws BOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("againInsSyncContentTmp()");
		}
		// �����������
		TransactionDB tdb = null;
		try
		{
			tdb = TransactionDB.getTransactionInstance();
			DataSyncDAO dao = DataSyncDAO.getTransactionInstance(tdb);
			
			// ����DataSyncDAO�е�againInsSyncContentTmp��������ѯ��CMS������/���������ҵ����������ݣ����뵽t_synctime_tmp����
			dao.againInsSyncContentTmp();
			
			// �ύ�������
			tdb.commit();
		}
		catch (Exception e)
		{
			// ִ�лع�
			tdb.rollback();
			throw new BOException("db error!", e);
		}
		finally
		{
			if (tdb != null)
			{
				tdb.close();
			}
		}
	}
	
	/**
	 * ƴװ����ͬ�����Զ����� �������֪ͨ����Ϣ����
	 * 
	 * @return
	 */
	private String assemblePhoneMsg(String type, List[] msgInfo)
	{
		
		StringBuffer sb = new StringBuffer();
		List updateList = msgInfo[0];// �ɹ����¡�
		List addList = msgInfo[1];// �ɹ�����
		List deleteList = msgInfo[2];// �ɹ�����
		List errorList = msgInfo[3];// ʧ��ͬ��
		PublicUtil.removeDuplicateWithOrder(errorList);// ȥ���ظ���¼ͬ��ʧ�ܵ����⡣
		int size = updateList.size() + addList.size() + deleteList.size()
				+ errorList.size();
		/*
		 * sb.append("����ͬ������������ͬ������Ϊ��" + size +
		 * "�����ݣ���������"+addList.size()+",������������Ϊ"+deleteList.size()+"����ͬ�����������Ϊ��" +
		 * errorList.size() + SyncDataConstant.CHANGE_LINE);
		 */
		sb.append("����" + type + "ͬ���ܹ�����");
		sb.append(size);
		sb.append("��Ӧ�á����гɹ�����Ӧ��");
		sb.append(addList.size());
		sb.append("����");
		sb.append("�ɹ�����Ӧ��");
		sb.append(deleteList.size());
		sb.append("����");
		sb.append("�ɹ�����Ӧ��");
		sb.append(updateList.size());
		sb.append("����");
		sb.append("ͬ��ʧ��");
		sb.append(errorList.size());
		sb.append("����");
		return sb.toString();
	}
	
	/**
	 * ����ͬ������Ϣ����mailInfo������mail
	 * 
	 * @param size
	 *            �ܹ�ͬ�������ݵ�����
	 * @param mailInfo
	 *            ͬ�����ݵ���Ϣ���� mailInfo[0]Ϊ�¼��������ݣ�mailInfo[1]Ϊ������������
	 * @author biran
	 */
	private void listToMail(List[] mailInfo)
	{
		// �����ʼ����ͽ��������ͬ����Ϣ���ʼ��������Ա.
		this.sendMail(this.assembleSyncContentMessage(mailInfo),
				SyncDataConstant.CONTENT_TYPE);
	}
	
	/**
	 * ƴװ������ͬ��������ʼ�����
	 * 
	 * @param errorList
	 * @param size
	 * @return
	 */
	private String assembleSyncContentMessage(List[] mailInfo)
	{
		
		List updateList = mailInfo[0];// �ɹ����¡�
		List addList = mailInfo[1];// �ɹ�����
		List deleteList = mailInfo[2];// �ɹ�����
		List errorList = mailInfo[3];// ʧ��ͬ��
		PublicUtil.removeDuplicateWithOrder(errorList);// ȥ���ظ���¼ͬ��ʧ�ܵ����⡣
		int size = updateList.size() + addList.size() + deleteList.size()
				+ errorList.size();
		StringBuffer sb = new StringBuffer();
		/*
		 * sb.append("����ͬ������������ͬ������Ϊ��" + size +
		 * "�����ݣ���������"+addList.size()+",������������Ϊ"+deleteList.size()+"����ͬ�����������Ϊ��" +
		 * errorList.size() + SyncDataConstant.CHANGE_LINE);
		 */
		sb.append("��ʼʱ�䣺");
		sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
		sb.append(",����ʱ�䣺");
		sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
		sb.append("��<h4>������������</h4>");
		sb.append("����ͬ���ܹ�����<b>");
		sb.append(size);
		sb.append("</b>��Ӧ�á����гɹ�����Ӧ��<b>");
		sb.append(addList.size());
		sb.append("</b>����");
		sb.append("�ɹ�����Ӧ��<b>");
		sb.append(deleteList.size());
		sb.append("</b>����");
		sb.append("�ɹ�����Ӧ��<b>");
		sb.append(updateList.size());
		sb.append("</b>����");
		sb.append("ͬ��ʧ��<b>");
		sb.append(errorList.size());
		sb.append("</b>����");
		
		if (size > 0)
		{
			sb.append("<h5>����ͬ������ϸ��Ϣ��</h5>");
		}
		boolean isFirst = true;
		for (int i = 0; i < addList.size(); i++)
		{
			if (isFirst)
			{
				sb.append("<p>Ӧ�����ߵ���ϢΪ��<br>");
				isFirst = false;
			}
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") "
					+ addList.get(i) + SyncDataConstant.CHANGE_LINE);
		}
		
		isFirst = true;
		for (int i = 0; i < deleteList.size(); i++)
		{
			if (isFirst)
			{
				sb.append("<p>Ӧ�����ߵ���ϢΪ:<br>");
				isFirst = false;
			}
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") "
					+ deleteList.get(i) + SyncDataConstant.CHANGE_LINE);
			
		}
		isFirst = true;
		for (int i = 0; i < updateList.size(); i++)
		{
			if (isFirst)
			{
				sb.append("<p>Ӧ�ø��µ���ϢΪ��<br>");
				isFirst = false;
			}
			if (i >= 100)// ֻ��Ҫչʾ100�����ɣ��������ʼ�̫��
			{
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") ������������"
						+ SyncDataConstant.CHANGE_LINE);
				break;
			}
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") "
					+ updateList.get(i) + SyncDataConstant.CHANGE_LINE);
		}
		isFirst = true;
		for (int i = 0; i < errorList.size(); i++)
		{
			if (isFirst)
			{
				sb.append("<p>Ӧ��ͬ��ʧ�ܵ���ϢΪ��<br>");
				isFirst = false;
			}
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") "
					+ errorList.get(i) + SyncDataConstant.CHANGE_LINE);
		}
		return sb.toString();
	}
	
	/**
	 * �����ʼ�
	 * 
	 * @param mailContent,�ʼ�����
	 */
	private void sendMail(String mailContent, String type)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("sendMail(" + mailContent + "," + type + ")");
		}
		// �õ��ʼ�����������
		String[] mailTo = MailConfig.getInstance().getMailToArray();
		String subject = null;
		if (SyncDataConstant.SERVICE_TYPE.equals(type))
		{
			subject = MailConfig.getInstance().getSyncServiceSubject();
		}
		else
		{
			subject = MailConfig.getInstance().getSyncContentSubject();
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("mailTo Array is:" + Arrays.asList(mailTo));
			logger.debug("mail subject is:" + subject);
			logger.debug("mailContent is:" + mailContent);
		}
		Mail.sendMail(subject, mailContent, mailTo);
	}
	
	/**
	 * ���ݲ�����������Ͷ�Ϣ֪ͨ
	 * 
	 * @param msgInfo
	 * @param content
	 */
	public void sendPhoneMsg(String type, String content)
	{
		String[] phones = null;
		String phoneArray = Config.getInstance().readConfigItem("phone");
		if (phoneArray != null)
		{
			phones = phoneArray.trim().split("\\s*,\\s*");// ��ȡ����֪ͨ�绰����
		}
		DataSyncDAO dao = DataSyncDAO.getInstance();
		if (phones != null && phones.length > 0)
		{
			for (int i = 0; i < phones.length; i++)
			{
				try
				{
					dao.sendMsg(phones[i], content);// ���Ͷ���
				}
				catch (DAOException e)
				{
					logger.error(type + "�����У����ֻ�" + phones[i] + "���Ͷ���ʧ�ܣ�" + e);
				}
			}
		}
	}
	
	public boolean isLock()
	{
		return isLock;
	}
}
