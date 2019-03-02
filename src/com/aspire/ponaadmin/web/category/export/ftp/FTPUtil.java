/**
 * <p>
 * FTP工具实现类
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
	 * 用于匹配当前任务的文件名的正则表达式
	 */
	protected String[] fNameRegex;
	/**
	 * 用于匹配当前任务的文件名的正则表达式
	 */
	protected String[] fName;

	private String ip;
	private int port;
	private String user;
	private String password;
	private String ftpDir;
	
	private String initPwd;//第一次连接上FTP的时候，记录下用户的开始路径。//add by aiyan 2012-07-17

	/**
	 * 初始化ftp连接信息
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
	 * 初始化ftp连接信息
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
	 * ftp 上传文件
	 * 
	 * @param ftpDir
	 *            ftp数据路径 ex: temp/ssms
	 * @param localDir
	 *            本地文件路径 ex: c:/test.txt
	 * @param targetFileName
	 *            ftp文件名 ex: test.txt
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
				this.mkdirs(ftp, datefile);// 如果没有创建文件夹并进入文件夹，有进入文件件
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
	////			this.mkdirs(ftp, datefile);// 如果没有创建文件夹并进入文件夹，有进入文件件
	//		}
			toFTPClinet = getFTPClient();
			if (!"".equals(ftpDir)) {
				toFTPClinet.chdir(ftpDir);
			System.out.println("切换ftpDir"+ftpDir+"正常！");	
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
		// 存放获取到的文件列表的list
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
				ftp.delete(fileName);// 删除文件
				if (logger.isDebugEnabled()) {
					logger.debug("成功下载文件：" + absolutePath);
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
	 * ftp 下载文件
	 * 
	 * @param ftpDir
	 *            ftp数据路径
	 * @param localDir
	 *            本地文件路径
	 * @return
	 */
	public String[] getFiles(String ftpDir, String localDir) throws Exception {

		FTPClient ftp = null;
		try {
			// 用于输出debug日志，保存本次任务同步的文件名
			String debugNameRegex = "";
			// 确定当前任务要同步的文件名
			for (int i = 0; i < fNameRegex.length; i++) {
				fName[i] = parseFileName(fNameRegex[i]);
				debugNameRegex += fName[i] + ",";
			}
			// 去除最后一个逗号
			debugNameRegex = debugNameRegex.substring(0, debugNameRegex
					.length() - 1);
			// 先确保本地目录已经创建了。
			IOUtil.checkAndCreateDir(localDir);

			// 存放获取到的文件列表的list
			ArrayList fileList = new ArrayList();

			// 取得远程目录中文件列表
			ftp = getFTPClient();
			if (!"".equals(ftpDir)) {
				ftp.chdir(ftpDir);
			}
			String[] Remotefiles = ftp.dir();
			// 根据正则表达式来获取匹配的文件，并保存。
			if (logger.isDebugEnabled()) {
				logger.debug("匹配文件名开始：");// + debugNameRegex);
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
						logger.debug("成功下载文件：" + absolutePath);
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
	 * 在ftp服务器上创建文件夹
	 * 
	 * @param ftp
	 * @param remotePath
	 * @throws IOException
	 * @throws FTPException
	 */
	private void mkdirs(FTPClient ftp, String remotePath) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("ftp资源服务器上货架商品导出或重复率轮换率存放目录 remotePath == "
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
					logger.error(paths[i] + " 目录不存在，需要创建！");
					isExist = false;
				}
				if (!isExist) {
					try {
						ftp.mkdir(paths[i]);
					} catch (Exception e) {
						logger.error(paths[i] + " 目录已经由其他线程创建！");
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
		// 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
		ftp.setConnectMode(FTPConnectMode.PASV);

		// 使用给定的用户名、密码登陆ftp
		if (logger.isDebugEnabled()) {
			logger.debug("login to FTPServer...");
		}
		ftp.login(user, password);
		// 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
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
	 * 解析含有日期字符的文件名正则表达式，日期是以~d开始，以~结束
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
			if (c == '~' && fNameRegex.charAt(i + 1) == 'D')// 只有字符为~D为开头的字符才表示日期
			{
				dStart = true;
				i++;// 需要跳过下一个字符
				continue;
			} else if (dStart && c == '~')// 匹配日期特殊字符结束。
			{
				String date = PublicUtil.getCurDateTime(dateCharSequence
						.toString());
				sb.append(date);
				dEnd = true;
				continue;
			}

			if (dStart && !dEnd)// 特殊日期字符
			{
				dateCharSequence.append(c);
			} else
			// 添加非特殊日期字符
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 判读是否是本次任务需要下载的文件名,支持多个文件，多个文件以英文逗号分隔
	 * 
	 * @param fName
	 *            当前ftp服务器上的文件
	 * @return true 是，false 否
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
	 * 用于下载ftp上指定路径下指定文件至本地指定目录下为指定文件名
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
			// 先确保本地目录已经创建了。
			IOUtil.checkAndCreateDir(localDir);

			// 取得远程目录中文件列表
			ftp = getFTPClient(false);
			ftp.connect();
			System.out.println("ftpDir==="+ftpDir);
			if (!"".equals(ftpDir)) {

				ftp.changeDirectory(ftpDir);
			}
			System.out.println("ftpFilePath===="+ftpFilePath);
			System.out.println("ftpFileName===="+ftpFileName);
			System.out.println("localFileName===="+localFileName);
			
			//去掉前后的斜杠/
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
				logger.debug("匹配文件名开始：");
			}

			for (int j = 0; j < tempfiles.length; j++) {
				String tempfileName = tempfiles[j];

				// 判断是否有指定文件
				if (tempfileName.equals(ftpFileName)) {
					// 创建本地文件
					absolutePath = localDir + File.separator + localFileName;
					absolutePath = absolutePath.replace('\\', '/');
					System.out.println("absolutePath="+absolutePath+";tempfiles[j]="+tempfiles[j]);
					ftp.downloadFile(absolutePath, tempfiles[j]);

					if (logger.isDebugEnabled()) {
						logger.debug("成功下载文件：" + absolutePath);
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
		// 先确保本地目录已经创建了。

		FileTransferClient  fromFtpClient = null;
		String absolutePath = localDir + File.separator + localFileName;
		try{
			IOUtil.checkAndCreateDir(localDir);
			fromFtpClient = getFTPClient(false);
			//设置普通下载
			fromFtpClient.connect();
			//去掉前后的斜杠/
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
			//System.out.println("切换ftpFilePath"+ftpFilePath+"正常！");	
			}
			System.out.println("absolutePath="+absolutePath+";ftpFilePath="+ftpFilePath+";ftpFileName="+ftpFileName);	
				absolutePath = absolutePath.replace('\\', '/');
				//fromFtpClient.get(absolutePath, ftpFileName);
				fromFtpClient.downloadFile(absolutePath, ftpFileName);
				if (logger.isDebugEnabled()) {
					logger.debug("成功下载文件：" + absolutePath);
				}
				return absolutePath;
		}
		catch(Exception e){
			logger.error("下载文件出错了！。。可能文件不存在！"+absolutePath+"|"+ftpFileName);
			throw new BOException ("下载文件出错了！。。可能文件不存在！",e);
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
