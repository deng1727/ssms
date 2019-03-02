package com.aspire.dotcard.basecomic.common;



import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public abstract class Task implements Runnable
{
    /**
     * 日志引用
     */
    private static JLogger LOG = LoggerFactory.getLogger(Task.class) ;
    
    /**
     * 对应的任务运行器的实例
     */
    protected BufferQueue bufferQueue;


    public void setRunner(BufferQueue bufferQueue) {
		this.bufferQueue = bufferQueue;
	}

	public void run()
    {
          try
          {
              this.task();
          }
          catch (Throwable t)
          {
              LOG.error("task run failed!",t);

          }
          finally{
              //运行完毕后，要通知运行器
              this.bufferQueue.notifyTaskFinished(this);
          }
    }

    /**
     * 任务的任务指令方法。虚方法，子类必须实现。
     * @throws Throwable
     */
    public abstract void task() throws Throwable;
}
