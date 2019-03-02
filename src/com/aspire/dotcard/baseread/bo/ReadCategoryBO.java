/*
 * �ļ�����ReadCategoryBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseread.bo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.WriteActionLogAndEmailTask;
import com.aspire.dotcard.baseread.config.BaseReadConfig;
import com.aspire.dotcard.baseread.dao.BookCategoryDao;
import com.aspire.dotcard.baseread.dao.CategoryDAO;
import com.aspire.dotcard.baseread.dao.ReadCategoryDAO;
import com.aspire.dotcard.baseread.dao.ReadReferenceDAO;
import com.aspire.dotcard.baseread.vo.ReadCategoryVO;
import com.aspire.dotcard.baseread.vo.ReadReferenceVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.datafield.dao.KeyResourceDAO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.datasync.DataSyncTools;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;
import com.aspire.ponaadmin.web.system.SystemConfig;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class ReadCategoryBO
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(ReadCategoryBO.class);

    private static ReadCategoryBO instance = new ReadCategoryBO();

    private ReadCategoryBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static ReadCategoryBO getInstance()
    {
        return instance;
    }
    
    /**
     * ����Ψһ����id
     * @return
     */
    public String getReadVOId() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadCategoryBO.getReadVOId( ) is start...");
        }

        try
        {
            // ����ReadCategoryDAO���в�ѯ
            return ReadCategoryDAO.getInstance().getReadId();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����Ψһ����idʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ����Ψһ�Զ������id
     * @return
     */
    public String getReadVOCategoryId() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadCategoryBO.getReadVOCategoryId( ) is start...");
        }

        try
        {
            // ����ReadCategoryDAO���в�ѯ
            return ReadCategoryDAO.getInstance().getReadCategoryId();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����Ψһ����idʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڷ���ͼ�������Ϣ
     * 
     * @return
     * @throws BOException
     */
    public ReadCategoryVO queryReadCategoryVO(String categoryId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadCategoryBO.queryBookCategoryVO(" + categoryId
                         + ") is start...");
        }

        try
        {
            // ����BookCategoryDAO���в�ѯ
            return ReadCategoryDAO.getInstance()
                                  .queryReadCategoryVO(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����ͼ�������Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ص�ǰ���ܵ��ӻ�����Ϣ�Ķ�������Ϣ
     * 
     * @return
     * @throws BOException
     */
    public int hasReadChild(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadCategoryBO.hasReadChild(" + categoryId
                         + ") is start...");
        }

        try
        {
            // ����ReadCategoryDAO���в�ѯ
            return ReadCategoryDAO.getInstance().hasReadChild(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ص�ǰ���ܵ��ӻ�����Ϣ�Ķ�������Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * �鿴��ǰ�������Ƿ񻹴�������Ʒ
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public int hasRead(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadCategoryBO.hasRead(" + categoryId
                         + ") is start...");
        }

        try
        {
            // ����ReadReferenceDAO���в�ѯ
            return ReadReferenceDAO.getInstance().hasRead(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ص�ǰ���ܵ��ӻ�����Ϣ�Ķ�������Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���������Ķ�����
     * 
     * @param BookCategoryVO
     * @throws BOException
     */
    public void saveReadCategory(ReadCategoryVO readVO) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadCategoryBO.saveReadCategory() is start...");
        }

        try
        {
            // ����ReadCategoryDAO�������������ֻ���
            ReadCategoryDAO.getInstance().saveReadCategory(readVO);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("�����Ķ�����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ����ɾ��ָ������
     * 
     * @param categoryId
     * @throws BOException
     */
    public void delReadCategory(TransactionDB tdb,String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadCategoryBO.delReadCategory(" + categoryId
                         + ") is start...");
        }

        try
        {
            // ����ReadCategoryDAO����ɾ��
            ReadCategoryDAO.getInstance().delReadCategory(tdb,categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("ɾ��ָ������ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڱ���Ķ�����
     * 
     * @param BookCategoryVO
     * @throws BOException
     */
    public void updateReadCategory(ReadCategoryVO readVO) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadCategoryBO.updateReadCategory() is start...");
        }

        try
        {
            // ����ReadCategoryDAO���б�������ֻ���
            ReadCategoryDAO.getInstance().updateReadCategory(readVO);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����Ķ�����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ��ǰ���������Ƿ��Ѵ�����ͬ���ƻ���
     * 
     * @param parentId
     * @param name
     * @param catalogType
     */
    public void hasReadNameInParentId(String parentId, String name) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadCategoryBO.hasReadNameInParentId(" + parentId
                         + ") is start...");
        }

        int number;

        try
        {
            // ��ǰ���������Ƿ��Ѵ�����ͬ���ƻ���
            number = ReadCategoryDAO.getInstance()
                                  .hasReadNameInParentId(parentId, name);

            // �鿴�Ƿ����
            if (number > 0)
            {
                throw new BOException("���ڼ�鵱ǰ���������Ƿ��Ѵ�����ͬ���ƻ���ʱ�������ݿ��쳣��",
                                      RepositoryBOCode.ADD_UPDATE_CATEGORY_NAME);
            }
        }
        catch (DAOException e)
        {
            throw new BOException("���ڼ�鵱ǰ���������Ƿ��Ѵ�����ͬ���ƻ���ʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * �ϴ����ܵ�Ԥ��ͼ��ͼƬ
     * @param request
     * @throws BOException
     */
    public String uploadCatePicURL(FormFile uploadFile,String cateId)throws BOException
    {
        String tempDir = ServerInfo.getAppRootPath() + File.separator + "read"
                         + File.separator;
        String ftpDir = BaseReadConfig.get("bookPicURl");
        
        IOUtil.checkAndCreateDir(tempDir);

        // ����ļ���������ļ�������·����
        String fileName = uploadFile.getFileName();
        String suffex = fileName.substring(fileName.lastIndexOf('.'));

        // ƴװ��ʱ�ļ���Ϊ temp.png
        String oldFileName = tempDir + "temp" + suffex;

        try
        {
            IOUtil.writeToFile(oldFileName, uploadFile.getFileData());
        }
        catch (Exception e1)
        {
            throw new BOException("д����ʱ�ļ�ʧ�ܡ�tempFilePath=" + oldFileName,
                                  RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
        }

        // ��Ҫ�������ֲ�ͬ����ͼƬ
        String logo1Path = tempDir + "logo1.png";
        String logo2Path = tempDir + "logo2.png";
        String logo3Path = tempDir + "logo3.png";
        String logo4Path = tempDir + "logo4.png";

        try
        {
            DataSyncTools.scaleIMG(oldFileName, logo1Path, 30, 30, "png");
            DataSyncTools.scaleIMG(oldFileName, logo2Path, 34, 34, "png");
            DataSyncTools.scaleIMG(oldFileName, logo3Path, 50, 50, "png");
            DataSyncTools.scaleIMG(oldFileName, logo4Path, 65, 65, "png");
        }
        catch (Exception e)
        {
            throw new BOException("ͼƬת������FileName=" + oldFileName,
                                  e,
                                  RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
        }

        FTPClient ftp = null;
        try
        {
            ftp = PublicUtil.getFTPClient(SystemConfig.SOURCESERVERIP,
                                          SystemConfig.SOURCESERVERPORT,
                                          SystemConfig.SOURCESERVERUSER,
                                          SystemConfig.SOURCESERVERPASSWORD,
                                          ftpDir);
            PublicUtil.checkAndCreateDir(ftp, cateId);
            ftp.chdir(cateId);
            ftp.put(logo1Path, "logo1.png");
            ftp.put(logo2Path, "logo2.png");
            ftp.put(logo3Path, "logo3.png");
            ftp.put(logo4Path, "logo4.png");
        }
        catch (Exception e)
        {
            throw new BOException("�ϴ�����Դ�����������쳣��",
                                  e,
                                  RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
        }
        finally
        {
            if (ftp != null)
            {
                try
                {
                    ftp.quit();
                }
                catch (Exception e)
                {
                }
            }
        }
        return SystemConfig.URL_PIC_VISIT + BaseReadConfig.get("bookPicURl")
               + cateId + "/";
    }
   
    /**************************/
    
    
    /**
     * ���ڷ���ͼ�������չ�ֶ��б�
     * @return
     * @throws BOException
     */
    public List queryReadCategoryKeyBaseList(String tid) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadCategoryBO.queryReadCategoryKeyBaseList( ) is start...");
        }

        try
        {
            // ����NewMusicCategoryDAO���в�ѯ
            return ReadCategoryDAO.getInstance()
                                      .queryReadCategoryKeyBaseList(tid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����ͼ�������չ�ֶ��б�ʱ�������ݿ��쳣��",e);
        }
    }

    /**
	 * ����ɾ��ͼ����ܱ�����չ�ֶ�
	 * 
	 * @param newMusicCategory
	 * @throws BOException
	 */
    public int delReadCategoryKeyResource(TransactionDB tdb,String tid) throws BOException
    {
		if (logger.isDebugEnabled())
		{
			logger
					.debug("ReadCategoryBO.delReadCategoryKeyResource() is start...");
		}
		int r = 0;
		try
		{
	
			r = ReadCategoryDAO.getInstance().delReadCategoryKeyResource(tdb,tid);
		} catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("ɾ��ͼ�������չ�ֶ�ֵʱ�����쳣��", e);
		}
		return r;
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
     * ���ڲ�ѯ�����Ķ����������б�
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryCategoryDescList(PageResult page, ReadCategoryVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("ReadCategoryBO.queryCategoryDescList( ) is start...");
        }

        try
        {
            // ����ReadReferenceDAO���в�ѯ
        	ReadCategoryDAO.getInstance().queryCategoryDescList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ڲ�ѯ�����Ķ����������б�ʱ�������ݿ��쳣��");
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
   			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;��������ͼ����ܹ���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����;<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��" + "�ύ����";
   			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;��������ͼ����ܹ���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 1) + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��" + "�ύ����";
   			map.put("mailContent", value);
   			map.put("status", "0");
   			map.put("categoryId", categoryId);
   			map.put("operation", "60");
   			map.put("operationtype", "ͼ����ܹ����ύ����");
   			map.put("operationobj", "ͼ����ܹ���");
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
   	
   	/**
     * ����ɾ��ָ������
     * 
     * @return
     * @throws BOException
     */
    public void delBookCategoryItem(String categoryId) throws BOException  
    {
    	 if (logger.isDebugEnabled())
         {
             logger.debug("BookCategoryBO.delBookCategoryItem(" + categoryId
                          + ") is start...");
         }

         try
         {
             // ����BookCategoryDao����ɾ��
        	 BookCategoryDao.getInstance().delBookCategoryItem(categoryId);
         }
         catch (DAOException e)
         {
             logger.error(e);
             throw new BOException("ɾ��ָ������ʱ�������ݿ��쳣��");
         }
    }
}
