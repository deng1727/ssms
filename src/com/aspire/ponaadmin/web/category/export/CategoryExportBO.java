/**
 * <p>
 * ���ܵ������ݹ�����
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 16, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.ponaadmin.web.category.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.FileUtils;
import com.aspire.ponaadmin.web.category.export.ftp.FTPUtil;
import com.aspire.ponaadmin.web.excel.ExcelComponent;
import com.aspire.ponaadmin.web.excel.ExcelException;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author dongke
 *
 */
public class CategoryExportBO
{
	/**
	 * ��¼��־ʵ��
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(CategoryExportBO.class);
	/**
	 * singleton ģʽʵ��
	 */
	
	private static CategoryExportBO instance = new CategoryExportBO();
	
	/**
	 * ˽�й��췽������singletonģʽ����
	 *
	 */
	private CategoryExportBO(){
		
	}
	/**
	 * ��ȡʵ��
	 * @return
	 */
	public static CategoryExportBO getInstance(){
		if(LOG.isDebugEnabled()){
			LOG.debug("ʵ���� ���ܵ������ݹ�����");
		}
		return instance;
	}
	
	/**
	 * �¼��������
	 * �Զ�����ܵı�����������categoryExport����
	 * @throws BOException
	 */
	public void doCategory() throws BOException
	{
		
		StringBuffer sb = new StringBuffer("");
		
		 /**
        * mail������Ϣ mail[0]Ϊ�ɹ����� mail[1]��ȡָ��������Ʒʧ�� 
        * mail[2]����excelʧ�� mail[3]�ϴ�ftpʧ��
        */
       List[] mailInfoList = new List[4];
       mailInfoList[0] = new ArrayList();
       mailInfoList[1] = new ArrayList();
       mailInfoList[2] = new ArrayList();
       mailInfoList[3] = new ArrayList();
		try
		{
			if(LOG.isDebugEnabled()){
				LOG.debug("����������Ʒ����...");
			}
			List list = CategoryExportDAO.getInstance().getCategoryExportID();
			for(int i = 0; i < list.size();i++){
				String cid = (String)list.get(i);
				Category cy =	(Category) Repository.getInstance().getNode(
						cid,
						RepositoryConstants.TYPE_CATEGORY);
				if(null == cy){
					//if(LOG.isDebugEnabled()){
						LOG.error("��ȡָ��"+cid+"����ʧ��");			
					//}
					
				}else{
				
				String fullName = cy.getNamePath();
				//System.out.println("fullName==="+fullName);
				if(LOG.isDebugEnabled()){
					LOG.debug("��ʼ��������..."+fullName+"����Ʒ");
				}
				fullName = fullName.replaceAll(">>",".");
				//this.categoryExport(cid,fullName);
				this.categoryExport(cid,fullName,mailInfoList);
				}
			}
			sb.append("������Ʒ������ɣ�����Ҫ����"+list.size()+"������");
			sb.append("<br>");
			sb.append("�ɹ�����"+mailInfoList[0].size()+"������");
			sb.append("<br>");
			int failedtotal = mailInfoList[1].size()+mailInfoList[2].size()+mailInfoList[3].size();
			sb.append("ʧ�ܵ���"+failedtotal+"������");
			if(failedtotal > 0){
				sb.append("����"+mailInfoList[1].size()+"������ ��ȡ��Ʒʧ��;");
				sb.append(mailInfoList[2].size()+"������ ����excelʧ��;");
				sb.append(mailInfoList[3].size()+"������ �ϴ�ftpʧ��");
			}	
			sb.append("<br>");
			this.sendMail(sb.toString());

//			Iterator it = hm.entrySet().iterator();
//			while(it.hasNext()){
//				Entry entry = (Map.Entry)it.next();
//				this.categoryExport((String)entry.getKey(),(String)entry.getValue());
//				}
		} catch (DAOException e)
		{
			sb.append("������Ʒ����ʧ��!"+e);
			sb.append("<br>");
			this.sendMail(sb.toString());
		  throw new BOException("��ȡָ������ʧ��",e);

		}
		
		
	}
	
	/**
	 * ȡ��ָ�������µ���Ʒ������excel�ļ������ϴ���ָ��ftpĿ¼
	 * @param categoryId
	 * @param categoryFullname
	 * @throws BOException
	 */
	
	//public void categoryExport(String categoryId,String categoryFullname) throws BOException
	public void categoryExport(String categoryId,String categoryFullname,List[] mailInfoList) throws BOException

	{
		if(null != categoryId && !categoryId.equals("")){
			try
			{
				List list = CategoryExportDAO.getInstance().getRefrenceByCategoryId(categoryId);
				//����excel��������������List  list,String categoryFullname
				Date nowDate = new Date();
				String datefile = PublicUtil.getDateString(nowDate, "yyyyMMdd");
				String templateFilePath = CategoryExportConfig.TEMPLATEDIR;//like  C:/template.xls
				String localFilePath = CategoryExportConfig.LOCALDIR;
                FileUtils.createDir(localFilePath+"/"+datefile);//��������ʱ��·��
                String datafilepath  = localFilePath+"/"+datefile;
				String FTPfileName = categoryFullname + datefile +".xls";
				String localfile = datafilepath + "/"+FTPfileName;
				//����excel�ļ��������ָ��Ŀ¼��
				String targetPath=	ExcelComponent.excelExport(templateFilePath, localfile, list);
				String IP = CategoryExportConfig.IP;
				int PORT = CategoryExportConfig.PORT;
				String USER = CategoryExportConfig.USER;
				String PWD = CategoryExportConfig.PWD;
				String 	FTPDIR = CategoryExportConfig.FTPDIR;
				//��ʼ��ftp��½��Ϣ
				FTPUtil fu = new FTPUtil(IP,PORT,USER,PWD);			
				fu.putFiles(FTPDIR,targetPath,FTPfileName);//ftp �ϴ��ļ�		
				mailInfoList[0].add(categoryId);

			} catch (DAOException e)
			{
				if(LOG.isDebugEnabled()){
					LOG.debug("��ȡָ��������Ʒʧ��");
				}
				LOG.error("��ȡָ��������Ʒʧ��"+e);
				//throw new BOException("��ȡָ��������Ʒʧ��", e);
				mailInfoList[1].add(categoryId);

			} catch (ExcelException e1)
			{
				if(LOG.isDebugEnabled()){
					LOG.debug("����excelʧ��");
				}
				LOG.error("����excelʧ��"+e1);
				//throw new BOException("����excelʧ��", e1);
				mailInfoList[2].add(categoryId);

			} catch (Exception e2)
			{  
				if(LOG.isDebugEnabled()){
					LOG.debug("�ϴ�ftpʧ��");
				}
				LOG.error("�ϴ�ftpʧ��"+e2);
				//throw new BOException("�ϴ�ftpʧ��", e2);
				mailInfoList[3].add(categoryId);

			}		
			
		}
		
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
        String subject = "������Ʒ����";
        if (LOG.isDebugEnabled())
        {
        	LOG.debug("mailTo Array is:" + Arrays.asList(mailTo));
        	LOG.debug("mail subject is:" + subject);
        	LOG.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailTo);
    }
	
}
