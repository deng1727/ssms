package com.aspire.ponaadmin.web.repository.persistency.db ;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.persistency.Command;
import com.aspire.ponaadmin.web.repository.persistency.CommandBuilder;
import com.aspire.ponaadmin.web.repository.persistency.NodePersistency;

/**
 * <p>继承NodePersistency类，实现持久化到数据库。</p>
 * <p>NodePersistencyDB根据相应的场景调用SQLBuilder，并执行其生成的sql语句。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class NodePersistencyDB extends NodePersistency
{

    /**
     * 最小的节点的ID
     */
    private static final int INIT_NODE_ID = 1000;
    
    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(NodePersistencyDB.class) ;

    /**
     * 索引值，用于分配新的节点id
     */
    private static int nodeIDIndex = INIT_NODE_ID ;

    /**
     * 节点id的最小开始值
     */
    private static final int MINNODEID = 7000 ;

    /**
     * 节点id索引值的锁
     */
    private static Object nodeIDIndexLock = new Object() ;

    /**
     * 命令构建器
     */
    private CommandBuilder cmdBuilder = DBCommandBuilder.getInstance();

    /**
     * 构造方法
     * @param node，对应的节点实例
     */
    public NodePersistencyDB(Node node)
    {
        super(node);
    }

    /**
     * 持久化器的初始化方法
     * @param configFile String配置文件
     */
    public void init(String configFile)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("init("+configFile+')');
        }

        DBPersistencyCFG.getInstance().load(configFile);

       /* ResultSet rs = null;
        try
        {
            rs = DB.getInstance().query("select max(id*1) from t_r_base t where ascii(substr(t.id,1,1))>47 and ascii(substr(t.id,1,1))<58", null);
            rs.next();
            int maxNodeID = rs.getInt(1) ;
            //最小必须从MINNODEID开始分配
            if(maxNodeID > MINNODEID)
            {
                nodeIDIndex = maxNodeID ;
            }
            else
            {
                nodeIDIndex = MINNODEID ;
            }
            LOG.debug("max nodeID is:"+nodeIDIndex);
        }
        catch(Exception e)
        {
            LOG.error("", e);
        }
        finally
        {
            DB.close(rs);
        }*/
    }

    /**
     * 分配一个新的节点ID
     * @return String，新的节点id
     */
    public String allocateNewNodeID()
    {
        synchronized(nodeIDIndexLock)
        {
        	 ResultSet rs = null;
             try
             {
                 rs = DB.getInstance().query("select SEQ_T_R_BASE_ID.NEXTVAL from dual", null);
                 rs.next();
                 int maxNodeID = rs.getInt(1) ;
                 //最小必须从MINNODEID开始分配
                 if(maxNodeID > MINNODEID)
                 {
                     nodeIDIndex = maxNodeID ;
                 }
                 else
                 {
                     nodeIDIndex = MINNODEID ;
                 }
                 LOG.debug("max nodeID is:"+nodeIDIndex);
             }
             catch(Exception e)
             {
                 LOG.error("", e);
             }
             finally
             {
                 DB.close(rs);
             }
            return String.valueOf(nodeIDIndex) ;
        }
    }

    /**
     * 保存对节点属性的变动
     * @param changeProperties HashMap
     */
    public void save(HashMap changeProperties)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("save()");
        }
        //如果没有属性变动，就直接返回了。
        if (changeProperties.isEmpty())
        {
            return ;
        }
        Command cmd = cmdBuilder.buildSaveCommand(this.node, changeProperties) ;
        cmd.execute() ;
    }

    /**
     * 保存对本节点的子节点的变动。
     * @param addChilds List,增加的节点列表
     * @param delChilds List,删除的节点列表
     */
    public void saveNode(List addChilds, List delChilds)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("saveNode()");
        }
        //如果没有子节点变动，就直接返回了。
        if (addChilds.isEmpty() && delChilds.isEmpty())
        {
            return ;
        }
        Command cmd = cmdBuilder.buildSaveNodeCommand(this.node, addChilds,
                                                      delChilds) ;
        cmd.execute() ;
    }

    /**
     * 搜索[被本节点引用/未被本节点引用]的某类的资源
     * @param type String，引用类型
     * @param searchor Searchor
     * @param taxis Taxis
     * @return List
     */
    public List searchRefNodes(String type, Searchor searchor, Taxis taxis)
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("searchRefNodes(" + type + "," + searchor + "," + taxis + ")") ;
        }
        Command cmd = cmdBuilder.buildSearchRefCommand(this.node, type, searchor, taxis);
        return (List) cmd.execute() ;
    }

    /**
     * 搜索[被本节点引用/未被本节点引用]的某类的资源
     * @param pager PageResult，分页器
     * @param type String，引用类型
     * @param searchor Searchor
     * @param taxis Taxis
     */
    public void searchRefNodes(PageResult pager, String type, Searchor searchor, Taxis taxis)
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("searchRefNodes(" + pager + "," + type + "," + searchor + "," +
                      taxis + ")") ;
        }
        Command cmd = cmdBuilder.buildPagerSearchRefCommand(pager, this.node, type
        		                                 , searchor, taxis);
        cmd.execute();
    }

    /**
     * 在本节点下查询符合条件的子节点
     * @param type String,要查找的子节点类型，如果为null表示查找所有类型的子节点。
     * @param searchor Searchor,搜索器。
     * @param taxis Taxis,排序器，如果为null表示不用排序。
     * @return List，搜索结果列表
     */
    public List searchNodes(String type, Searchor searchor, Taxis taxis)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("searchNodes("+type+")");
        }
        Command cmd = cmdBuilder.buildSearchNodeCommand(this.node, type, searchor, taxis);
        return (List) cmd.execute() ;
    }

    /**
     * 在本节点下计数符合条件的子节点
     * @param type String,要查找的子节点类型，如果为null表示查找所有类型的子节点。
     * @param searchor Searchor,搜索器。
     * @param taxis Taxis,排序器，如果为null表示不用排序。
     * @return List
     */
    public int countNodes(String type, Searchor searchor, Taxis taxis)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("countNodes("+type+")");
        }
        Command cmd = cmdBuilder.buildCountNodeCommand(this.node, type, searchor, taxis);
        return ((Integer) cmd.execute()).intValue();
    }

    /**
     * 在本节点下查询符合条件的子节点（分页版）
     * @param pager PageResult，分页器
     * @param type String,要查找的子节点类型，如果为null表示查找所有类型的子节点。
     * @param searchor Searchor,搜索器。
     * @param taxis Taxis,排序器，如果为null表示不用排序。
     */
    public void searchNodes(PageResult pager, String type, Searchor searchor, Taxis taxis)
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("searchNodes(" + pager + "," + type + "," + searchor + "," +
                      taxis + ")") ;
        }
        Command cmd = cmdBuilder.buildPagerSearchNodeCommand(pager, this.node, type
        		                                , searchor, taxis);
        cmd.execute();
    }

    /**
     * 根据字段列表，从结果集中构造类型为className的资源节点。
     * @param className String，类名称
     * @param rs ResultSet，结果集
     * @param rowList List，字段列表
     * @return Node，构建的资源节点实例
     * @throws Exception
     */
    static final Node buildNode(String className, ResultSet rs, List rowList) throws Exception
    {
        //用java反射创建对应的节点类
        Class clazz = Class.forName(className);
        Node node = (Node) clazz.newInstance();
        //用bean规范设置节点的各个属性
        for(int i = 0; i < rowList.size(); i++)
        {
            RowCFG row = (RowCFG) rowList.get(i) ;
            Object value = NodePersistencyDB.getDBValue(row.getName(), row.getType(), rs) ;
            String fieldName = row.getClassField() ;
            BeanUtils.setProperty(node, fieldName, value) ;
        }
        return node;
    }

    /**
     * 从数据库中获取值
     * @param rowName String，字段名称
     * @param rowType String，字段类型
     * @param rs ResultSet，结果集
     * @return Object，获取到的值
     * @throws Exception
     */
    static final Object getDBValue(String rowName, String rowType, ResultSet rs) throws Exception
    {
        Object value = null;
        if(rowType.equals(PConstants.ROW_TYPE_STRING))
        {
            value = rs.getString(rowName);
        }
        else if(rowType.equals(PConstants.ROW_TYPE_INT))
        {
            value = new Integer(rs.getInt(rowName));
        }
        else if(rowType.equals(PConstants.ROW_TYPE_LONG))
        {
            value = new Long(rs.getLong(rowName));
        }
        else if(rowType.equals(PConstants.ROW_TYPE_DOUBLE))
        {
            value = new Double(rs.getDouble(rowName)) ;
        }
        else if(rowType.equals(PConstants.ROW_TYPE_TEXT))
        {
            value = DB.getClobValue(rs, rowName) ;
        }
        else if(rowType.equals(PConstants.ROW_TYPE_BINARY))
        {
            value = DB.getBlobValue(rs, rowName) ;
        }
        else
        {
            throw new RuntimeException("not support rowType:" + rowType) ;
        }
        return value;
    }

    /**
     * 搜索内容的排序ID的最大值和最小值
     * @param refid
     * @param searchor
     * @return
     */
    public int searchConverge(String type, Searchor searchor,String operate,String column)
    {

        if (LOG.isDebugEnabled())
        {
            LOG.debug("searchConverge(" + type + "," + searchor +","+operate+","+column+ ")") ;
        }
        
        Command cmd = cmdBuilder.buildSearchConvergeCommand(this.node, type, searchor,operate,column);
        
        return ((Integer)cmd.execute()).intValue();
    }

}
