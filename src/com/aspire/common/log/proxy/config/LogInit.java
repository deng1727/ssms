/*
 * @(#)LogInit.java        1.6 04/04/14
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
package com.aspire.common.log.proxy.config;
/**
 *     the startup servlet for log initiation
 *     @author   YanFeng
 *     @version  1.6  2004-4-14
 *     @since    MISC1.6.3.1
 * @CheckItem@ REQ-YanFeng-20040414 add startup servlet for log initiation
 */

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.log.proxy.LoggerFactory;


public class LogInit extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public void init(ServletConfig config) throws ServletException
    {
        //日志初始化
        String prefix = ServerInfo.getAppRootPath();
        String logFile = prefix + config.getInitParameter("logConfig");
        String logRefreshTime = config.getInitParameter("logRefresh");
        String errFile = prefix + config.getInitParameter("errmsgConfig");

        LoggerFactory.configLog(logFile,logRefreshTime);
        LoggerFactory.loadErrorInfo(errFile);
        StringBuffer buff = new StringBuffer();
        buff.append("load log config...\n");
        buff.append("  logConfig              :");
        buff.append(logFile);
        buff.append("\n");
        buff.append("  logRefreshTime(seconds):");
        buff.append(logRefreshTime);
        buff.append("\n");
        buff.append("  error message file     :");
        buff.append(errFile);
        buff.append("\n");
        buff.append("end log startup servlet\n");
        System.out.println(buff);
    }

    public void destroy()
    {
    }
}
