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
	 * ��־����
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
	 * ���ڷ���wp��ۻ�����Ϣ
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
			throw new BOException("����wp��ۻ�����Ϣʱ�������ݿ��쳣��");
		}
	}
	
	 /**
     * ���ڲ�ѯ��ǰ��������Ʒ�б�
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
            throw new BOException("����wp��ۻ����б�ʱ�������ݿ��쳣��");
        }
    }
	
    /**
     * ���ڲ�ѯwp��۽�Ŀ�б�
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
            throw new BOException("��ѯwp����б�ʱ�������ݿ��쳣��");
        }
    }
	
	/**
	 * ���ڻ�ȡwp��ۻ��ܱ���ID
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
			throw new BOException("��ȡwp��ۻ��ܱ���IDʱ�������ݿ��쳣��");
		}
		return categoryId;
	}
	
	/**
	 * ��������wp��ۻ���
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
			throw new BOException("����wp��ۻ���ʱ�������ݿ��쳣��");
		}
	}
	
	/**
     * �������ָ����wp�����������
     * 
     * @param categoryId ����id
     * @param addVideoId wp���id��
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
            throw new BOException("���ָ����wp�����������ʱ�������ݿ��쳣��");
        }
    }
	
	/**
	 * �����޸�wp��ۻ���
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
			throw new BOException("�޸�wp��ۻ���ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * �鿴��ǰ�����Ƿ�����ӻ���
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
			throw new BOException("�鿴��ǰ�����Ƿ�����ӻ���ʱ�������ݿ��쳣��");
		}
	}
	
	/**
	 * �鿴��ǰ�������Ƿ񻹴�������Ʒ
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
			throw new BOException("�鿴��ǰ�������Ƿ񻹴�������Ʒʱ�������ݿ��쳣��");
		}
	}
	
	/**
     * ���ڲ鿴ָ���������Ƿ����ָ��wp��۽�Ŀ
     * 
     * @param categoryId ����id
     * @param addVideoId wp���id��
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
            
            // �����id��name�ֿ�
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
            throw new BOException("�鿴ָ���������Ƿ����ָ��wp��۽�Ŀʱ�������ݿ��쳣��");
        }
    }
	
	/**
	 * ����ɾ��ָ������
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
			throw new BOException("ɾ��ָ������ʱ�������ݿ��쳣��");
		}
	}
	
	/**
     * ����ɾ��ָ��������ָ����wp��۽�Ŀ��Ʒ
     * 
     * @param categoryId ����id
     * @param refId ��Ʒid��
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
            throw new BOException("ɾ��wp��ۻ�����ָ����wp��۽�Ŀ��Ʒʱ�������ݿ��쳣��");
        }
    }
    
    /**
     * ��������wp��ۻ�����wp�����Ʒ����ֵ
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
            throw new BOException("����wp��ۻ�����wp�����Ʒ����ֵʱ�������ݿ��쳣��");
        }
    }
}
