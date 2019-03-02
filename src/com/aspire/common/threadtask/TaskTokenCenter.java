package com.aspire.common.threadtask;

import java.util.HashSet;
import java.util.LinkedList;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 *
 * <p>Title:令牌中心 </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author x_gaoyuan
 * @version 1.2.0.0
 */
public class TaskTokenCenter
{

    /**
     * 日志引用
     */
    private static JLogger LOG = LoggerFactory.getLogger(TaskTokenCenter.class);

    /**
     * 空闲令牌列表。
     */
    private LinkedList idleTokenList;

    /**
     * 已被占用的令牌列表。使用HashSet有更好的查询，删除性能
     */
    private HashSet busyTokenList;

    /**
     * 最大令牌数
     */
    private static final int MAXSIZE = 1000;

    /**
     * 单例的实例
     */
    private static TaskTokenCenter taskCenter = new TaskTokenCenter();


    /**
     * 单类模式创建TaskTokenCenter的实例
     * @return TaskTokenCenter
     */
    public static TaskTokenCenter getInstance ()
    {
        return taskCenter ;
    }

    /**
     * 构造方法
     */
    private TaskTokenCenter()
    {
    	initTokens();
    }

    /**
     * 初始化方法,只需要初始化一次即可 ---added by zhangwei
     */
    private void initTokens()
    {
        idleTokenList = new LinkedList();
        busyTokenList = new HashSet();
        // 初始化idleTokenList
        for (int i = 0; i <MAXSIZE; i++)
        {
            TaskToken token = new TaskToken();
            idleTokenList.add(token);
        }
    }

    /**
     * 分配一个令牌
     * @return TaskToken
     */
    public TaskToken getToken()
    {
        TaskToken token = null;
        synchronized (idleTokenList)
        {
            if (idleTokenList.size() == 0)
            {
                return null;
            }
            else
            {
                // 从可用的令牌队列中删除一个令牌
                token = (TaskToken)idleTokenList.removeFirst();
            }
        }
        synchronized (busyTokenList)
        {
            // 将已分配令牌放入已用令牌队列中
            busyTokenList.add(token);
        }
        return token;
    }

    /**
     * 归还令牌
     * @param token TaskToken
     */
    public void givebackToken(TaskToken token)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("givebackToken(" + token + ")") ;
        }
        synchronized (busyTokenList)
        {
            // 从已用令牌队列中删除一个令牌
            busyTokenList.remove(token);
        }
        synchronized (idleTokenList)
        {
            // 将释放的令牌放到可用令牌队列中
            idleTokenList.addFirst(token);
        }
        synchronized (this)
        {
            // 唤醒等待令牌的线程
            this.notify();//只需要唤醒一个线程即可，没有必要唤醒所有的线程
        }
    }
    /**
     * test
     */
    public void getTokenCount()
    {
    	int a=idleTokenList.size();
    	int b=busyTokenList.size();
    	System.out.println("#########################空闲令牌"+a+".忙令牌"+b+",共"+(a+b));
    }

}
