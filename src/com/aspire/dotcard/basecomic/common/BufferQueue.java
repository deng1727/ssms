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
	 * 当前任务的数量
	 */
	private volatile int taskCount;

	/**
	 * 本运行器任务队列最大容量。当为0是表示不限制容量
	 */
	private int maxReceivedTask = 1000;
	/**
	 * 任务队列同步锁
	 */
	private Object maxReceivedLockObj = new Object();

	public BufferQueue() {
		// 构造一个线程池  

		threadPool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue(),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public BufferQueue(int corePoolSize) {
		// 构造一个线程池  
		threadPool = new ThreadPoolExecutor(corePoolSize, corePoolSize, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue(),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	
	public void addTask(Task task) {
		
		
		task.setRunner(this);

		if (maxReceivedTask > 0){//判断是否设置了队列最大容量
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

		//while-sleep很消耗CPU的 ,下面这个方法落后啊，所以丢掉。add by aiyan 2012-04-20
		//		synchronized (this.counterLockObj) {
		//			while (!threadPool.isTerminated()) {
		//				try {
		//					LOG.debug("waiting  for all task is over...");
		//					this.counterLockObj.wait(1000);
		//				} catch (InterruptedException e) {
		//					//应该不可能被其它线程打断
		//					if (LOG.isDebugEnabled()) {
		//						LOG.debug("Should not happen!!!" + e.getMessage());
		//					}
		//				}
		//			}
		//		}
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