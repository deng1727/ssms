package com.aspire.ponaadmin.web.util ;

/**
 * <p>���������</p>
 * <p>�������һЩ������һ�㶼���ַ��͵ģ��Ƿ����ĳ��Ҫ��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ParaChecker
{

    /**
     * ˽�л�������
     */
    private ParaChecker()
    {}

    /**
     * �ж�һ���ַ����Ƿ�������
     * @param src String Ҫ�����ַ�
     * @return boolean
     */
    public static boolean isInteger(String src)
    {
        String reg = "\\d{1,9}";
        return src.matches(reg);
    }

    /**
     * �ж�һ���ַ����Ƿ�����ַ�����˼���Ƿ�ֻ�ܰ������ġ���ĸ�����֣�
     * @param src String Ҫ�����ַ�
     * @return boolean true,�������ַ���false,û�а����ַ�
     */
    public static boolean containSymbol(String src)
    {
        String reg = "[a-zA-Z0-9\\u4e00-\\u9fa5]+" ;
        return !src.matches(reg) ;
    }
}
