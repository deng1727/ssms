package com.aspire.ponaadmin.web.datafield.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.datafield.bo.ContentExtBO;
import com.aspire.ponaadmin.web.repository.web.DataImportForm;

/**
 * <p>
 * 保存应用活动属性Action
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author aiyan
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class ContentExtImportAction extends BaseAction {

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(ContentExtImportAction.class);
	
	
	private static String[] typeArr= new String[]{"","_zk.xls","_ms.xls","_xsmf.xls"};

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("ContentExtImportAction doPerform()");
		}
		//定义返回页面
		String forward = Constants.FORWARD_COMMON_SUCCESS;

		//内容导入
		DataImportForm iForm = (DataImportForm) form;
        
        // 校验文件后缀名
        if(!iForm.checkFileNameExtension(new String[]{"xls"}))
        {
            this.saveMessages(request, "文件后缀名出错！");
            return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
        }
        
		FormFile dataFile = iForm.getDataFile();
		
		
		String userid = UserManagerBO.getInstance()
        .getUserSessionVO(request.getSession())
        .getUser()
        .getUserID();
		
		Map params = new HashMap();
		params.put("userid", userid);
		String type = request.getParameter("type");
		params.put("type", type);
		
		
		if(!dataFile.getFileName().toLowerCase().endsWith(typeArr[Integer.parseInt(type)])){
			this.saveMessages(request, "文件名错误，文件名应该以_MS.xls、_ZK.xls或者_XSMF.xls结尾,且与你选择的折扣类型匹配。");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		
		
		Map ret=ContentExtBO.getInstance().contentExtImport(dataFile,params);
		String message="数据导入成功，共成功导入"+ ret.get("succCount")+ "条数据";
		LOG.info(message);
		this.actionLog(request,
		               "折扣信息导入",
		               dataFile.getFileName(),
		               true,
		               "共导入了" +  ret.get("succCount")+ "条数据");
         this.saveMessagesValue(request, message);
         
         List formatList = (List)(ret.get("formatList"));
         List existList = (List)(ret.get("existList"));
         List notExistList = (List)(ret.get("notExistList"));
         
         
         if(formatList.size()>0){
        	 this.saveMessages(request, "数据格式不正确 "+formatList.size()+" 条");
             
             
             StringBuffer sb = new StringBuffer("具体的应用ID:");
             for(int i = 0;i<formatList.size();i++){
            	 sb.append(((Map)formatList.get(i)).get("A")).append(",");
             }
             
             this.saveMessages(request, sb.toString().substring(0,sb.toString().length()-1));
             
             
         }
         
         
         if(existList.size()>0){
        	 this.saveMessages(request, "数据重叠 "+existList.size()+" 条");
        	 StringBuffer sb = new StringBuffer("具体的应用ID:");
             for(int i = 0;i<existList.size();i++){
            	 sb.append(((Map)existList.get(i)).get("A")).append(",");
             }
             
             this.saveMessages(request, sb.toString().substring(0,sb.toString().length()-1));
             
         }
         
         if(notExistList.size()>0){
        	 this.saveMessages(request, "不存在这样的应用ID "+notExistList.size()+" 条");
        	 StringBuffer sb = new StringBuffer("具体的应用ID:");
             for(int i = 0;i<notExistList.size();i++){
            	 sb.append(((Map)notExistList.get(i)).get("A")).append(",");
             }
             
             this.saveMessages(request, sb.toString().substring(0,sb.toString().length()-1));
             
         }
         
         
		return mapping.findForward(forward);
	}
}
