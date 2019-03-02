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
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

/**
 * @author tungke
 *
 */
public class ProductAction extends BaseAction
{
	
	 /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(VideoAction.class);


	public ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		
	    // �������л�ȡ��������
        String perType = this.getParameter(request, "perType").trim();

        if ("listProduct".equals(perType))
        {
            return listProduct(mapping, form, request, response);
        }
        else if ("modifyDesc".equals(perType))
        {
            return modifyDesc(mapping, form, request, response);
        }
		// TODO Auto-generated method stub
		return null;
	}

	   public ActionForward listProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		LOG.debug("doPerform()");
		String forward = "listProduct";
		List videoProductList = null;
		try
		{
			videoProductList = VideoBO.getInstance().queryVideoProductList();
		} catch (BOException e)
		{
			LOG.error(e);
			this.saveMessagesValue(request, "��ѯ��Ƶ��Ʒ��Ϣ����");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		request.setAttribute("videoProductList", videoProductList);
		return mapping.findForward(forward);
	}
	   
	   
	   
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

				resultList = VideoBO.getInstance().updateProductFeeDescs(toDealList);
			} catch (BOException e)
			{
				LOG.error(e);
				this.saveMessagesValue(request, "��ѯ��Ƶ����������");
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
				 this.saveMessagesValue(request, "������Ƶ��Ʒ�ʷ����������ɹ���" + ret);
			        forward = Constants.FORWARD_COMMON_SUCCESS;
			}else{
			    forward = Constants.FORWARD_COMMON_FAILURE;
			}
			
			
			return mapping.findForward(forward);
		}
}
