package com.aspire.common.log.proxy;

/**
 * <p>Title: LoggerFactory</p>
 * <p>Description: the factory to construct different logger</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire</p>
 * @author YanFeng
 * @version 1.0
 * created at 31/3/2003
 * history:
 * revised at 12/6/2003 by yanfeng add uniform file seperator
 * @CheckItem@ OPT-yanfeng-20030912 add extended class to move biz log level to warn
 * @CheckItem@ SELFBUG-yanfeng-20031031 add biz logger decision in getLogger(String)
 */
import java.util.HashMap;
import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;
import com.aspire.common.log.proxy.config.ErrInfoInitiator;
import com.aspire.common.log.constants.LogConstants;
import java.io.*;
public class LoggerFactory
{

    private static HashMap bizLoggerMap=new HashMap(10);
    private static ErrInfoInitiator errInfo;

    public LoggerFactory()
    {

    }

    public static JLogger getLogger(String className)
    /**
       get the logger trace the running status
     */
    {
        JLogger runLog=null;
        if (bizLoggerMap.containsKey(className))
        {//if has stored return reference
            return (JLogger)bizLoggerMap.get(className);
        }
        //else new one and store it
        if (className.startsWith(LogConstants.BIZ_LOG_TYPE))
        {//biz logger
            runLog=new JBLogger(className);
        }
        else
        {//non-biz logger
            runLog = new JLogger(className);
        }
        bizLoggerMap.put(className,runLog);
        return runLog;
    }

    public static JLogger getLogger(Class className)
    /**
         get the logger trace the running status
     */
    {

        JLogger runLog=new JCLogger(className);
        return runLog;
    }


    public static void configLog(String file,String refresh)
    {
        try
        {
            if((file==null)||file.equals(""))
            {//no config file found
                System.err.println("log4j.xml not set");
                return;
            }
            String configFile = file.replace('/', LogConstants.FILE_SEP.charAt(0));

            System.err.println("Config file:"+configFile);

            File confFile=new File(configFile);
            if (!confFile.exists())
            {
                System.err.println("log4j.xml not found");
                return;
            }
            System.err.println("refresh time:"+refresh);
            if((refresh==null)||refresh.equals(""))
            {//no refresh provided
                if(configFile.endsWith(".properties"))
                {
                    PropertyConfigurator.configure(configFile);
                }
                else if(configFile.endsWith(".xml"))
                {
                    DOMConfigurator.configure(configFile);
                }
            }
            else
            {//refresh needed
                long period=Long.parseLong(refresh);
                if(configFile.endsWith(".properties"))
                {
                    PropertyConfigurator.configureAndWatch(configFile,period*1000);

                }
                else if(configFile.endsWith(".xml"))
                {

                    DOMConfigurator.configureAndWatch(configFile,period*1000);

                }
            }
        }

        catch(NumberFormatException e)
        {
            DOMConfigurator.configureAndWatch(file);
            e.printStackTrace();
            System.err.println("refresh period is not long type:"+refresh);
        }
        System.err.println("Config log successfully");
    }

    public static void configLog(String file)
    {
        if((file==null)||file.equals(""))
        {//no config file found
            System.err.println("log4j.xml not set");
            return;
        }
        if(file.endsWith(".properties"))
        {
            PropertyConfigurator.configure(file);

        }
        else if(file.endsWith(".xml"))
        {

            DOMConfigurator.configure(file);

        }
        System.err.println("load log config successfully");
    }

    public static void configLog(InputStream is)
    {
        if(is==null)
        {//no config file found
            System.err.println("log4j.xml not set");
            return;
        }
        new DOMConfigurator().doConfigure(is,LogManager.getLoggerRepository());
        System.err.println("load log config successfully");
    }
    /**
     * output log to console before read log4j.xml as default log
     */
    public static void configLog()
    {
        BasicConfigurator.configure();
        System.err.println("config default log successfully");
    }

    public static void closeAllLog(JLogger log)
    {
        Logger logger=log.getInternalLog();
        logger.getLoggerRepository().setThreshold(Level.OFF);
    }

    public static void releaseAllLog()
    {
        LogManager.shutdown();
    }

    /**
     * load the error.properties file to get the error message
     * @param file the file path
     */
    public static void loadErrorInfo(String file)
    {
        if((file==null)||file.equals(""))
        {//no config file found
            System.err.println("error.properties not set");
            return;
        }
        String configFile = file.replace('/', LogConstants.FILE_SEP.charAt(0));

        System.err.println("Error Msg File:"+configFile);

        File confFile=new File(configFile);
        if(!confFile.exists())
        {
            System.err.println("error.properties not found");
            return;
        }

        errInfo=new ErrInfoInitiator();
        errInfo.loadErrorInfo(configFile);
        System.err.println("load error info successfully");

    }
    public static void loadErrorInfo(InputStream is)
    {
        if(is==null)
        {//no config file found
            System.err.println("error.properties not set");
            return;
        }
        errInfo=new ErrInfoInitiator();
        errInfo.loadErrorInfo(is);
        System.err.println("load error info successfully");
    }

    /**
     * get the error message according to the error code
     * @param errorCode predefined code
     * @return error message
     */
    static String getErrorMsg(int errorCode)
    {
        if (errInfo==null)
        {
            System.err.println("error message not initialized for code:"+errorCode);
            return null;
        }
        return errInfo.getErrMsg(errorCode);
    }
}
