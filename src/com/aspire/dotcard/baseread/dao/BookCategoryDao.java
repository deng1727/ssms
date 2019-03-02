
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
     * ���� ר����Ϣ
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

		// ������sql�����Ҫ�滻�Ĳ���,
		Object[] paras =
		{ cate.getCateId(), cate.getCateName(), cate.getDescription(),
				cate.getCateParentId(), cate.getPicUrl(),
				cate.getBusinessTime()};
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

    /**
	 * �޸�
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

		// ������sql�����Ҫ�滻�Ĳ���,
		Object[] paras =
		{ cate.getCateName(), cate.getDescription(), cate.getCateParentId(),
				cate.getPicUrl(), cate.getBusinessTime(), cate.getCateId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

    /**
	 * ɾ��
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
        // ɾ��ר����Ϣ
        // delete from T_RB_CATE c where c.cateid=? and c.catetype=?
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookCategoryDao.delete";
        Object[] paras = { cate.getCateId(), cate.getCatalogType() };

        // ɾ��ר��������Ϣ
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
     * ɾ��������Ϣ
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
        // ɾ��ר����Ϣ
        // delete from t_rb_category_new c where c.id=?
        // update t_rb_category_new c set c.delflag=1 where c.id=?   --edit by 12.10.08
        String sqlCode = "baseread.dao.BookCategoryDao.deleteCateGory.del";
        Object[] paras = { cate.getId() };

        // ɾ��ר��������Ϣ
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
     * ���»�������Ʒ����
     * 
     * @param cate
     * @throws Exception
     */
    public void updateCateTotal() throws Exception
    {
        log.debug("update category total books");
        // ɾ��ר����Ϣ
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookCategoryDao.updateTotal";

        DB.getInstance().executeBySQLCode(sqlCode, null);
    }

    /**
     * ��ѯ���д��ڵĻ���
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
            log.error("���ݿ����ʧ��");
            throw new DAOException("���ݿ����ʧ�ܣ�" + e);
        }
        finally
        {
            DB.close(rs);
        }
        return m;
    }

    /**
     * ��ѯ���д��ڵĻ���
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
            log.error("���ݿ����ʧ��");
            throw new DAOException("���ݿ����ʧ�ܣ�" + e);
        }
        finally
        {
            DB.close(rs);
        }
        return m;
    }

    /**
     * ��ѯ���д��ڵĻ���
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
            log.error("���ݿ����ʧ��");
            throw new DAOException("���ݿ����ʧ�ܣ�" + e);
        }
        finally
        {
            DB.close(rs);
        }
        return m;
    }

    /**
     * ����Ƿ����
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
            log.error("���ݿ����ʧ��");
            throw new DAOException("���ݿ����ʧ�ܣ�" + e);

        }
        finally
        {
            DB.close(rs);
        }
        return count == 0 ? false : true;
    }

    /**
     * ɾ����Ʒ��Ϣ���ڸ������а���Ϣ��ɾ���ֻ����ж���Ļ���
     */
    public void cleanOldSimulationDataRank() throws BOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("ɾ����Ʒ��Ϣ���ڸ������а���Ϣ��ɾ���ֻ����ж���Ļ���,��ʼ");
        }

        // ɾ��ģ����Ʒ��
        String sqlCode = "baseread.dao.BookCategoryDao.cleanOldSimulationDataRank.reference";

        // ɾ��ģ����ܱ�
        String delRefSqlCode = "baseread.dao.BookCategoryDao.cleanOldSimulationDataRank.category";

        try
        {
            DB.getInstance()
              .executeMutiBySQLCode(new String[] { sqlCode, delRefSqlCode },
                                    new Object[][] { null, null });
        }
        catch (DAOException e)
        {
            log.error("ִ��ɾ����Ʒ��Ϣ���ڸ������а���Ϣ��ɾ���ֻ����ж���Ļ���ʱSQL�����쳣��ɾ���ɱ�ʧ��", e);
            throw new BOException("ִ��ɾ����Ʒ��Ϣ���ڸ������а���Ϣ��ɾ���ֻ����ж���Ļ���ʱSQL�����쳣", e);
        }

        if (log.isDebugEnabled())
        {
            log.debug("ɾ����Ʒ��Ϣ���ڸ������а���Ϣ��ɾ���ֻ����ж���Ļ���,����");
        }
    }

    /**
     * �Զ�����װģ�����ṹ��
     * 
     */
    public void insertDataToRank() throws BOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("�����Զ�����װģ�����ṹ��,��ʼ");
        }

        // �������а���Ϣ
		// update t_rb_category_new c set c.delflag = 0 where c.catalogtype =
		// '8' and 1 = (select distinct 1 from t_rb_rank_new r where r.rankid =
		// c.categoryid)
		String sqlCode = "baseread.dao.BookCategoryDao.updateDataToRank.rank";
        
        // update t_rb_category_new c set c.categoryname = (select r.rankname
		// from t_rb_rank_new r where r.rankid = c.categoryid) where
		// c.catalogtype = '8' and c.delflag = 0
		String sqlCode0 = "baseread.dao.BookCategoryDao.updateDataToRank.rank2";

        // �������а���Ϣ
        String sqlCode1 = "baseread.dao.BookCategoryDao.insertDataToRank.rank";

        try
        {
            DB.getInstance()
              .executeMutiBySQLCode(new String[] { sqlCode, sqlCode0, sqlCode1 },
                                    new Object[][] { null, null, null });
        }
        catch (DAOException e)
        {
            log.error("ִ���Զ�����װģ�����ṹ��ʱ�����쳣��������ʧ��", e);
            throw new BOException("ִ���Զ�����װģ�����ṹ��ʱ�����쳣", e);
        }

        if (log.isDebugEnabled())
        {
            log.debug("�����Զ�����װģ�����ṹ��,����");
        }
    }

    /**
     * �������а���Ϣ��������Ʒ����
     * 
     */
    public void insertDataByRankToReference() throws BOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("�������а���Ϣ��������Ʒ����,��ʼ");
        }

        // �������а���Ϣ
        String sqlCode = "baseread.dao.BookCategoryDao.insertDataByRankToReference.insert";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, null);
        }
        catch (DAOException e)
        {
            log.error("ִ�и������а���Ϣ��������Ʒ����ʱ�����쳣��������ʧ��", e);
            throw new BOException("ִ�и������а���Ϣ��������Ʒ����ʱ�����쳣", e);
        }

        if (log.isDebugEnabled())
        {
            log.debug("���ڸ������а���Ϣ��������Ʒ����,����");
        }
    }

    /**
     * ��һ������ѯ��ר�����в����ڡ��ڻ��ܱ��д��ڵĻ��ܣ���ɾ��
     */
    public List cleanOldSimulationDataTree() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("��ѯ��ר�����в����ڡ��ڻ��ܱ��д��ڵĻ���,��ʼ");
        }

        // ɾ��ģ����Ʒ��
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
            log.error("��ѯ��ר�����в����ڡ��ڻ��ܱ��д��ڵĻ��ܲ���ʧ��");
            throw new DAOException("��ѯ��ר�����в����ڡ��ڻ��ܱ��д��ڵĻ��ܲ���ʧ�ܣ�" + e);
        }
        catch (SQLException e)
        {
            log.error("��ѯ��ר�����в����ڡ��ڻ��ܱ��д��ڵĻ��ܲ���ʧ��");
            throw new DAOException("��ѯ��ר�����в����ڡ��ڻ��ܱ��д��ڵĻ��ܲ���ʧ�ܣ�" + e);
        }
        finally
        {
            DB.close(rs);
        }

        if (log.isDebugEnabled())
        {
            log.debug("��ѯ��ר�����в����ڡ��ڻ��ܱ��д��ڵĻ���,����");
        }
        return list;

    }

    /**
     * �ڶ���������ר����Ϣ����������»����д��ڵĻ���
     * 
     * @param allCate ר������ܹ��е�
     * @param allArea ר���еĻ���
     */
    public void updateDataToTree() throws BOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("����ר����Ϣ����������»����д��ڵĻ���,��ʼ");
        }

        Map allTree = null;
        Map allCate = null;

        try
        {
            // ר����Y��
            allTree = BookCategoryDao.getInstance().queryAllCateTree();

            // ���^id�c؛��id�����Pϵ
            allCate = BookCategoryDao.getInstance().queryAllId();
        }
        catch (Exception e)
        {
            log.error("ִ���Զ�����װģ�����ṹ��ʱ�����쳣��������ʧ��", e);
            throw new BOException("ִ���Զ�����װģ�����ṹ��ʱ�����쳣", e);
        }

        Set allTreeSet = allTree.keySet();

        for (Iterator iter = allTreeSet.iterator(); iter.hasNext();)
        {
            String key = ( String ) iter.next();
            
            RCategoryVO vo = ( RCategoryVO )allTree.get(key);

            // ����錣�^�ĸ�Ŀ䛣��ŵ����^��؛����
            if ("".equals(vo.getCateParentId()) || null == vo.getCateParentId())
            {
                vo.setParentId("101");
            }
            // �����Ǹ�·�����жϵ�ǰ��û�����ĸ�Ŀ¼������д���
            else if (allCate.containsKey(vo.getCateParentId()))
            {
                vo.setParentId(String.valueOf(allCate.get(vo.getCateParentId())));
            }
            // ������Ϊ000��������ַ���֮��ͳһ���
            else
            {
                vo.setParentId("000");
            }

            // ���Дஔǰ؛�����Ƿ�����@�����^�����Զ�������߀�Ǹ���
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

        // ����ͳһ���¸�Ŀ¼Ϊ000�Ļ���id
        this.updatedCategoryParentId();

        if (log.isDebugEnabled())
        {
            log.debug("����ר����Ϣ����������»����д��ڵĻ���,����");
        }
    }

    /**
     * ���ר��Ϊ����
     * 
     * @param vo
     * @throws BOException
     */
    public void updateCategory(RCategoryVO vo) throws BOException
    {
		if (log.isDebugEnabled())
		{
			log.debug("���ڸ���ר������,��ʼ");
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
			log.error("ִ�и���ר������ʱ�����쳣��������ʧ��", e);
			throw new BOException("ִ�и���ר������ʱ�����쳣", e);
		}

		if (log.isDebugEnabled())
		{
			log.debug("���ڸ���ר������,����");
		}
	}

    /**
	 * ���ר��Ϊ����
	 * 
	 * @param vo
	 * @throws BOException
	 */
    public void addCategory(RCategoryVO vo) throws BOException
    {
		if (log.isDebugEnabled())
		{
			log.debug("�������ר��Ϊ����,��ʼ");
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
			log.error("ִ�����ר��Ϊ����ʱ�����쳣��������ʧ��", e);
			throw new BOException("ִ�����ר��Ϊ����ʱ�����쳣", e);
		}

		if (log.isDebugEnabled())
		{
			log.debug("�������ר��Ϊ����,����");
		}
	}

    /**
	 * ����ͳһ���¸�Ŀ¼Ϊ000�Ļ���id
	 * 
	 * @param vo
	 * @throws BOException
	 */
    public void updatedCategoryParentId() throws BOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("����ͳһ���¸�Ŀ¼Ϊ000�Ļ���id,��ʼ");
        }

        // ������Ŀ��Ϣ
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
            log.error("ִ�и��¸�Ŀ¼Ϊ000�Ļ���idʱ�����쳣��������ʧ��", e);
            throw new BOException("ִ�и��¸�Ŀ¼Ϊ000�Ļ���idʱ�����쳣", e);
        }

        if (log.isDebugEnabled())
        {
            log.debug("����ͳһ���¸�Ŀ¼Ϊ000�Ļ���id,����");
        }
    }
    
    /**
     * ����ɾ��ָ������
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
            throw new DAOException("ɾ��ָ�����ܷ����쳣:", e);
        }
    }
}
