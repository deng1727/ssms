/**
 * com.aspire.ponaadmin.web.newmusic139.bo New139KeyWorldSynBO.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.newmusic139.NewMusic139Config;
import com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke 139音乐 关键字信息文件数据导入 3.11 新搜索关键字信息同步接口
 * 
 */
public class New139KeyWorldSynBO extends New139BaseSynBO
{
	private static final JLogger logger = LoggerFactory
			.getLogger(New139KeyWorldSynBO.class);

	/**
	 * 
	 *@desc 新139音乐关键字导入   手动执行入口
	 *@author dongke
	 *Apr 30, 2011
	 */
	public void handDeal()
	{
		Date startDate = new Date();
		Integer[] result = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			result = dealKeyWorldData();
			Date endDate = new Date();
			if (result != null && result.length == 3)
			{
				int success = result[0].intValue();
				int checkfaild = result[2].intValue();
				int dealfaild = result[1].intValue();
				sb.append("开始时间：");
				sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
				sb.append(",结束时间：");
				sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
				sb.append("。<h4>处理结果：</h4>");
				sb.append("<p>其中成功处理<b>");
				sb.append(success);
				sb.append("条;<p>处理失败<b>");
				sb.append(dealfaild);
				sb.append("条;<p>数据不符合规范<b>");
				sb.append(checkfaild);
				sb.append("条;<p>");
			}
			else
			{
				sb.append("处理失败");
			}
		} catch (BOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			sb.append("处理失败" + e);
		}

		sendMail(sb.toString(), "导入新139音乐关键字信息文件结果邮件");

	}

	/**
	 * 
	 * @desc 新139音乐歌曲标签信息导入 自动任务入口
	 * @author dongke Apr 30, 2011
	 * @return
	 * @throws BOException
	 * @throws Exception
	 */
	public Integer[] dealKeyWorldData() throws BOException
	{
		String keyWorldFileName = NewMusic139Config.getInstance().getNewKeywordFileName();
		String[] files = { keyWorldFileName };
		fileNameRex = files;

		// result [0] 成功处理;result [1] 失败处理;result [2] 校验失败
		Integer[] result = { new Integer(0), new Integer(0), new Integer(0) };
		List keyWorldList;
		try
		{
			keyWorldList = input139Data();
			this.ChecketAndDealData(keyWorldList, result);
		} catch (Exception e)
		{
			logger.error("获取文件失败");
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException("获取文件失败" + e);
		}
		return result;
	}

	/**
	 * 
	 * @desc 数据检查，过滤掉非法数据，并处理
	 * @author dongke Apr 30, 2011
	 * @param al
	 * @param result
	 */
	private void ChecketAndDealData(List al, Integer[] result)
	{

		int checkfaildnum = 0;
		int success = 0;
		int dealfailed = 0;
		if (al != null && al.size() > 0)
		{
			for (int i = 0; i < al.size(); i++)
			{
				String[] keyWorldLine = (String[]) al.get(i);
				if (keyWorldLine != null)
				{
					if (keyWorldLine.length != 2)
					{
						logger.warn("搜索关键字 数据检查数据字段数不为2;忽略该行；" + keyWorldLine);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}

					if (keyWorldLine[0] == null || keyWorldLine[0].length() == 0
							|| keyWorldLine[0].length() > 30)
					{
						logger.warn("发现一项数据不完整：关键字为空或长度不符合标准 30：" + keyWorldLine[0]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (!"1".equals(keyWorldLine[1]) && !"2".equals(keyWorldLine[1]))
					{
						logger.warn("不明操作类型:\r\n" + keyWorldLine[1]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}

					logger.debug("第" + i + "行数据检查完毕,开始入库处理");
					int dealResult = dealLineKeyWorldData(keyWorldLine);
					if (dealResult == 1)
					{
						success++;
					}
					else
					{
						dealfailed++;
					}
				}
				else
				{
					logger.warn("发现一项数据不完整：第 " + i + "行为null");
					checkfaildnum++;
				}
			}
		}
		else
		{
			logger.warn("文件为空 或者null");
		}
		// 计数器
		if (result != null && result.length == 3)
		{
			if (result[0] == null)
			{
				result[0] = new Integer(success);
			}
			else
			{
				result[0] = new Integer(result[0].intValue() + success);
			}
			if (result[1] == null)
			{
				result[1] = new Integer(dealfailed);
			}
			else
			{
				result[1] = new Integer(result[1].intValue() + dealfailed);
			}
			if (result[2] == null)
			{
				result[2] = new Integer(checkfaildnum);
			}
			else
			{
				result[2] = new Integer(result[2].intValue() + checkfaildnum);
			}
		}
	}

	/**
	 * 
	 * @desc 歌曲标签单行处理方法
	 * @author dongke May 1, 2011
	 * @param musicTagLine
	 * @return
	 */
	private int dealLineKeyWorldData(String[] keyWordLine)
	{
		String keyWorld = keyWordLine[0];
		String changeType = keyWordLine[1];
		int result = 0;
		if (changeType.equals("1"))
		{// 新增
			result = New139BaseSyncDAO.getInstance().addUpdateKeyWorld(keyWorld);
		}
		else if (changeType.equals("2"))
		{// 删除
			result = New139BaseSyncDAO.getInstance().deleteKeyWorld(keyWorld);
		}
		return result;
	}
}
