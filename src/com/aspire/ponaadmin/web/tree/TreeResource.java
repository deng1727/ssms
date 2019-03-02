
package com.aspire.ponaadmin.web.tree;

import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ������������Ϣ����
 *
 * <p>Copyright: Copyright (c) 2003-2005 <p>
 *
 * <p>ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 *
 * <p>Company: ׿�����뼼�������ڣ����޹�˾</p>
 *
 *  @author    ������
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
     * ��Դ����
     */
    static private Hashtable m_Bundles = new Hashtable();

    /**
     * ��Դ��
     */
    static private ResourceBundle resource = null;

    static
    {
        //Ҫ����Դ��λ��
        resource = getBundle("com.aspire.ponaadmin.web.tree.TreeResources");
    }

    /**
     *  ȡ���󶨵���Դ����
     * @param bundleName �󶨵���Դ���������
     * @return ��Դ����
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
     *  ȡ����Դ��ֵ
     *
     * @param key ��Դ�ļ�ֵ
     * @return ��Դ��ֵ
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
