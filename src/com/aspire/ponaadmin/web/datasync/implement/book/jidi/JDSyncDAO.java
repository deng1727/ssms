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
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(JDSyncDAO.class);
    
    private static JDSyncDAO dao=new JDSyncDAO();
    
    public static JDSyncDAO getInstance()
    {
    	return dao;
    }
	
	/**
     * �������ּ�¼
     * @param vo ����vo��
     * @return �����������
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
			logger.error("�������ּ�¼�쳣",e);
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
     * �������Ƿ����
     * @param vo ����vo��
     * @return �����������
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
     * ��ȡ������Ϣ
     * @param authorId ����id
     * @return  ������Ϣ�������ڣ����߲�ѯ�쳣����null
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
     * ɾ������
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
     * ����ͼ�����id�ͻ��ܷ����ӳ��
     * @param cateId ͼ�����id
     * @param id ���ܷ���id
     * @return �����������
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
			logger.error("����ͼ�����id�ͻ��ܷ����ӳ��",e);
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
     * ����Ƿ����ͼ�����id�ͻ��ܷ����ӳ��
     * @param cateId ͼ�����id
     * @return ture ���ڣ�false ������
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
			logger.error("cateId="+cateId+",��ѯͼ�����id����",e);
			throw new DAOException(e);
		}finally
		{
			DB.close(rs);
		}
    	
    }
    /**
     * ����ͼ�����id�ͻ��ܷ����ӳ��
     * @param cateId ͼ�����id
     * @param id ���ܷ���id
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
			logger.error("updateCateId����cateId="+cateId,e);
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
     * ɾͼ�����id�ͻ��ܷ����ӳ��
     * @param cateId ͼ�����id
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
     * ɾ�����Ϸ�ͼ����ࡣ���Ϸ���ָ����ӳ����ж�Ӧ�����id�Ѿ��ڻ�����ɾ����
     * @param cateId ͼ�����id
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
			logger.error("ɾ�����Ϸ�ͼ��������",e);
			
		}	
    }
    /**
     * ��ȡ����id
     * @param cateId ͼ�����id
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
     * ����ͼ���Ƽ�
     * @param cateId ͼ�����id
     * @param id ���ܷ���id
     * @return �����������
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
			logger.error("����ͼ���Ƽ�ʧ�ܣ�id="+id+",bookId="+bookId,e);
			return DataSyncConstants.FAILURE_ADD;
		}
		if(result<1)
		{
			logger.error("����ͼ���Ƽ�ʧ�ܣ�id="+id+",bookId="+bookId);
			return DataSyncConstants.FAILURE_ADD;
		}else
		{
			return DataSyncConstants.SUCCESS_ADD;
		}
    	
    }
    /**
     * ����Ƿ���ڻ����Ƽ�ͼ��
     * @param id ���ܷ���id
     * @return 1 ���ڣ�0 ��ͼ���Ƽ��ļ�¼�����ǻ����Ƽ������ڣ�-1��ʾ������ͼ�����ļ�¼��
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
    			//��Ȼ��Ҫ��ѯjbookid�ֶ��Ƿ�Ϊ�գ���Ϊ�п�����Ӫ�Ƽ����ȴ����˸û���id
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
			logger.error("cateId="+id+",��ѯͼ�����id����",e);
			throw new DAOException(e);
		}finally
		{
			DB.close(rs);
		}
    	
    }
    /**
     * ���»����Ƽ�ͼ��
     * @param Optype �������� 1,������2���޸�
     * @param id ���ܷ���id
     * @param bookContentId ͼ��id
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
    	
    	if(Optype == 1){//����
    		faild = DataSyncConstants.FAILURE_ADD;
    		success = DataSyncConstants.SUCCESS_ADD;
    	}else if(Optype == 2){//�޸�
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
			logger.error("updateComdBookId�������ܷ���Id="+id,e);
			//return DataSyncConstants.FAILURE_UPDATE;
			return faild;
		}
		if(result<1)
		{
			logger.error("updateComdBookId���������ڸû��ܷ���id�����ܷ���Id="+id);
			//return DataSyncConstants.FAILURE_UPDATE;
			return faild;
		}else
		{
			//return DataSyncConstants.SUCCESS_UPDATE;
			return success;
		}   
    }
    /**
     * ������Ӫ�Ƽ�ͼ��
     * @param id ���ܷ���id
     * @param bookId ͼ��id
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
			logger.error("������Ӫ�Ƽ�ͼ��������ܷ���Id="+id,e);
			return DataSyncConstants.FAILURE_UPDATE;
		}
		if(result<1)
		{
			logger.error("������Ӫ�Ƽ�ͼ����������ڸû��ܷ��࣬���ܷ���Id="+id);
			return DataSyncConstants.FAILURE_UPDATE;
		}else
		{
			return DataSyncConstants.SUCCESS_UPDATE;
		}   
    }
    /**
     * ɾ���Ƽ�ͼ��
     * @param id ���ܷ���id
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
		/*if(result<1)//��������ڸ��Ƽ����࣬ɾ��Ҳ��ɹ��ɡ�
		{
			logger.error("ɾ���Ƽ�ͼ��ʧ�ܡ�");
			return DataSyncConstants.FAILURE_DEL_NOT_EXIST;
		}*/
	    return DataSyncConstants.SUCCESS_DEL;
		
    }
    /**
     * ɾ�������Ƽ�ͼ��
     * @param id ���ܷ���id
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
		/*if(result<1)//��������ڸ��Ƽ����࣬ɾ��Ҳ��ɹ��ɡ�
		{
			logger.error("ɾ���Ƽ�ͼ��ʧ�ܡ�");
			return DataSyncConstants.FAILURE_DEL_NOT_EXIST;
		}*/
	    return DataSyncConstants.SUCCESS_DEL;
		
    }
    /**
     * ɾ��������Ƽ����ࡣ
     * @param cateId ͼ�����id
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
			logger.error("ɾ�����Ϸ�ͼ��������",e);
			
		}	
    }
    /**
     * ��ȡ��ǰ���Ƽ������л��ܡ�
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
			throw new DAOException("��ȡ���л���ͼ������쳣��",e);
		}finally
		{
			DB.close(rs);
		}
		return cateList;
    }

    /**
     * �漴��ȡ��ǰ��������һ����Ʒ��Ӧ�����ݵ�id
     * @param cateId ��ǰ���ܵ�id
     * @return �û�������Ʒ��Ӧ������id
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
			throw new DAOException("��ȡ���л���ͼ������쳣��",e);
		}finally
		{
			DB.close(rs);
		}
		
    }
   /* *//**
     * ��ȡ����id
     * @param cateId ͼ�����id
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
