/**
 * com.aspire.ponaadmin.web.newmusic139.bo New139AlbumBO.java
 * Apr 30, 2011
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.newmusic139.NewMusic139Config;
import com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 *专辑处理类
 */
public class New139AlbumBO extends New139BaseSynBO
{
	private static final JLogger logger = LoggerFactory
	.getLogger(New139AlbumBO.class);
	private String baseAlbumCategoryId;
private String ALBUM_PIC;
	
	/**
	 * 
	 *@desc  初始化 
	 *@author dongke
	 *Apr 30, 2011
	 */
	public  New139AlbumBO()
	{
		baseAlbumCategoryId = NewMusic139Config.getInstance().getBaseAlbumCategoryId();
		ALBUM_PIC = NewMusic139Config.getInstance().getNewAlbumPicUrl();
	}

	/**
	 * 
	 *@desc 新139音乐榜单导入   手动执行入口
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
				result = dealAlbumData();
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
				logger.error("处理失败",e);
				e.printStackTrace();
				 sb.append("处理失败"+e);
			}
			
			sendMail(sb.toString(),"导入新139音乐专辑文件结果邮件");
			

		}
	/**
	 * 
	 *@desc 新139音乐专辑导入   自动任务入口
	 *@author dongke
	 *Apr 30, 2011
	 * @return
	 * @throws BOException 
	 * @throws Exception
	 */
	public Integer[] dealAlbumData() throws BOException {
		String albumFileName = NewMusic139Config.getInstance().getNewAlbumFileName();
		String [] files = {albumFileName};
		fileNameRex = files;
		
		//result [0] 成功处理;result [1] 失败处理;result [2] 校验失败
		Integer [] result = {new Integer(0),new Integer(0),new Integer(0)};
		List albumList;
		try
		{
			albumList = input139Data();
			this.ChecketAndDealData(albumList,result);
		} catch (Exception e)
		{
			logger.error("获取文件失败",e);
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
				String[] albumline = (String[]) al.get(i);
				if (albumline != null)
				{
					if (albumline.length != 9)
					{
						logger.warn("专辑数据检查数据字段数不为9;忽略改行；" + albumline);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (!"1".equals(albumline[8]) && !"2".equals(albumline[8]))
					{
						logger.warn("不明操作类型:\r\n" + albumline[8]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (albumline[0] == null || albumline[0].length() == 0
							|| albumline[0].length() > 30)
					{
						logger.warn("发现一项数据不完整：专辑ID为空或长度不符合标准 30：" + albumline[0]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (albumline[1] == null || albumline[1].length() == 0
							|| albumline[1].length() > 100)
					{
						logger.warn("发现一项数据不完整：专辑名称为空或长度不符合标准 100 " + albumline[1]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (albumline[2] == null || albumline[2].length() == 0
							|| albumline[2].length() > 40)
					{
						logger.warn("发现一项数据不完整：专辑歌手为空或长度不符合标准 40 " + albumline[2]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (albumline[4] == null || albumline[4].length() == 0
							|| albumline[4].length() > 30)
					{
						logger.warn("发现一项数据不完整：专辑图片为空或长度不符合标准 30 " + albumline[4]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (albumline[5] == null || albumline[5].length() == 0
							|| albumline[5].length() > 1)
					{
						logger.warn("发现一项数据不完整：专辑评分为空或长度不符合标准 1 " + albumline[5]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}
					if (albumline[6] == null || albumline[6].length() == 0)
					{
						logger.warn("发现一项数据不完整：专辑排序为空 " + albumline[6]);
						al.remove(i);
						i--;
						checkfaildnum++;
						continue;
					}if("1".equals(albumline[8])){
						//新增修改的前提下
						if (albumline[7] == null || albumline[7].length() <= 0)
						{
							logger.warn("发现一项数据不完整：专辑歌曲列表为空  " + albumline[7]);
							al.remove(i);
							i--;
							checkfaildnum++;
							continue;
						}
					}
					
					logger.debug("第" + i + "行数据检查完毕,开始入库处理");
					int dealResult = dealLineAlbumData(albumline);
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
    *@desc 数据处理
    *@author dongke
    *Apr 30, 2011
    * @param line
    * @return 1， success;0 failed
    */
    
    public int dealLineAlbumData(String[] line) 
	{
		String albumId = line[0];
		String albumName = line[1];
		String changeType = line[8];
		 TransactionDB tdb = null;
		 // 进行事务调用
           
		try{
			 tdb = TransactionDB.getTransactionInstance();
	            New139BaseSyncDAO dao = New139BaseSyncDAO.getTransactionInstance(tdb);
	            
		if (changeType.equals("1"))
		{// 新增修改
			String categoryId = New139BaseSyncDAO.getInstance().getCategoryId(albumId,baseAlbumCategoryId);
			if(categoryId == null || categoryId.equals("")){
				//获取不到，新增
				categoryId =  new Integer(New139BaseSyncDAO.getInstance().getNewCategryId()).toString();
				String [] musicIdList = line[7].split(",");
				
				if(musicIdList != null && musicIdList.length>0){
					Set s = new HashSet();//去重容器
					for(int i = 0; i < musicIdList.length; i ++){
						if(musicIdList[i] != null){
							if(s.contains(musicIdList[i])){
								//重复的音乐上架到同一个货架
								logger.debug("重复的音乐上架到同一个货架,albumid="+albumId);
								continue;
							}
							s.add(musicIdList[i]);
							String songname = New139BaseSyncDAO.getInstance().getMusicName(musicIdList[i]);
							if(songname == null ||songname.equals("")){
								logger.debug("找不到音乐ID="+musicIdList[i]+"对应的音乐名称,albumid="+albumId);
								continue;
							}
							Object [] paras = {musicIdList[i],categoryId,songname,new Integer(i+1)};
							dao.insertMusicRefence(paras);
						}
						
					}
					//insert into t_mb_category_new 
					//(CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME, CATEGORYDESC, SORTID, SUM, ALBUM_ID,  RATE, ALBUM_SINGER)values (?, ?, ?, '1', '0', to_char(sysdate,'yyyy-MM-dd hh24:mm:ss'), ?, ?, 0, ?,  ?, ?);
					Object[] cayInsertParas = {categoryId,albumName,baseAlbumCategoryId,line[3],line[6],line[0],line[5],line[2]};
					dao.insertAlbum(cayInsertParas);
					 tdb.commit();
				}
				
			}
				else
				{
					// 获取到，修改
					String[] musicIdList = line[7].split(",");
					if (musicIdList != null && musicIdList.length > 0)
					{
						Set s = new HashSet();//去重容器
						dao.deleteMusicCategoryRefence(categoryId);
						for (int i = 0; i < musicIdList.length; i++)
						{
							if (musicIdList[i] != null)
							{
								if(s.contains(musicIdList[i])){
									//重复的音乐上架到同一个货架
									logger.debug("重复的音乐上架到同一个货架,albumid="+albumId);
									continue;
								}
								s.add(musicIdList[i]);
								String songname = New139BaseSyncDAO.getInstance()
										.getMusicName(musicIdList[i]);
								if(songname == null ||songname.equals("")){
									logger.debug("找不到音乐ID="+musicIdList[i]+"对应的音乐名称,albumid="+albumId);
									continue;
								}								
									Object[] paras = { musicIdList[i], categoryId,
											songname,new Integer(i+1) };
									dao.insertMusicRefence(paras);								
							}

						}
						//update  T_MB_CATEGORY_new t set t.lupddate=to_char(sysdate,'yyyy-mm-DD hh24:mm:ss'),
						//t.CATEGORYNAME=?,t.CATEGORYDESC=?,t.SORTID=?,t.RATE=?,t.ALBUM_SINGER=? where t.CATEGORYID=? and t.ALBUM_ID=?
						Object[] cayUpdateParas = {albumName,line[3],line[6],line[5],line[2],categoryId,line[0]};
						dao.updateAlbum(cayUpdateParas);
						 tdb.commit();
					}
				}
		}
		else if (changeType.equals("2"))
		{//删除

			String categoryId = New139BaseSyncDAO.getInstance().getCategoryId(albumId,baseAlbumCategoryId);
			if(categoryId != null &&! categoryId.equals("")){
				dao.delete139Category(baseAlbumCategoryId,albumId);
				dao.deleteMusicCategoryRefence(categoryId);
				 tdb.commit();
			}			
		}
		return 1;//返回成功标志
		}catch(Exception e){
			 tdb.rollback();
			 logger.error("处理albumid="+albumId+";name="+albumName+"失败",e);
			e.printStackTrace();
			return 0;//返回失败标志
		}finally
        {
            if (tdb != null)
            {
                tdb.close();
            }
        }
	}
	
}
