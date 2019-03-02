package com.aspire.dotcard.basecomic.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.basecomic.common.Const;
import com.aspire.dotcard.basecomic.common.StatisticsCallback;
import com.aspire.dotcard.basecomic.vo.AdapterVO;
import com.aspire.dotcard.basecomic.vo.BrandVO;
import com.aspire.dotcard.basecomic.vo.CPVO;
import com.aspire.dotcard.basecomic.vo.CategoryVO;
import com.aspire.dotcard.basecomic.vo.ChapterVO;
import com.aspire.dotcard.basecomic.vo.ComicSeriesVO;
import com.aspire.dotcard.basecomic.vo.DeviceGroupItemVO;
import com.aspire.dotcard.basecomic.vo.DeviceGroupVO;
import com.aspire.dotcard.basecomic.vo.InfoVO;
import com.aspire.dotcard.basecomic.vo.ReferenceVO;
import com.aspire.dotcard.basecomic.vo.StatisticsVO;
import com.aspire.dotcard.basecomic.vo.TVSeriesVO;
import com.aspire.dotcard.basecomic.vo.ThemeVO;
import com.aspire.dotcard.basecomic.vo.TopicVO;
import com.aspire.ponaadmin.web.db.TransactionDB;

/**
 * @author aiyan
 * 
 */
public class BaseComicDBOpration {

	/**
	 * 日志引用
	 * 
	 */
	private static JLogger logger = LoggerFactory
			.getLogger(BaseComicDBOpration.class);

	private StatisticsCallback statisticsCallback;
//	private TransactionDB tdb;

	public BaseComicDBOpration() {
	}

	public BaseComicDBOpration(StatisticsCallback statisticsCallback) {
		this.statisticsCallback = statisticsCallback;
	}

//	public BaseComicDBOpration(TransactionDB tdb) {
//		this.tdb = tdb;
//	}
	
	public void insertTopic(TopicVO vo){
		String[] para = new String[] { vo.getName(),vo.getTopicId(),vo.getParentTopicId(),Const.NAME_TOPIC};

//		String sql = "insert into t_cb_category(categoryId,categoryName,categoryValue,delFlag," +
//				"createTime,lupdate,parentCategoryValue,type) " +
//				"values(seq_cb_category_id.nextval,?,?,0,sysdate,sysdate,?,?)";
		
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.insertTopic.INSERT";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("insertTopic出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}
		
	}

	public void updateTopic(TopicVO vo){
		String[] para = new String[] { vo.getName(),vo.getTopicId(), vo.getParentTopicId(),Const.NAME_TOPIC};

//		String sql = "update t_cb_category set categoryName=?" +
//				" where categoryValue=? and parentCategoryValue=? and type = ?";
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateTopic.UPDATE";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("updateBrand出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}
		
	}
	//货架添加；
	public String  addCategory(CategoryVO vo) throws Exception {
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.addCategory.SELECT";
		String categoryId = BaseComicDAO.getInstance().queryOne(sqlCode,null);
		String[] para = null;
		para = new String[] {categoryId,vo.getCategoryName(),
				vo.getCategoryValue(),vo.getParentCategoryId(), vo.getType(), vo.getPictrue(), vo.getCategoryDesc(), vo.getSortid()};

		
		
//		String sql = "insert into t_cb_category(categoryid,categoryname,categoryvalue,parentcategoryid,type,picture,"
//				+ "delflag,categorydesc,sortid,createtime,lupdate)" +
//						"values(?,?,?,?,?,?,?,?,?,sysdate,sysdate)";
		
		sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.addCategory.INSERT";
		
		
		try {
//			if (tdb != null) {
//				tdb.execute(sql, para);
//			} else {
				BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			//}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("addCategory出错：", e);
			throw e;
		}
		
		return categoryId;

	}

	//货架修改；
	public void updateCategory(CategoryVO vo) {
			String[] para = new String[] { vo.getCategoryName(), vo.getCategoryId() };
			//String sql = "update t_cb_category set categoryname=?,lupdate=sysdate where categoryid=?";
			String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateCategory.UPDATE";
			try {
//				if (tdb != null) {
//					tdb.execute(sql, para);
//				} else {
					BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
				//}
			} catch (BOException e) {
				// TODO Auto-generated catch block
				logger.error("addCategory出错：", e);
			} 


	}

	//商品添加；
	public void addReference(ReferenceVO vo) throws Exception {
			String[] para = new String[] { vo.getCategoryId(),vo.getContentId(), vo.getSortid(),vo.getType(),vo.getPortal()};
			//String sql = "insert into t_cb_reference(id,categoryid,contentid,sortid,type,portal,flow_time)values(seq_cb_id.nextval,?,?,?,?,?,sysdate)";
			String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.addReference.INSERT";
//			if (tdb != null) {
//				tdb.execute(sql, para);
//			} else {
				BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			//}
	}


	//商品修改；
	public void updateReference(ReferenceVO vo) {
			String[] para = new String[] { vo.getSortid(),vo.getPortal(),vo.getCategoryId(),vo.getContentId()};
			//String sql = "update t_cb_reference set sortid=?,portal=?,flow_time=sysdate where categoryid=? and contentid=?";
			String sqlCode ="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateReference.UPDATE";
			try {
//				if (tdb != null) {
//					tdb.execute(sql, para);
//				} else {
					BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
				//}
			} catch (BOException e) {
				// TODO Auto-generated catch block
				logger.error("updateReference出错：", e);
			} 

	}
	
	public void insertInfo(InfoVO vo) {
		String[] para = new String[] { vo.getId(), vo.getName(),
				vo.getDescription(), vo.getProvider(), vo.getProviderType(),
				vo.getAuthodid(), vo.getType(), vo.getKeywords(),
				vo.getExpireTime(), vo.getLocation(),
				vo.getFirst(), vo.getUrl1(), vo.getUrl2(), vo.getUrl3(),
				vo.getUrl4(), vo.getInfoContent(),vo.getInfoPic(),vo.getInfoSource(),
				vo.getCreateTime(),vo.getClassify(),vo.getPortal(),vo.getBaseType()};

//		String sql = "insert into t_cb_content(id,name,description,provider,provider_type,authodid,type,keywords,expireTime,"
//				+ "location,first,url1,url2,url3,"
//				+ "url4,info_content,info_pic,info_source,createTime,classify,portal,flow_time) "
//				+ "values(?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,?,  ?,sysdate)";
		String sqlCode ="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.insertInfo.INSERT";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode,para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("insertInfo出错：", e);
			
			try {
				String sql = SQLCode.getInstance().getSQLStatement(sqlCode);
				logger.error("出错的执行语句:"+sql);
				StringBuffer sb = new StringBuffer();
				for(int i=0;i<para.length;i++){
					sb.append(",").append(para[i]);
					
				}
				if(sb.length()>0){
					logger.error("para:"+sb.substring(1));
				}else{
					logger.error("para:"+sb.toString());
				}
				
			} catch (DataAccessException e1) {
				// TODO Auto-generated catch block
				logger.error(e1);
			}
			
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}

	}
	
	public void updateInfo(InfoVO vo) {
		String[] para = new String[] { vo.getId(), vo.getName(),
				vo.getDescription(), vo.getProvider(), vo.getProviderType(),
				vo.getAuthodid(), vo.getType(), vo.getKeywords(),
				vo.getExpireTime(), vo.getLocation(),
				vo.getFirst(), vo.getUrl1(), vo.getUrl2(), vo.getUrl3(),
				vo.getUrl4(),vo.getInfoContent(),vo.getInfoPic(),vo.getInfoSource(),
				vo.getCreateTime(),vo.getClassify(),vo.getBaseType(),
				vo.getId() };
//		String sql = "update t_cb_content set id=?,name=?,description=?,provider=?,provider_type=?,authodid=?,type=?,"
//				+ "keywords=?,expireTime=?,location=?,first=?,url1=?,url2=?,url3=?,"
//				+ "url4=?,info_content=?,info_pic=?,info_source=?,createTime=?,classify=?,flow_time=sysdate where id=? ";
		
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateInfo.UPDATE";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("updateInfo出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}

	}

	

	/**
	 * 新增
	 * 
	 * @param vo
	 * @throws BOException 
	 * @throws Exception
	 */
	public void insertComicSeries(ComicSeriesVO vo) {
		String[] para = new String[] { vo.getId(), vo.getName(),
				vo.getDescription(), vo.getProvider(), vo.getProviderType(),
				vo.getAuthodid(), vo.getType(), vo.getKeywords(),
				vo.getExpireTime(), vo.getFee(), vo.getLocation(),
				vo.getFirst(), vo.getUrl1(), vo.getUrl2(), vo.getUrl3(),
				vo.getUrl4(), vo.getFeeCode(), vo.getDetailUrl1(),
				vo.getDetailUrl2(), vo.getDetailUrl3(), vo.getBookNum(),
				vo.getClassify(), vo.getBookType(), vo.getBookStyle(),
				vo.getBookColor(), vo.getArea(), vo.getLanguage(),
				vo.getYear(), vo.getStatus(), vo.getChapterType(),vo.getUserType(),
				vo.getPortal(), 
				vo.getBusinessid(), vo.getCreateTime(),vo.getBaseType(),vo.getEbookUrl() };

//		String sql = "insert into t_cb_content(id,name,description,provider,provider_type,authodid,type,keywords,expireTime,"
//				+ "fee,location,first,url1,url2,url3,"
//				+ "url4,fee_code,detail_url1,detail_url2,detail_url3,book_num,classify,book_type,book_style,book_color,area,language,year,status,"
//				+ "chapter_type,user_type,portal,businessid,createTime,flow_time) "
//				+ "values(?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,?, ?,  ?,?,?,sysdate)";
		String sqlCode ="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.insertComicSeries.INSERT";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode,para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("insertComicSeries出错：", e);
			
			try {
				String sql = SQLCode.getInstance().getSQLStatement(sqlCode);
				logger.error("出错的执行语句:"+sql);
				StringBuffer sb = new StringBuffer();
				for(int i=0;i<para.length;i++){
					sb.append(",").append(para[i]);
					
				}
				if(sb.length()>0){
					logger.error("para:"+sb.substring(1));
				}else{
					logger.error("para:"+sb.toString());
				}
				
			} catch (DataAccessException e1) {
				// TODO Auto-generated catch block
				logger.error(e1);
			}
			
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}

	}

	/**
	 * 修改
	 * 
	 * @param vo
	 * @throws  
	 * @throws BOException 
	 * @throws Exception
	 */
	public void updateComicSeries(ComicSeriesVO vo) {
		String[] para = new String[] { vo.getId(), vo.getName(),
				vo.getDescription(), vo.getProvider(), vo.getProviderType(),
				vo.getAuthodid(), vo.getType(), vo.getKeywords(),
				vo.getExpireTime(), vo.getFee(), vo.getLocation(),
				vo.getFirst(), vo.getUrl1(), vo.getUrl2(), vo.getUrl3(),
				vo.getUrl4(), vo.getFeeCode(), vo.getDetailUrl1(),
				vo.getDetailUrl2(), vo.getDetailUrl3(), vo.getBookNum(),
				vo.getClassify(), vo.getBookType(), vo.getBookStyle(),
				vo.getBookColor(), vo.getArea(), vo.getLanguage(),
				vo.getYear(), vo.getStatus(), vo.getChapterType(),vo.getUserType(),
				vo.getPortal(), 
				vo.getBusinessid(), vo.getCreateTime(),vo.getBaseType(), vo.getEbookUrl(),
				vo.getId() };
//		String sql = "update t_cb_content set id=?,name=?,description=?,provider=?,provider_type=?,authodid=?,type=?,"
//				+ "keywords=?,expireTime=?,fee=?,location=?,first=?,url1=?,url2=?,url3=?,"
//				+ "url4=?,fee_code=?,detail_url1=?,detail_url2=?,detail_url3=?,book_num=?,classify=?,"
//				+ "book_type=?,book_style=?,book_color=?,area=?,language=?,year=?,status=?,"
//				+ "chapter_type=?,user_type=?,portal=?,businessid=?,createTime=?,flow_time=sysdate where id=? ";
		
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateComicSeries.UPDATE";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("updateComicSeries出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}

	}

	public void addComicSeriesReference() {
		String[] para = new String[]{Const.CATEGROY_COMICSERIES};
		//String sql = "select c.categoryid,c.categoryvalue from t_cb_category c where c.parentcategoryid=? order by c.categoryvalue asc";
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.addComicSeriesReference.SELECT";
		
		try {
			RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, para);
			while (rs.next()) {
				String categoryid = rs.getString("categoryid");
				String categoryvalue = rs.getString("categoryvalue");

				String[] del_para = new String[]{categoryid};
				String[] update_para = new String[]{categoryvalue,categoryvalue,Const.TYPE_COMICSERIES};
				String[] insert_para = new String[]{categoryid,categoryvalue,Const.TYPE_COMICSERIES};
//				String del_sql = "delete from t_cb_reference where categoryid=?";
//				String insert_sql = "insert into t_cb_reference (id,contentid,categoryid,sortid,portal) "
//						+ "select SEQ_CB_ID.Nextval,a.id,?, rownum,a.portal from (select c.id,c.portal from t_cb_content c where  instr(c.classify,?,1)!=0 and c.type=? order by c.createtime  desc) a";

				String del_sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.addComicSeriesReference.DELETE";
				//update t_cb_content c set c.classify='|'||?||'|' where instr(c.classify, ?, 1) != 0 and c.type=?
				String update_sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateComicSeriesContent.Update";
				String insert_sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.addComicSeriesReference.INSERT";
				
				
				TransactionDB tdb = null;
				try{
					//事务模式
					tdb = TransactionDB.getTransactionInstance();
					tdb.executeBySQLCode(del_sqlCode, del_para);
					tdb.executeBySQLCode(update_sqlCode, update_para);
					tdb.executeBySQLCode(insert_sqlCode, insert_para);
					tdb.commit();
					//非事务模式
	//				BaseComicDAO.getInstance().executeBySQLCode(del_sqlCode, del_para);
	//				BaseComicDAO.getInstance().executeBySQLCode(insert_sqlCode, insert_para);
					logger.debug("漫画商品分类上架完成，货架ID:"+categoryid);
				}catch (DAOException e) {
						// TODO Auto-generated catch block
						logger.error("漫画分类上架过程出错"+e);
					
				}finally{
					if(tdb!=null){
						tdb.close();
					}
				}
				
				
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("addComicSeriesReference出错：", e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("addComicSeriesReference出错：", e);
		} 

	}

	/**
	 * 新增
	 * 
	 * @param vo
	 * @throws BOException 
	 * @throws Exception
	 */

	public void insertTVSeries(TVSeriesVO vo) {
		String[] para = new String[] { vo.getId(), vo.getName(),
				vo.getDescription(), vo.getProvider(), vo.getProviderType(),
				vo.getAuthodid(), vo.getType(), vo.getKeywords(),
				vo.getExpireTime(), vo.getFee(), vo.getLocation(),
				vo.getFirst(), vo.getUrl1(), vo.getUrl2(), vo.getUrl3(),
				vo.getUrl4(), vo.getFeeCode(), vo.getDetailUrl1(),
				vo.getDetailUrl2(), vo.getDetailUrl3(), vo.getBookNum(),
				vo.getClassify(), vo.getAuthods(), vo.getActor(),
				vo.getOthersActor(), vo.getBookType(), vo.getBookStyle(),
				vo.getBookColor(), vo.getArea(), vo.getLanguage(),
				vo.getYear(), vo.getStatus(), vo.getChapterType(),vo.getUserType(),
				vo.getPortal(), vo.getBusinessid(),  vo.getCreateTime(),vo.getLupdate(),vo.getBaseType(),vo.getEbookUrl()};

//		String sql = "insert into t_cb_content(id,name,description,provider,provider_type,authodid,type,keywords,expireTime,"
//				+ "fee,location,first,url1,url2,url3,"
//				+ "url4,fee_code,detail_url1,detail_url2,detail_url3,book_num,classify,"
//				+ "authods,actor,others_actor,"
//				+ "book_type,book_style,book_color,area,language,year,status,"
//				+ "chapter_type,user_type,portal,businessid,createTime,lupdate,flow_time) "
//				+ "values(?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,sysdate)";
		//String sqlCode = "com.aspire.dotcard.basecomic.BaseComicDBOpration.insertSqlCode";
		
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.insertTVSeries.INSERT";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("insertTVSeries出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}

	}
	

	/**
	 * 修改
	 * 
	 * @param vo
	 * @throws  
	 * @throws BOException 
	 * @throws Exception
	 */

	public void updateTVSeries(TVSeriesVO vo) {
		String[] para = new String[] { vo.getId(), vo.getName(),
				vo.getDescription(), vo.getProvider(), vo.getProviderType(),
				vo.getAuthodid(), vo.getType(), vo.getKeywords(),
				vo.getExpireTime(), vo.getFee(), vo.getLocation(),
				vo.getFirst(), vo.getUrl1(), vo.getUrl2(), vo.getUrl3(),
				vo.getUrl4(), vo.getFeeCode(), vo.getDetailUrl1(),
				vo.getDetailUrl2(), vo.getDetailUrl3(), vo.getBookNum(),
				vo.getClassify(), vo.getAuthods(), vo.getActor(),
				vo.getOthersActor(), vo.getBookType(), vo.getBookStyle(),
				vo.getBookColor(), vo.getArea(), vo.getLanguage(),
				vo.getYear(), vo.getStatus(), vo.getChapterType(),vo.getUserType(),vo.getPortal(),
				vo.getBusinessid(),vo.getCreateTime(),vo.getLupdate(),vo.getBaseType(),vo.getEbookUrl(),
				vo.getId() };
//		String sql = "update t_cb_content set id=?,name=?,description=?,provider=?,provider_type=?,authodid=?,type=?,"
//				+ "keywords=?,expireTime=?,fee=?,location=?,first=?,url1=?,url2=?,url3=?,"
//				+ "url4=?,fee_code=?,detail_url1=?,detail_url2=?,detail_url3=?,book_num=?,classify=?,"
//				+ "authods=?,actor=?,others_actor=?,"
//				+ "book_type=?,book_style=?,book_color=?,area=?,language=?,year=?,status=?,"
//				+ "chapter_type=?,user_type=?,businessid=?,createTime=?,lupdate=?,flow_time=sysdate where id=? ";
		
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateTVSeries.UPDATE";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode,para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("updateTVSeries出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}

	}

	public void addTVSeriesReference() {
		
		String[] para = new String[]{Const.CATEGROY_TVSERIES};
		//String sql = "select c.categoryid,c.categoryvalue from t_cb_category c where c.parentcategoryid=? order by c.categoryvalue asc";
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.addTVSeriesReference.SELECT";
		
			RowSet rs;
			try {
				rs = DB.getInstance().queryBySQLCode(sqlCode, para);
			
			while (rs.next()) {
				String categoryid = rs.getString("categoryid");
				String categoryvalue = rs.getString("categoryvalue");
				
				String[] del_para = new String[]{categoryid};
				String[] update_para = new String[]{categoryvalue,categoryvalue,Const.TYPE_TVSERIES};
				String[] insert_para = new String[]{categoryid,categoryvalue,Const.TYPE_TVSERIES};
				
				//String del_sql = "delete from t_cb_reference where categoryid= ?";
//				String insert_sql = "insert into t_cb_reference (id,contentid,categoryid,sortid,portal) "
//						+ "select SEQ_CB_ID.Nextval,a.id,?, rownum,a.portal from (select c.id,c.portal from t_cb_content c where  instr(c.classify,?,1)!=0 and c.type=? order by c.createtime  desc) a";

				String del_sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.addTVSeriesReference.DELETE";
				//update t_cb_content c set c.classify='|'||?||'|' where instr(c.classify, ?, 1) != 0 and c.type=?
				String update_sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateComicSeriesContent.Update";
				String insert_sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.addTVSeriesReference.INSERT";
				
				TransactionDB tdb = null;
				try{
					tdb = TransactionDB.getTransactionInstance();
					tdb.executeBySQLCode(del_sqlCode, del_para);
					tdb.executeBySQLCode(update_sqlCode, update_para);
					tdb.executeBySQLCode(insert_sqlCode, insert_para);
					tdb.commit();
	//				BaseComicDAO.getInstance().executeBySQLCode(del_sqlCode,del_para);
	//				BaseComicDAO.getInstance().executeBySQLCode(insert_sqlCode,insert_para);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					logger.error("动画分类上架过程出错"+e);
				}finally{
					if(tdb!=null){
						tdb.close();
					}
				}
			}
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				logger.error(e1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
				


	}
	
	
	public void addThemeReference() {
		
		TransactionDB tdb = null;
		try {
			String categoryid = Const.CATEGROY_THEME;
			String[] del_para = new String[]{categoryid};
			String[] insert_para = new String[]{categoryid,Const.TYPE_THEME};
//			String del_sql = "delete from t_cb_reference where categoryid= ?";
//			String insert_sql = "insert into t_cb_reference (id,contentid,categoryid,sortid,portal) "
//					+ "select SEQ_CB_ID.Nextval,a.id,?, rownum,a.portal from (select c.id,c.portal from t_cb_content c where c.type=? order by c.createtime  desc) a";

			
			String del_sqlCode="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.addThemeReference.DELETE";
			String insert_sqlCode="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.addThemeReference.INSERT";
			 tdb = TransactionDB.getTransactionInstance();
			 tdb.executeBySQLCode(del_sqlCode, del_para);
			 tdb.executeBySQLCode(insert_sqlCode, insert_para);
			 tdb.commit();
//			BaseComicDAO.getInstance().executeBySQLCode(del_sqlCode,del_para);
//			BaseComicDAO.getInstance().executeBySQLCode(insert_sqlCode, insert_para);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("addThemeReference出错:",e);
		} finally{
			if(tdb!=null){
				tdb.close();
			}
		}

	}
	
	
	public void updateReferenceNum(){
		List list = getAllCategoryId();
		for(int i=0;i<list.size();i++ ){
			String categoryId = (String)list.get(i);
			int mo_num = getNumByCategoryId(categoryId,Const.PORTAL_MO);
			int wap_num = getNumByCategoryId(categoryId,Const.PORTAL_WAP);
			updateReferenceNum(categoryId,mo_num,wap_num);
		}
		
	}
	private void updateReferenceNum(String categoryId,int mo_num,int wap_num){
		//String sql = "update t_cb_category set MO_REFERENCE_NUM =?,WAP_REFERENCE_NUM=? where CATEGORYID=?";
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateReferenceNum.UPDATE";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, new String[]{mo_num+"",wap_num+"",categoryId});
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("更新货架的商品数量出错，cagegoryid:"+categoryId);
		}
		
	}
	
	
	private List getAllCategoryId(){
		List list = new ArrayList(); 
		//String sql = "select c.categoryid from t_cb_category c";
		String sqlCode ="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.getAllCategoryId.SELECT";
		try {
			RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			while(rs.next()){
				list.add(rs.getString(1));
			}
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		return list;
	}
	
	/**
	 * 得到当前货架下的商品数量。
	 * @param categoryId
	 */
	public int getNumByCategoryId(String categoryId,String portal){
		int sum = countByCategoryId(categoryId,portal);
		if(sum==0){//第一种情况：分节点，不是支节点。第二种情况：如果是支节点，则确实没有叶子。
			List categoryList= getCategroyIdList(categoryId); 
			for(int i=0;i<categoryList.size();i++){
				String cid = (String)categoryList.get(i);
				sum+=getNumByCategoryId(cid,portal);
			}
		}else{//说明是支节点
			//do nothing,hehe...  add by aiyan 2012-04-30
		}
		return sum;
		
	}

	//得到该货架下的直接子货架ID
	private List getCategroyIdList(String categoryId) {
		// TODO Auto-generated method stub
		List list = new ArrayList(); 
		//String sql = "select c.categoryid from t_cb_category c where c.parentcategoryid = ?";
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.getCategroyIdList.SELECT";
		try {
			RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, new String[]{categoryId});
			while(rs.next()){
				list.add(rs.getString(1));
			}
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		} 
		return list;
		
	}

	/*
	 * 查找portal是客户端或者WAP的该货架下的商品数量
	 */
	private int countByCategoryId(String categoryId,String portal) {
		// TODO Auto-generated method stub
		//select count(1) from t_cb_reference where categoryId = ? and portal in (?,?)
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.countByCategoryId.SELECT";
		String val = BaseComicDAO.getInstance().queryOne(sqlCode,new String[]{categoryId,portal,Const.PORTAL_ALL});
		if(val==null) return 0;
		return Integer.parseInt(val);
	}

	/**
	 * 新增
	 * 
	 * @param vo
	 * @throws BOException 
	 * @throws Exception
	 */
	public void insertTheme(ThemeVO vo) {
		String[] para = new String[] { vo.getId(), vo.getName(),
				vo.getDescription(), vo.getProvider(), vo.getProviderType(),
				vo.getAuthodid(), vo.getType(), vo.getKeywords(),
				vo.getExpireTime(), vo.getFee(), vo.getLocation(),
				vo.getFirst(), vo.getUrl1(), vo.getUrl2(), vo.getUrl3(),
				vo.getUrl4(),
				vo.getFeeCode(), vo.getDetailUrl1(),
				vo.getDetailUrl2(), vo.getDetailUrl3(), vo.getClassify(),vo.getComicImage(),vo.getAdapterDesk(),
				vo.getPortal(),vo.getBusinessid(),vo.getCreateTime(),vo.getLupdate(),vo.getBaseType() };

//		String sql = "insert into t_cb_content(id,name,description,provider,provider_type,authodid,type,keywords,expireTime,"
//				+ "fee,location,first,url1,url2,url3,"
//				+ "url4,fee_code,detail_url1,detail_url2,detail_url3,classify,"
//				+ "comic_image,adapterDesk,portal,businessid,createTime,lupdate,flow_time) "
//				+ "values(?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,sysdate)";
		String sqlCode="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.insertTheme.INSERT";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("insertTheme出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}
	}

	/**
	 * 修改
	 * 
	 * @param vo
	 * @throws  
	 * @throws BOException 
	 * @throws Exception
	 */
	public void updateTheme(ThemeVO vo) throws BOException {
		String[] para = new String[] { vo.getId(), vo.getName(),
				vo.getDescription(), vo.getProvider(), vo.getProviderType(),
				vo.getAuthodid(), vo.getType(), vo.getKeywords(),
				vo.getExpireTime(), vo.getFee(), vo.getLocation(),
				vo.getFirst(), vo.getUrl1(), vo.getUrl2(), vo.getUrl3(),
				vo.getUrl4(), vo.getFeeCode(), vo.getDetailUrl1(),
				vo.getDetailUrl2(), vo.getDetailUrl3(), vo.getClassify(),vo.getComicImage(),vo.getAdapterDesk(),
				vo.getPortal(),vo.getBusinessid(), vo.getCreateTime(),vo.getLupdate(),vo.getBaseType(),
				vo.getId() };
//		String sql = "update t_cb_content set id=?,name=?,description=?,provider=?,provider_type=?,authodid=?,type=?,"
//				+ "keywords=?,expireTime=?,fee=?,location=?,first=?,url1=?,url2=?,url3=?,"
//				+ "url4=?,fee_code=?,detail_url1=?,detail_url2=?,detail_url3=?,classify=?,comic_image=?,adapterDesk=?,"
//				+ "portal=?,businessid=?,createTime=?,lupdate=?,flow_time=sysdate where id=? ";
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateTheme.UPDATE";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode,  para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("updateTheme出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}

	}

	/**
	 * 修改
	 * 
	 * @param vo
	 * @throws  
	 * @throws BOException 
	 * @throws Exception
	 */
	public void updateStatistics(StatisticsVO vo) {
		String[] para = new String[] { vo.getDownloadNum(),
				vo.getAveragemark(), vo.getFavoritesNum(), vo.getBookedNum(),
				vo.getWeekNum(), vo.getMonthNum(), vo.getWeekFlowersNum(),
				vo.getMonthFlowersNum(), vo.getId()};
		// update t_cb_content set download_num=?,averagemark=?,favorites_num=?,booked_num=? where id=?
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateStatistics.UPDATE";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode,  para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("updateStatistics出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}

	}

	
	public void insertChapter(ChapterVO vo) {

		String[] para = new String[] { vo.getChapterId(), vo.getContentid(),
				vo.getName(), vo.getDescription(),vo.getSmall(),vo.getMedium(),vo.getLarge(),
				vo.getFee(), vo.getUpdateFlag(),vo.getType(),vo.getFeeCode(),vo.getSortid(),vo.getPublished()};

		//insert into t_cb_chapter(chapterid,contentid,name,description,small,medium,large,fee,update_flag,type,fee_code,sortid) 
		//values(?,?,?,?,?, ?,?,?,?,?,  ?,?)
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.insertChapter.INSERT";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("insertChapter出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}
	}

	public void updateChapter(ChapterVO vo) {
		String[] para = new String[] { 
				vo.getContentid(),
				vo.getName(), vo.getDescription(),
				vo.getSmall(),vo.getMedium(),vo.getLarge(),
				vo.getFee(), vo.getUpdateFlag(),vo.getType(),
				vo.getFeeCode(),vo.getSortid(),vo.getPublished(),
				vo.getChapterId() };
		//update t_cb_chapter  set contentid=?,name=?,description=?,small=?,medium=?,large=?,fee=?,update_flag=?,type=?,fee_code=?,sortid=?) 
		//where chapterid=? ";
		
		String sqlCode = "com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateChapter.UPDATE";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("updateChapter出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}

	}
	//---------------------------------------
public void insertAdapter(AdapterVO vo){
		
		//String fullDeviceId = getFullDeviceId(vo.getGroups());
		String[] para = new String[] { vo.getId(), vo.getChapterId(),vo.getGroups(),vo.getFileSize(),vo.getUseType(),vo.getClear(),vo.getType()};
//
//		String sql = "insert into t_cb_adapter(id,chapterId,groups,file_size,use_type,clear,type) " +
//				"values(?,?,?,?,?,?,?)";
		String sqlCode="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.insertAdapter.INSERT";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("insertAdapter出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}
	}

public void updateAdapter(AdapterVO vo){

//String fullDeviceId = getFullDeviceId(vo.getGroups());
String[] para = new String[] {  vo.getChapterId(),vo.getGroups(),vo.getFileSize(),vo.getUseType(),vo.getClear(),vo.getType(),vo.getId()};

//String sql = "update t_cb_adapter set id=?,chapterId=?,groups=?,file_size=?,use_type=?,clear=?,type=?,flow_time=sysdate where id=?";
String sqlCode="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateAdapter.UPDATE";
try {
	BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
	if (statisticsCallback != null) {
		statisticsCallback.doStatistics(true);
	}
} catch (BOException e) {
	// TODO Auto-generated catch block
	logger.error("insertAdapter出错：", e);
	if (statisticsCallback != null) {
		statisticsCallback.doStatistics(false);
	}
}
}
	/**
	 * 新增
	 * 
	 * @param vo
	 * @throws BOException 
	 * @throws Exception
	 */

	public void insertCP(CPVO vo) {

		String[] para = new String[] { vo.getCpid(), vo.getCpName() };

		//String sql = "insert into t_cb_cp values(?,?)";
		String sqlCode = "com.aspire.dotcard.basecomic.BaseComicDBOpration.insertCP";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("insertCP出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}

	}

	/**
	 * 修改
	 * 
	 * @param vo
	 * @throws  
	 * @throws BOException 
	 * @throws Exception
	 */
	public void updateCP(CPVO vo) {
		String[] para = new String[] { vo.getCpName(), vo.getCpid() };

		//String sql = "update t_cb_cp set cpName=? where cpid=? ";
		String sqlCode = "com.aspire.dotcard.basecomic.BaseComicDBOpration.updateCP";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("updateCP出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}

	}
	
	public void insertDeviceGroup(DeviceGroupVO vo){
		String[] para = new String[] { vo.getGroupId(), vo.getLogic(),vo.getLogicField(),vo.getLogicValue() };

		//String sql = "insert into t_cb_DeviceGroup(groupId,logic,logicField,logicValue) values(?,?,?,?)";
		String sqlCode="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.insertDeviceGroup.INSERT";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("insertDeviceGroup出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}
	}
	
	public void insertDeviceGroupItem(DeviceGroupItemVO vo){
		String[] para = new String[] { vo.getGroupId(), vo.getDeviceId() };

		//String sql = "insert into t_cb_DeviceGroupItem(groupId,deviceId,flow_time) values(?,?,sysdate)";
		String sqlCode="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.insertDeviceGroupItem.INSERT";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("insertDeviceGroupItem出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}
	}
	
	
	
//	public void updateAdapter(AdapterVO vo){
//		
//		//String fullDeviceId = getFullDeviceId(vo.getGroups());
//		String[] para = new String[] { vo.getId(), vo.getChapterId(),vo.getGroups(),vo.getFileSize(),vo.getUseType(),vo.getClear(),vo.getType(),vo.getId()};
//
//		//String sql = "update t_cb_adapter set id=?,chapterId=?,groups=?,file_size=?,use_type=?,clear=?,type=?,flow_time=sysdate where id=?";
//		String sqlCode="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateAdapter.UPDATE";
//		try {
//			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
//			if (statisticsCallback != null) {
//				statisticsCallback.doStatistics(true);
//			}
//		} catch (BOException e) {
//			// TODO Auto-generated catch block
//			logger.error("insertAdapter出错：", e);
//			if (statisticsCallback != null) {
//				statisticsCallback.doStatistics(false);
//			}
//		}
//	}
	
//	private Set getFullDeviceIdByGroupId(String groupId){
//		Set result = new HashSet();
//		String[] para = new String[] {groupId};
//		String sql = "select deviceid from t_cb_devicegroupitem where groupId=?";
//		RowSet rs = null;
//		try {
//			rs = DB.getInstance().query(sql, para);
//			while(rs.next()){
//				result.add(rs.getString("deviceid"));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			logger.error("getFullDeviceIdByGroupId出错：",e);
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			logger.error("getFullDeviceIdByGroupId出错：",e);
//		} finally{
//			DB.close(rs);
//		}
//		return result;
//		
//	}
	
	
//	private String getFullDeviceId(String groups) {
//		// TODO Auto-generated method stub
//		//groups="1|2";
//		//{1247},{7203},{2186}
//		String groupIds[] = groups.split("\\|");
//		Set set = new HashSet();
//		for(int i=0;i<groupIds.length;i++){
//			set.addAll(getFullDeviceIdByGroupId(groupIds[i]));
//		}
//		
//		StringBuffer sb = new StringBuffer();
//		for(Iterator it=set.iterator();it.hasNext();){
//			sb.append(",{").append(it.next()).append("}");
//		}
//		if(sb.length()>0){
//			return sb.substring(1);	
//		}
//		return "";
//		
//	}

	public void insertBrand(BrandVO vo) {
		String[] para = new String[] { vo.getName(),vo.getBrandId(), vo.getLogo1(),vo.getLogo2(),vo.getLogo3(),
				vo.getDescription(),vo.getSortid(),vo.getParentBrandId(),Const.NAME_BRAND};

//		String sql = "insert into t_cb_category(categoryId,categoryName,categoryValue,logo1,logo2,logo3,delFlag," +
//				"categoryDesc,sortid,createTime,lupdate,parentCategoryValue,type) " +
//				"values(seq_cb_category_id.nextval,?,?,?,?,?,0,?,?,sysdate,sysdate,?,?)";
		String sqlCode ="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.insertBrand.INSERT";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("insertBrand出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}

	}
	
	public void updateBrand(BrandVO vo) {
		String[] para = new String[] { vo.getName(), vo.getLogo1(),vo.getLogo2(),vo.getLogo3(),
				vo.getDescription(),vo.getSortid(),
				vo.getBrandId(),vo.getParentBrandId(),Const.NAME_BRAND};

//		String sql = "update t_cb_category set categoryName=?,logo1=?,logo2=?,logo3=?,categoryDesc=?,sortid=?,type='BRAND',lupdate=sysdate " +
//				" where categoryValue=? and parentCategoryValue=? and type = ?";
		
		String sqlCode="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateBrand.UPDATE";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(true);
			}
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("updateBrand出错：", e);
			if (statisticsCallback != null) {
				statisticsCallback.doStatistics(false);
			}
		}

	}

	public void updateParentCategory(String type) {
		// TODO Auto-generated method stub
		//String sql = "update t_cb_category c set c.parentcategoryid =(select a.categoryid from t_cb_category a where a.categoryvalue=c.parentcategoryvalue) where c.categoryvalue is not null  and c.parentcategoryvalue is not null and c.type=?";
		String sqlCode="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.updateParentCategory.UPDATE";
		try {
			BaseComicDAO.getInstance().executeBySQLCode(sqlCode, new String[]{type});

		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("updateParentCategory出错：", e);
		}

		
	}

	/**
	 * 构建动漫基地每个内容的门户属性，我们的算法是，看这个内容的章节在呈现表中是否有终端门户的呈现记录，有则说明该内容是终端门户的，WAP同理。
	 * 
	 * 主题默认是所有门户，在单条导入的时候录入门户属性，不在这里处理。
	 * add by aiyan 2012-06-11
	 */
//	public void buildPortal() {
//		// TODO Auto-generated method stub
//		String sqlCode="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.buildPortal.UPDATE1";
//		try {
//			logger.info("构建漫画、动画终端门户属性开始。。。");
//			int num = BaseComicDAO.getInstance().executeBySQLCode(sqlCode, null);
//			logger.info("构建漫画、动画终端门户属性完成，共"+num+"受影响！");
//
//		} catch (BOException e) {
//			// TODO Auto-generated catch block
//			logger.error("buildPortal出错：", e);
//		}
//		
//		sqlCode="com.aspire.dotcard.basecomic.dao.BaseComicDBOpration.buildPortal.UPDATE2";
//		try {
//			logger.info("构建漫画、动画wap门户属性开始。。。");
//			int num = BaseComicDAO.getInstance().executeBySQLCode(sqlCode, null);
//			logger.info("构建漫画、动画wap门户属性完成，共"+num+"受影响！");
//
//		} catch (BOException e) {
//			// TODO Auto-generated catch block
//			logger.error("buildPortal出错：", e);
//		}
//		
//	}
	
	public int buildPortal() {
	// TODO Auto-generated method stub
		
   	 CallableStatement cs = null;
     
     try
     {  Connection conn = DB.getInstance().getConnection();
        
          cs = conn.prepareCall("{?=call f_buildPortal}");  
         cs.registerOutParameter(1, Types.INTEGER);  
         cs.execute();  
         int intValue = cs.getInt(1); //获取函数返回结果
         return intValue;
     }catch(Exception e){
    	e.printStackTrace(); 
    	//throw new BOException();
    	return 0;
     }

}
	
	




}
