package com.aspire.dotcard.baseVideo.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.BlackBO;
import com.aspire.dotcard.baseVideo.dao.BlackDAO;
import com.aspire.dotcard.baseVideo.vo.BlackVO;
import com.aspire.dotcard.baseVideo.vo.ProgramBlackVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;


/**
 * 视频基地黑名单新增，删除，导入操作
 */
public class BlackAction extends BaseAction
{

    private static final JLogger LOG = LoggerFactory.getLogger(BlackAction.class);

    /**
     * ACTION
     */
    
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
            return queryblack(mapping, form, request, response);
        }
        else if ("query_vb_content".equals(method))
        {
            return query_vb_content(mapping, form, request, response);
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
        else
        {
            this.saveMessagesValue(request, "对不起，您访问的路径不存在");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
    }

    /**
     * 查询视频黑名单列表信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward queryblack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入 query() 方法");
        }
        String actionType = "查询视频黑名单信息";
        String actionDesc = "查询视频黑名单信息成功!";
        String actionTarget = "BlackAction.query()";
        boolean actionResult = true;


         String programId= request.getParameter("programId") == null ? "" : request.getParameter("programId");
         String programName= request.getParameter("programName") == null ? "" : request.getParameter("programName");
         String nodeId= request.getParameter("nodeId") == null ? "" : request.getParameter("nodeId");
         String videoId = request.getParameter("videoId") == null ? "" : request.getParameter("videoId");

        PageResult page = new PageResult(request);
        ProgramBlackVO vo = new ProgramBlackVO();
        vo.setNodeId(nodeId);
        vo.setProgramId(programId);
        vo.setProgramName(programName);
        vo.setVideoId(videoId);
        
        try
        {
            BlackBO.getInstance().queryBlackList(page, vo);
        }
        catch (BOException e)
        {
            actionResult = false;
            actionDesc = "查询视频黑名单信息出错";
            e.printStackTrace();
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
        request.setAttribute("nodeId", nodeId);
        request.setAttribute("programId", programId);
        request.setAttribute("programName", programName);
        request.setAttribute("videoId", videoId);
        // 写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("离开 query() 方法");
        }
        return mapping.findForward(Constants.FORWARD_QUERY_TOKEN);
    }

    /**
     * 查询视频内容列表信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward query_vb_content(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入 query_vb_content() 方法");
        }
        String forward = "query_vb_content";
        String actionType = "查询视频内容列表信息";
        String actionDesc = "查询视频内容列表信息成功!";
        String actionTarget = "BlackAction.query_vb_content()";
        boolean actionResult = true;

        // 从请求中获取货架内码
        String isFirst = this.getParameter(request, "isFirst").trim();
     
        String programId= request.getParameter("programId") == null ? "" : request.getParameter("programId");
        String programName= request.getParameter("programName") == null ? "" : request.getParameter("programName");
        String nodeId= request.getParameter("nodeId") == null ? "" : request.getParameter("nodeId");
        String videoId = request.getParameter("videoId") == null ? "" : request.getParameter("videoId");

        BlackVO vo = new BlackVO();
        PageResult page = new PageResult(request);

        // 如果是第一次。跳过
        if ("1".equals(isFirst))
        {
            request.setAttribute("isFirst", isFirst);
            request.setAttribute("method", forward);
            request.setAttribute("PageResult", page);
            request.setAttribute("nodeId", nodeId);
            request.setAttribute("programId", programId);
            request.setAttribute("programName", programName);
            request.setAttribute("videoId", videoId);

            return mapping.findForward(forward);
        }

        request.setAttribute("nodeId", nodeId);
        request.setAttribute("programId", programId);
        request.setAttribute("programName", programName);
        request.setAttribute("videoId", videoId);
       vo.setProgramId(programId);
       vo.setProgramName(programName);
       vo.setVideoId(videoId);
       vo.setNodeId(nodeId);
        try
        {
            BlackBO.getInstance().queryContentList(page, vo);
        }
        catch (BOException e)
        {
            LOG.error(e);
            e.printStackTrace();
            this.saveMessagesValue(request, "查询内容列表出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        request.setAttribute("method", forward);
        request.setAttribute("PageResult", page);
        request.setAttribute("nodeId", nodeId);
        request.setAttribute("programId", programId);
        request.setAttribute("programName", programName);
        request.setAttribute("videoId", videoId);

        // 写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("离开 query_vb_content() 方法");
        }

        return mapping.findForward(forward);
    }

    /**
     * 删除视频黑名单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入 remove() 方法");
        }
        String actionType = "删除视频黑名单信息";
        String actionDesc = "删除视频黑名单信息成功!";
        String actionTarget = "BlackAction.remove()";
        String[] id = request.getParameterValues("dealRef");
       // String[] nodeid = request.getParameterValues("nodeid");

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
            actionDesc = "删除视频黑名单信息出错";
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

    /**
     * 新增视频黑名单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    @SuppressWarnings("unchecked")
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入 add() 方法");
        }
        String actionType = "新增视频黑名单";
        String actionDesc = "新增视频黑名单成功!";
        String actionTarget = "BlackAction.add()";
        String[] programid = request.getParameterValues("dealRef");
        
        boolean actionResult = true;

        if (programid == null || (programid != null && programid.length <= 0))
        {
            actionResult = false;
            actionDesc = "添加失败，参数错误，节目ID为空!";
            LOG.warn(actionDesc);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);

            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }


        try
        {

            // Map<String, Object> resultMap1 = BlackBO.getInstance().isExistContent(contentIdArray);
            // List<String> list1 = (List<String>)resultMap1.get("list");
            // String msg11 = (String)resultMap1.get("msg1");
            // String msg12 = (String)resultMap1.get("msg2");

            List<String> list1 = new ArrayList<String>(programid.length);
            List<String> list2 = new ArrayList<String>(programid.length);

            boolean resultb =false;
            for (String id : programid)
            {
            int indexStart = id.indexOf("|");
            String a= id.substring(0, indexStart);
            String b= id.substring(indexStart+1);
   			try {
				 resultb = BlackDAO.getInstance().isExistBlack(a,b);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!resultb){
                list1.add(a);
                list2.add(b);
			}
            }

//            Map<String, Object> resultMap2 = BlackBO.getInstance().isExistBlack(list1);
//            List<String> list2 = (List<String>)resultMap2.get("list");
//            String msg21 = (String)resultMap2.get("msg1");
//            String msg22 = (String)resultMap2.get("msg2");

            String arr[] = new String[list1.size()];
            String arr2[] = new String[list2.size()];

            list1.toArray(arr);
            list2.toArray(arr2);

            int result = BlackBO.getInstance().addBlack(arr,arr2);

            actionDesc = "新增视频黑名单" + result + "条记录!";

//            if (StringUtils.isNotBlank(msg11))
//            {
//                actionDesc = actionDesc + "<br/>" + msg11;
//            }
//            if (StringUtils.isNotBlank(msg12))
//            {
//                actionDesc = actionDesc + "<br/>" + msg12;
//            }
//            if (StringUtils.isNotBlank(msg21))
//            {
//                actionDesc = actionDesc + "<br/>" + msg21;
//            }
//            if (StringUtils.isNotBlank(msg22))
//            {
//                actionDesc = actionDesc + "<br/>" + msg22;
//            }

        }
        catch (BOException e)
        {
            // 写操作日志
            actionResult = false;
            actionDesc = "新增视频黑名单出错!";
            LOG.error(actionDesc, e);
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, "用于添加指定的黑名单至货架时出错");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        if (LOG.isDebugEnabled())
        {
            LOG.debug(actionDesc);
        }

        request.setAttribute(Constants.PARA_GOURL, "queryVbContent.do?method=query_vb_content");

        // 写操作日志
        this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
        this.saveMessagesValue(request, actionDesc);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("离开 add() 方法");
        }
        return mapping.findForward(Constants.FORWARD_COMMON_SUCCESS);
    }

    /**
     * 视频黑名单信息EXCEL导入
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    @SuppressWarnings("unchecked")
    public ActionForward importData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("进入 importData() 方法");
        }
        int result = 0;
        String actionType = "导入视频黑名单信息";
        String actionDesc = "导入视频黑名单信息成功!";
        String actionTarget = "BlackAction.importData()";
        DataImportForm iForm = (DataImportForm)form;
        String addType = this.getParameter(request, "addType");
        boolean actionResult = true;

        if (StringUtils.isBlank(addType))
        {
            actionResult = false;
            actionDesc = "倒入失败，参数错误，内容ID为空!";
            LOG.warn(actionDesc);
            // 写操作日志
            this.actionLog(request, actionType, actionTarget, actionResult, actionDesc);
            this.saveMessagesValue(request, actionDesc);
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }

        // 校验文件后缀名
        if (!iForm.checkFileNameExtension(new String[] { "xls","xlsx"}))
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
        	HashMap<String,String[]> arrList = BlackBO.getInstance().getImportBalckParaseDataList(dataFile);
//            Map<String, Object> resultMap1 = BlackBO.getInstance().isExistContent(arrList);
//            List<String> list1 = (List<String>)resultMap1.get("list");
//            String msg11 = (String)resultMap1.get("msg1");
//            String msg12 = (String)resultMap1.get("msg2");

            if ("ADD".equals(addType))
            {
//               Map<String, Object> resultMap2 = BlackBO.getInstance().isExistBlack(list1);
//                List<String> list2 = (List<String>)resultMap2.get("list");
//                String msg21 = (String)resultMap2.get("msg1");
//                String msg22 = (String)resultMap2.get("msg2");
//                String arr[] = new String[arrList.size()];
                //arrList.toArray(arr);

                result = BlackBO.getInstance().importBlackADD(arrList);

                actionDesc = "增量导入视频黑名单" + result + "条记录!";
//                if (StringUtils.isNotBlank(msg11))
//                {
//                    actionDesc = actionDesc + "<br/>" + msg11;
//                }
//                if (StringUtils.isNotBlank(msg12))
//                {
//                    actionDesc = actionDesc + "<br/>" + msg12;
//                }
//                if (StringUtils.isNotBlank(msg21))
//                {
//                    actionDesc = actionDesc + "<br/>" + msg21;
//                }
//                if (StringUtils.isNotBlank(msg22))
//                {
//                    actionDesc = actionDesc + "<br/>" + msg22;
//                }
            }
//            else if ("ALL".equals(addType))
//            {
//                result = BlackBO.getInstance().importBlackALL(list1);
//                actionDesc = "全量导入视频黑名单" + result + "条记录!";
////                if (StringUtils.isNotBlank(msg11))
////                {
////                    actionDesc = actionDesc + "<br/>" + msg11;
////                }
////                if (StringUtils.isNotBlank(msg12))
////                {
////                    actionDesc = actionDesc + "<br/>" + msg12;
////                }
//            }

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
