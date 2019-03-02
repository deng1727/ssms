/*
 * �ļ�����NewMusicCategoryBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.ponaadmin.web.newmusicsys.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.datafield.dao.KeyResourceDAO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.newmusicsys.WriteActionLogAndEmailTask;
import com.aspire.ponaadmin.web.newmusicsys.dao.CategoryDAO;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicCategoryDAO;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicCategoryVO;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class NewMusicCategoryBO
{
    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(NewMusicCategoryBO.class);

    private static NewMusicCategoryBO instance = new NewMusicCategoryBO();

    private NewMusicCategoryBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static NewMusicCategoryBO getInstance()
    {
        return instance;
    }
    
    /**
     * ���ڷ��������ֻ����б�
     * @return
     * @throws BOException
     */
    public List queryNewMusicCategoryList() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.queryNewMusicCategoryList( ) is start...");
        }

        try
        {
            // ����NewMusicCategoryDAO���в�ѯ
            return NewMusicCategoryDAO.getInstance()
                                      .queryNewMusicCategoryList();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���������ֻ����б�ʱ�������ݿ��쳣��");
        }
    }
   
    /**
     * ���ڷ��������ֻ�����Ϣ
     * @return
     * @throws BOException
     */
    public NewMusicCategoryVO queryNewMusicCategoryVO(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.queryNewMusicCategoryVO("+categoryId+") is start...");
        }

        try
        {
            // ����NewMusicCategoryDAO���в�ѯ
            return NewMusicCategoryDAO.getInstance()
                                      .queryNewMusicCategoryVO(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���������ֻ�����Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ���ص�ǰ���ܵ��ӻ�����Ϣ�����ֻ�����Ϣ
     * @return
     * @throws BOException
     */
    public int hasNewMusicChild(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.hasNewMusicChild("+categoryId+") is start...");
        }

        try
        {
            // ����NewMusicCategoryDAO���в�ѯ
            return NewMusicCategoryDAO.getInstance()
                                      .hasNewMusicChild(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ص�ǰ���ܵ��ӻ�����Ϣ�����ֻ�����Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �鿴��ǰ�������Ƿ񻹴�������Ʒ
     * @param categoryId
     * @return
     * @throws BOException
     */
    public int hasNewMusic(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.hasNewMusic("+categoryId+") is start...");
        }

        try
        {
            // ����NewMusicRefDAO���в�ѯ
            return NewMusicRefDAO.getInstance()
                                      .hasNewMusic(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ص�ǰ���ܵ��ӻ�����Ϣ�����ֻ�����Ϣʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ����ɾ��ָ������
     * @param tdb
     * @param categoryId
     * @throws BOException
     */
    public void delNewMusicCategory(TransactionDB tdb,String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.delNewMusicCategory("+categoryId+") is start...");
        }

        try
        {
            // ����NewMusicCategoryDAO����ɾ��
            NewMusicCategoryDAO.getInstance()
                                      .delNewMusicCategory(tdb,categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("ɾ��ָ������ʱ�������ݿ��쳣��");
        }
    }
    /**
	 * ���ڱ�������ֻ���
	 * 
	 * @param newMusicCategory
	 * @throws BOException
	 */
    public void updateNewMusicCategory(NewMusicCategoryVO newMusicCategory) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.updateNewMusicCategory() is start...");
        }

        try
        {
            // ����NewMusicCategoryDAO���б�������ֻ���
            NewMusicCategoryDAO.getInstance()
                                      .updateNewMusicCategory(newMusicCategory);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��������ֻ���ʱ�������ݿ��쳣��");
        }
    }
    /**
     * �������������ֻ���
     * @param newMusicCategory
     * @throws BOException
     */
    public void saveNewMusicCategory(NewMusicCategoryVO newMusicCategory) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.saveNewMusicCategory() is start...");
        }

        try
        {
            // ����NewMusicCategoryDAO�������������ֻ���
            NewMusicCategoryDAO.getInstance()
                                      .saveNewMusicCategory(newMusicCategory);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���������ֻ���ʱ�������ݿ��쳣��");
        }
    }
    
   /**********************/
    /**
     * ���ڷ��������ֻ�����չ�ֶ��б�
     * @return
     * @throws BOException
     */
    public List queryNewMusicCategoryKeyBaseList(String tid) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("NewMusicCategoryBO.queryNewMusicCategoryKeyBaseList( ) is start...");
        }

        try
        {
            // ����NewMusicCategoryDAO���в�ѯ
            return NewMusicCategoryDAO.getInstance()
                                      .queryNewMusicCategoryKeyBaseList(tid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���������ֻ�����չ�ֶ��б�ʱ�������ݿ��쳣��",e);
        }
    }
    
    /**
	 * ����ɾ�������ֻ��ܱ�����չ�ֶ�
	 * 
	 * @param newMusicCategory
	 * @throws BOException
	 */
    public int delNewMusicCategoryKeyResource(TransactionDB tdb,String tid) throws BOException
    {
		if (logger.isDebugEnabled())
		{
			logger
					.debug("NewMusicCategoryBO.delNewMusicCategoryKeyResource() is start...");
		}
		int r = 0;
		try
		{
			// ����NewMusicCategoryDAO�������������ֻ���
			r = NewMusicCategoryDAO.getInstance().delNewMusicCategoryKeyResource(tdb,tid);
		} catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("ɾ�����ֻ�����չ�ֶ�ֵʱ�����쳣��", e);
		}
		return r;
	}
    /**
	 * ���ڻ�ȡ�����ֻ���ID
	 * 
	 * @param newMusicCategory
	 * @throws BOException
	 */
    public String  getNewMusicCategoryId() throws BOException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("NewMusicCategoryBO.saveNewMusicCategory() is start...");
		}
		String cid = null;
		try
		{
			// ����NewMusicCategoryDAO�������������ֻ���
			cid = NewMusicCategoryDAO.getInstance().getNewMusicCategoryId();
		} catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("���������ֻ���ʱ�������ݿ��쳣��");
		}
		return cid;
	}

    
    /**
     * 
     *@desc ����
     *@author dongke
     *Aug 9, 2011
     * @param keyBaseList
     * @throws BOException
     */
    public void saveKeyResource(List keyBaseList) throws BOException
	{
		for (int i = 0; i < keyBaseList.size(); i++)
		{
			ResourceVO vo = (ResourceVO) keyBaseList.get(i);
			try
			{
				if (vo != null && vo.getValue() != null && !vo.getValue().equals(""))
				{
					KeyResourceDAO.getInstance().updateResourceValue(vo.getKeyid(),
							vo.getValue(), vo.getTid());
				}
			} catch (DAOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BOException("������չֵʧ��", e);
			}
		}
	}
    
	/**
     * ����ɾ��ָ������
     * 
     * @return
     * @throws BOException
     */
    public void delMusicCategoryItem(String categoryId) throws BOException  
    {
    	 if (logger.isDebugEnabled())
         {
             logger.debug("NewMusicCategoryBO.delMusicCategoryItem(" + categoryId
                          + ") is start...");
         }

         try
         {
             // ����NewMusicCategoryDAO����ɾ��
        	 NewMusicCategoryDAO.getInstance().delMusicCategoryItem(categoryId);
         }
         catch (DAOException e)
         {
             logger.error(e);
             throw new BOException("ɾ��ָ������ʱ�������ݿ��쳣��");
         }
    }
    
    /**
   	 * �ύ����
   	 * 
   	 * @param categoryId ���ܱ��
   	 * @throws BOException
   	 */
   	public void approvalCategory(HttpServletRequest request,String categoryId) throws BOException {
   		UserVO logUser = null;
   		UserSessionVO userSessionVO = UserManagerBO.getInstance()
   				.getUserSessionVO(request.getSession());

   		if (userSessionVO != null) {
   			logUser = userSessionVO.getUser();
   		} else {
   			logUser = new UserVO();
   			logUser.setName("unknow");
   		}
   		// �����������
   		TransactionDB tdb = null;
           try
           {
           	tdb = TransactionDB.getTransactionInstance();
            CategoryDAO.getInstance().approvalCategory(tdb,categoryId);
            CategoryDAO.getInstance().approvalCategory(tdb, categoryId, "2","1", logUser.getName());
            Map<String, Object> map = new HashMap<String, Object>();
   			map.put("mailTitle", "��������ͻ����Ż���Ӫ���ݵ�������");
   			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�����������ֻ��ܹ���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����;<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��" + "�ύ����";
   			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�����������ֻ��ܹ���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 3) + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��" + "�ύ����";
   			map.put("mailContent", value);
   			map.put("status", "0");
   			map.put("categoryId", categoryId);
   			map.put("operation", "50");
   			map.put("operationtype", "���ֻ��ܹ����ύ����");
   			map.put("operationobj", "���ֻ��ܹ���");
   			map.put("operationobjtype", "����Id��" + categoryId);
   			map.put("operator", logUser.getName());
   			WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
   			DaemonTaskRunner.getInstance().addTask(task);
   			tdb.commit();
           }
           catch (DAOException e)
           {
           	tdb.rollback();
               logger.error(e);
               throw new BOException("����ָ������ʱ�������ݿ��쳣��");
           }finally {
   			if (tdb != null) {
   				tdb.close();
   			}
   		}
   	}
    
}
