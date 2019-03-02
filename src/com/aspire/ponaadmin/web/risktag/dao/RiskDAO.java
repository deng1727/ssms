package com.aspire.ponaadmin.web.risktag.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.common.util.StringUtils;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.category.blacklist.dao.AndroidBlackListDAO;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelMoVo;
import com.aspire.ponaadmin.web.risktag.vo.RiskTagVO;
import com.aspire.ponaadmin.web.risktag.vo.RiskVO;

public class RiskDAO {
	 /**
     * 日志引用
     */
    protected static final JLogger logger = LoggerFactory.getLogger(RiskDAO.class);

    private static RiskDAO instance = new RiskDAO();
    
    private RiskDAO(){
    	
    }
    
    public static RiskDAO getInstance(){
    	return instance;
    }
	
    /**
     * 用于用于查询榜单黑名单元数据列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryTagList(PageResult page, String stats, String id)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("RiskTagDAO.queryTagList() is starting ...");
        }

        //select * from t_r_risktag_list  where 1\=1 
        String sqlCode = "tagmanager.dao.RiskDAO.queryTagList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            if (!"".equals(id) && !("0".equals(id)))
            {
//                sql += " and contentid = ? ";
            	sqlBuffer.append(" and t.id = ? ");
            	paras.add(SQLUtil.escape(id));
            }
            
            if (!"".equals(stats) && !("-1".equals(stats)))
            {
//                sql += " and name like('%" + name
//                       + "%')";
            	sqlBuffer.append(" and t.stats =  ? ");
            	paras.add(SQLUtil.escape(stats));
            }
            //sql += " order by createdate desc ";
            sqlBuffer.append(" order by t.handltime desc ");

            page.excute(sqlBuffer.toString(), paras.toArray(), new RiskPageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
      
    }
    
    private class RiskPageVO implements PageVOInterface{

        @Override
        public Object createObject()
        {
            return new RiskVO();
        }

        @Override
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
        	RiskVO vo = (RiskVO)content;
            vo.setId(rs.getString("id"));
            vo.setRisktag(rs.getString("risktag"));
            vo.setStats(rs.getString("stats"));
            vo.setHandleTime(dateToString("yyyy-MM-dd",rs.getDate("handlTime")));
        }
    }
    public int doCheckBlack(String contentid) throws DAOException{
    	 if (logger.isDebugEnabled())
         {
             logger.debug("RiskTagDAO.doInsertBlack() is starting ...");
         }
    	 //先查询是否存在黑名单表
         //select * from t_r_blacklist where contentid = ?
         String sqlCode = "tagmanager.dao.RiskTagDAO.doInsertBlack().SELECT";
         String[] paras = null;
         ResultSet rs = null;
         ArrayList<String> lists = new ArrayList<String>();
         try {
        	 paras = new String[] {contentid};
        	 rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
 			while (rs.next())
 			{
 				lists.add(rs.getString("contentid"));
 			}
		} catch (Exception e) {
			logger.error(e);
			throw new DAOException("查询"+contentid+"是否存在时出错", e);
		}
         return lists.size();
    }
    
    public int doInsert(String contentid ,String isblack) throws DAOException{
    	if (logger.isDebugEnabled())
        {
            logger.debug("RiskTagDAO.doInsert() is starting ...");
        }
    	int a = 0;
        // insert into t_r_blacklist(contentid,isblack,createtime) values(?,?,sysdate)
    	String insertCode = "tagmanager.dao.RiskTagDAO.doInsertBlack().INSERT";
        
        String[] paras = null;
        try
		{
        	paras = new String[] {contentid,isblack};
			a = DB.getInstance().executeBySQLCode(insertCode, paras);
		}
		catch (DAOException e)
		{
			logger.error("新增榜单黑名单时发生异常:", e);
			throw new DAOException("插入"+contentid+"黑名单表时出错", e);
		}
        return a ;
    }
    
    public int doUpdate (String contentid,String isblack) throws DAOException{
    	if (logger.isDebugEnabled())
        {
            logger.debug("RiskTagDAO.doUpdate() is starting ...");
        }
    	int a = 0;
    	//update t_r_blacklist  set isblack = ? where contentid =?
    	String updateCode = "tagmanager.dao.RiskTagDAO.doInsertBlack().UPDATE";
        
        String[] paras = null;
        try
		{
        	paras = new String[] {isblack,contentid};
			a =DB.getInstance().executeBySQLCode(updateCode, paras);
		}
		catch (DAOException e)
		{
			logger.error("新增榜单黑名单时发生异常:", e);
			throw new DAOException("更新"+contentid+"黑名单表时出错", e);
		}
        return a ;
    }
    
    private String dateToString (String dataFormat ,Date date){
    	if (null != date) {
    		SimpleDateFormat sd = new SimpleDateFormat(dataFormat);
        	return sd.format(date);
		}else {
			return "";
		}
    }
    
    public ArrayList<String> doSelectBlack() throws DAOException{
   	 if (logger.isDebugEnabled())
        {
            logger.debug("RiskTagDAO.doInsertBlack() is starting ...");
        }
   	 //先查询是否存在黑名单表
        //select * from t_r_blacklist where isblack = 1;
        String sqlCode = "tagmanager.dao.RiskTagDAO.doSelectBlack().SELECT";
        String[] paras = null;
        ResultSet rs = null;
        ArrayList<String> lists = new ArrayList<String>();
        try {
       	 rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next())
			{
				lists.add(rs.getString("contentid"));
			}
		} catch (Exception e) {
			logger.error(e);
			throw new DAOException("查询所有黑名单时出错", e);
		}
        return lists;
   }
    
    
    public void doDeleteBlack(String contentid) throws DAOException{
   	 if (logger.isDebugEnabled())
        {
            logger.debug("RiskDAO.doInsertBlack() is starting ...");
        }
   	 //先查询是否存在黑名单表
        //select * from t_r_blacklist where contentid = ?
        String sqlCode = "tagmanager.dao.RiskTagDAO.doInsertBlack().DELETE";
        String[] paras = null;
        try {
       	 paras = new String[] {contentid};
       	 DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (Exception e) {
			logger.error(e);
			throw new DAOException("删除"+contentid+"时出错", e);
		}
   }
    
    
    public void doUpdateBlack(String isblack ,String risktag ) throws DAOException{
      	 if (logger.isDebugEnabled())
           {
               logger.debug("RiskDAO.doInsertBlack() is starting ...");
           }
      	 //先查询是否存在黑名单表
           //select * from t_r_blacklist where contentid = ?
           String sqlCode = "tagmanager.dao.RiskDAO.queryTagList().UPDATE";
           String[] paras = null;
           try {
          	 paras = new String[] {isblack,risktag};
          	String sql = SQLCode.getInstance().getSQLStatement(sqlCode);
          	 DB.getInstance().execute(sql, paras);
   		} catch (Exception e) {
   			logger.error(e);
   			throw new DAOException("更新"+risktag+"时出错", e);
   		}
      }
}
