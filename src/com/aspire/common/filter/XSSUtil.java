package com.aspire.common.filter;

import org.apache.commons.lang.StringUtils;

public class XSSUtil {
    public static String stripXSS(String value) {
        if(StringUtils.isNotBlank(value)){
            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }
        System.out.println("value==============================================" + value);
        return value;
    }
}