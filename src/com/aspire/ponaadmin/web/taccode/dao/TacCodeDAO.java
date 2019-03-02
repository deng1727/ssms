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
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(TacCodeDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static TacCodeDAO instance = new TacCodeDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private TacCodeDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static TacCodeDAO getInstance()
    {
        return instance;
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
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
     * �������ڲ�ѯTAC����б�
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
            //����������sql�Ͳ���
            
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
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * ����ɾ��ָ��Tac��
     * 
     * @param id
     * @param tacCode Tac��
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
            throw new DAOException("ɾ��ָ��TAC��ʱ�����쳣:", e);
        }
    }

    /**
     * У���ļ��е�TAC���Ƿ������TAC������
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

        // ������ԃ
        for (int i = 0; i < list.size(); i++)
        {
            String temp = ( String ) list.get(i);
            try
            {
                rs = DB.getInstance().queryBySQLCode(sql.toString(),
                                                     new Object[] { temp });
                // �����������������
                if (!rs.next())
                {
                    list.remove(i);
                    i--;
                    sb.append(temp).append(". ");
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("У��ļ��Д����Ƿ��ڻ��ͱ��д���ʱ�����쳣:", e);
            }
            finally
            {
                DB.close(rs);
            }
        }

        return sb.toString();
    }
    
    /**
     * ��ȡTaccode�б���
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
            logger.error("���ݿ�SQLִ���쳣����ѯʧ��", e);
        }
        catch (DAOException ex)
        {
            logger.error("���ݿ�����쳣����ѯʧ��", ex);
        }
        finally
        {
            DB.close(rs);
        }

        return tacCodeMap;
    }
    

    /**
     * ��ӻ���id�б�
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
            throw new DAOException("����ص����id�б�ʱ�����쳣:", e);
        }
    }
    
    /**
	 * �����������������б�
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
			// ������sql�����Ҫ�滻�Ĳ���,
			if("1".equals(type)){//����
				//insert into t_tac_code_base(id,brand,device,taccode,createtime) values(SEQ_T_TAC_CODE_BASE_ID.NEXTVAL,?,?,?,sysdate)
				sqlCode = "com.aspire.ponaadmin.web.taccode.dao.TacCodeDAO.patchImportData.insert";	
			}else if("2".equals(type)){//����
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
			logger.error("������������ʧ��:", e);
			throw new BOException("������������:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}		
		return list.size();
	}
}
