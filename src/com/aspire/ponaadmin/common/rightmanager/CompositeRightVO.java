package com.aspire.ponaadmin.common.rightmanager;

import java.util.List;

/**
 * <p>
 * 角色复合权限VO类
 * </p>
 * <p>
 * 一个复合权限由多个权限组合而成
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
     * 复合权限包含的所有权限列表
     */
    protected List rightList;

    /**
     * 获取复合权限下属权限列表
     * 
     * @return List 复合权限下属权限列表
     */
    public List getRightList() {
        return rightList;
    }

    /**
     * 设置复合权限下属权限列表
     * 
     * @param rightList
     *            List 复合权限下属权限列表
     */
    public void setRightList(List rightList) {
        this.rightList = rightList;
    }

    /**
     * 检查权限。
     * 
     * @param id
     *            String 目标权限id
     * @return int 检查结果，都在RightManagerConstant里面定义着
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
