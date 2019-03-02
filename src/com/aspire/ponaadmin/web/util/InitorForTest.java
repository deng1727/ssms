package com.aspire.ponaadmin.web.util;

import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.Constant;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.rightmanager.RightModel;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.system.Config;
import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ServerInfo;
import com.aspire.common.config.SystemConfig;
import com.aspire.common.db.DB;

import java.util.ResourceBundle;

/**
 * <p>单元测试初始化类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

public class InitorForTest
{

	/**
	 * Automatically generated method: InitorForTest
	 */
	private InitorForTest () {
		
	}

    /**
     * 环境初始化方法 ,只需要修改此配置文件的路径即可
     */
	public static void init()
    {
        //获取配置文件地址。
    	
        init("C:\\bea\\user_projects\\domains\\ssmsdomain\\myserver\\ssms\\");
    }

    /**
     * 环境初始化方法
     * @param contextPath String，上下文路径
     */
    public static void init(String contextPath)
    {
        //初始化log
        ServerInfo.setAppRootPath(contextPath);
        ServerInfo.setSystemName("ssms");
        String logConfig = contextPath + "conf/log4j.xml";
        LoggerFactory.configLog(logConfig, "60");
        String errFile = contextPath + "conf/error.properties";
        LoggerFactory.loadErrorInfo(errFile);
        //初始化数据库配置文件
        Constant.PERSISTENCE_PROP = contextPath + "conf/persistence.properties";
        Constant.SQL_PROP = contextPath + "conf/sql.properties";
        //初始化配置文件
        DB.getInstance().setMode(DB.MODE_JDBC_DIRECT);
        String configFile = contextPath + "conf/system-config.xml";
        SystemConfig systemConfig = ConfigFactory.getSystemConfig();
        systemConfig.init();       
        
        systemConfig.setConfigFile(configFile);
        System.out.println(configFile);
        systemConfig.load();
        Config.getInstance().init();
        
        /*********货架管理中心************/
        Repository.getInstance().init(contextPath+"/conf/repository_db.xml");

        RightManagerBO.getInstance().registerRightModel(RightModel.getInstance());    
    }
    public static void main (String arg[])throws Exception
    {
    	init();
    	
    }
}
