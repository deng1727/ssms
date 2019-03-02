/*
 * 文件名：GoodsChanegHisBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.ponaadmin.web.util.PublicUtil;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class GoodsChanegHisBO
{
    /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(GoodsChanegHisBO.class);

    private static GoodsChanegHisBO bo = new GoodsChanegHisBO();
    
    private GoodsChanegHisBO()
    {
    }

    /**
     * 获取实例
     * @return 实例
     */
    public static GoodsChanegHisBO getInstance()
    {
        return bo;
    }
    
    /**
     * 用于加入紧急下架应用历史表集合
     * @param hisList
     * @param category
     * @param node
     */
    public void addDelHisToList(Category category, ReferenceNode node, String subType) throws BOException
    {
        GoodsChanegHis his = new GoodsChanegHis();
        his.setCid(category.getId());
        his.setType(category.getRelation());
        his.setGoodsId(node.getGoodsID());
        his.setSubType(subType);
        his.setAction(RepositoryConstants.SYN_ACTION_DEL);
        
        addDataToHis(his);
    }
    
    /**
     * 用于加入紧急上架应用历史表集合
     * @param hisList
     * @param category
     * @param conID
     */
    public void addAddHisToList(Category category, String conID, String subType) throws BOException
    {
        GoodsChanegHis his = new GoodsChanegHis();
        his.setCid(category.getId());
        his.setType(category.getRelation());
        his.setGoodsId(getGoodsid(category, conID));
        his.setSubType(subType);
        his.setAction(RepositoryConstants.SYN_ACTION_ADD);
        
        addDataToHis(his);
    }
    
    /**
     * 用于加入紧急上架应用历史表集合
     * @param category
     * @param conID
     */
    public void addAddHisToList(Category category, String conID) throws BOException
    {
        GContent content = (GContent)Repository.getInstance().getNode(conID,RepositoryConstants.TYPE_GCONTENT);
        GoodsChanegHis his = new GoodsChanegHis();
        his.setCid(category.getId());
        his.setType(category.getRelation());
        his.setGoodsId(getGoodsid(category, conID));
        his.setSubType(content.getSubType());
        his.setAction(RepositoryConstants.SYN_ACTION_ADD);
        
        addDataToHis(his);
    }
    
    /**
     * 用于根据主键id列表加入紧急下架应用历史表集合
     * @param list
     * @return
     */
    public List addDelHisByListId(List list) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addDelHisByListId() is starting ...");
        }
        
        List hisList = new ArrayList();
        try
        {
            for (Iterator iter = list.iterator(); iter.hasNext();)
            {
                String id = ( String ) iter.next();
                
                // 根据主键id，得到主键id所在的货架信息。
                hisList.addAll(GoodsChanegHisDAO.getInstance().addDelHisToList(id));
            }
        }
        catch (DAOException e)
        {
            throw new BOException("根据应用id，得到应用id所在的货架信息时发生数据加操作异常", e);
        }
        
        return hisList;
    }
    
    /**
     * 根据应用id，得到应用id所在的货架信息。统计添加被动下架历史信息存入历史表数据集合
     * @param contentId
     */
    public List addDelHisToList(String contentId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addAddHisToList() is starting ...");
        }
        List list;
        try
        {
            // 根据应用id，得到应用id所在的货架信息。
            list = GoodsChanegHisDAO.getInstance().addDelHisToList(contentId);
        }
        catch (DAOException e)
        {
            throw new BOException("根据应用id，得到应用id所在的货架信息时发生数据加操作异常", e);
        }

        return list;
    }
    
    /**
     * 用来返回当前数据上架的商品id
     * @param category
     * @param conID
     * @return
     */
    private String getGoodsid(Category category, String conID) throws BOException
    {
        String goodsid = "";
        
        if(conID.charAt(0)>='0'&&conID.charAt(0)<='9')//应用。
        {
            GContent content = (GContent)Repository.getInstance().getNode(conID,RepositoryConstants.TYPE_GCONTENT);
            goodsid = category.getCategoryID()
            + PublicUtil.lPad(content.getCompanyID(), 6)
            + PublicUtil.lPad(content.getProductID(), 12)
            + PublicUtil.lPad(content.getContentID(), 12);
        }
        else
        {
            goodsid = PublicUtil.rPad(category.getCategoryID() + "|" + conID + "|", 39, "0");
        }
        return goodsid;
    }
    
    /**
     * 添加紧急上线数据至数据库历史表
     * @param hisList
     */
    public void addDataToHis(List hisList) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addDataToHis() is starting ...");
        }
        
        try
        {
            GoodsChanegHisDAO.getInstance().addDataToHis(hisList);
        }
        catch (DAOException e)
        {
            throw new BOException("添加紧急上线数据至数据库历史表时发生数据加操作异常", e);
        }
    }
    
    /**
     * 添加紧急上线数据至数据库历史表
     * @param his
     */
    public void addDataToHis(GoodsChanegHis his) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addDataToHis() is starting ...");
        }
        
        try
        {
            GoodsChanegHisDAO.getInstance().addDataToHis(his);
        }
        catch (DAOException e)
        {
            throw new BOException("添加紧急上线数据至数据库历史表时发生数据加操作异常", e);
        }
    }
}
