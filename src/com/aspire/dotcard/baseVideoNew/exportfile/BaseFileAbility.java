package com.aspire.dotcard.baseVideoNew.exportfile;

import java.util.Calendar;

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
    
    /**
     * 用于手动调置导出日期 后加日期偏移量
     * @param setTime
     */
    public void setExportTime(Calendar setTime);
}
