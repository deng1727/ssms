package com.aspire.ponaadmin.web.repository.persistency.db ;

import java.io.File;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>�������ļ�repository_db.xml�ж�ȡ�־û������ݿ��������������</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class DBPersistencyCFG
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(DBPersistencyCFG.class) ;

    /**
     * ���췽��
     */
    private DBPersistencyCFG ()
    {
    }
    //�����Ѿ�����ķ�������
    private List orderedTypeList;

    /**
     * ����ʵ��
     */
    private static DBPersistencyCFG instance = new DBPersistencyCFG();

    public static DBPersistencyCFG getInstance()
    {
        return instance;
    }

    /**
     * �������������Դ������Ϣ��map
     */
    private HashMap nodeCFGs = new HashMap();

    /**
     * �������Ͳ�ѯһ����Դ�ĳ־û�������Ϣ
     * @param type String��Ҫ��ѯ����Դ����
     * @return NodeCFG��һ����Դ�ĳ־û�������Ϣ
     */
    public NodeCFG getNodeCFG(String type)
    {
        return (NodeCFG) this.nodeCFGs.get(type);
    }

    /**
     * ��ȡ������������Ϣ
     * @return TableCFG,��ȡ������������Ϣ
     */
    public TableCFG getBaseTable()
    {
        return (TableCFG) ((NodeCFG) this.nodeCFGs.get("nt:base")).getTables().
            get(0) ;
    }

    /**
     * ��һ��Row���õ�xmlԪ�ص���Ϣ����
     * @param rowElement Element��Row���õ�xmlԪ��
     * @return RowCFG��Row����
     */
    private RowCFG readRow(Element rowElement)
    {
        //��JDOM��Element�ж����������
        String rowName = rowElement.getAttributeValue("name") ;
        String rowClassField = rowElement.getAttributeValue("classField") ;
        String rowType = rowElement.getAttributeValue("type") ;
        RowCFG row = new RowCFG() ;
        row.setName(rowName) ;
        row.setClassField(rowClassField) ;
        row.setType(rowType) ;
        return row ;
    }

    /**
     * ��һ��Table���õ�xmlԪ�ص���Ϣ����
     * @param tableElement Element��Table���õ�xmlԪ��
     * @return TableCFG��Table����
     */
    private TableCFG readTable(Element tableElement)
    {
        //��JDOM��Element�ж����������
        String tableName = tableElement.getAttributeValue("name") ;
        String tableKey = tableElement.getAttributeValue("key") ;
        TableCFG table = new TableCFG() ;
        table.setName(tableName) ;
        table.setKey(tableKey) ;
        //����Ҫ�Ѹ���row������
        List rowElementList = tableElement.getChildren("row") ;
        for(int i = 0; i < rowElementList.size(); i++)
        {
            Element element = (Element) rowElementList.get(i);
            //����this.readRow������ȡ��ǰRow���õ�row
            RowCFG row = this.readRow(element);
            table.addRow(row) ;
        }
        return table ;
    }

    /**
     * ��һ��Node���õ�xmlԪ�ص���Ϣ����
     * @param nodeElement Element��Node���õ�xmlԪ��
     * @return NodeCFG��Node����
     */
    private NodeCFG readNode(Element nodeElement)
    {
        //��JDOM��Element�ж����������
        String nodeType = nodeElement.getChildText("type") ;
        String nodeTypeDesc = nodeElement.getChildText("typeDesc") ;
        String clazz = nodeElement.getChildText("class") ;
        String extend = nodeElement.getChildText("extend") ;
        NodeCFG node = new NodeCFG() ;
        node.setType(nodeType) ;
        node.setTypeDesc(nodeTypeDesc);
        node.setClazz(clazz) ;
        node.setExtend(extend) ;
        //���ýڵ�Ҫ���⴦��
        if(nodeType.startsWith("nt:reference"))
        {
            node.setRefType(nodeElement.getChildText("refType"));
            node.setRefKeyRow(nodeElement.getChildText("refKeyRow"));
        }
        //�������չ���ͣ�����Ҫ����չ���͵ı���Ϣ���롣
        if(extend != null)
        {
            NodeCFG extendNode = this.getNodeCFG(extend);
            for (int j = 0 ; j < extendNode.getTables().size() ; j++)
            {
                TableCFG table = (TableCFG) extendNode.getTables().get(j);
                //��Ҫcloneһ�ѣ��Ա������͸��ǵ�Σ��
                TableCFG newTable =(TableCFG)table.clone();
                node.addTable(newTable);
            }
        }
        //����Ҫ��tables�е�table������
        List tableElementList = nodeElement.getChild("tables").getChildren("table");
        for(int i = 0; i < tableElementList.size(); i++)
        {
            Element element = (Element) tableElementList.get(i);
            //����this.readTable������ȡ��ǰTable���õ�table
            TableCFG table = this.readTable(element);
            node.addTable(table) ;
        }
        return node ;
    }

    /**
     * ����Դģ��װ��������Ϣ�ķ���
     * @param configFile String����Դģ���ļ�
     */
    public void load(String configFile)
    {
        try
        {
            //����jdom������
            SAXBuilder sb = new SAXBuilder() ;
            //Document �ڵ�
            Document doc = sb.build(new File(configFile)) ;
            //�õ����ڵ�
            Element root = doc.getRootElement() ;
            //�õ�node�б�
            List nodeList = root.getChild("nodes").getChildren("node") ;
            //����nodeList
            for(int i = 0; i < nodeList.size(); i++)
            {
                Element element = (Element) nodeList.get(i);
                //����this.readTable������ȡ��ǰTable���õ�table
                NodeCFG node = this.readNode(element);
                //����this.readNode������ȡ��ǰNode���õ�node
                this.nodeCFGs.put(node.getType(), node);
            }
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
//        if(LOG.isDebugEnabled())
//        {
//            LOG.debug(this.toString());
//        }
    }

    /**
     * ��ȡĳ�����͵������ͣ������ǵݹ�ġ�
     * @param type String,���͡�
     * @param recursive boolean,�Ƿ�ݹ顣�ݹ�Ļ������ȡ�����е��������͡�
     * @return
     */
    private List getSubType(String type,boolean recursive)
    {
    	List result = new ArrayList();
    	Object[] types = this.nodeCFGs.values().toArray();
    	for(int i=0;i<types.length;i++)
    	{
    		NodeCFG nodeCFG = (NodeCFG)types[i];
    		String extend = nodeCFG.getExtend();
    		if(extend!=null && extend.equals(type))
    		{
    			result.add(nodeCFG);
    			//�������Ҫ�ݹ�ģ�����Ҫ�ѷ���������������͵���������ץ������
    			if(recursive)
    			{
        			List childResult = this.getSubType(nodeCFG.getType(), true);
        			result.addAll(childResult);
    			}
    		}
    	}
    	return result;
    }
    /**
     * ��ȡĳ�����͵������ͣ����Ͱ�����ĸ�������С�
     * @param type ���͡�
     * @param recursive �Ƿ�ݹ顣�ݹ�Ļ������ȡ�����е��������͡�
     * @return
     */
    public List getOrderedSubType(String type,boolean recursive)
    {
    	if(orderedTypeList==null)
    	{
    		orderedTypeList=getSubType(type,recursive);
    		//��Ҫ���ݺ��ֻ�������
    		final RuleBasedCollator  collator = (RuleBasedCollator)java.text.Collator.getInstance(java.util.Locale.CHINA);
    		Collections.sort(orderedTypeList, new Comparator()
			{
				public int compare(Object obj1, Object obj2)
				{
					NodeCFG t1 = (NodeCFG) obj1;
					NodeCFG t2 = (NodeCFG) obj2;
					return collator.compare(t1.typeDesc, t2.typeDesc);
					//return t1.typeDesc.compareTo(t2.typeDesc);
				}

			});
    		
    	}
    	return orderedTypeList;
    	
    }

    
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        Iterator nodeTypeIterator = this.nodeCFGs.keySet().iterator();
        while(nodeTypeIterator.hasNext())
        {
            String nodeType = (String) nodeTypeIterator.next() ;
            NodeCFG nodeCFG = (NodeCFG)this.nodeCFGs.get(nodeType) ;
            sb.append("\n");
            sb.append("node:"+nodeCFG);
            sb.append("\n=======================================\n");
        }
        return sb.toString();
    }
   
}
