package com.aspire.ponaadmin.common.usermanager ;

import java.util.Random;

/**
 * <p>用户新密码生成器</p>
 * <p>用户新密码生成器</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public final class PasswordGenerator
{

	/**
	 * 私有化构造方法，避免被任意构造。
	 */
	private PasswordGenerator () 
	{
		
	}

    /**
     * 生成新的用户密码
     * 采用java随机数
     * @return String
     */
    public static String generate ()
    {
        int pwdLen = 10 ; //密码长度
        StringBuffer password = new StringBuffer(pwdLen);
        Random random = new Random();
        for(int i = 0; i < pwdLen; i++)
        {
            //一次生成一个字符，字符范围0-9
            int key = (int) (random.nextFloat()*10);
            if(key == 10)
            {
                key = 0;
            }
            password.append(key);
        }
        return password.toString();
    }
}
