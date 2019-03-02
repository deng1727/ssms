package com.aspire.ponaadmin.web.dataexport.wapcategory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.FileUtils;
import com.aspire.ponaadmin.web.dataexport.appexp.ExportResult;
import com.aspire.ponaadmin.web.mail.Mail;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;

public class WapCategoryExportBo {

	private static final JLogger logger = LoggerFactory
			.getLogger(WapCategoryExportBo.class);

	public static final String NAME = "wapCategoryExport";

	public static ExportData export() throws BOException {
		ExportData rest = null;
		boolean flag = true;
		long s = System.currentTimeMillis();
		String msg = null;
		try {
			rest = getData();
			exportData(rest.data);
		} catch (SQLException e) {
			msg = "导出失败，原因是" + "访问数据库时发生错误!";
		} catch (DAOException e) {
			msg = "导出失败，原因是" + "访问数据库时发生错误!";
		} catch (IOException e) {
			msg = "导出失败，原因是" + "导出时写文件错误!";
		} catch (FTPException e) {
			msg = "导出失败，原因是" + "访问FTP服务器时发生错误!";
		} catch (Exception e) {
			flag = false;
			logger.error(msg, e);
			throw new BOException("访问FTP服务器时发生错误!");
		}
		long e = System.currentTimeMillis();
		rest.setSuccess(flag);
		rest.setMsg(rest.getOperation() + "成功！共导出记录条数为[" + rest.count + "]");
		rest.setSTime(s);
		rest.setETime(e);
		Mail.sendMail(rest.getOperation(), rest.getEmailContent(), config
				.getEmails());
		return rest;
	}

	private static IConfig config = FileConfigImpl.getInstance(NAME);

	public static IConfig getConfig() {
		return config;
	}

	public static String getFileName(String filename) {
		String p = filename.substring(filename.indexOf("_") + 1, filename
				.indexOf(".txt"));
		SimpleDateFormat sdf = new SimpleDateFormat(p);
		String date = sdf.format(new Date());
		String fileName = filename.replaceAll(p, date);
		return fileName;
	}

	private static void exportData(String data) throws IOException,
			FTPException {
		byte[] buf = data.getBytes();
		ByteArrayInputStream bis = new ByteArrayInputStream(buf);
		try {
			FileUtils.writeToFile(config.getLocalDir() + File.separator
					+ getFileName(config.getLocalFileName()), bis);
		} catch (IOException e) {
			logger.error("导出WAP货架时出错", e);
			throw e;
		}
		FTPClient ftp = config.getFtpClient();
		if (ftp == null) {
			return;
		}
		try {
			ByteArrayInputStream nbis = new ByteArrayInputStream(buf);
			ftp.put(nbis, getFileName(config.getFtpFileName()));
		} catch (IOException e) {
			logger.error("本地文件写入完毕，往FTP服务器写数据出错", e);
			throw e;
		} catch (FTPException e) {
			logger.error("本地文件写入完毕，往FTP服务器写数据出错", e);
			throw e;
		}
	}

	private static ExportData getData() throws SQLException, DAOException {
		RowSet rs = null;
		// System.out.print(config.getParentId());
		if (config.getParentId() == null) {
			rs = WapCategoryExportDao.queryFromRoot();
		} else {
			rs = WapCategoryExportDao.querySpecifiedParentId(config
					.getParentId());
		}
		StringBuffer sb = new StringBuffer();
		long count = 0;
		while (rs.next()) {
			sb.append(rs.getString(1)).append("|").append(rs.getString(2))
					.append("\r\n");
			count++;
		}
		ExportData d = new ExportData(sb.toString(), count);
		return d;
	}

}

class ExportData extends ExportResult {
	String data;

	long count;

	public ExportData(String d, long c) {
		super("WAP货架数据导出");
		this.data = d;
		this.count = c;
	}

}
