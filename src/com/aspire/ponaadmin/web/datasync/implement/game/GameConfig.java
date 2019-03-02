package com.aspire.ponaadmin.web.datasync.implement.game;

import com.aspire.ponaadmin.web.datasync.DataSyncConfig;

public class GameConfig
{
    public static  String IP ;

    public static  int  PORT ;

    public static  String USER ;

    public static  String PWD ;
    /**
     * ͼƬ�洢������û�Ŀ¼֮�����ȡ�
     */
    public static String BaseFolder;

 
    /**
     * ��ϷͼƬ��Դ�������ķ��ʸ���ַ�����磺http://ip:port/pic
     */
    public static  String sourceServerVisit;
    
    public static int maxProcessThread;
    /**
     * ��ʼ����Ϸ��������Ϣ��
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
