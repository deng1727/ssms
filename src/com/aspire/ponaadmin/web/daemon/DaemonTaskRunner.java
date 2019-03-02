package com.aspire.ponaadmin.web.daemon ;

import java.util.LinkedList;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>�첽�����������</p>
 * <p>��̨�첽��������࣬����ִ��������Ҫ�첽����ĺ�̨����
 * �ڲ���һ��������У������ṩһ���������Ľӿڣ������������������У�
 * ������һ����̨�̣߳�ʵ��Runnable�ӿڣ������������У�����������ִ�У����û����������ߡ�</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class DaemonTaskRunner implements Runnable
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(DaemonTaskRunner.class);

	/**
	 * singletonģʽ��ʵ��
	 */
    private static DaemonTaskRunner instance = new DaemonTaskRunner();

    /**
     * ���췽������singletonģʽ����
     */
    private DaemonTaskRunner ()
    {
    }

	/**
	 * ��ȡʵ��
	 * @return ʵ��
	 */
    public static DaemonTaskRunner getInstance()
    {
        return instance;
    }

    /**
     * �첽�������
     */
	protected LinkedList taskList = new LinkedList();

    /**
     * ��ʼ��������������̨�첽���������
     */
	public void init()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * �߳�ִ�з���������Runnable�ķ���
     * ���������������Ƿ�����������о�ִ�У����û�оͽ�������״̬��
     */
	public void run()
    {
        while(true)
        {
            //��taskList��βȡ������ִ��
            try
            {
                DaemonTask task = null;
                synchronized(this.taskList)
                {
                    if(this.taskList.size() != 0)
                    {
                        task = (DaemonTask) this.taskList.removeLast();
                    }
                    else
                    {
                        //���û�����񣬾����ߡ�
                        this.taskList.wait();
                    }
                }
                if(task != null)
                {
                    task.execute();
                }
            }
            catch(Exception e)
            {
                logger.error("get and run task failed.", e);
            }
        }
    }

    /**
     * ���һ���첽����ִ�ж���
     * ��Ӻ���ִ���߳�
     * @param task DaemonTask
     */
	public void addTask(DaemonTask task)
    {
        synchronized(this.taskList)
        {
            this.taskList.addFirst(task);
            this.taskList.notifyAll();
        }
    }
}
