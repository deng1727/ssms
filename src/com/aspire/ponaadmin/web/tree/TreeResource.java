
package com.aspire.ponaadmin.web.tree;

import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 加载树配置信息管理
 *
 * <p>Copyright: Copyright (c) 2003-2005 <p>
 *
 * <p>ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 *
 * <p>Company: 卓望数码技术（深圳）有限公司</p>
 *
 *  @author    胡春雨
 *
 *  @version   1.0 *
 *
 */
public class TreeResource
{

	/**
	 * Automatically generated method: TreeResourceMgr
	 */
	private TreeResource () {

	}

    /**
     * 资源集合
     */
    static private Hashtable m_Bundles = new Hashtable();

    /**
     * 资源包
     */
    static private ResourceBundle resource = null;

    static
    {
        //要绑定资源的位置
        resource = getBundle("com.aspire.ponaadmin.web.tree.TreeResources");
    }

    /**
     *  取出绑定的资源对象
     * @param bundleName 绑定的资源对象的名称
     * @return 资源对象
     */
    static private ResourceBundle getBundle(String bundleName)
    {

        ResourceBundle aBundle = ( ResourceBundle ) m_Bundles.get(bundleName);
        if (aBundle == null)
        {
            aBundle = ResourceBundle.getBundle(bundleName, new Locale("zh",
                                                                      "CN"));
            m_Bundles.put(bundleName, aBundle);
        }
        return aBundle;
    }

    /**
     *  取出资源的值
     *
     * @param key 资源的键值
     * @return 资源的值
     * @param type 
     */
    public static String getTreeCode(String type, String key)
    {

        if ((type == null) || (key == null))
        {
            return "";
        }
        else
        {
            try
            {
                String code = resource.getString(key.trim() + "_"
                                                            + type.trim());
                return code;
            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }

}
