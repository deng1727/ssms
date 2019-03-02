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
	 * 日志对象
	 */
	protected static JLogger logger = LoggerFactory.getLogger(VideoBO.class);

	private static VideoBO bo = new VideoBO();

	private VideoBO() {
	}

	public static VideoBO getInstance() {
		return bo;
	}

	/**
	 * 用于返回视频货架信息
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
			throw new BOException("返回视频架信息时发生数据库异常！");
		}
	}

	/**
	 * 用于查询当前货架下商品列表
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
			throw new BOException("返回视频货架列表时发生数据库异常！");
		}
	}
	/**
	 * 用于查询当前货架下热点内容商品列表
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
			throw new BOException("查询当前货架下热点内容商品列表时发生数据库异常！");
		}
	}

	/**
	 * 用于查询视频节目列表
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
			throw new BOException("查询视频列表时发生数据库异常！");
		}
	}

	/**
	 * 用于查询当前货架下二级分类列表
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
			throw new BOException("用于查询当前货架下二级分类列表时发生数据库异常！");
		}
		return subcateNameList;
	}

	/**
	 * 用于查询当前货架下二级分类下的一级标签名称列表
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
			throw new BOException("用于查询当前货架下二级分类列表时发生数据库异常！");
		}
		return tagNameList;
	}

	/**
	 * 用于获取视频货架编码ID
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
			throw new BOException("获取视频货架编码ID时发生数据库异常！");
		}
		return categoryId;
	}

	/**
	 * 用于新增视频货架
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
			throw new BOException("新增视频货架时发生数据库异常！");
		}
	}

	/**
	 * 用于添加指定的视频至货架中
	 * 
	 * @param categoryId
	 *            货架id
	 * @param addVideoId
	 *            视频id列
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
			throw new BOException("添加指定的视频至货架中时发生数据库异常！");
		}
	}

	/**
	 * 用于修改视频货架
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
			throw new BOException("修改视频货架时发生数据库异常！");
		}
	}

	/**
	 * 查看当前货架是否存在子货架
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
			throw new BOException("查看当前货架是否存在子货架时发生数据库异常！");
		}
	}

	/**
	 * 查看当前货架下是否还存在着商品
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
			throw new BOException("查看当前货架下是否还存在着商品时发生数据库异常！");
		}
	}

	/**
	 * 用于查看指定货架中是否存在指定视频节目
	 * 
	 * @param categoryId
	 *            货架id
	 * @param addVideoId
	 *            新视频id列
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

			// 分离出id与name分开
			for (int i = 0; i < addVideoIds.length; i++) {
				String temp = addVideoIds[i];
				String[] temps = temp.split("_");
				programIds[i] = temps[0];
			}

			return VideoDAO.getInstance().isHasReferences(categoryId,
					programIds);
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("查看指定货架中是否存在指定视频节目时发生数据库异常！");
		}
	}

	/**
	 * 用于删除指定货架
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
			throw new BOException("删除指定货架时发生数据库异常！");
		}
	}

	/**
	 * 用于删除指定货架下指定的视频节目商品
	 * 
	 * @param categoryId
	 *            货架id
	 * @param refId
	 *            商品id列
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
			throw new BOException("删除视频货架下指定的视频节目商品时发生数据库异常！");
		}
	}

	/**
	 * 用于设置视频货架下视频商品排序值
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
			throw new BOException("设置视频货架下视频商品排序值时发生数据库异常！");
		}
	}

	public String importContentADD(FormFile dataFile, String categoryId)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("VideoBO.importContentADD( ) is start...");
		}
		StringBuffer ret = new StringBuffer();
		// 解析EXECL文件，获取终端软件版本列表
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
		ret.append("成功导入" + count + "条记录.");
		if (temp.length() != 0) {
			ret.append("导入不成功id为").append(temp);
		}
		return ret.toString();
	}

	/**
	 * 文件批量导入新音乐商品上架
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
			// 用于清空原货架下新音乐
			NewVideoRefDAO.getInstance().delNewVideoRef(categoryId);

			// // 解析EXECL文件，获取终端软件版本列表
			// List list = BookRefBO.getInstance().paraseDataFile(dataFile);

			// 解析EXECL文件，获取终端软件版本列表
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
			ret.append("成功导入" + count + "条记录.");
			if (temp.length() != 0) {
				ret.append("导入不成功id为").append(temp);
			}

			return ret.toString();
		} catch (DAOException e) {
			logger.error(e);
			throw new BOException("批量导入列表时发生数据库异常");
		}
	}

	/**
	 * 提交审批
	 * 
	 * @param categoryId
	 *            货架编号
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
		// 进行事务操作
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getTransactionInstance();
			VideoDAO.getInstance().approvalCategory(tdb, categoryId);
			VideoDAO.getInstance().approvalCategory(tdb, categoryId, "2", "1",
					logUser.getName());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mailTitle", "待审事项：客户端门户运营内容调整审批");
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：视频POMS货架管理;<br>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;货架Id："
					+ categoryId
					+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径："
					+ SEQCategoryUtil.getInstance().getPathByCategoryId(
							categoryId, 5)
					+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式：" + "提交审批";
			map.put("mailContent", value);
			map.put("status", "0");
			map.put("categoryId", categoryId);
			map.put("operation", "50");
			map.put("operationtype", "视频POMS货架管理提交审批");
			map.put("operationobj", "视频POMS货架管理");
			map.put("operationobjtype", "货架Id：" + categoryId);
			map.put("operator", logUser.getName());
			WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(
					map);
			DaemonTaskRunner.getInstance().addTask(task);
			tdb.commit();
		} catch (DAOException e) {
			tdb.rollback();
			logger.error(e);
			throw new BOException("审批指定货架时发生数据库异常！");
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	
	/**
	 * 判断categoryId是否是Pid下的子货架
	 * @param categoryId
	 * @param Pid
	 * @return
	 */
	public boolean isSubCategory(String categoryId,String Pid){
		return CategoryDAO.getInstance().isSubCategory(categoryId, Pid);
	}
}
