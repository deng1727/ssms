package com.aspire.ponaadmin.webpps.mycontent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.webpps.mycontent.vo.MyContentVO;

public class MyContentDAO {

	private final static JLogger logger = LoggerFactory
			.getLogger(MyContentDAO.class);

	private static MyContentDAO instance = new MyContentDAO();

	private MyContentDAO() {

	}

	public static MyContentDAO getInstance() {
		return instance;
	}
	
	public void queryContentList(PageResult page,String companyId, String contentid,String name, String beginDate,String endDate,String catename,String keywords) {

		if (logger.isDebugEnabled()) {
			logger.debug("MyContentDAO queryContentList in...");
		}
		try {
			
			//select g.id,g.contentid,g.name,g.catename,g.marketdate,g.plupddate,g.introduction,g.keywords from t_r_gcontent g ,v_service v where 1=1  and v.icpservid=g.icpservid  and g.subtype in('1','2','7','11','12','13','14') %ANDSQLMORE% and g.companyid = ? order by plupddate desc nulls last
			String sql = DB.getInstance().getSQLByCode("com.aspire.ponaadmin.webpps.mycontent.dao.queryContentList.SELECT");
			//select count(g.id)  from t_r_gcontent g ,v_service v where 1=1  and v.icpservid=g.icpservid  and g.subtype in('1','2','7','11','12','13','14')  %ANDSQLMORE% and g.companyid = ?
		    String sqlCount = DB.getInstance().getSQLByCode("com.aspire.ponaadmin.webpps.mycontent.dao.queryContentList.SELECTCOUNT");
			// 构建更多的搜索条件
			StringBuffer sqlAndMore = new StringBuffer();

            List paras = new ArrayList();
            //构造搜索的sql和参数
			
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

			if (name != null && !"".equals(name.trim())) {
//				sqlAndMore.append(" and g.name like '%").append(name.trim())
//						.append("%'");
				
				sqlAndMore.append(" and g.name like ? ");
				paras.add("%"+SQLUtil.escape(name.trim())+"%");
			}
			
			if (catename != null && !"".equals(catename.trim())) {
//				sqlAndMore.append(" and g.catename = '").append(catename.trim())
//						.append("'");
				sqlAndMore.append(" and g.catename = ? ");
				paras.add(SQLUtil.escape(catename.trim()));
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
            paras.add(SQLUtil.escape(companyId));
            
			sql = PublicUtil
					.replace(sql, "%ANDSQLMORE%", sqlAndMore.toString());
			sqlCount = PublicUtil.replace(sqlCount, "%ANDSQLMORE%", sqlAndMore
					.toString());

			//page.excute(sql, sqlCount, null, new QueryAppVO());
			page.excute(sql, sqlCount, paras.toArray(), new MyContentPageVO());
		} catch (Exception e) {
			logger.error("全量商品应用查询失败dao", e);			
		}
	}
	
	/**
	 * 应用类分页读取VO的实现类
	 */
	private class MyContentPageVO implements PageVOInterface {

		public void CopyValFromResultSet(Object obj, ResultSet rs)
				throws SQLException {
			MyContentVO vo = (MyContentVO) obj;
			vo.setIntroduction(rs.getString("introduction"));		
			vo.setKeywords(rs.getString("keywords"));
			vo.setContentid(rs.getString("contentid"));
			vo.setName(rs.getString("name"));
			vo.setCatename(rs.getString("catename"));
			vo.setMarketdate(rs.getString("marketdate"));
			vo.setPlupddate(rs.getString("plupddate"));
			vo.setId(rs.getString("id"));
			
			try {
				String keywordsStr=rs.getString("keywords");
				keywordsStr=keywordsStr.replaceAll("\\{", "").replaceAll("}", "").replaceAll(";", "\n");
				vo.setKeywords(keywordsStr);
			} catch (Exception e) {
				
			}
		}
		
		public Object createObject() {

			return new MyContentVO();
		}
	}
}
