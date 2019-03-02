package com.aspire.ponaadmin.web.rightmanager ;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.rightmanager.RightManagerConstant;

/**
 *
 * <p>Title: Ȩ�޹����BO��</p>
 *
 * <p>
 * Description: ����pona common�е�Ȩ�޹���BO�Ĵ��������⣬�����ܸĶ���
 * ��ˣ�һЩ������������д
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
     * ��־����
     */
    private static JLogger LOG = LoggerFactory.getLogger(RightBO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static RightBO instance = new RightBO();

    /**
     * ���췽������singletonģʽ����
     */
    private RightBO ()
    {
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    public static RightBO getInstance()
    {
        return instance;
    }

    /**
     * ������ɫ�ķ�����ʵ�ַ�ҳ
     * @param page PageResult����ҳ��
     * @param name String����������
     * @param desc String����������
     * @param rightType int��Ȩ������
     * @param rightID String��Ȩ��id
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
