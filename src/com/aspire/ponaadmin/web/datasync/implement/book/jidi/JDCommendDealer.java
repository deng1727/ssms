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
		boolean isOverallCommend=false;//定义是否是总体推荐，是总体推荐需要特殊处理。
		String cateId=(String)record.get(2);
		if(cateId.equals(""))//如果推荐id为空，标识全局推荐。
		{
			isOverallCommend=true;
		}
		String bookId='r'+(String)record.get(3);
		//创建vo类
		String changeType=(String)record.get(4);
		String id=null;//定义图书分类对应货架的id
		if(isOverallCommend)
		{
			id=RepositoryConstants.ROOT_CATEGORY_READ_SUBCATE_ID;
		}else
		{
			id=JDSyncDAO.getInstance().getIdByCateId(cateId);
		} 
		if(id==null)
		{
			LOG.error("不存在基地图书分类。cateId="+cateId);
			return DataSyncConstants.FAILURE_DATA_ERROR;
		}
		
		if(changeType.equals("1"))
		{
//			首先产看bookId是否存在，不存在
			Node node=(Node)Repository.getInstance().getNode(bookId);
			if(node==null)
			{
				LOG.error("推荐的图书id不存在。bookId="+bookId);
				return DataSyncConstants.FAILURE;
			}
			//先判断是否存在该内容
		    int result=JDSyncDAO.getInstance().isExistedComdBookId(id);
			if(result==-1)//图书分类推荐记录不存在
			{
				//保存图书的id需要保存原有数据的id。既去掉前缀的r
				return JDSyncDAO.getInstance().insertComdBookId(id, bookId.substring(1));
			//}else if(result==0)//图书分类记录存在，但是基地图书分类不存在。
			}else if(result==1)//图书分类记录存在，但是基地图书分类不存在。
			{
				return JDSyncDAO.getInstance().updateComdBookId(1,id, bookId.substring(1));
			}else
			{
				LOG.error("图书分类推荐已经存在，不能新增。id="+id);
				return DataSyncConstants.FAILURE_ADD_EXIST;
			}
			
		}else if(changeType.equals("2"))
		{
//			首先产看bookId是否存在，不存在
			Node node=(Node)Repository.getInstance().getNode(bookId);
			if(node==null)
			{
				LOG.error("推荐的图书id不存在。bookId="+bookId);
				return DataSyncConstants.FAILURE;
			}
			//保存图书的id需要保存原有数据的id。既去掉前缀的r
			return JDSyncDAO.getInstance().updateComdBookId(2,id, bookId.substring(1));
			
			
		}else if(changeType.equals("3"))
		{
			//return JDSyncDAO.getInstance().deleteJDComdBookId(id,bookId.substring(1));
			return JDSyncDAO.getInstance().deleteJDComdBookId(id);

		}else
		{
			LOG.error("不存在该变更类型。changeType="+changeType);
			return DataSyncConstants.FAILURE_NOT_CHANGETYPE;
		}
	}

	public void init(DataSyncConfig config) throws Exception
	{
		// TODO Auto-generated method stub

	}

}
