package com.aspire.ponaadmin.web.channelUser.bo;




import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.MD5;
import com.aspire.ponaadmin.common.usermanager.UserManagerConstant;
import com.aspire.ponaadmin.web.channelUser.dao.ChannelDAO;
import com.aspire.ponaadmin.web.channelUser.vo.ChannelVO;

public class ChannelBO {
	/**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(ChannelBO.class);
    /**
	 * singleton模式的实例
	 */
    private static ChannelBO instance = new ChannelBO();
    /**
     * 构造方法，由singleton模式调用
     */
    private ChannelBO(){
    	
    }
    /**
	 * 获取实例
	 * @return 实例
	 */
    public static ChannelBO getInstance(){
    	 return instance;
    }
    /**
     * 用户登录方法
     * @param channelNO String,用户ID（帐号）
     * @param channelPwd String,密码
     * @return int，0：成功，1002：用户不存在，1003：密码错误，1：系统原因失败
     * @throws BOException
     */
    public int channelLogin(String channelNO,String channelPwd) throws BOException{
    	 if(logger.isDebugEnabled())
         {
             logger.debug("channelLogin("+channelNO+","+channelPwd);
         }
    	 if((channelNO == null) || channelNO.trim().equals("") || (channelPwd == null))
         {
             throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
         }
         int result = UserManagerConstant.SUCC;
         try
         {
             ChannelVO channel = ChannelDAO.getChannelByNO(channelNO);
             if(channel == null)
             {
                 //找不到用户
                 result = UserManagerConstant.USER_NOT_EXISTED;
             }
             else
             {
                 //密码是采用加密形式存放的。
                 if(!channel.getChannelsPwd().equals(MD5.getHexMD5Str(channelPwd)))
                 {
                     //密码不对
                     result = UserManagerConstant.INVALID_PWD;
                 }
             }
         }
         catch(Exception e)
         {
             logger.error("error during channelLogin.", e);
             result = UserManagerConstant.FAIL;
         }
         return result;
    }
    /**
     * 根据用户帐号查询用户信息
     * @param userID String渠道商用户帐号id
     * @return UserVO,用户信息，找不到为null
     * @throws BOException
     */
    public ChannelVO getChannelByNO(String channelNO) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getChannelByNO("+channelNO+')');
        }
        if(channelNO == null || channelNO.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        try
        {
            return ChannelDAO.getChannelByNO(channelNO);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据用户帐号查询渠道商用户信息失败！", e);
        }
    }
    /**
     * 根据合作商（渠道商）Id查询用户信息
     * @param channelsId String渠道商id
     * @return UserVO,用户信息，找不到为null
     * @throws BOException
     */
    public ChannelVO getChannelByChannelsId(String channelsId) throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getChannelByChannelsId("+channelsId+')');
        }
        if(channelsId == null || channelsId.trim().equals(""))
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
        try
        {
            return ChannelDAO.getInstance().getChannelByChannelsId(channelsId);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("根据合作商（渠道商）Id查询渠道商用户信息失败！", e);
        }
    }
    
    /**
     * <br/>修改用户密码
     * <br/>检查参数
     * <br/>检查用户是否存在
     * <br/>检查用户的旧密码是否正确
     * <br/>修改用户密码
     * <br/>上述步骤如果有出现数据库访问失败，返回系统原因失败
     * @param channelNO String,渠道商用户帐号
     * @param oldPwd String,旧密码
     * @param newPwd String,新密码
     * @return int，0：成功，1002：用户不存在，1003：密码错误，1：系统原因失败
     * @throws BOException
     */
    public int updateChannelPwd(String channelNO,String oldPwd,String newPwd) throws BOException{
    	if(logger.isDebugEnabled()){
    		logger.debug("updateChannelPwd("+channelNO+","+oldPwd+","+newPwd);
    	}
    	if((channelNO == null) || channelNO.trim().equals("") || (oldPwd == null) || (newPwd == null))
        {
            throw new BOException("Invalid parameter.", UserManagerConstant.INVALID_PARA);
        }
    	int result = 1;
    	try {
    		//先登录一下，检查用户名和旧密码的正确性。
            result = this.channelLogin(channelNO, oldPwd);
            if(result == UserManagerConstant.SUCC){
            	ChannelVO channelVO = new ChannelVO();
            	channelVO.setChannelsNO(channelNO);
            	channelVO.setChannelsPwd(MD5.getHexMD5Str(newPwd));
            	ChannelDAO.updateChannel(channelVO);
            }
		} catch (Exception e) {
			 logger.error(e);
             result = UserManagerConstant.FAIL;
		}
		if(logger.isDebugEnabled())
        {
            logger.debug("result is["+result+']');
        }
        return result;
    }
    public static void main(String[] args) throws Exception {
		System.out.println(MD5.getHexMD5Str("111111"));
	}
}
