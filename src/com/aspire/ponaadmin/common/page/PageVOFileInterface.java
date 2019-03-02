package com.aspire.ponaadmin.common.page;

import java.io.File;
/**
 * <p>File分页对象VO的构造和赋值接口</p>
 * <p>Copyright: Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author dongxk
 * @version 1.0.0.0
 */

public interface PageVOFileInterface
{

    /**
     * 创建VO对象
     *
     * @return VOObject
     */
    public Object createObject () ;

    /**
     * 从File中取得属性值赋给VO对象
     * @param vo VOObject
     * @param f File
     */
    public void CopyValFromFile(Object vo, File f);
}
