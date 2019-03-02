package com.aspire.dotcard.audit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import org.apache.commons.lang.StringUtils;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.audit.vo.CheckDetailVO;
import com.aspire.dotcard.audit.vo.CheckLogVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;

/**
 * 稽核模块数据库操作
 */
public class CheckLogDAO
{
    /**
     * 记录日志的实例对象
     */
    protected static JLogger LOG = LoggerFactory.getLogger(CheckLogDAO.class);

    /**
     * singleton模式的实例
     */
    private static CheckLogDAO instance = new CheckLogDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private CheckLogDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CheckLogDAO getInstance()
    {
        return instance;
    }

    /**
     * 用于查询当前稽核列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryCheckLogList(PageResult page, CheckLogVO vo) throws DAOException
    {

        StringBuilder sqlsb = new StringBuilder(200);
        String sql = "select taskid, categoryid, checktime, lucenepath, ip, dbcount, lucenecount from t_dc_checklog where 1=1 ";

        sqlsb.append(sql);

        List<String> paras = new ArrayList<String>(2);

        if (StringUtils.isNotBlank(vo.getTaskid()))
        {
            sqlsb.append(" and taskid = ? ");
            paras.add(vo.getTaskid());
        }
        if (StringUtils.isNotBlank(vo.getCategoryid()))
        {
            sqlsb.append(" and categoryid = ? ");
            paras.add(vo.getCategoryid().trim());
        }
        if (StringUtils.isNotBlank(vo.getIp()))
        {
            sqlsb.append(" and ip like ? ");
            paras.add("%" + SQLUtil.escape(vo.getIp().trim()) + "%");
        }

        if (StringUtils.isNotBlank(vo.getResultcount()))
        {
            if ("1".equals(vo.getResultcount()))
            {
                sqlsb.append(" and dbcount=lucenecount ");
            }
            else if ("0".equals(vo.getResultcount()))
            {
                sqlsb.append(" and dbcount!=lucenecount ");
            }
        }

        // 时间checktime
        if (StringUtils.isNotBlank(vo.getBeginDate()))
        {
            sqlsb.append(" and to_char(checktime,'yyyy-MM-dd')>= ? ");
            paras.add(SQLUtil.escape(vo.getBeginDate()));
        }
        if (StringUtils.isNotBlank(vo.getEndDate()))
        {
            sqlsb.append(" and to_char(checktime,'yyyy-MM-dd')<= ? ");
            paras.add(SQLUtil.escape(vo.getEndDate()));
        }

        sqlsb.append(" order by checktime desc");

        page.excute(sqlsb.toString(), paras.toArray(), new PageVOInterface()
        {
            public void CopyValFromResultSet(Object vo, ResultSet rs) throws SQLException
            {

                CheckLogVO checkLogVO = (CheckLogVO)vo;
                checkLogVO.setTaskid(rs.getString("taskid"));
                checkLogVO.setCategoryid(rs.getString("categoryid"));
                checkLogVO.setChecktime(rs.getTimestamp("checktime"));
                checkLogVO.setLucenepath(rs.getString("lucenepath"));
                checkLogVO.setIp(rs.getString("ip"));
                checkLogVO.setDbcount(rs.getInt("dbcount"));
                checkLogVO.setLucenecount(rs.getInt("lucenecount"));
                if (checkLogVO.getDbcount() == checkLogVO.getLucenecount())
                {
                    checkLogVO.setResultcount("1");
                }
                else
                {
                    checkLogVO.setResultcount("0");
                }
                if (StringUtils.isNotBlank(checkLogVO.getCategoryid()))
                {
                    try
                    {
                        String categoryPath = getCategoryPath(checkLogVO.getCategoryid());
                        checkLogVO.setCategoryPath(categoryPath);
                    }
                    catch (DAOException e)
                    {
                        LOG.error("getCategoryPath发生异常!", e);
                    }
                }
            }

            public Object createObject()
            {
                return new CheckLogVO();
            }
        });
    }

    /**
     * 查询稽核内容详情信息列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryCheckDetailList(PageResult page, CheckDetailVO vo) throws DAOException
    {

        StringBuilder sqlsb = new StringBuilder(200);
        String sql = " select taskid,categoryid,checktime,lucenepath, ip,pkid ,statusinfo from t_dc_checkdetail where 1=1 ";

        sqlsb.append(sql);

        List<String> paras = new ArrayList<String>(2);

        if (StringUtils.isNotBlank(vo.getTaskid()))
        {
            sqlsb.append(" and taskid = ? ");
            paras.add(vo.getTaskid().trim());
        }
        if (StringUtils.isNotBlank(vo.getCategoryid()))
        {
            sqlsb.append(" and categoryid = ? ");
            paras.add(vo.getCategoryid().trim());
        }

        if (StringUtils.isNotBlank(vo.getIp()))
        {
            sqlsb.append(" and ip like ? ");
            paras.add("%" + SQLUtil.escape(vo.getIp().trim()) + "%");
        }

        if (StringUtils.isNotBlank(vo.getStatusinfo()))
        {
            sqlsb.append(" and statusinfo = ? ");
            paras.add(vo.getStatusinfo().trim());
        }

        // 时间checktime
        if (StringUtils.isNotBlank(vo.getBeginDate()))
        {
            sqlsb.append(" and to_char(checktime,'yyyy-MM-dd')>= ? ");
            paras.add(SQLUtil.escape(vo.getBeginDate()));
        }
        if (StringUtils.isNotBlank(vo.getEndDate()))
        {
            sqlsb.append(" and to_char(checktime,'yyyy-MM-dd')<= ? ");
            paras.add(SQLUtil.escape(vo.getEndDate()));
        }

        sqlsb.append(" order by checktime desc");

        page.excute(sqlsb.toString(), paras.toArray(), new PageVOInterface()
        {
            public void CopyValFromResultSet(Object vo, ResultSet rs) throws SQLException
            {

                CheckDetailVO checkDetailVO = (CheckDetailVO)vo;
                checkDetailVO.setTaskid(rs.getString("taskid"));
                checkDetailVO.setCategoryid(rs.getString("categoryid"));
                checkDetailVO.setChecktime(rs.getTimestamp("checktime"));
                checkDetailVO.setLucenepath(rs.getString("lucenepath"));
                checkDetailVO.setIp(rs.getString("ip"));
                checkDetailVO.setPkid(rs.getString("pkid"));
                checkDetailVO.setStatusinfo(rs.getInt("statusinfo") + "");
                if (StringUtils.isNotBlank(checkDetailVO.getCategoryid()))
                {
                    try
                    {
                        String categoryPath = getCategoryPath(checkDetailVO.getCategoryid());
                        checkDetailVO.setCategoryPath(categoryPath);
                    }
                    catch (DAOException e)
                    {
                        LOG.error("getCategoryPath发生异常!", e);
                    }
                }
            }

            public Object createObject()
            {
                return new CheckDetailVO();
            }
        });
    }

    /**
     * 查询货架路径
     * @param categoryid
     * @return
     * @throws DAOException
     */
    public String getCategoryPath(String categoryid) throws DAOException
    {
        String path = "";
        String sqlCode = "com.aspire.dotcard.audit.dao.getCategoryPath";
        try
        {
            RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] { categoryid });
            while (rs.next())
            {
                path = path + rs.getString("path") + " ";
            }
        }
        catch (SQLException e)
        {
            LOG.error("getCategoryPath发生异常!", e);
            throw new DAOException("getCategoryPath发生异常!", e);
        }
        if (path != null && StringUtils.isNotBlank(path))
        {
            path = path.replace(",", "");
        }
        return path;
    }

}
