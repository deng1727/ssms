package com.aspire.ponaadmin.web.repository ;

import java.util.List;
import java.util.ArrayList;

/**
 * <p>�������ࡣ</p>
 * <p>�������ࡣ����ʵ�����Ǹ��ݽڵ�����Խ��������ġ�</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

public class Searchor
{

    /**
     * ���������б�
     */
    protected List params = new ArrayList();

    /**
     * ����ģʽ
     */
    protected boolean isRecursive;

    /**
     * ���������ýڵ�ʱ����in����not in
     */
    protected boolean isNotIn;

    /**
     * ����һ��������ţ���Ϊ�˽��cmnet����Ҫ���˵ı�̬����
     */
    protected boolean fixCmnet;

    /**
     * ���췽��
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
