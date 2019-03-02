package com.aspire.ponaadmin.web.system;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.dotcard.colorringmgr.clrLoad.ColorringConfig;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * ϵͳ����������
 * @author zhangwei
 *
 */
public class SystemConfig
{
	public final static String SOURCESERVERIP;
	
	public final static int SOURCESERVERPORT;
	
	public final static String SOURCESERVERUSER;
	
	public final static String SOURCESERVERPASSWORD;
	
	public final static String URL_PIC_VISIT;
	
	public final static String FTPDIR_CATEGORYPICURL;
    
	static
    {
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("ssms");
        
        SOURCESERVERIP = ColorringConfig.get("sourceServerIP").trim();//���﹫�ò�������Դ������������
        SOURCESERVERPORT = Integer.parseInt(ColorringConfig.get("sourceServerPort"));
        SOURCESERVERUSER = ColorringConfig.get("sourceServerUser");
        SOURCESERVERPASSWORD = ColorringConfig.get("sourceServerPassword");
        URL_PIC_VISIT=PublicUtil.getEndWithSlash( ColorringConfig.get("sourceServerVisit"));
        FTPDIR_CATEGORYPICURL=PublicUtil.getEndWithSlash(module.getItemValue("categoryPicURlFtpDir").trim());

    }
	

}
