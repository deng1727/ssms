/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm.basegame;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;

/**
 * @author x_wangml
 * 
 */
public class BaseGameDAO
{

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseGameDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static BaseGameDAO instance = new BaseGameDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private BaseGameDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static BaseGameDAO getInstance()
    {
        return instance;
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class BaseGamePageVO implements PageVOInterface
    {

        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {

            BaseGameVO vo = ( BaseGameVO ) content;
            vo.setPkgId(rs.getString("pkgId"));
            vo.setPkgName(rs.getString("pkgName"));
            vo.setPkgType(rs.getString("gameType"));
            vo.setPkgDesc(rs.getString("pkgDesc"));
        }

        public Object createObject()
        {

            return new BaseGameVO();
        }
    }

    /**
     * ���ڲ�ѯ������Ϸ�������
     * 
     * @param page
     * @param gameName
     * @param gameDesc
     * @throws DAOException
     */
    public void queryBaseGameList(PageResult page, String gameName,
                                  String gameDesc) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBaseGameList(" + gameName + "," + gameDesc
                         + ") is starting ...");
        }

        // select * from v_base_game t t where 1=1
        String sqlCode = "basegame.BaseGameDAO.queryBaseGameList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            

            StringBuffer sqlBuffer = new StringBuffer(sql);
            List paras = new ArrayList();
          //����������sql�Ͳ���
            
            if (!"".equals(gameName))
            {
                //sql += " and t.pkgName like ('%" + gameName + "%')";
            	sqlBuffer.append(" and t.pkgName like ? ");
            	paras.add("%"+SQLUtil.escape(gameName)+"%");
            }
            if (!"".equals(gameDesc))
            {
                //sql += " and t.pkgDesc like ('%" + gameDesc + "%')";
            	sqlBuffer.append(" and t.pkgDesc like ? ");
            	paras.add("%"+SQLUtil.escape(gameDesc)+"%");
            }

            //page.excute(sql, null, new BaseGamePageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new BaseGamePageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * ���ڲ�ѯ������Ϸ�������
     * 
     * @param page
     * @param gameName
     * @param gameDesc
     * @throws DAOException
     */
    public void queryBaseGameTempList(PageResult page) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryBaseGameList( ) is starting ...");
        }

        // select * from t_base_temp t, v_base_game v where v.pkgid = t.baseid
        // and t.basetype='baseGame'
        String sqlCode = "basegame.BaseGameDAO.queryBaseGameTempList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            page.excute(sql, null, new BaseGamePageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * ���������ļ�Ӧ������
     * 
     * @throws DAOException
     */
    public List queryToFileData() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("queryToFileData( ) is starting ...");
        }

        // select * from t_base_temp t, v_base_game v where v.pkgid = t.baseid
        // and t.basetype='baseGame'
        String sqlCode = "basegame.BaseGameDAO.queryBaseGameTempList().SELECT";
        List list = new ArrayList();
        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[] {});

            // �������next˵����������
            while (rs.next())
            {
                list.add(formatDataLine(rs));
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("��ȡ������ʱ��ʱ�������ݿ����");
        }
        finally
        {
            DB.close(rs);
        }

        return list;
    }

    /**
     * ��������ÿ����ʾ����
     * 
     * @param rs
     * @return
     * @throws SQLException
     */
    private String formatDataLine(ResultSet rs) throws SQLException
    {
        StringBuffer temp = new StringBuffer();

        temp.append("GM")
            .append(splitString())
            .append(rs.getString("pkgId"))
            .append(splitString())
            .append(rs.getString("pkgName"));

        return temp.toString();
    }

    /**
     * ���طָ���ASCII��31(0x1F)
     * 
     * @return
     */
    private char splitString()
    {
        String tmp = "0x1F";

        // 0x��ͷ�ģ���ʾ��16���Ƶģ���Ҫת��
        String s = tmp.substring(2, tmp.length());
        int i = Integer.parseInt(s, 16);

        return ( char ) i;
    }
}
