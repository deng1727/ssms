package com.aspire.ponaadmin.web.comic.bo;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.comic.WriteActionLogAndEmailTask;
import com.aspire.ponaadmin.web.comic.dao.CategoryDAO;
import com.aspire.ponaadmin.web.comic.vo.CategoryVO;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicCategoryDAO;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicRefDAO;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;



public class CategoryBO
{
    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(CategoryBO.class);

    private static CategoryBO instance = new CategoryBO();

    private CategoryBO(){
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CategoryBO getInstance()
    {
        return instance;
    }
    public CategoryVO queryCategoryVO(String categoryId) throws BOException
    {
        
        try
        {
            return CategoryDAO.getInstance()
                                      .queryCategoryVO(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ض���������Ϣʱ�������ݿ��쳣��");
        }
    }

	public String getComicCategoryId() throws BOException {
		// TODO Auto-generated method stub
		String cid = null;
		try
		{
			// ����NewMusicCategoryDAO�������������ֻ���
			cid = CategoryDAO.getInstance().getComicCategoryId();
		} catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("��ȡ��������ʱ�������ݿ��쳣��");
		}
		return cid;
	}

	public void saveCategory(CategoryVO categoryVO) throws BOException {
        try
        {
            CategoryDAO.getInstance()
                                      .saveCategory(categoryVO);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("������������ʱ�������ݿ��쳣��");
        }
	}

	public void updateCategory(CategoryVO categoryVO) throws BOException {
		// TODO Auto-generated method stub
        try
        {
            CategoryDAO.getInstance()
                                      .updateCategory(categoryVO);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�����������ʱ�������ݿ��쳣��");
        }
	}

	public int hasChild(String categoryId) throws BOException {
		// TODO Auto-generated method stub
        try
        {
            return CategoryDAO.getInstance()
                                      .hasChild(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ص�ǰ���ܵ��ӻ�����Ŀʱ�������ݿ��쳣��");
        }
	}

	public int hasReference(String categoryId) throws BOException {
		// TODO Auto-generated method stub
        try
        {
            // ����NewMusicRefDAO���в�ѯ
            return CategoryDAO.getInstance()
                                      .hasReference(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ص�ǰ���ܵ���Ʒ��Ŀʱ�����쳣��");
        }
	}

	public void delCategory(String categoryId) throws BOException {
		// TODO Auto-generated method stub
        try
        {
            // ����NewMusicCategoryDAO����ɾ��
            CategoryDAO.getInstance()
                                      .delCategory(categoryId);
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
			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺶������ܹ���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����;<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��" + "�ύ����";
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺶������ܹ���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 4) + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��" + "�ύ����";
			map.put("mailContent", value);
			map.put("status", "0");
			map.put("categoryId", categoryId);
			map.put("operation", "70");
			map.put("operationtype", "�������ܹ����ύ����");
			map.put("operationobj", "�������ܹ���");
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
