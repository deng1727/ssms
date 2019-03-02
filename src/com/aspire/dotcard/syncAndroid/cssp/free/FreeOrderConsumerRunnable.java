package com.aspire.dotcard.syncAndroid.cssp.free;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.Task;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.syncAndroid.cssp.Bean;
import com.aspire.dotcard.syncAndroid.cssp.CSSPConsumerRunnable;
import com.aspire.dotcard.syncAndroid.cssp.IBuilder;

/**
 * report_*.log文件处理
 */
public class FreeOrderConsumerRunnable extends CSSPConsumerRunnable
{
    private static JLogger LOG = LoggerFactory.getLogger(FreeOrderConsumerRunnable.class);

    public FreeOrderConsumerRunnable(String filePrefix, IBuilder builder, String localPath)
    {
        super(filePrefix, builder, localPath);
    }

    public void run()
    {
        try
        {
            if (LOG.isDebugEnabled())
            {
                LOG.debug("report_*.log文件下载完了，开始处理本地文件了。。。");
            }
            // clearNoTodayFile(localPath,day);
            String[] localFiles = getLocalName();
            if (localFiles != null && localFiles.length > 0)
            {
                TaskRunner runner = new TaskRunner(10);

                for (String fileName : localFiles)
                {
                    if (LOG.isDebugEnabled())
                    {
                        LOG.debug("开始处理" + fileName + "文件!");
                    }
                    try
                    {
                        BufferedReader br = new BufferedReader(new FileReader(localPath + "/" + fileName));
                        String line = "";
                        int index = fileName.lastIndexOf("/");
                        if (index != -1)
                        {
                            fileName = fileName.substring(index + 1);
                        }
                        int n = 1;
                        final Queue<Bean> beanList = new ConcurrentLinkedQueue<Bean>();
                        while ((line = br.readLine()) != null)
                        {
                            // 构造异步任务
                            Bean bean = builder.getBean(line, fileName);
                            if (bean != null)
                            {
                                beanList.add(bean);
                            }else{
                                LOG.info(fileName+",第 "+n+" 行："+line);
                            }
                            n++;
                        }
                        if (LOG.isDebugEnabled())
                        {
                            LOG.debug("文件[" + fileName + "] 记录数（" + beanList.size() + ")");
                        }
                        if (beanList.size() > 0)
                        {
                            Task task = new FreeOrderImportTask(builder.getDataDealer(), beanList);
                            runner.addTask(task);// 将任务加到运行器中
                        }

                        Thread.sleep(300);

                        br.close();
                        File srcFile = new File(localPath + "/" + fileName);// report_20131024
                        String day = filePrefix.substring(filePrefix.indexOf("_") + 1);
                        File destDir = new File(localPath + "/backup/" + day);
                        backupFile(srcFile, destDir);

                        if (LOG.isDebugEnabled())
                        {
                            LOG.debug("处理" + fileName + "文件完成!（" + day + ")");
                        }
                    }
                    catch (Exception e)
                    {
                        LOG.error("report_*.log的分析文本入库过程出错", e);
                    }
                }

                // 等待任务处理完成。
                runner.waitToFinished();
                runner.end();
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("report_*.log本次全部文件处理完毕");
                }
            }
        }
        catch (Exception e)
        {
            LOG.error("report_*.log分析文本入库线程被中断了", e);
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
                if (name.indexOf(filePrefix) != -1)
                {
                    return true;
                }
                return false;
            }
        });
    }
}
