package com.aspire.ponaadmin.web.baserecomm.basegame;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

/**
 * 黑名单新增，删除，导入操作
 *
 */
public class BlackAction extends BaseAction
{

    private static final JLogger LOG = LoggerFactory.getLogger(BlackAction.class);

    public ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入doPerform()方法");
        }

        // 从请求中获取操作类型
        String method = this.getParameter(request, "method");

        if ("query".equals(method))
        {
            return query(mapping, form, request, response);
        }
        else if ("remove".equals(method))
        {
            return remove(mapping, form, request, response);
        }
        else if ("add".equals(method))
        {
            return add(mapping, form, request, response);
        }
        else if ("importData".equals(method))
        {
            return importData(mapping, form, request, response);
        }
        else if ("queryItem".equals(method))
        {
            return queryItem(mapping, form, request, response);
        }
        else if ("delReference".equals(method))
        {
            return delReference(mapping, form, request, response);
        }
        else
        {
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
    }
    
    public ActionForward delReference(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入 delReference() 方法");
        }
        String actionType = "清理游戏黑名单信息";
        String actionDesc = "清理游戏黑名单信息成功!";
        String actionTarget = "BlackAction.delReference()";
        boolean actionResult = true;

        try
        {
            BlackBO.getInstance().delReference();
        }
        catch (Exception e)
        {
            actionResult = false;
            actionDesc = "清理游戏黑名单信息出错";
            LOG.error(actionDesc, e);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }

        request.setAttribute(Constants.PARA_GOURL, "black.do?method=query");

        // 写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        this.saveMessagesValue(request, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("离开 delReference() 方法");
        }
        return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
    }

    
    public ActionForward queryItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = "queryItem";

		// 从请求中获取货架内码
		String isFirst = this.getParameter(request, "isFirst").trim();
		String contentName = this.getParameter(request, "contentName").trim();
		String contentId = this.getParameter(request, "contentId").trim();
		BlackVO vo = new BlackVO();
		PageResult page = new PageResult(request);

		// 如果是第一次。跳过
		if ("1".equals(isFirst)) {
			request.setAttribute("isFirst", isFirst);
			request.setAttribute("method", forward);
			request.setAttribute("PageResult", page);
			request.setAttribute("contentName", contentName);
			request.setAttribute("contentId", contentId);

			return mapping.findForward(forward);
		}

		vo.setIcpservid(contentId);
		vo.setServname(contentName);

		try {
			BlackBO.getInstance().queryList(page, vo);
		} catch (BOException e) {
			LOG.error(e);
			this.saveMessagesValue(request, "查询内容列表出错");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}

		request.setAttribute("method", forward);
		request.setAttribute("PageResult", page);
		request.setAttribute("contentName", contentName);
		request.setAttribute("contentId", contentId);

		return mapping.findForward(forward);
	}

    public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入 query() 方法");
        }
        String actionType = "查询游戏黑名单信息";
        String actionDesc = "查询游戏黑名单信息成功!";
        String actionTarget = "BlackAction.query()";
        boolean actionResult = true;

        String contentId = request.getParameter("contentId") == null ? "" : request.getParameter("contentId");
        String contentName = request.getParameter("contentName") == null ? "" : request.getParameter("contentName");

        PageResult page = new PageResult(request);
        BlackVO vo = new BlackVO();
        vo.setIcpservid(contentId);
		vo.setServname(contentName);

        try
        {
            BlackBO.getInstance().queryBlackList(page, vo);
        }
        catch (BOException e)
        {
            actionResult = false;
            actionDesc = "查询游戏黑名单信息出错";
            LOG.error(actionDesc, e);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }

        request.setAttribute("PageResult", page);
        request.setAttribute("contentId", contentId);
        request.setAttribute("contentName", contentName);

        // 写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("离开 query() 方法");
        }
        return mapping.findForward(Constants.FORWARD_QUERY_TOKEN);
    }

    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入 remove() 方法");
        }
        String actionType = "删除游戏黑名单信息";
        String actionDesc = "删除游戏黑名单信息成功!";
        String actionTarget = "BlackAction.remove()";
        String[] id = request.getParameterValues("dealRef");
        boolean actionResult = true;

        if (id == null || (id != null && id.length <= 0))
        {
            actionResult = false;
            actionDesc = "删除黑名单，id参数为空!";
            LOG.warn(actionDesc);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {
            BlackBO.getInstance().removeBlack(id);
        }
        catch (BOException e)
        {
            actionResult = false;
            actionDesc = "删除游戏黑名单信息出错";
            LOG.error(actionDesc, e);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }

        request.setAttribute(Constants.PARA_GOURL, "black.do?method=query");

        // 写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        this.saveMessagesValue(request, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("离开 remove() 方法");
        }
        return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
    }

    @SuppressWarnings("unchecked")
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入 add() 方法");
        }
        String actionType = "新增游戏黑名单";
        String actionDesc = "新增游戏黑名单成功!";
        String actionTarget = "BlackAction.add()";
        String contentId = this.getParameter(request, "contentId");
        boolean actionResult = true;

        if (StringUtils.isBlank(contentId))
        {
            actionResult = false;
            actionDesc = "添加失败，参数错误，游戏业务ID为空!";
            LOG.warn(actionDesc);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);

            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        String[] contentIdArray = contentId.split(";");
        if (contentIdArray == null || (contentIdArray != null && contentIdArray.length <= 0))
        {
            actionResult = false;
            actionDesc = "添加失败，参数错误，游戏业务ID为空!";
            LOG.warn(actionDesc);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        try
        {

            Map<String, Object> resultMap1 = BlackBO.getInstance().isExistContent(contentIdArray);
            List<String> list1 = (List<String>)resultMap1.get("list");
            String msg11 = (String)resultMap1.get("msg1");
            String msg12 = (String)resultMap1.get("msg2");
            Map<String, Object> resultMap2 = BlackBO.getInstance().isExistBlack(list1);
            List<String> list2 = (List<String>)resultMap2.get("list");
            String msg21 = (String)resultMap2.get("msg1");
            String msg22 = (String)resultMap2.get("msg2");

            String arr[] = new String[list2.size()];
            list2.toArray(arr);

            int result = BlackBO.getInstance().addBlack(arr);

            actionDesc = "新增游戏黑名单" + result + "条记录!";

            if (StringUtils.isNotBlank(msg11))
            {
                actionDesc = actionDesc + "<br/>" + msg11;
            }
            if (StringUtils.isNotBlank(msg12))
            {
                actionDesc = actionDesc + "<br/>" + msg12;
            }
            if (StringUtils.isNotBlank(msg21))
            {
                actionDesc = actionDesc + "<br/>" + msg21;
            }
            if (StringUtils.isNotBlank(msg22))
            {
                actionDesc = actionDesc + "<br/>" + msg22;
            }

        }
        catch (BOException e)
        {
            // 写操作日志
            actionResult = false;
            actionDesc = "新增游戏黑名单出错!";
            LOG.error(actionDesc, e);
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, "用于添加指定的黑名单至货架时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }

        request.setAttribute(Constants.PARA_GOURL, "black.do?method=query");

        // 写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        this.saveMessagesValue(request, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("离开 add() 方法");
        }
        return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
    }

    public ActionForward importData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入 importData() 方法");
        }
        int result = 0;
        String actionType = "导入游戏黑名单信息";
        String actionDesc = "导入游戏黑名单信息成功!";
        String actionTarget = "BlackAction.importData()";
        DataImportForm iForm = (DataImportForm)form;
        String addType = this.getParameter(request, "addType");
        boolean actionResult = true;

        if (StringUtils.isBlank(addType))
        {
            actionResult = false;
            actionDesc = "倒入失败，参数错误，游戏业务ID为空!";
            LOG.warn(actionDesc);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 校验文件后缀名
        if (!iForm.checkFileNameExtension(new String[] { "xls","xlsx" }))
        {
            actionResult = false;
            actionDesc = "文件后缀名出错!";
            LOG.warn(actionDesc);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        FormFile dataFile = iForm.getDataFile();

        if (dataFile == null)
        {
            actionResult = false;
            actionDesc = "导入对象为空!";
            LOG.warn(actionDesc);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug("导入文件名称：" + dataFile.getFileName() + ";文件大小：" + dataFile.getFileSize());
        }
        try
        {
            String[] arrList = BlackBO.getInstance().getImportBalckParaseDataList(dataFile);
            Map<String, Object> resultMap1 = BlackBO.getInstance().isExistContent(arrList);
            List<String> list1 = (List<String>)resultMap1.get("list");
            String msg11 = (String)resultMap1.get("msg1");
            String msg12 = (String)resultMap1.get("msg2");

            if ("ADD".equals(addType))
            {
                Map<String, Object> resultMap2 = BlackBO.getInstance().isExistBlack(list1);
                List<String> list2 = (List<String>)resultMap2.get("list");
                String msg21 = (String)resultMap2.get("msg1");
                String msg22 = (String)resultMap2.get("msg2");
                String arr[] = new String[list2.size()];
                list2.toArray(arr);

                result = BlackBO.getInstance().importBlackADD(arr);

                actionDesc = "增量导入游戏黑名单" + result + "条记录!";
                if (StringUtils.isNotBlank(msg11))
                {
                    actionDesc = actionDesc + "<br/>" + msg11;
                }
                if (StringUtils.isNotBlank(msg12))
                {
                    actionDesc = actionDesc + "<br/>" + msg12;
                }
                if (StringUtils.isNotBlank(msg21))
                {
                    actionDesc = actionDesc + "<br/>" + msg21;
                }
                if (StringUtils.isNotBlank(msg22))
                {
                    actionDesc = actionDesc + "<br/>" + msg22;
                }
            }
            else if ("ALL".equals(addType))
            {
                result = BlackBO.getInstance().importBlackALL(list1);
                actionDesc = "全量导入游戏黑名单" + result + "条记录!";
                if (StringUtils.isNotBlank(msg11))
                {
                    actionDesc = actionDesc + "<br/>" + msg11;
                }
                if (StringUtils.isNotBlank(msg12))
                {
                    actionDesc = actionDesc + "<br/>" + msg12;
                }
            }

        }
        catch (BOException e)
        {
            actionResult = false;
            actionDesc = "导入黑名单出错!";
            LOG.error(actionDesc, e);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);

            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }
        request.setAttribute(Constants.PARA_GOURL, "black.do?method=query");

        // 写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        this.saveMessagesValue(request, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("离开 importData() 方法");
        }
        return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
    }

}
