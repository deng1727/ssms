package com.aspire.ponaadmin.web.actionlog ;

import com.aspire.common.db.DAOException;
import com.aspire.ponaadmin.web.daemon.DaemonTask;

/**
 * <p>д������־���첽����</p>
 * <p>�̳�DaemonTask��ʵ���첽д������־�Ĺ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0 
 */
public class WriteActionLogTask  extends DaemonTask
{

    /**
     * Ҫд�Ĳ�����־������
     */
	private ActionLogVO log;

	/**
	 * ���췽��������һ��д������־���첽����
	 * @param log Ҫд�Ĳ�����־������
	 */
    public WriteActionLogTask (ActionLogVO log)
    {
        this.log = log;
    }

    /**
     * ����DaemonTask��execute������ִ����Ӳ�����־�Ĳ�����
     */
    public void execute ()
    {
        try
        {
            ActionLogDAO.getInstance().addLog(this.log);
        }
        catch (DAOException e)
        {
            
        }
    }
}
