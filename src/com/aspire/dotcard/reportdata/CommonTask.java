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
 * �ա��ܡ��±������ݵ����ִ������
 * 
 * @author x_liyouli
 * 2008-03-03 patch-082
 */
public class CommonTask
{
    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(CommonTask.class);
    
    protected static JLogger topDatalog = LoggerFactory.getLogger("reportimp.client");
    
    private static final String TIME_FORMAT = "yyyyMMdd";
    
    
	public static void exec(int type)
	{ 
        // ȡ�ÿ�ʼ�ͽ���ʱ��
        String startDataTime;
        try
        {
            startDataTime = getStartDate(type);
        }
        catch (DAOException e)
        {
            topDatalog.error("��ȡ�ϴθ���ʱ��ʧ�ܣ��������ݿ��쳣������ͬ����ֹ��ͬ������Ϊ��"+getTypeName(type)+ ", errorMessage="+ e);
            logger.error(e);
            return ;
        }
        if(null == startDataTime || "".equals(startDataTime))
        {
        	topDatalog.error(getTypeName(type)+"�����������:���������Ѿ�ͬ������!");
        	return;
        }
        String endDataTime = getEndDate(type);
        StringBuffer msgInfo = new StringBuffer();
                        
//		 ���ȣ���Report Server����ftp��ȡ���ļ�
        int result = TopDataFTPProcessor.getTopDataFromFTP(startDataTime,endDataTime,type);
        
        if (result == TopDataConstants.TD_FTP_DATAFILE_NOTFOUND)
        {
            // ����ӿ��ļ�������
            logger.error(getTypeName(type)+"�ӿ������ļ������ڣ��������ݵ����Զ�������ֹ��\n");
            topDatalog.error(getTypeName(type)+"�ӿ������ļ������ڣ��������ݵ����Զ�������ֹ��\n");
            msgInfo.append(getTypeName(type)+"�ӿ������ļ������ڣ��������ݵ����Զ�������ֹ��\n");
            sendMail(msgInfo.toString(),getTypeName(type));//�����ʼ�
            return;
        }
        else if (result == TopDataConstants.TD_FTP_GETDATAFILE_ERROR)
        {
            // �����ȡ�ӿ��ļ��д��󣬱��ε���ֱ�ӽ���
            logger.error(getTypeName(type)+"�ӿڻ�ȡ�����ļ��д��󣬱������ݵ����Զ�������ֹ��\n");
            topDatalog.error(getTypeName(type)+"�ӿڻ�ȡ�����ļ��д��󣬱������ݵ����Զ�������ֹ��\n");
            msgInfo.append(getTypeName(type)+"�ӿڻ�ȡ�����ļ��д��󣬱������ݵ����Զ�������ֹ��\n");
            sendMail(msgInfo.toString(),getTypeName(type));//�����ʼ�
            return;
        }
       
        
        // ��������,�Ѵ�FTP��ȡ���������ݣ��ŵ����ݿ���
        result = importTopData(msgInfo, startDataTime, endDataTime,type);
        
        if (result == TopDataConstants.TD_IMP_ERROR)
        {
            // �������ݴ���
            logger.error(msgInfo);
            topDatalog.error(msgInfo);
            return;
        }

        // ����ִ�гɹ�
        msgInfo.append(getTypeName(type)+"����ִ�гɹ���\n");
        topDatalog.error(getTypeName(type)+"����ִ�гɹ���\n");
        logger.info(msgInfo);
	}
	
    /**
     * ��FTP��ȡ���ݺ󣬵������ݵ����ݿ�ķ��� ���а��������content_pay_w_20070129_000001.txt �͵㲥����
     * content_visit_w_20070129_000001.txt
     * 
     * @param msgInfo
     * @param RefDataTime
     * @return
     */
    private static int importTopData(StringBuffer msgInfo, String startDataTime, String endDataTime,int type)
    {
    	String lastDate = "0";
    	//��ȡϵͳ�����������ļ������·��
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
                // ��ӡ�����а�����ftp·���ľ���·��
                logger.debug("the productDataimp data ftp abslute address is :" + ftpPath);
            }                   
            // ���Ŀ¼�������򴴽�Ŀ¼
            IOUtil.checkAndCreateDir(ftpPath);
            
            // ����ftp·���ľ��Ե�ַ
            File ftpFile = new File(ftpPath);
            String[] files = ftpFile.list();
            // 0 ,�ܴ���������1���ɹ�����������2��ʧ�ܴ�������
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
//              �������ݿ����(�㲥)������
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
                logger.error("û���ҵ���Ҫ�Ľӿ��ļ������ļ���ʽ����type="+ getTypeName(type));
                msgInfo.append("û���ҵ���Ҫ�Ľӿ��ļ������ļ���ʽ����type="+ getTypeName(type) + "\n");
                return TopDataConstants.TD_IMP_ERROR;
            }else{
            	 //msgInfo
                if(results != null && results.length == 3 && results[0] != null && results[0].intValue() > 0){
                	if(results[0] == null){
                		results[1] = new Integer(0);
                		results[2] = new Integer(0);
                	}
                	msgInfo.append(getTypeName(type)+"����");
                	msgInfo.append("<br>");
                	msgInfo.append("������"+results[0]+"�����ݣ��ɹ�����"+results[1]+"�У�ʧ�ܴ���"+results[2]+"��.");
                }
            }
        }
        catch (Exception e)
        {
            logger.error("importTopData failed!", e);
            msgInfo.append(getTypeName(type)+"����ʧ��,���ݿ��쳣���������ݵ����Զ�������ֹ��\n");
            topDatalog.error(getTypeName(type)+"����ʧ��,���ݿ��쳣���������ݵ����Զ�������ֹ��\n");
            sendMail(msgInfo.toString(),getTypeName(type));//�����ʼ�
            return TopDataConstants.TD_IMP_ERROR;
        }
       
        // �ߵ�����˵������ɹ���
        msgInfo.append(getTypeName(type)+"����������ɡ�\n");
        sendMail(msgInfo.toString(),getTypeName(type));//�����ʼ�
        //�������ļ������ʱ��д�����ݿ���
        try
        {
            ImportReportDataDAO.getInstance().updateLastDate(type, lastDate);
        	topDatalog.error(getTypeName(type)+"������ʱ��д�����ݿ���,lastDate:" + lastDate);
        }
        catch(Exception e)
        {
        	logger.error(getTypeName(type)+"������ʱ��д�����ݿ����쳣, errorMessage=" + e);
        	topDatalog.error(getTypeName(type)+"������ʱ��д�����ݿ����쳣, errorMessage="+ e);
        }
        return TopDataConstants.TD_SUCC;
    }

    /**
     * �õ���Ҫͬ���ļ��Ŀ�ʼʱ���ַ���
     * 
     * @return String
     */
    private static String getStartDate(int type) throws DAOException
    {
        Calendar c = Calendar.getInstance();
        //�����ݿ���ȡ��type��Ӧ�����һ�γɹ��������ݵ�����
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
	    	//�����ܺ���,��Ҫ�Ƚ�ʱ��,�ж��Ƿ��Ѿ������������
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
     * �õ���Ҫͬ���ļ��Ľ���ʱ���ַ���
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
     * �õ����������ĵ�ǰʱ���ַ���
     * 
     * @param TIME_FORMAT String,��Ҫ��ʱ���ʽ
     * @return String
     */
    private static String getCurDateTime(String TIME_FORMAT)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(new Date());
    }
    
    /**
     * �Ƚϸ�����ʱ���Ƿ������ܵ���һ������֮��,�������ʱ��Ϊ��,���ؼ�
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
    	//ȡ������һ�����յ�����
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
     * �Ƚϸ�����ʱ���Ƿ����ϸ�������,�������ʱ��Ϊ��,���ؼ�
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
    	//ȡ�����¿�ʼ�ͽ���������
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
     * ȡdate��list�нϴ���Ǹ�����
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
     * ����yyyyMMdd���ַ������������ֵ��1��
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
            return "�ղ�Ʒ��Ӫ��������";
        }else if(TopDataConstants.CONTENT_TYPE_WEEK==type)
        {
            return "�ܲ�Ʒ��Ӫ��������";
        }else if(TopDataConstants.CONTENT_TYPE_MONTH==type)
        {
            return "�²�Ʒ��Ӫ��������";
        }else
        {
            return "";
        }
        
    }
    
    
	 /**
	 * �����ʼ�
	 * 
	 * @param mailContent,�ʼ�����
	 */
    private static  void sendMail(String mailContent,String subject)
    {
        if (logger.isDebugEnabled())
        {
        	logger.debug("sendMail(" + mailContent + ")");
        }
        // �õ��ʼ�����������
        String[] mailTo = MailConfig.getInstance().getMailToArray();
        //String subject = "��(��)(��)������������ͳ�����ݱ�����";
        if (logger.isDebugEnabled())
        {
        	logger.debug("mailTo Array is:" + Arrays.asList(mailTo));
        	logger.debug("mail subject is:" + subject);
        	logger.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailTo);
    }
}
