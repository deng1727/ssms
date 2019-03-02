package com.aspire.ponaadmin.web.channelUser.action;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class CgyContentListBO
{
	private static CgyContentListBO BO = new CgyContentListBO();

	public static CgyContentListBO getInstance()
	{
		return BO;
	}

	/**
	 * ��ȡ��ǰ�����·�ҳ����Ʒ�����ݡ�
	 * 
	 * @param page
	 *            ��ҳ����
	 * @param category
	 *            ����ѯ�Ļ���
	 * @param searchor
	 *            ��ѯ����.���ڲ��������ƿ����ڶ������ڵ����������id������Ҫ����ָ�������ơ�base��b��reference��r��gcontent��g��
	 * @param taxis
	 *            ��ѯ������
	 * @param isExistedType
	 *            ��ѯ���Ƿ����type�ֶ�
	 * @param isIncluded
	 *            �Ƿ��ѯ��ǰ���ܡ�true ��ʾ��ǰ���ܣ�false ��ʾ�ǵ�ǰ���������ݡ�
	 * @return �û�������Ʒ�������б�
	 */
	public void getCgyContentList(PageResult page, Category category,
			Searchor searchor, Taxis taxis, boolean isExistedType,
			boolean isExistedDevice) throws BOException
	{
		try
		{

			CgyContentListDAO.getInstance().getCgyContentList(page, category,
					searchor, taxis, isExistedType, isExistedDevice);

		}
		catch (DAOException e)
		{
			throw new BOException("��ѯ�����������쳣��cateId=" + category.getId(), e);
		}
	}
	
	/**
	 * ��ȡ��ǰ�����·�ҳ����Ʒ�����ݡ�
	 * 
	 * @param page
	 *            ��ҳ����
	 * @param category
	 *            ����ѯ�Ļ���
	 * @param searchor
	 *            ��ѯ����.���ڲ��������ƿ����ڶ������ڵ����������id������Ҫ����ָ�������ơ�base��b��reference��r��gcontent��g��
	 * @param taxis
	 *            ��ѯ������
	 * @param isExistedType
	 *            ��ѯ���Ƿ����type�ֶ�
	 * @param isIncluded
	 *            �Ƿ��ѯ��ǰ���ܡ�true ��ʾ��ǰ���ܣ�false ��ʾ�ǵ�ǰ���������ݡ�
	 * @return �û�������Ʒ�������б�
	 */
	public List getCgyContentListByExport(PageResult page, Category category,
			Searchor searchor, Taxis taxis, boolean isExistedType,
			boolean isExistedDevice) throws BOException
	{
		try
		{

			return CgyContentListDAO.getInstance().getCgyContentListByExport(page, category,
					searchor, taxis, isExistedType, isExistedDevice);

		}
		catch (DAOException e)
		{
			throw new BOException("��ѯ�����������쳣��cateId=" + category.getId(), e);
		}
	}

	/**
	 * ��ȡ��ǰ�����·�ҳ����Ʒ�����ݡ�
	 * 
	 * @param page
	 *            ��ҳ����
	 * @param category
	 *            ����ѯ�Ļ���
	 * @param searchor
	 *            ��ѯ����.���ڲ��������ƿ����ڶ������ڵ����������id������Ҫ����ָ�������ơ�base��b��reference��r��gcontent��g��
	 * @param taxis
	 *            ��ѯ������
	 * @param isExistedType
	 *            ��ѯ���Ƿ����type�ֶ�
	 * @param isIncluded
	 *            �Ƿ��ѯ��ǰ���ܡ�true ��ʾ��ǰ���ܣ�false ��ʾ�ǵ�ǰ���������ݡ�
	 * @return �û�������Ʒ�������б�
	 */
	public void getCgyContentList(PageResult page, Category category,
			Searchor searchor, Taxis taxis, boolean isExistedType)
			throws BOException
	{
		try
		{

			CgyContentListDAO.getInstance().getCgyContentList(page, category,
					searchor, taxis, isExistedType);

		}
		catch (DAOException e)
		{
			throw new BOException("��ѯ�����������쳣��cateId=" + category.getId(), e);
		}
	}

	/**
	 * ��ȡ�ǵ�ǰ�����·�ҳ����Ʒ�����ݡ�
	 * 
	 * @param page
	 *            ��ҳ����
	 * @param category
	 *            ����ѯ�Ļ���
	 * @param searchor
	 *            ��ѯ������
	 *            ���ڲ��������ƿ����ڶ������ڵ����������id������Ҫ����ָ�������ơ�base��b��reference��r��gcontent��g��
	 * @param taxis
	 *            ��ѯ������
	 * @param isExistedType
	 *            ��ѯ���Ƿ����type�ֶ�
	 * @param isIncluded
	 *            �Ƿ��ѯ��ǰ���ܡ�true ��ʾ��ǰ���ܣ�false ��ʾ�ǵ�ǰ���������ݡ�
	 * @param channelsId
	 *                ����Id        
	 * @return �û�������Ʒ�������б�
	 */
	public void getCgyNotContentList(PageResult page, Category category,
			Searchor searchor, Taxis taxis, boolean isExistedType,String channelsId)
			throws BOException
	{
		try
		{

			CgyContentListDAO.getInstance().getCgyNotContentList(page,
					category, searchor, taxis, isExistedType,channelsId);

		}
		catch (DAOException e)
		{
			throw new BOException("��ѯ�ǵ�ǰ�����������쳣��cateId=" + category.getId(), e);
		}
	}

	public void exportQueryApp(HttpServletRequest request, WritableWorkbook wwb) throws BOException
	{
        List list = null;
        String sheetName = "Sheet1";
        WritableSheet ws = wwb.createSheet(sheetName, 0); //����sheet

		// �Ƿ��ѯ���ݵ����͡�
		boolean isExistedType = false;
		String categoryID = this.getParameter(request, "categoryID");
		String name = this.getParameter(request, "name");
		String spName = this.getParameter(request, "spName");
		String cateName = this.getParameter(request, "cateName");
		String type = this.getParameter(request, "type");
		String icpCode = this.getParameter(request, "icpCode");
		String icpServId = this.getParameter(request, "icpServId");
		String contentID = this.getParameter(request, "contentID");
		String trueContentID = this.getParameter(request, "trueContentID");
		String contentTag = this.getParameter(request, "contentTag");
		String tagLogic = this.getParameter(request, "tagLogic");
		String deviceName = this.getParameter(request, "deviceName").trim();
		String platform = this.getParameter(request, "platform").trim();
		String servAttr = this.getParameter(request, "servAttr");

		// ��������������
		Searchor searchor = new Searchor();

		if (!name.equals(""))
		{
			searchor.getParams().add(
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
					new SearchParam("g.cateName", RepositoryConstants.OP_LIKE,
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
				SearchParam param = new SearchParam("g.keywords",
						RepositoryConstants.OP_LIKE, "%" + keyword + "%");
				// ������ǵ�һ��������or���͵�
				if (i > 0)
				{
					if ("OR".equals(tagLogic))
					{
						param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_OR);
					}
					else
					{
						param
								.setMode(RepositoryConstants.SEARCH_PARAM_MODE_AND);
					}

				}
				// ����ж��������Ҫ�������
				if (count > 1)
				{
					// ��һ��Ҫ��һ��������
					if (i == 0)
					{
						param
								.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
					}
					// ���һ��Ҫ��һ��������
					if (i == count - 1)
					{
						param
								.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);
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

		// add by aiyan 2012-03-06
		if (!"".equals(trueContentID))
		{
			searchor.getParams().add(
					new SearchParam("g.ContentID", RepositoryConstants.OP_LIKE,
							'%' + trueContentID + '%'));
		}

		// add by tungke
		if (!servAttr.equals(""))
		{
			searchor.getParams().add(
					new SearchParam("g.servAttr", RepositoryConstants.OP_EQUAL,
							servAttr));
		}

		// add by wml 2012-10-18
		if (!"".equals(platform))
		{
			searchor.getParams().add(
					new SearchParam("platform", RepositoryConstants.OP_LIKE,
							'%' + platform + '%'));
		}
		
		boolean isexistDevice = false;
		if (!deviceName.equals(""))
		{
			String devicenamen = PublicUtil
					.filterMbrandEspecialChar(deviceName);
			String temp = '%' + devicenamen + '%';
			SearchParam para = new SearchParam("fulldevicename",
					RepositoryConstants.OP_LIKE, temp);
			searchor.getParams().add(para);
			isexistDevice = true;// ��������Ͳ�ѯ
		}
		// ����ǳ������
		searchor.setIsRecursive(false);
		
		// ��ȡ������Ϣ
		Category category = (Category) Repository.getInstance().getNode(categoryID,
					RepositoryConstants.TYPE_CATEGORY);

		//��ȡ�����µ�����
		PageResult page = new PageResult(request);

		Taxis taxis = new Taxis();
		taxis.getParams().add(
				new TaxisParam("sortID", RepositoryConstants.ORDER_TYPE_DESC));
		taxis.getParams().add(
				new TaxisParam("marketDate", RepositoryConstants.ORDER_TYPE_DESC));

		list = CgyContentListBO.getInstance().getCgyContentListByExport(page, category, searchor, taxis,
				isExistedType,isexistDevice);
        
        generateQueryAppExcel(list, ws);
	}

	/**
	 * �������л�ȡ���������Ϊnull�ͷ��ؿ��ַ���""
	 * 
	 * @param request
	 *            http����
	 * @param key
	 *            �����Ĺؼ���
	 * @return ����ֵ
	 */
	protected String getParameter(HttpServletRequest request, String key)
	{
		String value = request.getParameter(key);
		if (value == null)
		{
			value = "";
		}
		return value;
	}

	/**
	 * ����Ӧ�ø��°汾��excel
	 * 
	 * @param list
	 * @param wwb
	 * @param sheetName
	 */
	private void generateQueryAppExcel(List list, WritableSheet ws)
	{

		Label label = null;
		int row = 0;
		int col = 0;
		try
		{
			label = new Label(col++, row, "����ID");
			ws.addCell(label);
			label = new Label(col++, row, "��������");
			ws.addCell(label);
			label = new Label(col++, row, "ҵ�����");
			ws.addCell(label);
			label = new Label(col++, row, "��������");
			ws.addCell(label);
			label = new Label(col++, row, "�ʷ�:��λ��");
			ws.addCell(label);
			label = new Label(col++, row, "�ṩ��");
			ws.addCell(label);
			label = new Label(col++, row, "��ҵ����");
			ws.addCell(label);
			label = new Label(col++, row, "�������");
			ws.addCell(label);
			label = new Label(col++, row, "����ʱ��");
			ws.addCell(label);

			for (int i = 0; i < list.size(); i++)
			{
				GContent vo = (GContent) list.get(i);
				col = 0;
				row++;

				label = new Label(col++, row, vo.getContentID());
				ws.addCell(label);
				label = new Label(col++, row, vo.getName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getCateName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getSubType());
				ws.addCell(label);
				label = new Label(col++, row, vo.getId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getSpName());
				ws.addCell(label);
				label = new Label(col++, row, vo.getIcpCode());
				ws.addCell(label);
				label = new Label(col++, row, vo.getIcpServId());
				ws.addCell(label);
				label = new Label(col++, row, vo.getLupdDate());
				ws.addCell(label);
			}
		}
		catch (RowsExceededException e)
		{
			e.printStackTrace();
		}
		catch (WriteException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
