package com.aspire.common.config;

/**
 * <p>Title: Poralt OAM</p>
 * <p>Description:Item,和ArrayItem类的公共方法</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author He Chengshou
 * @version 1.0
 */
// @CheckItem@ OPT-hecs-20040202 增加宏定义功能
// @CheckItem@ OPT-penglq-20050301 新增返回element的方法
import org.jdom.CDATA;
import org.jdom.Element;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class AbstractItem
    implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected Element element = null;
    /**
     * 得到此配置项的描述
     * @return 配置项描述字串
     */
    public String getDescription() {
        String desc = this.element.getChild("Description").getText();
        if (desc == null) {
            desc = " ";
        }
        return desc;
    }

    /**
     * 设置配置项描述，如果没有此标签，便增加一个。
     * @param value
     */
    public void setDescription(String desc) {
        this.addElement("Description", desc);
    }

    /**
     * 增加一个属性
     * @param name 属性名称
     * @param value 属性值
     */
    public void addAttribute(String name, String value) {
        this.element.setAttribute(name, value);
    }

    /**
     * 删除一个属性
     * @param attribute 属性名称
     */
    public void removeAttribute(String attName) {
        this.element.removeAttribute(attName);
    }

    /**
     * 得到参数的名称
     * @return
     */
    public String getName() {
        return element.getChild("Name").getText();
    }

    /**
     * 设置属性名称，如果没有此标签，便增加一个。
     * @param itemName
     */
    public void setName(String itemName) {
        this.addElement("Name", itemName);
    }

    /**
     * 得到配置项的一个属性列表
     * @param attribName
     * @return
     */
    public Attribute getAttribute(String attribName) {
        org.jdom.Attribute tempAtt = element.getAttribute(attribName);
        return new Attribute(tempAtt.getName(),
            tempAtt.getValue());
    }

    /**
     * 得到配置项的一个属性值
     * @param attributeName
     * @return
     */
    public String getOrigAttributeValue(String attributeName) {
        return element.getAttribute(attributeName).getValue();
    }
    /**
     * 得到配置项的一个属性值,并进行宏替换
     * @param attributeName
     * @return
     */
    public String getAttributeValue(String attributeName) {
        MarcoDef marco = MarcoDef.getInstance();
        String value = element.getAttribute(attributeName).getValue();
        if (value == null) {
                   return "";
         }
        return marco.replaceByMarco(value);
    }

    /**
     * 得到配置项的属性列表
     * @return
     */
    public Attribute[] getAttributes() {
        List list = this.element.getAttributes();
        Attribute[] attriubutes = new Attribute[list.size()];
        Iterator itrts = list.iterator();
        int i = 0;
        while (itrts.hasNext()) {
            org.jdom.Attribute tempAattribute = (org.jdom.Attribute) itrts.next();
            Attribute myAttr =
                new Attribute(tempAattribute.
                getName(),
                tempAattribute.getValue());
            attriubutes[i] = myAttr;
            i++;
        }
        return attriubutes;
    }

    /**
     * For Testing use
     */
    public void showData() {
        try {
            org.jdom.output.XMLOutputter out = new org.jdom.output.XMLOutputter();
            out.setIndent("   ");
            out.setNewlines(true);
            out.output(this.element, System.out);
        }
        catch (IOException ex) {
        }
    }

    /**
     * 增加配置一个配置参数子项
     * @param name 标签名称
     * @param value 对应值
     */
    protected void addElement(String name, String value) {
        this.element.removeChild(name);
        Element ele = new Element(name);
        if (value.indexOf("&") != -1 || value.indexOf("<") != -1 || value.indexOf(">") != -1) {
            CDATA cdata = new CDATA(value);
            ele.addContent(cdata);
        }
        else {
            ele.setText(value);
        }
        this.element.addContent(ele);

    }

    /**
     * 更新配置项的xml内容
     * @param ele xml Element
     */
    public void setElement(Element ele) {
        this.element = ele;
    }

    /**
     * 返回本Item对应的JDOM Element
     * @return
     */
    protected Element getElement() {
        return this.element.detach();
    }

    public Element getItemElement()
    {
        return this.element;
    }

    public String toString() {
        String xml = null;
        try {
            java.io.StringWriter sw = new java.io.StringWriter(1000);
            org.jdom.output.XMLOutputter out = new org.jdom.output.XMLOutputter();
            out.setIndent("   ");
            //out.setNewlines(true);
            out.output(this.element, sw);
            xml = sw.toString();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return xml;

    }

}
