package com.aspire.ponaadmin.web.music139;

import com.aspire.ponaadmin.web.dataexport.wapcategory.FileConfigImpl;

public class Music139Config extends FileConfigImpl {
	public Music139Config(String name) {
		super(name);
		this.initInner(name);
	}

	private static Music139Config config = new Music139Config("music139");

	public static Music139Config getConfig() {
		return config;
	}

	private String albumFileName;
	private String picUrl;
	private String billboardFileName;
	private String keywordFileName;
	private String tagFileName;
	//图片同步接口增加字段
	//专辑和歌曲的图片分开存放
	private String sourceAlbumFTPDir;
	private String sourceMusicFTPDir;
	//上传FTP服务器的信息
	private String destFTPServerIP;
	private int destFTPServerPort;
	private String destFTPServerUser;
	private String destFTPServerPassword;
//	专辑和歌曲的图片分开存
	private String destAlbumFTPDir;
	private String destMusicFTPDir;
	private String musicPicUrl;

	private int defRate = -1;

	protected void initInner(String name) {
		super.init(name);
		this.albumFileName = module.getItemValue("albumFileName");
		this.billboardFileName = module.getItemValue("billboardFileName");
		this.keywordFileName = module.getItemValue("keywordFileName");
		this.tagFileName = module.getItemValue("tagFileName");
		this.picUrl = module.getItemValue("picUrl");
		
		this.sourceAlbumFTPDir=module.getItemValue("SourceAlbumFTPDir").trim();
		this.sourceMusicFTPDir=module.getItemValue("SourceMusicFTPDir").trim();
		this.destFTPServerPort=Integer.parseInt(module.getItemValue("DestFTPServerPort").trim());
		this.destFTPServerIP=module.getItemValue("DestFTPServerIP").trim();
		this.destFTPServerUser=module.getItemValue("DestFTPServerUser").trim();
		this.destFTPServerPassword=module.getItemValue("DestFTPServerPassword").trim();
		this.destAlbumFTPDir=module.getItemValue("DestAlbumFTPDir").trim();
		this.destMusicFTPDir=module.getItemValue("DestMusicFTPDir").trim();
		this.musicPicUrl=module.getItemValue("MusicPicUrl").trim();
		
		if (module.getItem("rate") != null) {
			defRate = Integer.parseInt(module.getItemValue("rate"));
		}
	}

	public String getPicUrl() {
		return picUrl;
	}


	public int getDefRate() {
		return defRate;
	}

	public String getAlbumFileName() {
		return albumFileName;
	}

	public void setAlbumFileName(String albumFileName) {
		this.albumFileName = albumFileName;
	}

	public String getBillboardFileName() {
		return billboardFileName;
	}

	public void setBillboardFileName(String billboardFileName) {
		this.billboardFileName = billboardFileName;
	}

	public String getKeywordFileName() {
		return keywordFileName;
	}

	public String getTagFileName() {
		return tagFileName;
	}

	public void setTagFileName(String tagFileName) {
		this.tagFileName = tagFileName;
	}

	public String getDestAlbumFTPDir() {
		return destAlbumFTPDir;
	}

	public String getDestFTPServerIP() {
		return destFTPServerIP;
	}

	public String getDestFTPServerPassword() {
		return destFTPServerPassword;
	}

	public int getDestFTPServerPort() {
		return destFTPServerPort;
	}

	public String getDestFTPServerUser() {
		return destFTPServerUser;
	}

	public String getDestMusicFTPDir() {
		return destMusicFTPDir;
	}

	public String getSourceAlbumFTPDir() {
		return sourceAlbumFTPDir;
	}

	public String getSourceMusicFTPDir() {
		return sourceMusicFTPDir;
	}

	public String getMusicPicUrl() {
		return musicPicUrl;
	}

}
