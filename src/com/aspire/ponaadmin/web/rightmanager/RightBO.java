package com.aspire.ponaadmin.web.rightmanager ;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.rightmanager.RightManagerConstant;

/**
 *
 * <p>Title: 权限管理的BO类</p>
 *
 * <p>
 * Description: 由于pona common中的权限管理BO的代码有问题，而不能改动，
 * 因此，一些方法在这里重写
 * </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author shidr
 * @version 1.0
 */
public class RightBO
{
    /**
     * 日志引用
     */
    private static JLogger LOG = LoggerFactory.getLogger(RightBO.class);

    /**
     * singleton模式的实例
     */
    private static RightBO instance = new RightBO();

    /**
     * 构造方法，由singleton模式调用
     */
    private RightBO ()
    {
    }

    /**
     * 获取实例
     * @return 实例
     */
    public static RightBO getInstance()
    {
        return instance;
    }

    /**
     * 搜索角色的方法，实现分页
     * @param page PageResult，分页器
     * @param name String，名称条件
     * @param desc String，描述条件
     * @param rightType int，权限类型
     * @param rightID String，权限id
     * @throws BOException
     */
    public void searchRole (PageResult page, String name, String desc,
                            int rightType, String rightID)
        throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("searchRole(" + page + "," + name + "," + desc + "," +
                         rightType + "," + rightID + ")") ;
        }
        if(page == null)
        {
                throw new BOException("page is null!", RightManagerConstant.INVALID_PARA);
        }
        try
        {
            RoleDAOAddon.searchRole(page, name,desc,rightType,rightID) ;
        }
        catch (DAOException e)
        {
            throw new BOException("searchRole error", e) ;
        }
    }
}
