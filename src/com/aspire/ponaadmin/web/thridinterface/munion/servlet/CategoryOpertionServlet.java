package com.aspire.ponaadmin.web.thridinterface.munion.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseServlet;
import com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.CategoryOperationBO;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.thridinterface.munion.bo.CategoryOptionBo;

public class CategoryOpertionServlet extends BaseServlet {

	/**
	 * 对外接口对货架上下架操作
	 */
	private static final long serialVersionUID = -3175089950598805162L;
	private static final JLogger LOG = LoggerFactory
			.getLogger(CategoryOpertionServlet.class);
	private static final String parasErrorCode = "{\"Result\":\"200\",\"Msg\":\"请求参数有误...\"}";
	private static final String ErrorCode = "{\"Result\":\"100\",\"Msg\":\"内部错误\"}";
	private static final String successCode = "{\"Result\":\"000\",\"Msg\":\"上架成功\"}";

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject paras = null;
		try {
			paras = getJsonFromRequest(request, response);
		} catch (JSONException e) {
			flushJsonStr(parasErrorCode, response);
			LOG.debug(e);
			return;
		}
		String addType = paras.getString("addType");
		String cateId = paras.getString("cateId");
		String cgyPath = paras.getString("cgyPath");
		String isSyn = paras.getString("isSyn");
		String menuStatus = paras.getString("menuStatus");
		String[] contentids = paras.getString("contentids").split(",");
		String operation = "2";
		try {

			Category category = (Category) Repository.getInstance().getNode(
					cateId, RepositoryConstants.TYPE_CATEGORY);
			if (menuStatus != null && !"".equals(menuStatus)
					&& "3".equals(menuStatus)) {
				category.setGoodsStatus("0");
				category.save();
			}
			if ("ADD".equals(addType)) {
				CategoryOptionBo.getInstance().importCateData2(contentids,
						category, isSyn, menuStatus);
			} else if ("ALL".equals(addType)) {
				CategoryOptionBo.getInstance().importCateData(contentids,
						category, isSyn, menuStatus);
			}

			LockLocationDAO.getInstance().callProcedureAutoFixSortid(
					category.getCategoryID());
		} catch (Exception e) {
			flushJsonStr(ErrorCode, response);
		}
		try {
			// //审批

			// 获取分类信息
			Category category = (Category) Repository.getInstance().getNode(
					cateId, RepositoryConstants.TYPE_CATEGORY);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mailTitle", "待审事项：客户端门户运营内容调整审批");
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;货架Id："
					+ category.getCategoryID();
			map.put("mailContent", value);
			map.put("status", "2");
			map.put("categoryId", category.getCategoryID());
			map.put("operation", "10");
			map.put("operationtype", "货架商品管理提交审批");
			map.put("operationobj", "货架");
			map.put("operationobjtype", "货架Id：" + category.getCategoryID());
			map.put("submit", "all");
			map.put("dealContent", null);
			CategoryOperationBO.getInstance().operateInfo(request, category,
					"2", "1", map);

			// //发布
			Map<String, Object> map2 = new HashMap<String, Object>();

			// 处理MM货架
			map2.put("operation", "10");

			map2.put("operationobj", "货架");

			if ("1".equals(operation)) {// 货架分类的审批

				CategoryOperationBO.getInstance().operateInfoforCategory(
						request, cateId, "1", "2", map2);

			} else if ("2".equals(operation)) {// 货架商品的审批
				CategoryOperationBO.getInstance().operateInfoforGoods(request,
						cateId, "1", "2", map2);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		flushJsonStr(successCode, response);

	}

}
