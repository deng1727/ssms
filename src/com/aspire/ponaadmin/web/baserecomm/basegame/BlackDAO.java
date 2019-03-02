package com.aspire.ponaadmin.web.baserecomm.basegame;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.db.TransactionDB;

/**
 * 游戏黑名单数据库操作
 */
public class BlackDAO
{
    /**
     * 记录日志的实例对象
     */
    protected static JLogger LOG = LoggerFactory.getLogger(BlackDAO.class);

    /**
     * singleton模式的实例
     */
    private static BlackDAO instance = new BlackDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private BlackDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static BlackDAO getInstance()
    {
        return instance;
    }

    /**
     * 用于查询当前黑名单列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    @SuppressWarnings("unused")
    public void queryBlackList(PageResult page, BlackVO vo) throws DAOException
    {
        
        StringBuilder sqlsb = new StringBuilder(200);
        String sql = "select id, icpservid, servname, servdesc, oldprice, mobileprice from t_gamebase_black where 1 = 1 ";
        
        sqlsb.append(sql);
        
        List<String> paras = new ArrayList<String>(2);
        
        if (StringUtils.isNotBlank(vo.getIcpservid()))
        {
            sqlsb.append(" and icpservid like ? ");
            paras.add("%" + SQLUtil.escape(vo.getIcpservid().trim()) + "%");
        }
        if (StringUtils.isNotBlank(vo.getServname()))
        {
            sqlsb.append(" and servname like ? ");
            paras.add("%" + SQLUtil.escape(vo.getServname().trim()) + "%");
        }
        sqlsb.append(" order by id desc");
        
        page.excute(sqlsb.toString(), paras.toArray(), new PageVOInterface()
        {
            public void CopyValFromResultSet(Object obj, ResultSet rs) throws SQLException
            {
                BlackVO vo = (BlackVO)obj;
                vo.setId(rs.getString("id"));
				vo.setIcpservid(rs.getString("icpservid"));
				vo.setServname(rs.getString("servname"));
				vo.setOldprice(rs.getInt("oldprice"));
				vo.setMobileprice(rs.getInt("mobileprice"));
				vo.setServdesc(rs.getString("servdesc"));
                
            }
            public Object createObject()
            {
                return new BlackVO();
            }
        });
    }

    /**
     * 移除黑名单
     * 
     * @param id
     * @throws DAOException
     */
    public void removeBlack(String[] id) throws DAOException
    {
        if(id==null ||(id!=null&&id.length<=0)){
            LOG.warn("移除黑名单id[]参数为空!");
            return;
        }
        String sql = "com.aspire.ponaadmin.web.baserecomm.basegame.BlackDAO.remove";
        Object[][] object = new Object[id.length][1];
        
        for (int i = 0; i < id.length; i++)
        {
            object[i][0] = id[i];
        }
        
        try
        {
            DB.getInstance().executeBatchBySQLCode(sql, object);
        }
        catch (DAOException e)
        {
            LOG.error("移除指定黑名单时发生异常!", e);
            throw new DAOException("移除指定黑名单时发生异常!", e);
        }
    }



    /**
     * 检查黑名单表是否存在内容ID
     * 
     * @param icpservid
     * @return
     * @throws DAOException
     * @throws Exception
     */
    public boolean isExistBlack(String icpservid) throws DAOException
    {
        if (StringUtils.isEmpty(icpservid))
        {
            LOG.warn("检查黑名单表icpservid参数为空!");
            throw new DAOException("检查黑名单表icpservid参数为空!");
        }
        //
        String sqlCode = "com.aspire.ponaadmin.web.baserecomm.basegame.BlackDAO.isExistBlack";
        String[] paras = new String[] { icpservid };
        ResultSet rs = null;
        boolean result = false;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs != null)
            {
                result = rs.next();
            }
        }
        catch (DAOException e)
        {
            LOG.warn("检查黑名单表是否存在游戏业务ID:" + icpservid + " 发生异常:", e);
            throw new DAOException("检查黑名单表是否存在游戏业务ID:" + icpservid + " 发生异常:", e);
        }
        catch (SQLException e)
        {
            LOG.warn("检查黑名单表是否存在游戏业务ID:" + icpservid + " 发生异常:", e);
            throw new DAOException("检查黑名单表是否存在游戏业务ID:" + icpservid + " 发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }

        return result;
    }

    /**
     * 检查内容表t_cb_content是否存在conntentId
     * @param icpservid
     * @return
     * @throws DAOException
     */
    public boolean isExistContent(String icpservid) throws DAOException
    {
        if (StringUtils.isEmpty(icpservid))
        {
            LOG.warn("检查内容表icpservid参数为空!");
            throw new DAOException("检查内容表icpservid参数为空!");
        }
        //select 1 from t_r_gcontent c, t_game_service_new s where c.provider = 'B'  and s.icpservid = c.icpservid and c.icpservid = ?
        String sqlCode = "com.aspire.ponaadmin.web.baserecomm.basegame.BlackDAO.verifyIcpservid";
        String[] paras = new String[] { icpservid };
        ResultSet rs = null;
        boolean result = false;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            if (rs != null)
            {
                result = rs.next();
            }
        }
        catch (DAOException e)
        {
            LOG.error("检查内容表是否存在游戏业务ID:" + icpservid + " 发生异常:", e);
            throw new DAOException("检查内容表是否存在游戏业务ID:" + icpservid + " 发生异常:", e);
        }
        catch (SQLException e)
        {
            LOG.error("检查内容表是否存在游戏业务ID:" + icpservid + " 发生异常:", e);
            throw new DAOException("检查内容表是否存在游戏业务ID:" + icpservid + " 发生异常:", e);
        }
        finally
        {
            DB.close(rs);
        }
        return result;
    }

    /**
     * 新增黑名单
     * 
     * @param icpservid
     * @throws Exception
     */
    public void addBlack(String icpservid) throws Exception
    {
        if (StringUtils.isEmpty(icpservid))
        {
            LOG.warn("新增黑名单icpservid参数为空!");
            throw new DAOException("新增黑名单icpservid参数为空!");
        }
        
        this.addBlack(new String[]{icpservid});
    }
    
    /**
     * 新增多条黑名单
     * @param icpservid
     * @throws DAOException
     */
    public void addBlack(String[] icpservid) throws DAOException
    {
        if (icpservid==null ||(icpservid!=null&&icpservid.length<=0))
        {
           LOG.warn("新增黑名单icpservid[]为空!");
           return;
        }
        //insert into t_gamebase_black b(b.id,b.icpservid,b.servname,b.servdesc,b.oldprice,b.mobileprice,b.createdate,b.lupdate,b.status) select seq_t_gamebase_black_id.nextval,t.icpservid,t.servname,t.servdesc,t.oldprice,t.mobileprice,sysdate,sysdate,1 from t_game_service_new t, t_r_gcontent c where c.provider = 'B' and t.icpservid = c.icpservid and t.icpservid = ?;
        
        String sql = "com.aspire.ponaadmin.web.baserecomm.basegame.BlackDAO.addBlack";
        String sqlCode[] = new String[icpservid.length];
        Object[][] object = new Object[icpservid.length][1];

        for (int i = 0; i < icpservid.length; i++)
        {
            sqlCode[i] = sql;
            object[i][0] = icpservid[i];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            LOG.error("新增多条黑名单时发生异常!", e);
            throw new DAOException("新增多条黑名单时发生异常!", e);
        }
    }

    /**
     * 全量导入，先移除，后导入
     * 
     * @param list
     * @throws DAOException
     */
    public void allAddBlack(List<String> list) throws DAOException
    {
        if (list==null ||(list!=null&&list.size()<=0))
        {
           LOG.warn("全量导入黑名单为空!");
           return;
        }
        
        String sqlCode1 = "com.aspire.ponaadmin.web.baserecomm.basegame.BlackDAO.truncateBlack";
        String sqlCode2 = "com.aspire.ponaadmin.web.baserecomm.basegame.BlackDAO.addBlack";
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance();

            DB.getInstance().executeBySQLCode(sqlCode1, null);

            for (String id : list)
            {
                tdb.executeBySQLCode(sqlCode2, new Object[] { id });
            }

            tdb.commit();
        }
        catch (DAOException e)
        {
            tdb.rollback();
            LOG.error("游戏商品全量导入出错！", e);
            throw new DAOException("游戏商品全量导入出错:", e);
        }
        finally
        {
            if (tdb != null)
            {
                tdb.close();
            }
        }

    }

    public void delReference() throws DAOException
    {
        Connection conn = DB.getInstance().getConnection();
        CallableStatement cs = null;
        try
        {
        	LOG.debug("开始执行存贮过程：p_del_gamebase_black_rel");
            cs = conn.prepareCall("{call p_del_gamebase_black_rel}");  
            cs.execute();  
            LOG.debug("完成执行存贮过程：p_del_gamebase_black_rel");
           
        }
        catch (Exception e)
        {
        	LOG.error("执行存贮过程：p_del_gamebase_black_rel 出错了!");
            throw new DAOException("执行存贮过程：p_del_gamebase_black_rel 出错了!:", e);
        }
        finally
        {
        	try {
				cs.close();
			} catch (SQLException e) {
				LOG.error("关闭CallableStatement对象时出错！");
			}
        	DB.close(conn);
        }
    }
    
    
    
    /**
     * 用于查询基地游戏列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryList(PageResult page, BlackVO vo)
                    throws DAOException
    {
        // select * from t_cb_content c  where to_date(c.expiretime,'yyyyMMddHH24miss')>=trunc(sysdate)
    	
    	//select t.icpservid,t.servname,t.servdesc,t.oldprice,t.mobileprice,sysdate,sysdate,1 from t_game_service_new t, t_r_gcontent c  where c.provider = 'B' and t.icpservid = c.icpservid 
        String sqlCode = "com.aspire.ponaadmin.web.baserecomm.basegame.BlackDAO.queryList";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(vo.getIcpservid()))
            {
                //sql += " and t.icpservid like('%" + vo.getIcpservid() + "%')";
            	sqlBuffer.append(" and t.icpservid  like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getIcpservid())+"%");
            }
            if (!"".equals(vo.getServname()))
            {
                //sql += " and t.servname like('%" + vo.getServname() + "%')";
            	sqlBuffer.append(" and t.servname like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getServname())+"%");
            }

            //sql += " order by t.servname asc";
            sqlBuffer.append(" order by t.servname asc");

            //page.excute(sql, null, new PageVOInterface(){
            page.excute(sqlBuffer.toString(), paras.toArray(), new PageVOInterface(){

    			public void CopyValFromResultSet(Object obj, ResultSet rs)
    					throws SQLException {
    				
    				BlackVO vo = (BlackVO)obj;
    				vo.setIcpservid(rs.getString("icpservid"));
    				vo.setServname(rs.getString("servname"));
    				vo.setOldprice(rs.getInt("oldprice"));
    				vo.setMobileprice(rs.getInt("mobileprice"));
    				vo.setServdesc(rs.getString("servdesc"));
    				
    			}

    			public Object createObject() {
    				// TODO Auto-generated method stub
    				return new BlackVO();
    			}});
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }
}
