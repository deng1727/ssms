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
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(ColorringLoadTask.class);
    /**
     * 彩铃网关日志引用
     */
    private static final JLogger synLog = LoggerFactory.getLogger("colorring.syn") ;

    private static final String FILENAME = "txt" ;

    /**
     * 覆盖run运行方法
     */
    public void run ()
    {
        synLog.error("ColorringLoadTask start to run...") ;
        String synColorringSubject = MailConfig.getInstance().getSynColorringSubject();
        String[] ColorringSynMailto = ColorringConfig.get("ColorringSynMailto").split(",");
        StringBuffer msgInfo = new StringBuffer() ;
        //需要在起始获取当然日期字符串
        String FullDataTime = getCurDateTime("yyyyMMddHH");
        String RefDataTime = getCurDateTime("yyyyMMdd");
        //首先，到RBT GW上ftp获取到文件
        //
        List nameList=new ArrayList(1);
        int result = ColorringFTPProcessor.doit(FullDataTime, RefDataTime,nameList) ;
        if (result == ColorringLoadConstants.RC_FTP_FULLDATAFILE_NOTFOUND)
        {
            //如果全量彩铃文件不存在
            msgInfo.append("全量彩铃文件不存在，本次彩铃数据自动任务终止。\n") ;
            Mail.sendMail(synColorringSubject,msgInfo.toString(),ColorringSynMailto);
            return ;
        }
        else if (result == ColorringLoadConstants.RC_FTP_GETFULLDATAFILE_ERROR)
        {
            //如果获取全量彩铃文件有错误，本次导入直接结束
            msgInfo.append("获取全量彩铃文件有错误，本次彩铃数据自动任务终止。\n") ;
            Mail.sendMail(synColorringSubject,msgInfo.toString(),ColorringSynMailto);
            return ;
        }

        //导入数据
        result = this.importColorringData(msgInfo,RefDataTime,nameList);
        if (result != ColorringLoadConstants.RC_SUCC)
        {
            //导入数据错误
            msgInfo.append("彩铃数据导入失败！本次彩铃数据自动任务终止。\n") ;
            Mail.sendMail(synColorringSubject,msgInfo.toString(),ColorringSynMailto);
            return ;
        }

        //任务执行成功
        msgInfo.append("任务执行成功！\n") ;
        Mail.sendMail(synColorringSubject,ColorContentBO.getInstance().syncResult,ColorringSynMailto);
        synLog.error("ColorringLoadTask finished...") ;
        
        ColorContentBO.getInstance().getConvertTaskRunner().waitToFinished();
        ColorContentBO.getInstance().getConvertTaskRunner().end();//执行完毕，需要关闭运行期。
        
        /*// 走到这里说明彩铃导入完成了，后面接着进行彩铃试听转换
        List convertList = (List)obj[1];
        if (null == convertList || convertList.size() == 0)
        {
            logger.error("需要进行彩铃试听转换的铃音不存在，任务结束！");
            return;
        }
        *//********启动彩铃试听文件转换******//* 
        new ColorringConvertProcess(convertList, RefDataTime).doConvert();*/
    }


    private int importColorringData(StringBuffer msgInfo,String RefDataTime,List nameList)
    {
        try
        {
            // 从配置项读取彩铃在站点管理中的相关目录节点的路径
            String ftpPath = ColorringConfig.getColorDataFilePath();
            // 从配置项中获取彩铃数据的备份路径
            String bakPath = ColorringConfig.getColorDataFileBakPath();
            /*
            if (synLog.isDebugEnabled())
            {
                // 打印出彩铃ftp路径的绝对路径
                synLog.debug("the colorring data ftp abslute address is :"
                             + ftpPath);
                synLog.debug("the colorring data bakPath abslute address is :"
                             + bakPath);
            }
            // 如果目录不存在则创建目录
            IOUtil.checkAndCreateDir(ftpPath);
            IOUtil.checkAndCreateDir(bakPath);
            // 构造ftp路径的绝对地址
            File ftpFile = new File(ftpPath);
            String[] files = ftpFile.list();
            boolean FoundFlag = false;
            String fileName = "";
            int i = 0;
            for (i = 0; i < files.length; i++)
            {
                // 找到文件夹中的铃音信息接口文件后才能开始数据导入
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
                synLog.error("没有找到当天的铃音信息接口文件。");
                msgInfo.append("没有找到当天的铃音信息接口文件。\n");
                return ColorringLoadConstants.RC_IMP_ERROR;
            }
            // 如果取到了全量文件，开始进行导入
*/            ColorContentBO.getInstance().colorringFullImport((String)nameList.get(0),ftpPath, bakPath,RefDataTime);
        }
        catch (Exception e)
        {
            synLog.error("importColorringData failed!", e) ;
            return ColorringLoadConstants.RC_IMP_ERROR ;
        }

        //走到这里说明导入成功了
        msgInfo.append("导入数据成功。\n");
        return ColorringLoadConstants.RC_SUCC;
    }

    /**
     * 得到满足条件的当前时间字符串
     * @param TIME_FORMAT String,需要的时间格式
     * @return String
     */
    private static String getCurDateTime(String TIME_FORMAT)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(new Date());
    }

}
