/*
 * @(#)BizStaticFileAppender.java        1.6 05/03/10
 *
 * Copyright (c) 2003-2005 ASPire Technologies, Inc.
 * 6/F,IER BUILDING, SOUTH AREA,SHENZHEN HI-TECH INDUSTRIAL PARK Mail Box:11# 12#.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ASPire Technologies, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Aspire.
 */
package com.aspire.common.log.proxy;


import java.io.*;
import java.util.*;

import org.apache.log4j.*;
import org.apache.log4j.helpers.*;
import org.apache.log4j.spi.*;
import com.aspire.common.log.constants.*;
import com.aspire.common.log.proxy.model.*;
import com.aspire.common.util.*;
import com.aspire.common.config.ServerInfo;
import java.text.*;

/**
 * <p>Title: BizStaticFileAppender</p>
 * <p>Description: it record the biz log and output the statistics of files of biz log
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author YanFeng
 * @version 1.6.5
 * history
 * created at 2005/03/10
 */

public class BizStaticFileAppender
    extends TimeSizeRollingFileAppender  implements ErrorCode
{
    /**
     * record the biz log stastics
     */
    //private static File lastStatFile;
    private boolean newStartup=false;
    //private static FileWriter fw;
    private long nextCheck=System.currentTimeMillis()-1;
    /**
     * the current date for biz log stastics
     */
    //private static Date currStatDate=new Date();
    private static final String NEXT_LINE=System.getProperty("line.separator");
    private final static String FILE_SEP=System.getProperty("file.separator");
    private final static String PUBLISHED_CHECKFILE=".pub";
    private final static String CHECKFILE_PATH="stat";
    /**
     * 日志里业务时间戳的起始位数，从0开始
     */
    private int ts_start;
    /**
     * 为了提高日志效率，只在0点前后指定的时间段内做业务日志跨界检查
     * 以秒为单位
     */
    private int ts_offset;
    /**
     * 标识第一次越过一天
     * 缺省是true,以便系统在前一天停止，第二天再启动时也能执行发布对帐文件
     * 即使重复启动，也不会重复发布
     */
    private boolean first_on_day=true;
    /**
     * 当前的对帐文件
     */
    private File statFile;
    /**
     * 当前的例外日志文件
     */
    private File extFile;
    /**
     * 上回比较的当前时间与检查时间的差
     * 该值在一天内从－到＋递增
     */
//    private long last_ts_trap;
    /**
     * 上一次最后写的日志文件前缀名
     * 只到日期，不包含.n.bak,如Pullbiz.log2005-09-14-23
     */
    private String lastLogFilePrefix;
    /**
     * 上一次最后写的日志文件分块序号
     * 如无，则为0;
     */
    private int lastLogFileSize;
    /**
     * 当天零点的时间,用于比较是否在时间戳检查访问
     */
    private long currDate=getCurrDate();
    /**
     * 昨天零点的时间。用于判断是否要发布对帐文件，只有到了新的一天过了等待期后的第一条日志触发
     */
    private long lastDayTime=getCurrDate();
    /**
     * 对帐文件中的日期格式定义:缺省是'.'yyyy-MM-dd'.999999'
     */
    private String stat_datepattern="'.'yyyy-MM-dd'.999999'";
    /**
       The default constructor does nothing.
     */
    public BizStaticFileAppender()
    {
        super();
        newStartup=true;
        LogLog.debug("New BizStaticFileAppender instance");
    }

    public BizStaticFileAppender(Layout layout,String filename,
        String datePattern)
        throws IOException
    {
        super(layout,filename,datePattern);
        newStartup=true;
        LogLog.debug("New BizStaticFileAppender instance");
    }

    /**
     * 把以前未发布的对帐文件也发布
     */
    private void publishOldCheckFile()
    {
        String statPath=ServerInfo.getAppRootPath()+FILE_SEP+"log"+FILE_SEP
                            +CHECKFILE_PATH+FILE_SEP;
        File path=new File(statPath);

        File[] checkFiles=path.listFiles();
        if (checkFiles==null)
        {//如果没有stat目录，则直接返回
            return;
        }
        HashMap extLogMap=new HashMap();
        HashMap chkLogMap=new HashMap();
        ArrayList keyDate=new ArrayList();
        for(int i=0;i<checkFiles.length;i++)
        {
            File oldChkFile=checkFiles[i];
            String tmpfileName=oldChkFile.getName();
            if (!tmpfileName.startsWith(currFile.getName()))
            {//如果不是本类业务日志文件，跳过不做
                continue;
            }
            if (tmpfileName.endsWith(".bak"))
            {//这是一个未加入对帐文件的例外日志文件，需要加到对应的对帐文件中
                //int index=tmpfileName.indexOf(".log");
                //String fileDate=tmpfileName.substring(index+4,index+4+10);
                //String bizName=tmpfileName.substring(0,index+4);
                int index=fileName.lastIndexOf(FILE_SEP);
                //得到去除路径的文件类型名
                String bizName=fileName.substring(index+1);
                String fileDate=getUnidateFromFileName(tmpfileName,bizName,datePattern);
                extLogMap.put(bizName+fileDate,oldChkFile);
            }
            else if (tmpfileName.endsWith(".999999"))
            {//这是一个未发布的对帐文件，需要发布
                int index=fileName.lastIndexOf(FILE_SEP);
                //得到去除路径的文件类型名
                String bizName=fileName.substring(index+1);
                String fileDate=getUnidateFromFileName(tmpfileName,bizName,stat_datepattern);
                keyDate.add(bizName+fileDate);
                //当前日期
                String strCurrDate = StringUtils.toString(new Date(),"yyyyMMdd");
                if(strCurrDate.compareTo(fileDate) > 0)
                { //是当天以前的对帐文件,特别针对系统重启的情况
                    chkLogMap.put(bizName+fileDate,oldChkFile);
                }
            }
        }
        for(int i=0;i<keyDate.size();i++)
        {//针对发现的每一个未发布的对帐文件
            String logDay=(String)keyDate.get(i);
            if (chkLogMap.containsKey(logDay))
            {//有一个未发布的对帐文件
                File checkFile=(File)chkLogMap.get(logDay);
                try
                {
                    File targetFile = new File(currFile.getParentFile(),
                                               checkFile.getName());
                    FileWriter fw = new FileWriter(checkFile,true);

                    if(extLogMap.containsKey(logDay))
                    { //一个未加入对帐文件的例外日志文件
                        File extFile = (File)extLogMap.get(logDay);
                        //把未加入对帐文件的例外日志文件写入对应的对帐文件
                        fw.write(extFile.getName()+NEXT_LINE);
                        //把未加入对帐文件的例外日志文件拷贝到发布目录
                        File targetExtFile = new File(currFile.getParentFile(),
                                                      extFile.getName());
                        FileUtils.copy(extFile,targetExtFile);
                        //备份标识已加入对帐文件
                        File publishedExtFile = new File(extFile.
                                                         getParentFile(),
                                                         extFile.getName() +
                                                         PUBLISHED_CHECKFILE);
                        extFile.renameTo(publishedExtFile);

                    }
                    fw.write("999999"+NEXT_LINE);
                    fw.flush();
                    fw.close();
                    FileUtils.copy(checkFile,targetFile);
                    //把已发布的对帐文件备份标识
                    File publishedCheckFile = new File(checkFile.
                                                       getParentFile(),
                                                       checkFile.getName() +
                                                       PUBLISHED_CHECKFILE);
                    checkFile.renameTo(publishedCheckFile);
                }
                catch(IOException ex)
                {ex.printStackTrace(); }

            }
        }
        extLogMap.clear();
        extLogMap = null;
        chkLogMap.clear();
        chkLogMap = null;
        keyDate.clear();
        keyDate = null;

    }
    /**
     * 根据当前时间判断是否要进行业务时间戳检查
     * @return boolean
     */
    private boolean inCheckTrap()
    {

        long n = System.currentTimeMillis();
        //System.out.println("curr mill:"+n);
        long check=currDate+ts_offset*1000;
        long ts_trap=n-check;
//        System.out.println("n-check:"+ts_trap);
        if (ts_trap<0)
        {//在检查范围内，需要检查
            return true;
        }
//        System.out.println("lastDayTime:"+lastDayTime);
//        System.out.println("currDate:"+currDate);
//        System.out.println("first_on_day:"+first_on_day);
        if (first_on_day)
        { //第一次越过一天
            //发布前几天的对帐文件
            publishOldCheckFile();
            first_on_day = false;

        }
/*        if (lastDayTime<currDate)
        {//某天过了延迟期后的第一份日志，需要提供对帐文件和例外文件
            if (first_on_day)
            {//第一次越过一天
                first_on_day = false;
                //发布前一天的对帐文件
//                publishCheckFile();

                //删除已经移走的汇总文件
                //statFile.delete();
            }
        }*/
//        last_ts_trap=ts_trap;
        return false;
    }
    /**
     * 判断是否是把当前日志追加到例外的日志文件中
     * @param msg Object
     * @return boolean
     */
    private boolean isLog2Extra(Object msg)
    {
//        System.out.println("ts_start:"+ts_start);
        if (inCheckTrap())
        {
            if(ts_start > 0)
            {//有从配置指定要检查的时间戳
//                System.out.println("msg in isLog2Extra:"+msg);
//                System.out.println("msg type:"+msg.getClass());
                String strMsg = (String)msg.toString();
//                    System.out.println("msg:" + msg);
                String bizTsDate = strMsg.substring(ts_start,ts_start + 8);
//                System.out.println("bizTsDate:" + bizTsDate);
                String currDate = StringUtils.toString(new Date(),"yyyyMMdd");
//                System.out.println("currDate:" + currDate);
                if(currDate.compareTo(bizTsDate) > 0)
                { //当前系统时间比业务日志时间大，即进入了第二天,但日志还是记到前一天
                    //lastDay.append(bizTsDate);
                    return true;
                }
            }
            else if(msg instanceof BizLogContent)
            { //是通过方法调用来比较是否写入上一文件

                long bizTsDate = ((BizLogContent)msg).getTimestamp();
                if(bizTsDate > 0)
                { //有赋值才有比较
                    if(currDate > bizTsDate)
                    { //当前系统时间比业务日志时间大，即进入了第二天
                        //lastDay.append(bizTsDate);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * 获得前一天的补充的日志文件名
     * @param lastDay String
     * @return String
     */
    private String getExtraLogFileName()
    {
        int index=lastLogFilePrefix.lastIndexOf(FILE_SEP);
        //得到去除路径的文件名
        String realName=lastLogFilePrefix.substring(index+1);

        String extraFileName=realName+"."+(lastLogFileSize+1)
            +LogConstants.LOG_BACKUP_SUFFIX;
//    System.out.println("extraFileName:"+extraFileName);
        return extraFileName;
    }
    /**
     * 发现有业务时间戳小于当前系统时间的，把该日志写到一个例外文件中
     * @param event LoggingEvent
     * @return boolean 标记是否写入例外日志
     */
    private boolean log2extraFile(LoggingEvent event)
    {
        //标记是否写入例外日志
        boolean hasLog=false;
        //StringBuffer lastDay=new StringBuffer();
        Object msg=event.getMessage();
        boolean needAppendExtra=isLog2Extra(msg);
        if (needAppendExtra)
        {//在检查时间段，并且业务时间戳小于当前系统时间,写入例外文件
            String statPath=ServerInfo.getAppRootPath()+FILE_SEP+"log"+FILE_SEP
                            +CHECKFILE_PATH+FILE_SEP;
            File path=new File(statPath);
            if (!path.exists())
            {
                path.mkdir();
            }

            String extraFileName=getExtraLogFileName();
            extFile=new File(path,extraFileName);
            //System.out.println("statFile:"+statFile.getAbsolutePath());
            FileWriter exFw;
            try
            {
                if(extFile.exists())
                {
                    exFw = new FileWriter(extFile,true);
                }
                else
                {
                    exFw = new FileWriter(extFile);
                }
                if(msg instanceof String)
                {
                    exFw.write((String)msg+NEXT_LINE);
                    hasLog=true;
                }
                else if(msg instanceof BizLogContent)
                {
                    String content = ((BizLogContent)msg).toString();
                    exFw.write(content+NEXT_LINE);
                    hasLog=true;
                }

                exFw.flush();
                exFw.close();
            }
            catch(IOException ex)
            {ex.printStackTrace();}

        }
        return hasLog;
    }
    private long getCurrDate()
    {
        //取得当天的日期
            Calendar cal2 = Calendar.getInstance();
            cal2.set(Calendar.HOUR_OF_DAY,0);
            cal2.set(Calendar.MINUTE,0);
            cal2.set(Calendar.SECOND,0);
            cal2.set(Calendar.MILLISECOND,0);
            Date currStatDate = cal2.getTime();
            currDate=currStatDate.getTime();
//            System.out.println("set currDate:"+currDate);
//            System.out.println("get lastDayTime:"+lastDayTime);
            if (currDate>lastDayTime)
            {//如果当前的天比上次计算的天要大，说明到了新的一天
                first_on_day=true;
            }
            return currDate;
    }
    /**
     * 当业务时间戳大于当前的天时，写入上个小时的日志
     * @param event LoggingEvent
     */
    protected void subAppend(LoggingEvent event)
    {
        long n = System.currentTimeMillis();
        //boolean log2last=append2LastHour(event.getMessage());
//        System.out.println("log2last:"+log2last);

        //if ((n >= nextCheck)&&!log2last)
        if (n >= nextCheck)
        {//需要日志换名
//            System.out.println("Now rollOverForTime");
            now.setTime(n);
            //记住前一天的零时时间
            lastDayTime=currDate;
            getCurrDate();

            nextCheck = rc.getNextCheckMillis(now);
            try
            {
                rollOverForTime();
            }
            catch(IOException ioe)
            {
                LogLog.error("rollOver() failed.",ioe);
            }
        }
        //time roll first
        if((fileName != null) &&
           ((CountingQuietWriter)qw).getCount() >= maxFileSize)
        {
//            System.out.println("Now rollOverForSize");
            rollOverForSize();
        }
        if (!log2extraFile(event))
        {//没有写入例外日志，才写正常日志，否则会写重
            super.directSubAppend(event);
        }
    }
    /**
     * 根据日志类型名和日期模式，得到具体的文件名中的日期
     * @param pFileName String实际的文件名
     * @param typeName String文件类型名
     * @param pattern String文件名中的日期格式
     * @return String 统一以yyyyMMdd的格式返回
     */
    private String getUnidateFromFileName(String pFileName,String typeName,String pattern)
    {
        //得到按模板定义的文件名，例如pullbiz.log.yyyy-MM-dd.bak
        //System.out.println("pFileName:"+pFileName);
        //System.out.println("typeName:"+typeName);
        //System.out.println("pattern:"+pattern);
        StringBuffer strBuf=new StringBuffer();
        for (int i=0;i<pattern.length();i++)
        {
            char ch=pattern.charAt(i);
            if (ch!='\'')
            {//去除'的占位
                strBuf.append(ch);
            }
        }
        //System.out.println("new pattern:"+strBuf);
        String templateFileName=typeName+strBuf.toString();
        int indYear=templateFileName.indexOf("yyyy");
        String year=pFileName.substring(indYear,indYear+4);
        int indMonth=templateFileName.indexOf("MM");
        String month=pFileName.substring(indMonth,indMonth+2);
        int indDay=templateFileName.indexOf("dd");
        String day=pFileName.substring(indDay,indDay+2);
        return year+month+day;
    }
    /**
     * 当经过设定时段后，日志换名，日志文件名计入汇总文件
     * @param logFileName String the roll file name
     * like biz.log.2005-03-08-14.1.bak
     * @param seq String the roll file 's sequence No
     */
    private void logStastics(String logFileName,String seq)
    {
        //get the start index of .bak
        int rIndex=logFileName.lastIndexOf(LogConstants.LOG_BACKUP_SUFFIX);
        if (seq!=null)
        {//the seq is the sequence number when log roll over size
            rIndex=rIndex-seq.length()-1;
        }
        //get the end index of raw file
        int lIndex=fileName.length();
        String datePart=logFileName.substring(lIndex,rIndex);
        try
        {
            //the date of the biz log file output
            Date rawDate = sdf.parse(datePart);
            Calendar cal=Calendar.getInstance();
            cal.setTime(rawDate);
            cal.set(Calendar.HOUR_OF_DAY,0);
            cal.set(Calendar.MINUTE,0);
            //该日志记录的时间,精确到天
            Date logDate=cal.getTime();
            //String statPattern="'.'yyyy-MM-dd'.999999'";
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(
                    stat_datepattern);
            //get the string of stat log date
                        String statDate=simpleDateFormat.format(logDate);

            int index=fileName.lastIndexOf(FILE_SEP);
            //得到去除路径的文件名
            String realName=fileName.substring(index+1);
            //临时的stat目录放在log下
            String statPath=ServerInfo.getAppRootPath()+FILE_SEP+"log"+FILE_SEP
                            +CHECKFILE_PATH+FILE_SEP;
            File path=new File(statPath);
            if (!path.exists())
            {
                path.mkdir();
            }
            String statFileName=realName+statDate;
            statFile=new File(path,statFileName);
            //System.out.println("statFile:"+statFile.getAbsolutePath());
            FileWriter fw=null;
            if (statFile.exists())
            {
                fw=new FileWriter(statFile,true);
            }
            else
            {
                fw=new FileWriter(statFile);
            }
            //add timestamp---will removed at production
            /*SimpleDateFormat sdfTemp=new SimpleDateFormat("yyyyMMdd HH:mm:ss-SSS");
            Date now=new Date();
            fw.write(sdfTemp.format(now));
            */
            //add timestamp---will removed at production
            int lastFSIndex=logFileName.lastIndexOf(FILE_SEP);
            fw.write(logFileName.substring(lastFSIndex+1)+NEXT_LINE);
            fw.flush();
            fw.close();
        } catch(Exception ex)
        {ex.printStackTrace();}

    }

    /**
     * reload this method to generate the stastics
     * @throws IOException
     */
    public void rollOverForTime()
        throws IOException
    {
        //每回换名时，把上次日志名情空
        lastLogFilePrefix=null;
        //System.out.println("biz rollOverForTime");
        /* Compute filename, but only if datePattern is specified */
        if(datePattern==null)
        {
            errorHandler.error("Missing DatePattern option in rollOver().");
            return;
        }

        String datedFilename=fileName+sdf.format(now);
        LogLog.debug("datedFilename:"+datedFilename);
        // It is too early to roll over because we are still within the
        // bounds of the current interval. Rollover will occur once the
        // next interval is reached.
        if(scheduledFilename.equals(datedFilename))
        {
            return;
        }

        // close current file, and rename it to datedFilename
        this.closeFile();

        File target=new File(scheduledFilename+LogConstants.LOG_BACKUP_SUFFIX);
        if(target.exists())
        {
            target.delete();
        }

        File file=new File(fileName);
        lastLogFileSize = countForSize;
//        System.out.println("countForSize:"+countForSize);
        if ((newStartup)&&(countForSize==0))
        {//first start up,no countForSize remembered,just rename all bak
            newStartup=false;
            boolean isMax=true;
            for(int i=1;i<=maxBackupIndex;i++)
            {//roll for all size-backup files
                String before = fileName + "." + i;
                File files = new File(before);
                String after = scheduledFilename + "." + i +
                               LogConstants.LOG_BACKUP_SUFFIX;
                if(files.exists())
                { //only backup existed one
                    File targets = new File(after);
                    if(targets.exists())
                    {
                        targets.delete();
                    }
                    boolean result = files.renameTo(targets);
                    if(result)
                    {
                        if (isMax)
                        {//只找i最大的那个
                            lastLogFileSize = i;
                            isMax=false;
                        }
                        logStastics(after,String.valueOf(i));
                        LogLog.debug(before + " -> " + after);
                    } else
                    {
                        LogLog.error("Failed to rename [" + before + "] to [" +
                                     after + "].");
                    }
                }
                else
                {
                    break;
                }
            }
        }
        for(int i=1;i<=countForSize;i++)
        {//roll for all size-backup files
            String before=fileName+"."+i;
            File files=new File(before);
            String after=scheduledFilename+"."+i+LogConstants.LOG_BACKUP_SUFFIX;

            File targets=new File(after);
            if(targets.exists())
            {
                targets.delete();
            }
            //only backup existed one
            boolean result = files.renameTo(targets);
            logStastics(after,String.valueOf(i));
            if (result)
            {
                LogLog.debug(before + " -> " + after);
            }
            else
            {
                LogLog.error("Failed to rename [" + before + "] to [" +
                             after + "].");
            }

        }
        //after use,reset to 0
        countForSize=0;
        //rename current writting file is necessary

        boolean result=file.renameTo(target);
        logStastics(target.getAbsolutePath(),null);
        if(result)
        {
            //记录上次日志输出文件前缀
            lastLogFilePrefix = scheduledFilename;
            LogLog.debug(fileName+" -> "+scheduledFilename);

        }
        else
        {
            LogLog.error("Failed to rename ["+fileName+"] to ["+
                scheduledFilename+"].");
        }

        try
        {
            // This will also close the file. This is OK since multiple
            // close operations are safe.
            this.setFile(fileName,false,this.bufferedIO,this.bufferSize);
        }
        catch(IOException e)
        {
            errorHandler.error("setFile("+fileName+", false) call failed.");
        }
        scheduledFilename=datedFilename;
        LogLog.debug("scheduledFilename after roll:"+scheduledFilename);
    }

    public void setTs_start(int ts_start)
    {
        this.ts_start = ts_start;
    }
    public void setTs_offset(int ts_offset)
    {
        this.ts_offset = ts_offset;
    }
    public void setStat_datepattern(String stat_datepattern)
    {
        this.stat_datepattern = stat_datepattern;
    }




}

