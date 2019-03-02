package com.aspire.dotcard.syncAndroid.cssp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.Task;
import com.aspire.common.threadtask.TaskRunner;

public class CSSPConsumerRunnable implements Runnable
{
    private static JLogger LOG = LoggerFactory.getLogger(CSSPConsumerRunnable.class);

    protected String localPath;
    protected String filePrefix;
    protected IBuilder builder;

    public CSSPConsumerRunnable(String filePrefix, IBuilder builder, String localPath)
    {
        this.localPath = localPath;
        this.filePrefix = filePrefix;
        this.builder = builder;
    }

    public void run()
    {
        try
        {
            LOG.info("CSSP的文件下载完了，开始处理本地文件了。。。");

            // clearNoTodayFile(localPath,day);
            String[] localFiles = getLocalName();
            TaskRunner runner = new TaskRunner(10);
            for (String fileName : localFiles)
            {
                try
                {
                    BufferedReader br = new BufferedReader(new FileReader(localPath + "/" + fileName));
                    String line = "";
                    int index = fileName.lastIndexOf("/");
                    if (index != -1)
                    {
                        fileName = fileName.substring(index + 1);
                    }
                    int n = 0;
                    while ((line = br.readLine()) != null)
                    {
                        // 构造异步任务
                        Bean bean = builder.getBean(line, fileName);
                        if (bean != null)
                        {
                            Task task = new CSSPImportTask(builder.getDataDealer(), bean);
                            // 将任务加到运行器中
                            runner.addTask(task);
                        }
                        n++;
                    }
                    br.close();
                    // LOG.info("handle file"+fileName+" over...");
                    File srcFile = new File(localPath + "/" + fileName);
                    String day = filePrefix.substring(filePrefix.indexOf("_") + 1);
                    File destDir = new File(localPath + "/backup/" + day);
                    backupFile(srcFile, destDir);

                    // LOG.info("处理完成文件名："+fileName);
                }
                catch (Exception e)
                {
                    LOG.error("CSSP的分析文本入库过程出错", e);
                }

            }

            // 等待任务处理完成。
            runner.waitToFinished();
            runner.end();
            // System.out.println("本次全部文件处理完毕！");

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            LOG.error("CSSP的分析文本入库线程被中断了", e);
        }

    }

    protected String[] getLocalName()
    {
        File filePath = new File(localPath);
        return filePath.list(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String name)
            {
                // TODO Auto-generated method stub
                if (name.indexOf(filePrefix) != -1)
                {
                    return true;
                }

                return false;
            }

        });
    }

    protected void backupFile(File srcFile, File destDir)
    {
        try
        {
            FileUtils.copyFileToDirectory(srcFile, destDir);
            srcFile.delete();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            LOG.error("备份文件出错!" + srcFile.getName() + "---" + destDir.getName());
        }

    }

    // private void clearNoTodayFile(String localPath,final String day){
    // File dir = new File(localPath);
    // String[] clearFileNames = dir.list(new FilenameFilter(){
    //
    // @Override
    // public boolean accept(File dir, String name) {
    // // TODO Auto-generated method stub
    // //System.out.println(name+":"+name.endsWith(".log")+":"+name.indexOf(day));
    // if(name.endsWith(".log")&&name.indexOf(day)!=-1){
    // return false;
    // }
    // if(!name.endsWith(".log")){
    // return false;
    // }
    // return true;
    // }
    //			
    // });
    //		
    // for(String filename:clearFileNames){
    // File f = new File(localPath+"/"+filename);
    // f.delete();
    // }
    //		
    // }
    //	
    // public static void main(String[] a){
    // System.out.println("backup".indexOf(".log"));
    // }

}
