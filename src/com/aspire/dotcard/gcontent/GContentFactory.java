package com.aspire.dotcard.gcontent;

import com.aspire.common.util.StringUtils;
import com.aspire.ponaadmin.web.repository.persistency.db.DBPersistencyCFG;
import com.aspire.ponaadmin.web.repository.persistency.db.NodeCFG;

public class GContentFactory
{

    /**
     * ��ȡ�������͵����������ַ�
     * @param sType,��������
     * @return
     */
    public static String getTypeDesc(GContent content)
    {
        if(content.getType()!=null)//����������ͣ�ֱ��ʹ���������е�ֵ
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
        	if(firstCharOfId<'0'||firstCharOfId>'9')//��Ӧ���ࡣ
    	    {
    	    	if("clr".equals(typeFlag))
    	    	{
    	    		return "����";
    	    	}else if("a8".equals(typeFlag))
    	    	{
    	    		return "A8ȫ��";
    	    		
    	    	}else if("v".equals(typeFlag))
    	    	{
    	    		return "������Ƶ";
    	    		
    	    	}else if("m".equals(typeFlag))
    	    	{
    	    		return "��������";
    	    	}else if("r".equals(typeFlag))
    	    	{
    	    		return "����ͼ��";
    	    		
    	    	}else if("c".equals(typeFlag))
    	    	{
    	    		return "���ض���";
    	    	}else if("b".equals(typeFlag))
    	    	{
    	    		return "�㶫ͼ��";
    	    		
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
     * �����������͹������ݶ���
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
     * �����������͹������ݶ���
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
