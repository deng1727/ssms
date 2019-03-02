package com.aspire.ponaadmin.web.music139.pic;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.category.export.ftp.FTPUtil;
import com.aspire.ponaadmin.web.music139.AbstractMusic139SynBo;
import com.aspire.ponaadmin.web.music139.Music139Dao;

/**
 * 同步139音乐图片数据的接口实现类
 * @author yushiming 2011-02-19
 *
 */
public class PicSynchroBoImpl extends AbstractMusic139SynBo{
	
	private static final JLogger log = LoggerFactory.getLogger(PicSynchroBoImpl.class);
	private String[] filePaths;
	private StringBuffer sb=new StringBuffer();
	public void processMusic(){
		//1.copy歌曲图片到本地，并删除服务器上的目录
		try {
			filePaths=this.copyDirFromFTP(config.getLocalDir(),config.getSourceMusicFTPDir());
			//2.将本地图片上传到指定的ftp服务器文件夹上
			sb.append("下载歌曲图片到本地成功<br>");
			this.uploadFileToFTP(filePaths, config.getDestMusicFTPDir(),"musicPicUrl",true);	
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			sb.append("从ftp下载歌曲图片出错！<br>");
			
			log.warn("从FTP"+config.getSourceMusicFTPDir()+"下载歌曲图片到本地"+config.getLocalDir()+"出错<br>");
		}
		filePaths=null;	
	}
	
	public void processAlbum(){
		//1.copy专辑图片到本地，并删除服务器上的目录
		try {
			filePaths=this.copyDirFromFTP(config.getLocalDir(), config.getSourceAlbumFTPDir());
			//2.将本地图片上传到指定的ftp服务器文件夹上
			sb.append("下载专辑图片到本地成功<br>");
			this.uploadFileToFTP(filePaths, config.getDestAlbumFTPDir(),null,false);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			sb.append("从ftp下载专辑图片出错！<br>");
			log.warn("从FTP"+config.getSourceMusicFTPDir()+"下载专辑图片到本地"+config.getLocalDir()+"出错<br>");
		}
		filePaths=null;	
	}
	/**
	 * 从远程FTP服务器上下载文件到本地
	 * @param localDir 下载保存到本地的路径
	 * @param FtpDir 远程FTP数据的路径
	 * @return 文件名列表信息
	 * @throws Exception 
	 */
	public String[] copyDirFromFTP(String localDir, String ftpDir) throws Exception {
		// TODO 自动生成方法存根
		FTPUtil fUtil=new FTPUtil(config.getIp(),config.getPort(),config.getUser(),config.getPwd());
		String[] filePaths=null;
		filePaths = fUtil.getFilesNoRegex(ftpDir, localDir);
		return filePaths;
	}
	/**
	 * 将本地文件上传到指定FPT服务器路径
	 * @param filePath 文件详细路径：c:/qqq.txt
	 * @param ftpDir 要上传的远程FTP服务器的地址
	 * @param flag 是否要执行数据库更新操作
	 */
	public void uploadFileToFTP(String[] filePath, String ftpDir,String type,boolean flag) {
		// TODO 自动生成方法存根
		if(filePaths!=null && filePaths.length>0){
			FTPUtil fUtil=new FTPUtil(config.getDestFTPServerIP(),config.getDestFTPServerPort(),config.getDestFTPServerUser(),config.getDestFTPServerPassword());
			String fileName=null;
			String path=null;
			StringBuffer msg=new StringBuffer();
			int count=0;
			for(int i=0;i<filePaths.length;i++){
				path=filePaths[i];
				if(path.indexOf('/')==-1){
					path.replace('\\', '/');
				}
				fileName=path.substring(path.lastIndexOf('/')+1);
				try {
					fUtil.putFilesNoMkNewDateDir(ftpDir, path, fileName);
					count++;
					String picUrl=null;
					if(type==null){//专辑
						picUrl=config.getPicUrl()+fileName;
					}
					else{
						picUrl=config.getMusicPicUrl()+fileName;
					}
					if(flag)
					updateMusicPicDir(new Object[]{picUrl,fileName.substring(0, fileName.lastIndexOf('.'))});
				} catch (Exception e) {
					// TODO 自动生成 catch 块
					msg.append("图片"+fileName+"上传到ftp服务器出错<br>");
					log.warn("从本地"+fileName+"上传文件到FTP服务器"+ftpDir+"失败"+e);
				}
			}
			if(count==filePaths.length)
				sb.append("图片上传到资源服务器成功！<br>");
			else{
				sb.append("图片上传到资源服务器失败：<br>");
				sb.append(msg);
			}
			msg=null;
			fUtil=null;
		}	
	}
	/**
	 * 同步图片时，同时更新专辑中歌曲相应的图片路径字段
	 *
	 */
	public void updateMusicPicDir(Object[] paras) throws DAOException {
		// TODO 自动生成方法存根
		String sql="com.aspire.ponaadmin.web.music139.album.pic.PicSynchroDao.updateMusicPicDir";
		Music139Dao.updateMusicPicDir(sql, paras);
	}
	public String getOperationName() {
		// TODO 自动生成方法存根
		return "139音乐接口--专辑、音乐图片同步";
	}
	public String getPrefixName() {
		// TODO 自动生成方法存根
		return null;
	}
	protected void handleData(String file) throws Exception {
		// TODO 自动生成方法存根
	}
	public void synchroMusic139Data() {
		// TODO 自动生成方法存根
		this.processAlbum();
		this.processMusic();
		this.r.setMsg(sb.toString());
	}
	public void updateFileName() throws Exception {
		// TODO 自动生成方法存根
		
	}




	
}
