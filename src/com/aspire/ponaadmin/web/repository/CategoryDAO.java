package com.aspire.ponaadmin.web.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;


/**
 * 货架的DAO类，用于一些相关值的获取
 * <p>
 * Copyright (c) 2003-2006 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author bihui
 * @version 1.1.0.0
 */

public class CategoryDAO
{
    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(CategoryDAO.class);

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryDAO()
    {

    }

    /**
     * singleton模式的实例
     */
    private static CategoryDAO categoryDAO = new CategoryDAO();

    /**
     * 获取实例
     *
     * @return 实例
     */
    public static final CategoryDAO getInstance()
    {

        return categoryDAO;
    }
    
    /**
     * 通过分类id去查找对应的货架编码
     * @return categoryID 
     * @throws DAOException
     */
    public String getCategoryIDByID(String cateid) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getCategoryIDByID(" + cateid + ") is beginning ...." );
        }
        String result = "";
        Object[] paras = {cateid};
        String sqlCode = "CategoryDAO.getCategoryIDByID.SELECT";
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,paras);
            if(rs.next())
            {
                result = rs.getString("categoryID");
            }
        }
        catch (Exception e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs) ;
        }
        return result;
    }
    /**
     * 通过分类id去查找对应的名称
     * @return categoryID 
     * @throws DAOException
     */
    public String getCategoryNameByID(String cateid) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getCategoryNameByID(" + cateid + ") is beginning ...." );
        }
        String result = "";
        Object[] paras = {cateid};
        String sqlCode = "CategoryDAO.getCategoryNameByID.SELECT";
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,paras);
            if(rs.next())
            {
                result = rs.getString("name");
            }
        }
        catch (Exception e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs) ;
        }
        return result;
    }
    /**
     * 通过分类id去查找当前节点的PATH（包含子节点）.<br>
     * 节点之间的分割符为“>>”<br>
     * 该节点不存在则返回空字符
     * @return categoryID 
     * @throws DAOException
     */
    public String getCategoryNamePathByID(String cateid) throws DAOException
    {
    	return getCategoryNamePathByID(cateid,">>");
    }
    /**
     * 通过分类id去查找当前节点的PATH（包含子节点）
     * 该节点不存在则返回空字符
     * @return categoryID 
     * @return split 节点之间分割符
     * @throws DAOException
     */
    public String getCategoryNamePathByID(String cateid,String split) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getCategoryNamePathByID(" + cateid + ") is beginning ...." );
        }
        String result = "";
        Object[] paras = {cateid};
        String sqlCode = "CategoryDAO.getCategoryPathByID.SELECT";
        ResultSet rs = null;
        List nameList=new ArrayList();
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,paras);
            while(rs.next())
            {
            	nameList.add(rs.getString("name"));
            }
        }
        catch (Exception e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs) ;
        }
        StringBuffer bf=new StringBuffer();
        for(int i=nameList.size()-1;i>=0;i--)
        {
        	bf.append(split);
        	bf.append(nameList.get(i));
        }
        if(bf.length()>0)
        {
        	result= bf.substring(split.length());
        }
        return result;
    }
    
    /**
     * 获取货架编码系列的下一个值
     * @return result
     * @throws DAOException
     */
    public int getSeqCategoryID() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getSeqCategoryID() is beginning ...." );
        }
        int result = -1;
        Object[] paras = null;
        String sqlCode = "CategoryDAO.getSeqCategoryID.SELECT";
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,paras);
            if(rs.next())
            {
                result = rs.getInt(1);
            }
        }
        catch (Exception e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs) ;
        }
        return result;
    }
    
    /**
     * 获取某一个货架的所有一级子货架
     * 
     * @param categoryId
     * @return
     * @throws DAOException
     */
    public  List getAllSubCategorys(String categoryId) throws DAOException{
    	List cateList = new ArrayList();
    	
    	String sqlCode = "CategoryDAO.getAllSubCategorys.SELECT";
    	
    	 Object[] paras = {categoryId};
    	
    	 ResultSet rs = null;
    	 try
         {
             rs = DB.getInstance().queryBySQLCode(sqlCode,paras);
             while(rs.next())
             {
           	  Category cg = new Category();
           	  //removed by aiyan 2012-12-12 ，无用语句。最好把这个对于的SQL也改改成单一一个字段。看你了。
//           	cg.setId(rs.getString(1));
//           	cg.setName(rs.getString(2));
//           	cg.setDesc(rs.getString(3));
//           	cg.setSortID(rs.getInt(4));
//           	cg.setCtype(rs.getInt(5));
//           	cg.setCategoryID(rs.getString(6));
//           	cg.setDelFlag(rs.getInt(7));
//           	cg.setChangeDate(rs.getDate(8));
//           	cg.setState(rs.getInt(9));
//           	cg.setParentCategoryID(rs.getString(10));
           	cg.setRelation(rs.getString(11));
           	//  vo.setGoodsid(rs.getString(1));
           	//  vo.setCategoryId(rs.getString(2));
           	  cateList.add(cg);
             }
         }
         catch (Exception e)
         {
             throw new DAOException(e);
         }
         finally
         {
             DB.close(rs) ;
         }
   	
   	return cateList;
    	
    }
    
    
    /**
     * 通过内容id获取这个内容关联的所有产品类型
     * @param contentId
     * @return
     */
    public List getGoodsByContentId(String contentId) throws DAOException
    {
    	List cateList = new ArrayList();
    	
    	String sqlCode = "CategoryDAO.getCategoryByContentId.SELECT";
    	
    	 Object[] paras = {contentId};
    	
    	 ResultSet rs = null;
    	 
    	  try
          {
              rs = DB.getInstance().queryBySQLCode(sqlCode,paras);
              while(rs.next())
              {
            	  ContentGoodsInfo vo = new ContentGoodsInfo();
            	  
            	  vo.setGoodsid(rs.getString(1));
            	  vo.setCategoryId(rs.getString(2));
            	  cateList.add(vo);
              }
          }
          catch (Exception e)
          {
              throw new DAOException(e);
          }
          finally
          {
              DB.close(rs) ;
          }
    	
    	return cateList;
    }
    
    /**
     * 获取子货架的所有关联门店的集合
     * @param categoryId
     * @return 获取子货架的所有关联门店的集合（不包含孙子货架）
     */
    public String getSubCategoryRelation(String categoryId) throws DAOException
    {
    	String sqlCode1 = "CategoryDAO.getSubCategoryRelation.SELECT";
    	String sqlCode2 = "CategoryDAO.getSubCategoryRelation.701.SELECT";
    	 Object[] paras = {categoryId};
    	 StringBuffer relationUnion=new StringBuffer();    	
    	 ResultSet rs = null;    	 
    	  try
          {
    		  if("701".equals(categoryId))//只处理货架根分类的
    		  {
    			  rs = DB.getInstance().queryBySQLCode(sqlCode2,null);
    		  }else
    		  {
    			  rs = DB.getInstance().queryBySQLCode(sqlCode1,paras);
    		  }
              while(rs.next())
              {

            	  String relation[]=rs.getString(1).split(",");
            	  for(int i=0;i<relation.length;i++)
            	  {
            		  if(relationUnion.indexOf(relation[i])==-1)
            		  {
            			  relationUnion.append(',');
            			  relationUnion.append(relation[i]);
            		  }
            	  }
              }
              if(relationUnion.length()>0)
              {
            	  relationUnion.deleteCharAt(0);
              }
          }
          catch (Exception e)
          {
              throw new DAOException(e);
          }
          finally
          {
              DB.close(rs) ;
          }   	
    	return relationUnion.toString();
    }

    /**
     * 
     *@desc 
     *@author dongke
     *May 23, 2011
     * @return
     * @throws DAOException
     */
	public List getCategoryKeyBase() throws DAOException
	{
		List keyBaseList = null;
		String sqlCode = "CategoryDAO.getCategoryKeyBase.SELECT";
		// select * from t_key_base b where b.keytable='t_r_category'
		ResultSet rs = null;
		try
		{
			keyBaseList = new ArrayList();
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while (rs.next())
			{
				HashMap hm = new HashMap();
				hm.put("keyname",rs.getString("keyname"));
				hm.put("keydesc",rs.getString("keydesc"));
				hm.put("keytable",rs.getString("keytable"));
				hm.put("keyid",rs.getString("keyid"));
				hm.put("keytype",rs.getString("keytype"));
				keyBaseList.add(hm);
			}
		} catch (Exception e)
		{
			throw new DAOException(e);
		} finally
		{
			DB.close(rs);
		}
		return keyBaseList;
	}
    
	 public String getKeyIdByKeyName(String keyName) throws DAOException
	    {
			String sqlCode = "CategoryDAO.getKeyIdByKeyName.SELECT";
			//select * from t_key_base b where b.keytable='t_r_category' and b.keyname=?	
			Object[] paras = {keyName };
			ResultSet rs = null;
			String keyId = null;
			KeyResourceVO kr = null;
			try
			{
				rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
				if (rs.next())
				{				
					keyId = rs.getString("keyid");
				}
			} catch (Exception e)
			{
				throw new DAOException(e);
			} finally
			{
				DB.close(rs);
			}
			return keyId;
		}
	
    /**
     * 
     *@desc  获取货架所有扩展属性
     *@author dongke
     *May 21, 2011
     * @param categoryId
     * @return
     * @throws DAOException
     */
//    public List getCategoryKeyResourceByCid(String categoryId) throws DAOException
//    {
//		List keyResourcesList = null;
//		List  keyBaseList =   this.getCategoryKeyBase();
//		String sqlCode = "CategoryDAO.getCategoryKeyResourceByCid.SELECT";
//		// select * from t_key_resource s where  s.tid=?  and  s.keyid=?
//		
//		
//		ResultSet rs = null;
//	
//		
//			if(keyBaseList != null ){
//				keyResourcesList = new ArrayList();
//				ResourceVO kr = new ResourceVO();
//				for(int i = 0; i < keyBaseList.size();i++){
//					try{
//					HashMap kb =	(HashMap)keyBaseList.get(i);
//					kr.setKeyname((String)kb.get("keyname"));
//					kr.setKeydesc((String)kb.get("keydesc"));
//					kr.setTid(categoryId);
//					kr.setKeyid((String)kb.get("keyid"));
//					kr.setKeytable((String)kb.get("keytable"));
//					kr.setKeyType((String)kb.get("keytype"));
//					Object[] paras = { categoryId ,kb.get("keyid")};
//					rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
//					if (rs.next())
//					{
//						kr.setValue( rs.getString("value"));		
//					}
//					keyResourcesList.add(kr);
//					}catch (Exception e)
//					{
//						e.printStackTrace();
//						throw new DAOException(e);
//					} finally
//					{
//						DB.close(rs);
//					}
//				}
//			}
//		return keyResourcesList;
//	}
   /**
    * 
    *@desc 新增/修改货架扩展属性
    *@author dongke
    *May 24, 2011
    * @param categoryId
    * @param keyName
    * @param value
    * @return
    * @throws DAOException
    */
    public int insertCategoryKeyRes(String categoryId,String keyName,String value) throws DAOException
    {
		int rs = 0;
		String sqlCode = "CategoryDAO.insertCategoryKeyRes.INSERT";
		String keyId = this.getKeyIdByKeyName(keyName);
		// insert into t_key_resource (tid,keyid,value,lupdate) values
		// (?,?,?,sysdate);
		if (keyId != null && !keyId.equals("") && categoryId != null
				&& !categoryId.equals("") && value != null && !value.equals(""))
		{
			Object[] paras = { categoryId, keyId, value };
			try
			{
				rs = DB.getInstance().executeBySQLCode(sqlCode, paras);
			} catch (Exception e)
			{
				throw new DAOException(e);
			}
		}
		else
		{
			logger.error("paras is null :categoryId=" + categoryId + ";value=" + value
					+ ";keyName=" + keyName + ";keyId=" + keyId);
		}
		return rs;
	}
   /**
    * 
    *@desc 修改货架扩展属性
    *@author dongke
    *May 24, 2011
    * @param categoryId
    * @param keyId
    * @param value
    * @return
    * @throws DAOException
    */
    public int updateCategoryKeyRes(String categoryId,String keyId,String value) throws DAOException
    {
		String sqlCode = "CategoryDAO.updateCategoryKeyRes.UPDATE";
		// update t_key_resource s  set s.value=? where  s.keyid=?   and s.tid=?
		Object[] paras = {value,keyId, categoryId };
		int rs = 0;
		try
		{
			rs = DB.getInstance().executeBySQLCode(sqlCode, paras);
		
		} catch (Exception e)
		{
			throw new DAOException(e);
		} 
		return rs;
	}
    /**
     * 
     *@desc  获取货架所有扩展属性
     *@author dongke
     *May 21, 2011
     * @param categoryId
     * @return
     * @throws DAOException
     */
    public KeyResourceVO getCategoryKeyResByKey(String categoryId,String keyName) throws DAOException
    {
		//List keyResourcesList = null;
		String sqlCode = "CategoryDAO.getCategoryKeyResByKey.SELECT";
		// select b.keyid,b.keydesc,s.tid,b.keyname,s.value from t_key_resource s,t_key_base b where b.keytable='t_r_category' and b.keyname=?  and  s.keyid=b.keyid and s.tid=?
		Object[] paras = {keyName, categoryId };
		ResultSet rs = null;
		KeyResourceVO kr = null;
		try
		{
			//keyResourcesList = new ArrayList();
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			if (rs.next())
			{
				kr = new KeyResourceVO();
				kr.setKeyName(rs.getString("keyname"));
				kr.setValue( rs.getString("value"));
				kr.setKeyDesc(rs.getString("keydesc"));
				kr.setTid(rs.getString("tid"));
				kr.setKeyId(rs.getString("keyid"));
				//keyResourcesList.add(kr);
			}
		} catch (Exception e)
		{
			throw new DAOException(e);
		} finally
		{
			DB.close(rs);
		}
		return kr;
	}
    
    /**
     * 用于返回货架扩展字段列表
     * 
     * @return
     * @throws BOException
     */
    public List getCategoryKeyResourceByCid(String tid) throws DAOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("queryCategoryKeyBaseList( ) is starting ...");
		}
		// select * from t_r_category t where t.delflag = '0' order by
		// t.sortid
		String sqlCode = null;
		ResultSet rs = null;
		List list = new ArrayList();
		boolean insert = true;
		try
		{
			if (tid != null && !tid.equals(""))
			{
				//select *  from t_key_base b,     (select *   from t_key_resource r   where r.tid = ?) y   where b.keytable = 't_r_category' and b.keyid = y.keyid(+)
				sqlCode = "CategoryDAO.queryCategoryKeyBaseResList().SELECT";
				Object[] paras = { tid };
				rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
				insert = false;
			}
			else
			{
				//select *  from t_key_base b   where b.keytable = 't_r_category' 
				sqlCode = "CategoryDAO.queryCategoryKeyBaseList().SELECT";
				rs = DB.getInstance().queryBySQLCode(sqlCode, null);
				insert = true;
			}
			while (rs.next())
			{
				ResourceVO vo = new ResourceVO();
				fromCategoryKeyBaseVOByRs(vo, rs, insert);
				list.add(vo);
			}
		} catch (DAOException e)
		{
			e.printStackTrace();
			throw new DAOException("获取货架扩展信息表信息查询发生异常:", e);
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new DAOException("获取货架扩展信息表信息查询发生异常:", e);
		} finally
		{
			DB.close(rs);
		}

		return list;
	}
    
    /**
     * 为对象属性赋值
     * 
     * @param vo
     * @param rs
     * @throws SQLException
     */
    private void fromCategoryKeyBaseVOByRs(ResourceVO vo, ResultSet rs,boolean insert)
                    throws SQLException
    {
        vo.setKeyid(rs.getString("keyid"));
        vo.setKeyname(rs.getString("keyname"));
        vo.setKeytable(rs.getString("keytable"));
        vo.setKeydesc(rs.getString("keydesc"));
        vo.setKeyType(rs.getString("keytype"));
        if(!insert){
        	vo.setTid(rs.getString("tid"));
            vo.setValue(rs.getString("value"));
        } 
    }
}
