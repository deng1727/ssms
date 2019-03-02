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
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(ChannelBO.class);
    /**
	 * singletonģʽ��ʵ��
	 */
    private static ChannelBO instance = new ChannelBO();
    /**
     * ���췽������singletonģʽ����
     */
    private ChannelBO(){
    	
    }
    /**
	 * ��ȡʵ��
	 * @return ʵ��
	 */
    public static ChannelBO getInstance(){
    	 return instance;
    }
    /**
     * �û���¼����
     * @param channelNO String,�û�ID���ʺţ�
     * @param channelPwd String,����
     * @return int��0���ɹ���1002���û������ڣ�1003���������1��ϵͳԭ��ʧ��
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
                 //�Ҳ����û�
                 result = UserManagerConstant.USER_NOT_EXISTED;
             }
             else
             {
                 //�����ǲ��ü�����ʽ��ŵġ�
                 if(!channel.getChannelsPwd().equals(MD5.getHexMD5Str(channelPwd)))
                 {
                     //���벻��
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
     * �����û��ʺŲ�ѯ�û���Ϣ
     * @param userID String�������û��ʺ�id
     * @return UserVO,�û���Ϣ���Ҳ���Ϊnull
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
            throw new BOException("�����û��ʺŲ�ѯ�������û���Ϣʧ�ܣ�", e);
        }
    }
    /**
     * ���ݺ����̣������̣�Id��ѯ�û���Ϣ
     * @param channelsId String������id
     * @return UserVO,�û���Ϣ���Ҳ���Ϊnull
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
            throw new BOException("���ݺ����̣������̣�Id��ѯ�������û���Ϣʧ�ܣ�", e);
        }
    }
    
    /**
     * <br/>�޸��û�����
     * <br/>������
     * <br/>����û��Ƿ����
     * <br/>����û��ľ������Ƿ���ȷ
     * <br/>�޸��û�����
     * <br/>������������г������ݿ����ʧ�ܣ�����ϵͳԭ��ʧ��
     * @param channelNO String,�������û��ʺ�
     * @param oldPwd String,������
     * @param newPwd String,������
     * @return int��0���ɹ���1002���û������ڣ�1003���������1��ϵͳԭ��ʧ��
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
    		//�ȵ�¼һ�£�����û����;��������ȷ�ԡ�
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
