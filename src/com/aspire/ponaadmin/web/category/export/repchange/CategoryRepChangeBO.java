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
	 * 记录日志实例
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(CategoryRepChangeBO.class);
	/**
	 * singleton 模式实例
	 */
	
	private static CategoryRepChangeBO instance = new CategoryRepChangeBO();
	
	/**
	 * 私有构造方法，由singleton模式调用
	 *
	 */
	private CategoryRepChangeBO(){
		
	}
	/**
	 * 获取实例
	 * @return
	 */
	public static CategoryRepChangeBO getInstance(){
		if(LOG.isDebugEnabled()){
			LOG.debug("实例化 货架重复率轮换率导出数据管理类");
		}
		return instance;
	}
	
	/**
	 * 数据导出
	 * @throws BOException 
	 *
	 */
	public void categoryRepChangeExport() throws BOException{
				
		Date startDate=new Date();
		LOG.info("货架轮换率重复率导出开始：startTime:"+PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
		String changeRate30Sql = "category.export.repchange.CategoryRepChangeBO.select().changeRate30";
		String changeRate15Sql = "category.export.repchange.CategoryRepChangeBO.select().changeRate15";
		
		String cateChangeRate30Sql = "category.export.repchange.CategoryRepChangeBO.select().cateChangeRate30";
		String cateChangeRate15Sql = "category.export.repchange.CategoryRepChangeBO.select().cateChangeRate15";
		
		String repeateRate30Sql = "category.export.repchange.CategoryRepChangeBO.select().repeateRate30";
		String repeateRate15Sql = "category.export.repchange.CategoryRepChangeBO.select().repeateRate15";
		
		String N5802ChangeRateSql = "category.export.repchange.CategoryRepChangeBO.select().N5802ChangeRate";
		String N5802repeateRateSql = "category.export.repchange.CategoryRepChangeBO.select().N5802repeateRate";
		//轮换率模板
		String templateFilechangeRate = ServerInfo.getAppRootPath()+"/conf/changetemplate.xls";
		//重复率模板
		String templateFilerepeateRate = ServerInfo.getAppRootPath()+"/conf/repeatetemplate.xls";
		
		//榜单轮换率模板
		String templateFilecachangeRate = ServerInfo.getAppRootPath()+"/conf/cachangetemplate.xls";
		StringBuffer sb = new StringBuffer("");
		try {
			//初始化数据
			CategoryRepChangeDAO.getInstance().initRepChangeData();
			LOG.debug("重复率转换率初始化成功");
			sb.append("重复率转换率初始化成功!");
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
			//轮换率模板
		
			
			String localFilePath = CategoryExportConfig.LOCALDIR;
            FileUtils.createDir(localFilePath+"/"+datefile);//创建本地时间路径
            String datafilepath  = localFilePath+"/"+datefile;
			String FTPfileNamec1930 = "19机型前30轮换率统计" + datefile +".xls";
			String FTPfileNamec1915 = "19机型前15轮换率统计" + datefile +".xls";
			String FTPfileNameca30 = "榜单前30轮换率统计" + datefile +".xls";
			String FTPfileNameca15 = "榜单前15轮换率统计" + datefile +".xls";
			String FTPfileNamer1930 = "19机型前30重复率统计" + datefile +".xls";
			String FTPfileNamer1915 = "19机型前15重复率统计" + datefile +".xls";
			String FTPfileNamen30 = "N5802前30轮换率统计" + datefile +".xls";
			String FTPfileNamen20 = "N5802前20重复率统计" + datefile +".xls";
			//String localfile = datafilepath + "/"+FTPfileName;
			
			this.createExcelReport(templateFilechangeRate,datafilepath+"/"+FTPfileNamec1930,FTPfileNamec1930,changeRate30);
			sb.append(FTPfileNamec1930+"导出成功!");
			sb.append("<br>");
			this.createExcelReport(templateFilechangeRate,datafilepath+"/"+FTPfileNamec1915,FTPfileNamec1915,changeRate15);
			sb.append(FTPfileNamec1915+"导出成功!");
			sb.append("<br>");
			this.createExcelReport(templateFilecachangeRate,datafilepath+"/"+FTPfileNameca30,FTPfileNameca30,cateChangeRate30);
			sb.append(FTPfileNameca30+"导出成功!");
			sb.append("<br>");
			this.createExcelReport(templateFilecachangeRate,datafilepath+"/"+FTPfileNameca15,FTPfileNameca15,cateChangeRate15);
			sb.append(FTPfileNameca15+"导出成功!");
			sb.append("<br>");
			this.createExcelReport(templateFilerepeateRate,datafilepath+"/"+FTPfileNamer1930,FTPfileNamer1930,repeateRate30);
			sb.append(FTPfileNamer1930+"导出成功!");
			sb.append("<br>");
			this.createExcelReport(templateFilerepeateRate,datafilepath+"/"+FTPfileNamer1915,FTPfileNamer1915,repeateRate15);
			sb.append(FTPfileNamer1915+"导出成功!");
			sb.append("<br>");
			this.createExcelReport(templateFilechangeRate,datafilepath+"/"+FTPfileNamen30,FTPfileNamen30,N5802ChangeRate);
			sb.append(FTPfileNamen30+"导出成功!");
			sb.append("<br>");
			this.createExcelReport(templateFilerepeateRate,datafilepath+"/"+FTPfileNamen20,FTPfileNamen20,N5802repeateRate);
			sb.append(FTPfileNamen20+"导出成功!");
			sb.append("<br>");
			Date endDate=new Date();
			LOG.info("货架轮换率重复率导出结束：endTime:"+PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			LOG.debug("重复率转换率生成成功");
			sb.append("<br>");
			sb.append("重复率转换率生成成功!");
			sb.append("<br>");
			sb.append("开始时间"+PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("<br>");
			sb.append("结束时间"+PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			this.sendMail(sb.toString());
			//清理历史数据
			CategoryRepChangeDAO.getInstance().clearHisData();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(LOG.isDebugEnabled()){
				LOG.debug("获取指定货架重复率轮换率失败"+e);
		}
			LOG.error("获取指定货架重复率轮换率失败"+e);
			sb.append("获取指定货架重复率轮换率失败!"+e);
			sb.append("<br>");
			this.sendMail(sb.toString());
			throw new BOException("获取指定货架重复率轮换率失败", e);
		}
			
		
			//生成excel文件并存放在指定目录下
//			String targetPath=	ExcelComponent.excelExport(templateFilePath, localfile, list);
//			String IP = CategoryExportConfig.IP;
//			int PORT = CategoryExportConfig.PORT;
//			String USER = CategoryExportConfig.USER;
//			String PWD = CategoryExportConfig.PWD;
//			String 	FTPDIR = CategoryExportConfig.FTPDIR;
//			//初始化ftp登陆信息
//			FTPUtil fu = new FTPUtil(IP,PORT,USER,PWD);			
//			fu.putFiles(FTPDIR,targetPath,FTPfileName);//ftp 上传文件				
//		} catch (DAOException e)
//		{
//			if(LOG.isDebugEnabled()){
//				LOG.debug("获取指定货架商品失败");
//			}
//			throw new BOException("获取指定货架商品失败", e);
//		}
//		 catch (ExcelException e1)
//		{
//			if(LOG.isDebugEnabled()){
//				LOG.debug("生成excel失败");
//			}
//			throw new BOException("生成excel失败", e1);
//		} catch (Exception e2)
//		{  
//			if(LOG.isDebugEnabled()){
//				LOG.debug("上传ftp失败");
//			}
//			throw new BOException("上传ftp失败", e2);
//		}		
		
	
		
	}
	
	private void createExcelReport(String templateFilePath,String localfile, String FTPfileName, List list) throws BOException {
		//try{
//		生成excel文件并存放在指定目录下
		try {
			String	targetPath = ExcelComponent.excelExport(templateFilePath, localfile, list);
			String IP = CategoryExportConfig.IP;
			int PORT = CategoryExportConfig.PORT;
			String USER = CategoryExportConfig.USER;
			String PWD = CategoryExportConfig.PWD;
			String 	FTPDIR = CategoryExportConfig.FTPDIR;
			//初始化ftp登陆信息
			
			FTPUtil fu = new FTPUtil(IP,PORT,USER,PWD);
			fu.putFiles(FTPDIR,targetPath,FTPfileName);//ftp 上传文件	
		} catch (ExcelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(LOG.isDebugEnabled()){
				LOG.debug("生成excel失败");
			}
			throw new BOException("生成excel失败", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(LOG.isDebugEnabled()){
				LOG.debug("上传ftp失败");
			}
			throw new BOException("上传ftp失败", e);		
		}//ftp 上传文件		
		
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
        String subject = "货架重复率轮换率数据导出";
        if (LOG.isDebugEnabled())
        {
        	LOG.debug("mailTo Array is:" + Arrays.asList(mailTo));
        	LOG.debug("mail subject is:" + subject);
        	LOG.debug("mailContent is:" + mailContent);
        }
        Mail.sendMail(subject, mailContent, mailTo);
    }
	
}
