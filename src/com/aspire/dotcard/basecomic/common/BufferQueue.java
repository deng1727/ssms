package com.aspire.dotcard.basecomic.common;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

import edu.emory.mathcs.backport.java.util.concurrent.ArrayBlockingQueue;
import edu.emory.mathcs.backport.java.util.concurrent.LinkedBlockingQueue;
import edu.emory.mathcs.backport.java.util.concurrent.ThreadPoolExecutor;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class BufferQueue {

	private static JLogger LOG = LoggerFactory.getLogger(BufferQueue.class);
	ThreadPoolExecutor threadPool = null;
	//	private int corePoolSize = 5;
	//	private int maximumPoolSize = 10;
	//	private long keepAliveTime = 300;
	//	
//	private int maximumPoolSizes = 10;
//
//	private Byte[] counterLockObj = new Byte[0];
	
	/**
	 * ��ǰ���������
	 */
	private volatile int taskCount;

	/**
	 * ��������������������������Ϊ0�Ǳ�ʾ����������
	 */
	private int maxReceivedTask = 1000;
	/**
	 * �������ͬ����
	 */
	private Object maxReceivedLockObj = new Object();

	public BufferQueue() {
		// ����һ���̳߳�  

		threadPool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue(),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public BufferQueue(int corePoolSize) {
		// ����һ���̳߳�  
		threadPool = new ThreadPoolExecutor(corePoolSize, corePoolSize, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue(),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	
	public void addTask(Task task) {
		
		
		task.setRunner(this);

		if (maxReceivedTask > 0){//�ж��Ƿ������˶����������
			synchronized (maxReceivedLockObj) {
				while (this.taskCount > maxReceivedTask) {
					try {
						if (LOG.isDebugEnabled()) {
							LOG.debug("MAX maxReceivedTask TASK exceed! maxReceivedLockObj.wait()!");
						}
						LOG.debug("wait.....hehe....");
						//System.out.println("wait.....hehe....");
						maxReceivedLockObj.wait();
					} catch (InterruptedException e) {
						LOG.error("maxReceivedLockObj be interrupted to ignore maxReceivedTask parameter");
					}
				}
			}
		}
		
		
        synchronized (this){
            this.taskCount++ ;
        }
        
        LOG.debug("comic:taskCount"+taskCount);

        //System.out.println("comic:taskCount"+taskCount);
		threadPool.execute(task);

	}

	public void destory() {
		threadPool.shutdown();
	}

	public void isTerminated() {
		threadPool.isTerminated();
	}

	public void waitToFinished() {
		try {
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//while-sleep������CPU�� ,�������������󰡣����Զ�����add by aiyan 2012-04-20
		//		synchronized (this.counterLockObj) {
		//			while (!threadPool.isTerminated()) {
		//				try {
		//					LOG.debug("waiting  for all task is over...");
		//					this.counterLockObj.wait(1000);
		//				} catch (InterruptedException e) {
		//					//Ӧ�ò����ܱ������̴߳��
		//					if (LOG.isDebugEnabled()) {
		//						LOG.debug("Should not happen!!!" + e.getMessage());
		//					}
		//				}
		//			}
		//		}
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
        synchronized (this)
        {
            this.taskCount-- ;
            if (LOG.isDebugEnabled())
            {
                LOG.debug("runningTaskCount:" + this.taskCount) ;
            }
        }

    	synchronized (maxReceivedLockObj){
    		maxReceivedLockObj.notify();
		}
    }

}