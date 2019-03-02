package com.aspire.ponaadmin.web.actionlog ;

import com.aspire.common.db.DAOException;
import com.aspire.ponaadmin.web.daemon.DaemonTask;

/**
 * <p>写操作日志的异步任务</p>
 * <p>继承DaemonTask，实现异步写操作日志的功能</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0 
 */
public class WriteActionLogTask  extends DaemonTask
{

    /**
     * 要写的操作日志的内容
     */
	private ActionLogVO log;

	/**
	 * 构造方法，构造一个写操作日志的异步任务
	 * @param log 要写的操作日志的内容
	 */
    public WriteActionLogTask (ActionLogVO log)
    {
        this.log = log;
    }

    /**
     * 覆盖DaemonTask的execute方法，执行添加操作日志的操作。
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
