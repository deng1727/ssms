
package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.vo.RCategoryVO;

public class BookCategoryDao
{
    protected static JLogger log = LoggerFactory.getLogger(BookCategoryDao.class);
    private static BookCategoryDao instance = new BookCategoryDao();

    private BookCategoryDao()
    {
    }

    public static BookCategoryDao getInstance()
    {
        return instance;
    }

    /**
     * 新增 专区信息
     * 
     * @param cate
     * @throws Exception
     */
    public void add(RCategoryVO cate) throws Exception
    {
		if (log.isDebugEnabled())
		{
			log.debug("add BookCategory(" + cate + ")");
		}
		// insert into T_RB_CATE (CATEID, CATENAME, CATEDESC, PARENTCATEID,
		// CATEPIC, BUSINESSTIME) values
		// (?, ?, ?, ?, ?, ?)
		String sqlCode = "com.aspire.dotcard.baseread.dao.BookCategoryDao.add";

		// 定义在sql语句中要替换的参数,
		Object[] paras =
		{ cate.getCateId(), cate.getCateName(), cate.getDescription(),
				cate.getCateParentId(), cate.getPicUrl(),
				cate.getBusinessTime()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

    /**
	 * 修改
	 * 
	 * @param cate
	 * @throws Exception
	 */
    public void update(RCategoryVO cate) throws Exception
    {
		if (log.isDebugEnabled())
		{
			log.debug("add BookCategory(" + cate + ")");
		}

		// update T_RB_CATE set CATENAME \= ?, CATEDESC \= ?, PARENTCATEID \= ?,
		// CATEPIC \= ?, BUSINESSTIME\= ? where CATEID \= ?
		String sqlCode = "com.aspire.dotcard.baseread.dao.BookCategoryDao.update";

		// 定义在sql语句中要替换的参数,
		Object[] paras =
		{ cate.getCateName(), cate.getDescription(), cate.getCateParentId(),
				cate.getPicUrl(), cate.getBusinessTime(), cate.getCateId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

    /**
	 * 删除
	 * 
	 * @param cate
	 * @throws Exception
	 */
    public void delete(RCategoryVO cate) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("add BookCategory(" + cate + ")");
        }
        // 删除专区信息
        // delete from T_RB_CATE c where c.cateid=? and c.catetype=?
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookCategoryDao.delete";
        Object[] paras = { cate.getCateId(), cate.getCatalogType() };

        // 删除专区内容信息
        // delete from t_rb_reference_new r where r.cid in (select c.id from
        // t_rb_category_new c where c.categoryid=? and c.catalogtype=?) and
        // r.categoryid=?
        String delRefSqlCode = "com.aspire.dotcard.baseread.dao.BookReferenceDao.deleteRank";
        Object[] delRefParas = { cate.getCateId(), cate.getCatalogType(),
                        cate.getCateId() };

        DB.getInstance().executeMutiBySQLCode(new String[] { delRefSqlCode,
                                                              sqlCode },
                                              new Object[][] { delRefParas,
                                                              paras });
    }

    /**
     * 删除货架信息
     * 
     * @param cate
     * @throws Exception
     */
    public void deleteCateGory(RCategoryVO cate) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("del deleteCateGory(" + cate + ")");
        }
        // 删除专区信息
        // delete from t_rb_category_new c where c.id=?
        // update t_rb_category_new c set c.delflag=1 where c.id=?   --edit by 12.10.08
        String sqlCode = "baseread.dao.BookCategoryDao.deleteCateGory.del";
        Object[] paras = { cate.getId() };

        // 删除专区内容信息
        // delete from t_rb_reference_new r where r.cid in (select c.id from
        // t_rb_category_new c where c.categoryid=? and c.catalogtype=?) and
        // r.categoryid=?
        String delRefSqlCode = "com.aspire.dotcard.baseread.dao.BookReferenceDao.deleteRank";
        Object[] delRefParas = { cate.getCateId(), cate.getCatalogType(),
                        cate.getCateId() };

        DB.getInstance().executeMutiBySQLCode(new String[] { delRefSqlCode,
                                                              sqlCode },
                                              new Object[][] { delRefParas,
                                                              paras });
    }

    /**
     * 更新货架下商品总数
     * 
     * @param cate
     * @throws Exception
     */
    public void updateCateTotal() throws Exception
    {
        log.debug("update category total books");
        // 删除专区信息
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookCategoryDao.updateTotal";

        DB.getInstance().executeBySQLCode(sqlCode, null);
    }

    /**
     * 查询所有存在的货架
     * 
     * @return
     * @throws Exception
     */
    public Map queryAllCate() throws Exception
    {
        // select * from t_rb_cate
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookCategoryDao.queryAllAreaCate";
        Map m = null;
        ResultSet rs = null;
        try
        {

            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            m = new HashMap();
            while (rs.next())
            {
                RCategoryVO cate = new RCategoryVO();
                cate.setCateId(rs.getString("cateid"));
                cate.setCateName(rs.getString("catename"));
                cate.setCatalogType(rs.getString("catetype"));
                cate.setCateParentId(rs.getString("parentcateid"));
                cate.setPicUrl(rs.getString("catepic"));
                cate.setSortId(rs.getInt("sortId"));
                m.put(cate.getCateId(), cate);
            }
        }
        catch (SQLException e)
        {
            log.error("数据库操作失败");
            throw new DAOException("数据库操作失败，" + e);
        }
        finally
        {
            DB.close(rs);
        }
        return m;
    }

    /**
     * 查询所有存在的货架
     * 
     * @return
     * @throws Exception
     */
    public Map queryAllCateTree() throws Exception
    {
        // select * from t_rb_cate
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookCategoryDao.queryAllCateTree";
        Map m = null;
        ResultSet rs = null;
        try
        {

            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            m = new HashMap();
            while (rs.next())
            {
                RCategoryVO cate = new RCategoryVO();
                cate.setCateId(rs.getString("cateid"));
                cate.setCateName(rs.getString("catename"));
                cate.setCatalogType(rs.getString("catetype"));
                cate.setCateParentId(rs.getString("parentcateid"));
                cate.setPicUrl(rs.getString("catepic"));
                cate.setSortId(rs.getInt("sortId"));
                cate.setDescription(rs.getString("cateDesc"));
                cate.setBusinessTime(rs.getString("businessTime"));
                m.put(cate.getCateId(), cate);
            }
        }
        catch (SQLException e)
        {
            log.error("数据库操作失败");
            throw new DAOException("数据库操作失败，" + e);
        }
        finally
        {
            DB.close(rs);
        }
        return m;
    }

    /**
     * 查询所有存在的货架
     * 
     * @return
     * @throws Exception
     */
    public Map queryAllId() throws Exception
    {
        // select id, cateid from t_rb_cate c, t_rb_category_new n where
        // c.cateid=n.categoryid and c.catetype=n.catalogtype
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookCategoryDao.queryAllId";
        Map m = null;
        ResultSet rs = null;
        try
        {

            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            m = new HashMap();
            while (rs.next())
            {
                m.put(rs.getString("cateid"), String.valueOf(rs.getInt("id")));
            }
        }
        catch (SQLException e)
        {
            log.error("数据库操作失败");
            throw new DAOException("数据库操作失败，" + e);
        }
        finally
        {
            DB.close(rs);
        }
        return m;
    }

    /**
     * 检查是否存在
     * 
     * @param cate
     * @return
     * @throws Exception
     */
    public boolean isExist(RCategoryVO cate) throws Exception
    {
        int count = 0;
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookCategoryDao.isExist";

        ResultSet rs = null;
        try
        {
            Object[] paras = { cate.getCateId() };
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

            while (rs.next())
            {
                count = rs.getInt("count");
            }
        }
        catch (SQLException e)
        {
            log.error("数据库操作失败");
            throw new DAOException("数据库操作失败，" + e);

        }
        finally
        {
            DB.close(rs);
        }
        return count == 0 ? false : true;
    }

    /**
     * 删除商品信息，在根据排行榜信息，删除现货架中多出的货架
     */
    public void cleanOldSimulationDataRank() throws BOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("删除商品信息，在根据排行榜信息，删除现货架中多出的货架,开始");
        }

        // 删除模拟商品表
        String sqlCode = "baseread.dao.BookCategoryDao.cleanOldSimulationDataRank.reference";

        // 删除模拟货架表
        String delRefSqlCode = "baseread.dao.BookCategoryDao.cleanOldSimulationDataRank.category";

        try
        {
            DB.getInstance()
              .executeMutiBySQLCode(new String[] { sqlCode, delRefSqlCode },
                                    new Object[][] { null, null });
        }
        catch (DAOException e)
        {
            log.error("执行删除商品信息，在根据排行榜信息，删除现货架中多出的货架时SQL发生异常，删除旧表失败", e);
            throw new BOException("执行删除商品信息，在根据排行榜信息，删除现货架中多出的货架时SQL发生异常", e);
        }

        if (log.isDebugEnabled())
        {
            log.debug("删除商品信息，在根据排行榜信息，删除现货架中多出的货架,结束");
        }
    }

    /**
     * 自定义组装模拟树结构表
     * 
     */
    public void insertDataToRank() throws BOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("用于自定义组装模拟树结构表,开始");
        }

        // 更新排行榜信息
		// update t_rb_category_new c set c.delflag = 0 where c.catalogtype =
		// '8' and 1 = (select distinct 1 from t_rb_rank_new r where r.rankid =
		// c.categoryid)
		String sqlCode = "baseread.dao.BookCategoryDao.updateDataToRank.rank";
        
        // update t_rb_category_new c set c.categoryname = (select r.rankname
		// from t_rb_rank_new r where r.rankid = c.categoryid) where
		// c.catalogtype = '8' and c.delflag = 0
		String sqlCode0 = "baseread.dao.BookCategoryDao.updateDataToRank.rank2";

        // 加入排行榜信息
        String sqlCode1 = "baseread.dao.BookCategoryDao.insertDataToRank.rank";

        try
        {
            DB.getInstance()
              .executeMutiBySQLCode(new String[] { sqlCode, sqlCode0, sqlCode1 },
                                    new Object[][] { null, null, null });
        }
        catch (DAOException e)
        {
            log.error("执行自定义组装模拟树结构表时发生异常，创建表失败", e);
            throw new BOException("执行自定义组装模拟树结构表时发生异常", e);
        }

        if (log.isDebugEnabled())
        {
            log.debug("用于自定义组装模拟树结构表,结束");
        }
    }

    /**
     * 根据排行榜信息，加入商品表中
     * 
     */
    public void insertDataByRankToReference() throws BOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("根据排行榜信息，加入商品表中,开始");
        }

        // 加入排行榜信息
        String sqlCode = "baseread.dao.BookCategoryDao.insertDataByRankToReference.insert";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, null);
        }
        catch (DAOException e)
        {
            log.error("执行根据排行榜信息，加入商品表中时发生异常，创建表失败", e);
            throw new BOException("执行根据排行榜信息，加入商品表中时发生异常", e);
        }

        if (log.isDebugEnabled())
        {
            log.debug("用于根据排行榜信息，加入商品表中,结束");
        }
    }

    /**
     * 第一步：查询在专区表中不存在。在货架表中存在的货架，以删除
     */
    public List cleanOldSimulationDataTree() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("查询在专区表中不存在。在货架表中存在的货架,开始");
        }

        // 删除模拟商品表
        String sqlCode = "baseread.dao.BookCategoryDao.cleanOldSimulationDataTree.query";
        List list = null;
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            list = new ArrayList();
            while (rs.next())
            {
                RCategoryVO cate = new RCategoryVO();
                cate.setId(String.valueOf(rs.getInt("id")));
                cate.setCateId(rs.getString("categoryid"));
                cate.setCatalogType(rs.getString("catalogtype"));
                list.add(cate);
            }
        }
        catch (DAOException e)
        {
            log.error("查询在专区表中不存在。在货架表中存在的货架操作失败");
            throw new DAOException("查询在专区表中不存在。在货架表中存在的货架操作失败，" + e);
        }
        catch (SQLException e)
        {
            log.error("查询在专区表中不存在。在货架表中存在的货架操作失败");
            throw new DAOException("查询在专区表中不存在。在货架表中存在的货架操作失败，" + e);
        }
        finally
        {
            DB.close(rs);
        }

        if (log.isDebugEnabled())
        {
            log.debug("查询在专区表中不存在。在货架表中存在的货架,结束");
        }
        return list;

    }

    /**
     * 第二步：根据专区信息，新增或更新货架中存在的货架
     * 
     * @param allCate 专区与货架共有的
     * @param allArea 专区中的货架
     */
    public void updateDataToTree() throws BOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("根据专区信息，新增或更新货架中存在的货架,开始");
        }

        Map allTree = null;
        Map allCate = null;

        try
        {
            // 专区樹結構
            allTree = BookCategoryDao.getInstance().queryAllCateTree();

            // 專區id與貨架id對應關系
            allCate = BookCategoryDao.getInstance().queryAllId();
        }
        catch (Exception e)
        {
            log.error("执行自定义组装模拟树结构表时发生异常，创建表失败", e);
            throw new BOException("执行自定义组装模拟树结构表时发生异常", e);
        }

        Set allTreeSet = allTree.keySet();

        for (Iterator iter = allTreeSet.iterator(); iter.hasNext();)
        {
            String key = ( String ) iter.next();
            
            RCategoryVO vo = ( RCategoryVO )allTree.get(key);

            // 如果為專區的根目錄，放到專區根貨架下
            if ("".equals(vo.getCateParentId()) || null == vo.getCateParentId())
            {
                vo.setParentId("101");
            }
            // 否则不是根路径，判断当前有没有他的父目录，如果有存入
            else if (allCate.containsKey(vo.getCateParentId()))
            {
                vo.setParentId(String.valueOf(allCate.get(vo.getCateParentId())));
            }
            // 否则定义为000这个特殊字符。之后统一变更
            else
            {
                vo.setParentId("000");
            }

            // 先判斷當前貨架中是否存在這個專區。用以定是新增還是更新
            if (allCate.containsKey(vo.getCateId()))
            // update
            {
                vo.setId(String.valueOf(allCate.get(vo.getCateId())));

                updateCategory(vo);
            }
            else
            // add
            {
                addCategory(vo);
            }
        }

        // 用于统一更新父目录为000的货架id
        this.updatedCategoryParentId();

        if (log.isDebugEnabled())
        {
            log.debug("根据专区信息，新增或更新货架中存在的货架,结束");
        }
    }

    /**
     * 添加专区为货架
     * 
     * @param vo
     * @throws BOException
     */
    public void updateCategory(RCategoryVO vo) throws BOException
    {
		if (log.isDebugEnabled())
		{
			log.debug("用于更新专区货架,开始");
		}

		// update t_rb_category_new n set n.categoryname=?, n.catalogtype=?,
		// n.parentid=?, n.picurl=?, n.lupdate=sysdate, n.sortid=?,
		// n.parentcategoryid=?, DECRISPTION=?, BUSINESSTIME=?, delflag = 0 where n.id=? and n.categoryid=?
		String sqlCode = "baseread.dao.BookCategoryDao.insertDataToTree.updateCategory";

		Object[] paras =
		{ vo.getCateName(), vo.getCatalogType(), vo.getParentId(),
				vo.getPicUrl(), new Integer(vo.getSortId()),
				vo.getCateParentId(), vo.getDescription(),
				vo.getBusinessTime(), vo.getId(), vo.getCateId() };

		try
		{
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		}
		catch (DAOException e)
		{
			log.error("执行更新专区货架时发生异常，创建表失败", e);
			throw new BOException("执行更新专区货架时发生异常", e);
		}

		if (log.isDebugEnabled())
		{
			log.debug("用于更新专区货架,结束");
		}
	}

    /**
	 * 添加专区为货架
	 * 
	 * @param vo
	 * @throws BOException
	 */
    public void addCategory(RCategoryVO vo) throws BOException
    {
		if (log.isDebugEnabled())
		{
			log.debug("用于添加专区为货架,开始");
		}

		// insert into t_rb_category_new (id, categoryid, categoryname,
		// catalogtype, parentid, picurl, sortid, parentcategoryid, DECRISPTION,
		// BUSINESSTIME) values
		// (SEQ_BR_CATEGORY_NEW_ID.NEXTVAL, ?,?,?,?,?,?,?,?,?)
		String sqlCode = "baseread.dao.BookCategoryDao.insertDataToTree.addCategory";

		Object[] paras =
		{ vo.getCateId(), vo.getCateName(), vo.getCatalogType(),
				vo.getParentId(), vo.getPicUrl(), new Integer(vo.getSortId()),
				vo.getCateParentId(), vo.getDescription(),
				vo.getBusinessTime() };

		try
		{
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		}
		catch (DAOException e)
		{
			log.error("执行添加专区为货架时发生异常，创建表失败", e);
			throw new BOException("执行添加专区为货架时发生异常", e);
		}

		if (log.isDebugEnabled())
		{
			log.debug("用于添加专区为货架,结束");
		}
	}

    /**
	 * 用于统一更新父目录为000的货架id
	 * 
	 * @param vo
	 * @throws BOException
	 */
    public void updatedCategoryParentId() throws BOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("用于统一更新父目录为000的货架id,开始");
        }

        // 更新栏目信息
        // update t_rb_category_new n set n.parentid = (select c.id from
        // t_rb_category_new c where c.categoryid = n.parentcategoryid) where
        // n.parentid='000' and n.catalogtype != '8';
        String sqlCode = "baseread.dao.BookCategoryDao.insertDataToTree.updatedCategoryParentId";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, null);
        }
        catch (DAOException e)
        {
            log.error("执行更新父目录为000的货架id时发生异常，创建表失败", e);
            throw new BOException("执行更新父目录为000的货架id时发生异常", e);
        }

        if (log.isDebugEnabled())
        {
            log.debug("用于统一更新父目录为000的货架id,结束");
        }
    }
    
    /**
     * 用于删除指定货架
     * 
     * @return
     * @throws DAOException
     */
    public void delBookCategoryItem(String categoryId) throws DAOException
    {

        if (log.isDebugEnabled())
        {
        	log.debug("delBookCategoryItem(" + categoryId + ") is starting ...");
        }

        String sqlCode = "com.aspire.dotcard.baseread.dao.BookCategoryDao.delBookCategoryItem";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode,
                                              new Object[] { categoryId });
        }
        catch (DAOException e)
        {
            throw new DAOException("删除指定货架发生异常:", e);
        }
    }
}
