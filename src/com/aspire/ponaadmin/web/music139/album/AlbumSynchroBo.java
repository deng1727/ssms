package com.aspire.ponaadmin.web.music139.album;

import java.util.Date;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.music139.AbstractSynchroData;

public class AlbumSynchroBo extends AbstractSynchroData {
	private static final JLogger log = LoggerFactory
			.getLogger(AlbumSynchroBo.class);

	static final String INSERT_T_MB_CATEGORY = "insert into T_MB_CATEGORY(categoryid,categoryname,parentcategoryid,categorydesc,"
			+ "album_id,album_pic,rate,type,delflag,createtime,sortid,sum,album_singer)values(?,?,?,?,?,?,?,'1','0',to_char(sysdate,'yyyy-MM-dd HH24:mi:ss'),?,?,?)";

	static final String INSERT_T_MB_REFERENCE = "insert into T_MB_REFERENCE(Musicid, CATEGORYID, MUSICNAME, CREATETIME, SORTID)values"
			+ "(?, ?,(select a.songname from t_mb_music a where musicid = ?), to_char(sysdate,'yyyy-MM-dd HH24:mi:ss'), ?)";

	static final String UDPATE_T_MB_CATEGORY = "update T_MB_CATEGORY set categoryname = ?,parentcategoryid = ?,categorydesc = ?,album_id = ?,album_pic = ?,rate = ?,sum = ?,album_singer = ?,sortid=?,CREATETIME=to_char(sysdate,'yyyy-MM-dd HH24:mi:ss')  where categoryid = ?";

	public AlbumSynchroBo() {
		ftpName = getFileName(config.getAlbumFileName(), (Date) null);
	}

	protected Object[] getUpdateCategoryParams(String categoryId,
			String[] line, int sum) {
		Object[] temp = new Object[] { 
				line[1], getParentCategoryId(), 
				line[3],
				line[0], 
				config.getPicUrl() + line[4],
				new Integer(Integer.parseInt(line[5] == null ? "5" : line[5])),
				new Integer(sum), 
				line[2],
				new Integer(Integer.parseInt(line[6])),//add sortID ysm 2011-02-19 				
				categoryId };
		return temp;
	}

	protected String getUpdateCategorySQL() {
		return UDPATE_T_MB_CATEGORY;
	}

	protected String getParentCategoryId() {
		return "100001913";
	}

	public AlbumSynchroBo(Date date) {
		ftpName = getFileName(config.getAlbumFileName(), date);
	}

	public AlbumSynchroBo(String date) {
		ftpName = getFileName(config.getAlbumFileName(), date);
	}

	protected int indexOfOperation() {
		return 8;
	}

	public String getOperationName() {
		return "139音乐接口--专辑基础信息同步";
	}

	protected Object[] getInsertCategoryObjectParams(String categoryId,
			String[] line, int sum) {
		Object[] temp = new Object[] { categoryId, line[1],
				getParentCategoryId(), line[3], line[0],
				config.getPicUrl() + line[4],
				new Integer(Integer.parseInt(line[5] == null ? "5" : line[5])),
				new Integer(Integer.parseInt(line[6])),//add sortID ysm 2011-02-19 
				new Integer(sum), line[2] };
		return temp;
	}

	protected String getInsertCategorySQL() {
		return INSERT_T_MB_CATEGORY;
	}

	protected boolean validateLine(String[] line) {
		if ("2".equals(line[this.indexOfOperation()])) {
			return true;
		}
		if (line[5] == null || line[5].length() == 0) {
			if (config.getDefRate() == -1) {
				log.warn("发现一项数据不完整：评分为空!");
				this.addInvalidateMsg("评分为空,专辑ID：" + line[0]);
				return false;
			}
		} else {
			try {
				int s = Integer.parseInt(line[5]);
				if (s != 1 && s != 2 && s != 3 && s != 3 && s != 4 && s != 5) {
					String t = "专辑:" + line[0] + "的评分不正确,值是：" + line[5];
					log.warn(t);
					this.addInvalidateMsg(t);
					return false;
				}
			} catch (NumberFormatException e) {
				String t = "专辑:" + line[0] + "的评分不正确,值是：" + line[5];
				this.addInvalidateMsg(t);
				return false;
			}
		}
		if (line[0].length() > 30) {
			log.warn("专辑:" + line[0] + ",长度超过30");
			this.addInvalidateMsg("专辑:" + line[0] + ",长度超过30");
			return false;
		}
		if (line[1].getBytes().length > 40) {
			log.warn("专辑:" + line[0] + ",长度超过40");
			this.addInvalidateMsg("专辑:" + line[0] + ",专辑名称长度超过40");
			return false;
		}
		if (line[3] != null && line[3].getBytes().length > 1000) {
			log.warn("专辑:" + line[0] + ",专辑简介超过1000");
			this.addInvalidateMsg("专辑:" + line[0] + ",专辑简介超过1000");
			return false;
		}
		if (line[4] != null && line[4].length() > 30) {
			log.warn("专辑:" + line[0] + ",图片名称长度超过30");
			this.addInvalidateMsg("专辑:" + line[0] + ",图片名称长度超过30");
			return false;
		}
		return true;
	}

    public void updateFileName() throws Exception
    {
        ftpName = getFileName(config.getAlbumFileName(), ( Date ) null);
    }

}
