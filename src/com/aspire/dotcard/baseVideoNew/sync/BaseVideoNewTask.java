package com.aspire.dotcard.baseVideoNew.sync;

import java.util.TimerTask;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.BaseVideoFileBO;
import com.aspire.dotcard.baseVideo.bo.CollectionBO;
import com.aspire.dotcard.baseVideoNew.bo.BaseVideoDeleteBlackBO;
import com.aspire.dotcard.baseVideoNew.bo.BaseVideoNewFileBO;

public class BaseVideoNewTask extends TimerTask 
{
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseVideoNewTask.class);
	
	public void run()
	{
		StringBuffer msgInfo = new StringBuffer();
		logger.info("������Ƶ����ȫ��ͬ����ʼ����������");
		// ��ʼͬ��֮��
		BaseVideoNewFileBO.getInstance().fileDataSync();
		
		// ���ô洢���� ����ִ���м������ʽ��������ת��
		boolean syncTrue = BaseVideoNewFileBO.getInstance().syncVideoData();
		
		// ���ִ����ȷ
		if (syncTrue)
		{
			msgInfo.append("���ô洢���� ����ִ���м������ʽ��������ת����ɣ�");
			
			// ������վɵ�ģ������ݣ�������ȫ������
			BaseVideoNewFileBO.getInstance().cleanOldSimulationData(msgInfo);
			
			// �������б��������װģ���
			BaseVideoNewFileBO.getInstance().diyDataTable(msgInfo);
			
			// ����ͳ������Ŀ������Ŀ�½�Ŀ��
			BaseVideoNewFileBO.getInstance().updateCategoryNodeNum();
		}
		else
		{
			msgInfo.append("���ô洢���� ����ִ���м������ʽ��������ת��ʱ�������󣬺�������ȡ����");
		}
		// ��ѯ�м��洢������������ʼ���ʾ 
		msgInfo.append(BaseVideoNewFileBO.getInstance().getMailText());
		
        BaseVideoFileBO.getInstance().sendResultMail("������Ƶ��װģ����ܽ���ʼ�", msgInfo);
        
		//��ʼͬ�����ݼ��ڵ㼰���ݼ�����
		BaseVideoNewFileBO.getInstance().syncCollectAndNodeData();
		UpdateIsshowFieldTask isshowTask= new UpdateIsshowFieldTask();
		BaseVideoDeleteBlackBO backBO = new BaseVideoDeleteBlackBO();
		backBO.delVideoBlack();
//        CollectionBO collectionBo = new CollectionBO();
//        collectionBo.updateCollections();
	    try {
			isshowTask.updateIsshowField();
		} catch (BOException e) {			
			logger.error("��������ָ�����ݼ�(isShow�ֶ�)Ϊ��ʱ�����쳣��", e);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("��������ָ�����ݼ�(isShow�ֶ�)Ϊ��ʱ�����쳣�����ݿ����ʧ�ܣ�", e);
		}
		
		BaseVideoNewFileBO.getInstance().costProductRelation();

	}
}
