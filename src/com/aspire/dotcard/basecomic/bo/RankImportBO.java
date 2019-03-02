package com.aspire.dotcard.basecomic.bo;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecomic.common.BufferQueue;
import com.aspire.dotcard.basecomic.common.Const;
import com.aspire.dotcard.basecomic.common.StatisticsCallback;
import com.aspire.dotcard.basecomic.dao.BaseComicDAO;
import com.aspire.dotcard.basecomic.dao.BaseComicDBOpration;
import com.aspire.dotcard.basecomic.template.InsertImportTemplate;
import com.aspire.dotcard.basecomic.vo.CategoryVO;
import com.aspire.dotcard.basecomic.vo.RankVO;
import com.aspire.dotcard.basecomic.vo.ReferenceVO;
import com.aspire.dotcard.basecomic.vo.VO;

public class RankImportBO extends InsertImportTemplate{
	protected static JLogger logger = LoggerFactory.getLogger(RankImportBO.class);

	public RankImportBO() {
		super();
		this.nameRegex="rankNameRegex";
		this.fieldLength = 5;
	}

	public VO createVO(String[] field){
		return new RankVO(field);
		
	}
	public BufferQueue createQueue(){
		return new BufferQueue(10);
	}

	public void addData(VO rowVo, StatisticsCallback statisticsCallback) {
		// TODO Auto-generated method stub
		RankVO vo = (RankVO) rowVo;
		BaseComicDBOpration dao = new BaseComicDBOpration();
		//String sql_categroy = "select c.categoryid  from t_cb_category c where c.categoryValue = ? and c.type=?";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.RankImportBO.addData.SELECT";
		String categoryId = BaseComicDAO.getInstance().queryOne(sqlCode, new String[] { vo.getCategoryValue(),Const.NAME_RANK });

		try {
			if (categoryId == null) {
				
				//加货架
				CategoryVO cVo = getCategoryVO(vo);
				categoryId = dao.addCategory(cVo);
				
				//加商品
				ReferenceVO rVo = new ReferenceVO();
				rVo.setCategoryId(categoryId);
				rVo.setContentId(vo.getContentid());
				rVo.setSortid(vo.getSortid());
				rVo.setPortal(vo.getPortal());
				rVo.setType(Const.NAME_RANK);
				dao.addReference(rVo);
				
			} else {
				//修货架
				CategoryVO cVo = new CategoryVO();
				cVo.setCategoryId(categoryId);
				cVo.setCategoryName(vo.getName());
				dao.updateCategory(cVo);
				
				
				//String sql_reference = "select count(1) from t_cb_reference c where c.categoryid = ? and c.contentid = ?";
				sqlCode = "com.aspire.dotcard.basecomic.bo.RankImportBO.addData.SELECT2";
				
				int count_reference = BaseComicDAO.getInstance().count(sqlCode, new String[] {categoryId, vo.getContentid() });
				if (count_reference == 0) {//加商品
					ReferenceVO rVo = new ReferenceVO();
					rVo.setCategoryId(categoryId);
					rVo.setContentId(vo.getContentid());
					rVo.setSortid(vo.getSortid());
					rVo.setPortal(vo.getPortal());
					rVo.setType(Const.NAME_RANK);
					dao.addReference(rVo);
				} else if (count_reference > 0) {//修商品
					ReferenceVO rVo = new ReferenceVO();
					rVo.setCategoryId(categoryId);
					rVo.setContentId(vo.getContentid());
					rVo.setSortid(vo.getSortid());
					rVo.setPortal(vo.getPortal());
					dao.updateReference(rVo);
				}
			}
		
			statisticsCallback.doStatistics(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("排行榜处理数据出错！", e);
			statisticsCallback.doStatistics(false);
		}finally{
			//tdb.close();
		}

	}
	
	private CategoryVO getCategoryVO(RankVO vo) {
		// TODO Auto-generated method stub
		CategoryVO c = new CategoryVO();
		c.setCategoryValue(vo.getCategoryValue());
		c.setCategoryName(vo.getName());
		//c.setSortid(vo.getSortid());
		c.setSortid(vo.getCategoryValue());
		c.setDelFlag("0");//delflag 0:在门户上展示 1：不展示 2：逻辑删除了。
		c.setParentCategoryId(Const.CATEGROY_RANK);
		c.setType(Const.NAME_RANK);
		return c;
	}

	protected int delete() {
		// TODO Auto-generated method stub
		String[] para = new String[]{"RANK"};
		//String sql ="select count(1) from t_cb_reference r where r.type=? and trunc(flow_time)=trunc(sysdate)";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.RankImportBO.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode, para);
		
		int rowNum=0;
		if(count>0){//确实已经导入了本次业务的数据。于是可以删除首发以前的数据了。这样做的目的是怕基地给的是空文件或者没有导入进数据，
			//盲目删除之前的数据，形成门户无数据的可能性。
			//sql="delete from t_cb_reference r where r.type=? and trunc(flow_time)<trunc(sysdate)";
			sqlCode = "com.aspire.dotcard.basecomic.bo.RankImportBO.delete.DELETE";
			try {
				rowNum = BaseComicDAO.getInstance().executeBySQLCode(sqlCode, para);
			} catch (BOException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
		}
		return rowNum;
	}



	

}
