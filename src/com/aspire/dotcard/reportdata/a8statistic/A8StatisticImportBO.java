package com.aspire.dotcard.reportdata.a8statistic;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.TopDataConfig;
import com.aspire.dotcard.reportdata.gcontent.ImportReportDataBO;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.StringTool;
import com.enterprisedt.net.ftp.FTPClient;
/**
 * 终端音乐报表统计导入的BO类
 * @author zhangwei
 *
 */
public class A8StatisticImportBO
{
	private static final JLogger LOG = LoggerFactory.getLogger(A8StatisticImportBO.class);
	
	/**
     * 报表专用日志
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
	
	private static A8StatisticImportBO DAO=new A8StatisticImportBO();
	public static A8StatisticImportBO getInstance()
	{
		return DAO;
	}
	/**
	 * 下载a8歌曲统计数据文件
	 * @return 文件列表。
	 * @throws Exception
	 */
	public List downLoadReportFile()throws Exception
	{
		String ip=TopDataConfig.get("TopDataFTPServerIP");
		int port=Integer.parseInt(TopDataConfig.get("TopDataFTPServerPort"));
		String user=TopDataConfig.get("TopDataFTPServerUser");
		String password = TopDataConfig.get("TopDataFTPServerPassword");
		String ftpDir=PublicUtil.getEndWithSlash(TopDataConfig.get("A8_FtpDir"));
		String pasPath = PublicUtil.getEndWithSlash(TopDataConfig.get("TopDataSiteDir"));
		Calendar date=Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, -1);
		String dateString = PublicUtil.getDateString(date.getTime(), "yyyyMMdd");
		String fileNameRegex="gmcc_song_play_"+dateString+"_[0-9]{6}+\\.txt";//gmcc_song_play_YYYYMMDD_xxxxxx.txt
		String localDir=ServerInfo.getAppRootPath()+File.separator+pasPath+ftpDir;
		
		FTPClient ftp = null;
		try
		{
			ftp= PublicUtil.getFTPClient(ip, port, user, password, ftpDir);
			//用于输出debug日志，保存本次任务同步的文件名
			//先确保本地目录已经创建了。
			IOUtil.checkAndCreateDir(localDir);			
			//存放获取到的文件列表的list
			ArrayList fileList = new ArrayList();
			String[] Remotefiles = ftp.dir();
			//根据正则表达式来获取匹配的文件，并保存。
			if (LOG.isDebugEnabled())
			{
				LOG.debug("开始下载A8报表文件："+fileNameRegex);
			}
			for (int j = 0; j < Remotefiles.length; j++)
			{
				String remotefileName = Remotefiles[j]; 
				if (remotefileName.matches(fileNameRegex))
				{
					String absolutePath = localDir  + remotefileName;
					absolutePath = absolutePath.replace('\\', '/');
					ftp.get(absolutePath, Remotefiles[j]);
					fileList.add(absolutePath);
					if (LOG.isDebugEnabled())
					{
						LOG.debug("成功下载文件：" + absolutePath);
					}
				}
			}
			
			return fileList;
		} catch(Exception e)
		{
			throw new BOException(e,DataSyncConstants.EXCEPTION_FTP);
		}
		finally
		{
			if (ftp != null)
			{
				try
				{
					ftp.quit();
				} 
				catch (Exception e)
				{
				}
			}

		}
	}
	/**
	 * 保存统计信息到
	 * @param fileNameList
	 * @return
	 * @throws Exception
	 */
	public int saveCategoryStatistic(List fileNameList)throws Exception
	{
		int successCount=0;
        for(int i=0;i<fileNameList.size();i++)
        {
        	successCount+=processFileData((String)fileNameList.get(i));
        }
        return successCount;
       
	}
	 /**
     * 定购量数据插入到数据库
     *
     * @param fileName String
     * @throws Exception
     * @return int 成功更新的次数
     */
    private int processFileData(String fileName)
                    throws Exception
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("The file name of processing is：" + fileName);
        }
        // 读取文件内容
        Object[] arrColorContent = ImportReportDataBO.readAllFileLine(fileName);
        int iDataNum = arrColorContent.length;
        if (iDataNum == 0)
        {
            LOG.debug("没有要导入的数据");
            return 0;
        }
        else
        {
            LOG.debug("一共要导入" + iDataNum + "条数据");
        }
        int successCount=0;
        // 逐行处理
        for (int i = 0; i < iDataNum; i++)
        {     
            String lineData[];
            
            try
            {
            	lineData = parseLineText(( String ) arrColorContent[i]);
            	long count=Long.parseLong(lineData[2]);
            	int result=A8StatisticImportDAO.getInstance().updateCategoryStatistic(lineData[1], count);
            	successCount+=result;
            }catch(NumberFormatException e)
            {
            	LOG.error("该"+(i+1)+"行导入失败:"+arrColorContent[i]+" 统计必须是整数");
            	REPORT_LOG.error("该"+(i+1)+"行导入失败:"+arrColorContent[i]+" 统计必须是整数");
            }catch(DAOException e)
            {
            	LOG.error("该"+(i+1)+"行导入失败:"+arrColorContent[i]+" 数据库出错",e);
            	REPORT_LOG.error("该"+(i+1)+"行导入失败:"+arrColorContent[i]+" 数据库出错",e);
            } catch(BOException e)
            {
            	LOG.error("该"+(i+1)+"行导入失败:"+arrColorContent[i],e);
            	REPORT_LOG.error("该"+(i+1)+"行导入失败:"+arrColorContent[i],e);
            }
        }
        return successCount;
    }
    /**
     * 解析每一行数据到数组中
     */
    private String[]  parseLineText(String recordContent)throws BOException
    {

		String[] r = new String[3];

		if (StringTool.lengthOfHZ(recordContent) != 28)
		{
			throw new BOException("文件内容格式不正确!，总长度不等于28个字符");
		}
		int[] l = { 8, 9, 11 };
		int startIndex = 0;//字段开始位置
		for (int i = 0; i < 3; i++)
		{
			r[i] = StringTool.formatByLen(recordContent.substring(startIndex), l[i], "").trim();
			startIndex+=l[i];
		}
		return r;
	}
        

}
