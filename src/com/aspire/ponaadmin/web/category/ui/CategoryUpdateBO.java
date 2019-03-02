/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui;


import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.category.CategoryRuleVO;
import com.aspire.ponaadmin.web.category.rule.RuleVO;

/**
 * @author x_wangml
 * 
 */
public class CategoryUpdateBO
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryUpdateBO.class);

    private static CategoryUpdateBO bo = new CategoryUpdateBO();

    private CategoryUpdateBO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CategoryUpdateBO getInstance()
    {

        return bo;
    }

    /**
     * ���ݻ������룬����id����Чʱ������Ӧ���ܲ��Թ������Ϣ
     * 
     * @param cid ���ܵĻ�������
     * @param ruleID ���ܶ�Ӧ�Ĺ���ID
     * @param cName ����Name
     * @param ruleName ���ܶ�Ӧ�Ĺ���Name
     * @return
     * @throws BOException
     */
    public void queryCategoryUpdateList(PageResult page, String cid,
                                        String ruleID, String cName,
                                        String ruleName)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.queryCategoryUpdateList() is start...");
        }

        try
        {
            // ����CategoryUpdateDAO���в�ѯ
            CategoryUpdateDAO.getInstance()
                                    .queryCategoryUpdateList(page,
                                                             cid,
                                                             ruleID,
                                                             cName,
                                                             ruleName);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ������룬����id����Чʱ������Ӧ���ܲ��Թ������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ڸ��ݻ�������õ����ܲ��Թ������Ϣ
     * @param cid ��������
     * @return
     * @throws BOException 
     */
    public CategoryRuleVO getCategoryRuleVOByID(String cid) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.getCategoryRuleVOByID() is start...");
        }

        try
        {
            // ����CategoryUpdateDAO���в�ѯ
            return CategoryUpdateDAO.getInstance()
                                    .getCategoryRuleVOByID(cid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ�����������Ӧ���ܲ��Թ������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ݹ���id�õ���Ӧ����
     * @param ruleId ����id
     * @return
     * @throws BOException 
     */
    public RuleVO getCateRulesVOByID(String ruleId) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.getCateRulesVOByID() is start...");
        }

        try
        {
            // ����CategoryUpdateDAO���в�ѯ
            return CategoryUpdateDAO.getInstance()
                                    .getCateRulesVOByID(ruleId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݹ���id�����Ӧ�������Ϣʱ�������ݿ��쳣��",e);
        }
    }
    
    /**
     * ���ڸ��ݻ�������õ����Ӧ�Ĺ�����Ϣ
     * @param cid ��������
     * @return
     * @throws BOException 
     */
    public RuleVO getAllCateRules(String cid) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.getAllCateRules() is start...");
        }

        try
        {
            // ����CategoryUpdateDAO���в�ѯ
            return CategoryUpdateDAO.getInstance()
                                    .getAllCateRules(cid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ�����������Ӧ���Թ������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    
    /**
     * ���ڸ��ݻ�������ɾ�����Ӧ�Ĳ��Թ�����Ϣ
     * @param cid ��������
     * @return
     * @throws BOException 
     */
    public int dellCateRulesVOByID(String cid) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.dellCateRulesVOByID() is start...");
        }

        try
        {
            // ����CategoryUpdateDAO����ɾ��
            return CategoryUpdateDAO.getInstance()
                                    .dellCateRulesVOByID(cid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ�������ɾ����Ӧ���Թ������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    
    /**
     * �����޸Ĳ��Թ�����Ϣ
     * @param cid ��������
     * @param ruleId ����id
     * @param effectiveTime ��Чʱ��
     * @return
     * @throws BOException 
     */
    public int editCateRulesVOByID(String cid, String ruleId, String effectiveTime) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.dellCateRulesVOByID() is start...");
        }

        try
        {
            // ����CategoryUpdateDAO�����޸�
            return CategoryUpdateDAO.getInstance()
                                    .editCateRulesVOByID(cid,
                                                         ruleId,
                                                         effectiveTime);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ�������ɾ����Ӧ���Թ������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �����������Թ�����Ϣ
     * @param cid ��������
     * @param ruleId ����id
     * @param effectiveTime ��Чʱ��
     * @return
     * @throws BOException 
     */
    public int addCateRulesVOByID(String cid, String ruleId, String effectiveTime) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.addCateRulesVOByID() is start...");
        }

        try
        {
            // ����CategoryUpdateDAO�����޸�
            return CategoryUpdateDAO.getInstance()
                                    .addCateRulesVOByID(cid,
                                                         ruleId,
                                                         effectiveTime);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�������Թ������Ϣʱ�������ݿ��쳣��");
        }
    }
}
