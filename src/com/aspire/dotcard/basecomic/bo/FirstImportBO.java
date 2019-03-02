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
			if (count_reference == 0) {//����Ʒ
				ReferenceVO rVo = new ReferenceVO();
				rVo.setCategoryId(Const.CATEGROY_FIRST);//�׷�
				rVo.setContentId(vo.getContentid());
				rVo.setSortid(vo.getSortid());
				rVo.setPortal(vo.getPortal());
				rVo.setType(Const.NAME_FIRST);
				dao.addReference(rVo);
			} else if (count_reference > 0) {//����Ʒ
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
			logger.error("�׷��������ݳ���", e);
			statisticsCallback.doStatistics(false);
		}

	}
	
	//ɾ���׷������µ���Ʒ���ݡ�
	protected int delete() {
		// TODO Auto-generated method stub
		String[] para = new String[]{Const.CATEGROY_FIRST};
		//String sql ="select count(1) from t_cb_reference r where r.categoryid=? and trunc(flow_time)=trunc(sysdate)";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.FirstImportBO.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode, para);
		
		int rowNum=0;
		if(count>0){//ȷʵ�Ѿ������˱���ҵ������ݡ����ǿ���ɾ���׷���ǰ�������ˡ���������Ŀ�����»��ظ����ǿ��ļ�����û�е�������ݣ�
			//äĿɾ��֮ǰ�����ݣ��γ��Ż������ݵĿ����ԡ�
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
