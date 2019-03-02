package com.aspire.dotcard.appinfosyn;

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
import com.aspire.dotcard.appinfosyn.config.AppInfoConfig;
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
	 * У����ֵ�����õ�������ʽ
	 */
	private static String INTEGER = "-?[0-9]{0,d}";
	
	/**
	 * У���ļ��Ƿ�ȫΪ���Ծ����Ƿ�ɾ��
	 * 
	 * @param fileList
	 * @return
	 */
	public static boolean isNullFile(List<String> fileList)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("У���ļ��Ƿ�ȫΪ���Ծ����Ƿ�ɾ����ʼ");
		}
		
		boolean isNullFile = true;
		
		for (int i = 0; i < fileList.size(); i++)
		{
			String tempFileName = String.valueOf(fileList.get(i));
			
			File file = new File(tempFileName);
			
			// ����ļ�Ϊ��
			if (file.length() > 0)
			{
				isNullFile = false;
			}
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("У���ļ��Ƿ�ȫΪ���Ծ����Ƿ�ɾ��" + isNullFile);
		}
		
		return isNullFile;
	}
	
	/**
	 * �õ�ftp��Ϣ
	 * 
	 * @return
	 * @throws IOException
	 * @throws FTPException
	 */
	public static FTPClient getFTPClient() throws IOException, FTPException
	{
		String ip = AppInfoConfig.FTPIP;
		int port = AppInfoConfig.FTPPORT;
		String user = AppInfoConfig.FTPNAME;
		String password = AppInfoConfig.FTPPAS;
		
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
	 * ����ת��ģ���ļ����е�������ֵ
	 * 
	 * @param fileName
	 * @return
	 */
	public static String fileNameDataChange(String fileName, int getDateNum,
			Calendar setTime,boolean isByHour,int getTimeNum)
	{
		Calendar nowTime = null;
		
		// ����ļ��������������ڶ���
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
			
			// ��������
			//���ǰ�Сʱ����
            if(!isByHour)
            {
            	// ��������
            	nowTime.add(Calendar.DAY_OF_MONTH, 0 - getDateNum);
            }
            else
            {
            	nowTime.add(Calendar.HOUR_OF_DAY, 0 - getTimeNum);
            }
			
			tempString.append(PublicUtil.getDateString(nowTime.getTime(),
					dataType));
			
			tempString
					.append(fileName.substring(fileName.lastIndexOf("~") + 1));
			
			return tempString.toString();
		}
		
		return fileName;
	}
	
	/**
	 * �ж��Ƿ��Ǳ���������Ҫ���ص��ļ���
	 * 
	 * @param fName
	 *            ��ǰftp�������ϵ��ļ�
	 * @return true �ǣ�false ��
	 */
	public static boolean isMatchFileName(String fileName, String FtpFName)
	{
		return FtpFName.matches(fileName);
	}
	
	/**
	 * ���ڴ���Ŀ¼
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
     * ����XML�ļ�,���ظ��ڵ�
     * 
     * @param fileName 
     */
    public static Element loadXML(String fileName)
    {
    	// ����jdom������
    	SAXReader reader = new SAXReader(); 

        // Document �ڵ�
        Document doc = null;
        Element root = null;
        try
        {
            doc =  reader.read(new File(fileName));
            // �õ����ڵ�
            root = doc.getRootElement();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return root;
    }
	
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
	 * ����֤���衣��֤ʧ�ܴ�ӡ��־��
	 * 
	 * @param fieldName
	 *            ��������ơ�������־��¼
	 * @param field
	 *            ���ֵ
	 * @param maxLength
	 *            ��������ȡ�
	 * @param must
	 *            �Ƿ�����
	 * @return ��֤ʧ�ܷ���false�����򷵻�true
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
			logger.error(fieldName + "=" + field + ",���ֶ���֤ʧ�ܡ�ԭ�򣺳��ȳ�����"
					+ maxLength + "���ַ���");
		}
		if (must)
		{
			if (field.equals(""))
			{
				result = false;
				logger.error(fieldName + "=" + field
						+ ",���ֶ���֤ʧ�ܡ�ԭ�򣺸��ֶ��Ǳ����ֶΣ�������Ϊ��");
			}
		}
		return result;
		
	}
	
	/**
	 * �����ֶε���������
	 * 
	 * @param field
	 *            �������ֶ�
	 * @param maxLength
	 *            ���ֵ���󳤶�
	 * @param must
	 *            �Ƿ������ֶ�
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
			logger.error(fieldName + "=" + field + ",���ֶ���֤ʧ�ܡ�ԭ�򣺳��ȳ�����"
					+ maxLength + "�����֣�");
			return false;
		}
		if (must)
		{
			if (field.equals(""))
			{
				logger.error(fieldName + "=" + field
						+ ",���ֶ���֤ʧ�ܡ�ԭ�򣺸��ֶ��Ǳ����ֶΣ�������Ϊ��");
				return false;
			}
		}
		return true;
	}
    

	/**
	 * �Ƚ��������ڴ�С
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
			logger.error("�������ڱȽϳ���", e);
		}
		return flag;
	}
	
	public static void main(String[] args){
		String filename = "~Dyyyy-MM-dd~_PROGRAM.txt";
		String fileName = "~Dyyyy-MM-dd\\yyyy-MM-dd~_PROGRAM.verf";
		String str = fileNameDataChange(fileName,1,null,false,0);
		System.out.println(str);
		System.out.println(str.substring(0, 10));
	}
}
