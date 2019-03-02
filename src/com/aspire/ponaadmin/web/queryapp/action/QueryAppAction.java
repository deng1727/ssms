package com.aspire.ponaadmin.web.queryapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.queryapp.bo.QueryAppBO;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

public class QueryAppAction extends BaseAction {
	/**
	 * 记录日志的实例对象
	 */
	protected static JLogger log = LoggerFactory
			.getLogger(QueryAppAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {

		if (log.isDebugEnabled()) {
			log.debug("QueryAppAction in.....");
		}

		String opType = this.getParameter(request, "opType");
		if ("doQueryAddList".equals(opType)) {
			return this.doQueryAddList(mapping, form, request, response);
		} else if ("doQueryContentList".equals(opType)) {
			return this.doQueryContentList(mapping, form, request, response);
		} else if ("doQueryAppDetail".equals(opType)) {
			return this.doQueryAppDetail(mapping, form, request, response);
		}
		else if("doExport".equals(opType))//处理导出
        {
            return this.doExport(mapping, form, request, response);
        }

	        return null;
   }
    
    //导出功能
    private ActionForward doExport(ActionMapping mapping,
                                          ActionForm form,
                                          HttpServletRequest request,
                                          HttpServletResponse response)
    {

        if (log.isDebugEnabled())
        {
            log.debug("doExport in......");
            log.debug("excel导出.......");
        }
        
        OutputStream os = null;
        WritableWorkbook wwb = null;
        String excelName = System.currentTimeMillis() + "add_content_app.xls";
        try
        {
            os = new FileOutputStream(excelName);
            wwb = Workbook.createWorkbook(os);
            QueryAppBO.getInstance().exportQueryApp(request, wwb);
        }
        catch (Exception e)
        {
        }
        finally
        {
            try
            {
                if (wwb != null)
                {
                    wwb.write();
                    wwb.close();
                }
                if (os != null)
                {
                    os.close();
                }
            }
            catch (Exception e)
            {
            }
        }
        response.setHeader("Content-disposition", "attachment;filename=" + excelName);
        response.setContentType("application/msexcel");
        try
        {
            FileInputStream fileInputStream = new FileInputStream(excelName);
            OutputStream out = response.getOutputStream();
            int i = 0;
            while ((i = fileInputStream.read()) != -1)
            {
                out.write(i);
            }
            fileInputStream.close();
            
            File file = new File(excelName);
            file.delete();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }


	private ActionForward doQueryAppDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 找出内容信息
		String forward = Constants.FORWARD_COMMON_FAILURE;
		String id = this.getParameter(request, "id").trim();
		String contentId = this.getParameter(request, "contentId").trim();
		String catename = this.getParameter(request, "catename").trim();
		Object content;
		try {
			content = Repository.getInstance().getNode(id,
					getTypeByCatename(catename));
			request.setAttribute("content", content);
			request.setAttribute("contentId", contentId);
			forward = "toQueryAppDetail";
		} catch (BOException e) {

			this.saveMessages(request, "每日新增应用查询列表失败");
			request.setAttribute(Constants.PARA_GOURL,
					"../queryapp/QueryAppAction.do?opType=doQueryList");
		}

		return mapping.findForward(forward);
	}

	private String getTypeByCatename(String catename) {
		if ("游戏".equals(catename)) {
			return "nt:gcontent:appGame";
		} else if ("软件".equals(catename)) {
			return "nt:gcontent:appSoftWare";
		} else if ("主题".equals(catename)) {
			return "nt:gcontent:appTheme";
		}
		return "";
	}

	private ActionForward doQueryAddList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		if (log.isDebugEnabled()) {
			log.debug("doQueryAddList in.............");
		}
		String forward = Constants.FORWARD_COMMON_FAILURE;
		String id = this.getParameter(request, "id").trim();
		String contentid = this.getParameter(request, "contentid").trim();
		String name = this.getParameter(request, "name").trim();
		String beginDate = this.getParameter(request, "beginDate").trim();
		String endDate = this.getParameter(request, "endDate").trim();
		String icpcode = this.getParameter(request, "icpcode").trim();
		String developer = this.getParameter(request, "developer").trim();
		String spname = this.getParameter(request, "spname").trim();
		//String syntype = this.getParameter(request, "syntype").trim();
		String syntype = "1";//默认只能查新上线的
		String catename = this.getParameter(request, "catename").trim();
		
		String servattr = this.getParameter(request, "servattr").trim();
		String keywords = this.getParameter(request, "keywords").trim();
		String pageSize = this.getParameter(request, "pageSize").trim();
		if(pageSize == null || pageSize.equals("")){
			pageSize = PageSizeConstants.page_DEFAULT.toString();
		}
        
		// 实现分页，列表对象是保存在分页对象PageResult中的。
		PageResult page = new PageResult(request, Integer
				.parseInt(pageSize));
		try {
			QueryAppBO.getInstance().queryQueryAddList(page, contentid, name,
					beginDate, endDate, icpcode, developer, spname, syntype, catename,id,servattr,keywords);
			request.setAttribute("PageResult", page);
			request.setAttribute("contentid", contentid);
			request.setAttribute("name", name);
			request.setAttribute("beginDate", beginDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("icpcode", icpcode);
			request.setAttribute("developer", developer);
			request.setAttribute("spname", spname);
			request.setAttribute("syntype", syntype);
			request.setAttribute("catename", catename);
			request.setAttribute("id", id);
			request.setAttribute("servattr", servattr);
			request.setAttribute("keywords", keywords);
			request.setAttribute("pageSize", pageSize);

			forward = "app_add_query";
		} catch (Exception e) {
			this.saveMessages(request, "每日新增应用查询列表失败");
		}
		return mapping.findForward(forward);
	}

	private ActionForward doQueryContentList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		if (log.isDebugEnabled()) {
			log.debug("doQueryContentList in.............");
		}
		String forward = Constants.FORWARD_COMMON_FAILURE;

		String contentid = this.getParameter(request, "contentid").trim();
		String name = this.getParameter(request, "name").trim();
		String beginDate = this.getParameter(request, "beginDate").trim();
		String endDate = this.getParameter(request, "endDate").trim();
		String icpcode = this.getParameter(request, "icpcode").trim();
		String developer = this.getParameter(request, "developer").trim();
		String spname = this.getParameter(request, "spname").trim();
		String catename = this.getParameter(request, "catename").trim();
		String id = this.getParameter(request, "id").trim();
		
		String servattr = this.getParameter(request, "servattr").trim();
		String keywords = this.getParameter(request, "keywords").trim();
		String pageSize = this.getParameter(request, "pageSize").trim();
		if(pageSize == null || pageSize.equals("")){
			pageSize = PageSizeConstants.page_DEFAULT.toString();
		}
        
		// 实现分页，列表对象是保存在分页对象PageResult中的。
		PageResult page = new PageResult(request, Integer
				.parseInt(pageSize));
		try {
			QueryAppBO.getInstance().queryQueryContentList(page, contentid, name,
					beginDate, endDate, icpcode, developer, spname, catename,id,servattr,keywords);
			request.setAttribute("PageResult", page);
			request.setAttribute("contentid", contentid);
			request.setAttribute("name", name);
			request.setAttribute("beginDate", beginDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("icpcode", icpcode);
			request.setAttribute("developer", developer);
			request.setAttribute("spname", spname);
			request.setAttribute("catename", catename);
			request.setAttribute("id", id);
			request.setAttribute("servattr", servattr);
			request.setAttribute("keywords", keywords);
			request.setAttribute("pageSize", pageSize);

			forward = "app_content_query";
		} catch (Exception e) {
			this.saveMessages(request, "商品全量应用查询列表失败");
		}
		return mapping.findForward(forward);
	}

}
