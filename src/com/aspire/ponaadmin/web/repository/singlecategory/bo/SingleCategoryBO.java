/**
 * SSMS
 * com.aspire.ponaadmin.web.repository.camonitor SingleCategoryBO.java
 * Apr 20, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.repository.singlecategory.bo;

import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.category.ui.CategoryQueryDAO;
import com.aspire.ponaadmin.web.repository.singlecategory.dao.SingleCategoryDAO;
import com.aspire.ponaadmin.web.repository.singlecategory.vo.SingleCategoryVO;

/**
 * @author tungke
 *
 */
public class SingleCategoryBO {

	private  static SingleCategoryBO instance = new SingleCategoryBO();
	private SingleCategoryBO(){
		
	}
	
	public static SingleCategoryBO getInstance(){
		return instance;
	}
	/**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(SingleCategoryBO.class) ;

    
    /**
     * ��ȡ���л���
     * @return
     * @throws BOException
     */
    public List getAllCategoryList(String userId,String type) throws BOException{
    	List allCategorylist = null;
    	try {
			 allCategorylist = SingleCategoryDAO.getInstance().getCategorySingle(userId,type);
		} catch(Exception e)
        {
			logger.error("SingleCategoryBO.getAllCategoryList failed!"+ e);
            throw new BOException("SingleCategoryBO.getAllCategoryList failed!", e);
        }
		return allCategorylist;
    }

	public void querySingleCategoryUserGroupList(PageResult page, String code,
			String name,String type) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("SingleCategoryBO.querySingleCategoryUserGroupList() is start...");
        }

        try
        {
            // ����SingleCategoryDAO���в�ѯ
        	SingleCategoryDAO.getInstance().querySingleCategoryUserGroupList(page,code,name,type);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����SingleCategoryDAO���в�ѯʱ�������ݿ��쳣��");
        }
		
	}

	public void addSingleCategoryUserGroupByCode(String code, String name,
			String categoryId,String type) throws BOException {
		
		if (logger.isDebugEnabled())
        {
            logger.debug("SingleCategoryBO.addSingleCategoryUserGroupByCode() is start...");
        }

        try
        {
            // ����SingleCategoryDAO���в�ѯs
        	SingleCategoryDAO.getInstance().addSingleCategoryUserGroupByCode(code,name,type);
        	
        	String []categoryIds=categoryId.split(",");
        	List list=new ArrayList();
        	for(int i=0;i<categoryIds.length;i++)
        	{
        		SingleCategoryVO  vo=SingleCategoryDAO.getInstance().getCategoryByCategoryId(categoryIds[i]);
        		//ȥ�ظ�
        		if(!list.contains(categoryIds[i]))
        		{
	        		if(vo.getId()!=null&&!"".equals(vo.getId()))
	        		{
	        			SingleCategoryDAO.getInstance().addSingleCategoryByCode(vo,code,type);
	        			list.add(vo.getId());
	        		}
        		}
        	}
        	
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���������û���ʱ�������ݿ��쳣��");
        }
		
	}

	public void delSingleCategoryUserGroupByCode(String code,String type)  throws BOException {
		if (logger.isDebugEnabled())
        {
            logger.debug("SingleCategoryBO.delSingleCategoryUserGroupByCode() is start...");
        }

        try
        {
            // ����SingleCategoryDAO���в�ѯs
        	SingleCategoryDAO.getInstance().delSingleCategoryUserGroupByCode(code,type);
        	
        	SingleCategoryDAO.getInstance().delSingleCategoryByCode(code,type);
        	
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("ɾ�������û���ʱ�������ݿ��쳣��");
        }
		
	}

	public String querySingleCategoryIdsByCode(String code,String type) throws BOException{
		String categoryId = "";
    	try {
    		categoryId = SingleCategoryDAO.getInstance().querySingleCategoryIdsByCode(code,type);
		} catch(Exception e)
        {
			logger.error("SingleCategoryBO.querySingleCategoryIdsByCode failed!"+ e);
            throw new BOException("SingleCategoryBO.querySingleCategoryIdsByCode failed!", e);
        }
		return categoryId;
	}

	public void updateSingleCategoryUserGroupByCode(String code, String name,
			String categoryId,String type)throws BOException {
		if (logger.isDebugEnabled())
        {
            logger.debug("SingleCategoryBO.updateSingleCategoryUserGroupByCode() is start...");
        }

        try
        {
        	
        	
        	SingleCategoryDAO.getInstance().delSingleCategoryUserGroupByCode(code,type);
        	
        	SingleCategoryDAO.getInstance().delSingleCategoryByCode(code,type);
        	
        	addSingleCategoryUserGroupByCode(code, name, categoryId,type);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���������û���ʱ�������ݿ��쳣��");
        }
	}
	
	/**
     * ���ݻ��ܱ��룬�������Ʋ����Ӧ���ܱ���Ϣ
     * 
     * @param categoryId ���ܱ���
     * @param categoryName ��������
     * @return
     * @throws BOException
     */
    public void queryCategoryList(PageResult page, String categoryId,
                                        String categoryName,String relation)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("SingleCategoryBO.queryCategoryList("+categoryId+","+categoryName+","+relation+") is start...");
        }

        try
        {
            // ����CategoryQueryDAO���в�ѯ
        	SingleCategoryDAO.getInstance()
                                    .queryCategoryList(page,
                                                       categoryId,
                                                       categoryName,relation);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ��ܱ��룬�������Ʋ����Ӧ���ܱ���Ϣʱ�������ݿ��쳣��");
        }
    }
   
}
