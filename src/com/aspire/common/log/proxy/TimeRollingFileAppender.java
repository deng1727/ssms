/*
 * @(#)TimeRollingFileAppender.java        1.6 04/10/21
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

import java.io.File;
import org.apache.log4j.DailyRollingFileAppender;
import com.aspire.common.config.ServerInfo;
import com.aspire.common.log.constants.LogConstants;

/**
 *     the appender only rolling by time
 *     @author   YanFeng
 *     @version  1.6  2004-10-21
 *     @since    MISC 1.6.4.0
 * @CheckItem@ REQ-YanFeng-20041021 newly add
 */
public class TimeRollingFileAppender extends DailyRollingFileAppender
{
    public TimeRollingFileAppender()
    {
        super();
    }
    /**
     * onli override this method to
     * 1,set the relative path
     * 2,create path if not exist
     * @param file String
     */
    public void setFile(String file)
    {
        // Trim spaces from both ends. The users probably does not want
        // trailing spaces in file names.
        String val=file.trim();
        String tmpfileName=val.replace('/',LogConstants.FILE_SEP.charAt(0));
        if (!tmpfileName.startsWith(LogConstants.FILE_SEP))
            {
            tmpfileName=LogConstants.FILE_SEP+tmpfileName;
            }
         fileName=ServerInfo.getAppRootPath()+tmpfileName;
         //create non-exist path

        int index=fileName.lastIndexOf(ServerInfo.FS);
        if(index>0)
        {
            String sPath=fileName.substring(0,index);
            File path=new File(sPath);
            if(!path.exists())
            {
                path.mkdirs();
            }
        }

    }


}
