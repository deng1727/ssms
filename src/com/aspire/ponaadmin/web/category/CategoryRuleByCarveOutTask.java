
package com.aspire.ponaadmin.web.category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.repository.CategoryDAO;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class CategoryRuleByCarveOutTask extends TimerTask
{

    /**
     * ��δ��ִ��ʱ�䡣
     */
    public static final int EXECUTED = 1;

    /**
     * ����û����Ч
     */
    public static final int INEFFECTIVE = 2;

    /**
     * �ɹ�����
     */
    public static final int UPDATESUCESS = 0;

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger LOG = LoggerFactory.getLogger(CategoryRuleDAO.class);

    private int succCount = 0;

    private int executedCount = 0;

    private int ineffectiveCount = 0;

    private List errorList;

    public void run()
    {

        Date startDate = new Date();
        LOG.info("��ҵ���������Զ����¿�ʼ��startTime:"
                 + PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
        errorList = new ArrayList();
        succCount = 0;
        executedCount = 0;
        ineffectiveCount = 0;

        try
        {
            List list = CategoryRuleDAO.getInstance()
                                       .getAllAutoUpdateCategoryByCarverOut();
            // TaskTokenCenter.getInstance();
            TaskRunner runner = new TaskRunner(CategoryRuleByCarveOutConfig.RuleRunningTaskNum);
            if (list.size() == 1)
            {
                CategoryRuleByCarveOutVO categoryRule = ( CategoryRuleByCarveOutVO ) list.get(0);
                CategoryRuleByCarveOutExcutor executor = new CategoryRuleByCarveOutExcutor(categoryRule,
                                                                                           this);
                
                try
                {
                    executor.task();
                }
                catch (Throwable e)
                {
                    LOG.error("��ҵ�������ܸ���ʧ�ܡ�", e);
                }
            }
            else
            {
                for (int i = 0; i < list.size(); i++)
                {
                    CategoryRuleByCarveOutVO categoryRule = ( CategoryRuleByCarveOutVO ) list.get(i);
                    CategoryRuleByCarveOutExcutor executor = new CategoryRuleByCarveOutExcutor(categoryRule,
                                                                                               this);

                    // �����첽����
                    ReflectedTask task = new ReflectedTask(executor,
                                                           "task",
                                                           null,
                                                           null);
                    // ������ӵ���������
                    runner.addTask(task);
                }
                runner.waitToFinished();
                runner.end();
            }
            CategoryRuleByCarveOutExcutor.clearCache();// ��ջ��档
            LOG.info("���δ�ҵ�������ܸ���ִ�н�����ܹ���Ҫ����Ĵ�ҵ�������ܸ���" + list.size()
                     + ",���гɹ����¸�����" + succCount + ",����δ��Ч�Ĵ�ҵ�������ܸ�����"
                     + ineffectiveCount + ",��δ��ִ��ʱ��Ĵ�ҵ�������ܸ�����" + executedCount);
            Date endDate = new Date();
            StringBuffer sb = new StringBuffer();

            sb.append("��ʼʱ�䣺");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",����ʱ�䣺");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));

            sb.append("��<p>��������<br>");
            sb.append("���ι�" + (succCount + errorList.size()) + "����ҵ����������Ҫ����");
            sb.append("��<br>���гɹ�����ҵ��������");
            sb.append(succCount);
            sb.append("����ʧ�ܴ���ҵ�������ܸ���Ϊ");
            sb.append(errorList.size());
            sb.append("��<p>");
            if (errorList.size() != 0)
            {
                sb.append("ʧ�ܸ��µĴ�ҵ�����������£�<br>");
                for (int i = 0; i < errorList.size(); i++)
                {
                    sb.append(errorList.get(i));
                    sb.append("<br>");
                }
            }
            Mail.sendMail("��ҵ���������Զ����³ɹ�",
                          sb.toString(),
                          MailConfig.getInstance().getMailToArray());
        }
        catch (Exception e)
        {
            LOG.error("�޷���ȡ��ҵ�������ܵĹ��򣬴�ҵ���������Զ�����ʧ��", e);
            Mail.sendMail("��ҵ���������Զ�����ʧ��",
                          "��ҵ���������Զ�����ʧ��,����ϵ����Ա",
                          MailConfig.getInstance().getMailToArray());
        }
    }

    public synchronized int getSuccCount()
    {
        return succCount;
    }

    public synchronized void addSuccCount()
    {
        this.succCount++;
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

    public synchronized void addError(CategoryRuleByCarveOutVO vo)
    {
        String cateNamePath;
        try
        {
            cateNamePath = CategoryDAO.getInstance()
                                      .getCategoryNamePathByID(vo.getCid());
        }
        catch (DAOException e1)
        {
            LOG.error("��ȡ�û��������쳣", e1);
            cateNamePath = "";
        }
        errorList.add("id=" + vo.getCid() + ",namepath=" + cateNamePath);
    }
}
