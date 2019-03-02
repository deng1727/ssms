/**
 * com.aspire.ponaadmin.web.newmusic139.bo New139PicSynBO.java
 * May 1, 2011
 *<p>
 * Copyright (c) 2003-2011 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * @author dongke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.newmusic139.bo;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.category.export.ftp.FTPUtil;

import com.aspire.ponaadmin.web.newmusic139.NewMusic139Config;
import com.aspire.ponaadmin.web.newmusic139.dao.New139BaseSyncDAO;

/**
 * @author tungke
 *图片导入接口类
 */
public class New139PicSynBO extends New139BaseSynBO
{

	private static final JLogger logger = LoggerFactory.getLogger(New139PicSynBO.class);
	private String[] filePaths;
	private StringBuffer sb = new StringBuffer();

	
	/**
	 * 
	 *@desc 139新音乐 专辑，榜单，音乐图片导入 总入口
	 *@author dongke
	 *May 1, 2011
	 */
	public void handDeal() {
		// TODO 自动生成方法存根
		this.processAlbum();
		this.processBillboard();
		this.processMusic();
		mailto = NewMusic139Config.getInstance().getMailTo();
		
		//this.r.setMsg(sb.toString());
		sendMail(sb.toString(),getOperationName());
	}
	
	public void processMusic()
	{
		// 1.copy歌曲图片到本地，并删除服务器上的目录
		try
		{
			filePaths = this.copyDirFromFTP(
					NewMusic139Config.getInstance().getLocalDir(), NewMusic139Config
							.getInstance().getNewSourceMusicFTPDir());
			// 2.将本地图片上传到指定的ftp服务器文件夹上
			sb.append("下载歌曲图片到本地成功<br>");
			this.uploadFileToFTP(filePaths, NewMusic139Config.getInstance()
					.getNewdestMusicFTPDir(), "0", true);
		} catch (Exception e)
		{
			// TODO 自动生成 catch 块
			sb.append("从ftp下载歌曲图片出错！<br>");

			logger.warn("从FTP"
					+ NewMusic139Config.getInstance().getNewSourceMusicFTPDir()
					+ "下载歌曲图片到本地" + NewMusic139Config.getInstance().getLocalDir()
					+ "出错<br>");
		}
		filePaths = null;
	}

	public void processAlbum()
	{
		// 1.copy专辑图片到本地，并删除服务器上的目录
		try
		{
			filePaths = this.copyDirFromFTP(
					NewMusic139Config.getInstance().getLocalDir(), NewMusic139Config
							.getInstance().getNewSourceAlbumFTPDir());
			// 2.将本地图片上传到指定的ftp服务器文件夹上
			sb.append("下载专辑图片到本地成功<br>");
			this.uploadFileToFTP(filePaths, NewMusic139Config.getInstance()
					.getNewDestAlbumFTPDir(), "1", false);
		} catch (Exception e)
		{
			// TODO 自动生成 catch 块
			sb.append("从ftp下载专辑图片出错！<br>");
			logger.warn("从FTP"
					+ NewMusic139Config.getInstance().getNewSourceAlbumFTPDir()
					+ "下载专辑图片到本地" + NewMusic139Config.getInstance().getLocalDir()
					+ "出错<br>");
		}
		filePaths = null;
	}
	public void processBillboard()
	{
		// 1.copy榜单图片到本地，并删除服务器上的目录
		try
		{
			filePaths = this.copyDirFromFTP(
					NewMusic139Config.getInstance().getLocalDir(), NewMusic139Config
							.getInstance().getNewSourceBillboardFTPDir());
			// 2.将本地图片上传到指定的ftp服务器文件夹上
			sb.append("下载榜单图片到本地成功<br>");
			this.uploadFileToFTP(filePaths, NewMusic139Config.getInstance()
					.getNewDestBillboardFTPDir(), "2", false);
		} catch (Exception e)
		{
			// TODO 自动生成 catch 块
			sb.append("从ftp下载专辑图片出错！<br>");
			logger.warn("从FTP"
					+ NewMusic139Config.getInstance().getNewSourceBillboardFTPDir()
					+ "下载榜单图片到本地" + NewMusic139Config.getInstance().getLocalDir()
					+ "出错<br>");
		}
		filePaths = null;
	}
	/**
	 * 从远程FTP服务器上下载文件到本地
	 * 
	 * @param localDir
	 *            下载保存到本地的路径
	 * @param FtpDir
	 *            远程FTP数据的路径
	 * @return 文件名列表信息
	 * @throws Exception
	 */
	public String[] copyDirFromFTP(String localDir, String ftpDir) throws Exception
	{
		// TODO 自动生成方法存根
		FTPUtil fUtil = new FTPUtil(NewMusic139Config.getInstance()
				.getSourceFTPServerIP(), Integer.valueOf(
				NewMusic139Config.getInstance().getSourceFTPServerPort()).intValue(),
				NewMusic139Config.getInstance().getSourceFTPServerUser(),
				NewMusic139Config.getInstance().getSourceFTPServerPassword());
		String[] filePaths = null;
		filePaths = fUtil.getFilesNoRegex(ftpDir, localDir);
		return filePaths;
	}

	/**
	 * 将本地文件上传到指定FPT服务器路径
	 * 
	 * @param filePath
	 *            文件详细路径：c:/qqq.txt
	 * @param ftpDir
	 *            要上传的远程FTP服务器的地址
	 * @param flag
	 *            是否要执行数据库更新操作
	 */
	public void uploadFileToFTP(String[] filePath, String ftpDir, String type,
			boolean flag)
	{
		// TODO 自动生成方法存根
		if (filePaths != null && filePaths.length > 0)
		{
			FTPUtil fUtil = new FTPUtil(NewMusic139Config.getInstance()
					.getDestFTPServerIP(), Integer.valueOf(
					NewMusic139Config.getInstance().getDestFTPServerPort()).intValue(),
					NewMusic139Config.getInstance().getDestFTPServerUser(),
					NewMusic139Config.getInstance().getDestFTPServerPassword());
			String fileName = null;
			String path = null;
			StringBuffer msg = new StringBuffer();
			int count = 0;
			for (int i = 0; i < filePaths.length; i++)
			{
				path = filePaths[i];
				if (path.indexOf('/') == -1)
				{
					path.replace('\\', '/');
				}
				fileName = path.substring(path.lastIndexOf('/') + 1);
				try
				{
					fUtil.putFilesNoMkNewDateDir(ftpDir, path, fileName);
					count++;
					String picUrl = null;
					if (type.equals("1"))
					{// 专辑
						picUrl = NewMusic139Config.getInstance().getNewAlbumPicUrl()
								+ fileName;
						updateAlbumPicDir(new Object[] { picUrl,
								fileName.substring(0, fileName.lastIndexOf('.')),NewMusic139Config.getInstance().getBaseAlbumCategoryId() });
					}else if(type.equals("2")){
//						榜单图片
						picUrl = NewMusic139Config.getInstance().getNewBillboardPicUrl()
						+ fileName;
						updateBillboardPicDir(new Object[] { picUrl,
								fileName.substring(0, fileName.lastIndexOf('.')),NewMusic139Config.getInstance().getBaseBillboardCategoryId() });
					}
					else if(type.equals("0"))
					{//音乐图片
						picUrl = NewMusic139Config.getInstance().getNewMusicPicUrl()
								+ fileName;
						updateMusicPicDir(new Object[] { picUrl,
								fileName.substring(0, fileName.lastIndexOf('.')) });
					}
					//if (flag)
						
				} catch (Exception e)
				{
					// TODO 自动生成 catch 块
					msg.append("图片" + fileName + "上传到ftp服务器出错<br>");
					logger.warn("从本地" + fileName + "上传文件到FTP服务器" + ftpDir + "失败" + e);
				}
			}
			if (count == filePaths.length)
				sb.append("图片上传到资源服务器成功！<br>");
			else
			{
				sb.append("图片上传到资源服务器失败：<br>");
				sb.append(msg);
			}
			msg = null;
			fUtil = null;
		}
	}

	/**
	 * 同步图片时，同时更新专辑中歌曲相应的图片路径字段
	 * 
	 */
	public void updateMusicPicDir(Object[] paras) throws DAOException
	{
		New139BaseSyncDAO.getInstance().updateMusicPic(paras);
	}
	/**
	 * 同步图片时，同时更新专辑相应的图片路径字段
	 * 
	 */
	public void updateAlbumPicDir(Object[] paras) throws DAOException
	{
		New139BaseSyncDAO.getInstance().updateAlbumPic(paras);
	}
	/**
	 * 同步图片时，同时更新榜单相应的图片路径字段
	 * 
	 */
	public void updateBillboardPicDir(Object[] paras) throws DAOException
	{
		New139BaseSyncDAO.getInstance().updateBillboardPic(paras);
	}

	public String getOperationName()
	{
		// TODO 自动生成方法存根
		return "139音乐接口--专辑、榜单，音乐图片同步";
	}

}
