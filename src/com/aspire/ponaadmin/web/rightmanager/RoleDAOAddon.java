package com.aspire.ponaadmin.web.rightmanager ;

import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.rightmanager.RightManagerConstant;
import com.aspire.ponaadmin.common.rightmanager.RolePageVO;

/**
 *
 * <p>Title: RoleDAO的补充类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author shidr
 * @version 1.0
 */
public class RoleDAOAddon
{

    /**
     * 日志引用
     */
    private static JLogger LOG = LoggerFactory.getLogger(RightBO.class);

    /**
     * 搜索角色的方法，实现分页
     * @param page PageResult，分页器
     * @param name String，名称条件
     * @param desc String，描述条件
     * @param rightType int，权限类型
     * @param rightID String，权限id
     * @throws BOException
     */
    static final void searchRole (PageResult page, String name, String desc,
                                  int rightType, String rightID) throws DAOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("searchRole(" + page + "," + name + "," + desc + "," +
                         rightType + "," + rightID + ")") ;
        }
        StringBuffer sql = new StringBuffer() ;
        List paras = new ArrayList();
        //构造搜索的sql和参数
        sql.append("select * from t_role where 1=1");
        if(!name.equals(""))
        {
            sql.append(" and name like ?");
            paras.add("%" + name + "%") ;
        }
        if(!desc.equals(""))
        {
            sql.append(" and descs like ?");
            paras.add("%" + desc + "%") ;
        }
        if(rightType!=0)
        {
            if(rightType == RightManagerConstant.TYPE_RIGHT)
            {
                sql.append(" and roleid in ");
                sql.append("(select roleid from t_roleright where RIGHTID in");
                sql.append(" (SELECT RIGHTID FROM t_right START WITH RIGHTID=? CONNECT BY PRIOR parentid = RIGHTID))");
                paras.add(rightID) ;
            }
            else if(rightType == RightManagerConstant.TYPE_SITE)
            {
                sql.append(" and roleid in ");
                sql.append("(select roleid from t_rolesiteright where NODEID in");
                sql.append(" (SELECT NODEID FROM t_node START WITH NODEID=? CONNECT BY PRIOR parentid = NODEID))");
                paras.add(rightID) ;
            }
            else
            {
                //未知类型，记录错误日志
                LOG.error("unknown rightType[" + rightType + "[ found! ") ;
            }
        }
        sql.append(" order by name");

        page.excute(sql.toString(), paras.toArray(), new RolePageVO()) ;

    }
}
