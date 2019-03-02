package com.aspire.ponaadmin.web.music139;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class KeywordSynchroBo extends AbstractMusic139SynBo {

	public KeywordSynchroBo() {
		ftpName = getFileName(config.getKeywordFileName(), (Date) null);
	}

	private static final JLogger log = LoggerFactory
			.getLogger(KeywordSynchroBo.class);
	public String getPrefixName() {
		return ".txt";
	}
	protected void handleData(String file) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream((new File(file))), "utf-8"));
		String temp = null;
		String[] line = null;
		PreparedStatement prst = null;
		PreparedStatement delPst = null;
		int rc = 0, dc = 0, ac = 0, p = 0, wzc = 0;
		StringBuffer sb = new StringBuffer();
		StringBuffer msg = new StringBuffer();
		try {

			prst = db
					.getConnection()
					.prepareStatement(
							"insert into T_MB_KEYWORD(keyword,key_id)values(?,SEQ_MB_KEYWORD.Nextval)");
			delPst = db.getConnection().prepareStatement(
					"delete T_MB_KEYWORD where keyword=?");

			while ((temp = reader.readLine()) != null) {
				line = temp.split("\\|");
				if (line.length < 2) {
					continue;
				}
				if (line[0] == null || "".equals(line[0])) {
					continue;
				}
				if ("2".equals(line[1])) {
					delPst.setString(1, line[0]);
					delPst.addBatch();
					dc++;
				} else {
					if ("1".equals(line[1])) {
						if (queryUniqueObject(
								"select keyword from t_mb_keyword where keyword=?",
								new Object[] { line[0] }) != null) {
							log.warn("插入的关键字重复!" + line[0]);
							sb.append(line[0]).append("<br>");
							rc++;
							continue;
						}
						prst.setString(1, line[0]);
						prst.addBatch();
						ac++;
					} else {
						log.warn("未知的操作类型! " + line[1]);
						wzc++;
						continue;
					}
				}
				p++;
				if (p % 500 == 0) {
					prst.executeBatch();
				}
			}
			delPst.executeBatch();
			prst.executeBatch();
		} catch (Exception e) {
			log.error(e);
			r.setMsg("操作失败，原因是: " + e.getMessage());
			return;
		} finally {
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}
			db.commit();
			db.close();
			if (reader != null) {
				reader.close();
			}
		}
		msg.append("成功同步关键字!其中删除个数：").append(dc).append("，新增个数：").append(ac);
		if (rc > 0) {
			msg.append("，重复个数：").append(rc).append("下面是重复的关键字:").append(
					sb.toString());
		}
		if (wzc > 0) {
			msg.append("，未知的操作类型个数为: " + wzc);
		}
		r.setSuccess(true);
		r.setMsg(msg.toString());
	}

	public String getOperationName() {
		return "139音乐接口--搜索关键字信息同步";
	}
    public void updateFileName() throws Exception
    {
        ftpName = getFileName(config.getKeywordFileName(), ( Date ) null);
    }

}
