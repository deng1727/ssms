package com.aspire.common.threadtask;

import java.util.HashSet;
import java.util.LinkedList;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 *
 * <p>Title:�������� </p>
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
     * ��־����
     */
    private static JLogger LOG = LoggerFactory.getLogger(TaskTokenCenter.class);

    /**
     * ���������б�
     */
    private LinkedList idleTokenList;

    /**
     * �ѱ�ռ�õ������б�ʹ��HashSet�и��õĲ�ѯ��ɾ������
     */
    private HashSet busyTokenList;

    /**
     * ���������
     */
    private static final int MAXSIZE = 1000;

    /**
     * ������ʵ��
     */
    private static TaskTokenCenter taskCenter = new TaskTokenCenter();


    /**
     * ����ģʽ����TaskTokenCenter��ʵ��
     * @return TaskTokenCenter
     */
    public static TaskTokenCenter getInstance ()
    {
        return taskCenter ;
    }

    /**
     * ���췽��
     */
    private TaskTokenCenter()
    {
    	initTokens();
    }

    /**
     * ��ʼ������,ֻ��Ҫ��ʼ��һ�μ��� ---added by zhangwei
     */
    private void initTokens()
    {
        idleTokenList = new LinkedList();
        busyTokenList = new HashSet();
        // ��ʼ��idleTokenList
        for (int i = 0; i <MAXSIZE; i++)
        {
            TaskToken token = new TaskToken();
            idleTokenList.add(token);
        }
    }

    /**
     * ����һ������
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
                // �ӿ��õ����ƶ�����ɾ��һ������
                token = (TaskToken)idleTokenList.removeFirst();
            }
        }
        synchronized (busyTokenList)
        {
            // ���ѷ������Ʒ����������ƶ�����
            busyTokenList.add(token);
        }
        return token;
    }

    /**
     * �黹����
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
            // ���������ƶ�����ɾ��һ������
            busyTokenList.remove(token);
        }
        synchronized (idleTokenList)
        {
            // ���ͷŵ����Ʒŵ��������ƶ�����
            idleTokenList.addFirst(token);
        }
        synchronized (this)
        {
            // ���ѵȴ����Ƶ��߳�
            this.notify();//ֻ��Ҫ����һ���̼߳��ɣ�û�б�Ҫ�������е��߳�
        }
    }
    /**
     * test
     */
    public void getTokenCount()
    {
    	int a=idleTokenList.size();
    	int b=busyTokenList.size();
    	System.out.println("#########################��������"+a+".æ����"+b+",��"+(a+b));
    }

}
