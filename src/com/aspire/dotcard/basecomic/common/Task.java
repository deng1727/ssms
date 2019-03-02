package com.aspire.dotcard.basecomic.common;



import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public abstract class Task implements Runnable
{
    /**
     * ��־����
     */
    private static JLogger LOG = LoggerFactory.getLogger(Task.class) ;
    
    /**
     * ��Ӧ��������������ʵ��
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
              //������Ϻ�Ҫ֪ͨ������
              this.bufferQueue.notifyTaskFinished(this);
          }
    }

    /**
     * ���������ָ������鷽�����������ʵ�֡�
     * @throws Throwable
     */
    public abstract void task() throws Throwable;
}
