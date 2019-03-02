/**
 * SSMS
 * com.aspire.ponaadmin.web.category.export.repchange CategoryRepChangeBO.java
 * Apr 14, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.category.export.repchange;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.aspire.common.config.ServerInfo;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.FileUtils;
import com.aspire.ponaadmin.web.category.export.CategoryExportConfig;
import com.aspire.ponaadmin.web.category.export.ftp.FTPUtil;
import com.aspire.ponaadmin.web.excel.ExcelComponent;
import com.aspire.ponaadmin.web.excel.ExcelException;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 *
 */
public class CategoryRepChangeBO {

	
	/**
	 * ��¼��־ʵ��
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(CategoryRepChangeBO.class);
	/**
	 * singleton ģʽʵ��
	 */
	
	private static CategoryRepChangeBO instance = new CategoryRepChangeBO();
	
	/**
	 * ˽�й��췽������singletonģʽ����
	 *
	 */
	private CategoryRepChangeBO(){
		
	}
	/**
	 * ��ȡʵ��
	 * @return
	 */
	public static CategoryRepChangeBO getInstance(){
		if(LOG.isDebugEnabled()){
			LOG.debug("ʵ���� �����ظ����ֻ��ʵ������ݹ�����");
		}
		return instance;
	}
	
	/**
	 * ���ݵ���
	 * @throws BOException 
	 *
	 */
	public void categoryRepChangeExport() throws BOException{
				
		Date startDate=new Date();
		LOG.info("�����ֻ����ظ��ʵ�����ʼ��startTime:"+PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
		String changeRate30Sql = "category.export.repchange.CategoryRepChangeBO.select().changeRate30";
		String changeRate15Sql = "category.export.repchange.CategoryRepChangeBO.select().changeRate15";
		
		String cateChangeRate30Sql = "category.export.repchange.CategoryRepChangeBO.select().cateChangeRate30";
		String cateChangeRate15Sql = "category.export.repchange.CategoryRepChangeBO.select().cateChangeRate15";
		
		String repeateRate30Sql = "category.export.repchange.CategoryRepChangeBO.select().repeateRate30";
		String repeateRate15Sql = "category.export.repchange.CategoryRepChangeBO.select().repeateRate15";
		
		String N5802ChangeRateSql = "category.export.repchange.CategoryRepChangeBO.select().N5802ChangeRate";
		String N5802repeateRateSql = "category.export.repchange.CategoryRepChangeBO.select().N5802repeateRate";
		//�ֻ���ģ��
		String templateFilechangeRate = ServerInfo.getAppRootPath()+"/conf/changetemplate.xls";
		//�ظ���ģ��
		String templateFilerepeateRate = ServerInfo.getAppRootPath()+"/conf/repeatetemplate.xls";
		
		//���ֻ���ģ��
		String templateFilecachangeRate = ServerInfo.getAppRootPath()+"/conf/cachangetemplate.xls";
		StringBuffer sb = new StringBuffer("");
		try {
			//��ʼ������
			CategoryRepChangeDAO.getInstance().initRepChangeData();
			LOG.debug("�ظ���ת���ʳ�ʼ���ɹ�");
			sb.append("�ظ���ת���ʳ�ʼ���ɹ�!");
			sb.append("<br>");
		List changeRate30 = CategoryRepChangeDAO.getInstance().getRepChangeRate1(changeRate30Sql,null);
				
		List changeRate15 = CategoryRepChangeDAO.getInstance().getRepChangeRate1(changeRate15Sql,null);
		
		List cateChangeRate30 = CategoryRepChangeDAO.getInstance().getCaRepChangeRate1(cateChangeRate30Sql,null);
		List cateChangeRate15 = CategoryRepChangeDAO.getInstance().getCaRepChangeRate1(cateChangeRate15Sql,null);
		
		
		List repeateRate30 = CategoryRepChangeDAO.getInstance().getRepeateRate1(repeateRate30Sql,null);
		List repeateRate15 = CategoryRepChangeDAO.getInstance().getRepeateRate1(repeateRate15Sql,null);
		
		List N5802ChangeRate = CategoryRepChangeDAO.getInstance().getRepChangeRate1(N5802ChangeRateSql,null);
		List N5802repeateRate = CategoryRepChangeDAO.getInstance().getRepeateRate1(N5802repeateRateSql,null);
		
			Date nowDate = new Date();
			String datefile = PublicUtil.getDateString(nowDate, "yyyyMMdd");
			//String templateFilePath = CategoryExportConfig.TEMPLATEDIR;//like  C:/template.xls
			//�ֻ���ģ��
		
			
			String localFilePath = CategoryExportConfig.LOCALDIR;
            FileUtils.createDir(localFilePath+"/"+datefile);//��������ʱ��·��
            String datafilepath  = localFilePath+"/"+datefile;
			String FTPfileNamec1930 = "19����ǰ30�ֻ���ͳ��" + datefile +".xls";
			String FTPfileNamec1915 = "19����ǰ15�ֻ���ͳ��" + datefile +".xls";
			String FTPfileNameca30 = "��ǰ30�ֻ���ͳ��" + datefile +".xls";
			String FTPfileNameca15 = "��ǰ15�ֻ���ͳ��" + datefile +".xls";
			String FTPfileNamer1930 = "19����ǰ30�ظ���ͳ��" + datefile +".xls";
			String FTPfileNamer1915 = "19����ǰ15�ظ���ͳ��" + datefile +".xls";
			String FTPfileNamen30 = "N5802ǰ30�ֻ���ͳ��" + datefile +".xls";
			String FTPfileNamen20 = "N5802ǰ20�ظ���ͳ��" + datefile +".xls";
			//String localfile = datafilepath + "/"+FTPfileName;
			
			this.createExcelReport(templateFilechangeRate,datafilepath+"/"+FTPfileNamec1930,FTPfileNamec1930,changeRate30);
			sb.append(FTPfileNamec1930+"�����ɹ�!");
			sb.append("<br>");
			this.createExcelReport(templateFilechangeRate,datafilepath+"/"+FTPfileNamec1915,FTPfileNamec1915,changeRate15);
			sb.append(FTPfileNamec1915+"�����ɹ�!");
			sb.append("<br>");
			this.createExcelReport(templateFilecachangeRate,datafilepath+"/"+FTPfileNameca30,FTPfileNameca30,cateChangeRate30);
			sb.append(FTPfileNameca30+"�����ɹ�!");
			sb.append("<br>");
			this.createExcelReport(templateFilecachangeRate,datafilepath+"/"+FTPfileNameca15,FTPfileNameca15,cateChangeRate15);
			sb.append(FTPfileNameca15+"�����ɹ�!");
			sb.append("<br>");
			this.createExcelReport(templateFilerepeateRate,datafilepath+"/"+FTPfileNamer1930,FTPfileNamer1930,repeateRate30);
			sb.append(FTPfileNamer1930+"�����ɹ�!");
			sb.append("<br>");
			this.createExcelReport(templateFilerepeateRate,datafilepath+"/"+FTPfileNamer1915,FTPfileNamer1915,repeateRate15);
			sb.append(FTPfileNamer1915+"�����ɹ�!");
			sb.append("<br>");
			this.createExcelReport(templateFilechangeRate,datafilepath+"/"+FTPfileNamen30,FTPfileNamen30,N5802ChangeRate);
			sb.append(FTPfileNamen30+"�����ɹ�!");
			sb.append("<br>");
			this.createExcelReport(templateFilerepeateRate,datafilepath+"/"+FTPfileNamen20,FTPfileNamen20,N5802repeateRate);
			sb.append(FTPfileNamen20+"�����ɹ�!");
			sb.append("<br>");
			Date endDate=new Date();
			LOG.info("�����ֻ����ظ��ʵ���������endTime:"+PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			LOG.debug("�ظ���ת�������ɳɹ�");
			sb.append("<br>");
			sb.append("�ظ���ת�������ɳɹ�!");
			sb.append("<br>");
			sb.append("��ʼʱ��"+PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("<br>");
			sb.append("����ʱ��"+PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			this.sendMail(sb.toString());
			//������ʷ����
			CategoryRepChangeDAO.getInstance().clearHisData();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(LOG.isDebugEnabled()){
				LOG.debug("��ȡָ�������ظ����ֻ���ʧ��"+e);
		}
			LOG.error("��ȡָ�������ظ����ֻ���ʧ��"+e);
			sb.append("��ȡָ�������ظ����ֻ���ʧ��!"+e);
			sb.append("<br>");
			this.sendMail(sb.toString());
			throw new BOException("��ȡָ�������ظ����ֻ���ʧ��", e);
		}
			
		
			//����excel�ļ��������ָ��Ŀ¼��
//			String targetPath=	ExcelComponent.excelExport(templateFilePath, localfile, list);
//			String IP = CategoryExportConfig.IP;
//			int PORT = CategoryExportConfig.PORT;
//			String USER = CategoryExportConfig.USER;
//			String PWD = CategoryExportConfig.PWD;
//			String 	FTPDIR = CategoryExportConfig.FTPDIR;
//			//��ʼ��ftp��½��Ϣ
//			FTPUtil fu = new FTPUtil(IP,PORT,USER,PWD);			
//			fu.putFiles(FTPDIR,targetPath,FTPfileName);//ftp �ϴ��ļ�				
//		} catch (DAOException e)
//		{
//			if(LOG.isDebugEnabled()){
//				LOG.debug("��ȡָ��������Ʒʧ��");
//			}
//			throw new BOException("��ȡָ��������Ʒʧ��", e);
//		}
//		 catch (ExcelException e1)
//		{
//			if(LOG.isDebugEnabled()){
//				LOG.debug("����excelʧ��");
//			}
//			throw new BOException("����excelʧ��", e1);
//		} catch (Exception e2)
//		{  
//			if(LOG.isDebugEnabled()){
//				LOG.debug("�ϴ�ftpʧ��");
//			}
//			throw new BOException("�ϴ�ftpʧ��", e2);
//		}		
		
	
		
	}
	
	private void createExcelReport(String templateFilePath,String localfile, String FTPfileName, List list) throws BOException {
		//try{
//		����excel�ļ��������ָ��Ŀ¼��
		try {
			String	targetPath = ExcelComponent.excelExport(templateFilePath, localfile, list);
			String IP = CategoryExportConfig.IP;
			int PORT = CategoryExportConfig.PORT;
			String USER = CategoryExportConfig.USER;
			String PWD = CategoryExportConfig.PWD;
			String 	FTPDIR = CategoryExportConfig.FTPDIR;
			//��ʼ��ftp��½��Ϣ
			
			FTPUtil fu = new FTPUtil(IP,PORT,USER,PWD);
			fu.putFiles(FTPDIR,targetPath,FTPfileName);//ftp �ϴ��ļ�	
		} catch (ExcelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(LOG.isDebugEnabled()){
				LOG.debug("����excelʧ��");
			}
			throw new BOException("����excelʧ��", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(LOG.isDebugEnabled()){
				LOG.debug("�ϴ�ftpʧ��");
			}
			throw new BOException("�ϴ�ftpʧ��", e);		
		}//ftp �ϴ��ļ�		
		
	}
	/**
	 * �����ʼ�
	 * 
	 * @param mailContent,�ʼ�����
	 */
    private void sendMail(String mailContent)
    {
        if (LOG.isDebugEnabled())
        {
        	LOG.debug("sendMail(" + mailContent + ")");
        }
        // �õ��ʼ�����������
        String[] mailTo = MailConfig.getInstance().getMailToArray();
        String subject = "�����ظ����ֻ������ݵ���";
        if (LOG.isDebugEnabled())
        {
        	LOG.debug("mailTo Array is:" + Arrays.asList(mailTo));
        	LOG.debug("mail subject is:" + subject);
        	LOG.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailTo);
    }
	
}
