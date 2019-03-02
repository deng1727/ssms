package com.aspire.common.config;

/**
 * <p>Title: Poralt OAM</p>
 * <p>Description: Portal OAM Program file</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author He Chengshou
 * @version 1.0
 */
// @CheckItem@ OPT-penglq-20050301 新增返回element的方法
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
     * 构造器：创建一个新的子系统配置项。
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
     * 构造器：从配置文件构造一个子系统配置类。
     *
     * @param element
     */
    public ModuleConfig(Element element) {
        this.module = element;
        this.moduleName = element.getAttribute("Name").getValue();
    }

    /**
     * 返回本子系统名称
     *
     * @return 子系统名称
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * 返回子系统配置的描述信息
     *
     * @return
     */
    public String getDescription() {
        String desc = module.getAttribute("Description").getValue();
        return desc;
    }

    /**
     * 设置子系统配置的描述信息
     *
     * @param desc
     */
    public void setDescription(String desc) {
        module.setAttribute(new org.jdom.Attribute("Description", desc));
    }

    /**
     * 返回子系统配置的一个属性
     *
     * @param attName 属性名称
     * @return 系统配置的一个属性
     */
    public String getModuleAttributeValue(String attName) {
        String attributeValue = module.getAttributeValue(attName);
        return attributeValue;
    }

    /**
     * 得到指定的配置项类
     *
     * @param itemName 配置项名称
     * @return 配置项类
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
     * 得到本子系统的所有配置项
     *
     * @return 配置项数组
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
     * 得到指定的配置项类
     *
     * @param itemName 配置项名称
     * @return 配置项类
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
     * 得到本子系统的所有配置项
     *
     * @return 配置项数组
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
     * 简单的取值函数。为了方便调用，
     *
     * @param itemName 配置项名称
     * @return 配置值
     */
    public String getItemValue(String itemName) {
        Item item = getItem(itemName);
        return item.getValue();
    }

    /**
     * 简单的取属性值函数。为了方便调用，
     *
     * @param itemName 属性名称
     * @return 属性值
     */
    public String getItemAttributeValue(String itemName, String attributeName) {
        Item item = getItem(itemName);
        return item.getAttributeValue(attributeName);
    }

    /**
     * 简单的取值列表函数。为了方便调用，
     *
     * @param itemName 配置项名称
     * @return 配置值数组
     */
    public String[] getItemValueList(String itemName) {
        ArrayItem arrayItem = this.getArrayItem(itemName);
        return arrayItem.getValueList();
    }

    /**
     * 增加一个配置项
     *
     * @param item 配置项对象
     * @throws ConfigException
     */
    public void addItem(Item item) throws ConfigException {
        if (this.getItem(item.getName()) != null) {
            throw new ConfigException("Duplicated name tag:" + item.getName());
        }
        module.addContent(item.getElement());
    }

    /**
     * 增加一个多值配置项
     *
     * @param arrayItem 配置项对象
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
     * 删除一个配置项
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
     * 删除一个多值配置项
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
     * 获得Module的属性列表
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
     * 返回模块对象
     */
    public Element getModuleElement() {
        return module;
    }

}
