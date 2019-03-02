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
     * ����DaemonTask��execute�������Ѵӵ������õ�����Ϣ��T_A_PPMS_RECEIVEת��T_A_PPMS_RECEIVE_change��
     * @throws DAOException 
     */
    public void execute () throws DAOException{
    	
    	 APPInfoDAO.getInstance().putPPMSReceive(vo);
    }
}
