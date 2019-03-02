/*
 * 
 */

package com.aspire.ponaadmin.web.channeladmin.dao;

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
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryDAO;
import com.aspire.ponaadmin.web.system.Config;

/**
 * @author x_wangml
 * 
 */
public class CategoryQueryDAO
{

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(CategoryQueryDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static CategoryQueryDAO instance = new CategoryQueryDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private CategoryQueryDAO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CategoryQueryDAO getInstance()
    {

        return instance;
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class CategoryRulePageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

            Category vo = ( Category ) content;
            vo.setId(rs.getString("id"));
            vo.setCategoryID(rs.getString("categoryID"));
            vo.setName(rs.getString("name"));
            vo.setDesc(rs.getString("descs"));
            try
            {
                vo.setNamePath(CategoryDAO.getInstance().getCategoryNamePathByID(vo.getId()));
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
     * ���ݻ��ܱ��룬�������Ʋ����Ӧ���ܱ���Ϣ
     * 
     * @param categoryId ���ܱ���
     * @param categoryName ��������
     * @return
     * @throws DAOException
     */
    public void queryCategoryList(PageResult page, String categoryId, String categoryName)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryCategoryUpdateList(" + categoryId + "," + categoryName
                         + ") is starting ...");
        }
        
        // select * from t_r_category t where 1=1
        String sqlCode = "com.aspire.ponaadmin.web.channeladmin.dao.CategoryQueryDAO.queryCategoryList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            
            
            //����������sql�Ͳ���
           
            if (!"".equals(categoryId))
            {
               // sql += " and t.id ='" + categoryId + "'";
            	sqlBuffer.append(" and t.id =? ");
            	paras.add(categoryId);
            }
            if (!"".equals(categoryName))
            {
                //sql += " and t.name like('%" + categoryName + "%')";
            	sqlBuffer.append(" and t.name like ? ");
            	paras.add("%"+SQLUtil.escape(categoryName)+"%");
            }
            sqlBuffer.append(" connect by prior t.categoryid = t.parentcategoryid ");
            //���������л�ȡ�����̸�����ID
            String channelRootCategoryId = Config.getInstance().getModuleConfig()
			.getItemValue("channelRootCategoryId");
            if(channelRootCategoryId == null || "".equals(channelRootCategoryId.trim())){
            	 sqlBuffer.append(" start with t.parentcategoryid is null");
            }else{
            	 sqlBuffer.append(" start with t.parentcategoryid =?");
            	 paras.add(channelRootCategoryId);
            }
            //page.excute(sql, null, new CategoryRulePageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new CategoryRulePageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }
    
    /**
     * �жϸû���ID�Ƿ��Ѿ��������������Ӧ�Ļ����¡�
     * @param categoryId
     * @return
     * @throws DAOException
     */
    public boolean isExistInCategory(String categoryId)throws DAOException{
    	StringBuffer sqlBuffer = new StringBuffer("select count(1) from t_r_category t where 1=1 and t.DELFLAG=0") ;
    	List paras = new ArrayList();
    	 if (!"".equals(categoryId))
         {
         	sqlBuffer.append(" and t.categoryID =? ");
         	paras.add(categoryId);
         }
    	 sqlBuffer.append(" connect by prior t.categoryid = t.parentcategoryid ");
         //���������л�ȡ�����̸�����ID
         String channelRootCategoryId = Config.getInstance().getModuleConfig()
			.getItemValue("channelRootCategoryId");
         if(channelRootCategoryId == null || "".equals(channelRootCategoryId.trim())){
         	 sqlBuffer.append(" start with t.parentcategoryid is null");
         }else{
         	 sqlBuffer.append(" start with t.parentcategoryid =?");
         	 paras.add(channelRootCategoryId);
         }
         boolean flag = false;
         try {
        	ResultSet rSet = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());
        	if(rSet != null&&rSet.next()){
        		int count = rSet.getInt(1);
        		if(count >0){
        			flag = true;
        		}
        	}
        	 return flag;
		} catch (Exception e) {
			throw new DAOException("�жϸû���ID�Ƿ��Ѿ��������������Ӧ�Ļ�����");
		}
         
    }
}
