package com.aspire.dotcard.reportdata.appstatistic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
public class AppStatisticImportBO
{
	private static final JLogger LOG = LoggerFactory.getLogger(AppStatisticImportBO.class);
	
	/**
     * 报表专用日志
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
	
	private static AppStatisticImportBO DAO=new AppStatisticImportBO();
	public static AppStatisticImportBO getInstance()
	{
		return DAO;
	}
	/**
	 * 下载a8歌曲统计数据文件
	 * @return 文件列表。
	 * @throws Exception
	 */
	public List downLoadReportFile(String ftpDir,String fileNameRegex)throws Exception
	{
		String ip=TopDataConfig.get("TopDataFTPServerIP");
		int port=Integer.parseInt(TopDataConfig.get("TopDataFTPServerPort"));
		String user=TopDataConfig.get("TopDataFTPServerUser");
		String password = TopDataConfig.get("TopDataFTPServerPassword");
		//String ftpDir=PublicUtil.getEndWithSlash(TopDataConfig.get("A8_FtpDir"));
		String pasPath = PublicUtil.getEndWithSlash(TopDataConfig.get("TopDataSiteDir"));
		//Calendar date=Calendar.getInstance();
		//date.add(Calendar.DAY_OF_MONTH, -1);
		//String dateString = PublicUtil.getDateString(date.getTime(), "yyyyMMdd");
		//String fileNameRegex="gmcc_song_play_"+dateString+"_[0-9]{6}+\\.txt";//gmcc_song_play_YYYYMMDD_xxxxxx.txt
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
				LOG.debug("开始下载应用报表文件："+fileNameRegex);
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
	 * 推荐应用日统计数据
	 */
	public int saveAppLastStatistic(List fileNameList)throws Exception
	{
		int successCount=0;
        for(int i=0;i<fileNameList.size();i++)
        {
        	successCount+=processFileDataAppLast((String)fileNameList.get(i));
        }
        return successCount;
       
	}
	
	/**
	 * 保存统计信息到
	 * @param fileNameList
	 * @return
	 * @throws Exception
	 * 推荐应用日统计数据
	 */
	public int saveAppRecommondStatistic(List fileNameList)throws Exception
	{
		int successCount=0;
        for(int i=0;i<fileNameList.size();i++)
        {
        	successCount+=processFileDataAppRecommond((String)fileNameList.get(i));
        }
        return successCount;
       
	}
	
	
	/**
	 * 保存统计信息到
	 * @param fileNameList
	 * @return
	 * @throws Exception
	 * 最新应用日统计数据
	 */
	public int saveAppBaseStatistic(String type,List fileNameList)throws Exception
	{
		String updateSql = "reportdata.applaststatistic.updateAppLastStatistic.UPDATE";
		String insertSql  = "reportdata.applaststatistic.updateAppLastStatistic.INSERT";
		if(type != null && type.equals("latest")){
			 updateSql = "reportdata.applaststatistic.updateAppLastStatistic.UPDATE";
			insertSql  = "reportdata.applaststatistic.updateAppLastStatistic.INSERT";			
		}else if(type != null && type.equals("recommend")){
			 updateSql = "reportdata.apprecommondstatistic.updateAppRecommondStatistic.UPDATE";
				insertSql  = "reportdata.apprecommondstatistic.updateAppRecommondStatistic.INSERT";
		}else if(type != null && type.equals("competitive")){
			 updateSql = "reportdata.appcompetitivestatistic.updateAppCompetitiveStatistic.UPDATE";
				insertSql  = "reportdata.appcompetitivestatistic.updateAppCompetitiveStatistic.INSERT";
		}
		
		
		int successCount=0;
        for(int i=0;i<fileNameList.size();i++)
        {
        	successCount+=processFileDataAppBase(updateSql,insertSql,(String)fileNameList.get(i));
        }
        return successCount;
       
	}
	 /**
     * 从报表同步黑名单更新到数据库
     *
     * @param fileName String
     * @throws Exception
     * @return int 成功更新的次数
     */
	
	public int saveContentBlackList(List fileNameList) {
		String updateSql = "reportdata.BlackListImportBO.saveContentBlackList().UPDATE";
		String insertSql = "reportdata.BlackListImportBO.saveContentBlackList().INSERT";
		int successCount = 0;
		LOG.debug("fileNameList=" + fileNameList);
		for (int i = 0; i < fileNameList.size(); i++) {
			String fileName = (String) fileNameList.get(i);
			List oneFileDatas = getDatasByFileName(fileName);
			for (int j = 0; j < oneFileDatas.size(); j++) {
				String oneLineData = (String) oneFileDatas.get(j);
				Object splitOneLineData[];
				try {
					int l[] = { 8,8, 12,1 };
					splitOneLineData = parseLineText(oneLineData.trim(), l);
					if (splitOneLineData != null
							&& splitOneLineData.length == 4) {
						//将contentid放在最后，保证update 和insert都能顺利进行
						Object contentid = 	splitOneLineData[2];
						splitOneLineData[2] = splitOneLineData[3];
						splitOneLineData[3] = contentid;
						successCount += AppStatisticImportDAO.getInstance()
								.updateAppBaseStatistic(updateSql, insertSql,
										splitOneLineData, splitOneLineData);
					} else {
						LOG.error("该" + (j + 1) + "行导入失败:" + oneLineData);
						REPORT_LOG
								.error("该" + (j + 1) + "行导入失败:" + oneLineData);
					}
				} catch (DAOException e) {
					e.printStackTrace();
					LOG.error(
							"该" + (j + 1) + "行导入失败:" + oneLineData + " 数据库出错",
							e);
					REPORT_LOG.error("该" + (j + 1) + "行导入失败:" + oneLineData
							+ " 数据库出错", e);
				} catch (BOException e) {
					e.printStackTrace();
					LOG.error("该" + (j + 1) + "行导入失败:" + oneLineData, e);
					REPORT_LOG.error("该" + (j + 1) + "行导入失败:" + oneLineData, e);
				}
			}
		}
		return successCount;
	}
	
	

    /**
     * 获取一个文件的内容
     * @param fileName
     * @return
     */
    public List getDatasByFileName(String fileName){
    	//List allLineDate = null;
    	List arrColorContent;
		try {
			arrColorContent = this.readAllFileLine(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
         
    	return arrColorContent;
    }
	
	 /**
     * 最新应用更新到数据库
     *
     * @param fileName String
     * @throws Exception
     * @return int 成功更新的次数
     */
    private int processFileDataAppBase(String updateSql,String insertSql,String fileName)
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
            	double count=Double.parseDouble(lineData[2]);
            	int result=AppStatisticImportDAO.getInstance().updateAppBaseStatistic(updateSql,insertSql,lineData[1],lineData[0], count);
            	successCount+=result;
            }catch(NumberFormatException e)
            {
            	LOG.error("该"+(i+1)+"行导入失败:"+arrColorContent[i]+" 统计必须是double型数字");
            	REPORT_LOG.error("该"+(i+1)+"行导入失败:"+arrColorContent[i]+" 统计必须是double型数字");
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
     * 最新应用更新到数据库
     *
     * @param fileName String
     * @throws Exception
     * @return int 成功更新的次数
     */
    private int processFileDataAppLast(String fileName)
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
            	double count=Double.parseDouble(lineData[2]);
            	int result=AppStatisticImportDAO.getInstance().updateAppLastStatistic(lineData[1],lineData[0], count);
            	successCount+=result;
            }catch(NumberFormatException e)
            {
            	LOG.error("该"+(i+1)+"行导入失败:"+arrColorContent[i]+" 统计必须是double型数字");
            	REPORT_LOG.error("该"+(i+1)+"行导入失败:"+arrColorContent[i]+" 统计必须是double型数字");
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
     * 推荐应用更新到数据库
     *
     * @param fileName String
     * @throws Exception
     * @return int 成功更新的次数
     */
    private int processFileDataAppRecommond(String fileName)
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
            	double count=Double.parseDouble(lineData[2]);
            	int result=AppStatisticImportDAO.getInstance().updateAppRecommondStatistic(lineData[1],lineData[0], count);
            	successCount+=result;
            }catch(NumberFormatException e)
            {
            	LOG.error("该"+(i+1)+"行导入失败:"+arrColorContent[i]+" 统计必须是double型数字");
            	REPORT_LOG.error("该"+(i+1)+"行导入失败:"+arrColorContent[i]+" 统计必须是double型数字");
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

		if (StringTool.lengthOfHZ(recordContent) != 31)
		{
			throw new BOException("文件内容格式不正确!，总长度不等于31个字符");
		}
		int[] l = { 8, 12, 11 };
		int startIndex = 0;//字段开始位置
		for (int i = 0; i < 3; i++)
		{
			r[i] = StringTool.formatByLen(recordContent.substring(startIndex), l[i], "").trim();
			startIndex+=l[i];
		}
		return r;
	}
    
    /**
     * 解析每一行数据到数组中
     */
    private String[]  parseLineText(String recordContent ,int[]l)throws BOException
    {

    	int len = l.length;
    	String[] r = new String[len];
        int totallen = 0;
        for(int i =0;i < len; i ++){
        	totallen += l[i];
        }
		if (StringTool.lengthOfHZ(recordContent) != totallen)
		{
			throw new BOException("文件内容格式不正确!，总长度不等于"+totallen+"个字符"+recordContent);
		}
		//int[] l = { 8, 12, 11 };
		
		int startIndex = 0;//字段开始位置
		for (int i = 0; i < len; i++)
		{
			r[i] = StringTool.formatByLen(recordContent.substring(startIndex), l[i], "").trim();
			startIndex+=l[i];
		}
		return r;
	}
    /**
     * 解析每一行数据到数组中
     * @param recordContent 读入的一行数据
     * @param rexg 间隔字符
     * @param size 一行的字段数
     * @return
     * @throws BOException
     */
    private String[]  parseLineTextByRexg(String recordContent,String rexg,int size)throws BOException
    {
		String[] r = null;
		if (recordContent == null || recordContent.length() <= 0) {
			LOG.error("文件内容格式不正确!，行为空" + recordContent);
			throw new BOException("文件内容格式不正确!，行为空" + recordContent);
		}
		r = recordContent.split(rexg);
		if (r.length != size) {
			LOG.error("文件行内容" + recordContent + " 格式不正确!，根据分隔符：" + rexg
					+ "字段数不为" + size);
			throw new BOException("文件行内容" + recordContent + " 格式不正确!，根据分隔符："
					+ rexg + "字段数不为" + size);
		}
		return r;
	}  
    /**
     * 读取文件中的全部数据，组成一个String
     *
     * @param fileName ,文件的全路径名称
     * @return String
     * @throws IOException
     */
    public static List readAllFileLine(String fileName) throws IOException
    {
        List list = new ArrayList();
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null && line.length() > 0)
            {
                list.add(line);
                line = br.readLine();
            }
        }
        catch(IOException ie)
        {
        	LOG.error(ie);
            throw ie;
        }
        finally{
            br.close();
            fr.close();
        }

        return list;
    }
    
    /**
     * 用dblink方式更新小编推荐数据
     * @return 更新条数 
     */
    public int updateRecommendDate()
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("xiaobian Recommend update date");
        }

        // 更新记录条数
        int result = 0;
        
        try
        {
            result = AppStatisticImportDAO.getInstance().updateRecommendDate();
        }
        catch (DAOException e)
        {
            LOG.error("用dblink方式更新小编推荐数据时数据库出错", e);
            REPORT_LOG.error("用dblink方式更新小编推荐数据时数据库出错", e);
        }
        return result;

    }
}
