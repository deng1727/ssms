package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.repository.CategoryDAO;

public class JDSyncDAO
{
	/**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(JDSyncDAO.class);
    
    private static JDSyncDAO dao=new JDSyncDAO();
    
    public static JDSyncDAO getInstance()
    {
    	return dao;
    }
	
	/**
     * 新增歌手记录
     * @param vo 歌手vo类
     * @return 返回新增结果
     */
    public int insertAuthor(AuthorVO vo)
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("insertAuthor"+vo);
        }
    	Object paras[]= {vo.getId(),vo.getName(),vo.getDesc()};
    	String sqlCode="datasync.implement.book.jidi.insertAuthor().INSERT";
    	int result;
    	try
		{
    		result=DB.getInstance().executeBySQLCode(sqlCode, paras);
    		
		} catch (DAOException e)
		{
			logger.error("新增歌手记录异常",e);
			return DataSyncConstants.FAILURE_ADD;
		}
		if(result<1)
		{
			return DataSyncConstants.FAILURE_ADD;
		}else
		{
			return DataSyncConstants.SUCCESS_ADD;
		}
    	
    }
    /**
     * 检查歌手是否存在
     * @param vo 歌手vo类
     * @return 返回新增结果
     */
    public boolean isExistedVO(AuthorVO vo)throws DAOException
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("isExistedVO"+vo);
        }
    	Object paras[]= {vo.getId()};
    	String sqlCode="datasync.implement.book.jidi.isExistedVO().SELECT";
    	ResultSet rs=null;
    	try
		{
    		rs=DB.getInstance().queryBySQLCode(sqlCode, paras);
    		if(rs.next())
    		{
    			return true;
    		}else
    		{
    			return false;
    		}
		} catch (Exception e)
		{
			logger.error(vo,e);
			throw new DAOException(e);
		}finally
		{
			DB.close(rs);
		}
    	
    }
    
    /**
     * 获取作者信息
     * @param authorId 作者id
     * @return  作者信息，不存在，或者查询异常返回null
     */
    public AuthorVO getAuthorVO(String  authorId)throws DAOException
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("getAuthorVO"+authorId);
        }
    	Object paras[]= {authorId};
    	String sqlCode="datasync.implement.book.jidi.getAuthorVO().SELECT";
    	ResultSet rs=null;
    	AuthorVO vo=null;
    	try
		{
    		rs=DB.getInstance().queryBySQLCode(sqlCode, paras);
    		if(rs.next())
    		{
    			vo=new AuthorVO();
    			vo.setId(authorId);
    			vo.setName(rs.getString(1));
    			vo.setDesc(rs.getString(2));
    		}
		} catch (Exception e)
		{
			logger.error(authorId,e);
			
		}finally
		{
			DB.close(rs);
		}
		return vo;
    	
    }
    /**
     * 
     * @param vo
     * @return
     */
    public int updateAuthor(AuthorVO vo)
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("updateAuthor"+vo);
        }
    	Object paras[]= {vo.getName(),vo.getDesc(),vo.getId()};
    	String sqlCode="datasync.implement.book.jidi.updateAuthor().UPDATE";
    	int result;
    	try
		{
			result=DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (DAOException e)
		{
			logger.error(vo,e);
			return DataSyncConstants.FAILURE_UPDATE;
		}
		if(result<1)
		{
			return DataSyncConstants.FAILURE_UPDATE;
		}else
		{
			return DataSyncConstants.SUCCESS_UPDATE;
		}   
    }
    /**
     * 删除歌手
     * @param vo
     * @return
     */
    public int deleteAuthor(AuthorVO vo )
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("deleteAuthor");
        }
    	String sqlCode="datasync.implement.book.jidi.deleteAuthor().DELETE";
    	Object paras[]= {vo.getId()};
    	int result=0;
    	try
		{
			result=DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (DAOException e)
		{
			logger.error(e);
			return DataSyncConstants.FAILURE_DEL;
		}
		if(result<1)
		{
			return DataSyncConstants.FAILURE_DEL_NOT_EXIST;
		}
	    return DataSyncConstants.SUCCESS_DEL;
		
    }
    
    
    /**
     * 新增图书分类id和货架分类的映射
     * @param cateId 图书分类id
     * @param id 货架分类id
     * @return 返回新增结果
     */
    public int insertCateIdMap(String cateId,String id)
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("insertCateIdMap");
        }
    	Object paras[]= {cateId,id};
    	String sqlCode="datasync.implement.book.jidi.insertCateIdMap().INSERT";
    	int result;
    	try
		{
    		result=DB.getInstance().executeBySQLCode(sqlCode, paras);
    		
		} catch (DAOException e)
		{
			logger.error("新增图书分类id和货架分类的映射",e);
			return DataSyncConstants.FAILURE_ADD;
		}
		if(result<1)
		{
			return DataSyncConstants.FAILURE_ADD;
		}else
		{
			return DataSyncConstants.SUCCESS_ADD;
		}
    	
    }
    /**
     * 检查是否存在图书分类id和货架分类的映射
     * @param cateId 图书分类id
     * @return ture 存在，false 不存在
     */
    public boolean isExistedCateIdMap(String cateId)throws DAOException
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("isExistedCateIdMap,cateId="+cateId);
        }
    	Object paras[]= {cateId};
    	String sqlCode="datasync.implement.book.jidi.isExistedCateIdMap().SELECT";
    	ResultSet rs=null;
    	try
		{
    		rs=DB.getInstance().queryBySQLCode(sqlCode, paras);
    		if(rs.next())
    		{
    			return true;
    		}else
    		{
    			return false;
    		}
		} catch (Exception e)
		{
			logger.error("cateId="+cateId+",查询图书分类id出错",e);
			throw new DAOException(e);
		}finally
		{
			DB.close(rs);
		}
    	
    }
    /**
     * 更新图书分类id和货架分类的映射
     * @param cateId 图书分类id
     * @param id 货架分类id
     * @return
     */
    public int updateCateIdMap(String cateId ,String id)
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("updateCateId,cateId"+cateId);
        }
    	Object paras[]= {id,cateId};
    	String sqlCode="datasync.implement.book.jidi.updateCateIdMap().UPDATE";
    	int result;
    	try
		{
			result=DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (DAOException e)
		{
			logger.error("updateCateId出错，cateId="+cateId,e);
			return DataSyncConstants.FAILURE_UPDATE;
		}
		if(result<1)
		{
			return DataSyncConstants.FAILURE_UPDATE;
		}else
		{
			return DataSyncConstants.SUCCESS_UPDATE;
		}   
    }
    /**
     * 删图书分类id和货架分类的映射
     * @param cateId 图书分类id
     * @return 
     */
    public int deleteCateIdMap(String cateId )
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("deleteCateIdMap");
        }
    	String sqlCode="datasync.implement.book.jidi.deleteCateIdMap().DELETE";
    	Object paras[]= {cateId};
    	int result=0;
    	try
		{
			result=DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (DAOException e)
		{
			logger.error(e);
			return DataSyncConstants.FAILURE_DEL;
		}
		if(result<1)
		{
			return DataSyncConstants.FAILURE_DEL_NOT_EXIST;
		}
	    return DataSyncConstants.SUCCESS_DEL;
		
    }
    /**
     * 删除不合法图书分类。不合法是指。在映射表中对应分类的id已经在货架中删除。
     * @param cateId 图书分类id
     * @return 
     */
    public void deleteIllegalCate ()
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("deleteIllegalCate");
        }
    	String sqlCode="datasync.implement.book.jidi.deleteIllegalCate().DELETE";
    	try
		{
			DB.getInstance().executeBySQLCode(sqlCode, null);
		} catch (DAOException e)
		{
			logger.error("删除不合法图书分类出错。",e);
			
		}	
    }
    /**
     * 获取分类id
     * @param cateId 图书分类id
     * @return 
     */
    public String getIdByCateId(String cateId )throws DAOException
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("getIdByCateId");
        }
    	String sqlCode="datasync.implement.book.jidi.getIdByCateId().SELECT";
    	Object paras[]= {cateId};
    	ResultSet rs=null;
    	try
		{
			rs=DB.getInstance().queryBySQLCode(sqlCode, paras);
			if(rs.next())
			{
				return rs.getString(1);
			}else
			{
				return null;
			}
		} catch (Exception e)
		{
			logger.error(e);
			throw new DAOException(e);
		}finally
		{
			DB.close(rs);
		}
		
    }
    
    
    /**
     * 新增图书推荐
     * @param cateId 图书分类id
     * @param id 货架分类id
     * @return 返回新增结果
     */
    public int insertComdBookId(String id,String bookId)
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("insertComdBookId");
        }
    	Object paras[]= {id,bookId};
    	String sqlCode="datasync.implement.book.jidi.insertComdBookId().INSERT";
    	int result;
    	try
		{
    		result=DB.getInstance().executeBySQLCode(sqlCode, paras);
    		
		} catch (DAOException e)
		{
			logger.error("新增图书推荐失败，id="+id+",bookId="+bookId,e);
			return DataSyncConstants.FAILURE_ADD;
		}
		if(result<1)
		{
			logger.error("新增图书推荐失败，id="+id+",bookId="+bookId);
			return DataSyncConstants.FAILURE_ADD;
		}else
		{
			return DataSyncConstants.SUCCESS_ADD;
		}
    	
    }
    /**
     * 检查是否存在基地推荐图书
     * @param id 货架分类id
     * @return 1 存在，0 有图书推荐的记录，但是基地推荐不存在，-1表示不存在图书分类的记录。
     */
    public int isExistedComdBookId(String id)throws DAOException
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("isExistedComdBookId"+id);
        }
    	Object paras[]= {id};
    	String sqlCode="datasync.implement.book.jidi.isExistedComdBookId().SELECT";
    	ResultSet rs=null;
    	try
		{
    		rs=DB.getInstance().queryBySQLCode(sqlCode, paras);
    		if(rs.next())
    		{
    			//仍然需要查询jbookid字段是否为空，因为有可能运营推荐事先创建了该货架id
//    			String bookId=rs.getString(1);
//    			if(bookId==null)
//    			{
//    				return 0;
//    			}else
//    			{
//    				return 1;
//    			}
    			return 1;
    		}else
    		{
    			return -1;
    		}
		} catch (Exception e)
		{
			logger.error("cateId="+id+",查询图书分类id出错",e);
			throw new DAOException(e);
		}finally
		{
			DB.close(rs);
		}
    	
    }
    /**
     * 更新基地推荐图书
     * @param Optype 操作类型 1,新增；2，修改
     * @param id 货架分类id
     * @param bookContentId 图书id
     * @return
     */
    public int updateComdBookId(int Optype,String id ,String bookContentId)
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("updateComdBookId,cateId"+id);
        }
    	int faild = DataSyncConstants.FAILURE_UPDATE;
    	int success = DataSyncConstants.SUCCESS_UPDATE;
    	
    	if(Optype == 1){//新增
    		faild = DataSyncConstants.FAILURE_ADD;
    		success = DataSyncConstants.SUCCESS_ADD;
    	}else if(Optype == 2){//修改
    		faild = DataSyncConstants.FAILURE_UPDATE;
    		success = DataSyncConstants.SUCCESS_UPDATE;
    	}
    	Object paras[]= {bookContentId,id};
    	String sqlCode="datasync.implement.book.jidi.updateComdBookId().UPDATE";
    	int result;
    	try
		{
			result=DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (DAOException e)
		{
			logger.error("updateComdBookId出错，货架分类Id="+id,e);
			//return DataSyncConstants.FAILURE_UPDATE;
			return faild;
		}
		if(result<1)
		{
			logger.error("updateComdBookId出错，不存在该货架分类id，货架分类Id="+id);
			//return DataSyncConstants.FAILURE_UPDATE;
			return faild;
		}else
		{
			//return DataSyncConstants.SUCCESS_UPDATE;
			return success;
		}   
    }
    /**
     * 更新运营推荐图书
     * @param id 货架分类id
     * @param bookId 图书id
     * @return
     */
    public int updateYYComdBookId(String id ,String bookContentId)
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("updateYYComdBookId,cateId"+id);
        }
    	Object paras[]= {bookContentId,id};
    	String sqlCode="datasync.implement.book.jidi.updateYYComdBookId().UPDATE";
    	int result;
    	try
		{
			result=DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (DAOException e)
		{
			logger.error("更新运营推荐图书出错，货架分类Id="+id,e);
			return DataSyncConstants.FAILURE_UPDATE;
		}
		if(result<1)
		{
			logger.error("更新运营推荐图书出错，不存在该货架分类，货架分类Id="+id);
			return DataSyncConstants.FAILURE_UPDATE;
		}else
		{
			return DataSyncConstants.SUCCESS_UPDATE;
		}   
    }
    /**
     * 删除推荐图书
     * @param id 货架分类id
     * @return 
     */
    public int deleteComdBookId(String id )
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("deleteComdBookId");
        }
    	String sqlCode="datasync.implement.book.jidi.deleteComdBookId().DELETE";
    	Object paras[]= {id};
    	try
		{
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (DAOException e)
		{
			logger.error(e);
			return DataSyncConstants.FAILURE_DEL;
		}
		/*if(result<1)//如果不存在该推荐分类，删除也算成功吧。
		{
			logger.error("删除推荐图书失败。");
			return DataSyncConstants.FAILURE_DEL_NOT_EXIST;
		}*/
	    return DataSyncConstants.SUCCESS_DEL;
		
    }
    /**
     * 删除基地推荐图书
     * @param id 货架分类id
     * @param bookId
     * @return 
     */
    //public int deleteJDComdBookId(String id ,String bookId)
    public int deleteJDComdBookId(String id )

    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("deleteJDComdBookId");
        }
    	String sqlCode="datasync.implement.book.jidi.deleteJDComdBookId().UPDATE";
    	Object paras[]= {id};
    	try
		{
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (DAOException e)
		{
			logger.error(e);
			return DataSyncConstants.FAILURE_DEL;
		}
		/*if(result<1)//如果不存在该推荐分类，删除也算成功吧。
		{
			logger.error("删除推荐图书失败。");
			return DataSyncConstants.FAILURE_DEL_NOT_EXIST;
		}*/
	    return DataSyncConstants.SUCCESS_DEL;
		
    }
    /**
     * 删除冗余的推荐分类。
     * @param cateId 图书分类id
     * @return 
     */
    public void deleteIllegalComd ()
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("deleteIllegalComd");
        }
    	String sqlCode="datasync.implement.book.jidi.deleteIllegalComd().DELETE";
    	try
		{
			DB.getInstance().executeBySQLCode(sqlCode, null);
		} catch (DAOException e)
		{
			logger.error("删除不合法图书分类出错。",e);
			
		}	
    }
    /**
     * 获取当前被推荐的所有货架。
     */
    public List getAllCommendCate()throws DAOException
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("getAllCommendCate");
        }
    	String sqlCode="datasync.implement.book.jidi.getAllCommendCate().SELECT";
    	ResultSet rs=null;
    	List cateList=new ArrayList();
    	try
		{
			rs= DB.getInstance().queryBySQLCode(sqlCode, null);
			while(rs.next())
			{
				cateList.add(rs.getString(1));
			}
		} catch (Exception e)
		{
			throw new DAOException("获取所有基地图书分类异常。",e);
		}finally
		{
			DB.close(rs);
		}
		return cateList;
    }

    /**
     * 随即获取当前货架下面一个商品对应的内容的id
     * @param cateId 当前货架的id
     * @return 该货架下商品对应的内容id
     * @throws DAOException
     */
    public String getRandomBookIdInBookCategory(String cateId)throws DAOException
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("getRandomBookIdInBookCategory");
        }
    	
    	String categoryCode= CategoryDAO.getInstance().getCategoryIDByID(cateId);
    	String sqlCode="datasync.implement.book.jidi.getRandomBookIdInBookCategory().SELECT";
    	ResultSet rs=null;
    	try
		{
    		String[] paras= {categoryCode};
			rs= DB.getInstance().queryBySQLCode(sqlCode, paras);
			if(rs.next())
			{
				return rs.getString(1);
			}else
			{
				return null;
			}
		} catch (Exception e)
		{
			throw new DAOException("获取所有基地图书分类异常。",e);
		}finally
		{
			DB.close(rs);
		}
		
    }
   /* *//**
     * 获取分类id
     * @param cateId 图书分类id
     * @return 
     *//*
    public String getIdByCateId(String cateId )throws DAOException
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("getIdByCateId");
        }
    	String sqlCode="datasync.implement.book.jidi.getIdByCateId().DELETE";
    	Object paras[]= {cateId};
    	ResultSet rs=null;
    	try
		{
			rs=DB.getInstance().queryBySQLCode(sqlCode, paras);
			if(rs.next())
			{
				return rs.getString(1);
			}else
			{
				return null;
			}
		} catch (Exception e)
		{
			logger.error(e);
			throw new DAOException(e);
		}
		
    }
*/
}
