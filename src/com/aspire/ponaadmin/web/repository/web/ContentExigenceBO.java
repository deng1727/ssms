/*
 * 文件名：ContentExigenceBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(ContentExigenceBO.class);
	private static ContentExigenceBO instance = new ContentExigenceBO();
	
	/**
	 * 当前程序段是否在执行中
	 */
	private boolean isLock = false;
	
	private ContentExigenceBO()
	{}
	
	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static ContentExigenceBO getInstance()
	{
		return instance;
	}
	
	/**
	 * 同步开始时间
	 */
	private Date startDate;
	
	/**
	 * 同步结束时间
	 */
	private Date endDate;
	
	/**
	 * 任务运行器
	 */
	private TaskRunner dataSynTaskRunner;
	
	/**
	 * 查询定义的将要紧急上线的内容列表
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
			// 调用ContentExigenceDAO进行查询
			ContentExigenceDAO.getInstance().queryContentExigenceList(page);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查询定义的将要紧急上线的内容列表时发生数据库异常！");
		}
	}
	
	/**
	 * 删除紧急上线的内容列表
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
			// 调用ContentExigenceDAO进行删除
			ContentExigenceDAO.getInstance().delContentExigence(ids);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查询定义的将要紧急上线的内容列表时发生数据库异常！");
		}
	}
	
	/**
	 * 导入紧急上线的内容列表
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
			// 清空原列表
			ContentExigenceDAO.getInstance().delAllContentExigence();
			
			// 解析EXECL文件，获取终端软件版本列表
			List list = this.paraseDataFile(dataFile);
			
			// 校驗文件中數據是否在視圖中存在
			String temp = ContentExigenceDAO.getInstance()
					.verifyContentExigence(list);
			
			// 调用ContentExigenceDAO进行导入
			ContentExigenceDAO.getInstance().importContentExigence(list);
			
			ret.append("成功导入" + list.size() + "条记录.");
			
			if (!"".equals(temp))
			{
				ret.append("导入不成功id为").append(temp);
			}
			
			return ret.toString();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("导入紧急上线的内容列表时发生数据库异常！");
		}
	}
	
	/**
	 * 解析EXECL文件，获取要添加的商品信息
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
					// 不為空時
					if (!"".equals(value))
					{
						// 如果集合中存在此数据
						if (list.contains(value))
						{
							// 删除原来存在的数据
							list.remove(value);
						}
						
						list.add(value);
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.error("解析导入文件出现异常,fineName:" + dataFile.getFileName(), e);
			throw new BOException("解析导入文件出现异常", e);
		}
		finally
		{
			book.close();
		}
		return list;
	}
	
	/**
	 * 同步紧急上线内容.
	 * 
	 * @param exeContent
	 *            如何处理内容表数据 1：增量处理内容表信息 0：全量处理内容表信息（默认）
	 * @param isTimer
	 *            是否为电子流定时任务
	 * @param exeDeviceType
	 *            执行适配关系类型 1：执行 0：不执行
	 * 
	 * @throws BOException
	 */
	public void SyncContentExigence(String exeContent, boolean isTimer,
			String exeDeviceType) throws BOException
	{
		// 锁
		isLock = true;
		
		// 短信内容
		String content = null;
		
		// 同步操作结果集合
		List[] mailInfo = new ArrayList[4];
		
		try
		{
			startDate = new Date();
			
			// 如果是读电子流任务，这里要加入新的数据至紧急上下线临时表中
			if (isTimer)
			{
				logger.info("自动任务紧急上下线开始");
				
				// 清空原列表
				ContentExigenceDAO.getInstance().delAllContentExigence();
				
				logger.info("自动任务紧急上下线清空结束");
				
				// 由电子流导入紧急上下线信息
				ContentExigenceDAO.getInstance().sysContentExigence(
						ContentExigenceDAO.getInstance()
								.getSysContentExigenceDate());
				
				logger.info("自动任务紧急上下线导入数据结束");
				
				// 写入最后更新时间
				ContentExigenceDAO.getInstance().addExigenceLastTime();
				
				// 如果本次待紧急上下线数据为空
				if (!ContentExigenceDAO.getInstance().hasExigenceContent())
				{
					endDate = new Date();
					logger.error("本次待紧急上下线数据为空，中断紧急上下线操作！！！");
					// 如果异常就调用邮件发送接口发给相关人员
					content = "本次待紧急上下线数据为空，中断紧急上下线操作，请联系管理员！";
					this.sendMail(content, SyncDataConstant.CONTENT_TYPE);
					throw new BOException("本次待紧急上下线数据为空，中断紧急上下线操作！！！");
				}
			}
			
			List<String> contentIdList = ContentExigenceDAO.getInstance()
					.queryExigenceIdListByType();
			
			// 增量处理内容表信息
			if ("1".equals(exeContent))
			{
				// 将视图v_cm_content中数据信息进行增量处理
				updateVcontentDate(contentIdList);
			}
			else
			{
				// 全量处理视图v_cm_content数据
				DataSyncDAO.getInstance().initViewToTable();
			}
			
			// 清空紧急上下线操作历史表数据信息
			delGoodsChangeHis();
			
			// 紧急上下线前变更v_service表中数据信息
			updateServiceDate(contentIdList);
			
			// 紧急上下线前变更cm_ct_appgame拓展表中数据信息
			updateCMCTAPPDate(contentIdList);
			
			// 同步前放入临时表
			addSyncContentTmp(false);
			
			// 同步
			mailInfo = this.syncContent(false, RepositoryConstants.SYN_HIS_YES,
					exeDeviceType);
			
			// 同步结束时间
			endDate = new Date();
			
			// 发邮件
			listToMail(mailInfo);
			
			// 拼装内容同步，自动更新 结果短信通知的消息内容
			content = this.assemblePhoneMsg("增量", mailInfo);
			
			// 加入同步结果表
			DataSyncDAO.getInstance().insertSynResult(mailInfo);
			
			// 反写用户导入表中的内容同步类型
			updateContentExigenceType(mailInfo);
			
			// 清空紧急上线应用临时表数据
			// ContentExigenceDAO.getInstance().delAllContentExigence();
			
			// 如果是读电子流任务，这里要清空紧急上线应用临时表数据
			if (isTimer)
			{
				// ContentExigenceDAO.getInstance().delAllContentExigence();
			}
			
			// 生成紧急上下线索引文件
			createExigenceFile();
		}
		catch (BOException e)
		{
			logger.error("由自动任务抛出错误 ： 本次待紧急上下线数据为空，中断紧急上下线操作！！！", e);
		}
		catch (Exception e)
		{
			endDate = new Date();
			logger.error("本次同步紧急上线内容失败。", e);
			// 如果异常就调用邮件发送接口发给相关人员
			content = "本次同步紧急上线内容失败，请联系管理员！";
			this.sendMail(content, SyncDataConstant.CONTENT_TYPE);
			throw new BOException("本次同步紧急上线内容失败。！");
		}
		finally
		{
			this.sendPhoneMsg("增量内容同步", content);
			isLock = false;
		}
	}
	
	/**
	 * 生成紧急上下线索引文件
	 */
	private void createExigenceFile() throws BOException
	{
		TempExigenceFile app = new TempExigenceFile();
		app.createFile();
	}
	
	/**
	 * 清空紧急上下线操作历史表数据信息
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
			// 调用ContentExigenceDAO进行更新
			ContentExigenceDAO.getInstance().delGoodsChangeHis();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("清空紧急上下线操作历史表数据信息时发生数据库异常！");
		}
	}
	
	/**
	 * 紧急上下线前变更v_service表中数据信息
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
			// 调用ContentExigenceDAO进行更新
			ContentExigenceDAO.getInstance().updateServiceDate(contentIdList);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("紧急上下线前变更v_service表中数据信息时发生数据库异常！");
		}
	}
	
	/**
	 * 紧急上下线前增量处理视图v_cm_content中数据信息
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
			// 调用ContentExigenceDAO进行更新
			ContentExigenceDAO.getInstance().updateVcontentDate(contentIdList);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("紧急上下线前增量处理视图v_cm_content中数据信息时发生数据库异常！");
		}
	}
	
	/**
	 * 紧急上下线前变更cm_ct_appgame拓展表中数据信息
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
			// 调用ContentExigenceDAO进行更新
			ContentExigenceDAO.getInstance().updateCMCTAPPDate(contentIdList);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("紧急上下线前变更cm_ct_appgame拓展表中数据信息时发生数据库异常！");
		}
	}
	
	/**
	 * 反写用户导入表中的内容同步类型
	 * 
	 * @param mailInfo
	 *            同步结果内容信息列表
	 */
	private void updateContentExigenceType(List[] mailInfo) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger
					.debug("ContentExigenceBO.updateContentExigenceType( ) is start...");
		}
		
		// 成功更新。
		List updateList = mailInfo[0];
		// 成功上线
		List addList = mailInfo[1];
		// 成功下线
		List deleteList = mailInfo[2];
		// 成功下线
		List errorList = mailInfo[3];
		
		try
		{
			// 调用ContentExigenceDAO进行更新
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
			throw new BOException("反写用户导入表中的内容同步类型时发生数据库异常！");
		}
	}
	
	/**
	 * 将需要同步的内容列表并将内容id和内容最后更新时间插入到数据库中。
	 * 
	 * @param isFull
	 *            是否为全量同步，true为全量，false为增量
	 * 
	 */
	private void addSyncContentTmp(boolean isFull) throws BOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("addSyncContentTmp()");
		}
		// 进行事务操作
		TransactionDB tdb = null;
		try
		{
			tdb = TransactionDB.getTransactionInstance();
			DataSyncDAO dao = DataSyncDAO.getTransactionInstance(tdb);
			
			// 调用DataSyncDAO中的addSyncContenTmpt方法；
			dao.addContentTmp();
			
			// 日志
			logger.info("紧急上线应用加入至同步内容数据临时表中。");
			// 提交事务操作
			tdb.commit();
		}
		catch (Exception e)
		{
			// 执行回滚
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
	 * 将CMS中的内容同步到PAS数据库中，并发送结果邮件。
	 * 
	 * @param isFull
	 *            操作是否是全量同步
	 * @param isSyn
	 *            是否是紧急上线应用
	 * @param exeDeviceType
	 *            执行适配关系类型 1：执行 0：不执行
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
			// 获取所有的CMS内容同步策略
			tacticList = new TacticBO().queryAll();
			if (null == tacticList && logger.isDebugEnabled())
			{
				logger.debug("获取CMS内容同步策略为空，本次内容同步没有内容上架到货架下。");
			}
			else if (logger.isDebugEnabled())
			{
				logger.debug("CMS内容同步策略:");
				for (int i = 0; i < tacticList.size(); i++)
				{
					logger.debug("[" + (i + 1) + " ] "
							+ (TacticVO) tacticList.get(i));
				}
			}
		}
		catch (Exception e)
		{
			String result = "获取SMS内容同步策略异常，本次内容同步不会把内容上架到货架下。";
			logger.error(result, e);
			throw new BOException(result, e);
		}
		
		try
		{
			// 获取需要同步的内容信息列表
			list = DataSyncDAO.getInstance().getSyncContentTmp();
		}
		catch (DAOException e)
		{
			throw new BOException("读取临时表数据异常", e);
		}
		
		try
		{
			// 是否要执行同步适配关系
			if ("1".equals(exeDeviceType))
			{
				// 如果大于500条为全量，否则增量适配关系
				if (list.size() > 500)
				{
					// 全量处理适配关系视图变表
					logger.debug("此处为全量同步内容适配关系！");
					DataSyncDAO.getInstance().syncVCmDeviceResource();
				}
				else
				{
					// 增量处理适配关系表
					logger.debug("此处为增量同步内容适配关系！");
					
					// 提交事务操作
					TransactionDB tdb = TransactionDB.getTransactionInstance();
					DataSyncDAO.getTransactionInstance(tdb)
							.syncVCmDeviceResourceAdd(list);
					tdb.commit();
				}
			}
		}
		catch (Exception e)
		{
			throw new BOException("同步内容适配关系表时表数据异常", e);
		}
		
		// 初始化缓存
		DataSyncDAO.getInstance().prepareDate();
		// 将得到的list作为参数传入方法dealSyncContent中
		List[] mailInfo = this.dealSyncContent(list, tacticList, isSyn);
		
		// 需要再次同步。把存在gcontent表但不存在 cm_content的内容删除。
		// list = this.againSynccontetTmp();// 没有必要再次同步 if (list != null &
		// List[] againMailInfo = this.dealSyncContent(list, tacticList, isSyn);
		// mailInfo = DataSyncBO.addList(mailInfo, againMailInfo);
		
		// 清空缓存。
		DataSyncDAO.getInstance().clearDate();
		return mailInfo;
		
	}
	
	/**
	 * 处理将内容临时表中的数据。
	 * 
	 * @param list
	 * @param isSyn
	 *            是否是紧急上线应用。是否加入历史表
	 * @return mailInfo String[] mail发送信息 mail发送信息 mail[0]为更新的应用信息 mail[1]上线应用信息
	 *         mail[2]下线应用信息 mail[3]表示出错的信息
	 */
	private List[] dealSyncContent(List list, List tacticList, String isSyn)
			throws BOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("dealSyncContent()");
		}
		// 遍历列表
		int size = list.size();
		
		/**
		 * mail发送信息 mail[0]为更新的应用信息 mail[1]上线应用信息 mail[2]下线应用信息 mail[3]表示出错的信息
		 */
		List[] mailInfoList = new List[4];
		mailInfoList[0] = new ArrayList();
		mailInfoList[1] = new ArrayList();
		mailInfoList[2] = new ArrayList();
		mailInfoList[3] = new ArrayList();
		String syncDataMaxNum = Config.getInstance().getModuleConfig()
				.getItemValue("syncDataMaxNum");// MM内容同步多线程数量
		
		int maxNum = Integer.valueOf(syncDataMaxNum).intValue();
		dataSynTaskRunner = new TaskRunner(maxNum, 0);
		
		List mttacticList = new TacticBO().queryMOTOAll();
		List htctacticList = new TacticBO().queryHTCAll();
		//2015-10-13 add,触点泛化合作渠道商与根货架列表
        List channelstacticList = new TacticBO().queryChannelsCategoryAll();
		if (null == tacticList && logger.isDebugEnabled())
		{
			logger.debug("获取CMS内容同步策略为空，本次内容同步没有内容上架到货架下。");
		}
		logger.debug("准备进入多线程操作 list size =  " + size);
		
		if (size <= 20)
		{
			for (int i = 0; i < size; i++)
			{
				logger.debug("进入循环同步操作");
				
				// 得到ContentTmp对象
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
				logger.debug("进入多线程操作");
				// 得到ContentTmp对象
				ContentTmp tmp = (ContentTmp) list.get(i);
				
				DataSynOpration cm = new DataSynOpration(tacticList,
						mttacticList, htctacticList,channelstacticList, tmp, mailInfoList, isSyn);
				// 构造异步任务
				ReflectedTask task = new ReflectedTask(cm, "dataSynOp", null,
						null);
				// 将任务加到运行器中
				dataSynTaskRunner.addTask(task);
			}
			logger.debug("退出多线程操作");
			dataSynTaskRunner.waitToFinished();
			dataSynTaskRunner.stop();
		}
		
		// 清空缓存
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
		
		// 查询新增/解除的数据，插入到临时表中
		this.againInsSyncContentTmp();
		
		// 此处后调用的步骤与原同步的步骤相同
		// 调用dao方法getSyncContentTmp得到list;
		try
		{
			return DataSyncDAO.getInstance().getSyncContentTmp();
		}
		catch (DAOException e)
		{
			throw new BOException("再次同步出错", e);
		}
		
	}
	
	/**
	 * 将需要同步的内容列表插入到t_synctime_tmp表中。
	 * 
	 * @author biran
	 */
	private void againInsSyncContentTmp() throws BOException
	{
		
		if (logger.isDebugEnabled())
		{
			logger.debug("againInsSyncContentTmp()");
		}
		// 进行事务操作
		TransactionDB tdb = null;
		try
		{
			tdb = TransactionDB.getTransactionInstance();
			DataSyncDAO dao = DataSyncDAO.getTransactionInstance(tdb);
			
			// 调用DataSyncDAO中的againInsSyncContentTmp方法，查询出CMS中新增/解除内容与业务关联的数据，插入到t_synctime_tmp表中
			dao.againInsSyncContentTmp();
			
			// 提交事务操作
			tdb.commit();
		}
		catch (Exception e)
		{
			// 执行回滚
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
	 * 拼装内容同步，自动更新 结果短信通知的消息内容
	 * 
	 * @return
	 */
	private String assemblePhoneMsg(String type, List[] msgInfo)
	{
		
		StringBuffer sb = new StringBuffer();
		List updateList = msgInfo[0];// 成功更新。
		List addList = msgInfo[1];// 成功上线
		List deleteList = msgInfo[2];// 成功下线
		List errorList = msgInfo[3];// 失败同步
		PublicUtil.removeDuplicateWithOrder(errorList);// 去掉重复记录同步失败的问题。
		int size = updateList.size() + addList.size() + deleteList.size()
				+ errorList.size();
		/*
		 * sb.append("内容同步结束！本地同步总数为：" + size +
		 * "个内容，其中上线"+addList.size()+",个，下线内容为"+deleteList.size()+"个，同步出错的内容为：" +
		 * errorList.size() + SyncDataConstant.CHANGE_LINE);
		 */
		sb.append("本次" + type + "同步总共处理");
		sb.append(size);
		sb.append("个应用。其中成功上线应用");
		sb.append(addList.size());
		sb.append("个，");
		sb.append("成功下线应用");
		sb.append(deleteList.size());
		sb.append("个，");
		sb.append("成功更新应用");
		sb.append(updateList.size());
		sb.append("个，");
		sb.append("同步失败");
		sb.append(errorList.size());
		sb.append("个。");
		return sb.toString();
	}
	
	/**
	 * 根据同步的信息数组mailInfo，发送mail
	 * 
	 * @param size
	 *            总共同步的数据的总数
	 * @param mailInfo
	 *            同步数据的信息数组 mailInfo[0]为下架内容数据，mailInfo[1]为错误内容数据
	 * @author biran
	 */
	private void listToMail(List[] mailInfo)
	{
		// 调用邮件发送结果将本次同步信息发邮件给相关人员.
		this.sendMail(this.assembleSyncContentMessage(mailInfo),
				SyncDataConstant.CONTENT_TYPE);
	}
	
	/**
	 * 拼装出内容同步结果的邮件内容
	 * 
	 * @param errorList
	 * @param size
	 * @return
	 */
	private String assembleSyncContentMessage(List[] mailInfo)
	{
		
		List updateList = mailInfo[0];// 成功更新。
		List addList = mailInfo[1];// 成功上线
		List deleteList = mailInfo[2];// 成功下线
		List errorList = mailInfo[3];// 失败同步
		PublicUtil.removeDuplicateWithOrder(errorList);// 去掉重复记录同步失败的问题。
		int size = updateList.size() + addList.size() + deleteList.size()
				+ errorList.size();
		StringBuffer sb = new StringBuffer();
		/*
		 * sb.append("内容同步结束！本地同步总数为：" + size +
		 * "个内容，其中上线"+addList.size()+",个，下线内容为"+deleteList.size()+"个，同步出错的内容为：" +
		 * errorList.size() + SyncDataConstant.CHANGE_LINE);
		 */
		sb.append("开始时间：");
		sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
		sb.append(",结束时间：");
		sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
		sb.append("。<h4>处理结果概述：</h4>");
		sb.append("本次同步总共处理<b>");
		sb.append(size);
		sb.append("</b>个应用。其中成功上线应用<b>");
		sb.append(addList.size());
		sb.append("</b>个，");
		sb.append("成功下线应用<b>");
		sb.append(deleteList.size());
		sb.append("</b>个，");
		sb.append("成功更新应用<b>");
		sb.append(updateList.size());
		sb.append("</b>个，");
		sb.append("同步失败<b>");
		sb.append(errorList.size());
		sb.append("</b>个。");
		
		if (size > 0)
		{
			sb.append("<h5>本次同步的详细信息：</h5>");
		}
		boolean isFirst = true;
		for (int i = 0; i < addList.size(); i++)
		{
			if (isFirst)
			{
				sb.append("<p>应用上线的信息为：<br>");
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
				sb.append("<p>应用下线的信息为:<br>");
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
				sb.append("<p>应用更新的信息为：<br>");
				isFirst = false;
			}
			if (i >= 100)// 只需要展示100条即可，不至于邮件太大
			{
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") 。。。。。。"
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
				sb.append("<p>应用同步失败的信息为：<br>");
				isFirst = false;
			}
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (i + 1) + ") "
					+ errorList.get(i) + SyncDataConstant.CHANGE_LINE);
		}
		return sb.toString();
	}
	
	/**
	 * 发送邮件
	 * 
	 * @param mailContent,邮件内容
	 */
	private void sendMail(String mailContent, String type)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("sendMail(" + mailContent + "," + type + ")");
		}
		// 得到邮件接收者数组
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
	 * 根据操作结果，发送短息通知
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
			phones = phoneArray.trim().split("\\s*,\\s*");// 获取短信通知电话号码
		}
		DataSyncDAO dao = DataSyncDAO.getInstance();
		if (phones != null && phones.length > 0)
		{
			for (int i = 0; i < phones.length; i++)
			{
				try
				{
					dao.sendMsg(phones[i], content);// 发送短信
				}
				catch (DAOException e)
				{
					logger.error(type + "操作中，给手机" + phones[i] + "发送短信失败！" + e);
				}
			}
		}
	}
	
	public boolean isLock()
	{
		return isLock;
	}
}
