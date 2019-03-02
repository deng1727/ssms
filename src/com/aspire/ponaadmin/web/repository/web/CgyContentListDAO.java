package com.aspire.ponaadmin.web.repository.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GAppContent;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.util.PublicUtil;
/**
 * 
 * @author zhangwei
 *
 */
public class CgyContentListDAO
{
	private static CgyContentListDAO DAO=new CgyContentListDAO();
	
	private static final JLogger LOG = LoggerFactory.getLogger(CgyContentListDAO.class) ;
	
	public static CgyContentListDAO getInstance()
	{
		return DAO;
	}
	
	
	/**
	 * ��ȡ��ǰ����/�ǵ�ǰ�����·�ҳ����Ʒ�����ݡ�
	 * @param page ��ҳ����
	 * @param category ����ѯ�Ļ���
	 *  @param parasMap ��ѯ����
	 * @param isExistedType boolean �Ƿ�������������type���ͣ�����еĻ������⴦��
	 * @param isIncluded boolean �Ƿ�������ػ��ܵ���Ʒ��true��ʾ֮��ѯ�û��ܵ���Ʒ���ݣ�false��ʾ��ѯ�Ǹû��ܵ���Ʒ����
	 * @param isExistedDevice �Ƿ�������Ͳ�ѯ
	 * @param aprovalStatus
	 *           ������Ʒ״̬ 0���༭ 1���ѷ��� 2��������ͨ�� 3��������
	 */
	public void getCgyContentList( PageResult page,Category category, Searchor searchor,Taxis taxis,boolean isExistedType,boolean isExistedDevice,String aprovalStatus)throws DAOException
	{
		 String sql;
		// ����������������Ҫ����t_r_base��
		if (isExistedType)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("��ʼ��ѯ����Ϊ��" + category.getName() + ",�µ���Ʒ����ѯ�������������ֶ�");
			}
			if (isExistedDevice)
			{//��������  ����v_cm_device_resource ������ѯ�ٶ�
				sql = DB
				.getInstance()
				.getSQLByCode(
						//"ponaadmin.web.repository.web.CgyContentListDAO.existedTypeExistedDevice.SELECT");
						  "ponaadmin.web.repository.web.CgyContentListDAO.existedTypeExistedDevice.SELECT_Export");
			}
			else
			{
				sql = DB
						.getInstance()
						.getSQLByCode(
								//"ponaadmin.web.repository.web.CgyContentListDAO.existedType.SELECT");
								  "ponaadmin.web.repository.web.CgyContentListDAO.existedType.SELECT_Export");
			}
		}
		else
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("��ʼ��ѯ����Ϊ��" + category.getName() + ",�µ���Ʒ����ѯ���������������ֶ�");
			}
			if (isExistedDevice)
			{//�������� ����v_cm_device_resource ������ѯ�ٶ�
				sql = DB
				.getInstance()
				.getSQLByCode(
						//"ponaadmin.web.repository.web.CgyContentListDAO.notExistedTypeExistedDevice.SELECT");
						  "ponaadmin.web.repository.web.CgyContentListDAO.notExistedTypeExistedDevice.SELECT_Export");
			}
			else
			{
				sql = DB
				.getInstance()
				.getSQLByCode(
						//"ponaadmin.web.repository.web.CgyContentListDAO.notExistedType.SELECT");
						  "ponaadmin.web.repository.web.CgyContentListDAO.notExistedType.SELECT_Export");
			}
			
		}
	    StringBuffer bf=new StringBuffer(sql);
	    List paraList=new ArrayList();
	    paraList.add(category.getCategoryID());//��һ��������
	    //���ò�ѯ������
	    setSearchSQL(bf, paraList, searchor,aprovalStatus);
	    //�������������
	    setTaxisSQL(bf,taxis);
	    //��ѯ�������µ����� page�����Ľڵ�����Ϊ��nt:reference
	    page.excute(bf.toString(), paraList.toArray(), new AppContentPageTempVO());
	    
		
	}

	/**
	 * ��ȡ��ǰ����/�ǵ�ǰ�����·�ҳ����Ʒ�����ݡ�
	 * @param page ��ҳ����
	 * @param category ����ѯ�Ļ���
	 *  @param parasMap ��ѯ����
	 * @param isExistedType boolean �Ƿ�������������type���ͣ�����еĻ������⴦��
	 * @param isIncluded boolean �Ƿ�������ػ��ܵ���Ʒ��true��ʾ֮��ѯ�û��ܵ���Ʒ���ݣ�false��ʾ��ѯ�Ǹû��ܵ���Ʒ����
	 */
	public void getCgyContentList( PageResult page,Category category, Searchor searchor,Taxis taxis,boolean isExistedType)throws DAOException
	{
		 String sql;
		// ����������������Ҫ����t_r_base��
		if (isExistedType)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("��ʼ��ѯ����Ϊ��" + category.getName() + ",�µ���Ʒ����ѯ�������������ֶ�");
			}
			sql = DB.getInstance().getSQLByCode(
					"ponaadmin.web.repository.web.CgyContentListDAO.existedType.SELECT");
		}
		else
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("��ʼ��ѯ����Ϊ��" + category.getName() + ",�µ���Ʒ����ѯ���������������ֶ�");
			}
			sql = DB
					.getInstance()
					.getSQLByCode(
							"ponaadmin.web.repository.web.CgyContentListDAO.notExistedType.SELECT");
		}
	    StringBuffer bf=new StringBuffer(sql);
	    List paraList=new ArrayList();
	    paraList.add(category.getCategoryID());//��һ��������
	    //���ò�ѯ������
	    setSearchSQL(bf, paraList, searchor,null);
	    //�������������
	    setTaxisSQL(bf,taxis);
	    //��ѯ�������µ����� page�����Ľڵ�����Ϊ��nt:reference
	    page.excute(bf.toString(), paraList.toArray(), new AppContentPageVO());
	    
		
	}
	/**
	 * ��ȡ�ǵ�ǰ�����·�ҳ����Ʒ�����ݡ����categoryid�Ǹ�����id�����ʾ��ѯδ������Դ��
	 * @param page ��ҳ����
	 * @param category ����ѯ�Ļ���
	 *  @param parasMap ��ѯ����
	 * @param isExistedType boolean �Ƿ�������������type���ͣ�����еĻ������⴦��
	 * @param isIncluded boolean �Ƿ�������ػ��ܵ���Ʒ��true��ʾ֮��ѯ�û��ܵ���Ʒ���ݣ�false��ʾ��ѯ�Ǹû��ܵ���Ʒ����
	 */
	public void getCgyNotContentList( PageResult page,Category category, Searchor searchor,Taxis taxis,boolean isExistedType)throws DAOException
	{
		String sql;
		List paraList = new ArrayList();
		if(isExistedType)
		{
			if(LOG.isDebugEnabled())
			{
				LOG.debug("��ʼ��ѯ�����ڻ���Ϊ��"+category.getName()+",�µ����ݣ���ѯ���������������ֶ�");
			}
			sql =DB.getInstance().getSQLByCode("ponaadmin.web.repository.web.CgyContentListDAO.notCgy.existedType.SELECT");
		}else
		{
			if(LOG.isDebugEnabled())
			{
				LOG.debug("��ʼ��ѯ�����ڻ���Ϊ��"+category.getName()+",�µ����ݣ���ѯ���������������ֶ�");
			}
			sql =DB.getInstance().getSQLByCode("ponaadmin.web.repository.web.CgyContentListDAO.notCgy.notExistedType.SELECT");
		}
		if(RepositoryConstants.ROOT_CATEGORY_ID.equals(category.getId()))//��ѯδ������Դ����Ҫsql�е� and r.categoryid=? ȥ����Ϊ�˼򵥴���ֻ�ܺ�sql�����
		{
			sql=PublicUtil.replace(sql, "and r.categoryid=?", "");
		}else
		{
			paraList.add(category.getCategoryID());// ��һ��������
		}
		StringBuffer bf = new StringBuffer(sql);
		// ���ò�ѯ������
		setSearchSQL(bf, paraList, searchor,null);
		// �������������
		setTaxisSQL(bf, taxis);
		page.excute(bf.toString(), paraList.toArray(), new NotCgyContentPageVO());
	}
	/**
	 * Ӧ�����ҳ��ȡVO��ʵ����
	 * 
	 * @author zhangwei
	 * 
	 */
	private class AppContentPageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object vo, ResultSet rs) throws SQLException
		{
			ReferenceNode refNode=(ReferenceNode)vo;
			setReferenceValues(refNode,rs);
			GAppContent app=new GAppContent();
			setGAppContentValues(app,rs);
			refNode.setRefNode(app);
		}

		public Object createObject()
		{
			// TODO Auto-generated method stub
			return new ReferenceNode();
		}
	}
	
	/**
	 * Ӧ�����ҳ��ȡVO��ʵ����
	 * 
	 * @author zhangwei
	 * 
	 */
	private class AppContentPageTempVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object vo, ResultSet rs) throws SQLException
		{
			ReferenceNode refNode=(ReferenceNode)vo;
			setReferenceValues(refNode,rs);
			GAppContent app=new GAppContent();
			setGAppContentValues(app,rs);
			app.setAdvertPic(String.valueOf(rs.getInt("mobileprice")/10));
			refNode.setRefNode(app);
		}

		public Object createObject()
		{
			// TODO Auto-generated method stub
			return new ReferenceNode();
		}
	}
	
	/**
	 * Ӧ�����ҳ��ȡVO��ʵ����
	 * @author zhangwei
	 *
	 */
	private class NotCgyContentPageVO implements PageVOInterface
	{
		public void CopyValFromResultSet(Object vo, ResultSet rs) throws SQLException
		{
			GContent content=(GContent)vo;
			setGContentValues(content, rs);
			content.setAdvertPic(String.valueOf(rs.getInt("mobileprice")/10));
		}

		public Object createObject()
		{
			return new GContent();
		}
		
	}
	/**
	 * ������Ʒ�����Ϣ��
	 * @param content
	 * @param rs
	 * @throws SQLException 
	 */
	private void setReferenceValues(ReferenceNode refNode,ResultSet rs) throws SQLException
	{
		//refNode.setId("")��Ϊ�����ܣ��ٴ�ֻ��ȡt_r_reference ������Ʒ����Ϣ��
		//t_r_base ���е���Ʒ��Ϣ��ʱ�����õ��������ڴ˲���ȥȡ�ˡ�
		refNode.setId(rs.getString("referenceid"));//��Ʒ����id��Ϊ���������ݵ�id��ȡ�˸�������
		refNode.setGoodsID(rs.getString("goodsid"));
		refNode.setCategoryID(rs.getString("categoryid"));
		refNode.setSortID(rs.getInt("sortid"));
		refNode.setLoadDate("LoadDate");
		refNode.setRefNodeID("id");
		refNode.setVerifyStatus(rs.getString("verifyStatus"));
		refNode.setDelFlag(rs.getString("delFlag"));
		refNode.setAppId(rs.getString("appId"));
	}
	/**
	 * ���û������ݱ����Ϣ��Ŀǰֻ��Ҫ��д�б�����Ҫ�ļ���
	 * @param content
	 * @param rs
	 */
	private void setGContentValues(GContent content,ResultSet rs)throws SQLException
	{
		content.setType(null);//ȥ�����͡�
		content.setId(rs.getString("id"));
		content.setName(rs.getString("name"));
        content.setCateName(rs.getString("cateName"));
        content.setSpName(rs.getString("spName"));
        content.setIcpCode(rs.getString("icpCode"));
        content.setIcpServId(rs.getString("icpServId"));
        content.setContentID(rs.getString("contentID"));
        content.setMarketDate(rs.getString("marketdate"));
        content.setKeywords(rs.getString("keywords"));
        content.setLupdDate(rs.getString("lupddate"));
        content.setServAttr(rs.getString("servAttr"));
        content.setSubType(rs.getString("subType"));
        content.setAppId(rs.getString("appId"));
	}
	/**
	 * ����Ӧ�õ����͵���Ϣ��
	 * @param content
	 * @param rs
	 */
	private void setGAppContentValues(GAppContent content,ResultSet rs)throws SQLException
	{
		setGContentValues(content,rs);
		//�Ժ���Ҫ��ӡ�
	}
	private void setSearchSQL(StringBuffer sql, List paraList,Searchor searchor,String aprovalStatus)
	{
		 // �������ʽ�ĸ������������д���
        for (int i = 0; i < searchor.getParams().size(); i++)
        {
            SearchParam param = ( SearchParam ) searchor.getParams().get(i);
            sql.append(" ");
            sql.append(param.getMode());
            sql.append(" ");
            // ����������ſ�ʼ��Ҫ���һ��(
            if (param.getBracket()
                     .equals(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT))
            {
                sql.append(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
            }
            // �����ֶζ�Ӧ�����ݿ���ֶ�
            if(param.getOperator().equals(RepositoryConstants.OP_LIKE_IgnoreCase))
            {
            	sql.append("upper(");
            	sql.append(param.getProperty());
                sql.append(") like ");
                sql.append("?");
            	
            }else
            {
            	sql.append(param.getProperty());
                sql.append(' ');
                sql.append(param.getOperator());
                sql.append(' ');
                sql.append('?');
            }
            
            // ����������Ž�����Ҫ���һ��)
            if (param.getBracket()
                     .equals(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT))
            {
                sql.append(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);
            }
            
            paraList.add(param.getValue());
        }
        if(aprovalStatus != null && !"".equals(aprovalStatus) && !"4".equals(aprovalStatus)){
        	String[] strs = aprovalStatus.split(",");
        	if(strs.length > 1){
        		sql.append(" and r.verify_status in ('" + strs[0] + "','"+strs[1]+"')");
        	}else{
        		sql.append(" and r.verify_status = '" +strs[0]+"'");
        	}
	    	
	    }
	}
	 /**
     * ���ò�ѯʱ���������
     *
     * @param sql StringBuffer��sql�����StringBuffer
     * @param taxis Taxis��������ʽ��
     */
    private void setTaxisSQL(StringBuffer sql, Taxis taxis)
    {

        // ���û�����������ֱ�ӷ���
        if ((taxis == null) || (taxis.getParams().size() == 0))
        {
            return;
        }

        sql.append(" order by ");

        for (int i = 0; i < taxis.getParams().size(); i++)
        {
            TaxisParam param = ( TaxisParam ) taxis.getParams().get(i);
            String row = param.getProperty();
            if (i > 0)
            {
                sql.append(',');
            }
            sql.append(row);
            sql.append(' ');
            sql.append(param.getOrder());
        }
    }

    
    /**
	 * ��ȡ��ǰ����/�ǵ�ǰ�����·�ҳ����Ʒ�����ݡ�
	 * 
	 * @param page
	 *            ��ҳ����
	 * @param category
	 *            ����ѯ�Ļ���
	 * @param parasMap
	 *            ��ѯ����
	 * @param isExistedType
	 *            boolean �Ƿ�������������type���ͣ�����еĻ������⴦��
	 * @param isIncluded
	 *            boolean �Ƿ�������ػ��ܵ���Ʒ��true��ʾ֮��ѯ�û��ܵ���Ʒ���ݣ�false��ʾ��ѯ�Ǹû��ܵ���Ʒ����
	 * @param isExistedDevice
	 *            �Ƿ�������Ͳ�ѯ
	 */
	public List getCgyContentListByExport(PageResult page, Category category,
			Searchor searchor, Taxis taxis, boolean isExistedType,
			boolean isExistedDevice) throws DAOException
	{
		List list = new ArrayList();
		ResultSet rs = null;
		String sql;
		// ����������������Ҫ����t_r_base��
		if (isExistedType)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("��ʼ��ѯ����Ϊ��" + category.getName() + ",�µ���Ʒ����ѯ�������������ֶ�");
			}
			if (isExistedDevice)
			{
				// �������� ����v_cm_device_resource ������ѯ�ٶ�
				sql = DB
						.getInstance()
						.getSQLByCode(
								"ponaadmin.web.repository.web.CgyContentListDAO.existedTypeExistedDevice.SELECT_Export");
			}
			else
			{
				sql = DB
						.getInstance()
						.getSQLByCode(
								"ponaadmin.web.repository.web.CgyContentListDAO.existedType.SELECT_Export");
			}
		}
		else
		{
			if (LOG.isDebugEnabled())
			{
				LOG
						.debug("��ʼ��ѯ����Ϊ��" + category.getName()
								+ ",�µ���Ʒ����ѯ���������������ֶ�");
			}
			if (isExistedDevice)
			{
				// �������� ����v_cm_device_resource ������ѯ�ٶ�
				sql = DB
						.getInstance()
						.getSQLByCode(
								"ponaadmin.web.repository.web.CgyContentListDAO.notExistedTypeExistedDevice.SELECT_Export");
			}
			else
			{
				sql = DB
						.getInstance()
						.getSQLByCode(
								"ponaadmin.web.repository.web.CgyContentListDAO.notExistedType.SELECT_Export");
			}

		}
		StringBuffer bf = new StringBuffer(sql);
		List paraList = new ArrayList();
		paraList.add(category.getCategoryID());// ��һ��������
		// ���ò�ѯ������
		setSearchSQL(bf, paraList, searchor,null);
		// �������������
		setTaxisSQL(bf, taxis);

		try
		{
			rs = DB.getInstance().query(bf.toString(), paraList.toArray());

			while (rs.next())
			{
				GContent content = new GContent();

				content.setType(null);//ȥ�����͡�
				content.setId(String.valueOf(rs.getInt("mobileprice")));
				content.setName(rs.getString("name"));
				content.setCateName(rs.getString("appcatename"));
				content.setSpName(rs.getString("spName"));
				content.setIcpCode(rs.getString("icpCode"));
				content.setIcpServId(rs.getString("icpServId"));
				content.setContentID(rs.getString("contentID"));
				content.setMarketDate(rs.getString("marketdate"));
				content.setKeywords(rs.getString("keywords"));
				content.setLupdDate(rs.getString("lupddate"));
				content.setServAttr(rs.getString("servAttr"));
				content.setSubType(rs.getString("subType"));
				content.setAppId(rs.getString("appId"));
				list.add(content);
			}
		}
		catch (Exception e)
		{
			throw new DAOException("��ȡ��ǰ����/�ǵ�ǰ�����·�ҳ����Ʒ������ʱ�����쳣:", e);
		}

		return list;
	}
}
