
package com.aspire.ponaadmin.web.category;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CategoryMgr
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryMgr.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static CategoryMgr instance = new CategoryMgr();

    /**
     * ���췽������singletonģʽ����
     */
    private CategoryMgr()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CategoryMgr getInstance()
    {
        return instance;
    }
    
    public void loadConfig()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�����Զ����³�ʼ�������ļ���ʼ��");
        }

        try
        {
            CategoryRuleConfig.loadConfig();
        }
        catch (Exception e)
        {
            LOG.error("��ȡ�����Զ����µ���������������Զ�����������ֹ��", e);
            System.err.println("��ȡ�����Զ����µ���������������Զ�����������ֹ��");
            return;
        }
    }

    public void start()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("�����Զ���ϸ��ʼ��������ʼ��");
        }

        new CategoryRuleTask().run();
    }

    /**
     * ��ʼ��������ϵͳ������ʱ����Ҫ���ñ�������
     */
    public void init()
    {
        try
        {
            if (LOG.isDebugEnabled())
            {
                LOG.debug("�����Զ���ϸ��ʼ��������ʼ��");
            }
            /*
             * //��ʼ������� ModuleConfig module = ConfigFactory.getSystemConfig()
             * .getModuleConfig("categoryAutoUpdate"); if (module == null) {
             * throw new
             * BOException("system-config.xml�ļ����Ҳ���categoryAutoUpdateģ��"); }
             * 
             * try { STARTTIME = module.getItemValue("startTime");
             * CONDITIONMAXVALUE=Integer.parseInt(module.getItemValue("ConditionMaxValue"));
             * SPNAMESORTCOUNT=Integer.parseInt(module.getItemValue("spnameSortCount"));
             * SPNAMEMAXCOUNT=Integer.parseInt(module.getItemValue("spnameMaxCount")); }
             * catch (Exception e) { LOG.error("��ȡ�����Զ����µ���������������Զ�������ֹ��",e);
             * return ; }
             */
            try
            {
                CategoryRuleConfig.loadConfig();
            }
            catch (Exception e)
            {
                LOG.error("��ȡ�����Զ����µ���������������Զ�����������ֹ��", e);
                System.err.println("��ȡ�����Զ����µ���������������Զ�����������ֹ��");
                return;
            }
            Calendar firstExecTime = getTriggerTime();
            long period = 24 * 60 * 60 * 1000;
            // ����Timer���schedule����������ʱ����
            Timer timer = new Timer(true);
            timer.schedule(new CategoryRuleTask(),
                           firstExecTime.getTime(),
                           period);
        }
        catch (BOException e)
        {
            LOG.error("���ܸ����Զ������ʼ��ʧ�ܣ�", e);

        }

    }

    /**
     * ֱ��ִ�����л��ܵĹ������߼� �ɵ������CategoryTimerTask����Ӧ����һ���ģ�ֻ����������ÿͻ�����ֱ�ӵ���
     */
    public void runAllCategoryRules()
    {
        // ��������ʱ�Ȳ�ʵ��
    }

    private Calendar getTriggerTime() throws BOException
    {

        int hour, minute;
        String SynStartTime = CategoryRuleConfig.STARTTIME;;
        try
        {

            hour = Integer.parseInt(SynStartTime.split(":")[0]);
            minute = Integer.parseInt(SynStartTime.split(":")[1]);
            if (hour < 0 || hour > 23 || minute < 0 || minute > 59)
            {
                throw new IllegalArgumentException("ʱ���ʽ����");
            }
        }
        catch (Exception ex)
        {
            LOG.error("�����Զ����¿�ʼִ��ʱ�������ʽ����" + SynStartTime);
            throw new BOException("�����Զ����¿�ʼִ��ʱ�������ʽ����startTime:"
                                  + SynStartTime);
        }
        // ��һ��ִ��ʱ��
        Calendar firstExecTime = Calendar.getInstance();
        Calendar curTime = Calendar.getInstance();

        if (curTime.get(Calendar.HOUR_OF_DAY) > hour)
        {
            firstExecTime.add(Calendar.DAY_OF_MONTH, 1);
        }
        else if (curTime.get(Calendar.HOUR_OF_DAY) == hour)
        {
            if (curTime.get(Calendar.MINUTE) >= minute)
            {
                firstExecTime.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        // ����ִ��ʱ��
        firstExecTime.set(Calendar.HOUR_OF_DAY, hour);
        firstExecTime.set(Calendar.MINUTE, minute);
        firstExecTime.set(Calendar.SECOND, 0);
        firstExecTime.set(Calendar.MILLISECOND, 0);
        return firstExecTime;
    }

}
