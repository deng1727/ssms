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
 * report_*.log�ļ�����
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
                LOG.debug("report_*.log�ļ��������ˣ���ʼ�������ļ��ˡ�����");
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
                        LOG.debug("��ʼ����" + fileName + "�ļ�!");
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
                            // �����첽����
                            Bean bean = builder.getBean(line, fileName);
                            if (bean != null)
                            {
                                beanList.add(bean);
                            }else{
                                LOG.info(fileName+",�� "+n+" �У�"+line);
                            }
                            n++;
                        }
                        if (LOG.isDebugEnabled())
                        {
                            LOG.debug("�ļ�[" + fileName + "] ��¼����" + beanList.size() + ")");
                        }
                        if (beanList.size() > 0)
                        {
                            Task task = new FreeOrderImportTask(builder.getDataDealer(), beanList);
                            runner.addTask(task);// ������ӵ���������
                        }

                        Thread.sleep(300);

                        br.close();
                        File srcFile = new File(localPath + "/" + fileName);// report_20131024
                        String day = filePrefix.substring(filePrefix.indexOf("_") + 1);
                        File destDir = new File(localPath + "/backup/" + day);
                        backupFile(srcFile, destDir);

                        if (LOG.isDebugEnabled())
                        {
                            LOG.debug("����" + fileName + "�ļ����!��" + day + ")");
                        }
                    }
                    catch (Exception e)
                    {
                        LOG.error("report_*.log�ķ����ı������̳���", e);
                    }
                }

                // �ȴ���������ɡ�
                runner.waitToFinished();
                runner.end();
                if (LOG.isDebugEnabled())
                {
                    LOG.debug("report_*.log����ȫ���ļ��������");
                }
            }
        }
        catch (Exception e)
        {
            LOG.error("report_*.log�����ı�����̱߳��ж���", e);
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
