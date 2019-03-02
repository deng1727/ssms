package com.aspire.ponaadmin.webpps.mycontent.bo;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.queryapp.dao.QueryAppDAO;
import com.aspire.ponaadmin.webpps.mycontent.dao.MyContentDAO;

public class MyContentBO {

	private final static JLogger LOGGER = LoggerFactory
			.getLogger(MyContentBO.class);

	private static MyContentBO instance = new MyContentBO();

	private MyContentBO() {

	}

	public static MyContentBO getInstance() {
		return instance;
	}
	
	public void queryContentList(PageResult page,String companyId, String contentid,String name, String beginDate,String endDate,String catename,String keywords)

    {
    	MyContentDAO.getInstance().queryContentList(page,companyId,contentid, name, beginDate, endDate,catename,keywords);
    }
}
