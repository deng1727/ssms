package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class JDSyncBO
{
    protected static JLogger logger = LoggerFactory.getLogger(JDSyncBO.class);
    
    private static JDSyncBO dao=new JDSyncBO();
    
    public static JDSyncBO getInstance()
    {
    	return dao;
    }
    /**
     * �ӵ�ǰͼ��������漴��ѡһ������Ϊ��Ӫ�Ƽ���
     */
    public int updateYYBOOkCommend()throws BOException
    {
    	int successCount=0;
    	try
		{
			List cateList=JDSyncDAO.getInstance().getAllCommendCate();
			for(int i=0;i<cateList.size();i++)
			{
				String cateId=(String)cateList.get(i);
				String bookId=JDSyncDAO.getInstance().getRandomBookIdInBookCategory(cateId);
				if(bookId!=null)
				{
					//��Ҫ��ȡ����id ��Ӧ��contentid
					/*GContent content;				
					content = (GContent) Repository.getInstance().getNode(bookId, RepositoryConstants.TYPE_GCONTENT);
					String bookContentId=content.getContentID();*/
					//���ڻ���ͼ���id =r+contentid
					JDSyncDAO.getInstance().updateYYComdBookId(cateId, bookId.substring(1));
					successCount++;
					
				}else
				{
					JDSyncDAO.getInstance().updateYYComdBookId(cateId, null);//��Ҫ��ԭ�е��Ƽ�����ա�
					logger.error("ͼ�����idΪ"+cateId+"�Ļ��ܣ���ǰ������û��ͼ�飬�޷������Ӫ�Ƽ���");
				}
			}
		} catch (DAOException e)
		{
			throw new BOException("",e);
		}
    	return successCount;
    }

}
