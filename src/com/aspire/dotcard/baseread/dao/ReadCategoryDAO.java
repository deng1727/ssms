/*
 * 文件名：BookCategoryDAO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.dotcard.baseread.dao;

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
import com.aspire.dotcard.baseread.vo.ReadCategoryVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;

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
public class ReadCategoryDAO
{

    /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(ReadCategoryDAO.class);

    /**
     * singleton模式的实例
     */
    private static ReadCategoryDAO instance = new ReadCategoryDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private ReadCategoryDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static ReadCategoryDAO getInstance()
    {
        return instance;
    }

    /**
     * 为对象属性赋值
     * 
     * @param vo
     * @param rs
     * @throws SQLException
     */
    private void fromReadCategoryVOByRs(ReadCategoryVO vo, ResultSet rs)
                    throws SQLException
    {
        vo.setId(String.valueOf(rs.getInt("id")));
        vo.setCategoryId(rs.getString("categoryid"));
        vo.setCategoryName(rs.getString("categoryname"));
        vo.setParentId(rs.getString("parentId"));
        vo.setType(rs.getString("type"));
        vo.setCatalogType(rs.getString("catalogType"));
        vo.setDecrisption(rs.getString("decrisption"));
        vo.setPicUrl(rs.getString("picUrl"));
        vo.setLupDate(rs.getDate("lupDate"));
        vo.setTotal(rs.getString("total"));
        vo.setCityId(rs.getString("cityid"));
        vo.setPlatForm(rs.getString("platform"));
        vo.setSortId(rs.getInt("sortid"));
        vo.setParentCategoryId(rs.getString("parentCategoryId"));
        vo.setRead_status(rs.getString("read_status"));
    }


    /**
     * 用于返回图书货架信息
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public ReadCategoryVO queryReadCategoryVO(String categoryId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBookCategoryVO(" + categoryId
                         + ") is starting ...");
        }

        // select * from t_rb_category_new t where t.id = ?
        String sqlCode = "read.ReadCategoryDAO.queryReadCategoryVO().SELECT";
        ResultSet rs = null;
        ReadCategoryVO vo = new ReadCategoryVO();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { categoryId });

            if (rs.next())
            {
                fromReadCategoryVOByRs(vo, rs);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("得到图书货架表信息查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("得到图书货架表信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return vo;
    }

    /**
     * 返回当前货架的子货架信息阅读货架信息
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public int hasReadChild(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasBookChild(" + categoryId + ") is starting ...");
        }

        // select count(*) as countNum from t_rb_category_new t where t.parentid
        // = ?
        String sqlCode = "read.ReadCategoryDAO.hasReadChild().SELECT";
        ResultSet rs = null;
        int countNum = 0;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { categoryId });

            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("返回当前货架的子货架信息阅读货架信息发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("返回当前货架的子货架信息阅读货架信息发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }

    /**
     * 用于新增阅读货架
     * 
     * @param BookCategoryVO
     * @throws BOException
     */
    public void saveReadCategory(ReadCategoryVO readVO) throws DAOException
    {

		if (logger.isDebugEnabled())
		{
			logger.debug("saveBookCategory() is starting ...");
		}

		// insert into t_rb_category_new c (id, categoryid, categoryname,
		// parentid, catalogtype, type, decrisption, lupdate, picurl, total,
		// platform, cityid, sortid) values (?, '', ?, ?, ?, ?, ?, sysdate, ?,
		// 0, ?, ?, ?)
		String sqlCode = "read.ReadCategoryDAO.saveReadCategory().save";

		try
		{
			DB.getInstance().executeBySQLCode(
					sqlCode,
					new Object[]
					{ readVO.getId(), readVO.getCategoryId(),
							readVO.getCategoryName(), readVO.getParentId(),
							readVO.getCatalogType(), readVO.getType(),
							readVO.getDecrisption(), readVO.getPicUrl(),
							readVO.getPlatForm(), readVO.getCityId(),
							String.valueOf(readVO.getSortId()) });
		}
		catch (DAOException e)
		{
			throw new DAOException("新增阅读货架时发生异常:", e);
		}
	}

    /**
	 * 用于删除指定货架
	 * 
	 * @return
	 * @throws BOException
	 */
    public void delReadCategory(TransactionDB tdb,String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("delReadCategory(" + categoryId + ") is starting ...");
        }

        // delete from t_rb_category_new r where r.id = ?
        String sqlCode = "read.ReadCategoryDAO.delReadCategory().del";

        try
        {
        	tdb.executeBySQLCode(sqlCode,
                    new Object[] { categoryId });
        }
        catch (DAOException e)
        {
            throw new DAOException("删除指定货架发生异常:", e);
        }
    }

    /**
     * 用于变更图书货架
     * 
     * @param newMusicCategory
     * @throws BOException
     */
    public void updateReadCategory(ReadCategoryVO readVO) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateBookCategory() is starting ...");
        }

        // update t_rb_category c set c.categoryname = ?, c.type = ?, c.lupdate
        // = sysdate, c.decrisption = ?, c.catalogtype = ?, c.platform = ?,
        // c.cityid = ?, c.picurl=?, c.sortid=? where c.id = ?
        String sqlCode = "read.ReadCategoryDAO.updateReadCategory().update";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] {
                                                readVO.getCategoryName(),
                                                readVO.getType(),
                                                readVO.getDecrisption(),
                                                readVO.getCatalogType(),
                                                readVO.getPlatForm(),
                                                readVO.getCityId(),
                                                readVO.getPicUrl(),
                                                String.valueOf(readVO.getSortId()),
                                                readVO.getCategoryId() });
        }
        catch (DAOException e)
        {
            throw new DAOException("变更图书货架时发生异常:", e);
        }
    }

    /**
     * 当前父货架下是否已存在相同名称货架
     * 
     * @param parentId
     * @param name
     * @return 货架名称
     */
    public int hasReadNameInParentId(String parentId, String name)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("hasReadNameInParentId(" + parentId + ", " + name + ") is start...");
        }

        // select count(*) as countNum from t_rb_category_new t where t.parentid
        // = ? and t.categoryname = ?
        String sqlCode = "read.ReadCategoryDAO.hasReadNameInParentId().select";
        ResultSet rs = null;
        int countNum = 0;

        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode(sqlCode, new Object[] { parentId, name });

            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("返回当前父货架下是否已存在相同名称货架发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("返回当前父货架下是否已存在相同名称货架生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }

    /** ************************* */

    /**
     * 用于返回阅读货架扩展字段列表
     * 
     * @return
     * @throws BOException
     */
    public List queryReadCategoryKeyBaseList(String tid) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryReadCategoryKeyBaseList( ) is starting ...");
        }
        String sqlCode = null;
        ResultSet rs = null;
        List list = new ArrayList();
        boolean insert = true;
        try
        {
            if (tid != null && !tid.equals(""))
            {
                // select * from t_key_base b, (select * from t_key_resource r
                // where r.tid = ?) y where b.keytable = 't_rb_category_new' and
                // b.keyid = y.keyid(+)
                sqlCode = "read.ReadCategoryDAO.queryReadCategoryKeyBaseResList.SELECT";
                Object[] paras = { tid };
                rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
                insert = false;
            }
            else
            {
                // select * from t_key_base b where b.keytable = 't_rb_category_new'
                sqlCode = "read.ReadCategoryDAO.queryReadCategoryKeyBaseList.SELECT";
                rs = DB.getInstance().queryBySQLCode(sqlCode, null);
                insert = true;
            }
            while (rs.next())
            {
                ResourceVO vo = new ResourceVO();
                fromReadCategoryKeyBaseVOByRs(vo, rs, insert);
                list.add(vo);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("获取阅读货架扩展信息表信息查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("获取阅读货架扩展信息表信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    /**
     * 为对象属性赋值
     * 
     * @param vo
     * @param rs
     * @throws SQLException
     */
    private void fromReadCategoryKeyBaseVOByRs(ResourceVO vo, ResultSet rs,
                                               boolean insert)
                    throws SQLException
    {
        vo.setKeyid(rs.getString("keyid"));
        vo.setKeyname(rs.getString("keyname"));
        vo.setKeytable(rs.getString("keytable"));
        vo.setKeydesc(rs.getString("keydesc"));
        vo.setKeyType(rs.getString("keytype"));
        if (!insert)
        {
            vo.setTid(rs.getString("tid"));
            vo.setValue(rs.getString("value"));
        }

    }

    /**
     * 
     * @desc 根据seq 获取图书货架ID
     * @author dongke Aug 8, 2011
     * @return
     * @throws DAOException
     */
    public String getReadCategoryId() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getReadCategoryId() is starting ...");
        }
        // select SEQ_BR_CATEGORY_NEW_CID.NEXTVAL from dual
        String sqlCode = "read.ReadCategoryDAO.getReadCategoryId().SELECT";
        DB db = DB.getInstance();
        String newMusicCategoryId = null;
        ResultSet rs = null;
        try
        {
            rs = db.queryBySQLCode(sqlCode, null);
            if (rs.next())
            {
                newMusicCategoryId = rs.getString(1);
            }
        }
        catch (Exception e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }
        return newMusicCategoryId;
    }
    
    /**
     * 
     * @desc 根据seq 获取图书货架ID
     * @author dongke Aug 8, 2011
     * @return
     * @throws DAOException
     */
    public String getReadId() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getReadId() is starting ...");
        }
        // select SEQ_BR_CATEGORY_NEW_ID.NEXTVAL from dual
        String sqlCode = "read.ReadCategoryDAO.getReadId().SELECT";
        DB db = DB.getInstance();
        String newMusicCategoryId = null;
        ResultSet rs = null;
        try
        {
            rs = db.queryBySQLCode(sqlCode, null);
            if (rs.next())
            {
                newMusicCategoryId = rs.getString(1);
            }
        }
        catch (Exception e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }
        return newMusicCategoryId;
    }

    /**
     * 
     * @desc 删除阅读货架扩展字段
     * @author dongke Aug 8, 2011
     * @param vo
     * @return
     * @throws DAOException
     */
    public int delReadCategoryKeyResource(TransactionDB tdb,String tid) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delReadCategoryKeyResource() is starting ...");
        }
        // delete from t_key_resource where tid=? and keyid in (select keyid
        // from t_key_base b where b.keytable='t_rb_category_new')
        String sqlCode = "read.ReadCategoryDAO.delReadCategoryKeyResource().delete";
        int r = 0;
        try
        {
            r = tdb
                  .executeBySQLCode(sqlCode, new Object[] { tid });
        }
        catch (DAOException e)
        {
            throw new DAOException("删除阅读货架扩展字段值时发生异常:", e);
        }
        return r;
    }
    
    
    /**
	 * 应用类分页读取VO的实现类
	 */
	private class ReadCategoryDescPageVO implements PageVOInterface {
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException {
			ReadCategoryVO vo = (ReadCategoryVO) content;
			vo.setId(rs.getString("id"));
			vo.setCategoryName(rs.getString("categoryName"));
			vo.setParentId(rs.getString("parentId"));
			vo.setPath(SEQCategoryUtil.getInstance().getPathByCategoryId(rs.getString("id"), 1));
		}

		public Object createObject() {
			return new ReadCategoryVO();
		}
	}

	/**
	 * 用于查询当前货架下商品列表
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public void queryCategoryDescList(PageResult page, ReadCategoryVO vo)
			throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("queryCategoryDescList( ) is starting ...");
		}

		// select * from t_rb_category_new where 1=1
		String sqlCode = "read.ReadCategoryDAO.queryCategoryDescList().SELECT";
		String sql = null;

		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);

			StringBuffer sqlBuffer = new StringBuffer(sql);
			List<String> paras = new ArrayList<String>();

			// 构造搜索的sql和参数
			if (!"".equals(vo.getId())) {
				sqlBuffer.append(" and id =? ");
				paras.add(vo.getId());
			}
			if (!"".equals(vo.getCategoryName())) {
				sqlBuffer.append(" and categoryName like ? ");
				paras.add("%" + SQLUtil.escape(vo.getCategoryName()) + "%");
			}

			sqlBuffer.append(" order by id ");

			page.excute(sqlBuffer.toString(), paras.toArray(),
					new ReadCategoryDescPageVO());
		} catch (DataAccessException e) {
			throw new DAOException(
					"从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。", e);
		}
	}
}
