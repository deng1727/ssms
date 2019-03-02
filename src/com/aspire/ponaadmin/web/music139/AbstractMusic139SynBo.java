package com.aspire.ponaadmin.web.music139;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.db.ResultSetConvertor;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.mail.Mail;
import com.enterprisedt.net.ftp.FTPException;

public abstract class AbstractMusic139SynBo {
	protected String ftpName;

	protected TransactionDB db;

	private static final JLogger log = LoggerFactory
			.getLogger(AbstractMusic139SynBo.class);

	protected Result r = new Result(null);

	public abstract String getOperationName();

	protected Music139Config config = Music139Config.getConfig();

	public Result getR() {
		return r;
	}

	public void synchroMusic139Data() {
		try {
            updateFileName();
			r.setName(getOperationName());
			db = TransactionDB.getTransactionInstance();
			log.debug("开始从FTP上读取文件");
			String f = getData();
			log.debug("读取文件成功：" + f + ",开始处理数据.....");
			handleData(f);
			log.debug("处理数据成功,开始删除FTP上面的文件");
			deleteFile();
		} catch (IOException e) {
			log.error("访问FTP服务器失败!没有找到FTP上需要同步的数据文件:" + this.ftpName+e);
			r.setMsg("访问FTP服务器失败!没有找到FTP上需要同步的数据文件:" + this.ftpName);
		} catch (FTPException e) {
			log.error( "获取文件："+this.ftpName+ "失败;"+e);
			r.setMsg("访问FTP服务器失败!" +"获取文件："+this.ftpName+ "失败;"+ e.getMessage());
		} catch (DAOException e) {
			log.error(e);
			r.setMsg("数据库访问错误!" + e.getMessage());
		} catch (Exception e) {
			log.error(e);
			try {
				if (db.getConnection() != null
						&& !db.getConnection().isClosed()) {
					db.rollback();
				}
			} catch (DAOException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			r.setMsg("同步失败" +this.ftpName+ "失败;"+ e.getMessage());
		}
		log.debug("开始发送邮件....");
		Mail.sendMail(r.getName(), r.getMsg(), config.getEmails());
		log.debug("执行完毕!处理结果是:" + r.getMsg());
	}

	protected abstract void handleData(String file) throws Exception;
    
    public abstract void updateFileName() throws Exception;

	// private void writeResult() {
	// String result = null;
	// if (config.getLocalDir().charAt(config.getLocalDir().length() - 1) ==
	// File.separatorChar) {
	// result = "";
	// } else {
	// result = config.getLocalDir() + File.separator + "result_"
	// + this.ftpName;
	// }
	// BufferedWriter bw = null;
	// try {
	// bw = new BufferedWriter(new FileWriter(new File(result)));
	// bw.write(r.getMsg());
	// bw.flush();
	// } catch (IOException e) {
	// log.error(e);
	// } finally {
	// try {
	// bw.close();
	// } catch (IOException e) {
	// log.error(e);
	// }
	// }
	// }

	private String getData() throws IOException, FTPException {
		String localfile = null;
		if (config.getLocalDir().charAt(config.getLocalDir().length() - 1) == File.separatorChar) {
			localfile = "";
		} else {
			localfile = config.getLocalDir() + File.separator + this.ftpName;
		}
		int a=localfile.indexOf('\\');
		if(localfile.indexOf('\\')!=-1)
			localfile=localfile.replace('\\', '/');
		config.getFtpClient().get(localfile, this.ftpName);
		return localfile;
	}

	private void deleteFile() throws IOException, FTPException {
		config.getFtpClient().delete(this.ftpName);
	}

	public static void main(String[] args) {
		System.out.println(1 & 4);
	}

	public String getFileName(String filename, Date d) {
		if (d == null) {
			d = new Date();
		}
		String p = filename.substring(filename.indexOf("_") + 1, filename
				.indexOf(getPrefixName()));
		SimpleDateFormat sdf = new SimpleDateFormat(p);
		String date = sdf.format(d);
		String fileName = filename.replaceAll(p, date);
		return fileName;
	}

	public String getFileName(String filename, String d) {
		if (d == null) {
			return getFileName(filename, new Date());
		}
		String p = filename.substring(filename.indexOf("_") + 1, filename
				.indexOf(getPrefixName()));
		String fileName = filename.replaceAll(p, d);
		return fileName;
	}
	
	public abstract String getPrefixName();

	protected Object queryUniqueObject(String sql, Object[] params)
			throws DAOException {
		List list;
		list = db.query(sql, params, new ResultSetConvertor() {
			public Object convert(ResultSet rs) throws SQLException {
				return rs.getObject(1);
			}
		});
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
}
