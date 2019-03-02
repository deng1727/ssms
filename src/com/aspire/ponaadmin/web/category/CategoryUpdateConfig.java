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
     * ʵ��
     */
    private static CategoryUpdateConfig config = new CategoryUpdateConfig();

    /**
     * �ڴ�ṹ
     */
    private Map itemConfMap = new HashMap();
    /**
     * �ڴ�ṹ
     */
    private Map nodeConfMap = new HashMap();

    /**
     * ���췽��
     */
    private CategoryUpdateConfig()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return CategoryUpdateConfig
     */
    public static CategoryUpdateConfig getInstance()
    {
        return config;
    }

    /**
     * ��ʼ��ʵ��
     */
    public void init(String fileName)
    {

        // ����jdom������
        SAXBuilder sb = new SAXBuilder();

        // Document �ڵ�
        Document doc = null;
        try
        {
            doc = sb.build(new File(fileName));
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // �õ����ڵ�
        Element root = doc.getRootElement();

        // �õ�categorydate�б�
        List categorydate = root.getChildren("categoryData");

        // ����categoryupdateconfig.xml
        parseXML(categorydate);
    }

    /**
     * ����categoryupdateconfig.xml
     * 
     * @param categorydate ���ڵ�
     */
    private void parseXML(List categorydate)
    {

        // ѭ������
        for (int i = 0; i < categorydate.size(); i++)
        {
            Element element = ( Element ) categorydate.get(i);

            // �õ������ֶ�����
            String name = element.getAttributeValue("name");

            // �õ�������ֵ伯��
            List dates = element.getChildren("item");

            // ���ڴ洢��ʱ����
            
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
            // �����ڴ�ṹ
            
        }
    }

    /**
     * ���������ֶ����ֶ�ʵ��ֵ�õ���ʾ����
     * 
     * @param categoryDate �ֶ���
     * @param key �ֶ�ʵ��ֵ
     * @return ��ʾ����
     */
    public String getShowValue(String nodeName, String key)
    {
        String value = "�޶�Ӧ��ʾ��Ϣ";

        // �õ������ֶ���Ӧֵ
        String savekey=nodeName+"_"+key;
        RecordVO temp = ( RecordVO ) itemConfMap.get(savekey);

        // ���û�еõ������ؿ�
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
     * ��ȡ�ڵ�����ֵ���б�
     * @param nodeName �ڵ�����
     * @return �ýڵ�����ֵ���б�
     */
    public List getNodeValueList(String nodeName)
    {
        List temp = ( List ) nodeConfMap.get(nodeName);

        // ���û�еõ������ؿ�
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
