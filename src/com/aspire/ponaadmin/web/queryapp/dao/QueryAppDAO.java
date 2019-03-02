package com.aspire.ponaadmin.web.queryapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.queryapp.vo.QueryAppVO;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class QueryAppDAO {
	private static QueryAppDAO instance = new QueryAppDAO();
	protected static JLogger logger = LoggerFactory.getLogger(QueryAppDAO.class);
	private QueryAppDAO() {
	}

	/**
	 * 得到单例模式
	 * 
	 */
	public static QueryAppDAO getInstance() {
		return instance;
	}

	public void queryQueryAddList(PageResult page, String contentid,String name, String beginDate,String endDate,String icpcode,String developer,String spname,String syntype,String catename,String id,String servattr,String keywords) {

		if (logger.isDebugEnabled()) {
			logger.debug("QueryAppDAO queryQueryAddList in...");
		}
		try {
			
			String sql ="select g.id,g.contentid,g.name,g.icpcode,g.developer,g.spname,g.catename,g.marketdate,g.lupddate as plupddate,t.syntype,v.mobileprice,g.introduction,g.servattr,g.orderTimes,g.keywords from t_syn_result t,t_r_gcontent g ,v_service v where t.contentid = g.contentid  and v.icpservid=g.icpservid  and v.contentid=g.contentid  and g.subtype in('1','2','7','11','12','13','14') %ANDSQLMORE%  order by g.lupddate desc nulls last";
			String sqlCount ="select count(*)  from t_syn_result t,t_r_gcontent g ,v_service v where t.contentid = g.contentid  and v.icpservid=g.icpservid  and v.contentid=g.contentid   and g.subtype in('1','2','7','11','12','13','14') %ANDSQLMORE%";
			// 构建更多的搜索条件
			StringBuffer sqlAndMore = new StringBuffer();

            List paras = new ArrayList();
            //构造搜索的sql和参数
            
			if (servattr != null && !"".equals(servattr.trim())) {
//				sqlAndMore.append(" and g.servattr like '%").append(
//						servattr.trim()).append("%'");
				sqlAndMore.append(" and g.servattr like ? ");
				paras.add("%"+SQLUtil.escape(servattr.trim())+"%");
			}
			if (keywords != null && !"".equals(keywords.trim())) {
//				sqlAndMore.append(" and g.keywords like '%").append(
//						keywords.trim()).append("%'");
				
				sqlAndMore.append(" and g.keywords like ? ");
				paras.add("%"+SQLUtil.escape(keywords.trim())+"%");

			}
			
			if (contentid != null && !"".equals(contentid.trim())) {
//				sqlAndMore.append(" and g.contentid like '%").append(
//						contentid.trim()).append("%'");
				sqlAndMore.append(" and g.contentid like ? ");
				paras.add("%"+SQLUtil.escape(contentid.trim())+"%");

				
				
			}
			if (id != null && !"".equals(id.trim())) {
//				sqlAndMore.append(" and g.id like '%").append(
//						id.trim()).append("%'");
				
				sqlAndMore.append(" and g.id like  ? ");
				paras.add("%"+SQLUtil.escape(id.trim())+"%");
				
			}

			if (name != null && !"".equals(name.trim())) {
//				sqlAndMore.append(" and g.name like '%").append(name.trim())
//						.append("%'");
				
				sqlAndMore.append(" and g.name like ? ");
				paras.add("%"+SQLUtil.escape(name.trim())+"%");
			}
			
			if (icpcode != null && !"".equals(icpcode.trim())) {
//				sqlAndMore.append(" and g.icpcode like '%").append(icpcode.trim())
//						.append("%'");
				sqlAndMore.append(" and g.icpcode like ? ");
				paras.add("%"+SQLUtil.escape(icpcode.trim())+"%");
			}
			if (spname != null && !"".equals(spname.trim())) {
//				sqlAndMore.append(" and g.spname like '%").append(spname.trim())
//						.append("%'");
				sqlAndMore.append(" and g.spname like ? ");
				paras.add("%"+SQLUtil.escape(spname.trim())+"%");
			}
			
			if (developer != null && !"".equals(developer.trim())) {
				sqlAndMore.append(" and g.developer like ? ");
				paras.add("%"+SQLUtil.escape(developer.trim())+"%");
			}
			
			if (syntype != null && !"".equals(syntype.trim())) {
//				sqlAndMore.append(" and t.syntype = '").append(syntype.trim())
//						.append("'");
				
				sqlAndMore.append(" and t.syntype = ? ");
				paras.add(SQLUtil.escape(syntype.trim()));
				
			}
			else
			{
				sqlAndMore.append(" and t.syntype in(1,2)");
			}
			
			if (catename != null && !"".equals(catename.trim())) {
//				sqlAndMore.append(" and g.catename = '").append(catename.trim())
//						.append("'");
				
				sqlAndMore.append(" and g.catename = ? ");
				paras.add(SQLUtil.escape(catename.trim()));
				
			}
			else
			{
				sqlAndMore.append(" and g.catename in('软件','游戏','主题')");
			}
			
			//时间createTime
            if (beginDate != null && !"".equals(beginDate))
            {
                //sqlAndMore.append(" and substr(t.syntime,0,10) >= '").append(beginDate).append("'");
            	sqlAndMore.append(" and substr(t.syntime,0,10) >= ? ");
            	paras.add(SQLUtil.escape(beginDate));
            }
            if (endDate != null && !"".equals(endDate))
            {
                //sqlAndMore.append(" and substr(t.syntime,0,10) <= '").append(endDate).append("'");
            	 sqlAndMore.append(" and substr(t.syntime,0,10) <= ? ");
            	 paras.add(SQLUtil.escape(endDate));
            	 
            }
            
            if("".equals(beginDate)&&"".equals(endDate))
            {
            	sqlAndMore.append(" and substr(t.syntime,0,10) >= to_char(sysdate-2,'yyyy-mm-dd')");
            	sqlAndMore.append(" and substr(t.syntime,0,10) <= to_char(sysdate,'yyyy-mm-dd')");
            }


			sql = PublicUtil
					.replace(sql, "%ANDSQLMORE%", sqlAndMore.toString());
			sqlCount = PublicUtil.replace(sqlCount, "%ANDSQLMORE%", sqlAndMore
					.toString());

			//page.excute(sql, sqlCount, null, new QueryAppVO());
			logger.info(sql);
			page.excute(sql, sqlCount, paras.toArray(), new QueryAppVO());
		} catch (Exception e) {
			logger.error("每日新增应用查询失败dao", e);
			
		}
	}


	public void queryQueryContentList(PageResult page, String contentid,String name, String beginDate,String endDate,String icpcode,String developer,String spname,String catename,String id,String servattr,String keywords) {

		if (logger.isDebugEnabled()) {
			logger.debug("QueryAppDAO queryQueryContentList in...");
		}
		try {
			
			String sql ="select g.id,g.contentid,g.name,g.icpcode,g.developer,g.spname,g.catename,g.marketdate,g.lupddate as plupddate,v.mobileprice,g.introduction,g.servattr,g.orderTimes,g.keywords from t_r_gcontent g ,v_service v where 1=1  and v.icpservid=g.icpservid  and v.contentid=g.contentid  and g.subtype in('1','2','7','11','12','13','14') %ANDSQLMORE%  order by g.lupddate desc nulls last";
			String sqlCount ="select count(*)  from t_r_gcontent g ,v_service v where 1=1  and v.icpservid=g.icpservid  and v.contentid=g.contentid   and g.subtype in('1','2','7','11','12','13','14')  %ANDSQLMORE%";
			// 构建更多的搜索条件
			StringBuffer sqlAndMore = new StringBuffer();

            List paras = new ArrayList();
            //构造搜索的sql和参数
			
			if (servattr != null && !"".equals(servattr.trim())) {
//				sqlAndMore.append(" and g.servattr like '%").append(
//						servattr.trim()).append("%'");
				sqlAndMore.append(" and g.servattr like ? ");
				paras.add("%"+SQLUtil.escape(servattr.trim())+"%");

				
			}
			if (keywords != null && !"".equals(keywords.trim())) {
//				sqlAndMore.append(" and g.keywords like '%").append(
//						keywords.trim()).append("%'");
				sqlAndMore.append(" and g.keywords like ? ");
				paras.add("%"+SQLUtil.escape(keywords.trim())+"%");

			}
			
			if (contentid != null && !"".equals(contentid.trim())) {
//				sqlAndMore.append(" and g.contentid like '%").append(
//						contentid.trim()).append("%'");
				
				sqlAndMore.append(" and g.contentid like ? ");
				paras.add("%"+SQLUtil.escape(contentid.trim())+"%");

				
			}
			if (id != null && !"".equals(id.trim())) {
//				sqlAndMore.append(" and g.id like '%").append(
//						id.trim()).append("%'");
				
				sqlAndMore.append(" and g.id like ? ");
				paras.add("%"+SQLUtil.escape(id.trim())+"%");

				
			}

			if (name != null && !"".equals(name.trim())) {
//				sqlAndMore.append(" and g.name like '%").append(name.trim())
//						.append("%'");
				
				sqlAndMore.append(" and g.name like ? ");
				paras.add("%"+SQLUtil.escape(name.trim())+"%");

				
			}
			
			if (icpcode != null && !"".equals(icpcode.trim())) {
//				sqlAndMore.append(" and g.icpcode like '%").append(icpcode.trim())
//						.append("%'");
				
				sqlAndMore.append(" and g.icpcode like  ? ");
				paras.add("%"+SQLUtil.escape(icpcode.trim())+"%");
				
			}
			if (spname != null && !"".equals(spname.trim())) {
//				sqlAndMore.append(" and g.spname like '%").append(spname.trim())
//						.append("%'");
				
				sqlAndMore.append(" and g.spname like ? ");
				paras.add("%"+SQLUtil.escape(spname.trim())+"%");

			}
			
			if (developer != null && !"".equals(developer.trim())) {
				sqlAndMore.append(" and g.developer like ? ");
				paras.add("%"+SQLUtil.escape(developer.trim())+"%");
			}
			
			
			if (catename != null && !"".equals(catename.trim())) {
//				sqlAndMore.append(" and g.catename = '").append(catename.trim())
//						.append("'");
				sqlAndMore.append(" and g.catename = ? ");
				paras.add("%"+SQLUtil.escape(catename.trim())+"%");
			}
			else
			{
				sqlAndMore.append(" and g.catename in('软件','游戏','主题')");
			}
			
			//时间createTime
            if (beginDate != null && !"".equals(beginDate))
            {
                //sqlAndMore.append(" and substr(g.plupddate ,0,10) >= '").append(beginDate).append("'");
            	sqlAndMore.append(" and substr(g.plupddate ,0,10) >= ? ");
            	paras.add(SQLUtil.escape(beginDate));
            }
            if (endDate != null && !"".equals(endDate))
            {
                //sqlAndMore.append(" and substr(g.plupddate ,0,10) <= '").append(endDate).append("'");
            	sqlAndMore.append(" and substr(g.plupddate ,0,10) <= ? ");
            	paras.add(SQLUtil.escape(endDate));
            }


			sql = PublicUtil
					.replace(sql, "%ANDSQLMORE%", sqlAndMore.toString());
			sqlCount = PublicUtil.replace(sqlCount, "%ANDSQLMORE%", sqlAndMore
					.toString());

			//page.excute(sql, sqlCount, null, new QueryAppVO());
			page.excute(sql, sqlCount, paras.toArray(), new QueryAppVO());
		} catch (Exception e) {
			logger.error("全量商品应用查询失败dao", e);
			
		}
	}
	
	
	
	/**
     * 根据条件查找所有的每日新增应用信息
     * @param name
     * @param platformId
     * @param beginDate
     * @param endDate
     * @param state
     * @return
     */
    public List queryAppAddList(String contentid,String name, String beginDate,String endDate,String icpcode,String developer,String spname,String syntype,String catename,String id,String servattr,String keywords) {
        
        List list = new ArrayList();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try
        {
           // String sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            String sql="select g.id,g.contentid,g.name,g.icpcode,g.developer,g.spname,g.catename,g.marketdate,g.lupddate plupddate,t.syntype,v.mobileprice,g.introduction,g.servattr,g.orderTimes,g.keywords from t_syn_result t,t_r_gcontent g ,v_service v where t.contentid = g.contentid  and v.icpservid=g.icpservid  and v.contentid=g.contentid  and g.subtype in('1','2','7','11','12','13','14') %ANDSQLMORE%  order by g.lupddate desc nulls last";

         // 构建更多的搜索条件
			StringBuffer sqlAndMore = new StringBuffer();

            List paras = new ArrayList();
            //构造搜索的sql和参数
            
			if (servattr != null && !"".equals(servattr.trim())) {
//				sqlAndMore.append(" and g.servattr like '%").append(
//						servattr.trim()).append("%'");
				
				sqlAndMore.append(" and g.servattr like ? ");
				paras.add("%"+SQLUtil.escape(servattr.trim())+"%");
				
			}
			if (keywords != null && !"".equals(keywords.trim())) {
//				sqlAndMore.append(" and g.keywords like '%").append(
//						keywords.trim()).append("%'");
				
				sqlAndMore.append(" and g.keywords like ? ");
				paras.add("%"+SQLUtil.escape(keywords.trim())+"%");
				
			}
			
			if (contentid != null && !"".equals(contentid.trim())) {
//				sqlAndMore.append(" and g.contentid like '%").append(
//						contentid.trim()).append("%'");
				
				sqlAndMore.append(" and g.contentid like ? ");
				paras.add("%"+SQLUtil.escape(contentid.trim())+"%");
				
			}
			if (id != null && !"".equals(id.trim())) {
//				sqlAndMore.append(" and g.id like '%").append(
//						id.trim()).append("%'");
				
				sqlAndMore.append(" and g.id like ? ");
				paras.add("%"+SQLUtil.escape(id.trim())+"%");
				
			}

			if (name != null && !"".equals(name.trim())) {
//				sqlAndMore.append(" and g.name like '%").append(name.trim())
//						.append("%'");
				
				sqlAndMore.append(" and g.name like ? ");
				paras.add("%"+SQLUtil.escape(name.trim())+"%");
				
			}
			
			if (icpcode != null && !"".equals(icpcode.trim())) {
//				sqlAndMore.append(" and g.icpcode like '%").append(icpcode.trim())
//						.append("%'");
				
				sqlAndMore.append(" and g.icpcode like  ? ");
				paras.add("%"+SQLUtil.escape(icpcode.trim())+"%");
				
			}
			if (spname != null && !"".equals(spname.trim())) {
//				sqlAndMore.append(" and g.spname like '%").append(spname.trim())
//						.append("%'");
				
				sqlAndMore.append(" and g.spname like ? ");
				paras.add("%"+SQLUtil.escape(spname.trim())+"%");

				
			}
			if (developer != null && !"".equals(developer.trim())) {
				sqlAndMore.append(" and g.developer like ? ");
				paras.add("%"+SQLUtil.escape(developer.trim())+"%");
			}
			
			
			if (syntype != null && !"".equals(syntype.trim())) {
//				sqlAndMore.append(" and t.syntype = '").append(syntype.trim())
//						.append("'");
				
				sqlAndMore.append(" and t.syntype = ? ");
				paras.add(syntype.trim());
			}
			else
			{
				sqlAndMore.append(" and t.syntype in(1,2)");
			}
			
			if (catename != null && !"".equals(catename.trim())) {
//				sqlAndMore.append(" and g.catename = '").append(catename.trim())
//						.append("'");
				sqlAndMore.append(" and g.catename = ? ");
				paras.add(catename.trim());

			}
			else
			{
				sqlAndMore.append(" and g.catename in('软件','游戏','主题')");
			}
			
			//时间createTime
            if (beginDate != null && !"".equals(beginDate))
            {
                //sqlAndMore.append(" and substr(t.syntime,0,10) >= '").append(beginDate).append("'");
            	sqlAndMore.append(" and substr(t.syntime,0,10) >= ? ");
            	paras.add(beginDate);
            }
            if (endDate != null && !"".equals(endDate))
            {
                //sqlAndMore.append(" and substr(t.syntime,0,10) <= '").append(endDate).append("'");
                sqlAndMore.append(" and substr(t.syntime,0,10) <= ? ");
                paras.add(endDate);
            }
            
            if("".equals(beginDate)&&"".equals(endDate))
            {
            	sqlAndMore.append(" and substr(t.syntime,0,10) >= to_char(sysdate-2,'yyyy-mm-dd')");
            	sqlAndMore.append(" and substr(t.syntime,0,10) <= to_char(sysdate,'yyyy-mm-dd')");
            }

            sql = PublicUtil.replace(sql, "%ANDSQLMORE%", sqlAndMore.toString());

            conn = DB.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            for(int i=0;i<paras.size();i++){
            	pstmt.setString(i+1, (String)paras.get(i));
            	
            }
            rs = pstmt.executeQuery();
            while (rs.next())
            {
            	QueryAppVO vo = new QueryAppVO();
                vo.CopyValFromResultSet(vo, rs);
                list.add(vo);
            }
        }
        catch (Exception e)
        {
        }
        finally
        {
            DB.close(rs, pstmt, conn);
        }
        return list;
    }
	
    /**
     * 根据条件查找所有的每日新增应用信息
     * @param name
     * @param platformId
     * @param beginDate
     * @param endDate
     * @param state
     * @return
     */
    public List queryAppContentList(String contentid,String name, String beginDate,String endDate,String icpcode,String developer,String spname,String catename,String id,String servattr,String keywords) {
        
        List list = new ArrayList();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try
        {
           // String sql = SQLCode.getInstance().getSQLStatement(sqlCode);
            String sql="select g.id,g.contentid,g.name,g.icpcode,g.developer,g.spname,g.catename,g.marketdate,g.lupddate plupddate,v.mobileprice,g.introduction,g.servattr,g.orderTimes,g.keywords from t_r_gcontent g ,v_service v where 1=1  and v.icpservid=g.icpservid  and v.contentid=g.contentid   and g.subtype in('1','2','7','11','12','13','14') %ANDSQLMORE%  order by g.lupddate desc nulls last";

         // 构建更多的搜索条件
			StringBuffer sqlAndMore = new StringBuffer();
			
            List paras = new ArrayList();
            //构造搜索的sql和参数

			if (servattr != null && !"".equals(servattr.trim())) {
//				sqlAndMore.append(" and g.servattr like '%").append(
//						servattr.trim()).append("%'");
				
				sqlAndMore.append(" and g.servattr like ? ");
				paras.add("%"+SQLUtil.escape(servattr.trim())+"%");
				
			}
			if (keywords != null && !"".equals(keywords.trim())) {
//				sqlAndMore.append(" and g.keywords like '%").append(
//						keywords.trim()).append("%'");
				
				sqlAndMore.append(" and g.keywords like ? ");
				paras.add("%"+SQLUtil.escape(keywords.trim())+"%");
				
			}
			
			if (contentid != null && !"".equals(contentid.trim())) {
//				sqlAndMore.append(" and g.contentid like '%").append(
//						contentid.trim()).append("%'");
				
				sqlAndMore.append(" and g.contentid like ? ");
				paras.add("%"+SQLUtil.escape(contentid.trim())+"%");

				
			}
			if (id != null && !"".equals(id.trim())) {
//				sqlAndMore.append(" and g.id like '%").append(
//						id.trim()).append("%'");
				
				sqlAndMore.append(" and g.id like ? ");
				paras.add("%"+SQLUtil.escape(id.trim())+"%");

				
			}

			if (name != null && !"".equals(name.trim())) {
//				sqlAndMore.append(" and g.name like '%").append(name.trim())
//						.append("%'");
				sqlAndMore.append(" and g.name like ? ");
				paras.add("%"+SQLUtil.escape(name.trim())+"%");

			}
			
			if (icpcode != null && !"".equals(icpcode.trim())) {
//				sqlAndMore.append(" and g.icpcode like '%").append(icpcode.trim())
//						.append("%'");
				
				sqlAndMore.append(" and g.icpcode like  ? ");
				paras.add("%"+SQLUtil.escape(icpcode.trim())+"%");

				
			}
			if (spname != null && !"".equals(spname.trim())) {
//				sqlAndMore.append(" and g.spname like '%").append(spname.trim())
//						.append("%'");
				
				sqlAndMore.append(" and g.spname like ? ");
				paras.add("%"+SQLUtil.escape(spname.trim())+"%");

				
			}
			if (developer != null && !"".equals(developer.trim())) {
				sqlAndMore.append(" and g.developer like ? ");
				paras.add("%"+SQLUtil.escape(developer.trim())+"%");
			}
			
			
			if (catename != null && !"".equals(catename.trim())) {
//				sqlAndMore.append(" and g.catename = '").append(catename.trim())
//						.append("'");
				
				sqlAndMore.append(" and g.catename = ? ");
				paras.add("%"+SQLUtil.escape(catename.trim())+"%");

				
			}
			else
			{
				sqlAndMore.append(" and g.catename in('软件','游戏','主题')");
			}
			
			//时间createTime
            if (beginDate != null && !"".equals(beginDate))
            {
                //sqlAndMore.append(" and substr(g.plupddate ,0,10) >= '").append(beginDate).append("'");
                sqlAndMore.append(" and substr(g.lupddate ,0,10) >= ? ");
                paras.add(beginDate);
            }
            if (endDate != null && !"".equals(endDate))
            {
                //sqlAndMore.append(" and substr(g.plupddate ,0,10) <= '").append(endDate).append("'");
            	sqlAndMore.append(" and substr(g.lupddate ,0,10) <= ? ");
            	paras.add(endDate);
            }

            sql = PublicUtil.replace(sql, "%ANDSQLMORE%", sqlAndMore.toString());

            conn = DB.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            for(int i=0;i<paras.size();i++){
            	pstmt.setString(i+1, (String)paras.get(i));
            	
            }
            
            rs = pstmt.executeQuery();
            while (rs.next())
            {
            	QueryAppVO vo = new QueryAppVO();
                vo.CopyValFromResultSet(vo, rs);
                list.add(vo);
            }
        }
        catch (Exception e)
        {
        }
        finally
        {
            DB.close(rs, pstmt, conn);
        }
        return list;
    }
	
	
	
}
