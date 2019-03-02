/*
 * �ļ�����ProductExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
		this.mailTitle = "���ز�Ʒ���ݵ�����";
	}

	/**
	 * �������׼����������
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
			logger.debug("��ʼ��֤��Ʒ�����ֶθ�ʽ��productID=" + productID);
		}

		if (data.length != 10 && data.length != 6)
		{
			logger.error("�ֶ���������10���򲻵���6");
			return BaseVideoConfig.CHECK_FAILED;
		}

		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("productID=" + productID
					+ ",productID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
			return BaseVideoConfig.CHECK_FAILED;
		}
		// productName
		tmp = data[1];
		if (!BaseFileNewTools.checkFieldLength(tmp, 100, true))
		{
			logger.error("productID=" + productID
					+ ",productName��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����100����ֵ���ȣ�productName="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// fee
		tmp = data[2];
		if (!BaseFileNewTools.checkIntegerField("�ʷ���Ϣ", tmp, 12, true))
		{
			logger.error("productID=" + productID
					+ ",fee��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����12λ��fee=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// CPID
		tmp = data[3];
		if (!BaseFileNewTools.checkFieldLength(tmp, 20, true))
		{
			logger.error("productID=" + productID
					+ ",CPID��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����20����ֵ���ȣ�CPID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// feeType
		tmp = data[4];
		if (!BaseFileNewTools.checkFieldLength(tmp, 2, true))
		{
			logger.error("productID=" + productID
					+ ",feeType��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����2����ֵ���ȣ�feeType=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// startDate
		tmp = data[5];
		if (!BaseFileNewTools.checkFieldLength(tmp, 14, true))
		{
			logger.error("productID=" + productID
					+ ",startDate��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����14����ֵ���ȣ�startDate=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}

		if (data.length == 10)
		{
			// freeType
			tmp = data[6];
			if (!BaseFileNewTools.checkFieldLength(tmp, 4, true))
			{
				logger.error("productID=" + productID
						+ ",freeType��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����4����ֵ���ȣ�freeType=" + tmp);
				return BaseVideoConfig.CHECK_FAILED;
			}

			if (!"0".equals(tmp) && !"1".equals(tmp) && !"2".equals(tmp))
			{
				logger.error("productID=" + productID
						+ ",freeType��֤�������ֶ��Ǳ����ֶΣ���ֻ��Ϊ0��1��2��freeType=" + tmp);
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
									+ ",freeEffecTime��֤�������ֶ��ǷǱ����ֶΣ����Ȳ�����14����ֵ���ȣ�freeEffecTime="
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
									+ ",freeTimeFail��֤�������ֶ��ǷǱ����ֶΣ����Ȳ�����14����ֵ���ȣ�freeTimeFail="
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
									+ ",freeEffecTime��֤������freeTypeΪ��0ʱ�����ֶ��Ǳ����ֶΣ����Ȳ�����14����ֵ���ȣ�freeEffecTime="
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
									+ ",freeTimeFail��֤������freeTypeΪ��0ʱ�����ֶ��Ǳ����ֶΣ����Ȳ�����14����ֵ���ȣ�freeTimeFail="
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
								+ ",productDesc��֤�������ֶ��ǷǱ����ֶΣ����Ȳ�����1024����ֵ���ȣ�productDesc="
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
