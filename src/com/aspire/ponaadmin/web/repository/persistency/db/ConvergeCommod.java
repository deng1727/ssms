
package com.aspire.ponaadmin.web.repository.persistency.db;

import java.sql.ResultSet;

import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.repository.persistency.Command;

public class ConvergeCommod extends Command
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
     * ��Ҫ���ص���Դ������
     */
    private String nodeType;

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ConvergeCommod.class);

    /**
     * ���췽��
     * 
     * @param sql String��sql���
     * @param paras Object[]��sql���Ĳ���
     * @param nodeType String����Ҫ���ص���Դ�����ͣ�Ϊnull��ʾ���ػ�������
     */
    public ConvergeCommod(String sql, Object[] paras, String nodeType)
    {

        this.sql = sql;
        this.paras = paras;
        this.nodeType = nodeType;
        if (nodeType == null)
        {
            this.nodeType = "nt:base";
        }
    }

    /*
     * (non-Javadoc)
     * 
     */
    public Object execute()
    {

        ResultSet rs = null;
        Integer result = null;
        try
        {
            LOG.debug("ConvergeCommod.sql==" + sql);
            // ����com.aspire.ponaadmin.common.db.query�����õ������
            rs = DB.getInstance().query(this.sql, this.paras);
            if (rs != null && rs.next())
            {
                result = new Integer(rs.getInt(1));
            }// ���������

        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        finally
        {
            DB.close(rs);
        }

        return result;
    }

}
