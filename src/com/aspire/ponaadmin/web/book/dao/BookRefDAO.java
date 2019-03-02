/*
 * 文件名：BookRefDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.book.dao;

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
import com.aspire.ponaadmin.web.book.vo.BookRefVO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class BookRefDAO
{
    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(BookRefDAO.class);

    /**
     * singleton模式的实例
     */
    private static BookRefDAO instance = new BookRefDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private BookRefDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static BookRefDAO getInstance()
    {
        return instance;
    }
    
    /**
     * 应用类分页读取VO的实现类
     */
    private class BookRefPageVO implements PageVOInterface
    {
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            BookRefVO vo = ( BookRefVO ) content;

            vo.setCId(String.valueOf(rs.getInt("cId")));
            vo.setBookId(rs.getString("bookId"));
            vo.setBookName(rs.getString("bookName"));
            vo.setAuthorName(rs.getString("authorname"));
            vo.setSortNumber(String.valueOf(rs.getInt("sortNumber")));
            vo.setRankValue(String.valueOf(rs.getInt("rankValue")));
            vo.setTypeName(rs.getString("typename"));
        }

        public Object createObject()
        {
            return new BookRefVO();
        }
    }

    /**
     * 用于查询当前货架下商品列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryBookRefList(PageResult page, BookRefVO vo)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBookRefList(" + vo.getCId()
                         + ") is starting ...");
        }

        // select * from T_RB_REFERENCE r, T_RB_BOOK b, t_rb_type t where r.bookid = b.bookid and b.typeid = t.typeid
        String sqlCode = "book.BookRefDAO.queryBookRefList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数

            if (!"".equals(vo.getCId()))
            {
                //sql += " and r.cId ='" + vo.getCId() + "'";
            	sqlBuffer.append(" and r.cId =? ");
            	paras.add(vo.getCId());
            	
            }
            if (!"".equals(vo.getBookId()))
            {
                //sql += " and b.bookid like('%" + vo.getBookId() + "%')";
            	sqlBuffer.append(" and b.bookid like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getBookId())+"%");
            }
            if (!"".equals(vo.getBookName()))
            {
                //sql += " and b.bookname like('%" + vo.getBookName() + "%')";
            	sqlBuffer.append(" and b.bookname like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getBookName())+"%");
            }
            if (!"".equals(vo.getAuthorName()))
            {
                //sql += " and b.authorname like('%" + vo.getAuthorName() + "%')";
            	sqlBuffer.append(" and b.authorname like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getAuthorName())+"%");
            }
            if (!"".equals(vo.getTypeName()))
            {
                //sql += " and t.typeName like('%" + vo.getTypeName() + "%')";
            	sqlBuffer.append(" and t.typeName like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getTypeName())+"%");
            }

            //sql += " order by r.sortnumber asc";
            sqlBuffer.append(" order by r.sortnumber asc");

            //page.excute(sql, null, new BookRefPageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new BookRefPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }
    
    /**
     * 用于移除指定货架下指定的图书
     * 
     * @param categoryId 货架id
     * @param bookId 图书id列
     * @throws DAOException
     */
    public void removeBookRefs(String categoryId, String[] bookId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("removeBookRefs(" + categoryId
                         + ") is starting ...");
        }

        // delete from T_RB_REFERENCE r where r.cid = ? and r.bookid = ?
        String sql = "book.BookRefDAO.removeBookRefs().remove";
        String sqlCode[] = new String[bookId.length];;
        Object[][] object = new Object[bookId.length][2];

        for (int i = 0; i < bookId.length; i++)
        {
            sqlCode[i] = sql;
            object[i][0] = categoryId;
            object[i][1] = bookId[i];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("移除指定货架下指定的图书时发生异常:", e);
        }
    }
    
    /**
     * 用于设置图书货架下图书商品排序值
     * 
     * @param categoryId 货架id
     * @param setSortId 图书排序id
     * @throws DAOException
     */
    public void setBookSort(String categoryId, String[] setSortId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("setBookSort(" + categoryId + ") is starting ...");
        }

        // update T_RB_REFERENCE r set r.sortnumber=? where r.bookid=? and r.cid=?
        String sql = "book.BookRefDAO.setBookSort().set";
        String sqlCode[] = new String[setSortId.length];
        Object[][] object = new Object[setSortId.length][3];

        for (int i = 0; i < setSortId.length; i++)
        {
            String[] temp = setSortId[i].split("_");
            sqlCode[i] = sql;
            object[i][0] = temp[1];
            object[i][1] = temp[0];
            object[i][2] = categoryId;
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("设置图书货架下图书商品排序值时发生异常:", e);
        }
    }
    
    /**
     * 应用类分页读取VO的实现类
     */
    private class BookPageVO implements PageVOInterface
    {
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            BookRefVO vo = ( BookRefVO ) content;

            vo.setBookId(rs.getString("bookId"));
            vo.setBookName(rs.getString("bookName"));
            vo.setAuthorName(rs.getString("authorName"));
            vo.setTypeName(rs.getString("typeName"));
            vo.setDesc(rs.getString("description"));
            vo.setIsFinish(rs.getString("isfinish"));
            vo.setChargeType(rs.getString("chargeType"));
            vo.setFee(rs.getString("fee"));
            vo.setShortRecommend(rs.getString("shortRecommend"));
        }

        public Object createObject()
        {
            return new BookRefVO();
        }
    }

    /**
     * 用于查询图书列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryBookList(PageResult page, BookRefVO vo)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBookList( ) is starting ...");
        }

        // select * from T_RB_BOOK b,T_RB_TYPE t where b.delflag = 0 and
        // b.typeid = t.typeid
        String sqlCode = "book.BookRefDAO.queryBookList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(vo.getBookId()))
            {
                //sql += " and b.bookid like('%" + vo.getBookId() + "%')";
            	sqlBuffer.append(" and b.bookid like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getBookId())+"%");
            }
            if (!"".equals(vo.getBookName()))
            {
                //sql += " and b.bookname like('%" + vo.getBookName() + "%')";
            	sqlBuffer.append(" and b.bookname like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getBookName())+"%");
            }
            if (!"".equals(vo.getAuthorName()))
            {
                //sql += " and b.authorname like('%" + vo.getAuthorName() + "%')";
            	sqlBuffer.append(" and b.authorname like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getAuthorName())+"%");
            }
            if (!"".equals(vo.getTypeName()))
            {
                //sql += " and t.typename like('%" + vo.getTypeName() + "%')";
            	sqlBuffer.append(" and t.typename like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getTypeName())+"%");
            }

            //sql += " order by bookid";
            sqlBuffer.append(" order by bookid");

            //page.excute(sql, null, new BookPageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new BookPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }
    
    /**
     * 用于查看指定货架中是否存在指定图书
     * 
     * @param categoryId 货架id
     * @param bookId 图书id列
     * @throws DAOException
     */
    public String isHasBookRefs(String categoryId, String[] bookId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("isBookRefs(" + categoryId + ") is starting ...");
        }
        
        // select t.bookid from T_RB_REFERENCE t where 1=1
        String sqlCode = "book.BookRefDAO.hasBookList().SELECT";
        StringBuffer sql;
        ResultSet rs = null;
        String ret = "";
        StringBuffer temp = new StringBuffer("");
        
        try
        {
            sql = new StringBuffer(SQLCode.getInstance().getSQLStatement(sqlCode));
            
            sql.append(" and t.cId='").append(categoryId).append("' ");

            for (int i = 0; i < bookId.length; i++)
            {
                temp.append("'").append(bookId[i]).append("'").append(",");
            }
            
            if(temp.length() > 0)
            {
                temp.deleteCharAt(temp.length()-1);
                sql.append(" and t.bookid in ( ").append(temp).append(" )");
            }
            
            rs = DB.getInstance().query(sql.toString(), null);
            
            while (rs.next())
            {
                ret += rs.getString("bookid") + ". ";
            }
            
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
        catch (SQLException e)
        {
            throw new DAOException("查看指定货架中是否存在指定图书时发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }
        
        return ret;
    }

    /**
     * 用于添加指定的图书至货架中
     * 
     * @param categoryId 货架id
     * @param bookId 图书id列
     * @throws DAOException
     */
    public void addBookRefs(String categoryId, String[] bookId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addBookRefs(" + categoryId + ") is starting ...");
        }

        // insert into T_RB_REFERENCE (cid, bookid, categoryid, sortNumber,
        // rankValue) values (?, ?, '', (select decode(max(sortNumber), null, 1,
        // max(sortNumber) + 1) from T_RB_REFERENCE n where n.cid = ?), 0)
        String sql = "book.BookRefDAO.addBookRefs().add";
        String sqlCode[] = new String[bookId.length];
        Object[][] object = new Object[bookId.length][3];

        for (int i = 0; i < bookId.length; i++)
        {
            sqlCode[i] = sql;
            object[i][0] = categoryId;
            object[i][1] = bookId[i];
            object[i][2] = categoryId;
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("添加指定的图书至货架中时发生异常:", e);
        }
    }
    
    /**
     * 查看当前货架下是否还存在着商品
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public int hasBook(String categoryId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasBook("+ categoryId +" ) is starting ...");
        }

        // select count(*) as countNum from T_RB_REFERENCE t where t.cid = ?
        String sqlCode = "book.BookRefDAO.hasBook().SELECT";
        ResultSet rs = null;
        int countNum = 0;
        
        rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{categoryId});

        try
        {
            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("返回当前货架下是否还存在着商品信息发生异常:", e);
        }
        
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }
    
    /**
     * 用于清空原货架下图书
     * 
     * @param categoryId 货架id
     * @throws DAOException
     */
    public void delBookRef(String categoryId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delBookRef(" + categoryId + ") is starting ...");
        }

        // delete from T_RB_REFERENCE r where r.cid = ?
        String sql = "book.BookRefDAO.delBookRef().remove";

        try
        {
            DB.getInstance().executeBySQLCode(sql, new Object[] { categoryId });
        }
        catch (DAOException e)
        {
            throw new DAOException("用于清空原货架下图书时发生异常:", e);
        }
    }
    
    /**
     * 校驗文件中數據是否在图书表中存在
     * 
     * @param list
     * @throws DAOException
     */
    public String verifyBook(List list) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("verifyBook() is starting ...");
        }

        // select 1 from t_rb_book c where c.bookid = ?
        String sql = "book.BookRefDAO.verifyBook().select";
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        // 迭代查詢
        for (int i = 0; i < list.size(); i++)
        {
            String temp = (String)list.get(i);
            try
            {
                rs = DB.getInstance().queryBySQLCode(sql.toString(),
                                            new Object[] { temp });
                // 如果不存在相應數據
                if (!rs.next())
                {
                    list.remove(i);
                    i--;
                    sb.append(temp).append(". ");
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("查看指定货架中是否存在指定音乐时发生异常:", e);
            }
            finally
            {
                DB.close(rs);
            }
        }
        
        return sb.toString();
    }
    /**
     * 用于查询图书详情
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public BookRefVO queryBookInfo(String bookId)
                    throws DAOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("queryBookInfo( ) is starting ...");
		}
		// select * from t_rb_book t where t.bookid=?
		String sqlCode = "book.BookRefDAO.queryBookInfo().SELECT";
		DB db = DB.getInstance();
		ResultSet rs = null;
		Object paras[] = { bookId };
		BookRefVO vo = null;
		try
		{
			rs = db.queryBySQLCode(sqlCode, paras);
			if (rs.next())
			{
				vo = new BookRefVO();
				vo.setBookId(rs.getString("bookid"));
				vo.setBookName(rs.getString("bookname"));
				vo.setDesc(rs.getString("DESCRIPTION"));
				vo.setAuthorName(rs.getString("AUTHORNAME"));

				
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("执行查询音乐详情失败", e);
		}finally{
			db.close(rs);		
		}
		return vo;
	}
    
    
    /**
     * 
     *@desc 获取图书的扩展字段
     *@author dongke
     *Aug 9, 2011
     * @param musicId
     * @return
     * @throws DAOException
     */
    public List queryBookKeyResource(String bookId) throws DAOException
	{
		List keyResourceList = null;
		if (logger.isDebugEnabled())
		{
			logger.debug("queryBookKeyResource( ) is starting ...");
		}
		// select *  from t_key_base b,     (select *   from t_key_resource r   where r.tid = ?) y   where b.keytable = 't_rb_book' and b.keyid = y.keyid(+)
		String sqlCode = "book.BookRefDAO.queryBookKeyResource().SELECT";
		DB db = DB.getInstance();
		ResultSet rs = null;
		Object paras[] = { bookId };
		try
		{
			keyResourceList = new ArrayList();
			rs = db.queryBySQLCode(sqlCode, paras);
			while (rs.next())
			{
				ResourceVO vo = new ResourceVO();
				vo.setKeyid(rs.getString("keyid"));
				vo.setKeyname(rs.getString("keyname"));
				vo.setKeytable(rs.getString("keytable"));
				vo.setKeydesc(rs.getString("keydesc"));
				vo.setKeyType(rs.getString("keytype"));
				vo.setTid(rs.getString("tid"));
				vo.setValue(rs.getString("value"));

				keyResourceList.add(vo);
			}
		} catch (SQLException e)
		{

			e.printStackTrace();
			throw new DAOException("执行查询音乐扩展字段详情失败", e);
		} finally
		{
			db.close(rs);
		}
		return keyResourceList;
	} 
}
