/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm.basebook;

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
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;

/**
 * @author x_wangml
 * 
 */
public class BaseBookDAO
{

    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseBookDAO.class);

    /**
     * singleton模式的实例
     */
    private static BaseBookDAO instance = new BaseBookDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private BaseBookDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static BaseBookDAO getInstance()
    {
        return instance;
    }

    /**
     * 应用类分页读取VO的实现类
     */
    private class BaseBookPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

            BaseBookVO vo = ( BaseBookVO ) content;
            vo.setBookId(rs.getString("bookId"));
            vo.setBookName(rs.getString("bookName"));
            vo.setAuthorName(rs.getString("authorName"));
            vo.setBookType(rs.getString("typeName"));
            vo.setBookDesc(rs.getString("description"));
            if (rs.getInt("validityData") == 0)
            {
                vo.setValidityData("有效数据");
            }
            else
            {
                vo.setValidityData("失效数据");
            }
        }

        public Object createObject()
        {

            return new BaseBookVO();
        }
    }

    /**
     * 用于查询图书游戏数据情况
     * 
     * @param page
     * @throws DAOException
     */
    public void queryBaseBookList(PageResult page, BaseBookVO vo)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBaseBookList( ) is starting ...");
        }

        // select t.bookid,t.bookname,t.authorname,b.typename,t.description from
        // t_rb_book t,t_rb_type b where t.typeid = b.typeid
        String sqlCode = "basegame.BaseBookDAO.queryBaseBookList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(vo.getBookName()))
            {
                //sql += " and t.bookname like ('%" + vo.getBookName() + "%')";
            	sqlBuffer.append("  and t.bookname like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getBookName())+"%");
            }
            if (!"".equals(vo.getAuthorName()))
            {
                //sql += " and t.authorname like ('%" + vo.getAuthorName()+ "%')";
            	sqlBuffer.append(" and t.authorname like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getAuthorName())+"%");
            }
            if (!"".equals(vo.getBookType()))
            {
               // sql += " and b.typename like ('%" + vo.getBookType() + "%')";
            	sqlBuffer.append(" and b.typename like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getBookType())+"%");
            }
            if (!"".equals(vo.getKey()))
            {
                //sql += " and t.keyword like ('%" + vo.getKey() + "%')";
            	sqlBuffer.append(" and t.keyword like ?");
            	paras.add("%"+SQLUtil.escape(vo.getKey())+"%");
            }
            if (!"".equals(vo.getBookDesc()))
            {
                //sql += " and t.description like ('%" + vo.getBookDesc() + "%')";
            	sqlBuffer.append(" and t.description like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getBookDesc())+"%");
            }

            //page.excute(sql, null, new BaseBookPageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new BaseBookPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }

    /**
     * 用于查询基地图书数据情况
     * 
     * @param page
     * @param gameName
     * @param gameDesc
     * @throws DAOException
     */
    public void queryBaseBookTempList(PageResult page) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBaseBookList( ) is starting ...");
        }

        // select
        // t.bookid,t.bookname,t.authorname,b.typename,t.description,t.delflag
        // as validityData from t_rb_book t, t_rb_type b, t_base_temp v where
        // t.bookid = v.baseid and v.basetype='baseBook' and t.typeid = b.typeid
        String sqlCode = "basegame.BaseBookDAO.queryBaseBookTempList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            page.excute(sql, null, new BaseBookPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }

    /**
     * 用于生成文件应用数据
     * 
     * @throws DAOException
     */
    public List queryToFileData() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryToFileData( ) is starting ...");
        }

        // select t.bookid,t.bookname,t.authorname,b.typename,t.description from
        // t_rb_book t, t_rb_type b, t_base_temp v where t.bookid = v.baseid and
        // v.basetype='baseBook' and t.typeid = b.typeid and t.delflag = 0
        String sqlCode = "basegame.BaseBookDAO.queryToFileData().SELECT";
        List list = new ArrayList();
        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] {});

            // 如果存在next说明存在数据
            while (rs.next())
            {
                list.add(formatDataLine(rs));
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("读取数据临时表时发生数据库错误");
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    /**
     * 用于整理每条显示数据
     * 
     * @param rs
     * @return
     * @throws SQLException
     */
    private String formatDataLine(ResultSet rs) throws SQLException
    {
        StringBuffer temp = new StringBuffer();

        temp.append("BK")
            .append(splitString())
            .append(rs.getString("bookId"))
            .append(splitString())
            .append(rs.getString("bookName"));

        return temp.toString();
    }

    /**
     * 返回分隔符ASCII码31(0x1F)
     * 
     * @return
     */
    private char splitString()
    {
        String tmp = "0x1F";

        // 0x开头的，表示是16进制的，需要转换
        String s = tmp.substring(2, tmp.length());
        int i = Integer.parseInt(s, 16);

        return ( char ) i;
    }
}
