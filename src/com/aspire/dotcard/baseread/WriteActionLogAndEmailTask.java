package com.aspire.dotcard.baseread;

import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.dotcard.baseVideo.dao.CategoryEmailDAO;
import com.aspire.dotcard.baseVideo.dao.CategoryLoggerDao;
import com.aspire.ponaadmin.web.daemon.DaemonTask;
import com.aspire.ponaadmin.web.mail.Mail;

public class WriteActionLogAndEmailTask extends DaemonTask{
	
	/**
     * 要写的操作的内容
     */
	private Map<String,Object> map;
	
	/**
	 * 构造方法，构造一个写操作异步任务
	 * @param log 要写的操作内容
	 */
	public WriteActionLogAndEmailTask(Map<String,Object> map){
		this.map = map;
	}

	/**
     * 覆盖DaemonTask的execute方法，执行添加操作日志的操作。
     */
	@Override
	public void execute() {

		try {
			CategoryLoggerDao.getInstance().addLogger(map);
			String emailAddress = CategoryEmailDAO.getInstance()
					.getMailAddress(map);
			if (emailAddress != null) {
				String[] strs = emailAddress.split(",");
				if (strs.length > 1) {
					Mail.sendMail(map.get("mailTitle").toString(),
							map.get("mailContent").toString(),
							strs[0].split(";"),  strs[1].split(";"),
							null);
				} else {
					Mail.sendMail(map.get("mailTitle").toString(),
							map.get("mailContent").toString(),
							 emailAddress.split(";"));
				}
			}
		} catch (DAOException e) {

		}

	}

}