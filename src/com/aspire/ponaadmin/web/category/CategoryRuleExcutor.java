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
 * ����ִ���������ݻ��ܵ�ִ�й���ִ�е������ܵĸ�������
 * @author zhangwei
 *
 */
public class CategoryRuleExcutor extends Task
{
	/**
	 * �û��ܵ���Ϣ
	 */
	private CategoryRuleVO vo;
	/**
	 * ִ�и÷�����task��
	 */
	private CategoryRuleTask task;
	
	/**
	 * ִ�и÷�����task��
	 */
	private CategoryRuleBO categoryRuleBO;
	/**
	 * �û��ܶ�Ӧ�Ĺ���
	 */
	private RuleVO rule;
	private static JLogger LOG = LoggerFactory.getLogger(CategoryRuleExcutor.class);

	/**
	 *  Ӧ�ö�Ӧ�Ļ��ͷ����ֵ�Ļ���
	 */
	private static Map DEVICENAMECACHE=Collections.synchronizedMap(new HashMap()) ;
	
	/**
	 * ����spname ��Ϣ
	 */
	public static Map SPNAMECACHE=Collections.synchronizedMap(new HashMap()) ;
	
	/**
	 * ���������Ӧ����Ϣ
	 */
	public static Map BLACKAPPCACHE = Collections.synchronizedMap(new HashMap()) ;
	
	/**
	 * ���������ICPCODE�б���Ϣ
	 */
	public static Set WHITEAPPCACHE = Collections.synchronizedSet(new HashSet());
	
	public CategoryRuleExcutor(CategoryRuleVO vo)
	{
		this.vo=vo;
		this.rule = vo.getRuleVO();
	}
	/**
	 * ���̣߳�ȫ������ִ������
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
	 * ���̣߳�ȫ������ִ������
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
	 * �Զ�����������
	 * @return
	 * @throws BOException
	 */
	public int excucte() throws BOException
	{
		if (rule == null)
		{
			throw new BOException("���ܸ��¹���Ϊnull");
		}

		AutoSyncDAO.getInstance().addAutoRef(vo.getCid());// wml+ �����ȱ�����Ʒ��Ϣ������������ͷ�á�
		
		Date date = vo.getEffectiveTime();

		if (!isEffective(date))
		{
			LOG.info("����idΪ" + vo.getCid() + "�Ļ��ܻ�û����Ч������Ҫִ�С���Чʱ��:"
					+ PublicUtil.getDateString(date));
			return CategoryRuleTask.INEFFECTIVE;
		}

		LOG.info("��ʼ�Ի���idΪ��" + vo.getCid() + "�Ļ��ܽ��и���");

		long starttime = System.currentTimeMillis();

		// ��¼ִ����䳬���ı���
		StringBuffer sqlSb = new StringBuffer();

		if (LOG.isDebugEnabled())
		{
			LOG.debug("�û��ܵĹ���Ϊ��" + rule);
		}

		// ���ݹ����е���Ʒ�Ƿ���Ҫ�Զ����ɺ���Ʒ�Ƿ���Ҫ���¼����������жϱ����Ƿ���Ҫ����û��ܡ�
		// �������ֵ���ǡ��񡱣����β���Ҫ����û��ܡ�
		Category cate = (Category) Repository.getInstance().getNode(
				vo.getCid(), RepositoryConstants.TYPE_CATEGORY);

		if (cate == null)
		{
			LOG.info("�Ҳ���Ҫ���µĻ��ܣ�cateId=" + vo.getCid());
			throw new BOException("�Ҳ���Ҫ���µĻ���");
		}

		if (cate.getDelFlag() == 1)//
		{
			LOG.info("�û����Ѿ��¼ܣ�cateId=" + vo.getCid());
			throw new BOException("�û����Ѿ��¼�,�������ƣ�" + cate.getName());
		}

		// �����ϴ�ִ��ʱ�俴�Ƿ���Ҫ����ִ�С�
		int sortId = 1000;// Ĭ�ϴ�1000��ʼ����

		if (IsNeedExecution())
		{
			// ��ʼ��������Ӧ�õ�����
			if (BLACKAPPCACHE == null || BLACKAPPCACHE.size() <= 0)
			{
				this.getBlackContentList();
			}
			
			// ��ʼ��������Ӧ�õ�����
			if (WHITEAPPCACHE == null || WHITEAPPCACHE.size() <= 0)
			{
				this.getWhiteAppCache();
			}

			// ����ˢ�»���ADD BY AIYAN 2011-12-20
			if (rule.getRuleType() == CategoryConstants.RULETYPE_ADD_REFRESH)
			{
				// �Ȼ�ȡ�û�������Ʒ
				LOG.info("���ܸ��¿�ʼ��������");

				// ��ž�Ʒ��Ͳ�Ʒ�����Ʒ��
				List eliteList = new ArrayList();
				List productList = new ArrayList();
				Map idCompanyid = new HashMap();
				Map companyIdCount = new HashMap();
				if (rule.getEliteConds().size() != 0) // �л�ȡ��Ʒ���������ִ�С�
				{
					eliteList = new ArrayList();
					for (int i = 0; i < rule.getEliteConds().size(); i++)
					{
						eliteList.addAll(getContentIDWithMap((ConditionVO) rule
								.getEliteConds().get(i), idCompanyid));
					}
				}
				if (rule.getContentConds().size() != 0) // �л�ȡ��Ʒ���������ִ�С�
				{
					productList = new ArrayList();
					for (int i = 0; i < rule.getContentConds().size(); i++)
					{
						productList.addAll(getContentIDWithMap(
								(ConditionVO) rule.getContentConds().get(i),
								idCompanyid));
					}

				}
				LOG.info("ȥ������");// ��ӡ��־
				// ȥ������
				PublicUtil.removeDuplicateWithOrder(eliteList);
				PublicUtil.removeDuplicateWithOrder(productList);
				// ȥ��APӦ�ú������е�Ӧ��
				LOG.info("APӦ�ú�����");// ��ӡ��־
				this.removeConInBlackList(eliteList);
				this.removeConInBlackList(productList);
				if (rule.getRandomFactor() == CategoryConstants.RULE_RANDOM_ALL)// ��Ҫ�����
				{
					productList = radomList(productList, rule.getRandomFactor());
				}
				
				// ��Ҫ���¼�������Ʒ
				// ����õ��û�����Ŀǰ�е���Ʒ��
				List currentList = getContentIDListById(cate.getCategoryID());

				// ��Ҫɾ���ļ��ϡ�
				Set delSet = new HashSet();

				// ������ĳЩ��Ʒȷʵ�ڵ�ǰ�����Ѿ����ڣ���Ҫɾ����
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
				long del_startime = System.currentTimeMillis();//�¼ܿ�ʼʱ��
				if (delSet.size() > 0)
				{
					LOG.info("��ʼ�¼�" + delSet.size() + "����Ʒ��");
					for (Iterator it = delSet.iterator(); it.hasNext();)
					{
						// System.out.println("--�¼���Ʒ-"+c);
						CategoryTools.delGoodsByRefId(cate, it.next() + "");
					}
				}
				else
				{
					LOG.info("û����Ҫ�¼ܵ���Ʒ��");
				}
				
				int maxSortid = getMaxSortId(cate.getCategoryID());

				LOG.info("���ܵ�CategoryIDֵ" + cate.getCategoryID() + "���������ֵ"
						+ maxSortid);

				sortId = maxSortid + eliteList.size() + productList.size();
				long del_endtime = System.currentTimeMillis();//�¼ܽ�������ʼ�ϼ�ʱ��
				HashMap eliteMap = new HashMap();
				
				// �ϼ���Ʒ�����ϼܾ�Ʒ��ģ����ϼܲ�Ʒ��ġ�
				if (LOG.isDebugEnabled())
				{
					LOG.debug("��ʼ�ϼܾ�Ʒ�����Ʒ��");
				}
				for (int i = 0; i < eliteList.size(); i++)
				{
					String contId = (String) eliteList.get(i);
					try
					{
						// �ϼܶ����в�����Ϊһ��Ӧ���ϼ��쳣ʧ�ܣ������º����ж�
						if (!filterContent(contId, idCompanyid, rule
								.getRandomFactor(), companyIdCount))
							continue;// add by aiyan 2012-02-22
						
						CategoryTools.addGood(cate, contId, sortId--,
								RepositoryConstants.VARIATION_NEW, true,null);
						eliteMap.put(contId, "");
					}
					catch (Exception ec)
					{

						LOG.error("Ϊ����" + cate.getName() + "ID:"
								+ cate.getCategoryID() + ",�ϼܾ�Ʒ��Ӧ��ID:" + contId
								+ "ʧ�ܣ�", ec);
					}
				}
				if (LOG.isDebugEnabled())
				{
					LOG.debug("��ʼ�ϼܲ�Ʒ�����Ʒ��");
				}
				for (int i = 0; i < productList.size(); i++)
				{
					String contId = (String) productList.get(i);
					if (!eliteMap.containsKey(contId))// ֻ�в��ھ�Ʒ��Ĳ�Ʒ�ſ����ϼܡ�
					{
						try
						{
							// �ϼܶ����в�����Ϊһ��Ӧ���ϼ��쳣ʧ�ܣ������º����ж�
							if (!filterContent(contId, idCompanyid, rule
									.getRandomFactor(), companyIdCount))
								continue;// add by aiyan 2012-02-22
							
							CategoryTools.addGood(cate, contId, sortId--,
									RepositoryConstants.VARIATION_NEW, true,null);
						}
						catch (Exception ec)
						{

							LOG.error("Ϊ����" + cate.getName() + "ID:"
									+ cate.getCategoryID() + ",�ϼܲ�Ʒ��Ӧ��ID:"
									+ contId + "ʧ�ܣ�", ec);
						}

					}
				}
				// ��ӡ��־
				LOG.info("�ϼ����");
				int maxGoodsNum = rule.getMaxGoodsNum();
				// System.out.println("maxGoodsNum:"+maxGoodsNum);

				if (maxGoodsNum > 0)
				{
					// -1��ʾ����ˢ�»���ʱ������������С������ֵҲ���Ҫ���л�����Ʒ������ѹ���ˡ�
					if (eliteList.size() + productList.size()
							+ currentList.size() - delSet.size() > maxGoodsNum)
					{
						// ����ط���SQL�¼ܣ������Ǽٵ�HIBERNATE�¼ܣ��Ǻǡ�add by aiyan
						// 2011-12-22
						LOG.info("��ʼ�¼ܳ���maxGoodsNumֵ����Ʒ��");
						try
						{
							CategoryTools.maxGoodsNum(cate.getCategoryID(),
									maxGoodsNum);
						}
						catch (BOException e)
						{
							LOG.error("�¼ܳ���maxGoodsNumֵ����Ʒʧ�ܣ�", e);
						}
						LOG.info("�ɹ��¼ܳ���maxGoodsNumֵ����Ʒ��");
					}
					else
					{
						LOG.info("����ѹ����Ʒ����");
					}
				}
				else if (maxGoodsNum == 0)
				{
					LOG.error("�û�������ˢ�µ�����Ʒ������Ϊ0������ִ�С���Ϊ�������ĺ���Ǹû��ܽ�����գ�Σ�գ�");
				}
				//long del_startime = System.currentTimeMillis();//�¼ܿ�ʼʱ��
				//long del_endtime = System.currentTimeMillis();//�¼ܽ�������ʼ�ϼ�ʱ��
				//
				long add_endtime = System.currentTimeMillis();//�ϼܽ�����
				LOG.info("costtime-----cid=" + cate.getCategoryID() + "|ruleid="
						+ rule.getRuleId() + ";sql_time="
						+ (del_startime - starttime) + ";deltime="
						+ (del_endtime - del_startime)+";addtime="+(add_endtime-del_endtime));
			}
			// ��Ҫ��Ʒ�������ɡ�
			else if (rule.getRuleType() == CategoryConstants.RULETYPE_REFRESH
					|| rule.getRuleType() == CategoryConstants.RULETYPE_OPERATION)
			{
				// �Ȼ�ȡ�û�������Ʒ
				LOG.info("���ܸ��¿�ʼ��������");

				// ��ž�Ʒ��Ͳ�Ʒ�����Ʒ��
				List eliteList = new ArrayList();
				List productList = new ArrayList();
				if (rule.getEliteConds().size() != 0) // �л�ȡ��Ʒ���������ִ�С�
				{
					eliteList = new ArrayList();
					for (int i = 0; i < rule.getEliteConds().size(); i++)
					{
						eliteList.addAll(getContentIDList((ConditionVO) rule
								.getEliteConds().get(i), sqlSb));
					}

				}
				if (rule.getContentConds().size() != 0) // �л�ȡ��Ʒ���������ִ�С�
				{
					productList = new ArrayList();
					for (int i = 0; i < rule.getContentConds().size(); i++)
					{
						productList.addAll(getContentIDList((ConditionVO) rule
								.getContentConds().get(i), sqlSb));
					}

				}
				LOG.info("ȥ������");// ��ӡ��־
				// ȥ������
				PublicUtil.removeDuplicateWithOrder(eliteList);
				PublicUtil.removeDuplicateWithOrder(productList);
				// ȥ��APӦ�ú������е�Ӧ��
				LOG.info("APӦ�ú�����");// ��ӡ��־
				this.removeConInBlackList(eliteList);
				this.removeConInBlackList(productList);
				if (rule.getRandomFactor() == CategoryConstants.RULE_RANDOM_ALL)// ��Ҫ�����
				{
					productList = radomList(productList, rule.getRandomFactor());
				}
				int totalc = eliteList.size() + productList.size();
				if (totalc <= 0)
				{
					// ���ϼ���Ʒ�嵥Ϊ�գ��ݴ��ϲ����¼ܸû������д�����Ʒ
					LOG.info("�û���cateId=" + vo.getCid()
							+ "���ϼ���Ʒ ��Ʒ�⣬��Ʒ������Ϊ�գ���ִ��ȫ���¼�");
					throw new BOException("�û���cateId=" + vo.getCid()
							+ "���ϼ���Ʒ ��Ʒ�⣬��Ʒ������Ϊ�գ���ִ��ȫ���¼�,ruleid="
							+ rule.getRuleId());
				}
				// ��Ҫ���¼�������Ʒ
				if (LOG.isDebugEnabled())
				{
					LOG.debug("��ʼ�¼ܱ����ܵ�������Ʒ������");
				}
				LOG.info("�¼���Ʒ");// ��ӡ��־
				
				long del_startime = System.currentTimeMillis();//�¼ܿ�ʼʱ��
				
				CategoryTools.clearCateGoods(vo.getCid(), false);
				// ���˾�Ʒ�����Ʒʹ�ã�ʹ��hashmap��߲�ѯЧ�ʡ�
				HashMap eliteMap = new HashMap();
				// �ϼ���Ʒ�����ϼܾ�Ʒ��ģ����ϼܲ�Ʒ��ġ�
				if (LOG.isDebugEnabled())
				{
					LOG.debug("��ʼ�ϼܾ�Ʒ�����Ʒ��");
				}
				long del_endtime = System.currentTimeMillis();//�¼ܽ�������ʼ�ϼ�ʱ��
				
				for (int i = 0; i < eliteList.size(); i++)
				{
					String contId = (String) eliteList.get(i);
					try
					{// �ϼܶ����в�����Ϊһ��Ӧ���ϼ��쳣ʧ�ܣ������º����ж�
						CategoryTools.addGood(cate, contId, sortId--,
								RepositoryConstants.VARIATION_NEW, true,null);
						eliteMap.put(contId, "");
					}
					catch (Exception ec)
					{
						LOG.error("Ϊ����" + cate.getName() + "ID:"
								+ cate.getCategoryID() + ",�ϼܾ�Ʒ��Ӧ��ID:" + contId
								+ "ʧ�ܣ�", ec);
					}

				}
				if (LOG.isDebugEnabled())
				{
					LOG.debug("��ʼ�ϼܲ�Ʒ�����Ʒ��");
				}
				for (int i = 0; i < productList.size(); i++)
				{
					String contId = (String) productList.get(i);
					if (!eliteMap.containsKey(contId))// ֻ�в��ھ�Ʒ��Ĳ�Ʒ�ſ����ϼܡ�
					{
						try
						{// �ϼܶ����в�����Ϊһ��Ӧ���ϼ��쳣ʧ�ܣ������º����ж�
							CategoryTools.addGood(cate, contId, sortId--,
									RepositoryConstants.VARIATION_NEW, true,null);
						}
						catch (Exception ec)
						{

							LOG.error("Ϊ����" + cate.getName() + "ID:"
									+ cate.getCategoryID() + ",�ϼܲ�Ʒ��Ӧ��ID:"
									+ contId + "ʧ�ܣ�", ec);
						}

					}
				}
				if (rule.getRuleType() == CategoryConstants.RULETYPE_OPERATION)// �������Ӫ�Ƽ���������࣬����Ҫ�Ի��ܽ������⴦��
				{

					sortId = 1000;// ���³�ʼ��
					if (LOG.isDebugEnabled())
					{
						LOG.debug("��ʼ�ϼܻ��ͻ���");
					}
					// ����ÿһ�����ͻ��ܵ���Ҫ�ϼܵ���Ʒ��Ʒ������������Ʒ�����Ʒ����key deviceName value
					// list(����id)
					HashMap deviceCategoryGoods = new HashMap();
					// ��ȡ���ͻ�����Ϣ��key deviceName values Category����
					HashMap deviceCategoryMapping = this.getSubCategory(cate,
							deviceCategoryGoods);
					if (LOG.isDebugEnabled())
					{
						LOG.debug("��ʼ��ջ��ͻ�����Ʒ");
					}
					for (Iterator iter = deviceCategoryMapping.values()
							.iterator(); iter.hasNext();)
					{
						Category deviceCategory = (Category) iter.next();
						CategoryTools.clearCateGoods(deviceCategory, false);
					}

					// ��¼��Ʒ���ϼ���Ϣ��key ����name +
					// ����id,��Ҫ���ڲ�Ʒ���ϼ�ʱ�����ϼܾ�Ʒ���Ѿ��ϼܵ����ݡ���ֹ�ظ��ϼܡ�
					// HashMap eliteMapFlag=new HashMap();
					// �ϼ����е���Ʒ��
					// �ϼܾ�Ʒ��
					/*
					 * if(LOG.isDebugEnabled()) { LOG.debug("���ͻ��ܿ�ʼ�ϼܾ�Ʒ�����Ʒ"); }
					 * for (int i = 0; i < eliteList.size(); i++) { String
					 * contId = (String) eliteList.get(i); List deviceNameList =
					 * getAppDeviceName(contId); for(int j=0;j<deviceNameList.size();j++) {
					 * Category
					 * deviceCategory=(Category)deviceCategoryMapping.get(deviceNameList.get(j));
					 * if(deviceCategory!=null)//������Ҫ�Ѹ�Ӧ���ϼܵ��û��ͻ��ܡ� {
					 * CategoryTools.addGood(deviceCategory, contId, sortId--,
					 * RepositoryConstants.VARIATION_NEW, true);
					 * eliteMapFlag.put(deviceCategory.getName()+contId,
					 * "");//��¼��Ʒ���ϼܻ��ͻ�����Ϣ } } }
					 */
					for (int i = 0; i < eliteList.size(); i++)
					{
						String contId = (String) eliteList.get(i);
						List deviceNameList = getAppDeviceName(contId);
						for (int j = 0; j < deviceNameList.size(); j++)
						{
							List temp = (List) deviceCategoryGoods
									.get(deviceNameList.get(j));
							if (temp != null)// ��Ҫ�Ѿ�Ʒ��Ͳ�Ʒ��ϲ���һ��Ȼ�����sp��һ���ϼ�
							{
								temp.add(contId);
							}
						}

					}

					/*
					 * //����ÿһ�����ͻ����ϼܵ���Ʒ�б� if(LOG.isDebugEnabled()) {
					 * LOG.debug("���ͻ��ܿ�ʼ�ϼܲ�Ʒ��"); }
					 */
					for (int i = 0; i < productList.size(); i++)
					{
						String contId = (String) productList.get(i);
						// �жϸ�������Ҫ�ϼ��Ǽ������ͻ��ܡ�
						List deviceNameList = getAppDeviceName(contId);

						for (int j = 0; j < deviceNameList.size(); j++)
						{
							List temp = (List) deviceCategoryGoods
									.get(deviceNameList.get(j));
							if (temp != null)// ������Ҫ�Ѹ�Ӧ���ϼܵ��û��ͻ��ܡ�
							{
								temp.add(contId);
							}
						}
					}
					// �ϼܲ�Ʒ��;�Ʒ��
					for (Iterator iter = deviceCategoryGoods.keySet()
							.iterator(); iter.hasNext();)
					{
						String deviceName = (String) iter.next();
						if (LOG.isDebugEnabled())
						{
							LOG.debug("��ʼ�ϼܻ��ͻ��ܣ�" + deviceName);
						}
						Category categoryCategory = (Category) deviceCategoryMapping
								.get(deviceName);
						List contIdList = (List) deviceCategoryGoods
								.get(deviceName);

						if (rule.getRandomFactor() > 0
								&& rule.getRandomFactor() < 100)
						{
							contIdList = radomList(contIdList, rule
									.getRandomFactor());// ��Ҫ���ݷ����漴����
						}
						
						// ��Ҫȥ����Ʒ��Ͳ�Ʒ����ظ�������
						PublicUtil.removeDuplicateWithOrder(contIdList);
						
						// ��Ҫ����sp����˳��
						contIdList = CategoryUpdateTools.getInstance()
								.sortListBySpName(contIdList,
										CategoryRuleConfig.SPNAMESORTCOUNT,
										CategoryRuleConfig.SPNAMEMAXCOUNT);
						sortId = 1000;
						for (int i = 0; i < contIdList.size(); i++)
						{
							String contId = (String) contIdList.get(i);
							// if(!eliteMapFlag.containsKey(categoryCategory.getName()+contId))//�����Ʒ����Ʒ���ھͲ���Ҫ�ϼܡ�
							{
								try
								{
									// �ϼܶ����в�����Ϊһ��Ӧ���ϼ��쳣ʧ�ܣ������º����ж�
									CategoryTools.addGood(categoryCategory,
											contId, sortId--,
											RepositoryConstants.VARIATION_NEW,
											true,null);
								}
								catch (Exception ec)
								{

									LOG.error("Ϊ����" + cate.getName() + "ID:"
											+ cate.getCategoryID()
											+ ",�ϼܲ�Ʒ��Ӧ��ID:" + contId + "ʧ�ܣ�",
											ec);
								}
							}
						}
					}
				}
				LOG.info("�ϼ����");// ��ӡ��־
				long add_endtime = System.currentTimeMillis();//�ϼܽ���
				 LOG.info("costtime-----cid=" + cate.getCategoryID() + "|ruleid="
						+ rule.getRuleId() + ";sql_time="
						+ (del_startime - starttime) + ";deltime="
						+ (del_endtime - del_startime)+";addtime="+(add_endtime-del_endtime));
			}
			// ˢ�º�����������Ʒʱ���������
			else if (rule.getRuleType() == CategoryConstants.RULETYPE_REORDER)//
			{
				// ��ȡ��ǰ��������Ʒ�����ź���
				LOG.info("������������ʼ��������");

				// ��Ʒ����Ĺ��������ֻ������һ���������ɡ�
				if (rule.getContentConds().size() == 0)
				{
					LOG.error("���������û�ж������������������ܲ���������");
					throw new BOException("���������û�����������쳣");
				}
				ConditionVO condition = (ConditionVO) rule.getContentConds()
						.get(0);
				List list = getSortedGoods(cate, condition);
				
				if (rule.getRandomFactor() == CategoryConstants.RULE_RANDOM_ALL)// ��Ҫ�����
				{
					list = radomList(list, rule.getRandomFactor());
				}
				
				// ������Ӧ���õ�
				this.changeToLastBlackApp(list);
				
				List goodList = new ArrayList();
				
				// ���������
				for (int i = 0; i < list.size(); i++)
				{
					ReferenceNode node = new ReferenceNode();
					node.setId((String) list.get(i));
					node.setSortID(sortId--);
					goodList.add(node);
				}
				
				// ���µ����ݿ��С�
				if (goodList.size() > 0)// ֻ�к�����Ʒ����Ҫ��������ֵ��
				{
					updateSortId(goodList);
				}
			}
			else if (rule.getRuleType() == CategoryConstants.RULETYPE_BOOK_COMMEND)
			{
				LOG.info("��ʼ�������ͼ�������Ӫ�����Ƽ�");
				int count = JDSyncBO.getInstance().updateYYBOOkCommend();//
				LOG.info("�ܹ��ɹ��Ƽ���" + count + "�����ࡣ");
			}
			
			// ���»��ܵ����һ����Ʒ�Զ�����ִ��ʱ�䡣
			long endtime = System.currentTimeMillis();
			long exetime = endtime - starttime;
			CategoryRuleDAO.getInstance().updateRuleLastExecTime(vo.getCid(),
					exetime, new Date());
			return CategoryRuleTask.UPDATESUCESS;
		}
		else
		{
			LOG.info("���β���Ҫִ�С���������id=" + vo.getCid());
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
				LOG.error("contId:" + contId + "����ת���쳣", e);
				return true;// ������ת���쳣��ʱ�򣬾Ͳ��ٿ��������ĵ���ICPCODE ���ϼܲ�Ʒ���������ƣ��ݴ���ѡ�
			}
		}

		companyIdCount.put(companyId, count);

		try
		{
			// ���ΪICPCODE�������б����ݣ���ֱ������
			if(WHITEAPPCACHE.contains(companyId))
			{
				return true;
			}
			
			if (Integer.parseInt(count) > randomFactor)
			{
				LOG.info(contId + "--" + companyId + "--" + count
						+ "�����ˡ��Ǻǡ������á�����");
				return false;
			}
		}
		catch (Exception e)
		{
			LOG.error("contId:" + contId + "����ת���쳣", e);
			return true;// ������ת���쳣��ʱ�򣬾Ͳ��ٿ��������ĵ���ICPCODE ���ϼܲ�Ʒ���������ƣ��ݴ���ѡ�
		}

		LOG.debug(contId + "--" + companyId + "--" + count);
		return true;
	}

	private int getMaxSortId(String categoryid) {
		if(LOG.isDebugEnabled())
		{
			LOG.debug("��ʼ��ȡ��ǰ�����µ�MAX SORTID������");
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
		
			LOG.error("��ȡ��ǰ�����µ����SORTID���쳣��getMaxSortId("+categoryid+")",e);
			return 0;
			
		}finally{
			DB.close(rs);
		}
		return 0;
	}
	private List getContentIDListById(String categoryid) throws BOException {
		if(LOG.isDebugEnabled())
		{
			LOG.debug("��ʼ��ȡ��ǰ�����µ�������Ʒ������");
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
			
			throw new BOException("��ȡ��ǰ�����µ�������Ʒ����.getContentIDListById("+categoryid+")",e);
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
	 * �жϵ�ǰʱ��,�˴������Ƿ���Ҫִ�С�
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
			//��Ҫ��Date����ת��ΪCalendar
			lastDate.setTime(vo.getLastExcuteTime());
			return compareDateDif(now, lastDate, Calendar.DAY_OF_MONTH, rule.getIntervalCount());
		}else if(rule.getIntervalType()==CategoryConstants.INTERVALTYPE_WEEK)
			
		{
			//Calendar����ֵ�У�������1��������7,��Ҫת�����й��ı�׼��
			//��һ��1��������7
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
			//��Ҫ��Date����ת��ΪCalendar
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
			//��Ҫ��Date����ת��ΪCalendar
			lastDate.setTime(vo.getLastExcuteTime());
			return compareDateDif(now, lastDate, Calendar.MONTH, rule.getIntervalCount());
		}else
		{
			LOG.error("Ŀǰ����֧�ִ����ͣ�IntervalType="+rule.getIntervalType());
			return false;
		}
		
	}
	/**
	 * �Ƚ�nowDate��lastDate���ʱ���Ƿ���ڵ���count����field Ϊ��λ��ʱ�䡣
	 * @param nowDate ��ǰʱ��
	 * @param lastDate �ϴθ���ʱ��
	 * @param field ���ĵ�λ���գ��ܣ���
	 * @param count ��λʱ��ĸ���
	 */
	private boolean compareDateDif(Calendar nowDate,Calendar lastDate,int field,int count)
	{
		nowDate.add(field, -count);
		//�Ƚ��ַ����жϵ�ǰʱ���Ƿ���ͬ��
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
	 * �жϵ�ǰ�������Ƿ��Ѿ���Ч��
	 * @param date ��Ч����
	 * @return ��ǰ���ڴ��ڵ���date�ȱ�ʾ��Ч������Ϊ��λ���бȽϣ���
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
                throw new BOException("�鲻����ǰ�������������͡�condType="
                                      + condition.getCondType());
            }

            condType = baseCond.getBaseName() + "����sql��";
            sql = baseCond.getBaseSQL();

            if (condition.getCondType() == CategoryConstants.CONDTYPE_ELITE)// ��Ʒ�������
            {
                // condType="��Ʒ���ȡ����sql��";
                // sql = DB.getInstance().getSQLByCode(
                // "category.rule.CategoryRuleExcutor.SELECT.getEliteList");
                paras = new String[1];
                String categoryid = CategoryDAO.getInstance()
                                               .getCategoryIDByID(condition.getCid());
                paras[0] = categoryid;
            }
            // else
            // if(condition.getCondType()==CategoryConstants.CONDTYPE_PROD_OWNER)//�Ӳ�Ʒ���ȡ����ҵ�������
            // {
            // condType="��Ʒ������ҵ���ȡ����sql��";
            // sql = DB.getInstance().getSQLByCode(
            // "category.rule.CategoryRuleExcutor.SELECT.getOwnerContentIDList");
            // }else
            // if(condition.getCondType()==CategoryConstants.CONDTYPE_PROD_OTHERS)//�Ӳ�Ʒ��������ҵ�������
            // {
            // condType="��Ʒ�������ҵ���ȡ����sql��";
            // sql = DB.getInstance().getSQLByCode(
            // "category.rule.CategoryRuleExcutor.SELECT.getOtherContentIDList");
            // }else
            // if(condition.getCondType()==CategoryConstants.CONDTYPE_PROD_BASEGAME)//ֻ���������Ϸ
            // {
            // condType="��Ʒ�������Ϸ��ȡ����sql��";
            // sql = DB.getInstance().getSQLByCode(
            // "category.rule.CategoryRuleExcutor.SELECT.getBaseGameContentIDList");
            // }else
            // if(condition.getCondType()==CategoryConstants.CONDTYPE_PROD_BRAND)//ֻ����Ʒ�Ƶ��ײ�ҵ��
            // {
            // condType="Ʒ�Ƶ��ײ�ҵ���ȡ����sql��";
            // sql = DB.getInstance().getSQLByCode(
            // "category.rule.CategoryRuleExcutor.SELECT.getBrandContentIDList");
            //            }
            //            else if(condition.getCondType()==CategoryConstants.CONDTYPE_PROD_BASE)//ֻ�������ҵ��
            //            {
            //                condType="����ҵ���ȡ����sql��";
            //                sql = DB.getInstance().getSQLByCode(
            //                "category.rule.CategoryRuleExcutor.SELECT.getBaseContentIDList");
            //            }else
            //			{
            //				throw new BOException("Ŀǰ����֧�ָ����������͡�condType="+condition.getCondType());
            //			}

        } catch (DAOException e1)
		{
        
			throw new BOException("��ȡ���ݳ���",e1);
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
		if(count==-1)//-1��ʾϵͳ�����Ƹ���,ʹ���������е����ֵ��
		{
			count=CategoryRuleConfig.CONDITIONMAXVALUE;//default;
		}

		 //�����װsql��ֻ��ȡ�ƶ��������ݡ�
		 sql= "select * from ("+sb.toString()+")where rownum <= "+count;
		 
		LOG.info(condType+sql);//��ӡsql��־
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
			LOG.error("��ȡ���ݳ���:"+condType+sql, e);
			throw new BOException("��ȡ���ݳ���",e);
		}finally{
			DB.close(rs);
		}
		sqlSb =sqlSb.append(sql);
		sqlSb =sqlSb.append(";-----;");
		return list;
	}
	
	//MAP������Ŀ���ǵõ�Content-companyid��ӳ�䡣
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
                throw new BOException("�鲻����ǰ�������������͡�condType="
                                      + condition.getCondType());
            }

            condType = baseCond.getBaseName() + "����sql��";
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

            if (condition.getCondType() == CategoryConstants.CONDTYPE_ELITE)// ��Ʒ�������
            {
                paras = new String[1];
                String categoryid = CategoryDAO.getInstance()
                                               .getCategoryIDByID(condition.getCid());
                paras[0] = categoryid;
            }

        } catch (DAOException e1)
		{
			throw new BOException("��ȡ���ݳ���",e1);
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
		if(count==-1)//-1��ʾϵͳ�����Ƹ���,ʹ���������е����ֵ��
		{
			count=CategoryRuleConfig.CONDITIONMAXVALUE;//default;
		}

		 //�����װsql��ֻ��ȡ�ƶ��������ݡ�
		 sql= "select * from ("+sb.toString()+")where rownum <= "+count;
		 
		LOG.info(condType+sql);//��ӡsql��־
		ResultSet rs = null;
		List list = new ArrayList();
		try
		{
			 rs = DB.getInstance().query(sql, paras);
			while (rs.next())
			{
				list.add(rs.getString("id"));
				//��������Ǹ������ܵġ�Ϊ�˵õ�id-companyid��ӳ�䡣
				map.put(rs.getString("id"), rs.getString("companyid"));
			}
		} catch (Exception e)
		{
			LOG.error("��ȡ���ݳ���:"+condType+sql, e);
			throw new BOException("��ȡ���ݳ���");
		}finally{
			DB.close(rs);
		}
		return list;
	}
	
	
	private List getSortedGoods(Category cate,ConditionVO condition) throws BOException
	{
		if(LOG.isDebugEnabled())
		{
			LOG.debug("��ʼ��ȡ��ǰ�����µ�������Ʒ������");
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
					"��ѯsqlcode�쳣��sqlCode=category.rule.CategoryRuleExcutor.SELECT.getSortedGoods",e1);
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
			
			throw new BOException("�����ݿ��ȡ���ݳ���.getSortedGoods="+sb.toString()+",paras="+cate.getCategoryID(),e);
		}finally{
			DB.close(rs);
		}

		return list;
	}

	private void updateSortId(List goodList) throws BOException
	{
		if(LOG.isDebugEnabled())
		{
			LOG.debug("��ʼ���µ�ǰ���ܵ�����ֵ������");
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
			
			throw new BOException("���»�����Ʒ�����쳣",e);
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
	 * ��ȡ�÷����»��ͻ�����Ϣ
	 * @param category �Զ����µĻ���
	 * @param deviceCategoryGoods  ��ʼ���ӻ��ܵ���Ϣ
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
	 * �漴���򡣸�����������t������ÿһ��Ĵ�С��Ȼ���ÿһ���漴����
	 * �����С=list.size * t
	 * @param list ������ķ���
	 * @param t �����С�İٷ�ֵ 0~100
	 * @return �����ź�����б�0ֱ�ӷ���
	 */
	public static List radomList(List list, int randomFactor)
	{
		if (randomFactor > 100||randomFactor<=0)
		{
			//�������Բ�����
			return list;
		}
		int sum = list.size();
		int groupSize = Math.round(sum*((float)randomFactor /100));//ȷ�������С,������ӽ���������
		//System.out.println("sum=" + sum + ";t1=" + t1);
		if(groupSize<2)//ֻ�з����СΪ2������˼��
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
	 * ȥ�����ں�������Ӧ��
	 * @param conId ��������
	 * @return true ���Ǻ������������ϼܣ�false  �Ǻ��������������ϼ�
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
			LOG.debug("������Ӧ�ò��ϼ�");
		}
		for(int i = 0 ; i < contentlist.size();i++){
			String conId = (String)contentlist.get(i);
			String values = (String) hm.get(conId);
			if (values != null && values.equals("1")) {
				LOG.error("conId="+conId+",��APӦ�ú������У������ϼ�");
				contentlist.remove(i);
				i--;//��ֹ������©����
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
			
			Map hm = null;//��ȡ������Ӧ��
			if(BLACKAPPCACHE != null){
				 hm = BLACKAPPCACHE;
			}else{
				 this.getBlackContentList();
				 hm = BLACKAPPCACHE;
				 
			}
			if(LOG.isDebugEnabled()){
				LOG.debug("������Ӧ���õ�");
			}
			for(int i = 0 ; i < contentlist.size(); i ++){
				String conId = (String)contentlist.get(i);
				ReferenceNode ref = (ReferenceNode) Repository.getInstance().getNode(conId,
						RepositoryConstants.TYPE_REFERENCE);
				String values = (String) hm.get(ref.getRefNodeID());
				if (values != null && values.equals("1")) {
					LOG.error("conId="+conId+",��APӦ�ú������У��õ���ʾ-move to last");
					blackapp.add(contentlist.get(i));
					contentlist.remove(i);
					i--;//��ֹ������©����
					//return false;
				}
			}
			contentlist.addAll(blackapp);		
		}		
		//return blackcontentlastlist;
	}
	
	
	/**
	 * ��ȡAPˢ�������Ӧ��
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
				LOG.debug("�����ݿ��ȡӦ�ú������б�");
			}
			while (rs.next()) {
//				blackContentMap.put(rs.getString("cid"), "1");
				BLACKAPPCACHE.put(rs.getString("cid"), "1");

			}
		} catch (DAOException e) {
		
			LOG.error("�����ݿ��ȡӦ�ú������б�,DAO��������",e);
			throw new BOException("DAO��������", e);
		} catch (SQLException e1) {
			
			LOG.error("�����ݿ��ȡӦ�ú������б�,DAO��������",e1);
			throw new BOException("SQL��������", e1);
		}finally{
			DB.close(rs);
		}

		return blackContentMap;
	}
	
	/**
	 * ��ȡAPˢ�������Ӧ��
	 * @return 
	 * @throws BOException
	 */
	private  void getWhiteAppCache() throws BOException
	{
		if(LOG.isDebugEnabled())
		{
			LOG.debug("���ð�����ICPCODE�б�:��ʼ");
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
        	LOG.error("�õ���ȡ������companyid�б����ݵ�SQL���ʱ�����쳣",e);
            throw new BOException("�õ���ȡ������companyid�б����ݵ�SQL���ʱ�����쳣", e);
        }
		catch (DAOException e)
		{
        	LOG.error("���ڷ����Զ����¶���İ�����companyid�б�����ʱ�����쳣",e);
            throw new BOException("���ڷ����Զ����¶���İ�����companyid�б�����ʱ�����쳣", e);
		}
        finally
        {
            DB.close(rs);
        }

		if(LOG.isDebugEnabled())
		{
			LOG.debug("���ð�����companyid�б�:����");
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
			LOG.error("���ܸ���ʧ�ܡ�"+vo,e);
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
			LOG.error("���ܸ���ʧ�ܡ�"+vo,e);
			e.printStackTrace();
			//errorList.add("id="+categoryRule.getCid()+",name="+cateNamePath);
			//categoryRuleBO.addError(vo);
			categoryRuleBO.addErrorInfo(vo,e.getMessage());
			
		}
	}
	
}
