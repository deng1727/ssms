package com.aspire.common.threadtask;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * <p>Title: 反射任务类。继承任务类，通过反射的方式执行某个对象的某个方法。</p>
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
     * 执行对象的实例
     */
    protected Object executeObj;

    /**
     * 执行方法的名称
     */
    protected String executeMethodName;

    /**
     * 执行方法的参数
     */
    protected Object[] args;

    /**
     * 执行方法的参数
     */
    protected Class[] argsClass;

    /**
     * 构造方法
     * @param executeObj Object,执行对象的实例
     * @param executeMethodName String,执行方法的名称
     * @param args Object[],执行方法的参数
     * @param argsClass Class[],执行方法的参数的class
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
     * 任务的任务指令方法
     * @throws Throwable
     */
    public void task () throws Throwable
    {
        // 获取执行类的class
        Class clazz = this.executeObj.getClass() ;
        // 获取执行方法参数的class
        Class[] argsClazz = null ;
        if (this.args != null && this.argsClass != null)
        {
            argsClazz = this.argsClass;
        }
        // 找到方法对象
        Method method = clazz.getMethod(this.executeMethodName, argsClazz) ;
        // 调用方法
        try
        {
            method.invoke(this.executeObj, args) ;
        }
        catch (InvocationTargetException ex)
        {
            //反射方法执行错误时，异常堆栈信息从cause开始才是有用的。
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
