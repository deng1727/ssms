package com.aspire.ponaadmin.web.daemon ;

import java.util.LinkedList;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>异步任务管理器类</p>
 * <p>后台异步任务管理类，可以执行所有需要异步处理的后台任务。
 * 内部有一个任务队列，对外提供一个添加任务的接口，可以添加任务到任务队列；
 * 本身是一个后台线程（实现Runnable接口），检查任务队列，如果有任务就执行，如果没有任务就休眠。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class DaemonTaskRunner implements Runnable
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(DaemonTaskRunner.class);

	/**
	 * singleton模式的实例
	 */
    private static DaemonTaskRunner instance = new DaemonTaskRunner();

    /**
     * 构造方法，由singleton模式调用
     */
    private DaemonTaskRunner ()
    {
    }

	/**
	 * 获取实例
	 * @return 实例
	 */
    public static DaemonTaskRunner getInstance()
    {
        return instance;
    }

    /**
     * 异步任务队列
     */
	protected LinkedList taskList = new LinkedList();

    /**
     * 初始化方法，启动后台异步任务管理器
     */
	public void init()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * 线程执行方法，覆盖Runnable的方法
     * 检查任务队列里面是否有任务，如果有就执行；如果没有就进入休眠状态。
     */
	public void run()
    {
        while(true)
        {
            //从taskList队尾取出任务并执行
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
                        //如果没有任务，就休眠。
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
     * 添加一个异步任务到执行队列
     * 添加后唤醒执行线程
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
