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
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseBookDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static BaseBookDAO instance = new BaseBookDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private BaseBookDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static BaseBookDAO getInstance()
    {
        return instance;
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
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
                vo.setValidityData("��Ч����");
            }
            else
            {
                vo.setValidityData("ʧЧ����");
            }
        }

        public Object createObject()
        {

            return new BaseBookVO();
        }
    }

    /**
     * ���ڲ�ѯͼ����Ϸ�������
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
            //����������sql�Ͳ���
            
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
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * ���ڲ�ѯ����ͼ���������
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
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * ���������ļ�Ӧ������
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

            // �������next˵����������
            while (rs.next())
            {
                list.add(formatDataLine(rs));
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("��ȡ������ʱ��ʱ�������ݿ����");
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    /**
     * ��������ÿ����ʾ����
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
     * ���طָ���ASCII��31(0x1F)
     * 
     * @return
     */
    private char splitString()
    {
        String tmp = "0x1F";

        // 0x��ͷ�ģ���ʾ��16���Ƶģ���Ҫת��
        String s = tmp.substring(2, tmp.length());
        int i = Integer.parseInt(s, 16);

        return ( char ) i;
    }
}
