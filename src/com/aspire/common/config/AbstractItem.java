package com.aspire.common.config;

/**
 * <p>Title: Poralt OAM</p>
 * <p>Description:Item,��ArrayItem��Ĺ�������</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author He Chengshou
 * @version 1.0
 */
// @CheckItem@ OPT-hecs-20040202 ���Ӻ궨�幦��
// @CheckItem@ OPT-penglq-20050301 ��������element�ķ���
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
     * �õ��������������
     * @return �����������ִ�
     */
    public String getDescription() {
        String desc = this.element.getChild("Description").getText();
        if (desc == null) {
            desc = " ";
        }
        return desc;
    }

    /**
     * �������������������û�д˱�ǩ��������һ����
     * @param value
     */
    public void setDescription(String desc) {
        this.addElement("Description", desc);
    }

    /**
     * ����һ������
     * @param name ��������
     * @param value ����ֵ
     */
    public void addAttribute(String name, String value) {
        this.element.setAttribute(name, value);
    }

    /**
     * ɾ��һ������
     * @param attribute ��������
     */
    public void removeAttribute(String attName) {
        this.element.removeAttribute(attName);
    }

    /**
     * �õ�����������
     * @return
     */
    public String getName() {
        return element.getChild("Name").getText();
    }

    /**
     * �����������ƣ����û�д˱�ǩ��������һ����
     * @param itemName
     */
    public void setName(String itemName) {
        this.addElement("Name", itemName);
    }

    /**
     * �õ��������һ�������б�
     * @param attribName
     * @return
     */
    public Attribute getAttribute(String attribName) {
        org.jdom.Attribute tempAtt = element.getAttribute(attribName);
        return new Attribute(tempAtt.getName(),
            tempAtt.getValue());
    }

    /**
     * �õ��������һ������ֵ
     * @param attributeName
     * @return
     */
    public String getOrigAttributeValue(String attributeName) {
        return element.getAttribute(attributeName).getValue();
    }
    /**
     * �õ��������һ������ֵ,�����к��滻
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
     * �õ�������������б�
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
     * ��������һ�����ò�������
     * @param name ��ǩ����
     * @param value ��Ӧֵ
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
     * �����������xml����
     * @param ele xml Element
     */
    public void setElement(Element ele) {
        this.element = ele;
    }

    /**
     * ���ر�Item��Ӧ��JDOM Element
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
