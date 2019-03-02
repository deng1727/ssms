package com.aspire.dotcard.hwcolorring.clrLoad ;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.util.IOUtil;

public class ColorringLoadTask extends TimerTask
{
    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(ColorringLoadTask.class);
    /**
     * ����������־����
     */
    private static final JLogger synLog = LoggerFactory.getLogger("colorring.syn") ;

    private static final String FILENAME = "txt" ;

    /**
     * ����run���з���
     */
    public void run ()
    {
        synLog.error("ColorringLoadTask start to run...") ;
        String synColorringSubject = MailConfig.getInstance().getSynColorringSubject();
        String[] ColorringSynMailto = ColorringConfig.get("ColorringSynMailto").split(",");
        StringBuffer msgInfo = new StringBuffer() ;
        //��Ҫ����ʼ��ȡ��Ȼ�����ַ���
        String FullDataTime = getCurDateTime("yyyyMMddHH");
        String RefDataTime = getCurDateTime("yyyyMMdd");
        //���ȣ���RBT GW��ftp��ȡ���ļ�
        //
        List nameList=new ArrayList(1);
        int result = ColorringFTPProcessor.doit(FullDataTime, RefDataTime,nameList) ;
        if (result == ColorringLoadConstants.RC_FTP_FULLDATAFILE_NOTFOUND)
        {
            //���ȫ�������ļ�������
            msgInfo.append("ȫ�������ļ������ڣ����β��������Զ�������ֹ��\n") ;
            Mail.sendMail(synColorringSubject,msgInfo.toString(),ColorringSynMailto);
            return ;
        }
        else if (result == ColorringLoadConstants.RC_FTP_GETFULLDATAFILE_ERROR)
        {
            //�����ȡȫ�������ļ��д��󣬱��ε���ֱ�ӽ���
            msgInfo.append("��ȡȫ�������ļ��д��󣬱��β��������Զ�������ֹ��\n") ;
            Mail.sendMail(synColorringSubject,msgInfo.toString(),ColorringSynMailto);
            return ;
        }

        //��������
        result = this.importColorringData(msgInfo,RefDataTime,nameList);
        if (result != ColorringLoadConstants.RC_SUCC)
        {
            //�������ݴ���
            msgInfo.append("�������ݵ���ʧ�ܣ����β��������Զ�������ֹ��\n") ;
            Mail.sendMail(synColorringSubject,msgInfo.toString(),ColorringSynMailto);
            return ;
        }

        //����ִ�гɹ�
        msgInfo.append("����ִ�гɹ���\n") ;
        Mail.sendMail(synColorringSubject,ColorContentBO.getInstance().syncResult,ColorringSynMailto);
        synLog.error("ColorringLoadTask finished...") ;
        
        ColorContentBO.getInstance().getConvertTaskRunner().waitToFinished();
        ColorContentBO.getInstance().getConvertTaskRunner().end();//ִ����ϣ���Ҫ�ر������ڡ�
        
        /*// �ߵ�����˵�����嵼������ˣ�������Ž��в�������ת��
        List convertList = (List)obj[1];
        if (null == convertList || convertList.size() == 0)
        {
            logger.error("��Ҫ���в�������ת�������������ڣ����������");
            return;
        }
        *//********�������������ļ�ת��******//* 
        new ColorringConvertProcess(convertList, RefDataTime).doConvert();*/
    }


    private int importColorringData(StringBuffer msgInfo,String RefDataTime,List nameList)
    {
        try
        {
            // ���������ȡ������վ������е����Ŀ¼�ڵ��·��
            String ftpPath = ColorringConfig.getColorDataFilePath();
            // ���������л�ȡ�������ݵı���·��
            String bakPath = ColorringConfig.getColorDataFileBakPath();
            /*
            if (synLog.isDebugEnabled())
            {
                // ��ӡ������ftp·���ľ���·��
                synLog.debug("the colorring data ftp abslute address is :"
                             + ftpPath);
                synLog.debug("the colorring data bakPath abslute address is :"
                             + bakPath);
            }
            // ���Ŀ¼�������򴴽�Ŀ¼
            IOUtil.checkAndCreateDir(ftpPath);
            IOUtil.checkAndCreateDir(bakPath);
            // ����ftp·���ľ��Ե�ַ
            File ftpFile = new File(ftpPath);
            String[] files = ftpFile.list();
            boolean FoundFlag = false;
            String fileName = "";
            int i = 0;
            for (i = 0; i < files.length; i++)
            {
                // �ҵ��ļ����е�������Ϣ�ӿ��ļ�����ܿ�ʼ���ݵ���
                if (files[i].trim().toLowerCase().endsWith(FILENAME)
                    && files[i].substring(0, 8).equals(RefDataTime)
                    && files[i].substring(10, 11).equals("L"))
                {
                    fileName = files[i];
                    if (synLog.isDebugEnabled())
                    {
                        synLog.debug("the fulldata fileName is :" + fileName);
                    }
                    FoundFlag = true;
                    break;
                }
            }
            if (!FoundFlag)
            {
                synLog.error("û���ҵ������������Ϣ�ӿ��ļ���");
                msgInfo.append("û���ҵ������������Ϣ�ӿ��ļ���\n");
                return ColorringLoadConstants.RC_IMP_ERROR;
            }
            // ���ȡ����ȫ���ļ�����ʼ���е���
*/            ColorContentBO.getInstance().colorringFullImport((String)nameList.get(0),ftpPath, bakPath,RefDataTime);
        }
        catch (Exception e)
        {
            synLog.error("importColorringData failed!", e) ;
            return ColorringLoadConstants.RC_IMP_ERROR ;
        }

        //�ߵ�����˵������ɹ���
        msgInfo.append("�������ݳɹ���\n");
        return ColorringLoadConstants.RC_SUCC;
    }

    /**
     * �õ����������ĵ�ǰʱ���ַ���
     * @param TIME_FORMAT String,��Ҫ��ʱ���ʽ
     * @return String
     */
    private static String getCurDateTime(String TIME_FORMAT)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(new Date());
    }

}
