package com.aspire.common.threadtask;

import java.util.LinkedList;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;


/**
 *
 * <p>Title:任务运行器 </p>
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
     * 日志引用
     */
    private static JLogger LOG = LoggerFactory.getLogger(TaskRunner.class);

    /**
     * 运行器状态定义：运行中
     */
    public static final int STATE_RUNNING = 1;

    /**
     * 运行器状态定义：请求终止中
     */
    public static final int STATE_STOPPING = 2;

    /**
     * 运行器状态定义：停止
     */
    public static final int STATE_STOP = 3;

    /**
     * 运行器的状态
     */
    private volatile int state;

    /**
     * 需要执行的任务队列。
     */
    private LinkedList taskList = new LinkedList();
    
    /**
     * 当前任务的数量
     */
    private volatile int taskCount;

    /**
     * 正在执行的任务数。
     */
    private volatile int runningTaskCount = 0;

    /**
     * runningTaskCount的对象锁
     */
    private Object counterLockObj = new Object();

    /**
     * 内部观察线程，观察任务队列taskList
     */
    private WatchThread watchThread;

    /**
     * 本运行器允许并发执行的最大任务数
     */
    private int maxRunningTask;
    /**
     * 本运行器任务队列最大容量。当为0是表示不限制容量
     */
    private int maxReceivedTask=0;
    /**
     * 任务队列同步锁
     */
    private Object maxReceivedLockObj=new Object();

    /**
     * 内部观察线程启动的观察对象锁
     */
    private Object watchThreadStartLockObj = new Object();

    /**
     * 构造方法
     * @param maxRunningTask int,本运行器允许并发执行的最大任务数
     */
    public TaskRunner(int maxRunningTask)
    {
        if(maxRunningTask<=0)
        {
            throw new RuntimeException("invalid parameter maxRunningTask:"+maxRunningTask);
        }

        // 将状态改为运行中
        this.state = STATE_RUNNING;

        this.maxRunningTask = maxRunningTask;
        this.watchThread = new WatchThread();
        this.watchThread.start();

        //要等待内部观察线程启动了，才会返回，不然可能存在异步的bug
        synchronized(this.watchThreadStartLockObj)
        {
            try
            {
                watchThreadStartLockObj.wait();
            }
            catch (InterruptedException ex)
            {
                //不应该被打断的。
                if(LOG.isDebugEnabled())
                {
                    LOG.debug("should not happen!!!");
                }
                LOG.error(ex);
            }
        }
    }

    /**
     * 构造方法
     * @param maxRunningTask int,本运行器允许并发执行的最大任务数
     * @param maxReceivedTask 任务队列最大容量。0表示不限制容量
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
     * 获取运行器的状态
     * @return int
     */
    public int getState()
    {
        return this.state;
    }

    /**
     * 添加一个任务到任务运行器中。
     * @param task Task,一个任务
     */
    public void addTask(Task task)
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("addTask(" + task + ")") ;
        }

        // 如果state==停止，则直接返回。
        if (this.state == STATE_STOPPING || this.state == STATE_STOP)
        {
            if (LOG.isDebugEnabled())
            {
                LOG.debug("this runner is stop,return and do nothing.") ;
            }
            return;
        }

        if(maxReceivedTask>0)//判断是否设置了队列最大容量
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
            // 通知等待的watchThread，有任务添加进来了。
            this.taskList.notifyAll();
        }
    }

    /**
     * 执行一个任务
     * @param task ,任务实例
     */
    private void runTask(Task task)
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("runTask(" + task + ")") ;
        }

        // 运行任务数+1
        synchronized (this.counterLockObj)
        {
            this.runningTaskCount++;
        }

        task.setRunner(this);

        // 启动任务
        new Thread(task).start();
    }

    /**
     * 通知任务运行器一个任务执行完毕。回调方法，由任务调用。
     * @param task Task,任务实例
     */
    protected void notifyTaskFinished(Task task)
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("notifyTaskFinished(" + task + ")") ;
        }

        // 运行任务数-1
        synchronized (this.counterLockObj)
        {
            this.runningTaskCount-- ;
            this.counterLockObj.notifyAll() ;
            if (LOG.isDebugEnabled())
            {
                LOG.debug("runningTaskCount:" + this.runningTaskCount) ;
            }
        }

        // 释放令牌
        TaskTokenCenter.getInstance().givebackToken(task.getToken());
        //允许继续添加
        if(maxReceivedTask>0)
        {
        	synchronized (maxReceivedLockObj)
			{
        		maxReceivedLockObj.notify();
			}
        }
    }

    /**
     * 请求终止一次操作。请求之后，会等待所有已经执行的任务执行完毕，没有执行的任务全部被取消。
     */
    public void stop()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("stop()");
        }

        this.state = STATE_STOPPING;

        //清空任务列表
        synchronized (this.taskList)
        {
            this.taskCount = 0 ;
            this.taskList.clear();
        }

        //打断watchThread.interrupt
        watchThread.interrupt();

        //等待运行器执行完毕所有运行中的任务
        this.waitToRunningTaskFinished();

        this.state = STATE_STOP;
    }

    /**
     * 等待运行器执行完毕所有已经执行的任务
     */
    private void waitToRunningTaskFinished()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("waitToRunningTaskFinished()");
        }
        //检查当前运行任务数是否为0
        //如果为不为0，继续检查。直到为0才退出。
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
                    //应该不可能被其它线程打断
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
     * 如果任务只有一个的时候，让线程休眠2秒，以确保任务继续运行
     */
    public void waitRunningTask()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("waitRunningTask()");
        }
        //如果任务只有一个的时候，让线程休眠1秒
        synchronized(this.counterLockObj)
        {
        	try
        	{
        		this.counterLockObj.wait(2000);
        	}
        	catch (InterruptedException e)
        	{
        		//应该不可能被其它线程打断
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
     * 等待运行器执行完毕队列中所有的任务
     * 
     * 使用此方法前要让系统休眠一会，避免此方法与唯一task添加时同时执行判断出现差错
     */
    public void waitToFinished()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("waitToFinished()");
        }

        //检查当前运行任务数是否为0，并且运行器中的任务数是否为0，都为0才能退出
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
                    //应该不可能被其它线程打断
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
     * 结束一个运行器
     */
    public void end()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("end()");
        }
        this.state = STATE_STOP ;
        //打断watchThread.interrupt
        watchThread.interrupt();
    }

    /**
     *
     * <p>Title: TaskRunner任务运行器类的内部类，继承Thread，用来监控任务运行器类内部的任务队列</p>
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
         * 监控线程的run方法
         */
        public final void run()
        {
            if (LOG.isDebugEnabled())
            {
                LOG.debug("WatchThread started!") ;
            }

            //通知主类，线程已经启动了
            synchronized(watchThreadStartLockObj)
            {
                watchThreadStartLockObj.notifyAll();
            }

            // 当运行器处于运行状态时，监控线程一直运行着
            while (state == STATE_RUNNING)
            {
                //首先检查是否正在运行的任务数已经达到最大限制
                synchronized (counterLockObj)
                {
                    if(runningTaskCount>=maxRunningTask)
                    {
                        //达到最大限制了，等待有任务运行完毕
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
                            //如果接受到InterruptedException异常，表明时请求终止
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
                    //如果taskList内没有任务，等待
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
                            //如果接受到InterruptedException异常，表明时请求终止
                            if (LOG.isDebugEnabled())
                            {
                                LOG.debug("WatchThread be interrupted to stop![taskList]") ;
                            }
                            break ;
                        }
                    }
                }

                //取出先加入的一个任务
                Task task = null;
                synchronized (taskList)
                {
                    task = ( Task ) taskList.removeFirst();
                    taskCount-- ;
                    if(LOG.isDebugEnabled())
                    {
                        LOG.debug("取到task，taskCount == " + taskCount);
                    }
                }

                //申请令牌toke
                TaskToken token = null;
                while (token == null)
                {
                    TaskTokenCenter tokenCenter = TaskTokenCenter.getInstance() ;
                    token = (TaskToken) tokenCenter.getToken() ;
                    // 如果令牌为null，等待令牌中心
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
                                //如果接受到InterruptedException异常，表明时请求终止
                                if(LOG.isDebugEnabled())
                                {
                                    LOG.debug("WatchThread be interrupted to stop![tokenCenter]");
                                }
                                break ;
                            }
                        }
                    }
                }

                // 把令牌分配给任务
                task.setToken(token);
                // 启动任务
                runTask(task);
            }
            
            if(LOG.isDebugEnabled())
            {
                LOG.debug("watchThread end!");
            }

        }//public final void run()
    }

}
