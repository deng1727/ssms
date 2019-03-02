package com.aspire.ponaadmin.common.ressync;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class RsyncLogDAO
{
  private JLogger log = LoggerFactory.getLogger(RsyncLogDAO.class);

  private Connection conn = null;

  public void batchInsert(ArrayList list)
  {
    String sql = 
      "insert into RESRSYNC_LOG(ID,FROM_IP,TO_IP,STATUS,CONTENT) values(?,?,?,?,?)";
    if (this.log.isDebugEnabled())
      this.log.debug("batchInsert==" + sql);
    PreparedStatement statement = null;
    try
    {
      int size = list.size();

      this.conn = getConn();
      statement = this.conn.prepareStatement(sql);
      String id = "";
      int serverType = 1;
      for (int i = 0; i < size; i++)
      {
        RsyncLogVO logVO = (RsyncLogVO)list.get(i);
        if (i == 0)
        {
          id = logVO.getID();
          serverType = logVO.getServerType();
        }
        statement.setString(1, logVO.getID());
        statement.setString(2, logVO.getFromIP());
        statement.setString(3, logVO.getToIP());
        statement.setString(4, logVO.getStatus());
        statement.setString(5, logVO.getContent());
        statement.addBatch();
      }
      statement.executeBatch();
      DB.close(statement);

      updateStatus(id, serverType);
    }
    catch (SQLException ex)
    {
      this.log.error("batchInsert exception==" + ex.getMessage(), ex);
    }
    finally
    {
      DB.close(statement, this.conn);
    }
  }

  public void updateStatus(String id, int serverType)
  {
    String sql = 
      "select 1,count(*) total from RESRSYNC_LOG where id=? union select 2,count(*) total from RESRSYNC_LOG where id=? and STATUS='Y'";

    PreparedStatement statement = null;
    ResultSet rs = null;
    int total = 0;
    int okTotal = 0;
    int serverNum = 1;
    try
    {
      statement = this.conn.prepareStatement(sql);
      statement.setString(1, id);
      statement.setString(2, id);
      rs = statement.executeQuery();
      int i = 0;
      while (rs.next())
      {
        if (i == 0)
        {
          total = rs.getInt("total");
        }
        else
        {
          okTotal = rs.getInt("total");
        }
        i++;
      }

      if (this.log.isDebugEnabled())
        this.log.debug("###########total==" + total);
      if (this.log.isDebugEnabled()) {
        this.log.debug("###########okTotal==" + okTotal);
      }
      if (this.log.isDebugEnabled()) {
        this.log.debug("###########serverType==" + serverType);
      }
      if (serverType == RsyncLogVO.IS_PRESEVER)
      {
        serverNum = RsyncLogBO.statusVO.getServerTestNum();
      }
      else
      {
        serverNum = RsyncLogBO.statusVO.getServerNum();
      }
      if (this.log.isDebugEnabled()) {
        this.log.debug("###########serverNum==" + serverNum);
      }
      if ((total > 0) && (total == serverNum))
      {
        StringBuffer sb = new StringBuffer(50);
        sb.append("update ");
        sb.append(RsyncLogBO.statusVO.getTableName());
        sb.append(" set ");
        sb.append(RsyncLogBO.statusVO.getFieldName());
        sb.append("=?");
        sb.append(" where id=?");
        if (this.log.isDebugEnabled())
          this.log.debug("###########updateStatus==" + sb.toString());
        String sqlUpdate = sb.toString();
        statement = this.conn.prepareStatement(sqlUpdate);
        if (total == okTotal)
        {
          statement.setString(1, RsyncLogBO.statusVO.getOkStatus());
        }
        else
        {
          statement.setString(1, RsyncLogBO.statusVO.getFailStatus());
        }

        statement.setString(2, id);

        int up = statement.executeUpdate();
        if (this.log.isDebugEnabled()) {
          this.log.debug("@@@@@@@@up==" + up);
        }
      }
      DB.close(rs, statement);
    }
    catch (SQLException ex)
    {
      this.log.error("updateStatus exception==" + ex.getMessage(), ex);
      DB.close(rs, statement);
    }
  }

  private Connection getConn()
  {
    try
    {
      this.conn = DB.getInstance().getConnection();
    }
    catch (Exception ex)
    {
      this.log.error("exception==" + ex.getMessage(), ex);
    }
    return this.conn;
  }

  private Connection testConn()
  {
    Connection conn = null;
    String drivers = "oracle.jdbc.driver.OracleDriver";
    String m_URL = "jdbc:oracle:thin:@10.1.3.115:1521:ora9i";
    String m_User = "pona";
    String m_Password = "pona";
    try
    {
      Driver driver = (Driver)Class.forName(drivers).newInstance();
      conn = DriverManager.getConnection(m_URL, m_User, m_Password);
    }
    catch (Exception ex1)
    {
      ex1.printStackTrace();
    }
    return conn;
  }

  public List getById(String id)
    throws DAOException
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("execute method getById(String id=" + id + ")");
    }
    String sql = "select * from RESRSYNC_LOG where id=?";
    if (this.log.isDebugEnabled()) {
      this.log.debug("getById sql=" + sql);
    }
    Object[] paras = { id };

    ResultSet rs = null;
    List list = new ArrayList();
    try
    {
      rs = DB.getInstance().query(sql, paras);
      while ((rs != null) && (rs.next()))
      {
        RsyncLogVO vo = getModel(rs);
        list.add(vo);
      }
      return list;
    }
    catch (SQLException e)
    {
      throw new DAOException(
        "getById(String) error.", e);
    }
    finally
    {
      if (rs != null)
      {
        try
        {
          rs.close();
        }
        catch (Exception e)
        {
          this.log.error(e);
        }
      }
    }
  }

  private RsyncLogVO getModel(ResultSet rs)
    throws SQLException
  {
    RsyncLogVO vo = new RsyncLogVO();
    vo.setID(rs.getString("id"));
    vo.setFromIP(rs.getString("from_ip"));
    vo.setToIP(rs.getString("to_ip"));
    vo.setStatus(rs.getString("status"));
    vo.setContent(rs.getString("content"));
    return vo;
  }
}