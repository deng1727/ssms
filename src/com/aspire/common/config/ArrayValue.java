package com.aspire.common.config;

/**
 * <p>Title: Poralt OAM</p>
 * <p>Description: ��ֵ��������һ��ֵ����</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author He Chengshou
 * @version 1.0
 */
// @CheckItem@ OPT-hecs-20040202 ���Ӻ궨�幦��
import org.jdom.Element;
import org.jdom.*;

public class ArrayValue
    implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    private Element arrayElement = null;

    /**
     * �����������ڴ����е�������ϵͳ�еõ�һ����ֵ������ֵ
     * @param element jdom��Element
     */
    public ArrayValue(Element element)
    {
        this.arrayElement = element;
    }

    /**
     * ������������һ����ֵ������ֵ
     * @param element jdom��Element
     */
    public ArrayValue()
    {
        this.arrayElement = new Element("ArrayValue");
    }

    public String getId()
    {
        return this.arrayElement.getChildText("Id");
    }

    public void setId(String id)
    {
        setValueItem("Id", id);
    }

    /**
     * ȡ�ö�ֵ������ֵ��
     * @return ���滻ǰ��ֵ
     */
    public String getOrigValue()
    {
        return this.arrayElement.getChildText("Value");
    }

    /**
     * ȡ�ö�ֵ������ֵ�����к��滻
     * @return ���滻���ֵ
     */
    public String getValue()
    {
        MarcoDef marco = MarcoDef.getInstance();
        String value = this.arrayElement.getChildText("Value");
        return marco.replaceByMarco(value);
    }

    public void setValue(String value)
    {
        this.setValueItem("Value", value);
    }

    /**
     * ���غ��滻ǰ�ı���ֵ
     * @return
     */
    public String getOrigReserved()
    {
        return this.arrayElement.getChildText("Reserved");
    }

    /**
     * ���غ��滻��ı���ֵ
     * @return
     */
    public String getReserved()
    {
        MarcoDef marco = MarcoDef.getInstance();
        String value = this.arrayElement.getChildText("Reserved");
        return marco.replaceByMarco(value);
    }

    public void setReserved(String resv)
    {
        this.setValueItem("Reserved", resv);
    }

    /**
     * ���ر�ArrayItem��Ӧ��JDOM Element
     * @return
     */
    protected Element getElement()
    {
        return this.arrayElement;
    }

    /**
     * ͨ�÷������޸Ļ򴴽�һ��xmlԪ��
     * @param name ��ǩ����
     * @param value ��ǩ��ֵ
     */
    private void setValueItem(String name, String value)
    {
        arrayElement.removeChild(name);
        Element element = new Element(name);
        if (value.indexOf("&") != -1 || value.indexOf("<") != -1 || value.indexOf(">") != -1) {
            CDATA cdata = new CDATA(value);
            element.addContent(cdata);
        }
        else {
            element.setText(value);
        }
        this.arrayElement.addContent(element);

    }
}
