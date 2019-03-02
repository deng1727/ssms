package com.aspire.ponaadmin.web.repository.persistency.db ;

import java.sql.ResultSet;

import com.aspire.common.db.DB;
import com.aspire.ponaadmin.web.repository.persistency.Command;

/**
 * <p>�������ݿ�־û�������ڲ���װ��һ�����ݿ�־û���Ҫִ�е�һ����ѯsql���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CountDBCommand  extends Command
{

    /**
     * sql���
     */
    private String sql;

    /**
     * sql���Ĳ���
     */
    private Object[] paras;

    /**
     * ���췽��
     * @param sql String��sql���
     * @param paras Object[]��sql���Ĳ���
     */
    public CountDBCommand (String sql, Object[] paras)
    {
        this.sql = sql ;
        this.paras = paras ;
    }

    /**
     * ִ�г־û������ķ���������Command.execute��������μ���
     * @return Object���־û����
     * @todo Implement this
     *   com.aspire.ponaadmin.web.repository.persistency.Command method
     */
    public Object execute ()
    {
        //���巵�صĽ������һ���б�
        Integer result = null;
        ResultSet rs = null;
        try
        {
            //����com.aspire.ponaadmin.common.db.query�����õ������
            rs = DB.getInstance().query(this.sql, this.paras);

            if (rs != null && rs.next())
            {
                result = new Integer(rs.getInt(1));
            }//���������
        }
        catch (Exception e)
        {
            
        }
        finally
        {
            DB.close(rs);
        }
        return result;
    }

}
