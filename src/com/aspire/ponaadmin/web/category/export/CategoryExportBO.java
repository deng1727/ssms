/**
 * <p>
 * 货架导出数据管理类
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
	 * 记录日志实例
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(CategoryExportBO.class);
	/**
	 * singleton 模式实例
	 */
	
	private static CategoryExportBO instance = new CategoryExportBO();
	
	/**
	 * 私有构造方法，由singleton模式调用
	 *
	 */
	private CategoryExportBO(){
		
	}
	/**
	 * 获取实例
	 * @return
	 */
	public static CategoryExportBO getInstance(){
		if(LOG.isDebugEnabled()){
			LOG.debug("实例化 货架导出数据管理类");
		}
		return instance;
	}
	
	/**
	 * 事件调度入口
	 * 对多个货架的遍历，并调用categoryExport导出
	 * @throws BOException
	 */
	public void doCategory() throws BOException
	{
		
		StringBuffer sb = new StringBuffer("");
		
		 /**
        * mail发送信息 mail[0]为成功导出 mail[1]获取指定货架商品失败 
        * mail[2]生成excel失败 mail[3]上传ftp失败
        */
       List[] mailInfoList = new List[4];
       mailInfoList[0] = new ArrayList();
       mailInfoList[1] = new ArrayList();
       mailInfoList[2] = new ArrayList();
       mailInfoList[3] = new ArrayList();
		try
		{
			if(LOG.isDebugEnabled()){
				LOG.debug("启动货架商品导出...");
			}
			List list = CategoryExportDAO.getInstance().getCategoryExportID();
			for(int i = 0; i < list.size();i++){
				String cid = (String)list.get(i);
				Category cy =	(Category) Repository.getInstance().getNode(
						cid,
						RepositoryConstants.TYPE_CATEGORY);
				if(null == cy){
					//if(LOG.isDebugEnabled()){
						LOG.error("获取指定"+cid+"货架失败");			
					//}
					
				}else{
				
				String fullName = cy.getNamePath();
				//System.out.println("fullName==="+fullName);
				if(LOG.isDebugEnabled()){
					LOG.debug("开始导出货架..."+fullName+"的商品");
				}
				fullName = fullName.replaceAll(">>",".");
				//this.categoryExport(cid,fullName);
				this.categoryExport(cid,fullName,mailInfoList);
				}
			}
			sb.append("货架商品导出完成，共需要导出"+list.size()+"个货架");
			sb.append("<br>");
			sb.append("成功导出"+mailInfoList[0].size()+"个货架");
			sb.append("<br>");
			int failedtotal = mailInfoList[1].size()+mailInfoList[2].size()+mailInfoList[3].size();
			sb.append("失败导出"+failedtotal+"个货架");
			if(failedtotal > 0){
				sb.append("其中"+mailInfoList[1].size()+"个货架 获取商品失败;");
				sb.append(mailInfoList[2].size()+"个货架 生成excel失败;");
				sb.append(mailInfoList[3].size()+"个货架 上传ftp失败");
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
			sb.append("货架商品导出失败!"+e);
			sb.append("<br>");
			this.sendMail(sb.toString());
		  throw new BOException("获取指定货架失败",e);

		}
		
		
	}
	
	/**
	 * 取出指定货架下的商品，生成excel文件，并上传到指定ftp目录
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
				//传给excel程序两个参数，List  list,String categoryFullname
				Date nowDate = new Date();
				String datefile = PublicUtil.getDateString(nowDate, "yyyyMMdd");
				String templateFilePath = CategoryExportConfig.TEMPLATEDIR;//like  C:/template.xls
				String localFilePath = CategoryExportConfig.LOCALDIR;
                FileUtils.createDir(localFilePath+"/"+datefile);//创建本地时间路径
                String datafilepath  = localFilePath+"/"+datefile;
				String FTPfileName = categoryFullname + datefile +".xls";
				String localfile = datafilepath + "/"+FTPfileName;
				//生成excel文件并存放在指定目录下
				String targetPath=	ExcelComponent.excelExport(templateFilePath, localfile, list);
				String IP = CategoryExportConfig.IP;
				int PORT = CategoryExportConfig.PORT;
				String USER = CategoryExportConfig.USER;
				String PWD = CategoryExportConfig.PWD;
				String 	FTPDIR = CategoryExportConfig.FTPDIR;
				//初始化ftp登陆信息
				FTPUtil fu = new FTPUtil(IP,PORT,USER,PWD);			
				fu.putFiles(FTPDIR,targetPath,FTPfileName);//ftp 上传文件		
				mailInfoList[0].add(categoryId);

			} catch (DAOException e)
			{
				if(LOG.isDebugEnabled()){
					LOG.debug("获取指定货架商品失败");
				}
				LOG.error("获取指定货架商品失败"+e);
				//throw new BOException("获取指定货架商品失败", e);
				mailInfoList[1].add(categoryId);

			} catch (ExcelException e1)
			{
				if(LOG.isDebugEnabled()){
					LOG.debug("生成excel失败");
				}
				LOG.error("生成excel失败"+e1);
				//throw new BOException("生成excel失败", e1);
				mailInfoList[2].add(categoryId);

			} catch (Exception e2)
			{  
				if(LOG.isDebugEnabled()){
					LOG.debug("上传ftp失败");
				}
				LOG.error("上传ftp失败"+e2);
				//throw new BOException("上传ftp失败", e2);
				mailInfoList[3].add(categoryId);

			}		
			
		}
		
	}
	 /**
	 * 发送邮件
	 * 
	 * @param mailContent,邮件内容
	 */
    private void sendMail(String mailContent)
    {
        if (LOG.isDebugEnabled())
        {
        	LOG.debug("sendMail(" + mailContent + ")");
        }
        // 得到邮件接收者数组
        String[] mailTo = MailConfig.getInstance().getMailToArray();
        String subject = "货架商品导出";
        if (LOG.isDebugEnabled())
        {
        	LOG.debug("mailTo Array is:" + Arrays.asList(mailTo));
        	LOG.debug("mail subject is:" + subject);
        	LOG.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailTo);
    }
	
}
