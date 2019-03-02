package com.aspire.dotcard.syncAndroid.ssms;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.ponaadmin.web.category.intervenor.IntervenorVO;
import com.aspire.ponaadmin.web.category.intervenor.category.IntervenorCategoryBO;
import com.aspire.ponaadmin.web.category.intervenor.gcontent.IntervenorGcontentVO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class AndroidListTask extends TimerTask
{
	
	/**
	 * 日志对象
	 */
	protected static JLogger LOG = LoggerFactory
			.getLogger(AndroidListTask.class);
	
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		try
		{
			// 第一步，v_android_list视图固化 t_a_android_list_tmp
			LOG.info("开始建表t_a_android_list_tmp");
			createAndroidListTmp();
			
			LOG.info("开始检查t_a_android_list_compare是否安全");
			LOG
					.info("t_a_android_list_compare表的数据要大于5万，和t_a_android_list相比较记录数变化量小于5千");
			boolean flag = validate();
			if (!flag)
			{
				return;
			}
			LOG.info("执行updateRankNew");
			// add by aiyan 2013-07-02
			updateRankNew();// 同一个COMPANYID下最新榜单当天不能出现多个应用（最新榜单一个应用在一天中最多一次出现。）。
			
			updateIntervene();// 这个地方是人工干预的部分。主要是把t_a_intervene的数据反应到t_a_android_list中去。add
			// by aiyan 2013-07-15
			
			LOG.info("比较 t_a_android_list和t_a_android_list_tmp的变化，记录contentid");
			// 第二步，比较 t_a_android_list和t_a_android_list_tmp的变化，记录contentid
			compareChange();
			// 第三步，删除t_a_android_list表
			// 将t_a_android_list_tmp表变成t_a_android_list表
			LOG.info("将t_a_android_list_tmp表变成t_a_android_list表");
			changeTableName();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			LOG.error("AndroidListTask:this error may let timer object stop!!",
					e);
		}
		
	}
	
	/**
	 * 人工干预商品库货架商品置顶操作
	 * @param androidCategory
	 * @param intervenor
	 * @return
	 */
	private String intervenorTopSyn(String androidCategory, IntervenorVO intervenor)
	{
		List<IntervenorGcontentVO> gcontentList = null;
		
		try
		{
			// 得到当前干预数据列表
			gcontentList = IntervenorCategoryBO.getInstance().queryGcontentListByIntervenorIdAndroid(String.valueOf(intervenor.getId()));
		}
		catch (BOException e)
		{
			LOG.error("得到商品库货架对应的人工干预列表时发生错误",e);
			return "得到商品库货架对应的人工干预列表时发生错误";
		}
		
		// 得到当前商品库货架内容最大排序值
		long  number = getRandValueByCategory(androidCategory, intervenor.getStartSortId());
		if(number == -1L)
		{
			LOG.error("得到当前商品库货架内容最大排序值时发生错误");
			return "得到当前商品库货架内容最大排序值时发生错误";
		}
		number = number + 2L + gcontentList.size();
		
		// 更新当前干预数据列表对应的货架商品排序 + 排序最大值+1
		for(IntervenorGcontentVO vo : gcontentList)
		{
			number = number-1L;
			updateRandValueByContent(androidCategory, vo.getContentId(), number);
		}
		
		return intervenor.getName()+"执行成功";
	}
	
	/**
	 * 人工干预商品库货架商品置底操作
	 * @param androidCategory
	 * @param intervenor
	 * @return
	 */
	private String intervenorEndSyn(String androidCategory, IntervenorVO intervenor)
	{
		List<IntervenorGcontentVO> gcontentList = null;
		
		try
		{
			// 得到当前干预数据列表
			gcontentList = IntervenorCategoryBO.getInstance().queryGcontentListByIntervenorIdAndroid(String.valueOf(intervenor.getId()));
		}
		catch (BOException e)
		{
			LOG.error("得到商品库货架对应的人工干预列表时发生错误",e);
			return "得到商品库货架对应的人工干预列表时发生错误";
		}
		
		// 得到当前商品库货架内容最小排序值
		long  number = getRandValueByCategory(androidCategory, intervenor.getStartSortId());
		if(number == -1L)
		{
			LOG.error("得到当前商品库货架内容最大排序值时发生错误");
			return "得到当前商品库货架内容最大排序值时发生错误";
		}
		
		// 更新当前干预数据列表对应的货架商品排序 + 排序最小值-1
		for(IntervenorGcontentVO vo : gcontentList)
		{
			number = number-1L;
			updateRandValueByContent(androidCategory, vo.getContentId(), number);
		}
		
		return intervenor.getName()+"执行成功";
	}
	
	/**
	 * 人工干预商品库货架商品指定位置操作
	 * @param androidCategory
	 * @param intervenor
	 * @return
	 */
	private String intervenorOtherSyn(String androidCategory, IntervenorVO intervenor)
	{
		List<IntervenorGcontentVO> gcontentList = null;
		
		try
		{
			// 得到当前干预数据列表
			gcontentList = IntervenorCategoryBO.getInstance().queryGcontentListByIntervenorIdAndroid(String.valueOf(intervenor.getId()));
		}
		catch (BOException e)
		{
			LOG.error("得到商品库货架对应的人工干预列表时发生错误",e);
			return "得到商品库货架对应的人工干预列表时发生错误";
		}
		
		// 得到当前商品库货架内容最大排序值
		// 最高位
		long  startNumber = getRandValueByCategoryByIntervenor(androidCategory, intervenor.getStartSortId()-1, intervenor.getId());
		// 最低位
		long  endNumber = getRandValueByCategoryByIntervenor(androidCategory, intervenor.getStartSortId(), intervenor.getId());
		
		if(startNumber == -1L || endNumber == -1L)
		{
			LOG.error("得到当前商品库货架内容最大排序值时发生错误");
			return "得到当前商品库货架内容最大排序值时发生错误";
		}
		
		// 更新当前干预数据列表对应的货架商品排序 + 排序最小值-1
		for(IntervenorGcontentVO vo : gcontentList)
		{
			if(startNumber > endNumber+1L)
			{
				startNumber = startNumber-1L;
			}
			
			updateRandValueByContent(androidCategory, vo.getContentId(), startNumber);
		}
		
		return intervenor.getName()+"执行成功";
	}
	
	/**
	 * 人工干预商品库货架商品顺序
	 */
	private void updateIntervene()
	{
		LOG.info("t_a_android_list的人工干预启动。");
		
		String[] typeCategory = new String[] { "rank_new_appGame",
				"rank_new_appSoftWare", "rank_hot_appGame",
				"rank_hot_appSoftWare", "rank_scores_appGame",
				"rank_scores_appSoftWare",
				"rank_new_appAll", "rank_all_appAll",
				"rank_scores_appAll", "rank_fee_appAllPriceFree",
				"rank_fee_appAllPricePay" };
		
		for (String androidCategory : typeCategory)
		{
			List<IntervenorVO> intervenorList;
			
			try
			{
				// 得到可执行干预容器
				intervenorList = IntervenorCategoryBO.getInstance()
						.getIntervenorCategoryVO(androidCategory);
			}
			catch (BOException e)
			{
				LOG.error("得到当前商品库榜单人工干预列表时发生数据库异常！", e);
				continue;
			}
			
			for (Iterator<IntervenorVO> iter = intervenorList.iterator(); iter
					.hasNext();)
			{
				// 得到对应干预信息
				IntervenorVO intervenor = (IntervenorVO) iter.next();
				int startid = intervenor.getStartSortId();
				String synText;
				
				// 判断干预类型
				switch (startid)
				{
					// 置顶
					case IntervenorVO.TOP:
						synText = intervenorTopSyn(androidCategory,intervenor);
						break;
					// 置底
					case IntervenorVO.END:
						synText = intervenorEndSyn(androidCategory,intervenor);
						break;
					// 指定位置
					default:
						synText = intervenorOtherSyn(androidCategory,intervenor);
						break;
				}
				LOG.info(synText);
			}
		}
		LOG.info("t_a_android_list的人工干预已经完成。");
	}
	
	private void updateIntervene_old()
	{
		// TODO Auto-generated method stub
		String[] typeArr = new String[] { "rank_new", "rank_all", "rank_fee",
				"rank_hot", "rank_scores" };
		for (String type : typeArr)
		{
			List<InterveneVO> interveneList = getInterveneList(type);
			int max = 0;
			for (InterveneVO vo : interveneList)
			{
				if (max < vo.getSort())
					max = vo.getSort();
				
			}
			if (interveneList.size() == 0)
			{
				continue;
			}
			long[] a = new long[max + 2];// rank_xxx
			String[] b = new String[max + 2];// contentid
			final long falgA = -1;
			for (InterveneVO vo : interveneList)
			{
				a[vo.getSort()] = falgA;
				b[vo.getSort()] = vo.getContentid();
			}
			int needDBdataNum = 0;
			for (int i = 1; i < a.length; i++)
			{
				if (a[i] != falgA)
					needDBdataNum++;
			}
			List<RankVO> dbdataList = getDBdataList(type, needDBdataNum);
			int j = 0;
			for (int i = 1; i < a.length; i++)
			{
				if (a[i] != falgA)
				{
					RankVO vo = dbdataList.get(j++);
					a[i] = vo.getRank();
					b[i] = vo.getContentid();
				}
			}
			List<Integer> arr = new ArrayList<Integer>();
			for (int i = a.length - 1; i > 1; i--)
			{
				if (a[i - 1] == falgA)
				{
					a[i - 1] = a[i] + 1;
				}
				else if (a[i - 1] <= a[i])
				{
					a[i - 1] = a[i] + 1;
					arr.add(new Integer(i - 1));
				}
			}
			// System.out.println("++++++++++++++++++++++++");
			// for(int i=0;i<a.length;i++){
			// System.out.print(a[i]+"--");
			// }
			// System.out.println("++++++++++++++++++++++++");
			// for(int i=0;i<b.length;i++){
			// System.out.print(b[i]+"--");
			// }
			// System.out.println("++++++++++++++++++++++++");
			for (InterveneVO vo : interveneList)
			{
				updateRankValue(vo, a[vo.getSort()]);
			}
			if (arr.size() > 0)
			{
				for (int i = 0; i < arr.size(); i++)
				{
					SSMSDAO.getInstance().updateRankValue(type, b[arr.get(i)],
							a[arr.get(i)]);
				}
				
			}
			
		}
		LOG.info("t_a_android_list的人工干预已经完成，依靠t_a_intervene数据完成的。");
	}
	
	private void updateRandValueByContent(String categoryType, String contentId, long number)
	{
		SSMSDAO.getInstance().updateRandValueByContent(categoryType, contentId, number);
	}
	
	private long getRandValueByCategory(String categoryType, int startid)
	{
		return SSMSDAO.getInstance().getRandValueByCategory(categoryType, startid, 0);
	}
	
	private long getRandValueByCategoryByIntervenor(String categoryType, int startid, int intervenorId)
	{
		return SSMSDAO.getInstance().getRandValueByCategory(categoryType, startid, intervenorId);
	}
	
	private List<RankVO> getDBdataList(String type, int needDBdataNum)
	{
		// TODO Auto-generated method stub
		return SSMSDAO.getInstance().getDBdataList(type, needDBdataNum);
	}
	
	private void updateRankValue(InterveneVO vo, long l)
	{
		// TODO Auto-generated method stub
		SSMSDAO.getInstance().updateRankValue(vo.getType(), vo.getContentid(),
				l);
	}
	
	private List<InterveneVO> getInterveneList(String type)
	{
		// TODO Auto-generated method stub
		return SSMSDAO.getInstance().getInterveneList(type);
	}
	
	private void updateRankNew()
	{
		// TODO Auto-generated method stub
		SSMSDAO.getInstance().updateRankNew();
	}
	
	private boolean validate()
	{
		// TODO Auto-generated method stub
		
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"syncAndroid");
		int abs_android_list_num = 5000;// 新旧表记录幅度变化5k;
		int min_android_list_num = 50000;// 榜单表最小记录数5w
		try
		{
			abs_android_list_num = Integer.parseInt(module
					.getItemValue("abs_android_list_num"));
			min_android_list_num = Integer.parseInt(module
					.getItemValue("min_android_list_num"));
		}
		catch (Exception e)
		{};
		
		int rowNum_new = SSMSDAO.getInstance().getRowNum(
				"t_a_android_list_compare");
		int rowNum_old = SSMSDAO.getInstance().getRowNum("t_a_android_list");
		if (rowNum_new < min_android_list_num
				|| Math.abs(rowNum_new - rowNum_old) > abs_android_list_num)
		{
			LOG.error("rowNum_new<min_android_list_num-->"
					+ (rowNum_new < min_android_list_num));
			LOG
					.error("Math.abs(rowNum_new-rowNum_old)>abs_android_list_num-->"
							+ (Math.abs(rowNum_new - rowNum_old) > abs_android_list_num));
			LOG.error("ssms的t_a_android_list_compare表的数据要大于（"
					+ min_android_list_num + "），和t_a_android_list相比较记录数变化量小于("
					+ abs_android_list_num + ") 这个校验不通过！这样榜单将没有变化，麻烦尽快查原因！！！");
			
			StringBuffer sb = new StringBuffer();
			sb.append("<br>");
			sb.append("发生时间：");
			sb.append(PublicUtil.getDateString(new Date(),
					"yyyy-MM-dd HH:mm:ss"));
			sb.append("<br>");
			sb.append("<br>");
			sb.append("ssms的t_a_android_list_compare表的数据要大于（"
					+ min_android_list_num + "），和t_a_android_list相比较记录数变化量小于("
					+ abs_android_list_num + ") 这个校验不通过！这样榜单将没有变化，麻烦尽快查原因！！！");
			sb.append("<br>");
			sb.append("<br>");
			sb
					.append("处理方法：确认是v_android_list的记录数是否正常,确认v_android_list和t_a_android_list的两者差异是否正常，若是，则提高阀值，重启系统！");
			LOG.error(sb.toString());
			// 紧急邮件！商品库优化榜单下线值超过阀值，放弃了下线动作！！！！
			// String subject =
			// module.getItemValue("SYNC_ANDROID_LIST_SUBJECT");
			String subject = "紧急邮件！商品库优化榜单表记录数目验证不通过！！！";
			// bihui@aspirecn.com,mm724@aspirecn.com,mm_gd@aspirecn.com,dongke@aspirecn.com,chenzili@aspirecn.com,wangjun@aspirecn.com,aiyan@aspirecn.com
			String[] mailTo = module.getItemValue("SYNC_ANDROID_MAILTO").split(
					",");
			String mailContent = sb.toString();
			Mail.sendMail(subject, mailContent, mailTo);
			
			return false;
		}
		LOG.info("ssms的t_a_android_list_compare表的数据要大于(" + min_android_list_num
				+ ")，和t_a_android_list相比较记录数变化量小于(" + abs_android_list_num
				+ ") 这个校验通过！");
		LOG.info("t_a_android_list_compare的量是" + rowNum_new
				+ ",前后变化量是（rowNum_new-rowNum_old）" + (rowNum_new - rowNum_old));
		return true;
	}
	
	private void createAndroidListTmp() throws BOException
	{
		// TODO Auto-generated method stub
		try
		{
			SSMSDAO.getInstance().createAndroidListTmp();
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			throw new BOException("在生成t_a_android_list_tmp的时候出现错误！", e);
		}
	}
	
	private void compareChange()
	{
		// TODO Auto-generated method stub
		List<String> listAdd = SSMSDAO.getInstance().compareChangeAdd();
		List<String> listUpdate = SSMSDAO.getInstance().compareChangeUpdate();
		List<String> listDel = SSMSDAO.getInstance().compareChangeDel();
		// 得到记录到t_a_messages
		// Contentid 必须 String 应用ID
		// Action 必须 String 0：新增或直接更新
		// 9：删除
		// Transactionid 必须 String 事务序列ID，用于处理同一事务请求时使用。
		for (String contentid : listAdd)
		{
			addMessages(contentid, "0");
		}
		for (String contentid : listUpdate)
		{
			addMessages(contentid, "0");
		}
		// 这里保护一下：add by aiyan 2013-06-25
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"syncAndroid");
		int down_android_list_num = 5000;// 下线阀值默认5000;
		try
		{
			down_android_list_num = Integer.parseInt(module
					.getItemValue("down_android_list_num"));
		}
		catch (Exception e)
		{};
		
		if (listDel.size() > down_android_list_num)
		{
			
			LOG.error("ssms的的实时下线大于" + down_android_list_num + "，这里放弃下线动作！！");
			
			StringBuffer sb = new StringBuffer();
			sb.append("<br>");
			sb.append("发生时间：");
			sb.append(PublicUtil.getDateString(new Date(),
					"yyyy-MM-dd HH:mm:ss"));
			sb.append("<br>");
			sb.append("<br>");
			sb.append("ssms的的实时下线大于（" + down_android_list_num + "），这里放弃下线动作！！");
			sb.append("处理方法：确认是否有" + (listDel.size() + "下线，若是，则提高下线阀值，重启系统！"));
			LOG.error("紧急邮件！商品库优化榜单下线值超过阀值，放弃了下线动作！！！！");
			LOG.error(sb.toString());
			// 紧急邮件！商品库优化榜单下线值超过阀值，放弃了下线动作！！！！
			// String subject =
			// module.getItemValue("SYNC_ANDROID_LIST_SUBJECT");
			String subject = "紧急邮件！商品库优化榜单下线值超过阀值，放弃了下线动作！！！！";
			// bihui@aspirecn.com,mm724@aspirecn.com,mm_gd@aspirecn.com,dongke@aspirecn.com,chenzili@aspirecn.com,wangjun@aspirecn.com,aiyan@aspirecn.com
			String[] mailTo = module.getItemValue("SYNC_ANDROID_MAILTO").split(
					",");
			String mailContent = sb.toString();
			Mail.sendMail(subject, mailContent, mailTo);
			
		}
		else
		{
			LOG.info(new Date() + " ssms的的实时下线数目是：" + listDel.size());
			
			for (String contentid : listDel)
			{
				addMessages(contentid, "9");
			}
		}
		
	}
	
	private void addMessages(String contentid, String action)
	{
		// TODO Auto-generated method stub
		try
		{
			// SSMSDAO.getInstance().addMessages(contentid,action);
			SSMSDAO.getInstance().addMessages(MSGType.CountUpdateReq,
					contentid + ":" + action);
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			LOG.error(e);
		}
	}
	
	private void changeTableName() throws BOException
	{
		// TODO Auto-generated method stub
		try
		{
			SSMSDAO.getInstance().changeTableName();
		}
		catch (DAOException e)
		{
			// TODO Auto-generated catch block
			throw new BOException(
					"将表t_a_android_list_tmp该为表t_a_android_list出错", e);
		}
		
	}
}
