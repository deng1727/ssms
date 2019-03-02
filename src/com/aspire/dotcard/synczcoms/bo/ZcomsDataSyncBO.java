/**
 * SSMS
 * com.aspire.dotcard.synczcom.bo ZcomDataSyncBO.java
 * Apr 8, 2010
 * @author tungke
 * @version 1.0
 *
 */

package com.aspire.dotcard.synczcoms.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncData.util.SyncDataConstant;
import com.aspire.dotcard.synczcom.vo.ZcomContentTmp;
import com.aspire.dotcard.synczcoms.dao.ZcomsDataSyncDAO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 * 
 */
public class ZcomsDataSyncBO
{

    /**
     * 同步开始时间
     */
    private Date startDate;

    /**
     * 同步结束时间
     */
    private Date endDate;

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(ZcomsDataSyncBO.class);

    private static ZcomsDataSyncBO instance = new ZcomsDataSyncBO();

    private ZcomsDataSyncBO()
    {
    }

    /**
     * 得到单例模式
     * 
     */
    public static ZcomsDataSyncBO getInstance()
    {
        return instance;
    }

    /**
     * 手动同步
     * 
     * @param type
     */
    public void syncZcomCon(String type)
    {
        // 初始化zcomContent视图转表
        try
        {
            // 初始化适配关系表
            ZcomsDataSyncDAO.getInstance().syncVCmDeviceResource();

            if (type != null && !type.equals(""))
            {
                if (type.equals("0"))
                {
                    // 增量同步
                    this.syncZcomConAdd();
                }
                else if (type.equals("1"))
                {
                    // 全量同步
                    this.syncZcomConFull();
                }
                else
                {
                    // 同步类型不对
                    logger.debug("同步类型不对" + type);
                }
            }
            else
            {
                // 同步类型不对
                logger.debug("同步类型数据不合法" + type);
            }
        }
        catch (BOException e)
        {
            e.printStackTrace();

            logger.error("本次手动同步内容失败。", e);
            // 如果异常就调用邮件发送接口发给相关人员
            this.sendMail("本次手动同步Zcom内容失败，请联系管理员！",
                          SyncDataConstant.CONTENT_TYPE);
            
        }
    }

    /**
     * 全量同步
     * 
     */
    public void syncZcomConFull()
    {
        try
        {
            startDate = new Date();

            // 得到系统时间；
            long sysTime = System.currentTimeMillis();
            // ZcomDataSyncDAO.getInstance().updateZcomContent(sysTime,false);
            // 初始化zcomContent视图转表
            ZcomsDataSyncDAO.getInstance().initZcomsContentViewToTable();
            // 初始化临时表 --true 全量同步
            ZcomsDataSyncDAO.getInstance().addZcomsContentTmp(sysTime, true);
            // 获取zcom分类信息
            Hashtable parentmap = ZcomsDataSyncDAO.getInstance()
                                                  .getZcomsParentId();
            // 获取临时表信息
            List list = ZcomsDataSyncDAO.getInstance().getZcomsSyncContentTmp();
            // 开始处理
            List[] mailInfo = this.dealZcomsSyncContent(list, parentmap);

            // 处理结束，记录要删除的
            List dellist = ZcomsDataSyncDAO.getInstance()
                                           .getDelCmsNotExistZcomsContent();
            mailInfo[2].addAll(dellist);
            // 删除cms中不存在的
            int delcount = ZcomsDataSyncDAO.getInstance()
                                           .delCmsNotExistZcomsContent();
            logger.error("删除了" + delcount + "条，PPMS里不存在的");
            // 将本次系统执行内容同步时间插入到表t_lastsynctime_zcom
            ZcomsDataSyncDAO.getInstance().insertSysTime(sysTime);
            endDate = new Date();
            listToMail(mailInfo);
        }
        catch (Exception e)
        {
            logger.error("本次全量同步内容失败。", e);
            // 如果异常就调用邮件发送接口发给相关人员
            this.sendMail("本次全量同步内容失败，请联系管理员！", SyncDataConstant.CONTENT_TYPE);
        }
        finally
        {
            // 由于是单例模式，故在每次同步后，都必须将缓存清掉
            // ttMap = new HashMap();
            // tagMap = new HashMap();
        }
    }

    /**
     * 增量同步
     * 
     */
    public void syncZcomConAdd()
    {

        try
        {
            startDate = new Date();
            // 得到系统时间；
            long sysTime = System.currentTimeMillis();
            // ZcomDataSyncDAO.getInstance().updateZcomContent(sysTime,false);
            // 初始化ZcomContent视图转表
            ZcomsDataSyncDAO.getInstance().initZcomsContentViewToTable();
            // 初始化临时表 --false 增量同步
            ZcomsDataSyncDAO.getInstance().addZcomsContentTmp(sysTime, false);
            // 获取zcom分类信息
            Hashtable parentmap = ZcomsDataSyncDAO.getInstance()
                                                  .getZcomsParentId();
            // 获取临时表信息
            List list = ZcomsDataSyncDAO.getInstance().getZcomsSyncContentTmp();
            // 开始处理
            List[] mailInfo = this.dealZcomsSyncContent(list, parentmap);

            // 处理结束，记录要删除的
            List dellist = ZcomsDataSyncDAO.getInstance()
                                           .getDelCmsNotExistZcomsContent();
            mailInfo[2].addAll(dellist);
            // 删除cms中不存在的
            int delcount = ZcomsDataSyncDAO.getInstance()
                                           .delCmsNotExistZcomsContent();
            logger.error("删除了" + delcount + "条，PPMS里不存在的");
            // 将本次系统执行内容同步时间插入到表t_lastsynctime_zcom
            ZcomsDataSyncDAO.getInstance().insertSysTime(sysTime);
            endDate = new Date();
            listToMail(mailInfo);

        }
        catch (Exception e)
        {
            logger.error("本次增量同步内容失败。", e);
            // 如果异常就调用邮件发送接口发给相关人员
            this.sendMail("本次增量同步内容失败，请联系管理员！", SyncDataConstant.CONTENT_TYPE);
        }
        finally
        {
            // 由于是单例模式，故在每次同步后，都必须将缓存清掉
            // ttMap = new HashMap();
            // tagMap = new HashMap();
        }
    }

    /**
     * 处理将内容临时表中的数据。
     * 
     * @param list 全部信息。
     * @param parentmap 《id，name》
     * @return mailInfo String[] mail发送信息 mail发送信息 mail[0]为更新的应用信息 mail[1]上线应用信息
     *         mail[2]下线应用信息 mail[3]表示出错的信息
     */
    private List[] dealZcomsSyncContent(List list, Hashtable parentmap)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("dealSyncContent()");
        }
        // 遍历列表
        int size = list.size();

        // mail发送信息 mail[0]为更新的应用信息 mail[1]上线应用信息 mail[2]下线应用信息 mail[3]表示出错的信息
        List[] mailInfoList = new List[4];
        mailInfoList[0] = new ArrayList();
        mailInfoList[1] = new ArrayList();
        mailInfoList[2] = new ArrayList();
        mailInfoList[3] = new ArrayList();

        Hashtable tempdelht = new Hashtable();
        Hashtable tempaddht = new Hashtable();
        Hashtable tempupdateht = new Hashtable();
        // 用于记录应用处理的结果
        StringBuffer record = null;
        for (int i = 0; i < size; i++)
        {
            TransactionDB tdb = null;
            // 得到ContentTmp对象
            ZcomContentTmp tmp = ( ZcomContentTmp ) list.get(i);
            try
            {
                // 进行事务调用
                tdb = TransactionDB.getTransactionInstance();
                ZcomsDataSyncDAO dao = ZcomsDataSyncDAO.getTransactionInstance(tdb);
                record = new StringBuffer(tmp.getContentId());
                record.append(" | ");
                record.append(tmp.getLupdDate());
                record.append(" | ");

                int result = dao.insertIntoZcomPPs(tmp, parentmap);
                if (result > 0)
                {
                    if (result == 1)
                    {
                        // mailInfoList[1].add(tmp);//新增
                        tempaddht.put(tmp.getContentId(), tmp);
                    }
                    else if (result == 2)
                    {
                        // mailInfoList[0].add(tmp);//修改
                        tempupdateht.put(tmp.getContentId(), tmp);
                    }
                    // 调用DataSyncDAO的delSynccontetTmp方法删除t_syncContent_tmp表中的这笔记录
                    dao.delZcomsContentTemp(tmp.getContentId());
                }
                else
                {
                    // mailInfoList[3].add(tmp);//出错的
                    tempdelht.put(tmp.getContentId(), tmp);
                }

                // 提交事务
                tdb.commit();

            }
            catch (Exception e)
            {
                // 回滚
                tdb.rollback();
                logger.error(e);
                // 记录插入出错的ContentTmp对象
                // mailInfoList[3].add(tmp);
                tmp.setOptype("异常：" + e);
                tempdelht.put(tmp.getContentId(), tmp);
                record.append('0');
            }
            finally
            {
                if (tdb != null)
                {
                    tdb.close();
                }
            }
        }
        // 放到hashtable里根据contentid去重
        for (Iterator itr = tempdelht.keySet().iterator(); itr.hasNext();)
        {
            String key = ( String ) itr.next();
            mailInfoList[3].add(( ZcomContentTmp ) tempdelht.get(key));// 出错的
        }
        // 放到hashtable里根据contentid去重
        for (Iterator itr = tempaddht.keySet().iterator(); itr.hasNext();)
        {
            String key = ( String ) itr.next();
            mailInfoList[1].add(( ZcomContentTmp ) tempaddht.get(key));// 出错的
        }
        // 放到hashtable里根据contentid去重
        for (Iterator itr = tempupdateht.keySet().iterator(); itr.hasNext();)
        {
            String key = ( String ) itr.next();
            mailInfoList[0].add(( ZcomContentTmp ) tempupdateht.get(key));// 出错的
        }

        return mailInfoList;
    }

    /**
     * 根据同步的信息数组mailInfo，发送mail
     * 
     * @param size 总共同步的数据的总数
     * @param mailInfo 同步数据的信息数组 mailInfo[0]为下架内容数据，mailInfo[1]为错误内容数据
     * @author biran
     */
    private void listToMail(List[] mailInfo)
    {
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
            subject = "ZCOM "
                      + MailConfig.getInstance().getSyncServiceSubject();
        }
        else
        {
            subject = "ZCOM "
                      + MailConfig.getInstance().getSyncContentSubject();
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("mailTo Array is:" + Arrays.asList(mailTo));
            logger.debug("mail subject is:" + subject);
            logger.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailTo);
    }
    

    

    
    
}
