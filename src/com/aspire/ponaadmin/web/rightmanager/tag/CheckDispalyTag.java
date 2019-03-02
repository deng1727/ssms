package com.aspire.ponaadmin.web.rightmanager.tag ;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.rightmanager.RightManagerBO;
import com.aspire.ponaadmin.common.rightmanager.RightVO;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;

/**
 * <p>ҳ����ʾ����tag</p>
 * <p>�����û���Ȩ�ޣ��жϸ��ֵĲ˵����ť�������Ƿ���Ҫ��ʾ</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class CheckDispalyTag extends TagSupport
{
    private static final long serialVersionUID = 1L;

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CheckDispalyTag.class);

    /**
     * Ҫ�жϵ�Ԫ�ض�Ӧ��Ȩ��id
     */
    private String rightID;

    /**
     * ���췽��
     */
    public CheckDispalyTag()
    {
    }

    /**
     * �����жϵ�Ԫ�ض�Ӧ��Ȩ��id
     * @param rightID String �жϵ�Ԫ�ض�Ӧ��Ȩ��id
     */
    public void setRightID (String rightID)
    {
        this.rightID = rightID ;
    }

    /**
     * ��ȡ�жϵ�Ԫ�ض�Ӧ��Ȩ��id
     * @return String �жϵ�Ԫ�ض�Ӧ��Ȩ��id
     */
    public String getRightID ()
    {
        return rightID ;
    }

    /**
     * tag�жϷ����������Ȩ�޾ͼ��������û��Ȩ�޾ͺ������ݡ�
     * @return int
     * @throws JspException
     */
    public int doStartTag ()
        throws JspException
    {
        HttpSession session = this.pageContext.getSession() ;
        UserSessionVO user = UserManagerBO.getInstance().getUserSessionVO(
            session) ;
        try
        {
            RightVO right = RightManagerBO.getInstance().getRightByID(rightID) ;
            if (RightTagHelper.isInclude(right, user))
            {
                return Tag.EVAL_BODY_INCLUDE ;
            }
        }
        catch (BOException e)
        {
            LOG.error(e) ;
        }
        //�ߵ�����˵��û��Ȩ��
        return Tag.SKIP_BODY ;
    }
}
