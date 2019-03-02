package com.aspire.dotcard.basevideosync.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.dao.CategoryDAO;
import com.aspire.dotcard.basevideosync.dao.NewVideoRefDAO;
import com.aspire.dotcard.basevideosync.dao.VideoDAO;
import com.aspire.dotcard.basevideosync.vo.ProgramVO;
import com.aspire.dotcard.basevideosync.vo.VideoCategoryVO;
import com.aspire.dotcard.basevideosync.vo.VideoReferenceVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.newmusicsys.WriteActionLogAndEmailTask;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;

public class VideoBO {

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory.getLogger(VideoBO.class);

	private static VideoBO bo = new VideoBO();

	private VideoBO() {
	}

	public static VideoBO getInstance() {
		return bo;
	}

	/**
	 * ���ڷ�����Ƶ������Ϣ
	 * 
	 * @param categoryId
	 * @return
	 * @throws BOException
	 */
	public VideoCategoryVO queryVideoCategoryVO(String categoryId)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("basevideosync.bo.VideoBO.queryVideoCategoryVO("
					+ categoryId + ") is start...");
		}

		try {
			return VideoDAO.getInstance().queryVideoCategoryVO(categoryId);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("������Ƶ����Ϣʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ���ڲ�ѯ��ǰ��������Ʒ�б�
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryVideoReferenceList(PageResult page, VideoReferenceVO vo)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger
					.debug("basevideosync.bo.VideoBO.queryVideoReferenceList( ) is start...");
		}

		try {
			VideoDAO.getInstance().queryVideoReferenceList(page, vo);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("������Ƶ�����б�ʱ�������ݿ��쳣��");
		}
	}
	/**
	 * ���ڲ�ѯ��ǰ�������ȵ�������Ʒ�б�
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryHotContentReferenceList(PageResult page, VideoReferenceVO vo)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger
					.debug("basevideosync.bo.queryHotContentReferenceList( ) is start...");
		}

		try {
			VideoDAO.getInstance().queryHotContentReferenceList(page, vo);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("��ѯ��ǰ�������ȵ�������Ʒ�б�ʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ���ڲ�ѯ��Ƶ��Ŀ�б�
	 * 
	 * @param page
	 * @param vo
	 * @throws BOException
	 */
	public void queryProgramList(PageResult page, ProgramVO vo)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger
					.debug("basevideosync.bo.VideoBO.queryProgramList( ) is start...");
		}

		try {
			VideoDAO.getInstance().queryProgramList(page, vo);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("��ѯ��Ƶ�б�ʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ���ڲ�ѯ��ǰ�����¶��������б�
	 * 
	 * @param categoryId
	 * @throws BOException
	 */
	public List<String> getSubcateNameList(String categoryId)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger
					.debug("basevideosync.bo.VideoBO.getSubcateNameList( ) is start...");
		}

		List<String> subcateNameList = null;

		try {
			subcateNameList = VideoDAO.getInstance().getSubcateNameList(
					categoryId);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("���ڲ�ѯ��ǰ�����¶��������б�ʱ�������ݿ��쳣��");
		}
		return subcateNameList;
	}

	/**
	 * ���ڲ�ѯ��ǰ�����¶��������µ�һ����ǩ�����б�
	 * 
	 * @param categoryId
	 * @param subcateName
	 * @throws BOException
	 */
	public List<String> getTagNameList(String categoryId, String subcateName)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger
					.debug("basevideosync.bo.VideoBO.getTagNameList( ) is start...");
		}

		List<String> tagNameList = null;

		try {
			tagNameList = VideoDAO.getInstance().getTagNameList(categoryId,
					subcateName);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("���ڲ�ѯ��ǰ�����¶��������б�ʱ�������ݿ��쳣��");
		}
		return tagNameList;
	}

	/**
	 * ���ڻ�ȡ��Ƶ���ܱ���ID
	 * 
	 * @throws BOException
	 */
	public String getVideoCategoryId() throws BOException {
		if (logger.isDebugEnabled()) {
			logger
					.debug("basevideosync.bo.VideoBO.getVideoCategoryId() is start...");
		}
		String categoryId = null;
		try {
			categoryId = VideoDAO.getInstance().getVideoCategoryId();
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("��ȡ��Ƶ���ܱ���IDʱ�������ݿ��쳣��");
		}
		return categoryId;
	}

	/**
	 * ����������Ƶ����
	 * 
	 * @param videoCategoryVO
	 * @throws BOException
	 */
	public void addVideoCategory(VideoCategoryVO videoCategoryVO)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger
					.debug("basevideosync.bo.VideoBO.addVideoCategory() is start...");
		}

		try {
			VideoDAO.getInstance().addVideoCategory(videoCategoryVO);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("������Ƶ����ʱ�������ݿ��쳣��");
		}
	}

	/**
	 * �������ָ������Ƶ��������
	 * 
	 * @param categoryId
	 *            ����id
	 * @param addVideoId
	 *            ��Ƶid��
	 * @throws BOException
	 */
	public void addVideoReferences(String categoryId, String addVideoId)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger
					.debug("basevideosync.bo.VideoBO.addVideoReferences( ) is start...");
		}

		try {
			String[] addVideoIds = addVideoId.split(";");

			VideoDAO.getInstance().addVideoReferences(categoryId, addVideoIds);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("���ָ������Ƶ��������ʱ�������ݿ��쳣��");
		}
	}

	/**
	 * �����޸���Ƶ����
	 * 
	 * @param videoCategoryVO
	 * @throws BOException
	 */
	public void updateVideoCategory(VideoCategoryVO videoCategoryVO)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger
					.debug("basevideosync.bo.VideoBO.updateVideoCategory() is start...");
		}

		try {
			VideoDAO.getInstance().updateVideoCategory(videoCategoryVO);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("�޸���Ƶ����ʱ�������ݿ��쳣��");
		}
	}

	/**
	 * �鿴��ǰ�����Ƿ�����ӻ���
	 * 
	 * @param videoCategoryId
	 * @return
	 * @throws BOException
	 */
	public int hasChild(String videoCategoryId) throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("basevideosync.bo.VideoBO.hasChild(" + videoCategoryId
					+ ") is start...");
		}

		try {
			return VideoDAO.getInstance().hasChild(videoCategoryId);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("�鿴��ǰ�����Ƿ�����ӻ���ʱ�������ݿ��쳣��");
		}
	}

	/**
	 * �鿴��ǰ�������Ƿ񻹴�������Ʒ
	 * 
	 * @param categoryId
	 * @return
	 * @throws BOException
	 */
	public int hasReference(String categoryId) throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("basevideosync.bo.VideoBO.hasReference(" + categoryId
					+ ") is start...");
		}

		try {
			return VideoDAO.getInstance().hasReference(categoryId);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("�鿴��ǰ�������Ƿ񻹴�������Ʒʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ���ڲ鿴ָ���������Ƿ����ָ����Ƶ��Ŀ
	 * 
	 * @param categoryId
	 *            ����id
	 * @param addVideoId
	 *            ����Ƶid��
	 * @throws BOException
	 */
	public String isHasReferences(String categoryId, String addVideoId)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger
					.debug("basevideosync.bo.VideoBO.isHasReferences( ) is start...");
		}

		try {
			String[] addVideoIds = addVideoId.split(";");

			String[] programIds = new String[addVideoIds.length];

			// �����id��name�ֿ�
			for (int i = 0; i < addVideoIds.length; i++) {
				String temp = addVideoIds[i];
				String[] temps = temp.split("_");
				programIds[i] = temps[0];
			}

			return VideoDAO.getInstance().isHasReferences(categoryId,
					programIds);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("�鿴ָ���������Ƿ����ָ����Ƶ��Ŀʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ����ɾ��ָ������
	 * 
	 * @param categoryId
	 * @throws BOException
	 */
	public void delVideoCategory(String categoryId) throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("basevideosync.bo.VideoBO.delVideoCategory("
					+ categoryId + ") is start...");
		}

		try {
			VideoDAO.getInstance().delVideoCategory(categoryId);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("ɾ��ָ������ʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ����ɾ��ָ��������ָ������Ƶ��Ŀ��Ʒ
	 * 
	 * @param categoryId
	 *            ����id
	 * @param refId
	 *            ��Ʒid��
	 * @throws BOException
	 */
	public void delVideoReferences(String categoryId, String[] refId)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger
					.debug("basevideosync.bo.VideoBO.delVideoReferences( ) is start...");
		}

		try {
			VideoDAO.getInstance().delVideoReferences(categoryId, refId);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("ɾ����Ƶ������ָ������Ƶ��Ŀ��Ʒʱ�������ݿ��쳣��");
		}
	}

	/**
	 * ����������Ƶ��������Ƶ��Ʒ����ֵ
	 * 
	 * @param categoryId
	 * @param setSortId
	 * @throws BOException
	 */
	public void setVideoReferenceSort(String categoryId, String setSortId)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger
					.debug("basevideosync.bo.VideoBO.setVideoReferenceSort( ) is start...");
		}

		try {
			String[] sort = setSortId.split(";");
			VideoDAO.getInstance().setVideoReferenceSort(categoryId, sort);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("������Ƶ��������Ƶ��Ʒ����ֵʱ�������ݿ��쳣��");
		}
	}

	public String importContentADD(FormFile dataFile, String categoryId)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("VideoBO.importContentADD( ) is start...");
		}
		StringBuffer ret = new StringBuffer();
		// ����EXECL�ļ�����ȡ�ն�����汾�б�
		List list = new ExcelHandle().paraseDataFile(dataFile, null);
		int errorN = 0;

		StringBuffer temp = new StringBuffer();
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			String sortId = (String) m.get(0);
			String contentId = (String) m.get(1);
			try {
				NewVideoRefDAO.getInstance().addReference(categoryId,
						contentId.trim(), (-1 * Integer.parseInt(sortId)) + "");
				count++;
			} catch (Exception e) {
				errorN++;
				if (errorN % 10 == 0) {
					temp.append("<br>").append(contentId);
				} else {
					temp.append(",").append(contentId);
				}
			}

		}
		NewVideoRefDAO.getInstance().approvalCategoryGoods(categoryId);
		ret.append("�ɹ�����" + count + "����¼.");
		if (temp.length() != 0) {
			ret.append("���벻�ɹ�idΪ").append(temp);
		}
		return ret.toString();
	}

	/**
	 * �ļ�����������������Ʒ�ϼ�
	 * 
	 * @param dataFile
	 * @param categoryId
	 * @throws BOException
	 */
	public String importContentALL(FormFile dataFile, String categoryId)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("VideoBO.importContentALL( ) is start...");
		}

		StringBuffer ret = new StringBuffer();
		int errorN = 0;

		try {
			// �������ԭ������������
			NewVideoRefDAO.getInstance().delNewVideoRef(categoryId);

			// // ����EXECL�ļ�����ȡ�ն�����汾�б�
			// List list = BookRefBO.getInstance().paraseDataFile(dataFile);

			// ����EXECL�ļ�����ȡ�ն�����汾�б�
			List list = new ExcelHandle().paraseDataFile(dataFile, null);

			StringBuffer temp = new StringBuffer();
			int count = 0;
			for (int i = 0; i < list.size(); i++) {
				Map m = (Map) list.get(i);
				String sortId = (String) m.get(0);
				String contentId = (String) m.get(1);
				try {
					NewVideoRefDAO.getInstance().addReference(categoryId,
							contentId.trim(),
							(-1 * Integer.parseInt(sortId)) + "");
					count++;
				} catch (Exception e) {
					errorN++;
					if (errorN % 10 == 0) {
						temp.append("<br>").append(contentId);
					} else {
						temp.append(",").append(contentId);
					}
				}

			}
			NewVideoRefDAO.getInstance().approvalCategoryGoods(categoryId);
			ret.append("�ɹ�����" + count + "����¼.");
			if (temp.length() != 0) {
				ret.append("���벻�ɹ�idΪ").append(temp);
			}

			return ret.toString();
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("���������б�ʱ�������ݿ��쳣");
		}
	}

	/**
	 * �ύ����
	 * 
	 * @param categoryId
	 *            ���ܱ��
	 * @throws BOException
	 */
	public void approvalCategory(HttpServletRequest request, String categoryId)
			throws BOException {
		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}
		// �����������
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getTransactionInstance();
			VideoDAO.getInstance().approvalCategory(tdb, categoryId);
			VideoDAO.getInstance().approvalCategory(tdb, categoryId, "2", "1",
					logUser.getName());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mailTitle", "��������ͻ����Ż���Ӫ���ݵ�������");
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;����������ƵPOMS���ܹ���;<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;����Id��"
					+ categoryId
					+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����"
					+ SEQCategoryUtil.getInstance().getPathByCategoryId(
							categoryId, 5)
					+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��" + "�ύ����";
			map.put("mailContent", value);
			map.put("status", "0");
			map.put("categoryId", categoryId);
			map.put("operation", "50");
			map.put("operationtype", "��ƵPOMS���ܹ����ύ����");
			map.put("operationobj", "��ƵPOMS���ܹ���");
			map.put("operationobjtype", "����Id��" + categoryId);
			map.put("operator", logUser.getName());
			WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(
					map);
			DaemonTaskRunner.getInstance().addTask(task);
			tdb.commit();
		} catch (DAOException e) {
			tdb.rollback();
			logger.error(e);
			throw new BOException("����ָ������ʱ�������ݿ��쳣��");
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	
	/**
	 * �ж�categoryId�Ƿ���Pid�µ��ӻ���
	 * @param categoryId
	 * @param Pid
	 * @return
	 */
	public boolean isSubCategory(String categoryId,String Pid){
		return CategoryDAO.getInstance().isSubCategory(categoryId, Pid);
	}
}
