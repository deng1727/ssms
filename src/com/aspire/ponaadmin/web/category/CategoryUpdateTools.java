package com.aspire.ponaadmin.web.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CategoryUpdateTools
{
	private static CategoryUpdateTools instance =new CategoryUpdateTools();
	
	public static CategoryUpdateTools getInstance()
	{
		return instance;
	}
	private static JLogger LOG = LoggerFactory.getLogger(CategoryUpdateTools.class);
	 /**
	    * 对oldList的商品进行排序，要求sortCount的商品中，每一个sp的应用不超过maxCount个
	    * 本程序只能最大程度的确保前sortCount满足其要求。
	    * 如果读取数据库中spname失败，默认spname为空
	    * @param oldList
	    * @param sortCount
	    * @param maxCount
	    * @return
	    */
	   public  List sortListBySpName(List oldList,int sortCount,int maxCount)
	   
	   {
		   if(sortCount<1||maxCount<1)
		   {
			   LOG.info("parameters is illegal, return oldList. sortCount="+sortCount+",maxCount="+maxCount);
			   return oldList;
		   }
			//保存不符合要求的商品信息
			List illegalList=new ArrayList();
			List newList=new ArrayList();
			HashMap statisticMap=new HashMap();
			int demandedCount=0;//统计有多少个应用满足要求
			/*if(oldList.size()<sortCount)
			{
				sortCount=oldList.size();
			}*/
			for(int i=0;i<oldList.size()&&demandedCount<sortCount;i++)
			{
				String id=(String)oldList.get(i);
				String spName=this.getSpNameById(id);
				//
				Integer count=(Integer)statisticMap.get(spName);
				if(count==null)
				{
					statisticMap.put(spName, new Integer(0));
					count=(Integer)statisticMap.get(spName);
				}
				if(count.intValue()>=maxCount)
				{
					illegalList.add(id);
				}else
				{
					statisticMap.put(spName, new Integer(count.intValue()+1));
					newList.add(id);
					demandedCount++;
				}
			}
			
			newList.addAll(illegalList);
			newList.addAll(oldList.subList(newList.size(),oldList.size()));
			if(LOG.isDebugEnabled())//把前后比对效果打印出来
			{
				LOG.debug("根据sp挑选前后对比 demandedCount="+demandedCount);
				StringBuffer bf =new StringBuffer();
				bf.append("################## before {");
				for(int i=0;i<oldList.size();i++)
				{
					bf.append(oldList.get(i));
					bf.append(',');
				}
				bf.append('}');
				LOG.debug(bf);
				
				bf =new StringBuffer();
				bf.append("################## after  {");
				for(int i=0;i<newList.size();i++)
				{
					bf.append(newList.get(i));
					bf.append(',');
				}
				bf.append('}');
				LOG.debug(bf);
				
			}
			return newList;
		}
	   /**
	    * 通过内容id获取该内容sp名称。
	    * @param id
	    */
	   private  String getSpNameById(String id)
	   {
		   String spName=(String)CategoryRuleExcutor.SPNAMECACHE.get(id);
		   if(spName==null)
		   {
			   try
			{
				spName=CategoryRuleBO.getInstance().getSpNameById(id);
			} catch (BOException e)
			{
				LOG.error("获取spname出错",e);
				spName="";
			}
			   CategoryRuleExcutor.SPNAMECACHE.put(id, spName);
		   }
		   return spName;
	   }
	   
	   public static void main(String arg[])
	   {

	   }

}
