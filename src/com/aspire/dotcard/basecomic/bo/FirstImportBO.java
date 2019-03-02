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
import com.aspire.dotcard.basecomic.vo.FirstVO;
import com.aspire.dotcard.basecomic.vo.ReferenceVO;
import com.aspire.dotcard.basecomic.vo.VO;

public class FirstImportBO extends InsertImportTemplate {
	protected static JLogger logger = LoggerFactory
			.getLogger(FirstImportBO.class);

	public FirstImportBO() {
		super();
		this.nameRegex = "firstNameRegex";
		this.fieldLength = 4;
	}

	public VO createVO(String[] field) {
		return new FirstVO(field);

	}

	public BufferQueue createQueue() {
		return new BufferQueue();
	}
	
	
	protected void addData(VO rowVo, StatisticsCallback statisticsCallback) {
		// TODO Auto-generated method stub
		FirstVO vo = (FirstVO) rowVo;
		BaseComicDBOpration dao = new BaseComicDBOpration();
		try {
			//String sql_reference = "select count(1) from t_cb_reference c where c.categoryid = ? and c.contentid = ?";
			String sqlCode = "com.aspire.dotcard.basecomic.bo.FirstImportBO.addData.SELECT";
			int count_reference = BaseComicDAO.getInstance().count(sqlCode, new String[] {Const.CATEGROY_FIRST, vo.getContentid() });
			if (count_reference == 0) {//加商品
				ReferenceVO rVo = new ReferenceVO();
				rVo.setCategoryId(Const.CATEGROY_FIRST);//首发
				rVo.setContentId(vo.getContentid());
				rVo.setSortid(vo.getSortid());
				rVo.setPortal(vo.getPortal());
				rVo.setType(Const.NAME_FIRST);
				dao.addReference(rVo);
			} else if (count_reference > 0) {//修商品
				ReferenceVO rVo = new ReferenceVO();
				rVo.setCategoryId(Const.CATEGROY_FIRST);
				rVo.setContentId(vo.getContentid());
				rVo.setSortid(vo.getSortid());
				rVo.setPortal(vo.getPortal());
				dao.updateReference(rVo);
			}
			statisticsCallback.doStatistics(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("首发处理数据出错！", e);
			statisticsCallback.doStatistics(false);
		}

	}
	
	//删除首发货架下的商品数据。
	protected int delete() {
		// TODO Auto-generated method stub
		String[] para = new String[]{Const.CATEGROY_FIRST};
		//String sql ="select count(1) from t_cb_reference r where r.categoryid=? and trunc(flow_time)=trunc(sysdate)";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.FirstImportBO.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode, para);
		
		int rowNum=0;
		if(count>0){//确实已经导入了本次业务的数据。于是可以删除首发以前的数据了。这样做的目的是怕基地给的是空文件或者没有导入进数据，
			//盲目删除之前的数据，形成门户无数据的可能性。
			//sql="delete from t_cb_reference r where r.categoryid=? and trunc(flow_time)<trunc(sysdate)";
			sqlCode = "com.aspire.dotcard.basecomic.bo.FirstImportBO.delete.DELETE";
			
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
