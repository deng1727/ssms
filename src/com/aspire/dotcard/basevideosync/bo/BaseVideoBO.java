package com.aspire.dotcard.basevideosync.bo;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.dao.BaseVideoDAO;
import com.aspire.dotcard.basevideosync.exportfile.BaseFileFactory;
import com.aspire.ponaadmin.web.mail.Mail;

public class BaseVideoBO {

	/**
	 * 日志对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseVideoBO.class);

	private static BaseVideoBO bo = new BaseVideoBO();

	private BaseVideoBO() {
	}

	public static BaseVideoBO getInstance() {
		return bo;
	}

	/**
	 * 用于全量同步所有txt文件数据
	 */
	public void fileDataSync() {
		StringBuffer mailText = new StringBuffer();

		// 视频节目文件内容全量导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PROGRAM)
				.execution(false));

		// 业务产品文件内容导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PRD_PKG)
				.execution(false));

		// 产品包促销计费数据文件内容导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PKG_SALES)
				.execution(false));

		// 计费数据文件内容导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PRODUCT)
				.execution(false));

		// 热点主题列表文件内容导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_HOTCONTENT)
				.execution(false));

		// 榜单发布文件内容导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_TOPLIST)
				.execution(false));

		sendResultMail("新基地视频全量txt文件数据同步结果邮件", mailText);
	}

	/**
	 * 用于全量同步所有xml文件数据
	 */
	public void xmlFileDataSync() {

		StringBuffer mailText = new StringBuffer();
		// 视频节目详情xml文件内容全量导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PROGRAM_XML)
				.execution(false));
		// 视频直播节目单xml文件内容全量导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_LIVE_XML)
				.execution(false));
		// 视频热点主题列表xml文件内容全量导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_HOTCONTENT_XML)
				.execution(false));

		sendResultMail("新基地视频全量xml文件数据同步结果邮件", mailText);
	}

	/**
	 * 用于增量同步节目文件数据
	 */
	public void propramDataByHourSync() {

		StringBuffer mailText = new StringBuffer();
		// 基地视频节目增量txt数据同步
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PROGRAM_ADD_HOUR)
				.execution(false));
		// 视频节目详情xml文件内容导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PROGRAM_XML)
				.execution(false));
		// 视频直播节目单xml文件内容导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_LIVE_XML)
				.execution(false));

		sendResultMail("新基地视频普通节目文件增量同步结果邮件", mailText);
	}

	/**
	 * 用于删除指定的数据
	 * 
	 * @param sql
	 * @param key
	 * @return
	 */
	public boolean delDataByKey(String sql, String[] key) {
		boolean isTrue = true;
		try {
			BaseVideoDAO.getInstance().delDataByKey(sql, key);
		} catch (BOException e) {
			isTrue = false;
			logger.error("执行删除指定的数据时发生错误！！！", e);
		}
		return isTrue;
	}

	/**
	 * 删除当前同步表数据
	 * 
	 * @return
	 */
	public void delTable(String tableName) {
		if (logger.isDebugEnabled()) {
			logger.debug("删除当前同步表数据  strat ,tablename=" + tableName);
		}

		try {
			// 删除当前同步表数据
			BaseVideoDAO.getInstance()
					.truncateTable(new String[] { tableName });
		} catch (Exception e) {
			logger.error("删除当前同步表数据失败！tablename=" + tableName);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("删除当前同步表数据  end ,tablename=" + tableName);
		}
	}

	/**
	 * 删除当前同步中间表数据
	 * 
	 * @return
	 */
	public void delMidTable() {
		if (logger.isDebugEnabled()) {
			logger.debug("删除当前同步中间表数据  strat ");
		}

		try {
			// 删除当前同步中间表表数据
			BaseVideoDAO.getInstance().truncateTable(
					new String[] { "t_v_lables_mid", "t_v_videomedia_mid",
							"t_v_videopic_mid", "t_v_videospropertys_mid","T_V_PROGRAM_SALES_MID" });

		} catch (Exception e) {
			logger.error("删除当前同步中间表数据失败！");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("删除当前同步中间表数据  end ");
		}
	}

	/**
	 * 用于保存增量执行时间
	 * 
	 * @param date
	 * @return
	 */
	public boolean saveLastTime(String date) {
		boolean isTrue = true;
		try {
			BaseVideoDAO.getInstance().saveLastTime(date);
		} catch (BOException e) {
			isTrue = false;
			logger.error("用于保存增量执行时间时发生错误！！！", e);
		}
		return isTrue;
	}

	/**
	 * 调用存储过程 用以执行中间表与正式表中数据转移
	 */
	public boolean syncMidTableData() {
		if (logger.isDebugEnabled()) {
			logger.debug("调用存储过程 用以执行中间表与正式表中数据转移, 开始");
		}

		StringBuffer mailText = new StringBuffer();
		int status = 0;

		status = BaseVideoDAO.getInstance().callSyncMidTableData();

		if (logger.isDebugEnabled()) {
			logger.debug("执行中间表与正式表中数据转移, 结束,执行结果status=" + status);
		}
		if (status != 0) {
			mailText.append("基地视频执行中间表与正式表中数据转移结果成功success！");
		} else {
			mailText.append("基地视频执行中间表与正式表中数据转移结果失败！！！！！！请查看存储过程日志");
		}

		sendResultMail("新基地视频执行中间表与正式表中数据转移结果邮件", mailText);

		return status != 0 ? true : false;
	}

	/**
	 * 调用存储过程 用以执行节目商品上架更新操作
	 */
	public void updateCategoryReference() {
		if (logger.isDebugEnabled()) {
			logger.debug("调用存储过程  用以执行节目商品上架更新操作, 开始");
		}

		StringBuffer mailText = new StringBuffer();
		int status = 0;

		status = BaseVideoDAO.getInstance().callUpdateCategoryReference();

		if (logger.isDebugEnabled()) {
			logger.debug("调用存储过程  用以执行节目商品上架更新操作, 结束,执行结果status=" + status);
		}
		if (status != 0) {
			mailText.append("基地视频执行节目商品上架更新操作结果成功success！");
		} else {
			mailText.append("基地视频执行节目商品上架更新操作结果失败！！！！！！请查看存储过程日志");
		}

		sendResultMail("新基地视频执行节目商品上架更新操作结果邮件", mailText);

	}

	/**
	 * 调用存储过程 用以执行热点主题货架更新操作
	 */
	public String updateHotcontentCategoryMap() {
		if (logger.isDebugEnabled()) {
			logger.debug("调用存储过程  用以执行热点主题货架更新操作, 开始");
		}

		int status = 0;

		status = BaseVideoDAO.getInstance().callUpdateHotcontentCategoryMap();

		if (logger.isDebugEnabled()) {
			logger.debug("调用存储过程  用以执行热点主题货架更新操作, 结束,执行结果status=" + status);
		}
		if (status != 0) {
			return "基地视频执行热点主题货架更新操作结果成功success！";
		} else {
			return "基地视频执行热点主题货架更新操作结果失败！！！！！！请查看存储过程日志";
		}

	}

	/**
	 * 发送结果邮件。
	 * 
	 * @param mailTitle
	 *            标题
	 * @param mailText
	 *            内容
	 */
	public void sendResultMail(String mailTitle, StringBuffer mailText) {
		logger.info(mailText.toString());
		Mail.sendMail(mailTitle, mailText.toString(), BaseVideoConfig.mailTo);
	}

	/**
	 * 数据api接口调用 数据同步
	 */
	public void DataApiRequestSync() {
		StringBuffer mailText = new StringBuffer();
		// 普通节目api请求数据 内容导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PROGRAM_API)
				.execution(false));
		// 产品包促销计费api请求数据 内容导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PRD_API)
				.execution(false));
		// 业务产品api请求 内容导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PKG_SALES_API)
				.execution(false));
		// 计费信息数据Api请求 数据同步
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_PRODUCT_API)
				.execution(false));
		// 热点主题列表api请求数据 内容导入
		mailText.append(BaseFileFactory.getInstance()
				.getBaseFile(BaseVideoConfig.FILE_TYPE_HOTCONTENT_API)
				.execution(false));

		sendResultMail("数据api接口调用 数据同步结果邮件", mailText);
	}
}
