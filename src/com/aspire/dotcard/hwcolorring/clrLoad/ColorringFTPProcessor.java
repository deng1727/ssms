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
 * <p>Title:彩铃导入的数据文件FTP处理类，用于从华为的彩铃平台获取所有的数据文件 </p>
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
     * 彩铃网关日志引用
     */
    private static final JLogger synLog = LoggerFactory.getLogger("colorring.syn") ;

    /**
     * 工具类，因此构造方法私有
     */
    private ColorringFTPProcessor()
    {}

    /**
     * 彩铃数据文件ftp获取的方法
     * @param FullDataTime String
     * @param RefDataTime String
     * @nameList 下载的文件名
     * @return int
     */
    public static int doit(String FullDataTime,String RefDataTime,List nameList)
    {
        synLog.error("ColorringFTPProcessor is beginning......") ;
        // 从配置项读取ftp连接传输的端口号
        String FTPServerPort = ColorringConfig.get("FTPServerPort");
        // 从配置项读取ftp连接的地址（即华为平台提供的彩铃数据地址）
        String FTPServerIP = ColorringConfig.get("FTPServerIP");
        // 从配置项读取FTP服务器的登录用户名
        String FTPServerUser = ColorringConfig.get("FTPServerUser");
        // 从配置项读取FTP服务器的登录密码
        String FTPServerPassword = ColorringConfig.get("FTPServerPassword");
        // 从配置项读取FTP服务器上铃音信息接口文件的存放路径
        String LYXXDir = ColorringConfig.get("LYXXDir");
        // 获取系统中彩铃数据文件保存的路径
        String ColorringSiteDir = ColorringConfig.getColorDataFilePath();
        File file = new File(ColorringSiteDir);
        // 判断目录是否存在，如不存在，则创建
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
            // 初始化ftp客户端
            if (synLog.isDebugEnabled())
            {
                synLog.debug("Construct FTPClient...");
            }
            ftp = new FTPClient(FTPServerIP, Integer.parseInt(FTPServerPort));
            //初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
            ftp.setConnectMode(FTPConnectMode.PASV);

            //使用给定的用户名、密码登陆ftp
            if (synLog.isDebugEnabled())
            {
                synLog.debug("login to FTPServer...");
            }
            ftp.login(FTPServerUser, FTPServerPassword);
            //设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
            ftp.setType(FTPTransferType.BINARY);
            ftp.chdir(LYXXDir);
            //遍历远程目录中的文件名列表
            String[] Remotefiles = ftp.dir();
            //过滤文件列表数组，只获取与当前日期匹配的文件名列表
            ArrayList fileList = new ArrayList();
            for (int i = 0; i < Remotefiles.length; i++)
            {
                if (Remotefiles[i].startsWith(RefDataTime))
                {
                    fileList.add(Remotefiles[i]);
                }
            }
            //设置一个标志信息FoundFlag
            boolean FoundFlag = false;
            if(synLog.isDebugEnabled())
            {
                synLog.debug("the FullDataTime is ::::" + FullDataTime) ;
                synLog.debug("the RefDataTime is ::::" + RefDataTime) ;
            }
            //遍历文件名列表，获取当然日期最近整点时间的彩铃数据文件
            for (;;)
            {
                for (int j = 0; j < fileList.size(); j++)
                {
                    if (synLog.isDebugEnabled())
                    {
                        synLog.debug("check ftp remote file:" + fileList.get(j));
                    }
                    String  fileName = (String)fileList.get(j);
                    // 判断文件类型以及查找当天生成的接口文件
                    if (fileName.trim().toLowerCase().endsWith(".txt")
                        && fileName.substring(0, 11).equals(FullDataTime + "L"))
                    {
                        synLog.error("Transfer is beginning ......");
                        // 开始文件传输,将远程铃音信息接口文件传输到本地文件夹ColorringSiteDir
                        ftp.get(ColorringSiteDir + File.separator + fileName, fileName);
                        nameList.add(fileName);//
                        //获取到彩铃文件后退出ftp链接
                        ftp.quit();
                        FoundFlag = true;
                        synLog.error("Transfer is end ......");
                        // 找到最新的铃音信息接口文件取到后跳出循环
                        break;
                    }
                }
                // 如果当前时间是00点（24小时制，小时显示格式00,01,...,22,23）并且又没有找到需要的接口文件或者已经找到需要的接口文件，跳出循环查找
                if (FullDataTime.equals(RefDataTime + "00")||FoundFlag)
                {
                    break;
                }
                int tempTime = Integer.parseInt(FullDataTime);
                FullDataTime = String.valueOf(--tempTime);
            }
            // 没有取到铃音信息接口文件，不进行后续的导入数据操作，直接返回。
            if (!FoundFlag)
            {
                //FoundFlag==false时，说明没有找到需要的全量数据：铃音信息接口文件
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
                synLog.error("ftp退出时发生异常！");
            }
            synLog.error("ColorringFTPProcessor.doit() failed!",e);
            return ColorringLoadConstants.RC_FTP_GETFULLDATAFILE_ERROR;
        }

        return ColorringLoadConstants.RC_SUCC;
    }

}
