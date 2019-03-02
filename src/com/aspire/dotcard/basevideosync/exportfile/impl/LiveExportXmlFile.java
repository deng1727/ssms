package com.aspire.dotcard.basevideosync.exportfile.impl;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.dao.BaseVideoDAO;
import com.aspire.dotcard.basevideosync.exportfile.BaseExportXmlFile;
import com.aspire.dotcard.basevideosync.exportfile.BaseFileTools;
import com.aspire.dotcard.basevideosync.vo.LiveVideoVO;

public class LiveExportXmlFile extends BaseExportXmlFile{

	public LiveExportXmlFile()
	{
		//this.tableName = "t_v_sprogram";
		//this.fileName = "~Dyyyy-MM-dd\\yyyy-MM-dd~_PROGRAM.txt";
		//this.verfFileName = "~Dyyyy-MM-dd\\yyyy-MM-dd~_PROGRAM.verf";
		this.mailTitle = "�»�����Ƶֱ����Ŀ��xml�ļ�����ͬ�����";
		this.fileDir = "playbill";
	}
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		dataList = BaseVideoDAO.getInstance().getLiveIDList();
	}
	
	/**
	 * ���ڻ�������
	 */
	public void destroy()
	{
		if(dataList!=null) dataList.clear();
	}

	protected String checkData(Object object,String[] data) {
		
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤��Ŀ������ֱ����Ŀ��xml�ļ��ֶθ�ʽ��cmsID=" + data[1]);
		}
		
		List list = (List)object;
		if(null == list || list.size() == 0){
			logger.error("cmsID=" + data[0]
					+ ",�ý�Ŀ�����µ�ֱ����Ŀ������Ϊ��");
			return BaseVideoConfig.CHECK_FAILED;
		}
		String tmp = null;
		for(int i = 0; i < list.size() ; i++){
			LiveVideoVO liveVideoVO =  (LiveVideoVO) list.get(i);
			//PlayDay
			tmp = liveVideoVO.getPlayDay();
			if (!BaseFileTools.checkFieldLength(tmp, 8, true))
			{
				logger.error("cmsID=" + data[1]
						+ ",PlayDay��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����8���ַ���PlayDay="
						+ tmp);
				return BaseVideoConfig.CHECK_FAILED;
			}
			//StartTime
			tmp = liveVideoVO.getStartTime();
			if (!BaseFileTools.checkFieldLength(tmp, 20, true))
			{
				logger.error("cmsID=" + data[1]
						+ ",StartTime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����8���ַ���StartTime="
						+ tmp);
				return BaseVideoConfig.CHECK_FAILED;
			}
			//EndTime
			tmp = liveVideoVO.getEndTime();
			if (!BaseFileTools.checkFieldLength(tmp, 20, false))
			{
				logger.error("cmsID=" + data[1]
						+ ",EndTime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����8���ַ���EndTime="
						+ tmp);
				return BaseVideoConfig.CHECK_FAILED;
			}
			//PlayName
			tmp = liveVideoVO.getPlayName();
			if (!BaseFileTools.checkFieldLength(tmp, 128, false))
			{
				logger.error("cmsID=" + data[1]
						+ ",PlayName��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����128���ַ���PlayName="
						+ tmp);
				return BaseVideoConfig.CHECK_FAILED;
			}
			//ranking
			tmp = liveVideoVO.getRanking();
			if (!BaseFileTools.checkFieldLength(tmp, 21, false))
			{
				logger.error("cmsID=" + data[1]
						+ ",ranking��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����21���ַ���ranking="
						+ tmp);
				return BaseVideoConfig.CHECK_FAILED;
			}
		}
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	protected Object getObject(Element root) {
		List liveVideoList = new ArrayList();
		List <Element> playbills  =  root.elements("Playbill");
		//Element playbill = root.element("Playbill");
		
		if (playbills != null && playbills.size() > 0) {
			for (int i = 0; i < playbills.size(); i++) {
				Element playbill = (Element) playbills.get(i);
				String playDay = playbill.elementText("PlayDay");
				
				List PlayLists = playbill.elements("PlayLists");
				if (PlayLists != null && PlayLists.size() > 0) {
					for (int j = 0; j < PlayLists.size(); j++) {
						Element PlayList = (Element) PlayLists.get(j);
						List <Element>  Plays = PlayList.elements("Play");
						
						if (Plays != null && Plays.size() > 0) {
							for (int k = 0; k < Plays.size(); k++) {
								Element Play = (Element) Plays.get(k);
								String playName = Play.elementText("PlayName");
								String startTime = Play
										.elementText("StartTime");
								String endTime = Play.elementText("EndTime");
								String ranking = Play.elementText("ranking");
								LiveVideoVO liveVideoVO = new LiveVideoVO();
								
								liveVideoVO.setPlayDay(playDay);
								liveVideoVO.setStartTime(startTime);
								liveVideoVO.setEndTime(endTime);
								liveVideoVO.setPlayName(playName);
								liveVideoVO.setRanking(ranking);
								liveVideoVO.setPlayShellID(Play.elementText("PlayShellID"));
								liveVideoVO.setPlayVodID(Play.elementText("PlayVodID"));
								liveVideoList.add(liveVideoVO);
								
							}
						}
					}
				}
			}
		}
		
		return liveVideoList;
	}
	
	protected String getInsertSqlCode() {
		// insert into T_V_LIVE (id,programid,cmsid,playday,playname,starttime,endtime,ranking,PlayShellID,PlayVodID,lupdate) values(SEQ_T_V_LIVE_ID.Nextval,?,?,?,?,?,?,?,?,?,sysdate)
		return  "com.aspire.dotcard.basevideosync.exportfile.impl.LiveExportXmlFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode() {
		// update t_v_dprogram t set t.livestatus='1' where t.programid=? and t.cmsid=? ;
		return "com.aspire.dotcard.basevideosync.exportfile.impl.LiveExportXmlFile.getUpdateSqlCode";
	}

	protected String getDelSqlCode() {
		// delete from T_V_LIVE t where t.programid=? and t.cmsid=? ;
		return "com.aspire.dotcard.basevideosync.exportfile.impl.LiveExportXmlFile.getDelSqlCode";
	}

	protected Object getObjectParas(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
}
