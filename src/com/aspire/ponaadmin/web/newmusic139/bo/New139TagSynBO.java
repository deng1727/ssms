/**
 * com.aspire.ponaadmin.web.newmusic139.bo New139TagSynBO.java
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
 * @author tungke
 *3.12	新歌曲标签信息同步接口
 *
 */
public class New139TagSynBO extends New139BaseSynBO
{
	private static final JLogger logger = LoggerFactory
	.getLogger(New139TagSynBO.class);
	
	
	/**
	 * 
	 *@desc 新139音乐音乐标签导入   手动执行入口
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
				result = dealMusicTagData();
				Date endDate = new Date();
				if(result != null && result.length==3){
					int success  = result[0].intValue();
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
				}else{
					 sb.append("处理失败");
				}
			} catch (BOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				 sb.append("处理失败"+e);
			}
			
			sendMail(sb.toString(),"导入新139音乐标签文件结果邮件");
			

		}
	/**
	 * 
	 *@desc 新139音乐歌曲标签信息导入   自动任务入口
	 *@author dongke
	 *Apr 30, 2011
	 * @return
	 * @throws BOException 
	 * @throws Exception
	 */
	public Integer[] dealMusicTagData() throws BOException {
		String tagFileName = NewMusic139Config.getInstance().getNewTagFileName();
		String [] files = {tagFileName};
		fileNameRex = files;
		
		//result [0] 成功处理;result [1] 失败处理;result [2] 校验失败
		Integer [] result = {new Integer(0),new Integer(0),new Integer(0)};
		List tagList;
		try
		{
			tagList = input139Data();
			this.ChecketAndDealData(tagList,result);
		} catch (Exception e)
		{
			logger.error("获取文件失败");
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException("获取文件失败"+e);
		}
		return result;
	}
	/**
	 * 
	 *@desc 数据检查，过滤掉非法数据，并处理
	 *@author dongke
	 *Apr 30, 2011
	 * @param al
	 * @param result
	 */
    private void ChecketAndDealData(List al,Integer[] result)
	{

		int checkfaildnum = 0;
		int success = 0;
		int dealfailed = 0;
		if (al != null && al.size() > 0)
		{
			for (int i = 0; i < al.size(); i++)
			{
				String[] musicTagLine = (String[]) al.get(i);
				if (musicTagLine != null)
				{
					if (musicTagLine.length != 4)
					{
						logger.warn("歌曲标签数据检查数据字段数不为4;忽略改行；" + musicTagLine);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					
					if (musicTagLine[0] == null || musicTagLine[0].length() == 0
							|| musicTagLine[0].length() > 30)
					{
						logger.warn("发现一项数据不完整：歌曲ID为空或长度不符合标准 30：" + musicTagLine[0]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (musicTagLine[3] == null || musicTagLine[3].length() == 0
							|| musicTagLine[1].length() > 400)
					{
						logger.warn("发现一项数据不完整：标签信息为空或长度不符合标准 400 " + musicTagLine[3]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					
					
					logger.debug("第" + i + "行数据检查完毕,开始入库处理");
					int dealResult = dealLineMusicTagData(musicTagLine);
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
     *@desc 歌曲标签单行处理方法
     *@author dongke
     *May 1, 2011
     * @param musicTagLine
     * @return
     */
    private int dealLineMusicTagData(String [] musicTagLine){
    	String musicId = musicTagLine[0];
    	String tags = buildTags(musicTagLine[3]);
    	 int result = New139BaseSyncDAO.getInstance().updateMusicTags(musicId,tags);
    	return result;
    }
	private  String buildTags(String tags) {
		String[] temp = tags.split(",");
		int count = temp.length;
		if (count == 0) {
			return "{" + tags + "}";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			if (i != 0) {
				sb.append(";");
			}
			sb.append("{").append(temp[i]).append("}");
		}
		return sb.toString();
	}
//	public static void main(String args[]){
//		String tags = "总共,nih,英国";
//		System.out.println(buildTags(tags));
//	} 
	
}
