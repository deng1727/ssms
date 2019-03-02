package com.aspire.ponaadmin.common.ressync;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import java.io.File;
import java.util.ArrayList;

public class RsyncLogThread
  implements Runnable
{
  public static int spaceTime = 30000;

  private static String logFilePath = "D:\\ftp\\rsyncwork\\log\\";

  private static boolean isRun = true;

  private static boolean isStart = false;

  private JLogger log = LoggerFactory.getLogger(RsyncLogThread.class);

  public RsyncLogThread()
  {
    if (this.log.isDebugEnabled())
      this.log.debug("logFilePath = " + logFilePath);
    if (this.log.isDebugEnabled()) {
      this.log.debug("isStart = " + isStart);
    }
    if (!isStart)
    {
      init();
      if (this.log.isDebugEnabled())
        this.log.debug("isStart = " + isStart);
    }
  }

  public void init()
  {
    if (this.log.isDebugEnabled())
      this.log.debug("RsyncLogThread Started... ");
    new Thread(this, "RsyncLogThread").start();
    isStart = true;
  }

  public void run()
  {
    while (isRun)
    {
      try {
        checkLogFile();
      }
      catch (Exception ex1) {
        this.log.error("RsyncLogThread run ex1= " + ex1.getMessage(), ex1);
      }
      try
      {
        Thread.sleep(spaceTime);
      }
      catch (InterruptedException ex)
      {
        this.log.error("RsyncLogThread run ex= " + ex.getMessage(), ex);
      }
    }
    if (this.log.isDebugEnabled())
      this.log.error("@@@###$$$exit RsyncLogThread $$$###@@@");
  }

  private void checkLogFile()
  {
    if (logFilePath == null)
    {
      if (this.log.isDebugEnabled())
        this.log.debug("@@@logFilePath=" + logFilePath);
      if (this.log.isDebugEnabled())
        this.log.info("@@@logFilePath=" + logFilePath);
      return;
    }

    ArrayList list = RsyncLogFile.listLogFile(logFilePath);
    int size = list.size();
    RsyncLogBO logBO = new RsyncLogBO();
    String temp = "";
    for (int i = 0; i < size; i++)
    {
      temp = (String)list.get(i);
      String fileName = logFilePath + File.separator + temp;

      logBO.analysisLog(fileName);

      RsyncLogFile.moveFileToBak(temp, logFilePath);
    }
  }

  public static void setSpaceTime(int sTime)
  {
    if (sTime >= 10000)
    {
      spaceTime = sTime;
    }
  }

  public static void setLogFilePath(String str)
  {
    logFilePath = str;
  }

  public static void main(String[] args)
  {
    RsyncLogThread t = new RsyncLogThread();
  }

  public void setIsRun(boolean isRun)
  {
    isRun = isRun;
  }
}