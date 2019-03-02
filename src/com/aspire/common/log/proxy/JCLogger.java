package com.aspire.common.log.proxy;

/**
 * <p>Title: JCLogger</p>
 * <p>Description: the common logger extends JLogger,same as JLogger now.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author YanFeng
 * @version 1.0
 *  * @CheckItem@ OPT-yanfeng-20030912 add extended class to move biz log level to warn
 */

public class JCLogger extends JLogger
{
   protected JCLogger(Class logclazz)
   {
       super(logclazz);
   }


}
