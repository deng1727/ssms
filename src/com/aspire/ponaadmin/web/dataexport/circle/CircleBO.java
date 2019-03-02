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
 *�ֶ�ִ�� Ȧ��Ӫ�� ���ݵ��� ������
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
     * �ֶ� Ȧ��Ӫ��ȫ������ ����
     * ���ţ�139���䣬139˵��
     * 
     * 
     */
	public void fullExport(){
		
//		 TODO Auto-generated method stub
		 if (LOG.isDebugEnabled())
	        {
	            LOG.debug("��ʼ����Ȧ��Ӫ��ƽ̨�ļ�");
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
	        	//�������ݵ���
	        	String ftpFetionFileName =  DataExportTools.parseFileName(fetionFileName);
	        	String localfetionFileName =  ConfigFactory.getSystemConfig()
	            .getModuleConfig("circle").getItemValue("LOCALPATH") + File.separator +  ftpFetionFileName;
	        	List list = getAppList("001");
	        	writeToFile(list,localfetionFileName,ftpFetionFileName);	
	        	sucessList.add("�������ݵ���");
	        	result1 = true;
	        }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	        	failureList.add("�������ݵ���ʧ��"+e);
	        	message.append("�������ݵ���ʧ��"+e);
	        }
	        try
	        {
	        	//139�������ݵ���
	        	String ftp139mailFileName =  DataExportTools.parseFileName(f139mailFileName);
	        	String local139mailFileName =  ConfigFactory.getSystemConfig()
	            .getModuleConfig("circle").getItemValue("LOCALPATH") + File.separator +  ftp139mailFileName;
	        	List list = getAppList("002");
	        	writeToFile(list,local139mailFileName,ftp139mailFileName);	
	        	sucessList.add("139�������ݵ���");
	        	result1 = true;
	        }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	        	failureList.add("139�������ݵ���ʧ��"+e);
	        	message.append("139�������ݵ���ʧ��"+e);
	        }
	        try
	        {
	        	//139˵�����ݵ���
	        	String ftp139skFileName =  DataExportTools.parseFileName(f139skFileName);
	        	String local139skFileName =  ConfigFactory.getSystemConfig()
	            .getModuleConfig("circle").getItemValue("LOCALPATH") + File.separator +  ftp139skFileName;
	        	List list = getAppList("003");
	        	writeToFile(list,local139skFileName,ftp139skFileName);	
	        	sucessList.add("139˵�����ݵ���");
	        	result1 = true;
	        }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	        	failureList.add("139˵�����ݵ���ʧ��"+e);
	        	message.append("139˵�����ݵ���ʧ��"+e);
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
            LOG.info("д��FTP" +ftpFileName);
        
        
    }
	 /**
     * �Ӱ�Ӧ����Ϣ���л�ȡȫ���б�
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
//	  from t_r_gcontent g, t_r_circle��l, t_r_category c, t_r_reference r
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
			 LOG.error("��ȡȦ��Ӫ����Ӧ����Ϣ���ݳ���",e);
			throw new DAOException("��ȡȦ��Ӫ����Ӧ����Ϣ���ݳ���", e);
		} finally
		{
			DB.close(rs);
		}

		return list;
    }
    /**
     * д�ļ���FTPָ��Ŀ¼��
     * 
     * @throws BOException
     * 
     */
    protected void copyFileToFTP(String localfileName,String ftpFileName) throws BOException
    {

        FTPClient ftp = null;

        try
        {
            // ȡ��Զ��Ŀ¼���ļ��б�
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
        
        // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
        ftp.setConnectMode(FTPConnectMode.PASV);

        // ʹ�ø������û����������½ftp
        ftp.login(user, password);
        
        // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
        ftp.setType(FTPTransferType.BINARY);

        return ftp;
    }

	  /**
		 * ���ͽ���ʼ���
		 */
   private void sendResultMail(boolean result, String reason)
   {

       String mailTitle;
       // �����ʼ���ʾ���δ������
       Date endDate = new Date();
       StringBuffer sb = new StringBuffer();
       int totalSuccessCount = sucessList.size();
       int totalFailureCount = failureList.size();
       mailTitle = "�ֶ�Ȧ��Ӫ�������ļ����ɽ��";
       if (result)
       {

           sb.append("��ʼʱ�䣺");
           sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append(",����ʱ�䣺");
           sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append("��<h4>��������</h4>");
           sb.append("<p>���гɹ�����<b>");
           sb.append(totalSuccessCount);
           sb.append("</b>���ļ���");
           sb.append("ʧ�ܵ���<b>");
           sb.append(totalFailureCount);
           sb.append("</b>���ļ���");
           if (totalSuccessCount != 0)
           {
               sb.append("<p>������ϢΪ��<p>");

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
           sb.append("��ʼʱ�䣺");
           sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append(",����ʱ�䣺");
           sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
           sb.append("��<p>ʧ��ԭ��<br>");
           sb.append(reason);
       }

       LOG.info(sb.toString());
       //Mail.sendMail(mailTitle, sb.toString(), ExperienceConfig.mailTo);
       String[] mailTo = ConfigFactory.getSystemConfig()
       .getModuleConfig("circle").getItemValue("mailTo").split(",");
       Mail.sendMail(mailTitle, sb.toString(), mailTo);
   }
	
	
}
