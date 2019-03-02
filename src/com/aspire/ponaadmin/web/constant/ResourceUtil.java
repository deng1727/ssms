/*
 * �ļ�����Constants.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  ���ļ�������س���
 * �޸��ˣ� �߱���
 * �޸�ʱ�䣺2005/06/20
 * �޸����ݣ�����
 */
package com.aspire.ponaadmin.web.constant;

/**
 * <p>Title: ResourceUtil</p>
 * <p>Description: the utility to get predefined static string from table resourceinfo</p>
 * <p>Copyright: Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author
 * @version 1.6
 */
import java.sql.ResultSet;
import java.util.HashMap;

import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>
 * Title: ��Դ��ʼ��������
 * </p>
 * <p>
 * Description: ������Ҫ���ڶ���ҳ��Ťת�ĳ���������ȣ�����Ӳ���롣
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All
 * Rights Reserved
 * </p>
 *
 * @author gaobaobing
 * @version 1.0.0.0
 */
public class ResourceUtil
{

    /**
     * д��־
     */
    protected static JLogger log = LoggerFactory.getLogger(ResourceUtil.class);
    
    /**
     * HASH��
     */
    private static HashMap msgMap = new HashMap();

    /**
     * ���췽��
     *
     */
    private ResourceUtil()
    {
    }

    /**
     * ��ʾԱ��Դ�ĳ�ʼ�����������������������ݿ����ӵĳ�ʼ��֮����ã���Ȼ�����
     * @throws java.lang.Exception
     */
    public static void init() throws Exception
    {
        //��ʼ����ʾ����Դ��Ϣ
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().query("select RESOURCEKEY,RESOURCEVALUE from t_resource", null);
            while(rs.next())
            {
                String key = rs.getString("RESOURCEKEY");
                String msg = rs.getString("RESOURCEVALUE");
                msgMap.put(key, msg);
            }
        }
        finally
        {
            DB.close(rs);
        }
    }


    // @CheckItem@ OPT-yanfeng-20031106 add debug for resource

    /**
     * get the resource value by the key
     * @param key the resource key
     * @return the resource value
     */
    public static String getResource(String key)
    {
        String val = (String) msgMap.get(key) ;
        if(val == null)
        {
            if(log.isDebugEnabled())
            {
                log.debug("message for ["+key+"] not found!!!");
            }
            val = key;
        }
        return val;
    }

    /**
     * replace the specified string with a new string within a string
     * @param srcStr the whole string
     * @param varStr the string need to be replaced
     * @param repStr the string to be inserted
     * @return the manipulated string
     */
    public static String replace(String srcStr, String varStr, String repStr)
    {
        log.debug("replace "+varStr+" with "+repStr);
        StringBuffer sb = new StringBuffer();
        int leftIndex = srcStr.indexOf(varStr);
        if (leftIndex > -1)
        {//has something to be replaced
            String leftStr = srcStr.substring(0, leftIndex);
            int rightIndex = leftIndex+varStr.length();
            String rightStr = srcStr.substring(rightIndex);
            sb.append(leftStr);
            sb.append(repStr);
            sb.append(rightStr);
        }
        else
        {//nothing to replace,remain as it is
            sb.append(srcStr);
        }
        log.debug("after replace:"+sb);
        return sb.toString();
    }

    /**
     * replace some formated url to normal url
     * @param url the formated url
     * @return the normal url
     */
    public static String formatURL(String url)
    {
        url = url.replace('^', '?');
        url = url.replace('~', '&');
        return url;
    }
}
