package com.aspire.ponaadmin.web.datasync.implement.game;

import com.aspire.ponaadmin.web.datasync.DataSyncConfig;

public class GameConfig
{
    public static  String IP ;

    public static  int  PORT ;

    public static  String USER ;

    public static  String PWD ;
    /**
     * 图片存储的相对用户目录之间的深度。
     */
    public static String BaseFolder;

 
    /**
     * 游戏图片资源服务器的访问根地址，比如：http://ip:port/pic
     */
    public static  String sourceServerVisit;
    
    public static int maxProcessThread;
    /**
     * 初始化游戏的配置信息。
     */
    public static void init(DataSyncConfig config)
    {
    	IP=config.get("task.sourceServer-ftp-ip");
    	PORT=Integer.parseInt(config.get("task.sourceServer-ftp-port"));
    	USER=config.get("task.sourceServer-ftp-user");
    	PWD=config.get("task.sourceServer-ftp-pwd");
    	BaseFolder=config.get("task.sourceServer-ftp-baseFolder");
    	
    	sourceServerVisit=config.get("task.sourceServerVisit");
    	if (sourceServerVisit.charAt(sourceServerVisit.length() - 1) != '/')
		{
    		sourceServerVisit=sourceServerVisit+'/';
		}
    	maxProcessThread=Integer.parseInt(config.get("task.maxProcessThread"));
    }
    


}
