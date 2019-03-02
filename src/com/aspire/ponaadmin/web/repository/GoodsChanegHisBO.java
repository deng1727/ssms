/*
 * �ļ�����GoodsChanegHisBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(GoodsChanegHisBO.class);

    private static GoodsChanegHisBO bo = new GoodsChanegHisBO();
    
    private GoodsChanegHisBO()
    {
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    public static GoodsChanegHisBO getInstance()
    {
        return bo;
    }
    
    /**
     * ���ڼ�������¼�Ӧ����ʷ����
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
     * ���ڼ�������ϼ�Ӧ����ʷ����
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
     * ���ڼ�������ϼ�Ӧ����ʷ����
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
     * ���ڸ�������id�б��������¼�Ӧ����ʷ����
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
                
                // ��������id���õ�����id���ڵĻ�����Ϣ��
                hisList.addAll(GoodsChanegHisDAO.getInstance().addDelHisToList(id));
            }
        }
        catch (DAOException e)
        {
            throw new BOException("����Ӧ��id���õ�Ӧ��id���ڵĻ�����Ϣʱ�������ݼӲ����쳣", e);
        }
        
        return hisList;
    }
    
    /**
     * ����Ӧ��id���õ�Ӧ��id���ڵĻ�����Ϣ��ͳ����ӱ����¼���ʷ��Ϣ������ʷ�����ݼ���
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
            // ����Ӧ��id���õ�Ӧ��id���ڵĻ�����Ϣ��
            list = GoodsChanegHisDAO.getInstance().addDelHisToList(contentId);
        }
        catch (DAOException e)
        {
            throw new BOException("����Ӧ��id���õ�Ӧ��id���ڵĻ�����Ϣʱ�������ݼӲ����쳣", e);
        }

        return list;
    }
    
    /**
     * �������ص�ǰ�����ϼܵ���Ʒid
     * @param category
     * @param conID
     * @return
     */
    private String getGoodsid(Category category, String conID) throws BOException
    {
        String goodsid = "";
        
        if(conID.charAt(0)>='0'&&conID.charAt(0)<='9')//Ӧ�á�
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
     * ��ӽ����������������ݿ���ʷ��
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
            throw new BOException("��ӽ����������������ݿ���ʷ��ʱ�������ݼӲ����쳣", e);
        }
    }
    
    /**
     * ��ӽ����������������ݿ���ʷ��
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
            throw new BOException("��ӽ����������������ݿ���ʷ��ʱ�������ݼӲ����쳣", e);
        }
    }
}
