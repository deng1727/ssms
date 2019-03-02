package com.aspire.ponaadmin.web.music139.tag;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.music139.AbstractMusic139SynBo;

public class MusicTagSynchroBo extends AbstractMusic139SynBo {

	public MusicTagSynchroBo() {
		ftpName = getFileName(config.getTagFileName(), (Date) null);
	}

	public String getOperationName() {
		return "139音乐接口--歌曲标签信息同步";
	}

	public String getPrefixName() {
		return ".xls";
	}

	private static final JLogger log = LoggerFactory
			.getLogger(MusicTagSynchroBo.class);

	protected void handleData(String file) throws Exception {
		long start = System.currentTimeMillis();
		r.setName("139音乐歌曲标签信息同步接口");
		r.setSuccess(false);
		Workbook book = null;
		try {
			book = Workbook.getWorkbook(new File(file));
		} catch (BiffException e2) {
			r.setMsg("读取EXCEL文件出错" + e2.getMessage());
			log.debug(e2);
			throw e2;
		} catch (IOException e2) {
			r.setMsg("读取EXCEL文件出错" + e2.getMessage());
			log.debug(e2);
			throw e2;
		}
		Sheet[] st = book.getSheets();
		Cell[] cs = null;
		PreparedStatement prst = null;
		List noMusic = new ArrayList();
		List noMusicId = new ArrayList();
		List noTags = new ArrayList();
		int count = 0, updateCount = 0,sum=0;
		try {
			for (int i = 0, j = st[0].getRows(); i < j; i++) {
				prst = db.getConnection().prepareStatement(SQL_UPDATE_TAG);
				cs = st[0].getRow(i);
				if (cs == null) {
					continue;
				}
				sum++;
				if (cs[0] == null || "".equals(cs[0].getContents())) {
					log.warn("musicid为空,序号是：" + i);
					noMusicId.add(new Integer(i));
					continue;
				}
				if (cs[3] == null || "".equals(cs[3].getContents())) {
					log.warn("标签为空,序号是：" + i);
					noTags.add(cs[0].getContents());
					continue;
				}
				prst.setString(1, buildTags(cs[3].getContents()));
				prst.setString(2, cs[0].getContents());
				int r = prst.executeUpdate();
				if (r == 1) {
					updateCount++;
				} else {
					noMusic.add(((Cell[]) st[0].getRow(i))[0].getContents());
				}
				prst.close();
			}
			// prst.addBatch();
			// if ((i % 100 == 0)) {
			// if (i != 0) {
			// int[] s = prst.executeBatch();
			// for (int n = 0; n < s.length; n++) {
			// if (s[n] == 0) {
			// noMusic.add(((Cell[]) st[0].getRow(count * 100
			// + n))[0].getContents());
			// } else {
			// updateCount++;
			// }
			// }
			// count++;
			// }
			// }
			// }
			// int[] temp = prst.executeBatch();
			// for (int n = 0; n < temp.length; n++) {
			// if (temp[n] == 0) {
			// noMusic.add(((Cell[]) st[0].getRow(count * 100 +
			// n))[0].getContents());
			// } else {
			// updateCount++;
			// }
			// }
			db.commit();
		} catch (SQLException e1) {
			r.setMsg("数据库错误" + e1.getMessage());
			log.debug(e1);
			throw e1;
		} catch (DAOException e1) {
			r.setMsg("数据库错误" + e1.getMessage());
			log.debug(e1);
			throw e1;
		} finally {
			if (db != null) {
				db.close();
			}
		}
		getR().setSuccess(true);
		StringBuffer sb = new StringBuffer("139音乐接口--歌曲标签信息同步操作结果:")
				.append("<br>");
		sb.append("总共处理数据："+sum+"，共耗时:" + (System.currentTimeMillis() - start) + " ms ");
		sb.append("其中更新标签个数：").append(updateCount).append("<br>");
		if (noMusic.size() > 0) {
			sb.append("没有匹配的musicId的个数：").append(noMusic.size()).append(
					" ,以下是对应的MusicId:<br>");
			for (int i = 0, j = noMusic.size(); i < j && i < 100; i++) {
				sb.append(noMusic.get(i));
				if (i != j) {
					sb.append(",");
				}
				if (i % 10 == 0) {
					if (i != 0)
						sb.append("<br>");
				}
			}
			if (noMusic.size() > 100) {
				sb.append("......");
			}
		}
		sb.append("<br>");
		if (noMusicId.size() > 0) {
			sb.append("缺少musicId的个数：").append(noMusicId.size()).append(
					" ,以下是对应的行序号:<br>");
			for (int i = 0, j = noMusicId.size(); i < j && i < 100; i++) {
				sb.append(noMusicId.get(i));
				if (i != j) {
					sb.append(",");
				}
				if (i % 10 == 0) {
					if (i != 0)
						sb.append("<br>");
				}
			}
			if (noMusicId.size() > 100) {
				sb.append("......");
			}
		}
		sb.append("<br>");
		if (noTags.size() > 0) {
			sb.append("缺少标签的个数：").append(noTags.size()).append(
					" ,以下是对应的MusicId:<br>");
			for (int i = 0, j = noTags.size(); i < j && i < 100; i++) {
				sb.append(noTags.get(i));
				if (i != j) {
					sb.append(",");
				}
				if (i % 10 == 0) {
					if (i != 0)
						sb.append("<br>");
				}
			}
			if (noTags.size() > 100) {
				sb.append("......");
			}
		}

		getR().setMsg(sb.toString());
	}

	private String buildTags(String tags) {
		String[] temp = tags.split("\\|");
		int count = temp.length;
		if (count == 0) {
			return "{" + tags + "}";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			if (i != 0) {
				sb.append(";");
			}
			sb.append("{").append(temp[i]).append("}");
		}
		return sb.toString();
	}

	static final String SQL_UPDATE_TAG = "update T_MB_MUSIC set tags=?,updatetime=to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') where musicid=?";

	private void validate(Cell[] temp) throws NullException,
			LengthOutOfException {
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] == null || temp[i].getContents() == null
					|| "".equals(temp[i].getContents())) {
				throw new NullException(i);
			}
			if (temp[i].getContents().length() > langths[i]) {
				throw new LengthOutOfException(i);
			}
		}
	}

	static String[] names = { "歌曲ID", "歌曲名称", "歌手", "标签" };

	static int[] langths = { 30, 40, 40, 10000 };

	static class NullException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		int index;

		public NullException(int i) {
			this.index = i;
		}
	}

	static class LengthOutOfException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		int index;

		public LengthOutOfException(int i) {
			this.index = i;
		}
	}

    public void updateFileName() throws Exception
    {
        ftpName = getFileName(config.getTagFileName(), (Date) null);
    }
}
