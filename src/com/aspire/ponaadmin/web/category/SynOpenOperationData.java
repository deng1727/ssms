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
 * 开放运营数据同步类
 */
/**
 * @author wangminlong
 * 
 */
public class SynOpenOperationData
{

	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory
			.getLogger(SynOpenOperationData.class);

	/**
	 * singleton模式的实例
	 */
	private static SynOpenOperationData instance = new SynOpenOperationData();
	
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 构造方法，由singleton模式调用
	 */
	private SynOpenOperationData()
	{}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static SynOpenOperationData getInstance()
	{
		return instance;
	}

	/**
	 * 用于同步开放运营数据
	 */
	public void start()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开放运营数据渠道信息同步任务开始！");
		}

		// 用于同步开放运营数据信息
		synData();

		// 根据同步过来的开放运营数据来创建货架，上货商品
		diyDataToCategory();

		if (logger.isDebugEnabled())
		{
			logger.debug("开放运营数据渠道信息同步任务结束！");
		}
	}

	/**
	 * 根据同步过来的开放运营数据来创建货架，上货商品
	 */
	private void diyDataToCategory()
	{
		String mailChannelContent = "开放运营数据货架化过程执行成功！";

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

			logger.info("准备统计待创建货架！");

			// 传入两个map对比。是否存在这个货架。不存在创建。且加入映射表中
			createList = getDifferenceList(openchannelMap, openCateMap);

			logger.info("统计待创建货架数为" + createList.size() + "！");

			//logger.info("准备统计待变更货架！");

			// 传入两个map对比。是否存在这个货架。存在的话。名称是否有变更
			//updateList = getUpdateList(openchannelMap, openCateMap, "name");

			//logger.info("统计待变更货架数为" + updateList.size() + "！");

			logger.info("准备统计待删除货架！");

			// 传入两个map对比。对比。是否要自动生成。如果是。是否存在规则id，之后进行相应操作
			upRuleList = getUpdateList(openchannelMap, openCateMap, "addRule");

			logger.info("统计规则变更的货架数为" + upRuleList.size() + "！");

			logger.info("准备统计规则变更的货架！");

			// 传入两个map对比。对比。是否要自动生成。如果否。是否存在规则id，之后进行相应操作
			delRuleList = getUpdateList(openchannelMap, openCateMap, "delRule");

			logger.info("统计规则变更的货架数为" + delRuleList.size() + "！");

			logger.info("准备统计规则变更的货架！");

			// 传入两个map对比。是否多出了这个。如果多出删除。且对映射表进行变更
			delList = getDifferenceList(openCateMap, openchannelMap);

			logger.info("统计待删除货架数为" + delList.size() + "！");

			// 删除渠道货架，删除渠道映射关系
			delOpenCate(delList, openCateMap);

			// 删除渠道货架规则，变更渠道映射关系
			delRuleOpenCate(delRuleList, openCateMap);

			// 变更渠道货架，变更渠道映射关系
			//updateOpenCate(updateList, openCateMap, openchannelMap);

			// 变更渠道货架规则，变更渠道映射关系
			addRuleOpenCate(upRuleList, openCateMap, openchannelMap);

			// 新增渠道货架，新增渠道映射关系
			createOpenCate(createList, openchannelMap);

		}
		catch (Exception e)
		{
			logger.error("根据同步过来的开放运营数据来创建货架时发生错误，无法继续，同步过程结束。", e);
			mailChannelContent = "开放运营数据货架化过程执行过程中发生错误！失败！！！";

		}

		// 将业务同步结果发邮件给相关人员
		this.sendMail(mailChannelContent, "开放运营数据货架化过程结果");
	}

	/**
	 * 对比两个集合。找到其中的区别，以主要集合为准从属集合中不存在的数据。
	 * 
	 * @param mainMap
	 *            主要集合
	 * @param minorMap
	 *            从属集合
	 * @return 以主要集合为准从属集合中不存在的数据
	 */
	private List getDifferenceList(Map mainMap, Map minorMap)
	{
		List list = new ArrayList();
		Set key = mainMap.keySet();

		// 遍历主要集合key
		for (Iterator iterator = key.iterator(); iterator.hasNext();)
		{
			String keyTemp = (String) iterator.next();

			// 如果从属集合不存在指定的key，加入到返回集合
			if (!minorMap.containsKey(keyTemp))
			{
				list.add(keyTemp);
			}
		}

		return list;
	}

	/**
	 * 对比两个集合。找到其中的相同的，在判断名称是否有变化
	 * 
	 * @param openchannelMap
	 *            key:openchannelCode value:OpenOperationVO 主要集合
	 * @param openCateMap
	 *            key:openchannelCode value:cateId_cateName 从属集合
	 * @param type
	 *            操作类型 name 名称不同的列表，addRule 要新增规则的列表， delRule 要删除规则的列表
	 * @return 以主要集合为准从属集合中存在且名称有变化的数据
	 */
	private List getUpdateList(Map openchannelMap, Map openCateMap, String type)
	{
		List list = new ArrayList();
		Set key = openchannelMap.keySet();

		// 遍历主要集合key
		for (Iterator iterator = key.iterator(); iterator.hasNext();)
		{
			String channelId = (String) iterator.next();
			SynOpenOperationVO vo = (SynOpenOperationVO) openchannelMap
					.get(channelId);

			// 如果从属集合存在指定的key
			if (openCateMap.containsKey(channelId))
			{
				String value = (String) openCateMap.get(channelId);

				if ("name".equals(type))
				{
					String cateName = value.substring(value.indexOf("_") + 1,
							value.lastIndexOf("_"));

					// 判断名称是否相同，如果不同，放入变更列表
					if (!vo.getCompanyName().equals(cateName))
					{
						list.add(channelId);
					}
				}
				else if ("addRule".equals(type))
				{
					String ruleId = value.substring(value.lastIndexOf("_") + 1);

					// 判断要自动更新的，但是现表中不存在ruleid的。
					if ("1".equals(vo.getIsAuto())
							&& ("".equals(ruleId) || "null".equals(ruleId)))
					{
						list.add(channelId);
					}
				}
				else if ("delRule".equals(type))
				{
					String ruleId = value.substring(value.lastIndexOf("_") + 1);

					// 判断不要自动更新的，但是现表中存在ruleid的。
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
	 * 根据删除列表，删除渠道货架，删除渠道映射关系
	 * 
	 * @param delList
	 *            删除列表
	 * @param openCateMap
	 *            渠道映射关系
	 */
	private void delOpenCate(List delList, Map openCateMap)
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("准备根据删除列表，删除渠道货架，删除渠道映射关系！");
		}

		// String pCategoryId = Config.getInstance().getModuleConfig()
		// .getItemValue("openChannelCategoryId");

		// 迭代删除
		for (Iterator iterator = delList.iterator(); iterator.hasNext();)
		{
			String channelId = (String) iterator.next();
			String valueTemp = (String) openCateMap.get(channelId);
			String cateId = valueTemp.substring(0, valueTemp.indexOf("_"));
			String ruleId = valueTemp.substring(valueTemp.lastIndexOf("_") + 1);

			try
			{
				// 删除货架
				CategoryTools.delCategory(cateId);

				// 删除货架与渠道的关系
				SynOpenOperationDAO.getInstance().delOpenCateMap(cateId,
						channelId);

				// 删除货架对应规则的关系
				CategoryUpdateDAO.getInstance().dellCateRulesVOByID(cateId);

				// 如果规则不为空
				if (!"".equals(ruleId) && !"null".equals(ruleId))
				{
					RuleDAO.getInstance().dellRuleVOByID(ruleId);
				}

			}
			catch (Exception e)
			{
				logger.error("删除渠道映射关系出错，出错渠道商编码为=" + channelId, e);
			}
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("根据删除列表，删除渠道货架，删除渠道映射关系！结束");
		}
	}

	/**
	 * 根据删除规则列表，删除渠道货架规则，变更渠道映射关系
	 * 
	 * @param delRuleList
	 *            删除规则列表
	 * @param openCateMap
	 *            渠道映射关系
	 */
	private void delRuleOpenCate(List delRuleList, Map openCateMap)
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("准备根据删除列表，删除渠道货架，删除渠道映射关系！");
		}

		// 迭代删除
		for (Iterator iterator = delRuleList.iterator(); iterator.hasNext();)
		{
			String channelId = (String) iterator.next();
			String valueTemp = (String) openCateMap.get(channelId);
			String cateId = valueTemp.substring(0, valueTemp.indexOf("_"));
			String ruleId = valueTemp.substring(valueTemp.lastIndexOf("_") + 1);

			try
			{

				// 删除货架对应规则的关系
				CategoryUpdateDAO.getInstance().dellCateRulesVOByID(cateId);

				// 如果规则不为空
				if (!"".equals(ruleId) && !"null".equals(ruleId))
				{
					RuleDAO.getInstance().dellRuleVOByID(ruleId);
				}

				SynOpenOperationDAO.getInstance().updateRuleOpenCateMap("",
						cateId, channelId);

			}
			catch (Exception e)
			{
				logger.error("删除渠道映射关系出错，出错渠道商编码为=" + channelId, e);
			}
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("根据删除列表，删除渠道货架，删除渠道映射关系！结束");
		}
	}

	/**
	 * 用于变更渠道货架的属性，变更渠道货架映射关系中的名称
	 * 
	 * @param updateList
	 *            变更列表
	 * @param openCateMap
	 *            渠道映射关系集合
	 * @param openchannelMap
	 *            渠道信息集合
	 */
	public void updateOpenCate(List updateList, Map openCateMap,
			Map openchannelMap)
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("准备变更渠道货架的属性，变更渠道货架映射关系中的名称！");
		}

		String pCategoryId = Config.getInstance().getModuleConfig()
				.getItemValue("openChannelCategoryId");

		for (Iterator iterator = updateList.iterator(); iterator.hasNext();)
		{
			// 得到货架id
			String channelId = (String) iterator.next();
			String valueTemp = (String) openCateMap.get(channelId);
			String cateId = valueTemp.substring(0, valueTemp.indexOf("_"));

			// 得到渠道信息
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
				logger.error("变更渠道映射关系出错，出错渠道商编码为=" + channelId, e);
			}
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("变更渠道货架的属性，变更渠道货架映射关系中的名称！结束");
		}
	}

	/**
	 * 用于变更渠道货架的规则属性，变更渠道货架规则映射关系中的名称
	 * 
	 * @param upRuleList
	 *            变更规则列表
	 * @param openCateMap
	 *            渠道映射关系集合
	 * @param openchannelMap
	 *            渠道信息集合
	 */
	private void addRuleOpenCate(List upRuleList, Map openCateMap,
			Map openchannelMap)
	{

		if (logger.isDebugEnabled())
		{
			logger.debug("准备变更渠道货架的规则属性，变更渠道货架映规则射关系中的名称！");
		}

		for (Iterator iterator = upRuleList.iterator(); iterator.hasNext();)
		{
			// 得到货架id
			String channelId = (String) iterator.next();
			String valueTemp = (String) openCateMap.get(channelId);
			String cateId = valueTemp.substring(0, valueTemp.indexOf("_"));
			String ruleId = "";

			// 得到渠道信息
			SynOpenOperationVO vo = (SynOpenOperationVO) openchannelMap
					.get(channelId);

			try
			{
				// 如果存在条件信息
				if ("1".equals(vo.getIsAuto()))
				{
					// 创建特有自动更新规则
					ruleId = createRulByCategory(cateId, vo);
				}

				SynOpenOperationDAO.getInstance().updateRuleOpenCateMap(ruleId,
						cateId, channelId);
			}
			catch (Exception e)
			{
				logger.error("变更渠道规则映射关系出错，出错渠道商编码为=" + channelId, e);
			}
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("变更渠道货架的规则属性，变更渠道货架映规则射关系中的名称！结束");
		}
	}

	/**
	 * 根据新增列表，新增渠道货架，新增渠道映射关系
	 * 
	 * @param createList
	 *            新增列表
	 * @param openchannelMap
	 *            渠道信息集合
	 */
	private void createOpenCate(List createList, Map openchannelMap)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("准备根据新增列表，新增渠道货架，新增渠道映射关系！");
		}

		String pCategoryId = Config.getInstance().getModuleConfig()
				.getItemValue("openChannelCategoryId");
		Category pcategory = null;
		Category category = null;
		try
		{
			// 得到渠道货架根货架
			pcategory = (Category) Repository.getInstance().getNode(
					pCategoryId, RepositoryConstants.TYPE_CATEGORY);

		}
		catch (BOException e)
		{
			logger.error("获取渠道商货架根货架出错，创建阶段无法继续。退出。", e);
			return;
		}

		for (Iterator iterator = createList.iterator(); iterator.hasNext();)
		{
			String openchannelCode = (String) iterator.next();
			String ruleId = "";

			SynOpenOperationVO vo = (SynOpenOperationVO) openchannelMap
					.get(openchannelCode);

			// 组装信息
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

				// 如果存在条件信息
				if ("1".equals(vo.getIsAuto()))
				{
					// 创建特有自动更新规则
					ruleId = createRulByCategory(newCategoryID, vo);
				}

				SynOpenOperationDAO.getInstance().createOpenCateMap(
						newCategoryID, vo.getCompanyName(), openchannelCode,
						ruleId);

			}
			catch (Exception e)
			{
				logger.error("创建渠道映射关系出错，出错渠道商编码为=" + openchannelCode, e);
			}
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("准备根据新增列表，新增渠道货架，新增渠道映射关系！结束");
		}
	}

	/**
	 * 创建特有自动更新规则
	 * 
	 * @param cateId
	 *            新增货架ID
	 * @throws BOException
	 */
	private String createRulByCategory(String cateId, SynOpenOperationVO vo)
			throws BOException
	{
		RuleVO rule = null;

		// 创建自动更新任务。
		try
		{
			rule = getRuleVO(vo);
			RuleDAO.getInstance().addRuleVO(rule);
		}
		catch (DAOException e)
		{
			logger.error("新增自动生成自定义的规则时，发生异常！");
			throw new BOException("新增自动生成自定义的规则时，发生异常！", e);
		}
		catch (BOException e)
		{
			logger.error("自动生成自定义的规则时，发生异常！");
			throw new BOException("自动生成自定义的规则时，发生异常！", e);
		}

		ConditionVO condition = getConditionVO(vo, rule.getRuleId());

		// 根据特有条件语句创建任务规则
		try
		{
			ConditionUpdateDAO.getInstance().addConditeionVO(condition);
		}
		catch (DAOException e)
		{
			logger.error("新增生成的自定义规则条件时，发生异常！");
			throw new BOException("新增生成的自定义规则条件时，发生异常！", e);
		}

		// 组建自动更新任务与货架的对应关系
		try
		{
			CategoryUpdateDAO.getInstance().addCateRulesVOByID(cateId,
					String.valueOf(rule.getRuleId()), getEffectiveTime());
		}
		catch (DAOException e)
		{
			logger.error("组建自动更新任务与货架的对应关系时，发生异常！");
			throw new BOException("组建自动更新任务与货架的对应关系时，发生异常！", e);
		}

		return String.valueOf(rule.getRuleId());
	}

	/**
	 * 用于返回当天的日期字符
	 * 
	 * @return
	 */
	private String getEffectiveTime()
	{
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * 用于自动生成自定义的规则
	 * 
	 * @param svo
	 * @return
	 * @throws DAOException
	 * @throws BOException
	 */
	private RuleVO getRuleVO(SynOpenOperationVO svo) throws BOException
	{
		// 组装信息
		String name = svo.getCompanyName();
		
		if(name.length() > 22)
		{
			name = name.substring(0, 22);
		}
		
		RuleVO vo = new RuleVO();
		vo.setRuleName(name + "自动生成规则");
		vo.setRuleType(0);
		vo.setIntervalType(0);
		vo.setExcuteTime(0);
		vo.setExcuteInterval(1);
		vo.setRandomFactor(0);

		boolean hasName = true;

		// 查看名称是否存在
		try
		{
			hasName = RuleBO.getInstance().hasRuleName(vo.getRuleName());
		}
		catch (BOException e)
		{
			throw new BOException("查询当前规则名称是否存在时，发生数据库异常！", e);
		}

		if (hasName)
		{
			logger.error("您输入的规则名称已存在，请更换！");
			throw new BOException("您输入的规则名称已存在，请更换！");
		}

		// 得到规则id
		int ruleId = 0;

		try
		{
			ruleId = RuleBO.getInstance().getRuleId();
		}
		catch (BOException e)
		{
			throw new BOException("获取当前规则ID序列时，发生数据库异常！", e);
		}
		vo.setRuleId(ruleId);
		return vo;
	}

	/**
	 * 用于自动生成条件
	 * 
	 * @param svo
	 * @param ruleId
	 *            条件对应的规则ID
	 * @return
	 */
	private ConditionVO getConditionVO(SynOpenOperationVO svo, int ruleId)
	{
		// 组装信息
		ConditionVO vo = new ConditionVO();
		vo.setRuleId(ruleId);
		vo.setCid("");
		vo.setCondType(41);
		vo.setWSql("m.openchannelcode = '" + svo.getOpenChannelCode()+"'");
		vo.setOSql("g.lupddate desc nulls last");

		return vo;
	}

	/**
	 * 同步开放运营数据信息
	 */
	private void synData()
	{
		StringBuffer mail = new StringBuffer();

		String mailChannelContent = "开放运营数据渠道信息同步成功！";
		String mailContentContent = "开放运营数据渠道内容同步成功！";

		// 同步渠道信息
		try
		{
			SynOpenOperationDAO.getInstance().synChannelBusine();
		}
		catch (Exception e)
		{
			logger.error(e);
			mailChannelContent = "开放运营数据渠道信息同步失败！";
		}

		mail.append(mailChannelContent);

		// 同步渠道内容信息
		try
		{
			SynOpenOperationDAO.getInstance().synContent();
		}
		catch (Exception e)
		{
			logger.error(e);
			mailContentContent = "开放运营数据渠道内容同步失败！";
		}
		
		mail.append(mailContentContent);
		
		//mail.append(SynOpenOperationDAO.getInstance().createContentIndex());

		// 将业务同步结果发邮件给相关人员
		this.sendMail(mail.toString(), "开放运营数据同步结果");
	}

	/**
	 * 发送邮件
	 * 
	 * @param mailContent,邮件内容
	 */
	private void sendMail(String mailContent, String mailTitle)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("sendMail(" + mailContent + ")");
		}
		// 得到邮件接收者数组
		String[] mailTo = MailConfig.getInstance().getMailToArray();

		if (logger.isDebugEnabled())
		{
			logger.debug("mail mailTitle is:" + mailTitle);
			logger.debug("mailContent is:" + mailContent);
		}
		Mail.sendMail(mailTitle, mailContent, mailTo);
	}
}
