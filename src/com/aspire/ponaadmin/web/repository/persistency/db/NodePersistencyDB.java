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
 * <p>�̳�NodePersistency�࣬ʵ�ֳ־û������ݿ⡣</p>
 * <p>NodePersistencyDB������Ӧ�ĳ�������SQLBuilder����ִ�������ɵ�sql��䡣</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class NodePersistencyDB extends NodePersistency
{

    /**
     * ��С�Ľڵ��ID
     */
    private static final int INIT_NODE_ID = 1000;
    
    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(NodePersistencyDB.class) ;

    /**
     * ����ֵ�����ڷ����µĽڵ�id
     */
    private static int nodeIDIndex = INIT_NODE_ID ;

    /**
     * �ڵ�id����С��ʼֵ
     */
    private static final int MINNODEID = 7000 ;

    /**
     * �ڵ�id����ֵ����
     */
    private static Object nodeIDIndexLock = new Object() ;

    /**
     * �������
     */
    private CommandBuilder cmdBuilder = DBCommandBuilder.getInstance();

    /**
     * ���췽��
     * @param node����Ӧ�Ľڵ�ʵ��
     */
    public NodePersistencyDB(Node node)
    {
        super(node);
    }

    /**
     * �־û����ĳ�ʼ������
     * @param configFile String�����ļ�
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
            //��С�����MINNODEID��ʼ����
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
     * ����һ���µĽڵ�ID
     * @return String���µĽڵ�id
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
                 //��С�����MINNODEID��ʼ����
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
     * ����Խڵ����Եı䶯
     * @param changeProperties HashMap
     */
    public void save(HashMap changeProperties)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("save()");
        }
        //���û�����Ա䶯����ֱ�ӷ����ˡ�
        if (changeProperties.isEmpty())
        {
            return ;
        }
        Command cmd = cmdBuilder.buildSaveCommand(this.node, changeProperties) ;
        cmd.execute() ;
    }

    /**
     * ����Ա��ڵ���ӽڵ�ı䶯��
     * @param addChilds List,���ӵĽڵ��б�
     * @param delChilds List,ɾ���Ľڵ��б�
     */
    public void saveNode(List addChilds, List delChilds)
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("saveNode()");
        }
        //���û���ӽڵ�䶯����ֱ�ӷ����ˡ�
        if (addChilds.isEmpty() && delChilds.isEmpty())
        {
            return ;
        }
        Command cmd = cmdBuilder.buildSaveNodeCommand(this.node, addChilds,
                                                      delChilds) ;
        cmd.execute() ;
    }

    /**
     * ����[�����ڵ�����/δ�����ڵ�����]��ĳ�����Դ
     * @param type String����������
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
     * ����[�����ڵ�����/δ�����ڵ�����]��ĳ�����Դ
     * @param pager PageResult����ҳ��
     * @param type String����������
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
     * �ڱ��ڵ��²�ѯ�����������ӽڵ�
     * @param type String,Ҫ���ҵ��ӽڵ����ͣ����Ϊnull��ʾ�����������͵��ӽڵ㡣
     * @param searchor Searchor,��������
     * @param taxis Taxis,�����������Ϊnull��ʾ��������
     * @return List����������б�
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
     * �ڱ��ڵ��¼��������������ӽڵ�
     * @param type String,Ҫ���ҵ��ӽڵ����ͣ����Ϊnull��ʾ�����������͵��ӽڵ㡣
     * @param searchor Searchor,��������
     * @param taxis Taxis,�����������Ϊnull��ʾ��������
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
     * �ڱ��ڵ��²�ѯ�����������ӽڵ㣨��ҳ�棩
     * @param pager PageResult����ҳ��
     * @param type String,Ҫ���ҵ��ӽڵ����ͣ����Ϊnull��ʾ�����������͵��ӽڵ㡣
     * @param searchor Searchor,��������
     * @param taxis Taxis,�����������Ϊnull��ʾ��������
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
     * �����ֶ��б��ӽ�����й�������ΪclassName����Դ�ڵ㡣
     * @param className String��������
     * @param rs ResultSet�������
     * @param rowList List���ֶ��б�
     * @return Node����������Դ�ڵ�ʵ��
     * @throws Exception
     */
    static final Node buildNode(String className, ResultSet rs, List rowList) throws Exception
    {
        //��java���䴴����Ӧ�Ľڵ���
        Class clazz = Class.forName(className);
        Node node = (Node) clazz.newInstance();
        //��bean�淶���ýڵ�ĸ�������
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
     * �����ݿ��л�ȡֵ
     * @param rowName String���ֶ�����
     * @param rowType String���ֶ�����
     * @param rs ResultSet�������
     * @return Object����ȡ����ֵ
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
     * �������ݵ�����ID�����ֵ����Сֵ
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
