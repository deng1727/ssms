package com.aspire.ponaadmin.common.ressync;

import java.io.File;
import java.util.ArrayList;

public class RsyncLogFile
{
  public static final String FILE_NAME_PREFIX = "server";
  public static final String DIR_BAK_NAME = "bak";

  public static ArrayList listLogFile(String path)
  {
    ArrayList logFiles = new ArrayList();
    File filePath = new File(path);
    if (filePath.isDirectory())
    {
      String[] files = filePath.list();
      if (files != null)
      {
        for (int i = 0; i < files.length; i++)
        {
          if (files[i].startsWith("server"))
          {
            logFiles.add(files[i]);
          }
        }
      }
    }

    return logFiles;
  }

  public static void moveFileToBak(String fileName, String path)
  {
    String fileStr = path + File.separator + fileName;
    File destDir = mkDirPath(path);

    File file = new File(fileStr);

    File fileDest = new File(destDir, fileName);
    file.renameTo(fileDest);
  }

  private static File mkDirPath(String path)
  {
    File filePath = new File(path + File.separator + "bak");
    if (!filePath.exists())
    {
      filePath.mkdirs();
    }
    return filePath;
  }

  public static void main(String[] args)
  {
    String path = "D:\\ftp\\rsyncwork\\log\\";
    String fileName = "error.log";
    listLogFile(path);
    moveFileToBak(fileName, path);
  }
}