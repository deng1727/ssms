/*
 * 
 */

package com.aspire.ponaadmin.web.category.ui.rule;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.category.rule.RuleVO;

/**
 * @author x_wangml
 * 
 */
public class RuleBO
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(RuleBO.class);

    private static RuleBO bo = new RuleBO();

    private RuleBO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static RuleBO getInstance()
    {

        return bo;
    }

    /**
     * ���ڸ���������ѯ�����б�
     * @param page 
     * @param ruleId ����id
     * @param ruleName ��������
     * @param ruleType ��������
     * @param intervalType ִ��ʱ��������
     * @throws BOException
     */
    public void queryRuleList(PageResult page, String ruleId, String ruleName,
                              String ruleType, String intervalType)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CategoryUpdateBO.queryCategoryUpdateList() is start...");
        }

        try
        {
            // ����CategoryUpdateDAO���в�ѯ
            RuleDAO.getInstance().queryRuleList(page,
                                                ruleId,
                                                ruleName,
                                                ruleType,
                                                intervalType);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ������룬����id����Чʱ������Ӧ���ܲ��Թ������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �����������Թ�����Ϣ
     * 
     * @param vo ������Ϣ
     * @return
     * @throws BOException
     */
    public int addRuleVO(RuleVO vo) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("RuleBO.addRuleVO() is start...");
        }

        try
        {
            // ����RuleBO��������
            return RuleDAO.getInstance().addRuleVO(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�������Թ�����Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * �����޸Ĺ�����Ϣ
     * 
     * @param vo ҳ��������޸ĺ������Ϣ��
     * @return
     * @throws BOException
     */
    public int editRuleVO(RuleVO vo) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("RuleBO.editRuleVO() is start...");
        }

        try
        {
            // ����RuleBO�����޸�
            return RuleDAO.getInstance().editCateRulesVOByID(vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݹ�������޸Ķ�Ӧ������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ����ͨ������idɾ����Ӧ�Ĺ���
     * @param ruleId ����id
     * @return
     * @throws BOException 
     */
    public void dellRuleVOByID(String ruleId) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("RuleBO.dellRuleVOByID() is start...");
        }

        try
        {
            // ����RuleDAO����ɾ��
            RuleDAO.getInstance()
                                    .dellRuleVOByID(ruleId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݻ�������ɾ����Ӧ���Թ������Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ݹ������鿴�˹����Ƿ�󶨻���
     * @param ruleId �������
     * @return
     * @throws BOException
     */
    public boolean isRuleBind(String ruleId) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("RuleBO.isRuleBind() is start...");
        }

        try
        {
            // ����RuleBO���в鿴�˹����Ƿ�󶨻���
            return RuleDAO.getInstance().isRuleBind(ruleId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݹ������鿴�˹����Ƿ�󶨻��ܷ����쳣��");
        }
    }

    /**
     * ���ڲ鿴�Ƿ��Ѵ���Ҫ�޸ĵ�����
     * 
     * @param ruleName ��������
     * @return
     * @throws BOException
     */
    public boolean hasRuleName(String ruleName) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("RuleBO.hasRuleName() is start...");
        }

        try
        {
            // ����RuleBO�����޸�
            return RuleDAO.getInstance().hasRuleName(ruleName);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݹ������Ƶõ��Ƿ��Ѵ���ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ع���id
     * @return
     * @throws BOException
     */
    public int getRuleId() throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("RuleBO.hasRuleName() is start...");
        }

        try
        {
            // ���ع���id
            return RuleDAO.getInstance().getRuleId();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ݹ������Ƶõ��Ƿ��Ѵ���ʱ�������ݿ��쳣��");
        }
    }

}
