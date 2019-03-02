package com.aspire.dotcard.reportdata;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.gcontent.ImportReportDataBO;
import com.aspire.dotcard.reportdata.gcontent.ImportReportDataDAO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * 日、周、月报表数据导入的执行任务
 * 
 * @author x_liyouli
 * 2008-03-03 patch-082
 */
public class CommonTask
{
    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(CommonTask.class);
    
    protected static JLogger topDatalog = LoggerFactory.getLogger("reportimp.client");
    
    private static final String TIME_FORMAT = "yyyyMMdd";
    
    
	public static void exec(int type)
	{ 
        // 取得开始和结束时间
        String startDataTime;
        try
        {
            startDataTime = getStartDate(type);
        }
        catch (DAOException e)
        {
            topDatalog.error("获取上次更新时间失败，出现数据库异常，本次同步终止，同步类型为："+getTypeName(type)+ ", errorMessage="+ e);
            logger.error(e);
            return ;
        }
        if(null == startDataTime || "".equals(startDataTime))
        {
        	topDatalog.error(getTypeName(type)+"导入任务结束:上期数据已经同步过了!");
        	return;
        }
        String endDataTime = getEndDate(type);
        StringBuffer msgInfo = new StringBuffer();
                        
//		 首先，到Report Server上用ftp获取到文件
        int result = TopDataFTPProcessor.getTopDataFromFTP(startDataTime,endDataTime,type);
        
        if (result == TopDataConstants.TD_FTP_DATAFILE_NOTFOUND)
        {
            // 如果接口文件不存在
            logger.error(getTypeName(type)+"接口数据文件不存在，本次数据导入自动任务终止。\n");
            topDatalog.error(getTypeName(type)+"接口数据文件不存在，本次数据导入自动任务终止。\n");
            msgInfo.append(getTypeName(type)+"接口数据文件不存在，本次数据导入自动任务终止。\n");
            sendMail(msgInfo.toString(),getTypeName(type));//发送邮件
            return;
        }
        else if (result == TopDataConstants.TD_FTP_GETDATAFILE_ERROR)
        {
            // 如果获取接口文件有错误，本次导入直接结束
            logger.error(getTypeName(type)+"接口获取数据文件有错误，本次数据导入自动任务终止。\n");
            topDatalog.error(getTypeName(type)+"接口获取数据文件有错误，本次数据导入自动任务终止。\n");
            msgInfo.append(getTypeName(type)+"接口获取数据文件有错误，本次数据导入自动任务终止。\n");
            sendMail(msgInfo.toString(),getTypeName(type));//发送邮件
            return;
        }
       
        
        // 导入数据,把从FTP获取过来的数据，放到数据库中
        result = importTopData(msgInfo, startDataTime, endDataTime,type);
        
        if (result == TopDataConstants.TD_IMP_ERROR)
        {
            // 导入数据错误
            logger.error(msgInfo);
            topDatalog.error(msgInfo);
            return;
        }

        // 任务执行成功
        msgInfo.append(getTypeName(type)+"任务执行成功！\n");
        topDatalog.error(getTypeName(type)+"任务执行成功！\n");
        logger.info(msgInfo);
	}
	
    /**
     * 从FTP获取数据后，导入数据到数据库的方法 排行榜访问量。content_pay_w_20070129_000001.txt 和点播数据
     * content_visit_w_20070129_000001.txt
     * 
     * @param msgInfo
     * @param RefDataTime
     * @return
     */
    private static int importTopData(StringBuffer msgInfo, String startDataTime, String endDataTime,int type)
    {
    	String lastDate = "0";
    	//获取系统中排行数据文件保存的路径
        String curDate = getCurDateTime(TIME_FORMAT);
        
        boolean FoundFlag = false;
        String fileName = "";
        List maxDate = new ArrayList();
        try
        {
            String pasPath = TopDataConfig.get("TopDataSiteDir");            
            String ftpPath = TopDataConfig.getTopDataFilePath(pasPath + File.separator + curDate,type);
            if (logger.isDebugEnabled())
            {
                // 打印出排行榜数据ftp路径的绝对路径
                logger.debug("the productDataimp data ftp abslute address is :" + ftpPath);
            }                   
            // 如果目录不存在则创建目录
            IOUtil.checkAndCreateDir(ftpPath);
            
            // 构造ftp路径的绝对地址
            File ftpFile = new File(ftpPath);
            String[] files = ftpFile.list();
            // 0 ,总处理行数；1，成功处理行数；2，失败处理行数
            Integer results [] = new Integer[3];
            
            for (int i = 0; i < files.length; i++)
            {				
				if(!TopDataFTPProcessor.checkFileName(files[i], type, Long.parseLong(startDataTime), Long.parseLong(endDataTime),maxDate))
				{
					continue;
				}            	
                fileName = files[i];
                topDatalog.error("Parse file begin: " + ftpPath + File.separator + fileName);
                boolean insertSucc = false;
//              插入数据库操作(点播)定购量
                switch(type)
            	{
            		case TopDataConstants.CONTENT_TYPE_DAY:
            		case TopDataConstants.CONTENT_TYPE_WEEK:
            		case TopDataConstants.CONTENT_TYPE_MONTH:
            			insertSucc = ImportReportDataBO.getInstance().importReportData(fileName, ftpPath,type,results);
                        topDatalog.error("Parse file end: " + ftpPath + File.separator + fileName);
            			break;
            	}                
                if (logger.isDebugEnabled())
                {
                    logger.debug("the fileName is :" + fileName);
                }          
                lastDate = compareDate(lastDate,maxDate);
                if(FoundFlag || insertSucc)
                {
                	FoundFlag = true;
                }
                
               
                
            }
            if (!FoundFlag)
            {
                logger.error("没有找到需要的接口文件或者文件格式错误。type="+ getTypeName(type));
                msgInfo.append("没有找到需要的接口文件或者文件格式错误。type="+ getTypeName(type) + "\n");
                return TopDataConstants.TD_IMP_ERROR;
            }else{
            	 //msgInfo
                if(results != null && results.length == 3 && results[0] != null && results[0].intValue() > 0){
                	if(results[0] == null){
                		results[1] = new Integer(0);
                		results[2] = new Integer(0);
                	}
                	msgInfo.append(getTypeName(type)+"导入");
                	msgInfo.append("<br>");
                	msgInfo.append("共处理"+results[0]+"行数据，成功处理"+results[1]+"行；失败处理"+results[2]+"行.");
                }
            }
        }
        catch (Exception e)
        {
            logger.error("importTopData failed!", e);
            msgInfo.append(getTypeName(type)+"导入失败,数据库异常！本次数据导入自动任务终止。\n");
            topDatalog.error(getTypeName(type)+"导入失败,数据库异常！本次数据导入自动任务终止。\n");
            sendMail(msgInfo.toString(),getTypeName(type));//发送邮件
            return TopDataConstants.TD_IMP_ERROR;
        }
       
        // 走到这里说明导入成功了
        msgInfo.append(getTypeName(type)+"导入数据完成。\n");
        sendMail(msgInfo.toString(),getTypeName(type));//发送邮件
        //将数据文件的最大时间写到数据库中
        try
        {
            ImportReportDataDAO.getInstance().updateLastDate(type, lastDate);
        	topDatalog.error(getTypeName(type)+"最后更新时间写到数据库中,lastDate:" + lastDate);
        }
        catch(Exception e)
        {
        	logger.error(getTypeName(type)+"最后更新时间写到数据库中异常, errorMessage=" + e);
        	topDatalog.error(getTypeName(type)+"最后更新时间写到数据库中异常, errorMessage="+ e);
        }
        return TopDataConstants.TD_SUCC;
    }

    /**
     * 得到需要同步文件的开始时间字符串
     * 
     * @return String
     */
    private static String getStartDate(int type) throws DAOException
    {
        Calendar c = Calendar.getInstance();
        //从数据库中取得type对应的最近一次成功导入数据的日期
        String lastDate = ImportReportDataDAO.getInstance().queryLastDate(type);
        
    	switch (type)
    	{
	    	case TopDataConstants.CONTENT_TYPE_DAY:
	    	{
	    		if("".equals(lastDate) || null == lastDate)
	    		{
	    			c.add(Calendar.DATE,-1);
	    			break;
	    		}
	    		else
	    		{
	    		    c.add(Calendar.DATE,-1);
	    		    if(lastDate.equals(PublicUtil.getDateString(c.getTime(), TIME_FORMAT)))
                    {
                        return null;
                    }
	    			return addDate(lastDate);
	    		}
	    	}
	    	//对于周和月,需要比较时间,判断是否已经导入过数据了
	    	case TopDataConstants.CONTENT_TYPE_WEEK:
	    	{	    			
    			if(scopeWeek(lastDate))
    			{
    				return null;
    			}
    			else
    			{
    				int temp = c.get(Calendar.DAY_OF_WEEK);
    				if(Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK))
    				{
    					temp = 8;
    				}
    				c.add(Calendar.DATE, Calendar.MONDAY - temp - 7);
		    		break;
    			}	    			    		
	    	}
	    	case TopDataConstants.CONTENT_TYPE_MONTH:
	    	{
	    		if(scopeMonth(lastDate))
	    		{
	    			return null;
	    		}
	    		else
	    		{
	    			c.add(Calendar.MONTH, -1);
		    		c.add(Calendar.DATE, 1-c.get(Calendar.DAY_OF_MONTH));
		    		break;
	    		}
	    	}
    	}        
        return new SimpleDateFormat(TIME_FORMAT).format(c.getTime());
    }

    /**
     * 得到需要同步文件的结束时间字符串
     * 
     * @return String
     */
    private static String getEndDate(int type)
    {
        Calendar c = Calendar.getInstance();
        
    	switch (type)
    	{
	    	case TopDataConstants.CONTENT_TYPE_DAY:
	    	{
	    		c.add(Calendar.DATE,-1);
	    		break;
	    	}
	    	case TopDataConstants.CONTENT_TYPE_WEEK:
	    	{	    		
	    		int temp = c.get(Calendar.DAY_OF_WEEK);
				if(Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK))
				{
					temp = 8;
				}
				c.add(Calendar.DATE, Calendar.MONDAY - temp - 1);
	    		break;
	    	}
	    	case TopDataConstants.CONTENT_TYPE_MONTH:
	    	{
	    		c.add(Calendar.MONTH, -1);
	    		c.add(Calendar.DATE, 0-c.get(Calendar.DAY_OF_MONTH) + c.getActualMaximum(Calendar.DAY_OF_MONTH));	    		
	    		break;
	    	}
    	}
        return new SimpleDateFormat(TIME_FORMAT).format(c.getTime());
    }

    /**
     * 得到满足条件的当前时间字符串
     * 
     * @param TIME_FORMAT String,需要的时间格式
     * @return String
     */
    private static String getCurDateTime(String TIME_FORMAT)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(new Date());
    }
    
    /**
     * 比较给定的时间是否在上周的周一和周日之间,如果给定时间为空,返回假
     * @param date
     * @return
     */
    private static boolean scopeWeek(String date)
    {
    	if("".equals(date) || null == date)
    	{
    		return false;
    	}
    	
    	long time = Long.parseLong(date);
    	//取得上周一和周日的日期
    	Calendar c = Calendar.getInstance();
    	int temp = c.get(Calendar.DAY_OF_WEEK);
		if(Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK))
		{
			temp = 8;
		}
		c.add(Calendar.DATE, Calendar.MONDAY - temp - 7);
		String beginDay = new SimpleDateFormat(TIME_FORMAT).format(c.getTime());
		long begin = Long.parseLong(beginDay);
		
		c = Calendar.getInstance();
		temp = c.get(Calendar.DAY_OF_WEEK);
		if(Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK))
		{
			temp = 8;
		}
		c.add(Calendar.DATE, Calendar.MONDAY - temp-1);
		String endDay = new SimpleDateFormat(TIME_FORMAT).format(c.getTime());
    	long end = Long.parseLong(endDay);
    	
    	return time >= begin && time <=end;
    }
    
    /**
     * 比较给定的时间是否在上个月里面,如果给定时间为空,返回假
     * @param date
     * @return
     */
    private static boolean scopeMonth(String date)
    {
    	if("".equals(date) || null == date)
    	{
    		return false;
    	}
    	
    	long time = Long.parseLong(date);
    	//取得上月开始和结束的日期
    	Calendar c = Calendar.getInstance();
		
    	c.add(Calendar.MONTH, -1);
		c.add(Calendar.DATE, 1-c.get(Calendar.DAY_OF_MONTH));
		String beginDay = new SimpleDateFormat(TIME_FORMAT).format(c.getTime());
		long begin = Long.parseLong(beginDay);
		
		c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		c.add(Calendar.DATE, 0-c.get(Calendar.DAY_OF_MONTH) + c.getActualMaximum(Calendar.DAY_OF_MONTH));	    		
		String endDay = new SimpleDateFormat(TIME_FORMAT).format(c.getTime());
    	long end = Long.parseLong(endDay);
    	
    	return time >= begin && time <=end;
    }
    
    /**
     * 取date和list中较大的那个日期
     * @param date
     * @param list
     * @return
     */
    private static String compareDate(String date,List list)
    {
    	if(null == list || list.size() == 0)
    	{
    		return date;
    	}
    	if(null == date || "".equals(date))
    	{
    		date = "0";
    	}
    	String temp = (String)list.get(0);
    	if(Long.parseLong(temp) > Long.parseLong(date))
    	{
    		return temp;
    	}
    	else
    	{
    		return date;
    	}
    }
    
    /**
     * 将“yyyyMMdd”字符串代表的日期值＋1天
     * @param str
     * @return
     */
    private static String addDate(String str)
    {
    	Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(str.substring(0,4)));
		c.set(Calendar.MONTH, Integer.parseInt(str.substring(4,6))-1);
		c.set(Calendar.DATE, Integer.parseInt(str.substring(6,8)));
		
    	c.add(Calendar.DATE, 1);
    	
		return new SimpleDateFormat(TIME_FORMAT).format(c.getTime());
    }
    
    private static String getTypeName(int type)
    {
        if(TopDataConstants.CONTENT_TYPE_DAY==type)
        {
            return "日产品运营属性数据";
        }else if(TopDataConstants.CONTENT_TYPE_WEEK==type)
        {
            return "周产品运营属性数据";
        }else if(TopDataConstants.CONTENT_TYPE_MONTH==type)
        {
            return "月产品运营属性数据";
        }else
        {
            return "";
        }
        
    }
    
    
	 /**
	 * 发送邮件
	 * 
	 * @param mailContent,邮件内容
	 */
    private static  void sendMail(String mailContent,String subject)
    {
        if (logger.isDebugEnabled())
        {
        	logger.debug("sendMail(" + mailContent + ")");
        }
        // 得到邮件接收者数组
        String[] mailTo = MailConfig.getInstance().getMailToArray();
        //String subject = "日(周)(月)下载量搜索量统计数据报表导入";
        if (logger.isDebugEnabled())
        {
        	logger.debug("mailTo Array is:" + Arrays.asList(mailTo));
        	logger.debug("mail subject is:" + subject);
        	logger.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailTo);
    }
}
