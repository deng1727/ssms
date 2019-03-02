package com.aspire.ponaadmin.web.actionlog ;

import com.aspire.ponaadmin.common.page.PageVOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>������־VO��</p>
 * <p>������־VO�࣬��װ�˲�����־����Ϣ</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ActionLogVO implements PageVOInterface
{

	/**
	 * ������־��ID
     */
    private String logID;

    /**
     * ����ʱ��
     */
    private String actionTime ;

    /**
     * �û�ID
     */
    private String userID ;

    /**
     * �û���
     */
    private String userName ;

    /**
     * �û���ɫ���û����в���ʱ���õĽ�ɫ
     */
    private String roles ;

    /**
     * ����IP
     */
    private String IP ;

    /**
     * ��������
     */
    private String actionType ;

    /**
     * ��������
     */
    private String actionTarget ;

    /**
     * ����������ɹ�����ʧ��
     */
    private boolean actionResult ;

    /**
     * ����
     */
    private String actionDesc ;

    /**
     * ���췽��
     */
    public ActionLogVO ()
    {
    }

    /**
     * ��ȡ�û���
     * @return �û���
     */
    public String getUserName ()
    {
        return userName ;
    }

    /**
     * ��ȡ�û��ʺ�ID
     * @return �û��ʺ�ID
     */
    public String getUserID ()
    {
        return userID ;
    }

    /**
     * ��ȡ������ɫ�������ж��
     * @return ������ɫ
     */
    public String getRoles ()
    {
        return roles ;
    }

    /**
     * ��ȡ�������
     * @return �������
     */
    public boolean getActionResult ()
    {
        return actionResult ;
    }

    /**
     * ��ȡ��������
     * @return ��������
     */
    public String getActionDesc ()
    {
        return actionDesc ;
    }

    /**
     * ��ȡ��������
     * @return ��������
     */
    public String getActionType ()
    {
        return actionType ;
    }

    /**
     * ��ȡ����ʱ��
     * @return ����ʱ��
     */
    public String getActionTime ()
    {
        return actionTime ;
    }

    /**
     * ��ȡ��������
     * @param actionTarget ��������
     */
    public void setActionTarget (String actionTarget)
    {
        this.actionTarget = actionTarget ;
    }

    /**
     * �����û���
     * @param userName �û���
     */
    public void setUserName (String userName)
    {
        this.userName = userName ;
    }

    /**
     * �����û��ʺ�ID
     * @param userID �û��ʺ�ID
     */
    public void setUserID (String userID)
    {
        this.userID = userID ;
    }

    /**
     * �����û���ɫ�������ж��
     * @param roles �û���ɫ
     */
    public void setRoles (String roles)
    {
        this.roles = roles ;
    }

    /**
     * ���ò������
     * @param actionResult �������
     */
    public void setActionResult (boolean actionResult)
    {
        this.actionResult = actionResult ;
    }

    /**
     * ���ò�������
     * @param actionDesc ��������
     */
    public void setActionDesc (String actionDesc)
    {
        this.actionDesc = actionDesc ;
    }

    /**
     * ���ò�������
     * @param actionType ��������
     */
    public void setActionType (String actionType)
    {
        this.actionType = actionType ;
    }

    /**
     * ���ò���ʱ��
     * @param actionTime ����ʱ��
     */
    public void setActionTime (String actionTime)
    {
        this.actionTime = actionTime ;
    }

    /**
     * ������־ID
     * @param logID ��־ID
     */
    public void setLogID (String logID)
    {
        this.logID = logID ;
	}

    /**
     * ���ò�������
     * @return ��������
     */
    public String getActionTarget ()
    {
        return actionTarget ;
    }

    /**
     * ��ȡ��־ID
     * @return ��־ID
     */
    public String getLogID ()
    {
        return logID ;
    }

    /**
     * ���÷���IP
     * @param IP ����IP
     */
    public void setIP (String IP)
    {
        this.IP = IP ;
    }

    /**
     * ��ȡ����IP
     * @return ����IP
     */
    public String getIP ()
    {
        return IP ;
    }

    /**
     * ��ȡ�������ڵ���ʾ
     * @return String �������ڵ���ʾ
     */
    public String getDisplayActionTime()
    {
        try
        {
            String display = this.actionTime.substring(0, 4) + '-' +
                this.actionTime.substring(4, 6) + '-'
                + this.actionTime.substring(6, 8) + ' ' +
                this.actionTime.substring(8, 10)
                + ':' + this.actionTime.substring(10, 10 + 2) ;
            return display;
        }
        catch(Exception e)
        {
            return "";
        }
    }

    /**
     * ��ȡ�����������ʾ
     * @return String �����������ʾ
     */
    public String getDisplayActionResult()
    {
        return this.actionResult ? "�ɹ�" : "ʧ��" ;
    }

    /**
     *
     * @param vo VOObject
     * @param rs ResultSet
     * @todo Implement this com.aspire.ponaadmin.PageVOInterface method
     */
    public void CopyValFromResultSet (Object vo, ResultSet rs) throws SQLException
    {
        ActionLogDAO.getInstance().getActionLogVOFromRS((ActionLogVO) vo, rs) ;
    }

    /**
     *
     * @return VOObject
     * @todo Implement this com.aspire.ponaadmin.PageVOInterface method
     */
    public Object createObject ()
    {
        return new ActionLogVO() ;
    }
}
