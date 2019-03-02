package com.aspire.dotcard.appinfosyn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.StringTool;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class BaseFileNewTools
{
	private static JLogger logger = LoggerFactory
			.getLogger(BaseFileNewTools.class);
	
	/**
	 * У����ֵ�����õ�������ʽ
	 */
	private static String INTEGER = "-?[0-9]{0,d}";
	
	/**
     * ÿ��д���ļ��Ĵ�С
     */
    final static int BUFFEREDSIZE = 1024;
	
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
	 * ���ڵõ�shell�ű�Ӧ�õ�ʱ�����
	 * 
	 * @param getDateNum
	 * @param setTime
	 * @return
	 */
	public static String getShellFileDate(int getDateNum, Calendar setTime)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar nowTime = null;
		
		if (null == setTime)
		{
			nowTime = Calendar.getInstance();
		}
		else
		{
			nowTime = setTime;
		}
		
		// ��������
		nowTime.add(Calendar.DAY_OF_MONTH, 0 - getDateNum);
		
		return sdf.format(nowTime.getTime());
	}
	
	/**
	 * ���ڵõ�shell�ű�Ӧ�õ�ʱ�����
	 * 
	 * @param getDateNum
	 * @param setTime
	 * @return
	 */
	public static String getShellFileEndDate(String date)
	{
		return "_" + date.replaceAll("-", "");
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
	 * �õ�ftp��Ϣ
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
		// ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
		ftp.setConnectMode(FTPConnectMode.PASV);
		// ʹ�ø������û����������½ftp
		ftp.login(user, password);
		// �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
		ftp.setType(FTPTransferType.BINARY);
		
		return ftp;
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
	 * ִ��SHELL�Ա��ļ��������ɶԱȺ��ļ�
	 * 
	 * @return �ԱȺ��ļ������б�
	 * @throws BOException
	 */
	protected static List<String> execShell(String data) throws BOException
	{
		List<String> fileList = new ArrayList<String>();
		Process pid = null;
		String cmd = BaseVideoNewConfig.EXEC_SHELL_PATH
				+ BaseVideoNewConfig.EXEC_SHELL_FILE + " " + data;
		
		logger.info("��ʼִ��SHELL�ű��ļ�:" + cmd);
		
		try
		{
			// ִ��Shell����
			pid = Runtime.getRuntime().exec(cmd);
			
			if (pid != null)
			{
				logger.info("��ǰ�ű����̺ţ�" + pid.toString());
				pid.waitFor();
			}
			else
			{
				logger.info("��ǰ�ű�û�н��̺š�");
			}
		}
		catch (IOException e)
		{
			logger.info("��ǰ�ű�ִ��ʱ��������", e);
			return fileList;
		}
		catch (InterruptedException e)
		{
			logger.info("��ǰ�ű�ִ��ʱͬ���ȴ���������", e);
			return fileList;
		}
		finally
		{
			if (pid != null)
			{
				pid.destroy();
			}
		}
		
		logger.info("��ǰ�ű�����ִ�����!");
		
		String add = BaseVideoNewConfig.LOCALDIR + File.separator
				+ BaseVideoNewConfig.ADD_SHELL_FILE + getShellFileEndDate(data);
		String del = BaseVideoNewConfig.LOCALDIR + File.separator
				+ BaseVideoNewConfig.DEL_SHELL_FILE + getShellFileEndDate(data);
		
		logger.info("��ǰ�ļ���Ϊ��" + add + ", " + del);
		fileList.add(add.replace('\\', '/'));
		fileList.add(del.replace('\\', '/'));
		
		return fileList;
	}
	
	/**
	 * ִ��SHELL����Ƶ����ȫ���ļ�������ʱ��
	 * 
	 * @throws BOException
	 */
	protected static void execShellOfVideoFullImport(String data) throws BOException
	{
		Process pid = null;
		String cmd = BaseVideoNewConfig.EXEC_SHELL_PATH
				+ BaseVideoNewConfig.EXEC_SHELL_VIDEO_FULL_IMPORT_FILE + " " + data;
		
		logger.info("��ʼִ����Ƶ����ȫ���ļ�����SHELL�ű��ļ�:" + cmd);
		
		try
		{
			// ִ��Shell����
			pid = Runtime.getRuntime().exec(cmd);
			
			if (pid != null)
			{
				logger.info("��ǰ�ű����̺ţ�" + pid.toString());
				//pid.waitFor();
				  StreamGobbler errorGobbler = new BaseFileNewTools().new StreamGobbler(pid.getErrorStream(), "Error");            
	                 StreamGobbler outputGobbler = new BaseFileNewTools().new StreamGobbler(pid.getInputStream(), "Output");
	                 errorGobbler.start();
	                 outputGobbler.start();
	                 pid.waitFor();
	                 
	              
			}
			else
			{
				logger.info("��ǰ�ű�û�н��̺š�");
			}
		}
		catch (IOException e)
		{
			logger.info("��ǰ�ű�ִ��ʱ��������", e);
		}
		catch (InterruptedException e)
		{
			logger.info("��ǰ�ű�ִ��ʱͬ���ȴ���������", e);
		}
		finally
		{
			if (pid != null)
			{
				pid.destroy();
			}
		}
		logger.info("��ǰ�ű�����ִ�����!");
	}
	
	/** 
     * ��ȡ��ǰ���������ڼ� 
     * 
     * @param date 
     * @return ��ǰ���������ڼ� 
     */ 
    public static int getWeekOfDate(Date date) { 
        //String[] weekDays = {"������", "����һ", "���ڶ�", "������", "������", "������", "������"}; 
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) 
            w = 0; 
        //0:������,1:����һ,2:����һ,3:������,4:������,5:������,6:������
        return w;
    } 
    
    
    /**
     * ��ѹtar.gz�ļ�����ǰĿ¼��ѹ���ļ����ļ�����������tar.gz����Ϊ��ѹ���ļ��ĸ�Ŀ¼
     * 
     * @param gzipFileName ��ѹ���ļ��ľ���Ŀ¼
     * @return ��ѹ����ѹ���ļ��ĸ�Ŀ¼��
     * @exception Exception ��ѹ�������쳣
     */
    public static String untargzip(String gzipFileName) throws Exception
    {
        String outputDirectory = gzipFileName.substring(0,
                                                        gzipFileName.indexOf('.'));
        File file = new File(outputDirectory);
        if (!file.exists())
        {
            file.mkdir();
        }
        ungzip(gzipFileName, outputDirectory);
        return outputDirectory;
    }
    /**
     * ��ѹtar.gz�ļ�����ǰĿ¼��ѹ���ļ����ļ�����������tar.gz����Ϊ��ѹ���ļ��ĸ�Ŀ¼
     * 
     * @param gzipFileName ��ѹ���ļ��ľ���Ŀ¼
     * @return ��ѹ����ѹ���ļ��ĸ�Ŀ¼��
     * @exception Exception ��ѹ�������쳣
     */
    public static String ungzip(String gzipFileName) throws Exception
    {
        String outputDirectory = gzipFileName.substring(0,
                                                        gzipFileName.indexOf('.'));
        File file = new File(outputDirectory);
        if (!file.exists())
        {
            file.mkdir();
        }
        ungzip(gzipFileName, outputDirectory);
        return outputDirectory;
    }

    /**
     * ��ѹtar.gz�ļ���ָ��Ŀ¼
     * 
     * @param gzipFileName ѹ���ļ��ľ���·��
     * @param outputDirectory ��ѹ�ļ��ı���Ŀ¼·����
     * @exception Exception ��ѹ�������쳣
     */
    public static void ungzip(String gzipFileName, String outputDirectory)
                    throws Exception
    {
        FileInputStream fis = null;
        ArchiveInputStream in = null;
        BufferedInputStream bufferedInputStream = null;
        try
        {
            fis = new FileInputStream(gzipFileName);
            GZIPInputStream is = new GZIPInputStream(new BufferedInputStream(fis));
            in = new ArchiveStreamFactory().createArchiveInputStream("tar", is);
            bufferedInputStream = new BufferedInputStream(in);
            TarArchiveEntry entry = ( TarArchiveEntry ) in.getNextEntry();
            while (entry != null)
            {
                String name = entry.getName();
                String[] names = name.split("/");
                String fileName = outputDirectory;
                for (int i = 0; i < names.length; i++)
                {
                    fileName = fileName + File.separator + names[i];
                }
                if (name.endsWith("/"))
                {
                    // mkFolder(fileName);
                    IOUtil.checkAndCreateDir(fileName);
                }
                else
                {
                    File file = new File(fileName);
                    //
                    // IOUtil.
                    IOUtil.checkAndCreateDir(file);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                    // ��¼���ζ�ȡ����
                    int b;
                    byte[] by = new byte[BUFFEREDSIZE];
                    while ((b = in.read(by)) != -1)
                    {
                        bufferedOutputStream.write(by, 0, b);
                    }

                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
                entry = ( TarArchiveEntry ) in.getNextEntry();
            }

        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	logger.error(e);
            throw e;
        }
        finally
        {
            try
            {
                if (bufferedInputStream != null)
                {
                    bufferedInputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            	logger.error(e);
            }
        }
    }

	
	public static void main(String[] args){
		File file= new File("D://test.txt");
		System.out.println(file.exists()+":"+file.length());
		Date date = new Date();
		System.out.println(getWeekOfDate(date));
		String string = "i_v-videodetail_~DyyyyMMdd~_[0-9]{6}.tar.gz";
		System.out.println(fileNameDataChange(string,1,null,false,0));
	}
public	   class StreamGobbler extends Thread {
  		 InputStream is;
  		 String type;
  		 StreamGobbler(InputStream is, String type) {
  		  this.is = is;
  		  this.type = type;
  		 }
  		 public void run() {
  		  try {
  		   InputStreamReader isr = new InputStreamReader(is);
  		   BufferedReader br = new BufferedReader(isr);
  		   String line = null;
  		   while ((line = br.readLine()) != null) {
  		    if (type.equals("Error"))
  		    	logger.error(line);
  		    else
  		    	logger.debug(line);
  		   }
  		  } catch (IOException ioe) {
  		   ioe.printStackTrace();
  		  }
  		 }
  		}
	
}
