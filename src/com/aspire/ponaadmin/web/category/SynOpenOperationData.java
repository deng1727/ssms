package com.aspire.ponaadmin.web.category;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.category.rule.RuleVO;
import com.aspire.ponaadmin.web.category.rule.condition.ConditionVO;
import com.aspire.ponaadmin.web.category.ui.CategoryUpdateDAO;
import com.aspire.ponaadmin.web.category.ui.condition.ConditionUpdateDAO;
import com.aspire.ponaadmin.web.category.ui.rule.RuleBO;
import com.aspire.ponaadmin.web.category.ui.rule.RuleDAO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.system.Config;

/**
 * ������Ӫ����ͬ����
 */
/**
 * @author wangminlong
 * 
 */
public class SynOpenOperationData
{

	/**
	 * ��־����
	 */
	private static final JLogger logger = LoggerFactory
			.getLogger(SynOpenOperationData.class);

	/**
	 * singletonģʽ��ʵ��
	 */
	private static SynOpenOperationData instance = new SynOpenOperationData();
	
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * ���췽������singletonģʽ����
	 */
	private SynOpenOperationData()
	{}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static SynOpenOperationData getInstance()
	{
		return instance;
	}

	/**
	 * ����ͬ��������Ӫ����
	 */
	public void start()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("������Ӫ����������Ϣͬ������ʼ��");
		}

		// ����ͬ��������Ӫ������Ϣ
		synData();

		// ����ͬ�������Ŀ�����Ӫ�������������ܣ��ϻ���Ʒ
		diyDataToCategory();

		if (logger.isDebugEnabled())
		{
			logger.debug("������Ӫ����������Ϣͬ�����������");
		}
	}

	/**
	 * ����ͬ�������Ŀ�����Ӫ�������������ܣ��ϻ���Ʒ
	 */
	private void diyDataToCategory()
	{
		String mailChannelContent = "������Ӫ���ݻ��ܻ�����ִ�гɹ���";

		Map openCateMap = null;
		Map openchannelMap = null;
		List createList = null;
		//List updateList = null;
		List delList = null;
		List upRuleList = null;
		List delRuleList = null;

		try
		{
			// key:openchannelCode value:OpenOperationVO
			openchannelMap = SynOpenOperationDAO.getInstance().getOpenChannel();

			// key:openchannelCode value:cateId_cateName
			openCateMap = SynOpenOperationDAO.getInstance().getOpenCateMap();

			logger.info("׼��ͳ�ƴ��������ܣ�");

			// ��������map�Աȡ��Ƿ����������ܡ������ڴ������Ҽ���ӳ�����
			createList = getDifferenceList(openchannelMap, openCateMap);

			logger.info("ͳ�ƴ�����������Ϊ" + createList.size() + "��");

			//logger.info("׼��ͳ�ƴ�������ܣ�");

			// ��������map�Աȡ��Ƿ����������ܡ����ڵĻ��������Ƿ��б��
			//updateList = getUpdateList(openchannelMap, openCateMap, "name");

			//logger.info("ͳ�ƴ����������Ϊ" + updateList.size() + "��");

			logger.info("׼��ͳ�ƴ�ɾ�����ܣ�");

			// ��������map�Աȡ��Աȡ��Ƿ�Ҫ�Զ����ɡ�����ǡ��Ƿ���ڹ���id��֮�������Ӧ����
			upRuleList = getUpdateList(openchannelMap, openCateMap, "addRule");

			logger.info("ͳ�ƹ������Ļ�����Ϊ" + upRuleList.size() + "��");

			logger.info("׼��ͳ�ƹ������Ļ��ܣ�");

			// ��������map�Աȡ��Աȡ��Ƿ�Ҫ�Զ����ɡ�������Ƿ���ڹ���id��֮�������Ӧ����
			delRuleList = getUpdateList(openchannelMap, openCateMap, "delRule");

			logger.info("ͳ�ƹ������Ļ�����Ϊ" + delRuleList.size() + "��");

			logger.info("׼��ͳ�ƹ������Ļ��ܣ�");

			// ��������map�Աȡ��Ƿ����������������ɾ�����Ҷ�ӳ�����б��
			delList = getDifferenceList(openCateMap, openchannelMap);

			logger.info("ͳ�ƴ�ɾ��������Ϊ" + delList.size() + "��");

			// ɾ���������ܣ�ɾ������ӳ���ϵ
			delOpenCate(delList, openCateMap);

			// ɾ���������ܹ��򣬱������ӳ���ϵ
			delRuleOpenCate(delRuleList, openCateMap);

			// ����������ܣ��������ӳ���ϵ
			//updateOpenCate(updateList, openCateMap, openchannelMap);

			// ����������ܹ��򣬱������ӳ���ϵ
			addRuleOpenCate(upRuleList, openCateMap, openchannelMap);

			// �����������ܣ���������ӳ���ϵ
			createOpenCate(createList, openchannelMap);

		}
		catch (Exception e)
		{
			logger.error("����ͬ�������Ŀ�����Ӫ��������������ʱ���������޷�������ͬ�����̽�����", e);
			mailChannelContent = "������Ӫ���ݻ��ܻ�����ִ�й����з�������ʧ�ܣ�����";

		}

		// ��ҵ��ͬ��������ʼ��������Ա
		this.sendMail(mailChannelContent, "������Ӫ���ݻ��ܻ����̽��");
	}

	/**
	 * �Ա��������ϡ��ҵ����е���������Ҫ����Ϊ׼���������в����ڵ����ݡ�
	 * 
	 * @param mainMap
	 *            ��Ҫ����
	 * @param minorMap
	 *            ��������
	 * @return ����Ҫ����Ϊ׼���������в����ڵ�����
	 */
	private List getDifferenceList(Map mainMap, Map minorMap)
	{
		List list = new ArrayList();
		Set key = mainMap.keySet();

		// ������Ҫ����key
		for (Iterator iterator = key.iterator(); iterator.hasNext();)
		{
			String keyTemp = (String) iterator.next();

			// ����������ϲ�����ָ����key�����뵽���ؼ���
			if (!minorMap.containsKey(keyTemp))
			{
				list.add(keyTemp);
			}
		}

		return list;
	}

	/**
	 * �Ա��������ϡ��ҵ����е���ͬ�ģ����ж������Ƿ��б仯
	 * 
	 * @param openchannelMap
	 *            key:openchannelCode value:OpenOperationVO ��Ҫ����
	 * @param openCateMap
	 *            key:openchannelCode value:cateId_cateName ��������
	 * @param type
	 *            �������� name ���Ʋ�ͬ���б�addRule Ҫ����������б� delRule Ҫɾ��������б�
	 * @return ����Ҫ����Ϊ׼���������д����������б仯������
	 */
	private List getUpdateList(Map openchannelMap, Map openCateMap, String type)
	{
		List list = new ArrayList();
		Set key = openchannelMap.keySet();

		// ������Ҫ����key
		for (Iterator iterator = key.iterator(); iterator.hasNext();)
		{
			String channelId = (String) iterator.next();
			SynOpenOperationVO vo = (SynOpenOperationVO) openchannelMap
					.get(channelId);

			// ����������ϴ���ָ����key
			if (openCateMap.containsKey(channelId))
			{
				String value = (String) openCateMap.get(channelId);

				if ("name".equals(type))
				{
					String cateName = value.substring(value.indexOf("_") + 1,
							value.lastIndexOf("_"));

					// �ж������Ƿ���ͬ�������ͬ���������б�
					if (!vo.getCompanyName().equals(cateName))
					{
						list.add(channelId);
					}
				}
				else if ("addRule".equals(type))
				{
					String ruleId = value.substring(value.lastIndexOf("_") + 1);

					// �ж�Ҫ�Զ����µģ������ֱ��в�����ruleid�ġ�
					if ("1".equals(vo.getIsAuto())
							&& ("".equals(ruleId) || "null".equals(ruleId)))
					{
						list.add(channelId);
					}
				}
				else if ("delRule".equals(type))
				{
					String ruleId = value.substring(value.lastIndexOf("_") + 1);

					// �жϲ�Ҫ�Զ����µģ������ֱ��д���ruleid�ġ�
					if ("0".equals(vo.getIsAuto()) && !"".equals(ruleId)
							&& !"null".equals(ruleId))
					{
						list.add(channelId);
					}
				}
			}
		}

		return list;
	}

	/**
	 * ����ɾ���б�ɾ���������ܣ�ɾ������ӳ���ϵ
	 * 
	 * @param delList
	 *            ɾ���б�
	 * @param openCateMap
	 *            ����ӳ���ϵ
	 */
	private void delOpenCate(List delList, Map openCateMap)
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("׼������ɾ���б�ɾ���������ܣ�ɾ������ӳ���ϵ��");
		}

		// String pCategoryId = Config.getInstance().getModuleConfig()
		// .getItemValue("openChannelCategoryId");

		// ����ɾ��
		for (Iterator iterator = delList.iterator(); iterator.hasNext();)
		{
			String channelId = (String) iterator.next();
			String valueTemp = (String) openCateMap.get(channelId);
			String cateId = valueTemp.substring(0, valueTemp.indexOf("_"));
			String ruleId = valueTemp.substring(valueTemp.lastIndexOf("_") + 1);

			try
			{
				// ɾ������
				CategoryTools.delCategory(cateId);

				// ɾ�������������Ĺ�ϵ
				SynOpenOperationDAO.getInstance().delOpenCateMap(cateId,
						channelId);

				// ɾ�����ܶ�Ӧ����Ĺ�ϵ
				CategoryUpdateDAO.getInstance().dellCateRulesVOByID(cateId);

				// �������Ϊ��
				if (!"".equals(ruleId) && !"null".equals(ruleId))
				{
					RuleDAO.getInstance().dellRuleVOByID(ruleId);
				}

			}
			catch (Exception e)
			{
				logger.error("ɾ������ӳ���ϵ�������������̱���Ϊ=" + channelId, e);
			}
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("����ɾ���б�ɾ���������ܣ�ɾ������ӳ���ϵ������");
		}
	}

	/**
	 * ����ɾ�������б�ɾ���������ܹ��򣬱������ӳ���ϵ
	 * 
	 * @param delRuleList
	 *            ɾ�������б�
	 * @param openCateMap
	 *            ����ӳ���ϵ
	 */
	private void delRuleOpenCate(List delRuleList, Map openCateMap)
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("׼������ɾ���б�ɾ���������ܣ�ɾ������ӳ���ϵ��");
		}

		// ����ɾ��
		for (Iterator iterator = delRuleList.iterator(); iterator.hasNext();)
		{
			String channelId = (String) iterator.next();
			String valueTemp = (String) openCateMap.get(channelId);
			String cateId = valueTemp.substring(0, valueTemp.indexOf("_"));
			String ruleId = valueTemp.substring(valueTemp.lastIndexOf("_") + 1);

			try
			{

				// ɾ�����ܶ�Ӧ����Ĺ�ϵ
				CategoryUpdateDAO.getInstance().dellCateRulesVOByID(cateId);

				// �������Ϊ��
				if (!"".equals(ruleId) && !"null".equals(ruleId))
				{
					RuleDAO.getInstance().dellRuleVOByID(ruleId);
				}

				SynOpenOperationDAO.getInstance().updateRuleOpenCateMap("",
						cateId, channelId);

			}
			catch (Exception e)
			{
				logger.error("ɾ������ӳ���ϵ�������������̱���Ϊ=" + channelId, e);
			}
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("����ɾ���б�ɾ���������ܣ�ɾ������ӳ���ϵ������");
		}
	}

	/**
	 * ���ڱ���������ܵ����ԣ������������ӳ���ϵ�е�����
	 * 
	 * @param updateList
	 *            ����б�
	 * @param openCateMap
	 *            ����ӳ���ϵ����
	 * @param openchannelMap
	 *            ������Ϣ����
	 */
	public void updateOpenCate(List updateList, Map openCateMap,
			Map openchannelMap)
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("׼������������ܵ����ԣ������������ӳ���ϵ�е����ƣ�");
		}

		String pCategoryId = Config.getInstance().getModuleConfig()
				.getItemValue("openChannelCategoryId");

		for (Iterator iterator = updateList.iterator(); iterator.hasNext();)
		{
			// �õ�����id
			String channelId = (String) iterator.next();
			String valueTemp = (String) openCateMap.get(channelId);
			String cateId = valueTemp.substring(0, valueTemp.indexOf("_"));

			// �õ�������Ϣ
			SynOpenOperationVO vo = (SynOpenOperationVO) openchannelMap
					.get(channelId);

			try
			{
				Category category = (Category) Repository.getInstance()
						.getNode(cateId, RepositoryConstants.TYPE_CATEGORY);
				category.setName(vo.getCompanyName());

				CategoryBO.getInstance().modCategory(pCategoryId, category);

				SynOpenOperationDAO.getInstance().updateOpenCateMap(cateId,
						vo.getCompanyName(), channelId);
			}
			catch (Exception e)
			{
				logger.error("�������ӳ���ϵ�������������̱���Ϊ=" + channelId, e);
			}
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("����������ܵ����ԣ������������ӳ���ϵ�е����ƣ�����");
		}
	}

	/**
	 * ���ڱ���������ܵĹ������ԣ�����������ܹ���ӳ���ϵ�е�����
	 * 
	 * @param upRuleList
	 *            ��������б�
	 * @param openCateMap
	 *            ����ӳ���ϵ����
	 * @param openchannelMap
	 *            ������Ϣ����
	 */
	private void addRuleOpenCate(List upRuleList, Map openCateMap,
			Map openchannelMap)
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("׼������������ܵĹ������ԣ������������ӳ�������ϵ�е����ƣ�");
		}

		for (Iterator iterator = upRuleList.iterator(); iterator.hasNext();)
		{
			// �õ�����id
			String channelId = (String) iterator.next();
			String valueTemp = (String) openCateMap.get(channelId);
			String cateId = valueTemp.substring(0, valueTemp.indexOf("_"));
			String ruleId = "";

			// �õ�������Ϣ
			SynOpenOperationVO vo = (SynOpenOperationVO) openchannelMap
					.get(channelId);

			try
			{
				// �������������Ϣ
				if ("1".equals(vo.getIsAuto()))
				{
					// ���������Զ����¹���
					ruleId = createRulByCategory(cateId, vo);
				}

				SynOpenOperationDAO.getInstance().updateRuleOpenCateMap(ruleId,
						cateId, channelId);
			}
			catch (Exception e)
			{
				logger.error("�����������ӳ���ϵ�������������̱���Ϊ=" + channelId, e);
			}
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("����������ܵĹ������ԣ������������ӳ�������ϵ�е����ƣ�����");
		}
	}

	/**
	 * ���������б������������ܣ���������ӳ���ϵ
	 * 
	 * @param createList
	 *            �����б�
	 * @param openchannelMap
	 *            ������Ϣ����
	 */
	private void createOpenCate(List createList, Map openchannelMap)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("׼�����������б������������ܣ���������ӳ���ϵ��");
		}

		String pCategoryId = Config.getInstance().getModuleConfig()
				.getItemValue("openChannelCategoryId");
		Category pcategory = null;
		Category category = null;
		try
		{
			// �õ��������ܸ�����
			pcategory = (Category) Repository.getInstance().getNode(
					pCategoryId, RepositoryConstants.TYPE_CATEGORY);

		}
		catch (BOException e)
		{
			logger.error("��ȡ�����̻��ܸ����ܳ��������׶��޷��������˳���", e);
			return;
		}

		for (Iterator iterator = createList.iterator(); iterator.hasNext();)
		{
			String openchannelCode = (String) iterator.next();
			String ruleId = "";

			SynOpenOperationVO vo = (SynOpenOperationVO) openchannelMap
					.get(openchannelCode);

			// ��װ��Ϣ
			String name = vo.getCompanyName();
			
			if(name.length() > 49)
			{
				name = name.substring(0, 49);
			}
			
			category = new Category();
			category.setName(name);
			category.setDesc(pcategory.getDesc());
			category.setRelation(pcategory.getRelation());
			category.setCtype(pcategory.getCtype());
			category.setChangeDate(new Date());
			category.setDelFlag(0);
			category.setState(pcategory.getState());
			category.setDeviceCategory(pcategory.getDeviceCategory());
			category.setCityId(pcategory.getCityId());
			category.setPlatForm(pcategory.getPlatForm());
			category.setStartDate(pcategory.getStartDate());
			category.setEndDate(pcategory.getEndDate());
			category.setMultiurl(pcategory.getMultiurl());
			category.setOthernet(pcategory.getOthernet());
			category.setSortID(pcategory.getSortID());

			try
			{
				String newCategoryID = CategoryTools.createCategory(
						pCategoryId, category);

				// �������������Ϣ
				if ("1".equals(vo.getIsAuto()))
				{
					// ���������Զ����¹���
					ruleId = createRulByCategory(newCategoryID, vo);
				}

				SynOpenOperationDAO.getInstance().createOpenCateMap(
						newCategoryID, vo.getCompanyName(), openchannelCode,
						ruleId);

			}
			catch (Exception e)
			{
				logger.error("��������ӳ���ϵ�������������̱���Ϊ=" + openchannelCode, e);
			}
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("׼�����������б������������ܣ���������ӳ���ϵ������");
		}
	}

	/**
	 * ���������Զ����¹���
	 * 
	 * @param cateId
	 *            ��������ID
	 * @throws BOException
	 */
	private String createRulByCategory(String cateId, SynOpenOperationVO vo)
			throws BOException
	{
		RuleVO rule = null;

		// �����Զ���������
		try
		{
			rule = getRuleVO(vo);
			RuleDAO.getInstance().addRuleVO(rule);
		}
		catch (DAOException e)
		{
			logger.error("�����Զ������Զ���Ĺ���ʱ�������쳣��");
			throw new BOException("�����Զ������Զ���Ĺ���ʱ�������쳣��", e);
		}
		catch (BOException e)
		{
			logger.error("�Զ������Զ���Ĺ���ʱ�������쳣��");
			throw new BOException("�Զ������Զ���Ĺ���ʱ�������쳣��", e);
		}

		ConditionVO condition = getConditionVO(vo, rule.getRuleId());

		// ��������������䴴���������
		try
		{
			ConditionUpdateDAO.getInstance().addConditeionVO(condition);
		}
		catch (DAOException e)
		{
			logger.error("�������ɵ��Զ����������ʱ�������쳣��");
			throw new BOException("�������ɵ��Զ����������ʱ�������쳣��", e);
		}

		// �齨�Զ�������������ܵĶ�Ӧ��ϵ
		try
		{
			CategoryUpdateDAO.getInstance().addCateRulesVOByID(cateId,
					String.valueOf(rule.getRuleId()), getEffectiveTime());
		}
		catch (DAOException e)
		{
			logger.error("�齨�Զ�������������ܵĶ�Ӧ��ϵʱ�������쳣��");
			throw new BOException("�齨�Զ�������������ܵĶ�Ӧ��ϵʱ�������쳣��", e);
		}

		return String.valueOf(rule.getRuleId());
	}

	/**
	 * ���ڷ��ص���������ַ�
	 * 
	 * @return
	 */
	private String getEffectiveTime()
	{
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * �����Զ������Զ���Ĺ���
	 * 
	 * @param svo
	 * @return
	 * @throws DAOException
	 * @throws BOException
	 */
	private RuleVO getRuleVO(SynOpenOperationVO svo) throws BOException
	{
		// ��װ��Ϣ
		String name = svo.getCompanyName();
		
		if(name.length() > 22)
		{
			name = name.substring(0, 22);
		}
		
		RuleVO vo = new RuleVO();
		vo.setRuleName(name + "�Զ����ɹ���");
		vo.setRuleType(0);
		vo.setIntervalType(0);
		vo.setExcuteTime(0);
		vo.setExcuteInterval(1);
		vo.setRandomFactor(0);

		boolean hasName = true;

		// �鿴�����Ƿ����
		try
		{
			hasName = RuleBO.getInstance().hasRuleName(vo.getRuleName());
		}
		catch (BOException e)
		{
			throw new BOException("��ѯ��ǰ���������Ƿ����ʱ���������ݿ��쳣��", e);
		}

		if (hasName)
		{
			logger.error("������Ĺ��������Ѵ��ڣ��������");
			throw new BOException("������Ĺ��������Ѵ��ڣ��������");
		}

		// �õ�����id
		int ruleId = 0;

		try
		{
			ruleId = RuleBO.getInstance().getRuleId();
		}
		catch (BOException e)
		{
			throw new BOException("��ȡ��ǰ����ID����ʱ���������ݿ��쳣��", e);
		}
		vo.setRuleId(ruleId);
		return vo;
	}

	/**
	 * �����Զ���������
	 * 
	 * @param svo
	 * @param ruleId
	 *            ������Ӧ�Ĺ���ID
	 * @return
	 */
	private ConditionVO getConditionVO(SynOpenOperationVO svo, int ruleId)
	{
		// ��װ��Ϣ
		ConditionVO vo = new ConditionVO();
		vo.setRuleId(ruleId);
		vo.setCid("");
		vo.setCondType(41);
		vo.setWSql("m.openchannelcode = '" + svo.getOpenChannelCode()+"'");
		vo.setOSql("g.lupddate desc nulls last");

		return vo;
	}

	/**
	 * ͬ��������Ӫ������Ϣ
	 */
	private void synData()
	{
		StringBuffer mail = new StringBuffer();

		String mailChannelContent = "������Ӫ����������Ϣͬ���ɹ���";
		String mailContentContent = "������Ӫ������������ͬ���ɹ���";

		// ͬ��������Ϣ
		try
		{
			SynOpenOperationDAO.getInstance().synChannelBusine();
		}
		catch (Exception e)
		{
			logger.error(e);
			mailChannelContent = "������Ӫ����������Ϣͬ��ʧ�ܣ�";
		}

		mail.append(mailChannelContent);

		// ͬ������������Ϣ
		try
		{
			SynOpenOperationDAO.getInstance().synContent();
		}
		catch (Exception e)
		{
			logger.error(e);
			mailContentContent = "������Ӫ������������ͬ��ʧ�ܣ�";
		}
		
		mail.append(mailContentContent);
		
		//mail.append(SynOpenOperationDAO.getInstance().createContentIndex());

		// ��ҵ��ͬ��������ʼ��������Ա
		this.sendMail(mail.toString(), "������Ӫ����ͬ�����");
	}

	/**
	 * �����ʼ�
	 * 
	 * @param mailContent,�ʼ�����
	 */
	private void sendMail(String mailContent, String mailTitle)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("sendMail(" + mailContent + ")");
		}
		// �õ��ʼ�����������
		String[] mailTo = MailConfig.getInstance().getMailToArray();

		if (logger.isDebugEnabled())
		{
			logger.debug("mail mailTitle is:" + mailTitle);
			logger.debug("mailContent is:" + mailContent);
		}
		Mail.sendMail(mailTitle, mailContent, mailTo);
	}
}
