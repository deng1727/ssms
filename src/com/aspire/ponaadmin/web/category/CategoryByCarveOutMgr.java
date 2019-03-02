
package com.aspire.ponaadmin.web.category;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CategoryByCarveOutMgr
{
    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryByCarveOutMgr.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static CategoryByCarveOutMgr instance = new CategoryByCarveOutMgr();

    /**
     * ���췽������singletonģʽ����
     */
    private CategoryByCarveOutMgr()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CategoryByCarveOutMgr getInstance()
    {
        return instance;
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
                LOG.debug("��ҵ���������Զ����³�ʼ��������ʼ��");
            }
            try
            {
                CategoryRuleByCarveOutConfig.loadConfig();
            }
            catch (Exception e)
            {
                LOG.error("��ȡ��ҵ���������Զ����µ���������������Զ�����������ֹ��", e);
                System.err.println("��ȡ��ҵ���������Զ����µ���������������Զ�����������ֹ��");
                return;
            }
            Calendar firstExecTime = getTriggerTime();
            long period = 24 * 60 * 60 * 1000;
            // ����Timer���schedule����������ʱ����
            Timer timer = new Timer(true);
            timer.schedule(new CategoryRuleByCarveOutTask(),
                           firstExecTime.getTime(),
                           period);
        }
        catch (BOException e)
        {
            LOG.error("��ҵ���������Զ����������ʼ��ʧ�ܣ�", e);

        }
    }

    private Calendar getTriggerTime() throws BOException
    {
        int hour, minute;
        String SynStartTime = CategoryRuleByCarveOutConfig.STARTTIME;;
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
            LOG.error("��ҵ���������Զ����¿�ʼִ��ʱ�������ʽ����" + SynStartTime);
            throw new BOException("��ҵ���������Զ����¿�ʼִ��ʱ�������ʽ����startTime:"
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
