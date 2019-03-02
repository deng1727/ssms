package com.aspire.ponaadmin.common.rightmanager;

import java.util.List;

/**
 * <p>
 * ��ɫ����Ȩ��VO��
 * </p>
 * <p>
 * һ������Ȩ���ɶ��Ȩ����϶���
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */

public class CompositeRightVO extends RightVO {

    /**
     * ����Ȩ�ް���������Ȩ���б�
     */
    protected List rightList;

    /**
     * ��ȡ����Ȩ������Ȩ���б�
     * 
     * @return List ����Ȩ������Ȩ���б�
     */
    public List getRightList() {
        return rightList;
    }

    /**
     * ���ø���Ȩ������Ȩ���б�
     * 
     * @param rightList
     *            List ����Ȩ������Ȩ���б�
     */
    public void setRightList(List rightList) {
        this.rightList = rightList;
    }

    /**
     * ���Ȩ�ޡ�
     * 
     * @param id
     *            String Ŀ��Ȩ��id
     * @return int �����������RightManagerConstant���涨����
     */
    public int checkRight(String id) {
        if (this.rightList == null) {
            return RightManagerConstant.RIGHTCHECK_NO_RIGHT;
        }

        if (this.getRightID().equals(id)) {
            return RightManagerConstant.RIGHTCHECK_PASSED;
        }
        for (int i = 0; i < this.rightList.size(); i++)
        {
            RightVO right = ( RightVO ) this.rightList.get(i);
            if (right.checkRight(id) == RightManagerConstant.RIGHTCHECK_PASSED)
            {
                return RightManagerConstant.RIGHTCHECK_PASSED;
            }
        }

        return RightManagerConstant.RIGHTCHECK_NO_RIGHT;
    }
}
