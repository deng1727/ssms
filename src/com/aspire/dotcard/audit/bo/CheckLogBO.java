package com.aspire.dotcard.audit.bo;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.audit.dao.CheckLogDAO;
import com.aspire.dotcard.audit.vo.CheckDetailVO;
import com.aspire.dotcard.audit.vo.CheckLogVO;
import com.aspire.ponaadmin.common.page.PageResult;

/**
 * 稽核模块业务类
 */
public class CheckLogBO
{
    /**
     * 日志引用
     */
    protected static final JLogger LOG = LoggerFactory.getLogger(CheckLogBO.class);

    private static CheckLogBO instance = new CheckLogBO();

    private CheckLogBO()
    {
    }

    /**
     * 获取实例
     * 
     * @return 实例
     */
    public static CheckLogBO getInstance()
    {
        return instance;
    }

    /**
     * 用于查询当前稽核列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryCheckLogList(PageResult page, CheckLogVO vo) throws BOException
    {
        try
        {
            CheckLogDAO.getInstance().queryCheckLogList(page, vo);
        }
        catch (DAOException e)
        {
            LOG.error("稽核发生数据库异常!", e);
            throw new BOException("稽核发生数据库异常!");
        }
    }

    /**
     * 用于查询当前内容列表
     * 
     * @param page
     * @param vo
     * @throws BOException
     */
    public void queryCheckDetailList(PageResult page, CheckDetailVO vo) throws BOException
    {
        try
        {
            CheckLogDAO.getInstance().queryCheckDetailList(page, vo);
        }
        catch (DAOException e)
        {
            LOG.error("查询内容数据发生数据库异常!", e);
            throw new BOException("查询内容数据发生数据库异常!");
        }
    }

}
