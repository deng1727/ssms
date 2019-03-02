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
	 * ��δ��ִ��ʱ�䡣
	 */
	public static final int EXECUTED=1;
	/**
	 * ����û����Ч
	 */
	public static final int INEFFECTIVE=2;
	/**
	 * �ɹ�����
	 */
	public static final int UPDATESUCESS=0;
	/**
	 * ��¼��־��ʵ������
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
		LOG.info("�����Զ����¿�ʼ��startTime:"+PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
		errorList=new ArrayList();
		succCount=0;
		executedCount=0;
		ineffectiveCount=0;
		String content=null;//����֪ͨ��Ϣ
		try
		{//ԭ��list,����Ϊlist0
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
			CategoryRuleExcutor.clearCache();//��ջ��档
			LOG.info("���λ��ܸ���ִ�н�����ܹ���Ҫ����Ļ��ܸ���" + list.size() + ",���гɹ����¸�����" + succCount
					+ ",����δ��Ч�Ļ��ܸ�����" + ineffectiveCount + ",��δ��ִ��ʱ��Ļ��ܸ�����"
					+ executedCount);
			Date endDate=new Date();
			StringBuffer sb=new StringBuffer();

			sb.append("��ʼʱ�䣺");
			sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",����ʱ�䣺");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			
			sb.append("��<p>��������<br>");
			sb.append("���ι�"+(succCount+errorList.size())+"��������Ҫ����");
			sb.append("��<br>���гɹ��������");
			sb.append(succCount);
			sb.append("����ʧ�ܴ�����ܸ���Ϊ");
			sb.append(errorList.size());
			sb.append("��<p>");
			if(errorList.size()!=0)
			{
				sb.append("ʧ�ܸ��µĻ������£�<br>");
				for(int i=0;i<errorList.size();i++)
				{
					sb.append(errorList.get(i));
					sb.append("<br>");
				}
				
			}
			Mail.sendMail("�����Զ����³ɹ�", sb.toString(),MailConfig.getInstance().getMailToArray());
			content=getPhoneMsg();//��ȡ����ɹ���Ķ���֪ͨ��Ϣ
		}
		catch(Exception e)
		{
			content="�޷���ȡ���ܵĹ��򣬻����Զ�����ʧ��";
			LOG.error(content,e);
			Mail.sendMail("�����Զ�����ʧ��", "�����Զ�����ʧ��,����ϵ����Ա",MailConfig.getInstance().getMailToArray());
		}finally{
			DataSyncBO.getInstance().sendPhoneMsg("�˹���Ԥ",content);//���Ͷ�Ϣ
			//�˹���Ԥ�߼�����
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
	 * ƴװ�����Զ����´������֪ͨ�����Ϣ
	 * @return
	 */
	private String getPhoneMsg(){
		StringBuffer sb=new StringBuffer();		
		sb.append("�����Զ����´�������");
		sb.append("���ι�"+(succCount+errorList.size())+"��������Ҫ����");
		sb.append("�����гɹ��������");
		sb.append(succCount);
		sb.append("����ʧ�ܴ�����ܸ���Ϊ");
		sb.append(errorList.size());
		sb.append("����");
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
			LOG.error("��ȡ�û��������쳣",e1);
			cateNamePath="";
		}
		errorList.add("id="+vo.getCid()+",namepath="+cateNamePath);
	}
}
