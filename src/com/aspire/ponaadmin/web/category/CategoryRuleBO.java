
package com.aspire.ponaadmin.web.category;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.ponaadmin.web.category.rule.RuleBO;
import com.aspire.ponaadmin.web.category.rule.RuleVO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.repository.CategoryDAO;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class CategoryRuleBO extends TimerTask
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryRuleBO.class);

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
    /**
     * singleton模式的实例
     */
    private static CategoryRuleBO instance = new CategoryRuleBO();

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryRuleBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CategoryRuleBO getInstance()
    {
        return instance;
    }

    private int succCount = 0;

    private int executedCount = 0;

    private int ineffectiveCount = 0;

    private List errorList;

    private String[] cids;

    public void manualUpdateCategory(String[] cid)
    {

        this.cids = cid;
        this.run();

    }

    /**
     * 
     * @return
     * @throws BOException
     */
    public List getAllCategoryRuleName() throws BOException
    {

        CategoryRuleDAO categoryRuleDAO = CategoryRuleDAO.getInstance();
        List categoryRuleName;
        try
        {
            categoryRuleName = categoryRuleDAO.getAllCategoryRuleName();
        }
        catch (DAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new BOException("执行数据库操作失败", e);
        }
        return categoryRuleName;
    }

    /**
     * 货架商品手动更新
     * 
     * 
     */
    // public void manualUpdateCategory(){
    // public void manualUpdateCategory(String [] cid){
    public void run()
    {

        String[] cid = this.cids;
        // 设置最后修改时间为前一天
        // Calendar lastExecTime=Calendar.getInstance();
        // lastExecTime.set(Calendar.DAY_OF_MONTH,lastExecTime.get(Calendar.DAY_OF_MONTH)-1);
        // Date lTime = lastExecTime.getTime();
        CategoryRuleDAO categoryRuleDAO = CategoryRuleDAO.getInstance();
        // int updatecount =
        // categoryRuleDAO.updateAllRuleLastExecTime(lTime);//updateAllRuleLastExecTimeNull
        int updatecount = 0;
        if (cid == null || cid.length <= 0)
        {
            // 全量货架手动更新
            updatecount = categoryRuleDAO.updateAllRuleLastExecTimeNull();
        }
        else
        {// 选中货架手动更新
            updatecount = categoryRuleDAO.updateCheckedRuleLastExecTimeNull(cid);
        }
        LOG.info("将:" + updatecount + "条更新规则最后更新时间调整为null");

        Date startDate = new Date();
        LOG.info("货架手动更新开始：startTime:"
                 + PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
        // List errorList=new ArrayList();
        // int succCount=0;
        // int executedCount=0;
        // int ineffectiveCount=0;

        errorList = new ArrayList();
        succCount = 0;
        executedCount = 0;
        ineffectiveCount = 0;

        try
        {
            List list = getAllCategoryRules();
            TaskRunner runner = new TaskRunner(CategoryRuleConfig.RuleRunningTaskNum);
            for (int i = 0; i < list.size(); i++)
            {
                CategoryRuleVO categoryRule = ( CategoryRuleVO ) list.get(i);
                CategoryRuleExcutor executor = new CategoryRuleExcutor(categoryRule,
                                                                       this);
                ReflectedTask task = new ReflectedTask(executor,
                                                       "mutask",
                                                       null,
                                                       null);
                runner.addTask(task);
                // CategoryRuleExcutor executor = new
                // CategoryRuleExcutor(categoryRule,this);
                // runner.addTask(executor);
                // try
                // {
                // int result=executor.excucte();
                // if(result==EXECUTED)
                // {
                // //executedCount++;
                // this.addExecutedCount();
                // }
                // if(result==INEFFECTIVE)
                // {
                // //ineffectiveCount++;
                // this.addIneffectiveCount();
                // }
                // if(result==UPDATESUCESS)
                // {
                // //succCount++;
                // this.addSuccCount();
                // }
                // }
                // catch(Exception e)
                // {
                // LOG.error("货架更新失败。",e);
                // String cateName;
                // try
                // {
                // cateName =
                // CategoryDAO.getInstance().getCategoryNameByID(categoryRule.getCid());
                // } catch (DAOException e1)
                // {
                // LOG.error("获取该货架名称异常",e);
                // cateName="";
                // }
                // this.addError(categoryRule);
                // //errorList.add("id="+categoryRule.getCid()+",name="+cateName);
                //					
                // }
            }

            runner.waitToFinished();
            runner.stop();
            CategoryRuleExcutor.clearCache();// 清空缓存。
            LOG.info("本次货架更新执行结果：总共需要处理的货架个数" + list.size() + ",其中成功更新个数："
                     + succCount + ",规则未生效的货架个数：" + ineffectiveCount
                     + ",还未到执行时间的货架个数：" + executedCount);
            Date endDate = new Date();
            StringBuffer sb = new StringBuffer();

            sb.append("开始时间：");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",结束时间：");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));

            sb.append("。<p>处理结果：<br>");
            sb.append("本次共" + (succCount + errorList.size()) + "个货架需要更新");
            sb.append("。<br>其中成功处理货架");
            sb.append(succCount);
            sb.append("个，失败处理货架个数为");
            sb.append(errorList.size());
            sb.append("条<p>");
            if (errorList.size() != 0)
            {
                sb.append("失败更新的货架如下：<br>");
                for (int i = 0; i < errorList.size(); i++)
                {
                    sb.append(errorList.get(i));
                    sb.append("<br>");
                }

            }
            Mail.sendMail("货架手动更新成功",
                          sb.toString(),
                          MailConfig.getInstance().getMailToArray());
        }
        catch (Exception e)
        {
            LOG.error("无法获取货架的规则，货架手动更新失败", e);
            Mail.sendMail("货架手动更新失败",
                          "货架手动更新失败,请联系管理员",
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

    public synchronized void addError(CategoryRuleVO vo)
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
    public synchronized void addErrorInfo(CategoryRuleVO vo,String errorMsg)
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
        errorList.add("id=" + vo.getCid() + ",namepath=" + cateNamePath+";上架失败原因:"+errorMsg);
    }
    /**
     * 获取所有需要执行的货架规则
     * 
     * @return
     * @throws BOException
     */
    public List getAllCategoryRules() throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("获取所有需要执行的货架规则:开始执行......");
        }
        HashMap rules = RuleBO.getInstance().getAllRules();
        List legalCateRules = new ArrayList(100);
        try
        {
            List cateRules = CategoryRuleDAO.getInstance()
                                            .getAllAutoUpdateCategory();
            for (int i = 0; i < cateRules.size(); i++)
            {
                CategoryRuleVO vo = ( CategoryRuleVO ) cateRules.get(i);
                RuleVO ruleVO = ( RuleVO ) rules.get(new Integer(vo.getRuleId()));
                if (ruleVO == null)
                {
                    LOG.error("不存在对应规则id，cid=" + vo.getCid() + ",ruleid="
                              + vo.getRuleId());
                    continue;
                }
                vo.setRuleVO(ruleVO);
                legalCateRules.add(vo);
            }

        }
        catch (DAOException ex)
        {
            throw new BOException("获取所有需要执行的货架规则列表失败", ex);
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug("获取所有需要执行的货架规则:执行结束");
        }

        return legalCateRules;
    }

    /**
     * 获取应用机型适配信息。（去除机型名称中空格）
     * 
     * @param id 内容的内码id
     * @return 内容的appcatename字段，如果没有找到则返回null
     * @throws BOException NullPointerException
     */
    public List DeviceNameList(String id) throws BOException
    {
        List deviceNameList = null;
        try
        {
            deviceNameList = CategoryRuleDAO.getInstance()
                                            .getDeviceNameListById(id);

        }
        catch (DAOException e)
        {
            throw new BOException("获取应用机型适配信息出错id=" + id, e);
        }
        return deviceNameList;
    }

    /**
     * 获取应用spname信息
     * 
     * @param id 内容的内码id
     * @return 内容的spname字段，如果没有找到则返回""
     * @throws BOException NullPointerException
     */
    public String getSpNameById(String id) throws BOException
    {
        String spName;
        try
        {
            spName = CategoryRuleDAO.getInstance().getSpNameById(id);

        }
        catch (DAOException e)
        {
            throw new BOException("获取应用spname信息出错id=" + id, e);
        }
        return spName;
    }

    /**
     * 用于把精品库中数据同步到指定门户类型货架上
     * 
     * @param categoryId 精品库货架
     * @param type 门户类型
     * @throws BOException
     */
    public void updateSynCategory(String categoryId, String type)
                    throws BOException
    {
        try
        {
            CategoryRuleDAO.getInstance().updateSynCategory(categoryId, type);
        }
        catch (DAOException e)
        {
            throw new BOException("同步精品库" + categoryId + " 中数据到门户类型" + type
                                  + " 时发生数据库错误", e);
        }
    }
}
