/*
 * �ļ�����BaseRecommConfig.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.ponaadmin.web.baserecomm;

import java.io.File;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class BaseRecommConfig
{

    /**
     * ��¼��־��ʵ������
     */
    private static JLogger LOG = LoggerFactory.getLogger(BaseRecommConfig.class);

    /**
     * ��������Ŀ¼��
     */
    public static String LOCALFILEPATH;

    /**
     * �����ļ��ϴ�FTP�����ַ
     */
    public static String FTPIP;

    /**
     * �����ļ��ϴ���FTP����˿�
     */
    public static int FTPPORT;

    /**
     * �����ļ��ϴ���FTP�ĵ�¼�û���
     */
    public static String FTPUSER;

    /**
     * �����ļ��ϴ���FTP�ĵ�¼����
     */
    public static String FTPPASS;

    /**
     * FTP���Ŀ¼
     */
    public static String FTPDIR;

    /**
     * �ļ���ֻ���Դ����������
     */
    public static int DATANUM;

    public static void loadConfig() throws Exception
    {
        LOG.info("MM����ҵ�����������Ƽ���ʼ��ȡ������");

        // ��ʼ�������
        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("baseDataSys");
        if (module == null)
        {
            LOG.error("system-config.xml�ļ����Ҳ���baseDataSysģ��");
            System.out.println("system-config.xml�ļ����Ҳ���baseDataSysģ��,�Զ������޷���ȷ����");
            throw new BOException("system-config.xml�ļ����Ҳ���baseDataSysģ��");

        }
        LOCALFILEPATH = module.getItemValue("localFilePath");
        LOG.info("localFilePath=" + LOCALFILEPATH);
        FTPIP = module.getItemValue("FTPServerIP");
        LOG.info("FTPServerIP=" + FTPIP);
        FTPPORT = Integer.parseInt(module.getItemValue("FTPServerPort"));
        LOG.info("FTPServerPort=" + FTPPORT);
        FTPUSER = module.getItemValue("FTPServerUser");
        LOG.info("FTPServerUser=" + FTPUSER);
        FTPPASS = module.getItemValue("FTPServerPassword");
        LOG.info("FTPServerPassword=" + FTPPASS);
        FTPDIR = module.getItemValue("FTPDir");
        LOG.info("FTPDir=" + FTPDIR);
        DATANUM = Integer.parseInt(module.getItemValue("fileDataNum"));
        LOG.info("fileDataNum=" + DATANUM);

        LOCALFILEPATH = LOCALFILEPATH.endsWith(File.separator) ? LOCALFILEPATH
                        : LOCALFILEPATH + File.separator;
    }

}
