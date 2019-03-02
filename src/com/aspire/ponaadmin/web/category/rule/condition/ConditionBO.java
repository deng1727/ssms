package com.aspire.ponaadmin.web.category.rule.condition;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * ��ȡ�����Ӧ������BO��
 * @author zhangwei
 *
 */
public class ConditionBO
{
	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory.getLogger(ConditionBO.class);

	private static  ConditionBO instance=new ConditionBO();

	public static ConditionBO getInstance()
	{
		return instance;
	}
	/**
	 * ����ruleId��ȡ����,�Ӳ�Ʒ���Ʒ���ȡ��condType�������֡�
	 * @param ruleId ����ID
	 * @param condType ��������
	 * @return
	 * @throws BOException
	 */
	public List getConditions(int ruleId,int condType) throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("��ȡ����ruleID="+ruleId+",condType="+condType+"��Ӧ����������:��ʼִ��......");
		}

		List conds = null;
		try
		{
			conds = ConditionDAO.getInstance().getConditionsByRuleID(ruleId,condType);

		} catch (DAOException ex)
		{
			throw new BOException("��ȡ����ruleID="+ruleId+",condType="+condType+"��Ӧ�����������б�ʧ��", ex);
		}
		if (LOG.isDebugEnabled())
		{
			LOG.debug("��ȡ����ruleID="+ruleId+",condType="+condType+"��Ӧ����������:ִ�н���");
		}

		return conds;
	}
	/**
	 * ����ruleId��ȡ����,�Ӳ�Ʒ���Ʒ���ȡ��condType�������֡�
	 * @param ruleId ����ID
	 * @param condType ��������
	 * @return
	 * @throws BOException
	 */
	public List getNotEliteConditions(int ruleId) throws BOException
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("��ȡ����ruleID="+ruleId+"��Ӧ�ķǾ�Ʒ�����������:��ʼִ��......");
		}

		List conds = null;
		try
		{
			conds = ConditionDAO.getInstance().getNotEliteConditionsByRuleID(ruleId);

		} catch (DAOException ex)
		{
			throw new BOException("��ȡ����ruleID="+ruleId+"��Ӧ�ķǾ�Ʒ������������б�ʧ��", ex);
		}
		if (LOG.isDebugEnabled())
		{
			LOG.debug("��ȡ����ruleID"+ruleId+"��Ӧ�ķǾ�Ʒ�����������:ִ�н���");
		}

		return conds;
	}

}
