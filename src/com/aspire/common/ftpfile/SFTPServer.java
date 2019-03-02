package com.aspire.common.ftpfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class SFTPServer 
{
	private final String CHANNEL_NAME = "sftp"; 
	private static final int DEFAULT_PORT = 22;
	protected String host;
	protected int port;
	protected String userName;
	protected String password;
	private JSch jSch = new JSch();
	private ChannelSftp sftp;
	private Session session;
	private static final String FTP_ROOT_DIR = "./";
	/**
	 * 构造方法（默认端口23）
	 * @param host 主机名
	 * @param userName SSH用户名
	 * @param password SSH密码
	 */
	public SFTPServer(String host,String userName,String password)
	{
		this(host, userName, password, DEFAULT_PORT);
	}
	/**
	 * 构造方法
	 * @param host 主机名
	 * @param userName SSH用户名
	 * @param password SSH密码
	 * @param port SSH端口
	 */
	public SFTPServer(String host,String userName,String password,int port) 
	{
		this.host = host;
		this.userName = userName;
		this.password = password;
		this.port = port;
	}
	/**
	 * 登录SFTP，获取SFTP连接
	 * @return
	 * @throws JSchException 
	 * @throws BoException
	 */
	public ChannelSftp login() throws JSchException 
	{
		
			if (session==null
					||sftp==null
					||!session.isConnected()
					||sftp.isClosed()
					||!sftp.isConnected()) 
			{
				initSession();
				initChannel();
			}
			if (!sftp.isConnected()||sftp.isClosed()) 
			{
				sftp.connect();
				
			}
		
		
		return sftp;
	}
	
	public void close(ChannelSftp sftp){
		if (sftp!=null) 
		{
			sftp.disconnect();
			sftp.exit();
			
		}
		if (session!=null) 
		{
			session.disconnect();
		}
		
	}
	/**
	 * 断开SFTP连接
	 * @param sftp
	 * @throws BoException
	 */
	public void disconnect() 
	{
		
			if (sftp!=null) 
			{
				sftp.disconnect();
				sftp.exit();
				
			}
			if (session!=null) 
			{
				session.disconnect();
			}
		
		
	}
	/**
	 * 获取SFTP通道对应的SSH会话
	 * @param sftp SFTP通道
	 * @return
	 * @throws JSchException 
	 * @throws BoException
	 */
	public static Session getSession(ChannelSftp sftp) throws JSchException 
	{
		
			return sftp.getSession();
		
		
	}
	/**
	 * 重新获得连接
	 * @throws JSchException 
	 * @throws BoException
	 */
	public ChannelSftp reconnect() throws JSchException 
	{
		
			if (session==null
					||sftp==null
					||!session.isConnected()) 
			{
				login();
			}
			else 
			{
				if (sftp.isClosed()) 
				{
					initChannel();
				}
				if (!sftp.isConnected()) 
				{
					sftp.connect();
				}
			}
			return sftp;
		} 
		
	
	
	private void initChannel() throws JSchException 
	{
		Channel channel = session.openChannel(CHANNEL_NAME);
		channel.connect();
		sftp = (ChannelSftp)channel;
	}
	
	private void initSession() throws JSchException 
	{
		session = jSch.getSession(userName, host,port);
		session.setPassword(password);
		Properties sshConfig = new Properties();
		sshConfig.setProperty("StrictHostKeyChecking", "no");
		session.setConfig(sshConfig);
		session.connect();
	}
	
	 /**
     * 检测并创建目录
     * @param fileName 文件路径
     */
    public static void checkAndCreateDir (String fileName)
    {
        if (fileName == null)
        {
            return ;
        }
        File file = new File(fileName) ;
        file.mkdirs() ;
    }
  
    
	/**
	 * 下载单个文件
	 * @param localPath 本地绝对路径
	 * @param remotePath 远程路径 包括文件名
	 * @param sftp ChannelSftp对象
	 * @throws BoException
	 */
	public static void downloadSingleFile(String localPath,String remotePath,ChannelSftp sftp) 
	{
		FileOutputStream fos = null;
		try 
		{
			
			
//			File rfile = new File(remotePath);
//			String name = rfile.getName();
//			String dir = rfile.getParent();
			
//			checkAndCreateDir(localPath);
//			File locaFile = new File(localPath+name);
//			if(dir != null && !dir.equals("")){
//				dir = dir.replace('\\', '/');
//				changeWorkDir(sftp,dir);
//			}
			//lsFile(sftp);
			sftp.pwd();
			fos = new FileOutputStream(localPath);
			sftp.get(remotePath,fos);
			fos.flush();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			//throw new BoException(25007,new String[]{SFTPServer.getSession(sftp).getHost(),remotePath},e);
		}
		finally
		{
			try
			{  if(fos != null){
				fos.close();
			}
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 上传单个文件
	 * @param targetDir 目标目录路径
	 * @param source 源(本地)文件绝对路径
	 * @param sftp ChannelSftp对象
	 * @throws IOException 
	 * @throws BoException
	 */
	private static void uploadSingleFile(String targetDir,String source,ChannelSftp sftp) throws IOException
	{
		FileInputStream fis = null;
		File file = new File(source);
		if (!file.exists()) 
		{
			System.out.println("source file is not exists!");
			//throw new BoException(25005,new String[]{source});
		}
		try 
		{
			mkdirs(sftp, targetDir);
			changeWorkDir(sftp, targetDir);
			fis = new FileInputStream(file);
			sftp.put(fis, file.getName());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
		}
		finally
		{
			
				fis.close();
			
		}
	}
	
	/**
	 * 
	 *@desc  获取FTP上当前目录的文件名列表
	 *@author dongke
	 *Jul 19, 2012
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public String[] getDirFilenames(ChannelSftp sftp) throws SftpException{
		String[] remoteFilenames = null;
		Vector v = sftp.ls("./");
		List fileList = new ArrayList();
		for (int i = 0; i < v.size(); i++)
		{
			LsEntry le = (LsEntry) v.get(i);
			SftpATTRS sft = le.getAttrs();
			String filename = le.getFilename();
			if (sft.isDir())
			{
				System.out.println("dir:" + filename);
			}
			else
			{
				System.out.println("file: " + filename);
				fileList.add(filename);
			}
		}
		if(fileList != null && fileList.size()>0){
			int s =  fileList.size();
			remoteFilenames = new String[s];
			for(int r = 0 ;r <s; r++){
				remoteFilenames[r] = (String) fileList.get(r);
			}
		}
		return remoteFilenames;
	}
	
	
	/**
	 * 上传单个文件
	 * @param targetDir 目标目录路径
	 * @param targetFileName 目标目录文件名
	 * @param fis 源(本地)文件流
	 * @param sftp ChannelSftp对象
	 * @throws IOException 
	 * @throws BoException
	 */
	private static void uploadSingleFile(String targetDir,String targetFileName,FileInputStream fis,ChannelSftp sftp) throws IOException
	{
		/*FileInputStream fis = null;
		File file = new File(source);
		if (!file.exists()) 
		{
			System.out.println("source file is not exists!");
			//throw new BoException(25005,new String[]{source});
		}*/
		try 
		{
			mkdirs(sftp, targetDir);
			changeWorkDir(sftp, targetDir);
			//fis = new FileInputStream(file);
			sftp.put(fis, targetFileName);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
		}
		finally
		{
				fis.close();
			
		}
	}
	/**
	 * 创建目录
	 * @param sftp
	 * @param dir
	 * @throws SftpException
	 */
	private static void mkdirs(ChannelSftp sftp,String dir) throws SftpException 
	{
		String currentDir = sftp.pwd();
		File tdir = new File(dir);
		try 
		{
			sftp.cd(dir);
		} 
		catch (Exception e) 
		{
			String parent = tdir.getParent();
			if (!(parent != null && !parent.equals("") &&currentDir.equals(parent))) 
			{
				mkdirs(sftp, parent);
				sftp.mkdir(dir);
			}
		}
		changeWorkDir(sftp, currentDir);
	}
	/**
	 * 更改工作目录
	 * @param sftp ChannelSftp对象
	 * @param targetDir 需要转变的工作目录路径
	 * @throws SftpException
	 */
	private static void changeWorkDir(ChannelSftp sftp,String targetDir) throws SftpException 
	{
		sftp.cd(FTP_ROOT_DIR);
		//lsFile(sftp);
		System.out.println("targetDir="+targetDir);
		sftp.cd(targetDir);
		lsFile(sftp);
	}
	
	  /**
     * 转换文件路径中的目录分隔符
     * @param path
     * @return
     */
    public static String parseFilePath(String path)
    {
        try
        {
            path = path.replace('\\', File.separatorChar);
            path = path.replace('/', File.separatorChar);
            path = path.replaceAll("((\\\\)+)|(/+)","\\"+File.separator);
        }
        catch (Exception e)
        {
            //logger.error(e);
        	e.printStackTrace();
            path = null;
        }
//        path = path.replaceAll("\\"+File.separator+"+", "\\"+File.separator);
        return path;
    }
    /**
     * 创建目录，如果目录已存在或创建成功，返回目录的File对象，否则返回null
     * @param path 目录路径
     * @return
     * @throws IOException
     */
    public static File buildDir(String path)throws IOException
    {
        File file = new File(path);
        if (buildDir(file))
        {
            return file;
        }else {
            return null;
        }
    }
    /**
     * 创建目录，如果file已存在且不是目录，删除file并重新创建，如果目录已存在或创建目录成功，返回true，否则返回null
     * @param file 目录的File对象
     * @return
     * @throws IOException
     */
    public static boolean buildDir(File file)throws IOException
    {
        if (!file.exists())
        {
            return file.mkdirs();
        }
        if (!file.isDirectory())
        {
            file.delete();
            return file.mkdirs();
        }
        return true;
    }
    public static void  lsFile(ChannelSftp sftp) throws SftpException
	{
		Vector v = sftp.ls("./");
		for (int i = 0; i < v.size(); i++)
		{
			LsEntry le = (LsEntry) v.get(i);
			SftpATTRS sft = le.getAttrs();
			String filename = le.getFilename();
			if (sft.isDir())
			{
				System.out.println("dir:" + filename);
			}
			else
			{
				System.out.println("file: " + filename);
			}
		}

	}
	public static void main(String[] args) throws Exception
	{
		SFTPServer server = new SFTPServer("10.1.3.167", "max", "pps.167",22);
		StringBuffer msg = new StringBuffer();
		ChannelSftp  sftp = server.login();
		
		//downloadSingleFile("d:\\temp\\ss\\","temp/ssms/100000254.png",sftp);
		
		//OK
		uploadSingleFile("temp/ssms/df/","d:\\temp\\push.xml",sftp);
	/*	sftp.cd("temp/ssms/");
		  Vector  v =  sftp.ls("./");
		  for(int i = 0 ; i < v.size();i ++){
			  LsEntry le = (LsEntry)v.get(i);
			SftpATTRS sft =   le.getAttrs();
			  String filename = le.getFilename();
			if(sft.isDir()){
				  System.out.println("dir:"+filename);
			}else{
			  System.out.println("file: "+filename);
			}
		  }
		  */
		//uploadDir("D:\\sftp_test", "keys/ddd", server, msg, Boolean.FALSE);
		System.out.println(msg.toString());
//		upload("/", "D:\\sftp_test\\aaa\\bbbb.txt", server);
		System.out.println("success");
		sftp.disconnect();
		server.disconnect();
	}

}
