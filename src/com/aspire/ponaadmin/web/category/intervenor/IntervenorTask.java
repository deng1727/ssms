/*
 * 
 */
package com.aspire.ponaadmin.web.category.intervenor;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.util.ArrangedTask;


/**
 * @author x_wangml
 *
 */
public class IntervenorTask extends ArrangedTask
{
    /**
     * ��ʼ���ֶ�
     */
    public void init()throws BOException
    {
        this.setExecuteTime(IntervenorConfig.STARTTIME);
        this.setTaskDesc("�˹���Ԥ�񵥶�ʱ����");
    }
    
    /**
     * ��ʱִ������
     */
    public void doTask()
    {
        IntervenorTools.getInstance().intervenorCategoryAll();
    }
}
