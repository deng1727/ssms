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
     * 从当前图书分类中随即挑选一本书作为运营推荐。
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
					//需要获取内容id 对应的contentid
					/*GContent content;				
					content = (GContent) Repository.getInstance().getNode(bookId, RepositoryConstants.TYPE_GCONTENT);
					String bookContentId=content.getContentID();*/
					//由于基地图书的id =r+contentid
					JDSyncDAO.getInstance().updateYYComdBookId(cateId, bookId.substring(1));
					successCount++;
					
				}else
				{
					JDSyncDAO.getInstance().updateYYComdBookId(cateId, null);//需要把原有的推荐的清空。
					logger.error("图书分类id为"+cateId+"的货架，当前货架下没有图书，无法完成运营推荐！");
				}
			}
		} catch (DAOException e)
		{
			throw new BOException("",e);
		}
    	return successCount;
    }

}
