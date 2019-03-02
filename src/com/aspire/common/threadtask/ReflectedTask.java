package com.aspire.common.threadtask;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * <p>Title: ���������ࡣ�̳������࣬ͨ������ķ�ʽִ��ĳ�������ĳ��������</p>
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
public class ReflectedTask extends Task
{
    /**
     * ִ�ж����ʵ��
     */
    protected Object executeObj;

    /**
     * ִ�з���������
     */
    protected String executeMethodName;

    /**
     * ִ�з����Ĳ���
     */
    protected Object[] args;

    /**
     * ִ�з����Ĳ���
     */
    protected Class[] argsClass;

    /**
     * ���췽��
     * @param executeObj Object,ִ�ж����ʵ��
     * @param executeMethodName String,ִ�з���������
     * @param args Object[],ִ�з����Ĳ���
     * @param argsClass Class[],ִ�з����Ĳ�����class
     */
    public ReflectedTask (Object executeObj, String executeMethodName,
                          Object[] args, Class[] argsClass)
    {
        if ((args != null)&&(argsClass == null))
        {
            throw new RuntimeException("invalid parameter argsClass!");
        }
        this.executeObj = executeObj ;
        this.args = args ;
        this.executeMethodName = executeMethodName ;
        this.argsClass = argsClass ;
    }

    /**
     * ���������ָ���
     * @throws Throwable
     */
    public void task () throws Throwable
    {
        // ��ȡִ�����class
        Class clazz = this.executeObj.getClass() ;
        // ��ȡִ�з���������class
        Class[] argsClazz = null ;
        if (this.args != null && this.argsClass != null)
        {
            argsClazz = this.argsClass;
        }
        // �ҵ���������
        Method method = clazz.getMethod(this.executeMethodName, argsClazz) ;
        // ���÷���
        try
        {
            method.invoke(this.executeObj, args) ;
        }
        catch (InvocationTargetException ex)
        {
            //���䷽��ִ�д���ʱ���쳣��ջ��Ϣ��cause��ʼ�������õġ�
            if(ex.getCause()!=null)
            {
                throw ex.getCause();
            }
            else
            {
                throw ex;
            }
        }

    }

    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append('[') ;
        buf.append(this.executeObj.getClass().getName()) ;
        buf.append(',') ;
        buf.append(this.executeObj) ;
        buf.append(',') ;
        buf.append(this.executeMethodName) ;
        buf.append(']') ;
        return buf.toString();
    }
}
