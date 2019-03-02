package com.aspire.ponaadmin.web.music139.pic;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.category.export.ftp.FTPUtil;
import com.aspire.ponaadmin.web.music139.AbstractMusic139SynBo;
import com.aspire.ponaadmin.web.music139.Music139Dao;

/**
 * ͬ��139����ͼƬ���ݵĽӿ�ʵ����
 * @author yushiming 2011-02-19
 *
 */
public class PicSynchroBoImpl extends AbstractMusic139SynBo{
	
	private static final JLogger log = LoggerFactory.getLogger(PicSynchroBoImpl.class);
	private String[] filePaths;
	private StringBuffer sb=new StringBuffer();
	public void processMusic(){
		//1.copy����ͼƬ�����أ���ɾ���������ϵ�Ŀ¼
		try {
			filePaths=this.copyDirFromFTP(config.getLocalDir(),config.getSourceMusicFTPDir());
			//2.������ͼƬ�ϴ���ָ����ftp�������ļ�����
			sb.append("���ظ���ͼƬ�����سɹ�<br>");
			this.uploadFileToFTP(filePaths, config.getDestMusicFTPDir(),"musicPicUrl",true);	
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			sb.append("��ftp���ظ���ͼƬ����<br>");
			
			log.warn("��FTP"+config.getSourceMusicFTPDir()+"���ظ���ͼƬ������"+config.getLocalDir()+"����<br>");
		}
		filePaths=null;	
	}
	
	public void processAlbum(){
		//1.copyר��ͼƬ�����أ���ɾ���������ϵ�Ŀ¼
		try {
			filePaths=this.copyDirFromFTP(config.getLocalDir(), config.getSourceAlbumFTPDir());
			//2.������ͼƬ�ϴ���ָ����ftp�������ļ�����
			sb.append("����ר��ͼƬ�����سɹ�<br>");
			this.uploadFileToFTP(filePaths, config.getDestAlbumFTPDir(),null,false);
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			sb.append("��ftp����ר��ͼƬ����<br>");
			log.warn("��FTP"+config.getSourceMusicFTPDir()+"����ר��ͼƬ������"+config.getLocalDir()+"����<br>");
		}
		filePaths=null;	
	}
	/**
	 * ��Զ��FTP�������������ļ�������
	 * @param localDir ���ر��浽���ص�·��
	 * @param FtpDir Զ��FTP���ݵ�·��
	 * @return �ļ����б���Ϣ
	 * @throws Exception 
	 */
	public String[] copyDirFromFTP(String localDir, String ftpDir) throws Exception {
		// TODO �Զ����ɷ������
		FTPUtil fUtil=new FTPUtil(config.getIp(),config.getPort(),config.getUser(),config.getPwd());
		String[] filePaths=null;
		filePaths = fUtil.getFilesNoRegex(ftpDir, localDir);
		return filePaths;
	}
	/**
	 * �������ļ��ϴ���ָ��FPT������·��
	 * @param filePath �ļ���ϸ·����c:/qqq.txt
	 * @param ftpDir Ҫ�ϴ���Զ��FTP�������ĵ�ַ
	 * @param flag �Ƿ�Ҫִ�����ݿ���²���
	 */
	public void uploadFileToFTP(String[] filePath, String ftpDir,String type,boolean flag) {
		// TODO �Զ����ɷ������
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
					if(type==null){//ר��
						picUrl=config.getPicUrl()+fileName;
					}
					else{
						picUrl=config.getMusicPicUrl()+fileName;
					}
					if(flag)
					updateMusicPicDir(new Object[]{picUrl,fileName.substring(0, fileName.lastIndexOf('.'))});
				} catch (Exception e) {
					// TODO �Զ����� catch ��
					msg.append("ͼƬ"+fileName+"�ϴ���ftp����������<br>");
					log.warn("�ӱ���"+fileName+"�ϴ��ļ���FTP������"+ftpDir+"ʧ��"+e);
				}
			}
			if(count==filePaths.length)
				sb.append("ͼƬ�ϴ�����Դ�������ɹ���<br>");
			else{
				sb.append("ͼƬ�ϴ�����Դ������ʧ�ܣ�<br>");
				sb.append(msg);
			}
			msg=null;
			fUtil=null;
		}	
	}
	/**
	 * ͬ��ͼƬʱ��ͬʱ����ר���и�����Ӧ��ͼƬ·���ֶ�
	 *
	 */
	public void updateMusicPicDir(Object[] paras) throws DAOException {
		// TODO �Զ����ɷ������
		String sql="com.aspire.ponaadmin.web.music139.album.pic.PicSynchroDao.updateMusicPicDir";
		Music139Dao.updateMusicPicDir(sql, paras);
	}
	public String getOperationName() {
		// TODO �Զ����ɷ������
		return "139���ֽӿ�--ר��������ͼƬͬ��";
	}
	public String getPrefixName() {
		// TODO �Զ����ɷ������
		return null;
	}
	protected void handleData(String file) throws Exception {
		// TODO �Զ����ɷ������
	}
	public void synchroMusic139Data() {
		// TODO �Զ����ɷ������
		this.processAlbum();
		this.processMusic();
		this.r.setMsg(sb.toString());
	}
	public void updateFileName() throws Exception {
		// TODO �Զ����ɷ������
		
	}




	
}
