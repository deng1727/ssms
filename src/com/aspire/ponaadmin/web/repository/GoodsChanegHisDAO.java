/*
 * �ļ�����GoodsChanegHisDAO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.ponaadmin.web.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class GoodsChanegHisDAO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(GoodsChanegHisDAO.class);

    /**
     * ���췽������singletonģʽ����
     */
    private GoodsChanegHisDAO()
    {
    }

    /**
     * singletonģʽ��ʵ��
     */
    private static GoodsChanegHisDAO dao = new GoodsChanegHisDAO();

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static final GoodsChanegHisDAO getInstance()
    {
        return dao;
    }

    /**
     * ������ӽ����������������ݿ���ʷ��
     * 
     * @param hisList
     * @throws DAOException
     */
    public void addDataToHis(List hisList) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addDataToHis() is starting ...");
        }

        // insert into T_GOODS_CHANGE_HIS s (goodsid, type, cid, Dataversion,
        // Action, Subtype, contentid, paht, catetype) values (?, ?, ?, sysdate,
        // ?, ?, ?, ?, ?)
        String sqlCode = "repository.GoodsChanegHisDAO.addDataToHis().save";

        String[] mutiSQL = new String[hisList.size()];
        Object paras[][] = new String[hisList.size()][8];

        for (int i = 0; i < hisList.size(); i++)
        {
            GoodsChanegHis temp = ( GoodsChanegHis ) hisList.get(i);

            mutiSQL[i] = sqlCode;
            paras[i][0] = temp.getGoodsId();
            paras[i][1] = temp.getType();
            paras[i][2] = temp.getCid();
            paras[i][3] = temp.getAction();
            paras[i][4] = temp.getSubType();
            paras[i][5] = temp.getContentid();
            paras[i][6] = temp.getPath();
            paras[i][7] = temp.getCateType();
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(mutiSQL, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("��������ݿ���ʷ����ʱ�����쳣:", e);
        }
    }

    /**
     * ������ӽ����������������ݿ���ʷ��
     * 
     * @param his
     * @throws DAOException
     */
    public void addDataToHis(GoodsChanegHis his) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addDataToHis() is starting ...");
        }

        // insert into T_GOODS_CHANGE_HIS (goodsid, type, cid, Dataversion,
        // Action, Subtype) values (?,?,?,sysdate,?,?)
        String sqlCode = "repository.GoodsChanegHisDAO.addDataToHis().save";
        Object[] paras = new Object[8];
        paras[0] = his.getGoodsId();
        paras[1] = his.getType();
        paras[2] = his.getCid();
        paras[3] = his.getAction();
        paras[4] = his.getSubType();
        paras[5] = "";
        paras[6] = "";
        paras[7] = "";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("��������ݿ���ʷ����ʱ�����쳣:", e);
        }
    }

    /**
     * ����Ӧ��id���õ�Ӧ��id���ڵĻ�����Ϣ
     * 
     * @param contentId
     */
    public List addDelHisToList(String contentId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addDataToHis() is starting ...");
        }

        // select t2.contentid, t1.goodsid, t3.relation, t3.id, t2.subtype,
        // t2.catename, b.path from t_r_reference t1, t_r_gcontent t2,
        // t_r_category t3, t_r_base b where t1.refNodeid = t2.id and t1.id =
        // b.id and t1.categoryid = t3.categoryid and t2.contentID = ?
        String sqlCode = "repository.GoodsChanegHisDAO.addDelHisToList().select";
        RowSet rs;
        List list = new ArrayList();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { contentId });

            while (rs.next())
            {
                GoodsChanegHis his = new GoodsChanegHis();
                his.setCid(rs.getString("id"));
                his.setType(rs.getString("relation"));
                his.setGoodsId(rs.getString("goodsid"));
                his.setSubType(rs.getString("subtype"));
                his.setContentid(rs.getString("contentid"));
                his.setCateType(rs.getString("catename"));
                his.setPath(rs.getString("path"));
                his.setAction(RepositoryConstants.SYN_ACTION_DEL);
                list.add(his);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("����Ӧ��id���õ�Ӧ��id���ڵĻ�����Ϣʱ�����쳣:", e);
        }

        return list;
    }

    /**
     * ��������id���õ�����id���ڵĻ�����Ϣ��
     * 
     * @param contentId
     */
    public List addDelHisById(String id) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addDelHisById() is starting ...");
        }

        // select t2.contentid, t1.goodsid, t3.relation, t3.id,
        // t2.subtype from t_r_reference t1, t_r_gcontent t2, t_r_category t3
        // where t1.refNodeid = t2.id and t1.categoryid = t3.categoryid and
        // t1.id=?
        String sqlCode = "repository.GoodsChanegHisDAO.addDelHisById().select";
        RowSet rs;
        List list = new ArrayList();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] { id });

            while (rs.next())
            {
                GoodsChanegHis his = new GoodsChanegHis();
                his.setCid(rs.getString("id"));
                his.setType(rs.getString("relation"));
                his.setGoodsId(rs.getString("goodsid"));
                his.setSubType(rs.getString("subtype"));
                his.setAction(RepositoryConstants.SYN_ACTION_DEL);
                list.add(his);
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("��������id���õ�����id���ڵĻ�����Ϣʱ�����쳣:", e);
        }
        return list;
    }
}
