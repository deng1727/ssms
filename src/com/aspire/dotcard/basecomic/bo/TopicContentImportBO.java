package com.aspire.dotcard.basecomic.bo;

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
import com.aspire.dotcard.basecomic.vo.ReferenceVO;
import com.aspire.dotcard.basecomic.vo.TopicContentVO;
import com.aspire.dotcard.basecomic.vo.VO;

public class TopicContentImportBO extends InsertImportTemplate{
	protected static JLogger logger = LoggerFactory.getLogger(TopicContentImportBO.class);
	private Map topicCategoryMap = null;

	
	public TopicContentImportBO() {
		super();
		this.nameRegex="topicContentNameRegex";
		this.fieldLength = 3;
		topicCategoryMap = getTopicCategoryMap();
	}

	public VO createVO(String[] field){
		return new TopicContentVO(field);
		
	}

	public BufferQueue createQueue(){
		return new BufferQueue();
	}



	protected void addData(VO rowVo,StatisticsCallback statisticsCallback) {
		// TODO Auto-generated method stub
		TopicContentVO vo = (TopicContentVO)rowVo;
		BaseComicDBOpration dao = new BaseComicDBOpration();
		String categoryId = (String)topicCategoryMap.get(vo.getTopicId());
		if(categoryId==null){
			statisticsCallback.doStatistics(false);
		}else{
			try {
				//delete from t_cb_reference where contentid=? and categoryid=? and type=?
				String sqlCode = "com.aspire.dotcard.basecomic.bo.TopicContentImportBO.addData.DELETE";
				BaseComicDAO.getInstance().executeBySQLCode(sqlCode,
						new String[]{vo.getContentId(),categoryId,Const.NAME_TOPIC});
				ReferenceVO rVo = new ReferenceVO();
				rVo.setCategoryId(categoryId);
				rVo.setContentId(vo.getContentId());
				rVo.setSortid(vo.getSortid());
				rVo.setType(Const.NAME_TOPIC);
				rVo.setPortal(Const.PORTAL_ALL);//û���Ż����ͣ�����ֱ��ָ���Ż�����Ϊ�����ԡ���Ĭ��ֵ���ѡ�
				dao.addReference(rVo);
				statisticsCallback.doStatistics(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("topicContent_addData����",e);
				statisticsCallback.doStatistics(false);
			}
		}
	}
	private Map getTopicCategoryMap(){
		// TODO Auto-generated method stub
		Map topicCategoryMap = new HashMap();
		//String sql = "select c.categoryid,c.categoryvalue from t_cb_category c where type=? and c.categoryvalue is not null";
		String sqlCode ="com.aspire.dotcard.basecomic.bo.TopicContentImportBO.getTopicCategoryMap.SELECT";
		
		try {
			RowSet rs =  DB.getInstance().queryBySQLCode(sqlCode, new String[]{Const.NAME_TOPIC});
			while(rs.next()){
				topicCategoryMap.put(rs.getString("categoryvalue"), rs.getString("categoryid"));
			}
				
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			logger.error("getTopicCategoryMap����", e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("getTopicCategoryMap����", e);
		}
		return topicCategoryMap;
	}

	protected int delete() {
		// TODO Auto-generated method stub
		//String sql ="select count(1) from t_cb_reference r where r.type=? and trunc(flow_time)=trunc(sysdate)";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.TopicContentImportBO.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode, new String[]{Const.NAME_TOPIC});
		
		int rowNum=0;
		if(count>0){//ȷʵ�Ѿ������˱���ҵ������ݡ����ǿ���ɾ���׷���ǰ�������ˡ���������Ŀ�����»��ظ����ǿ��ļ�����û�е�������ݣ�
			//äĿɾ��֮ǰ�����ݣ��γ��Ż������ݵĿ����ԡ�
			//sql="delete from t_cb_reference r where r.type=? and trunc(flow_time)<trunc(sysdate)";
			sqlCode = "com.aspire.dotcard.basecomic.bo.TopicContentImportBO.delete.DELETE";
			try {
				rowNum = BaseComicDAO.getInstance().executeBySQLCode(sqlCode, new String[]{Const.NAME_TOPIC});
			} catch (BOException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
		}
		return rowNum;
	}
}
