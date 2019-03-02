package com.aspire.dotcard.basevideosync.exportfile;

import java.util.Calendar;

public interface BaseFileAbility {

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
