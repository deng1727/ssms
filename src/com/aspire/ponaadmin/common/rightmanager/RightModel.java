package com.aspire.ponaadmin.common.rightmanager ;

import java.util.List ;

import com.aspire.common.db.DAOException;

/**
 * <p>����Ȩ��ģ����</p>
 * <p>�̳���Ȩ��ģ�ͣ�ʵ�ֲ���Ȩ�޵���ز���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RightModel extends AbstractRightModel
{

    /**
     * singletonģʽ��ʵ��
     */
    private static RightModel instance = new RightModel();

    /**
     * ���췽������singletonģʽ����
     */
    private RightModel ()
    {
        //Ȩ�޵����Ͷ��壺��������Ȩ��
        this.type = 1;
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    public static RightModel getInstance()
    {
        return instance;
    }

    /**
     *
     * @param roleID String ��ɫ��ID
     * @return List Ȩ���б�
     * @todo Implement this
     *   com.aspire.ponaadmin.common.rightmanager.RightModel method
     */
    public List getRoleRight (String roleID)
    {
        List list = null;
        try
        {
            list = RoleRightDAO.getInstance().getRoleRights(roleID);
        }
        catch (DAOException e)
        {
            
        }
        return list;
    }

    /**
     *
     * @param roleID String ��ɫ��Ȩ��
     * @param rightIDList List Ȩ��ID���б�
     * @todo Implement this
     *   com.aspire.ponaadmin.common.rightmanager.RightModel method
     */
    public void setRoleRight (String roleID, List rightIDList)
    {
        try
        {
            RoleRightDAO.getInstance().setRoleRights(roleID, rightIDList);
        }
        catch (DAOException e)
        {

        }
    }
}
