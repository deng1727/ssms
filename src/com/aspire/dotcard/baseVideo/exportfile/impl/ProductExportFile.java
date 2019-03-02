/*
 * �ļ�����ProductExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
		this.mailTitle = "���ز�Ʒ���ݵ�����";
		this.isDelTable = false;
		// this.keyMap = this.initData();

	}

	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		keyMap = BaseVideoFileDAO.getInstance().getVideoProductMap();

	}

	public void destroy()
	{
		// ���ִ�й��������ݵ��룬��ִ�������߼�
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
					logger.debug("��ʼɾ���ڱ���ȫ����Ʒ�ļ������ڵ��ϵĲ�ƷID��productID="
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
			logger.debug("��ʼ��֤��Ʒ�����ֶθ�ʽ��productID=" + productID);
		}

		if (data.length != 9 && data.length != 6)
		{
			logger.error("�ֶ���������9���򲻵���6");
			return BaseVideoConfig.CHECK_FAILED;
		}

		if (!this.checkFieldLength(tmp, 60, true))
		{
			logger.error("productID=" + productID
					+ ",productID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
			return BaseVideoConfig.CHECK_FAILED;
		}
		// productName
		tmp = data[1];
		if (!this.checkFieldLength(tmp, 100, true))
		{
			logger.error("productID=" + productID
					+ ",productName��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����100����ֵ���ȣ�productName="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// fee
		tmp = data[2];
		if (!this.checkIntegerField("�ʷ���Ϣ", tmp, 12, true))
		{
			logger.error("productID=" + productID
					+ ",fee��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����12λ��fee=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// CPID
		tmp = data[3];
		if (!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("productID=" + productID
					+ ",CPID��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����20����ֵ���ȣ�CPID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// feeType
		tmp = data[4];
		if (!this.checkFieldLength(tmp, 2, true))
		{
			logger.error("productID=" + productID
					+ ",feeType��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����2����ֵ���ȣ�feeType=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// startDate
		tmp = data[5];
		if (!this.checkFieldLength(tmp, 14, true))
		{
			logger.error("productID=" + productID
					+ ",startDate��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����14����ֵ���ȣ�startDate=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}

		if (data.length == 9)
		{
			// freeType
			tmp = data[6];
			if (!this.checkFieldLength(tmp, 4, true))
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
				if (!this.checkFieldLength(tmp, 14, false))
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
				if (!this.checkFieldLength(tmp, 14, false))
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
				if (!this.checkFieldLength(tmp, 14, true))
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
				if (!this.checkFieldLength(tmp, 14, true))
				{
					logger
							.error("productID="
									+ productID
									+ ",freeTimeFail��֤������freeTypeΪ��0ʱ�����ֶ��Ǳ����ֶΣ����Ȳ�����14����ֵ���ȣ�freeTimeFail="
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
