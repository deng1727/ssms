package com.aspire.ponaadmin.web.daemon ;

/**
 * ��̨�����࣬��װһ����̨�첽ִ����������һ�����࣬������븲��execute������
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: aspire</p>
 *
 * @author shidr
 * @version 1.0
 */
public abstract class DaemonTask
{

    /**
     * �������з�������ʱ����ʾ��Ϣ
     */
    private String errorHint;

    /**
     * ����ִ�з�����
     */
    public abstract void execute();

    /**
     * �����������з�������ʱ����ʾ��Ϣ
     * @param _hint String ��ʾ��Ϣ
     */
    public void setErrorHint(String _hint)
    {
        this.errorHint = _hint;
    }

    /**
     * ��ȡ�������з�������ʱ����ʾ��Ϣ
     * @return String
     */
    public String getErrorHint()
    {
        return this.errorHint == null ? "" : this.errorHint ;
    }
}
