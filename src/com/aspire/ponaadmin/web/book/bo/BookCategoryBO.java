/*
 * �ļ�����BookCategoryBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.ponaadmin.web.book.bo;

import java.io.File;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basebook.config.BaseBookConfig;
import com.aspire.ponaadmin.web.book.dao.BookCategoryDAO;
import com.aspire.ponaadmin.web.book.dao.BookRefDAO;
import com.aspire.ponaadmin.web.book.vo.BookCategoryVO;
import com.aspire.ponaadmin.web.datafield.dao.KeyResourceDAO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.datasync.DataSyncTools;
import com.aspire.ponaadmin.web.newmusicsys.bo.NewMusicCategoryBO;
import com.aspire.ponaadmin.web.newmusicsys.dao.NewMusicCategoryDAO;
import com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicCategoryVO;
import com.aspire.ponaadmin.web.repository.RepositoryBOCode;
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
public class BookCategoryBO
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BookCategoryBO.class);

    private static BookCategoryBO instance = new BookCategoryBO();

    private BookCategoryBO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static BookCategoryBO getInstance()
    {
        return instance;
    }
    
    /**
     * ����Ψһ����id
     * @return
     */
    public String getBookVOId() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookCategoryBO.getBookVOId( ) is start...");
        }

        try
        {
            // ����BookCategoryDAO���в�ѯ
            return BookCategoryDAO.getInstance().getBookCategoryId();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����Ψһ����idʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڷ���ͼ������б�
     * 
     * @return
     * @throws BOException
     */
    public List queryBookCategoryList() throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookCategoryBO.queryBookCategoryList( ) is start...");
        }

        try
        {
            // ����BookCategoryDAO���в�ѯ
            return BookCategoryDAO.getInstance().queryBookCategoryList();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����ͼ������б�ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڷ���ͼ�������Ϣ
     * 
     * @return
     * @throws BOException
     */
    public BookCategoryVO queryBookCategoryVO(String categoryId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookCategoryBO.queryBookCategoryVO(" + categoryId
                         + ") is start...");
        }

        try
        {
            // ����BookCategoryDAO���в�ѯ
            return BookCategoryDAO.getInstance()
                                  .queryBookCategoryVO(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����ͼ�������Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ص�ǰ���ܵ��ӻ�����Ϣͼ�������Ϣ
     * 
     * @return
     * @throws BOException
     */
    public int hasBookChild(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookCategoryBO.hasBookChild(" + categoryId
                         + ") is start...");
        }

        try
        {
            // ����BookCategoryDAO���в�ѯ
            return BookCategoryDAO.getInstance().hasBookChild(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ص�ǰ���ܵ��ӻ�����Ϣͼ�������Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * �鿴��ǰ�������Ƿ񻹴�������Ʒ
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public int hasBook(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookCategoryBO.hasBook(" + categoryId
                         + ") is start...");
        }

        try
        {
            // ����BookRefDAO���в�ѯ
            return BookRefDAO.getInstance().hasBook(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ص�ǰ���ܵ��ӻ�����Ϣͼ�������Ϣʱ�������ݿ��쳣��");
        }
    }

    /**
     * ��������ͼ�����
     * 
     * @param BookCategoryVO
     * @throws BOException
     */
    public void saveBookCategory(BookCategoryVO bookVO) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookCategoryBO.saveBookCategory() is start...");
        }

        try
        {
            // ����BookCategoryDAO�������������ֻ���
            BookCategoryDAO.getInstance().saveBookCategory(bookVO);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����ͼ�����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ����ɾ��ָ������
     * 
     * @param categoryId
     * @throws BOException
     */
    public void delBookCategory(String categoryId) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookCategoryBO.delBookCategory(" + categoryId
                         + ") is start...");
        }

        try
        {
            // ����BookCategoryDAO����ɾ��
            BookCategoryDAO.getInstance().delBookCategory(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("ɾ��ָ������ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ���ڱ��ͼ�����
     * 
     * @param BookCategoryVO
     * @throws BOException
     */
    public void updateBookCategory(BookCategoryVO bookVO) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookCategoryBO.updateBookCategory() is start...");
        }

        try
        {
            // ����BookCategoryDAO���б�������ֻ���
            BookCategoryDAO.getInstance().updateBookCategory(bookVO);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("���ͼ�����ʱ�������ݿ��쳣��");
        }
    }

    /**
     * ��ǰ���������Ƿ��Ѵ�����ͬ���ƻ���
     * 
     * @param parentId
     * @param name
     */
    public void hasBookNameInParentId(String parentId, String name) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookCategoryBO.hasBookNameInParentId(" + parentId
                         + ") is start...");
        }

        int number;

        try
        {
            // ��ǰ���������Ƿ��Ѵ�����ͬ���ƻ���
            number = BookCategoryDAO.getInstance()
                                  .hasBookNameInParentId(parentId, name);

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
        String tempDir = ServerInfo.getAppRootPath() + File.separator + "book"
                         + File.separator;
        String ftpDir = BaseBookConfig.get("bookPicURl");
        
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
        return SystemConfig.URL_PIC_VISIT + BaseBookConfig.get("bookPicURl")
               + cateId + "/";
    }
   
    /**************************/
    
    
    /**
     * ���ڷ���ͼ�������չ�ֶ��б�
     * @return
     * @throws BOException
     */
    public List queryBookCategoryKeyBaseList(String tid) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("BookCategoryBO.queryBookCategoryKeyBaseList( ) is start...");
        }

        try
        {
            // ����NewMusicCategoryDAO���в�ѯ
            return BookCategoryDAO.getInstance()
                                      .queryBookCategoryKeyBaseList(tid);
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
    public int delBookCategoryKeyResource(String tid) throws BOException
    {
		if (logger.isDebugEnabled())
		{
			logger
					.debug("BookCategoryBO.delBookCategoryKeyResource() is start...");
		}
		int r = 0;
		try
		{
	
			r = BookCategoryDAO.getInstance().delBookCategoryKeyResource(tid);
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
    
   
}
