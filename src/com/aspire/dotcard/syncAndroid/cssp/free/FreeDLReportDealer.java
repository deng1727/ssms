package com.aspire.dotcard.syncAndroid.cssp.free;

import java.util.List;
import java.util.Queue;

import com.aspire.dotcard.syncAndroid.cssp.Bean;
import com.aspire.dotcard.syncAndroid.cssp.CSSPDAO;
import com.aspire.dotcard.syncAndroid.cssp.DataDealer;

/**
 * add by fanqh
 */
public class FreeDLReportDealer implements DataDealer
{

    @SuppressWarnings("unchecked")
    @Override
    public void insert(Object bean)
    {
        if (bean != null)
        {
            //CSSPDAO.getInstance().batchQueueReport.add((FreeDLReportBean)bean);
            //CSSPDAO.getInstance().batchInsertFreeDownloadReport((Queue<Bean>)bean);
            CSSPDAO.getInstance().batchInsertFreeDownloadReport((Queue<Bean>)bean);
        }
    }

}
