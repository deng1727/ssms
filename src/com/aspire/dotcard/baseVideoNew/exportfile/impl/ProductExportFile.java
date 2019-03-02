/*
 * 文件名：ProductExportFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.dotcard.baseVideoNew.exportfile.impl;


import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideoNew.dao.BaseVideoNewFileDAO;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseFileNewTools;

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
		this.tableName = "t_vo_product";
		this.fileName = "i_v-product_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_v-product_~DyyyyMMdd~.verf";
		this.mailTitle = "基地产品数据导入结果";
	}

	/**
	 * 用于添加准备动作数据
	 */
	public void init()
	{
		super.init();
		keyMap = BaseVideoNewFileDAO.getInstance().getVideoProductMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data,boolean flag)
	{
		String productID = data[0];
		String tmp = productID;

		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证产品数据字段格式，productID=" + productID);
		}

		if (data.length != 10 && data.length != 6)
		{
			logger.error("字段数不等于10，或不等于6");
			return BaseVideoConfig.CHECK_FAILED;
		}

		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("productID=" + productID
					+ ",productID验证错误，该字段是必填字段，且不超过60个字符");
			return BaseVideoConfig.CHECK_FAILED;
		}
		// productName
		tmp = data[1];
		if (!BaseFileNewTools.checkFieldLength(tmp, 100, true))
		{
			logger.error("productID=" + productID
					+ ",productName验证出错，该字段是必填字段，长度不超过100个数值长度！productName="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// fee
		tmp = data[2];
		if (!BaseFileNewTools.checkIntegerField("资费信息", tmp, 12, true))
		{
			logger.error("productID=" + productID
					+ ",fee验证出错，该字段是必填字段，长度不超过12位！fee=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// CPID
		tmp = data[3];
		if (!BaseFileNewTools.checkFieldLength(tmp, 20, true))
		{
			logger.error("productID=" + productID
					+ ",CPID验证出错，该字段是必填字段，长度不超过20个数值长度！CPID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// feeType
		tmp = data[4];
		if (!BaseFileNewTools.checkFieldLength(tmp, 2, true))
		{
			logger.error("productID=" + productID
					+ ",feeType验证出错，该字段是必填字段，长度不超过2个数值长度！feeType=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// startDate
		tmp = data[5];
		if (!BaseFileNewTools.checkFieldLength(tmp, 14, true))
		{
			logger.error("productID=" + productID
					+ ",startDate验证出错，该字段是必填字段，长度不超过14个数值长度！startDate=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}

		if (data.length == 10)
		{
			// freeType
			tmp = data[6];
			if (!BaseFileNewTools.checkFieldLength(tmp, 4, true))
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
				if (!BaseFileNewTools.checkFieldLength(tmp, 14, false))
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
				if (!BaseFileNewTools.checkFieldLength(tmp, 14, false))
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
				if (!BaseFileNewTools.checkFieldLength(tmp, 14, true))
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
				if (!BaseFileNewTools.checkFieldLength(tmp, 14, true))
				{
					logger
							.error("productID="
									+ productID
									+ ",freeTimeFail验证出错，当freeType为非0时，该字段是必填字段，长度不超过14个数值长度！freeTimeFail="
									+ tmp);
					return BaseVideoConfig.CHECK_FAILED;
				}
			}
			tmp = data[9];
			if (!BaseFileNewTools.checkFieldLength(tmp, 1024, false))
			{
				logger
						.error("productID="
								+ productID
								+ ",productDesc验证出错，该字段是非必填字段，长度不超过1024个数值长度！productDesc="
								+ tmp);
				return BaseVideoConfig.CHECK_FAILED;
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
		Object[] object = new Object[10];

		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[3];
		object[3] = data[4];
		object[4] = data[5];
		
		if (data.length == 10)
		{
			object[5] = data[6];
			object[6] = data[7];
			object[7] = data[8];
			object[8] = data[9];
		}
		else
		{
			object[5] = "";
			object[6] = "";
			object[7] = "";
			object[8] = "";
		}
		
		object[9] = data[0];
		return object;
	}


	protected String getKey(String[] data)
	{
		return data[0];
	}
	
	protected String getInsertSqlCode()
	{
		// insert into t_vo_product_mid (productname, fee, cpid, feetype, startdate, freetype, freeeffectime, freetimefail, productdesc, productid, status) values (?,?,?,?,?,?,?,?,?,?,'A')
		return "baseVideoNew.exportfile.ProductExportFile.getInsertSqlCode";
	}

	protected String getUpdateSqlCode()
	{
		// insert into t_vo_product_mid (productname, fee, cpid, feetype, startdate, freetype, freeeffectime, freetimefail, productdesc, productid, status) values (?,?,?,?,?,?,?,?,?,?,'U')
		return "baseVideoNew.exportfile.ProductExportFile.getUpdateSqlCode";
	}

	protected String getDelSqlCode()
	{
		// insert into t_vo_product_mid (productid, status) values (?,'D')
		return "baseVideoNew.exportfile.ProductExportFile.getDelSqlCode";
	}
}
