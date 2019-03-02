package com.aspire.common.log.proxy;

/**
 * <p>Title: Logger</p>
 * <p>Description: the logger interface for usage by xPortal</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire</p>
 * @author YanFeng
 * @version 1.0
 * @created at 28/3/2003
 * revised at 4/6/2003 add a type attribute as the log type
 * 0:running log;1:biz log,running log can also attach mobile ID
 * @CheckItem@ OPT-yanfeng-20030912 add extended class to move biz log level to warn
 * @CheckItem@ OPT-yanfeng-20040313 add method isDebugEnabled(),requested by zhangji
 * @CheckItem@ OPT-yanfeng-20040526 print error to console before log is configured
* @CheckItem@ OPT-yanfeng-20040914 send alert first and then write the log
 * @CheckItem@ OPT-yanfeng-20040921 add the switch for alert
* @CheckItem@ OPT-yanfeng-20041130 add function error(Throwable t)
 */
import com.aspire.common.log.constants.LogConstants;
import com.aspire.common.log.proxy.model.BizLogContent;
import com.aspire.common.util.StringUtils;
import com.aspire.common.log.config.LogConfig;
import org.apache.log4j.Logger;
public class JLogger
{

    private Logger log=null;
    protected int type=0;
    protected JLogger(String logname)
    {
        if (logname.startsWith(LogConstants.BIZ_LOG_TYPE))
        {
            type=1;
        }
        this.log=org.apache.log4j.Logger.getLogger(logname);
    }

    protected JLogger(Class logclazz)
    {
        this.log=org.apache.log4j.Logger.getLogger(logclazz);
    }

    public void debug(Object msg)
    {
        log.debug(msg);
    }

    public void debug(Throwable t)
    {
        log.debug("",t);
    }

    public void debug(Object msg,Throwable t)
    {
        log.debug(msg,t);
    }

    public void info(Object msg)
    {
        log.info(msg);
    }

    public void info(Object msg,Throwable t)
    {
        log.info(msg,t);
    }
    /**
     * log the biz or running log
     * @param msg biz or running content
     * @param mobileID the mobile ID
     */
    public void info(Object msg,String mobileID)
    {
        BizLogContent bizLog=new BizLogContent(msg,mobileID,type);
        log.info(bizLog);
    }

    public void warn(Object msg)
    {
        log.warn(msg);
    }

    public void warn(Object msg,Throwable t)
    {
        log.warn(msg,t);
    }

    public void error(Object msg)
    {
        log.error(msg);
    }
    /**
     * only print the exception and stack trace
     * @param t Throwable
     */
    public void error(Throwable t)
    {
        log.error("",t);
    }


    public void error(Object msg,Throwable t)
    {
        log.error(msg,t);
    }
    /**
     * Record the error log,log the error code and the error message
     * @param errCode the code defined in XXXErorCode and error.properties
     */
    public void error(int errCode)
    {
        error(errCode,"");
    }
    /**
     * Record the error log,log the error code and the configured error message
     * @param errCode the code defined in XXXErorCode and error.properties
     * @param value the variable replace ? in error message in error.properties
     */
    public void error(int errCode,String value)
    {
        error(errCode,value,null);
    }

    /**
     * Record the error log,log the error code ,error message and Exception
     * @param errCode the code defined in XXXErorCode and error.properties
     * @param t the exception caught
     */
    public void error(int errCode,Throwable t)
    {
        error(errCode,"",t);
    }
    /**
     * Record the error log,log the error code ,he configured error message and Exception
     * @param errCode the code defined in XXXErorCode and error.properties
     * @param value the variable replace ? in error message in error.properties
     * @param t the exception caught
     @CheckItem@ selfbug-yanfeng-20031023 call StringUtils.replaceOnlyOnce insteadof iteratable replace
     */
    public void error(int errCode,String value,Throwable t)
   {
       String errDesc = "";
       String errMsg=LoggerFactory.getErrorMsg(errCode);
       if (errMsg==null)
       {//config file cannot read,so don't send alert,display error directly
           log.error(""+errCode,t);
       }
       else
       {
           errDesc = StringUtils.replaceOnlyOnce(errMsg,
                                                 LogConstants.REP_MARK,
                                                 value);
           LogConfig config = LogConfig.getInstance();
           if (config.isAlertAllowed())
           {
               //¡ò¡ò¡ò
               //·¢ËÍ¸æ¾¯
           }
           log.error(errCode + LogConstants.COLON_SEPERATOR + errDesc,t);
       }

   }


    public void fatal(Object msg)
    {
        log.fatal(msg);
    }

    public void fatal(Object msg,Throwable t)
    {
        log.fatal(msg,t);
    }
    public boolean isDebugEnabled()
    /**
     * get the log level to judge the debug is on
     * requested by zhangji
     */
    {

        return log.isDebugEnabled();
    }
    /**
    * get the attached logger of log4j
    * @return the logger
    */
    protected Logger getInternalLog()
    {
        return log;
    }
}
