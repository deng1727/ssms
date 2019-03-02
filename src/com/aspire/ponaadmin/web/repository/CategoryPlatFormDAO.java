/*
 * 文件名：CategoryDeviceDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.ponaadmin.web.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.gcontent.PlatFormVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;

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
public class CategoryPlatFormDAO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(CategoryPlatFormDAO.class);

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryPlatFormDAO()
    {
    }

    /**
     * singleton模式的实例
     */
    private static CategoryPlatFormDAO categoryDeviceDAO = new CategoryPlatFormDAO();

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static final CategoryPlatFormDAO getInstance()
    {
        return categoryDeviceDAO;
    }

    /**
     * 应用类分页读取VO的实现类
     */
    private class CategoryPlatFormPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            PlatFormVO vo = ( PlatFormVO ) content;
            vo.setPlatFormId(rs.getString("cut_id"));
            vo.setPlatFormName(rs.getString("platform"));
        }

        public Object createObject()
        {
            return new PlatFormVO();
        }
    }

    /**
     * 根据平台信息。得到平台列表
     * 
     * @param platForm 平台信息
     * @return
     * @throws DAOException
     */
    public void queryPlatFormList(PageResult page, PlatFormVO platForm)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryPlatFormList(" + platForm.toString()
                         + ") is starting ...");
        }

        // select * from t_d_platform where 1=1
        String sqlCode = "repository.CategoryPlatFormDAO.queryPlatFormList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(platForm.getPlatFormId()))
            {
                //sql += " and cut_id = '" + platForm.getPlatFormId() + "'";
            	sqlBuffer.append(" and cut_id = ? ");
            	paras.add(platForm.getPlatFormId());
            }
            if (!"".equals(platForm.getPlatFormName()))
            {
                //sql += " and platform like('%" + platForm.getPlatFormName() + "%')";
            	sqlBuffer.append(" and platform like  ? ");
            	paras.add("%"+SQLUtil.escape(platForm.getPlatFormName())+"%");
            }
            
            //sql += " order by cut_id";
            sqlBuffer.append(" order by cut_id");

            //page.excute(sql, null, new CategoryPlatFormPageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new CategoryPlatFormPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }

    /**
     * 根据货架所存平台信息id组合。得到用于页面显示信息
     * @param platForm
     * @return
     */
    public String queryPlatFormListByPlatFormId(String[] platForm) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryPlatFormListByPlatFormId() is starting ...");
        }
        
        // select * from t_d_platform where 1=1
        String sqlCode = "repository.CategoryPlatFormDAO.queryPlatFormList().SELECT";
        ResultSet rs = null;
        StringBuffer temp = new StringBuffer();
        StringBuffer ret = new StringBuffer();
        
        try
        {
            temp.append(SQLCode.getInstance().getSQLStatement(sqlCode));
            
            String s = "";
            // 迭代放入IN条件中
            for (int i = 0; i < platForm.length; i++)
            {
                String c = platForm[i];
                s += c + "','";
            }
            
            // 去最后的逗号
            if(s.length()>0)
            {
                s = s.substring(0, s.length()-3);
            }
            
            temp.append(" and cut_id in ('");
            temp.append(s);
            temp.append("')");

            rs = DB.getInstance().query(temp.toString(),new Object[]{});
            
            while(rs.next())
            {
                ret.append(rs.getString("platform")).append("; ");
            }
            
            return ret.toString();
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
        catch (DAOException e)
        {
            throw new DAOException("根据货架所存平台信息id组合。得到用于页面显示信息时，发生数据库操作错误。",
                                   e);
        }
        catch (SQLException e)
        {
            throw new DAOException("根据货架所存平台信息id组合。得到用于页面显示信息时，发生数据库操作错误。",
                                   e);
        }
    }
}
