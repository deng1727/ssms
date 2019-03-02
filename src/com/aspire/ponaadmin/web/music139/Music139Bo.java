package com.aspire.ponaadmin.web.music139;


import java.io.OutputStream;
import java.sql.SQLException;

import javax.sql.RowSet;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class Music139Bo {
	public static final String KEY_MUSIC_NAME = "musicname";

	public static final String KEY_MUSIC_ID = "musicid";

	public static final String KEY_SINGER = "singer";
	
	public static final String VALIDITY = "validity";
	protected static JLogger log = LoggerFactory.getLogger(Music139Bo.class);


	public static String queryMusicData(String musicname, String singer, OutputStream out) {
		StringBuffer sb = new StringBuffer("[");
		RowSet rs = Music139Dao.listMusicInfo(musicname, singer);
		boolean flag = true;
		try {
			while (rs.next()) {
				if (!flag) {
					sb.append(",");
				}
				sb.append("{\"");
				sb.append(KEY_MUSIC_ID).append("\":\"").append(rs.getString(2))
						.append("\",\"");
				sb.append(KEY_MUSIC_NAME).append("\":\"").append(rs.getString(1))
						.append("\",\"");
				sb.append(KEY_SINGER).append("\":\"").append(rs.getString(3))
										.append("\",\"");
				sb.append(VALIDITY).append("\":\"").append(rs.getString(4))
						.append("\"");
				sb.append("}");
				flag = false;
			}
		} catch (SQLException e) {
			log.debug("数据查询出错",e);
			return null;
		} 
		sb.append("]");
		return sb.toString();
	}
}
