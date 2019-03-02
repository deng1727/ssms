
package com.aspire.ponaadmin.web.category;

import java.util.Calendar;
import java.util.Timer;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class SynCategoryMgr
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(SynCategoryMgr.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static SynCategoryMgr instance = new SynCategoryMgr();

    /**
     * ���췽������singletonģʽ����
     */
    private SynCategoryMgr()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static SynCategoryMgr getInstance()
    {
        return instance;
    }
    
    public void loadConfig()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("����ͬ����Ӿ�Ʒ���ʼ�������ļ���ʼ��");
        }
        try
        {
            SynCategoryConfig.loadConfig();
        }
        catch (Exception e)
        {
            LOG.error("��ȡ����ͬ����Ӿ�Ʒ����������������ͬ����Ӿ�Ʒ��������ֹ��", e);
            System.err.println("��ȡ����ͬ����Ӿ�Ʒ����������������ͬ����Ӿ�Ʒ��������ֹ��");
            return;
        }
    }
    
    public void start()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("����ͬ����Ӿ�Ʒ���ʼ��������ʼ��");
        }
        
        new SynCategoryTask().run();
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
                LOG.debug("����ͬ����Ӿ�Ʒ���ʼ��������ʼ��");
            }
            try
            {
                SynCategoryConfig.loadConfig();
            }
            catch (Exception e)
            {
                LOG.error("��ȡ����ͬ����Ӿ�Ʒ����������������ͬ����Ӿ�Ʒ��������ֹ��", e);
                System.err.println("��ȡ����ͬ����Ӿ�Ʒ����������������ͬ����Ӿ�Ʒ��������ֹ��");
                return;
            }
            
            Calendar firstExecTime = getTriggerTime();
            
            long period = 24 * 60 * 60 * 1000;
            
            // ����Timer���schedule����������ʱ����
            Timer timer = new Timer(true);
            
            timer.schedule(new SynCategoryTask(),
                           firstExecTime.getTime(),
                           period);
        }
        catch (BOException e)
        {
            LOG.error("����ͬ����Ӿ�Ʒ�������ʼ��ʧ�ܣ�", e);
        }
    }

    private Calendar getTriggerTime() throws BOException
    {
        int hour, minute;
        String SynStartTime = SynCategoryConfig.STARTTIME;;
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
            LOG.error("����ͬ����Ӿ�Ʒ������ʼִ��ʱ�������ʽ����" + SynStartTime);
            throw new BOException("����ͬ����Ӿ�Ʒ������ʼִ��ʱ�������ʽ����startTime:"
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
