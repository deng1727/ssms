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
	    * ��oldList����Ʒ��������Ҫ��sortCount����Ʒ�У�ÿһ��sp��Ӧ�ò�����maxCount��
	    * ������ֻ�����̶ȵ�ȷ��ǰsortCount������Ҫ��
	    * �����ȡ���ݿ���spnameʧ�ܣ�Ĭ��spnameΪ��
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
			//���治����Ҫ�����Ʒ��Ϣ
			List illegalList=new ArrayList();
			List newList=new ArrayList();
			HashMap statisticMap=new HashMap();
			int demandedCount=0;//ͳ���ж��ٸ�Ӧ������Ҫ��
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
			if(LOG.isDebugEnabled())//��ǰ��ȶ�Ч����ӡ����
			{
				LOG.debug("����sp��ѡǰ��Ա� demandedCount="+demandedCount);
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
	    * ͨ������id��ȡ������sp���ơ�
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
				LOG.error("��ȡspname����",e);
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
