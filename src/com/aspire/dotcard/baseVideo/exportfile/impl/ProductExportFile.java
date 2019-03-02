/*
 * 文件名：ProductExportFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.dotcard.baseVideo.exportfile.impl;

import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import com.aspire.common.exception.BOException;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO;
import com.aspire.dotcard.baseVideo.exportfile.BaseExportFile;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class ProductExportFile extends BaseExportFile
{
	public ProductExportFile()
	{
		this.fileName = "i_v-product_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_v-product_~DyyyyMMdd~.verf";
		this.mailTitle = "基地产品数据导入结果";
		this.isDelTable = false;
		// this.keyMap = this.initData();

	}

	/**
	 * 用于添加准备动作数据
	 */
	public void init()
	{
		super.init();
		keyMap = BaseVideoFileDAO.getInstance().getVideoProductMap();

	}

	public void destroy()
	{
		// 如果执行过内容数据导入，则执行下面逻辑
		if (isImputDate)
		{
			Set ts = keyMap.entrySet();
			Iterator it = ts.iterator();

			while (it.hasNext())
			{
				Entry ey = (Entry) it.next();
				String productId = (String) ey.getKey();
				if (logger.isDebugEnabled())
				{
					logger.debug("开始删除在本次全量产品文件不存在的老的产品ID，productID="
							+ productId);
				}
				try
				{
					BaseVideoFileDAO.getInstance().delProduct(productId);
				}
				catch (BOException e)
				{
					e.printStackTrace();
				}
			}
		}
		super.destroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data)
	{
		String productID = data[0];
		String tmp = productID;

		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证产品数据字段格式，productID=" + productID);
		}

		if (data.length != 9 && data.length != 6)
		{
			logger.error("字段数不等于9，或不等于6");
			return BaseVideoConfig.CHECK_FAILED;
		}

		if (!this.checkFieldLength(tmp, 60, true))
		{
			logger.error("productID=" + productID
					+ ",productID验证错误，该字段是必填字段，且不超过60个字符");
			return BaseVideoConfig.CHECK_FAILED;
		}
		// productName
		tmp = data[1];
		if (!this.checkFieldLength(tmp, 100, true))
		{
			logger.error("productID=" + productID
					+ ",productName验证出错，该字段是必填字段，长度不超过100个数值长度！productName="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// fee
		tmp = data[2];
		if (!this.checkIntegerField("资费信息", tmp, 12, true))
		{
			logger.error("productID=" + productID
					+ ",fee验证出错，该字段是必填字段，长度不超过12位！fee=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// CPID
		tmp = data[3];
		if (!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("productID=" + productID
					+ ",CPID验证出错，该字段是必填字段，长度不超过20个数值长度！CPID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// feeType
		tmp = data[4];
		if (!this.checkFieldLength(tmp, 2, true))
		{
			logger.error("productID=" + productID
					+ ",feeType验证出错，该字段是必填字段，长度不超过2个数值长度！feeType=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// startDate
		tmp = data[5];
		if (!this.checkFieldLength(tmp, 14, true))
		{
			logger.error("productID=" + productID
					+ ",startDate验证出错，该字段是必填字段，长度不超过14个数值长度！startDate=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}

		if (data.length == 9)
		{
			// freeType
			tmp = data[6];
			if (!this.checkFieldLength(tmp, 4, true))
			{
				logger.error("productID=" + productID
						+ ",freeType验证出错，该字段是必填字段，长度不超过4个数值长度！freeType=" + tmp);
				return BaseVideoConfig.CHECK_FAILED;
			}

			if (!"0".equals(tmp) && !"1".equals(tmp) && !"2".equals(tmp))
			{
				logger.error("productID=" + productID
						+ ",freeType验证出错，该字段是必填字段，且只能为0、1、2！freeType=" + tmp);
				return BaseVideoConfig.CHECK_FAILED;
			}

			if ("0".equals(tmp))
			{
				// freeEffecTime
				tmp = data[7];
				if (!this.checkFieldLength(tmp, 14, false))
				{
					logger
							.error("productID="
									+ productID
									+ ",freeEffecTime验证出错，该字段是非必填字段，长度不超过14个数值长度！freeEffecTime="
									+ tmp);
					return BaseVideoConfig.CHECK_FAILED;
				}

				// freeTimeFail
				tmp = data[8];
				if (!this.checkFieldLength(tmp, 14, false))
				{
					logger
							.error("productID="
									+ productID
									+ ",freeTimeFail验证出错，该字段是非必填字段，长度不超过14个数值长度！freeTimeFail="
									+ tmp);
					return BaseVideoConfig.CHECK_FAILED;
				}
			}
			else
			{
				// freeEffecTime
				tmp = data[7];
				if (!this.checkFieldLength(tmp, 14, true))
				{
					logger
							.error("productID="
									+ productID
									+ ",freeEffecTime验证出错，当freeType为非0时，该字段是必填字段，长度不超过14个数值长度！freeEffecTime="
									+ tmp);
					return BaseVideoConfig.CHECK_FAILED;
				}

				// freeTimeFail
				tmp = data[8];
				if (!this.checkFieldLength(tmp, 14, true))
				{
					logger
							.error("productID="
									+ productID
									+ ",freeTimeFail验证出错，当freeType为非0时，该字段是必填字段，长度不超过14个数值长度！freeTimeFail="
									+ tmp);
					return BaseVideoConfig.CHECK_FAILED;
				}
			}
		}

		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java.lang.String[])
	 */
	protected Object[] getObject(String[] data)
	{
		Object[] object = new Object[9];

		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[3];
		object[3] = data[4];
		object[4] = data[5];
		
		if (data.length == 9)
		{
			object[5] = data[6];
			object[6] = data[7];
			object[7] = data[8];
		}
		else
		{
			object[5] = "";
			object[6] = "";
			object[7] = "";
		}
		
		object[8] = data[0];
		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getInsertSqlCode()
	 */
	protected String getInsertSqlCode()
	{
		// insert into t_vo_product (productname, fee, cpid, feetype, startdate,
		// freetype, freeeffectime, freetimefail, productid) values
		// (?,?,?,?,?,?,?,?,?)
		return "baseVideo.exportfile.ProductExportFile.getInsertSqlCode";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
	 */
	protected String getUpdateSqlCode()
	{
		// update t_vo_product p set p.productname=?, p.fee=?, p.cpid=?,
		// p.feetype=?, p.startdate=?, freetype=?, freeeffectime=?,
		// freetimefail=? where p.productid=?
		return "baseVideo.exportfile.ProductExportFile.getUpdateSqlCode";
	}

	protected String getDelSqlCode()
	{
		return null;
	}

	protected Object[] getHasObject(String[] data)
	{
		Object[] object = new Object[1];
		object[0] = data[0];
		return object;
	}

	public synchronized void delKeyMap(String key)
	{
		keyMap.remove(key);
	}

	protected String getHasSqlCode()
	{
		// select 1 from t_vo_product p where p.productid=?
		return "baseVideo.exportfile.ProductExportFile.getHasSqlCode";
	}

	protected void clearProduct()
	{

	}

	protected String getKey(String[] data)
	{
		return data[0];
	}

}
