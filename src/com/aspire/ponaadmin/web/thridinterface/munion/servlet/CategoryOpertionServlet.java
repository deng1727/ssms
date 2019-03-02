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
	 * ����ӿڶԻ������¼ܲ���
	 */
	private static final long serialVersionUID = -3175089950598805162L;
	private static final JLogger LOG = LoggerFactory
			.getLogger(CategoryOpertionServlet.class);
	private static final String parasErrorCode = "{\"Result\":\"200\",\"Msg\":\"�����������...\"}";
	private static final String ErrorCode = "{\"Result\":\"100\",\"Msg\":\"�ڲ�����\"}";
	private static final String successCode = "{\"Result\":\"000\",\"Msg\":\"�ϼܳɹ�\"}";

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
			// //����

			// ��ȡ������Ϣ
			Category category = (Category) Repository.getInstance().getNode(
					cateId, RepositoryConstants.TYPE_CATEGORY);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mailTitle", "��������ͻ����Ż���Ӫ���ݵ�������");
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;����Id��"
					+ category.getCategoryID();
			map.put("mailContent", value);
			map.put("status", "2");
			map.put("categoryId", category.getCategoryID());
			map.put("operation", "10");
			map.put("operationtype", "������Ʒ�����ύ����");
			map.put("operationobj", "����");
			map.put("operationobjtype", "����Id��" + category.getCategoryID());
			map.put("submit", "all");
			map.put("dealContent", null);
			CategoryOperationBO.getInstance().operateInfo(request, category,
					"2", "1", map);

			// //����
			Map<String, Object> map2 = new HashMap<String, Object>();

			// ����MM����
			map2.put("operation", "10");

			map2.put("operationobj", "����");

			if ("1".equals(operation)) {// ���ܷ��������

				CategoryOperationBO.getInstance().operateInfoforCategory(
						request, cateId, "1", "2", map2);

			} else if ("2".equals(operation)) {// ������Ʒ������
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
