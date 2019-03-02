package com.aspire.dotcard.basecomic.bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.common.BufferQueue;
import com.aspire.dotcard.basecomic.common.Const;
import com.aspire.dotcard.basecomic.common.StatisticsCallback;
import com.aspire.dotcard.basecomic.dao.BaseComicDAO;
import com.aspire.dotcard.basecomic.dao.BaseComicDBOpration;
import com.aspire.dotcard.basecomic.template.InsertImportTemplate;
import com.aspire.dotcard.basecomic.vo.BrandContentVO;
import com.aspire.dotcard.basecomic.vo.ReferenceVO;
import com.aspire.dotcard.basecomic.vo.VO;

public class BrandContentImportBO extends InsertImportTemplate{
	protected static JLogger logger = LoggerFactory.getLogger(BrandContentImportBO.class);
	private Map brandCategoryMap = null;

	public BrandContentImportBO() {
		super();
		this.nameRegex="brandContentNameRegex";
		this.fieldLength = 3;
		brandCategoryMap = getBrandCategoryMap();
	}

	public VO createVO(String[] field){
		return new BrandContentVO(field);
		
	}

	public BufferQueue createQueue(){
		return new BufferQueue();
	}



	protected void addData(VO rowVo,StatisticsCallback statisticsCallback) {
		// TODO Auto-generated method stub
		BrandContentVO vo = (BrandContentVO)rowVo;
		
		//add by aiyan 2013-01-18 防止动画、漫画之外的类型上品牌馆货架 开始
		String portal_type = getContentStr(vo.getContentId());
		if(portal_type==null||portal_type.split(":").length!=2){
			statisticsCallback.doStatistics(false);
			logger.info("品牌馆下出现的非门户的东东portal_type："+portal_type+",contentid:"+vo.getContentId());
			return;
		}
		String[] pt = portal_type.split(":");
		if(!"116".equals(pt[1])&&!"220".equals(pt[1])){
			statisticsCallback.doStatistics(false);
			logger.info("品牌馆下出现的非动画门户的东东portal_type："+portal_type+",contentid:"+vo.getContentId());
			return;
		}//add by aiyan 2013-01-18 防止动画、漫画之外的类型上品牌馆货架 结束
		
		BaseComicDBOpration dao = new BaseComicDBOpration();
		String categoryId = (String)brandCategoryMap.get(vo.getBrandId());
		if(categoryId==null){
			statisticsCallback.doStatistics(false);
		}else{
			try {
				//delete from t_cb_reference where contentid=? and categoryid=? and type=?
				String sqlCode = "com.aspire.dotcard.basecomic.bo.BrandContentImportBO.addData.DELETE";
				BaseComicDAO.getInstance().executeBySQLCode(sqlCode, 
						new String[]{vo.getContentId(),categoryId,Const.NAME_BRAND});
				
				ReferenceVO rVo = new ReferenceVO();
				rVo.setCategoryId(categoryId);
				rVo.setContentId(vo.getContentId());
				rVo.setSortid(vo.getSortid());
				rVo.setType(Const.NAME_BRAND);
				rVo.setPortal(pt[0]);
				dao.addReference(rVo);
				statisticsCallback.doStatistics(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("brandContent_addData出错！",e);
				statisticsCallback.doStatistics(false);
			}
		}
	}
	//portal:type  这里的portal是1 终端门户 2 wap门户 3 两者都是  type是 动画 漫画 主题 资讯 四类
//	101:Theme，主题
//	116（新）-104（旧）:MovieSeries，动画片
//	115:Information，资讯
//	220（新）-221（旧）:ComicSeries，漫画书
	private String getContentStr(String contentid){
		String result = null;
		RowSet rs = null;
		try {
			rs = DB.getInstance().query("select c.portal||':'||c.type from t_cb_content c where c.id=?",new Object[]{contentid});
			if(rs.next()){
				result= rs.getString(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("getContentStr居然出错了。",e);
		}finally{
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.equals(e);
				}
		}
		return result;
	}
	
	
	private Map getBrandCategoryMap(){
		// TODO Auto-generated method stub
		Map brandCategoryMap = new HashMap();
		//String sql = "select c.categoryid,c.categoryvalue from t_cb_category c where type=? and c.categoryvalue is not null";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.BrandContentImportBO.getBrandCategoryMap.SELECT";
		try {
			RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, new String[]{Const.NAME_BRAND});
			while(rs.next()){
				brandCategoryMap.put(rs.getString("categoryvalue"), rs.getString("categoryid"));
			}
				
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("getBrandCategoryMap出错：", e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("getBrandCategoryMap出错：", e);
		}
		return brandCategoryMap;
	}

	protected int delete() {
		// TODO Auto-generated method stub
		//String sql ="select count(1) from t_cb_reference r where r.type=? and trunc(flow_time)=trunc(sysdate)";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.BrandContentImportBO.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode, new String[]{Const.NAME_BRAND});
		
		int rowNum=0;
		if(count>0){//确实已经导入了本次业务的数据。于是可以删除首发以前的数据了。这样做的目的是怕基地给的是空文件或者没有导入进数据，
			//盲目删除之前的数据，形成门户无数据的可能性。
			//sql="delete from t_cb_reference r where r.type=? and trunc(flow_time)<trunc(sysdate)";
			sqlCode = "com.aspire.dotcard.basecomic.bo.BrandContentImportBO.delete.DELETE";
			try {
				rowNum = BaseComicDAO.getInstance().executeBySQLCode(sqlCode, new String[]{Const.NAME_BRAND});
			} catch (BOException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
		}
		return rowNum;
	}
	
	public static void main(String[] argv){
		String result = ":";
		String[] a = result.split(":");
		
		System.out.println(a.length);
	}
}
