/*
 * 文件名：BookCategoryBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BookCategoryBO.class);

    private static BookCategoryBO instance = new BookCategoryBO();

    private BookCategoryBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static BookCategoryBO getInstance()
    {
        return instance;
    }
    
    /**
     * 返回唯一主键id
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
            // 调用BookCategoryDAO进行查询
            return BookCategoryDAO.getInstance().getBookCategoryId();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回唯一主键id时发生数据库异常！");
        }
    }

    /**
     * 用于返回图书货架列表
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
            // 调用BookCategoryDAO进行查询
            return BookCategoryDAO.getInstance().queryBookCategoryList();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回图书货架列表时发生数据库异常！");
        }
    }

    /**
     * 用于返回图书货架信息
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
            // 调用BookCategoryDAO进行查询
            return BookCategoryDAO.getInstance()
                                  .queryBookCategoryVO(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回图书货架信息时发生数据库异常！");
        }
    }

    /**
     * 返回当前货架的子货架信息图书货架信息
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
            // 调用BookCategoryDAO进行查询
            return BookCategoryDAO.getInstance().hasBookChild(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回当前货架的子货架信息图书货架信息时发生数据库异常！");
        }
    }

    /**
     * 查看当前货架下是否还存在着商品
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
            // 调用BookRefDAO进行查询
            return BookRefDAO.getInstance().hasBook(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回当前货架的子货架信息图书货架信息时发生数据库异常！");
        }
    }

    /**
     * 用于新增图书货架
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
            // 调用BookCategoryDAO进行新增新音乐货架
            BookCategoryDAO.getInstance().saveBookCategory(bookVO);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("新增图书货架时发生数据库异常！");
        }
    }

    /**
     * 用于删除指定货架
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
            // 调用BookCategoryDAO进行删除
            BookCategoryDAO.getInstance().delBookCategory(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("删除指定货架时发生数据库异常！");
        }
    }

    /**
     * 用于变更图书货架
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
            // 调用BookCategoryDAO进行变更新音乐货架
            BookCategoryDAO.getInstance().updateBookCategory(bookVO);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("变更图书货架时发生数据库异常！");
        }
    }

    /**
     * 当前父货架下是否已存在相同名称货架
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
            // 当前父货架下是否已存在相同名称货架
            number = BookCategoryDAO.getInstance()
                                  .hasBookNameInParentId(parentId, name);

            // 查看是否存在
            if (number > 0)
            {
                throw new BOException("用于检查当前父货架下是否已存在相同名称货架时发生数据库异常！",
                                      RepositoryBOCode.ADD_UPDATE_CATEGORY_NAME);
            }
        }
        catch (DAOException e)
        {
            throw new BOException("用于检查当前父货架下是否已存在相同名称货架时发生数据库异常！");
        }
    }
    
    /**
     * 上传货架的预览图的图片
     * @param request
     * @throws BOException
     */
    public String uploadCatePicURL(FormFile uploadFile,String cateId)throws BOException
    {
        String tempDir = ServerInfo.getAppRootPath() + File.separator + "book"
                         + File.separator;
        String ftpDir = BaseBookConfig.get("bookPicURl");
        
        IOUtil.checkAndCreateDir(tempDir);

        // 获得文件名，这个文件名包括路径：
        String fileName = uploadFile.getFileName();
        String suffex = fileName.substring(fileName.lastIndexOf('.'));

        // 拼装临时文件名为 temp.png
        String oldFileName = tempDir + "temp" + suffex;

        try
        {
            IOUtil.writeToFile(oldFileName, uploadFile.getFileData());
        }
        catch (Exception e1)
        {
            throw new BOException("写入临时文件失败。tempFilePath=" + oldFileName,
                                  RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
        }

        // 需要生成四种不同规格的图片
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
            throw new BOException("图片转化出错，FileName=" + oldFileName,
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
            throw new BOException("上传到资源服务器出现异常。",
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
     * 用于返回图书货架扩展字段列表
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
            // 调用NewMusicCategoryDAO进行查询
            return BookCategoryDAO.getInstance()
                                      .queryBookCategoryKeyBaseList(tid);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回图书货架扩展字段列表时发生数据库异常！",e);
        }
    }

    /**
	 * 用于删除图书货架保存扩展字段
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
			throw new BOException("删除图书货架扩展字段值时发生异常！", e);
		}
		return r;
	}
   
    
    
    
    /**
     * 
     *@desc 保存
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
				throw new BOException("保存扩展值失败", e);
			}
		}
	}
    
   
}
