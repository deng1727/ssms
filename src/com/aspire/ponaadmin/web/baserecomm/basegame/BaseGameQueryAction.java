/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm.basegame;


import java.util.ArrayList;

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
public class BaseGameQueryAction extends BaseAction
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BaseGameQueryAction.class);

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

        // �������л�ȡ������Ϸ��ѯ����
        String gameName = this.getParameter(request, "gameName").trim();
        String gameDesc = this.getParameter(request, "gameDesc").trim();
        String isFirst = this.getParameter(request, "isFirst").trim();

        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // ���ݻ�ȡ�Ĳ�����ѯ������Ϸ����Ϣ
        if (isFirst.equals("1"))// ��һ�β���Ҫ��ѯ
        {
            page.excute(new ArrayList(0));
            request.setAttribute("notice", "������������ѯ");
        }
        else
        {
            BaseRecommBO.getInstance()
                      .queryBaseGameList(page, gameName, gameDesc);
        }

        // ��list���õ�page�����ڷ�ҳչʾ
        request.setAttribute("gameName", gameName);
        request.setAttribute("gameDesc", gameDesc);
        request.setAttribute("PageResult", page);

        return mapping.findForward(forward);
    }
}
