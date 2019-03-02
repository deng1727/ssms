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
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(VideoAction.class);


	public ActionForward doPerform(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		
	    // 从请求中获取操作类型
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
			this.saveMessagesValue(request, "查询视频产品信息出错");
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
			//内容导入
			DataImportForm iForm = (DataImportForm) form;
			FormFile dataFile = iForm.getDataFile();
			if(!dataFile.getFileName().toLowerCase().endsWith(".xls")){
				this.saveMessages(request, "文件名错误，文件名应该以.xls或者.XLS结尾");
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
				this.saveMessagesValue(request, "查询视频货架树出错");
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
			StringBuffer ret = new StringBuffer("");
			if(toDealList != null ){
				ret.append("成功导入" + toDealList.size() + "条记录.");

				if(resultList != null && resultList.size()>0){
					//有执行失败的
					for(int j = 0 ; j < resultList.size();j ++){
						String temp = (String)resultList.get(j);
						if (!"".equals(temp))
			            {
			                ret.append("导入不成功id为").append(temp);
			            }
					}  
				}
				 this.saveMessagesValue(request, "导入视频产品资费描述操作成功，" + ret);
			        forward = Constants.FORWARD_COMMON_SUCCESS;
			}else{
			    forward = Constants.FORWARD_COMMON_FAILURE;
			}
			
			
			return mapping.findForward(forward);
		}
}
