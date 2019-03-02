
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
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryRuleBO.class);

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
    /**
     * singletonģʽ��ʵ��
     */
    private static CategoryRuleBO instance = new CategoryRuleBO();

    /**
     * ���췽������singletonģʽ����
     */
    private CategoryRuleBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
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
            throw new BOException("ִ�����ݿ����ʧ��", e);
        }
        return categoryRuleName;
    }

    /**
     * ������Ʒ�ֶ�����
     * 
     * 
     */
    // public void manualUpdateCategory(){
    // public void manualUpdateCategory(String [] cid){
    public void run()
    {

        String[] cid = this.cids;
        // ��������޸�ʱ��Ϊǰһ��
        // Calendar lastExecTime=Calendar.getInstance();
        // lastExecTime.set(Calendar.DAY_OF_MONTH,lastExecTime.get(Calendar.DAY_OF_MONTH)-1);
        // Date lTime = lastExecTime.getTime();
        CategoryRuleDAO categoryRuleDAO = CategoryRuleDAO.getInstance();
        // int updatecount =
        // categoryRuleDAO.updateAllRuleLastExecTime(lTime);//updateAllRuleLastExecTimeNull
        int updatecount = 0;
        if (cid == null || cid.length <= 0)
        {
            // ȫ�������ֶ�����
            updatecount = categoryRuleDAO.updateAllRuleLastExecTimeNull();
        }
        else
        {// ѡ�л����ֶ�����
            updatecount = categoryRuleDAO.updateCheckedRuleLastExecTimeNull(cid);
        }
        LOG.info("��:" + updatecount + "�����¹���������ʱ�����Ϊnull");

        Date startDate = new Date();
        LOG.info("�����ֶ����¿�ʼ��startTime:"
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
                // LOG.error("���ܸ���ʧ�ܡ�",e);
                // String cateName;
                // try
                // {
                // cateName =
                // CategoryDAO.getInstance().getCategoryNameByID(categoryRule.getCid());
                // } catch (DAOException e1)
                // {
                // LOG.error("��ȡ�û��������쳣",e);
                // cateName="";
                // }
                // this.addError(categoryRule);
                // //errorList.add("id="+categoryRule.getCid()+",name="+cateName);
                //					
                // }
            }

            runner.waitToFinished();
            runner.stop();
            CategoryRuleExcutor.clearCache();// ��ջ��档
            LOG.info("���λ��ܸ���ִ�н�����ܹ���Ҫ����Ļ��ܸ���" + list.size() + ",���гɹ����¸�����"
                     + succCount + ",����δ��Ч�Ļ��ܸ�����" + ineffectiveCount
                     + ",��δ��ִ��ʱ��Ļ��ܸ�����" + executedCount);
            Date endDate = new Date();
            StringBuffer sb = new StringBuffer();

            sb.append("��ʼʱ�䣺");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",����ʱ�䣺");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));

            sb.append("��<p>��������<br>");
            sb.append("���ι�" + (succCount + errorList.size()) + "��������Ҫ����");
            sb.append("��<br>���гɹ��������");
            sb.append(succCount);
            sb.append("����ʧ�ܴ�����ܸ���Ϊ");
            sb.append(errorList.size());
            sb.append("��<p>");
            if (errorList.size() != 0)
            {
                sb.append("ʧ�ܸ��µĻ������£�<br>");
                for (int i = 0; i < errorList.size(); i++)
                {
                    sb.append(errorList.get(i));
                    sb.append("<br>");
                }

            }
            Mail.sendMail("�����ֶ����³ɹ�",
                          sb.toString(),
                          MailConfig.getInstance().getMailToArray());
        }
        catch (Exception e)
        {
            LOG.error("�޷���ȡ���ܵĹ��򣬻����ֶ�����ʧ��", e);
            Mail.sendMail("�����ֶ�����ʧ��",
                          "�����ֶ�����ʧ��,����ϵ����Ա",
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
            LOG.error("��ȡ�û��������쳣", e1);
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
            LOG.error("��ȡ�û��������쳣", e1);
            cateNamePath = "";
        }
        errorList.add("id=" + vo.getCid() + ",namepath=" + cateNamePath+";�ϼ�ʧ��ԭ��:"+errorMsg);
    }
    /**
     * ��ȡ������Ҫִ�еĻ��ܹ���
     * 
     * @return
     * @throws BOException
     */
    public List getAllCategoryRules() throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ȡ������Ҫִ�еĻ��ܹ���:��ʼִ��......");
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
                    LOG.error("�����ڶ�Ӧ����id��cid=" + vo.getCid() + ",ruleid="
                              + vo.getRuleId());
                    continue;
                }
                vo.setRuleVO(ruleVO);
                legalCateRules.add(vo);
            }

        }
        catch (DAOException ex)
        {
            throw new BOException("��ȡ������Ҫִ�еĻ��ܹ����б�ʧ��", ex);
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��ȡ������Ҫִ�еĻ��ܹ���:ִ�н���");
        }

        return legalCateRules;
    }

    /**
     * ��ȡӦ�û���������Ϣ����ȥ�����������пո�
     * 
     * @param id ���ݵ�����id
     * @return ���ݵ�appcatename�ֶΣ����û���ҵ��򷵻�null
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
            throw new BOException("��ȡӦ�û���������Ϣ����id=" + id, e);
        }
        return deviceNameList;
    }

    /**
     * ��ȡӦ��spname��Ϣ
     * 
     * @param id ���ݵ�����id
     * @return ���ݵ�spname�ֶΣ����û���ҵ��򷵻�""
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
            throw new BOException("��ȡӦ��spname��Ϣ����id=" + id, e);
        }
        return spName;
    }

    /**
     * ���ڰѾ�Ʒ��������ͬ����ָ���Ż����ͻ�����
     * 
     * @param categoryId ��Ʒ�����
     * @param type �Ż�����
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
            throw new BOException("ͬ����Ʒ��" + categoryId + " �����ݵ��Ż�����" + type
                                  + " ʱ�������ݿ����", e);
        }
    }
}
