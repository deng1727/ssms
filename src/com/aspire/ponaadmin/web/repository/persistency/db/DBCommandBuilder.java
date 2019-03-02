
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
 * ���ݿ�־û�����������������ݿ�־û�����̳�����CommandBuilder��
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
     * ��־
     */
    private static final JLogger LOG = LoggerFactory.getLogger(DBCommandBuilder.class) ;

    /**
     * ����ģʽ��Ψһʵ��
     */
    private static DBCommandBuilder instance = new DBCommandBuilder();

    /**
     * ���췽��
     */
    private DBCommandBuilder()
    {

    }

    /**
     * ����ģʽ�Ļ�ȡ����
     *
     * @return DBCommandBuilder������
     */
    static final DBCommandBuilder getInstance()
    {

        return instance;
    }

    /**
     * ��������ڵ������Եĳ־û������
     *
     * @param node Node��Ŀ��ڵ�ʵ�����������map
     * @param changeProperties HashMap
     * @return Command���־û�����
     */
    public Command buildSaveCommand(Node node, HashMap changeProperties)
    {

        // �˷������չ���һ������ڵ����Ե�ExecuteDBCommand
        List executorList = new ArrayList();// ExecuteDBCommand�����ݿ�ִ�������б�
        // ��ȡ�ýڵ��Ӧ����Դģ�嶨�壺
        NodeCFG nodeCFG = DBPersistencyCFG.getInstance()
                                          .getNodeCFG(node.getType());

        // ����ģ�嶨���е�ÿ��table����һ����Ӧ��update sql���
        List tableList = nodeCFG.getTables();
        for (int i = 0; i < tableList.size(); i++)
        {
            // ���ڱ����еĵ�ǰtable
            TableCFG table = ( TableCFG ) tableList.get(i);
            
            // ��ʼ����update sql�������Ӧ�Ĳ���
            StringBuffer sql = new StringBuffer();
            List paraList = new ArrayList();

            sql.append("update ");
            sql.append(table.getName());
            sql.append(" set ");

            // ����һ����������¼���table����û���ֶζ�Ӧ�����Ա��޸�
            int count = 0;
            // ������table�е�ÿ���ֶ�row������update sql����set��䲿��
            List rowList = table.getRows();
            LobExecutor lobe = null;
            for (int j = 0; j < rowList.size(); j++)
            {
                RowCFG row = ( RowCFG ) rowList.get(j);
                Property prop = ( Property ) changeProperties.get(row.getClassField());
                if (prop == null)
                {
                    // ���propΪnull��˵��û�б��޸ģ�ֱ��continue
                    continue;
                }
                if(PConstants.ROW_TYPE_TEXT.equals(row.getType())){
                	LOG.debug("����һ��clob�ֶ�");
                	if(lobe==null){
                		lobe = new LobExecutor(table.getName(),table.getKey(),node.getId());
                		executorList.add(lobe);
                	}
                	lobe.addPropertyName(row.getName(),prop.getValue()
									.toString());
                	executorList.add(lobe);
                	continue;
           
                }
                // ��changeProperties����row.getFieldName()���ң�����ǰ�ֶζ�Ӧ������
                // �Ƿ��޸ģ�
                
                // �ߵ�����˵������ֶζ�Ӧ�����Ա��޸���
                if (count > 0)
                {
                    sql.append(',');
                }
                // sql����Ҫ�޸�����ֶ�
                sql.append(row.getName());
                sql.append("=?");
                // ����Ϊ���Զ�Ӧ��ֵvalue
                paraList.add(prop.getValue());
                // �޸ĵ��ֶ�������һ��
                count++;
            }
            if (count > 0)// ˵�����ֶα��޸�
            {
                // Ҫ����sql�е�where����
                sql.append(" where ");
                sql.append(table.getKey());
                sql.append("=?");
                paraList.add(node.getId());
                // ����Executor��DB����
                Executor executor = new Executor();
                executor.setSql(sql.toString());
                executor.setParas(paraList.toArray());
                executorList.add(executor);
            }
        }
        // ����ExecuteDBCommand��
        ExecuteDBCommand cmd = new ExecuteDBCommand(executorList);
        return cmd;
    }

    /**
     * �����ڵ���ӽڵ�������ĳ־û������
     *
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param addChilds List�����ӵ��ӽڵ��б�
     * @param delChilds List��ɾ�����ӽڵ��б�
     * @return Command���־û�����
     */
    public Command buildSaveNodeCommand(Node node, List addChilds,
                                        List delChilds)
    {

        // �˷������չ���һ�������ӽڵ��ExecuteDBCommand
        List executorList = new ArrayList();// ExecuteDBCommand�����ݿ�ִ�������б�
        // ���ȸ���delChilds����һϵ�е�delete sql
        for (int i = 0; i < delChilds.size(); i++)
        {
            // ���ڱ����еĵ�ǰdelNode
            Node delNode = ( Node ) delChilds.get(i);

            // ����DBPersistencyCFG��getNodeCFG������ȡ�ڵ����Դģ�嶨��nodeCFG
            NodeCFG nodeCFG = DBPersistencyCFG.getInstance()
                                              .getNodeCFG(delNode.getType());

            // ����ģ�嶨��nodeCFG�е�ÿ��table����һ����Ӧ��update sql���
            List tableList = nodeCFG.getTables();
            for (int j = 0; j < tableList.size(); j++)
            {
                // ���ڱ����еĵ�ǰtable
                TableCFG table = ( TableCFG ) tableList.get(j);
                // ��ʼ����delete sql�������Ӧ�Ĳ���
                StringBuffer sql = new StringBuffer();
                sql.append("delete from ");
                sql.append(table.getName());
                sql.append(" where ");
                sql.append(table.getKey());
                sql.append("=?");
                Object[] paras = new Object[1];
                paras[0] = delNode.getId();
                // ����Executor��DB����
                Executor executor = new Executor();
                executor.setSql(sql.toString());
                executor.setParas(paras);
                executorList.add(executor);
            }
        }// ����delChilds����

        // ������addChilds����һϵ�е�insert sql
        for (int i = 0; i < addChilds.size(); i++)
        {
            // ���ڱ����еĵ�ǰaddNode
            Node addNode = ( Node ) addChilds.get(i);
            // ����DBPersistencyCFG��getNodeCFG������ȡ�ڵ����Դģ�嶨��nodeCFG
            NodeCFG nodeCFG = DBPersistencyCFG.getInstance()
                                              .getNodeCFG(addNode.getType());
            // ����ģ�嶨��nodeCFG�е�ÿ��table����һ����Ӧ��insert sql���
            List tableList = nodeCFG.getTables();
            for (int j = 0; j < tableList.size(); j++)
            {
                // ���ڱ����еĵ�ǰtable
                TableCFG table = ( TableCFG ) tableList.get(j);
                // ��ʼ����insert sql�������Ӧ�Ĳ���
                StringBuffer sql = new StringBuffer();
                List paraList = new ArrayList();
                sql.append("insert into ");
                sql.append(table.getName());

                // ����Ҫ����table�еĸ����ֶ�row����insert����е�fields��values����
                StringBuffer fields = new StringBuffer();
                StringBuffer values = new StringBuffer();
                // ����table.getRows()
                List rowList = table.getRows();
                LobExecutor lobe = null;
                for (int k = 0; k < rowList.size(); k++)
                {
                    // ���ڱ����еĵ�ǰrow
                    RowCFG row = ( RowCFG ) rowList.get(k);
                    // ����java bean�淶��ȡֵ�����õ�����List��
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
                    	LOG.debug("����һ��clob�ֶ�");
                    	if(lobe==null){
                    		lobe = new LobExecutor(table.getName(),table.getKey(),addNode.getId());
                    		executorList.add(lobe);
                    	}
                    	lobe.addPropertyName(row.getName(), value == null ? "" : value.toString());
                    	lobe.setInsert(true);
                    	executorList.add(lobe);
                    	continue;
                    }
                    // ������ǵ�һ��row��fields��values��Ҫ��ӷָ�����
                    if (k > 0)
                    {
                        fields.append(',');
                        values.append(',');
                    }
                    fields.append(row.getName());
                    values.append('?');

                   
                    paraList.add(value);
                }// ����table.getRows����
                // ��Щ���key��������row���������ֱ�Ҫ���⴦��
                RowCFG keyRow = table.getRowByName(table.getKey());
                if (keyRow == null)// ��key��������row�ı����⴦��
                {
                    fields.append(',');
                    fields.append(table.getKey());
                    values.append(",?");
                    // sql������addNode��ID
                    paraList.add(addNode.getId());
                }
                sql.append('(');
                sql.append(fields);
                sql.append(") values(");
                sql.append(values);
                sql.append(')');

                // ����Executor
                Executor executor = new Executor();
                executor.setSql(sql.toString());
                executor.setParas(paraList.toArray());
                executorList.add(executor);
            }// ����nodeCFG.getTables
        }// ����addChilds
        // ����ExecuteDBCommand��
        ExecuteDBCommand cmd = new ExecuteDBCommand(executorList);
        return cmd;
    }

    /**
     *
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�Ϊnull��ʾ�����������͵Ľڵ�
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
     */
    public Command buildSearchNodeCommand(Node node, String type,
                                          Searchor searchor, Taxis taxis)
    {

        // �����ѯ����
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
     * �����ڽڵ���м����ĳ־û������
     *
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�Ϊnull��ʾ�����������͵Ľڵ�
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
     */
    public Command buildCountNodeCommand(Node node, String type,
                                         Searchor searchor, Taxis taxis)
    {

        // �����ѯ����
        Executor executor = this.buildCountExecutor(node, type, searchor, taxis);
        CountDBCommand cmd = new CountDBCommand(executor.getSql(),
                                                executor.getParas());

        return cmd;
    }

    /**
     * �����ڽڵ������������ҳ�ĳ־û������
     *
     * @param pager PageResult����ҳ��
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�Ϊnull��ʾ�����������͵Ľڵ�
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
     */
    public Command buildPagerSearchNodeCommand(PageResult pager, Node node,
                                               String type, Searchor searchor,
                                               Taxis taxis)
    {

        // �����ѯ����
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
            //�����ָ������������������Ҫ��cmnet���ݽ��й���
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
     * ��������[�ڽڵ���/δ�ڽڵ���]���õ�����Ϊtype����Դ�ڵ�ĳ־û������
     *
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�һ�㲻��Ϊnt:base
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
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

        // ת��Ϊexecutor
        QueryDBCommand cmd = new QueryDBCommand(executor.getSql(),
                                                executor.getParas(),
                                                refConfig.getType());
        return cmd;
    }

    /**
     * ��������[�ڽڵ���/δ�ڽڵ���]���õ�����Ϊtype����Դ�ڵ�ĳ־û������
     *
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�һ�㲻��Ϊnt:base
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
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

        // ת��Ϊexecutor
        ConvergeCommod cmd = new ConvergeCommod(executor.getSql(),
                                          executor.getParas(),
                                          refConfig.getType());
        return cmd;
    }

    /**
     * ��������[�ڽڵ���/δ�ڽڵ���]���õ�����Ϊtype����Դ�ڵ�ĳ־û�������ķ�ҳ�汾
     *
     * @param pager PageResult����ҳ��
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�һ�㲻��Ϊnt:base
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
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
        // ת��Ϊexecutor
        QueryPagerDBCommand cmd = new QueryPagerDBCommand(pager,
                                                          executor.getSql(),
                                                          countSql,
                                                          executor.getParas(),
                                                          refConfig.getType());
        return cmd;
    }

    /**
     * ��������[�ڽڵ���/δ�ڽڵ���]���õ�����Ϊtype����Դ�ڵ��sql����ִ����
     *
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�һ�㲻��Ϊnt:base
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
     */
    private Executor buildSearchRefExecutor(Node node, String type,
                                            Searchor searchor, Taxis taxis)
    {

        StringBuffer sql = new StringBuffer();
        List paraList = new ArrayList();

        // ��������Ϣ
        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();

        NodeCFG config = DBPersistencyCFG.getInstance().getNodeCFG(type);

        NodeCFG refConfig = DBPersistencyCFG.getInstance()
                                            .getNodeCFG(config.getRefType());

        sql.append("select ");

        // ���죺�ֶ��б� from ���б� where �������
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

        // ��������
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

        // ����
        this.setTaxisSQL(sql, taxis, refConfig);

        // ��� ^_^

        // ת��Ϊexecutor
        Executor executor = new Executor();
        executor.setSql(sql.toString());
        executor.setParas(paraList.toArray());
        return executor;
    }

    /**
     * ��������[�ڽڵ���/δ�ڽڵ���]���õ�����Ϊtype����Դ�ڵ��sql����ִ����
     *
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�һ�㲻��Ϊnt:base
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Command���־û�����
     */
    private Executor buildSearchConvergeExecutor(Node node, String type,
                                                 Searchor searchor,
                                                 String operate, String column)
    {

        StringBuffer sql = new StringBuffer();
        List paraList = new ArrayList();

        // ��������Ϣbase table��������Դ�Ļ�����Ϣ���������������
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

        // ���죺�ֶ��б� from ���б� where �������
        // {
        // ���죺�ֶ��б� from ���б� where �������
        StringBuffer joinTables = new StringBuffer();
        StringBuffer selectFields = new StringBuffer();
        StringBuffer selectTables = new StringBuffer();
        List tables = nodeCFG.getTables();

        // ���죺�ֶ��б� from ���б� where �������
        this.setTablesSQL(selectFields,
                          selectTables,
                          joinTables,
                          tables,
                          baseTable,
                          baseTable.getKey());

        // //���ýڵ㻹Ҫ���⴦��
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
        // ���ֻ��һ�����ڲ�ѯ�������͵�����£�����û�б��������
        if (joinTables.length() > 0)
        {
            sql.append(" where ");
            sql.append(joinTables);
        }
        else
        {
            sql.append(" where 1=1");
        }

        // type���Ͳ�ѯ�ж�
        if (type != null)
        {
            sql.append(" and ");
            sql.append(baseTable.getAliasName());
            sql.append(".type like ?");
            paraList.add(type + "%");
        }

        // ��������
        {
            this.setSearchorSQL(sql, paraList, searchor, nodeCFG);
            this.setSearchorRecursive(sql, paraList, node, searchor);
        }

        // ���򣬵�type==null��ʱ��������
        // if (type != null)
        // {
        // this.setTaxisSQL(sql, taxis, nodeCFG);
        // }

        // ��� ^_^
        Executor executor = new Executor();
        executor.setSql(sql.toString());
        executor.setParas(paraList.toArray());
        return executor;
    }

    /**
     * �����ڵ������DB����ִ����
     *
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�Ϊnull��ʾ�����������͵Ľڵ�
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Executor��DB����ִ����
     */
    private Executor buildCountExecutor(Node node, String type,
                                        Searchor searchor, Taxis taxis)
    {

        StringBuffer sql = new StringBuffer();
        List paraList = new ArrayList();

        // ��������Ϣbase table��������Դ�Ļ�����Ϣ���������������
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

        // ���죺�ֶ��б� from ���б� where �������
        StringBuffer joinTables = new StringBuffer();
        StringBuffer selectFields = new StringBuffer();
        StringBuffer selectTables = new StringBuffer();
        {
            List tables = nodeCFG.getTables();

            // ���죺�ֶ��б� from ���б� where �������
            this.setTablesSQL(selectFields,
                              selectTables,
                              joinTables,
                              tables,
                              baseTable,
                              baseTable.getKey());

            // ���ýڵ㻹Ҫ���⴦��
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
        // ���ֻ��һ�����ڲ�ѯ�������͵�����£�����û�б��������
        if (joinTables.length() > 0)
        {
            sql.append(" where ");
            sql.append(joinTables);
        }
        else
        {
            sql.append(" where 1=1");
        }

        // type���Ͳ�ѯ�ж�
        if (type != null)
        {
            sql.append(" and ");
            sql.append(baseTable.getAliasName());
            sql.append(".type like ?");
            paraList.add(type + "%");
        }

        // ��������
        {
            this.setSearchorSQL(sql, paraList, searchor, nodeCFG);
            this.setSearchorRecursive(sql, paraList, node, searchor);
        }

        // ����
        //��Ϊ��count������ǲ�Ӧ�ü�order by�ġ�

        // ��� ^_^
        Executor executor = new Executor();
        executor.setSql(sql.toString());
        executor.setParas(paraList.toArray());
        return executor;
    }

    /**
     * �����ڵ�������DB����ִ����
     *
     * @param node Node��Ŀ��ڵ�ʵ��
     * @param type String��Ҫ�����Ľڵ����ͣ�Ϊnull��ʾ�����������͵Ľڵ�
     * @param searchor Searchor�������������ʽ��Ϊnull��ʾ���趨����
     * @param taxis Taxis��������ʽ��Ϊnull��ʾ���趨����
     * @return Executor��DB����ִ����
     */
    private Executor buildSearchExecutor(Node node, String type,
                                         Searchor searchor, Taxis taxis)
    {

        StringBuffer sql = new StringBuffer();
        List paraList = new ArrayList();

        // ��������Ϣbase table��������Դ�Ļ�����Ϣ���������������
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

        // ���죺�ֶ��б� from ���б� where �������
        StringBuffer joinTables = new StringBuffer();
        StringBuffer selectFields = new StringBuffer();
        StringBuffer selectTables = new StringBuffer();
        {
            List tables = nodeCFG.getTables();

            // ���죺�ֶ��б� from ���б� where �������
            this.setTablesSQL(selectFields,
                              selectTables,
                              joinTables,
                              tables,
                              baseTable,
                              baseTable.getKey());

            // ���ýڵ㻹Ҫ���⴦��
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
        // ���ֻ��һ�����ڲ�ѯ�������͵�����£�����û�б��������
        if (joinTables.length() > 0)
        {
            sql.append(" where ");
            sql.append(joinTables);
        }
        else
        {
            sql.append(" where 1=1");
        }

        // type���Ͳ�ѯ�ж�
        if (type != null)
        {
            sql.append(" and ");
            sql.append(baseTable.getAliasName());
            sql.append(".type like ?");
            paraList.add(type + "%");
        }

        // ��������
        {
            this.setSearchorSQL(sql, paraList, searchor, nodeCFG);
            this.setSearchorRecursive(sql, paraList, node, searchor);
        }

        // ���򣬵�type==null��ʱ��������
        if (type != null)
        {
            this.setTaxisSQL(sql, taxis, nodeCFG);
        }

        // ��� ^_^
        Executor executor = new Executor();
        executor.setSql(sql.toString());
        executor.setParas(paraList.toArray());
        return executor;
    }

    /**
     * �����ѯʱ��select�ֶΡ�from���Ͷ��Ĺ������ select x,x,x,x,x from t,t,t,t,t where
     * a1=a1,b1=b2 ���죺�ֶ��б� from ���б� where �������
     *
     * @param selectFields StringBuffer���ֶ��б�Ҫ�����StringBuffer
     * @param selectTables StringBuffer�����б�Ҫ�����StringBuffer
     * @param joinTables StringBuffer���������Ҫ�����StringBuffer
     * @param tables List��Ҫ��ѯ�ĸ�������б�
     * @param joinTable TableCFG�����ڹ����ı�
     * @param refKeyRow String�����ڹ���������
     */
    private void setTablesSQL(StringBuffer selectFields,
                              StringBuffer selectTables,
                              StringBuffer joinTables, List tables,
                              TableCFG joinTable, String refKeyRow)
    {

        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();
        // ����Ҫ��ѯ�ĸ�����
        for (int i = 0; i < tables.size(); i++)
        {
            TableCFG table = ( TableCFG ) tables.get(i);
            String prefix = "";
            if (table.getName().equals(baseTable.getName())
                && !table.getName().equals(joinTable.getName()))
            {
                prefix = NodeCFG.SPEC_PREFIX;
            }
            // ������ǵ�һ��Ҫ���һ���ָ���,
            if (selectTables.length() > 0)
            {
                selectTables.append(',');
            }
            selectTables.append(table.getName());
            selectTables.append(' ');
            // �ñ�ļ������������ɵ�sql���̫����
            selectTables.append(prefix);
            selectTables.append(table.getAliasName());
            // ������ĸ����ֶ�
            List rows = table.getRows();
            for (int j = 0; j < rows.size(); j++)
            {
                RowCFG row = ( RowCFG ) rows.get(j);
                // ������ǵ�һ���ֶΣ�Ҫ���һ���ָ���,
                if (selectFields.length() > 0)
                {
                    selectFields.append(',');
                }
                selectFields.append(prefix);
                selectFields.append(table.getAliasName());
                selectFields.append('.');
                selectFields.append(row.getName());
                // ���⴦����ǰ׺��Ҫ��as���֣���Ȼ���ظ�baseTable������ֶ�
                if (!prefix.equals(""))
                {
                    selectFields.append(" as ");
                    selectFields.append(prefix);
                    selectFields.append(row.getName());
                }
            }
            // ���ǹ�����ͻ�����ģ�����Ҫ�͹��������
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
     * ���ò�ѯʱ��where�����ж����
     *
     * @param sql StringBuffer��sql�����StringBuffer
     * @param paraList List������������б�
     * @param searchor Searchor�������������ʽ
     * @param config NodeCFG����ѯ�ڵ����Ͷ�Ӧ�ĳ־û�����
     */
    private void setSearchorSQL(StringBuffer sql, List paraList,
                                Searchor searchor, NodeCFG config)
    {

        // ������ʽΪ�գ�ֱ�ӷ���
        if (searchor == null)
        {
            return;
        }

        // ��Ҫ���������Ϣ
        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();

        // �������ʽ�ĸ������������д���
        for (int i = 0; i < searchor.getParams().size(); i++)
        {
            SearchParam param = ( SearchParam ) searchor.getParams().get(i);
            sql.append(" ");
            sql.append(param.getMode());
            sql.append(" ");
            // ����������ſ�ʼ��Ҫ���һ��(
            if (param.getBracket()
                     .equals(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT))
            {
                sql.append(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
            }
            // �����ֶζ�Ӧ�����ݿ���ֶ�
            {
                String field = param.getProperty();
                RowCFG row = baseTable.getRowByField(field);
                //��������������ö����õ������������������ǰ+x
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
            // ����������Ž�����Ҫ���һ��)
            if (param.getBracket()
                     .equals(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT))
            {
                sql.append(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);
            }
            paraList.add(param.getValue());
        }
    }

    /**
     * �����������������
     *
     * @param sql StringBuffer��sql�����StringBuffer
     * @param paraList List������������б�
     * @param node Node����ѯ�����Ľڵ�
     * @param searchor Searchor�������������ʽ
     */
    private void setSearchorRecursive(StringBuffer sql, List paraList,
                                      Node node, Searchor searchor)
    {

        // ��Ҫ���������Ϣ
        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();

        // Ĭ����ǳ������
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
     * ���ò�ѯʱ���������
     *
     * @param sql StringBuffer��sql�����StringBuffer
     * @param taxis Taxis��������ʽ
     * @param config NodeCFG����ѯ�ڵ����Ͷ�Ӧ�ĳ־û�����
     */
    private void setTaxisSQL(StringBuffer sql, Taxis taxis, NodeCFG config)
    {

        // ���û�����������ֱ�ӷ���
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
            // ��������ýڵ㣬������Ҫ�����������͵��ֶ�������
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

    // ���´�����Ϊ���Ż�SQL���
    /**
     * �����������������
     *
     * @param sql StringBuffer��sql�����StringBuffer
     * @param paraList List������������б�
     * @param node Node����ѯ�����Ľڵ�
     * @param searchor Searchor�������������ʽ
     */
    private void setSearchorRecursive_optimize(StringBuffer sql, List paraList,
                                               Node node, Searchor searchor)
    {

        // ��Ҫ���������Ϣ
        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();

        // Ĭ����ǳ������
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
     * �����ѯʱ��select�ֶΡ�from���Ͷ��Ĺ������ select x,x,x,x,x from t,t,t,t,t where
     * a1=a1,b1=b2 ���죺�ֶ��б� from ���б� where �������
     *
     * @param selectFields StringBuffer���ֶ��б�Ҫ�����StringBuffer
     * @param selectTables StringBuffer�����б�Ҫ�����StringBuffer
     * @param joinTables StringBuffer���������Ҫ�����StringBuffer
     * @param tables List��Ҫ��ѯ�ĸ�������б�
     * @param joinTable TableCFG�����ڹ����ı�
     * @param refKeyRow String�����ڹ���������
     */
    private void setTablesSQL_optimize(StringBuffer selectFields,
                                       StringBuffer selectTables,
                                       StringBuffer joinTables, List tables,
                                       TableCFG joinTable, String refKeyRow)
    {

        TableCFG baseTable = DBPersistencyCFG.getInstance().getBaseTable();
        // ����Ҫ��ѯ�ĸ�����
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
            // ������ǵ�һ��Ҫ���һ���ָ���,
            if (selectTables.length() > 0)
            {
                selectTables.append(',');
            }
            selectTables.append(table.getName());
            selectTables.append(' ');
            // �ñ�ļ������������ɵ�sql���̫����
            selectTables.append(prefix);
            selectTables.append(table.getAliasName());
            // ������ĸ����ֶ�
            List rows = table.getRows();
            for (int j = 0; j < rows.size(); j++)
            {
                RowCFG row = ( RowCFG ) rows.get(j);
                // ������ǵ�һ���ֶΣ�Ҫ���һ���ָ���,
                if (selectFields.length() > 0)
                {
                    selectFields.append(',');
                }
                selectFields.append(prefix);
                selectFields.append(table.getAliasName());
                selectFields.append('.');
                selectFields.append(row.getName());
                // ���⴦����ǰ׺��Ҫ��as���֣���Ȼ���ظ�baseTable������ֶ�
                if (!prefix.equals(""))
                {
                    selectFields.append(" as ");
                    selectFields.append(prefix);
                    selectFields.append(row.getName());
                }
            }
            // ���ǹ�����ͻ�����ģ�����Ҫ�͹��������
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
