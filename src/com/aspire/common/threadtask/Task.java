package com.aspire.common.threadtask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 *
 * <p>Title:线程任务类 </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author shidr
 * @version 1.2.0.0
 */
public abstract class Task implements Runnable
{
    /**
     * 日志引用
     */
    private static JLogger LOG = LoggerFactory.getLogger(Task.class) ;

    /**
     * 令牌
     */
    protected TaskToken token;

    /**
     * 对应的任务运行器的实例
     */
    protected TaskRunner runner;

    /**
     * @return Returns the token.
     */
    public TaskToken getToken()
    {
        return token;
    }

    public TaskRunner getRunner ()
    {
        return runner ;
    }

    /**
     * @param token The token to set.
     */
    void setToken(TaskToken token)
    {
        this.token = token;
    }

    void setRunner (TaskRunner runner)
    {
        this.runner = runner ;
    }

    /**
     * 实现Runnable的run方法
     */
    public final void run()
    {
      //只有当运行器是运行状态时，任务才运行
      int runnerState = this.runner.getState();
      if (runnerState == TaskRunner.STATE_RUNNING)
      {
          try
          {
              this.task();
          }
          catch (Throwable t)
          {
              LOG.error("task run failed!",t);

          }
      }
      //运行完毕后，要通知运行器
      this.runner.notifyTaskFinished(this);
    }

    /**
     * 任务的任务指令方法。虚方法，子类必须实现。
     * @throws Throwable
     */
    public abstract void task() throws Throwable;
}
