package com.aspire.ponaadmin.common.usermanager ;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.MD5;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * <p>用户管理组件BO类</p>
 * <p>用户管理组件BO类，提供外部调用的全部接口</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserManagerBO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(UserManagerBO.class);

	/**
	 * singleton模式的实例
	 */
    private static UserManagerBO instance = new UserManagerBO();

    /**
     * 构造方法，由singleton模式调用
     */
    protected UserManagerBO ()
    {
    }

	/**
	 * 获取实例
	 * @return 实例
	 */
    public static UserManagerBO getInstance()
    {
        return instance;
    }

    /**
     * 用户注册请求，注册后为预注册用户。
     * @param userID String,用户帐号
     * @param pwd String,密码
     * @return 操作结果0:成功，3数据库错误，1008用户已经存在
     * @throws BOException
     */
    public int registerReq(String userID, String pwd) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("registerReq("+userID+','+pwd+')');
        }
        if((userID == null) || userID.trim().equals("") || (pwd == null))
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        //检查用户是否存在
        if(this.getUserByID(userID) != null)
        {
            return UserManagerConstant.USER_EXISTED;
        }
        try
        {
            UserVO user = new UserVO();
            user.setUserID(userID);
            user.setPassword(MD5.getHexMD5Str(pwd));
            user.setState(UserManagerConstant.STATE_PRE_REGISTER);
            UserDAO.addUser(user);
        }
        catch(Exception e)
        {
            logger.error(e);
            return UserManagerConstant.FAIL;
        }
        return UserManagerConstant.SUCC;
    }

    /**
     * 注册审核。
     * @param userID String,审核的用户ID
     * @param result boolean,审核通过与否，true审核通过，false审核不通过
     * @param desc String,审核描述，当不通过的时候需要提供
     * @throws BOException
     */
    public void checkRegister(String userID, boolean result, String desc) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("checkRegister("+userID+','+result+','+desc+')');
        }
        if(userID == null || userID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        try
        {
            UserVO user = new UserVO();
            user.setUserID(userID);
            user.setCheckDESC(desc);
            int state = UserManagerConstant.STATE_NORMAL;
            //审核不通过
            if(result == false)
            {
                state = UserManagerConstant.STATE_CHECK_FAIL;
            }
            user.setState(state);
            UserDAO.updateUser(user);
        }
        catch(DAOException e)
        {
            throw new BOException("checkRegister error", e);
        }
    }

    /**
     * 修改用户个人信息方法。
     * @param user UserVO,用户信息
     * @throws BOException
     */
    public void modifyUserInfo(UserVO user) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("modifyUserInfo("+user+')');
        }
        if((user == null) || (user.getUserID() == null) || user.getUserID().trim().equals(""))
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        try
        {
            UserDAO.updateUser (user) ;
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("修改用户个人信息失败！", e);
        }
    }

    /**
     * 用户登录方法
     * @param userID String,用户ID（帐号）
     * @param pwd String,密码
     * @return int，0：成功，1002：用户不存在，1003：密码错误，1：系统原因失败
     * @throws BOException
     */
    public int login(String userID, String pwd) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("login("+userID+','+pwd+')');
        }
        if((userID == null) || userID.trim().equals("") || (pwd == null))
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        int result = UserManagerConstant.SUCC;
        try
        {
            UserVO user = UserDAO.getUserByID(userID);
            if(user == null)
            {
                //找不到用户
                result = UserManagerConstant.USER_NOT_EXISTED;
            }
            else
            {
                //密码是采用加密形式存放的。
                if(!user.getPassword().equals(MD5.getHexMD5Str(pwd)))
                {
                    //密码不对
                    result = UserManagerConstant.INVALID_PWD;
                }
            }
        }
        catch(Exception e)
        {
            logger.error("error during login.", e);
            result = UserManagerConstant.FAIL;
        }
        return result;
    }
    /**
     * 用户登录方法
     * @param userID String,用户ID（帐号）
     * @param pwd String,密码
     * @return int，0：成功，1002：用户不存在，1003：密码错误，1：系统原因失败
     * @throws BOException
     */
    public int userCenterLogin(String userID, String pwd) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("login("+userID+','+pwd+')');
        }
        if((userID == null) || userID.trim().equals("") || (pwd == null))
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        int result = UserManagerConstant.SUCC;
        try
        {
            UserVO user = UserDAO.getUserByID(userID);
            if(user == null)
            {
                //找不到用户
                result = UserManagerConstant.USER_NOT_EXISTED;
            }
            else
            {
                //密码是采用加密形式存放的。
                if(!user.getPassword().toUpperCase().equals(pwd.toUpperCase()))
                {
                    //密码不对
                    result = UserManagerConstant.INVALID_PWD;
                }
            }
        }
        catch(Exception e)
        {
            logger.error("error during login.", e);
            result = UserManagerConstant.FAIL;
        }
        return result;
    }
    /**
     * <br/>修改用户密码
     * <br/>检查参数
     * <br/>检查用户是否存在
     * <br/>检查用户的旧密码是否正确
     * <br/>修改用户密码
     * <br/>上述步骤如果有出现数据库访问失败，返回系统原因失败
     * @param userID String,用户帐号
     * @param oldPwd String,旧密码
     * @param newPwd String,新密码
     * @param userState int,用户状态
     * @return int，0：成功，1002：用户不存在，1003：密码错误，1：系统原因失败
     * @throws BOException
     */
    public int modifyUserPW(String userID, String oldPwd, String newPwd, int userState)
        throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("modifyUserPW("+userID+','+oldPwd+','+newPwd+')');
        }
        if((userID == null) || userID.trim().equals("") || (oldPwd == null) || (newPwd == null))
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        //先登录一下，检查用户名和旧密码的正确性。
        int result = this.login(userID, oldPwd);
        //只有登录成功，才进行修改密码操作。
        if(result == UserManagerConstant.SUCC)
        {
            try
            {
                UserVO user = new UserVO();
                user.setUserID(userID);
                user.setPassword(MD5.getHexMD5Str(newPwd));
                //如果是密码复位的用户，需要把状态设置为正常
                if(userState == UserManagerConstant.STATE_PWD_RESET)
                {
                    user.setState(UserManagerConstant.STATE_NORMAL);
                }
                UserDAO.updateUser(user);
            }
            catch(Exception e)
            {
                logger.error(e);
                result = UserManagerConstant.FAIL;
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("result is["+result+']');
        }
        return result;
    }

    /**
     * 用户密码复位
     * @param userID String
     * @return String 用户的新密码
     * @throws BOException
     */
    public String resetUserPW(String userID) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("resetUserPW("+userID+')');
        }
        if(userID == null || userID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        String password = null;
        try
        {
            password = PasswordGenerator.generate();
            UserVO user = new UserVO();
            user.setUserID(userID);
            user.setPassword(MD5.getHexMD5Str(password));
            user.setState(UserManagerConstant.STATE_PWD_RESET);
            int count = UserDAO.updateUser(user);
            //没有用户被修改，说明用户找不到。
            if(count == 0)
            {
                throw new BOException("user not found!", UserManagerConstant.USER_NOT_EXISTED);
            }
        }
        catch(Exception e)
        {
            logger.error("error during lockUser.", e);
            throw new BOException("reset user fail!", e);
        }
        return password;
    }

    /**
     * 用户帐号锁定
     * @param userID String
     * @return int，0：成功，1002：用户不存在，1：系统原因失败
     * @throws BOException
     */
    public int lockUser(String userID) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("lockUser("+userID+')');
        }
        if(userID == null || userID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        int result = UserManagerConstant.SUCC;
        try
        {
            UserVO user = new UserVO();
            user.setUserID(userID);
            user.setState(UserManagerConstant.STATE_LOCKED);
            int count = UserDAO.updateUser(user);
            //没有用户被修改，说明用户找不到。
            if(count == 0)
           {
                result = UserManagerConstant.USER_NOT_EXISTED;
            }
        }
        catch(Exception e)
        {
            logger.error("error during lockUser.", e);//reult=ErrorCode.FAIL;
            result = UserManagerConstant.FAIL;
       }
        return result;
    }

    /**
     * 用户帐号解锁
     * @param userID String
     * @return int，0：成功，1002：用户不存在，1：系统原因失败
     * @throws BOException
     */
    public int unlockUser(String userID) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("unlockUser("+userID+')');
        }
        if(userID == null || userID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        int result = UserManagerConstant.SUCC;
        try
        {
            UserVO user = new UserVO();
            user.setUserID(userID);
            user.setState(UserManagerConstant.STATE_NORMAL);
            int count = UserDAO.updateUser(user);
            //没有用户被修改，说明用户找不到。
            if(count == 0)
            {
                result = UserManagerConstant.USER_NOT_EXISTED;
            }
        }
        catch(Exception e)
        {
            logger.error("error during unlockUser.", e);
            result = UserManagerConstant.FAIL;
        }
        return result;
    }

    /**
     * 根据用户帐号查询用户信息
     * @param userID String用户帐号id
     * @return UserVO,用户信息，找不到为null
     * @throws BOException
     */
    public UserVO getUserByID(String userID) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getUserByID("+userID+')');
        }
        if(userID == null || userID.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        try
        {
            return UserDAO.getUserByID(userID);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据用户帐号查询用户信息失败！", e);
        }
    }

    /**
     * 获取所有用户的列表
     * @return List
     */
    public List getAllUser() throws BOException
    {
        if (logger.isDebugEnabled ())
        {
            logger.debug ("getAllUser()") ;
        }
        try
        {
            return UserDAO.getAllUser () ;
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("获取所有用户的列表失败！", e);
        }
    }

    /**
     * 通过关键字和用户状态获取用户列表
     * 实现分页
     * @param page PageResult 分页对象
     * @param key String 关键字，和userID、name进行模糊查找
     * @param states int[] 用户状态列表，请见类UserManagerConstant中的定义。如果不在其中不会出错，只是查找不到。
     * @throws BOException
     * @see com.aspire.ponaadmin.common.usermanager.UserManagerConstant
     */
    public void getUser(PageResult page, String key, int[] states) throws BOException
    {
        if (logger.isDebugEnabled ())
        {
            logger.debug("getUser(" + key + "," + states + ')') ;
        }
        if((page == null) || (states == null) || (states.length == 0))
        {
            throw new BOException("invalid para", UserManagerConstant.INVALID_PARA);
        }
        if(key == null)
        {
            key = "";
        }
        try
		{
            UserDAO.getUser(page, key, states) ;
		}
        catch(DAOException e)
		{
        	throw new BOException("getUser fail!",e,UserManagerConstant.FAIL);
		}
    }

    /**
     * 用户搜索功能，实现分页
     * @param page PageResult 分页对象
     * @param userVO UserVO，搜索条件
     * @param roleID String，对应的角色
     * @param rightType int，对应的权限类型
     * @param rightID String，对应的权限ID
     * @throws BOException
     * @see com.aspire.ponaadmin.common.usermanager.UserManagerConstant
     */
    public void searchUser(PageResult page, UserVO userVO, String roleID,int rightType,String rightID) throws BOException
    {
        if (logger.isDebugEnabled ())
        {
            logger.debug("searchUser(" + page + "," + userVO + "," + roleID +
                         "," + rightType + "," + rightID + ')') ;
        }
        if((page == null) || (userVO == null))
        {
            throw new BOException("invalid para", UserManagerConstant.INVALID_PARA);
        }
        try
        {
            UserDAO.searchUser(page,userVO,roleID,rightType,rightID);
        }
        catch (DAOException e)
        {
            throw new BOException("搜索用户失败！",e);
        }
    }

    /**
     * 销毁一个用户的信息
     * @param userID String,用户帐号
     * @return int，0：成功，1002：用户不存在，1：系统原因失败
     * @throws BOException
     */
    public int checkoutUser(String userID) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("checkoutUser("+userID+')');
        }
        if(userID == null)
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        int result = UserManagerConstant.SUCC;
        try
        {
            int count = UserDAO.delUser(userID);
            if(count == 0)
            {
                result = UserManagerConstant.USER_NOT_EXISTED;
            }
        }
        catch(DAOException e)
        {
            throw new BOException("checkoutUser error", e);
        }
        return result;
    }

    /**
     * 获取所有注册成功后的用户
     * @return List
     */
    public List getAllCommonUser()throws BOException
    {
        if (logger.isDebugEnabled ())
        {
            logger.debug ("getAllCommonUser()") ;
        }
        int[] states =
            {UserManagerConstant.STATE_NORMAL, UserManagerConstant.STATE_LOCKED,
            UserManagerConstant.STATE_PWD_RESET} ;
        try
        {
            return UserDAO.getUser ("", states) ;
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("获取所有注册成功后的用户失败！", e);
        }
    }

    /**
     * 从session中去用户session信息的vo
     * @param session HttpSession http的session
     * @return UserSessionVO 保存在session里面的VO类，封装用户需要保存在session里面的全部信息
     */
    public UserSessionVO getUserSessionVO(HttpSession session)
    {
    	if(session == null)
    	{
    		return null;
    	}
        UserSessionVO userSession = (UserSessionVO) (session.getAttribute(
            UserManagerConstant.USER_SESSION_KEY)) ;
        return userSession;
    }

    /**
     * 把用户信息保存到session中
     * @param session HttpSession http的session
     * @param userSession UserSessionVO 封装用户需要保存在session里面的全部信息的VO类
     */
    public void setUserSessionVO(HttpSession session, UserSessionVO userSession)
    {
    	if(session == null)
    	{
    		return;
    	}
        session.setAttribute(UserManagerConstant.USER_SESSION_KEY, userSession);
    }
}
