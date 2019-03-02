package com.aspire.ponaadmin.web.coManagement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.common.util.MD5;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.coManagement.vo.CooperationVO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;
import com.aspire.ponaadmin.web.system.Config;


public class CooperationDAO {
	
	private final static JLogger LOGGER = LoggerFactory.getLogger(CooperationDAO.class);
	
	private static CooperationDAO instance = new CooperationDAO();
	
	private CooperationDAO(){
		
	}
	
	public static CooperationDAO getInstance(){
		return instance;
	}
	
	 /**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class CooperationPageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

        	CooperationVO vo = ( CooperationVO ) content;
        	vo.setCooperationId(rs.getString("CHANNELSID"));
        	vo.setCooperationName(rs.getString("CHANNELSNAME"));
        	vo.setStatus(rs.getString("status"));
        	vo.setCooperationDate(rs.getTimestamp("codate"));
        }

        public Object createObject()
        {

            return new CooperationVO();
        }
    }
    
	/**
	 * ��ѯ�������б�
	 * 
	 * @param page
	 * @param cooperationId ������ID
	 * @param cooperationName ����������
	 * @throws DAOException
	 */
	public void queryCooperationList(PageResult page, String cooperationId, String cooperationName) throws DAOException{
		
		if (LOGGER.isDebugEnabled())
        {
			LOGGER.debug("queryCooperationList(" + cooperationId + "," + cooperationName 
                         + ") is starting ...");
        }

        // select c.channelsid,c.channelsname,c.status,c.codate from T_OPEN_CHANNELS c where 1=1
        String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.queryCooperationList";
        StringBuffer sb = new StringBuffer("");

        try
        {
        	sb.append(SQLCode.getInstance().getSQLStatement(sqlCode));
            
            if (cooperationId != null && !"".equals(cooperationId))
            {
            	 sb.append(" and c.CHANNELSID= '" + cooperationId + "'");
            	 
            }
            if (cooperationName != null && !"".equals(cooperationName))
            {
            	
            	sb.append(" and c.channelsname like '%" + cooperationName + "%'");
                
            }
            page.excute(sb.toString(), new Object[]{}, new CooperationPageVO());
        }
        catch (DataAccessException e)
        {
        	LOGGER.error("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode������:", e);
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        } catch (DAOException e) {
        	LOGGER.error("��ѯ�������б����쳣:", e);
        	throw new DAOException("��ѯ�������б����쳣:",e);
		}
		
	}
	
	 /**
     * �ж��������Ӧ�Ļ����Ƿ����
     * @param categoryId
     * @return
     * @throws DAOException
     */
    public boolean isExistInCategory()throws DAOException{
    	StringBuffer sqlBuffer = new StringBuffer("select count(1) from t_r_category t where t.DELFLAG=0 ") ;
    	List paras = new ArrayList();
         //���������л�ȡ�����̸�����ID
         String channelRootCategoryId = Config.getInstance().getModuleConfig()
			.getItemValue("channelRootCategoryId");
         if(channelRootCategoryId != null && !"".equals(channelRootCategoryId.trim())){
         	 sqlBuffer.append(" and t.categoryID =?");
         	 paras.add(channelRootCategoryId);
         }
         boolean flag = false;
         try {
        	ResultSet rSet = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());
        	if(rSet != null&&rSet.next()){
        		int count = rSet.getInt(1);
        		if(count >0){
        			flag = true;
        		}
        	}
        	 return flag;
		} catch (Exception e) {
			LOGGER.error("�ж��������Ӧ�Ļ����Ƿ���ڣ�", e);
			throw new DAOException("�ж��������Ӧ�Ļ����Ƿ���ڣ�",e);
		}
         
    }
    
	/**
	 * ��Ӻ�����
	 * 
	 * @param map
	 * @throws DAOException
	 */
	public void insertCooperation(Map<String,Object> map) throws DAOException{
		String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.insertCooperation";
		// �����������
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getTransactionInstance();
			tdb.executeBySQLCode(sqlCode, new Object[]{map.get("cooperationId"),map.get("cooperationName"),map.get("cooperationType"),map.get("channelNumber"),"admin" + map.get("cooperationId") ,MD5.getHexMD5Str("admin" + map.get("cooperationId"))});
			 //���������л�ȡ�����̸�����ID
	         String channelRootCategoryId = Config.getInstance().getModuleConfig()
				.getItemValue("channelRootCategoryId");
	         Category category = new Category();
	         category.setName(map.get("cooperationName").toString());
	         category.setRelation("W,O,P,A");
	         category.setSortID(SEQCategoryUtil.getInstance()
 					.getSEQByCategoryType(1));
	         category.setCtype(0);
             category.setChangeDate(new Date());
             category.setDelFlag(0);
             category.setState(1);
             category.setCityId("{0000}");
             category.setPlatForm("{0000}");
             category.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
             category.setEndDate("2099-01-01");
             category.setClassifyStatus("1");
             String categoryId = this.queryCategory(channelRootCategoryId);
             CategoryTools.createCategory(categoryId,
                     category);
	        if(this.queryOpenChannelsCategoryVo(map.get("cooperationId").toString())){
	        	tdb.executeBySQLCode("com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.insertCooperation.UPDATE", new Object[]{category.getCategoryID(),map.get("cooperationId")});
	        }else{
	        	tdb.executeBySQLCode("com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.insertCooperation.INSERT", new Object[]{map.get("cooperationId"),category.getCategoryID()});
	        }
			tdb.commit();
		} catch (DAOException e) {
			tdb.rollback();
			LOGGER.error("��Ӻ����̷����쳣:", e);
			throw new DAOException("��Ӻ����̷����쳣:",e);
		} catch (BOException e) {
			tdb.rollback();
			LOGGER.error("��Ӻ����̷����쳣:", e);
			throw new DAOException("��Ӻ����̷����쳣:",e);
		} catch (Exception e) {
			tdb.rollback();
			LOGGER.error("��Ӻ����̷����쳣:", e);
			throw new DAOException("��Ӻ����̷����쳣:",e);
		}finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	
	 /**
     * ��ѯ�����̻�����Ϣʧ��
     * @throws DAOException 
     */
    public boolean queryOpenChannelsCategoryVo(String channelsId) throws DAOException{
        String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.queryOpenChannelsCategoryVo";
        try
        {
            ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{channelsId});
            if(rs.next()){
            	return true;
            }
            return false;
        }
        catch (DAOException e)
        {
        	LOGGER.debug("��ѯ�����̻�����Ϣʧ��:", e);
            throw new DAOException("��ѯ�����̻�����Ϣʧ��:", e);
        }
        catch (SQLException e)
        {
        	LOGGER.debug("��ѯ�����̻�����Ϣʧ��:", e);
            throw new DAOException("��ѯ�����̻�����Ϣʧ��:", e);
        }
    }
	/**
	 * ��ѯ��������Ϣ
	 * 
	 * @param cooperationId ������ID
	 * @return
	 * @throws DAOException
	 */
	public Map<String,Object> queryCooperation(String cooperationId) throws DAOException{
		Map<String,Object> map = new HashMap<String,Object>();
		String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.queryCooperation";
		ResultSet rs;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{cooperationId});
			while(rs.next()){
				map.put("cooperationId", rs.getString("channelsid"));
				map.put("cooperationName", rs.getString("channelsname"));
				map.put("channelsnumber", rs.getString("channelsnumber") == null ? 0 : rs.getString("channelsnumber"));
				map.put("cotype", rs.getString("cotype") == null ? "" : rs.getString("cotype"));
				map.put("categoryId", rs.getString("categoryId"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				map.put("codate", format.format(rs.getTimestamp("codate")));
			}
		} catch (DAOException e) {
			LOGGER.debug("��ѯ��������Ϣʧ��:", e);
            throw new DAOException("��ѯ��������Ϣʧ��:", e);
		} catch (SQLException e) {
			LOGGER.debug("��ѯ��������Ϣʧ��:", e);
            throw new DAOException("��ѯ��������Ϣʧ��:", e);
		}
		return map;
	}
	/**
	 * ���º�������Ϣ
	 * @param map
	 * @throws DAOException
	 */
	public void updateCooperation(Map<String,Object> map) throws DAOException{
		String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.updateCooperation";
		try {
			DB.getInstance().executeBySQLCode(sqlCode, new Object[]{map.get("cooperationType"),map.get("channelNumber"),map.get("cooperationId")});
		} catch (DAOException e) {
			LOGGER.debug("���º�������Ϣʧ��:", e);
            throw new DAOException("���º�������Ϣʧ��:", e);
		}
	}
	
	/**
	 * ���º�����״̬��Ϣ
	 * @param map
	 * @throws DAOException
	 */
	public void operationCooperation(Map<String,Object> map) throws DAOException{
		String sqlCode = "com.aspire.ponaadmin.web.coManagement.dao.CooperationDAO.operationCooperation";
		try {
			DB.getInstance().executeBySQLCode(sqlCode, new Object[]{map.get("type"),map.get("cooperationId")});
		} catch (DAOException e) {
			LOGGER.debug("���º�����״̬��Ϣʧ��:", e);
            throw new DAOException("���º�����״̬��Ϣʧ��:", e);
		}
	}
	
	/**
	 * ��ѯ����
	 * 
	 * @param cooperationId ������ID
	 * @return
	 * @throws DAOException
	 */
	public String queryCategory(String categoryId) throws DAOException{
		String sqlCode = "select id from t_r_category c where c.categoryid = ?";
		ResultSet rs;
		try {
			rs = DB.getInstance().query(sqlCode, new Object[]{categoryId});
			while(rs.next()){
				return rs.getString("id");
			}
		} catch (DAOException e) {
			LOGGER.debug("��ѯ������Ϣʧ��:", e);
            throw new DAOException("��ѯ������Ϣʧ��:", e);
		} catch (SQLException e) {
			LOGGER.debug("��ѯ������Ϣʧ��:", e);
            throw new DAOException("��ѯ������Ϣʧ��:", e);
		}
		throw new DAOException("��ѯ������Ϣʧ��:");
	}

}
