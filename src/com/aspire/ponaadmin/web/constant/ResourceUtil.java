/*
 * 文件名：Constants.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  该文件定义相关常量
 * 修改人： 高宝兵
 * 修改时间：2005/06/20
 * 修改内容：初稿
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
 * Title: 资源初始化定义类
 * </p>
 * <p>
 * Description: 本类主要用于定义页面扭转的常量定义类等，不可硬编码。
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
     * 写日志
     */
    protected static JLogger log = LoggerFactory.getLogger(ResourceUtil.class);
    
    /**
     * HASH表
     */
    private static HashMap msgMap = new HashMap();

    /**
     * 构造方法
     *
     */
    private ResourceUtil()
    {
    }

    /**
     * 提示员资源的初始化方法，本方法必须在数据库链接的初始化之后调用，不然会出错！
     * @throws java.lang.Exception
     */
    public static void init() throws Exception
    {
        //初始化提示语资源信息
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
