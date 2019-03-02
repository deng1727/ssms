package com.aspire.ponaadmin.web.repository ;

import java.util.List;
import java.util.ArrayList;

/**
 * <p>搜索器类。</p>
 * <p>搜索器类。搜索实际上是根据节点的属性进行搜索的。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

public class Searchor
{

    /**
     * 搜索参数列表。
     */
    protected List params = new ArrayList();

    /**
     * 搜索模式
     */
    protected boolean isRecursive;

    /**
     * 当搜索引用节点时，是in还是not in
     */
    protected boolean isNotIn;

    /**
     * 设置一个特殊符号，是为了解决cmnet内容要过滤的变态需求。
     */
    protected boolean fixCmnet;

    /**
     * 构造方法
     */
    public Searchor ()
    {
    }

    public void setIsRecursive(boolean isRecursive)
    {
        this.isRecursive = isRecursive;
    }

    public boolean getIsRecursive()
    {
        return this.isRecursive;
    }

    public void setIsNotIn(boolean isNotIn)
    {
        this.isNotIn = isNotIn;
    }

    public boolean getIsNotIn()
    {
        return this.isNotIn;
    }

    public List getParams()
    {
        return this.params;
    }

    public void setFixCmnet (boolean fixCmnet)
    {
        this.fixCmnet = fixCmnet ;
    }

    public boolean isFixCmnet ()
    {
        return fixCmnet ;
    }

}
