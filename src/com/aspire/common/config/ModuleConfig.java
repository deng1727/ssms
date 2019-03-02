package com.aspire.common.config;

/**
 * <p>Title: Poralt OAM</p>
 * <p>Description: Portal OAM Program file</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author He Chengshou
 * @version 1.0
 */
// @CheckItem@ OPT-penglq-20050301 ��������element�ķ���
import org.jdom.Element;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ModuleConfig implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private org.jdom.Element module = null;
    private String moduleName = null;

    /**
     * ������������һ���µ���ϵͳ�����
     *
     * @param moduleName
     */
    public ModuleConfig(String moduleName) {
        Element element = new Element("ModuleConfig");
        element.setAttribute(new org.jdom.Attribute("Name", moduleName));
        this.moduleName = moduleName;
        this.module = element;
    }

    /**
     * ���������������ļ�����һ����ϵͳ�����ࡣ
     *
     * @param element
     */
    public ModuleConfig(Element element) {
        this.module = element;
        this.moduleName = element.getAttribute("Name").getValue();
    }

    /**
     * ���ر���ϵͳ����
     *
     * @return ��ϵͳ����
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * ������ϵͳ���õ�������Ϣ
     *
     * @return
     */
    public String getDescription() {
        String desc = module.getAttribute("Description").getValue();
        return desc;
    }

    /**
     * ������ϵͳ���õ�������Ϣ
     *
     * @param desc
     */
    public void setDescription(String desc) {
        module.setAttribute(new org.jdom.Attribute("Description", desc));
    }

    /**
     * ������ϵͳ���õ�һ������
     *
     * @param attName ��������
     * @return ϵͳ���õ�һ������
     */
    public String getModuleAttributeValue(String attName) {
        String attributeValue = module.getAttributeValue(attName);
        return attributeValue;
    }

    /**
     * �õ�ָ������������
     *
     * @param itemName ����������
     * @return ��������
     */
    public Item getItem(String itemName) {
        Item item = null;
        List list = module.getChildren("ConfigItem");
        Iterator itrts = list.iterator();
        while (itrts.hasNext()) {
            Element element = (Element) itrts.next();
            if ((element.getChild("Name").getText()).equalsIgnoreCase(itemName)) {
                item = new Item(element);
                break;
            }

        }
        return item;
    }

    /**
     * �õ�����ϵͳ������������
     *
     * @return ����������
     */
    public Item[] getItems() {
        List list = module.getChildren("ConfigItem");
        Item[] items = new Item[list.size()];
        Iterator itrts = list.iterator();
        int i = 0;
        while (itrts.hasNext()) {
            Element element = (Element) itrts.next();
            Item item = new Item(element);
            items[i] = item;
            i++;
        }
        return items;
    }

    /**
     * �õ�ָ������������
     *
     * @param itemName ����������
     * @return ��������
     */
    public ArrayItem getArrayItem(String itemName) {
        ArrayItem arrayItem = null;
        List list = module.getChildren("ArrayConfigItem");
        Iterator itrts = list.iterator();
        while (itrts.hasNext()) {
            Element element = (Element) itrts.next();
            if ((element.getChild("Name").getText()).equalsIgnoreCase(itemName)) {
                arrayItem = new ArrayItem(element);
                break;
            }

        }
        return arrayItem;
    }

    /**
     * �õ�����ϵͳ������������
     *
     * @return ����������
     */
    public ArrayItem[] getArrayItems() {
        List list = module.getChildren("ArrayConfigItem");
        ArrayItem[] arrayItems = new ArrayItem[list.size()];
        Iterator itrts = list.iterator();
        int i = 0;
        while (itrts.hasNext()) {
            Element element = (Element) itrts.next();
            ArrayItem arrayItem = new ArrayItem(element);
            arrayItems[i] = arrayItem;
            i++;
        }
        return arrayItems;
    }

    /**
     * �򵥵�ȡֵ������Ϊ�˷�����ã�
     *
     * @param itemName ����������
     * @return ����ֵ
     */
    public String getItemValue(String itemName) {
        Item item = getItem(itemName);
        return item.getValue();
    }

    /**
     * �򵥵�ȡ����ֵ������Ϊ�˷�����ã�
     *
     * @param itemName ��������
     * @return ����ֵ
     */
    public String getItemAttributeValue(String itemName, String attributeName) {
        Item item = getItem(itemName);
        return item.getAttributeValue(attributeName);
    }

    /**
     * �򵥵�ȡֵ�б�����Ϊ�˷�����ã�
     *
     * @param itemName ����������
     * @return ����ֵ����
     */
    public String[] getItemValueList(String itemName) {
        ArrayItem arrayItem = this.getArrayItem(itemName);
        return arrayItem.getValueList();
    }

    /**
     * ����һ��������
     *
     * @param item ���������
     * @throws ConfigException
     */
    public void addItem(Item item) throws ConfigException {
        if (this.getItem(item.getName()) != null) {
            throw new ConfigException("Duplicated name tag:" + item.getName());
        }
        module.addContent(item.getElement());
    }

    /**
     * ����һ����ֵ������
     *
     * @param arrayItem ���������
     * @throws ConfigException
     */
    public void addArrayItem(ArrayItem arrayItem) throws ConfigException {
        if (this.getArrayItem(arrayItem.getName()) != null) {
            throw new ConfigException("Duplicated name tag:" +
                    arrayItem.getName());
        }
        module.addContent(arrayItem.getElement());
    }

    /**
     * ɾ��һ��������
     *
     * @param itemName
     */
    public void removeItem(String itemName) {
        Item item = this.getItem(itemName);
        if (item == null) {
            return;
        }
        module.removeContent(item.getElement());
    }

    /**
     * ɾ��һ����ֵ������
     *
     * @param itemName
     */
    public void removeArrayItem(String itemName) {
        ArrayItem ai = this.getArrayItem(itemName);
        if (ai == null) {
            return;
        }
        module.removeContent(ai.getElement());
    }

    protected Element getElement() {
        return this.module;
    }

    public String toString() {
        String xml = null;
        try {
            java.io.StringWriter sw = new java.io.StringWriter(1000);
            org.jdom.output.XMLOutputter out = new org.jdom.output.XMLOutputter();
            out.setIndent("   ");
            out.setNewlines(true);
            out.output(this.module, sw);
            xml = sw.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return xml;
    }

    public void showData() {
        try {
            org.jdom.output.XMLOutputter out = new org.jdom.output.XMLOutputter();
            out.setIndent("   ");
            //out.setNewlines(true);
            out.output(this.module, System.out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ���Module�������б�
     * #author Chen Yang 2004-08-19
     */
    public Attribute[] getAttributes() {
        List list = this.module.getAttributes();
        Attribute[] attriubutes = new Attribute[list.size()];
        Iterator itrts = list.iterator();
        int i = 0;
        while (itrts.hasNext()) {
            org.jdom.Attribute tempAattribute = (org.jdom.Attribute) itrts.next();
            Attribute myAttr =
                    new Attribute(tempAattribute.getName(), tempAattribute.getValue());
            attriubutes[i] = myAttr;
            i++;
        }
        return attriubutes;
    }

    /*
     * ����ģ�����
     */
    public Element getModuleElement() {
        return module;
    }

}
