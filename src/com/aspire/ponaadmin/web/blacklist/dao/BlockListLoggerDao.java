package com.aspire.ponaadmin.web.blacklist.dao;

import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class BlockListLoggerDao {
	
	/**
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(BlockListLoggerDao.class);

	private static BlockListLoggerDao bo = new BlockListLoggerDao();

	private BlockListLoggerDao() {
	}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static BlockListLoggerDao getInstance() {
		return bo;
	}

	/**
	 * 添加日志
	 * 
	 * @param map
	 * @throws DAOException
	 */
	public void addLogger(Map<String, Object> map) throws DAOException {
		try {
			DB.getInstance()
					.executeBySQLCode(
							"com.aspire.ponaadmin.web.blacklist.dao.BlockListLoggerDao.addLogger",
							new Object[] { map.get("operationtype"),//操作类型
									map.get("operator"), //操作人
									map.get("operationobj"), //操作对象
									map.get("operationobjtype") }); //操作对象类型
		} catch (DAOException e) {
			logger.error("添加运营操作日志发生异常:", e);
			throw new DAOException("添加运营操作日志发生异常:", e);
		}

	}

}
