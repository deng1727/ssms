/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui.condition;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.category.rule.condition.ConditionVO;

/**
 * @author x_wangml
 * 
 */
public class ConditionUpdateBO
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(ConditionUpdateBO.class);

    private static ConditionUpdateBO bo = new ConditionUpdateBO();

    private ConditionUpdateBO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static ConditionUpdateBO getInstance()
    {

        return bo;
    }

    /**
     * ���ڸ��ݹ������õ����ڴ˹��������
     * 
     * @param page
     * @param ruleId ����id
     * @throws BOException
     */
    public void queryCondListByID(PageResult page, String ruleId)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.queryCategoryUpdateList(" + ruleId
                         + ") is start...");
        }

        try
        {
            // ����ConditionUpdateDAO���в�ѯ
            ConditionUpdateDAO.getInstance().queryCondListByID(page, ruleId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݸ��ݹ������õ����ڴ˹����������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ������������������Ϣ
     * 
     * @param vo ����������Ϣ
     * @return
     * @throws BOException
     */
    public int addConditeionVO(ConditionVO vo) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ConditionUpdateBO.addConditeionVO() is start...");
        }

        try
        {
            // ����ConditionUpdateDAO��������
            return ConditionUpdateDAO.getInstance().addConditeionVO(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��������������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ����ɾ������������Ϣ
     * 
     * @param id ������������
     * @return
     * @throws BOException
     */
    public int deleteConditeionVO(String id) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ConditionUpdateBO.deleteConditeionVO() is start...");
        }

        try
        {
            // ����ConditionUpdateDAO��������
            return ConditionUpdateDAO.getInstance().deleteConditeionVO(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("ɾ������������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڸ��ݱ����ѯ����������Ϣ
     * 
     * @param id ������������
     * @return
     * @throws BOException
     */
    public ConditionVO getConditionVOById(String id) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ConditionUpdateBO.getConditionVOById() is start...");
        }

        try
        {
            // ����ConditionUpdateDAO���в�ѯ
            return ConditionUpdateDAO.getInstance().getConditionVOById(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݱ����ѯ����������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �����޸Ĺ���������Ϣ
     * 
     * @param vo ����������Ϣ
     * @return
     * @throws BOException
     */
    public int editConditeionVO(ConditionVO vo) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("ConditionUpdateBO.editConditeionVO() is start...");
        }

        try
        {
            // ����ConditionUpdateDAO��������
            return ConditionUpdateDAO.getInstance().editConditeionVO(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�޸Ĺ���������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �õ������������
     * @return
     * @throws DAOException
     */
    public List queryBaseCondList() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ConditionUpdateBO.queryBaseCondList() is start...");
        }

        try
        {
            // ����ConditionUpdateDAO��������
            return ConditionUpdateDAO.getInstance().queryBaseCondList();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�õ������������ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �õ������������
     * @return
     * @throws DAOException
     */
    public BaseCondVO queryBaseCondVO(String id) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ConditionUpdateBO.queryBaseCondVO() is start...");
        }

        try
        {
            // ����ConditionUpdateDAO��������
            return ConditionUpdateDAO.getInstance().queryBaseCondVO(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�õ������������ʱ�������ݿ��쳣��");
        }
    }
}
