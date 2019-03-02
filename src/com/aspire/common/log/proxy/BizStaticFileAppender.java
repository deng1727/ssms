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
     * ��־��ҵ��ʱ�������ʼλ������0��ʼ
     */
    private int ts_start;
    /**
     * Ϊ�������־Ч�ʣ�ֻ��0��ǰ��ָ����ʱ�������ҵ����־�����
     * ����Ϊ��λ
     */
    private int ts_offset;
    /**
     * ��ʶ��һ��Խ��һ��
     * ȱʡ��true,�Ա�ϵͳ��ǰһ��ֹͣ���ڶ���������ʱҲ��ִ�з��������ļ�
     * ��ʹ�ظ�������Ҳ�����ظ�����
     */
    private boolean first_on_day=true;
    /**
     * ��ǰ�Ķ����ļ�
     */
    private File statFile;
    /**
     * ��ǰ��������־�ļ�
     */
    private File extFile;
    /**
     * �ϻرȽϵĵ�ǰʱ������ʱ��Ĳ�
     * ��ֵ��һ���ڴӣ���������
     */
//    private long last_ts_trap;
    /**
     * ��һ�����д����־�ļ�ǰ׺��
     * ֻ�����ڣ�������.n.bak,��Pullbiz.log2005-09-14-23
     */
    private String lastLogFilePrefix;
    /**
     * ��һ�����д����־�ļ��ֿ����
     * ���ޣ���Ϊ0;
     */
    private int lastLogFileSize;
    /**
     * ��������ʱ��,���ڱȽ��Ƿ���ʱ���������
     */
    private long currDate=getCurrDate();
    /**
     * ��������ʱ�䡣�����ж��Ƿ�Ҫ���������ļ���ֻ�е����µ�һ����˵ȴ��ں�ĵ�һ����־����
     */
    private long lastDayTime=getCurrDate();
    /**
     * �����ļ��е����ڸ�ʽ����:ȱʡ��'.'yyyy-MM-dd'.999999'
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
     * ����ǰδ�����Ķ����ļ�Ҳ����
     */
    private void publishOldCheckFile()
    {
        String statPath=ServerInfo.getAppRootPath()+FILE_SEP+"log"+FILE_SEP
                            +CHECKFILE_PATH+FILE_SEP;
        File path=new File(statPath);

        File[] checkFiles=path.listFiles();
        if (checkFiles==null)
        {//���û��statĿ¼����ֱ�ӷ���
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
            {//������Ǳ���ҵ����־�ļ�����������
                continue;
            }
            if (tmpfileName.endsWith(".bak"))
            {//����һ��δ��������ļ���������־�ļ�����Ҫ�ӵ���Ӧ�Ķ����ļ���
                //int index=tmpfileName.indexOf(".log");
                //String fileDate=tmpfileName.substring(index+4,index+4+10);
                //String bizName=tmpfileName.substring(0,index+4);
                int index=fileName.lastIndexOf(FILE_SEP);
                //�õ�ȥ��·�����ļ�������
                String bizName=fileName.substring(index+1);
                String fileDate=getUnidateFromFileName(tmpfileName,bizName,datePattern);
                extLogMap.put(bizName+fileDate,oldChkFile);
            }
            else if (tmpfileName.endsWith(".999999"))
            {//����һ��δ�����Ķ����ļ�����Ҫ����
                int index=fileName.lastIndexOf(FILE_SEP);
                //�õ�ȥ��·�����ļ�������
                String bizName=fileName.substring(index+1);
                String fileDate=getUnidateFromFileName(tmpfileName,bizName,stat_datepattern);
                keyDate.add(bizName+fileDate);
                //��ǰ����
                String strCurrDate = StringUtils.toString(new Date(),"yyyyMMdd");
                if(strCurrDate.compareTo(fileDate) > 0)
                { //�ǵ�����ǰ�Ķ����ļ�,�ر����ϵͳ���������
                    chkLogMap.put(bizName+fileDate,oldChkFile);
                }
            }
        }
        for(int i=0;i<keyDate.size();i++)
        {//��Է��ֵ�ÿһ��δ�����Ķ����ļ�
            String logDay=(String)keyDate.get(i);
            if (chkLogMap.containsKey(logDay))
            {//��һ��δ�����Ķ����ļ�
                File checkFile=(File)chkLogMap.get(logDay);
                try
                {
                    File targetFile = new File(currFile.getParentFile(),
                                               checkFile.getName());
                    FileWriter fw = new FileWriter(checkFile,true);

                    if(extLogMap.containsKey(logDay))
                    { //һ��δ��������ļ���������־�ļ�
                        File extFile = (File)extLogMap.get(logDay);
                        //��δ��������ļ���������־�ļ�д���Ӧ�Ķ����ļ�
                        fw.write(extFile.getName()+NEXT_LINE);
                        //��δ��������ļ���������־�ļ�����������Ŀ¼
                        File targetExtFile = new File(currFile.getParentFile(),
                                                      extFile.getName());
                        FileUtils.copy(extFile,targetExtFile);
                        //���ݱ�ʶ�Ѽ�������ļ�
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
                    //���ѷ����Ķ����ļ����ݱ�ʶ
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
     * ���ݵ�ǰʱ���ж��Ƿ�Ҫ����ҵ��ʱ������
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
        {//�ڼ�鷶Χ�ڣ���Ҫ���
            return true;
        }
//        System.out.println("lastDayTime:"+lastDayTime);
//        System.out.println("currDate:"+currDate);
//        System.out.println("first_on_day:"+first_on_day);
        if (first_on_day)
        { //��һ��Խ��һ��
            //����ǰ����Ķ����ļ�
            publishOldCheckFile();
            first_on_day = false;

        }
/*        if (lastDayTime<currDate)
        {//ĳ������ӳ��ں�ĵ�һ����־����Ҫ�ṩ�����ļ��������ļ�
            if (first_on_day)
            {//��һ��Խ��һ��
                first_on_day = false;
                //����ǰһ��Ķ����ļ�
//                publishCheckFile();

                //ɾ���Ѿ����ߵĻ����ļ�
                //statFile.delete();
            }
        }*/
//        last_ts_trap=ts_trap;
        return false;
    }
    /**
     * �ж��Ƿ��ǰѵ�ǰ��־׷�ӵ��������־�ļ���
     * @param msg Object
     * @return boolean
     */
    private boolean isLog2Extra(Object msg)
    {
//        System.out.println("ts_start:"+ts_start);
        if (inCheckTrap())
        {
            if(ts_start > 0)
            {//�д�����ָ��Ҫ����ʱ���
//                System.out.println("msg in isLog2Extra:"+msg);
//                System.out.println("msg type:"+msg.getClass());
                String strMsg = (String)msg.toString();
//                    System.out.println("msg:" + msg);
                String bizTsDate = strMsg.substring(ts_start,ts_start + 8);
//                System.out.println("bizTsDate:" + bizTsDate);
                String currDate = StringUtils.toString(new Date(),"yyyyMMdd");
//                System.out.println("currDate:" + currDate);
                if(currDate.compareTo(bizTsDate) > 0)
                { //��ǰϵͳʱ���ҵ����־ʱ��󣬼������˵ڶ���,����־���Ǽǵ�ǰһ��
                    //lastDay.append(bizTsDate);
                    return true;
                }
            }
            else if(msg instanceof BizLogContent)
            { //��ͨ�������������Ƚ��Ƿ�д����һ�ļ�

                long bizTsDate = ((BizLogContent)msg).getTimestamp();
                if(bizTsDate > 0)
                { //�и�ֵ���бȽ�
                    if(currDate > bizTsDate)
                    { //��ǰϵͳʱ���ҵ����־ʱ��󣬼������˵ڶ���
                        //lastDay.append(bizTsDate);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * ���ǰһ��Ĳ������־�ļ���
     * @param lastDay String
     * @return String
     */
    private String getExtraLogFileName()
    {
        int index=lastLogFilePrefix.lastIndexOf(FILE_SEP);
        //�õ�ȥ��·�����ļ���
        String realName=lastLogFilePrefix.substring(index+1);

        String extraFileName=realName+"."+(lastLogFileSize+1)
            +LogConstants.LOG_BACKUP_SUFFIX;
//    System.out.println("extraFileName:"+extraFileName);
        return extraFileName;
    }
    /**
     * ������ҵ��ʱ���С�ڵ�ǰϵͳʱ��ģ��Ѹ���־д��һ�������ļ���
     * @param event LoggingEvent
     * @return boolean ����Ƿ�д��������־
     */
    private boolean log2extraFile(LoggingEvent event)
    {
        //����Ƿ�д��������־
        boolean hasLog=false;
        //StringBuffer lastDay=new StringBuffer();
        Object msg=event.getMessage();
        boolean needAppendExtra=isLog2Extra(msg);
        if (needAppendExtra)
        {//�ڼ��ʱ��Σ�����ҵ��ʱ���С�ڵ�ǰϵͳʱ��,д�������ļ�
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
        //ȡ�õ��������
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
            {//�����ǰ������ϴμ������Ҫ��˵�������µ�һ��
                first_on_day=true;
            }
            return currDate;
    }
    /**
     * ��ҵ��ʱ������ڵ�ǰ����ʱ��д���ϸ�Сʱ����־
     * @param event LoggingEvent
     */
    protected void subAppend(LoggingEvent event)
    {
        long n = System.currentTimeMillis();
        //boolean log2last=append2LastHour(event.getMessage());
//        System.out.println("log2last:"+log2last);

        //if ((n >= nextCheck)&&!log2last)
        if (n >= nextCheck)
        {//��Ҫ��־����
//            System.out.println("Now rollOverForTime");
            now.setTime(n);
            //��סǰһ�����ʱʱ��
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
        {//û��д��������־����д������־�������д��
            super.directSubAppend(event);
        }
    }
    /**
     * ������־������������ģʽ���õ�������ļ����е�����
     * @param pFileName Stringʵ�ʵ��ļ���
     * @param typeName String�ļ�������
     * @param pattern String�ļ����е����ڸ�ʽ
     * @return String ͳһ��yyyyMMdd�ĸ�ʽ����
     */
    private String getUnidateFromFileName(String pFileName,String typeName,String pattern)
    {
        //�õ���ģ�嶨����ļ���������pullbiz.log.yyyy-MM-dd.bak
        //System.out.println("pFileName:"+pFileName);
        //System.out.println("typeName:"+typeName);
        //System.out.println("pattern:"+pattern);
        StringBuffer strBuf=new StringBuffer();
        for (int i=0;i<pattern.length();i++)
        {
            char ch=pattern.charAt(i);
            if (ch!='\'')
            {//ȥ��'��ռλ
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
     * �������趨ʱ�κ���־��������־�ļ�����������ļ�
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
            //����־��¼��ʱ��,��ȷ����
            Date logDate=cal.getTime();
            //String statPattern="'.'yyyy-MM-dd'.999999'";
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(
                    stat_datepattern);
            //get the string of stat log date
                        String statDate=simpleDateFormat.format(logDate);

            int index=fileName.lastIndexOf(FILE_SEP);
            //�õ�ȥ��·�����ļ���
            String realName=fileName.substring(index+1);
            //��ʱ��statĿ¼����log��
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
        //ÿ�ػ���ʱ�����ϴ���־�����
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
                        {//ֻ��i�����Ǹ�
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
            //��¼�ϴ���־����ļ�ǰ׺
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

