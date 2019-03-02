package com.aspire.ponaadmin.web.taccode.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.taccode.vo.TacVO;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class TacCodeDAO {

	 /**
     * 记录日志的实例对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(TacCodeDAO.class);

    /**
     * singleton模式的实例
     */
    private static TacCodeDAO instance = new TacCodeDAO();

    /**
     * 构造方法，由singleton模式调用
     */
    private TacCodeDAO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static TacCodeDAO getInstance()
    {
        return instance;
    }

    /**
     * 应用类分页读取VO的实现类
     */
    private class TacCodePageVO implements PageVOInterface
    {
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            TacVO vo = ( TacVO ) content;
            vo.setId(rs.getString("id"));
            vo.setTacCode(rs.getString("taccode"));
            vo.setDevice(rs.getString("device"));
            vo.setBrand(rs.getString("brand"));
            vo.setChannelId(rs.getString("channelid"));
            vo.setChannelName(rs.getString("channelname"));
            //vo.setCreateTime(String.valueOf(rs.getDate("createtime")));
        }

        public Object createObject()
        {
            return new TacVO();
        }
    }

    /**
     * 用于用于查询TAC码库列表
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryTacCodeList(PageResult page, TacVO vo)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryTacCodeList() is starting ...");
        }

        // select id,taccode,brand,device from t_tac_code_base where 1=1
        String sqlCode = "com.aspire.ponaadmin.web.taccode.dao.TacCodeDAO.queryTacCodeList.SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
  
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //构造搜索的sql和参数
            
            if (!"".equals(vo.getTacCode()))
            {
                //sql += " and t.taccode ='" + vo.getTacCode() + "'";
            	sqlBuffer.append(" and t.taccode =? ");
            	paras.add(vo.getTacCode());
            }
            if (!"".equals(vo.getBrand()))
            {
//                sql += " and upper(t.brand) like('%"
//                       + vo.getBrand().toUpperCase() + "%')";
            	sqlBuffer.append(" and upper(t.brand) like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getBrand().toUpperCase())+"%");
            }
            if (!"".equals(vo.getDevice()))
            {
//                sql += " and upper(t.device) like('%" + vo.getDevice().toUpperCase()
//                       + "%')";
            	sqlBuffer.append(" and upper(t.device) like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getDevice().toUpperCase())+"%");
            }
            if (!"".equals(vo.getChannelId()))
            {
//                sql += " and upper(t.device) like('%" + vo.getDevice().toUpperCase()
//                       + "%')";
            	sqlBuffer.append(" and upper(t.channelid) like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getChannelId().toUpperCase())+"%");
            }
            if (!"".equals(vo.getChannelName()))
            {
//                sql += " and upper(t.device) like('%" + vo.getDevice().toUpperCase()
//                       + "%')";
            	sqlBuffer.append(" and upper(t.channelname) like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getChannelName().toUpperCase())+"%");
            }

            //sql += " order by t.taccode asc";
            sqlBuffer.append(" order by t.taccode asc");

            //page.excute(sql, null, new TacCodePageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new TacCodePageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                                   e);
        }
    }

    /**
     * 用于删除指定Tac码
     * 
     * @param id
     * @param tacCode Tac码
     * @throws DAOException
     */
    public void delByTacCode(String id,String tacCode) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delByTacCode() is starting ...");
        }

        // delete from t_tac_code_base c where c.id = ? and c.taccode =?
        String sqlCode = "com.aspire.ponaadmin.web.taccode.dao.TacCodeDAO.delByTacCode.delete";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, new Object[]{id,tacCode});
        }
        catch (DAOException e)
        {
            throw new DAOException("删除指定TAC码时发生异常:", e);
        }
    }

    /**
     * 校验文件中的TAC码是否存在于TAC码库表中
     * 
     * @param list
     * @throws DAOException
     */
    public String verifyDeviceId(List list) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("verifyDeviceId() is starting ...");
        }

        // select 1 from t_device c where c.device_id = ?
        String sql = "pivot.PivotDeviceDAO.verifyDeviceId().select";
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        // 迭代查詢
        for (int i = 0; i < list.size(); i++)
        {
            String temp = ( String ) list.get(i);
            try
            {
                rs = DB.getInstance().queryBySQLCode(sql.toString(),
                                                     new Object[] { temp });
                // 如果不存在相應數據
                if (!rs.next())
                {
                    list.remove(i);
                    i--;
                    sb.append(temp).append(". ");
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("校驗文件中數據是否在机型表中存在时发生异常:", e);
            }
            finally
            {
                DB.close(rs);
            }
        }

        return sb.toString();
    }
    
    /**
     * 获取Taccode列表集合
     * 
     * @param list
     * @throws DAOException
     */
    public Map getTacCodeMap() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getTacCodeMap() is starting ...");
        }

        Map tacCodeMap = new HashMap();
        ResultSet rs = null;
        try
        {
        	// select t.taccode from t_tac_code_base c 
            rs = DB.getInstance()
                   .queryBySQLCode("com.aspire.ponaadmin.web.taccode.dao.TacCodeDAO.getTacCodeMap.SELECT",
                                   null);
            while (rs.next())
            {
            	tacCodeMap.put(rs.getString("taccode"), "");
            }
        }
        catch (SQLException e)
        {
            logger.error("数据库SQL执行异常，查询失败", e);
        }
        catch (DAOException ex)
        {
            logger.error("数据库操作异常，查询失败", ex);
        }
        finally
        {
            DB.close(rs);
        }

        return tacCodeMap;
    }
    

    /**
     * 添加机型id列表
     * 
     * @param String
     */
    public void addDeviceId(String[] deviceId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("addDeviceId() is starting ...");
        }

        // insert into t_pivot_device (device_id, device_name, brand_name,
        // os_name) select d.device_id, d.device_name, b.brand_name, s.osname
        // from t_device d, t_device_brand b, t_device_os s where b.brand_id =
        // d.brand_id and s.os_id = d.os_id and d.device_id = ?
        String sql = "pivot.PivotDeviceDAO.addDeviceId().add";

        String sqlCode[] = new String[deviceId.length];
        Object[][] object = new Object[deviceId.length][1];

        for (int i = 0; i < deviceId.length; i++)
        {
            sqlCode[i] = sql;
            object[i][0] = deviceId[i];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("添加重点机型id列表时发生异常:", e);
        }
    }
    
    /**
	 * 用于批量导入数据列表
	 * 
	 * @param sql
	 * @param key
	 * @return
	 * @throws BOException
	 */
	public int patchImportData(String type, List<Object[]> list) throws BOException
	{
		int ret = 0;
		String sqlCode =null;
		try
		{
			// 定义在sql语句中要替换的参数,
			if("1".equals(type)){//新增
				//insert into t_tac_code_base(id,brand,device,taccode,createtime) values(SEQ_T_TAC_CODE_BASE_ID.NEXTVAL,?,?,?,sysdate)
				sqlCode = "com.aspire.ponaadmin.web.taccode.dao.TacCodeDAO.patchImportData.insert";	
			}else if("2".equals(type)){//更新
				//update t_tac_code_base set brand =?,device=?,createtime = sysdate where taccode = ?
				sqlCode = "com.aspire.ponaadmin.web.taccode.dao.TacCodeDAO.patchImportData.update";
			}
	        Object[][] paras = new Object[list.size()][];
			for(int i =0;i<list.size();i++){
				paras[i] =  list.get(i);
			}
			DB.getInstance().executeBatchBySQLCode(sqlCode, paras);
		}
		catch (DAOException e)
		{
			logger.error("批量导入数据失败:", e);
			throw new BOException("批量导入数据:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}		
		return list.size();
	}
}
