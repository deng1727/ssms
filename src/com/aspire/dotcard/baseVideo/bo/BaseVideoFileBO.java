/*
 * 文件名：BaseVideoFileBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
	 * 日志对象
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
			ret = BaseVideoFileDAO.getInstance().cleanOldSimulationData();
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
	 * 用于同步所有数据文件
	 */
	public void fileDataSync()
    {
        StringBuffer mailText = new StringBuffer();
        
        // 删除同步临时表的索引
        mailText.append(dropIndex());

        // 删除同步临时表当前数据，导入正式表数据
        mailText.append(updateTable());

        // 当前系统配置为周几执行
        int confWeek = BaseVideoConfig.sysDayByWeek;
        
		if (confWeek > 6)
		{
			confWeek = 1;
		}
		else
		{
			confWeek++;
		}
		
		// 如果是当前周多执行全量更新一步
		if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == confWeek)
		{
	        // 视频文件内容导入
	        mailText.append(BaseFileFactory.getInstance()
	                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_VIDEO)
	                                       .execution(false));
		}
        
		try
		{
			logger.info("建立临时索引，用于在视频表中应用删除操作!");
			
			// 建立临时索引，用于在视频表中应用删除操作
			BaseVideoFileDAO.getInstance().createVideoIndexByAdd();
		} 
		catch (DAOException e)
		{
			logger.error(e);
		}
		
        // 视频文件内容增量导入
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_VIDEO_ADD)
                                       .execution(false));
        
		try
		{
			logger.info("删除视频表中临时索引!");
			
			// 删除视频表中临时索引
			BaseVideoFileDAO.getInstance().delVideoIndexByAdd();
		} 
		catch (DAOException e)
		{
			logger.error(e);
		}

        // 栏目文件内容导入
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_NODE)
                                       .execution(false));

        // 得到所有的当前logopath
        Map allLogopath = new HashMap();
        logger.debug("得到所有的当前logopath。呵呵！");
        allLogopath = BaseVideoFileBO.getInstance().getAllLogopath();

        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_VIDEO_DETAIL)
                                       .execution(false));
        // downAndUpPic();准备把这个耗时间的过程搬到线程中处理了。

        logger.debug("用之前的更新logopath。呵呵！");
        BaseVideoFileBO.getInstance().updateLogopath(allLogopath);

        logger.debug("更新logopath完毕！");

        // 直播节目单内容导入
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_LIVE)
                                       .execution(false));

        // 产品内容导入
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_PRODUCT)
                                       .execution(false));

        // 视频节目统计
        mailText.append(BaseFileFactory.getInstance()
                                       .getBaseFile(BaseVideoConfig.FILE_TYPE_VIDEODETAIL)
                                       .execution(false));

        sendResultMail("基地视频数据同步结果邮件", mailText);
    }

	public void downAndUpPic()
	{

		if (!syncLogopath)
		{
			logger
					.error("downAndUpPic的syncLogopath为假" + syncLogopath
							+ "不能同步！");
			return;
		}

		syncLogopath = false;

		long start = System.currentTimeMillis();
		logger.info("aiyan_上海视频开始下载文件了。。。");
		// 集中处理图片下载上传add by aiyan 2012-07-14
		StringBuffer mailText = new StringBuffer("开始时间：" + new Date());

		try
		{
			// rs.getString("programid")+"|"+rs.getString("logopath")
			List videoNodeList = getAllVideoNode();
			logger.debug("需要处理的文件是" + videoNodeList.size());

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
				logger.debug("处理第" + i + "行：" + line);
				// 构造异步任务
				//	        	
				// Object executeObj, String executeMethodName,
				// Object[] args, Class[] argsClass)
				ReflectedTask task = new ReflectedTask(this, "handleLineNode",
						new Object[]
						{ line, fromFtp, toFtp, }, new Class[]
						{ String.class, FTPUtil.class, FTPUtil.class });
				dataSynTaskRunner.addTask(task);

			}
			dataSynTaskRunner.waitToFinished();// 等待更新数据库完毕。
			dataSynTaskRunner.end();// 结束运行器
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			syncLogopath = true;

			logger.info("aiyan_上海视频开始下载栏目文件了。。。结束。耗时："
					+ (System.currentTimeMillis() - start));
		}

		try
		{
			// rs.getString("programid")+"|"+rs.getString("logopath")
			List videoDetailList = getAllVideoDetail();
			logger.debug("需要处理的文件是" + videoDetailList.size());
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
				logger.debug("处理第" + i + "行：" + line);
				// 构造异步任务
				//	        	
				// Object executeObj, String executeMethodName,
				// Object[] args, Class[] argsClass)
				ReflectedTask task = new ReflectedTask(this, "handleLine",
						new Object[]
						{ line, fromFtp, toFtp, }, new Class[]
						{ String.class, FTPUtil.class, FTPUtil.class });
				dataSynTaskRunner.addTask(task);

			}
			dataSynTaskRunner.waitToFinished();// 等待更新数据库完毕。
			dataSynTaskRunner.end();// 结束运行器
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			syncLogopath = true;

			logger.info("aiyan_上海视频开始下载文件了。。。结束。耗时："
					+ (System.currentTimeMillis() - start));
		}
		StringBuffer nodeF = new StringBuffer("失败详细情况：");
		for (int s = 0; s < nodeFaild.size(); s++)
		{
			nodeF.append(nodeFaild.get(s));
			nodeF.append(",");

		}
		StringBuffer detailF = new StringBuffer("失败详细情况：");
		for (int h = 0; h < videoDetailFail.size(); h++)
		{
			nodeF.append(videoDetailFail.get(h));
			nodeF.append(",");

		}
		mailText.append("结束时间:" + new Date() + "结果：栏目图片总数:" + nodeLogoTotal
				+ ";更新失败数:" + nodeFaild.size() + nodeF + "<br>;节目图片总数:"
				+ videoDetailTotal + ";更新失败数:" + videoDetailFail.size()
				+ detailF);
		sendResultMail("基地视频图片数据同步结果邮件", mailText);
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
				logger.error("下载文件出错！，", e);

			}
		}
		logger.debug("fileName:" + fileName);
		if (fileName == null || "".equals(fileName))
		{
			logger.debug("没有下载到文件，嗯。。。 这行就没有更新LOGOPATH的机会了。" + line);
			videoDetailFail.add(arr[0]);
			return;
		}
		String logoPath = BaseVideoConfig.logoPath;// 默认值；
		// 上传至自有文件服务器
		try
		{
			if (fileName != null && !"".equals(fileName))
			{
				toFtp.putFiles2(BaseVideoConfig.ToProgramFTPDir, fileName,
						localFileName);
				// 定义入库字段信息
				logoPath = BaseVideoConfig.ProgramLogoPath + File.separator
						+ localFileName;
			}

		}
		catch (Exception e)
		{
			logger.error(" 用来存放logo图片的自有FTP配置出错，FTP链接出错！！！<br> ", e);
			videoDetailFail.add(arr[0]);
		}

		if (!logoPath.equals(BaseVideoConfig.logoPath))
		{
			BaseVideoFileDAO.getInstance().updateLogoPath(arr[0], logoPath);
		}
		else
		{
			logger.error("图片没有上传成功！");
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
				logger.error("下载文件出错！，", e);
				nodeFaild.add(arr[0]);
			}
		}
		logger.debug("fileName:" + fileName);
		if (fileName == null || "".equals(fileName))
		{
			logger.debug("没有下载到文件，嗯。。。 这行就没有更新LOGOPATH的机会了。" + line);
			nodeFaild.add(arr[0]);
			return;
		}
		String logoPath = BaseVideoConfig.logoPath;// 默认值；
		// 上传至自有文件服务器
		try
		{
			if (fileName != null && !"".equals(fileName))
			{
				toFtp.putFiles2(BaseVideoConfig.ToNodeFTPDir, fileName,
						localFileName);
				// 定义入库字段信息
				logoPath = BaseVideoConfig.NodeLogoPath + File.separator
						+ localFileName;
			}

		}
		catch (Exception e)
		{
			logger.error(" 用来存放logo图片的自有FTP配置出错，FTP链接出错！！！<br> ", e);
			nodeFaild.add(arr[0]);
		}

		// if (!logoPath.equals(BaseVideoConfig.logoPath)) {
		BaseVideoFileDAO.getInstance().updateNodeLogoPath(arr[0], logoPath);
		// } else {
		// logger.error("图片没有上传成功！");
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
			sf.append(BaseVideoFileDAO.getInstance().insertDataToTree());

			// 组装自定义的干支结构
			sf.append(BaseVideoFileDAO.getInstance().insertDataToReference());
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
	 * 发送结果邮件。
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
	 * 删除表中重复的数据
	 */
	public void delRepeatData()
	{
		logger.info("delRepeatData start");
		try
		{
			logger.info("建立临时索引，用于删除表中重复数据!");
			
			// 建立临时索引，用于删除表中重复数据
			BaseVideoFileDAO.getInstance().createVideoIndex();
			
			logger.info("删除表中重复数据!");
			
			// 删除表中重复数据
			BaseVideoFileDAO.getInstance().delRepeatData();
			
			logger.info("删除表中临时索引!");
			
			// 删除表中临时索引
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
		mailText.append("改表名任务开始：");
		mailText.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss")
				+ "<br>");
		boolean tag = false;

		// 操作中备份的表名后缀 _bak
		String bakSuffix = BaseVideoConfig.bakSuffix;
		// 默认的表名后缀 _tra
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

				// 改表名时的临时表名
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
					mailText.append("需要创建索引的表：" + renameTables + "<br>");
					int videoSync_ID = DB.getSeqValue("SEQ_VIDEO_SYNC_ID");
					for (int i = 0; i < len; i++)
					{
						mailText.append("创建" + renameTable[i] + defSuffix
								+ "表的索引时间：");
						mailText.append(PublicUtil
								.getCurDateTime("yyyy-MM-dd HH:mm:ss")
								+ "<br>");
						if ("t_vo_category".equals(renameTable[i]))
						{
							// 货架表，则增加两个索引
							BaseVideoFileDAO.getInstance().createIndex(
									"t_vo_category_id", videoSync_ID);
							BaseVideoFileDAO.getInstance().createIndex(
									"t_vo_category_pid", videoSync_ID);

						}
						else if ("t_vo_reference".equals(renameTable[i]))
						{
							// 商品表增加货架ID索引
							BaseVideoFileDAO.getInstance().createIndex(
									"t_vo_reference_cid", videoSync_ID);
						}
						else if ("t_vo_live".equals(renameTable[i]))
						{
							// 直播表，则增加两个索引
//							BaseVideoFileDAO.getInstance().createIndex(
//									renameTable[i], videoSync_ID);
							BaseVideoFileDAO.getInstance().createIndex(
									"t_vo_live_id", videoSync_ID);
						}

						BaseVideoFileDAO.getInstance().createIndex(
								renameTable[i], videoSync_ID);

						mailText.append("索引完成时间：");
						mailText.append(PublicUtil
								.getCurDateTime("yyyy-MM-dd HH:mm:ss")
								+ "<br>");
					}

				}
				catch (BOException e)
				{
					mailText.append(e);
					mailText.append(" 创建索引失败！！！<br>");
				}

				try
				{
					mailText.append("备份同步原表开始：");
					mailText.append(PublicUtil
							.getCurDateTime("yyyy-MM-dd HH:mm:ss")
							+ "<br>");
					for (int i = 0; i < len; i++)
					{
						bakTable = renameTable[i] + bakSuffix;
						defTable = renameTable[i];
						BaseVideoFileDAO.getInstance().createBackupSql(
								bakTable, defTable);
						mailText.append("备份同步原表:" + defTable);
						mailText.append("，为bak表:" + bakTable);
						mailText.append(" 成功！！！<br>");
					}

				}
				catch (BOException e)
				{
					mailText.append("同步原表" + defTable);
					mailText.append(" 备份bak表:" + bakTable);
					mailText.append(" 失败！！！<br>");
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
						mailText.append("修改同步表名成功: ");
						mailText.append("   完成时间："
								+ PublicUtil
										.getCurDateTime("yyyy-MM-dd HH:mm:ss")
								+ "<br>");
						mailText.append("   renameTable:" + renameTable[i]
								+ " tempTable:" + tempTable + " defTable:"
								+ defTable + "<br/>");

					}
					mailText.append("删除备份bak表开始：");
					mailText.append(PublicUtil
							.getCurDateTime("yyyy-MM-dd HH:mm:ss")
							+ "<br>");
					// 改名成功， 删除所有备份表
					for (int i = 0; i < len; i++)
					{
						bakTable = renameTable[i] + bakSuffix;

						BaseVideoFileDAO.getInstance().delBackupSql(bakTable);
						mailText.append(" 删除备份bak表:" + bakTable);
						mailText.append(" 成功！！！<br>");
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
					logger.debug("需改名的同步表数据为空");
				}
				mailText.append("需改名的同步表数据为空<br>");
				mailText.append("需改名的同步表：" + renameTables);
			}
		}
		catch (Exception e)
		{
			logger.error("renameDataSync error:", e);
			mailText.append("同步表统计查询失败");
			mailText.append(e);
		}
		finally
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("发送改表名邮件");
			}

			sendResultMail("基地视频数据同步后改表名结果邮件", mailText);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("改表名任务完成");
		}

	}
	
	/**
	 * 新增查询应用的三张表的索引
	 * @return
	 */
	public StringBuffer addIndex()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("addIndex() strat");
		}

		StringBuffer text = new StringBuffer();
		text.append("新增查询应用的三张表的索引开始：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");

		// 默认的表名后缀 _tra
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
				text.append("创建" + renameTable[i] + defSuffix + "表的索引时间：");
				text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss")
						+ "<br>");

				if ("t_vo_category".equals(renameTable[i]))
				{
					// 货架表，则增加两个索引
					BaseVideoFileDAO.getInstance().createIndex(
							"t_vo_category_id", videoSync_ID);
					BaseVideoFileDAO.getInstance().createIndex(
							"t_vo_category_pid", videoSync_ID);

				}
				
				if ("t_vo_program".equals(renameTable[i]))
				{
					// 货架表，则增加两个索引
					BaseVideoFileDAO.getInstance().createIndex(
							"t_vo_program_nodeid", videoSync_ID);
				}

				BaseVideoFileDAO.getInstance().createIndex(renameTable[i],
						videoSync_ID);

				text.append("索引完成时间：");
				text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss")
						+ "<br>");
			}

		}
		catch (BOException e)
		{
			text.append(e);
			text.append(" 创建索引失败！！！<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("dropIndex() end");
		}
		text.append("新增查询应用的三张表的索引结束：");
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
		text.append("删除同步表索引开始：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// 获取所有同步表对应的索引名称
			List indexNames = BaseVideoFileDAO.getInstance().queryDropIndex(
					BaseVideoConfig.renameTables, BaseVideoConfig.defSuffix);
			// 删除同步表索引
			BaseVideoFileDAO.getInstance().dropIndex(indexNames);
			text.append("删除同步表索引成功<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("dropIndex() end");
		}
		text.append("删除同步表索引结束：");
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
		text.append("删除所有同步_TRA临时表数据开始：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// 删除所有同步临时表数据
			BaseVideoFileDAO.getInstance().truncateTempSync(
					BaseVideoConfig.renameTables, BaseVideoConfig.defSuffix);
			text.append("删除所有同步_TRA临时表数据成功<br>");

			// 导入同步正式表数据到临时表
			text.append("导入同步正式表数据到_TRA临时表开始：");
			text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss")
					+ "<br>");
			BaseVideoFileDAO.getInstance().insertTempSync(
					BaseVideoConfig.renameTables, BaseVideoConfig.defSuffix);
			text.append("导入同步正式表数据到_TRA临时表成功<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("updateTable() end");
		}
		text.append("临时表数据更新完毕：");
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
		text.append("删除"+ getTableByVideoType(tableType) +"同步表索引开始：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// 获取所有同步表对应的索引名称
			List indexNames = BaseVideoFileDAO.getInstance().queryDropIndex(
					getTableByVideoType(tableType), BaseVideoConfig.defSuffix);
			// 删除同步表索引
			BaseVideoFileDAO.getInstance().dropIndex(indexNames);
			text.append("删除"+ getTableByVideoType(tableType) +"同步表索引成功<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("dropIndex() end");
		}
		text.append("删除"+ getTableByVideoType(tableType) +"同步表索引结束：");
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
		text.append("删除"+ getTableByVideoType(tableType) +"同步_TRA临时表数据开始：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		try
		{
			// 删除所有同步临时表数据
			BaseVideoFileDAO.getInstance().truncateTempSync(
					getTableByVideoType(tableType), BaseVideoConfig.defSuffix);
			text.append("删除"+ getTableByVideoType(tableType) +"同步_TRA临时表数据成功<br>");

			// 导入同步正式表数据到临时表
			text.append("导入同步"+ getTableByVideoType(tableType) +"正式表数据到_TRA临时表开始：");
			text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss")
					+ "<br>");
			BaseVideoFileDAO.getInstance().insertTempSync(
					getTableByVideoType(tableType), BaseVideoConfig.defSuffix);
			text.append("导入"+ getTableByVideoType(tableType) +"同步正式表数据到_TRA临时表成功<br>");
		}
		catch (Exception e)
		{
			text.append(e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("updateTable() end");
		}
		text.append(getTableByVideoType(tableType) +"临时表数据更新完毕：");
		text.append(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "<br>");
		return text;
	}
	
	/**
	 * 根据同步类型返回指定表名
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

		status = BaseVideoFileDAO.getInstance().callUpdateCategoryNum();

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
}
