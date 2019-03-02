package com.aspire.ponaadmin.common.usermanager ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.util.ClearDBResource;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.rightmanager.RightManagerConstant;
/**
 * <p>�û������DAO��</p>
 * <p>�û������DAO��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserDAO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(UserDAO.class) ;

        /**
         * ˽�й��췽��
         */
        private UserDAO ()
        {
        }

    /**
     * �����ݿ��¼����ȡ���ݲ���װ��һ��UserVO����
     * @param user UserVO UserVO����
     * @param rs ResultSet ���ݿ��¼��
     */
    public static void getUserVOFromRS (UserVO user, ResultSet rs) throws SQLException
    {
        user.setUserID(rs.getString("userID"));
        user.setName(rs.getString("name"));
        user.setSex(rs.getString("sex"));
        user.setBirthday(rs.getString("birthday"));
        user.setCertType(rs.getString("certtype"));
        user.setCertID(rs.getString("certid"));
        user.setCompanyName(rs.getString("companyname"));
        user.setCompanyAddr(rs.getString("companyaddr"));
        user.setPostcode(rs.getString("postcode"));
        user.setPhone(rs.getString("phone"));
        user.setMobile(rs.getString("mobile"));
        user.setEmail(rs.getString("email"));
        user.setQQ(rs.getString("qq"));
        user.setMSN(rs.getString("msn"));
        user.setState(rs.getInt("state"));
        // �����Ƿ�Ҫ�ŵ�vo���棿
        user.setPassword(rs.getString("password"));
        user.setCheckDESC(rs.getString("checkDESC"));
        user.setMISCID(rs.getString("misc_id"));
    }

    /**
     * ���һ���û���Ϣ�����ݿ�
     * 
     * @param user UserVO��Ҫ��ӵ��û���Ϣ
     */
    static final void addUser (UserVO user) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addUser(" + user + ")") ;
        }
        String sqlCode = "usermanager.UserDAO.addUser().INSERT" ;
        Object[] paras =
            {user.getUserID(), user.getPassword(), new Integer(user.getState())} ;
        DB.getInstance().executeBySQLCode(sqlCode, paras) ;
    }

    /**
     * ����һ���û���Ϣ�����ݿ�
     * @param user UserVO��Ҫ���µ��û���Ϣ
     * @return int,�ж��ٸ��û��������ˡ�
     */
    static final int updateUser (UserVO user) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateUser(" + user + ")") ;
        }
        String sqlCode = "usermanager.UserDAO.updateUser().UPDATE" ;
        String sql = "" ;
        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode) ;
        }
        catch (Exception e)
        {
            throw new DAOException("get sql error", e) ;
        }
        //���ݸ����ֶ��Ƿ�Ϊnull������set��sql���
        StringBuffer setSQL = new StringBuffer() ;
        if (user.getName() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("name='" + user.getName() + "'") ;
        }
        if (user.getPassword() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("Password='" + user.getPassword() + "'") ;
        }
        if (user.getState() != 0)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("state='" + user.getState() + "'") ;
        }
        if (user.getSex() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("sex='" + user.getSex() + "'") ;
        }
        if (user.getBirthday() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("birthday='" + user.getBirthday() + "'") ;
        }
        if (user.getCertType() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("certType='" + user.getCertType() + "'") ;
        }
        if (user.getCertID() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("certID='" + user.getCertID() + "'") ;
        }
        if (user.getCompanyName() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("companyName='" + user.getCompanyName() + "'") ;
        }
        if (user.getCompanyAddr() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("companyAddr='" + user.getCompanyAddr() + "'") ;
        }
        if (user.getPostcode() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("postcode='" + user.getPostcode() + "'") ;
        }
        if (user.getPhone() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("phone='" + user.getPhone() + "'") ;
        }
        if (user.getMobile() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("mobile='" + user.getMobile() + "'") ;
        }
        if (user.getEmail() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("email='" + user.getEmail() + "'") ;
        }
        if (user.getQQ() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("QQ='" + user.getQQ() + "'") ;
        }
        if (user.getMSN() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("MSN='" + user.getMSN() + "'") ;
        }
        if (user.getCheckDESC() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("checkDESC='" + user.getCheckDESC() + "'") ;
        }
        if (user.getMISCID() != null)
        {
            appendSQLSep(setSQL) ;
            setSQL.append("misc_id='" + user.getMISCID() + "'") ;
        }
        //�滻sql�������
        sql = replace(sql, "{1}", setSQL.toString()) ;
        Object[] paras =
            {user.getUserID()} ;
        return DB.getInstance().execute(sql, paras) ;
    }

    /**
     * Ϊupdate��乹���ʱ����Ӷ��ŷָ���
     * @param sb ����sql����StringBuffer
     */
    private static void appendSQLSep (StringBuffer sb)
    {
        if (sb.length() != 0)
        {
            sb.append(",") ;
        }
    }

    /**
     * ͨ���ؼ��ֺ��û�״̬��ȡ�û��б�
     * @param key String �ؼ��֣���userID��name����ģ������
     * @param states int[] �û�״̬�б�
     * @return List �û��б�
     */
    static final List getUser (String key, int[] states) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getUser(" + key + "," + states + ')') ;
        }
        String sqlCode = "usermanager.UserDAO.getUser().QUERY" ;
        String sql = DB.getInstance().getSQLByCode(sqlCode) ;
        StringBuffer sqlIn = new StringBuffer() ;
        for (int i = 0 ; i < states.length ; i++)
        {
            if (i > 0)
            {
                sqlIn.append(',') ;
            }
            sqlIn.append(states[i]) ;
        }
        sql = replace(sql, "<$1>", sqlIn.toString()) ;
        String sqlKey = "%" + key + "%" ;
        Object[] paras =
            {sqlKey, sqlKey} ;
        ResultSet rs = null ;
        List list = new ArrayList() ;
        try
        {
            rs = DB.getInstance().query(sql, paras) ;
            while (rs != null && rs.next())
            {
                UserVO user = new UserVO() ;
                getUserVOFromRS(user, rs) ;
                list.add(user) ;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("getUser error.", e) ;

        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close() ;
                }
                catch (Exception e)
                {
                    logger.error(e) ;
                }
            }
        }
        return list ;
    }

    /**
     * ͨ���ؼ��ֺ��û�״̬��ȡ�û��б�ʵ�ַ�ҳ
     * @param page PageResult
     * @param key String �ؼ��֣���userID��name����ģ������
     * @param states int[] �û�״̬�б�
     */
    static final void getUser (PageResult page, String key, int[] states) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getUser(" + page + "," + key + "," + states + ')') ;
        }
        String sqlCode = "usermanager.UserDAO.getUser().QUERY" ;
        String sql = DB.getInstance().getSQLByCode(sqlCode) ;
        StringBuffer sqlIn = new StringBuffer() ;
        for (int i = 0 ; i < states.length ; i++)
        {
            if (i > 0)
            {
                sqlIn.append(',') ;
            }
            sqlIn.append(states[i]) ;
        }
        sql = replace(sql, "<$1>", sqlIn.toString()) ;
        String sqlKey = "%" + key + "%" ;
        Object[] paras =
            {sqlKey, sqlKey} ;
        page.excute(sql, paras, new UserPageVO()) ;
    }

    /**
     * �û��������ܣ�ʵ�ַ�ҳ
     * @param page PageResult ��ҳ����
     * @param userVO UserVO����������
     * @param roleID String����Ӧ�Ľ�ɫ
     * @param rightType int����Ӧ��Ȩ������
     * @param rightID String����Ӧ��Ȩ��ID
     * @see com.aspire.ponaadmin.common.usermanager.UserManagerConstant
     */
    static final void searchUser(PageResult page, UserVO userVO, String roleID,int rightType,String rightID) throws DAOException
    {
        if (logger.isDebugEnabled ())
        {
            logger.debug("searchUser(" + page + "," + userVO + "," + roleID +
                         "," + rightType + "," + rightID + ')') ;
        }
        StringBuffer sql = new StringBuffer() ;
        List paras = new ArrayList();
        //����������sql�Ͳ���
        sql.append("select distinct u.* from t_user u,t_userrole r where r.userid (+)= u.userid");
        if(!userVO.getUserID().equals(""))
        {
            sql.append(" and u.userid like ?");
            //paras.add("%" + userVO.getUserID() + "%") ;
            paras.add("%" + SQLUtil.escape(userVO.getUserID()) + "%") ;
        }
        if(!userVO.getName().equals(""))
        {
            sql.append(" and u.name like ?");
            //paras.add("%" + userVO.getName() + "%") ;
            paras.add("%" + SQLUtil.escape(userVO.getName()) + "%");
        }
        if(!userVO.getCompanyName().equals(""))
        {
            sql.append(" and u.COMPANYNAME like ?");
            //paras.add("%" + userVO.getCompanyName() + "%") ;
            paras.add("%" + SQLUtil.escape(userVO.getCompanyName()) + "%") ;
        }
        if(userVO.getState()!=0)
        {
            sql.append(" and u.STATE = ?");
            paras.add(new Integer(userVO.getState())) ;
        }
        else
        {
            //ֻ�ܲ�ѯ���Ѿ�ע��ɹ����û�
            sql.append(" and u.STATE in(");
            sql.append(UserManagerConstant.STATE_NORMAL);
            sql.append(',');
            sql.append(UserManagerConstant.STATE_LOCKED);
            sql.append(',');
            sql.append(UserManagerConstant.STATE_PWD_RESET);
            sql.append(')');
        }
        if(!roleID.equals(""))
        {
            sql.append(" and r.roleid = ?");
            paras.add(new Integer(roleID)) ;
        }
        if(rightType!=0)
        {
            if(rightType == RightManagerConstant.TYPE_RIGHT)
            {
                //˫���Ӳ�ѯǶ�ף�����������û�кð취�ˡ�
                sql.append(" and r.roleid in ");
                sql.append("(select roleid from t_roleright where RIGHTID in");
                sql.append(" (SELECT RIGHTID FROM t_right START WITH RIGHTID=? CONNECT BY PRIOR parentid = RIGHTID))");
                paras.add(rightID) ;
            }
            else if(rightType == RightManagerConstant.TYPE_SITE)
            {
                //˫���Ӳ�ѯǶ�ף�����������û�кð취�ˡ�
                sql.append(" and r.roleid in ");
                sql.append("(select roleid from t_rolesiteright where NODEID in");
                sql.append(" (SELECT NODEID FROM t_node START WITH NODEID=? CONNECT BY PRIOR parentid = NODEID))");
                paras.add(rightID) ;
            }
            else
            {
                //δ֪���ͣ���¼������־
                logger.error("unknown rightType[" + rightType + "[ found! ") ;
            }
        }
        sql.append(" order by u.userid");

        page.excute(sql.toString(), paras.toArray(), new UserPageVO()) ;
    }

    /**
     * ͨ���û�ID��ѯ�û���Ϣ
     * @param userID String ��ѯ���û�ID�ؼ���
     * @return UserVO ��Ӧ���û�
     */
    static final UserVO getUserByID (String userID) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getUserByID(" + userID + ")") ;
        }
        String sqlCode = "usermanager.UserDAO.getUserByID().QUERY" ;
        Object[] paras =
            {userID} ;
        ResultSet rs = null ;
        UserVO user = null ;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras) ;
            if (rs != null && rs.next())
            {
                user = new UserVO() ;
                getUserVOFromRS(user, rs) ;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("getUserByID error.", e) ;

        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close() ;
                }
                catch (Exception e)
                {
                    logger.error(e) ;
                }
            }
        }
        return user ;
    }


    /**
     * ����һ�����Դ���connection�ķ���-penglq
     * @param userID String
     * @return UserVO
     */
    public static final UserVO getUserByID (java.sql.Connection conn,String userID) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getUserByID(" + userID + ")") ;
        }
        String sqlCode = "usermanager.UserDAO.getUserByID().QUERY" ;
        java.sql.PreparedStatement pstmt = null;
        ResultSet rs = null ;
        UserVO user = null ;
        try
        {
            pstmt = conn.prepareStatement(SQLCode.getInstance().getSQLStatement(sqlCode));
            pstmt.setString(1,userID);
            rs = pstmt.executeQuery();
            if (rs != null && rs.next())
            {
                user = new UserVO() ;
                getUserVOFromRS(user, rs) ;
            }
        }
        catch (Exception e)
        {
            throw new DAOException("getUserByID error.", e) ;

        }
        finally
        {
           ClearDBResource.closeResultSet(rs);
           ClearDBResource.closeStatment(pstmt);
        }
        return user ;
    }




    /**
     * ��ȡ�����û����б�
     * @return List
     */
    static final List getAllUser () throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getAllUser()") ;
        }
        String sqlCode = "usermanager.UserDAO.getAllUser().QUERY" ;
        ResultSet rs = null ;
        List list = new ArrayList() ;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null) ;
            while (rs != null && rs.next())
            {
                UserVO user = new UserVO() ;
                getUserVOFromRS(user, rs) ;
                list.add(user) ;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("getAllUser error.", e) ;

        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close() ;
                }
                catch (Exception e)
                {
                    logger.error(e) ;
                }
            }
        }
        return list ;
    }

    /**
     * ɾ��һ���û���Ϣ
     * @param userID String Ҫɾ�����û��ʺ�ID
     * @return int �ж��ٸ��û���ɾ��
     */
    static final int delUser (String userID) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delUser(" + userID + ")") ;
        }
        String sqlCode = "usermanager.UserDAO.delUser().DELETE" ;
        Object[] paras =
            {userID} ;
        return DB.getInstance().executeBySQLCode(sqlCode, paras) ;
    }

    /**
     * ����ʵ����string �е��滻
     * @param line String ��Ҫ��������ַ���
     * @param oldString String ���滻������
     * @param newString String �滻���������
     * @return String �滻���
     */
    private static final String replace(String line, String oldString,
                                       String newString)
    {
      if (line == null)
      {
        return null;
      }
      int i = 0;
      if ( (i = line.indexOf(oldString, i)) >= 0)
      {
        char[] line2 = line.toCharArray();
        char[] newString2 = newString.toCharArray();
        int oLength = oldString.length();
        StringBuffer buf = new StringBuffer(line2.length);
        buf.append(line2, 0, i).append(newString2);
        i += oLength;
        int j = i;
        while ( (i = line.indexOf(oldString, i)) > 0)
        {
          buf.append(line2, j, i - j).append(newString2);
          i += oLength;
          j = i;
        }
        buf.append(line2, j, line2.length - j);
        return buf.toString();
      }
      return line;
  }
  }
