package com.aspire.ponaadmin.web.blacklist.action;

import java.util.StringTokenizer;

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
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.repository.web.CgyContentListBO;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * 列表
 * @author x_zhailiqing
 *
 */
public class ContentListAction extends BaseAction {
	private static final JLogger LOG = LoggerFactory
			.getLogger(ContentListAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOG.debug("doPerform()");

		// 是否查询内容的类型。
		String name = this.getParameter(request, "name");
		String spName = this.getParameter(request, "spName");
        String contentType = this.getParameter(request, "contentType");
		String contentID = this.getParameter(request, "contentID");
		String pageSize = this.getParameter(request, "pageSize");

		if ("".equals(pageSize.trim())) {
			pageSize = PageSizeConstants.page_DEFAULT;
		}

		// 设置搜索条件：
		Searchor searchor = new Searchor();

		if (!name.equals("")) {
			searchor.getParams().add(
					new SearchParam("name", RepositoryConstants.OP_LIKE,
							'%' + SQLUtil.escape(name) + '%'));
		}
		if (!spName.equals("")) {
			searchor.getParams().add(
					new SearchParam("spName", RepositoryConstants.OP_LIKE,
							'%' + SQLUtil.escape(spName) + '%'));
		}
        if (!contentType.equals(""))
        {
            if(Constants.BLACK_LIST_CY.equals(contentType))
            {
                searchor.getParams()
                        .add(new SearchParam("subType",
                                             RepositoryConstants.OP_EQUAL,
                                             Constants.BLACK_LIST_CONTENT_SUBTYPE_CY));
            }
            else if(Constants.BLACK_LIST_MM.equals(contentType))
            {
                searchor.getParams()
                .add(new SearchParam("subType",
                                     RepositoryConstants.OP_NOT_EQUAL,
                                     Constants.BLACK_LIST_CONTENT_SUBTYPE_CY));
            }
        }
		if (!contentID.equals("")) {
			searchor.getParams().add(
					new SearchParam("contentID", RepositoryConstants.OP_LIKE,
							'%' + SQLUtil.escape(contentID)+'%' ));
		}

		// 获取分类下的内容
		PageResult page = new PageResult(request);
		// page.setPageSize(6+6);
		page.setPageSize(Integer.parseInt(pageSize));

		// 排序方式:根据1.1.1.044需求（先按序号降序再按接入时间降序排序）　guanzf 20071108
		Taxis taxis = new Taxis();
		taxis.getParams().add(
				new TaxisParam("sortID", RepositoryConstants.ORDER_TYPE_DESC));
		taxis.getParams().add(
				new TaxisParam("marketDate",
						RepositoryConstants.ORDER_TYPE_DESC));

		BlackListBo.getInstance().queryContentNoInBlackList(page, searchor, taxis);
        request.setAttribute("contentType", contentType);
		request.setAttribute("PageResult", page);
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("ctList");
	}

}
