package com.aspire.dotcard.wpinfo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.wpinfo.vo.AppInfoCategoryVO;
import com.aspire.dotcard.wpinfo.vo.AppInfoReferenceVO;
import com.aspire.dotcard.wpinfo.vo.AppInfoVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;

public class AppInfoDAO {

	protected static JLogger logger = LoggerFactory.getLogger(AppInfoDAO.class);

	private static AppInfoDAO dao = new AppInfoDAO();

	private AppInfoDAO() {
	}

	public static AppInfoDAO getInstance() {
		return dao;
	}
	
	/**
     * ���ڷ���wp��ۻ�����Ϣ
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public AppInfoCategoryVO queryAppInfoCategoryVO(String categoryId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.dao.AppInfoDAO.queryAppInfoCategoryVO(" + categoryId
                         + ") is starting ...");
        }

        // select c.id,c.categoryid,c.parentcid,c.cname,c.cdesc,c.pic,c.sortid,c.isshow from t_v_category c where c.categoryid = ?
        //String sqlCode = "com.aspire.dotcard.basevideosync.dao.VideoDAO.queryVideoCategoryVO.SELECT";
        String sqlCode = "com.aspire.dotcard.wpinfo.dao.AppInfoDAO.queryAppInfoCategoryVO.SELECT";
        ResultSet rs = null;
        AppInfoCategoryVO vo = new AppInfoCategoryVO();

        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode(sqlCode, new Object[] { categoryId });

            if (rs.next())
            {
                appInfoCategoryVOByRs(vo, rs);
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("����wp��ۻ�����Ϣ��ѯ�����쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("����wp��ۻ�����Ϣ��ѯ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return vo;
    }
	
    /**
     * ���ڲ�ѯ��ǰ��������Ʒ�б�
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryAppInfoReferenceList(PageResult page, AppInfoReferenceVO vo)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.dao.AppInfoDAO.queryAppInfoReferenceList(" + vo.getRefId()
                         + ") is starting ...");
        }

		String sqlCode = "com.aspire.dotcard.wpinfo.dao.AppInfoDAO.queryAppInfoReferenceList.SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //����������sql�Ͳ���

            if (!"".equals(vo.getAppId()))
            {
            	sqlBuffer.append(" and a.appid = ? ");
            	paras.add(vo.getAppId());
            }
            if (!"".equals(vo.getAppName()))
            {
            	sqlBuffer.append(" and a.appname like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getAppName())+"%");
            }
            
            sqlBuffer.append("  and b.categoryid =? ");
            sqlBuffer.append("  order by sortid desc");
            paras.add(vo.getCategoryId());
            
            page.excute(sqlBuffer.toString(), paras.toArray(), new AppInfoReferencePageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }
    
    /**
     * ���ڲ�ѯwp��۽�Ŀ�б�
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryProgramList(PageResult page, com.aspire.dotcard.wpinfo.vo.AppInfoVO vo)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.dao.AppInfoDAO.queryProgramList( ) is starting ...");
        }

		// select p.programid,p.cmsid,p.name,p.updatetimev from t_v_dprogram p
		String sqlCode = "com.aspire.dotcard.wpinfo.dao.AppInfoDAO.queryProgramList.SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //����������sql�Ͳ���

            if (!"".equals(vo.getAppId()))
            {
                sqlBuffer.append(" and p.appid = ? ");
                paras.add(vo.getAppId());
            }
            if (!"".equals(vo.getAppName()))
            {
                sqlBuffer.append(" and p.appname like ? ");
                paras.add("%"+SQLUtil.escape(vo.getAppName())+"%");
            }
         

            sqlBuffer.append(" order by p.appid");

            page.excute(sqlBuffer.toString(), paras.toArray(), new AppInfoPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }
    
    /**
     * 
     *  ����seq ��ȡ�µ�wp��ۻ��ܱ���ID
     *  
     * @return
     * @throws DAOException
     */
    public String getAppInfoCategoryId() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.dao.AppInfoDAO.getAppInfoCategoryId() is starting ...");
        }

        String sqlCode = "com.aspire.dotcard.wpinfo.dao.AppInfoDAO.getAppInfoCategoryId.SELECT";

        String categoryId = null;

        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            if (rs.next())
            {
            	categoryId = rs.getString(1);
            }
        }
        catch (Exception e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }
        return categoryId;
    }
    
    /**
     * ����������ۻ���
     * 
     * @param videoCategoryVO
     * @throws BOException
     */
    public void addAppInfoCategory(AppInfoCategoryVO appInfoCategoryVO)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.dao.AppInfoDAO.addAppInfoCategory() is starting ...");
        }

        String sqlCode = "com.aspire.dotcard.wpinfo.dao.AppInfoDAO.addAppInfoCategory.INSERT";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] {  appInfoCategoryVO.getCategoryId(),
            		                              appInfoCategoryVO.getParentcId(),
            		                              appInfoCategoryVO.getCname(),
            		                              appInfoCategoryVO.getCdesc(),
            		                              appInfoCategoryVO.getPicture(),
                                                new Integer(appInfoCategoryVO.getSortId()),
                                                appInfoCategoryVO.getIsShow() });
        }
        catch (DAOException e)
        {
            throw new DAOException("����wp��ۻ���ʱ�����쳣:", e);
        }
    }
    
    /**
     * �������ָ����wp��۽�Ŀ��������
     * 
     * @param categoryId ����id
     * @param videoId wp���id��
     * @throws DAOException
     */
    public void addAppInfoReferences(String categoryId, String[] appIds)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.dao.AppInfoDAO.addAppInfoReferences(" + categoryId + ") is starting ...");
        }

        String sql = "com.aspire.dotcard.wpinfo.dao.AppInfoDAO.addAppInfoReferences.INSERT";
        String sqlCode[] = new String[appIds.length];
        Object[][] object = new Object[appIds.length][4];

        for (int i = 0; i < appIds.length; i++)
        {
            String[] temps = appIds[i].split("_");

            sqlCode[i] = sql;
            object[i][0] = categoryId;
            //Ӧ��id
            object[i][1] = temps[0];
            //Ӧ����
            object[i][2] = temps[1];
            object[i][3] = categoryId;
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("���ָ����wp�����������ʱ�����쳣:", e);
        }
    }
    
    /**
     * ���ڸ���wp��ۻ���
     * 
     * @param videoCategoryVO
     * @throws BOException
     */
    public void updateAppInfoCategory(AppInfoCategoryVO appInfoCategoryVO)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.dao.AppInfoDAO.updateAppInfoCategory() is starting ...");
        }

        String sqlCode = "com.aspire.dotcard.wpinfo.dao.AppInfoDAO.updateAppInfoCategory.UPDATE";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] {  appInfoCategoryVO.getCname(),
            		                            appInfoCategoryVO.getCdesc(),
            		                            appInfoCategoryVO.getPicture(),
                                                new Integer(appInfoCategoryVO.getSortId()),
                                                appInfoCategoryVO.getIsShow(),
                                                appInfoCategoryVO.getCategoryId() });
        }
        catch (DAOException e)
        {
            throw new DAOException("����wp��ۻ���ʱ�����쳣:", e);
        }
    }
    
    /**
     * ����ɾ��ָ������
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public void delAppInfoCategory(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.dao.AppInfoDAO.delAppInfoCategory(" + categoryId + ") is starting ...");
        }

        String sqlCode = "com.aspire.dotcard.wpinfo.dao.AppInfoDAO.delAppInfoCategory.DELETE";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode,
                                              new Object[] { categoryId });
        }
        catch (DAOException e)
        {
            throw new DAOException("���ݻ��ܱ���ɾ��ָ������ʱ�����쳣:", e);
        }
    }
    
    /**
     * ����ɾ��ָ��������ָ����wp��۽�Ŀ��Ʒ
     * 
     * @param categoryId ����id
     * @param refId wp�����Ʒid��
     * @throws DAOException
     */
    public void delAppInfoReferences(String categoryId, String[] refId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.dao.AppInfoDAO.delAppInfoReferences(" + categoryId + ") is starting ...");
        }

        String sql = "com.aspire.dotcard.wpinfo.dao.AppInfoDAO.delAppInfoReferences.DELETE";
        String sqlCode[] = new String[refId.length];
        Object[][] object = new Object[refId.length][1];

        for (int i = 0; i < refId.length; i++)
        {
            sqlCode[i] = sql;
            object[i][0] = refId[i];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("�Ƴ�ָ��������ָ����wp���ʱ�����쳣:", e);
        }
    }
    
    /**
     * ��������wp��ۻ�����wp�����Ʒ����ֵ
     * 
     * @param categoryId ����id
     * @param setSortId wp�������id
     * @throws DAOException
     */
    public void setAppInfoReferenceSort(String categoryId, String[] setSortId)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.dao.AppInfoDAO.setAppInfoReferenceSort(" + categoryId + ") is starting ...");
        }

        String sql = "com.aspire.dotcard.wpinfo.dao.AppInfoDAO.setAppInfoReferenceSort.set";
        String sqlCode[] = new String[setSortId.length];
        Object[][] object = new Object[setSortId.length][2];

        for (int i = 0; i < setSortId.length; i++)
        {
            String[] temp = setSortId[i].split("_");
            sqlCode[i] = sql;
            object[i][0] = temp[1];

            object[i][1] = temp[0];

        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("����wp��ۻ�����wp�����Ʒ����ֵʱ�����쳣:", e);
        }
    }
    
    /**
     * �鿴��ǰ�����Ƿ�����ӻ���
     * 
     * @param categoryId
     * @return
     * @throws BOException
     */
    public int hasChild(String categoryId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.dao.AppInfoDAO.hasChild(" + categoryId + ") is starting ...");
        }

        String sqlCode = "com.aspire.dotcard.wpinfo.dao.AppInfoDAO.hasChild.SELECT";
        ResultSet rs = null;
        int countNum = 0;

        try
        {
            rs = DB.getInstance()
                   .queryBySQLCode(sqlCode, new Object[] { categoryId });

            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("�鿴��ǰ�����Ƿ�����ӻ��ܷ����쳣:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("�鿴��ǰ�����Ƿ�����ӻ��ܷ����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }
    
    /**
     * �鿴��ǰ�������Ƿ񻹴�������Ʒ
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public int hasReference(String categoryId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.dao.AppInfoDAO.hasReference(" + categoryId + ") is starting ...");
        }

        String sqlCode = "com.aspire.dotcard.wpinfo.dao.AppInfoDAO.hasReference.SELECT";
        ResultSet rs = null;
        int countNum = 0;
        
        rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{categoryId});

        try
        {
            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("�鿴��ǰ�������Ƿ񻹴�������Ʒ��Ϣʱ�����쳣:", e);
        }
        
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }
    
    /**
     * ���ڲ鿴ָ���������Ƿ����ָ��wp��۽�Ŀ
     * 
     * @param categoryId ����id
     * @param programIds wp��۽�Ŀid��
     * @throws DAOException
     */
    public String isHasReferences(String categoryId, String[] programIds)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("wpinfo.dao.AppInfoDAO.isHasReferences(" + categoryId + ") is starting ...");
        }

        String sqlCode = "com.aspire.dotcard.wpinfo.dao.AppInfoDAO.isHasReferences.SELECT";
        String sql;
        ResultSet rs = null;
        String ret = "";

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //����������sql�Ͳ���

            paras.add(categoryId);
            
            for (int i = 0; i < programIds.length; i++)
            {
            	if(i == 0){
            		sqlBuffer.append(" and r.appid in ( ? ");
            	}else{
            		sqlBuffer.append(" , ? ");
            	}
            	if(i == programIds.length-1)
            		sqlBuffer.append(" ) ");
            	paras.add(programIds[i]);
            }
            
            rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());

            while (rs.next())
            {
                ret += rs.getString("appid") + ". ";
            }

        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
        catch (SQLException e)
        {
            throw new DAOException("�鿴ָ���������Ƿ����ָ��wp��۽�Ŀʱ�����쳣:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return ret;
    }
    
    /**
     * Ϊ�������Ը�ֵ
     * 
     * @param vo
     * @param rs
     * @throws SQLException
     */
    private void appInfoCategoryVOByRs(AppInfoCategoryVO vo, ResultSet rs)
                    throws SQLException
    {
        vo.setId(rs.getString("id"));
        vo.setCategoryId(rs.getString("categoryid"));
        vo.setParentcId(rs.getString("parentcid"));
        vo.setCname(rs.getString("cname"));
        vo.setSortId(rs.getInt("sortid"));
        vo.setCdesc(rs.getString("cdesc"));
        vo.setPicture(rs.getString("pic"));
        vo.setIsShow(Integer.parseInt(rs.getString("isshow")));
    }
    
    /**
     * wp�����Ʒ��ҳ��ȡVO��ʵ����
     */
    private class AppInfoReferencePageVO implements PageVOInterface
    {
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            AppInfoReferenceVO vo = ( AppInfoReferenceVO ) content;

            vo.setRefId(rs.getString("Id"));
           // vo.setCategoryId(rs.getString("categoryId"));
            vo.setAppId(rs.getString("appId"));
            vo.setAppName(rs.getString("appname"));
            vo.setAppPrice(rs.getString("appprice"));
            vo.setSortId(rs.getInt("sortId"));
            vo.setLastUpTime(rs.getString("AppUpdateDate"));
        }

        public Object createObject()
        {
            return new AppInfoReferenceVO();
        }
    }
    
    /**
     * wp��۽�Ŀ��ҳ��ȡVO��ʵ����
     */
    private class AppInfoPageVO implements PageVOInterface
    {
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            AppInfoVO vo = ( AppInfoVO ) content;

            vo.setAppId(rs.getString("AppId"));
            vo.setAppName(rs.getString("appname"));
            vo.setLastUpTime(rs.getString("AppUpdateDate"));
            vo.setAppPrice(rs.getString("appprice"));

        }

        public Object createObject()
        {
            return new AppInfoVO();
        }
    }
    
}
