/*
 * 
 */

package com.aspire.ponaadmin.web.category.intervenor.category;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.category.intervenor.IntervenorVO;
import com.aspire.ponaadmin.web.category.intervenor.gcontent.IntervenorGcontentDAO;
import com.aspire.ponaadmin.web.category.intervenor.gcontent.IntervenorGcontentVO;
import com.aspire.ponaadmin.web.repository.Category;

/**
 * @author x_wangml
 * 
 */
public class IntervenorCategoryBO
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(IntervenorCategoryBO.class);

    private static IntervenorCategoryBO instance = new IntervenorCategoryBO();

    private IntervenorCategoryBO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static IntervenorCategoryBO getInstance()
    {

        return instance;
    }

    /**
     * ��ѯ���б���Ϣ
     * 
     * @param page
     * @param id �񵥱��
     * @param name ������
     * @throws BOException
     */
    public void queryCategoryVOList(PageResult page, String id, String name)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.queryCategoryVOList(" + id
                         + name + ") is start...");
        }

        try
        {
            // ����IntervenorCategoryDAO���в�ѯ
            IntervenorCategoryDAO.getInstance().queryCategoryVOList(page,
                                                                    id,
                                                                    name);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��������id�õ������������б�ʱ�������ݿ��쳣��");
        }
    }
    /**
     * ��ѯ���б���Ϣ
     * 
     * @param list װ�ذ�vo����
     */
    public void queryCategoryVOList(List<Category> list)
    		throws Exception
    		{
    	
    	try
    	{
    		// ����IntervenorCategoryDAO���в�ѯ
    		IntervenorCategoryDAO.getInstance().queryCategoryVOList(list);
    	}
    	catch (DAOException e)
    	{
    		logger.error(e);
    		throw new BOException("��������id�õ������������б�ʱ�������ݿ��쳣��");
    	}
    		}
    
    /**
     * ��ѯ���б���Ϣ
     * 
     * @param page
     * @param id �񵥱��
     * @param categoryid categoryid
     * @throws BOException
     */
    public void queryCategoryList(PageResult page, String id, String categoryid)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.queryCategoryList(" + id
                         + categoryid + ") is start...");
        }

        try
        {
            // ����IntervenorCategoryDAO���в�ѯ
            IntervenorCategoryDAO.getInstance().queryCategoryList(page,
                                                                    id,
                                                                    categoryid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��������id�õ������������б�ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ��ѯ�񵥶�Ӧ�ĸ�Ԥ����
     * 
     * @param page
     * @param id ��id
     * @throws BOException
     */
    public void queryIntervenorVOByCategory(PageResult page, String id)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.queryIntervenorVOByCategory("
                         + id + ") is start...");
        }

        try
        {
            // ����IntervenorCategoryDAO���в�ѯ
            IntervenorCategoryDAO.getInstance()
                                 .queryIntervenorVOByCategory(page, id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ�񵥶�Ӧ�ĸ�Ԥ����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * �����������ʱ��ʾ�������б�
     * 
     * @param page
     * @param id ��ѯ����
     * @param name ��ѯ����
     * @throws BOException
     */
    public void queryIntervenorVOList(PageResult page, String id, String name)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.queryIntervenorVOList(id = "
                         + id + ", name = " + name + ") is start...");
        }

        try
        {
            // ����IntervenorCategoryDAO���в�ѯ
            IntervenorCategoryDAO.getInstance().queryIntervenorVOList(page,
                                                                      id,
                                                                      name);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ�������������ʾ�������б�ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ð���������������Ϣ
     * 
     * @param categoryId �񵥱���
     * @param intervenorId ��������
     * @param sortId ��������
     * @throws BOException
     */
    public void setSortId(String categoryId, String intervenorId, String sortId)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.setSortId(categoryId��"
                         + categoryId + ", intervenorId=" + intervenorId
                         + ", sortId=" + sortId + ") is start...");
        }

        try
        {
            // ����IntervenorCategoryDAO������������
            IntervenorCategoryDAO.getInstance().setSortId(categoryId,
                                                          intervenorId,
                                                          sortId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ð���������������Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * ����ɾ�����е�������Ϣ
     * 
     * @param categoryId �񵥱���
     * @param intervenorId ��������
     * @throws BOException
     */
    public void delIntervenorIdByCategoryId(String categoryId,
                                            String intervenorId)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.setSortId(categoryId��"
                         + categoryId + ", intervenorId=" + intervenorId
                         + ") is start...");
        }

        try
        {
            // ����IntervenorCategoryDAO����ɾ�����е�������Ϣ
            IntervenorCategoryDAO.getInstance()
                                 .delIntervenorIdByCategoryId(categoryId,
                                                              intervenorId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����ɾ�����е�������Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * �鿴�����Ƿ����ָ���˹���Ԥ����
     * 
     * @param categoryId ��id
     * @param intervenorId ����id
     * @return
     * @throws BOException
     */
    public boolean hasInternor(String categoryId, String intervenorId)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.hasInternor(categoryId��"
                         + categoryId + ", intervenorId=" + intervenorId
                         + ") is start...");
        }

        try
        {
            // ����IntervenorCategoryDAO�鿴�����Ƿ����ָ���˹���Ԥ����
            return IntervenorCategoryDAO.getInstance()
                                        .hasInternor(categoryId, intervenorId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����ɾ�����е�������Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڹ�����������
     * 
     * @param categoryId ��id
     * @param intervenorId ����id
     * @return
     * @throws BOException
     */
    public int addCategoryVOByIntervenor(String categoryId, String intervenorId, String type)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.addCategoryVOByIntervenor(categoryId��"
                         + categoryId
                         + ", intervenorId="
                         + intervenorId
                         + ") is start...");
        }

        try
        {
            // ����IntervenorCategoryDAO������������
            return IntervenorCategoryDAO.getInstance()
                                        .addCategoryVOByIntervenor(categoryId,
                                                                   intervenorId,type);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("������������ʱ�������ݿ��쳣��");
        }
    }

    /**
     * �õ����б��˹���Ԥ�İ�
     * 
     * @return
     * @throws DAOException
     */
    public List getIntervenorCategory() throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.getIntervenorCategory() is start...");
        }

        try
        {
            return IntervenorCategoryDAO.getInstance().getIntervenorCategory();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�õ����б��˹���Ԥ�İ�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �õ���Ʒ����ܶ�Ӧ���˹���Ԥ�б�
     * @param androidCategoryId
     * @return
     * @throws BOException 
     */
    public List<IntervenorVO> getIntervenorCategoryVO(String androidCategoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.getIntervenorCategoryVO() is start...");
        }

        try
        {
            return IntervenorCategoryDAO.getInstance().getIntervenorCategoryVO(androidCategoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�õ���Ʒ����ܶ�Ӧ���˹���Ԥ�б�ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �õ���Ʒ����ܶ�Ӧ���˹���Ԥ�б�
     * @param androidCategoryId
     * @return
     * @throws BOException 
     */
    public List<IntervenorGcontentVO> queryGcontentListByIntervenorIdAndroid(String androidCategoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.getIntervenorCategoryVO() is start...");
        }

        try
        {
            return IntervenorGcontentDAO.getInstance().queryGcontentListByIntervenorIdAndroid(androidCategoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�õ���Ʒ����ܶ�Ӧ���˹���Ԥ�б�ʱ�������ݿ��쳣��");
        }
    }
    /**
     * 
     * @param id
     * @throws BOException 
     */
    public String  queryCategoryListById(String id) throws BOException {
    	if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.queryCategoryListById(" + id + ") is start...");
        }

        try
        {
            return IntervenorCategoryDAO.getInstance().queryCategoryListById(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("ͨ��id��ѯCategory���쳣��");
        }
    }
    /**
     * ��Messages�������һ����Ϣ
     * @param categoryid
     * @return
     * @throws BOException 
     */
    public int insertMessagesList(String categoryid) throws BOException {
    	if (logger.isDebugEnabled())
        {
            logger.debug("IntervenorCategoryBO.insertMessagesList(" + categoryid + ") is start...");
        }

        try
        {
            return IntervenorCategoryDAO.getInstance().insertMessagesList(categoryid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��Messages�������һ����Ϣ�����쳣"+ e);
        }
	}
}
