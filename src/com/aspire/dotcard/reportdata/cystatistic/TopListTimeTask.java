
package com.aspire.dotcard.reportdata.cystatistic;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.cystatistic.bo.CyListBO;
import com.aspire.dotcard.reportdata.cystatistic.bo.TopListBO;

public class TopListTimeTask extends TimerTask
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(TopListTimeTask.class);

 
    /**
     * ����run���з���
     */
    public void run()
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("TopListBO.service start...");
        }

        // ����Ӧ��top�񵥡�������������ݴ�����
        TopListBO.getInstance().service(0);

        // ����ÿ�մ�ҵ������Ʒ��Ӫ�������ݴ�������
      //  CyListBO.getInstance().service();
    }

  
}
