package com.aspire.ponaadmin.web.dataexport.appexp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.FileUtils;
import com.aspire.ponaadmin.web.dataexport.wapcategory.FileConfigImpl;
import com.aspire.ponaadmin.web.dataexport.wapcategory.IConfig;
import com.aspire.ponaadmin.web.dataexport.wapcategory.WapCategoryExportBo;
import com.aspire.ponaadmin.web.mail.Mail;
import com.enterprisedt.net.ftp.FTPException;

public class AppsExpBo {
	private AppsExpDao dao = new AppsExpDao();

	public static final String NAME = "wapCategoryExport";

	private IConfig config = FileConfigImpl.getInstance(NAME);

	protected static JLogger log = LoggerFactory.getLogger(AppsExpBo.class);

	private static final AppsExpBo bo = new AppsExpBo();

	public static AppsExpBo getInstance() {
		return bo;
	}

	public ExportResult exportAll() {
		RowSet rs = null;
		ExportResult r = new ExportResult("WAP����Ӧ������ȫ������");
		long sum = 0;
		try {
			sum = dao.queryRecords();
			if (sum < 100000) {
				rs = dao.queryAppsAll();
				exportRs(rs, r, "All");
			} else {
				int size = this.getPageSize(sum), start = 1, end = 1 * size, repeat = (int) Math
						.ceil(sum / size);
				for (int i = 0; i < repeat; i++) {
					rs = dao.queryPageRecord(new Long(start), new Long(end));
					exportRs(rs, r, "All_" + String.valueOf(i));
					start = end + 1;
					end = (i + 2) * size;
				}
				r.setSize(sum);
				return r;
			}
		} catch (DAOException e) {
			log.error("���ݿ���ʳ���,ԭ����" + e.getMessage(), e);
			r.setMsg("���ݿ���ʳ���,ԭ����" + e.getMessage());
			r.setSuccess(false);
		} catch (SQLException e) {
			log.error("���ݿ���ʳ���,ԭ����" + e.getMessage(), e);
			r.setMsg("���ݿ���ʳ���,ԭ����" + e.getMessage());
			r.setSuccess(false);
		}
		return r;
	}

	public int getPageSize(long size) {
		if (size > 10000000) {
			return 20000;
		}
		if (size > 500000) {
			return 10000;
		}
		if (size > 100000) {
			return 5000;
		}
		if (size > 50000) {
			return 500;
		}
		return 200;
	}

	public ExportResult calculateTime(String operation, Date d) {
		long s = System.currentTimeMillis();
		ExportResult r = null;
		if ("All".equals(operation)) {
			if (log.isDebugEnabled()) {
				log.debug("��ʼȫ������WAPӦ������");
			}
			r = exportAll();
		} else {
			if (log.isDebugEnabled()) {
				log.debug("��ʼ��������WAPӦ������");
			}
			r = exportDay(d);
		}
		long e = System.currentTimeMillis();
		r.setETime(e);
		r.setSTime(s);
		if (log.isDebugEnabled()) {
			log.debug("����WAPӦ��������ɣ���ʼ����EMAIL֪ͨ��֪ͨ�����ǣ�" + r.getEmailContent());
		}
		Mail
				.sendMail(r.getOperation(), r.getEmailContent(), config
						.getEmails());
		if (log.isDebugEnabled()) {
			log.debug("����EMAIL֪ͨ���");
		}
		return r;
	}

	public ExportResult exportDay(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar c = Calendar.getInstance();
		String startDate = sdf.format(d);
		c.setTime(d);
		c.add(Calendar.DATE, 1);
		String endDate = sdf.format(c.getTime());
		RowSet rs = null;
		ExportResult r = new ExportResult("WAP����Ӧ��������������");
		try {
			rs = dao.queryAppsDay(startDate, endDate);
		} catch (DAOException e) {
			log.error("���ݿ���ʳ���,ԭ����" + e.getMessage(), e);
			r.setMsg("���ݿ���ʳ���,ԭ����" + e.getMessage());
			r.setSuccess(false);
		}
		return exportRs(rs, r, "Day");
	}

	private ExportResult exportRs(RowSet rs, ExportResult r, String operation) {
		StringBuffer sb = new StringBuffer();
		long count = 0;
		String msg = null;
		boolean flag = true;
		try {
			while (rs.next()) {
				count++;
				sb.append(rs.getString(1)).append("|").append(rs.getString(2))
						.append("|").append(rs.getString(3)).append("|")
						.append(rs.getString(4)).append("\r\n");
			}
			byte[] buf = sb.toString().getBytes();
			ByteArrayInputStream bis = new ByteArrayInputStream(buf);
			FileUtils.writeToFile(config.getLocalDir()
					+ File.separator
					+ WapCategoryExportBo.getFileName(
							config.getAppsLocalFileName()).replaceAll(
							"%operation%", operation), bis);
			log.debug("["
					+ r.getOperation()
					+ "]д�����ļ�"
					+ config.getAppsLocalFileName().replaceAll("%operation%",
							operation) + "��ɣ���ʼ�ϴ�FTP�ļ�");
			ByteArrayInputStream nbis = new ByteArrayInputStream(buf);
			config.getFtpClient().put(
					nbis,
					WapCategoryExportBo
							.getFileName(config.getAppsFtpFileName())
							.replaceAll("%operation%", operation));
			log.debug("["
					+ r.getOperation()
					+ "]�ϴ�FTP�ļ�"
					+ WapCategoryExportBo.getFileName(
							config.getAppsFtpFileName()).replaceAll(
							"%operation%", operation) + "���");
			buf = null;
			bis = null;
			nbis = null;
		} catch (SQLException e) {
			log.error("���ݿ���ʳ���,ԭ����" + e.getMessage(), e);
			msg = "���ݿ���ʳ���";
			flag = false;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("����FTP����������,ԭ����" + e.getMessage(), e);
			msg = "����FTP����������";
			flag = false;
		} catch (FTPException e) {
			log.error("����FTP����������,ԭ����" + e.getMessage(), e);
			msg = "����FTP����������";
			flag = false;
		}
		if (flag) {
			msg = r.getOperation() + "�ɹ�,��������¼��[" + count + "]";
		}
		r.setMsg(msg);
		r.setSuccess(flag);
		r.setSize(count);
		return r;
	}
}
