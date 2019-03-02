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
 * <p>Title: RoleDAO�Ĳ�����</p>
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
     * ��־����
     */
    private static JLogger LOG = LoggerFactory.getLogger(RightBO.class);

    /**
     * ������ɫ�ķ�����ʵ�ַ�ҳ
     * @param page PageResult����ҳ��
     * @param name String����������
     * @param desc String����������
     * @param rightType int��Ȩ������
     * @param rightID String��Ȩ��id
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
        //����������sql�Ͳ���
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
                //δ֪���ͣ���¼������־
                LOG.error("unknown rightType[" + rightType + "[ found! ") ;
            }
        }
        sql.append(" order by name");

        page.excute(sql.toString(), paras.toArray(), new RolePageVO()) ;

    }
}
