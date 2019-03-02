package com.aspire.dotcard.basemusic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basemusic.config.BaseMusicConfig;
import com.aspire.ponaadmin.web.mail.Mail;

public class BaseMusicFullBO {
	
protected static JLogger logger = LoggerFactory.getLogger(BaseMusicLoadTask.class);
public void run() throws BOException{
    /** ********************新增导入新音乐数据到WAP门户********************** */
    /**
     * 0,处理的总行数；1，成功新增；2，成功修改；3，成功删除；4，数据检查不合法；5，数据处理失败
     */
    Integer newmusicmailInfo[] = new Integer[6];
    // 1,获取新音乐清单文件名称
    /**
     * 下架新商品情况
     */
    Integer[] delNewRefmailInfo = new Integer[1];
    String synBaseMusicSubject = BaseMusicConfig.get("synBaseFullMusicSubject");
    String[] BaseMusicSynMailto = BaseMusicConfig.get("BaseFullMusicSynMailto")
                                                 .split(",");
    /**
     * 更新货架商品数量
     */
    Integer charRefmailInfo[] = new Integer[6];

    /**
     * 下架商品情况
     */
    Integer[] delRefmailInfo = new Integer[1];

    /**
     * 更新货架商品数量
     */
    Integer[] updatecrInfo = new Integer[1];

    /**
     * 用于保存校验失败的数据行信息
     */
    StringBuffer checkFailureRow = new StringBuffer();

    /**
     * 用于保存新音乐校验失败的数据行信息
     */
    StringBuffer checkFailureRowByNew = new StringBuffer();

    /**
     * 用于保存校验失败的数据行信息
     */
    StringBuffer checkFailureRowByLatest = new StringBuffer();

    /**
     * 用于保存新音乐校验失败的数据行信息
     */
    HashMap existsNewMusicSinger = new HashMap();
    /**
     * 用于保存新音乐校验失败的数据行信息
     */
    StringBuffer errorFailureRowByNew = new StringBuffer();
    StringBuffer msgInfo = new StringBuffer();
    Integer[] updateNewcrInfo = new Integer[1];

    String[] newFNameRegex = BaseMusicConfig.get("NewFullMusicNameRegex")
                                            .split(",");
    NewBaseMusicFtpProcessor newbp = new NewBaseMusicFtpProcessor();
    // 已存在的全量基地音乐id清单
    HashMap existsNewMusic = new HashMap();
    try
    { // 获取所有的已经存在的音乐
        existsNewMusic = BMusicDAO.getInstance()
                                  .getAllexistNewMusicID();
    }
    catch (DAOException e1)
    {
        logger.error("从数据库中获取所有基地 新 音乐ID时发生数据库异常！", e1);
        throw new BOException("从数据库中获取所有基地 新 音乐ID时发生数据库异常", e1);
    }
    msgInfo.append("<br>");
    msgInfo.append("基地音乐歌曲数据全量数据导入结果：");
    msgInfo.append("<br>");
    try
    {
        String newLocalFileNames[] = newbp.process(newFNameRegex);// ftp
        // 下载到本地
        this.importNewBaseMusicData(existsNewMusic,existsNewMusicSinger,
                                    newmusicmailInfo,
                                    newLocalFileNames,
                                    checkFailureRowByNew,
                                    errorFailureRowByNew);// 执行音乐清单导入
//        Iterator iterMap=existsNewMusic.entrySet().iterator();  
//        while(iterMap.hasNext()){                         
//            Map.Entry  strMap=(Map.Entry)iterMap.next();                          
//            if((new Integer(0)).equals(strMap.getValue()));  
//            iterMap.remove();                                               
//        }  

        if (newmusicmailInfo[0] != null)
        {
            msgInfo.append("共处理行数：" + newmusicmailInfo[0].intValue());
            msgInfo.append(";<br>成功新增："
                           + newmusicmailInfo[1].intValue());
            msgInfo.append(";<br>成功修改："
                           + newmusicmailInfo[2].intValue());
            msgInfo.append(";<br>成功删除："
                           + newmusicmailInfo[3].intValue());
            msgInfo.append(";<br>数据检查不合法："
                           + newmusicmailInfo[4].intValue());
            if (newmusicmailInfo[4].intValue() > 0)
            {
                msgInfo.append(";<br>数据检查出错具体行：")
                       .append(checkFailureRowByNew );
            }
            msgInfo.append(";<br>数据处理失败："
                           + newmusicmailInfo[5].intValue());
            if (newmusicmailInfo[5].intValue() > 0)
            {
                msgInfo.append(";<br>数据处理失败具体行：第")
                       .append(errorFailureRowByNew.substring(0, errorFailureRowByNew.length()-2))
                       .append("行<br>");
            }
            msgInfo.append("<br>");
        }

    }
    catch (BOException e)
    {
        // TODO Auto-generated catch block
        logger.error("基地音乐歌曲数据全量导入操作失败" + e);
        e.printStackTrace();
        msgInfo.append("基地音乐歌曲数据全量导入操作失败" + e);
        msgInfo.append("<br>");
    }
    // 4,下架全量过期基地音乐商品
    // this.delInvalBMusicRef(delNewRefmailInfo);
    BaseMusicBO.getInstance().delInvalNewBMusicRef(delRefmailInfo);
    if (delNewRefmailInfo != null && delNewRefmailInfo[0] != null)
    {
        msgInfo.append("<br>");
        msgInfo.append("下架过期基地 新 音乐商品数量为："
                       + delNewRefmailInfo[0].intValue());
        msgInfo.append("<br>");
    }
    // 5,更新货架商品数量
    // this.updateCategoryRefSum(updateNewcrInfo);
    BaseMusicBO.getInstance().updateAllNewCategoryRefSum(updatecrInfo);
    if (updateNewcrInfo != null && updateNewcrInfo[0] != null)
    {
        msgInfo.append("<br>");
        msgInfo.append("更新 新 音乐货架数量为：" + updateNewcrInfo[0].intValue());
        msgInfo.append("<br>");
    }
        logger.error("基地音乐歌曲数据全量导入：" + msgInfo);
        this.sendMail(msgInfo.toString(),
                      BaseMusicSynMailto,
                      synBaseMusicSubject);

    /** *******************新增导入新音乐数据到WAP门户 结束******************* */
}
/**
 * 发送邮件
 * 
 * @param mailContent,邮件内容
 */
private void sendMail(String mailContent, String[] mailTo, String subject)
{
    if (logger.isDebugEnabled())
    {
        logger.debug("sendMail(" + mailContent + ")");
    }
    // 得到邮件接收者数组
    // String[] mailTo = MailConfig.getInstance().getMailToArray();
    // String subject = "基地音乐数据导入";
    if (logger.isDebugEnabled())
    {
        logger.debug("mailTo Array is:" + Arrays.asList(mailTo));
        logger.debug("mail subject is:" + subject);
        logger.debug("mailContent is:" + mailContent);
    }
    Mail.sendMail(subject, mailContent, mailTo);
}
/**
 * 插入新 音乐清单
 * 
 * @param msgInfo
 * @param RefDataTime
 * @param nameList
 * @return
 * @throws BOException
 */
private void importNewBaseMusicData(HashMap existsMusic,
									HashMap  existsMusicSingers,
                                    Integer musicmailInfo[],
                                    String[] nameList,
                                    StringBuffer checkFailureRowByNew, StringBuffer errorFailureRowByNew)
                throws BOException
{
	BaseMusicBO.getInstance().importNewBaseFullMusic(existsMusic,existsMusicSingers,
                                                 musicmailInfo,nameList, checkFailureRowByNew, errorFailureRowByNew);
}
}
