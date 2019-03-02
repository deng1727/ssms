/*
 * �ļ�����NodeExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideoNew.exportfile.impl;

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
public class NodeExportFile extends BaseExportFile
{
	
	public NodeExportFile()
	{
		this.tableName = "t_vo_node";
		this.fileName = "i_v-node_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_v-node_~DyyyyMMdd~.verf";
		this.mailTitle = "������Ŀ���ݵ�����";
	}
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		keyMap = BaseVideoNewFileDAO.getInstance().getNodeIDMap();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data,boolean flag)
	{
		String nodeID = data[0];
		String tmp = nodeID;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤��Ŀ�����ֶθ�ʽ��nodeID=" + nodeID);
		}
		
		if (data.length != 8)
		{
			logger.error("�ֶ���������8,�ֶγ���:"+data.length);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("nodeID=" + nodeID + ",nodeID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
			return BaseVideoConfig.CHECK_FAILED;
		}
		// nodeName
		tmp = data[1];
		if (!BaseFileNewTools.checkFieldLength(tmp, 128, true))
		{
			logger.error("nodeID=" + nodeID
					+ ",nodeName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����128���ַ�����nodeName=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// description
		tmp = data[2];
		if (!BaseFileNewTools.checkFieldLength(tmp, 4000, true))
		{
			logger.error("nodeID=" + nodeID
					+ ",description��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����4000���ַ���description="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// parentNodeID
		tmp = data[3];
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, false))
		{
			logger.error("nodeID=" + nodeID
					+ ",parentNodeID��֤�������ֶηǱ����ֶΣ����Ȳ�����60���ַ���parentNodeID="
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
		if (!BaseFileNewTools.checkIntegerField("�����", tmp, 19, false))
		{
			logger.error("nodeID=" + nodeID + ",sortID��֤�������Ȳ�����19����ֵ��sortID="
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
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java.lang.String[])
	 */
	protected Object[] getObject(String[] data)
	{
		Object[] object = new Object[8];
		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[3];
		if ("".equals(data[4]))
		{
			object[3] = "";
		}
		else
		{
			if (data[4] != null && !data[4].equals(""))
			{
				String ftplogo = (String) data[4];
				String strtemp = ftplogo.toUpperCase();
				ftplogo = ftplogo.substring(strtemp.indexOf("IMAGE"));
				if (ftplogo.startsWith("/"))
				{
					object[3] = BaseVideoConfig.NodeLogoPath + ftplogo;
				}
				else
				{
					
					object[3] = BaseVideoConfig.NodeLogoPath + "/" + ftplogo;
				}
			}
		}
		object[4] = data[5];
		object[5] = data[6];
		object[6] = data[7];
		object[7] = data[0];
		
		return object;
	}
	
	protected String getKey(String[] data)
	{
		return data[0];
	}

	protected String getInsertSqlCode()
	{
		// insert into t_vo_node_mid (nodename, description, parentnodeid, logopath, sortid, productid,collectflag, exporttime, nodeid, status) values (?,?,?,?,?,?,sysdate,?, 'A')
		return "baseVideoNew.exportfile.NodeExportFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode()
	{
		// insert into t_vo_node_mid (nodename, description, parentnodeid, logopath, sortid, productid,collectflag, exporttime, nodeid, status) values (?,?,?,?,?,?,sysdate,?, 'U')
		return "baseVideoNew.exportfile.NodeExportFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode()
	{
		// insert into t_vo_node_mid (nodeid, status) values (?, 'D')
		return "baseVideoNew.exportfile.NodeExportFile.getDelSqlCode";
	}
}
