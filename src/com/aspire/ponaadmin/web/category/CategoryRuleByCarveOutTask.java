
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
     * 还未到执行时间。
     */
    public static final int EXECUTED = 1;

    /**
     * 规则还没有生效
     */
    public static final int INEFFECTIVE = 2;

    /**
     * 成功更新
     */
    public static final int UPDATESUCESS = 0;

    /**
     * 记录日志的实例对象
     */
    protected static JLogger LOG = LoggerFactory.getLogger(CategoryRuleDAO.class);

    private int succCount = 0;

    private int executedCount = 0;

    private int ineffectiveCount = 0;

    private List errorList;

    public void run()
    {

        Date startDate = new Date();
        LOG.info("创业大赛货架自动更新开始：startTime:"
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
                    LOG.error("创业大赛货架更新失败。", e);
                }
            }
            else
            {
                for (int i = 0; i < list.size(); i++)
                {
                    CategoryRuleByCarveOutVO categoryRule = ( CategoryRuleByCarveOutVO ) list.get(i);
                    CategoryRuleByCarveOutExcutor executor = new CategoryRuleByCarveOutExcutor(categoryRule,
                                                                                               this);

                    // 构造异步任务
                    ReflectedTask task = new ReflectedTask(executor,
                                                           "task",
                                                           null,
                                                           null);
                    // 将任务加到运行器中
                    runner.addTask(task);
                }
                runner.waitToFinished();
                runner.end();
            }
            CategoryRuleByCarveOutExcutor.clearCache();// 清空缓存。
            LOG.info("本次创业大赛货架更新执行结果：总共需要处理的创业大赛货架个数" + list.size()
                     + ",其中成功更新个数：" + succCount + ",规则未生效的创业大赛货架个数："
                     + ineffectiveCount + ",还未到执行时间的创业大赛货架个数：" + executedCount);
            Date endDate = new Date();
            StringBuffer sb = new StringBuffer();

            sb.append("开始时间：");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",结束时间：");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));

            sb.append("。<p>处理结果：<br>");
            sb.append("本次共" + (succCount + errorList.size()) + "个创业大赛货架需要更新");
            sb.append("。<br>其中成功处理创业大赛货架");
            sb.append(succCount);
            sb.append("个，失败处理创业大赛货架个数为");
            sb.append(errorList.size());
            sb.append("条<p>");
            if (errorList.size() != 0)
            {
                sb.append("失败更新的创业大赛货架如下：<br>");
                for (int i = 0; i < errorList.size(); i++)
                {
                    sb.append(errorList.get(i));
                    sb.append("<br>");
                }
            }
            Mail.sendMail("创业大赛货架自动更新成功",
                          sb.toString(),
                          MailConfig.getInstance().getMailToArray());
        }
        catch (Exception e)
        {
            LOG.error("无法获取创业大赛货架的规则，创业大赛货架自动更新失败", e);
            Mail.sendMail("创业大赛货架自动更新失败",
                          "创业大赛货架自动更新失败,请联系管理员",
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
            LOG.error("获取该货架名称异常", e1);
            cateNamePath = "";
        }
        errorList.add("id=" + vo.getCid() + ",namepath=" + cateNamePath);
    }
}
