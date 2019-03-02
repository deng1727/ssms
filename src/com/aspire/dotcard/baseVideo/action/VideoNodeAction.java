/**
 * com.aspire.dotcard.baseVideo.action ProductAction.java
 * Jul 5, 2012
 *<p>
 * Copyright (c) 2003-2011 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * @author dongke
 * @version 1.0
 *
 */
package com.aspire.dotcard.baseVideo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.bo.VideoBO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.ContentExtBO;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * @author tungke
 *
 */
public class VideoNodeAction extends BaseAction
{
	
	 /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(VideoAction.class);


	public ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		
		
		
		
		LOG.debug("VideoNodeAction............doPerform()");
	      /*  String nodeId = this.getParameter(request, "nodeId");
	        String name = this.getParameter(request, "nodeName");
	        String pageSize = this.getParameter(request, "pageSize");
	        if (LOG.isDebugEnabled())
	        {
	        	LOG.debug(" nodeId=" + nodeId + " name=" + name
	                          + " pageSize="
	                         + pageSize);
	        }

	        if ("".equals(pageSize.trim()))
	        {
	            pageSize = PageSizeConstants.page_DEFAULT;
	        }

	        // ��ȡ�����µ�����
	        PageResult page = new PageResult(request, Integer.parseInt(pageSize));
	        VideoBO.getInstance().queryVideoNodeExtList(page,
	                                                       nodeId,
	                                                       name);
	        		
//	                                                       spName,
//	                                                       type,
//	                                                       isrecomm,date.replaceAll("-", ""));

	        if (LOG.isDebugEnabled())
	        {
	        	LOG.debug("��ѯVideoNodeAction�ɹ�");
	        }
	        request.setAttribute("nodeId", nodeId);
	        request.setAttribute("name", name);
	  
	        request.setAttribute("PageResult", page);
	        request.setAttribute("pageSize", pageSize);

	        String forward = "showList";
*/
		
	
	    // �������л�ȡ��������
        String perType = this.getParameter(request, "perType").trim();

        if ("query".equals(perType))
        {
            return query(mapping, form, request, response);
        }
        else if ("modifyDesc".equals(perType))
        {
            return modifyDesc(mapping, form, request, response);
        }
		// TODO Auto-generated method stub
		return mapping.findForward("showList");
	      //  return mapping.findForward(forward);
	}

	/**
	 * 
	 *@desc ��ѯ
	 *@author dongke
	 *Jul 9, 2012
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws BOException
	 */
	public  ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
			{
		   String nodeId = this.getParameter(request, "nodeId");
	        String name = this.getParameter(request, "nodeName");
	        String pageSize = this.getParameter(request, "pageSize");
	        if (LOG.isDebugEnabled())
	        {
	        	LOG.debug(" nodeId=" + nodeId + " name=" + name
	                          + " pageSize="
	                         + pageSize);
	        }

	        if ("".equals(pageSize.trim()))
	        {
	            pageSize = PageSizeConstants.page_DEFAULT;
	        }

	        // ��ȡ�����µ�����
	        PageResult page = new PageResult(request, Integer.parseInt(pageSize));
	        VideoBO.getInstance().queryVideoNodeExtList(page,
	                                                       nodeId,
	                                                       name);
	        		
//	                                                       spName,
//	                                                       type,
//	                                                       isrecomm,date.replaceAll("-", ""));

	        if (LOG.isDebugEnabled())
	        {
	        	LOG.debug("��ѯVideoNodeAction�ɹ�");
	        }
	        request.setAttribute("nodeId", nodeId);
	        request.setAttribute("name", name);
	  
	        request.setAttribute("PageResult", page);
	        request.setAttribute("pageSize", pageSize);

	        String forward = "showList";
		
		   return mapping.findForward(forward);
			}
	
	
	/*   public ActionForward listVideoNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "listProduct";
		List videoNodeList = null;
		try
		{
			videoNodeList = VideoBO.getInstance().queryVideoProductList();
		} catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ��Ƶ��Ŀ˵����Ϣ����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		request.setAttribute("videoNodeList", videoNodeList);
		return mapping.findForward(forward);
	}*/
	   
	   
	   
	   public ActionForward modifyDesc(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws BOException
		{
			LOG.debug(" modifyDesc  doPerform()");
			String forward = Constants.FORWARD_COMMON_FAILURE;
			//���ݵ���
			DataImportForm iForm = (DataImportForm) form;
			FormFile dataFile = iForm.getDataFile();
			if(!dataFile.getFileName().toLowerCase().endsWith(".xls")){
				this.saveMessages(request, "�ļ��������ļ���Ӧ����.xls����.XLS��β");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
			List resultList = null;
			List toDealList = null;
			try
			{
				 toDealList =  VideoBO.getInstance().paraseDataFile( dataFile) ;

				resultList = VideoBO.getInstance().updateVideoNodeExts(toDealList);
			} catch (BOException e)
			{
				LOG.error(e);
				this.saveMessagesValue(request, "����������Ƶ��Ŀ˵����Ϣ����");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
			StringBuffer ret = new StringBuffer("");
			if(toDealList != null ){
				ret.append("�ɹ�����" + toDealList.size() + "����¼.");

				if(resultList != null && resultList.size()>0){
					//��ִ��ʧ�ܵ�
					for(int j = 0 ; j < resultList.size();j ++){
						String temp = (String)resultList.get(j);
						if (!"".equals(temp))
			            {
			                ret.append("���벻�ɹ�idΪ").append(temp);
			            }
					}  
				}
				 this.saveMessagesValue(request, "������Ƶ��Ŀ˵�������ɹ���" + ret);
			        forward = Constants.FORWARD_COMMON_SUCCESS;
			}else{
			    forward = Constants.FORWARD_COMMON_FAILURE;
			}
			
			
			return mapping.findForward(forward);
		}
}
