
package com.aspire.ponaadmin.web.repository.persistency.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.aspire.common.db.Executor;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.Property;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.repository.persistency.Command;
import com.aspire.ponaadmin.web.repository.persistency.CommandBuilder;

/**
 * <p>
 * 数据库持久化命令构建器，构建数据库持久化命令，继承了类CommandBuilder。
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 *
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class DBCommandBuilder extends CommandBuilder
{

    /**
     * 日志
     */
    private static final JLogger LOG = LoggerFactory.getLogger(DBCommandBuilder.class) ;

    /**
     * 单根模式的唯一实例
     */
    private static DBCommandBuilder instance = new DBCommandBuilder();

    /**
     * 构造方法
     */
    private DBCommandBuilder()
    {

    }

    /**
     * 单根模式的获取方法
     *
     * @return DBCommandBuilder，单例
     */
    static final DBCommandBuilder getInstance()
    {

        return instance;
    }

    /**
     * 构建保存节点变更属性的持久化命令方法
     *
     * @param node Node，目标节点实例，变更属性map
     * @param changeProperties HashMap
     * @return Command，持久化命令
     */
    public Command buildSaveCommand(Node node, HashMap changeProperties)
    {

        // 此方法最终构建一个保存节点属性的ExecuteDBCommand
        List executorList = new ArrayList();// ExecuteDBCommand的数据库执行命令列表
        // 获取该节点对应的资源模板定义：
        NodeCFG nodeCFG = DBPersistencyCFG.getInstance()
                                          .getNodeCFG(node.getType());

        // 遍历模板定义中的每个table生成一条对应的update sql语句
        List tableList = nodeCFG.getTables();
        for (int i = 0; i < tableList.size(); i++)
        {
            // 对于遍历中的当前table
            TableCFG table = ( TableCFG ) tableList.get(i);
            
            // 开始构造update sql语句和其对应的参数
            StringBuffer sql = new StringBuffer();
            List paraList = new ArrayList();

            sql.append("update ");
            sql.append(table.getName());
            sql.append(" set ");

            // 定义一个变量来记录这个table中有没有字段对应的属性被修改
            int count = 0;
            // 遍历表table中的每个字段row，生成update sql语句的set语句部分
            List rowList = table.getRows();
            LobExecutor lobe = null;
            for (int j = 0; j < rowList.size(); j++)
            {
                RowCFG row = ( RowCFG ) rowList.get(j);
                Property prop = ( Property ) changeProperties.get(row.getClassField());
                if (prop == null)
                {
                    // 如果prop为null，说明没有被修改，直接continue
                    continue;
                }
                if(PConstants.ROW_TYPE_TEXT.equals(row.getType())){
                	LOG.debug("处理一个clob字段");
                	if(lobe==null){
                		lobe = new LobExecutor(table.getName(),table.getKey(),node.getId());
                		executorList.add(lobe);
                	}
                	lobe.addPropertyName(row.getName(),prop.getValue()
									.toString());
                	executorList.add(lobe);
                	continue;
           
                }
                // 到changeProperties根据row.getFieldName()查找，看当前字段对应的属性
                // 是否被修改：
                
                // 走到这里说明这个字段对应的属性被修改了
                if (count > 0)
                {
                    sql.append(',');
                }
                // sql中需要修改这个字段
                sql.append(row.getName());
                sql.append("=?");
                // 参数为属性对应的值value
                paraList.add(prop.getValue());
                // 修改的字段数量加一：
                count++;
            }
            if (count > 0)// 说明有字段被修改
            {
                // 要构造sql中的where部分
                sql.append(" where ");
                sql.append(table.getKey());
                sql.append("=?");
                paraList.add(node.getId());
                // 构造Executor（DB包）
                Executor executor = new Executor();
                executor.setSql(sql.toString());
                executor.setParas(paraList.toArray());
                executorList.add(executor);
            }
        }
        // 构建ExecuteDBCommand：
        ExecuteDBCommand cmd = new ExecuteDBCommand(executorList);
        return cmd;
    }

    /**
     * 构建节点的子节点变更保存的持久化命令方法
     *
     * @param node Node，目标节点实例
     * @param addChilds List，增加的子节点列表
     * @param delChilds List，删除的子节点列表
     * @return Command，持久化命令
     */
    public Command buildSaveNodeCommand(Node node, List addChilds,
                                        List delChilds)
    {

        // 此方法最终构建一个保存子节点的ExecuteDBCommand
        List executorList = new ArrayList();// ExecuteDBCommand的数据库执行命令列表
        // 首先根据delChilds构建一系列的delete sql
        for (int i = 0; i < delChilds.size(); i++)
        {
            // 对于遍历中的当前delNode
            Node delNode = ( Node ) delChilds.get(i);

            // 调用DBPersistencyCFG的getNodeCFG方法获取节点的资源模板定义nodeCFG
            NodeCFG nodeCFG = DBPersistencyCFG.getInstance()
                                              .getNodeCFG(delNode.getType());

            // 遍历模板定义nodeCFG中的每个table生成一条对应的update sql语句
            List tableList = nodeCFG.getTables();
            for (int j = 0; j < tableList.size(); j++)
            {
                // 对于遍历中的当前table
                TableCFG table = ( TableCFG ) tableList.get(j);
                // 开始构造delete sql语句和其对应的参数
                StringBuffer sql = new StringBuffer();
                sql.append("delete from ");
                sql.append(table.getName());
                sql.append(" where ");
                sql.append(table.getKey());
                sql.append("=?");
                Object[] paras = new Object[1];
                paras[0] = delNode.getId();
                // 构造Executor（DB包）
                Executor executor = new Executor();
                executor.setSql(sql.toString());
                executor.setParas(paras);
                executorList.add(executor);
            }
        }// 遍历delChilds结束

        // 最后根据addChilds构建一系列的insert sql
        for (int i = 0; i < addChilds.size(); i++)
        {
            // 对于遍历中的当前addNode
            Node addNode = ( Node ) addChilds.get(i);
            // 调用DBPersistencyCFG的getNodeCFG方法获取节点的资源模板定义nodeCFG
            NodeCFG nodeCFG = DBPersistencyCFG.getInstance()
                                              .getNodeCFG(addNode.getType());
            // 遍历模板定义nodeCFG中的每个table生成一条对应的insert sql语句
            List tableList = nodeCFG.getTables();
            for (int j = 0; j < tableList.size(); j++)
            {
                // 对于遍历中的当前table
                TableCFG table = ( TableCFG ) tableList.get(j);
                // 开始构造insert sql语句和其对应的参数
                StringBuffer sql = new StringBuffer();
                List paraList = new ArrayList();
                sql.append("insert into ");
                sql.append(table.getName());

                // 还需要根据table中的各个字段row构造insert语句中的fields和values部分
                StringBuffer fields = new StringBuffer();
                StringBuffer values = new StringBuffer();
                // 遍历table.getRows()
                List rowList = table.getRows();
                LobExecutor lobe = null;
                for (int k = 0; k < rowList.size(); k++)
                {
                    // 对于遍历中的当前row
                    RowCFG row = ( RowCFG ) rowList.get(k);
                    // 根据java bean规范获取值并设置到参数List中
                    Object value = null;
                    try
                    {
                        value = PropertyUtils.getProperty(addNode,
                                                          row.getClassField());

                    }
                    catch (Exception e)
                    {
                        
                    }
                    if(PConstants.ROW_TYPE_TEXT.equals(row.getType())){
                    	LOG.debug("处理一个clob字段");
                    	if(lobe==null){
                    		lobe = new LobExecutor(table.getName(),table.getKey(),addNode.getId());
                    		executorList.add(lobe);
                    	}
                    	lobe.addPropertyName(row.getName(), value == null ? "" : value.toString());
                    	lobe.setInsert(true);
                    	executorList.add(lobe);
                    	continue;
                    }
                    // 如果不是第一个row，fields和values中要添加分隔符号
                    if (k > 0)
                    {
                        fields.append(',');
                        values.append(',');
                    }
                    fields.append(row.getName());
                    values.append('?');

                   
                    paraList.add(value);
                }// 遍历table.getRows结束
                // 有些表的key不是它的row，对于这种表，要特殊处理
                RowCFG keyRow = table.getRowByName(table.getKey());
                if (keyRow == null)// 是key不是它的row的表，特殊处理
                {
                    fields.append(',');
                    fields.append(table.getKey());
                    values.append(",?");
                    // sql参数是addNode的ID
                    paraList.add(addNode.getId());
                }
                sql.append('(');
                sql.append(fields);
                sql.append(") values(");
                sql.append(values);
                sql.append(')');

                // 构造Executor
                Executor executor = new Executor();
                executor.setSql(sql.toString());
                executor.setParas(paraList.toArray());
                executorList.add(executor);
            }// 遍历nodeCFG.getTables
        }// 遍历addChilds
        // 构建ExecuteDBCommand：
        ExecuteDBCommand cmd = new ExecuteDBCommand(executorList);
        return cmd;
    }

    /**
     *
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，为null表示搜索所有类型的节点
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    public Command buildSearchNodeCommand(Node node, String type,
                                          Searchor searchor, Taxis taxis)
    {

        // 构造查询命令
        Executor executor = this.buildSearchExecutor(node,
                                                     type,
                                                     searchor,
                                                     taxis);

        QueryDBCommand cmd = new QueryDBCommand(executor.getSql(),
                                                executor.getParas(),
                                                type);

        return cmd;
    }

    /**
     * 构建在节点进行计数的持久化命令方法
     *
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，为null表示搜索所有类型的节点
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    public Command buildCountNodeCommand(Node node, String type,
                                         Searchor searchor, Taxis taxis)
    {

        // 构造查询命令
        Executor executor = this.buildCountExecutor(node, type, searchor, taxis);
        CountDBCommand cmd = new CountDBCommand(executor.getSql(),
                                                executor.getParas());

        return cmd;
    }

    /**
     * 构建在节点进行搜索并分页的持久化命令方法
     *
     * @param pager PageResult，分页器
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，为null表示搜索所有类型的节点
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    public Command buildPagerSearchNodeCommand(PageResult pager, Node node,
                                               String type, Searchor searchor,
                                               Taxis taxis)
    {

        // 构造查询命令
        Executor queryExecutor = this.buildSearchExecutor(node,
                                                          type,
                                                          searchor,
                                                          taxis);
        Executor countExecutor = this.buildCountExecutor(node,
                                                         type,
                                                         searchor,
                                                         taxis);
        String querySQL = queryExecutor.getSql();
        String countSQL = countExecutor.getSql();
        if(searchor!=null && searchor.isFixCmnet())
        {
            //如果有指定搜索条件，并且需要对cmnet内容进行过滤
            querySQL = this.fixCmnetSQL(querySQL);
            countSQL = this.fixCmnetSQL(countSQL);
        }
        if(LOG.isDebugEnabled())
        {
            LOG.debug("query sql is:"+querySQL);
            LOG.debug("count sql is:"+countSQL);
        }
        QueryPagerDBCommand cmd = new QueryPagerDBCommand(pager,
                                                          querySQL,
                                                          countSQL,
                                                          queryExecutor.getParas(),
                                                          type);
        return cmd;
    }

    private String fixCmnetSQL(String sql)
    {
        int whereIDX = sql.indexOf("where");
        int orderIDX = sql.indexOf("order by");
        StringBuffer resultSQL = new StringBuffer();
        resultSQL.append(sql.substring(0,whereIDX));
        resultSQL.append(" ,v_service v ");
        if(orderIDX!=-1)
        {
            resultSQL.append(sql.substring(whereIDX,orderIDX));
            resultSQL.append(" and t5.icpcode = v.icpcode(+) and t5.icpservid = v.icpservid(+) and (v.umflag is null or v.umflag!='A') ");
            resultSQL.append(sql.substring(orderIDX));
        }
        else
        {
            resultSQL.append(sql.substring(whereIDX));
            resultSQL.append(" and t5.icpcode = v.icpcode(+) and t5.icpservid = v.icpservid(+) and (v.umflag is null or v.umflag!='A') ");
        }
        return resultSQL.toString() ;
    }

    /**
     * 构建搜索[在节点下/未在节点下]引用的类型为type的资源节点的持久化命令方法
     *
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，一般不会为nt:base
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    public Command buildSearchRefCommand(Node node, String type,
                                         Searchor searchor, Taxis taxis)
    {

        NodeCFG config = DBPersistencyCFG.getInstance().getNodeCFG(type);

        NodeCFG refConfig = DBPersistencyCFG.getInstance()
                                            .getNodeCFG(config.getRefType());
        Executor executor = this.buildSearchRefExecutor(node,
                                                        type,
                                                        searchor,
                                                        taxis);

        // 转化为executor
        QueryDBCommand cmd = new QueryDBCommand(executor.getSql(),
                                                executor.getParas(),
                                                refConfig.getType());
        return cmd;
    }

    /**
     * 构建搜索[在节点下/未在节点下]引用的类型为type的资源节点的持久化命令方法
     *
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，一般不会为nt:base
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    public Command buildSearchConvergeCommand(Node node, String type,
                                              Searchor searchor,
                                              String operate, String column)
    {

        NodeCFG config = DBPersistencyCFG.getInstance().getNodeCFG(type);

        NodeCFG refConfig = DBPersistencyCFG.getInstance()
                                            .getNodeCFG(config.getRefType());
        Executor executor = this.buildSearchConvergeExecutor(node,
                                                             type,
                                                             searchor,
                                                             operate,
                                                             column);

        // 转化为executor
        ConvergeCommod cmd = new ConvergeCommod(executor.getSql(),
                                          executor.getParas(),
                                          refConfig.getType());
        return cmd;
    }

    /**
     * 构建搜索[在节点下/未在节点下]引用的类型为type的资源节点的持久化命令方法的分页版本
     *
     * @param pager PageResult，分页器
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，一般不会为nt:base
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    public Command buildPagerSearchRefCommand(PageResult pager, Node node,
                                              String type, Searchor searchor,
                                              Taxis taxis)
    {

        NodeCFG config = DBPersistencyCFG.getInstance().getNodeCFG(type);

        NodeCFG refConfig = DBPersistencyCFG.getInstance()
                                            .getNodeCFG(config.getRefType());
        Executor executor = this.buildSearchRefExecutor(node,
                                                        type,
                                                        searchor,
                                                        taxis);
        String selectSql = executor.getSql();
        int idxOfFrom = selectSql.indexOf("from")+4;
        int idxOforder = selectSql.lastIndexOf("order by");
        String countSql = "";
        if(idxOforder!=-1)
        {
            countSql = "select count(*) from"+selectSql.substring(idxOfFrom,idxOforder);
        }
        else
        {
            countSql = "select count(*) from"+selectSql.substring(idxOfFrom);
        }
        // 转化为executor
        QueryPagerDBCommand cmd = new QueryPagerDBCommand(pager,
                                                          executor.getSql(),
                                                          countSql,
                                                          executor.getParas(),
                                                          refConfig.getType());
        return cmd;
    }

    /**
     * 构建搜索[在节点下/未在节点下]引用的类型为type的资源节点的sql命令执行器
     *
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，一般不会为nt:base
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    private Executor buildSearchRefExecutor(Node node, String type,
                                            Searchor searchor, Taxis taxis)
    {

        StringBuffer sql = new StringBuffer();
        List paraList = new ArrayList();

        // 基本表信息
        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();

        NodeCFG config = DBPersistencyCFG.getInstance().getNodeCFG(type);

        NodeCFG refConfig = DBPersistencyCFG.getInstance()
                                            .getNodeCFG(config.getRefType());

        sql.append("select ");

        // 构造：字段列表 from 表列表 where 链接语句
        {
            StringBuffer joinTables = new StringBuffer();
            StringBuffer selectFields = new StringBuffer();
            StringBuffer selectTables = new StringBuffer();
            List tables = refConfig.getTables();
            this.setTablesSQL(selectFields,
                              selectTables,
                              joinTables,
                              tables,
                              baseTable,
                              baseTable.getKey());
            sql.append(selectFields);
            sql.append(" from ");
            sql.append(selectTables);
            sql.append(" where ");
            sql.append(baseTable.getAliasName());
            sql.append(".type like ?");
            paraList.add(refConfig.getType() + "%");
            if (joinTables.length() > 0)
            {
                sql.append(" and ");
                sql.append(joinTables);
            }
        }

        // 搜索条件
        {
            this.setSearchorSQL(sql, paraList, searchor, refConfig);
        }

        sql.append(" and ");
        if (searchor.getIsNotIn())
        {
            sql.append(" not ");
        }
        sql.append(" exists (");
        {
            StringBuffer selectFields = new StringBuffer();
            StringBuffer selectTables = new StringBuffer();
            StringBuffer joinTables = new StringBuffer();
            List tables = config.getTables();
            this.setTablesSQL_optimize(selectFields,
                                       selectTables,
                                       joinTables,
                                       tables,
                                       baseTable,
                                       baseTable.getKey());
            sql.append("select ");
            sql.append("1");
            sql.append(" from ");
            sql.append(selectTables);
            sql.append(" where ");
            sql.append("op" + baseTable.getAliasName());
            sql.append(".type like ?");
            paraList.add(config.getType() + "%");
            if (joinTables.length() > 0)
            {
                sql.append(" and ");
                sql.append(joinTables);
            }
            this.setSearchorRecursive_optimize(sql, paraList, node, searchor);
            sql.append(" and ");
            sql.append(baseTable.getAliasName());
            sql.append('.');
            sql.append(baseTable.getKey());
            sql.append(" = ");
            sql.append(config.getRefKeyRow());
        }

        sql.append(')');

        // 排序
        this.setTaxisSQL(sql, taxis, refConfig);

        // 完毕 ^_^

        // 转化为executor
        Executor executor = new Executor();
        executor.setSql(sql.toString());
        executor.setParas(paraList.toArray());
        return executor;
    }

    /**
     * 构建搜索[在节点下/未在节点下]引用的类型为type的资源节点的sql命令执行器
     *
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，一般不会为nt:base
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Command，持久化命令
     */
    private Executor buildSearchConvergeExecutor(Node node, String type,
                                                 Searchor searchor,
                                                 String operate, String column)
    {

        StringBuffer sql = new StringBuffer();
        List paraList = new ArrayList();

        // 基本表信息base table是所有资源的基本信息表，其它表跟它关联
        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();

        NodeCFG nodeCFG = null;
        String tableAlias = "";
        if (type != null)
        {
            nodeCFG = DBPersistencyCFG.getInstance().getNodeCFG(type);
            tableAlias = nodeCFG.getRowByField(column).getTableAliasName();
        }
        else
        {
            nodeCFG = DBPersistencyCFG.getInstance().getNodeCFG("nt:base");
            tableAlias = nodeCFG.getRowByField(column).getTableAliasName();
        }

        sql.append("select ");
        sql.append(operate);
        sql.append("(");
        sql.append(tableAlias);
        sql.append(".");
        sql.append(column);
        sql.append(")");

        // 构造：字段列表 from 表列表 where 链接语句
        // {
        // 构造：字段列表 from 表列表 where 链接语句
        StringBuffer joinTables = new StringBuffer();
        StringBuffer selectFields = new StringBuffer();
        StringBuffer selectTables = new StringBuffer();
        List tables = nodeCFG.getTables();

        // 构造：字段列表 from 表列表 where 链接语句
        this.setTablesSQL(selectFields,
                          selectTables,
                          joinTables,
                          tables,
                          baseTable,
                          baseTable.getKey());

        // //引用节点还要特殊处理
        // if (nodeCFG.getType().equals("nt:reference"))
        // {
        // NodeCFG refNodeCFG = DBPersistencyCFG.getInstance().getNodeCFG(
        // nodeCFG.getRefType());
        // List refTables = refNodeCFG.getTables();
        // TableCFG refTable = (TableCFG) tables.get(1) ;
        // this.setTablesSQL(selectFields, selectTables, joinTables, refTables,
        // refTable, nodeCFG.getRefKeyRow()) ;
        // }
        // sql.append(selectFields) ;
        sql.append(" from ");
        sql.append(selectTables);
        // }
        // 如果只有一个表（在查询基本类型的情况下），是没有表关联语句的
        if (joinTables.length() > 0)
        {
            sql.append(" where ");
            sql.append(joinTables);
        }
        else
        {
            sql.append(" where 1=1");
        }

        // type类型查询判断
        if (type != null)
        {
            sql.append(" and ");
            sql.append(baseTable.getAliasName());
            sql.append(".type like ?");
            paraList.add(type + "%");
        }

        // 搜索条件
        {
            this.setSearchorSQL(sql, paraList, searchor, nodeCFG);
            this.setSearchorRecursive(sql, paraList, node, searchor);
        }

        // 排序，当type==null的时候不起作用
        // if (type != null)
        // {
        // this.setTaxisSQL(sql, taxis, nodeCFG);
        // }

        // 完毕 ^_^
        Executor executor = new Executor();
        executor.setSql(sql.toString());
        executor.setParas(paraList.toArray());
        return executor;
    }

    /**
     * 构建节点计数的DB命令执行器
     *
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，为null表示搜索所有类型的节点
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Executor，DB命令执行器
     */
    private Executor buildCountExecutor(Node node, String type,
                                        Searchor searchor, Taxis taxis)
    {

        StringBuffer sql = new StringBuffer();
        List paraList = new ArrayList();

        // 基本表信息base table是所有资源的基本信息表，其它表跟它关联
        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();

        NodeCFG nodeCFG = null;
        if (type != null)
        {
            nodeCFG = DBPersistencyCFG.getInstance().getNodeCFG(type);
        }
        else
        {
            nodeCFG = DBPersistencyCFG.getInstance().getNodeCFG("nt:base");
        }

        sql.append("select ");

        // 构造：字段列表 from 表列表 where 链接语句
        StringBuffer joinTables = new StringBuffer();
        StringBuffer selectFields = new StringBuffer();
        StringBuffer selectTables = new StringBuffer();
        {
            List tables = nodeCFG.getTables();

            // 构造：字段列表 from 表列表 where 链接语句
            this.setTablesSQL(selectFields,
                              selectTables,
                              joinTables,
                              tables,
                              baseTable,
                              baseTable.getKey());

            // 引用节点还要特殊处理
            if (nodeCFG.getType().equals("nt:reference"))
            {
                NodeCFG refNodeCFG = DBPersistencyCFG.getInstance()
                                                     .getNodeCFG(nodeCFG.getRefType());
                List refTables = refNodeCFG.getTables();
                TableCFG refTable = ( TableCFG ) tables.get(1);
                this.setTablesSQL(selectFields,
                                  selectTables,
                                  joinTables,
                                  refTables,
                                  refTable,
                                  nodeCFG.getRefKeyRow());
            }
            sql.append(" count(*) from ");
            sql.append(selectTables);
        }
        // 如果只有一个表（在查询基本类型的情况下），是没有表关联语句的
        if (joinTables.length() > 0)
        {
            sql.append(" where ");
            sql.append(joinTables);
        }
        else
        {
            sql.append(" where 1=1");
        }

        // type类型查询判断
        if (type != null)
        {
            sql.append(" and ");
            sql.append(baseTable.getAliasName());
            sql.append(".type like ?");
            paraList.add(type + "%");
        }

        // 搜索条件
        {
            this.setSearchorSQL(sql, paraList, searchor, nodeCFG);
            this.setSearchorRecursive(sql, paraList, node, searchor);
        }

        // 排序
        //因为是count，因此是不应该加order by的。

        // 完毕 ^_^
        Executor executor = new Executor();
        executor.setSql(sql.toString());
        executor.setParas(paraList.toArray());
        return executor;
    }

    /**
     * 构建节点搜索的DB命令执行器
     *
     * @param node Node，目标节点实例
     * @param type String，要搜索的节点类型，为null表示搜索所有类型的节点
     * @param searchor Searchor，搜索条件表达式，为null表示不设定条件
     * @param taxis Taxis，排序表达式，为null表示不设定排序
     * @return Executor，DB命令执行器
     */
    private Executor buildSearchExecutor(Node node, String type,
                                         Searchor searchor, Taxis taxis)
    {

        StringBuffer sql = new StringBuffer();
        List paraList = new ArrayList();

        // 基本表信息base table是所有资源的基本信息表，其它表跟它关联
        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();

        NodeCFG nodeCFG = null;
        if (type != null)
        {
            nodeCFG = DBPersistencyCFG.getInstance().getNodeCFG(type);
        }
        else
        {
            nodeCFG = DBPersistencyCFG.getInstance().getNodeCFG("nt:base");
        }

        sql.append("select ");

        // 构造：字段列表 from 表列表 where 链接语句
        StringBuffer joinTables = new StringBuffer();
        StringBuffer selectFields = new StringBuffer();
        StringBuffer selectTables = new StringBuffer();
        {
            List tables = nodeCFG.getTables();

            // 构造：字段列表 from 表列表 where 链接语句
            this.setTablesSQL(selectFields,
                              selectTables,
                              joinTables,
                              tables,
                              baseTable,
                              baseTable.getKey());

            // 引用节点还要特殊处理
            if (nodeCFG.getType().equals("nt:reference"))
            {
                NodeCFG refNodeCFG = DBPersistencyCFG.getInstance()
                                                     .getNodeCFG(nodeCFG.getRefType());
                List refTables = refNodeCFG.getTables();
                TableCFG refTable = ( TableCFG ) tables.get(1);
                this.setTablesSQL(selectFields,
                                  selectTables,
                                  joinTables,
                                  refTables,
                                  refTable,
                                  nodeCFG.getRefKeyRow());
            }
            sql.append(selectFields);
            sql.append(" from ");
            sql.append(selectTables);
        }
        // 如果只有一个表（在查询基本类型的情况下），是没有表关联语句的
        if (joinTables.length() > 0)
        {
            sql.append(" where ");
            sql.append(joinTables);
        }
        else
        {
            sql.append(" where 1=1");
        }

        // type类型查询判断
        if (type != null)
        {
            sql.append(" and ");
            sql.append(baseTable.getAliasName());
            sql.append(".type like ?");
            paraList.add(type + "%");
        }

        // 搜索条件
        {
            this.setSearchorSQL(sql, paraList, searchor, nodeCFG);
            this.setSearchorRecursive(sql, paraList, node, searchor);
        }

        // 排序，当type==null的时候不起作用
        if (type != null)
        {
            this.setTaxisSQL(sql, taxis, nodeCFG);
        }

        // 完毕 ^_^
        Executor executor = new Executor();
        executor.setSql(sql.toString());
        executor.setParas(paraList.toArray());
        return executor;
    }

    /**
     * 构造查询时的select字段、from表，和多表的关联语句 select x,x,x,x,x from t,t,t,t,t where
     * a1=a1,b1=b2 构造：字段列表 from 表列表 where 链接语句
     *
     * @param selectFields StringBuffer，字段列表要保存的StringBuffer
     * @param selectTables StringBuffer，表列表要保存的StringBuffer
     * @param joinTables StringBuffer，链接语句要保存的StringBuffer
     * @param tables List，要查询的各个表的列表
     * @param joinTable TableCFG，用于关联的表
     * @param refKeyRow String，用于关联的主键
     */
    private void setTablesSQL(StringBuffer selectFields,
                              StringBuffer selectTables,
                              StringBuffer joinTables, List tables,
                              TableCFG joinTable, String refKeyRow)
    {

        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();
        // 遍历要查询的各个表
        for (int i = 0; i < tables.size(); i++)
        {
            TableCFG table = ( TableCFG ) tables.get(i);
            String prefix = "";
            if (table.getName().equals(baseTable.getName())
                && !table.getName().equals(joinTable.getName()))
            {
                prefix = NodeCFG.SPEC_PREFIX;
            }
            // 如果不是第一个要添加一个分隔符,
            if (selectTables.length() > 0)
            {
                selectTables.append(',');
            }
            selectTables.append(table.getName());
            selectTables.append(' ');
            // 用表的假名，避免生成的sql语句太长。
            selectTables.append(prefix);
            selectTables.append(table.getAliasName());
            // 遍历表的各个字段
            List rows = table.getRows();
            for (int j = 0; j < rows.size(); j++)
            {
                RowCFG row = ( RowCFG ) rows.get(j);
                // 如果不是第一个字段，要添加一个分隔符,
                if (selectFields.length() > 0)
                {
                    selectFields.append(',');
                }
                selectFields.append(prefix);
                selectFields.append(table.getAliasName());
                selectFields.append('.');
                selectFields.append(row.getName());
                // 特殊处理，加前缀需要用as区分，不然会重复baseTable里面的字段
                if (!prefix.equals(""))
                {
                    selectFields.append(" as ");
                    selectFields.append(prefix);
                    selectFields.append(row.getName());
                }
            }
            // 不是关联表和基本表的，都需要和关联表关联
            if (!table.getName().equals(joinTable.getName()))
            {
                if (joinTables.length() > 0)
                {
                    joinTables.append(" and ");
                }
                joinTables.append(prefix);
                joinTables.append(table.getAliasName());
                joinTables.append('.');
                joinTables.append(table.getKey());
                joinTables.append('=');
                joinTables.append(joinTable.getAliasName());
                joinTables.append('.');
                joinTables.append(refKeyRow);
            }
        }
    }

    /**
     * 设置查询时的where条件判断语句
     *
     * @param sql StringBuffer，sql保存的StringBuffer
     * @param paraList List，参数保存的列表
     * @param searchor Searchor，搜索条件表达式
     * @param config NodeCFG，查询节点类型对应的持久化配置
     */
    private void setSearchorSQL(StringBuffer sql, List paraList,
                                Searchor searchor, NodeCFG config)
    {

        // 如果表达式为空，直接返回
        if (searchor == null)
        {
            return;
        }

        // 需要基本表的信息
        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();

        // 遍历表达式的各个参数，进行处理
        for (int i = 0; i < searchor.getParams().size(); i++)
        {
            SearchParam param = ( SearchParam ) searchor.getParams().get(i);
            sql.append(" ");
            sql.append(param.getMode());
            sql.append(" ");
            // 如果是左括号开始，要添加一个(
            if (param.getBracket()
                     .equals(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT))
            {
                sql.append(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
            }
            // 从类字段对应到数据库表字段
            {
                String field = param.getProperty();
                RowCFG row = baseTable.getRowByField(field);
                //如果搜索条件设置对引用的搜索则在搜索表别名前+x
                if (row == null && !param.isSearchRef())
                {
                    row = config.getRowByField(field);
                }
                if (row == null)
                {
                    NodeCFG refNodeCFG = DBPersistencyCFG.getInstance()
                                                         .getNodeCFG(config.getRefType());
                    row = refNodeCFG.getRowByField(field);
                }
                if(param.isSearchRef())
                {
                    sql.append(NodeCFG.SPEC_PREFIX + row.getTableAliasName());
                }
                else
                {
                    sql.append(row.getTableAliasName());
                }
                sql.append('.');
                sql.append(row.getName());
            }
            sql.append(' ');
            sql.append(param.getOperator());
            sql.append(' ');
            sql.append('?');
            // 如果是右括号结束，要添加一个)
            if (param.getBracket()
                     .equals(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT))
            {
                sql.append(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);
            }
            paraList.add(param.getValue());
        }
    }

    /**
     * 设置搜索的深度条件
     *
     * @param sql StringBuffer，sql保存的StringBuffer
     * @param paraList List，参数保存的列表
     * @param node Node，查询发生的节点
     * @param searchor Searchor，搜索条件表达式
     */
    private void setSearchorRecursive(StringBuffer sql, List paraList,
                                      Node node, Searchor searchor)
    {

        // 需要基本表的信息
        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();

        // 默认是浅度搜索
        if (searchor == null || !searchor.getIsRecursive())
        {
            sql.append(" and ");
            sql.append(baseTable.getAliasName());
            sql.append(".parentid=?");
            paraList.add(node.getId());
        }
        else
        {
            sql.append(" and ");
            sql.append(baseTable.getAliasName());
            sql.append(".path like ?");
            paraList.add(node.getPath() + '%');
        }
    }

    /**
     * 设置查询时的排序语句
     *
     * @param sql StringBuffer，sql保存的StringBuffer
     * @param taxis Taxis，排序表达式
     * @param config NodeCFG，查询节点类型对应的持久化配置
     */
    private void setTaxisSQL(StringBuffer sql, Taxis taxis, NodeCFG config)
    {

        // 如果没有排序参数，直接返回
        if ((taxis == null) || (taxis.getParams().size() == 0))
        {
            return;
        }

        sql.append(" order by ");
        String nodeType = config.getType();
        String refType = config.getRefType();
        NodeCFG refNodeCFG = DBPersistencyCFG.getInstance().getNodeCFG(refType);

        for (int i = 0; i < taxis.getParams().size(); i++)
        {
            TaxisParam param = ( TaxisParam ) taxis.getParams().get(i);
            String classField = param.getProperty();
            RowCFG row = config.getRowByField(classField);
            // 如果是引用节点，可能需要根据引用类型的字段来排序
            if (row == null && nodeType.startsWith("nt:reference"))
            {
                row = refNodeCFG.getRowByField(classField);
            }
            if (i > 0)
            {
                sql.append(',');
            }
            sql.append(row.getTableAliasName());
            sql.append('.');
            sql.append(row.getName());
            sql.append(' ');
            sql.append(param.getOrder());
        }
    }

    // 以下代码是为了优化SQL设计
    /**
     * 设置搜索的深度条件
     *
     * @param sql StringBuffer，sql保存的StringBuffer
     * @param paraList List，参数保存的列表
     * @param node Node，查询发生的节点
     * @param searchor Searchor，搜索条件表达式
     */
    private void setSearchorRecursive_optimize(StringBuffer sql, List paraList,
                                               Node node, Searchor searchor)
    {

        // 需要基本表的信息
        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();

        // 默认是浅度搜索
        if (searchor == null || !searchor.getIsRecursive())
        {
            sql.append(" and ");
            sql.append("op" + baseTable.getAliasName());
            sql.append(".parentid=?");
            paraList.add(node.getId());
        }
        else
        {
            sql.append(" and ");
            sql.append("op" + baseTable.getAliasName());
            sql.append(".path like ?");
            paraList.add(node.getPath() + '%');
        }
    }

    /**
     * 构造查询时的select字段、from表，和多表的关联语句 select x,x,x,x,x from t,t,t,t,t where
     * a1=a1,b1=b2 构造：字段列表 from 表列表 where 链接语句
     *
     * @param selectFields StringBuffer，字段列表要保存的StringBuffer
     * @param selectTables StringBuffer，表列表要保存的StringBuffer
     * @param joinTables StringBuffer，链接语句要保存的StringBuffer
     * @param tables List，要查询的各个表的列表
     * @param joinTable TableCFG，用于关联的表
     * @param refKeyRow String，用于关联的主键
     */
    private void setTablesSQL_optimize(StringBuffer selectFields,
                                       StringBuffer selectTables,
                                       StringBuffer joinTables, List tables,
                                       TableCFG joinTable, String refKeyRow)
    {

        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();
        // 遍历要查询的各个表
        for (int i = 0; i < tables.size(); i++)
        {
            TableCFG table = ( TableCFG ) tables.get(i);
            String prefix = "";
            if (table.getName().equals(baseTable.getName())
                && !table.getName().equals(joinTable.getName()))
            {
                prefix = NodeCFG.SPEC_PREFIX;
            }
            else if (table.getName().equals(baseTable.getName()))
            {
                prefix = "op";
            }
            // 如果不是第一个要添加一个分隔符,
            if (selectTables.length() > 0)
            {
                selectTables.append(',');
            }
            selectTables.append(table.getName());
            selectTables.append(' ');
            // 用表的假名，避免生成的sql语句太长。
            selectTables.append(prefix);
            selectTables.append(table.getAliasName());
            // 遍历表的各个字段
            List rows = table.getRows();
            for (int j = 0; j < rows.size(); j++)
            {
                RowCFG row = ( RowCFG ) rows.get(j);
                // 如果不是第一个字段，要添加一个分隔符,
                if (selectFields.length() > 0)
                {
                    selectFields.append(',');
                }
                selectFields.append(prefix);
                selectFields.append(table.getAliasName());
                selectFields.append('.');
                selectFields.append(row.getName());
                // 特殊处理，加前缀需要用as区分，不然会重复baseTable里面的字段
                if (!prefix.equals(""))
                {
                    selectFields.append(" as ");
                    selectFields.append(prefix);
                    selectFields.append(row.getName());
                }
            }
            // 不是关联表和基本表的，都需要和关联表关联
            if (!table.getName().equals(joinTable.getName()))
            {
                if (joinTables.length() > 0)
                {
                    joinTables.append(" and ");
                }
                joinTables.append(prefix);
                joinTables.append(table.getAliasName());
                joinTables.append('.');
                joinTables.append(table.getKey());
                joinTables.append('=');
                joinTables.append("op" + joinTable.getAliasName());
                joinTables.append('.');
                joinTables.append(refKeyRow);
            }
        }
    }
}
