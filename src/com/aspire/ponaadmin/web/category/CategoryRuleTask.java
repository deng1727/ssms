package com.aspire.ponaadmin.web.category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;


import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.syncData.bo.DataSyncBO;
import com.aspire.ponaadmin.web.category.intervenor.IntervenorTools;
import com.aspire.ponaadmin.web.category.intervenor.category.IntervenorCategoryBO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryDAO;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;


public class CategoryRuleTask extends TimerTask 
{
	/**
	 * 还未到执行时间。
	 */
	public static final int EXECUTED=1;
	/**
	 * 规则还没有生效
	 */
	public static final int INEFFECTIVE=2;
	/**
	 * 成功更新
	 */
	public static final int UPDATESUCESS=0;
	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger LOG = LoggerFactory
			.getLogger(CategoryRuleDAO.class);
	private int succCount=0;
	private int executedCount=0;
	private int ineffectiveCount=0;
	private List errorList;
	public void run()
	{
		
		Date startDate=new Date();
		LOG.info("货架自动更新开始：startTime:"+PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
		errorList=new ArrayList();
		succCount=0;
		executedCount=0;
		ineffectiveCount=0;
		String content=null;//短信通知信息
		try
		{//原名list,改名为list0
			List list = CategoryRuleBO.getInstance().getAllCategoryRules();
			TaskRunner runner=new TaskRunner(CategoryRuleConfig.RuleRunningTaskNum);
			for(int i=0;i<list.size();i++)
			{
				CategoryRuleVO categoryRule = (CategoryRuleVO)list.get(i);
				CategoryRuleExcutor executor = new CategoryRuleExcutor(categoryRule,this);
				runner.addTask(executor);
			}
			runner.waitToFinished();
			runner.stop();
			CategoryRuleExcutor.clearCache();//清空缓存。
			LOG.info("本次货架更新执行结果：总共需要处理的货架个数" + list.size() + ",其中成功更新个数：" + succCount
					+ ",规则未生效的货架个数：" + ineffectiveCount + ",还未到执行时间的货架个数："
					+ executedCount);
			Date endDate=new Date();
			StringBuffer sb=new StringBuffer();

			sb.append("开始时间：");
			sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",结束时间：");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			
			sb.append("。<p>处理结果：<br>");
			sb.append("本次共"+(succCount+errorList.size())+"个货架需要更新");
			sb.append("。<br>其中成功处理货架");
			sb.append(succCount);
			sb.append("个，失败处理货架个数为");
			sb.append(errorList.size());
			sb.append("条<p>");
			if(errorList.size()!=0)
			{
				sb.append("失败更新的货架如下：<br>");
				for(int i=0;i<errorList.size();i++)
				{
					sb.append(errorList.get(i));
					sb.append("<br>");
				}
				
			}
			Mail.sendMail("货架自动更新成功", sb.toString(),MailConfig.getInstance().getMailToArray());
			content=getPhoneMsg();//获取处理成功后的短信通知信息
		}
		catch(Exception e)
		{
			content="无法获取货架的规则，货架自动更新失败";
			LOG.error(content,e);
			Mail.sendMail("货架自动更新失败", "货架自动更新失败,请联系管理员",MailConfig.getInstance().getMailToArray());
		}finally{
			DataSyncBO.getInstance().sendPhoneMsg("人工干预",content);//发送短息
			//人工干预逻辑调用
			List<Category> list=new ArrayList<Category>();
			try {
				IntervenorCategoryBO.getInstance().queryCategoryVOList(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < list.size(); i++) {
				try{
					Category c=list.get(i);
					if(!c.getType().equals("2")){
						String id=c.getId();
						try {
							IntervenorTools.getInstance().intervenorCategory(id,RepositoryConstants.SYN_HIS_NO);
						} catch (BOException e) {
						}
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
					
			}
		}
		
	}
    /**
	 * 拼装处理自动更新处理短信通知结果信息
	 * @return
	 */
	private String getPhoneMsg(){
		StringBuffer sb=new StringBuffer();		
		sb.append("货架自动更新处理结果：");
		sb.append("本次共"+(succCount+errorList.size())+"个货架需要更新");
		sb.append("。其中成功处理货架");
		sb.append(succCount);
		sb.append("个，失败处理货架个数为");
		sb.append(errorList.size());
		sb.append("条。");
		return sb.toString();
	}
	public synchronized int getSuccCount()
	{
		return succCount;
	}
	public synchronized void addSuccCount()
	{
		this.succCount ++;
	}
	public synchronized int getExecutedCount()
	{
		return executedCount;
	}
	public synchronized void addExecutedCount()
	{
		this.executedCount++;
	}
	public synchronized int getIneffectiveCount()
	{
		return ineffectiveCount;
	}
	public synchronized void addIneffectiveCount()
	{
		this.ineffectiveCount++;
	}
	public synchronized void addError(CategoryRuleVO vo)
	{
		String cateNamePath;
		try
		{
			cateNamePath = CategoryDAO.getInstance().getCategoryNamePathByID(vo.getCid());
		} catch (DAOException e1)
		{
			LOG.error("获取该货架名称异常",e1);
			cateNamePath="";
		}
		errorList.add("id="+vo.getCid()+",namepath="+cateNamePath);
	}
}
