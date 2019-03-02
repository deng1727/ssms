/*
 * �ļ�����NodeExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideo.exportfile.impl;

import java.io.File;

import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.exportfile.BaseExportFile;
import com.aspire.ponaadmin.web.category.export.ftp.FTPUtil;

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
    /**
     * FTP����
     */
    private FTPUtil fromFtp = null;

    private FTPUtil toFtp = null;

    public NodeExportFile()
    {
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

        fromFtp = new FTPUtil(BaseVideoConfig.FromFTPIP,
                              BaseVideoConfig.FromFTPPort,
                              BaseVideoConfig.FromFTPUser,
                              BaseVideoConfig.FromFTPPassword,
                              BaseVideoConfig.FromNodeFTPDir);

        toFtp = new FTPUtil(BaseVideoConfig.ToFTPIP,
                            BaseVideoConfig.ToFTPPort,
                            BaseVideoConfig.ToFTPUser,
                            BaseVideoConfig.ToFTPPassword,
                            BaseVideoConfig.ToNodeFTPDir);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
     */
    protected String checkData(String[] data)
    {
        String nodeID = data[0];
        String tmp = nodeID;

        if (logger.isDebugEnabled())
        {
            logger.debug("��ʼ��֤��Ŀ�����ֶθ�ʽ��nodeID=" + nodeID);
        }

        if (data.length != 7)
        {
            logger.error("�ֶ���������7");
            return BaseVideoConfig.CHECK_FAILED;
        }

        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("nodeID=" + nodeID + ",nodeID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
            return BaseVideoConfig.CHECK_FAILED;
        }
        // nodeName
        tmp = data[1];
        if (!this.checkFieldLength(tmp, 128, true))
        {
            logger.error("nodeID=" + nodeID
                         + ",nodeName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����128���ַ�����nodeName="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }

        // description
        tmp = data[2];
        if (!this.checkFieldLength(tmp, 4000, true))
        {
            logger.error("nodeID="
                         + nodeID
                         + ",description��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����4000���ַ���description="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // parentNodeID
        tmp = data[3];
        if (!this.checkFieldLength(tmp, 60, false))
        {
            logger.error("nodeID="
                         + nodeID
                         + ",parentNodeID��֤�������ֶηǱ����ֶΣ����Ȳ�����60���ַ���parentNodeID="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // logoPath
        tmp = data[4];
        if (!this.checkFieldLength(tmp, 512, false))
        {
            logger.error("nodeID=" + nodeID
                         + ",logoPath��֤�������ֶηǱ����ֶΣ����Ȳ�����512���ַ���logoPath=" + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // sortID
        tmp = data[5];
        if (!this.checkIntegerField("�����", tmp, 19, false))
        {
            logger.error("nodeID=" + nodeID + ",sortID��֤�������Ȳ�����19����ֵ��sortID="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // productID
        tmp = data[6];
        if (!this.checkFieldLength(tmp, 1024, false))
        {
            logger.error("nodeID=" + nodeID
                         + ",productID��֤�������ֶ��ǷǱ����ֶΣ����Ȳ�����1024����ֵ���ȣ�productID="
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
        Object[] object = new Object[7];

        object[0] = data[1];
        object[1] = data[2];
        object[2] = data[3];
        if ("".equals(data[4]))
        {
            object[3] = "";
        }
        else
        {
            //object[3] = getLogoPath(data[4], data[0]);
        	   if( data[4] != null && ! data[4].equals("")){
               	String ftplogo = (String) data[4];
               	 if(ftplogo.startsWith("/")){
               		object[3] =  BaseVideoConfig.NodeLogoPath + data[4] ;
               	 }else{
               		 
               		object[3] =  BaseVideoConfig.NodeLogoPath + "/"+data[4] ;
               	 }
        	   }
        	
        }
        object[4] = data[5];
        object[5] = data[6];
        object[6] = data[0];

        return object;
    }

    /**
     * ��������ͼƬlogo����ԭftp��ͼƬ�µ����أ���ת������Ӧftp��
     * 
     * @return
     */
    private String getLogoPath(String ftpLogoPath, String localName)
    {
        String fileName = null;
        String ftpFilePath = ftpLogoPath.substring(0,
                                                   ftpLogoPath.lastIndexOf("/"));
        String ftpFileName = ftpLogoPath.substring(ftpLogoPath.lastIndexOf("/") + 1,
                                                   ftpLogoPath.length());
        String localFileName = localName
                               + ftpFileName.substring(ftpFileName.indexOf("."),
                                                       ftpFileName.length());

        // ����FTP�ϵ�logo�ļ�
        try
        {
            fileName = fromFtp.getFtpFileByFileName(ftpFilePath,
                                                    ftpFileName,
                                                    BaseVideoConfig.nodelogoTemplocalDir,
                                                    localFileName);
        }
        catch (Exception e)
        {
            logger.error(" �����ṩ�Ĵ��logoͼƬ��FTP���ó���FTP���ӳ�������<br> ", e);
            return "";
        }

        // �ϴ��������ļ�������
        try
        {
            if (fileName != null && !"".equals(fileName))
            {
                toFtp.putFiles(BaseVideoConfig.ToNodeFTPDir,
                               fileName,
                               localFileName);
            }
            else
            {
                return "";
            }
        }
        catch (Exception e)
        {
            logger.error(" �������logoͼƬ������FTP���ó���FTP���ӳ�������<br> ", e);
            return "";
        }

        // ��������ֶ���Ϣ
        return BaseVideoConfig.NodeLogoPath + File.separator + localFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getInsertSqlCode()
     */
    protected String getInsertSqlCode()
    {
        // insert into t_vo_node (nodename, description, parentnodeid, logopath,
        // sortid, productid, exporttime, nodeid) values (?,?,?,?,?,?,sysdate,?)
        return "baseVideo.exportfile.NodeExportFile.getInsertSqlCode";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
     */
    protected String getUpdateSqlCode()
    {
        // update t_vo_node n set n.nodename=?, n.description=?,
        // n.parentnodeid=?, n.logopath=?, n.sortid=?, n.productid=?,
        // n.exporttime=sysdate where n.nodeid=?
        return "baseVideo.exportfile.NodeExportFile.getUpdateSqlCode";
    }

    protected String getDelSqlCode()
    {
        // truncate table t_vo_node
        return "baseVideo.exportfile.NodeExportFile.getDelSqlCode";
    }

    protected Object[] getHasObject(String[] data)
    {
        Object[] object = new Object[1];
        object[0] = data[0];
        return object;
    }

    protected String getHasSqlCode()
    {
        // select 1 from t_vo_node n where n.nodeid=?
        return "baseVideo.exportfile.NodeExportFile.getHasSqlCode";
    }

    protected String getKey(String[] data)
    {
        return data[0];
    }

}
