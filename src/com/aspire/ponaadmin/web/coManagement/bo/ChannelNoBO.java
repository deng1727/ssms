package com.aspire.ponaadmin.web.coManagement.bo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.coManagement.dao.ChannelNoDAO;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;

public class ChannelNoBO {

	private final static JLogger LOGGER = LoggerFactory
			.getLogger(ChannelNoBO.class);

	private static ChannelNoBO instance = new ChannelNoBO();

	private ChannelNoBO() {

	}

	public static ChannelNoBO getInstance() {
		return instance;
	}
	/**
	 * ��ѯ������
	 * 
	 * @param page
	 * @throws BOException
	 */
	public void queryChannelsNoList(PageResult page) throws BOException{
		try {
			ChannelNoDAO.getInstance().queryChannelsNoList(page);
		} catch (DAOException e) {
			LOGGER.error("��ѯ�����ŷ����쳣:", e);
			throw new BOException("��ѯ�����ŷ����쳣:", e);
		}
	}
	/**
	 * ���������
	 * 
	 * @param request
	 * @param dataFile
	 * @return
	 * @throws BOException
	 */
	public String importChannelsNo(HttpServletRequest request, FormFile dataFile)
			throws BOException {
		StringBuffer ret = new StringBuffer();
		// ����EXECL�ļ�����ȡ�ն�����汾�б�
		List list = new ExcelHandle().paraseDataFile(dataFile, null);
		int count = 0;

		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}

		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			String channelsNo = (String) m.get(0);
			try {
				if (!ChannelNoDAO.getInstance().queryChannelsNo(channelsNo)) {
					ChannelNoDAO.getInstance().insertChannelsNo(
							logUser.getName(), channelsNo);
					count++;
				}

			} catch (DAOException e) {
				LOGGER.error("��������ŷ����쳣:", e);
				throw new BOException("��������ŷ����쳣:", e);
			}
		}
		ret.append("�ɹ�����" + count + "����¼.");
		return ret.toString();
	}
	
	/**
	 * ��ѯ����������
	 * 
	 * @param status �����Ų���״̬
	 * @return
	 * @throws BOException 
	 */
	public int queryChannelsNoTotal(String status) throws BOException{
		try {
			return ChannelNoDAO.getInstance().queryChannelsNoTotal(status);
		} catch (DAOException e) {
			LOGGER.error("��ѯ���������������쳣:", e);
			throw new BOException("��ѯ���������������쳣:", e);
		}
	}

}
