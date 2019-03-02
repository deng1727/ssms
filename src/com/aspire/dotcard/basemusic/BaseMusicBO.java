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
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseMusicLoadTask.class);

    private BaseMusicBO()
    {

    }

    /**
     * singletonģʽ��ʵ��
     */
    private static BaseMusicBO instance = new BaseMusicBO();
    private TaskRunner updateTaskRunner;
    private TaskRunner insertRefTaskRunner;

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static final BaseMusicBO getInstance()
    {

        return instance;
    }

    /**
     * ��������
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
            throw new BOException("û���ҵ����������������Ԫ���ݵ��ļ�",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // �����ļ��ɹ����������
        int lineNumeber = 0;

        // HashMap existsMusic = null;
        // try
        // { //��ȡ���е��Ѿ����ڵ�����
        // existsMusic = BMusicDAO.getInstance().getAllexistMusicID();
        // } catch (DAOException e1)
        // {
        // logger.error("�����ݿ��л�ȡ���л�������IDʱ�������ݿ��쳣��", e1);
        // throw new BOException("�����ݿ��л�ȡ���л�������IDʱ�������ݿ��쳣",e1);
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
                    logger.debug("��ʼ�����ļ���" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// ��¼�ļ���������
                    musicmailInfo[0] = new Integer(musicmailInfo[0].intValue() + 1);// �ܴ�������������
                    if (lineNumeber == 1)// ɾ����һ��bom�ַ�
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("��ʼ�����" + lineNumeber + "�����ݡ�");
                    }
                    if ("".equals(lineText.trim()))// ���ڿ��еļ�¼������
                    {
                        logger.debug("�����ǿ��У�������lineNumeber=" + lineNumeber);
                        checkFailureRow.append("<br>").append("  ��").append(lineNumeber).append("��Ϊ��");
                        musicmailInfo[4] = new Integer(musicmailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
                        continue;
                    }
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        int checkResult = BMusicChecker.getInstance()
                                                       .checkDateRecord(dr);
                        logger.debug("�����=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("��" + lineNumeber + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
                            checkFailureRow.append("<br>").append("  ��").append(lineNumeber).append("�����ݼ��ʧ��");
                            musicmailInfo[4] = new Integer(musicmailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
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
                                // ����
                                Integer existid = ( Integer ) existsMusic.get(bv.getMusicId());
                                if (existid == null)
                                {

                                    // ����
                                    bv.setDelFlag(0);
                                    this.insertDBBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(0));
                                    musicmailInfo[1] = new Integer(musicmailInfo[1].intValue() + 1);// ��������������
                                }
                                else
                                {
                                    // �޸�
                                    bv.setDelFlag(0);
                                    this.addupdateDBBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(0));
                                    musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// �޸�����������
                                }

                            }
                            else if (bv.getChangetype().equals("2"))
                            {
                                // �޸�
                                bv.setDelFlag(0);
                                // this.updateDBBaseMusic(bv);
                                this.addupdateDBBaseMusic(bv);
                                existsMusic.put(bv.getMusicId(), new Integer(0));
                                musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// �޸�����������
                            }
                            else if (bv.getChangetype().equals("3"))
                            {
                                // ɾ��
                                bv.setDelFlag(1);// ���ɾ�����λ
                                this.delDBBaseMusic(bv);
                                // existsMusic.remove(bv.getMusicId());
                                existsMusic.put(bv.getMusicId(), new Integer(1));
                                musicmailInfo[3] = new Integer(musicmailInfo[3].intValue() + 1);// ɾ������������
                            }
                            // �ּ�����
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("��" + lineNumeber + "�����ݴ���ʧ�ܡ�", e);
                        musicmailInfo[5] = new Integer(musicmailInfo[5].intValue() + 1);// ����ʧ������������
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
        updateTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
        updateTaskRunner.end();// ����������

    }
    /**
     * ��������--�����ֻ������ݽ���WAP
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
            throw new BOException("û���ҵ������������������Ԫ���ݵ��ļ�",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // �����ļ��ɹ����������
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
                    logger.debug("��ʼ�����ļ���" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// ��¼�ļ���������
                    musicmailInfo[0] = new Integer(musicmailInfo[0].intValue() + 1);// �ܴ�������������
                    if (lineNumeber == 1)// ɾ����һ��bom�ַ�
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("��ʼ�����" + lineNumeber + "�����ݡ�");
                    }
                    if ("".equals(lineText.trim()))// ���ڿ��еļ�¼������
                    {
                        logger.debug("�����ǿ��У�������lineNumeber=" + lineNumeber);
                      //  checkFailureRowByNew.append("<br>").append("  ��").append(lineNumeber).append("��Ϊ��");
                        if (musicmailInfo[4] < 201) {
							checkFailureRowByNew.append("<br>").append("  ��")
									.append(lineNumeber).append("��Ϊ��");
							
						}
						if (musicmailInfo[4] == 201) {
							checkFailureRowByNew.append("<br>").append(
									" .......");
						}
                        musicmailInfo[4] = new Integer(musicmailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
                        continue;
                    }
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        // int checkResult =
                        // BMusicChecker.getInstance().checkDateRecord(dr);
                        int checkResult = BMusicChecker.getInstance()
                                                       .checkNewDateRecord(dr);
                        logger.debug("�����=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("��" + lineNumeber + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
                            //checkFailureRowByNew.append("<br>").append("  ��").append(lineNumeber).append("�����ݼ��ʧ��");
                            
                            if (musicmailInfo[4] < 201) {
    							checkFailureRowByNew.append("<br>").append("  ��")
    									.append(lineNumeber).append("�����ݼ��ʧ��");
    							
    						}
    						if (musicmailInfo[4] == 201) {
    							checkFailureRowByNew.append("<br>").append(
    									" .......");
    						}
                            
                            musicmailInfo[4] = new Integer(musicmailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
                            continue;
                        }
                    }
                    try
                    {
                        BMusicVO bv = this.getBaseNewMusicVOBylist(dr);
                        
                        if (dr != null && (dr.size() == 7 ||dr.size() == 8||dr.size() == 9||dr.size() == 10||dr.size() == 11))
						{//���������ں�

							// Ϊ�Ż�����singer�ֶ�
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
								bv.setSinger("δ֪");
								 logger.error("MusicSingers cann't find singername musicid="+bv.getMusicId());
							}
							// Ϊ�Ż�����productmask�ֶ�

							if (bv != null && bv.getProductMask() != null
									&& bv.getProductMask().length() == 4)
							{
                            	logger.debug("��ֵ������������");

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
                                // ����
                                Integer existid = ( Integer ) existsMusic.get(bv.getMusicId());
                                if (existid == null)
                                {
                                	
        
                                    // ����
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(1));
                                    musicmailInfo[1] = new Integer(musicmailInfo[1].intValue() + 1);// ��������������
                                }
                                else
                                {
                                    // �޸�
                                    bv.setDelFlag(0);
                                    this.addupdateDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                    		new Integer(1));
                                    musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// �޸�����������
                                }

                            }
                            
                            else if (bv.getChangetype().equals("2"))
                            {
                            	
                            	
                            	 // ����
                                Integer existid = ( Integer ) existsMusic.get(bv.getMusicId());
                                if (existid == null)
                                {
          
                                    // ����
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(1));
                                    musicmailInfo[1] = new Integer(musicmailInfo[1].intValue() + 1);// ��������������
                                }
                                else
                                {
                                    // �޸�
                                    bv.setDelFlag(0);
                                    this.addupdateDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(1));
                                    musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// �޸�����������
                                }
                            	
                                // �޸�
                              //  bv.setDelFlag(0);
                                // this.updateDBBaseMusic(bv);
                               // this.addupdateDBNewBaseMusic(bv);
                               // existsMusic.put(bv.getMusicId(), new Integer(0));
                               // musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// �޸�����������
                            }
                            else if (bv.getChangetype().equals("3"))
                            {
                                // ɾ��
                                bv.setDelFlag(1);// ���ɾ�����λ
                                this.delDBNewBaseMusic(bv);
                                // existsMusic.remove(bv.getMusicId());
                                existsMusic.put(bv.getMusicId(), new Integer(0));
                                musicmailInfo[3] = new Integer(musicmailInfo[3].intValue() + 1);// ɾ������������
                            }
                            // �ּ�����
                      
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("��" + lineNumeber + "�����ݴ���ʧ�ܡ�", e);
                        musicmailInfo[5] = new Integer(musicmailInfo[5].intValue() + 1);// ����ʧ������������
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
        updateTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
        updateTaskRunner.end();// ����������
        Iterator iterMap=existsMusic.entrySet().iterator(); 
        
        while(iterMap.hasNext()){                         
            Map.Entry  strMap=(Map.Entry)iterMap.next();                          
            if("0".equals(strMap.getValue().toString())){  
             this.delDBFullBaseMusic((String)strMap.getKey());
            iterMap.remove();                                               
        }  }

    }
    /**
     * ��������--�����ֻ������ݽ���WAP
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
            throw new BOException("û���ҵ������������������Ԫ���ݵ��ļ�",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // �����ļ��ɹ����������
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
                    logger.debug("��ʼ�����ļ���" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// ��¼�ļ���������
                    musicmailInfo[0] = new Integer(musicmailInfo[0].intValue() + 1);// �ܴ�������������
                    if (lineNumeber == 1)// ɾ����һ��bom�ַ�
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("��ʼ�����" + lineNumeber + "�����ݡ�");
                    }
                    if ("".equals(lineText.trim()))// ���ڿ��еļ�¼������
                    {
                        logger.debug("�����ǿ��У�������lineNumeber=" + lineNumeber);
                      //  checkFailureRowByNew.append("<br>").append("  ��").append(lineNumeber).append("��Ϊ��");
                        if (musicmailInfo[4] < 201) {
							checkFailureRowByNew.append("<br>").append("  ��")
									.append(lineNumeber).append("��Ϊ��");
							
						}
						if (musicmailInfo[4] == 201) {
							checkFailureRowByNew.append("<br>").append(
									" .......");
						}
                        musicmailInfo[4] = new Integer(musicmailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
                        continue;
                    }
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        // int checkResult =
                        // BMusicChecker.getInstance().checkDateRecord(dr);
                        int checkResult = BMusicChecker.getInstance()
                                                       .checkNewDateRecord(dr);
                        logger.debug("�����=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("��" + lineNumeber + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
                            //checkFailureRowByNew.append("<br>").append("  ��").append(lineNumeber).append("�����ݼ��ʧ��");
                            
                            if (musicmailInfo[4] < 201) {
    							checkFailureRowByNew.append("<br>").append("  ��")
    									.append(lineNumeber).append("�����ݼ��ʧ��");
    							
    						}
    						if (musicmailInfo[4] == 201) {
    							checkFailureRowByNew.append("<br>").append(
    									" .......");
    						}
                            
                            musicmailInfo[4] = new Integer(musicmailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
                            continue;
                        }
                    }
                    try
                    {
                        BMusicVO bv = this.getBaseNewMusicVOBylist(dr);
                        
                        if (dr != null && (dr.size() == 7 ||dr.size() == 8||dr.size() == 9||dr.size() == 11))
						{//���������ں�

							// Ϊ�Ż�����singer�ֶ�
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
								bv.setSinger("δ֪");
								 logger.error("MusicSingers cann't find singername musicid="+bv.getMusicId());
							}
							// Ϊ�Ż�����productmask�ֶ�
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
                                // ����
                                Integer existid = ( Integer ) existsMusic.get(bv.getMusicId());
                                if (existid == null)
                                {

                                    // ����
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(0));
                                    musicmailInfo[1] = new Integer(musicmailInfo[1].intValue() + 1);// ��������������
                                }
                                else
                                {
                                    // �޸�
                                    bv.setDelFlag(0);
                                    this.addupdateDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(0));
                                    musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// �޸�����������
                                }

                            }
                            else if (bv.getChangetype().equals("2"))
                            {
                            	
                            	
                            	 // ����
                                Integer existid = ( Integer ) existsMusic.get(bv.getMusicId());
                                if (existid == null)
                                {

                                    // ����
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(0));
                                    musicmailInfo[1] = new Integer(musicmailInfo[1].intValue() + 1);// ��������������
                                }
                                else
                                {
                                    // �޸�
                                    bv.setDelFlag(0);
                                    this.addupdateDBNewBaseMusic(bv);
                                    existsMusic.put(bv.getMusicId(),
                                                    new Integer(0));
                                    musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// �޸�����������
                                }
                            	
                                // �޸�
                              //  bv.setDelFlag(0);
                                // this.updateDBBaseMusic(bv);
                               // this.addupdateDBNewBaseMusic(bv);
                               // existsMusic.put(bv.getMusicId(), new Integer(0));
                               // musicmailInfo[2] = new Integer(musicmailInfo[2].intValue() + 1);// �޸�����������
                            }
                            else if (bv.getChangetype().equals("3"))
                            {
                                // ɾ��
                                bv.setDelFlag(1);// ���ɾ�����λ
                                this.delDBNewBaseMusic(bv);
                                // existsMusic.remove(bv.getMusicId());
                                existsMusic.put(bv.getMusicId(), new Integer(1));
                                musicmailInfo[3] = new Integer(musicmailInfo[3].intValue() + 1);// ɾ������������
                            }
                            // �ּ�����
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("��" + lineNumeber + "�����ݴ���ʧ�ܡ�", e);
                        musicmailInfo[5] = new Integer(musicmailInfo[5].intValue() + 1);// ����ʧ������������
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
        updateTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
        updateTaskRunner.end();// ����������

    }
    /**
     * ��������--�����ָ��ֻ������ݽ���WAP
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
            throw new BOException("û���ҵ�����������������ָ���Ԫ���ݵ��ļ�",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // �����ļ��ɹ����������
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
                    logger.debug("��ʼ�����ļ���" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// ��¼�ļ���������
                    newmusicSingermailInfo[0] = new Integer(newmusicSingermailInfo[0].intValue() + 1);// �ܴ�������������
                    if (lineNumeber == 1)// ɾ����һ��bom�ַ�
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("��ʼ�����" + lineNumeber + "�����ݡ�");
                    }
                    if ("".equals(lineText.trim()))// ���ڿ��еļ�¼������
                    {
						logger.debug("�����ǿ��У�������lineNumeber=" + lineNumeber);

						if (newmusicSingermailInfo[4] < 201) {
							checkFailureRowByNew.append("<br>").append("  ��")
									.append(lineNumeber).append("��Ϊ��");
							
						}
						if (newmusicSingermailInfo[4] == 201) {
							checkFailureRowByNew.append("<br>").append(
									" .......");
						}
						newmusicSingermailInfo[4] = new Integer(
								newmusicSingermailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
						continue;
					}
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        // int checkResult =
                        // BMusicChecker.getInstance().checkDateRecord(dr);
                        int checkResult = BNewMusicSingerChecker.getInstance()
                                                       .checkDateRecord(dr);
                        logger.debug("�����=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("��" + lineNumeber + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
                           // checkFailureRowByNew.append("<br>").append("  ��").append(lineNumeber).append("�����ݼ��ʧ��");
                            
                        	if (newmusicSingermailInfo[4] < 201) {
    							checkFailureRowByNew.append("<br>").append("  ��")
    									.append(lineNumeber).append("�����ݼ��ʧ��");
    							
    						}
    						if (newmusicSingermailInfo[4] == 201) {
    							checkFailureRowByNew.append("<br>").append(
    									" .......");
    						}
                            
                            newmusicSingermailInfo[4] = new Integer(newmusicSingermailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
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
                                // ����
                                String existSingerName = ( String ) existsNewMusicSinger.get(bv.getSingerId());
                                if (existSingerName == null)
                                {

                                    // ����
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusicSinger(bv);
                                    existsNewMusicSinger.put(bv.getSingerId(),
                                    		bv.getSingername());
                                    newmusicSingermailInfo[1] = new Integer(newmusicSingermailInfo[1].intValue() + 1);// ��������������
                                }
                                else
                                {
                                    // �޸�
                                    bv.setDelFlag(0);
                                    this.updateDBNewBaseMusicSinger(bv);
                                    existsNewMusicSinger.put(bv.getSingerId(),
                                    		bv.getSingername());
                                    newmusicSingermailInfo[2] = new Integer(newmusicSingermailInfo[2].intValue() + 1);// �޸�����������
                                }

                            }
                            else if (bv.getChangetype().equals("2"))
                            {
                            	
                            	 // �޸�
                            	 String existSingerName = ( String ) existsNewMusicSinger.get(bv.getSingerId());
                                if (existSingerName == null)
                                {

                                    // ����
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusicSinger(bv);
                                    existsNewMusicSinger.put(bv.getSingerId(),
                                    		bv.getSingername());
                                    newmusicSingermailInfo[1] = new Integer(newmusicSingermailInfo[1].intValue() + 1);// ��������������
                                }
                                else
                                {
                                    // �޸�
                                    bv.setDelFlag(0);
                                    this.updateDBNewBaseMusicSinger(bv);
                                    existsNewMusicSinger.put(bv.getSingerId(),
                                    		bv.getSingername());
                                    newmusicSingermailInfo[2] = new Integer(newmusicSingermailInfo[2].intValue() + 1);// �޸�����������
                                }
                                // �޸�
                              //  bv.setDelFlag(0);
                                // this.updateDBBaseMusic(bv);
                               // this.updateDBNewBaseMusicSinger(bv);
                              //  existsNewMusicSinger.put(bv.getSingerId(), new Integer(0));
                               // newmusicSingermailInfo[2] = new Integer(newmusicSingermailInfo[2].intValue() + 1);// �޸�����������
                            }
                            else if (bv.getChangetype().equals("3"))
                            {
                                // ɾ��
                                bv.setDelFlag(1);// ���ɾ�����λ
                                this.delDBNewBaseMusicSinger(bv);
                                // existsMusic.remove(bv.getMusicId());
                                existsNewMusicSinger.put(bv.getSingerId(), bv.getSingername());
                                newmusicSingermailInfo[3] = new Integer(newmusicSingermailInfo[3].intValue() + 1);// ɾ������������
                            }
                            // �ּ�����
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("��" + lineNumeber + "�����ݴ���ʧ�ܡ�", e);
                        newmusicSingermailInfo[5] = new Integer(newmusicSingermailInfo[5].intValue() + 1);// ����ʧ������������
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
        updateTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
        updateTaskRunner.end();// ����������

    }
    
    
    /**
     * ��������--������ר�� ���ݽ���WAP
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
            throw new BOException("û���ҵ������������������ר�� ���ݵ��ļ�",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // �����ļ��ɹ����������
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
                    logger.debug("��ʼ�����ļ���" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// ��¼�ļ���������
                    newmusicAlbummailInfo[0] = new Integer(newmusicAlbummailInfo[0].intValue() + 1);// �ܴ�������������
                    if (lineNumeber == 1)// ɾ����һ��bom�ַ�
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("��ʼ�����" + lineNumeber + "�����ݡ�");
                    }
                    if ("".equals(lineText.trim()))// ���ڿ��еļ�¼������
                    {
                        logger.debug("�����ǿ��У�������lineNumeber=" + lineNumeber);
                       // checkFailureRowByNew.append("<br>").append("  ��").append(lineNumeber).append("��Ϊ��");
                    	if (newmusicAlbummailInfo[4] < 201) {
							checkFailureRowByNew.append("<br>").append("  ��")
									.append(lineNumeber).append("��Ϊ��");
							
						}
						if (newmusicAlbummailInfo[4] == 201) {
							checkFailureRowByNew.append("<br>").append(
									" .......");
						}
                        newmusicAlbummailInfo[4] = new Integer(newmusicAlbummailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
                        continue;
                    }
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        // int checkResult =
                        // BMusicChecker.getInstance().checkDateRecord(dr);
                        int checkResult = BMusicNewChecker.getInstance()
                                                       .checkAlbumDateRecord(dr);
                        logger.debug("�����=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("��" + lineNumeber + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
                            //checkFailureRowByNew.append("<br>").append("  ��").append(lineNumeber).append("�����ݼ��ʧ��");
                            
                        	if (newmusicAlbummailInfo[4] < 201) {
    							checkFailureRowByNew.append("<br>").append("  ��")
    									.append(lineNumeber).append("�����ݼ��ʧ��");
    							
    						}
    						if (newmusicAlbummailInfo[4] == 201) {
    							checkFailureRowByNew.append("<br>").append(
    									" .......");
    						}
                            
                            newmusicAlbummailInfo[4] = new Integer(newmusicAlbummailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
                            continue;
                        }
                    }
                    try
                    {
                    	BNewMusicAlbumVO bv = this.getBaseNewMusicAlbumVOBylist(dr);
                    	bv.setSortId(new Integer(lineNumeber));
                    	
                    	bv.setParentId(categoryID);
                    	  //Ϊ�Ż�����singer�ֶ�
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
							bv.setSinger("δ֪");
							 logger.error("MusicSingers cann't find singername Albumid="+bv.getAlbumId());
						}
                        if (bv != null)
                        {
                            if (bv.getChangetype().equals("1"))
                            {
                                // ����
                                Integer existid = ( Integer ) existsAlbumCate.get(bv.getAlbumId());
                                if (existid == null)
                                {

                                    // ����
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusicAlbum(bv);
                                    existsAlbumCate.put(bv.getAlbumId(),
                                    		new Integer(0));
                                    newmusicAlbummailInfo[1] = new Integer(newmusicAlbummailInfo[1].intValue() + 1);// ��������������
                                }
                                else
                                {
                                    // �޸�
                                    bv.setDelFlag(0);
                                    this.updateDBNewBaseMusicAlbum(bv);
                                    existsAlbumCate.put(bv.getAlbumId(),
                                    		new Integer(0));
                                    newmusicAlbummailInfo[2] = new Integer(newmusicAlbummailInfo[2].intValue() + 1);// �޸�����������
                                }

                            }
                            else if (bv.getChangetype().equals("2"))
                            {
                            	
                            	 // ����
                                Integer existid = ( Integer ) existsAlbumCate.get(bv.getAlbumId());
                                if (existid == null)
                                {

                                    // ����
                                    bv.setDelFlag(0);
                                    this.insertDBNewBaseMusicAlbum(bv);
                                    existsAlbumCate.put(bv.getAlbumId(),
                                    		new Integer(0));
                                    newmusicAlbummailInfo[1] = new Integer(newmusicAlbummailInfo[1].intValue() + 1);// ��������������
                                }
                                else
                                {
                                    // �޸�
                                    bv.setDelFlag(0);
                                    this.updateDBNewBaseMusicAlbum(bv);
                                    existsAlbumCate.put(bv.getAlbumId(),
                                    		new Integer(0));
                                    newmusicAlbummailInfo[2] = new Integer(newmusicAlbummailInfo[2].intValue() + 1);// �޸�����������
                                }
                                // �޸�
                              //  bv.setDelFlag(0);
                                // this.updateDBBaseMusic(bv);
                               // this.updateDBNewBaseMusicSinger(bv);
                              //  existsNewMusicSinger.put(bv.getSingerId(), new Integer(0));
                               // newmusicSingermailInfo[2] = new Integer(newmusicSingermailInfo[2].intValue() + 1);// �޸�����������
                            }
                            else if (bv.getChangetype().equals("3"))
                            {
                                // ɾ��
                                bv.setDelFlag(1);// ���ɾ�����λ
                                this.delDBNewBaseMusicAlbum(bv);
                                // existsMusic.remove(bv.getMusicId());
                                existsAlbumCate.put(bv.getAlbumId(),new Integer(1));
                                newmusicAlbummailInfo[3] = new Integer(newmusicAlbummailInfo[3].intValue() + 1);// ɾ������������
                            }
                            // �ּ�����
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("��" + lineNumeber + "�����ݴ���ʧ�ܡ�", e);
                        newmusicAlbummailInfo[5] = new Integer(newmusicAlbummailInfo[5].intValue() + 1);// ����ʧ������������
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
        updateTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
        updateTaskRunner.end();// ����������

    }
    
    /**
     * �ϼ�������ר����Ʒ
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
            throw new BOException("û���ҵ�������������������Ʒ���ļ�",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // �����ļ��ɹ����������
        int lineNumeber = 0;
        int count = 0; // ���������
        // HashMap existsMusic = null;
//        try
//        {
          //  boolean checkCategoryExist = BMusicDAO.getInstance()
          //                                        .checkCategoryById(categoryID);
            // �������Ƿ���ڣ����������˳�
           // if (!checkCategoryExist)
           // {
           //     logger.error("�����ݿ����Ҳ������»���IDΪ" + categoryID + "�Ĳ������ܣ�");
            //    throw new BOException("�����ݿ����Ҳ������»���IDΪ" + categoryID + "�Ĳ������ܣ�");
           // }
            // ��ȡ���е��Ѿ����ڵ�����
            // existsMusic = BMusicDAO.getInstance().getAllexistMusicID();

            // �¼����»����µ�������Ʒ
           // BMusicDAO.getInstance().delBMusicRefByCateID(categoryID);

//        }
//        catch (DAOException e1)
//        {
//            logger.error("�����ݿ��л�ȡ���л�������IDʱ�������ݿ��쳣��", e1);
//            throw new BOException("�����ݿ��л�ȡ���л�������IDʱ�������ݿ��쳣", e1);
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
                    logger.debug("��ʼ�����ļ���" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));

                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// ��¼�ļ���������
                    newRefmailInfo[0] = new Integer(newRefmailInfo[0].intValue() + 1);// ��������������
                    if (lineNumeber == 1)// ɾ����һ��bom�ַ�
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("��ʼ�����" + lineNumeber + "�����ݡ�");
                    }
                    if ("".equals(lineText.trim()))// ���ڿ��еļ�¼������
                    {
                        logger.debug("�����ǿ��У�������lineNumeber=" + lineNumeber);
                        //checkFailureRowByLatest.append("<br>").append("  ��").append(lineNumeber).append("��Ϊ��");
                        
                        if (newRefmailInfo[4] < 201) {
                        	checkFailureRowByLatest.append("<br>").append("  ��")
									.append(lineNumeber).append("��Ϊ��");
							
						}
						if (newRefmailInfo[4] == 201) {
							checkFailureRowByLatest.append("<br>").append(
									" .......");
						}
                        
                        newRefmailInfo[4] = new Integer(newRefmailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
                        continue;
                    }
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        int checkResult = BMusicNewChecker.getInstance()
                                                          .checkDateRecord(dr);
                        logger.debug("�����=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("��" + lineNumeber + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
                            //checkFailureRowByLatest.append("<br>").append("  ��").append(lineNumeber).append("�����ݼ��ʧ��");
                            if (newRefmailInfo[4] < 201) {
                            	checkFailureRowByLatest.append("<br>").append("  ��")
    									.append(lineNumeber).append("�����ݼ��ʧ��");
    							
    						}
    						if (newRefmailInfo[4] == 201) {
    							checkFailureRowByLatest.append("<br>").append(
    									" .......");
    						}
                            
                            newRefmailInfo[4] = new Integer(newRefmailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
                            continue;
                        }
                    }
                    try
                    {

                        if (dr != null && dr.size() == 4)
                        {

                            // ����
                            Integer existid = ( Integer ) existsMusic.get(dr.get(1));
                            String existsalbumCateId = ( String ) existsAlbum.get(dr.get(0));
                            if (existid != null && existid.intValue() == 0 && existsalbumCateId != null )
                            {// ���ڲ���δɾ��
                                // ����
                               // dr.add(categoryID);
                                String goodsid = ( String ) dr.get(0)
                                                 + ( String ) dr.get(1);
                                String temp = ( String ) existsRefMusic.get(goodsid);
                                dr.set(3,existsalbumCateId);
                                dr.remove(0);
                                dr.add(1,"");
                                if (temp == null)
                                {// û���ظ��ϼ�
                                    this.insertDBBaseMusicNewRef(dr);
                                    count++;
                                    existsRefMusic.put(goodsid,
                                                       ( String ) dr.get(0));
                                    newRefmailInfo[1] = new Integer(newRefmailInfo[1].intValue() + 1);// ��������������
                                }
                                else
                                {
                                    // �ظ��ϼ�
                                    logger.error("�ظ��ϼ�,�ϼ�����ID:" + dr.get(0)
                                                 + ",�ϼܻ���Ϊ��" + existsalbumCateId);
                                    newRefmailInfo[2] = new Integer(newRefmailInfo[2].intValue() + 1);// �ظ��ϼ�ʧ������������
                                }
                            }
                            else
                            {
                                // ���ֿ���û�и����֣������ϼ�
                                logger.error("���°��ϼܣ���" + lineNumeber
                                             + "�У����ֿ���û��Musicid=" + dr.get(0)
                                             + "������ �����ϼ�");
                                newRefmailInfo[3] = new Integer(newRefmailInfo[3].intValue() + 1);// Ԫ���ݲ������ϼ�ʧ������������
                            }

                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("��" + lineNumeber + "�����ݴ���ʧ�ܡ�", e);
                        newRefmailInfo[5] = new Integer(newRefmailInfo[5].intValue() + 1);// ���ݴ���ʧ������������
                        errorFailureRowByLatest.append(lineNumeber).append(", ");
                        continue;
                    }

                }
            }
            // ���»�����Ʒ����
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
        insertRefTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
        insertRefTaskRunner.end();// ����������

    }

    /**
     * �ϼܾɰ�������Ʒ  �ѷ���
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
            logger.error("û���ҵ����������������Ʒ���ļ���");
            return;
            // throw new BOException("û���ҵ�����������ļ�",
            // DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // �����ļ��ɹ����������
        int lineNumeber = 0;
        // HashMap existsMusic = null;
        HashMap excueCharsRef = new HashMap();
        try
        {
            boolean checkCategoryExist = BMusicDAO.getInstance()
                                                  .checkCategoryById(categoryID);
            // �������Ƿ���ڣ����������˳�
            if (!checkCategoryExist)
            {
                logger.error("�����ݿ����Ҳ����񵥻���IDΪ" + categoryID + "�Ĳ������ܣ�");
                throw new BOException("�����ݿ����Ҳ����񵥻���IDΪ" + categoryID + "�Ĳ������ܣ�");
            }
            // ��ȡ���е��Ѿ����ڵ�����
            // existsMusic =
            // BMusicDAO.getInstance().getAllexistMusicID();//������ȥ���ݿ����

            // �¼����»����µ�������Ʒ
            // BMusicDAO.getInstance().delBMusicRefByCateID(categoryID);

        }
        catch (DAOException e1)
        {
            logger.error("�����ݿ��л�ȡ���л�������IDʱ�������ݿ��쳣��", e1);
            throw new BOException("�����ݿ��л�ȡ���л�������IDʱ�������ݿ��쳣", e1);
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
                    logger.debug("��ʼ�����ļ���" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));

                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// ��¼�ļ���������
                    charRefmailInfo[0] = new Integer(charRefmailInfo[0].intValue() + 1);// ���ݴ�������������
                    if (lineNumeber == 1)// ɾ����һ��bom�ַ�
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("��ʼ�����" + lineNumeber + "�����ݡ�");
                    }
                    if ("".equals(lineText.trim()))// ���ڿ��еļ�¼������
                    {
                        logger.debug("�����ǿ��У�������lineNumeber=" + lineNumeber);
                        charRefmailInfo[4] = new Integer(charRefmailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
                        continue;
                    }
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        int checkResult = BMusicHotTopChecker.getInstance()
                                                             .checkDateRecord(dr);
                        logger.debug("�����=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("��" + lineNumeber + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
                            charRefmailInfo[4] = new Integer(charRefmailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
                            continue;
                        }
                    }
                    try
                    {

                        if (dr != null && dr.size() == 4)
                        {
                            // ����
                            Integer existid = ( Integer ) existsMusic.get(dr.get(1));

                            // ���������ԭ����
                            List tempcaref = ( List ) excueCharsRef.get(dr.get(0));
                            if (tempcaref == null)
                            {
                                excueCharsRef.put(dr.get(0), null);// ��ʼ����������

                            }
                            if (existid != null && existid.intValue() == 0)
                            { // ���ڣ�����δɾ��
                                // ����
                                excueCharsRef = this.getBaseMusicCharBylist(excueCharsRef,
                                                                            dr);
                                charRefmailInfo[1] = new Integer(charRefmailInfo[1].intValue() + 1);// ��������������
                            }
                            else
                            {
                                // ���ֿ���û�и����֣������ϼ�
                                logger.error("���а��ϼܣ���" + lineNumeber
                                             + "�У����ֿ���û��Musicid=" + dr.get(1)
                                             + "������ �����ϼ�");
                                charRefmailInfo[3] = new Integer(charRefmailInfo[3].intValue() + 1);// Ԫ���ݲ������ϼ�ʧ������������
                                continue;

                            }

                            // �ּ�����
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("��" + lineNumeber + "�����ݴ���ʧ�ܡ�", e);
                        charRefmailInfo[5] = new Integer(charRefmailInfo[5].intValue() + 1);// ���ݴ���ʧ������������
                        continue;
                    }

                }
            }
            // ��excueCharsRef���д���
            // ��ʼ�ϼ�������Ʒ
            for (Iterator iter = excueCharsRef.entrySet().iterator(); iter.hasNext();)
            {
                Map.Entry entry = ( Map.Entry ) iter.next();
                String cateName = ( String ) entry.getKey();// ��������
                List val = ( List ) entry.getValue();// ��������ϵ�������Ʒ
                int cou = 0;
                if (val != null)
                {
                    cou = val.size();
                }
                String subCategoryID = BMusicDAO.getInstance()
                                                .getCategoryIDByNameAndParCid(categoryID,
                                                                              cateName,"",//add by dongke 20130607 music_list change
                                                                              cou);
                // �¼ܷ�����ܶ��������µ�������Ʒ
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
                            {// û���ظ��ϼ�
                                this.insertDBBaseMusicNewRef(ref);
                                existsRefMusic.put(goodsid,
                                                   ( String ) ref.get(0));
                                count++;
                            }
                            else
                            {
                                // �ظ��ϼ�
                                logger.error("�ظ��ϼ�,�ϼ�����ID:" + ref.get(0)
                                             + ",�ϼܻ���Ϊ��" + subCategoryID);
                                charRefmailInfo[2] = new Integer(charRefmailInfo[2].intValue() + 1);// �ظ��ϼ�����������
                            }

                        }

                    }

                }
                // ���»�����Ʒ����
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
        insertRefTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
        insertRefTaskRunner.end();// ����������

    }

    /**
     * �ϼ��°�������Ʒ
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
            logger.error("û���ҵ��������������ְ�������Ʒ���ļ���");
            return;
            // throw new BOException("û���ҵ�����������ļ�",
            // DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // �����ļ��ɹ����������
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
                        // �������Ƿ���ڣ����������˳�
                        if (!checkCategoryExist)
                        {
                            logger.error("�����ݿ����Ҳ����񵥻���IDΪ" + categoryID
                                         + "�Ĳ������ܣ�");
                            // throw new
                            // BOException("�����ݿ����Ҳ����񵥻���IDΪ"+categoryID+"�Ĳ������ܣ�");
                        }
                        else
                        {
                            typeMapCate.put(mapcates[1], mapcates[0]);
                        }
                    }
                }

            }
            // ��ȡ���е��Ѿ����ڵ�����
            // existsMusic =
            // BMusicDAO.getInstance().getAllexistMusicID();//������ȥ���ݿ����

            // �¼����»����µ�������Ʒ
            // BMusicDAO.getInstance().delBMusicRefByCateID(categoryID);

        }
        catch (DAOException e1)
        {
            logger.error("�����ݿ��л�ȡ���л�������IDʱ�������ݿ��쳣��", e1);
            throw new BOException("�����ݿ��л�ȡ���л�������IDʱ�������ݿ��쳣", e1);
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
                    logger.debug("��ʼ�����ļ���" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));

                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// ��¼�ļ���������
                    charRefmailInfo[0] = new Integer(charRefmailInfo[0].intValue() + 1);// ���ݴ�������������
                    if (lineNumeber == 1)// ɾ����һ��bom�ַ�
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("��ʼ�����" + lineNumeber + "�����ݡ�");
                    }
                    if ("".equals(lineText.trim()))// ���ڿ��еļ�¼������
                    {
                        logger.debug("�����ǿ��У�������lineNumeber=" + lineNumeber);
                        //checkFailureRowByUp.append("<br>").append("  ��").append(lineNumeber).append("��Ϊ��");
                        
                        if (charRefmailInfo[4] < 201) {
                        	checkFailureRowByUp.append("<br>").append("  ��")
									.append(lineNumeber).append("��Ϊ��");
							
						}
						if (charRefmailInfo[4] == 201) {
							checkFailureRowByUp.append("<br>").append(
									" .......");
						}
                        
                        charRefmailInfo[4] = new Integer(charRefmailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
                        continue;
                    }
                    List dr = this.readDataRecordChar(lineText, sep);
                    if (dr != null)
                    {
                        int checkResult = BMusicHotTopChecker.getInstance()
                                                             .checkDateRecord(dr);
                        logger.debug("�����=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("��" + lineNumeber + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
                            //checkFailureRowByUp.append("<br>").append("  ��").append(lineNumeber).append("�����ݼ��ʧ��");
                            
                            if (charRefmailInfo[4] < 201) {
                            	checkFailureRowByUp.append("<br>").append("  ��")
    									.append(lineNumeber).append("�����ݼ��ʧ��");
    							
    						}
    						if (charRefmailInfo[4] == 201) {
    							checkFailureRowByUp.append("<br>").append(
    									" .......");
    						}
                            
                            charRefmailInfo[4] = new Integer(charRefmailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
                            continue;
                        }
                    }
                    try
                    {

                        if (dr != null && dr.size() == 5)
                        {
                            // ����
                            Integer existid = ( Integer ) existsMusic.get(dr.get(1));

                            // ���������ԭ����
                            List tempcaref = ( List ) excueCharsRef.get(dr.get(4)
                                                                        + "|"
                                                                        + dr.get(0));
                            if (tempcaref == null)
                            {
                                excueCharsRef.put(dr.get(4) + "|" + dr.get(0),
                                                  null);// ��ʼ����������,����+�������ƣ�// ��ID+������ change by dongke 20130607
                                // 04|�¸��Ա������

                            }
                            if (existid != null && existid.intValue() == 0)
                            { // ���ڣ�����δɾ��
                                // ����
                                excueCharsRef = this.getBaseMusicCharBylist(excueCharsRef,
                                                                            dr);
                                charRefmailInfo[1] = new Integer(charRefmailInfo[1].intValue() + 1);// ��������������
                            }
                            else
                            {
                                // ���ֿ���û�и����֣������ϼ�
                                logger.error("���а��ϼܣ���" + lineNumeber
                                             + "�У����ֿ���û��Musicid=" + dr.get(1)
                                             + "������ �����ϼ�");
                                charRefmailInfo[3] = new Integer(charRefmailInfo[3].intValue() + 1);// Ԫ���ݲ������ϼ�ʧ������������
                                continue;

                            }

                            // �ּ�����
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("��" + lineNumeber + "�����ݴ���ʧ�ܡ�", e);
                        charRefmailInfo[5] = new Integer(charRefmailInfo[5].intValue() + 1);// ���ݴ���ʧ������������
                        errorFailureRowByUp.append(lineNumeber).append(", ");
                        continue;
                    }

                }
            }
            // ��excueCharsRef���д���
            // ��ʼ�ϼ�������Ʒ
            for (Iterator iter = excueCharsRef.entrySet().iterator(); iter.hasNext();)
            {
                Map.Entry entry = ( Map.Entry ) iter.next();
                String typeAndcateName = ( String ) entry.getKey();// ��������
                List val = ( List ) entry.getValue();// ��������ϵ�������Ʒ
                int cou = 0;
                if (val != null)
                {
                    cou = val.size();
                }
                String[] typeAndcateNames = typeAndcateName.split("[|]");
                if (typeAndcateNames.length == 2)
                {
                    String cateName = typeAndcateNames[1];
                    String listId = typeAndcateNames[0]; //��ID �����ֻ����ṩ�Ŀ�ѡ�ֶ�

//                    if (typeMapCate.get(type) != null)
//                    {
//                        String parCategoryId = ( String ) typeMapCate.get(type);

                        String subCategoryID = BMusicDAO.getInstance()
                                                        .getCategoryIDByNameAndParCid(categoryID,
                                                                                      cateName,listId,
                                                                                      cou);
                        // �¼ܷ�����ܶ��������µ�������Ʒ
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
                                    {// û���ظ��ϼ�
                                        this.insertDBBaseMusicNewRef(ref);
                                        existsRefMusic.put(goodsid,
                                                           ( String ) ref.get(0));
                                        count++;
                                    }
                                    else
                                    {
                                        // �ظ��ϼ�
                                        logger.error("�ظ��ϼ�,�ϼ�����ID:"
                                                     + ref.get(0) + ",�ϼܻ���Ϊ��"
                                                     + subCategoryID);
                                        charRefmailInfo[2] = new Integer(charRefmailInfo[2].intValue() + 1);// �ظ��ϼ�����������
                                    }

                                }

                            }

                        }

                        // ���»�����Ʒ����
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
        insertRefTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
        insertRefTaskRunner.end();// ����������

    }

    /**
     * �¼� �� ��������ȫ������������Ʒ
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
            logger.error("���ݿ����ʧ��" + e);
            throw new BOException("���ݿ����ʧ��", e);

        }
    }

    /**
     * ���� �� ���ֻ�����Ʒ����
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
            logger.error("���ݿ����ʧ��" + e);
            throw new BOException("���ݿ����ʧ��", e);

        }
    }

    /**
     * �¼ܻ�������ȫ������������Ʒ
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
            logger.error("���ݿ����ʧ��" + e);
            throw new BOException("���ݿ����ʧ��", e);

        }
    }

    // 100002166
    /**
     * �¼ܻ�������ȫ������������Ʒ
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
            logger.error("���ݿ����ʧ��" + e);
            throw new BOException("���ݿ����ʧ��", e);

        }
    }

    /**
     * ��ȡVO
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
     * ��ȡVO
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
     * ��ȡVO
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
     *@desc ר��
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
     * ��ȡ�����ϼ���Ʒ���浽Hashmap
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
            {// �����ڶ��������������
                cateRefList = new ArrayList();
                record.remove(0);
                // String temp = (String)record.get(0);
                cateRefList.add(record);
                hm.put(type + "|" + cateName, cateRefList);
            }
            else
            {// ���ڶ��������������
                record.remove(0);
                cateRefList.add(record);
                hm.put(type + "|" + cateName, cateRefList);
            }
        }

        return hm;
    }

    /**
     * ���»���������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ����������Ϣ��vo
     * @return int �ɹ����»����������ݵĸ���
     */
    private void addupdateDBBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBBaseMusic() id=" + vo.getMusicId());
        }
        BaseMusicDBOpration cm = new BaseMusicDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm,
                                               "addUpdateBMusicVO",
                                               null,
                                               null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ���»���������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ����������Ϣ��vo
     * @return int �ɹ����»����������ݵĸ���
     */
    private void updateDBBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBBaseMusic() id=" + vo.getMusicId());
        }
        BaseMusicDBOpration cm = new BaseMusicDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "UpdateBMusicVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * �������������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ����������Ϣ��vo
     * @return int �ɹ����»����������ݵĸ���
     */
    private void insertDBBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("InsertDBBaseMusic() id=" + vo.getMusicId());
        }
        BaseMusicDBOpration cm = new BaseMusicDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "InsertBMusicVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ɾ������������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ����������Ϣ��vo
     * @return int �ɹ����»����������ݵĸ���
     */
    private void delDBBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("DelDBBaseMusic() id=" + vo.getMusicId());
        }
        BaseMusicDBOpration cm = new BaseMusicDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "DelBMusicVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ������ ����������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ����������Ϣ��vo
     * @return int �ɹ����»����������ݵĸ���
     */
    private void addupdateDBNewBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBBaseMusic() id=" + vo.getMusicId());
        }
        NewBaseMusicDBOpration cm = new NewBaseMusicDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm,
                                               "addUpdateBMusicVO",
                                               null,
                                               null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ������ ����������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ����������Ϣ��vo
     * @return int �ɹ����»����������ݵĸ���
     */
    private void updateDBNewBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBNewBaseMusic() id=" + vo.getMusicId());
        }
        NewBaseMusicDBOpration cm = new NewBaseMusicDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "UpdateBMusicVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ������ ����������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ����������Ϣ��vo
     * @return int �ɹ����»����������ݵĸ���
     */
    private void insertDBNewBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("InsertDBNewBaseMusic() id=" + vo.getMusicId());
        }
        NewBaseMusicDBOpration cm = new NewBaseMusicDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "InsertBMusicVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ɾ���� ����������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ����������Ϣ��vo
     * @return int �ɹ����»����������ݵĸ���
     */
    private void delDBNewBaseMusic(BMusicVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("DelDBNewBaseMusic() id=" + vo.getMusicId());
        }
        NewBaseMusicDBOpration cm = new NewBaseMusicDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "DelBMusicVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }
    
    /**
     * ɾ���� ����������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ����������Ϣ��vo
     * @return int �ɹ����»����������ݵĸ���
     */
    private void delDBFullBaseMusic(String MusicId)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("DelDBNewBaseMusic() id=" +MusicId);
        }
        NewBaseMusicDBOpration cm = new NewBaseMusicDBOpration(new BMusicVO());
        // �����첽����
//        ReflectedTask task = new ReflectedTask(cm, "DelFullMusicVO", null, null);
//        // ������ӵ���������
//        updateTaskRunner.addTask(task);
         cm.DelFullMusicVO(MusicId);
    }
    
    /**
     * ������ �������ָ�����Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo �������ָ�����Ϣ��vo
     * @return int �ɹ����»������ָ������ݵĸ���
     */
    private void updateDBNewBaseMusicSinger(BNewMusicSingerVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBNewBaseMusicSinger() id=" + vo.getSingerId());
        }
        NewBaseMusicSingerDBOpration cm = new NewBaseMusicSingerDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "addUpdateBMusicSingerVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ������ �������ָ�����Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo �������ָ�����Ϣ��vo
     * @return int �ɹ����»������ָ������ݵĸ���
     */
    private void insertDBNewBaseMusicSinger(BNewMusicSingerVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("insertDBNewBaseMusicSinger() id=" + vo.getSingerId());
        }
        NewBaseMusicSingerDBOpration cm = new NewBaseMusicSingerDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "InsertBMusicSingerVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ɾ���� ����������Ϣ����,���ö��߳�����첽���¡�
     * 
     * @param vo ����������Ϣ���ֵ�vo
     * @return int �ɹ����»������ָ������ݵĸ���
     */
    private void delDBNewBaseMusicSinger(BNewMusicSingerVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delDBNewBaseMusicSinger() id=" + vo.getSingerId());
        }
        NewBaseMusicSingerDBOpration cm = new NewBaseMusicSingerDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "DelBMusicSingerVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    
    /**
     * ������ ��������ר����Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ��������ר����Ϣ��vo
     * @return int �ɹ����»�������ר�����ݵĸ���
     */
    private void updateDBNewBaseMusicAlbum(BNewMusicAlbumVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBNewBaseMusicAlbum() id=" + vo.getAlbumId());
        }
        BaseMusicAlbumDBOpration cm = new BaseMusicAlbumDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "addUpdateBMusicAlbumVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ������ ��������ר����Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ��������ר����Ϣ��vo
     * @return int �ɹ����»�������ר�����ݵĸ���
     */
    private void insertDBNewBaseMusicAlbum(BNewMusicAlbumVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("insertDBNewBaseMusicSinger() id=" + vo.getAlbumId());
        }
        BaseMusicAlbumDBOpration cm = new BaseMusicAlbumDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "InsertBMusicAlbumVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ɾ���� ����������Ϣר��,���ö��߳�����첽���¡�
     * 
     * @param vo ����������Ϣר����vo
     * @return int �ɹ����»�������ר�����ݵĸ���
     */
    private void delDBNewBaseMusicAlbum(BNewMusicAlbumVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delDBNewBaseMusicSinger() id=" + vo.getAlbumId());
        }
        BaseMusicAlbumDBOpration cm = new BaseMusicAlbumDBOpration(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "DelBMusicAlbumVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    
    /**
     * �������������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ����������Ϣ��vo
     * @return int �ɹ����»����������ݵĸ���
     */
    private void insertDBBaseMusicNewRef(List dr)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("InsertDBBaseMusicNewRef() id=" + dr.get(0));
        }
        BaseMusicReferenceDBOpration cm = new BaseMusicReferenceDBOpration(dr);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm,
                                               "InsertNewBMusicReference",
                                               null,
                                               null);
        // ������ӵ���������
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
            // 0x��ͷ�ģ���ʾ��16���Ƶģ���Ҫת��
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
     * ����������
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
            throw new BOException("û���ҵ���������������Ԫ���ݵ��ļ�",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // �����ļ��ɹ����������
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
                    logger.debug("��ʼ�����ļ���" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// ��¼�ļ���������
                    toneboxMailInfo[0] = new Integer(toneboxMailInfo[0].intValue() + 1);// �ܴ�������������
                    if (lineNumeber == 1)// ɾ����һ��bom�ַ�
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("��ʼ�����" + lineNumeber + "�����ݡ�");
                    }
                    if ("".equals(lineText.trim()))// ���ڿ��еļ�¼������
                    {
						logger.debug("�����ǿ��У�������lineNumeber=" + lineNumeber);

						if (toneboxMailInfo[4] < 201) {
							checkFailureRowByNew.append("<br>").append("  ��")
									.append(lineNumeber).append("��Ϊ��");
							
						}
						if (toneboxMailInfo[4] == 201) {
							checkFailureRowByNew.append("<br>").append(
									" .......");
						}
						toneboxMailInfo[4] = new Integer(
								toneboxMailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
						continue;
					}
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        int checkResult = ToneBoxChecker.getInstance()
                                                       .checkDateRecord(dr);
                        logger.debug("�����=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("��" + lineNumeber + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
                            
                        	if (toneboxMailInfo[4] < 201) {
    							checkFailureRowByNew.append("<br>").append("  ��")
    									.append(lineNumeber).append("�����ݼ��ʧ��");
    							
    						}
    						if (toneboxMailInfo[4] == 201) {
    							checkFailureRowByNew.append("<br>").append(
    									" .......");
    						}
                            
                            toneboxMailInfo[4] = new Integer(toneboxMailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
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
                                // ����
                                String existSingerName = ( String ) existsTonebox.get(bv.getId());
                                if (existSingerName == null)
                                {

                                    // ����
                                    this.insertDBToneBox(bv);
                                    existsTonebox.put(bv.getId(),bv.getName());
                                    toneboxMailInfo[1] = new Integer(toneboxMailInfo[1].intValue() + 1);// ��������������
                                }
                                else
                                {
                                    // �޸�
                                    this.updateDBToneBox(bv);
                                    existsTonebox.put(bv.getId(),bv.getName());
                                    toneboxMailInfo[2] = new Integer(toneboxMailInfo[2].intValue() + 1);// �޸�����������
                                    existsToneboxIds.put(bv.getId(), 1);
                                }

//                            }
//                            else if ("3".equals(bv.getOperType()))
//                            {
//                                // ɾ��
//                                this.delDBToneBox(bv);
//                                existsTonebox.remove(bv.getId());
//                                toneboxMailInfo[3] = new Integer(toneboxMailInfo[3].intValue() + 1);// ɾ������������
//                            }
                            // �ּ�����
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("��" + lineNumeber + "�����ݴ���ʧ�ܡ�", e);
                        toneboxMailInfo[5] = new Integer(toneboxMailInfo[5].intValue() + 1);// ����ʧ������������
                        errorFailureRowByNew.append(lineNumeber).append(", ");
                        continue;
                    }

                }
            }
            
            //ɾ����������
            logger.debug("ɾ�������п�ʼ" );
            if(existsToneboxIds!=null&&!existsToneboxIds.isEmpty()&&existsToneboxIds.size()>0)
            {
	            for(String toneboxId:existsToneboxIds.keySet())
	            {
	            	if(existsToneboxIds.get(toneboxId)==0)
	            	{
		              // ɾ��
	            	  logger.debug("ɾ��������,toneboxId:" + toneboxId);
		              ToneBoxVO bv = new ToneBoxVO();
		              bv.setId(toneboxId);
		              this.delDBToneBox(bv);
		              existsTonebox.remove(toneboxId);
		              toneboxMailInfo[3] = new Integer(toneboxMailInfo[3].intValue() + 1);// ɾ������������
	            	}
	            }
            }
            logger.debug("ɾ�������н���" );

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
        updateTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
        updateTaskRunner.end();// ����������
		
	}
	
	
	/**
     * ��ȡVO
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
     * ������������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ��������Ϣ��vo
     */
    private void updateDBToneBox(ToneBoxVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBToneBox() id=" + vo.getId());
        }
        ToneBoxDAO cm = new ToneBoxDAO(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "updateToneBoxVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ������������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ��������Ϣ��vo
     */
    private void insertDBToneBox(ToneBoxVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("insertDBToneBox() id=" + vo.getId());
        }
        ToneBoxDAO cm = new ToneBoxDAO(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "insertToneBoxVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ɾ����������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ��������Ϣ��vo
     */
    private void delDBToneBox(ToneBoxVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delDBToneBox() id=" + vo.getId());
        }
        ToneBoxDAO cm = new ToneBoxDAO(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "delToneBoxVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }
    
    
    
    /**
     * ���������и���
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
            throw new BOException("û���ҵ��������������и���Ԫ���ݵ��ļ�",
                                  DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
        }
        String lineText = null;
        BufferedReader reader = null;
        // �����ļ��ɹ����������
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
                    logger.debug("��ʼ�����ļ���" + filenameList[i]);
                }
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenameList[i]),
                                                                  fileEncoding));
                while ((lineText = reader.readLine()) != null)
                {
                    lineNumeber++;// ��¼�ļ���������
                    toneboxMailInfo[0] = new Integer(toneboxMailInfo[0].intValue() + 1);// �ܴ�������������
                    if (lineNumeber == 1)// ɾ����һ��bom�ַ�
                    {
                        lineText = PublicUtil.delStringWithBOM(lineText);
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("��ʼ�����" + lineNumeber + "�����ݡ�");
                    }
                    if ("".equals(lineText.trim()))// ���ڿ��еļ�¼������
                    {
						logger.debug("�����ǿ��У�������lineNumeber=" + lineNumeber);

						if (toneboxMailInfo[4] < 201) {
							checkFailureRowByNew.append("<br>").append("  ��")
									.append(lineNumeber).append("��Ϊ��");
							
						}
						if (toneboxMailInfo[4] == 201) {
							checkFailureRowByNew.append("<br>").append(
									" .......");
						}
						toneboxMailInfo[4] = new Integer(
								toneboxMailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
						continue;
					}
                    List dr = this.readDataRecord(lineText, sep);
                    if (dr != null)
                    {
                        int checkResult = ToneBoxSongChecker.getInstance()
                                                       .checkDateRecord(dr);
                        logger.debug("�����=" + checkResult);
                        if (checkResult == DataSyncConstants.CHECK_FAILED)
                        {
                            logger.error("��" + lineNumeber + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
                            
                        	if (toneboxMailInfo[4] < 201) {
    							checkFailureRowByNew.append("<br>").append("  ��")
    									.append(lineNumeber).append("�����ݼ��ʧ��");
    							
    						}
    						if (toneboxMailInfo[4] == 201) {
    							checkFailureRowByNew.append("<br>").append(
    									" .......");
    						}
                            
                            toneboxMailInfo[4] = new Integer(toneboxMailInfo[4].intValue() + 1);// ���ݼ�鲻�Ϸ�����������
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
                                // ����
                                String existSingerName = ( String ) existsTonebox.get(bv.getId()+"_"+bv.getBoxId());
                                if (existSingerName == null)
                                {

                                    // ����
                                    this.insertDBToneBoxSong(bv);
                                    existsTonebox.put(bv.getId()+"_"+bv.getBoxId(),bv.getId()+"_"+bv.getBoxId());
                                    toneboxMailInfo[1] = new Integer(toneboxMailInfo[1].intValue() + 1);// ��������������
                                }
                                else
                                {
                                    // �޸�
                                    this.updateDBToneBoxSong(bv);
                                    existsTonebox.put(bv.getId()+"_"+bv.getBoxId(),bv.getId()+"_"+bv.getBoxId());
                                    toneboxMailInfo[2] = new Integer(toneboxMailInfo[2].intValue() + 1);// �޸�����������
                                    existsToneboxSongIds.put(bv.getId()+"_"+bv.getBoxId(), 1);
                                }

//                            }
//                            else if ("3".equals(bv.getOperType()))
//                            {
//                                // ɾ��
//                                this.delDBToneBoxSong(bv);
//                                existsTonebox.remove(bv.getId()+"_"+bv.getBoxId());
//                                toneboxMailInfo[3] = new Integer(toneboxMailInfo[3].intValue() + 1);// ɾ������������
//                            }
                            // �ּ�����
                        }

                    }
                    catch (Exception e)
                    {
                        logger.error("��" + lineNumeber + "�����ݴ���ʧ�ܡ�", e);
                        toneboxMailInfo[5] = new Integer(toneboxMailInfo[5].intValue() + 1);// ����ʧ������������
                        errorFailureRowByNew.append(lineNumeber).append(", ");
                        continue;
                    }

                }
            }
            logger.debug("ɾ�������и�����ʼ" );
            //ɾ����������
            if(existsToneboxSongIds!=null&&!existsToneboxSongIds.isEmpty()&&existsToneboxSongIds.size()>0)
            {
	            for(String toneboxId:existsToneboxSongIds.keySet())
	            {
	            	if(existsToneboxSongIds.get(toneboxId)==0)
	            	{
	            		// ɾ��
	  	              ToneBoxSongVO bv = new ToneBoxSongVO();
	  	              String ids[]=toneboxId.split("_");
	  	              logger.debug("ɾ�������и���,����ID:" + ids[0]+",������ID:"+ids[1]);
	  	              bv.setId(ids[0]);
	  	              bv.setBoxId(ids[1]);
	  	              this.delDBToneBoxSong(bv);
	  	              existsTonebox.remove(toneboxId);
	  	              toneboxMailInfo[3] = new Integer(toneboxMailInfo[3].intValue() + 1);// ɾ������������
	            	}
	            
	            }
            }
            logger.debug("ɾ�������и�������" );

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
        updateTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
        updateTaskRunner.end();// ����������
		
	}
	
	
	
	/**
     * ��ȡVO
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
     * ������������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ��������Ϣ��vo
     */
    private void updateDBToneBoxSong(ToneBoxSongVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateDBToneBoxSong() id=" + vo.getId());
        }
        ToneBoxSongDAO cm = new ToneBoxSongDAO(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "updateToneBoxSongVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ������������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ��������Ϣ��vo
     */
    private void insertDBToneBoxSong(ToneBoxSongVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("insertDBToneBoxSong() id=" + vo.getId());
        }
        ToneBoxSongDAO cm = new ToneBoxSongDAO(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "insertToneBoxSongVO", null, null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

    /**
     * ɾ����������Ϣ,���ö��߳�����첽���¡�
     * 
     * @param vo ��������Ϣ��vo
     */
    private void delDBToneBoxSong(ToneBoxSongVO vo)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delDBToneBox() id=" + vo.getId()+",boxId="+vo.getBoxId());
        }
        ToneBoxSongDAO cm = new ToneBoxSongDAO(vo);
        // �����첽����
        ReflectedTask task = new ReflectedTask(cm, "delToneBoxSongVO", null, null);
        // ������ӵ���������
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
            // 0x��ͷ�ģ���ʾ��16���Ƶģ���Ҫת��
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
