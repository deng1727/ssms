package com.aspire.ponaadmin.web.repository.persistency.db ;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.persistency.Command;

/**
 * <p>��ѯ���ݿ�־û�������ڲ���װ��һ�����ݿ�־û���Ҫִ�е�һ����ѯsql���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class QueryDBCommand  extends Command
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(QueryDBCommand.class);
    
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
     * ���췽��
     * @param sql String��sql���
     * @param paras Object[]��sql���Ĳ���
     * @param nodeType String����Ҫ���ص���Դ�����ͣ�Ϊnull��ʾ���ػ�������
     */
    public QueryDBCommand (String sql, Object[] paras, String nodeType)
    {
        this.sql = sql ;
        this.paras = paras ;
        this.nodeType = nodeType ;
        if (nodeType == null)
        {
            this.nodeType = "nt:base" ;
        }
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
        List list = new ArrayList();
        //�ҳ������ѯ��Ҫ���ص���Դ���͵ĳ־û����ã�
        NodeCFG nodeCFG = DBPersistencyCFG.getInstance().getNodeCFG(this.nodeType);
        NodeCFG refNodeCFG = null;
        if(this.nodeType.startsWith("nt:reference"))
        {
            refNodeCFG = DBPersistencyCFG.getInstance().getNodeCFG(nodeCFG.
                getRefType()) ;
        }
        ResultSet rs = null;
        try
        {
            //����com.aspire.ponaadmin.common.db.query�����õ������
            rs = DB.getInstance().query(this.sql, this.paras);
            List rowList = nodeCFG.getRows() ;
            //���������rs�������ڵ��б�
            while (rs != null && rs.next())
            {
                //�����ڵ�ʵ�������ѽڵ�ʵ����ӵ����صĽ���б���
                Node node = NodePersistencyDB.buildNode(nodeCFG.getClazz(), rs, rowList);
                //���������ͣ���Ҫ���������ýڵ�
                if(refNodeCFG != null)
                {
                    Class clazz = Class.forName(refNodeCFG.getClazz());
                    Node refNode = (Node) clazz.newInstance();
                    List refRowList = refNodeCFG.getRows() ;
                    String baseTableName = DBPersistencyCFG.getInstance().getBaseTable().getName();
                    //��bean�淶���ýڵ�ĸ�������
                    for (int i = 0 ; i < refRowList.size() ; i++)
                    {
                        RowCFG row = (RowCFG) refRowList.get(i) ;
                        String prefix = "";
                        if(row.getTableName().equals(baseTableName))
                        {
                            prefix = NodeCFG.SPEC_PREFIX;
                        }
                        Object value = NodePersistencyDB.getDBValue(prefix +
                                                                    row.getName(),
                                                                    row.getType(),
                                                                    rs) ;
                        String fieldName = row.getClassField() ;
                        BeanUtils.setProperty(refNode, fieldName, value) ;
                    }
                    ((ReferenceNode) node).setRefNode(refNode) ;
                }

                list.add(node) ;
            } //���������
            if (LOG.isDebugEnabled())
            {
                LOG.debug("�����rs������ɣ���ȫ������list�У�");
            }
        }
        catch (Throwable e)
        {
            LOG.error("command execute error.", e);
        }
        finally
        {
            DB.close(rs);
        }
        return list;
    }

}
