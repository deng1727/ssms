/**
 * <p>
 *  �������ݵ����¼�����timer ��
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 16, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.ponaadmin.web.category.export.timer;

import java.util.TimerTask;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.category.export.CategoryExportBO;
import com.aspire.ponaadmin.web.category.export.repchange.CategoryRepChangeBO;

/**
 * @author dongke
 *
 */
public class CategoryExportTask extends TimerTask
{
	private static final JLogger logger = LoggerFactory.getLogger(CategoryExportTask.class);

	public void run()
	{
		CategoryExportBO cb = CategoryExportBO.getInstance();
		
		CategoryRepChangeBO crb = CategoryRepChangeBO.getInstance();
		
		/**
		try
		{
			crb.categoryRepChangeExport();
		} catch (BOException e1)
		{

			e1.printStackTrace();
			logger.error("������Ʒ�ֻ����ظ��ʵ��� �����쳣",e1);
		}
		*/
		try
		{
			cb.doCategory();
		} catch (BOException e)
		{
			logger.error("������Ʒ���� �����쳣",e);
			return ;
		}
		
	}

}
