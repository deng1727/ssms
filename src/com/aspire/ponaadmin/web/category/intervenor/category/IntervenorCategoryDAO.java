/*
 * 
 */

package com.aspire.ponaadmin.web.category.intervenor.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.category.intervenor.IntervenorVO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryDAO;

/**
 * ������񵥶�Ӧ��ϵ���ݲ�����
 * 
 * @author x_wangml
 * 
 */
public class IntervenorCategoryDAO
{

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(IntervenorCategoryDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static IntervenorCategoryDAO instance = new IntervenorCategoryDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private IntervenorCategoryDAO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static IntervenorCategoryDAO getInstance()
    {

        return instance;
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class CategoryPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

            Category vo = ( Category ) content;
            String type = rs.getString("type");
            if("2".equals(type))
            {
				vo.setId(rs.getString("id"));
				vo.setCategoryID(rs.getString("categoryID"));
				String name = rs.getString("name");
				
				String rank_type = name.substring(0, name
						.lastIndexOf("_"));
				
				if("rank_new".equals(rank_type))
				{
					rank_type = "����";
				}
				else if ("rank_hot".equals(rank_type))
				{
					rank_type = "����";
				}
				else if ("rank_scores".equals(rank_type))
				{
					rank_type = "���";
				}
				else if ("rank_all".equals(rank_type))
				{
					rank_type = "�ܰ�";
				}
				else if ("rank_fee".equals(rank_type))
				{
					rank_type = "";
				}
				
				String rank_name = name.substring(name
						.lastIndexOf("_")+1);
				
				if("appGame".equals(rank_name))
				{
					rank_name = "��Ϸ";
				}
				else if("appSoftWare".equals(rank_name)|"appTheme".equals(rank_name))
				{
					rank_name = "���";
				}
				else if("appAll".equals(rank_name))
				{
					rank_name = "ȫ����";
				}
				else if("appAllPriceFree".equals(rank_name))
				{
					rank_name = "���";
				}
				else if("appAllPricePay".equals(rank_name))
				{
					rank_name = "�շ�";
				}
				
				vo.setName("��Ʒ��" + rank_name + rank_type + "��");
				vo.setDesc("��Ʒ��" + rank_name + rank_type + "��");
				vo.setNamePath("��Ʒ��" + rank_name + rank_type + "��");
				vo.setType(type);
			}
            else
            {
                vo.setId(rs.getString("id"));
                vo.setCategoryID(rs.getString("categoryID"));
                vo.setName(rs.getString("name"));
                String temp = rs.getString("descs");
                if (null != temp && temp.length() > 20)
                {
                    temp = temp.substring(0, 20) + "...";
                }
                vo.setDesc(temp);
                try
                {
                    vo.setNamePath(CategoryDAO.getInstance()
                                              .getCategoryNamePathByID(vo.getId()));
                }
                catch (DAOException e)
                {
                    e.printStackTrace();
                }
                vo.setType(type);
            }
        }

        public Object createObject()
        {

            return new Category();
        }
    }
    
    
    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class AddCategoryPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
			
			Category vo = (Category) content;
			vo.setId(rs.getString("id"));
			vo.setCategoryID(rs.getString("categoryID"));
			vo.setName(rs.getString("name"));
			String temp = rs.getString("descs");
			if (null != temp && temp.length() > 20)
			{
				temp = temp.substring(0, 20) + "...";
			}
			vo.setDesc(temp);
			try
			{
				vo.setNamePath(CategoryDAO.getInstance()
						.getCategoryNamePathByID(vo.getId()));
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
		}

        public Object createObject()
        {

            return new Category();
        }
    }

    /**
	 * Ӧ�����ҳ��ȡVO��ʵ����
	 */
    private class IntervenorPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

            IntervenorVO vo = ( IntervenorVO ) content;
            vo.setId(rs.getInt("id"));
            vo.setName(rs.getString("name"));
            vo.setSortId(rs.getInt("sortId"));
            vo.setStartDate(rs.getDate("startDate").toString());
            vo.setEndDate(rs.getDate("endDate").toString());
            vo.setStartSortId(rs.getInt("startSortId"));
            vo.setEndSortId(rs.getInt("endSortId"));
        }

        public Object createObject()
        {

            return new IntervenorVO();
        }
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class IntervenorIDVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

            IntervenorVO vo = ( IntervenorVO ) content;
            vo.setId(rs.getInt("id"));
            vo.setName(rs.getString("name"));
            vo.setStartDate(rs.getDate("startDate").toString());
            vo.setEndDate(rs.getDate("endDate").toString());
            vo.setStartSortId(rs.getInt("startSortId"));
            vo.setEndSortId(rs.getInt("endSortId"));
        }

        public Object createObject()
        {

            return new IntervenorVO();
        }
    }

    /**
     * ��ѯ���б���Ϣ
     * 
     * @param page
     * @param id �񵥱��
     * @param name ������
     * @throws DAOException
     */
    public void queryCategoryVOList(PageResult page, String id, String name)
                    throws DAOException
    {
		// select * from (select distinct t.id, t.name, t.descs, t.categoryID,
		// i.type from t_r_category t, t_Intervenor_Category_Map i, t_intervenor
		// r where t.delflag = 0 and t.id = i.categoryid and i.intervenorid =
		// r.id and r.isdel = 0 union all select distinct i.categoryid id,
		// i.categoryid name, i.categoryid descs, i.categoryid categoryID,
		// i.type from t_Intervenor_Category_Map i, t_intervenor r where
		// i.intervenorid = r.id and r.isdel = 0 and i.type = 2) t where 1=1
		String sqlCode = "intervenor.IntervenorCategoryDAO.queryCategoryVOList().SELECT";
		String sql = null;
		
		try
		{
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);
			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();
			// ����������sql�Ͳ���
			
			if (!"".equals(id))
			{
				// sql += " and t.id ='" + id + "'";
				sqlBuffer.append(" and t.id =? ");
				paras.add(id);
			}
			if (!"".equals(name))
			{
				// sql += " and t.name like('%" + name + "%')";
				sqlBuffer.append(" and t.name like  ? ");
				paras.add("%" + name + "%");
			}
			
			// sql += " order by t.id asc";
			sqlBuffer.append(" order by t.id asc");
			
			// page.excute(sql, null, new CategoryPageVO());
			page.excute(sqlBuffer.toString(), paras.toArray(),
					new CategoryPageVO());
		}
		catch (DataAccessException e)
		{
			throw new DAOException(
					"��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�", e);
		}
	}
    
    public void queryCategoryVOList(List<Category> list )
    		throws DAOException, SQLException
    		{
    	
    	String sqlCode = "intervenor.IntervenorCategoryDAO.queryCategoryVOList().SELECT";
    	String sql = null;
    	ResultSet rs =null;
    	Connection conn =null;
    	PreparedStatement statement =null;
    	try
    	{
    		sql = SQLCode.getInstance().getSQLStatement(sqlCode);
    		list.clear();
    		conn = DB.getInstance().getConnection();
    		statement = conn.prepareStatement(sql);
    		DB.getInstance().prepareParams(statement, null);
    		rs = statement.executeQuery();
    		while(rs.next()){
    			Category c=new Category();
    			try{
    				c.setId(rs.getString("id").trim());
    				c.setType(rs.getString("type").trim());
    			}catch(NullPointerException e){
    				e.printStackTrace();
    			}
    			
    			list.add(c);
    		}
    	}
    	catch (DataAccessException e)
    	{
    		throw new DAOException(
    				"��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�", e);
    	}finally
        {
            DB.close(rs, statement, conn);
        }
    		}

    /**
	 * ��ѯ���б���Ϣ
	 * 
	 * @param page
	 * @param id
	 *            �񵥱��
	 * @param categoryid
	 *            categoryid
	 * @throws DAOException
	 */
    public void queryCategoryList(PageResult page, String id, String categoryid)
                    throws DAOException
    {

        // select * from t_r_category t where 1=1
        String sqlCode = "autoupdate.CategoryQueryDAO.queryCategoryList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            if (!"".equals(id))
            {
               // sql += " and t.id ='" + id + "'";
            	sql += " and t.id in (" + id + ")";// modify by aiyan 2012-03-07
            }
            if (!"".equals(categoryid))
            {
                sql += " and t.categoryid ='" + categoryid + "'";
            }

            sql += " order by t.id asc";

            page.excute(sql, null, new AddCategoryPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * ��ѯ�񵥶�Ӧ�ĸ�Ԥ����
     * 
     * @param page
     * @param id ��id
     * @throws DAOException
     */
    public void queryIntervenorVOByCategory(PageResult page, String id)
                    throws DAOException
    {

        // select * from t_intervenor_category_map c, t_intervenor i where
        // c.categoryid = ? and i.id = c.intervenorid order by c.sortid
        // asc
        String sqlCode = "intervenor.IntervenorCategoryDAO.queryIntervenorVOByCategory().SELECT";

        page.excuteBySQLCode(sqlCode,
                             new Object[] { id },
                             new IntervenorPageVO());
    }

    /**
     * �����������ʱ��ʾ�������б�
     * 
     * @param page
     * @param id ��ѯ����
     * @param name ��ѯ����
     * @throws DAOException
     */
    public void queryIntervenorVOList(PageResult page, String id, String name)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryIntervenorVOList(id = " + id + ", name = "
                         + name + ") is starting ...");
        }

        // select * from t_intervenor t where 1=1
        String sqlCode = "intervenor.IntervenorDAO.queryIntervenorVOList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            if (!"".equals(id))
            {
                sql += " and t.id = '" + id + "'";
            }
            if (!"".equals(name))
            {
                sql += " and t.name like '%" + name + "%'";
            }

            page.excute(sql, null, new IntervenorIDVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
        catch (DAOException e)
        {
            throw new DAOException("��ѯ�����������ʱ��ʾ�������б���Ϣ�����쳣:", e);
        }
    }

    /**
     * �õ����б��˹���Ԥ�İ�
     * 
     * @return
     * @throws DAOException
     */
    public List getIntervenorCategory() throws DAOException
    {
		
		if (logger.isDebugEnabled())
		{
			logger.debug("getIntervenorCategory() is starting ...");
		}
		
		// select distinct c.categoryid from t_intervenor i,
		// t_intervenor_category_map c, t_r_category g where i.startdate <=
		// sysdate and i.enddate >= sysdate and i.id = c.intervenorid and
		// i.isdel=0 and c.categoryid = g.id and g.delflag = 0
		String sqlCode = "intervenor.IntervenorDAO.getIntervenorCategory().SELECT";
		
		ResultSet rs = null;
		List retList = new ArrayList();
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			
			while (rs.next())
			{
				retList.add(rs.getString("CATEGORYID"));
			}
		}
		catch (DAOException e)
		{
			throw new DAOException("��������id�õ���Ԥ������Ϣ��ѯ�����쳣:", e);
		}
		catch (SQLException e)
		{
			throw new DAOException("��������id�õ���Ԥ������Ϣ��ѯ�����쳣:", e);
		}
		finally
		{
			DB.close(rs);
		}
		
		return retList;
	}

    /**
	 * ���ð���������������Ϣ
	 * 
	 * @param categoryId
	 *            �񵥱���
	 * @param intervenorId
	 *            ��������
	 * @param sortId
	 *            ��������
	 * @throws DAOException
	 */
    public void setSortId(String categoryId, String intervenorId, String sortId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("setSortId(categoryId��" + categoryId
                         + ", intervenorId=" + intervenorId + ", sortId="
                         + sortId + ") is starting ...");
        }

        // update t_intervenor_category_map t set t.sortid=? where
        // t.categoryid=? and t.intervenorid=?
        String sqlCode = "intervenor.IntervenorCategoryDAO.setSortId().UPDATE";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode,
                                              new Object[] { sortId,
                                                              categoryId,
                                                              intervenorId });
        }
        catch (DAOException e)
        {
            logger.error("IntervenorCategoryDAO.setSortId():", e);
            throw new DAOException(e);
        }
    }

    /**
     * �������������񵥵Ķ�Ӧ��ϵ
     * 
     * @param vo
     * @return
     * @throws DAOException
     */
    public int addCategoryVOByIntervenor(String categoryId, String intervenorId, String type)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addCategoryVOByIntervenor(categoryId=" + categoryId
                         + ", intervenorId=" + intervenorId
                         + ") is starting ...");
        }
        String sqlCode = null;
        if("2".equals(type))
        {
			// insert into t_intervenor_category_map(INTERVENORID, CATEGORYID,
			// SORTID, type) select ?, ?, decode(max(sortid), null, 1,
			// max(sortid) + 1), 2 from t_intervenor_category_map where
			// CATEGORYID = ?
			sqlCode = "intervenor.IntervenorCategoryDAO.addCategoryVOByIntervenor().INSERT_type2";
		}
        else
        {
			// insert into t_intervenor_category_map
			// (INTERVENORID,CATEGORYID,SORTID) select ?,?, decode(max(sortid),
			// null, 1, max(sortid)+ 1) from
			// t_intervenor_category_map where CATEGORYID=?
			sqlCode = "intervenor.IntervenorCategoryDAO.addCategoryVOByIntervenor().INSERT";
		}
        int num = 0;

        try
        {
            num = DB.getInstance()
                    .executeBySQLCode(sqlCode,
                                      new Object[] { intervenorId, categoryId,
                                                      categoryId });
        }
        catch (DAOException e)
        {
            logger.error("IntervenorCategoryDAO.addCategoryVOByIntervenor():",
                         e);
            throw new DAOException(e);
        }

        return num;
    }

    /**
     * ����ɾ�����е�������Ϣ
     * 
     * @param categoryId �񵥱���
     * @param intervenorId ��������
     * @throws DAOException
     */
    public int delIntervenorIdByCategoryId(String categoryId,
                                           String intervenorId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("delIntervenorIdByCategoryId(categoryId��" + categoryId
                         + ", intervenorId=" + intervenorId
                         + ") is starting ...");
        }

        // delete from t_intervenor_category_map t where t.categoryid=? and
        // t.intervenorid=?
        String sqlCode = "intervenor.IntervenorCategoryDAO.delIntervenorIdByCategoryId().DELETE";

        int num = 0;

        try
        {
            num = DB.getInstance()
                    .executeBySQLCode(sqlCode,
                                      new Object[] { categoryId, intervenorId });
        }
        catch (DAOException e)
        {
            logger.error("IntervenorCategoryDAO.delIntervenorIdByCategoryId():",
                         e);
            throw new DAOException(e);
        }

        return num;

    }

    /**
     * �鿴�����Ƿ����ָ���˹���Ԥ����
     * 
     * @param categoryId ��id
     * @param intervenorId ����id
     * @return
     * @throws DAOException
     */
    public boolean hasInternor(String categoryId, String intervenorId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug(" hasInternor(categoryId��" + categoryId
                         + ", intervenorId=" + intervenorId + ")");
        }

        // select count(*) from t_intervenor_category_map t where t.categoryid=?
        // and t.intervenorid=?
        String sqlCode = "intervenor.IntervenorCategoryDAO.hasInternor().SELECT";
        ResultSet rs = null;
        int i = 0;

        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode(sqlCode,
                                   new Object[] { categoryId, intervenorId });

            if (rs.next())
            {
                i = rs.getInt(1);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("�鿴�����Ƿ����ָ���˹���Ԥ����ʱ�����쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("�鿴�����Ƿ����ָ���˹���Ԥ����ʱ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        if (i == 0)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    
    
    /**
     * �õ���Ʒ����ܶ�Ӧ���˹���Ԥ�б�
     * 
     * @param androidCategoryId
     * @return
     * @throws DAOException
     */
    public List<IntervenorVO> getIntervenorCategoryVO(String androidCategoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getIntervenorCategoryVO(" + androidCategoryId + ") is starting ...");
        }

        // select i.id,i.name,c.sortid,i.startsortid,i.endsortid from t_intervenor i, t_intervenor_category_map c where i.startdate <= sysdate and i.enddate >= sysdate and i.id = c.intervenorid and i.isdel=0 and categoryid=? order by c.sortid
        String sqlCode = "intervenor.IntervenorDAO.getIntervenorCategoryVO().SELECT";

        ResultSet rs = null;
        List<IntervenorVO> retList = new ArrayList<IntervenorVO>();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{androidCategoryId});

            while (rs.next())
            {
                IntervenorVO vo = new IntervenorVO();
                vo.setId(rs.getInt("id"));
                vo.setName(rs.getString("name"));
                vo.setSortId(rs.getInt("sortId"));
                vo.setStartSortId(rs.getInt("startSortId"));
                vo.setEndSortId(rs.getInt("endSortId"));
                retList.add(vo);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("�õ���Ʒ����ܶ�Ӧ���˹���Ԥ�б��ѯ�����쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("�õ���Ʒ����ܶ�Ӧ���˹���Ԥ�б��ѯ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return retList;
    }
    /**
     * ͨ��id,��ѯcategory���е���Ϣ
     * @param id
     * @throws DAOException 
     */
    public String queryCategoryListById(String id) throws DAOException{
    	 if (logger.isDebugEnabled())
         {
             logger.debug("queryCategory(" + id + ") is starting ...");
         }

         // select categoryid from categoryid where id \= ? 
         String sqlCode = "intervenor.IntervenorDAO.getqueryCategory().SELECT";
         System.out.println(sqlCode+"================="+id);
         ResultSet rs = null;
         String categoryid = "";
         try
         {
             rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{id});

             while (rs.next())
             {
                categoryid = rs.getString("categoryid");
             }
         }
         catch (Exception e)
         {
             throw new DAOException("��ѯt_r_category�е�categoryid����:", e);
         }
         finally
         {
             DB.close(rs);
         }

         return categoryid;
    	
    }
    /**
     * ��Messages���в���һ��ѶϢ
     * @param categoryid
     * @return
     * @throws DAOException
     */
    public int insertMessagesList(String categoryid) throws DAOException {
    	if (logger.isDebugEnabled())
        {
            logger.debug("insertMessagesList(" + categoryid + ") is starting ...");
        }

        // insert into t_a_messages (id,type,message,status) values (SEQ_T_A_MESSAGES.NEXTVAL,'BatchRefModifyReq',?,'-1') 
        String sqlCode = "intervenor.IntervenorDAO.insertMessagesList().INSERT";
        System.out.println(sqlCode +"====================="+categoryid);
        int status = 0 ;
        try
        {
            status = DB.getInstance().executeBySQLCode(sqlCode, new Object[]{categoryid});
        }
        catch (Exception e)
        {
            throw new DAOException("����t_a_messages�г���:", e);
        }
       

        return status;
    }
    
}
