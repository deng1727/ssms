package com.aspire.common.log.proxy.config;

/**
 * <p>Title: UserHpIDFilter</p>
 * <p>Description: The filter for User Handphone ID filter</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire</p>
 * @author YanFeng
 * @version 1.0
 * @created at 15/4/2003
 */

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import com.aspire.common.log.proxy.model.*;
import com.aspire.common.util.StringUtils;

public class UserMobileIDFilter
    extends Filter
{

    private String mobileID;

    public void setMobileID(String mobileID)
    {
        this.mobileID = StringUtils.trim(mobileID);

    }

    public String getMobileID()
    {
        return mobileID;
    }

    /**
     * @param event the logging event
       @return is there string match.
     */
    public
        int decide(LoggingEvent event)
    {
        String id="xxx";

        Object obj=event.getMessage();
        if(obj instanceof BizLogContent)
        { //is a biz log
            BizLogContent bizObj=(BizLogContent)obj;
            id=StringUtils.trim(bizObj.getMobileID());
            if(StringUtils.trim(mobileID).equals((id)))
            {
                return Filter.ACCEPT;
            }
        }
        return Filter.DENY;
        //return Filter.ACCEPT;//this is for test
    }
}
