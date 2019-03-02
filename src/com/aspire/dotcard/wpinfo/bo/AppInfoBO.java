package com.aspire.dotcard.wpinfo.bo;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.wpinfo.dao.AppInfoDAO;
import com.aspire.dotcard.wpinfo.vo.AppInfoCategoryVO;
import com.aspire.dotcard.wpinfo.vo.AppInfoReferenceVO;
import com.aspire.dotcard.wpinfo.vo.AppInfoVO;
import com.aspire.ponaadmin.common.page.PageResult;

public class AppInfoBO {

	/**
	 * 日志对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(AppInfoBO.class);
	
	private static AppInfoBO bo = new AppInfoBO();
	
	private AppInfoBO()
	{}
	
	public static AppInfoBO getInstance()
	{
		return bo;
	}
	
	/**
	 * 用于返回wp汇聚货架信息
	 * 
	 * @param categoryId
	 * @return
	 * @throws BOException
	 */
	public AppInfoCategoryVO queryAppInfoCategoryVO(String categoryId)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("wpinfo.bo.AppInfoBO.queryAppInfoCategoryVO(" + categoryId
					+ ") is start...");
		}

		try
		{
			return AppInfoDAO.getInstance().queryAppInfoCategoryVO(categoryId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("返回wp汇聚货架信息时发生数据库异常！");
		}
	}
	
	 /**
     * 用于查询当前货架下商品列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryAppInfoReferenceList(PageResult page, AppInfoReferenceVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.bo.AppInfoBO.queryAppInfoReferenceList( ) is start...");
        }

        try
        {
            AppInfoDAO.getInstance().queryAppInfoReferenceList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("返回wp汇聚货架列表时发生数据库异常！");
        }
    }
	
    /**
     * 用于查询wp汇聚节目列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryAppInfoList(PageResult page, AppInfoVO vo)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.bo.AppInfoBO.queryProgramList( ) is start...");
        }

        try
        {
            AppInfoDAO.getInstance().queryProgramList(page, vo);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查询wp汇聚列表时发生数据库异常！");
        }
    }
	
	/**
	 * 用于获取wp汇聚货架编码ID
	 * 
	 * @throws BOException
	 */
	public String getAppInfoCategoryId() throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("wpinfo.bo.AppInfoBO.getAppInfoCategoryId() is start...");
		}
		String categoryId = null;
		try
		{
			categoryId = AppInfoDAO.getInstance().getAppInfoCategoryId();
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("获取wp汇聚货架编码ID时发生数据库异常！");
		}
		return categoryId;
	}
	
	/**
	 * 用于新增wp汇聚货架
	 * 
	 * @param videoCategoryVO
	 * @throws BOException
	 */
	public void addAppInfoCategory(AppInfoCategoryVO videoCategoryVO)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("wpinfo.bo.AppInfoBO.addAppInfoCategory() is start...");
		}

		try
		{
			AppInfoDAO.getInstance().addAppInfoCategory(videoCategoryVO);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("新增wp汇聚货架时发生数据库异常！");
		}
	}
	
	/**
     * 用于添加指定的wp汇聚至货架中
     * 
     * @param categoryId 货架id
     * @param addVideoId wp汇聚id列
     * @throws BOException
     */
    public void addAppInfoReferences(String categoryId, String addVideoId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.bo.AppInfoBO.addAppInfoReferences( ) is start...");
        }

        try
        {
            String[] addVideoIds = addVideoId.split(";");

            AppInfoDAO.getInstance().addAppInfoReferences(categoryId,addVideoIds);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("添加指定的wp汇聚至货架中时发生数据库异常！");
        }
    }
	
	/**
	 * 用于修改wp汇聚货架
	 * 
	 * @param videoCategoryVO
	 * @throws BOException
	 */
	public void updateAppInfoCategory(AppInfoCategoryVO videoCategoryVO)
			throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("wpinfo.bo.AppInfoBO.updateAppInfoCategory() is start...");
		}

		try
		{
			AppInfoDAO.getInstance().updateAppInfoCategory(videoCategoryVO);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("修改wp汇聚货架时发生数据库异常！");
		}
	}
	
	/**
	 * 查看当前货架是否存在子货架
	 * 
	 * @param videoCategoryId
	 * @return
	 * @throws BOException
	 */
	public int hasChild(String appInfoCategoryId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("wpinfo.bo.AppInfoBO.hasChild(" + appInfoCategoryId
					+ ") is start...");
		}

		try
		{
			return AppInfoDAO.getInstance().hasChild(appInfoCategoryId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查看当前货架是否存在子货架时发生数据库异常！");
		}
	}
	
	/**
	 * 查看当前货架下是否还存在着商品
	 * 
	 * @param categoryId
	 * @return
	 * @throws BOException
	 */
	public int hasReference(String categoryId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("wpinfo.bo.AppInfoBO.hasReference(" + categoryId
					+ ") is start...");
		}

		try
		{
			return AppInfoDAO.getInstance().hasReference(categoryId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("查看当前货架下是否还存在着商品时发生数据库异常！");
		}
	}
	
	/**
     * 用于查看指定货架中是否存在指定wp汇聚节目
     * 
     * @param categoryId 货架id
     * @param addVideoId wp汇聚id列
     * @throws BOException
     */
    public String isHasReferences(String categoryId, String addAppInfoId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.bo.AppInfoBO.isHasReferences( ) is start...");
        }

        try
        {
            String[] addAppInfoIds = addAppInfoId.split(";");
            
            String[] appIds = new String[addAppInfoIds.length];
            
            // 分离出id与name分开
            for (int i = 0; i < addAppInfoIds.length; i++)
            {
                String temp = addAppInfoIds[i];
                String[] temps = temp.split("_");
                appIds[i] = temps[0];
            }
            
            return AppInfoDAO.getInstance().isHasReferences(categoryId, appIds);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("查看指定货架中是否存在指定wp汇聚节目时发生数据库异常！");
        }
    }
	
	/**
	 * 用于删除指定货架
	 * 
	 * @param categoryId
	 * @throws BOException
	 */
	public void delAppInfoCategory(String categoryId) throws BOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("wpinfo.bo.AppInfoBO.delAppInfoCategory(" + categoryId
					+ ") is start...");
		}

		try
		{
			AppInfoDAO.getInstance().delAppInfoCategory(categoryId);
		}
		catch (DAOException e)
		{
			logger.error(e);
			throw new BOException("删除指定货架时发生数据库异常！");
		}
	}
	
	/**
     * 用于删除指定货架下指定的wp汇聚节目商品
     * 
     * @param categoryId 货架id
     * @param refId 商品id列
     * @throws BOException
     */
    public void delAppInfoReferences(String categoryId, String[] refId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.bo.AppInfoBO.delAppInfoReferences( ) is start...");
        }

        try
        {
        	AppInfoDAO.getInstance().delAppInfoReferences(categoryId, refId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("删除wp汇聚货架下指定的wp汇聚节目商品时发生数据库异常！");
        }
    }
    
    /**
     * 用于设置wp汇聚货架下wp汇聚商品排序值
     * 
     * @param categoryId
     * @param setSortId 
     * @throws BOException
     */
    public void setAppInfoReferenceSort(String categoryId, String setSortId)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.bo.AppInfoBO.setAppInfoReferenceSort( ) is start...");
        }

        try
        {
            String[] sort = setSortId.split(";");
            AppInfoDAO.getInstance().setAppInfoReferenceSort(categoryId, sort);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("设置wp汇聚货架下wp汇聚商品排序值时发生数据库异常！");
        }
    }
}
