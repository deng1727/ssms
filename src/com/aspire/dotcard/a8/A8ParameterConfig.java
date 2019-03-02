package com.aspire.dotcard.a8;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class A8ParameterConfig
{
	private static final JLogger logger = LoggerFactory.getLogger(A8ParameterConfig.class);

    public static final String IP ;

    public static final int  PORT ;

    public static final String USER ;

    public static final String PWD ;


    public static final String BackupDir;

    public static final String [] MailTo;
    
    public static final int MaxSaveDay;
    
    public static final String A8MusicDir;
    
    public static final String A8SingerDir;
    
    public static final String A8TopListDir;
    
   // public static final String ;
    
    /**
     * 新增操作成功 
     */
    public static final int success_add=1;
    /**
     * 更新操作成功
     */
    public static final int success_update=2;
    /**
     * 删除操作成功
     */
    public static final int success_delete=3;
    /**
     * 操作失败
     */
    public static final int failure=-1;
    /**
     * 音乐同步开始时间
     */
    public static String MusicSynStartTime;
    /**
     * 音乐同步频率（每天/每周）
     */
    public static String MusicSynFrequency;
    /**
     * 歌手同步开始时间
     */
    public static String SingerSynStartTime;
    /**
     * 歌手同步频率（每天/每周）
     */
    public static String SingerSynFrequency;
    /**
     * 榜单同步开始时间
     */
    public static String TopListSynStartTime;
    /**
     * 榜单同步频率（每天/每周）
     */
    public static String TopListSynFrequency;
    
    
    


    static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("a8");
        
        IP = module.getItemValue("FTPServerIP").trim();
        PORT = Integer.parseInt(module.getItemValue("FTPServerPort").trim());
        USER = module.getItemValue("FTPServerUser").trim();
        PWD = module.getItemValue("FTPServerPassword").trim();
        
        MailTo=module.getItemValue("a8MailTo").trim().split(",");
        BackupDir=module.getItemValue("BackupDir").trim();
        MaxSaveDay=Integer.parseInt(module.getItemValue("MaxSaveDay").trim());
        A8MusicDir=module.getItemValue("A8MusicDir").trim();
        A8SingerDir=module.getItemValue("A8SingerDir").trim();
        A8TopListDir=module.getItemValue("TopListDir").trim();
        
        MusicSynStartTime=module.getItemValue("MusicSynStartTime").trim();
        MusicSynFrequency=module.getItemValue("MusicSynFrequency").trim();
        SingerSynStartTime=module.getItemValue("SingerSynStartTime").trim();
        SingerSynFrequency=module.getItemValue("SingerSynFrequency").trim();
        TopListSynStartTime=module.getItemValue("TopListSynStartTime").trim();
        TopListSynFrequency=module.getItemValue("TopListSynFrequency").trim();

    

        if (logger.isDebugEnabled())
        {
            logger.debug("the FTPServerIP is " + IP);
            logger.debug("the FTPServerPort is " + PORT);
            logger.debug("the FTPServerUser is " + USER);
            logger.debug("the FTPServerPassword is " + PWD);
            
            logger.debug("the MailTo is " + module.getItemValue("a8MailTo"));
            logger.debug("the BackupDir is " + BackupDir);
            logger.debug("the MaxSaveDay is " + MaxSaveDay);
            logger.debug("the A8MusicDir is " + A8MusicDir);
            
            logger.debug("the A8SingerDir is " + A8SingerDir);
            logger.debug("the A8TopListDir is " + A8TopListDir);
            logger.debug("the MusicSynStartTime is " + MusicSynStartTime);
            logger.debug("the MusicSynFrequency is " + MusicSynFrequency);
            
            logger.debug("the SingerSynStartTime is " + SingerSynStartTime);
            logger.debug("the SingerSynFrequency is " + SingerSynFrequency);
            logger.debug("the TopListSynStartTime is " + TopListSynStartTime);
            logger.debug("the TopListSynFrequency is " + TopListSynFrequency);

        }

    }

}
