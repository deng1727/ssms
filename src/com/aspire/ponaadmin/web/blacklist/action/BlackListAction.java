
package com.aspire.ponaadmin.web.blacklist.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.db.SQLUtil;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.blacklist.biz.BlackListBo;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * 列表
 * 
 * @author x_zhailiqing
 * 
 */
public class BlackListAction extends BaseAction
{

    private static final JLogger LOG = LoggerFactory.getLogger(BlackListAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");

        // 是否查询内容的类型。
        String name = this.getParameter(request, "name");
        String spName = this.getParameter(request, "spName");
        String contentType = this.getParameter(request, "contentType");
        String contentID = this.getParameter(request, "contentID");
        String pageSize = this.getParameter(request, "pageSize");
        String aprovalStatus = this.getParameter(request, "aprovalStatus");

        if ("".equals(pageSize.trim()))
        {
            pageSize = PageSizeConstants.page_DEFAULT;
        }

        // 设置搜索条件：
        Searchor searchor = new Searchor();

        if (!name.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("trg.name",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + SQLUtil.escape(name) + '%'));
        }
        if (!spName.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("trg.spName",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + SQLUtil.escape(spName) + '%'));
        }
        if (!contentType.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("contentType",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + SQLUtil.escape(contentType) + '%'));
        }

        if (!contentID.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("tcb.contentID",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + SQLUtil.escape(contentID) + '%'));
        }

        // 获取分类下的内容
        PageResult page = new PageResult(request);
        // page.setPageSize(6+6);
        page.setPageSize(Integer.parseInt(pageSize));

        // 排序方式:根据1.1.1.044需求（先按序号降序再按接入时间降序排序） guanzf 20071108
        Taxis taxis = new Taxis();
        taxis.getParams()
             .add(new TaxisParam("sortID", RepositoryConstants.ORDER_TYPE_DESC));
        taxis.getParams()
             .add(new TaxisParam("marketDate",
                                 RepositoryConstants.ORDER_TYPE_DESC));

        BlackListBo.getInstance().queryBlackList(page, searchor, taxis,aprovalStatus);
        request.setAttribute("contentType", contentType);
        request.setAttribute("PageResult", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("aprovalStatus", aprovalStatus);
        return mapping.findForward("blackList");
    }

}
