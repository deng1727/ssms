package com.aspire.ponaadmin.web.repository.persistency.db ;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.ponaadmin.web.repository.persistency.Command;

/**
 * <p>��ִ�����ݿ�־û�����̳���Command��</p>
 * <p>���ڲ���װ��һ�����ݿ�־û���Ҫִ�е�sql�����б����Ǹ��¡�ɾ�����޸ĵ�û�з������ݵ�sql��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class ExecuteDBCommand extends Command
{

    /**
     * sql�����б�
     */
    private List executorList;

    /**
     * ���췽��
     * @param _executorList List��ִ��sql�����б�
     */
    public ExecuteDBCommand(List _executorList)
    {
        this.executorList = _executorList;
    }

    /**
     * ִ�г־û������ķ���������Command.execute��������μ���
     * @return Object���־û����
     */
    public Object execute ()
    {
        //����com.aspire.ponaadmin.common.db. executeMuti�������ɡ�
        try
        {
            DB.getInstance().executeMuti(this.executorList);
        }
        catch (DAOException e)
        {          
            throw new RuntimeException(e);
        }
        return null ;
    }

}
