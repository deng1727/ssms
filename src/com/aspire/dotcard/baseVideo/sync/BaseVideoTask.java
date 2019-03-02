package com.aspire.dotcard.baseVideo.sync;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.BaseVideoFileBO;

public class BaseVideoTask extends TimerTask
{
    protected static JLogger logger = LoggerFactory.getLogger(BaseVideoTask.class);
    
    public void run()
    {
        StringBuffer sb = new StringBuffer();
        boolean isDiyTrue = false; 
    	
        // ͬ�����ֱ� 
        BaseVideoFileBO.getInstance().fileDataSync();
        
        // ������վɵ�ģ������ݣ�������ȫ������
        BaseVideoFileBO.getInstance().cleanOldSimulationData(sb);
        
        // ������ѯӦ�õ����ű������
        sb.append(BaseVideoFileBO.getInstance().addIndex());
        
        // �������б��������װģ���
        isDiyTrue = BaseVideoFileBO.getInstance().diyDataTable(sb);
        
        // ɾ��ͬ����ʱ�������
        sb.append(BaseVideoFileBO.getInstance().dropIndex());
		
        // ��װģ������
        if(!isDiyTrue)
        {
        	sb.append("��װģ�������ʱ�������󣬺�������ȡ����");
        }
        
        BaseVideoFileBO.getInstance().sendResultMail("������Ƶ��װģ����ܽ���ʼ�", sb);
        
        // ģ����齨����Ƿ���ȷ
        if(isDiyTrue)
        {
        	// ɾ�������ظ�������
        	BaseVideoFileBO.getInstance().delRepeatData();
        	
            //�޸�ͬ����ʱ��Ϊ��ʽ��
            BaseVideoFileBO.getInstance().renameDataSync();
            
            // ����ͳ������Ŀ������Ŀ�½�Ŀ��
            BaseVideoFileBO.getInstance().updateCategoryNodeNum();
        }
    }
}
