package com.aspire.ponaadmin.common.rightmanager ;

import java.util.List;

/**
 * <p>Ȩ�����͵ĳ���ģ��</p>
 * <p>���־������͵�Ȩ�����ͣ��������Ȩ�ޣ�����̳б���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public abstract class AbstractRightModel
{

    /**
     * Ȩ�޵����Ͷ���
     */
    protected int type;

    /**
     * ��ȡȨ������
     * @return int
     */
    public int getType()
    {
        return this.type;
    }

    /**
     * �ж�ĳ�����͵�Ȩ���Ƿ��Ǳ�����
     * @param _type int Ҫ�жϵ�����
     * @return boolean true:�ǣ�flase:����
     */
    public boolean isThisType(int _type)
    {
        return this.type == _type;
    }

    /**
     * ��ȡһ����ɫ��ĳ�����͵�Ȩ��
     * @param roleID String ��ɫ��ID
     * @return List Ȩ���б�
     */
    public abstract List getRoleRight(String roleID);

    /**
     * ����һ����ɫ��ĳ�����͵�Ȩ��
     * @param roleID String ��ɫ��Ȩ��
     * @param rightIDList List Ȩ��ID���б�
     */
    public abstract void setRoleRight(String roleID, List rightIDList);

}
