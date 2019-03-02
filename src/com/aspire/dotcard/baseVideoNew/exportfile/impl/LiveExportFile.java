/*
 * �ļ�����LiveExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideoNew.exportfile.impl;

import java.util.Iterator;
import java.util.Map;

import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideoNew.dao.BaseVideoNewFileDAO;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseFileNewTools;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class LiveExportFile extends BaseExportFile {
	/**
	 * ��ĿID�б�
	 */
	private Map<String, String> programNodeIDMap = null;

	public LiveExportFile() {
		this.tableName = "t_vo_live";
		this.fileName = "i_v-live-new_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_v-live-new_~DyyyyMMdd~.verf";
		this.mailTitle = "����ֱ����Ŀ���ݵ�����";
	}

	/**
	 * �������׼����������
	 */
	public void init() {
		super.init();
		keyMap = BaseVideoNewFileDAO.getInstance().getLiveIDMap();
		programNodeIDMap = getCheckMidIDMap(BaseVideoNewFileDAO.getInstance()
				.getVideoIDMap(), BaseVideoNewFileDAO.getInstance()
				.getVideoMidIDMap("A"), BaseVideoNewFileDAO.getInstance()
				.getVideoMidIDMap("U"), BaseVideoNewFileDAO.getInstance()
				.getVideoMidIDMap("D"));
	}

	/**
	 * �õ�����У�����ݺϷ��Ե���ʽ�����м��ϱ�����
	 * 
	 * @param map
	 *            ��ʽ�픵����
	 * @param mapAdd
	 *            ���g������������
	 * @param mapDel
	 *            ���g��h��������
	 * @return �ϱ픵����
	 */
	public Map<String, String> getCheckMidIDMap(Map<String, String> map,
			Map<String, String> mapAdd, Map<String, String> mapUpdate,
			Map<String, String> mapDel) {

		Iterator<String> it = mapDel.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (map.containsKey(key)) {
				map.remove(key);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("uniall map and mapadd");
		}
		map.putAll(mapAdd); // �߼�Ҫ�ʹ洢���̺ϲ���ʽ��һ��
		map.putAll(mapUpdate); // �߼�Ҫ�ʹ洢���̺ϲ���ʽ��һ��
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java
	 * .lang.String[])
	 */
	protected String checkData(String[] data, boolean flag) {
		String videoId = data[0];
		String tmp = videoId;

		if (logger.isDebugEnabled()) {
			logger.debug("��ʼ��ֱ֤����Ŀ�����ֶθ�ʽ��videoId=" + videoId);
		}

		if (data.length != 4) {
			logger.error("�ֶ���������4");
			return BaseVideoConfig.CHECK_FAILED;
		}

		// videoId
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true)) {
			logger.error("videoId=" + videoId
					+ ",videoId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
			return BaseVideoConfig.CHECK_FAILED;
		}
		if (!programNodeIDMap.containsKey(tmp)) {
			logger.error("videoId=" + tmp + ",������¼��֤������Ŀ�����в����ڴ˼�¼��Ӧ��ϵ��");
			return BaseVideoConfig.CHECK_FAILED;
		}

		// startTime
		tmp = data[1];
		if (!BaseFileNewTools.checkFieldLength(tmp, 14, true)) {
			logger.error("videoId=" + videoId
					+ ",startTime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����14���ַ���startTime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// endTime
		tmp = data[2];
		if (!BaseFileNewTools.checkFieldLength(tmp, 14, true)) {
			logger.error("videoId=" + videoId
					+ ",endTime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����14���ַ���endTime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// liveName
		tmp = data[3];
		if (!BaseFileNewTools.checkFieldLength(tmp, 200, true)) {
			logger.error("videoId=" + videoId
					+ ",liveName��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����200���ַ���liveName=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java
	 * .lang.String[])
	 */
	protected Object[] getObject(String[] data) {
		return new Object[] { data[0], data[3], data[1], data[2],
				data[0] + data[1] + data[2]};
	}

	protected String getKey(String[] data) {
		// ֱ����Ŀ�� Ҫ���Ͽ�ʼʱ�� ��ΪΨһ����
		return data[0] + "|" + data[1] ;
	}

	protected String getInsertSqlCode() {
		// insert into t_vo_live_mid (videoid, livename, starttime,endtime,sid, STATUS) 
		//values (?,?,?,?,?,'A')
		return "baseVideoNew.exportfile.LiveExportFile.getInsertSqlCode";
	}

	protected String getUpdateSqlCode() {
		// insert into t_vo_live_mid (videoid, livename, starttime,endtime,sid, STATUS)
		//values (?,?,?,?,?,'U')
		return "baseVideoNew.exportfile.LiveExportFile.getUpdateSqlCode";
	}

	protected String getDelSqlCode() {
		// insert into t_vo_live_mid (videoid, starttime, status, LIVENAME, ENDTIME) values (?,?,'D','temp','temp')
		return "baseVideoNew.exportfile.LiveExportFile.getDelSqlCode";
	}

	/**
	 * ���ڻ�������
	 */
	protected void clear() {
		super.clear();
		programNodeIDMap.clear();
	}
}
