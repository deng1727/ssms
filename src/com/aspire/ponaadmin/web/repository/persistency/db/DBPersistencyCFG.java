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
 * <p>从配置文件repository_db.xml中读取持久化到数据库的相关配置项的类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */

public class DBPersistencyCFG
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(DBPersistencyCFG.class) ;

    /**
     * 构造方法
     */
    private DBPersistencyCFG ()
    {
    }
    //缓存已经排序的分类类型
    private List orderedTypeList;

    /**
     * 单根实例
     */
    private static DBPersistencyCFG instance = new DBPersistencyCFG();

    public static DBPersistencyCFG getInstance()
    {
        return instance;
    }

    /**
     * 保存各种类型资源配置信息的map
     */
    private HashMap nodeCFGs = new HashMap();

    /**
     * 根据类型查询一种资源的持久化配置信息
     * @param type String，要查询的资源类型
     * @return NodeCFG，一种资源的持久化配置信息
     */
    public NodeCFG getNodeCFG(String type)
    {
        return (NodeCFG) this.nodeCFGs.get(type);
    }

    /**
     * 获取基本表配置信息
     * @return TableCFG,获取基本表配置信息
     */
    public TableCFG getBaseTable()
    {
        return (TableCFG) ((NodeCFG) this.nodeCFGs.get("nt:base")).getTables().
            get(0) ;
    }

    /**
     * 把一段Row配置的xml元素的信息读入
     * @param rowElement Element，Row配置的xml元素
     * @return RowCFG，Row配置
     */
    private RowCFG readRow(Element rowElement)
    {
        //用JDOM从Element中读入各个属性
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
     * 把一段Table配置的xml元素的信息读入
     * @param tableElement Element，Table配置的xml元素
     * @return TableCFG，Table配置
     */
    private TableCFG readTable(Element tableElement)
    {
        //用JDOM从Element中读入各个属性
        String tableName = tableElement.getAttributeValue("name") ;
        String tableKey = tableElement.getAttributeValue("key") ;
        TableCFG table = new TableCFG() ;
        table.setName(tableName) ;
        table.setKey(tableKey) ;
        //下面要把各个row读出来
        List rowElementList = tableElement.getChildren("row") ;
        for(int i = 0; i < rowElementList.size(); i++)
        {
            Element element = (Element) rowElementList.get(i);
            //调用this.readRow方法读取当前Row配置到row
            RowCFG row = this.readRow(element);
            table.addRow(row) ;
        }
        return table ;
    }

    /**
     * 把一段Node配置的xml元素的信息读入
     * @param nodeElement Element，Node配置的xml元素
     * @return NodeCFG，Node配置
     */
    private NodeCFG readNode(Element nodeElement)
    {
        //用JDOM从Element中读入各个属性
        String nodeType = nodeElement.getChildText("type") ;
        String nodeTypeDesc = nodeElement.getChildText("typeDesc") ;
        String clazz = nodeElement.getChildText("class") ;
        String extend = nodeElement.getChildText("extend") ;
        NodeCFG node = new NodeCFG() ;
        node.setType(nodeType) ;
        node.setTypeDesc(nodeTypeDesc);
        node.setClazz(clazz) ;
        node.setExtend(extend) ;
        //引用节点要特殊处理
        if(nodeType.startsWith("nt:reference"))
        {
            node.setRefType(nodeElement.getChildText("refType"));
            node.setRefKeyRow(nodeElement.getChildText("refKeyRow"));
        }
        //如果有扩展类型，还需要把扩展类型的表信息读入。
        if(extend != null)
        {
            NodeCFG extendNode = this.getNodeCFG(extend);
            for (int j = 0 ; j < extendNode.getTables().size() ; j++)
            {
                TableCFG table = (TableCFG) extendNode.getTables().get(j);
                //需要clone一把，以避免错误和覆盖的危险
                TableCFG newTable =(TableCFG)table.clone();
                node.addTable(newTable);
            }
        }
        //下面要把tables中的table读出来
        List tableElementList = nodeElement.getChild("tables").getChildren("table");
        for(int i = 0; i < tableElementList.size(); i++)
        {
            Element element = (Element) tableElementList.get(i);
            //调用this.readTable方法读取当前Table配置到table
            TableCFG table = this.readTable(element);
            node.addTable(table) ;
        }
        return node ;
    }

    /**
     * 从资源模板装载配置信息的方法
     * @param configFile String，资源模板文件
     */
    public void load(String configFile)
    {
        try
        {
            //引入jdom的配置
            SAXBuilder sb = new SAXBuilder() ;
            //Document 节点
            Document doc = sb.build(new File(configFile)) ;
            //得到根节点
            Element root = doc.getRootElement() ;
            //得到node列表：
            List nodeList = root.getChild("nodes").getChildren("node") ;
            //遍历nodeList
            for(int i = 0; i < nodeList.size(); i++)
            {
                Element element = (Element) nodeList.get(i);
                //调用this.readTable方法读取当前Table配置到table
                NodeCFG node = this.readNode(element);
                //调用this.readNode方法读取当前Node配置到node
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
     * 获取某个类型的子类型，可以是递归的。
     * @param type String,类型。
     * @param recursive boolean,是否递归。递归的话，会获取到所有的子孙类型。
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
    			//如果是需要递归的，还需要把符合条件的这个类型的子类型再抓出来。
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
     * 获取某个类型的子类型，类型按照字母升序排列。
     * @param type 类型。
     * @param recursive 是否递归。递归的话，会获取到所有的子孙类型。
     * @return
     */
    public List getOrderedSubType(String type,boolean recursive)
    {
    	if(orderedTypeList==null)
    	{
    		orderedTypeList=getSubType(type,recursive);
    		//需要根据汉字机型排序
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
