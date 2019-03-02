/**
 * SSMS
 * com.aspire.dotcard.basemusic BaseMusicBO.java
 * May 8, 2010
 * @author tungke
 * @version 1.0
 *
 */

package com.aspire.dotcard.basemusic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.basemusic.config.BaseMusicConfig;
import com.aspire.dotcard.basemusic.dao.ToneBoxDAO;
import com.aspire.dotcard.basemusic.dao.ToneBoxSongDAO;
import com.aspire.dotcard.basemusic.vo.ToneBoxSongVO;
import com.aspire.dotcard.basemusic.vo.ToneBoxVO;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 * 
 */
public class BaseMusicBO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseMusicLoadTask.class);

    private BaseMusicBO()
    {

    }

    /**
     * singleton模式的实例
     */
    private static BaseMusicBO instance = new BaseMusicBO();
    private TaskRunner updateTaskRunner;
    private TaskRunner insertRefTaskRunner;

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static final BaseMusicBO getInstance()
    {

        return instance;
    }

    /**
     * 导入音乐
     * 
     * @param existsMusic
     * @param newRefailInfo
     * @param filenameList
     * @throws BOException
     */

    public void importBaseMusic(HashMap existsMusic, Integer[] musicmailInfo,
                                String filenameList[],
                                StringBuffer checkFailureRow, StringBuffer errorFailureRow)
                    throws BOException
    {
        String fileEncoding = BaseMusicConfig.get("fileEncoding");
        updateTaskRunner = new TaskRunner(BaseMusicConfig.getBaseMusicUpdateTaskNum(),
                                          BaseMusicConfig.getBaseMusicMaxReceivedNum());
        String sep = BaseMusicConfig.get("BMusicListSep");
        if (filenameList.length == 0)
        {
            throw new BOException("没有找到本次任务基地音乐元数据的文件",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // 用于文件成功处理的行数
        int lineNumeber = 0;

        // HashMap existsMusic = null;
        // try
        // { //获取所有的已经存在的音乐
        // existsMusic = BMusicDAO.getInstance().getAllexistMusicID();
        // } catch (DAOException e1)
        // {
        // logger.error("从数据库中获取所有基地音乐ID时发生数据库异常！", e1);
        // throw new BOException("从数据库中获取所有基地音乐ID时发生数据库异常",e1);
        // }

        try
        {
            if (musicmailInfo[0] == null)
            {
                musicmailInfo[0] = new Integer(0);
            }
            if (musicmailInfo[1] == null)
            {
                musicmailInfo[1] = new Integer(0);
            }
            if (musicmailInfo[2] == null)
            {
                musicmailInfo[2] = new Integer(0);
            }
            if (musicmailInfo[3] == null)
            {
                musicmailInfo[3] = new Integer(0);
            }
            if (musicmailInfo[4] == null)
            {
                musicmailInfo[4] = new Integer(0);
            }
            if (musicmailInfo[5] == null)
            {
                musicmailInfo[5] = new Integer(0);
            }
            for (int i = 0; i < filenameList.length; i++)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("开始处理文件：" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// 记录文件的行数。
                    musicmailInfo[0] = new Integer(musicmailInfo[0].intValue() + 1);// 总处理行数计数器
                    if (lineNumeber == 1)// 删除第一行bom字符
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("开始处理第" + lineNumeber + "行数据。");
                    }
                    if ("".equals(lineText.trim()))// 对于空行的记录不处理。
                    {
                        logger.debug("该行是空行，不处理。lineNumeber=" + lineNumeber);
                        checkFailureRow.append("<br>").append("  第").append(lineNumeber).append("行为空");
                        musicmailInfo[4] = new Integer(musicmailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                        continue;
                    }
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        int checkResult = BMusicChecker.getInstance()
                                                       .checkDateRecord(dr);
                        logger.debug("检查结果=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("第" + lineNumeber + "行数据检查失败，忽略该数据。");
                            checkFailureRow.append("<br>").append("  第").append(lineNumeber).append("行数据检查失败");
                            musicmailInfo[4] = new Integer(musicmailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                            continue;
                        }
                    }
                    try
                    {
                        BMusicVO bv = this.getBaseMusicVOBylist(dr);

                        if (bv != null)
                        {
                            if (bv.getChangetype().equals("1"))
                            {
                                // 新增
                                Integer existid = ( Integer ) existsMusic.get(bv.getMusicId());
                                if (existid == null)
                                {

                                    // 新增
                                    bv.setDelFlag(0);
                                    this.insertDBBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(0));
                                    musicmailInfo[1] = new Integer(musicmailInfo[1].intValue() + 1);// 新增行数计数器
                                }
                                else
                                {
                                    // 修改
                                    bv.setDelFlag(0);
                                    this.addupdateDBBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(0));
                                    musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// 修改行数计数器
                                }

                            }
                            else if (bv.getChangetype().equals("2"))
                            {
                                // 修改
                                bv.setDelFlag(0);
                                // this.updateDBBaseMusic(bv);
                                this.addupdateDBBaseMusic(bv);
                                existsMusic.put(bv.getMusicId(), new Integer(0));
                                musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// 修改行数计数器
                            }
                            else if (bv.getChangetype().equals("3"))
                            {
                                // 删除
                                bv.setDelFlag(1);// 添加删除标记位
                                this.delDBBaseMusic(bv);
                                // existsMusic.remove(bv.getMusicId());
                                existsMusic.put(bv.getMusicId(), new Integer(1));
                                musicmailInfo[3] = new Integer(musicmailInfo[3].intValue() + 1);// 删除行数计数器
                            }
                            // 分拣数据
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("第" + lineNumeber + "行数据处理失败。", e);
                        musicmailInfo[5] = new Integer(musicmailInfo[5].intValue() + 1);// 处理失败行数计数器
                        errorFailureRow.append(lineNumeber).append(", ");
                        continue;
                    }

                }
            }

        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        updateTaskRunner.waitToFinished();// 等待更新数据库完毕。
        updateTaskRunner.end();// 结束运行器

    }
    /**
     * 导入音乐--新音乐基地数据接入WAP
     * 
     * @param existsMusic
     * @param newRefailInfo
     * @param filenameList
     * @throws BOException
     */
    public void importNewBaseFullMusic(HashMap existsMusic,
    		                       HashMap  existsMusicSingers,
                                   Integer[] musicmailInfo,
                                   String filenameList[],
                                   StringBuffer checkFailureRowByNew,StringBuffer errorFailureRowByNew)
                    throws BOException
    {

        String fileEncoding = BaseMusicConfig.get("fileEncoding");
        updateTaskRunner = new TaskRunner(BaseMusicConfig.getBaseMusicUpdateTaskNum(),
                                          BaseMusicConfig.getBaseMusicMaxReceivedNum());
        String sep = BaseMusicConfig.get("BNewMusicListSep");
        if (filenameList.length == 0)
        {
            throw new BOException("没有找到本次任务基地新音乐元数据的文件",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // 用于文件成功处理的行数
        int lineNumeber = 0;

        try
        {
            if (musicmailInfo[0] == null)
            {
                musicmailInfo[0] = new Integer(0);
            }
            if (musicmailInfo[1] == null)
            {
                musicmailInfo[1] = new Integer(0);
            }
            if (musicmailInfo[2] == null)
            {
                musicmailInfo[2] = new Integer(0);
            }
            if (musicmailInfo[3] == null)
            {
                musicmailInfo[3] = new Integer(0);
            }
            if (musicmailInfo[4] == null)
            {
                musicmailInfo[4] = new Integer(0);
            }
            if (musicmailInfo[5] == null)
            {
                musicmailInfo[5] = new Integer(0);
            }
            for (int i = 0; i < filenameList.length; i++)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("开始处理文件：" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// 记录文件的行数。
                    musicmailInfo[0] = new Integer(musicmailInfo[0].intValue() + 1);// 总处理行数计数器
                    if (lineNumeber == 1)// 删除第一行bom字符
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("开始处理第" + lineNumeber + "行数据。");
                    }
                    if ("".equals(lineText.trim()))// 对于空行的记录不处理。
                    {
                        logger.debug("该行是空行，不处理。lineNumeber=" + lineNumeber);
                      //  checkFailureRowByNew.append("<br>").append("  第").append(lineNumeber).append("行为空");
                        if (musicmailInfo[4] < 201) {
							checkFailureRowByNew.append("<br>").append("  第")
									.append(lineNumeber).append("行为空");
							
						}
						if (musicmailInfo[4] == 201) {
							checkFailureRowByNew.append("<br>").append(
									" .......");
						}
                        musicmailInfo[4] = new Integer(musicmailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                        continue;
                    }
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        // int checkResult =
                        // BMusicChecker.getInstance().checkDateRecord(dr);
                        int checkResult = BMusicChecker.getInstance()
                                                       .checkNewDateRecord(dr);
                        logger.debug("检查结果=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("第" + lineNumeber + "行数据检查失败，忽略该数据。");
                            //checkFailureRowByNew.append("<br>").append("  第").append(lineNumeber).append("行数据检查失败");
                            
                            if (musicmailInfo[4] < 201) {
    							checkFailureRowByNew.append("<br>").append("  第")
    									.append(lineNumeber).append("行数据检查失败");
    							
    						}
    						if (musicmailInfo[4] == 201) {
    							checkFailureRowByNew.append("<br>").append(
    									" .......");
    						}
                            
                            musicmailInfo[4] = new Integer(musicmailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                            continue;
                        }
                    }
                    try
                    {
                        BMusicVO bv = this.getBaseNewMusicVOBylist(dr);
                        
                        if (dr != null && (dr.size() == 7 ||dr.size() == 8||dr.size() == 9||dr.size() == 10||dr.size() == 11))
						{//二期音乐融合

							// 为门户更新singer字段
							if (bv != null && bv.getSingersId() != null)
							{
								String singersArray[] = bv.getSingersId().split("[|]");
								if (singersArray != null && singersArray.length > 0)
								{
									for (int r = 0; r < singersArray.length; r++)
									{
										String singerid = singersArray[r];
										if (singerid != null
												&& existsMusicSingers.get(singerid) != null)
										{
											String singerName = (String) existsMusicSingers
													.get(singerid);
											bv.setSinger(singerName);
											break;
										}
									}
								}
							}
							if(bv.getSinger() == null || "".equals(bv.getSinger())){
								bv.setSinger("未知");
								 logger.error("MusicSingers cann't find singername musicid="+bv.getMusicId());
							}
							// 为门户更新productmask字段

							if (bv != null && bv.getProductMask() != null
									&& bv.getProductMask().length() == 4)
							{
                            	logger.debug("赋值！！！！！！");

								String productMask = bv.getProductMask();

								Integer onlinetype = Integer.valueOf(productMask
										.substring(0, 1));
								Integer colortype = Integer.valueOf(productMask
										.substring(1, 2));
								Integer ringtype = Integer.valueOf(productMask.substring(
										2, 3));
								Integer songtype = Integer.valueOf(productMask.substring(
										3, 4));

								bv.setOnlinetype(onlinetype);
								bv.setColortype(colortype);
								bv.setRingtype(ringtype);
								bv.setSongtype(songtype);
                       
							}

						}
      
                        if (bv != null)
                        {
                            if (bv.getChangetype().equals("1"))
                            {
                                // 新增
                                Integer existid = ( Integer ) existsMusic.get(bv.getMusicId());
                                if (existid == null)
                                {
                                	
        
                                    // 新增
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(1));
                                    musicmailInfo[1] = new Integer(musicmailInfo[1].intValue() + 1);// 新增行数计数器
                                }
                                else
                                {
                                    // 修改
                                    bv.setDelFlag(0);
                                    this.addupdateDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                    		new Integer(1));
                                    musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// 修改行数计数器
                                }

                            }
                            
                            else if (bv.getChangetype().equals("2"))
                            {
                            	
                            	
                            	 // 新增
                                Integer existid = ( Integer ) existsMusic.get(bv.getMusicId());
                                if (existid == null)
                                {
          
                                    // 新增
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(1));
                                    musicmailInfo[1] = new Integer(musicmailInfo[1].intValue() + 1);// 新增行数计数器
                                }
                                else
                                {
                                    // 修改
                                    bv.setDelFlag(0);
                                    this.addupdateDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(1));
                                    musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// 修改行数计数器
                                }
                            	
                                // 修改
                              //  bv.setDelFlag(0);
                                // this.updateDBBaseMusic(bv);
                               // this.addupdateDBNewBaseMusic(bv);
                               // existsMusic.put(bv.getMusicId(), new Integer(0));
                               // musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// 修改行数计数器
                            }
                            else if (bv.getChangetype().equals("3"))
                            {
                                // 删除
                                bv.setDelFlag(1);// 添加删除标记位
                                this.delDBNewBaseMusic(bv);
                                // existsMusic.remove(bv.getMusicId());
                                existsMusic.put(bv.getMusicId(), new Integer(0));
                                musicmailInfo[3] = new Integer(musicmailInfo[3].intValue() + 1);// 删除行数计数器
                            }
                            // 分拣数据
                      
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("第" + lineNumeber + "行数据处理失败。", e);
                        musicmailInfo[5] = new Integer(musicmailInfo[5].intValue() + 1);// 处理失败行数计数器
                        errorFailureRowByNew.append(lineNumeber).append(", ");
                        continue;
                    }

                }
            }

        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        updateTaskRunner.waitToFinished();// 等待更新数据库完毕。
        updateTaskRunner.end();// 结束运行器
        Iterator iterMap=existsMusic.entrySet().iterator(); 
        
        while(iterMap.hasNext()){                         
            Map.Entry  strMap=(Map.Entry)iterMap.next();                          
            if("0".equals(strMap.getValue().toString())){  
             this.delDBFullBaseMusic((String)strMap.getKey());
            iterMap.remove();                                               
        }  }

    }
    /**
     * 导入音乐--新音乐基地数据接入WAP
     * 
     * @param existsMusic
     * @param newRefailInfo
     * @param filenameList
     * @throws BOException
     */
    public void importNewBaseMusic(HashMap existsMusic,
    		                       HashMap  existsMusicSingers,
                                   Integer[] musicmailInfo,
                                   String filenameList[],
                                   StringBuffer checkFailureRowByNew,StringBuffer errorFailureRowByNew)
                    throws BOException
    {

        String fileEncoding = BaseMusicConfig.get("fileEncoding");
        updateTaskRunner = new TaskRunner(BaseMusicConfig.getBaseMusicUpdateTaskNum(),
                                          BaseMusicConfig.getBaseMusicMaxReceivedNum());
        String sep = BaseMusicConfig.get("BNewMusicListSep");
        if (filenameList.length == 0)
        {
            throw new BOException("没有找到本次任务基地新音乐元数据的文件",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // 用于文件成功处理的行数
        int lineNumeber = 0;

        try
        {
            if (musicmailInfo[0] == null)
            {
                musicmailInfo[0] = new Integer(0);
            }
            if (musicmailInfo[1] == null)
            {
                musicmailInfo[1] = new Integer(0);
            }
            if (musicmailInfo[2] == null)
            {
                musicmailInfo[2] = new Integer(0);
            }
            if (musicmailInfo[3] == null)
            {
                musicmailInfo[3] = new Integer(0);
            }
            if (musicmailInfo[4] == null)
            {
                musicmailInfo[4] = new Integer(0);
            }
            if (musicmailInfo[5] == null)
            {
                musicmailInfo[5] = new Integer(0);
            }
            for (int i = 0; i < filenameList.length; i++)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("开始处理文件：" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// 记录文件的行数。
                    musicmailInfo[0] = new Integer(musicmailInfo[0].intValue() + 1);// 总处理行数计数器
                    if (lineNumeber == 1)// 删除第一行bom字符
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("开始处理第" + lineNumeber + "行数据。");
                    }
                    if ("".equals(lineText.trim()))// 对于空行的记录不处理。
                    {
                        logger.debug("该行是空行，不处理。lineNumeber=" + lineNumeber);
                      //  checkFailureRowByNew.append("<br>").append("  第").append(lineNumeber).append("行为空");
                        if (musicmailInfo[4] < 201) {
							checkFailureRowByNew.append("<br>").append("  第")
									.append(lineNumeber).append("行为空");
							
						}
						if (musicmailInfo[4] == 201) {
							checkFailureRowByNew.append("<br>").append(
									" .......");
						}
                        musicmailInfo[4] = new Integer(musicmailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                        continue;
                    }
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        // int checkResult =
                        // BMusicChecker.getInstance().checkDateRecord(dr);
                        int checkResult = BMusicChecker.getInstance()
                                                       .checkNewDateRecord(dr);
                        logger.debug("检查结果=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("第" + lineNumeber + "行数据检查失败，忽略该数据。");
                            //checkFailureRowByNew.append("<br>").append("  第").append(lineNumeber).append("行数据检查失败");
                            
                            if (musicmailInfo[4] < 201) {
    							checkFailureRowByNew.append("<br>").append("  第")
    									.append(lineNumeber).append("行数据检查失败");
    							
    						}
    						if (musicmailInfo[4] == 201) {
    							checkFailureRowByNew.append("<br>").append(
    									" .......");
    						}
                            
                            musicmailInfo[4] = new Integer(musicmailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                            continue;
                        }
                    }
                    try
                    {
                        BMusicVO bv = this.getBaseNewMusicVOBylist(dr);
                        
                        if (dr != null && (dr.size() == 7 ||dr.size() == 8||dr.size() == 9||dr.size() == 11))
						{//二期音乐融合

							// 为门户更新singer字段
							if (bv != null && bv.getSingersId() != null)
							{
								String singersArray[] = bv.getSingersId().split("[|]");
								if (singersArray != null && singersArray.length > 0)
								{
									for (int r = 0; r < singersArray.length; r++)
									{
										String singerid = singersArray[r];
										if (singerid != null
												&& existsMusicSingers.get(singerid) != null)
										{
											String singerName = (String) existsMusicSingers
													.get(singerid);
											bv.setSinger(singerName);
											break;
										}
									}
								}
							}
							if(bv.getSinger() == null || "".equals(bv.getSinger())){
								bv.setSinger("未知");
								 logger.error("MusicSingers cann't find singername musicid="+bv.getMusicId());
							}
							// 为门户更新productmask字段
							if (bv != null && bv.getProductMask() != null
									&& bv.getProductMask().length() == 4)
							{
								String productMask = bv.getProductMask();

								Integer onlinetype = Integer.valueOf(productMask
										.substring(0, 1));
								Integer colortype = Integer.valueOf(productMask
										.substring(1, 2));
								Integer ringtype = Integer.valueOf(productMask.substring(
										2, 3));
								Integer songtype = Integer.valueOf(productMask.substring(
										3, 4));

								bv.setOnlinetype(onlinetype);
								bv.setColortype(colortype);
								bv.setRingtype(ringtype);
								bv.setSongtype(songtype);
							}

						}

                        if (bv != null)
                        {
                            if (bv.getChangetype().equals("1"))
                            {
                                // 新增
                                Integer existid = ( Integer ) existsMusic.get(bv.getMusicId());
                                if (existid == null)
                                {

                                    // 新增
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(0));
                                    musicmailInfo[1] = new Integer(musicmailInfo[1].intValue() + 1);// 新增行数计数器
                                }
                                else
                                {
                                    // 修改
                                    bv.setDelFlag(0);
                                    this.addupdateDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(0));
                                    musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// 修改行数计数器
                                }

                            }
                            else if (bv.getChangetype().equals("2"))
                            {
                            	
                            	
                            	 // 新增
                                Integer existid = ( Integer ) existsMusic.get(bv.getMusicId());
                                if (existid == null)
                                {

                                    // 新增
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(0));
                                    musicmailInfo[1] = new Integer(musicmailInfo[1].intValue() + 1);// 新增行数计数器
                                }
                                else
                                {
                                    // 修改
                                    bv.setDelFlag(0);
                                    this.addupdateDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(0));
                                    musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// 修改行数计数器
                                }
                            	
                                // 修改
                              //  bv.setDelFlag(0);
                                // this.updateDBBaseMusic(bv);
                               // this.addupdateDBNewBaseMusic(bv);
                               // existsMusic.put(bv.getMusicId(), new Integer(0));
                               // musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// 修改行数计数器
                            }
                            else if (bv.getChangetype().equals("3"))
                            {
                                // 删除
                                bv.setDelFlag(1);// 添加删除标记位
                                this.delDBNewBaseMusic(bv);
                                // existsMusic.remove(bv.getMusicId());
                                existsMusic.put(bv.getMusicId(), new Integer(1));
                                musicmailInfo[3] = new Integer(musicmailInfo[3].intValue() + 1);// 删除行数计数器
                            }
                            // 分拣数据
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("第" + lineNumeber + "行数据处理失败。", e);
                        musicmailInfo[5] = new Integer(musicmailInfo[5].intValue() + 1);// 处理失败行数计数器
                        errorFailureRowByNew.append(lineNumeber).append(", ");
                        continue;
                    }

                }
            }

        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        updateTaskRunner.waitToFinished();// 等待更新数据库完毕。
        updateTaskRunner.end();// 结束运行器

    }
    /**
     * 导入音乐--新音乐歌手基地数据接入WAP
     * 
     * @param existsNewMusicSinger
     * @param newmusicSingermailInfo
     * @param filenameList
     * @throws BOException
     */
    public void importNewBaseMusicSinger(HashMap existsNewMusicSinger,
                                   Integer[] newmusicSingermailInfo,
                                   String filenameList[],
                                   StringBuffer checkFailureRowByNew,StringBuffer errorFailureRowByNew)
                    throws BOException
    {

        String fileEncoding = BaseMusicConfig.get("fileEncoding");
        updateTaskRunner = new TaskRunner(BaseMusicConfig.getBaseMusicUpdateTaskNum(),
                                          BaseMusicConfig.getBaseMusicMaxReceivedNum());
        String sep = BaseMusicConfig.get("BNewMusicListSep");
        if (filenameList.length == 0)
        {
            throw new BOException("没有找到本次任务基地新音乐歌手元数据的文件",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // 用于文件成功处理的行数
        int lineNumeber = 0;

        try
        {
            if (newmusicSingermailInfo[0] == null)
            {
            	newmusicSingermailInfo[0] = new Integer(0);
            }
            if (newmusicSingermailInfo[1] == null)
            {
            	newmusicSingermailInfo[1] = new Integer(0);
            }
            if (newmusicSingermailInfo[2] == null)
            {
            	newmusicSingermailInfo[2] = new Integer(0);
            }
            if (newmusicSingermailInfo[3] == null)
            {
            	newmusicSingermailInfo[3] = new Integer(0);
            }
            if (newmusicSingermailInfo[4] == null)
            {
            	newmusicSingermailInfo[4] = new Integer(0);
            }
            if (newmusicSingermailInfo[5] == null)
            {
            	newmusicSingermailInfo[5] = new Integer(0);
            }
            for (int i = 0; i < filenameList.length; i++)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("开始处理文件：" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// 记录文件的行数。
                    newmusicSingermailInfo[0] = new Integer(newmusicSingermailInfo[0].intValue() + 1);// 总处理行数计数器
                    if (lineNumeber == 1)// 删除第一行bom字符
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("开始处理第" + lineNumeber + "行数据。");
                    }
                    if ("".equals(lineText.trim()))// 对于空行的记录不处理。
                    {
						logger.debug("该行是空行，不处理。lineNumeber=" + lineNumeber);

						if (newmusicSingermailInfo[4] < 201) {
							checkFailureRowByNew.append("<br>").append("  第")
									.append(lineNumeber).append("行为空");
							
						}
						if (newmusicSingermailInfo[4] == 201) {
							checkFailureRowByNew.append("<br>").append(
									" .......");
						}
						newmusicSingermailInfo[4] = new Integer(
								newmusicSingermailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
						continue;
					}
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        // int checkResult =
                        // BMusicChecker.getInstance().checkDateRecord(dr);
                        int checkResult = BNewMusicSingerChecker.getInstance()
                                                       .checkDateRecord(dr);
                        logger.debug("检查结果=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("第" + lineNumeber + "行数据检查失败，忽略该数据。");
                           // checkFailureRowByNew.append("<br>").append("  第").append(lineNumeber).append("行数据检查失败");
                            
                        	if (newmusicSingermailInfo[4] < 201) {
    							checkFailureRowByNew.append("<br>").append("  第")
    									.append(lineNumeber).append("行数据检查失败");
    							
    						}
    						if (newmusicSingermailInfo[4] == 201) {
    							checkFailureRowByNew.append("<br>").append(
    									" .......");
    						}
                            
                            newmusicSingermailInfo[4] = new Integer(newmusicSingermailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                            continue;
                        }
                    }
                    try
                    {
                        BNewMusicSingerVO bv = this.getBaseNewMusicSingerVOBylist(dr);

                        if (bv != null)
                        {
                            if (bv.getChangetype().equals("1"))
                            {
                                // 新增
                                String existSingerName = ( String ) existsNewMusicSinger.get(bv.getSingerId());
                                if (existSingerName == null)
                                {

                                    // 新增
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusicSinger(bv);
                                    existsNewMusicSinger.put(bv.getSingerId(),
                                    		bv.getSingername());
                                    newmusicSingermailInfo[1] = new Integer(newmusicSingermailInfo[1].intValue() + 1);// 新增行数计数器
                                }
                                else
                                {
                                    // 修改
                                    bv.setDelFlag(0);
                                    this.updateDBNewBaseMusicSinger(bv);
                                    existsNewMusicSinger.put(bv.getSingerId(),
                                    		bv.getSingername());
                                    newmusicSingermailInfo[2] = new Integer(newmusicSingermailInfo[2].intValue() + 1);// 修改行数计数器
                                }

                            }
                            else if (bv.getChangetype().equals("2"))
                            {
                            	
                            	 // 修改
                            	 String existSingerName = ( String ) existsNewMusicSinger.get(bv.getSingerId());
                                if (existSingerName == null)
                                {

                                    // 新增
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusicSinger(bv);
                                    existsNewMusicSinger.put(bv.getSingerId(),
                                    		bv.getSingername());
                                    newmusicSingermailInfo[1] = new Integer(newmusicSingermailInfo[1].intValue() + 1);// 新增行数计数器
                                }
                                else
                                {
                                    // 修改
                                    bv.setDelFlag(0);
                                    this.updateDBNewBaseMusicSinger(bv);
                                    existsNewMusicSinger.put(bv.getSingerId(),
                                    		bv.getSingername());
                                    newmusicSingermailInfo[2] = new Integer(newmusicSingermailInfo[2].intValue() + 1);// 修改行数计数器
                                }
                                // 修改
                              //  bv.setDelFlag(0);
                                // this.updateDBBaseMusic(bv);
                               // this.updateDBNewBaseMusicSinger(bv);
                              //  existsNewMusicSinger.put(bv.getSingerId(), new Integer(0));
                               // newmusicSingermailInfo[2] = new Integer(newmusicSingermailInfo[2].intValue() + 1);// 修改行数计数器
                            }
                            else if (bv.getChangetype().equals("3"))
                            {
                                // 删除
                                bv.setDelFlag(1);// 添加删除标记位
                                this.delDBNewBaseMusicSinger(bv);
                                // existsMusic.remove(bv.getMusicId());
                                existsNewMusicSinger.put(bv.getSingerId(), bv.getSingername());
                                newmusicSingermailInfo[3] = new Integer(newmusicSingermailInfo[3].intValue() + 1);// 删除行数计数器
                            }
                            // 分拣数据
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("第" + lineNumeber + "行数据处理失败。", e);
                        newmusicSingermailInfo[5] = new Integer(newmusicSingermailInfo[5].intValue() + 1);// 处理失败行数计数器
                        errorFailureRowByNew.append(lineNumeber).append(", ");
                        continue;
                    }

                }
            }

        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        updateTaskRunner.waitToFinished();// 等待更新数据库完毕。
        updateTaskRunner.end();// 结束运行器

    }
    
    
    /**
     * 导入音乐--新音乐专辑 数据接入WAP
     * 
     * @param existsNewMusicSinger
     * @param newmusicSingermailInfo
     * @param filenameList
     * @throws BOException
     */
    public void importNewBaseMusicAlbumCate(HashMap existsNewMusicSingers,
    										HashMap existsAlbumCate,
                                   Integer[] newmusicAlbummailInfo,
                                    String categoryID,
                                   String filenameList[],
                                   StringBuffer checkFailureRowByNew,StringBuffer errorFailureRowByNew)
                    throws BOException
    {

        String fileEncoding = BaseMusicConfig.get("fileEncoding");
        updateTaskRunner = new TaskRunner(BaseMusicConfig.getBaseMusicUpdateTaskNum(),
                                          BaseMusicConfig.getBaseMusicMaxReceivedNum());
        String sep = BaseMusicConfig.get("BNewMusicListSep");
        if (filenameList.length == 0)
        {
            throw new BOException("没有找到本次任务基地新音乐专辑 数据的文件",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // 用于文件成功处理的行数
        int lineNumeber = 0;

        try
        {
            if (newmusicAlbummailInfo[0] == null)
            {
            	newmusicAlbummailInfo[0] = new Integer(0);
            }
            if (newmusicAlbummailInfo[1] == null)
            {
            	newmusicAlbummailInfo[1] = new Integer(0);
            }
            if (newmusicAlbummailInfo[2] == null)
            {
            	newmusicAlbummailInfo[2] = new Integer(0);
            }
            if (newmusicAlbummailInfo[3] == null)
            {
            	newmusicAlbummailInfo[3] = new Integer(0);
            }
            if (newmusicAlbummailInfo[4] == null)
            {
            	newmusicAlbummailInfo[4] = new Integer(0);
            }
            if (newmusicAlbummailInfo[5] == null)
            {
            	newmusicAlbummailInfo[5] = new Integer(0);
            }
            for (int i = 0; i < filenameList.length; i++)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("开始处理文件：" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// 记录文件的行数。
                    newmusicAlbummailInfo[0] = new Integer(newmusicAlbummailInfo[0].intValue() + 1);// 总处理行数计数器
                    if (lineNumeber == 1)// 删除第一行bom字符
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("开始处理第" + lineNumeber + "行数据。");
                    }
                    if ("".equals(lineText.trim()))// 对于空行的记录不处理。
                    {
                        logger.debug("该行是空行，不处理。lineNumeber=" + lineNumeber);
                       // checkFailureRowByNew.append("<br>").append("  第").append(lineNumeber).append("行为空");
                    	if (newmusicAlbummailInfo[4] < 201) {
							checkFailureRowByNew.append("<br>").append("  第")
									.append(lineNumeber).append("行为空");
							
						}
						if (newmusicAlbummailInfo[4] == 201) {
							checkFailureRowByNew.append("<br>").append(
									" .......");
						}
                        newmusicAlbummailInfo[4] = new Integer(newmusicAlbummailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                        continue;
                    }
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        // int checkResult =
                        // BMusicChecker.getInstance().checkDateRecord(dr);
                        int checkResult = BMusicNewChecker.getInstance()
                                                       .checkAlbumDateRecord(dr);
                        logger.debug("检查结果=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("第" + lineNumeber + "行数据检查失败，忽略该数据。");
                            //checkFailureRowByNew.append("<br>").append("  第").append(lineNumeber).append("行数据检查失败");
                            
                        	if (newmusicAlbummailInfo[4] < 201) {
    							checkFailureRowByNew.append("<br>").append("  第")
    									.append(lineNumeber).append("行数据检查失败");
    							
    						}
    						if (newmusicAlbummailInfo[4] == 201) {
    							checkFailureRowByNew.append("<br>").append(
    									" .......");
    						}
                            
                            newmusicAlbummailInfo[4] = new Integer(newmusicAlbummailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                            continue;
                        }
                    }
                    try
                    {
                    	BNewMusicAlbumVO bv = this.getBaseNewMusicAlbumVOBylist(dr);
                    	bv.setSortId(new Integer(lineNumeber));
                    	
                    	bv.setParentId(categoryID);
                    	  //为门户更新singer字段
                        if(bv != null && bv.getSingersid() != null){
                        	String singersArray[] = bv.getSingersid().split("[|]");
                        	if(singersArray != null && singersArray.length>0){
                        		for(int r =0 ; r<singersArray.length;r++){
                        			String singerid = singersArray[r];
                        			if(singerid != null && existsNewMusicSingers.get(singerid) != null){
                        				String singerName = (String)existsNewMusicSingers.get(singerid);
                        				bv.setSinger(singerName);
                        				break;
                        			}
                        		}
                        	}
                        }
                        if(bv.getSinger() == null || "".equals(bv.getSinger())){
							bv.setSinger("未知");
							 logger.error("MusicSingers cann't find singername Albumid="+bv.getAlbumId());
						}
                        if (bv != null)
                        {
                            if (bv.getChangetype().equals("1"))
                            {
                                // 新增
                                Integer existid = ( Integer ) existsAlbumCate.get(bv.getAlbumId());
                                if (existid == null)
                                {

                                    // 新增
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusicAlbum(bv);
                                    existsAlbumCate.put(bv.getAlbumId(),
                                    		new Integer(0));
                                    newmusicAlbummailInfo[1] = new Integer(newmusicAlbummailInfo[1].intValue() + 1);// 新增行数计数器
                                }
                                else
                                {
                                    // 修改
                                    bv.setDelFlag(0);
                                    this.updateDBNewBaseMusicAlbum(bv);
                                    existsAlbumCate.put(bv.getAlbumId(),
                                    		new Integer(0));
                                    newmusicAlbummailInfo[2] = new Integer(newmusicAlbummailInfo[2].intValue() + 1);// 修改行数计数器
                                }

                            }
                            else if (bv.getChangetype().equals("2"))
                            {
                            	
                            	 // 新增
                                Integer existid = ( Integer ) existsAlbumCate.get(bv.getAlbumId());
                                if (existid == null)
                                {

                                    // 新增
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusicAlbum(bv);
                                    existsAlbumCate.put(bv.getAlbumId(),
                                    		new Integer(0));
                                    newmusicAlbummailInfo[1] = new Integer(newmusicAlbummailInfo[1].intValue() + 1);// 新增行数计数器
                                }
                                else
                                {
                                    // 修改
                                    bv.setDelFlag(0);
                                    this.updateDBNewBaseMusicAlbum(bv);
                                    existsAlbumCate.put(bv.getAlbumId(),
                                    		new Integer(0));
                                    newmusicAlbummailInfo[2] = new Integer(newmusicAlbummailInfo[2].intValue() + 1);// 修改行数计数器
                                }
                                // 修改
                              //  bv.setDelFlag(0);
                                // this.updateDBBaseMusic(bv);
                               // this.updateDBNewBaseMusicSinger(bv);
                              //  existsNewMusicSinger.put(bv.getSingerId(), new Integer(0));
                               // newmusicSingermailInfo[2] = new Integer(newmusicSingermailInfo[2].intValue() + 1);// 修改行数计数器
                            }
                            else if (bv.getChangetype().equals("3"))
                            {
                                // 删除
                                bv.setDelFlag(1);// 添加删除标记位
                                this.delDBNewBaseMusicAlbum(bv);
                                // existsMusic.remove(bv.getMusicId());
                                existsAlbumCate.put(bv.getAlbumId(),new Integer(1));
                                newmusicAlbummailInfo[3] = new Integer(newmusicAlbummailInfo[3].intValue() + 1);// 删除行数计数器
                            }
                            // 分拣数据
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("第" + lineNumeber + "行数据处理失败。", e);
                        newmusicAlbummailInfo[5] = new Integer(newmusicAlbummailInfo[5].intValue() + 1);// 处理失败行数计数器
                        errorFailureRowByNew.append(lineNumeber).append(", ");
                        continue;
                    }

                }
            }

        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        updateTaskRunner.waitToFinished();// 等待更新数据库完毕。
        updateTaskRunner.end();// 结束运行器

    }
    
    /**
     * 上架新音乐专辑商品
     * 
     * @param categoryID
     * @param fileName
     */
    public void importNewMusicRefrence(
    									HashMap existsAlbum,
    									HashMap existsMusic,
                                       HashMap existsRefMusic,
                                       Integer[] newRefmailInfo,
                                     
                                       String[] filenameList,
                                       StringBuffer checkFailureRowByLatest,
                                       StringBuffer errorFailureRowByLatest)
                    throws BOException
    {
        String fileEncoding = BaseMusicConfig.get("fileEncoding");
        insertRefTaskRunner = new TaskRunner(BaseMusicConfig.getBaseMusicUpdateTaskNum(),
                                             BaseMusicConfig.getBaseMusicMaxReceivedNum());
        String sep = BaseMusicConfig.get("BNewMusicListSep");
        if (filenameList.length == 0)
        {
            throw new BOException("没有找到本次任务最新音乐商品的文件",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // 用于文件成功处理的行数
        int lineNumeber = 0;
        int count = 0; // 插入的行数
        // HashMap existsMusic = null;
//        try
//        {
          //  boolean checkCategoryExist = BMusicDAO.getInstance()
          //                                        .checkCategoryById(categoryID);
            // 检查货架是否存在，不存在则退出
           // if (!checkCategoryExist)
           // {
           //     logger.error("从数据库中找不到最新货架ID为" + categoryID + "的操作货架！");
            //    throw new BOException("从数据库中找不到最新货架ID为" + categoryID + "的操作货架！");
           // }
            // 获取所有的已经存在的音乐
            // existsMusic = BMusicDAO.getInstance().getAllexistMusicID();

            // 下架最新货架下的所有商品
           // BMusicDAO.getInstance().delBMusicRefByCateID(categoryID);

//        }
//        catch (DAOException e1)
//        {
//            logger.error("从数据库中获取所有基地音乐ID时发生数据库异常！", e1);
//            throw new BOException("从数据库中获取所有基地音乐ID时发生数据库异常", e1);
//        }

        try
        {

            if (newRefmailInfo[0] == null)
            {
                newRefmailInfo[0] = new Integer(0);
            }
            if (newRefmailInfo[1] == null)
            {
                newRefmailInfo[1] = new Integer(0);
            }
            if (newRefmailInfo[2] == null)
            {
                newRefmailInfo[2] = new Integer(0);
            }
            if (newRefmailInfo[3] == null)
            {
                newRefmailInfo[3] = new Integer(0);
            }
            if (newRefmailInfo[4] == null)
            {
                newRefmailInfo[4] = new Integer(0);
            }
            if (newRefmailInfo[5] == null)
            {
                newRefmailInfo[5] = new Integer(0);
            }
            for (int i = 0; i < filenameList.length; i++)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("开始处理文件：" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));

                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// 记录文件的行数。
                    newRefmailInfo[0] = new Integer(newRefmailInfo[0].intValue() + 1);// 处理行数计数器
                    if (lineNumeber == 1)// 删除第一行bom字符
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("开始处理第" + lineNumeber + "行数据。");
                    }
                    if ("".equals(lineText.trim()))// 对于空行的记录不处理。
                    {
                        logger.debug("该行是空行，不处理。lineNumeber=" + lineNumeber);
                        //checkFailureRowByLatest.append("<br>").append("  第").append(lineNumeber).append("行为空");
                        
                        if (newRefmailInfo[4] < 201) {
                        	checkFailureRowByLatest.append("<br>").append("  第")
									.append(lineNumeber).append("行为空");
							
						}
						if (newRefmailInfo[4] == 201) {
							checkFailureRowByLatest.append("<br>").append(
									" .......");
						}
                        
                        newRefmailInfo[4] = new Integer(newRefmailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                        continue;
                    }
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        int checkResult = BMusicNewChecker.getInstance()
                                                          .checkDateRecord(dr);
                        logger.debug("检查结果=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("第" + lineNumeber + "行数据检查失败，忽略该数据。");
                            //checkFailureRowByLatest.append("<br>").append("  第").append(lineNumeber).append("行数据检查失败");
                            if (newRefmailInfo[4] < 201) {
                            	checkFailureRowByLatest.append("<br>").append("  第")
    									.append(lineNumeber).append("行数据检查失败");
    							
    						}
    						if (newRefmailInfo[4] == 201) {
    							checkFailureRowByLatest.append("<br>").append(
    									" .......");
    						}
                            
                            newRefmailInfo[4] = new Integer(newRefmailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                            continue;
                        }
                    }
                    try
                    {

                        if (dr != null && dr.size() == 4)
                        {

                            // 新增
                            Integer existid = ( Integer ) existsMusic.get(dr.get(1));
                            String existsalbumCateId = ( String ) existsAlbum.get(dr.get(0));
                            if (existid != null && existid.intValue() == 0 && existsalbumCateId != null )
                            {// 存在并且未删除
                                // 新增
                               // dr.add(categoryID);
                                String goodsid = ( String ) dr.get(0)
                                                 + ( String ) dr.get(1);
                                String temp = ( String ) existsRefMusic.get(goodsid);
                                dr.set(3,existsalbumCateId);
                                dr.remove(0);
                                dr.add(1,"");
                                if (temp == null)
                                {// 没有重复上架
                                    this.insertDBBaseMusicNewRef(dr);
                                    count++;
                                    existsRefMusic.put(goodsid,
                                                       ( String ) dr.get(0));
                                    newRefmailInfo[1] = new Integer(newRefmailInfo[1].intValue() + 1);// 新增行数计数器
                                }
                                else
                                {
                                    // 重复上架
                                    logger.error("重复上架,上架音乐ID:" + dr.get(0)
                                                 + ",上架货架为：" + existsalbumCateId);
                                    newRefmailInfo[2] = new Integer(newRefmailInfo[2].intValue() + 1);// 重复上架失败行数计数器
                                }
                            }
                            else
                            {
                                // 音乐库中没有该音乐，不予上架
                                logger.error("最新榜单上架，第" + lineNumeber
                                             + "行，音乐库中没有Musicid=" + dr.get(0)
                                             + "的音乐 不予上架");
                                newRefmailInfo[3] = new Integer(newRefmailInfo[3].intValue() + 1);// 元数据不存在上架失败行数计数器
                            }

                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("第" + lineNumeber + "行数据处理失败。", e);
                        newRefmailInfo[5] = new Integer(newRefmailInfo[5].intValue() + 1);// 数据处理失败行数计数器
                        errorFailureRowByLatest.append(lineNumeber).append(", ");
                        continue;
                    }

                }
            }
            // 更新货架商品数量
            //BMusicDAO.getInstance().updateCategorySum(count, categoryID);

        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        insertRefTaskRunner.waitToFinished();// 等待更新数据库完毕。
        insertRefTaskRunner.end();// 结束运行器

    }

    /**
     * 上架旧榜单音乐商品  已废弃
     * 
     * @param categoryID
     * @param fileName
     */
    public void importCharMusicRefrence(HashMap existsMusic,
                                        HashMap existsRefMusic,
                                        Integer[] charRefmailInfo,
                                        String categoryID, String[] filenameList)
                    throws BOException
    {
        String fileEncoding = BaseMusicConfig.get("fileEncoding");
        insertRefTaskRunner = new TaskRunner(BaseMusicConfig.getBaseMusicUpdateTaskNum(),
                                             BaseMusicConfig.getBaseMusicMaxReceivedNum());
        String sep = BaseMusicConfig.get("BMusicListSep");
        if (filenameList.length == 0)
        {
            logger.error("没有找到本次任务榜单音乐商品的文件！");
            return;
            // throw new BOException("没有找到本次任务的文件",
            // DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // 用于文件成功处理的行数
        int lineNumeber = 0;
        // HashMap existsMusic = null;
        HashMap excueCharsRef = new HashMap();
        try
        {
            boolean checkCategoryExist = BMusicDAO.getInstance()
                                                  .checkCategoryById(categoryID);
            // 检查货架是否存在，不存在则退出
            if (!checkCategoryExist)
            {
                logger.error("从数据库中找不到榜单货架ID为" + categoryID + "的操作货架！");
                throw new BOException("从数据库中找不到榜单货架ID为" + categoryID + "的操作货架！");
            }
            // 获取所有的已经存在的音乐
            // existsMusic =
            // BMusicDAO.getInstance().getAllexistMusicID();//不用再去数据库查了

            // 下架最新货架下的所有商品
            // BMusicDAO.getInstance().delBMusicRefByCateID(categoryID);

        }
        catch (DAOException e1)
        {
            logger.error("从数据库中获取所有基地音乐ID时发生数据库异常！", e1);
            throw new BOException("从数据库中获取所有基地音乐ID时发生数据库异常", e1);
        }

        try
        {
            if (charRefmailInfo[0] == null)
            {
                charRefmailInfo[0] = new Integer(0);
            }
            if (charRefmailInfo[1] == null)
            {
                charRefmailInfo[1] = new Integer(0);
            }
            if (charRefmailInfo[2] == null)
            {
                charRefmailInfo[2] = new Integer(0);
            }
            if (charRefmailInfo[3] == null)
            {
                charRefmailInfo[3] = new Integer(0);
            }
            if (charRefmailInfo[4] == null)
            {
                charRefmailInfo[4] = new Integer(0);
            }
            if (charRefmailInfo[5] == null)
            {
                charRefmailInfo[5] = new Integer(0);
            }
            for (int i = 0; i < filenameList.length; i++)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("开始处理文件：" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));

                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// 记录文件的行数。
                    charRefmailInfo[0] = new Integer(charRefmailInfo[0].intValue() + 1);// 数据处理行数计数器
                    if (lineNumeber == 1)// 删除第一行bom字符
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("开始处理第" + lineNumeber + "行数据。");
                    }
                    if ("".equals(lineText.trim()))// 对于空行的记录不处理。
                    {
                        logger.debug("该行是空行，不处理。lineNumeber=" + lineNumeber);
                        charRefmailInfo[4] = new Integer(charRefmailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                        continue;
                    }
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        int checkResult = BMusicHotTopChecker.getInstance()
                                                             .checkDateRecord(dr);
                        logger.debug("检查结果=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("第" + lineNumeber + "行数据检查失败，忽略该数据。");
                            charRefmailInfo[4] = new Integer(charRefmailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                            continue;
                        }
                    }
                    try
                    {

                        if (dr != null && dr.size() == 4)
                        {
                            // 新增
                            Integer existid = ( Integer ) existsMusic.get(dr.get(1));

                            // 放在清掉了原来的
                            List tempcaref = ( List ) excueCharsRef.get(dr.get(0));
                            if (tempcaref == null)
                            {
                                excueCharsRef.put(dr.get(0), null);// 初始化货架名称

                            }
                            if (existid != null && existid.intValue() == 0)
                            { // 存在，并且未删除
                                // 新增
                                excueCharsRef = this.getBaseMusicCharBylist(excueCharsRef,
                                                                            dr);
                                charRefmailInfo[1] = new Integer(charRefmailInfo[1].intValue() + 1);// 新增行数计数器
                            }
                            else
                            {
                                // 音乐库中没有该音乐，不予上架
                                logger.error("排行榜单上架，第" + lineNumeber
                                             + "行，音乐库中没有Musicid=" + dr.get(1)
                                             + "的音乐 不予上架");
                                charRefmailInfo[3] = new Integer(charRefmailInfo[3].intValue() + 1);// 元数据不存在上架失败行数计数器
                                continue;

                            }

                            // 分拣数据
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("第" + lineNumeber + "行数据处理失败。", e);
                        charRefmailInfo[5] = new Integer(charRefmailInfo[5].intValue() + 1);// 数据处理失败行数计数器
                        continue;
                    }

                }
            }
            // 对excueCharsRef进行处理
            // 开始上架音乐商品
            for (Iterator iter = excueCharsRef.entrySet().iterator(); iter.hasNext();)
            {
                Map.Entry entry = ( Map.Entry ) iter.next();
                String cateName = ( String ) entry.getKey();// 货架名称
                List val = ( List ) entry.getValue();// 具体货架上的音乐商品
                int cou = 0;
                if (val != null)
                {
                    cou = val.size();
                }
                String subCategoryID = BMusicDAO.getInstance()
                                                .getCategoryIDByNameAndParCid(categoryID,
                                                                              cateName,"",//add by dongke 20130607 music_list change
                                                                              cou);
                // 下架分类货架二级货架下的所有商品
                BMusicDAO.getInstance().delBMusicRefByCateID(subCategoryID);
                int count = 0;
                if (val != null && val.size() > 0)
                {
                    for (int j = 0; j < val.size(); j++)
                    {
                        List ref = ( List ) val.get(j);
                        if (ref != null && ref.size() == 3)
                        {
                            // String temp = (String)ref.get(0);
                            ref.add(subCategoryID);
                            String goodsid = ( String ) ref.get(0)
                                             + subCategoryID;
                            String temp = ( String ) existsRefMusic.get(goodsid);
                            if (temp == null)
                            {// 没有重复上架
                                this.insertDBBaseMusicNewRef(ref);
                                existsRefMusic.put(goodsid,
                                                   ( String ) ref.get(0));
                                count++;
                            }
                            else
                            {
                                // 重复上架
                                logger.error("重复上架,上架音乐ID:" + ref.get(0)
                                             + ",上架货架为：" + subCategoryID);
                                charRefmailInfo[2] = new Integer(charRefmailInfo[2].intValue() + 1);// 重复上架行数计数器
                            }

                        }

                    }

                }
                // 更新货架商品数量
                BMusicDAO.getInstance().updateCategorySum(count, subCategoryID);
            }

        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        insertRefTaskRunner.waitToFinished();// 等待更新数据库完毕。
        insertRefTaskRunner.end();// 结束运行器

    }

    /**
     * 上架新榜单音乐商品
     * 
     * @param categoryID
     * @param fileName
     */
    public void importCharNewMusicRefrence(HashMap existsMusic,
                                           HashMap existsRefMusic,
                                           Integer[] charRefmailInfo,
                                           String categoryID,
                                           String[] filenameList,
                                           StringBuffer checkFailureRowByUp,
                                           StringBuffer errorFailureRowByUp)
                    throws BOException
    {
        String fileEncoding = BaseMusicConfig.get("fileEncoding");
        insertRefTaskRunner = new TaskRunner(BaseMusicConfig.getBaseMusicUpdateTaskNum(),
                                             BaseMusicConfig.getBaseMusicMaxReceivedNum());
        String sep = BaseMusicConfig.get("BNewMusicListSep");
        if (filenameList.length == 0)
        {
            logger.error("没有找到本次任务新音乐榜单音乐商品的文件！");
            return;
            // throw new BOException("没有找到本次任务的文件",
            // DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // 用于文件成功处理的行数
        int lineNumeber = 0;
        // HashMap existsMusic = null;
        HashMap excueCharsRef = new HashMap();
      //  HashMap typeMapCate = new HashMap();
        //String typeMapingCategoryId = BaseMusicConfig.get("typeMapingCategoryId");// 1000000344|02,100004545|04

        /*try
        {
            //String[] typeMapingCategoryIds = typeMapingCategoryId.split(",");
            if (typeMapingCategoryIds != null)
            {
                for (int m = 0; m < typeMapingCategoryIds.length; m++)
                {
                    String[] mapcates = typeMapingCategoryIds[m].split("[|]");
                    if (mapcates != null && mapcates.length == 2)
                    {
                        boolean checkCategoryExist = BMusicDAO.getInstance()
                                                              .checkCategoryById(mapcates[0]);
                        // 检查货架是否存在，不存在则退出
                        if (!checkCategoryExist)
                        {
                            logger.error("从数据库中找不到榜单货架ID为" + categoryID
                                         + "的操作货架！");
                            // throw new
                            // BOException("从数据库中找不到榜单货架ID为"+categoryID+"的操作货架！");
                        }
                        else
                        {
                            typeMapCate.put(mapcates[1], mapcates[0]);
                        }
                    }
                }

            }
            // 获取所有的已经存在的音乐
            // existsMusic =
            // BMusicDAO.getInstance().getAllexistMusicID();//不用再去数据库查了

            // 下架最新货架下的所有商品
            // BMusicDAO.getInstance().delBMusicRefByCateID(categoryID);

        }
        catch (DAOException e1)
        {
            logger.error("从数据库中获取所有基地音乐ID时发生数据库异常！", e1);
            throw new BOException("从数据库中获取所有基地音乐ID时发生数据库异常", e1);
        }
*/
        try
        {
            if (charRefmailInfo[0] == null)
            {
                charRefmailInfo[0] = new Integer(0);
            }
            if (charRefmailInfo[1] == null)
            {
                charRefmailInfo[1] = new Integer(0);
            }
            if (charRefmailInfo[2] == null)
            {
                charRefmailInfo[2] = new Integer(0);
            }
            if (charRefmailInfo[3] == null)
            {
                charRefmailInfo[3] = new Integer(0);
            }
            if (charRefmailInfo[4] == null)
            {
                charRefmailInfo[4] = new Integer(0);
            }
            if (charRefmailInfo[5] == null)
            {
                charRefmailInfo[5] = new Integer(0);
            }
            for (int i = 0; i < filenameList.length; i++)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("开始处理文件：" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));

                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// 记录文件的行数。
                    charRefmailInfo[0] = new Integer(charRefmailInfo[0].intValue() + 1);// 数据处理行数计数器
                    if (lineNumeber == 1)// 删除第一行bom字符
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("开始处理第" + lineNumeber + "行数据。");
                    }
                    if ("".equals(lineText.trim()))// 对于空行的记录不处理。
                    {
                        logger.debug("该行是空行，不处理。lineNumeber=" + lineNumeber);
                        //checkFailureRowByUp.append("<br>").append("  第").append(lineNumeber).append("行为空");
                        
                        if (charRefmailInfo[4] < 201) {
                        	checkFailureRowByUp.append("<br>").append("  第")
									.append(lineNumeber).append("行为空");
							
						}
						if (charRefmailInfo[4] == 201) {
							checkFailureRowByUp.append("<br>").append(
									" .......");
						}
                        
                        charRefmailInfo[4] = new Integer(charRefmailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                        continue;
                    }
                    List dr = this.readDataRecordChar(lineText, sep);
                    if (dr != null)
                    {
                        int checkResult = BMusicHotTopChecker.getInstance()
                                                             .checkDateRecord(dr);
                        logger.debug("检查结果=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("第" + lineNumeber + "行数据检查失败，忽略该数据。");
                            //checkFailureRowByUp.append("<br>").append("  第").append(lineNumeber).append("行数据检查失败");
                            
                            if (charRefmailInfo[4] < 201) {
                            	checkFailureRowByUp.append("<br>").append("  第")
    									.append(lineNumeber).append("行数据检查失败");
    							
    						}
    						if (charRefmailInfo[4] == 201) {
    							checkFailureRowByUp.append("<br>").append(
    									" .......");
    						}
                            
                            charRefmailInfo[4] = new Integer(charRefmailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                            continue;
                        }
                    }
                    try
                    {

                        if (dr != null && dr.size() == 5)
                        {
                            // 新增
                            Integer existid = ( Integer ) existsMusic.get(dr.get(1));

                            // 放在清掉了原来的
                            List tempcaref = ( List ) excueCharsRef.get(dr.get(4)
                                                                        + "|"
                                                                        + dr.get(0));
                            if (tempcaref == null)
                            {
                                excueCharsRef.put(dr.get(4) + "|" + dr.get(0),
                                                  null);// 初始化货架名称,分类+货架名称：// 榜单ID+榜单名称 change by dongke 20130607
                                // 04|新歌会员抢先听

                            }
                            if (existid != null && existid.intValue() == 0)
                            { // 存在，并且未删除
                                // 新增
                                excueCharsRef = this.getBaseMusicCharBylist(excueCharsRef,
                                                                            dr);
                                charRefmailInfo[1] = new Integer(charRefmailInfo[1].intValue() + 1);// 新增行数计数器
                            }
                            else
                            {
                                // 音乐库中没有该音乐，不予上架
                                logger.error("排行榜单上架，第" + lineNumeber
                                             + "行，音乐库中没有Musicid=" + dr.get(1)
                                             + "的音乐 不予上架");
                                charRefmailInfo[3] = new Integer(charRefmailInfo[3].intValue() + 1);// 元数据不存在上架失败行数计数器
                                continue;

                            }

                            // 分拣数据
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("第" + lineNumeber + "行数据处理失败。", e);
                        charRefmailInfo[5] = new Integer(charRefmailInfo[5].intValue() + 1);// 数据处理失败行数计数器
                        errorFailureRowByUp.append(lineNumeber).append(", ");
                        continue;
                    }

                }
            }
            // 对excueCharsRef进行处理
            // 开始上架音乐商品
            for (Iterator iter = excueCharsRef.entrySet().iterator(); iter.hasNext();)
            {
                Map.Entry entry = ( Map.Entry ) iter.next();
                String typeAndcateName = ( String ) entry.getKey();// 货架名称
                List val = ( List ) entry.getValue();// 具体货架上的音乐商品
                int cou = 0;
                if (val != null)
                {
                    cou = val.size();
                }
                String[] typeAndcateNames = typeAndcateName.split("[|]");
                if (typeAndcateNames.length == 2)
                {
                    String cateName = typeAndcateNames[1];
                    String listId = typeAndcateNames[0]; //榜单ID ，音乐基地提供的可选字段

//                    if (typeMapCate.get(type) != null)
//                    {
//                        String parCategoryId = ( String ) typeMapCate.get(type);

                        String subCategoryID = BMusicDAO.getInstance()
                                                        .getCategoryIDByNameAndParCid(categoryID,
                                                                                      cateName,listId,
                                                                                      cou);
                        // 下架分类货架二级货架下的所有商品
                        BMusicDAO.getInstance()
                                 .delBMusicRefByCateID(subCategoryID);
                        int count = 0;
                        if (val != null && val.size() > 0)
                        {
                            for (int j = 0; j < val.size(); j++)
                            {
                                List ref = ( List ) val.get(j);
                                if (ref != null && ref.size() == 4)
                                {
                                    // String temp = (String)ref.get(0);
                                    ref.remove(3);// add by dongke 20120203
                                    ref.add(subCategoryID);
                                    String goodsid = ( String ) ref.get(0)
                                                     + subCategoryID;
                                    String temp = ( String ) existsRefMusic.get(goodsid);
                                    if (temp == null)
                                    {// 没有重复上架
                                        this.insertDBBaseMusicNewRef(ref);
                                        existsRefMusic.put(goodsid,
                                                           ( String ) ref.get(0));
                                        count++;
                                    }
                                    else
                                    {
                                        // 重复上架
                                        logger.error("重复上架,上架音乐ID:"
                                                     + ref.get(0) + ",上架货架为："
                                                     + subCategoryID);
                                        charRefmailInfo[2] = new Integer(charRefmailInfo[2].intValue() + 1);// 重复上架行数计数器
                                    }

                                }

                            }

                        }

                        // 更新货架商品数量
                        BMusicDAO.getInstance()
                                 .updateCategorySum(count, subCategoryID);
                    }
                    else
                    {
                        logger.error("parCategoryId  type is null =" + categoryID);
                    }
//                }
//                else
//                {
//                    logger.error("typeAndcateName is wrong=" + typeAndcateName);
//                }
            }

        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        insertRefTaskRunner.waitToFinished();// 等待更新数据库完毕。
        insertRefTaskRunner.end();// 结束运行器

    }

    /**
     * 下架 新 基地音乐全量过期音乐商品
     * 
     * @throws DAOException
     * 
     */
    public void delInvalNewBMusicRef(Integer[] delRefmailInfo)
                    throws BOException
    {
        try
        {
            int delcount = BMusicDAO.getInstance().delInvalNewBMusicRef();
            if (delRefmailInfo[0] == null)
            {
                delRefmailInfo[0] = new Integer(0);
            }
            delRefmailInfo[0] = new Integer(delRefmailInfo[0].intValue()
                                            + delcount);
        }
        catch (DAOException e)
        {
            logger.error("数据库操作失败" + e);
            throw new BOException("数据库操作失败", e);

        }
    }

    /**
     * 更新 新 音乐货架商品数量
     * 
     * @throws DAOException
     * 
     */
    public void updateAllNewCategoryRefSum(Integer[] delRefmailInfo)
                    throws BOException
    {
        try
        {
            int delcount = BMusicDAO.getInstance().updateAllNewCategoryRefSum();
            if (delRefmailInfo[0] == null)
            {
                delRefmailInfo[0] = new Integer(0);
            }
            delRefmailInfo[0] = new Integer(delRefmailInfo[0].intValue()
                                            + delcount);
        }
        catch (DAOException e)
        {
            logger.error("数据库操作失败" + e);
            throw new BOException("数据库操作失败", e);

        }
    }

    /**
     * 下架基地音乐全量过期音乐商品
     * 
     * @throws DAOException
     * 
     */
    public void delInvalBMusicRef(Integer[] delRefmailInfo) throws BOException
    {
        try
        {
            int delcount = BMusicDAO.getInstance().delInvalBMusicRef();
            if (delRefmailInfo[0] == null)
            {
                delRefmailInfo[0] = new Integer(0);
            }
            delRefmailInfo[0] = new Integer(delRefmailInfo[0].intValue()
                                            + delcount);
        }
        catch (DAOException e)
        {
            logger.error("数据库操作失败" + e);
            throw new BOException("数据库操作失败", e);

        }
    }

    // 100002166
    /**
     * 下架基地音乐全量过期音乐商品
     * 
     * @throws DAOException
     * 
     */
    public void updateAllCategoryRefSum(Integer[] updatecaInfo)
                    throws BOException
    {
        try
        {
            int delcount = BMusicDAO.getInstance().updateAllCategoryRefSum();
            if (updatecaInfo[0] == null)
            {
                updatecaInfo[0] = new Integer(0);
            }
            updatecaInfo[0] = new Integer(updatecaInfo[0].intValue() + delcount);
        }
        catch (DAOException e)
        {
            logger.error("数据库操作失败" + e);
            throw new BOException("数据库操作失败", e);

        }
    }

    /**
     * 获取VO
     * 
     * @param record
     * @return
     */

    private BMusicVO getBaseMusicVOBylist(List record)
    {

        BMusicVO bv = null;
        if (record != null && record.size() == 5)
        {
            bv = new BMusicVO();
            String musicId = ( String ) record.get(0);
            bv.setMusicId(musicId);
            bv.setSongname(( String ) record.get(1));
            bv.setSinger(( String ) record.get(2));
            bv.setValidity(( String ) record.get(3));
            bv.setChangetype(( String ) record.get(4));
        }

        return bv;
    }

    /**
     * 获取VO
     * 
     * @param record
     * @return
     */

    private BMusicVO getBaseNewMusicVOBylist(List record)
    {

        BMusicVO bv = null;
        if (record != null && record.size() == 6)
        {
            bv = new BMusicVO();
            String musicId = ( String ) record.get(0);
            bv.setMusicId(musicId);
            bv.setSongname(( String ) record.get(1));
            bv.setSinger(( String ) record.get(2));
            bv.setValidity(( String ) record.get(3));
            bv.setProductMask(( String ) record.get(4));
            bv.setChangetype(( String ) record.get(5));
        }
        if (record != null && record.size() == 5)
        {
            bv = new BMusicVO();
            String musicId = ( String ) record.get(0);
            bv.setMusicId(musicId);
            bv.setSongname(( String ) record.get(1));
            bv.setSinger(( String ) record.get(2));
            bv.setValidity(( String ) record.get(3));
            bv.setProductMask("");
            bv.setChangetype(( String ) record.get(4));
        } 
        if (record != null && (record.size() == 7 || record.size() == 8|| record.size() == 9 ||record.size() == 11))
        {
            bv = new BMusicVO();
            String musicId = ( String ) record.get(0);
            bv.setMusicId(musicId);
            bv.setSongname(( String ) record.get(1));
         
            bv.setValidity(( String ) record.get(3));
            bv.setProductMask(( String ) record.get(4));
            bv.setSingersId(( String ) record.get(2));
            bv.setPubtime(( String ) record.get(5));
            
            bv.setChangetype(( String ) record.get(6));
            bv.setDolbytype(( String ) record.get(7));
            // 2014.02.12 by wml +
            if(record.size() == 9)
            {
            	bv.setMusicImage((String) record.get(8));
            }
            if(record.size() == 11)
            {
            	bv.setMusicImage((String) record.get(8));
            	bv.setFormat320kbps((String) record.get(9));
            	bv.setLosslessmusic((String) record.get(10));
            }
        }

        return bv;
    }
    /**
     * 获取VO
     * 
     * @param record
     * @return
     */

    private BNewMusicSingerVO getBaseNewMusicSingerVOBylist(List record)
    {

    	BNewMusicSingerVO bv = null;
     
        if (record != null && record.size() == 7)
        {
        	bv = new BNewMusicSingerVO();
            String singerId = ( String ) record.get(0);
            bv.setSingerId(singerId);
            
            String singerUpcase = ( String ) record.get(1);
            if(singerUpcase.length()>1){
            	singerUpcase =singerUpcase.substring(0,1);
            }
            bv.setSingerupcase(singerUpcase.toUpperCase());
            
            bv.setSingername(( String ) record.get(2));
            bv.setSingerdesc(( String ) record.get(3));
            bv.setSingerpicurl(( String ) record.get(4));
            bv.setSingertype(( String ) record.get(5));
            bv.setChangetype(( String ) record.get(6));
        }

        return bv;
    }

    
    /**
     * 
     *@desc 专辑
     *@author dongke
     *Sep 26, 2012
     * @param record
     * @return
     */

    private BNewMusicAlbumVO getBaseNewMusicAlbumVOBylist(List record)
    {

    	BNewMusicAlbumVO bv = null;
     
        if (record != null && record.size() == 8)
        {
        	bv = new BNewMusicAlbumVO();
            String albumId = ( String ) record.get(0);
            bv.setAlbumId(albumId);
            bv.setSingersid(( String ) record.get(1));
            bv.setAlbumupcase( ( String ) record.get(2));
            bv.setAlbumName(( String ) record.get(3));
            bv.setAlbumDesc(( String ) record.get(4));
            bv.setAlbumpicurl(( String ) record.get(5));
            bv.setPubtime(( String ) record.get(6));
            
            bv.setChangetype(( String ) record.get(7));
        }

        return bv;
    }

    /**
     * 获取将待上架商品保存到Hashmap
     * 
     * @param record
     * @return
     */

    private HashMap getBaseMusicCharBylist(HashMap hm, List record)
    {

        if (record != null && record.size() == 5)
        {
            // hm.pu
            String cateName = ( String ) record.get(0);
            String type = ( String ) record.get(4);
            List cateRefList = ( List ) hm.get(type + "|" + cateName);

            if (cateRefList == null)
            {// 不存在二级分类货架名称
                cateRefList = new ArrayList();
                record.remove(0);
                // String temp = (String)record.get(0);
                cateRefList.add(record);
                hm.put(type + "|" + cateName, cateRefList);
            }
            else
            {// 存在二级分类货架名称
                record.remove(0);
                cateRefList.add(record);
                hm.put(type + "|" + cateName, cateRefList);
            }
        }

        return hm;
    }

    /**
     * 更新基地音乐信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐信息的vo
     * @return int 成功更新基地音乐内容的个数
     */
    private void addupdateDBBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBBaseMusic() id=" + vo.getMusicId());
        }
        BaseMusicDBOpration cm = new BaseMusicDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm,
                                               "addUpdateBMusicVO",
                                               null,
                                               null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 更新基地音乐信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐信息的vo
     * @return int 成功更新基地音乐内容的个数
     */
    private void updateDBBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBBaseMusic() id=" + vo.getMusicId());
        }
        BaseMusicDBOpration cm = new BaseMusicDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "UpdateBMusicVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 插入基地音乐信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐信息的vo
     * @return int 成功更新基地音乐内容的个数
     */
    private void insertDBBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("InsertDBBaseMusic() id=" + vo.getMusicId());
        }
        BaseMusicDBOpration cm = new BaseMusicDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "InsertBMusicVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 删除基地音乐信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐信息的vo
     * @return int 成功更新基地音乐内容的个数
     */
    private void delDBBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("DelDBBaseMusic() id=" + vo.getMusicId());
        }
        BaseMusicDBOpration cm = new BaseMusicDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "DelBMusicVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 更新新 基地音乐信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐信息的vo
     * @return int 成功更新基地音乐内容的个数
     */
    private void addupdateDBNewBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBBaseMusic() id=" + vo.getMusicId());
        }
        NewBaseMusicDBOpration cm = new NewBaseMusicDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm,
                                               "addUpdateBMusicVO",
                                               null,
                                               null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 更新新 基地音乐信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐信息的vo
     * @return int 成功更新基地音乐内容的个数
     */
    private void updateDBNewBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBNewBaseMusic() id=" + vo.getMusicId());
        }
        NewBaseMusicDBOpration cm = new NewBaseMusicDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "UpdateBMusicVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 插入新 基地音乐信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐信息的vo
     * @return int 成功更新基地音乐内容的个数
     */
    private void insertDBNewBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("InsertDBNewBaseMusic() id=" + vo.getMusicId());
        }
        NewBaseMusicDBOpration cm = new NewBaseMusicDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "InsertBMusicVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 删除新 基地音乐信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐信息的vo
     * @return int 成功更新基地音乐内容的个数
     */
    private void delDBNewBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("DelDBNewBaseMusic() id=" + vo.getMusicId());
        }
        NewBaseMusicDBOpration cm = new NewBaseMusicDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "DelBMusicVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }
    
    /**
     * 删除新 基地音乐信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐信息的vo
     * @return int 成功更新基地音乐内容的个数
     */
    private void delDBFullBaseMusic(String MusicId)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("DelDBNewBaseMusic() id=" +MusicId);
        }
        NewBaseMusicDBOpration cm = new NewBaseMusicDBOpration(new BMusicVO());
        // 构造异步任务
//        ReflectedTask task = new ReflectedTask(cm, "DelFullMusicVO", null, null);
//        // 将任务加到运行器中
//        updateTaskRunner.addTask(task);
         cm.DelFullMusicVO(MusicId);
    }
    
    /**
     * 更新新 基地音乐歌手信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐歌手信息的vo
     * @return int 成功更新基地音乐歌手内容的个数
     */
    private void updateDBNewBaseMusicSinger(BNewMusicSingerVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBNewBaseMusicSinger() id=" + vo.getSingerId());
        }
        NewBaseMusicSingerDBOpration cm = new NewBaseMusicSingerDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "addUpdateBMusicSingerVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 插入新 基地音乐歌手信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐歌手信息的vo
     * @return int 成功更新基地音乐歌手内容的个数
     */
    private void insertDBNewBaseMusicSinger(BNewMusicSingerVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("insertDBNewBaseMusicSinger() id=" + vo.getSingerId());
        }
        NewBaseMusicSingerDBOpration cm = new NewBaseMusicSingerDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "InsertBMusicSingerVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 删除新 基地音乐信息歌手,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐信息歌手的vo
     * @return int 成功更新基地音乐歌手内容的个数
     */
    private void delDBNewBaseMusicSinger(BNewMusicSingerVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delDBNewBaseMusicSinger() id=" + vo.getSingerId());
        }
        NewBaseMusicSingerDBOpration cm = new NewBaseMusicSingerDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "DelBMusicSingerVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    
    /**
     * 更新新 基地音乐专辑信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐专辑信息的vo
     * @return int 成功更新基地音乐专辑内容的个数
     */
    private void updateDBNewBaseMusicAlbum(BNewMusicAlbumVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBNewBaseMusicAlbum() id=" + vo.getAlbumId());
        }
        BaseMusicAlbumDBOpration cm = new BaseMusicAlbumDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "addUpdateBMusicAlbumVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 插入新 基地音乐专辑信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐专辑信息的vo
     * @return int 成功更新基地音乐专辑内容的个数
     */
    private void insertDBNewBaseMusicAlbum(BNewMusicAlbumVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("insertDBNewBaseMusicSinger() id=" + vo.getAlbumId());
        }
        BaseMusicAlbumDBOpration cm = new BaseMusicAlbumDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "InsertBMusicAlbumVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 删除新 基地音乐信息专辑,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐信息专辑的vo
     * @return int 成功更新基地音乐专辑内容的个数
     */
    private void delDBNewBaseMusicAlbum(BNewMusicAlbumVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delDBNewBaseMusicSinger() id=" + vo.getAlbumId());
        }
        BaseMusicAlbumDBOpration cm = new BaseMusicAlbumDBOpration(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "DelBMusicAlbumVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    
    /**
     * 插入基地音乐信息,调用多线程完成异步更新。
     * 
     * @param vo 基地音乐信息的vo
     * @return int 成功更新基地音乐内容的个数
     */
    private void insertDBBaseMusicNewRef(List dr)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("InsertDBBaseMusicNewRef() id=" + dr.get(0));
        }
        BaseMusicReferenceDBOpration cm = new BaseMusicReferenceDBOpration(dr);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm,
                                               "InsertNewBMusicReference",
                                               null,
                                               null);
        // 将任务加到运行器中
        insertRefTaskRunner.addTask(task);
    }

    /**
     * 
     * @param lineText
     * @param sep
     * @return
     */
    private List readDataRecord(String lineText, String sep)
    {
        if (sep.startsWith("0x"))
        {
            // 0x开头的，表示是16进制的，需要转换
            String s = sep.substring(2, sep.length());
            int i = Integer.parseInt(s, 16);
            char c = ( char ) i;
            sep = String.valueOf(c);
        }
        String[] dataArray = lineText.split("[" + sep + "]",-1);
        List record = new ArrayList();
        for (int i = 0; i < dataArray.length; i++)
        {
           // if (dataArray[i] != null && dataArray[i].length() > 0)
         //   {
                record.add(dataArray[i]);
          //  }
        }
        return record;
    }

    /**
     * 导入铃音盒
     * @param existsMusic
     * @param existsRefMusic
     * @param charRefmailInfo
     * @param fileName
     * @param checkFailureRowByUp
     * @param errorFailureRowByUp
     */
	public void importToneBox(HashMap existsTonebox,
            Integer[] toneboxMailInfo,
            String filenameList[],
            StringBuffer checkFailureRowByNew,StringBuffer errorFailureRowByNew) throws BOException
	{
		
		String fileEncoding = BaseMusicConfig.get("fileEncoding");
        updateTaskRunner = new TaskRunner(BaseMusicConfig.getBaseMusicUpdateTaskNum(),
                                          BaseMusicConfig.getBaseMusicMaxReceivedNum());
        String sep = BaseMusicConfig.get("BNewMusicListSep");
        if (filenameList.length == 0)
        {
            throw new BOException("没有找到本次任务铃音盒元数据的文件",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // 用于文件成功处理的行数
        int lineNumeber = 0;
        
        HashMap<String, Integer> existsToneboxIds=new HashMap<String, Integer>();
        if(existsTonebox!=null&&!existsTonebox.isEmpty()&&existsTonebox.size()>0)
        {
        	for(Object toneboxId:existsTonebox.keySet())
            {
            	existsToneboxIds.put((String)toneboxId,0);
            }
        }
        
        try
        {
            if (toneboxMailInfo[0] == null)
            {
            	toneboxMailInfo[0] = new Integer(0);
            }
            if (toneboxMailInfo[1] == null)
            {
            	toneboxMailInfo[1] = new Integer(0);
            }
            if (toneboxMailInfo[2] == null)
            {
            	toneboxMailInfo[2] = new Integer(0);
            }
            if (toneboxMailInfo[3] == null)
            {
            	toneboxMailInfo[3] = new Integer(0);
            }
            if (toneboxMailInfo[4] == null)
            {
            	toneboxMailInfo[4] = new Integer(0);
            }
            if (toneboxMailInfo[5] == null)
            {
            	toneboxMailInfo[5] = new Integer(0);
            }
            for (int i = 0; i < filenameList.length; i++)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("开始处理文件：" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// 记录文件的行数。
                    toneboxMailInfo[0] = new Integer(toneboxMailInfo[0].intValue() + 1);// 总处理行数计数器
                    if (lineNumeber == 1)// 删除第一行bom字符
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("开始处理第" + lineNumeber + "行数据。");
                    }
                    if ("".equals(lineText.trim()))// 对于空行的记录不处理。
                    {
						logger.debug("该行是空行，不处理。lineNumeber=" + lineNumeber);

						if (toneboxMailInfo[4] < 201) {
							checkFailureRowByNew.append("<br>").append("  第")
									.append(lineNumeber).append("行为空");
							
						}
						if (toneboxMailInfo[4] == 201) {
							checkFailureRowByNew.append("<br>").append(
									" .......");
						}
						toneboxMailInfo[4] = new Integer(
								toneboxMailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
						continue;
					}
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        int checkResult = ToneBoxChecker.getInstance()
                                                       .checkDateRecord(dr);
                        logger.debug("检查结果=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("第" + lineNumeber + "行数据检查失败，忽略该数据。");
                            
                        	if (toneboxMailInfo[4] < 201) {
    							checkFailureRowByNew.append("<br>").append("  第")
    									.append(lineNumeber).append("行数据检查失败");
    							
    						}
    						if (toneboxMailInfo[4] == 201) {
    							checkFailureRowByNew.append("<br>").append(
    									" .......");
    						}
                            
                            toneboxMailInfo[4] = new Integer(toneboxMailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                            continue;
                        }
                    }
                    try
                    {
                    	ToneBoxVO bv = this.getToneBoxVOByList(dr);

                        if (bv != null)
                        {
//                            if ("1".equals(bv.getOperType())||"2".equals(bv.getOperType()))
//                            {
                                // 新增
                                String existSingerName = ( String ) existsTonebox.get(bv.getId());
                                if (existSingerName == null)
                                {

                                    // 新增
                                    this.insertDBToneBox(bv);
                                    existsTonebox.put(bv.getId(),bv.getName());
                                    toneboxMailInfo[1] = new Integer(toneboxMailInfo[1].intValue() + 1);// 新增行数计数器
                                }
                                else
                                {
                                    // 修改
                                    this.updateDBToneBox(bv);
                                    existsTonebox.put(bv.getId(),bv.getName());
                                    toneboxMailInfo[2] = new Integer(toneboxMailInfo[2].intValue() + 1);// 修改行数计数器
                                    existsToneboxIds.put(bv.getId(), 1);
                                }

//                            }
//                            else if ("3".equals(bv.getOperType()))
//                            {
//                                // 删除
//                                this.delDBToneBox(bv);
//                                existsTonebox.remove(bv.getId());
//                                toneboxMailInfo[3] = new Integer(toneboxMailInfo[3].intValue() + 1);// 删除行数计数器
//                            }
                            // 分拣数据
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("第" + lineNumeber + "行数据处理失败。", e);
                        toneboxMailInfo[5] = new Integer(toneboxMailInfo[5].intValue() + 1);// 处理失败行数计数器
                        errorFailureRowByNew.append(lineNumeber).append(", ");
                        continue;
                    }

                }
            }
            
            //删除存量数据
            logger.debug("删除铃音盒开始" );
            if(existsToneboxIds!=null&&!existsToneboxIds.isEmpty()&&existsToneboxIds.size()>0)
            {
	            for(String toneboxId:existsToneboxIds.keySet())
	            {
	            	if(existsToneboxIds.get(toneboxId)==0)
	            	{
		              // 删除
	            	  logger.debug("删除铃音盒,toneboxId:" + toneboxId);
		              ToneBoxVO bv = new ToneBoxVO();
		              bv.setId(toneboxId);
		              this.delDBToneBox(bv);
		              existsTonebox.remove(toneboxId);
		              toneboxMailInfo[3] = new Integer(toneboxMailInfo[3].intValue() + 1);// 删除行数计数器
	            	}
	            }
            }
            logger.debug("删除铃音盒结束" );

        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        updateTaskRunner.waitToFinished();// 等待更新数据库完毕。
        updateTaskRunner.end();// 结束运行器
		
	}
	
	
	/**
     * 获取VO
     * 
     * @param record
     * @return
     */

    private ToneBoxVO getToneBoxVOByList(List record)
    {

    	ToneBoxVO vo = null;
        if (record != null && record.size() == 6)
        {
        	vo = new ToneBoxVO();
        	vo.setId(( String ) record.get(0));
        	vo.setName(( String ) record.get(1));
        	vo.setDescription(( String ) record.get(2));
        	vo.setCharge(( String ) record.get(3));
        	vo.setValid(( String ) record.get(4));
//        	vo.setOperType(( String ) record.get(5));

        }

        return vo;
    }
    
    /**
     * 更新铃音盒信息,调用多线程完成异步更新。
     * 
     * @param vo 铃音盒信息的vo
     */
    private void updateDBToneBox(ToneBoxVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBToneBox() id=" + vo.getId());
        }
        ToneBoxDAO cm = new ToneBoxDAO(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "updateToneBoxVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 新增铃音盒信息,调用多线程完成异步更新。
     * 
     * @param vo 铃音盒信息的vo
     */
    private void insertDBToneBox(ToneBoxVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("insertDBToneBox() id=" + vo.getId());
        }
        ToneBoxDAO cm = new ToneBoxDAO(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "insertToneBoxVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 删除铃音盒信息,调用多线程完成异步更新。
     * 
     * @param vo 铃音盒信息的vo
     */
    private void delDBToneBox(ToneBoxVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delDBToneBox() id=" + vo.getId());
        }
        ToneBoxDAO cm = new ToneBoxDAO(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "delToneBoxVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }
    
    
    
    /**
     * 导入铃音盒歌曲
     * @param existsMusic
     * @param existsRefMusic
     * @param charRefmailInfo
     * @param fileName
     * @param checkFailureRowByUp
     * @param errorFailureRowByUp
     */
	public void importToneBoxSong(HashMap existsTonebox,
            Integer[] toneboxMailInfo,
            String filenameList[],
            StringBuffer checkFailureRowByNew,StringBuffer errorFailureRowByNew) throws BOException
	{
		
		String fileEncoding = BaseMusicConfig.get("fileEncoding");
        updateTaskRunner = new TaskRunner(BaseMusicConfig.getBaseMusicUpdateTaskNum(),
                                          BaseMusicConfig.getBaseMusicMaxReceivedNum());
        String sep = BaseMusicConfig.get("BNewMusicListSep");
        if (filenameList.length == 0)
        {
            throw new BOException("没有找到本次任务铃音盒歌曲元数据的文件",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // 用于文件成功处理的行数
        int lineNumeber = 0;

        HashMap<String, Integer> existsToneboxSongIds=new HashMap<String, Integer>();
        if(existsTonebox!=null&&!existsTonebox.isEmpty()&&existsTonebox.size()>0)
        {
        	for(Object toneboxId:existsTonebox.keySet())
            {
        		existsToneboxSongIds.put((String)toneboxId,0);
            }
        }
        
        
        try
        {
            if (toneboxMailInfo[0] == null)
            {
            	toneboxMailInfo[0] = new Integer(0);
            }
            if (toneboxMailInfo[1] == null)
            {
            	toneboxMailInfo[1] = new Integer(0);
            }
            if (toneboxMailInfo[2] == null)
            {
            	toneboxMailInfo[2] = new Integer(0);
            }
            if (toneboxMailInfo[3] == null)
            {
            	toneboxMailInfo[3] = new Integer(0);
            }
            if (toneboxMailInfo[4] == null)
            {
            	toneboxMailInfo[4] = new Integer(0);
            }
            if (toneboxMailInfo[5] == null)
            {
            	toneboxMailInfo[5] = new Integer(0);
            }
            for (int i = 0; i < filenameList.length; i++)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("开始处理文件：" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// 记录文件的行数。
                    toneboxMailInfo[0] = new Integer(toneboxMailInfo[0].intValue() + 1);// 总处理行数计数器
                    if (lineNumeber == 1)// 删除第一行bom字符
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("开始处理第" + lineNumeber + "行数据。");
                    }
                    if ("".equals(lineText.trim()))// 对于空行的记录不处理。
                    {
						logger.debug("该行是空行，不处理。lineNumeber=" + lineNumeber);

						if (toneboxMailInfo[4] < 201) {
							checkFailureRowByNew.append("<br>").append("  第")
									.append(lineNumeber).append("行为空");
							
						}
						if (toneboxMailInfo[4] == 201) {
							checkFailureRowByNew.append("<br>").append(
									" .......");
						}
						toneboxMailInfo[4] = new Integer(
								toneboxMailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
						continue;
					}
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        int checkResult = ToneBoxSongChecker.getInstance()
                                                       .checkDateRecord(dr);
                        logger.debug("检查结果=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("第" + lineNumeber + "行数据检查失败，忽略该数据。");
                            
                        	if (toneboxMailInfo[4] < 201) {
    							checkFailureRowByNew.append("<br>").append("  第")
    									.append(lineNumeber).append("行数据检查失败");
    							
    						}
    						if (toneboxMailInfo[4] == 201) {
    							checkFailureRowByNew.append("<br>").append(
    									" .......");
    						}
                            
                            toneboxMailInfo[4] = new Integer(toneboxMailInfo[4].intValue() + 1);// 数据检查不合法行数计数器
                            continue;
                        }
                    }
                    try
                    {
                    	ToneBoxSongVO bv = this.getToneBoxSongVOByList(dr);

                        if (bv != null)
                        {
//                            if ("1".equals(bv.getOperType())||"2".equals(bv.getOperType()))
//                            {
                                // 新增
                                String existSingerName = ( String ) existsTonebox.get(bv.getId()+"_"+bv.getBoxId());
                                if (existSingerName == null)
                                {

                                    // 新增
                                    this.insertDBToneBoxSong(bv);
                                    existsTonebox.put(bv.getId()+"_"+bv.getBoxId(),bv.getId()+"_"+bv.getBoxId());
                                    toneboxMailInfo[1] = new Integer(toneboxMailInfo[1].intValue() + 1);// 新增行数计数器
                                }
                                else
                                {
                                    // 修改
                                    this.updateDBToneBoxSong(bv);
                                    existsTonebox.put(bv.getId()+"_"+bv.getBoxId(),bv.getId()+"_"+bv.getBoxId());
                                    toneboxMailInfo[2] = new Integer(toneboxMailInfo[2].intValue() + 1);// 修改行数计数器
                                    existsToneboxSongIds.put(bv.getId()+"_"+bv.getBoxId(), 1);
                                }

//                            }
//                            else if ("3".equals(bv.getOperType()))
//                            {
//                                // 删除
//                                this.delDBToneBoxSong(bv);
//                                existsTonebox.remove(bv.getId()+"_"+bv.getBoxId());
//                                toneboxMailInfo[3] = new Integer(toneboxMailInfo[3].intValue() + 1);// 删除行数计数器
//                            }
                            // 分拣数据
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("第" + lineNumeber + "行数据处理失败。", e);
                        toneboxMailInfo[5] = new Integer(toneboxMailInfo[5].intValue() + 1);// 处理失败行数计数器
                        errorFailureRowByNew.append(lineNumeber).append(", ");
                        continue;
                    }

                }
            }
            logger.debug("删除铃音盒歌曲开始" );
            //删除存量数据
            if(existsToneboxSongIds!=null&&!existsToneboxSongIds.isEmpty()&&existsToneboxSongIds.size()>0)
            {
	            for(String toneboxId:existsToneboxSongIds.keySet())
	            {
	            	if(existsToneboxSongIds.get(toneboxId)==0)
	            	{
	            		// 删除
	  	              ToneBoxSongVO bv = new ToneBoxSongVO();
	  	              String ids[]=toneboxId.split("_");
	  	              logger.debug("删除铃音盒歌曲,歌曲ID:" + ids[0]+",铃音盒ID:"+ids[1]);
	  	              bv.setId(ids[0]);
	  	              bv.setBoxId(ids[1]);
	  	              this.delDBToneBoxSong(bv);
	  	              existsTonebox.remove(toneboxId);
	  	              toneboxMailInfo[3] = new Integer(toneboxMailInfo[3].intValue() + 1);// 删除行数计数器
	            	}
	            
	            }
            }
            logger.debug("删除铃音盒歌曲结束" );

        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        updateTaskRunner.waitToFinished();// 等待更新数据库完毕。
        updateTaskRunner.end();// 结束运行器
		
	}
	
	
	
	/**
     * 获取VO
     * 
     * @param record
     * @return
     */

    private ToneBoxSongVO getToneBoxSongVOByList(List record)
    {

    	ToneBoxSongVO vo = null;
        if (record != null && record.size() == 4)
        {
        	vo = new ToneBoxSongVO();
        	vo.setBoxId(( String ) record.get(0));
        	vo.setId(( String ) record.get(1));
        	vo.setSortId(( String ) record.get(2));
        	//vo.setOperType(( String ) record.get(3));
        }

        return vo;
    }
    
    /**
     * 更新铃音盒信息,调用多线程完成异步更新。
     * 
     * @param vo 铃音盒信息的vo
     */
    private void updateDBToneBoxSong(ToneBoxSongVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBToneBoxSong() id=" + vo.getId());
        }
        ToneBoxSongDAO cm = new ToneBoxSongDAO(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "updateToneBoxSongVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 新增铃音盒信息,调用多线程完成异步更新。
     * 
     * @param vo 铃音盒信息的vo
     */
    private void insertDBToneBoxSong(ToneBoxSongVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("insertDBToneBoxSong() id=" + vo.getId());
        }
        ToneBoxSongDAO cm = new ToneBoxSongDAO(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "insertToneBoxSongVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    /**
     * 删除铃音盒信息,调用多线程完成异步更新。
     * 
     * @param vo 铃音盒信息的vo
     */
    private void delDBToneBoxSong(ToneBoxSongVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delDBToneBox() id=" + vo.getId()+",boxId="+vo.getBoxId());
        }
        ToneBoxSongDAO cm = new ToneBoxSongDAO(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cm, "delToneBoxSongVO", null, null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

    

    /**
     * 
     * @param lineText
     * @param sep
     * @return
     */
    private List readDataRecordChar(String lineText, String sep)
    {
        if (sep.startsWith("0x"))
        {
            // 0x开头的，表示是16进制的，需要转换
            String s = sep.substring(2, sep.length());
            int i = Integer.parseInt(s, 16);
            char c = ( char ) i;
            sep = String.valueOf(c);
        }
        String[] dataArray = lineText.split("[" + sep + "]",-1);
        List record = new ArrayList();
        for (int i = 0; i < dataArray.length; i++)
        {
        	if(dataArray[i] == null ){
        		dataArray[i] = "null";
        	}
           // if (dataArray[i] != null && dataArray[i].length() > 0)
         //   {
                record.add(dataArray[i]);
          //  }
        }
        return record;
    }

    // public static void main(String[] args) throws Exception {
    // Date nowdate = new Date();
    // String datastr = PublicUtil.getDateString(nowdate,"yyyy-mm-dd");
    // String datastr1 = PublicUtil.getDateString(nowdate,"yyyy-MM-DD");
    // String datastr2 = PublicUtil.getDateString(nowdate,"yyyy-MM-dd");
    // System.out.println(datastr);
    // System.out.println(datastr1);
    // System.out.println(datastr2);
    //	
    // }
}
