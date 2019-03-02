package com.aspire.dotcard.baseread;

import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.dotcard.baseVideo.dao.CategoryEmailDAO;
import com.aspire.dotcard.baseVideo.dao.CategoryLoggerDao;
import com.aspire.ponaadmin.web.daemon.DaemonTask;
import com.aspire.ponaadmin.web.mail.Mail;

public class WriteActionLogAndEmailTask extends DaemonTask{
	
	/**
     * Ҫд�Ĳ���������
     */
	private Map<String,Object> map;
	
	/**
	 * ���췽��������һ��д�����첽����
	 * @param log Ҫд�Ĳ�������
	 */
	public WriteActionLogAndEmailTask(Map<String,Object> map){
		this.map = map;
	}

	/**
     * ����DaemonTask��execute������ִ����Ӳ�����־�Ĳ�����
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