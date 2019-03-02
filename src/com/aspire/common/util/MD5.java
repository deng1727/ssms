package com.aspire.common.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ASPire</p>
 * @author ZhangJi
 * @version 1.6
 */

import java.security.MessageDigest;

public class MD5
{

    public static String getHexMD5Str(String strIn)
        throws Exception
    {
        return getHexMD5Str(strIn.getBytes());
    }

    public static String getHexMD5Str(byte[] arrIn)
        throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] arrB = md.digest(arrIn);
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < arrB.length; i++)
        {
            int intTmp = arrB[i];
            while (intTmp < 0)
            {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16)
            {
                sb.append('0');
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString().toUpperCase();
    }

    public static void main(String[] args)
    {
        try
        {
            String strTest = "2222";

            System.out.println(System.currentTimeMillis());
            System.out.println(strTest.hashCode());
            System.out.println(System.currentTimeMillis());
            System.out.println(getHexMD5Str(strTest));
            System.out.println(System.currentTimeMillis());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

}