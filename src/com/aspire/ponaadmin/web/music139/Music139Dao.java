package com.aspire.ponaadmin.web.music139;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class Music139Dao {

	protected static JLogger log = LoggerFactory.getLogger(Music139Dao.class);

	public static RowSet listMusicInfo(String musicname, String singer) {
		RowSet r = null;
		try {
			return DB
					.getInstance()
					.query(
							
							"select t.songname,t.musicid,t.singer,t.VALIDITY from T_MB_MUSIC t where t.singer like ? and t.songname like ? and t.delflag=0 and to_date(t.validity,'yyyy-mm-dd')>= sysdate",
							new String[] { "%"+SQLUtil.escape(singer)+"%","%"+SQLUtil.escape(musicname)+"%" });
		} catch (DAOException e) {
			log.debug("数据库查询异常", e);
			return r;
		}
	}
	public static int updateMusicPicDir(String sql,Object[] paras) throws DAOException{
		return DB.getInstance().executeBySQLCode(sql, paras);
	}

}
