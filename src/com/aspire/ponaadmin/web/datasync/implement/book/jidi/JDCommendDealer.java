package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

public class JDCommendDealer implements DataDealer
{

	private static JLogger LOG =LoggerFactory.getLogger(JDCommendDealer.class);
	public void prepareData() throws Exception
	{
		JDSyncDAO.getInstance().deleteIllegalComd();
	}
	public void clearDirtyData()
	{
		
	}

	public int dealDataRecrod(DataRecord record) throws Exception
	{
		boolean isOverallCommend=false;//�����Ƿ��������Ƽ����������Ƽ���Ҫ���⴦��
		String cateId=(String)record.get(2);
		if(cateId.equals(""))//����Ƽ�idΪ�գ���ʶȫ���Ƽ���
		{
			isOverallCommend=true;
		}
		String bookId='r'+(String)record.get(3);
		//����vo��
		String changeType=(String)record.get(4);
		String id=null;//����ͼ������Ӧ���ܵ�id
		if(isOverallCommend)
		{
			id=RepositoryConstants.ROOT_CATEGORY_READ_SUBCATE_ID;
		}else
		{
			id=JDSyncDAO.getInstance().getIdByCateId(cateId);
		} 
		if(id==null)
		{
			LOG.error("�����ڻ���ͼ����ࡣcateId="+cateId);
			return DataSyncConstants.FAILURE_DATA_ERROR;
		}
		
		if(changeType.equals("1"))
		{
//			���Ȳ���bookId�Ƿ���ڣ�������
			Node node=(Node)Repository.getInstance().getNode(bookId);
			if(node==null)
			{
				LOG.error("�Ƽ���ͼ��id�����ڡ�bookId="+bookId);
				return DataSyncConstants.FAILURE;
			}
			//���ж��Ƿ���ڸ�����
		    int result=JDSyncDAO.getInstance().isExistedComdBookId(id);
			if(result==-1)//ͼ������Ƽ���¼������
			{
				//����ͼ���id��Ҫ����ԭ�����ݵ�id����ȥ��ǰ׺��r
				return JDSyncDAO.getInstance().insertComdBookId(id, bookId.substring(1));
			//}else if(result==0)//ͼ������¼���ڣ����ǻ���ͼ����಻���ڡ�
			}else if(result==1)//ͼ������¼���ڣ����ǻ���ͼ����಻���ڡ�
			{
				return JDSyncDAO.getInstance().updateComdBookId(1,id, bookId.substring(1));
			}else
			{
				LOG.error("ͼ������Ƽ��Ѿ����ڣ�����������id="+id);
				return DataSyncConstants.FAILURE_ADD_EXIST;
			}
			
		}else if(changeType.equals("2"))
		{
//			���Ȳ���bookId�Ƿ���ڣ�������
			Node node=(Node)Repository.getInstance().getNode(bookId);
			if(node==null)
			{
				LOG.error("�Ƽ���ͼ��id�����ڡ�bookId="+bookId);
				return DataSyncConstants.FAILURE;
			}
			//����ͼ���id��Ҫ����ԭ�����ݵ�id����ȥ��ǰ׺��r
			return JDSyncDAO.getInstance().updateComdBookId(2,id, bookId.substring(1));
			
			
		}else if(changeType.equals("3"))
		{
			//return JDSyncDAO.getInstance().deleteJDComdBookId(id,bookId.substring(1));
			return JDSyncDAO.getInstance().deleteJDComdBookId(id);

		}else
		{
			LOG.error("�����ڸñ�����͡�changeType="+changeType);
			return DataSyncConstants.FAILURE_NOT_CHANGETYPE;
		}
	}

	public void init(DataSyncConfig config) throws Exception
	{
		// TODO Auto-generated method stub

	}

}
