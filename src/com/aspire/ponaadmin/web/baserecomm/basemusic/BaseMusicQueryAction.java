/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm.basemusic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.baserecomm.BaseRecommBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * @author x_wangml
 * 
 */
public class BaseMusicQueryAction extends BaseAction
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BaseMusicQueryAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("doPerform()");
        }
        String forward = "list";

        // �������л�ȡ�������ֲ�ѯ����
        String songName = this.getParameter(request, "songName").trim();
        String singer = this.getParameter(request, "singer").trim();

        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // ���ݻ�ȡ�Ĳ�����ѯ�������ֱ���Ϣ
        BaseRecommBO.getInstance().queryBaseMusicList(page, songName, singer);

        // ��list���õ�page�����ڷ�ҳչʾ
        request.setAttribute("songName", songName);
        request.setAttribute("singer", singer);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }
}
