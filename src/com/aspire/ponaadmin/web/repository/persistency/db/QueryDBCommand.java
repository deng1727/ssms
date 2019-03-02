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
 * <p>查询数据库持久化命令。其内部封装了一次数据库持久化需要执行的一条查询sql命令。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class QueryDBCommand  extends Command
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(QueryDBCommand.class);
    
    /**
     * sql语句
     */
    private String sql;

    /**
     * sql语句的参数
     */
    private Object[] paras;

    /**
     * 需要返回的资源的类型
     */
    private String nodeType;

    /**
     * 构造方法
     * @param sql String，sql语句
     * @param paras Object[]，sql语句的参数
     * @param nodeType String，需要返回的资源的类型，为null表示返回基本类型
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
     * 执行持久化操作的方法，覆盖Command.execute方法，请参见。
     * @return Object，持久化结果
     * @todo Implement this
     *   com.aspire.ponaadmin.web.repository.persistency.Command method
     */
    public Object execute ()
    {
        //定义返回的结果，是一个列表
        List list = new ArrayList();
        //找出这个查询需要返回的资源类型的持久化配置：
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
            //调用com.aspire.ponaadmin.common.db.query方法得到结果集
            rs = DB.getInstance().query(this.sql, this.paras);
            List rowList = nodeCFG.getRows() ;
            //遍历结果集rs，构建节点列表
            while (rs != null && rs.next())
            {
                //构建节点实例，并把节点实例添加到返回的结果列表中
                Node node = NodePersistencyDB.buildNode(nodeCFG.getClazz(), rs, rowList);
                //是引用类型，还要设置其引用节点
                if(refNodeCFG != null)
                {
                    Class clazz = Class.forName(refNodeCFG.getClazz());
                    Node refNode = (Node) clazz.newInstance();
                    List refRowList = refNodeCFG.getRows() ;
                    String baseTableName = DBPersistencyCFG.getInstance().getBaseTable().getName();
                    //用bean规范设置节点的各个属性
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
            } //遍历结果集
            if (LOG.isDebugEnabled())
            {
                LOG.debug("结果集rs遍历完成，并全部放入list中！");
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
