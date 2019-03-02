/*
 * 文件名：ReadCategoryBO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(ReadCategoryBO.class);

    private static ReadCategoryBO instance = new ReadCategoryBO();

    private ReadCategoryBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static ReadCategoryBO getInstance()
    {
        return instance;
    }
    
    /**
     * 返回唯一主键id
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
            // 调用ReadCategoryDAO进行查询
            return ReadCategoryDAO.getInstance().getReadId();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回唯一主键id时发生数据库异常！");
        }
    }
    
    /**
     * 返回唯一自定义货架id
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
            // 调用ReadCategoryDAO进行查询
            return ReadCategoryDAO.getInstance().getReadCategoryId();
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回唯一主键id时发生数据库异常！");
        }
    }

    /**
     * 用于返回图书货架信息
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
            // 调用BookCategoryDAO进行查询
            return ReadCategoryDAO.getInstance()
                                  .queryReadCategoryVO(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回图书货架信息时发生数据库异常！");
        }
    }

    /**
     * 返回当前货架的子货架信息阅读货架信息
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
            // 调用ReadCategoryDAO进行查询
            return ReadCategoryDAO.getInstance().hasReadChild(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回当前货架的子货架信息阅读货架信息时发生数据库异常！");
        }
    }

    /**
     * 查看当前货架下是否还存在着商品
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
            // 调用ReadReferenceDAO进行查询
            return ReadReferenceDAO.getInstance().hasRead(categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回当前货架的子货架信息阅读货架信息时发生数据库异常！");
        }
    }

    /**
     * 用于新增阅读货架
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
            // 调用ReadCategoryDAO进行新增新音乐货架
            ReadCategoryDAO.getInstance().saveReadCategory(readVO);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("新增阅读货架时发生数据库异常！");
        }
    }

    /**
     * 用于删除指定货架
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
            // 调用ReadCategoryDAO进行删除
            ReadCategoryDAO.getInstance().delReadCategory(tdb,categoryId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("删除指定货架时发生数据库异常！");
        }
    }

    /**
     * 用于变更阅读货架
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
            // 调用ReadCategoryDAO进行变更新音乐货架
            ReadCategoryDAO.getInstance().updateReadCategory(readVO);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("变更阅读货架时发生数据库异常！");
        }
    }

    /**
     * 当前父货架下是否已存在相同名称货架
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
            // 当前父货架下是否已存在相同名称货架
            number = ReadCategoryDAO.getInstance()
                                  .hasReadNameInParentId(parentId, name);

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
        String tempDir = ServerInfo.getAppRootPath() + File.separator + "read"
                         + File.separator;
        String ftpDir = BaseReadConfig.get("bookPicURl");
        
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
        return SystemConfig.URL_PIC_VISIT + BaseReadConfig.get("bookPicURl")
               + cateId + "/";
    }
   
    /**************************/
    
    
    /**
     * 用于返回图书货架扩展字段列表
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
            // 调用NewMusicCategoryDAO进行查询
            return ReadCategoryDAO.getInstance()
                                      .queryReadCategoryKeyBaseList(tid);
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
    
    /**
     * 用于查询基地阅读货架详情列表
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
            // 调用ReadReferenceDAO进行查询
        	ReadCategoryDAO.getInstance().queryCategoryDescList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("用于查询基地阅读货架详情列表时发生数据库异常！");
        }
    }
    
    /**
   	 * 提交审批
   	 * 
   	 * @param categoryId 货架编号
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
   		// 进行事务操作
   		TransactionDB tdb = null;
           try
           {
           	tdb = TransactionDB.getTransactionInstance();
            CategoryDAO.getInstance().approvalCategory(tdb,categoryId);
            CategoryDAO.getInstance().approvalCategory(tdb, categoryId, "2","1", logUser.getName());
            Map<String, Object> map = new HashMap<String, Object>();
   			map.put("mailTitle", "待审事项：客户端门户运营内容调整审批");
   			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：图书货架管理;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：;<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式：" + "提交审批";
   			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：图书货架管理;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 1) + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式：" + "提交审批";
   			map.put("mailContent", value);
   			map.put("status", "0");
   			map.put("categoryId", categoryId);
   			map.put("operation", "60");
   			map.put("operationtype", "图书货架管理提交审批");
   			map.put("operationobj", "图书货架管理");
   			map.put("operationobjtype", "货架Id：" + categoryId);
   			map.put("operator", logUser.getName());
   			WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
   			DaemonTaskRunner.getInstance().addTask(task);
   			tdb.commit();
           }
           catch (DAOException e)
           {
           	tdb.rollback();
               logger.error(e);
               throw new BOException("审批指定货架时发生数据库异常！");
           }finally {
   			if (tdb != null) {
   				tdb.close();
   			}
   		}
   	}
   	
   	/**
     * 用于删除指定货架
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
             // 调用BookCategoryDao进行删除
        	 BookCategoryDao.getInstance().delBookCategoryItem(categoryId);
         }
         catch (DAOException e)
         {
             logger.error(e);
             throw new BOException("删除指定货架时发生数据库异常！");
         }
    }
}
