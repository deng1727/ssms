package com.aspire.dotcard.baseVideo.exportfile;

/**
 * 定义导入对象基本能力
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @author wangminlong
 * @version
 */
public interface BaseFileAbility
{
    /**
     *  执行体
     *  @param isSendMail 是否发送邮件
     */
    public String execution(boolean isSendMail);
}
