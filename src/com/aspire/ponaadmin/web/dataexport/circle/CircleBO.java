/**
 * SSMS
 * com.aspire.ponaadmin.web.dataexport.experience ExperienceBO.java
 * Jul 9, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.dataexport.circle;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.DataExportTools;
import com.aspire.ponaadmin.web.dataexport.experience.CommonExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ExperienceConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

/**
 * @author tungke
 *手动执行 圈子营销 数据导出 功能类
 */
public class CircleBO
{
	private static final JLogger LOG = LoggerFactory.getLogger(CircleBO.class);

    private List sucessList = new ArrayList();

    private List failureList = new ArrayList();

    private Date startDate;
    
    
    
    
    /**
     * 
     * 
     * 手动 圈子营销全量数据 导出
     * 飞信，139邮箱，139说客
     * 
     * 
     */
	public void fullExport(){
		
//		 TODO Auto-generated method stub
		 if (LOG.isDebugEnabled())
	        {
	            LOG.debug("开始生成圈子营销平台文件");
	        }
	        startDate = new Date();
	        sucessList.clear();
	        failureList.clear();
		 StringBuffer message = new StringBuffer("");
	        boolean result1 = false;
	        boolean result2 = false;
	        boolean result3 = false;
	      String fetionFileName =  ConfigFactory.getSystemConfig()
            .getModuleConfig("circle").getItemValue("FetionFilename");
	      String f139mailFileName =  ConfigFactory.getSystemConfig()
          .getModuleConfig("circle").getItemValue("f139mailFileName");
	      String f139skFileName =  ConfigFactory.getSystemConfig()
          .getModuleConfig("circle").getItemValue("f139skFileName");
	        
	        try
	        {
	        	//飞信数据导出
	        	String ftpFetionFileName =  DataExportTools.parseFileName(fetionFileName);
	        	String localfetionFileName =  ConfigFactory.getSystemConfig()
	            .getModuleConfig("circle").getItemValue("LOCALPATH") + File.separator +  ftpFetionFileName;
	        	List list = getAppList("001");
	        	writeToFile(list,localfetionFileName,ftpFetionFileName);	
	        	sucessList.add("飞信数据导出");
	        	result1 = true;
	        }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	        	failureList.add("飞信数据导出失败"+e);
	        	message.append("飞信数据导出失败"+e);
	        }
	        try
	        {
	        	//139邮箱数据导出
	        	String ftp139mailFileName =  DataExportTools.parseFileName(f139mailFileName);
	        	String local139mailFileName =  ConfigFactory.getSystemConfig()
	            .getModuleConfig("circle").getItemValue("LOCALPATH") + File.separator +  ftp139mailFileName;
	        	List list = getAppList("002");
	        	writeToFile(list,local139mailFileName,ftp139mailFileName);	
	        	sucessList.add("139邮箱数据导出");
	        	result1 = true;
	        }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	        	failureList.add("139邮箱数据导出失败"+e);
	        	message.append("139邮箱数据导出失败"+e);
	        }
	        try
	        {
	        	//139说客数据导出
	        	String ftp139skFileName =  DataExportTools.parseFileName(f139skFileName);
	        	String local139skFileName =  ConfigFactory.getSystemConfig()
	            .getModuleConfig("circle").getItemValue("LOCALPATH") + File.separator +  ftp139skFileName;
	        	List list = getAppList("003");
	        	writeToFile(list,local139skFileName,ftp139skFileName);	
	        	sucessList.add("139说客数据导出");
	        	result1 = true;
	        }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	        	failureList.add("139说客数据导出失败"+e);
	        	message.append("139说客数据导出失败"+e);
	        }
	        sendResultMail((result1||result2 ||result3),message.toString());  
	}
	
	   /**
     * 
     */
    protected void writeToFile(List list,String localFileName,String ftpFileName) throws BOException
    {
        	LOG.debug("list.size="+list.size());
				//DataExportTools.writeToTXTFile(this.exportedFileName,list,lineSeparator,encoding);
        		DataExportTools.writeToCSVFile(localFileName,list);
			
            LOG.info("export file=" + localFileName);
            
            copyFileToFTP(localFileName,ftpFileName);
            LOG.info("写入FTP" +ftpFileName);
        
        
    }
	 /**
     * 从榜单应用信息表中获取全部列表
     * 
     * @return
     * @throws DAOException
     */
    private List getAppList(String target) throws DAOException
    {

		ResultSet rs = null;
		List list = new ArrayList();

		String sqlCode = "com.aspire.ponaadmin.web.dataexport.circle.CircleBO.getAppList.select";
//		select g.contentid,
//	       g.name,
//	       g.wwwpropapicture2,
//	       g.wwwpropapicture3,
//	       g.picture1,
//	       c.name cname,
//	       l.relation
//	  from t_r_gcontent g, t_r_circle　l, t_r_category c, t_r_reference r
//	 where c.categoryid = l.CATEGORYID
//	   and r.categoryid = l.CATEGORYID
//	   and r.refnodeid = g.id
//	   and l.target = ?

		try
		{
			Object parms[] = {target};
			rs = DB.getInstance().queryBySQLCode(sqlCode, parms);

			while (rs.next())
			{
				String[] temp = new String[7];
				temp[0] = rs.getString("contentid");
				temp[1] = rs.getString("name");
				temp[2] = rs.getString("wwwpropapicture2");
				temp[3] = rs.getString("wwwpropapicture3");
				temp[4] = rs.getString("picture1");
				temp[5] = rs.getString("cname");
				temp[6] = rs.getString("relation");

				list.add(temp);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			 LOG.error("读取圈子营销榜单应用信息数据出错",e);
			throw new DAOException("读取圈子营销榜单应用信息数据出错", e);
		} finally
		{
			DB.close(rs);
		}

		return list;
    }
    /**
     * 写文件至FTP指定目录中
     * 
     * @throws BOException
     * 
     */
    protected void copyFileToFTP(String localfileName,String ftpFileName) throws BOException
    {

        FTPClient ftp = null;

        try
        {
            // 取得远程目录中文件列表
            ftp = getFTPClient();

            if (!"".equals( ConfigFactory.getSystemConfig()
                    .getModuleConfig("circle").getItemValue("FTPDir")))
            {
                ftp.chdir(ConfigFactory.getSystemConfig()
                        .getModuleConfig("circle").getItemValue("FTPDir"));
            }

            ftp.put(localfileName, ftpFileName);

        }
        catch (Exception e)
        {
            throw new BOException(e, DataSyncConstants.EXCEPTION_FTP);
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

    private FTPClient getFTPClient() throws IOException, FTPException
    {

        String ip = ConfigFactory.getSystemConfig()
        .getModuleConfig("circle").getItemValue("FTPServerIP");//ExperienceConfig.FTPIP;
        int port = Integer.valueOf(ConfigFactory.getSystemConfig()
        .getModuleConfig("circle").getItemValue("FTPServerPort")).intValue();//ExperienceConfig.FTPPORT;
        String user = ConfigFactory.getSystemConfig()
        .getModuleConfig("circle").getItemValue("FTPServerUser");//ExperienceConfig.FTPNAME;
        String password = ConfigFactory.getSystemConfig()
        .getModuleConfig("circle").getItemValue("FTPServerPassword");//ExperienceConfig.FTPPAS;

        FTPClient ftp = new FTPClient(ip, port);
        
        // 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
        ftp.setConnectMode(FTPConnectMode.PASV);

        // 使用给定的用户名、密码登陆ftp
        ftp.login(user, password);
        
        // 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
        ftp.setType(FTPTransferType.BINARY);

        return ftp;
    }

	  /**
		 * 发送结果邮件。
		 */
   private void sendResultMail(boolean result, String reason)
   {

       String mailTitle;
       // 发送邮件表示本次处理结束
       Date endDate = new Date();
       StringBuffer sb = new StringBuffer();
       int totalSuccessCount = sucessList.size();
       int totalFailureCount = failureList.size();
       mailTitle = "手动圈子营销数据文件生成结果";
       if (result)
       {

           sb.append("开始时间：");
           sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append(",结束时间：");
           sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append("。<h4>处理结果：</h4>");
           sb.append("<p>其中成功导出<b>");
           sb.append(totalSuccessCount);
           sb.append("</b>个文件。");
           sb.append("失败导出<b>");
           sb.append(totalFailureCount);
           sb.append("</b>个文件。");
           if (totalSuccessCount != 0)
           {
               sb.append("<p>具体信息为：<p>");

               for (int i = 0; i < sucessList.size(); i++)
               {
                   sb.append(sucessList.get(i));
                   sb.append("<br>");
               }
               sb.append("<br>");
           }

           for (int i = 0; i < failureList.size(); i++)
           {
               sb.append(failureList.get(i));
               sb.append("<br>");
           }

       }
       else
       {
           sb.append("开始时间：");
           sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append(",结束时间：");
           sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append("。<p>失败原因：<br>");
           sb.append(reason);
       }

       LOG.info(sb.toString());
       //Mail.sendMail(mailTitle, sb.toString(), ExperienceConfig.mailTo);
       String[] mailTo = ConfigFactory.getSystemConfig()
       .getModuleConfig("circle").getItemValue("mailTo").split(",");
       Mail.sendMail(mailTitle, sb.toString(), mailTo);
   }
	
	
}
