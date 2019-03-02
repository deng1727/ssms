/*
 * 
 */

package com.aspire.ponaadmin.web.category.intervenor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.syncData.bo.DataSyncBO;
import com.aspire.ponaadmin.web.category.intervenor.category.IntervenorCategoryBO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.GoodsChanegHisBO;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.ReferenceTools;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author x_wangml
 * 
 */
public class IntervenorTools
{

    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(IntervenorTools.class);

    private static IntervenorTools instance = new IntervenorTools();

    private TaskRunner updateTaskRunner;

    private StringBuffer errorString = new StringBuffer();
    
    private int errorNum = 0;
    
    private IntervenorTools()
    {

    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static IntervenorTools getInstance()
    {

        return instance;
    }

    /**
     * 删除失效内容
     * 
     * @throws BOException
     */
    private void delInvalidationContent() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("删除失效内容 is start...");
        }

        try
        {
            IntervenorBO.getInstance().delInvalidationContent();
        }
        catch (BOException e)
        {
            logger.error(e);

            throw new BOException("删除失效内容时发生数据库异常！");
        }
    }

    /**
     * 释放过期容器
     * 
     * @throws BOException
     */
    private void overdueIntervenor() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("释放过期容器 is start...");
        }

        try
        {
            IntervenorBO.getInstance().overdueIntervenor();
        }
        catch (BOException e)
        {
            logger.error(e);

            throw new BOException("释放过期容器时发生数据库异常！");
        }
    }

    /**
     * 得到所有被人工干预的榜单
     * 
     * @return
     * @throws BOException
     */
    private List getIntervenorCategory() throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("查询所有被人工干预的榜单 is start...");
        }

        try
        {
            return IntervenorCategoryBO.getInstance().getIntervenorCategory();
        }
        catch (BOException e)
        {
            logger.error(e);

            throw new BOException("得到所有被人工干预的榜单时发生数据库异常！");
        }
    }

    /**
     * 用于查询当前榜单的内存排序情况
     * 
     * @param categoryId 榜单id
     * @return
     * @throws BOException
     */
    private List getGoodsByCategory(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("查询当前榜单的内存排序情况 is start...");
        }

        Category childCategory = ( Category ) Repository.getInstance()
                                                        .getNode(categoryId,
                                                                 RepositoryConstants.TYPE_CATEGORY);

        Taxis taxis = new Taxis();
        taxis.getParams()
             .add(new TaxisParam("sortID", RepositoryConstants.ORDER_TYPE_DESC));
        List goodsList = childCategory.searchNodes(RepositoryConstants.TYPE_REFERENCE,
                                                   null,
                                                   taxis);

        List resultList = new ArrayList();
        for (int i = 0; i < goodsList.size(); i++)
        {
            ReferenceNode refnode = ( ReferenceNode ) goodsList.get(i);
            GoodChangedVO vo = new GoodChangedVO();
            vo.setId(refnode.getId());
            vo.setRefNodeId(refnode.getRefNodeID());
            vo.setSortId(i);// 根据顺序确定排序值，主要为了以后计算 variation的值
            vo.setStatus(GoodChangedVO.Status_Existed);
            resultList.add(vo);
        }
        return resultList;
    }

    /**
     * 用于查询榜单对应的容器的内容情况
     * 
     * @param categoryId 榜单id
     * @return
     * @throws BOException
     */
    private List getIntervenorData(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("查询榜单对应的容器的内容情况 is start...");
        }

        try
        {
            return IntervenorBO.getInstance().getIntervenorData(categoryId);
        }
        catch (BOException e)
        {
            logger.error(e);

            throw new BOException("查询榜单对应的容器的内容情况时发生数据库异常！");
        }
    }

    /**
     * 更新指定的榜单排序为返回列表
     * 
     * @param retList 干预后列表
     * @param categoryId 榜单id
     * @param isSyn 是否是紧急上线应用
     */
    private void editCategoryData(List retList, String categoryId, String isSyn)
                    throws BOException
    {

        int sortid = 1000;
        Category cate = ( Category ) Repository.getInstance()
                                               .getNode(categoryId,
                                                        RepositoryConstants.TYPE_CATEGORY);

        for (int i = 0; i < retList.size(); i++)
        {
            GoodChangedVO vo = ( GoodChangedVO ) retList.get(i);

            try
            {
                if (vo.getStatus() == GoodChangedVO.Status_ADD)
                {
                    CategoryTools.addGood(cate,
                                          vo.getRefNodeId(),
                                          sortid--,
                                          RepositoryConstants.VARIATION_NEW,
                                          true,null);
                    
                    // 如果是紧急上线应用
                    if(RepositoryConstants.SYN_ACTION_ADD.equals(isSyn))
                    {
                        // 加入紧急上线应用表
                        GoodsChanegHisBO.getInstance().addAddHisToList(cate, vo.getRefNodeId());
                    }
                }
                else if (vo.getStatus() == GoodChangedVO.Status_Existed)
                {
                    ReferenceTools.getInstance()
                                  .updateSortid(vo.getId(),
                                                sortid--,
                                                vo.getVariation(),null);
                }
                else if (vo.getStatus() == GoodChangedVO.Status_DEL)
                {
                    // 目前不会存在删除业务。

                }
            }
            catch (BOException e)
            {
                logger.error("容器干预榜单出错 categoryId=" + cate.getId(), e);
            }

        }
    }

    /**
     * 用于比对榜单内容排序与容器内容结合
     * 
     * @param categoryDate 榜单内容信息
     * @param intervenorDate 容器内容信息
     * @return
     */
    public List intervenorContrast(List goodsList, List intervenorList)
    {
        Map googdsBackup = new HashMap();
        int maxIntervenorSize = 0;// 保存容器中定义在榜单中最大位置
        // 备份一下商品的信息。为了以后去掉榜单中在容器中存在的商品，以及统计variation的值
        for (int i = 0; i < goodsList.size(); i++)
        {
            GoodChangedVO changedVO = ( GoodChangedVO ) goodsList.get(i);
            googdsBackup.put(changedVO.getRefNodeId(), changedVO);
        }
        Map tempMap = new HashMap();// 去重容器中相同对象，从优先级从高到底过滤
        for (int i = 0; i < intervenorList.size(); i++)
        {
            IntervenorVO vo = ( IntervenorVO ) intervenorList.get(i);

            int startid = vo.getStartSortId();

            if (IntervenorVO.TOP == startid || IntervenorVO.END == startid)// 如果是置顶，默认从榜单第一个位置插入
            {
                startid = 1;
            }

            if (startid + vo.getContentList().size() > maxIntervenorSize)// 预计容器的大小
            {
                maxIntervenorSize = startid + vo.getContentList().size();
            }

            List contentList = vo.getContentList();
            List turnedContentList = new ArrayList();
            // 把id转为GoodChangedVO的格式
            for (int j = 0; j < contentList.size(); j++)
            {
                String id = ( String ) contentList.get(j);
                Object value = tempMap.get(id);
                if (value == null)
                {
                    tempMap.put(id, "");
                    Object isExistedgoosid = googdsBackup.get(id);
                    GoodChangedVO goodvo = null;
                    if (isExistedgoosid == null)// 判断当前id是否存在于当前的榜单中。主要是为了后面的上架的需要
                    {
                        goodvo = new GoodChangedVO();
                        goodvo.setRefNodeId(id);
                        goodvo.setStatus(GoodChangedVO.Status_ADD);
                    }
                    else
                    {
                        goodvo = ( GoodChangedVO ) isExistedgoosid;
                    }
                    turnedContentList.add(goodvo);
                }
            }
            vo.setContentList(turnedContentList);//
        }

        // 去掉榜单中在容器中存在的商品
        for (int i = 0; i < goodsList.size(); i++)
        {
            GoodChangedVO changedVO = ( GoodChangedVO ) goodsList.get(i);
            // googdsBackup.put(changedVO.getRefNodeId(),changedVO);
            if (tempMap.containsKey(changedVO.getRefNodeId()))
            {
                goodsList.remove(i);
                i--;
            }
        }
        List finalList = new ArrayList(maxIntervenorSize);// 初始化列表
        for (int i = 0; i < maxIntervenorSize; i++)
        {
            finalList.add("");
        }
        // 最终榜单生成：先放置容器内的商品，低优先级先放，高优先级后排
        // 高优先接有可能打乱低优先级容器的顺序
        // List topList=new ArrayList();
        List endList = new ArrayList();

        int start = 0;// 定义容器插入的起始位置
        for (int i = intervenorList.size() - 1; i >= 0; i--)
        {
            IntervenorVO vo = ( IntervenorVO ) intervenorList.get(i);
            start = vo.getStartSortId() - 1;
            List contentList = vo.getContentList();
            if (vo.getStartSortId() == IntervenorVO.TOP)
            {
                // topList.addAll(contentList);
                start = 0;// 从第一位开始
            }
            else if (vo.getStartSortId() == IntervenorVO.END)
            {
                endList.addAll(contentList);
                continue;
            }
            for (int j = 0; j < contentList.size(); j++)// 上架容器的中商品
            {
                Object temp = finalList.get(start);
                if (temp.equals(""))
                {
                    finalList.set(start, contentList.get(j));
                    start++;//
                }
                else
                // 存在就新增吧
                {
                    finalList.addAll(start,
                                     contentList.subList(j, contentList.size()));
                    break;//
                }
            }
        }
        // 把榜单的中商品插入到容器商品排序后空隙中。
        start = 0;
        int finallListSize = finalList.size();
        for (int i = 0; i < goodsList.size(); i++)
        {
            if (start < finallListSize)
            {
                for (int j = start; j < finallListSize; j++)
                {
                    Object temp = finalList.get(j);
                    if (temp.equals(""))
                    {
                        finalList.set(j, goodsList.get(i));
                        start = j + 1;// 下一次直接从j+1开始
                        break;
                    }
                }
            }
            else
            {
                // 超出所有容器商品的位置后，直接放入即可
                finalList.addAll(goodsList.subList(i, goodsList.size()));
                break;
            }

        }
        // 去掉最终列表中可能存在空元素。
        for (int i = 0; i < finalList.size(); i++)
        {
            Object obj = finalList.get(i);
            if (obj instanceof String)
            {
                finalList.remove(i);
                i--;
            }
        }
        // 放入置地的元素
        finalList.addAll(endList);
        // 设置变更值。
        for (int i = 0; i < finalList.size(); i++)
        {
            GoodChangedVO vo = ( GoodChangedVO ) finalList.get(i);
            GoodChangedVO oldvo = ( GoodChangedVO ) googdsBackup.get(vo.getRefNodeId());
            if (oldvo == null)// 表示新增
            {
                vo.setVariation(RepositoryConstants.VARIATION_NEW);
            }
            else
            {
                vo.setVariation(oldvo.getSortId() - i);
            }
        }
        return finalList;
    }

    /**
     * 执行单个榜单容器干预
     * 
     * @param categoryId 榜单id
     * @param isSyn 是否是紧急上线应用
     * @return
     * @throws BOException
     * @throws BOException
     */
    public void intervenorCategory(String categoryId, String isSyn) throws BOException
    {

        // 用于存储原榜单内容排名信息
        List goodsList = null;

        try
        {
            goodsList = this.getGoodsByCategory(categoryId);
        }
        catch (BOException e)
        {
            synchronized (this)
            {
                errorString.append(" 货架id=" + categoryId + "的人工干预执行失败<br>");
                errorNum++;
            }
            throw new BOException("查看存储原榜单内容排名时发生数据库异常！", e);
        }

        // 用于存储容器中内容排名信息
        List intervenorList;

        try
        {
            intervenorList = this.getIntervenorData(categoryId);
        }
        catch (BOException e)
        {
            synchronized (this)
            {
                errorString.append(" 货架id=" + categoryId + "的人工干预执行失败<br>");
                errorNum++;
            }
            throw new BOException("查询榜单对应的容器的内容情况时发生数据库异常！", e);
        }

        // 关联容器干预之后的内容排序情况
        List retList = this.intervenorContrast(goodsList, intervenorList);

        // 变更干预后数据库中榜单内容排序信息
        try
        {
            this.editCategoryData(retList, categoryId, isSyn);
        }
        catch (BOException e)
        {
            synchronized (this)
            {
                errorString.append(" 货架id=" + categoryId + "的人工干预执行失败");
                errorNum++;
            }
            throw new BOException("变更干预后数据库中榜单内容排序时发生异常！", e);
        }
    }
    
    /**
     * 执行全部榜单容器干预
     * 
     * @throws BOException
     */
    public void intervenorCategoryAll()
    {

        Date startDate = new Date();
        errorString = new StringBuffer();
        errorNum = 0;

        logger.info("人工干预开始：startTime:"
                    + PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));

        // 先调用释放过期容器
        try
        {
            this.overdueIntervenor();
        }
        catch (BOException e)
        {
            return;
        }

        // 先调用释放过期容器
        //try
        //{
        //    this.delInvalidationContent();
        //}
        //catch (BOException e)
        //{
        //   return;
        //}

        // 得到所有被人工干预的榜单
        List categoryList;

        try
        {
            categoryList = getIntervenorCategory();
        }
        catch (BOException e1)
        {
            return;
        }

        // 人工干预最大同步多线程数量
        updateTaskRunner = new TaskRunner(IntervenorConfig.INTERVENORMAXNUM, 0);

        // 分别执行榜单
        for (Iterator iter = categoryList.iterator(); iter.hasNext();)
        {
            String categoryId = ( String ) iter.next();

            // 执行单个榜单干预 异步任务
            ReflectedTask task = new ReflectedTask(this,
                                                   "intervenorCategory",
                                                   new Object[] { categoryId, RepositoryConstants.SYN_HIS_NO },
                                                   new Class[] { String.class, String.class });
            // 将任务加到运行器中
            updateTaskRunner.addTask(task);
        }

        updateTaskRunner.waitToFinished();
        updateTaskRunner.stop();

        logger.info("人工干预结束，共干预" + categoryList.size() + "个货架。");

        Date endDate = new Date();
        StringBuffer sb = new StringBuffer();
        sb.append("开始时间：");
        sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append(",结束时间：");
        sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
        sb.append("。<p>人工干预结束，共干预" + categoryList.size() + "个货架。<br>");
        sb.append("。<p>失败" + errorNum + "个货架。<br>");
        sb.append("。<p>成功" + (categoryList.size() - errorNum) + "个货架。<br>");
        if (!"".equals(errorString.toString()))
        {
            sb.append("。<p>处理结果：其中有" + errorString.toString() + "<br>");
        }
        else
        {
            sb.append("。<p>处理结果：成功<br>");
        }
        Mail.sendMail("人工干预成功", sb.toString(), MailConfig.getInstance()
                                                         .getMailToArray());
        //发送结果短信
        DataSyncBO.getInstance().sendPhoneMsg("人工干预",this.getPhoneMsg(categoryList));
    }
    /**
     * 发送短息通知信息
     * @param categoryList 总的干预量
     * @return 短息信息
     */
    private String getPhoneMsg( List categoryList){
        StringBuffer sb = new StringBuffer();
        sb.append("人工干预结束，共干预" );
        sb.append(categoryList.size() );
        sb.append("个货架。"); 
        sb.append("。失败" );
        sb.append(errorNum); 
        sb.append("个货架。");
        sb.append("。成功" );
        sb.append((categoryList.size() - errorNum)); 
        sb.append("个货架。");
        return sb.toString();
    }
}
