package com.aspire.dotcard.appinfosyn;

import java.util.Calendar;

public interface BaseFileAbility {

	/**
     *  ִ����
     *  @param isSendMail �Ƿ����ʼ�
     */
    public String execution(boolean isSendMail);
    
    /**
     * �����ֶ����õ������� �������ƫ����
     * @param setTime
     */
    public void setExportTime(Calendar setTime);
}
