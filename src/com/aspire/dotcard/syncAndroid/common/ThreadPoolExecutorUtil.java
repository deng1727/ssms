package com.aspire.dotcard.syncAndroid.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorUtil {
	
	private static ExecutorService executor = null;
	
	static {
		executor = new ThreadPoolExecutor(5, 5, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1000),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
	public static void execute(Runnable command){
		executor.execute(command);
	}

}
