/**
 * <p>
 * FTP����ʵ����
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 17, 2009
 * @author dongke
 * @version 1.0.0.0
 */

package com.aspire.ponaadmin.web.category.export.ftp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.enterprisedt.net.ftp.FileTransferClient;

/**
 * @author dongke
 * 
 */
public class FTPUtil implements FTPUtilInterface {
	private static JLogger logger = LoggerFactory.getLogger(FTPUtil.class);

	/**
	 * ����ƥ�䵱ǰ������ļ�����������ʽ
	 */
	protected String[] fNameRegex;
	/**
	 * ����ƥ�䵱ǰ������ļ�����������ʽ
	 */
	protected String[] fName;

	private String ip;
	private int port;
	private String user;
	private String password;
	private String ftpDir;
	
	private String initPwd;//��һ��������FTP��ʱ�򣬼�¼���û��Ŀ�ʼ·����//add by aiyan 2012-07-17

	/**
	 * ��ʼ��ftp������Ϣ
	 * 
	 * @param ftpIp
	 * @param ftpPort
	 * @param ftpUser
	 * @param ftpPassword
	 */
	public FTPUtil(String ftpIp, int ftpPort, String ftpUser, String ftpPassword) {
		this.ip = ftpIp;
		this.port = ftpPort;
		this.user = ftpUser;
		this.password = ftpPassword;
	}

	/**
	 * ��ʼ��ftp������Ϣ
	 * 
	 * @param ftpIp
	 * @param ftpPort
	 * @param ftpUser
	 * @param ftpPassword
	 */
	public FTPUtil(String ftpIp, int ftpPort, String ftpUser,
			String ftpPassword, String ftpDir) {
		this.ip = ftpIp;
		this.port = ftpPort;
		this.user = ftpUser;
		this.password = ftpPassword;
		this.ftpDir = ftpDir;
	}

	/**
	 * ftp �ϴ��ļ�
	 * 
	 * @param ftpDir
	 *            ftp����·�� ex: temp/ssms
	 * @param localDir
	 *            �����ļ�·�� ex: c:/test.txt
	 * @param targetFileName
	 *            ftp�ļ��� ex: test.txt
	 * @throws Exception
	 */
	public void putFiles(String ftpDir, String localDir, String targetFileName)
			throws Exception {

		FTPClient ftp = null;
		try {
			ftp = getFTPClient();
			if (!"".equals(ftpDir)) {
				ftp.chdir(ftpDir);
				Date nowDate = new Date();
				String datefile = PublicUtil.getDateString(nowDate, "yyyyMMdd");
				this.mkdirs(ftp, datefile);// ���û�д����ļ��в������ļ��У��н����ļ���
			}
			ftp.put(localDir, targetFileName);
			if (logger.isDebugEnabled()) {
				logger.debug("put file  to FTPServer... success");
			}
		} finally {
			if (ftp != null) {
				try {
					ftp.quit();
				} catch (Exception e) {
				}
			}

		}
	}

	public void putFiles2(String ftpDir, String localDir,
			String targetFileName) throws Exception {



		FTPClient toFTPClinet = null;
		try{
	//		if (!"".equals(ftpDir)) {
	//			ftp.chdir(ftpDir);
	////			Date nowDate = new Date();
	////			String datefile = PublicUtil.getDateString(nowDate, "yyyyMMdd");
	////			this.mkdirs(ftp, datefile);// ���û�д����ļ��в������ļ��У��н����ļ���
	//		}
			toFTPClinet = getFTPClient();
			if (!"".equals(ftpDir)) {
				toFTPClinet.chdir(ftpDir);
			System.out.println("�л�ftpDir"+ftpDir+"������");	
			}
			toFTPClinet.put(localDir, targetFileName);
			if (logger.isDebugEnabled()) {
				logger.debug("put file  to FTPServer... success");
			}
			}catch(Exception e){}
		finally {
			if (toFTPClinet != null) {
				try {
					toFTPClinet.quit();
				} catch (Exception e) {
				}
			}

		}
	}

	public void putFilesNoMkNewDateDir(String ftpDir, String localDir,
			String targetFileName) throws Exception {

		FTPClient ftp = null;
		ftp = getFTPClient();
		this.mkdirs(ftp, ftpDir);
		try {
			ftp.put(localDir, targetFileName);
			if (logger.isDebugEnabled()) {
				logger.debug("put file  to FTPServer... success");
			}
		} finally {
			if (ftp != null) {
				try {
					ftp.quit();
				} catch (Exception e) {
				}
			}

		}
	}

	public String[] getFilesNoRegex(String ftpDir, String localDir)
			throws Exception {
		FTPClient ftp = null;
		// ��Ż�ȡ�����ļ��б��list
		ArrayList fileList = new ArrayList();
		try {
			IOUtil.checkAndCreateDir(localDir);
			ftp = getFTPClient();
			if (!"".equals(ftpDir)) {
				ftp.chdir(ftpDir);
			}
			String[] remoteFiles = ftp.dir();
			String fileName = null;
			for (int i = 0; i < remoteFiles.length; i++) {
				fileName = remoteFiles[i];
				String absolutePath = localDir + File.separator + fileName;
				absolutePath = absolutePath.replace('\\', '/');
				ftp.get(absolutePath, fileName);
				fileList.add(absolutePath);
				ftp.delete(fileName);// ɾ���ļ�
				if (logger.isDebugEnabled()) {
					logger.debug("�ɹ������ļ���" + absolutePath);
				}
			}
			String fileNames[] = new String[fileList.size()];
			fileList.toArray(fileNames);
			return (String[]) fileList.toArray(fileNames);
		} finally {
			if (ftp != null) {
				try {
					ftp.quit();
				} catch (Exception e) {
				}
			}

		}
	}

	/**
	 * ftp �����ļ�
	 * 
	 * @param ftpDir
	 *            ftp����·��
	 * @param localDir
	 *            �����ļ�·��
	 * @return
	 */
	public String[] getFiles(String ftpDir, String localDir) throws Exception {

		FTPClient ftp = null;
		try {
			// �������debug��־�����汾������ͬ�����ļ���
			String debugNameRegex = "";
			// ȷ����ǰ����Ҫͬ�����ļ���
			for (int i = 0; i < fNameRegex.length; i++) {
				fName[i] = parseFileName(fNameRegex[i]);
				debugNameRegex += fName[i] + ",";
			}
			// ȥ�����һ������
			debugNameRegex = debugNameRegex.substring(0, debugNameRegex
					.length() - 1);
			// ��ȷ������Ŀ¼�Ѿ������ˡ�
			IOUtil.checkAndCreateDir(localDir);

			// ��Ż�ȡ�����ļ��б��list
			ArrayList fileList = new ArrayList();

			// ȡ��Զ��Ŀ¼���ļ��б�
			ftp = getFTPClient();
			if (!"".equals(ftpDir)) {
				ftp.chdir(ftpDir);
			}
			String[] Remotefiles = ftp.dir();
			// ����������ʽ����ȡƥ����ļ��������档
			if (logger.isDebugEnabled()) {
				logger.debug("ƥ���ļ�����ʼ��");// + debugNameRegex);
			}
			for (int j = 0; j < Remotefiles.length; j++) {
				String RemotefileName = Remotefiles[j];
				Remotefiles[j].substring(Remotefiles[j].lastIndexOf("/") + 1);
				// if (RemotefileName.matches(fNameRegex))
				if (isMatchFileName(RemotefileName)) {
					String absolutePath = localDir + File.separator
							+ RemotefileName;
					absolutePath = absolutePath.replace('\\', '/');
					ftp.get(absolutePath, Remotefiles[j]);
					fileList.add(absolutePath);
					if (logger.isDebugEnabled()) {
						logger.debug("�ɹ������ļ���" + absolutePath);
					}
				}
			}

			String fileName[] = new String[fileList.size()];
			return (String[]) fileList.toArray(fileName);
		} finally {
			if (ftp != null) {
				try {
					ftp.quit();
				} catch (Exception e) {
				}
			}

		}

	}

	/**
	 * ��ftp�������ϴ����ļ���
	 * 
	 * @param ftp
	 * @param remotePath
	 * @throws IOException
	 * @throws FTPException
	 */
	private void mkdirs(FTPClient ftp, String remotePath) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("ftp��Դ�������ϻ�����Ʒ�������ظ����ֻ��ʴ��Ŀ¼ remotePath == "
					+ remotePath);
		}
		String[] paths = remotePath.split("/");
		if (paths != null) {
			for (int i = 0; i < paths.length; i++) {
				if ("".equals(paths[i])) {
					continue;
				}
				boolean isExist = true;

				try {
					ftp.chdir(paths[i]);
				} catch (Exception e) {
					logger.error(paths[i] + " Ŀ¼�����ڣ���Ҫ������");
					isExist = false;
				}
				if (!isExist) {
					try {
						ftp.mkdir(paths[i]);
					} catch (Exception e) {
						logger.error(paths[i] + " Ŀ¼�Ѿ��������̴߳�����");
					}
					ftp.chdir(paths[i]);
				}
			}
		}
	}

	public void isFilePathExits(String basePath, String subPath)
			throws Exception, FTPException {
		FTPClient ftp = null;
		try {
			ftp = getFTPClient();
			if (!"".equals(basePath)) {
				ftp.chdir(basePath);
			}
			ftp.mkdir(subPath);
		} finally {
			if (ftp != null) {
				try {
					ftp.quit();
				} catch (Exception e) {
				}
			}

		}
	}

	/*
	 * public static void main(String [] args) throws Exception{
	 * 
	 * FTPUtil fu = new FTPUtil("10.1.3.201",21,"max","1qazZAQ!");
	 * //fu.isFilePathExits("temp/ssms","2009"); // ip = "10.1.3.201"; // port =
	 * 21; // user ="max"; // password ="1qazZAQ!";
	 * fu.putFiles("temp/ssms","C:/test1.xls","test2.txt"); }
	 */

	public FTPClient getFTPClient() throws IOException, FTPException {
		//FTPClient ftp = new FTPClient(ip, port,false);
		FTPClient ftp = new FTPClient(ip, port);
		// ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
		ftp.setConnectMode(FTPConnectMode.PASV);

		// ʹ�ø������û����������½ftp
		if (logger.isDebugEnabled()) {
			logger.debug("login to FTPServer...");
		}
		ftp.login(user, password);
		// �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
		ftp.setType(FTPTransferType.BINARY);
		if (logger.isDebugEnabled()) {
			logger
					.debug("login FTPServer successfully,transfer type is binary");
		}
		return ftp;
	}
	public FileTransferClient getFTPClient(boolean type) throws IOException, FTPException {
		
		FileTransferClient ftp = null;
		ftp = new FileTransferClient(type);
		ftp.setRemoteHost(ip);
		ftp.setRemotePort(port);
        ftp.setUserName(user);
        ftp.setPassword(password);
		if (logger.isDebugEnabled()) {
			logger.debug("login to FTPServer...");
		}
		if (logger.isDebugEnabled()) {
			logger
					.debug("login FTPServer successfully,transfer type is binary");
		}
		return ftp;
	}
	/**
	 * �������������ַ����ļ���������ʽ����������~d��ʼ����~����
	 * 
	 * @param fNameRegex
	 * @return
	 */
	private String parseFileName(String fNameRegex) {
		if (fNameRegex == null) {
			return "";//
		}
		StringBuffer dateCharSequence = new StringBuffer();
		boolean dStart = false;
		boolean dEnd = false;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fNameRegex.length(); i++) {
			char c = fNameRegex.charAt(i);
			if (c == '~' && fNameRegex.charAt(i + 1) == 'D')// ֻ���ַ�Ϊ~DΪ��ͷ���ַ��ű�ʾ����
			{
				dStart = true;
				i++;// ��Ҫ������һ���ַ�
				continue;
			} else if (dStart && c == '~')// ƥ�����������ַ�������
			{
				String date = PublicUtil.getCurDateTime(dateCharSequence
						.toString());
				sb.append(date);
				dEnd = true;
				continue;
			}

			if (dStart && !dEnd)// ���������ַ�
			{
				dateCharSequence.append(c);
			} else
			// ��ӷ����������ַ�
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * �ж��Ƿ��Ǳ���������Ҫ���ص��ļ���,֧�ֶ���ļ�������ļ���Ӣ�Ķ��ŷָ�
	 * 
	 * @param fName
	 *            ��ǰftp�������ϵ��ļ�
	 * @return true �ǣ�false ��
	 */
	protected boolean isMatchFileName(String FtpFName) {

		for (int i = 0; i < fName.length; i++) {
			if (FtpFName.matches(fName[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ��������ftp��ָ��·����ָ���ļ�������ָ��Ŀ¼��Ϊָ���ļ���
	 * 
	 * @param ftpDir
	 * @param ftpFileName
	 * @param localDir
	 * @param localFileName
	 * @return
	 * @throws Exception 
	 */
	public String getFtpFileByFileName(String ftpFilePath, String ftpFileName,
			String localDir, String localFileName) throws IOException,
			FTPException, Exception {
		FileTransferClient  ftp = null;
		ftpTest();
		String absolutePath = null;

		try {
			// ��ȷ������Ŀ¼�Ѿ������ˡ�
			IOUtil.checkAndCreateDir(localDir);

			// ȡ��Զ��Ŀ¼���ļ��б�
			ftp = getFTPClient(false);
			ftp.connect();
			System.out.println("ftpDir==="+ftpDir);
			if (!"".equals(ftpDir)) {

				ftp.changeDirectory(ftpDir);
			}
			System.out.println("ftpFilePath===="+ftpFilePath);
			System.out.println("ftpFileName===="+ftpFileName);
			System.out.println("localFileName===="+localFileName);
			
			//ȥ��ǰ���б��/
			if(ftpFilePath.charAt(0) == '/')
	        {
				ftpFilePath=ftpFilePath.substring(1, ftpFilePath.length());
	           
	        }
	        if(ftpFilePath.lastIndexOf('/')==(ftpFilePath.length()-1)){
	        	ftpFilePath=ftpFilePath.substring(0, ftpFilePath.length()-1);
	        }

	    	System.out.println("ftpFilePath=new ==="+ftpFilePath);
			if (!"".equals(ftpFilePath)) {
				ftp.changeDirectory(ftpFilePath);
			}
           List filenameList =new ArrayList();
           System.out.println("ftp dir list ===");
			FTPFile[] files = ftp.directoryList(".");
			System.out.println("files.length-===="+files.length);
            for (int i = 0; i < files.length; i++) {
            	
            	if(files[i].isDir()){
            		 String   name=files[i].getName();
                     System.out.println("dir--------"+name);
            	}else{
            		String   name=files[i].getName();
                    System.out.println("file--------"+name);
                    filenameList.add(name);
            	}

            }
        	String[] tempfiles = null;
        	if(filenameList != null && filenameList.size()>0){
        		tempfiles  = new String[filenameList.size()];
        	}
            for(int r = 0 ; r < filenameList.size();r ++){
            	tempfiles[r] = (String) filenameList.get(r);
            }

			if (logger.isDebugEnabled()) {
				logger.debug("ƥ���ļ�����ʼ��");
			}

			for (int j = 0; j < tempfiles.length; j++) {
				String tempfileName = tempfiles[j];

				// �ж��Ƿ���ָ���ļ�
				if (tempfileName.equals(ftpFileName)) {
					// ���������ļ�
					absolutePath = localDir + File.separator + localFileName;
					absolutePath = absolutePath.replace('\\', '/');
					System.out.println("absolutePath="+absolutePath+";tempfiles[j]="+tempfiles[j]);
					ftp.downloadFile(absolutePath, tempfiles[j]);

					if (logger.isDebugEnabled()) {
						logger.debug("�ɹ������ļ���" + absolutePath);
					}

					return absolutePath;
				}
			}

			return "";
		} finally {
			if (ftp != null) {
				try {
					ftp.disconnect();
					///ftp.quit();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getFtpFileByFileName2(String ftpFilePath,
			String ftpFileName, String localDir, String localFileName) throws BOException{
		// ��ȷ������Ŀ¼�Ѿ������ˡ�

		FileTransferClient  fromFtpClient = null;
		String absolutePath = localDir + File.separator + localFileName;
		try{
			IOUtil.checkAndCreateDir(localDir);
			fromFtpClient = getFTPClient(false);
			//������ͨ����
			fromFtpClient.connect();
			//ȥ��ǰ���б��/
			if(ftpFilePath.charAt(0) == '/')
	        {
				ftpFilePath=ftpFilePath.substring(1, ftpFilePath.length());
	           
	        }
	        if(ftpFilePath.lastIndexOf('/')==(ftpFilePath.length()-1)){
	        	ftpFilePath=ftpFilePath.substring(0, ftpFilePath.length()-1);
	        }
	        //System.out.println("localFileName="+localFileName+";localDir="+localDir+";ftpFilePath="+ftpFilePath);	
	     
			if (!"".equals(ftpFilePath)) {
				fromFtpClient.changeDirectory(ftpFilePath);
				//fromFtpClient.chdir(ftpFilePath);
			//System.out.println("�л�ftpFilePath"+ftpFilePath+"������");	
			}
			System.out.println("absolutePath="+absolutePath+";ftpFilePath="+ftpFilePath+";ftpFileName="+ftpFileName);	
				absolutePath = absolutePath.replace('\\', '/');
				//fromFtpClient.get(absolutePath, ftpFileName);
				fromFtpClient.downloadFile(absolutePath, ftpFileName);
				if (logger.isDebugEnabled()) {
					logger.debug("�ɹ������ļ���" + absolutePath);
				}
				return absolutePath;
		}
		catch(Exception e){
			logger.error("�����ļ������ˣ����������ļ������ڣ�"+absolutePath+"|"+ftpFileName);
			throw new BOException ("�����ļ������ˣ����������ļ������ڣ�",e);
		}finally {
			if (fromFtpClient != null) {
				try {
					fromFtpClient.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//return "";
	}
	
	
	public static void ftpTest() 
    {
        try
        {
            System.out.println("ftp start");
            FileTransferClient ftp = null;
            System.out.println("ftp false");
            ftp = new FileTransferClient(false);

            System.out.println("ftp config");
            ftp.setRemoteHost("221.181.100.61");
            ftp.setRemotePort(21);
            ftp.setUserName("wondertek");
            ftp.setPassword("Wd3Wd@2012");
            System.out.println("ftp config end");
            ftp.connect();
            System.out.println("ftp connect end");
            ftp.changeDirectory("depository/image/10/31");
            //FTPFile[] files = ftp.directoryList(".");
            //System.out.println("ftp dir...files.length="+files.length);
            String name="999.gif";
            /*for (int i = 0; i < files.length; i++) {
                name=files[i].getName();
                if(files[i].isDir()){
                	System.out.println("dirname = --------"+name);
                }else{
                	System.out.println("filename = --------"+name);*/
               //ftp.downloadFile(name + ".copy", name);
               
               ftp.downloadFile("/opt/aspire/product/mm_ssms/ssmsdomain/ssmsserver/ssms/log/"+name , name);
            //    }
          //  }
            System.out.println("ftp qiut");
            ftp.disconnect();
            System.out.println("ftp end");
        }
        catch (FTPException e)
        {
            // And show the Error Screen.
            ByteArrayOutputStream buf=new ByteArrayOutputStream();
            e.printStackTrace(new PrintWriter(buf,true));
            System.out.println("FTPException:"+buf.toString());
        }
        
        catch (IOException e)
        {
            ByteArrayOutputStream buf=new ByteArrayOutputStream();
            e.printStackTrace(new PrintWriter(buf,true));
            System.out.println("IOException:"+buf.toString());
        }
        
    }

	
}
