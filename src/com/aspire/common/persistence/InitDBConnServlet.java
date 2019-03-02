package com.aspire.common.persistence;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>Title: Portal /p>
 * <p>Description: Used by xtbs. Set the persistence property file
 *    and sql properties file  to DB pool</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author He Chengshou
 * @version 1.0
 */

public class InitDBConnServlet extends HttpServlet {
    protected static JLogger logger = LoggerFactory
            .getLogger(InitDBConnServlet.class);

    //Initialize global variables
    public void init() throws ServletException {
        //Set db connection pool config file
        String persistenceFile = this.getInitParameter("persistence-config")
                .replace('/', ServerInfo.FS.charAt(0));
        if (!persistenceFile.startsWith(ServerInfo.FS)) {
            persistenceFile = ServerInfo.FS + persistenceFile;
        }
        Constant.PERSISTENCE_PROP = ServerInfo.getAppRootPath()
                + persistenceFile;
        logger.info("persistence.properties is: " + Constant.PERSISTENCE_PROP);
        //Set Sql desc file
        String sqlFile = this.getInitParameter("sql-config").replace('/',
                ServerInfo.FS.charAt(0));
        if (!sqlFile.startsWith(ServerInfo.FS)) {
            sqlFile = ServerInfo.FS + sqlFile;
        }
        Constant.SQL_PROP = ServerInfo.getAppRootPath() + sqlFile;
        logger.info("sql.properties is: " + Constant.SQL_PROP);

    }

    //Clean up resources
    public void destroy() {}
}