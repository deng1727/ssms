package com.aspire.dotcard.hwcolorring.clrLoad ;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p>Title:���嵼��������ļ�FTP�����࣬���ڴӻ�Ϊ�Ĳ���ƽ̨��ȡ���е������ļ� </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author bihui
 * @version 1.0
 */
public class ColorringFTPProcessor
{

    /**
     * ����������־����
     */
    private static final JLogger synLog = LoggerFactory.getLogger("colorring.syn") ;

    /**
     * �����࣬��˹��췽��˽��
     */
    private ColorringFTPProcessor()
    {}

    /**
     * ���������ļ�ftp��ȡ�ķ���
     * @param FullDataTime String
     * @param RefDataTime String
     * @nameList ���ص��ļ���
     * @return int
     */
    public static int doit(String FullDataTime,String RefDataTime,List nameList)
    {
        synLog.error("ColorringFTPProcessor is beginning......") ;
        // ���������ȡftp���Ӵ���Ķ˿ں�
        String FTPServerPort = ColorringConfig.get("FTPServerPort");
        // ���������ȡftp���ӵĵ�ַ������Ϊƽ̨�ṩ�Ĳ������ݵ�ַ��
        String FTPServerIP = ColorringConfig.get("FTPServerIP");
        // ���������ȡFTP�������ĵ�¼�û���
        String FTPServerUser = ColorringConfig.get("FTPServerUser");
        // ���������ȡFTP�������ĵ�¼����
        String FTPServerPassword = ColorringConfig.get("FTPServerPassword");
        // ���������ȡFTP��������������Ϣ�ӿ��ļ��Ĵ��·��
        String LYXXDir = ColorringConfig.get("LYXXDir");
        // ��ȡϵͳ�в��������ļ������·��
        String ColorringSiteDir = ColorringConfig.getColorDataFilePath();
        File file = new File(ColorringSiteDir);
        // �ж�Ŀ¼�Ƿ���ڣ��粻���ڣ��򴴽�
        if (!file.isDirectory())
        {
            file.mkdirs();
        }
        synLog.error("the FTPServerPort is " + FTPServerPort) ;
        synLog.error("the FTPServerIP is " + FTPServerIP) ;
        synLog.error("the FTPServerUser is " + FTPServerUser) ;
        synLog.error("the FTPServerPassword is " + FTPServerPassword) ;
        synLog.error("the LYXXDir is " + LYXXDir) ;
        synLog.error("the ColorringSiteDir is " + ColorringSiteDir) ;

        FTPClient ftp = null;
        try
        {
            // ��ʼ��ftp�ͻ���
            if (synLog.isDebugEnabled())
            {
                synLog.debug("Construct FTPClient...");
            }
            ftp = new FTPClient(FTPServerIP, Integer.parseInt(FTPServerPort));
            //��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
            ftp.setConnectMode(FTPConnectMode.PASV);

            //ʹ�ø������û����������½ftp
            if (synLog.isDebugEnabled())
            {
                synLog.debug("login to FTPServer...");
            }
            ftp.login(FTPServerUser, FTPServerPassword);
            //�����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
            ftp.setType(FTPTransferType.BINARY);
            ftp.chdir(LYXXDir);
            //����Զ��Ŀ¼�е��ļ����б�
            String[] Remotefiles = ftp.dir();
            //�����ļ��б����飬ֻ��ȡ�뵱ǰ����ƥ����ļ����б�
            ArrayList fileList = new ArrayList();
            for (int i = 0; i < Remotefiles.length; i++)
            {
                if (Remotefiles[i].startsWith(RefDataTime))
                {
                    fileList.add(Remotefiles[i]);
                }
            }
            //����һ����־��ϢFoundFlag
            boolean FoundFlag = false;
            if(synLog.isDebugEnabled())
            {
                synLog.debug("the FullDataTime is ::::" + FullDataTime) ;
                synLog.debug("the RefDataTime is ::::" + RefDataTime) ;
            }
            //�����ļ����б���ȡ��Ȼ�����������ʱ��Ĳ��������ļ�
            for (;;)
            {
                for (int j = 0; j < fileList.size(); j++)
                {
                    if (synLog.isDebugEnabled())
                    {
                        synLog.debug("check ftp remote file:" + fileList.get(j));
                    }
                    String  fileName = (String)fileList.get(j);
                    // �ж��ļ������Լ����ҵ������ɵĽӿ��ļ�
                    if (fileName.trim().toLowerCase().endsWith(".txt")
                        && fileName.substring(0, 11).equals(FullDataTime + "L"))
                    {
                        synLog.error("Transfer is beginning ......");
                        // ��ʼ�ļ�����,��Զ��������Ϣ�ӿ��ļ����䵽�����ļ���ColorringSiteDir
                        ftp.get(ColorringSiteDir + File.separator + fileName, fileName);
                        nameList.add(fileName);//
                        //��ȡ�������ļ����˳�ftp����
                        ftp.quit();
                        FoundFlag = true;
                        synLog.error("Transfer is end ......");
                        // �ҵ����µ�������Ϣ�ӿ��ļ�ȡ��������ѭ��
                        break;
                    }
                }
                // �����ǰʱ����00�㣨24Сʱ�ƣ�Сʱ��ʾ��ʽ00,01,...,22,23��������û���ҵ���Ҫ�Ľӿ��ļ������Ѿ��ҵ���Ҫ�Ľӿ��ļ�������ѭ������
                if (FullDataTime.equals(RefDataTime + "00")||FoundFlag)
                {
                    break;
                }
                int tempTime = Integer.parseInt(FullDataTime);
                FullDataTime = String.valueOf(--tempTime);
            }
            // û��ȡ��������Ϣ�ӿ��ļ��������к����ĵ������ݲ�����ֱ�ӷ��ء�
            if (!FoundFlag)
            {
                //FoundFlag==falseʱ��˵��û���ҵ���Ҫ��ȫ�����ݣ�������Ϣ�ӿ��ļ�
                return ColorringLoadConstants.RC_FTP_FULLDATAFILE_NOTFOUND;
            }
        }
        catch(Exception e)
        {
            try
            {
                ftp.quit();
            }
            catch (Exception e1)
            {
                synLog.error("ftp�˳�ʱ�����쳣��");
            }
            synLog.error("ColorringFTPProcessor.doit() failed!",e);
            return ColorringLoadConstants.RC_FTP_GETFULLDATAFILE_ERROR;
        }

        return ColorringLoadConstants.RC_SUCC;
    }

}
