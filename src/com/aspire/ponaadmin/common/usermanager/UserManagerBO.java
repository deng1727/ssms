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
 * <p>�û��������BO��</p>
 * <p>�û��������BO�࣬�ṩ�ⲿ���õ�ȫ���ӿ�</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserManagerBO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(UserManagerBO.class);

	/**
	 * singletonģʽ��ʵ��
	 */
    private static UserManagerBO instance = new UserManagerBO();

    /**
     * ���췽������singletonģʽ����
     */
    protected UserManagerBO ()
    {
    }

	/**
	 * ��ȡʵ��
	 * @return ʵ��
	 */
    public static UserManagerBO getInstance()
    {
        return instance;
    }

    /**
     * �û�ע������ע���ΪԤע���û���
     * @param userID String,�û��ʺ�
     * @param pwd String,����
     * @return �������0:�ɹ���3���ݿ����1008�û��Ѿ�����
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
        //����û��Ƿ����
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
     * ע����ˡ�
     * @param userID String,��˵��û�ID
     * @param result boolean,���ͨ�����true���ͨ����false��˲�ͨ��
     * @param desc String,�������������ͨ����ʱ����Ҫ�ṩ
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
            //��˲�ͨ��
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
     * �޸��û�������Ϣ������
     * @param user UserVO,�û���Ϣ
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
            throw new BOException("�޸��û�������Ϣʧ�ܣ�", e);
        }
    }

    /**
     * �û���¼����
     * @param userID String,�û�ID���ʺţ�
     * @param pwd String,����
     * @return int��0���ɹ���1002���û������ڣ�1003���������1��ϵͳԭ��ʧ��
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
                //�Ҳ����û�
                result = UserManagerConstant.USER_NOT_EXISTED;
            }
            else
            {
                //�����ǲ��ü�����ʽ��ŵġ�
                if(!user.getPassword().equals(MD5.getHexMD5Str(pwd)))
                {
                    //���벻��
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
     * �û���¼����
     * @param userID String,�û�ID���ʺţ�
     * @param pwd String,����
     * @return int��0���ɹ���1002���û������ڣ�1003���������1��ϵͳԭ��ʧ��
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
                //�Ҳ����û�
                result = UserManagerConstant.USER_NOT_EXISTED;
            }
            else
            {
                //�����ǲ��ü�����ʽ��ŵġ�
                if(!user.getPassword().toUpperCase().equals(pwd.toUpperCase()))
                {
                    //���벻��
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
     * <br/>�޸��û�����
     * <br/>������
     * <br/>����û��Ƿ����
     * <br/>����û��ľ������Ƿ���ȷ
     * <br/>�޸��û�����
     * <br/>������������г������ݿ����ʧ�ܣ�����ϵͳԭ��ʧ��
     * @param userID String,�û��ʺ�
     * @param oldPwd String,������
     * @param newPwd String,������
     * @param userState int,�û�״̬
     * @return int��0���ɹ���1002���û������ڣ�1003���������1��ϵͳԭ��ʧ��
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
        //�ȵ�¼һ�£�����û����;��������ȷ�ԡ�
        int result = this.login(userID, oldPwd);
        //ֻ�е�¼�ɹ����Ž����޸����������
        if(result == UserManagerConstant.SUCC)
        {
            try
            {
                UserVO user = new UserVO();
                user.setUserID(userID);
                user.setPassword(MD5.getHexMD5Str(newPwd));
                //��������븴λ���û�����Ҫ��״̬����Ϊ����
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
     * �û����븴λ
     * @param userID String
     * @return String �û���������
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
            //û���û����޸ģ�˵���û��Ҳ�����
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
     * �û��ʺ�����
     * @param userID String
     * @return int��0���ɹ���1002���û������ڣ�1��ϵͳԭ��ʧ��
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
            //û���û����޸ģ�˵���û��Ҳ�����
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
     * �û��ʺŽ���
     * @param userID String
     * @return int��0���ɹ���1002���û������ڣ�1��ϵͳԭ��ʧ��
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
            //û���û����޸ģ�˵���û��Ҳ�����
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
     * �����û��ʺŲ�ѯ�û���Ϣ
     * @param userID String�û��ʺ�id
     * @return UserVO,�û���Ϣ���Ҳ���Ϊnull
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
            throw new BOException("�����û��ʺŲ�ѯ�û���Ϣʧ�ܣ�", e);
        }
    }

    /**
     * ��ȡ�����û����б�
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
            throw new BOException("��ȡ�����û����б�ʧ�ܣ�", e);
        }
    }

    /**
     * ͨ���ؼ��ֺ��û�״̬��ȡ�û��б�
     * ʵ�ַ�ҳ
     * @param page PageResult ��ҳ����
     * @param key String �ؼ��֣���userID��name����ģ������
     * @param states int[] �û�״̬�б������UserManagerConstant�еĶ��塣����������в������ֻ�ǲ��Ҳ�����
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
     * �û��������ܣ�ʵ�ַ�ҳ
     * @param page PageResult ��ҳ����
     * @param userVO UserVO����������
     * @param roleID String����Ӧ�Ľ�ɫ
     * @param rightType int����Ӧ��Ȩ������
     * @param rightID String����Ӧ��Ȩ��ID
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
            throw new BOException("�����û�ʧ�ܣ�",e);
        }
    }

    /**
     * ����һ���û�����Ϣ
     * @param userID String,�û��ʺ�
     * @return int��0���ɹ���1002���û������ڣ�1��ϵͳԭ��ʧ��
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
     * ��ȡ����ע��ɹ�����û�
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
            throw new BOException("��ȡ����ע��ɹ�����û�ʧ�ܣ�", e);
        }
    }

    /**
     * ��session��ȥ�û�session��Ϣ��vo
     * @param session HttpSession http��session
     * @return UserSessionVO ������session�����VO�࣬��װ�û���Ҫ������session�����ȫ����Ϣ
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
     * ���û���Ϣ���浽session��
     * @param session HttpSession http��session
     * @param userSession UserSessionVO ��װ�û���Ҫ������session�����ȫ����Ϣ��VO��
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
