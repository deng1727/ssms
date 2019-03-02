package com.aspire.dotcard.syncAndroid.cssp.free;

import java.util.Queue;

import com.aspire.dotcard.syncAndroid.cssp.Bean;
import com.aspire.dotcard.syncAndroid.cssp.CSSPDAO;
import com.aspire.dotcard.syncAndroid.cssp.DataDealer;

/**
 * add by fanqh
 */
public class FreeDLOrderDealer implements DataDealer
{

    @SuppressWarnings("unchecked")
    @Override
    public void insert(Object bean)
    {
        if (bean != null)
        {
            // CSSPDAO.getInstance().batchQueueOrder.add((FreeDLOrderBean)bean);
            // CSSPDAO.getInstance().insertFreeDownLoadOrder((FreeDLOrderBean)bean);
            CSSPDAO.getInstance().batchInsertFreeDownLoadOrder((Queue<Bean>)bean);
        }
    }

}
