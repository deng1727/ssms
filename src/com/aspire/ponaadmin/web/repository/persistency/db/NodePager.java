package com.aspire.ponaadmin.web.repository.persistency.db ;

import java.sql.ResultSet ;
import java.sql.SQLException ;

import com.aspire.ponaadmin.common.page.PageVOInterface ;
import com.aspire.ponaadmin.web.repository.Node;
import org.apache.commons.beanutils.BeanUtils;
import java.util.List;
import com.aspire.ponaadmin.web.repository.ReferenceNode;

/**
 * <p>分页器类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.0.0
 */

public class NodePager implements PageVOInterface
{

    /**
     * 要处理的节点类型
     */
    private String nodeType;

    /**
     * 要处理的节点类型的配置
     */
    private NodeCFG nodeCFG;

    /**
     * 要处理的节点类型的引用类型的配置
     */
    private NodeCFG refNodeCFG;

    /**
     * 构造方法
     * @param nodeType String，要处理的节点类型
     */
    public NodePager(String nodeType)
    {
        this.nodeType = nodeType;
        this.nodeCFG = DBPersistencyCFG.getInstance().getNodeCFG(this.nodeType);
        if(this.nodeType.startsWith("nt:reference"))
        {
            this.refNodeCFG = DBPersistencyCFG.getInstance().getNodeCFG(this.
                                                                        nodeCFG.
                getRefType()) ;
        }
    }

    /**
     *
     * @param vo VOObject
     * @param rs ResultSet
     * @throws SQLException
     * @todo Implement this com.aspire.ponaadmin.common.page.PageVOInterface
     *   method
     */
    public void CopyValFromResultSet (Object vo, ResultSet rs)
        throws SQLException
    {
        try
        {
            List rowList = this.nodeCFG.getRows() ;
            //用bean规范设置节点的各个属性
            for (int i = 0 ; i < rowList.size() ; i++)
            {
                RowCFG row = (RowCFG) rowList.get(i) ;
                Object value = NodePersistencyDB.getDBValue(row.getName(),
                                                            row.getType(), rs) ;
                String fieldName = row.getClassField() ;
                BeanUtils.setProperty(vo, fieldName, value) ;
            }
            //是引用类型，还要设置其引用节点
            if(this.refNodeCFG != null)
            {
                Class clazz = Class.forName(this.refNodeCFG.getClazz());
                Node refNode = (Node) clazz.newInstance();
                List refRowList = this.refNodeCFG.getRows() ;
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
                ((ReferenceNode) vo).setRefNode(refNode);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("set value to node object failed!", e) ;
        }
    }

    /**
     *
     * @return VOObject
     * @todo Implement this com.aspire.ponaadmin.common.page.PageVOInterface
     *   method
     */
    public Object createObject ()
    {
        Node node = null;
        try
        {
            Class clazz = Class.forName(this.nodeCFG.getClazz());
            node = (Node) clazz.newInstance();
        }
        catch(Exception e)
        {
            throw new RuntimeException("create node object failed!", e);
        }
        return node;
    }
}
