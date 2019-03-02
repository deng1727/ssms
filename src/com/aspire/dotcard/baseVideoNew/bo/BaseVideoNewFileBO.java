/*
 * 文件名：BaseVideoFileBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
	 * 日志对象
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
	 * 发送结果邮件。
	 */
	public void sendResultMail(String mailTitle, StringBuffer mailText)
	{
		logger.info(mailText.toString());
		Mail.sendMail(mailTitle, mailText.toString(), BaseVideoConfig.mailTo);
	}
	
	/**
	 * 删除同步中间表当前数据
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
		text.append("删除所有同步_mid中间表数据开始：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// 删除所有同步临时表数据
			BaseVideoNewFileDAO.getInstance().truncateTempSync(tableName,
					BaseVideoNewConfig.midDefSuffix);
			text.append("删除所有同步_mid中间表数据成功<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("delMidTable() end");
		}
		text.append("mid中间表数据清理完毕：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}
	
	/**
	 * 删除同步视频全量临时表当前数据
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
		text.append("删除所有同步视频全量临时表数据开始：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// 删除同步视频全量临时表数据
			BaseVideoNewFileDAO.getInstance().truncateTempSync(tableName,BaseVideoNewConfig.fullDefSuffix);
			text.append("删除同步视频全量临时表数据成功<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("delVideoFullTable() end");
		}
		text.append("视频全量临时表数据清理完毕：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}
	
	/**
	 * 用于同步所有数据文件
	 */
	public void fileDataSync()
	{
		StringBuffer mailText = new StringBuffer();
		
		// 视频文件内容增量导入
		mailText.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_VIDEO_ADD).execution(false));
		
		
		// 栏目文件内容导入
		mailText.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_NODE).execution(false));
		
		// 节目单详情内容导入
		mailText.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_VIDEO_DETAIL).execution(false));
		
		//删除视频文件中间表融创格式视频数据
		mailText.append(deleteVideoMidData());
		//删除视频文件正式表融创格式视频数据
		mailText.append(deleteVideoData());
		mailText.append("<br><br>");
		// 直播节目单内容导入
		mailText.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_LIVE).execution(false));
		
		// 产品内容导入
		mailText.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_PRODUCT).execution(false));
		
		// 视频节目统计
		mailText.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_VIDEODETAIL).execution(false));
		
		sendResultMail("基地视频数据同步结果邮件", mailText);
	}
	
	/**
	 * 用于插入预删除数据至指定中间表
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
			logger.debug("执行插入预删除数据至指定中间表时发生错误！！！", e);
		}
		return isTrue;
	}
	
	/**
	 * 调用存储过程 用以执行中间表与正式表中数据转移
	 */
	public boolean syncVideoData()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("调用存储过程 用以执行中间表与正式表中数据转移, 开始");
		}
		
		StringBuffer mailText = new StringBuffer();
		int status = 0;
		
		status = BaseVideoNewFileDAO.getInstance().callSyncVideoData();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("执行中间表与正式表中数据转移, 结束,执行结果status=" + status);
		}
		if (status != 0)
		{
			mailText.append("基地视频执行中间表与正式表中数据转移结果成功success！");
		}
		else
		{
			mailText.append("基地视频执行中间表与正式表中数据转移结果失败！！！后续模拟表重建动作取消！！！请查看存储过程日志");
		}
		
		sendResultMail("基地视频执行中间表与正式表中数据转移结果邮件", mailText);
		
		return status != 0 ? true : false;
	}
	
	/**
	 * 调用存储过程 用以执行视频全量临时表与中间表中数据转移
	 */
	public boolean syncVideoFullData()
	{
		//if (logger.isDebugEnabled())
		//{
			logger.info("调用存储过程 用以执行视频全量临时表与中间表中数据转移, 开始");
		//}
		
		StringBuffer mailText = new StringBuffer();
		int status = 0;
		
		status = BaseVideoNewFileDAO.getInstance().callSyncVideoFullData();
		
		//if (logger.isDebugEnabled())
		//{
			logger.info("执行视频全量临时表与中间表中数据转移, 结束,执行结果status=" + status);
		//}
		if (status != 0)
		{
			mailText.append("基地视频执行视频全量临时表与中间表中数据转移结果成功success！");
		}
		else
		{
			mailText.append("基地视频执行视频全量临时表与中间表中数据转移结果失败！！！！请查看存储过程日志");
		}
		
		sendResultMail("基地视频执行视频全量临时表与中间表中数据转移结果邮件", mailText);
		
		return status != 0 ? true : false;
	}
	
	/**
	 * 用于清空旧的模拟表数据
	 */
	public boolean cleanOldSimulationData(StringBuffer sb)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("清空旧的模拟表数据,开始");
		}
		
		String ret;
		try
		{
			ret = BaseVideoNewFileDAO.getInstance().cleanOldSimulationData();
		}
		catch (BOException e)
		{
			logger.debug("清空旧的模拟表数据,失败！" + e.getMessage());
			sb.append("清空旧的模拟表数据,失败！" + e.getMessage());
			return false;
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("清空旧的模拟表数据,结束");
		}
		logger.info(ret);
		sb.append(ret);
		return true;
	}
	
	/**
	 * 自定义组装模拟表
	 */
	public boolean diyDataTable(StringBuffer sb)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("自定义组装模拟表,开始");
		}
		
		StringBuffer sf = new StringBuffer();
		
		try
		{
			// 组装自定义树结构表
			sf.append(BaseVideoNewFileDAO.getInstance().insertDataToTree());
			
			// 组装自定义的干支结构
			sf
					.append(BaseVideoNewFileDAO.getInstance()
							.insertDataToReference());
		}
		catch (BOException e)
		{
			logger.debug("自定义组装模拟表,失败" + e.getMessage());
			sb.append("自定义组装模拟表,失败" + e.getMessage());
			return false;
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("自定义组装模拟表,结束");
		}
		logger.info(sf);
		sb.append(sf);
		return true;
	}
	
	/**
	 * 用于统计子栏目数与栏目下节目数
	 */
	public void updateCategoryNodeNum()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("统计子栏目数与栏目下节目数, 开始");
		}
		
		StringBuffer mailText = new StringBuffer();
		int status = 0;
		
		status = BaseVideoNewFileDAO.getInstance().callUpdateCategoryNum();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("统计子栏目数与栏目下节目数, 结束,统计结果status=" + status);
		}
		if (status == 0)
		{
			mailText.append("基地视频统计子孙节目数与栏目下节目结果成功success！");
		}
		else
		{
			mailText.append("基地视频统计子孙节目数与栏目下节目结果失败！failed！请查看存储过程日志");
		}
		
		sendResultMail("基地视频统计子栏目数与栏目下节目数结果邮件", mailText);
	}
	
	/**
	 * 查询中间表存储分类情况用以邮件显示
	 */
	public StringBuffer getMailText()
	{
		StringBuffer sb = new StringBuffer();
		String[] tableName = new String[] { "T_VO_VIDEO_MID",
				"T_VO_VIDEODETAIL_MID", "t_vo_program_mid", "t_vo_product_mid",
				"t_vo_node_mid", "t_vo_live_mid" };
		logger.info("查询中间表存储分类情况用以邮件显示, 开始");
		
		sb.append("<br>统计中间表数据分类情况:<br>");
		
		// 得到不同中间表中数据的分类信息。用以放入结果邮件
		for (String name : tableName)
		{
			sb.append(name + "表中数据情况为：").append("<br>");
			sb.append(BaseVideoNewFileDAO.getInstance()
					.getMidTableGroupBy(name));
			sb.append("<br>");
		}
		
		logger.info("查询中间表存储分类情况用以邮件显示, 结束");
		return sb;
	}
	
	/**
	 * 用于同步视频内容集及内容集节点数据文件
	 */
	public void syncCollectAndNodeData(){
		StringBuffer msgInfo = new StringBuffer();
		//用于同步视频内容集节点数据文件
		msgInfo.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_COLLECT_NODE).execution(false));
		// 用于同步视频内容集数据文件
		msgInfo.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_COLLECT).execution(false));
		msgInfo.append("<br>");
		//删除内容集节目为空的内容集和内容集节点,2015-03-18 add
		deleteCollectData(msgInfo);
		BaseVideoNewFileBO.getInstance().sendResultMail("基地视频内容集节点及内容集数据同步结果邮件", msgInfo);
		//更新栏目表和内容集节点表的内容集标识字段设定为1
		BaseVideoNewFileDAO.getInstance().updateCollectflag();
	}
	
	public void costProductRelation(){
		StringBuffer msgInfo = new StringBuffer();
		//用于同步视频产品打折关系信息接口数据文件
		msgInfo.append(BaseFileFactory.getInstance().getBaseFile(
				BaseVideoConfig.FILE_TYPE_COST).execution(false));
		msgInfo.append("<br>");
		BaseVideoNewFileBO.getInstance().sendResultMail("基地视频产品打折关系信息数据同步结果邮件", msgInfo);
	}
	
	/**
	 * 删除视频文件正式表融创格式视频数据
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
		text.append("删除视频文件正式表融创格式视频数据开始：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// 删除视频正式表融创格式视频数据
			text.append(BaseVideoNewFileDAO.getInstance().deleteVideoData());
		}
		catch (BOException e)
		{
			logger.debug("清空视频文件正式表融创格式视频数据,失败！" + e.getMessage());
			text.append("清空视频文件正式表融创格式视频数据,失败！" + e.getMessage());
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("deleteVideoData() end");
		}
		text.append("视频文件正式表融创格式视频数据清理完毕：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}
	
	/**
	 * 删除视频节目正式表融创格式视频数据
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
		text.append("删除视频节目正式表融创格式视频数据开始：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// 删除视频节目正式表融创格式视频数据
			text.append(BaseVideoNewFileDAO.getInstance().deleteProgramData());
		}
		catch (BOException e)
		{
			logger.debug("清空视频节目正式表融创格式视频数据,失败！" + e.getMessage());
			text.append("清空视频节目正式表融创格式视频数据,失败！" + e.getMessage());
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("deleteProgramData() end");
		}
		text.append("视频节目正式表融创格式视频数据清理完毕：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}
	
	/**
	 * 删除视频中间表融创格式视频数据
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
		text.append("删除视频文件中间表融创格式视频数据开始：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// 删除视频文件中间表融创格式视频数据
			text.append(BaseVideoNewFileDAO.getInstance().deleteVideoMidData());
		}
		catch (BOException e)
		{
			logger.debug("清空视频文件中间表融创格式视频数据,失败！" + e.getMessage());
			text.append("清空视频文件中间表融创格式视频数据,失败！" + e.getMessage());
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("deleteVideoMidData() end");
		}
		text.append("视频文件中间表融创格式视频数据清理完毕：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}
	
	/**
	 * 调用存储过程 用以执行删除内容集节目为空的内容集和内容集节点
	 */
	public void deleteCollectData(StringBuffer msgInfo)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("调用存储过程 用以执行删除内容集节目为空的内容集和内容集节点, 开始");
		}
		int status = 0;
		
		status = BaseVideoNewFileDAO.getInstance().deleteCollectData();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("执行删除内容集节目为空的内容集和内容集节点, 结束,执行结果status=" + status);
		}
		if (status != 0)
		{
			msgInfo.append("删除内容集节目为空的内容集和内容集节点成功success！");
		}
		else
		{
			msgInfo.append("删除内容集节目为空的内容集和内容集节点失败！！！！！请查看存储过程日志");
		}

	}
}
