package com.aspire.dotcard.basevideosync.exportfile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.StringTool;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class BaseFileTools {

	private static JLogger logger = LoggerFactory
	.getLogger(BaseFileTools.class);
	
	/**
	 * 校验数值长度用的正则表达式
	 */
	private static String INTEGER = "-?[0-9]{0,d}";
	
	/**
	 * 校验文件是否全为空以决定是否删表
	 * 
	 * @param fileList
	 * @return
	 */
	public static boolean isNullFile(List<String> fileList)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("校验文件是否全为空以决定是否删表：开始");
		}
		
		boolean isNullFile = true;
		
		for (int i = 0; i < fileList.size(); i++)
		{
			String tempFileName = String.valueOf(fileList.get(i));
			
			File file = new File(tempFileName);
			
			// 如果文件为空
			if (file.length() > 0)
			{
				isNullFile = false;
			}
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("校验文件是否全为空以决定是否删表：" + isNullFile);
		}
		
		return isNullFile;
	}
	
	/**
	 * 得到ftp信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws FTPException
	 */
	public static FTPClient getFTPClient() throws IOException, FTPException
	{
		String ip = BaseVideoConfig.FTPIP;
		int port = BaseVideoConfig.FTPPORT;
		String user = BaseVideoConfig.FTPNAME;
		String password = BaseVideoConfig.FTPPAS;
		
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
	 * 用于转义模糊文件名中的日期数值
	 * 
	 * @param fileName
	 * @return
	 */
	public static String fileNameDataChange(String fileName, int getDateNum,
			Calendar setTime,boolean isByHour,int getTimeNum)
	{
		Calendar nowTime = null;
		
		// 如果文件名定义中有日期定义
		if (fileName.indexOf("~D") != -1)
		{
			StringBuffer tempString = new StringBuffer();
			
			if (null == setTime)
			{
				nowTime = Calendar.getInstance();
			}
			else
			{
				nowTime = setTime;
			}
			
			int temp = fileName.indexOf("~D");
			
			tempString.append(fileName.substring(0, temp));
			
			String dataType = fileName.substring(temp + 2, fileName
					.lastIndexOf("~"));
			
			// 调整日期
			//不是按小时增量
            if(!isByHour)
            {
            	// 调整日期
            	nowTime.add(Calendar.DAY_OF_MONTH, 0 - getDateNum);
            }
            else
            {
            	nowTime.add(Calendar.HOUR_OF_DAY, 0 - getTimeNum);
            }
			if(dataType.indexOf("~D") != -1){
				int index = dataType.indexOf("~D");
				tempString.append(PublicUtil.getDateString(nowTime.getTime(),
						dataType.substring(0, dataType.indexOf('~'))));
				tempString.append(dataType.substring(dataType.indexOf('~') + 1,index));
				tempString.append(PublicUtil.getDateString(nowTime.getTime(),
						dataType.substring(index + 2)));
			}else{
				tempString.append(PublicUtil.getDateString(nowTime.getTime(),
						dataType));
			}
			
			tempString
					.append(fileName.substring(fileName.lastIndexOf("~") + 1));
			
			return tempString.toString();
		}
		
		return fileName;
	}
	
	/**
	 * 判读是否是本次任务需要下载的文件名
	 * 
	 * @param fName
	 *            当前ftp服务器上的文件
	 * @return true 是，false 否
	 */
	public static boolean isMatchFileName(String fileName, String FtpFName)
	{
		return FtpFName.matches(fileName);
	}
	
	/**
	 * 用于创建目录
	 */
	public static void createFilePath(String fileAllName)
	{
		File file = new File(fileAllName);
		
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
	}
	
	/**
     * 加载XML文件,返回根节点
     * 
     * @param fileName 
     */
    public static Element loadXML(String fileName)
    {
    	// 引入jdom的配置
    	SAXReader reader = new SAXReader(); 

        // Document 节点
        Document doc = null;
        Element root = null;
        try
        {
            doc =  reader.read(new File(fileName));
            // 得到根节点
            root = doc.getRootElement();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return root;
    }
	
    /**
	 * 验证该字段的长度。
	 * 
	 * @param field
	 *            域的值
	 * @param maxLength
	 *            最大允许长度。
	 * @param must
	 *            是否必须的
	 * @return 验证失败返回false，否则返回true
	 */
    public static boolean checkFieldLength(String field, int maxLength,
			boolean must)
	{
		if (field == null)
		{
			return false;
		}
		if (StringTool.lengthOfHZ(field) > maxLength)
		{
			return false;
		}
		if (must)
		{
			if (field.equals(""))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 简化验证步骤。验证失败打印日志。
	 * 
	 * @param fieldName
	 *            该域的名称。用于日志记录
	 * @param field
	 *            域的值
	 * @param maxLength
	 *            最大允许长度。
	 * @param must
	 *            是否必须的
	 * @return 验证失败返回false，否则返回true
	 */
	public static boolean checkFieldLength(String fieldName, String field,
			int maxLength, boolean must)
	{
		boolean result = true;
		if (field == null)
		{
			result = false;
		}
		if (StringTool.lengthOfHZ(field) > maxLength)
		{
			result = false;
			logger.error(fieldName + "=" + field + ",该字段验证失败。原因：长度超过了"
					+ maxLength + "个字符！");
		}
		if (must)
		{
			if (field.equals(""))
			{
				result = false;
				logger.error(fieldName + "=" + field
						+ ",该字段验证失败。原因：该字段是必填字段，不允许为空");
			}
		}
		return result;
		
	}
	
	/**
	 * 检查该字段的数字类型
	 * 
	 * @param field
	 *            待检查的字段
	 * @param maxLength
	 *            数字的最大长度
	 * @param must
	 *            是否必须的字段
	 * @return
	 */
	public static boolean checkIntegerField(String fieldName, String field,
			int maxLength, boolean must)
	{
		if (field == null)
		{
			return false;
		}
		if (!field
				.matches(INTEGER.replaceFirst("d", String.valueOf(maxLength))))
		{
			logger.error(fieldName + "=" + field + ",该字段验证失败。原因：长度超过了"
					+ maxLength + "个数字！");
			return false;
		}
		if (must)
		{
			if (field.equals(""))
			{
				logger.error(fieldName + "=" + field
						+ ",该字段验证失败。原因：该字段是必填字段，不允许为空");
				return false;
			}
		}
		return true;
	}
    

	/**
	 * 比较两个日期大小
	 * 
	 * @param date1
	 *            
	 * @param date2
	 *            
	 * @return
	 */
	public static boolean compare_date(String date1, String date2) {

		boolean flag = false;
		if(null == date1 || null == date2 || "".equals(date1) || "".equals(date2))
			return flag;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				flag = true;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("两个日期比较出错", e);
		}
		return flag;
	}
	
	public static void main(String[] args){
		String filename =  "~DyyyyMMdd~"+File.separator+"i_~DyyyyMMdd~_week.txt";
		String fileName = "~DyyyyMMdd~"+File.separator+"i_~DyyyyMMdd~_week.verf";
		System.out.println(fileNameDataChange(fileName,1,null,true,0));
		String str = fileNameDataChange(filename,1,null,true,0);
		System.out.println(str);
		System.out.println(compare_date("2014-12-18 18:44:00","2014-12-18 11:31:00"));
		
	}
}
