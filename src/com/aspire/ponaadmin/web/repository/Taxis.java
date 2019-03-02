package com.aspire.ponaadmin.web.repository ;

import java.util.List;
import java.util.ArrayList;

/**
 * <p>排序器</p>
 * <p>排序器。用于返回列表的操作场景中，对结果列表进行期望的排序。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.0.0
 */
public class Taxis
{

    /**
     * 排序参数列表。
     */
    protected List params = new ArrayList();

    public List getParams ()
    {
        return params ;
    }
}
