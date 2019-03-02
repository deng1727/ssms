/*
 * 
 */

package com.aspire.ponaadmin.web.category;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * @author x_wangml
 * 
 */
public class CategoryUpdateConfig
{

    /**
     * 实例
     */
    private static CategoryUpdateConfig config = new CategoryUpdateConfig();

    /**
     * 内存结构
     */
    private Map itemConfMap = new HashMap();
    /**
     * 内存结构
     */
    private Map nodeConfMap = new HashMap();

    /**
     * 构造方法
     */
    private CategoryUpdateConfig()
    {

    }

    /**
     * 获取实例
     * 
     * @return CategoryUpdateConfig
     */
    public static CategoryUpdateConfig getInstance()
    {
        return config;
    }

    /**
     * 初始化实例
     */
    public void init(String fileName)
    {

        // 引入jdom的配置
        SAXBuilder sb = new SAXBuilder();

        // Document 节点
        Document doc = null;
        try
        {
            doc = sb.build(new File(fileName));
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // 得到根节点
        Element root = doc.getRootElement();

        // 得到categorydate列表：
        List categorydate = root.getChildren("categoryData");

        // 解析categoryupdateconfig.xml
        parseXML(categorydate);
    }

    /**
     * 解析categoryupdateconfig.xml
     * 
     * @param categorydate 根节点
     */
    private void parseXML(List categorydate)
    {

        // 循环遍历
        for (int i = 0; i < categorydate.size(); i++)
        {
            Element element = ( Element ) categorydate.get(i);

            // 得到数据字段名称
            String name = element.getAttributeValue("name");

            // 得到定义的字典集合
            List dates = element.getChildren("item");

            // 用于存储临时数据
            
            List itemList=new ArrayList();
            
            for (Iterator iter = dates.iterator(); iter.hasNext();)
            {
                Element date = ( Element ) iter.next();
                RecordVO temp = new RecordVO();
                temp.put(date.getChild("key").getText(), date.getChild("value")
                                                             .getText());
                String key=name+"_"+date.getChild("key").getText();
                itemConfMap.put(key, temp);
                itemList.add(temp);
            }
            nodeConfMap.put(name, itemList);
            // 存入内存结构
            
        }
    }

    /**
     * 根据数据字段与字段实际值得到显示内容
     * 
     * @param categoryDate 字段名
     * @param key 字段实际值
     * @return 显示内容
     */
    public String getShowValue(String nodeName, String key)
    {
        String value = "无对应显示信息";

        // 得到数据字段相应值
        String savekey=nodeName+"_"+key;
        RecordVO temp = ( RecordVO ) itemConfMap.get(savekey);

        // 如果没有得到。返回空
        if (null != temp)
        {
            return temp.getValue();
        }
        else
        {
            return value;
        }
    }
    /**
     * 获取节点所有值的列表
     * @param nodeName 节点名称
     * @return 该节点所有值的列表
     */
    public List getNodeValueList(String nodeName)
    {
        List temp = ( List ) nodeConfMap.get(nodeName);

        // 如果没有得到。返回空
        if (null != temp)
        {
            return temp;
        }
        else
        {
        	nodeConfMap.put(nodeName, new ArrayList(0));
        	return getNodeValueList(nodeName);
        }
    }
}
