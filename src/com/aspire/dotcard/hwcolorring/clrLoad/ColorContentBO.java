package com.aspire.dotcard.hwcolorring.clrLoad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.common.util.FileUtils;
import com.aspire.dotcard.hwcolorring.clrConvert.DealOneColorring;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.StringTool;

/**
 * <p>
 * 彩铃业务内容导入
 * </p>
 * <p>
 * Copyright (c) ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 *
 * @author zhangrihui
 */

public class ColorContentBO
{

	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory.getLogger(ColorContentBO.class);

	/**
	 * 彩铃网关日志引用
	 */
	private static final JLogger synLog = LoggerFactory.getLogger("colorring.syn");
	/**
	 * 彩铃试听转换日志引用
	 */
	private static final JLogger convertLog = LoggerFactory
			.getLogger("colorring.convert");

	private TaskRunner updateTaskRunner;
	private TaskRunner convertTaskRunner;
	private String RefDataTime;
	/**
	 * 彩铃转化的索引值
	 */
	private int convertIndex = 0;
	/**
	 * 彩铃根节点货架对象，用于缓存。
	 */
	public static Category rootColorCagetory;
	/**
	 * 内容存储根节点货架对象
	 */
	public static Category rootContentNode;
	/**
	 * 本次彩铃导入的同步结果
	 */
	public String syncResult;

	/**
	 * 构造方法，由singleton模式调用
	 */
	private ColorContentBO()
	{

	}

	/**
	 * singleton模式的实例
	 */
	private static ColorContentBO colorContentBO = new ColorContentBO();

	/**
	 * 获取实例
	 *
	 * @return 实例
	 */
	public static final ColorContentBO getInstance()
	{

		return colorContentBO;
	}

	/**
	 * 导入全量数据文件
	 * @param fileName String
	 * @throws Exception
	 * @return List
	 */
	public void colorringFullImport(String fileName, String ftpPath, String bakPath,
			String RefDataTime) throws BOException

	{
		syncResult=null;
		int addCount = 0;
		int updateCount = 0;
		int delCount = 0;
		int lineNum = 0;
		int snum = 0;
		convertIndex = 0;
		if (logger.isDebugEnabled())
		{
			logger.debug("ColorringFullImport()");
			logger.debug("the file name =" + fileName);
		}
		this.RefDataTime = RefDataTime;
		String fullPathFilename = ftpPath + "/" + fileName;
		initDate();
		// 读取文件内容
		//Object[] arrColorContent = readAllFileLine(fullPathFilename);
		//得到系统当前时间
		String currentTime = PublicUtil.getCurDateTime();
		HashMap existsColor = null;
		try
		{
			existsColor = ColorContentDAO.getInstance().getAllColorringID();
		} catch (DAOException e1)
		{
			logger.error("从数据库中获取所有彩铃ID时发生数据库异常！", e1);
			throw new BOException("从数据库中获取所有彩铃ID时发生数据库异常");
		}
		FileReader fr = null;
		BufferedReader br = null;
		try
		{

			fr = new FileReader(fullPathFilename);
			br = new BufferedReader(fr);

			String lineText;// = br.readLine();

			while ((lineText = br.readLine()) != null)
			{
				lineText = lineText.trim();
				if (lineText.length() == 0)
				{
					continue;
				}else{
					snum ++;
				}
				lineNum++;
				if(logger.isDebugEnabled())
				{
					logger.debug("#######################开始处理第"+lineNum+"行数据");
				}
				GColorring clrVO=getGColorringVOByline(lineText, currentTime);
				ColorSyncVO syncVO = (ColorSyncVO) existsColor.get(clrVO.getId());
				boolean expirebo = true;
				String expired = clrVO.getExpire();
				String nowdate = PublicUtil.getCurDateTime("yyyyMMdd");
				if(nowdate.compareTo(expired)>0)
				{
					expirebo= false;
				}
				if(expirebo)
				{
					//有效期没有过期
					if (syncVO == null  )//货架系统不存在，需要新增,并且没有过有效期
					{
						this.InsertContent(clrVO);
						this.convertColorring(clrVO);
						existsColor.put(clrVO.getId(), new ColorSyncVO(clrVO.getLupdDate(),2));//标记新增flag
						addCount++;
					}
					else if (syncVO.getFlag() == 0 )//第一次访问。
					{
						if(clrVO.getLupdDate().compareTo(syncVO.getLupDate())>0)
						{
							this.updateDBColorring(clrVO, RefDataTime);
							this.convertColorring(clrVO);//更新也需要转化数据的。
							updateCount++;
						}else if(!syncVO.isConvert())//如果没有转化成功，就继续转化
						{
							this.convertColorring(clrVO);
						}
						syncVO.setFlag(1);//记住已经更新
						
					}
				}
			
			}

		} catch (IOException e)
		{
			throw new BOException("读取彩铃文件异常。fileName=" + fileName);
		} finally
		{
			try
			{
				if (br != null)
				{
					br.close();
				}
				if (fr != null)
				{
					fr.close();
				}

			} catch (IOException e)
			{
				logger.error("关闭彩铃文件异常", e);
			}

		}
		//防止空文件这个极端特殊情况下，把所有的现网彩铃删除。
		//int processCount=addCount+updateCount;
		int processCount=snum;
		if(processCount>0)
		{
			//计算需要删除的；
			for (Iterator ite = existsColor.keySet().iterator(); ite.hasNext();)
			{
				String colorId = (String) ite.next();
				ColorSyncVO temp = (ColorSyncVO) existsColor.get(colorId);
				if (temp.getFlag() == 0)
				{
					this.deleteDBColorring(colorId);
					delCount++;
				}
			}
		}
		updateTaskRunner.waitToFinished();//等待更新数据库完毕。
		updateTaskRunner.end();//结束运行器
		/*  
		// 将所有数据转成对象放入队列
		List arrColorList = new ArrayList();
		
		//如果获取的是铃音信息接口文件，采用对应于铃音信息的内容分解方式
		int[] p = new int[iDataNum];
		for (int i = 0; i < iDataNum; i++)
		{
		    arrColorList.add(getGColorringVOByline(( String ) arrColorContent[i],
		                                          currentTime));
		}
		//查询数据库中存在的所有彩铃ID，并放置到一个list中
		HashMap existsColor;
		try
		{
		    existsColor = ColorContentDAO.getInstance().getAllColorringID();
		}
		catch (DAOException e1)
		{
		    logger.error("从数据库中获取所有彩铃ID时发生数据库异常！", e1);
		    throw new BOException("从数据库中获取所有彩铃ID时发生数据库异常");
		}
		//从彩铃平台获取的彩铃数目
		int out = arrColorList.size();
		
		// 进行更新，新增，删除的核对
		List addList = new ArrayList();
		List updateList = new ArrayList();
		//彩铃试听转化列表
		List convertList = new ArrayList();
		//彩铃在数据库中的记录
		GColorring dbColor = null; 
		for (int i = 0; i < out; i++)
		{
		    GColorring clrVO = ( GColorring ) arrColorList.get(i);
		    // 将新解析出来的彩铃ID对应的记录从待删除记录列表中移除
		    boolean result = existsColor.remove(clrVO.getId());
		    // 返回成功，说明对应的彩铃记录在数据库中存在
		    if (result)
		    {
		        if (logger.isDebugEnabled())
		        {
		            logger.debug("铃音编码 " + clrVO.getContentID() + " 对应的彩铃存在于系统中，需要更新！");
		        }
		        dbColor = ( GColorring ) Repository.getInstance()
		                                           .getNode(clrVO.getId(),
		                                                    GColorring.TYPE_COLORRING);
		     
		        updateList.add(clrVO);
		        if (null == dbColor)
		        {
		            // 从数据库中没有成功获取到对应的彩铃，默认不做转换，记下比较下一首彩铃
		            continue;
		        }
		        // 如果同步过来才彩铃数据的最后更新时间大于系统中存在的彩铃数据的最后更新时间，需要重新转换试听文件
		        if (clrVO.getLupdDate().compareTo(dbColor.getLupdDate()) > 0
		            || "".equals(dbColor.getClientAuditionUrl()))
		        {
		            clrVO.setClientAuditionUrl(dbColor.getClientAuditionUrl());
		            convertList.add(clrVO);
		        }
		    }
		    // 返回失败
		    else
		    {
		        addList.add(clrVO);
		    }
		}
		
		

		//将所有新增彩铃也加入转换列表中
		convertList.addAll(addList);
		// 用于日志统计
		int addCount = addList.size();
		int updateCount = 0;
		int delCount = 0;
		// 向t_r_gcontent表插入彩铃数据并上架
		p = checkAndInsert(addList);
		int iFailNum = 0;
		for (int i = 0; i < p.length; i++)
		{
		    iFailNum = iFailNum + p[i];
		}
		addCount = addCount - iFailNum;
		
		//更新彩铃信息
		updateCount = this.updateDBColorring(updateList);
		//删除全量彩铃数据中没有的记录
		delCount = this.deleteDBColorring(existsColor);
		iFailNum = iFailNum + (updateList.size() - updateCount)
		           + (existsColor.size() - delCount);*/

		syncResult=new StringBuffer().append("任务执行成功！一共成功新增彩铃数据 ").append(addCount).append(
		"条，更新彩铃数据 ").append(updateCount).append("条，删除彩铃数据 ").append(delCount)
		.append("条。\n").toString();
		synLog.error("本次彩铃数据同步结果如下：\n");
		synLog.error(syncResult);
		try
		{
			// 执行完导入后将文件复制到备份目录
			FileUtils.copy(ftpPath, bakPath, fileName);
			// 删除这个原文件
			new File(fullPathFilename).delete();
		} catch (Exception e)
		{
			logger.debug("备份文件或删除源文件出错", e);
		}
		//返回需要进行彩铃试听转换的铃音列表
		// return convertList;
	}

	/**
	 * 将每一条彩铃内容分解，设置到彩铃对象VO中去
	 */
	private static GColorring getGColorringVOByline(String recordContent,
			String currentTime)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getGColorringVOByline is starting.....");
		}
		String[] arrColorContent = recordContent.split("\\|");
		GColorring gColorringVO = new GColorring();
		// 设置VO的字段值，注意ID做了特别处理，前面加了字符串"clr"
		gColorringVO.setId("clr" + arrColorContent[0]);
		gColorringVO.setName(StringTool.formatByLen(arrColorContent[1], 200, "..."));
		gColorringVO.setTonenameletter(arrColorContent[2]);
		gColorringVO.setSinger(StringTool.formatByLen(arrColorContent[3], 197, "..."));
		gColorringVO.setSingerletter(arrColorContent[4]);
		gColorringVO.setIntroduction(arrColorContent[5]);
		gColorringVO.setPrice(modifyPrice(arrColorContent[6]));
		gColorringVO.setLupdDate(arrColorContent[7]);
		gColorringVO.setDownloadtimes(Integer.parseInt(arrColorContent[8]));
		gColorringVO.setSettimes(Integer.parseInt(arrColorContent[9]));
		gColorringVO.setAuditionUrl(arrColorContent[10]);
		gColorringVO.setTonebigtype(arrColorContent[11]);
		gColorringVO.setCateName(arrColorContent[12]);
		gColorringVO.setExpire(arrColorContent[13]);
		//下面是新提供的数据中没有的字段
		gColorringVO.setCreateDate(currentTime);
		gColorringVO.setMarketDate(currentTime);
		//彩铃播放时长
		gColorringVO.setAverageMark(0);
		//设置内容内码为铃音编码
		gColorringVO.setContentID(arrColorContent[0]);
		return gColorringVO;
	}
	/**
	 * 资费乘以10，数据保存的单位为"厘"
	 * @param price
	 * @return
	 */
	private static String modifyPrice(String price)
	{
		int newPrice= Integer.parseInt(price.trim());
		return String.valueOf(newPrice*10);
	}
	/**
	 * 逐一的插入彩铃记录
	 */
	private void InsertContent(GColorring gColorringVO)
	{
		updateTaskRunner.addTask(new ColorringAddTask(gColorringVO));
	}

	/**
	 * 更新彩铃信息,调用多线程完成异步更新。
	 * 
	 * @param vo 彩铃信息的vo
	 * @return int 成功更新彩铃内容的个数
	 */
	public void updateDBColorring(GColorring vo, String RefDataTime)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("updateDBColorring() id=" + vo.getId());
		}
		ColorringUpdateMulti cm = new ColorringUpdateMulti(vo);
		//构造异步任务
		ReflectedTask task = new ReflectedTask(cm, "updateColorring", null, null);
		//将任务加到运行器中
		updateTaskRunner.addTask(task);
	}

	public void convertColorring(GColorring clrVO)
	{
		convertLog.error(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")
				+ "，彩铃试听转换开始。。。");
		/*
		//统一内容适配平台访问地址
		String UCAPUrl = "";
		//彩铃试听转换内容格式及编码率数组
		String[] code_abps = null;
		//资源服务器上面转换后的彩铃存放目录（相对ftp登录后的目录）
		String clrConvertPath = "";
		//资源服务器的访问方式，即彩铃试听apache访问路径
		String sourceServerVisit = "";
		//彩铃试听转换启动的异步任务数
		int taskNum = 50;
		try
		{
		    UCAPUrl = ColorringConfig.get("UCAPUrl");
		    code_abps = ConfigFactory.getSystemConfig().getModuleConfig("colorring").getItemValueList("code_abps");
		    clrConvertPath = ColorringConfig.get("clrConvertPath");
		    sourceServerVisit = ColorringConfig.get("sourceServerVisit");
		    taskNum = Integer.parseInt(ColorringConfig.get("ColorringConvertTaskNum"));
		}
		catch (Exception e1)
		{
		    convertLog.error("从彩铃配置项中获取彩铃转换参数UCAPUrl、code_abps、ColorringConvertTaskNum时发生异常，彩铃试听文件转换停止！", e1);
		    return;
		}*/
		//当前日期字符串路径下彩铃试听文件的存放路径，因为linux/unix环境下每个目录下的文件是有限制的
		// int path 
		//计划每个文件夹下最多存放60000个试听文件，一个彩铃目前需要转换两个
		/* if (i % 30000 == 0)
		 {
		     path++;
		 }*/
		convertIndex++;
		int path = convertIndex / 30000;
		//构造彩铃试听文件的终极存贮目录
		String tmp = new StringBuffer().append(ColorringConfig.clrConvertPath).append(
				File.separator).append(RefDataTime).append(File.separator).append(
				PublicUtil.lPad("" + path, 3)).toString();
		if (convertLog.isDebugEnabled())
		{
			convertLog.debug("彩铃试听文件的存贮目录是：" + tmp);
		}
		//如果资源服务器上面转换后的彩铃存放目录就是ftp登陆后的目录，则不需要使用File.separator分隔
		if ("./".equals(ColorringConfig.clrConvertPath))
		{
			tmp = tmp.substring(3);
		}
		//如果彩铃终端试听url为空，说明是初次转换
		if ("".equals(clrVO.getClientAuditionUrl()))
		{
			// 直接设置彩铃终端试听文件地址
			clrVO.setClientAuditionUrl(new StringBuffer().append(
					ColorringConfig.sourceServerVisit).append(tmp).append(File.separator)
					.append(clrVO.getContentID()).toString());
		}
		//否则需要重新转换，转换后需要覆盖原来的
		else
		{
			String clientAuditionUrl = clrVO.getClientAuditionUrl();
			tmp = clientAuditionUrl.substring(ColorringConfig.sourceServerVisit.length(),
					clientAuditionUrl.lastIndexOf(File.separator));
		}
		if (convertLog.isDebugEnabled())
		{
			convertLog.debug("彩铃试听文件的终极存贮目录是：" + tmp);
		}
		DealOneColorring dealOneColorring = new DealOneColorring(clrVO,
				ColorringConfig.UCAPUrl, ColorringConfig.code_abps, tmp);
		//构造异步任务
		ReflectedTask task = new ReflectedTask(dealOneColorring, "colorringConvert",
				null, null);
		//将任务加到运行器中
		this.convertTaskRunner.addTask(task);

		convertLog.error(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")
				+ "，彩铃试听转换结束。。。");
	}
	
	/**
	 * 
	 * @param expireDate
	 * @return
	 */
//private boolean checkExpireDate(String expireDate){
//	Date expired = DateUtil.stringToDate(expireDate,"yyyy-MM-dd HH:mm:ss");
//	Date nowdate = new Date();
//	if(expired.before(nowdate)){
//		return false;	
//	}
	
//	return true;
//}
	/**
	 * 删除全量彩铃文件中不存在而本地数据库的彩铃数据，删除前需要进行下架操作
	 * 
	 * @param deleteList
	 * @return 成功删除的媒体个数
	 */
	private void deleteDBColorring(String clrID)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("deleteDBColorring(clrID)" + clrID);
		}
		updateTaskRunner.addTask(new ColorringDeleteTask(clrID));

	}

	private void initDate()throws BOException
	{
		updateTaskRunner = new TaskRunner(ColorringConfig.ColorringUpdateTaskNum,
				ColorringConfig.ColorringMaxReceivedNum);
		convertTaskRunner =new TaskRunner(ColorringConfig.ColorringConvertTaskNum,ColorringConfig.ColorringMaxReceivedNum);
		rootColorCagetory=(Category) Repository.getInstance().getNode(
				RepositoryConstants.ROOT_CATEGORY_GCOLORRING_ID, RepositoryConstants.TYPE_CATEGORY);
		rootContentNode=(Category) Repository.getInstance().getNode(
				RepositoryConstants.ROOT_CONTENT_ID, RepositoryConstants.TYPE_CATEGORY);
		
	}
	public TaskRunner getConvertTaskRunner()
	{
		return convertTaskRunner;
	}
}
