package com.aspire.dotcard.baseVideoNew.exportfile;

import java.util.Calendar;

/**
 * ���嵼������������
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @author wangminlong
 * @version
 */
public interface BaseFileAbility
{
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
