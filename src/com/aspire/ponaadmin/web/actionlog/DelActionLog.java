package com.aspire.ponaadmin.web.actionlog ;

import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.log.proxy.JLogger;
import java.util.Date;

/**
 * <p>ɾ����־��</p>
 * <p>�����뵱ǰʱ��6����ǰ����־</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class DelActionLog implements Runnable
{

    /** Automatically generated javadoc for: THE_SIXTY_SIXTY */
    private static final int THE_SIXTY_SIXTY = 3600;

    /** Automatically generated javadoc for: THE_KILO */
    private static final int THE_KILO = 1000;

    /** Automatically generated javadoc for: THE_MONTH */
    private static final int THE_MONTH = 12;

    /** Automatically generated javadoc for: THE_YEAR */
    private static final int THE_YEAR = 1900;

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(DelActionLog.class) ;

    /**
     * singletonģʽ��ʵ��
     */
    private static DelActionLog instance = new DelActionLog() ;

    /**
     * �����߳�ʵ��
     */
    private Thread selfThread = null ;

    /**
     * ���췽������singletonģʽ����
     */
    private DelActionLog ()
    {
        if (selfThread == null)
        {
            //Ϊ�̷߳���ʵ��
            selfThread = new Thread(this) ;
        }
        //�����߳�
        selfThread.start() ;
    }

    /**
     * �����߳�
     */

    public void run ()
    {
        while (true)
        {
            try
            {
                Date date = new Date();
                int hour = date.getHours();

                if ((hour >= 2 ) && (hour < 4))
                {
                    //�õ����
                    int year = date.getYear()+THE_YEAR;
                    //�õ��·�
                    int month = date.getMonth()+1;
                    //�õ�����
                    int day = date.getDate();
                    String time = null;
                    //����6����ǰ������
                    month -= 6;
                    if (month <= 0)
                    {
                        year -= 1;
                        month += THE_MONTH;
                    }
                    //�������յ�ʱ��
                    if(month < 10)
                    {
                        time = year+"0"+month+""+day+"000000";
                    }
                    else
                    {
                        time = year + "" + month + "" + day + "000000" ;
                    }
                    logger.debug("the time is:"+time);
                        //ִ��ɾ������
                    ActionLogDAO.getInstance().delActionlog(time) ;
                    logger.debug("del success!");
                }

            }
            catch (Throwable e)
            {
                logger.error("DelActionLog error", e);
            }
            finally
            {
                try
                {
                   Thread.sleep(THE_KILO * THE_SIXTY_SIXTY) ;
                }
                catch(InterruptedException k)
                {
                    logger.error("InterruptedException:", k);
                }
            }
        }
    }

        /**
         * ��ȡʵ��
         * @return ʵ��
         */
    public static DelActionLog getInstance()
    {
        return instance;
    }

}
