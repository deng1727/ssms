package com.aspire.ponaadmin.web.category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.Task;
import com.aspire.dotcard.syncAndroid.autosync.dao.AutoSyncDAO;
import com.aspire.ponaadmin.web.category.rule.RuleVO;
import com.aspire.ponaadmin.web.category.rule.condition.ConditionVO;
import com.aspire.ponaadmin.web.category.ui.condition.BaseCondVO;
import com.aspire.ponaadmin.web.category.ui.condition.ConditionUpdateDAO;
import com.aspire.ponaadmin.web.datasync.implement.book.jidi.JDSyncBO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryDAO;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;
/**
 * 规则执行器。根据货架的执行规则，执行单个货架的更新任务。
 * @author zhangwei
 *
 */
public class CategoryRuleExcutor extends Task
{
	/**
	 * 该货架的信息
	 */
	private CategoryRuleVO vo;
	/**
	 * 执行该方法的task类
	 */
	private CategoryRuleTask task;
	
	/**
	 * 执行该方法的task类
	 */
	private CategoryRuleBO categoryRuleBO;
	/**
	 * 该货架对应的规则
	 */
	private RuleVO rule;
	private static JLogger LOG = LoggerFactory.getLogger(CategoryRuleExcutor.class);

	/**
	 *  应用对应的机型分类的值的缓存
	 */
	private static Map DEVICENAMECACHE=Collections.synchronizedMap(new HashMap()) ;
	
	/**
	 * 缓存spname 信息
	 */
	public static Map SPNAMECACHE=Collections.synchronizedMap(new HashMap()) ;
	
	/**
	 * 缓存黑名单应用信息
	 */
	public static Map BLACKAPPCACHE = Collections.synchronizedMap(new HashMap()) ;
	
	/**
	 * 缓存白名单ICPCODE列表信息
	 */
	public static Set WHITEAPPCACHE = Collections.synchronizedSet(new HashSet());
	
	public CategoryRuleExcutor(CategoryRuleVO vo)
	{
		this.vo=vo;
		this.rule = vo.getRuleVO();
	}
	/**
	 * 多线程，全量规则执行任务
	 * @param vo
	 * @param task
	 */
	public CategoryRuleExcutor(CategoryRuleVO vo,CategoryRuleBO categoryRuleBO)
	{
		this.vo=vo;
		this.rule = vo.getRuleVO();
		this.categoryRuleBO=categoryRuleBO;
	}
	
	/**
	 * 多线程，全量规则执行任务
	 * @param vo
	 * @param task
	 */
	public CategoryRuleExcutor(CategoryRuleVO vo,CategoryRuleTask task)
	{
		this.vo=vo;
		this.rule = vo.getRuleVO();
		this.task=task;
	}

	/**
	 * 自动更新主流程
	 * @return
	 * @throws BOException
	 */
	public int excucte() throws BOException
	{
		if (rule == null)
		{
			throw new BOException("货架更新规则为null");
		}

		AutoSyncDAO.getInstance().addAutoRef(vo.getCid());// wml+ 用于先备份商品信息，数据中心那头用。
		
		Date date = vo.getEffectiveTime();

		if (!isEffective(date))
		{
			LOG.info("货架id为" + vo.getCid() + "的货架还没有生效。不需要执行。生效时间:"
					+ PublicUtil.getDateString(date));
			return CategoryRuleTask.INEFFECTIVE;
		}

		LOG.info("开始对货架id为：" + vo.getCid() + "的货架进行更新");

		long starttime = System.currentTimeMillis();

		// 记录执行语句超长的变量
		StringBuffer sqlSb = new StringBuffer();

		if (LOG.isDebugEnabled())
		{
			LOG.debug("该货架的规则为：" + rule);
		}

		// 根据规则中的商品是否需要自动生成和商品是否需要重新计算排序，来判断本次是否需要处理该货架。
		// 如果两个值都是“否”，本次不需要处理该货架。
		Category cate = (Category) Repository.getInstance().getNode(
				vo.getCid(), RepositoryConstants.TYPE_CATEGORY);

		if (cate == null)
		{
			LOG.info("找不到要更新的货架，cateId=" + vo.getCid());
			throw new BOException("找不到要更新的货架");
		}

		if (cate.getDelFlag() == 1)//
		{
			LOG.info("该货架已经下架，cateId=" + vo.getCid());
			throw new BOException("该货架已经下架,货架名称：" + cate.getName());
		}

		// 根据上次执行时间看是否需要本次执行。
		int sortId = 1000;// 默认从1000开始排序。

		if (IsNeedExecution())
		{
			// 初始化黑名单应用到缓存
			if (BLACKAPPCACHE == null || BLACKAPPCACHE.size() <= 0)
			{
				this.getBlackContentList();
			}
			
			// 初始化白名单应用到缓存
			if (WHITEAPPCACHE == null || WHITEAPPCACHE.size() <= 0)
			{
				this.getWhiteAppCache();
			}

			// 增量刷新货架ADD BY AIYAN 2011-12-20
			if (rule.getRuleType() == CategoryConstants.RULETYPE_ADD_REFRESH)
			{
				// 先获取该货架下商品
				LOG.info("货架更新开始。。。。");

				// 存放精品库和产品库的商品。
				List eliteList = new ArrayList();
				List productList = new ArrayList();
				Map idCompanyid = new HashMap();
				Map companyIdCount = new HashMap();
				if (rule.getEliteConds().size() != 0) // 有获取精品库的条件则执行。
				{
					eliteList = new ArrayList();
					for (int i = 0; i < rule.getEliteConds().size(); i++)
					{
						eliteList.addAll(getContentIDWithMap((ConditionVO) rule
								.getEliteConds().get(i), idCompanyid));
					}
				}
				if (rule.getContentConds().size() != 0) // 有获取产品库的条件则执行。
				{
					productList = new ArrayList();
					for (int i = 0; i < rule.getContentConds().size(); i++)
					{
						productList.addAll(getContentIDWithMap(
								(ConditionVO) rule.getContentConds().get(i),
								idCompanyid));
					}

				}
				LOG.info("去重数据");// 打印日志
				// 去重数据
				PublicUtil.removeDuplicateWithOrder(eliteList);
				PublicUtil.removeDuplicateWithOrder(productList);
				// 去掉AP应用黑名单中的应用
				LOG.info("AP应用黑名单");// 打印日志
				this.removeConInBlackList(eliteList);
				this.removeConInBlackList(productList);
				if (rule.getRandomFactor() == CategoryConstants.RULE_RANDOM_ALL)// 需要大随机
				{
					productList = radomList(productList, rule.getRandomFactor());
				}
				
				// 需要先下架所有商品
				// 我想得到该货架下目前有的商品。
				List currentList = getContentIDListById(cate.getCategoryID());

				// 需要删除的集合。
				Set delSet = new HashSet();

				// 新增的某些商品确实在当前货架已经存在，需要删除。
				for (int i = 0; i < currentList.size(); i++)
				{
					// System.out.println("==="+(productList.contains(currentList.get(i)))+"--"+currentList.get(i));
					if (eliteList.contains(currentList.get(i)))
					{
						delSet.add(currentList.get(i));
					}
					else if (productList.contains(currentList.get(i)))
					{
						delSet.add(currentList.get(i));
					}
				}
				long del_startime = System.currentTimeMillis();//下架开始时间
				if (delSet.size() > 0)
				{
					LOG.info("开始下架" + delSet.size() + "个商品。");
					for (Iterator it = delSet.iterator(); it.hasNext();)
					{
						// System.out.println("--下架商品-"+c);
						CategoryTools.delGoodsByRefId(cate, it.next() + "");
					}
				}
				else
				{
					LOG.info("没有需要下架的商品。");
				}
				
				int maxSortid = getMaxSortId(cate.getCategoryID());

				LOG.info("货架的CategoryID值" + cate.getCategoryID() + "的最大排序值"
						+ maxSortid);

				sortId = maxSortid + eliteList.size() + productList.size();
				long del_endtime = System.currentTimeMillis();//下架结束，开始上架时间
				HashMap eliteMap = new HashMap();
				
				// 上架商品。先上架精品库的，再上架产品库的。
				if (LOG.isDebugEnabled())
				{
					LOG.debug("开始上架精品库的商品！");
				}
				for (int i = 0; i < eliteList.size(); i++)
				{
					String contId = (String) eliteList.get(i);
					try
					{
						// 上架队列中不能因为一个应用上架异常失败，而导致后续中断
						if (!filterContent(contId, idCompanyid, rule
								.getRandomFactor(), companyIdCount))
							continue;// add by aiyan 2012-02-22
						
						CategoryTools.addGood(cate, contId, sortId--,
								RepositoryConstants.VARIATION_NEW, true,null);
						eliteMap.put(contId, "");
					}
					catch (Exception ec)
					{

						LOG.error("为货架" + cate.getName() + "ID:"
								+ cate.getCategoryID() + ",上架精品库应用ID:" + contId
								+ "失败！", ec);
					}
				}
				if (LOG.isDebugEnabled())
				{
					LOG.debug("开始上架产品库的商品！");
				}
				for (int i = 0; i < productList.size(); i++)
				{
					String contId = (String) productList.get(i);
					if (!eliteMap.containsKey(contId))// 只有不在精品库的产品才可以上架。
					{
						try
						{
							// 上架队列中不能因为一个应用上架异常失败，而导致后续中断
							if (!filterContent(contId, idCompanyid, rule
									.getRandomFactor(), companyIdCount))
								continue;// add by aiyan 2012-02-22
							
							CategoryTools.addGood(cate, contId, sortId--,
									RepositoryConstants.VARIATION_NEW, true,null);
						}
						catch (Exception ec)
						{

							LOG.error("为货架" + cate.getName() + "ID:"
									+ cate.getCategoryID() + ",上架产品库应用ID:"
									+ contId + "失败！", ec);
						}

					}
				}
				// 打印日志
				LOG.info("上架完成");
				int maxGoodsNum = rule.getMaxGoodsNum();
				// System.out.println("maxGoodsNum:"+maxGoodsNum);

				if (maxGoodsNum > 0)
				{
					// -1表示增量刷新货架时不考虑总量大小，其它值也许就要进行货架商品数量的压缩了。
					if (eliteList.size() + productList.size()
							+ currentList.size() - delSet.size() > maxGoodsNum)
					{
						// 这个地方用SQL下架，而不是假的HIBERNATE下架，呵呵。add by aiyan
						// 2011-12-22
						LOG.info("开始下架超过maxGoodsNum值的商品。");
						try
						{
							CategoryTools.maxGoodsNum(cate.getCategoryID(),
									maxGoodsNum);
						}
						catch (BOException e)
						{
							LOG.error("下架超过maxGoodsNum值的商品失败！", e);
						}
						LOG.info("成功下架超过maxGoodsNum值的商品。");
					}
					else
					{
						LOG.info("无需压缩商品数量");
					}
				}
				else if (maxGoodsNum == 0)
				{
					LOG.error("用户将增量刷新的总商品量设置为0，程序不执行。因为这样做的后果是该货架将被清空，危险！");
				}
				//long del_startime = System.currentTimeMillis();//下架开始时间
				//long del_endtime = System.currentTimeMillis();//下架结束，开始上架时间
				//
				long add_endtime = System.currentTimeMillis();//上架结束，
				LOG.info("costtime-----cid=" + cate.getCategoryID() + "|ruleid="
						+ rule.getRuleId() + ";sql_time="
						+ (del_startime - starttime) + ";deltime="
						+ (del_endtime - del_startime)+";addtime="+(add_endtime-del_endtime));
			}
			// 需要商品重新生成。
			else if (rule.getRuleType() == CategoryConstants.RULETYPE_REFRESH
					|| rule.getRuleType() == CategoryConstants.RULETYPE_OPERATION)
			{
				// 先获取该货架下商品
				LOG.info("货架更新开始。。。。");

				// 存放精品库和产品库的商品。
				List eliteList = new ArrayList();
				List productList = new ArrayList();
				if (rule.getEliteConds().size() != 0) // 有获取精品库的条件则执行。
				{
					eliteList = new ArrayList();
					for (int i = 0; i < rule.getEliteConds().size(); i++)
					{
						eliteList.addAll(getContentIDList((ConditionVO) rule
								.getEliteConds().get(i), sqlSb));
					}

				}
				if (rule.getContentConds().size() != 0) // 有获取产品库的条件则执行。
				{
					productList = new ArrayList();
					for (int i = 0; i < rule.getContentConds().size(); i++)
					{
						productList.addAll(getContentIDList((ConditionVO) rule
								.getContentConds().get(i), sqlSb));
					}

				}
				LOG.info("去重数据");// 打印日志
				// 去重数据
				PublicUtil.removeDuplicateWithOrder(eliteList);
				PublicUtil.removeDuplicateWithOrder(productList);
				// 去掉AP应用黑名单中的应用
				LOG.info("AP应用黑名单");// 打印日志
				this.removeConInBlackList(eliteList);
				this.removeConInBlackList(productList);
				if (rule.getRandomFactor() == CategoryConstants.RULE_RANDOM_ALL)// 需要大随机
				{
					productList = radomList(productList, rule.getRandomFactor());
				}
				int totalc = eliteList.size() + productList.size();
				if (totalc <= 0)
				{
					// 待上架商品清单为空，容错上不可下架该货架所有存量商品
					LOG.info("该货架cateId=" + vo.getCid()
							+ "待上架商品 产品库，精品库数据为空，不执行全量下架");
					throw new BOException("该货架cateId=" + vo.getCid()
							+ "待上架商品 产品库，精品库数据为空，不执行全量下架,ruleid="
							+ rule.getRuleId());
				}
				// 需要先下架所有商品
				if (LOG.isDebugEnabled())
				{
					LOG.debug("开始下架本货架的所有商品。。。");
				}
				LOG.info("下架商品");// 打印日志
				
				long del_startime = System.currentTimeMillis();//下架开始时间
				
				CategoryTools.clearCateGoods(vo.getCid(), false);
				// 过滤精品库的商品使用，使用hashmap提高查询效率。
				HashMap eliteMap = new HashMap();
				// 上架商品。先上架精品库的，再上架产品库的。
				if (LOG.isDebugEnabled())
				{
					LOG.debug("开始上架精品库的商品！");
				}
				long del_endtime = System.currentTimeMillis();//下架结束，开始上架时间
				
				for (int i = 0; i < eliteList.size(); i++)
				{
					String contId = (String) eliteList.get(i);
					try
					{// 上架队列中不能因为一个应用上架异常失败，而导致后续中断
						CategoryTools.addGood(cate, contId, sortId--,
								RepositoryConstants.VARIATION_NEW, true,null);
						eliteMap.put(contId, "");
					}
					catch (Exception ec)
					{
						LOG.error("为货架" + cate.getName() + "ID:"
								+ cate.getCategoryID() + ",上架精品库应用ID:" + contId
								+ "失败！", ec);
					}

				}
				if (LOG.isDebugEnabled())
				{
					LOG.debug("开始上架产品库的商品！");
				}
				for (int i = 0; i < productList.size(); i++)
				{
					String contId = (String) productList.get(i);
					if (!eliteMap.containsKey(contId))// 只有不在精品库的产品才可以上架。
					{
						try
						{// 上架队列中不能因为一个应用上架异常失败，而导致后续中断
							CategoryTools.addGood(cate, contId, sortId--,
									RepositoryConstants.VARIATION_NEW, true,null);
						}
						catch (Exception ec)
						{

							LOG.error("为货架" + cate.getName() + "ID:"
									+ cate.getCategoryID() + ",上架产品库应用ID:"
									+ contId + "失败！", ec);
						}

					}
				}
				if (rule.getRuleType() == CategoryConstants.RULETYPE_OPERATION)// 如果是运营推荐的特殊分类，还需要对货架进行特殊处理。
				{

					sortId = 1000;// 重新初始化
					if (LOG.isDebugEnabled())
					{
						LOG.debug("开始上架机型货架");
					}
					// 保存每一个机型货架的需要上架的商品商品数（不包含精品库的商品），key deviceName value
					// list(内容id)
					HashMap deviceCategoryGoods = new HashMap();
					// 获取机型货架信息，key deviceName values Category对象
					HashMap deviceCategoryMapping = this.getSubCategory(cate,
							deviceCategoryGoods);
					if (LOG.isDebugEnabled())
					{
						LOG.debug("开始清空机型货架商品");
					}
					for (Iterator iter = deviceCategoryMapping.values()
							.iterator(); iter.hasNext();)
					{
						Category deviceCategory = (Category) iter.next();
						CategoryTools.clearCateGoods(deviceCategory, false);
					}

					// 记录精品库上架信息。key 货架name +
					// 内容id,主要用于产品库上架时，不上架精品库已经上架的内容。防止重复上架。
					// HashMap eliteMapFlag=new HashMap();
					// 上架所有的商品。
					// 上架精品库
					/*
					 * if(LOG.isDebugEnabled()) { LOG.debug("机型货架开始上架精品库的商品"); }
					 * for (int i = 0; i < eliteList.size(); i++) { String
					 * contId = (String) eliteList.get(i); List deviceNameList =
					 * getAppDeviceName(contId); for(int j=0;j<deviceNameList.size();j++) {
					 * Category
					 * deviceCategory=(Category)deviceCategoryMapping.get(deviceNameList.get(j));
					 * if(deviceCategory!=null)//存在需要把该应用上架到该机型货架。 {
					 * CategoryTools.addGood(deviceCategory, contId, sortId--,
					 * RepositoryConstants.VARIATION_NEW, true);
					 * eliteMapFlag.put(deviceCategory.getName()+contId,
					 * "");//记录精品库上架机型货架信息 } } }
					 */
					for (int i = 0; i < eliteList.size(); i++)
					{
						String contId = (String) eliteList.get(i);
						List deviceNameList = getAppDeviceName(contId);
						for (int j = 0; j < deviceNameList.size(); j++)
						{
							List temp = (List) deviceCategoryGoods
									.get(deviceNameList.get(j));
							if (temp != null)// 需要把精品库和产品库合并在一起，然后过滤sp后，一并上架
							{
								temp.add(contId);
							}
						}

					}

					/*
					 * //计算每一个机型货架上架的商品列表。 if(LOG.isDebugEnabled()) {
					 * LOG.debug("机型货架开始上架产品库"); }
					 */
					for (int i = 0; i < productList.size(); i++)
					{
						String contId = (String) productList.get(i);
						// 判断该内容需要上架那几个机型货架。
						List deviceNameList = getAppDeviceName(contId);

						for (int j = 0; j < deviceNameList.size(); j++)
						{
							List temp = (List) deviceCategoryGoods
									.get(deviceNameList.get(j));
							if (temp != null)// 存在需要把该应用上架到该机型货架。
							{
								temp.add(contId);
							}
						}
					}
					// 上架产品库和精品库
					for (Iterator iter = deviceCategoryGoods.keySet()
							.iterator(); iter.hasNext();)
					{
						String deviceName = (String) iter.next();
						if (LOG.isDebugEnabled())
						{
							LOG.debug("开始上架机型货架：" + deviceName);
						}
						Category categoryCategory = (Category) deviceCategoryMapping
								.get(deviceName);
						List contIdList = (List) deviceCategoryGoods
								.get(deviceName);

						if (rule.getRandomFactor() > 0
								&& rule.getRandomFactor() < 100)
						{
							contIdList = radomList(contIdList, rule
									.getRandomFactor());// 需要根据分组随即排序
						}
						
						// 先要去掉精品库和产品库的重复的内容
						PublicUtil.removeDuplicateWithOrder(contIdList);
						
						// 还要根据sp调整顺序。
						contIdList = CategoryUpdateTools.getInstance()
								.sortListBySpName(contIdList,
										CategoryRuleConfig.SPNAMESORTCOUNT,
										CategoryRuleConfig.SPNAMEMAXCOUNT);
						sortId = 1000;
						for (int i = 0; i < contIdList.size(); i++)
						{
							String contId = (String) contIdList.get(i);
							// if(!eliteMapFlag.containsKey(categoryCategory.getName()+contId))//如果精品库商品存在就不需要上架。
							{
								try
								{
									// 上架队列中不能因为一个应用上架异常失败，而导致后续中断
									CategoryTools.addGood(categoryCategory,
											contId, sortId--,
											RepositoryConstants.VARIATION_NEW,
											true,null);
								}
								catch (Exception ec)
								{

									LOG.error("为货架" + cate.getName() + "ID:"
											+ cate.getCategoryID()
											+ ",上架产品库应用ID:" + contId + "失败！",
											ec);
								}
							}
						}
					}
				}
				LOG.info("上架完成");// 打印日志
				long add_endtime = System.currentTimeMillis();//上架结束
				 LOG.info("costtime-----cid=" + cate.getCategoryID() + "|ruleid="
						+ rule.getRuleId() + ";sql_time="
						+ (del_startime - starttime) + ";deltime="
						+ (del_endtime - del_startime)+";addtime="+(add_endtime-del_endtime));
			}
			// 刷新和重新生成商品时互斥操作。
			else if (rule.getRuleType() == CategoryConstants.RULETYPE_REORDER)//
			{
				// 获取当前货架下商品，并排好序。
				LOG.info("货架重新排序开始。。。。");

				// 商品排序的规则的条件只有排序一个条件即可。
				if (rule.getContentConds().size() == 0)
				{
					LOG.error("该排序规则没有定义排序条件，本货架不进行排序。");
					throw new BOException("该排序规则没有排序条件异常");
				}
				ConditionVO condition = (ConditionVO) rule.getContentConds()
						.get(0);
				List list = getSortedGoods(cate, condition);
				
				if (rule.getRandomFactor() == CategoryConstants.RULE_RANDOM_ALL)// 需要大随机
				{
					list = radomList(list, rule.getRandomFactor());
				}
				
				// 黑名单应用置底
				this.changeToLastBlackApp(list);
				
				List goodList = new ArrayList();
				
				// 设置排序号
				for (int i = 0; i < list.size(); i++)
				{
					ReferenceNode node = new ReferenceNode();
					node.setId((String) list.get(i));
					node.setSortID(sortId--);
					goodList.add(node);
				}
				
				// 更新到数据库中。
				if (goodList.size() > 0)// 只有含有商品才需要更新排序值。
				{
					updateSortId(goodList);
				}
			}
			else if (rule.getRuleType() == CategoryConstants.RULETYPE_BOOK_COMMEND)
			{
				LOG.info("开始处理基地图书分类运营分类推荐");
				int count = JDSyncBO.getInstance().updateYYBOOkCommend();//
				LOG.info("总共成功推荐了" + count + "个分类。");
			}
			
			// 更新货架的最后一次商品自动处理执行时间。
			long endtime = System.currentTimeMillis();
			long exetime = endtime - starttime;
			CategoryRuleDAO.getInstance().updateRuleLastExecTime(vo.getCid(),
					exetime, new Date());
			return CategoryRuleTask.UPDATESUCESS;
		}
		else
		{
			LOG.info("本次不需要执行。货架内码id=" + vo.getCid());
			return CategoryRuleTask.EXECUTED;

		}

	}
	
	private boolean filterContent(String contId, Map idcompanyId,
			int randomFactor, Map companyIdCount) throws Exception
	{
		if (randomFactor <= 0)
			return true;
		String companyId = idcompanyId.get(contId) + "";
		String count = companyIdCount.get(companyId) + "";
		if ("null".equals(count))
		{
			count = "1";
		}
		else
		{
			try
			{
				count = (Integer.parseInt(count) + 1) + "";
			}
			catch (Exception e)
			{
				LOG.error("contId:" + contId + "数据转换异常", e);
				return true;// 当出现转换异常的时候，就不再考虑这样的单个ICPCODE 的上架产品的数量限制，容错而已。
			}
		}

		companyIdCount.put(companyId, count);

		try
		{
			// 如果为ICPCODE白名单列表数据，则直接跳过
			if(WHITEAPPCACHE.contains(companyId))
			{
				return true;
			}
			
			if (Integer.parseInt(count) > randomFactor)
			{
				LOG.info(contId + "--" + companyId + "--" + count
						+ "出界了。呵呵。。。好。。。");
				return false;
			}
		}
		catch (Exception e)
		{
			LOG.error("contId:" + contId + "数据转换异常", e);
			return true;// 当出现转换异常的时候，就不再考虑这样的单个ICPCODE 的上架产品的数量限制，容错而已。
		}

		LOG.debug(contId + "--" + companyId + "--" + count);
		return true;
	}

	private int getMaxSortId(String categoryid) {
		if(LOG.isDebugEnabled())
		{
			LOG.debug("开始获取当前货架下的MAX SORTID。。。");
		}
		ResultSet rs = null;
		
		String sql = "select max(sortid) from t_r_reference r"+
			  " where r.categoryid=? "
			  ;
		try{
			 rs = DB.getInstance().query(sql, new String[]{categoryid});
			if (rs.next()){
				//return Integer.parseInt(rs.getInt(1));
				return rs.getInt(1);
			}
		} catch (Exception e){
		
			LOG.error("获取当前货架下的最大SORTID出异常。getMaxSortId("+categoryid+")",e);
			return 0;
			
		}finally{
			DB.close(rs);
		}
		return 0;
	}
	private List getContentIDListById(String categoryid) throws BOException {
		if(LOG.isDebugEnabled())
		{
			LOG.debug("开始获取当前货架下的所有商品。。。");
		}
		List list = new ArrayList();
		ResultSet rs = null;
		
		String sql = "select r.REFNODEID from t_r_reference r"+
			  " where r.categoryid=? ";
		try{
			 rs = DB.getInstance().query(sql, new String[]{categoryid});
			while (rs.next()){
				list.add(rs.getString("REFNODEID"));
			}
		} catch (Exception e){
			
			throw new BOException("获取当前货架下的所有商品出错.getContentIDListById("+categoryid+")",e);
		}finally{
			DB.close(rs);
		}
		return list;
	}
	
	
	private  List getAppDeviceName(String contId) throws BOException
	{
		List deviceNameList=(List)DEVICENAMECACHE.get(contId);
		if(deviceNameList==null)
		{
			deviceNameList=CategoryRuleBO.getInstance().DeviceNameList(contId);
			DEVICENAMECACHE.put(contId, deviceNameList);
		}
		return deviceNameList;
	}

	/**
	 * 判断当前时间,此次任务是否需要执行。
	 * @return
	 */
	private boolean IsNeedExecution()//(Date lastExcuteTime, int period)
	{
		Calendar now = Calendar.getInstance();
		if(CategoryConstants.INTERVALTYPE_DAY==rule.getIntervalType())
		{
			if(vo.getLastExcuteTime()==null)
			{
				return true;
			}
			Calendar lastDate = Calendar.getInstance();
			//需要把Date类型转化为Calendar
			lastDate.setTime(vo.getLastExcuteTime());
			return compareDateDif(now, lastDate, Calendar.DAY_OF_MONTH, rule.getIntervalCount());
		}else if(rule.getIntervalType()==CategoryConstants.INTERVALTYPE_WEEK)
			
		{
			//Calendar返回值中，周日是1，周六是7,需要转换成中国的标准。
			//周一是1，周日是7
			int weekDay=now.get(Calendar.DAY_OF_WEEK);
			weekDay=weekDay-1;
			if(weekDay==0)
			{
				weekDay=7;
			}
			if(weekDay!=rule.getExcuteTime())
			{
				return false;
			}
			if(vo.getLastExcuteTime()==null)
			{
				return true;
			}
			Calendar lastDate = Calendar.getInstance();
			//需要把Date类型转化为Calendar
			lastDate.setTime(vo.getLastExcuteTime());
			return compareDateDif(now, lastDate, Calendar.WEEK_OF_MONTH, rule.getIntervalCount());
		}else if (rule.getIntervalType()==CategoryConstants.INTERVALTYPE_MONTH)
		{
			int monthDay=now.get(Calendar.DAY_OF_MONTH);
			if(monthDay!=rule.getExcuteTime())
			{
				return false;
			}
			if(vo.getLastExcuteTime()==null)
			{
				return true;
			}
			Calendar lastDate = Calendar.getInstance();
			//需要把Date类型转化为Calendar
			lastDate.setTime(vo.getLastExcuteTime());
			return compareDateDif(now, lastDate, Calendar.MONTH, rule.getIntervalCount());
		}else
		{
			LOG.error("目前还不支持此类型，IntervalType="+rule.getIntervalType());
			return false;
		}
		
	}
	/**
	 * 比较nowDate与lastDate相差时间是否大于等于count个以field 为单位的时间。
	 * @param nowDate 当前时间
	 * @param lastDate 上次更新时间
	 * @param field 相差的单位，日，周，月
	 * @param count 单位时间的个数
	 */
	private boolean compareDateDif(Calendar nowDate,Calendar lastDate,int field,int count)
	{
		nowDate.add(field, -count);
		//比较字符串判断当前时间是否相同。
		String data1String=PublicUtil.getDateString(nowDate.getTime(), "yyyyMMdd");
		String data2String=PublicUtil.getDateString(lastDate.getTime(), "yyyyMMdd");

		int result=data1String.compareTo(data2String);
		if(result>=0)
		{
			return true;
		}else
		{
			return false;
		}
	}

	/**
	 * 判断当前规则是是否已经有效。
	 * @param date 有效日期
	 * @return 当前日期大于等于date既表示有效（以天为单位进行比较）。
	 */
	private boolean isEffective(Date date)
	{
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		return now.getTime().after(date);
	}

	private List getContentIDList(ConditionVO condition,StringBuffer sqlSb) throws BOException
	{
		String sql;
		String paras[]=null;
		String condType;
        BaseCondVO baseCond = null;
		
		try
		{
            baseCond = ConditionUpdateDAO.getInstance()
                                         .queryBaseCondVO(String.valueOf(condition.getCondType()));

            if (baseCond == null)
            {
                throw new BOException("查不到当前这种条件的类型。condType="
                                      + condition.getCondType());
            }

            condType = baseCond.getBaseName() + "条件sql：";
            sql = baseCond.getBaseSQL();

            if (condition.getCondType() == CategoryConstants.CONDTYPE_ELITE)// 精品库的条件
            {
                // condType="精品库获取条件sql：";
                // sql = DB.getInstance().getSQLByCode(
                // "category.rule.CategoryRuleExcutor.SELECT.getEliteList");
                paras = new String[1];
                String categoryid = CategoryDAO.getInstance()
                                               .getCategoryIDByID(condition.getCid());
                paras[0] = categoryid;
            }
            // else
            // if(condition.getCondType()==CategoryConstants.CONDTYPE_PROD_OWNER)//从产品库获取自有业务的条件
            // {
            // condType="产品库自有业务获取条件sql：";
            // sql = DB.getInstance().getSQLByCode(
            // "category.rule.CategoryRuleExcutor.SELECT.getOwnerContentIDList");
            // }else
            // if(condition.getCondType()==CategoryConstants.CONDTYPE_PROD_OTHERS)//从产品库获非自有业务的条件
            // {
            // condType="产品库非自有业务获取条件sql：";
            // sql = DB.getInstance().getSQLByCode(
            // "category.rule.CategoryRuleExcutor.SELECT.getOtherContentIDList");
            // }else
            // if(condition.getCondType()==CategoryConstants.CONDTYPE_PROD_BASEGAME)//只处理基地游戏
            // {
            // condType="产品库基地游戏获取条件sql：";
            // sql = DB.getInstance().getSQLByCode(
            // "category.rule.CategoryRuleExcutor.SELECT.getBaseGameContentIDList");
            // }else
            // if(condition.getCondType()==CategoryConstants.CONDTYPE_PROD_BRAND)//只处理品牌店套餐业务
            // {
            // condType="品牌店套餐业务获取条件sql：";
            // sql = DB.getInstance().getSQLByCode(
            // "category.rule.CategoryRuleExcutor.SELECT.getBrandContentIDList");
            //            }
            //            else if(condition.getCondType()==CategoryConstants.CONDTYPE_PROD_BASE)//只处理基本业务
            //            {
            //                condType="基本业务获取条件sql：";
            //                sql = DB.getInstance().getSQLByCode(
            //                "category.rule.CategoryRuleExcutor.SELECT.getBaseContentIDList");
            //            }else
            //			{
            //				throw new BOException("目前还不支持该条件的类型。condType="+condition.getCondType());
            //			}

        } catch (DAOException e1)
		{
        
			throw new BOException("获取内容出错",e1);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		if (condition.getWSql() != null)
		{
			sb.append(" and ");
			sb.append(condition.getWSql());
		}
		if (condition.getOSql() != null&&!"".equals(condition.getOSql().trim()))
		{
			sb.append(" order by ");
			sb.append(condition.getOSql());
		}
		List list = new ArrayList();
		int count=condition.getCount();
		if(count==-1)//-1表示系统不限制个数,使用配置项中的最大值。
		{
			count=CategoryRuleConfig.CONDITIONMAXVALUE;//default;
		}

		 //最后组装sql，只读取制定的行数据。
		 sql= "select * from ("+sb.toString()+")where rownum <= "+count;
		 
		LOG.info(condType+sql);//打印sql日志
		ResultSet rs = null;
		try
		{
			 rs = DB.getInstance().query(sql, paras);
			while (rs.next())
			{
				list.add(rs.getString("id"));
			}
		} catch (Exception e)
		{
			LOG.error("获取内容出错:"+condType+sql, e);
			throw new BOException("获取内容出错",e);
		}finally{
			DB.close(rs);
		}
		sqlSb =sqlSb.append(sql);
		sqlSb =sqlSb.append(";-----;");
		return list;
	}
	
	//MAP参数的目的是得到Content-companyid的映射。
	private  List getContentIDWithMap(ConditionVO condition,Map map) throws BOException
	{
		String sql;
		String paras[]=null;
		String condType;
        BaseCondVO baseCond = null;
		try
		{
            baseCond = ConditionUpdateDAO.getInstance()
                                         .queryBaseCondVO(String.valueOf(condition.getCondType()));
            
            if (baseCond == null)
            {
                throw new BOException("查不到当前这种条件的类型。condType="
                                      + condition.getCondType());
            }

            condType = baseCond.getBaseName() + "条件sql：";
            sql = baseCond.getBaseSQL();
            
            
            
            LOG.debug("AIYA11N:"+sql.toLowerCase());
            LOG.debug("AIYAN:"+sql.toLowerCase().indexOf("from"));
//            System.out.println("pp:"+sql.toLowerCase());
//            System.out.println("pp:"+sql.toLowerCase().indexOf("from"));
            int p = sql.toLowerCase().indexOf("from");
            if(p!=-1){
            	sql = sql.substring(0,p)+",g.companyid "+sql.substring(p);
            }else{
            	//System.out.println(sql);
            }

            if (condition.getCondType() == CategoryConstants.CONDTYPE_ELITE)// 精品库的条件
            {
                paras = new String[1];
                String categoryid = CategoryDAO.getInstance()
                                               .getCategoryIDByID(condition.getCid());
                paras[0] = categoryid;
            }

        } catch (DAOException e1)
		{
			throw new BOException("获取内容出错",e1);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		if (condition.getWSql() != null)
		{
			sb.append(" and ");
			sb.append(condition.getWSql());
		}
		if (condition.getOSql() != null&&!"".equals(condition.getOSql().trim()))
		{
			sb.append(" order by ");
			sb.append(condition.getOSql());
		}
		int count=condition.getCount();
		if(count==-1)//-1表示系统不限制个数,使用配置项中的最大值。
		{
			count=CategoryRuleConfig.CONDITIONMAXVALUE;//default;
		}

		 //最后组装sql，只读取制定的行数据。
		 sql= "select * from ("+sb.toString()+")where rownum <= "+count;
		 
		LOG.info(condType+sql);//打印sql日志
		ResultSet rs = null;
		List list = new ArrayList();
		try
		{
			 rs = DB.getInstance().query(sql, paras);
			while (rs.next())
			{
				list.add(rs.getString("id"));
				//下面这句是辅助功能的。为了得到id-companyid的映射。
				map.put(rs.getString("id"), rs.getString("companyid"));
			}
		} catch (Exception e)
		{
			LOG.error("获取内容出错:"+condType+sql, e);
			throw new BOException("获取内容出错");
		}finally{
			DB.close(rs);
		}
		return list;
	}
	
	
	private List getSortedGoods(Category cate,ConditionVO condition) throws BOException
	{
		if(LOG.isDebugEnabled())
		{
			LOG.debug("开始获取当前货架下的所有商品。。。");
		}
		String sql;
		try
		{
			// select r.id from t_r_gcontent g ,t_r_reference r
			//where r.categoryid=? and r.refnodeid=g.id
			sql = DB.getInstance().getSQLByCode(
					"category.rule.CategoryRuleExcutor.SELECT.getSortedGoods");
		} catch (DAOException e1)
		{
			
			throw new BOException(
					"查询sqlcode异常，sqlCode=category.rule.CategoryRuleExcutor.SELECT.getSortedGoods",e1);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		if (condition.getOSql() != null&&!condition.getOSql().equals(""))
		{
			sb.append(" order by ");
			sb.append(condition.getOSql());
		}
		
		LOG.info("getSortedGoods:"+sb.toString());
		LOG.info("paras:"+cate.getCategoryID());
		
		String paras[] = { cate.getCategoryID() };
		List list = new ArrayList();
		ResultSet rs = null;
		try
		{
			 rs = DB.getInstance().query(sb.toString(), paras);
			while 	(rs.next())
			{
				list.add(rs.getString("id"));
			}
		} catch (Exception e)
		{
			
			throw new BOException("从内容库获取内容出错.getSortedGoods="+sb.toString()+",paras="+cate.getCategoryID(),e);
		}finally{
			DB.close(rs);
		}

		return list;
	}

	private void updateSortId(List goodList) throws BOException
	{
		if(LOG.isDebugEnabled())
		{
			LOG.debug("开始更新当前货架的排序值。。。");
		}
		String sqlCode = "category.rule.CategoryRuleExcutor.UPDATE.updateSortId";
		Object mutiParas[][] = new Object[goodList.size()][2];
		for (int i = 0; i < goodList.size(); i++)
		{
			ReferenceNode node = (ReferenceNode) goodList.get(i);
			mutiParas[i][0] = new Integer(node.getSortID());
			mutiParas[i][1] = node.getId();
		}
		try
		{
			DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
		} catch (DAOException e)
		{
			
			throw new BOException("更新货架商品排序异常",e);
		}
	}
	public static void clearCache()
	{
		//APPCATENAMECACHE.clear();
		//THIRDCATEGORYNAMEMAPPING.clear();
		//CATEGORYCLEANFLAGMAP.clear();
		DEVICENAMECACHE.clear();
		SPNAMECACHE.clear();
		BLACKAPPCACHE.clear();
		WHITEAPPCACHE.clear();
	}
	/**
	 * 获取该分类下机型货架信息
	 * @param category 自动更新的货架
	 * @param deviceCategoryGoods  初始化子货架的信息
	 * @return 
	 * @throws BOException
	 */
	private HashMap getSubCategory(Category category, HashMap deviceCategoryGoods)
			throws BOException
	{
		List cateList = category.getNode(RepositoryConstants.TYPE_CATEGORY, null);
		HashMap deviceCategory = new HashMap();
		for (int i = 0; i < cateList.size(); i++)
		{
			Category deviceCate = (Category) cateList.get(i);
			deviceCategory.put(deviceCate.getName(), deviceCate);
			deviceCategoryGoods.put(deviceCate.getName(), new ArrayList());
		}

		return deviceCategory;
	}

	/**
	 * 随即排序。根据排序因子t，决定每一组的大小。然后对每一组随即排序。
	 * 分组大小=list.size * t
	 * @param list 待排序的分组
	 * @param t 分组大小的百分值 0~100
	 * @return 返回排好序的列表。0直接返回
	 */
	public static List radomList(List list, int randomFactor)
	{
		if (randomFactor > 100||randomFactor<=0)
		{
			//参数不对不排序
			return list;
		}
		int sum = list.size();
		int groupSize = Math.round(sum*((float)randomFactor /100));//确定分组大小,返回最接近的整数。
		//System.out.println("sum=" + sum + ";t1=" + t1);
		if(groupSize<2)//只有分组大小为2才有意思。
		{
			return list;
		}
		List newList = new ArrayList();
		for (int i = 0; i < sum;)
		{
			int end=i+groupSize;
			if(end>sum)
			{
				end=sum;
			}
			List groupList = list.subList(i, end);
			Collections.shuffle(groupList);
			newList.addAll(groupList);
			i=end;
		}
		return newList;
	}
	
	/**
	 * 去掉存在黑名单中应用
	 * @param conId 内容内码
	 * @return true 不是黑名单，可以上架，false  是黑名单，不可以上架
	 * @throws BOException 
	 */
	private synchronized void removeConInBlackList(List contentlist) throws BOException {
		Map hm = null;
		if(BLACKAPPCACHE != null){
			 hm = BLACKAPPCACHE;
		}else{
			this.getBlackContentList();
			 hm =BLACKAPPCACHE;
		}
		if(LOG.isDebugEnabled()){
			LOG.debug("黑名单应用不上架");
		}
		for(int i = 0 ; i < contentlist.size();i++){
			String conId = (String)contentlist.get(i);
			String values = (String) hm.get(conId);
			if (values != null && values.equals("1")) {
				LOG.error("conId="+conId+",在AP应用黑名单中，不予上架");
				contentlist.remove(i);
				i--;//防止连续的漏掉了
				//return false;
			}
			
		}
//		if (conId != null && conId.length() > 0) {
//			
//		}
	//	return true;
	}
	/**
	 * 
	 * @param contentlist
	 * @throws BOException 
	 */
	private  void changeToLastBlackApp(List contentlist) throws BOException{
		List blackapp = new ArrayList();
		if(contentlist != null && contentlist.size() > 0){
			
			Map hm = null;//获取黑名单应用
			if(BLACKAPPCACHE != null){
				 hm = BLACKAPPCACHE;
			}else{
				 this.getBlackContentList();
				 hm = BLACKAPPCACHE;
				 
			}
			if(LOG.isDebugEnabled()){
				LOG.debug("黑名单应用置底");
			}
			for(int i = 0 ; i < contentlist.size(); i ++){
				String conId = (String)contentlist.get(i);
				ReferenceNode ref = (ReferenceNode) Repository.getInstance().getNode(conId,
						RepositoryConstants.TYPE_REFERENCE);
				String values = (String) hm.get(ref.getRefNodeID());
				if (values != null && values.equals("1")) {
					LOG.error("conId="+conId+",在AP应用黑名单中，置底显示-move to last");
					blackapp.add(contentlist.get(i));
					contentlist.remove(i);
					i--;//防止连续的漏掉了
					//return false;
				}
			}
			contentlist.addAll(blackapp);		
		}		
		//return blackcontentlastlist;
	}
	
	
	/**
	 * 获取AP刷榜黑名单应用
	 * @return 
	 * @throws BOException
	 */
	private  HashMap getBlackContentList() throws BOException {

		HashMap blackContentMap = new HashMap();
		String sqlCode = "category.rule.CategoryRuleExcutor.getBlackContentList().SELECT";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			if(LOG.isDebugEnabled()){
				LOG.debug("从数据库获取应用黑名单列表");
			}
			while (rs.next()) {
//				blackContentMap.put(rs.getString("cid"), "1");
				BLACKAPPCACHE.put(rs.getString("cid"), "1");

			}
		} catch (DAOException e) {
		
			LOG.error("从数据库获取应用黑名单列表,DAO操作错误",e);
			throw new BOException("DAO操作错误", e);
		} catch (SQLException e1) {
			
			LOG.error("从数据库获取应用黑名单列表,DAO操作错误",e1);
			throw new BOException("SQL操作错误", e1);
		}finally{
			DB.close(rs);
		}

		return blackContentMap;
	}
	
	/**
	 * 获取AP刷榜黑名单应用
	 * @return 
	 * @throws BOException
	 */
	private  void getWhiteAppCache() throws BOException
	{
		if(LOG.isDebugEnabled())
		{
			LOG.debug("设置白名单ICPCODE列表:开始");
		}
		ResultSet rs = null;
        try
        {
            // select * from t_content_whitelist
            rs = DB.getInstance()
                   .queryBySQLCode("category.rule.CategoryRuleExcutor.getWhiteAppCache().select",
                                   null);
            
            while (rs.next())
            {
            	WHITEAPPCACHE.add(rs.getString("companyid"));
            }
        }
        catch (SQLException e)
        {
        	LOG.error("得到获取白名单companyid列表数据的SQL语句时发生异常",e);
            throw new BOException("得到获取白名单companyid列表数据的SQL语句时发生异常", e);
        }
		catch (DAOException e)
		{
        	LOG.error("用于返回自动更新定义的白名单companyid列表数据时发生异常",e);
            throw new BOException("用于返回自动更新定义的白名单companyid列表数据时发生异常", e);
		}
        finally
        {
            DB.close(rs);
        }

		if(LOG.isDebugEnabled())
		{
			LOG.debug("设置白名单companyid列表:结束");
		}
	}
	
	
	public void task() throws Throwable
	{
		try
		{
			int result=excucte();
			if(result==CategoryRuleTask.EXECUTED)
			{
				//executedCount++;
				task.addExecutedCount();
			}
			if(result==CategoryRuleTask.INEFFECTIVE)
			{
				task.addIneffectiveCount();
				//ineffectiveCount++;
			}
			if(result==CategoryRuleTask.UPDATESUCESS)
			{
				//succCount++;
				task.addSuccCount();
			}
		}
		catch(Exception e)
		{
			LOG.error("货架更新失败。"+vo,e);
			e.printStackTrace();
			//errorList.add("id="+categoryRule.getCid()+",name="+cateNamePath);
			task.addError(vo);
			
		}
	}
	public void mutask() throws Throwable
	{
		try
		{
			int result=excucte();
			if(result==CategoryRuleTask.EXECUTED)
			{
				//executedCount++;
				categoryRuleBO.addExecutedCount();
			}
			if(result==CategoryRuleTask.INEFFECTIVE)
			{
				categoryRuleBO.addIneffectiveCount();
				//ineffectiveCount++;
			}
			if(result==CategoryRuleTask.UPDATESUCESS)
			{
				//succCount++;
				categoryRuleBO.addSuccCount();
			}
		}
		catch(Exception e)
		{
			LOG.error("货架更新失败。"+vo,e);
			e.printStackTrace();
			//errorList.add("id="+categoryRule.getCid()+",name="+cateNamePath);
			//categoryRuleBO.addError(vo);
			categoryRuleBO.addErrorInfo(vo,e.getMessage());
			
		}
	}
	
}
