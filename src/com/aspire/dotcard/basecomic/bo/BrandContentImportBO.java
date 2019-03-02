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
		
		//add by aiyan 2013-01-18 ��ֹ����������֮���������Ʒ�ƹݻ��� ��ʼ
		String portal_type = getContentStr(vo.getContentId());
		if(portal_type==null||portal_type.split(":").length!=2){
			statisticsCallback.doStatistics(false);
			logger.info("Ʒ�ƹ��³��ֵķ��Ż��Ķ���portal_type��"+portal_type+",contentid:"+vo.getContentId());
			return;
		}
		String[] pt = portal_type.split(":");
		if(!"116".equals(pt[1])&&!"220".equals(pt[1])){
			statisticsCallback.doStatistics(false);
			logger.info("Ʒ�ƹ��³��ֵķǶ����Ż��Ķ���portal_type��"+portal_type+",contentid:"+vo.getContentId());
			return;
		}//add by aiyan 2013-01-18 ��ֹ����������֮���������Ʒ�ƹݻ��� ����
		
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
				logger.error("brandContent_addData����",e);
				statisticsCallback.doStatistics(false);
			}
		}
	}
	//portal:type  �����portal��1 �ն��Ż� 2 wap�Ż� 3 ���߶���  type�� ���� ���� ���� ��Ѷ ����
//	101:Theme������
//	116���£�-104���ɣ�:MovieSeries������Ƭ
//	115:Information����Ѷ
//	220���£�-221���ɣ�:ComicSeries��������
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
			logger.error("getContentStr��Ȼ�����ˡ�",e);
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
			logger.error("getBrandCategoryMap����", e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("getBrandCategoryMap����", e);
		}
		return brandCategoryMap;
	}

	protected int delete() {
		// TODO Auto-generated method stub
		//String sql ="select count(1) from t_cb_reference r where r.type=? and trunc(flow_time)=trunc(sysdate)";
		String sqlCode = "com.aspire.dotcard.basecomic.bo.BrandContentImportBO.delete.SELECT";
		int count = BaseComicDAO.getInstance().count(sqlCode, new String[]{Const.NAME_BRAND});
		
		int rowNum=0;
		if(count>0){//ȷʵ�Ѿ������˱���ҵ������ݡ����ǿ���ɾ���׷���ǰ�������ˡ���������Ŀ�����»��ظ����ǿ��ļ�����û�е�������ݣ�
			//äĿɾ��֮ǰ�����ݣ��γ��Ż������ݵĿ����ԡ�
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
