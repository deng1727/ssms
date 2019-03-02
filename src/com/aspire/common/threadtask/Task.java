package com.aspire.common.threadtask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 *
 * <p>Title:�߳������� </p>
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
     * ��־����
     */
    private static JLogger LOG = LoggerFactory.getLogger(Task.class) ;

    /**
     * ����
     */
    protected TaskToken token;

    /**
     * ��Ӧ��������������ʵ��
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
     * ʵ��Runnable��run����
     */
    public final void run()
    {
      //ֻ�е�������������״̬ʱ�����������
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
      //������Ϻ�Ҫ֪ͨ������
      this.runner.notifyTaskFinished(this);
    }

    /**
     * ���������ָ������鷽�����������ʵ�֡�
     * @throws Throwable
     */
    public abstract void task() throws Throwable;
}
