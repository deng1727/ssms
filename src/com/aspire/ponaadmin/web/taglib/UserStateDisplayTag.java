package com.aspire.ponaadmin.web.taglib ;

import java.io.IOException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.ponaadmin.common.usermanager.UserManagerConstant;

/**
 * <p>用来显示用户状态对应中文信息的tag类</p>
 * <p>用来显示用户状态对应中文信息的tag类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class UserStateDisplayTag extends TagSupport
{

    /**
     */
    private static final JLogger LOG = LoggerFactory.getLogger(UserStateDisplayTag.class);

    /**
     */
    private int state;//the key in resource

    /**
     */
    public UserStateDisplayTag()
    {
    }

    /**
     * @return int
     * @throws javax.servlet.jsp.JspTagException 
     */
    public int doEndTag()
        throws JspTagException
    {

        JspWriter jspwriter = pageContext.getOut();

        String msg = "未知状态";
        if(this.state == UserManagerConstant.STATE_NORMAL)
        {
            msg = "正常";
        }
        else if(this.state == UserManagerConstant.STATE_PRE_REGISTER)
        {
            msg = "预注册";
        }
        else if(this.state == UserManagerConstant.STATE_TO_BE_CHECK)
        {
            msg = "待审核";
        }
        else if(this.state == UserManagerConstant.STATE_CHECK_FAIL)
        {
            msg = "注册审核不通过";
        }
        else if(this.state == UserManagerConstant.STATE_LOCKED)
        {
            msg = "锁定";
        }
        else if(this.state == UserManagerConstant.STATE_PWD_RESET)
        {
            msg = "密码复位";
        }
        try
        {
            jspwriter.write(msg);
        }
        catch(IOException ex)
        {
            LOG.error("Error in UserStateDisplayTag", ex);
        }
        return EVAL_PAGE;//OK
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }
}
