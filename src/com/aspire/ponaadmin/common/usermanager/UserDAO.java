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
 * <p>用户管理的DAO类</p>
 * <p>用户管理的DAO类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserDAO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(UserDAO.class) ;

        /**
         * 私有构造方法
         */
        private UserDAO ()
        {
        }

    /**
     * 从数据库记录集获取数据并封装到一个UserVO对象
     * @param user UserVO UserVO对象
     * @param rs ResultSet 数据库记录集
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
        // 密码是否要放到vo里面？
        user.setPassword(rs.getString("password"));
        user.setCheckDESC(rs.getString("checkDESC"));
        user.setMISCID(rs.getString("misc_id"));
    }

    /**
     * 添加一条用户信息到数据库
     * 
     * @param user UserVO，要添加的用户信息
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
     * 更新一条用户信息到数据库
     * @param user UserVO，要更新的用户信息
     * @return int,有多少个用户被更新了。
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
        //根据各个字段是否为null来构造set的sql语句
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
        //替换sql里面变量
        sql = replace(sql, "{1}", setSQL.toString()) ;
        Object[] paras =
            {user.getUserID()} ;
        return DB.getInstance().execute(sql, paras) ;
    }

    /**
     * 为update语句构造的时候，添加逗号分隔。
     * @param sb 保存sql语句的StringBuffer
     */
    private static void appendSQLSep (StringBuffer sb)
    {
        if (sb.length() != 0)
        {
            sb.append(",") ;
        }
    }

    /**
     * 通过关键字和用户状态获取用户列表
     * @param key String 关键字，和userID、name进行模糊查找
     * @param states int[] 用户状态列表
     * @return List 用户列表
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
     * 通过关键字和用户状态获取用户列表，实现分页
     * @param page PageResult
     * @param key String 关键字，和userID、name进行模糊查找
     * @param states int[] 用户状态列表
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
     * 用户搜索功能，实现分页
     * @param page PageResult 分页对象
     * @param userVO UserVO，搜索条件
     * @param roleID String，对应的角色
     * @param rightType int，对应的权限类型
     * @param rightID String，对应的权限ID
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
        //构造搜索的sql和参数
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
            //只能查询出已经注册成功的用户
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
                //双重子查询嵌套！！！，不过没有好办法了。
                sql.append(" and r.roleid in ");
                sql.append("(select roleid from t_roleright where RIGHTID in");
                sql.append(" (SELECT RIGHTID FROM t_right START WITH RIGHTID=? CONNECT BY PRIOR parentid = RIGHTID))");
                paras.add(rightID) ;
            }
            else if(rightType == RightManagerConstant.TYPE_SITE)
            {
                //双重子查询嵌套！！！，不过没有好办法了。
                sql.append(" and r.roleid in ");
                sql.append("(select roleid from t_rolesiteright where NODEID in");
                sql.append(" (SELECT NODEID FROM t_node START WITH NODEID=? CONNECT BY PRIOR parentid = NODEID))");
                paras.add(rightID) ;
            }
            else
            {
                //未知类型，记录错误日志
                logger.error("unknown rightType[" + rightType + "[ found! ") ;
            }
        }
        sql.append(" order by u.userid");

        page.excute(sql.toString(), paras.toArray(), new UserPageVO()) ;
    }

    /**
     * 通过用户ID查询用户信息
     * @param userID String 查询的用户ID关键字
     * @return UserVO 对应的用户
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
     * 新增一个可以传递connection的方法-penglq
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
     * 获取所有用户的列表
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
     * 删除一条用户信息
     * @param userID String 要删除的用户帐号ID
     * @return int 有多少个用户被删除
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
     * 用来实现在string 中的替换
     * @param line String 需要被处理的字符串
     * @param oldString String 被替换的内容
     * @param newString String 替换后的新内容
     * @return String 替换结果
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
