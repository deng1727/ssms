package com.aspire.ponaadmin.web.util ;

/**
 * <p>参数检查类</p>
 * <p>用来检查一些参数（一般都是字符型的）是否符合某种要求</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ParaChecker
{

    /**
     * 私有化构造器
     */
    private ParaChecker()
    {}

    /**
     * 判断一个字符串是否是整数
     * @param src String 要检查的字符
     * @return boolean
     */
    public static boolean isInteger(String src)
    {
        String reg = "\\d{1,9}";
        return src.matches(reg);
    }

    /**
     * 判断一个字符串是否包含字符（意思就是非只能包含中文、字母、数字）
     * @param src String 要检查的字符
     * @return boolean true,包含了字符；false,没有包含字符
     */
    public static boolean containSymbol(String src)
    {
        String reg = "[a-zA-Z0-9\\u4e00-\\u9fa5]+" ;
        return !src.matches(reg) ;
    }
}
