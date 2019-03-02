package com.aspire.ponaadmin.web.risktag.bo;

import java.util.ArrayList;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.category.blacklist.bo.AndroidBlackListBO;
import com.aspire.ponaadmin.web.category.blacklist.dao.AndroidBlackListDAO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.risktag.dao.RiskDAO;
import com.aspire.ponaadmin.web.risktag.dao.RiskTagDAO;

public class RiskBo {
	/**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(RiskBo.class);

    private static RiskBo instance = new RiskBo();
    
    private RiskBo(){
    	
    }
    
    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static RiskBo getInstance()
    {
        return instance;
    }

    /**
     * ���ڲ�ѯ��Ԫ�����б�
     * 
     * @param page
     * @param tagName
     * @throws BOException
     */
    public void query(PageResult page, String id, String stats)
                    throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("RiskBo.queryList( ) is start...");
        }

        try
        {
            // ����TagManagerDAO���в�ѯ
        	RiskDAO.getInstance().queryTagList(page, stats, id);
        }
        catch (DAOException e)
        {
            logger.error(e);
            throw new BOException("��ѯ��Ԫ�����б�ʱ�������ݿ��쳣��");
        }
    }
    
    public void output(){
    	
    	
    }
}
