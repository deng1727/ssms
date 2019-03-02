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
     * 用于返回wp汇聚货架信息
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
            throw new DAOException("返回wp汇聚货架信息查询发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("返回wp汇聚货架信息查询发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return vo;
    }
	
    /**
     * 用于查询当前货架下商品列表
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
            //构造搜索的sql和参数

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
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }
    
    /**
     * 用于查询wp汇聚节目列表
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
            //构造搜索的sql和参数

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
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }
    
    /**
     * 
     *  根据seq 获取新的wp汇聚货架编码ID
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
     * 用于新增汇聚货架
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
            throw new DAOException("新增wp汇聚货架时发生异常:", e);
        }
    }
    
    /**
     * 用于添加指定的wp汇聚节目至货架中
     * 
     * @param categoryId 货架id
     * @param videoId wp汇聚id列
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
            //应用id
            object[i][1] = temps[0];
            //应用明
            object[i][2] = temps[1];
            object[i][3] = categoryId;
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("添加指定的wp汇聚至货架中时发生异常:", e);
        }
    }
    
    /**
     * 用于更新wp汇聚货架
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
            throw new DAOException("更新wp汇聚货架时发生异常:", e);
        }
    }
    
    /**
     * 用于删除指定货架
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
            throw new DAOException("根据货架编码删除指定货架时发生异常:", e);
        }
    }
    
    /**
     * 用于删除指定货架下指定的wp汇聚节目商品
     * 
     * @param categoryId 货架id
     * @param refId wp汇聚商品id列
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
            throw new DAOException("移除指定货架下指定的wp汇聚时发生异常:", e);
        }
    }
    
    /**
     * 用于设置wp汇聚货架下wp汇聚商品排序值
     * 
     * @param categoryId 货架id
     * @param setSortId wp汇聚排序id
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
            throw new DAOException("设置wp汇聚货架下wp汇聚商品排序值时发生异常:", e);
        }
    }
    
    /**
     * 查看当前货架是否存在子货架
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
            throw new DAOException("查看当前货架是否存在子货架发生异常:", e);
        }
        catch (SQLException e)
        {
            throw new DAOException("查看当前货架是否存在子货架发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }
    
    /**
     * 查看当前货架下是否还存在着商品
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
            throw new DAOException("查看当前货架下是否还存在着商品信息时发生异常:", e);
        }
        
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }
    
    /**
     * 用于查看指定货架中是否存在指定wp汇聚节目
     * 
     * @param categoryId 货架id
     * @param programIds wp汇聚节目id列
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
            //构造搜索的sql和参数

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
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
        catch (SQLException e)
        {
            throw new DAOException("查看指定货架中是否存在指定wp汇聚节目时发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return ret;
    }
    
    /**
     * 为对象属性赋值
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
     * wp汇聚商品分页读取VO的实现类
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
     * wp汇聚节目分页读取VO的实现类
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
