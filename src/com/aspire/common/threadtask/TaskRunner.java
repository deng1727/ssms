package com.aspire.common.threadtask;

import java.util.LinkedList;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;


/**
 *
 * <p>Title:���������� </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author zhangmin
 * @version 1.2.0.0
 */
public class TaskRunner
{

    /**
     * ��־����
     */
    private static JLogger LOG = LoggerFactory.getLogger(TaskRunner.class);

    /**
     * ������״̬���壺������
     */
    public static final int STATE_RUNNING = 1;

    /**
     * ������״̬���壺������ֹ��
     */
    public static final int STATE_STOPPING = 2;

    /**
     * ������״̬���壺ֹͣ
     */
    public static final int STATE_STOP = 3;

    /**
     * ��������״̬
     */
    private volatile int state;

    /**
     * ��Ҫִ�е�������С�
     */
    private LinkedList taskList = new LinkedList();
    
    /**
     * ��ǰ���������
     */
    private volatile int taskCount;

    /**
     * ����ִ�е���������
     */
    private volatile int runningTaskCount = 0;

    /**
     * runningTaskCount�Ķ�����
     */
    private Object counterLockObj = new Object();

    /**
     * �ڲ��۲��̣߳��۲��������taskList
     */
    private WatchThread watchThread;

    /**
     * ��������������ִ�е����������
     */
    private int maxRunningTask;
    /**
     * ��������������������������Ϊ0�Ǳ�ʾ����������
     */
    private int maxReceivedTask=0;
    /**
     * �������ͬ����
     */
    private Object maxReceivedLockObj=new Object();

    /**
     * �ڲ��۲��߳������Ĺ۲������
     */
    private Object watchThreadStartLockObj = new Object();

    /**
     * ���췽��
     * @param maxRunningTask int,��������������ִ�е����������
     */
    public TaskRunner(int maxRunningTask)
    {
        if(maxRunningTask<=0)
        {
            throw new RuntimeException("invalid parameter maxRunningTask:"+maxRunningTask);
        }

        // ��״̬��Ϊ������
        this.state = STATE_RUNNING;

        this.maxRunningTask = maxRunningTask;
        this.watchThread = new WatchThread();
        this.watchThread.start();

        //Ҫ�ȴ��ڲ��۲��߳������ˣ��Ż᷵�أ���Ȼ���ܴ����첽��bug
        synchronized(this.watchThreadStartLockObj)
        {
            try
            {
                watchThreadStartLockObj.wait();
            }
            catch (InterruptedException ex)
            {
                //��Ӧ�ñ���ϵġ�
                if(LOG.isDebugEnabled())
                {
                    LOG.debug("should not happen!!!");
                }
                LOG.error(ex);
            }
        }
    }

    /**
     * ���췽��
     * @param maxRunningTask int,��������������ִ�е����������
     * @param maxReceivedTask ����������������0��ʾ����������
     */
    public TaskRunner(int maxRunningTask,int maxReceivedTask)
    {
    	this(maxRunningTask);
    	if(maxReceivedTask<0)
    	{
    		maxReceivedTask=0;
    	}
    	this.maxReceivedTask=maxReceivedTask;
    	
    }

    /**
     * ��ȡ��������״̬
     * @return int
     */
    public int getState()
    {
        return this.state;
    }

    /**
     * ���һ�����������������С�
     * @param task Task,һ������
     */
    public void addTask(Task task)
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("addTask(" + task + ")") ;
        }

        // ���state==ֹͣ����ֱ�ӷ��ء�
        if (this.state == STATE_STOPPING || this.state == STATE_STOP)
        {
            if (LOG.isDebugEnabled())
            {
                LOG.debug("this runner is stop,return and do nothing.") ;
            }
            return;
        }

        if(maxReceivedTask>0)//�ж��Ƿ������˶����������
        {
        	synchronized(maxReceivedLockObj)
        	{
        		while(this.taskCount>maxReceivedTask)
        		{
        			try
					{
        				if (LOG.isDebugEnabled())
                        {
                            LOG.debug("MAX maxReceivedTask TASK exceed! maxReceivedLockObj.wait()!") ;
                        }
						maxReceivedLockObj.wait();
					} catch (InterruptedException e)
					{
						LOG.error("maxReceivedLockObj be interrupted to ignore maxReceivedTask parameter");
					}
        		}
        	}
        }
        synchronized (this.taskList)
        {
            this.taskCount++ ;
            this.taskList.addLast(task);
            // ֪ͨ�ȴ���watchThread����������ӽ����ˡ�
            this.taskList.notifyAll();
        }
    }

    /**
     * ִ��һ������
     * @param task ,����ʵ��
     */
    private void runTask(Task task)
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("runTask(" + task + ")") ;
        }

        // ����������+1
        synchronized (this.counterLockObj)
        {
            this.runningTaskCount++;
        }

        task.setRunner(this);

        // ��������
        new Thread(task).start();
    }

    /**
     * ֪ͨ����������һ������ִ����ϡ��ص���������������á�
     * @param task Task,����ʵ��
     */
    protected void notifyTaskFinished(Task task)
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("notifyTaskFinished(" + task + ")") ;
        }

        // ����������-1
        synchronized (this.counterLockObj)
        {
            this.runningTaskCount-- ;
            this.counterLockObj.notifyAll() ;
            if (LOG.isDebugEnabled())
            {
                LOG.debug("runningTaskCount:" + this.runningTaskCount) ;
            }
        }

        // �ͷ�����
        TaskTokenCenter.getInstance().givebackToken(task.getToken());
        //����������
        if(maxReceivedTask>0)
        {
        	synchronized (maxReceivedLockObj)
			{
        		maxReceivedLockObj.notify();
			}
        }
    }

    /**
     * ������ֹһ�β���������֮�󣬻�ȴ������Ѿ�ִ�е�����ִ����ϣ�û��ִ�е�����ȫ����ȡ����
     */
    public void stop()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("stop()");
        }

        this.state = STATE_STOPPING;

        //��������б�
        synchronized (this.taskList)
        {
            this.taskCount = 0 ;
            this.taskList.clear();
        }

        //���watchThread.interrupt
        watchThread.interrupt();

        //�ȴ�������ִ��������������е�����
        this.waitToRunningTaskFinished();

        this.state = STATE_STOP;
    }

    /**
     * �ȴ�������ִ����������Ѿ�ִ�е�����
     */
    private void waitToRunningTaskFinished()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("waitToRunningTaskFinished()");
        }
        //��鵱ǰ�����������Ƿ�Ϊ0
        //���Ϊ��Ϊ0��������顣ֱ��Ϊ0���˳���
        synchronized(this.counterLockObj)
        {
            while (this.runningTaskCount > 0)
            {
                try
                {
                    this.counterLockObj.wait();
                }
                catch (InterruptedException e)
                {
                    //Ӧ�ò����ܱ������̴߳��
                    if(LOG.isDebugEnabled())
                    {
                        LOG.debug("Should not happen!!!"+e.getMessage());
                    }
                }
            }
        }//synchronized(this.counterLockObj)
        if (LOG.isDebugEnabled())
        {
            LOG.debug("All running task is finished!");
        }
    }

    /**
     * �������ֻ��һ����ʱ�����߳�����2�룬��ȷ�������������
     */
    public void waitRunningTask()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("waitRunningTask()");
        }
        //�������ֻ��һ����ʱ�����߳�����1��
        synchronized(this.counterLockObj)
        {
        	try
        	{
        		this.counterLockObj.wait(2000);
        	}
        	catch (InterruptedException e)
        	{
        		//Ӧ�ò����ܱ������̴߳��
        		if(LOG.isDebugEnabled())
        		{
        			LOG.debug("Should not happen!!!"+e.getMessage());
        		}
        	}
        }//synchronized(this.counterLockObj)
        if (LOG.isDebugEnabled())
        {
            LOG.debug("All running task is finished!");
        }
    }
    
    /**
     * �ȴ�������ִ����϶��������е�����
     * 
     * ʹ�ô˷���ǰҪ��ϵͳ����һ�ᣬ����˷�����Ψһtask���ʱͬʱִ���жϳ��ֲ��
     */
    public void waitToFinished()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("waitToFinished()");
        }

        //��鵱ǰ�����������Ƿ�Ϊ0�������������е��������Ƿ�Ϊ0����Ϊ0�����˳�
        synchronized(this.counterLockObj)
        {
            while (this.runningTaskCount > 0 || this.taskCount > 0)
            {
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("runningTaskCount:" + this.runningTaskCount
                              + ",taskCount:" + this.taskCount);
                }
                try
                {
                    this.counterLockObj.wait(1000);
                }
                catch (InterruptedException e)
                {
                    //Ӧ�ò����ܱ������̴߳��
                    if(LOG.isDebugEnabled())
                    {
                        LOG.debug("Should not happen!!!"+e.getMessage());
                    }
                }
            }
        }//synchronized(this.counterLockObj)
        if (LOG.isDebugEnabled())
        {
            LOG.debug("waitToFinished()...TaskRunner is finished!!!");
        }
    }

    /**
     * ����һ��������
     */
    public void end()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("end()");
        }
        this.state = STATE_STOP ;
        //���watchThread.interrupt
        watchThread.interrupt();
    }

    /**
     *
     * <p>Title: TaskRunner��������������ڲ��࣬�̳�Thread����������������������ڲ����������</p>
     *
     * <p>Description: </p>
     *
     * <p>Copyright: Copyright (c) 2006</p>
     *
     * <p>Company: </p>
     *
     * @author zhangmin
     * @version 1.2.0.0
     */
    class WatchThread extends Thread
    {

        /**
         * ����̵߳�run����
         */
        public final void run()
        {
            if (LOG.isDebugEnabled())
            {
                LOG.debug("WatchThread started!") ;
            }

            //֪ͨ���࣬�߳��Ѿ�������
            synchronized(watchThreadStartLockObj)
            {
                watchThreadStartLockObj.notifyAll();
            }

            // ����������������״̬ʱ������߳�һֱ������
            while (state == STATE_RUNNING)
            {
                //���ȼ���Ƿ��������е��������Ѿ��ﵽ�������
                synchronized (counterLockObj)
                {
                    if(runningTaskCount>=maxRunningTask)
                    {
                        //�ﵽ��������ˣ��ȴ��������������
                        try
                        {
                            if (LOG.isDebugEnabled())
                            {
                                LOG.debug("MAX Running TASK exceed! counterLockObj.wait()!") ;
                            }

                            counterLockObj.wait();
                        }
                        catch (InterruptedException e)
                        {
                            //������ܵ�InterruptedException�쳣������ʱ������ֹ
                            if (LOG.isDebugEnabled())
                            {
                                LOG.debug("WatchThread be interrupted to stop![counterLockObj]") ;
                            }
                            break ;
                        }
                    }
                }

                synchronized (taskList)
                {
                    //���taskList��û�����񣬵ȴ�
                    if (taskList.size() == 0)
                    {
                        try
                        {
                            if (LOG.isDebugEnabled())
                            {
                                LOG.debug("no task found! taskList.wait()!") ;
                            }

                            taskList.wait() ;
                        }
                        catch (InterruptedException e)
                        {
                            //������ܵ�InterruptedException�쳣������ʱ������ֹ
                            if (LOG.isDebugEnabled())
                            {
                                LOG.debug("WatchThread be interrupted to stop![taskList]") ;
                            }
                            break ;
                        }
                    }
                }

                //ȡ���ȼ����һ������
                Task task = null;
                synchronized (taskList)
                {
                    task = ( Task ) taskList.removeFirst();
                    taskCount-- ;
                    if(LOG.isDebugEnabled())
                    {
                        LOG.debug("ȡ��task��taskCount == " + taskCount);
                    }
                }

                //��������toke
                TaskToken token = null;
                while (token == null)
                {
                    TaskTokenCenter tokenCenter = TaskTokenCenter.getInstance() ;
                    token = (TaskToken) tokenCenter.getToken() ;
                    // �������Ϊnull���ȴ���������
                    if (token == null)
                    {
                        synchronized (tokenCenter)
                        {
                            try
                            {
                                if (LOG.isDebugEnabled())
                                {
                                    LOG.debug("can't get TaskToken! tokenCenter.wait()!") ;
                                }

                                tokenCenter.wait() ;
                            }
                            catch (InterruptedException e)
                            {
                                //������ܵ�InterruptedException�쳣������ʱ������ֹ
                                if(LOG.isDebugEnabled())
                                {
                                    LOG.debug("WatchThread be interrupted to stop![tokenCenter]");
                                }
                                break ;
                            }
                        }
                    }
                }

                // �����Ʒ��������
                task.setToken(token);
                // ��������
                runTask(task);
            }
            
            if(LOG.isDebugEnabled())
            {
                LOG.debug("watchThread end!");
            }

        }//public final void run()
    }

}
