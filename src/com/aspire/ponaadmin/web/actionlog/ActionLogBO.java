package com.aspire.ponaadmin.web.actionlog ;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.constant.ErrorCode;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>������־BO��</p>
 * <p>������־BO�࣬�ṩ������־�Ľӿڷ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ActionLogBO
{

    /**
     * ��־����  
     */
    protected static JLogger logger = LoggerFactory.getLogger(ActionLogBO.class) ;

    /**
     * ��־�в���������󳤶ȡ�
     */
    private static final int MAX_ACTIONTARGET_LEN = 300 ;

    /**
     * singletonģʽ��ʵ��
     */
    private static ActionLogBO instance = new ActionLogBO() ;

    /**
     * ���췽������singletonģʽ����
     */
	private ActionLogBO ()
    {
    }

	/**
	 * ��ȡʵ��
	 * @return ʵ��
	 */
    public static ActionLogBO getInstance()
    {
        return instance;
    }

    /**
     * ���ݹؼ��֡���ʼʱ�䡢����ʱ���ѯ������־��
     * @param page PageResult ��ҳ����
     * @param key String ��ѯ�Ĺؼ���
     * @param startDate String ��ѯ�Ŀ�ʼ���ڣ���ʽΪYYYYMMDDHHmmSS
     * @param endDate String ��ѯ�Ľ������ڣ���ʽΪYYYYMMDDHHmmSS
     * @throws BOException
     */
    public void queryLog(PageResult page, String key, String startDate,
			String endDate,String result) throws BOException
    {
        if (logger.isDebugEnabled ())
        {
            logger.debug ("queryLog(" + key + "," + startDate + "," + endDate + ")") ;
        }
        if(page == null)
        {
        	throw new BOException("invalid para , page is null");
        }
        if(key == null)
        {
            key = "";
        }
        //���startDate����Ļ���Ϊ��������ڡ�
        if(startDate == null || startDate.trim().equals(""))
        {
            startDate = "00000000000000";
        }
        //���endDate����Ļ���Ϊ��ǰ���ڡ�
        if(endDate == null || endDate.trim().equals(""))
        {
            endDate = PublicUtil.getCurDateTime("yyyyMMddHHmmss");
        }
        try
        {
            ActionLogDAO.getInstance().queryLog(page, key, startDate,
                                                            endDate,result) ;
        }
        catch (DAOException e)
        {
            logger.error(e);
        }
    }

    /**
     * ����logID��ѯ
     * @param logID String
     * @return ActionLogVO
     * @throws BOException
     */
    public ActionLogVO getLogByID(String logID) throws BOException
    {
        if (logger.isDebugEnabled ())
        {
            logger.debug ("getLogByID(" + logID + ")") ;
        }
        if (logID == null || !logID.matches("\\d{1,9}"))
        {
            throw new BOException("invalid para logID");
        }
        try
        {
            return ActionLogDAO.getInstance().getLogByID(logID);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("����logID��ѯʧ�ܣ�");
        }
    }

    /**
     * �����ṩ�ļ�¼������־������
     * <br/>1��������еĲ���������Ϊnull
     * <br/>2����¼������־
     * @param userID String ִ�в������û��ʺ�
     * @param userName String ִ�в������û�����
     * @param roles String ִ�в������û����еĽ�ɫ�������ж��
     * @param actionType String ��������
     * @param result boolean �������
     * @param actionTarget String ��������
     * @param IP String ����IP
     * @param desc String ��������
     * @throws BOException
     */
    public void log(String userID, String userName, String roles, String actionType
    		, boolean result, String actionTarget, String IP, String desc)
    throws BOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("log("+userID+","+userName+","+roles+","+actionType+","+result
            		+","+actionTarget+","+IP+","+desc+")");
        }
        //������
        if ((actionType == null) || (actionTarget == null) ||
            (IP == null ) || (userID == null))
        {
            throw new BOException("invalid para", ErrorCode.INVALID_PARA);
        }
        desc = (desc == null ? "" : desc) ;
        userName = (userName == null ? "" : userName) ;
        roles = (roles == null ? "" : roles) ;
        //�����������������ͽس���
        if(PublicUtil.getLength(actionTarget) > MAX_ACTIONTARGET_LEN)
        {
            actionTarget = PublicUtil.formatByLen(actionTarget, MAX_ACTIONTARGET_LEN, "��");
        }
        //����vo
        ActionLogVO log = new ActionLogVO();
        log.setUserID(userID);
        log.setUserName(userName);
        log.setIP(IP);
        log.setActionType(actionType);
        log.setActionResult(result);
        log.setActionDesc(desc);
        log.setActionTarget(actionTarget);
        log.setActionTime(PublicUtil.getCurDateTime("yyyyMMddHHmmss"));
        log.setRoles(roles);
        //��ӵ���̨�첽����ִ�ж�����
        WriteActionLogTask task = new WriteActionLogTask(log);
        DaemonTaskRunner.getInstance().addTask(task);
    }
}
