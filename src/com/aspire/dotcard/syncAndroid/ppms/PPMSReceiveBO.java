package com.aspire.dotcard.syncAndroid.ppms ;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;


public class PPMSReceiveBO
{

	protected static JLogger LOG = LoggerFactory.getLogger(PPMSReceiveBO.class);
	
	private APPInfoVO vo;
	
	PPMSReceiveBO(APPInfoVO vo){
		this.vo = vo;
	}
    /**
     * 覆盖DaemonTask的execute方法，把从电子流得到的消息从T_A_PPMS_RECEIVE转到T_A_PPMS_RECEIVE_change。
     * @throws DAOException 
     */
    public void execute () throws DAOException{
    	
    	 APPInfoDAO.getInstance().putPPMSReceive(vo);
    }
}
