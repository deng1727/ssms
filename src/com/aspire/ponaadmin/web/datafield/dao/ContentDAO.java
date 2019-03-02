
package com.aspire.ponaadmin.web.datafield.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.datafield.vo.ContentVO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;

/**
 * <p>
 * ��ѯ��Ŀ�������б��DAO��
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ContentDAO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(ContentDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static ContentDAO instance = new ContentDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private ContentDAO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static final ContentDAO getInstance()
    {

        return instance;
    }

    /**
     * �������ơ��ṩ�̵��ֶβ�ѯ��ѯӦ�á�
     * 
     * @param page
     * @param name ����
     * @param spName �ṩ��
     * @param icpServId ҵ�����
     * @param icpCode ��ҵ����
     * @param contentID ����ID
     * @param provider ��������
     * @param cateName ҵ�����ݷ���
     * @param subtype mm����ҵ��
     * @throws DAOException
     */
    public void queryContentList(PageResult page, String name, String spName,
                                 String icpServId, String icpCode,
                                 String contentID, String provider,
                                 String cateName, String subtype)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryContentList(" + name + "," + spName + ","
                         + icpServId + "," + icpCode + "," + contentID + ","
                         + provider + "," + cateName + "," + subtype + ")");
        }
        if ("O".equals(provider))
        {
            if ("1".equals(cateName))
            {
                cateName = "���";
            }
            if ("2".equals(cateName))
            {
                cateName = "��Ϸ";
            }
            if ("3".equals(cateName))
            {
                cateName = "����";
            }
        }

        String sqlCode = "datafield.ContentDAO.queryContentList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //����������sql�Ͳ���
            
            paras.add(provider);
            
            if (!"".equals(name))
            {
                //sql += " and name like'%" + name + "%'";
            	sqlBuffer.append(" and name like  ? ");
            	paras.add("%"+SQLUtil.escape(name)+"%");
            }
            if (!"".equals(spName))
            {
                //sql += " and spName like'%" + spName + "%'";
            	sqlBuffer.append(" and spName like ? ");
            	paras.add("%"+SQLUtil.escape(spName)+"%");
            }
            if (!"".equals(icpServId))
            {
                //sql += " and icpServId ='" + icpServId + "'";
            	sqlBuffer.append(" and icpServId = ? ");
            	paras.add(icpServId);
            }
            if (!"".equals(icpCode))
            {
                //sql += " and icpCode ='" + icpCode + "'";
            	sqlBuffer.append(" and icpCode = ? ");
            	paras.add(icpCode);
            }
            if (!"".equals(contentID))
            {
                //sql += " and contentID ='" + contentID + "'";
            	sqlBuffer.append(" and contentID =? ");
            	paras.add(contentID);
            }
            if ("O".equals(provider))
            {
                //sql += " and subtype ='" + subtype + "'";
            	sqlBuffer.append(" and subtype =? ");
            	paras.add(subtype);
                if (!"".equals(cateName))
                {
                    //sql += " and cateName ='" + cateName + "'";
                	sqlBuffer.append(" and cateName =? ");
                	paras.add(cateName);
                	
                }
            }
            // sql += " order by name";

           //page.excute(sql, new Object[] { provider }, new ContentVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new ContentVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("queryContentList is error", e);
        }
    }

    /**
     * ����contentID��ѯ
     * 
     * @param contentID
     * @return ContentVO
     * @throws DAOException
     */
    public ContentVO getContentByID(String id) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getContentByID(" + id + ")");
        }
        String sqlCode = "datafield.ContentDAO.getContentByID().SELECT";
        Object[] paras = { id };
        ResultSet rs = null;
        ContentVO vo = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs.next())
            {
                vo = new ContentVO();
                vo.setId(rs.getString("id"));
                vo.setContentID(rs.getString("contentID"));
                vo.setName(rs.getString("name"));
                vo.setCateName(rs.getString("cateName"));
                vo.setSpName(rs.getString("spName"));
                vo.setIcpServId(rs.getString("icpServId"));
                vo.setIcpCode(rs.getString("icpCode"));
                vo.setServattr(rs.getString("servattr"));
                vo.setContenttag(rs.getString("contenttag"));
                vo.setKeywords(rs.getString("keywords"));
                vo.setMarketdate(rs.getString("marketdate"));
                vo.setSubType(rs.getString("subType"));

            }
        }
        catch (SQLException e)
        {
            throw new DAOException("getContentByID error", e);
        }
        finally
        {
            DB.close(rs);
        }
        return vo;
    }

    /**
     * ����contentID��ѯӦ�����Լ���
     * 
     * @param contentID
     * @return
     * @throws DAOException
     */
    public List getResourceList(String contentID) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getResourceList(" + contentID + ")");
        }
        String sqlCode = "datafield.ContentDAO.getResourceList().SELECT";
        Object[] paras = { contentID };
        ResourceVO vo = null;
        ResultSet rs = null;

        List list = new ArrayList();
        // �������,��ÿ����¼������ResourceVO�ĸ���������
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while (rs.next())
            {
                vo = new ResourceVO();
                vo.setKeyid(rs.getString("keyid"));
                vo.setKeyname(rs.getString("keyname"));
                vo.setKeydesc(rs.getString("keydesc"));
                vo.setTid(rs.getString("tid"));
                vo.setValue(rs.getString("value"));
                vo.setLupdate(rs.getString("lupdate"));
                list.add(vo);
            }

        }
        catch (SQLException e)
        {
            throw new DAOException("getResourceList error", e);
        }
        finally
        {
            DB.close(rs);

        }
        return list;

    }

    /**
     * �����ݿ��¼����ȡ���ݲ���װ��һ���¹����ContentVO����
     * 
     * @param vo ContentVO
     * @param rs ���ݿ��¼��
     */
    public final void getContentVOFromRS(ContentVO vo, ResultSet rs)
                    throws SQLException
    {

        vo.setId(rs.getString("id"));
        vo.setName(rs.getString("name"));
        vo.setCateName(rs.getString("cateName"));
        vo.setSpName(rs.getString("spName"));
        vo.setKeywords(rs.getString("keywords"));
        vo.setMarketdate(rs.getString("marketdate"));
        vo.setSubType(rs.getString("subType"));
    }
    
    /**
     * ���ڷ���������չ�ֶ��б�
     * 
     * @return
     * @throws BOException
     */
    public List queryContentKeyBaseList(String tid) throws DAOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("queryContentKeyBaseList( ) is starting ...");
		}
		String sqlCode = null;
		ResultSet rs = null;
		List list = new ArrayList();
		boolean insert = true;
		try
		{
			if (tid != null && !tid.equals(""))
			{
				sqlCode = "datafield.ContentDAO.queryContentKeyBaseList()res.SELECT";
				// datafield.ContentDAO.queryContentKeyBaseList()res.SELECT=select
				// * from t_key_base b, (select * from t_key_resource r where
				// r.tid = ?) y where b.keytable = 't_r_gcontent' and
				// b.keyid = y.keyid(+)
				Object[] paras = { tid };
				rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
				insert = false;
			}
			else
			{
				sqlCode = "datafield.ContentDAO.queryContentKeyBaseList().SELECT";
				// datafield.ContentDAO.queryContentKeyBaseList().SELECT=select
				// * from t_key_base b where b.keytable = 't_r_gcontent'
				rs = DB.getInstance().queryBySQLCode(sqlCode, null);
				insert = true;
			}
			while (rs.next())
			{
				ResourceVO vo = new ResourceVO();
				fromContentKeyBaseVOByRs(vo, rs, insert);
				list.add(vo);
			}
		} catch (DAOException e)
		{
			throw new DAOException("��ȡMMӦ����չ��Ϣ����Ϣ��ѯ����DAOException�쳣:", e);
		} catch (SQLException e)
		{
			throw new DAOException("��ȡMMӦ����չ��Ϣ����Ϣ��ѯ����SQLException�쳣:", e);
		} finally
		{
			DB.close(rs);
		}

		return list;
	}
    
    public boolean validateContent(String tid)
    {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		try {
			String sqlCode = "datafield.ContentDAO.validateContent";
			rs = DB.getInstance().queryBySQLCode(sqlCode,new String[] {tid});
			return rs.next();
		} catch (Exception ex) {
			//throw new DAOException("getNewFullDevice error!!", ex);
			logger.error("validateContent error!!", ex);
		} finally {
			DB.close(rs);
		}
		return false;
	}
    
    /**
     * Ϊ�������Ը�ֵ
     * 
     * @param vo
     * @param rs
     * @throws SQLException
     */
    private void fromContentKeyBaseVOByRs(ResourceVO vo, ResultSet rs,boolean insert)
                    throws SQLException
    {
        vo.setKeyid(rs.getString("keyid"));
        vo.setKeyname(rs.getString("keyname"));
        vo.setKeytable(rs.getString("keytable"));
        vo.setKeydesc(rs.getString("keydesc"));
        vo.setKeyType(rs.getString("keytype"));
        if(!insert){
        	vo.setTid(rs.getString("tid"));
            vo.setValue(rs.getString("value"));
        }
        
   
       
    }
}
