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
 * <p>页面显示控制tag</p>
 * <p>
 * 本来只要CheckDispalyTag就足够了。
 * 由于需要嵌套判断，所以增加了这个tag。
 * 和CheckDispalyTag做的事情是一样的。
 * </p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class CheckMenuDisplayTag extends TagSupport
{

    private static final long serialVersionUID = 1L;

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CheckMenuDisplayTag.class);

    /**
     * 要判断的元素对应的权限id
     */
    private String rightID;

    /**
     * 构造方法
     */
    public CheckMenuDisplayTag()
    {
    }

    /**
     * 设置判断的元素对应的权限id
     * @param rightID String 判断的元素对应的权限id
     */
    public void setRightID (String rightID)
    {
        this.rightID = rightID ;
    }

    /**
     * 获取判断的元素对应的权限id
     * @return String 判断的元素对应的权限id
     */
    public String getRightID ()
    {
        return rightID ;
    }

    /**
     * tag判断方法，如果有权限就继续，如果没有权限就忽略内容。
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
          //终端公司显示TAC码库管理和推送管理，1表示终端公司
            if(user != null && user.getChannel() != null && "0".equals(user.getChannel().getMoType())){
            	return Tag.EVAL_BODY_INCLUDE ;
            }
        }
        catch (BOException e)
        {
            LOG.error(e) ;
        }
        //走到这里说明没有权限
        return Tag.SKIP_BODY ;
    }
}
