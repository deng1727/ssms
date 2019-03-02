package com.aspire.ponaadmin.common.rightmanager ;

/**
 * <p>��ɫȨ��VO��</p>
 * <p>��ɫȨ��VO�࣬��װ�˽�ɫȨ��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class RightVO
{

    /**
     * Ȩ��ID
     */
    protected String rightID ;

    /**
     * Ȩ������
     */
    protected String name ;

    /**
     * Ȩ������
     */
    protected String desc ;

    /**
     * Ȩ������
     */
    protected int type;

    /**
     * ��Ȩ�޵�ID�����û�и�Ȩ�ޣ���Ϊnull
     */
    protected String parentID;

    public String getDesc ()
    {
        return desc ;
    }

    public String getName ()
    {
        return name ;
    }

    public void setRightID (String rightID)
    {
        this.rightID = rightID ;
    }

    public void setDesc (String desc)
    {
        this.desc = desc ;
    }

    public void setName (String name)
    {
        this.name = name ;
    }

    public String getRightID ()
    {
        return rightID ;
    }

    public int getType()
    {
        return this.type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public void setParentID(String _parentID)
    {
        this.parentID = _parentID;
    }

    public String getParentID()
    {
        return this.parentID;
    }

    /**
     * ���췽��
     */
    public RightVO ()
    {
    }

    /**
     * ����equals���������rightIDһ��������ȡ�
     * @param obj Object
     * @return boolean
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof RightVO)
        {
            if(((RightVO) obj).getRightID().equals(this.rightID))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * ���Ȩ�ޡ������������͵�Ȩ�ޣ����������һ���������븲�Ǳ�������
     * @param id String Ŀ��Ȩ��id
     * @return int �����������RightManagerConstant���涨����
     */
    public int checkRight(String id)
    {
        if(this.rightID .equals(id))
        {
            return RightManagerConstant.RIGHTCHECK_PASSED;
        }
        return RightManagerConstant.RIGHTCHECK_NO_RIGHT;
    }
}
