package com.aspire.dotcard.baseVideoNew.exportfile.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.vo.VerfDataVO;
import com.aspire.dotcard.baseVideoNew.bo.BaseVideoNewFileBO;
import com.aspire.dotcard.baseVideoNew.dao.BaseVideoNewFileDAO;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseFileNewTools;
import com.aspire.dotcard.baseVideoNew.vo.NodeVO;
import com.aspire.dotcard.baseVideoNew.exportfile.SynBaseVideoTask;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class CollectNodeExportFile extends BaseExportFile{

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(CollectNodeExportFile.class);
	
	public CollectNodeExportFile()
	{
		this.tableName = "t_vo_collect_node";
		this.fileName = "i_v-collect-node_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_v-collect-node_~DyyyyMMdd~.verf";
		this.mailTitle = "�������ݼ��ڵ����ݵ�����";
		this.isDelMidTable = false;
		this.isCollect = true;
	}
	
	public void init()
	{
		super.init();
		keyMap = BaseVideoNewFileDAO.getInstance().getCollectNodeIDMap();
	}
	
	@Override
	protected String checkData(String[] data,boolean flag) {
		String nodeID = data[0];
		String tmp = nodeID;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤���ݼ��ڵ������ֶθ�ʽ��nodeID=" + nodeID);
		}
		
		if (data.length != 9)
		{
			logger.error("�ֶ���������9���ֶγ���:"+data.length);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("nodeID=" + nodeID + ",nodeID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
			return BaseVideoConfig.CHECK_FAILED;
		}
		NodeVO vo = BaseVideoNewFileDAO.getInstance().getNodeDataByNodeId(nodeID);
		if(vo == null){
			logger.error("nodeID=" + nodeID + ",nodeID��֤����nodeID��Ӧ����Ŀ������");
			return BaseVideoConfig.CHECK_FAILED;
		}
		//nodebyname
		tmp = data[1];
		if (!BaseFileNewTools.checkFieldLength(tmp, 128, true))
		{
			logger.error("nodeID=" + nodeID
					+ ",nodebyname��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����4000���ַ�����nodeName=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// description
		tmp = data[2];
		if (!BaseFileNewTools.checkFieldLength(tmp, 4000, false))
		{
			logger.error("nodeID=" + nodeID
					+ ",description��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����4000���ַ���description="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// parentNodeID
		tmp = data[3];
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("nodeID=" + nodeID
					+ ",parentNodeID��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����60���ַ���parentNodeID="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// logoPath
		tmp = data[4];
		if (!BaseFileNewTools.checkFieldLength(tmp, 512, false))
		{
			logger.error("nodeID=" + nodeID
					+ ",logoPath��֤�������ֶηǱ����ֶΣ����Ȳ�����512���ַ���logoPath=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// sortID
		tmp = data[5];
		if (!BaseFileNewTools.checkIntegerField("�����", tmp, 10, false))
		{
			logger.error("nodeID=" + nodeID + ",sortID��֤�������Ȳ�����10����ֵ��sortID="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// productID
		tmp = data[6];
		if (!BaseFileNewTools.checkFieldLength(tmp, 1024, false))
		{
			logger.error("nodeID=" + nodeID
					+ ",productID��֤�������ֶ��ǷǱ����ֶΣ����Ȳ�����1024����ֵ���ȣ�productID="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// collectFlag
		tmp = data[7];
		if (!BaseFileNewTools.checkIntegerField("���ݼ���ʶ",tmp, 2, false))
		{
			logger.error("nodeID=" + nodeID
					+ ",collectFlag��֤�������ֶ��ǷǱ����ֶΣ����Ȳ�����2����ֵ���ȣ�collectFlag="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		//nodetype
		tmp = data[8];
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("nodeID=" + nodeID
					+ ",nodetype��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����60���ַ���nodetype="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	@Override
	protected String getKey(String[] data) {
		return data[0] + "|" + data[3];
	}
	
	@Override
	protected Object[] getObject(String[] data) {
		Object[] object = new Object[10];
		
		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[4];
		object[3] = data[5];
		object[4] = data[6];
		object[5] = data[7];
		object[6] = data[8];
		object[7] = data[1];
		object[8] = data[0];
		object[9] = data[3];
		
		return object;
	}
	
	@Override
	protected String getInsertSqlCode() {
		// insert into t_vo_collect_node (nodebyname,description,logopath,sortid,productid,collectflag,nodetype,nodename,exporttime, nodeid) values (?,?,?,?,?,?,?,?,sysdate,?,?)
		return "baseVideoNew.exportfile.CollectNodeExportFile.getInsertSqlCode";
	}
	
	@Override
	protected String getUpdateSqlCode() {
		// update t_vo_collect_node set nodebyname = ?,description = ?,logopath = ?,sortid = ?,productid = ?,collectflag = ?,nodetype = ?,nodename = ? where nodeid = ? and parentnodeid = ?
		return "baseVideoNew.exportfile.CollectNodeExportFile.getUpdateSqlCode";
	}

	@Override
	protected String getDelSqlCode() {
		// delete from t_vo_collect_node where nodeid = ? and parentnodeid = ?
		return "baseVideoNew.exportfile.CollectNodeExportFile.getDelSqlCode";
	}

}
