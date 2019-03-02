package com.aspire.ponaadmin.web.repository.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.dc.json2.JsonRespVO;
import com.aspire.dotcard.syncAndroid.dc.json2.JsonSender;
import com.aspire.dotcard.syncAndroid.dc.json2.ListJson;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.common.page.PageConstants;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.lockLocation.bo.LockLocationBO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.system.PageSizeConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>查询栏目下内容列表的Action</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CgyContentListAction extends BaseAction
{

	/**
	 * 日志引用
	 */
	private static final JLogger LOG = LoggerFactory
			.getLogger(CgyContentListAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("CgyContentListAction in.....");
		}
		String isExport = this.getParameter(request, "isExport");

		if ("0".equals(isExport))
		{
			return this.ssms(mapping, form, request, response);
		}
		else if ("1".equals(isExport))
		{
			return this.doExport(mapping, form, request, response);
		}
		else 
		{
			return this.ssms(mapping, form, request, response);
		}
	}
	
	/**
	 * 虚拟货架显示
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward handVirtualCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String forward = "showListVirtual";
		try {
			List<JsonRespVO> list = new ArrayList<JsonRespVO>();
			
			String parentid = request.getParameter("categoryID");//必选
			String deviceid = request.getParameter("deviceid");//可选
			
			
			//从请求参数中得到当前页序号，如果获取失败，则认为取第一页
	        String param = request.getParameter(PageConstants.PAGE_INDEX_NAME) ;
	        int queryPage = 1;
	        if (null != param && (param.length() != 0))
	        {
	        	queryPage = Integer.parseInt(param);
	        }
	        else
	        {
	        	queryPage = 1;
	        }
	        
	        ModuleConfig module_1 = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid_virtual_1");
	        int pageSize = Integer.parseInt(module_1.getItemValue("RecordsPage"));
	        
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
			String url = module.getItemValue("DC_LIST_URL");
			LOG.debug("DC_LIST_URL:"+url);
			String jsonStr = new JsonSender(url,new ListJson().getListJson(parentid,deviceid,queryPage)).send();
			LOG.debug("jsonStr:"+jsonStr);
			JSONObject json = new JSONObject(jsonStr);
			String hRet = json.getString("hRet");
			if(hRet.equals("0")){
				//{"CurPage":1,"TotalRecord":0,"hRet":0,"RecordPage":0}
				if(!"0".equals(json.getString("TotalRecord"))){
					JSONArray arr = json.getJSONArray("Ids");
					for(int i=0;i<arr.length();i++){
						JSONObject obj = (JSONObject)arr.get(i);
						String id = obj.getString("Id");
						JSONObject content = obj.getJSONObject("Content");
						//NAME|SPNAME|ICPCODE|ICPSERVID|CATENAME|LUPDATE|SERVATTR|CONTENTID
						String name = content.getString("NAME");
						String spname = content.getString("SPNAME");
						String icpcode = content.getString("ICPCODE");

						String icpservid = content.getString("ICPSERVID");
						String catename = content.getString("CATENAME");
						String servattr = content.getString("SERVATTR");
						String contentid = content.getString("CONTENTID");
//						名称name
//						提供商spName
//						企业代码icpCode
//						业务代码icpServId
//						内容类型cateName
//						提供范围servAttr
						JsonRespVO vo = new JsonRespVO();
						vo.setName(name);
						vo.setSpname(spname);
						vo.setIcpcode(icpcode);
						vo.setIcpservid(icpservid);
						vo.setCatename(catename);
						vo.setServattr(servattr);
						vo.setContentid(contentid);
						list.add(vo);
					}
				}
				PageResult page = new PageResult();
				page.setTotalRows(json.getInt("TotalRecord"));
				page.setPageSize(pageSize);
				page.setCurrentPageNo(queryPage);
				int totalPages = page.getTotalRows()/page.getPageSize()+(page.getTotalRows()%page.getPageSize()==0?0:1);
                page.setTotalPages(totalPages);;
					
				page.setPageInfo(list);
				request.setAttribute("PageResult", page);
			}else{
				LOG.error("数据中心的返回JSON表示查询不成功！返回JSON值："+json.toString());
				saveMessagesValue(request, "数据中心的返回JSON表示查询不成功！返回JSON值："+json.toString());
				return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("虚拟货架展示出现异常！",e);
			saveMessagesValue(request, "虚拟货架展示出现异常！");
			return mapping.findForward(Constants.FORWARD_COMMON_FAILURE);
		}
		return mapping.findForward(forward);
	}
	public ActionForward ssms(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		LOG.debug("doPerform()");
		//是否查询内容的类型。
		boolean isExistedType = false;
		String categoryID = this.getParameter(request, "categoryID");
		
        //商品库优化：虚拟货架商品显示。
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
		String rootCategoryId = module.getItemValue("VIRTUAL_ROOT_CATEGORYID");
        if(SSMSDAO.getInstance().isAndroidCategory(categoryID, rootCategoryId)){
        	return handVirtualCategory(mapping,form,request,response);
        }
		
		
		String name = this.getParameter(request, "name");
		// String author = this.getParameter(request, "author");
		//String source = this.getParameter(request, "source");
		// String keywords = this.getParameter(request, "keywords");

		String spName = this.getParameter(request, "spName");
		String cateName = this.getParameter(request, "cateName");
		String type = this.getParameter(request, "type");
		String icpCode = this.getParameter(request, "icpCode");
		String icpServId = this.getParameter(request, "icpServId");
		String contentID = this.getParameter(request, "contentID");
		String appId = this.getParameter(request, "appId");
		//add by aiyan 2012-03-05
		String trueContentID = this.getParameter(request, "trueContentID");

		String contentTag = this.getParameter(request, "contentTag");
		String tagLogic = this.getParameter(request, "tagLogic");

		//String deviceName = this.getParameter(request, "deviceName").toUpperCase();//忽略大小写。
		String deviceName = this.getParameter(request, "deviceName").trim();
		String platform = this.getParameter(request, "platform").trim();

		String pageSize = this.getParameter(request, "pageSize");
		String servAttr = this.getParameter(request, "servAttr");
		String aprovalStatus = this.getParameter(request, "aprovalStatus");
		String menuStatus = this.getParameter(request, "menuStatus");
		String lockNums = this.getParameter(request, "lockNums");

		if ("".equals(pageSize.trim()))
		{
			pageSize = PageSizeConstants.page_DEFAULT;
		}

		//设置搜索条件：
		Searchor searchor = new Searchor();

		if (!name.equals(""))
		{
			searchor.getParams()
					.add(
							new SearchParam("name", RepositoryConstants.OP_LIKE,
									'%' + name + '%'));
		}
		if (!spName.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("g.spName", RepositoryConstants.OP_LIKE,
							'%' + spName + '%'));
		}
		if (!cateName.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("cateName", RepositoryConstants.OP_LIKE,
							'%' + cateName + '%'));
		}
		if (!type.equals(""))
		{
			if("nt:gcontent:appBaseGame".equals(type))
			{
				SearchParam searchParam = new SearchParam("type",
						RepositoryConstants.OP_LIKE, "nt:gcontent:appGame" + '%');
				searchParam.setSearchRef(true);
				searchor.getParams().add(searchParam);
				
				searchor.getParams().add(
						new SearchParam("provider", RepositoryConstants.OP_EQUAL,
								"B"));
			}
			else
			{
				SearchParam searchParam = new SearchParam("type",
						RepositoryConstants.OP_LIKE, type + '%');
				searchParam.setSearchRef(true);
				searchor.getParams().add(searchParam);
			}

			isExistedType = true;
		}
		if (!icpCode.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("g.icpCode", RepositoryConstants.OP_LIKE,
							'%' + icpCode + '%'));
		}
		if (!icpServId.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("g.icpServId", RepositoryConstants.OP_LIKE,
							'%' + icpServId + '%'));
		}
		if (!contentTag.equals(""))
		{
			StringTokenizer st = new StringTokenizer(contentTag, " ");
			int count = st.countTokens();
			for (int i = 0; st.hasMoreTokens(); i++)
			{
				String keyword = st.nextToken();
				SearchParam param = new SearchParam("keywords",
						RepositoryConstants.OP_LIKE, "%" + keyword + "%");
				// 如果不是第一个，就是or类型的
				if (i > 0)
				{
					if ("OR".equals(tagLogic))
					{
						param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_OR);
					}
					else
					{
						param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_AND);
					}

				}
				// 如果有多个条件，要组合括号
				if (count > 1)
				{
					// 第一个要加一个左括号
					if (i == 0)
					{
						param.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
					}
					// 最后一个要加一个右括号
					if (i == count - 1)
					{
						param.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);
					}
				}
				searchor.getParams().add(param);
			}
		}

		if (!contentID.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("refNodeID", RepositoryConstants.OP_LIKE,
							'%' + contentID + '%'));
		}
		if (!appId.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("g.appid", RepositoryConstants.OP_LIKE,
							'%'+appId+'%' ));
		}
		//add by aiyan 2012-03-06
		if (!"".equals(trueContentID)){
			searchor.getParams().add(new SearchParam("g.ContentID", RepositoryConstants.OP_LIKE,'%' +trueContentID+ '%'));
		}
		
		//      add by tungke 
		if (!servAttr.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("g.servAttr", RepositoryConstants.OP_EQUAL, servAttr));
		}
		
		// add by wml 2012-10-18
		if(!"".equals(platform))
		{
			searchor.getParams().add(
					new SearchParam("platform", RepositoryConstants.OP_LIKE, '%' +platform+ '%'));
		}
		boolean isexistDevice = false;
		if (!deviceName.equals(""))
		{
			String devicenamen = PublicUtil.filterMbrandEspecialChar(deviceName);
			String temp = '%' + devicenamen + '%';
			SearchParam para=new SearchParam("fulldevicename",RepositoryConstants.OP_LIKE, temp);
			//para.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);//第一个参数前需要添加左括号
			searchor.getParams().add(para);
			isexistDevice = true;//按适配机型查询
//			for (int i = 2; i <= 20; i++)
//			{
//				String colName = i < 10 ? "deviceName0" + i : "deviceName" + i;
//				para=new SearchParam(colName, RepositoryConstants.OP_LIKE_IgnoreCase,temp);
//				para.setMode(RepositoryConstants.SEARCH_PARAM_MODE_OR);
//				searchor.getParams().add(para);
//			}
//			para.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);//最后一个参数需要添加右括号。
		}
		// 都是浅度搜索
		searchor.setIsRecursive(false);

		// 获取分类信息
		Category category = (Category) Repository.getInstance().getNode(categoryID,
				RepositoryConstants.TYPE_CATEGORY);

		//获取分类下的内容
		PageResult page = new PageResult(request);
		// page.setPageSize(6+6);
		page.setPageSize(Integer.parseInt(pageSize));

		//排序方式:根据1.1.1.044需求（先按序号降序再按接入时间降序排序）　guanzf 20071108
		Taxis taxis = new Taxis();
		taxis.getParams().add(
				new TaxisParam("sortID", RepositoryConstants.ORDER_TYPE_DESC));
		taxis.getParams().add(
				new TaxisParam("marketDate", RepositoryConstants.ORDER_TYPE_DESC));

//		CgyContentListBO.getInstance().getCgyContentList(page, category, searchor, taxis,
//				isExistedType);
		CgyContentListBO.getInstance().getCgyContentList(page, category, searchor, taxis,
				isExistedType,isexistDevice,aprovalStatus);
		Map map = new HashMap();
		map.put("categoryId", category.getCategoryID());
		if("".equals(lockNums)){
			lockNums = LockLocationBO.getInstance().getLockNums(map);
		}
		request.setAttribute("category", category);
		request.setAttribute("PageResult", page);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("lockNums", lockNums);
		if(menuStatus != null && !"".equals(menuStatus)){
			request.setAttribute("menuStatus", menuStatus);
		}
		String forward = "showList";

		return mapping.findForward(forward);
	}
	
	public ActionForward doExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("doExport in......");
			log.debug("excel导出.......");
		}

		OutputStream os = null;
		WritableWorkbook wwb = null;
		String excelName = "query_category_content_"
				+ System.currentTimeMillis() + ".xls";
		try
		{
			os = new FileOutputStream(excelName);
			wwb = Workbook.createWorkbook(os);
			CgyContentListBO.getInstance().exportQueryApp(request, wwb);
		}
		catch (Exception e)
		{}
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
			{}
		}
		response.setHeader("Content-disposition", "attachment;filename="
				+ excelName);
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
}
