package com.aspire.dotcard.gcontent;

import com.aspire.common.util.StringUtils;
import com.aspire.ponaadmin.web.repository.persistency.db.DBPersistencyCFG;
import com.aspire.ponaadmin.web.repository.persistency.db.NodeCFG;

public class GContentFactory
{

    /**
     * 获取内容类型的中文描述字符
     * @param sType,内容类型
     * @return
     */
    public static String getTypeDesc(GContent content)
    {
        if(content.getType()!=null)//如果包含类型，直接使用配置项中的值
        {
        	NodeCFG cfg=DBPersistencyCFG.getInstance().getNodeCFG(content.getType());
            if(cfg==null)
            {
            	return "";
            }else
            {
            	return cfg.getTypeDesc();
            }
        }else
        {
        	char firstCharOfId=content.getId().charAt(0);
        	String typeFlag=StringUtils.getBeginningNotDigitString(content.getId());
        	if(firstCharOfId<'0'||firstCharOfId>'9')//非应用类。
    	    {
    	    	if("clr".equals(typeFlag))
    	    	{
    	    		return "彩铃";
    	    	}else if("a8".equals(typeFlag))
    	    	{
    	    		return "A8全曲";
    	    		
    	    	}else if("v".equals(typeFlag))
    	    	{
    	    		return "基地视频";
    	    		
    	    	}else if("m".equals(typeFlag))
    	    	{
    	    		return "基地音乐";
    	    	}else if("r".equals(typeFlag))
    	    	{
    	    		return "基地图书";
    	    		
    	    	}else if("c".equals(typeFlag))
    	    	{
    	    		return "基地动漫";
    	    	}else if("b".equals(typeFlag))
    	    	{
    	    		return "广东图书";
    	    		
    	    	}else 
    	    	{
    	    		return "";
    	    	}
    	    }else
    	    {
    	    	return content.getCateName();
    	    }
        }
    	
    }
    
    /**
     * 根据内容类型构造内容对象
     * @param type
     * @return
     */
    public static GContent getGContentByType(String type)
    {
        if (GAppGame.TYPE_APPGAME.equals(type))
        {
            return new GAppGame();
        }
        else if(GAppTheme.TYPE_APPTHEME.equals(type))
        {
        	return new GAppTheme();   
        }
        else
        {
            return new GContent();
        }
    }
    
    /**
     * 根据内容类型构造内容对象
     * @param type
     * @return
     */
    public static String getSqlCodeByType(String type)
    {
        if (GAppGame.TYPE_APPGAME.equals(type))
        {
            return "SyncData.DataSyncDAO.getGcontentFromCMS().SELECTGAME";
        }
        else if(GAppSoftWare.TYPE_APPSOFTWARE.equals(type))
        {
            return "SyncData.DataSyncDAO.getGcontentFromCMS().SELECTSOFTWARE";
        }
        else if(GAppTheme.TYPE_APPTHEME.equals(type))
        {
            return "SyncData.DataSyncDAO.getGcontentFromCMS().SELECTTHEME";
        }
        else
        {
        	return "";
        }
    }
}
