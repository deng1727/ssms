package com.aspire.common.config;

/**
 * <p>Title: Poralt OAM</p>
 * <p>Description: Portal OAM Program file</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author He Chengshou
 * @version 1.0
 */
// @CheckItem@ OPT-hecs-20040202 ���Ӻ궨�幦��
// @CheckItem@ OPT-hecs-20040212 �����޸�:������޸ĺ�,���������ļ��е�λ�ú�webҳ���е�λ�ñ��ֲ���
import org.jdom.Element;

public class Item extends AbstractItem implements java.io.Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Item()
    {
        this.element = new Element("ConfigItem");
    }

    /**
     * �����������ڴ����е�������ϵͳ�еõ�һ��������
     * @param element jdom��Element
     */
    public Item(org.jdom.Element element)
    {
        this.element = element;
    }

    /**
     * ������������һ���µ������
     * @param itemName ���������ơ�
     */
    public Item(String itemName)
    {
        this.element = new Element("ConfigItem");
        this.addElement("Name", itemName);
    }

    /**
     * ��ȡ�������ֵ��ԭʼֵ
     * @return ������ֵ
     */
    public String getOrigValue()
    {
        String value = element.getChild("Value").getText();
        if (value == null) {
               return "";
         }
        return value;
    }
    /**
     * ��ȡ�������ֵ,���к��滻��
     * @return ������ֵ
     */
    public String getValue()
    {
        MarcoDef marco = MarcoDef.getInstance();
        String value = element.getChild("Value").getText();
        if (value == null) {
                   return "";
         }
        return marco.replaceByMarco(value);
    }

    /**
     * �����������ֵ�����û�д˱�ǩ��������һ����
     * @param value
     */
    public void setValue(String value)
    {
            this.addElement("Value", value);
    }

    /**
     * �õ�����������
     * @return ���
     */
    public String getId()
    {
        String id = element.getChild("Id").getText();
        if (id == null) {
            return "";
        }
        return id;
    }

    /**
     * ������������ţ����û�д˱�ǩ��������һ����
     * @param value
     */
    public void setId(String id)
    {
        this.addElement("Id", id);
    }

    /**
     * �õ���������ı���ֵ��
     * @return
     */
    public String getOrigReserved()
    {
        String orireserved = this.element.getChild("Reserved").getText();
        if (orireserved == null) {
                return "";
         }
        return orireserved;
    }
    /**
     * �õ���������ı���ֵ�����滻��
     * @return ���滻��ı���ֵ
     */
    public String getReserved()
    {
        MarcoDef marco = MarcoDef.getInstance();
        String value = this.element.getChild("Reserved").getText();
        if (value == null) {
               return "";
         }
        return marco.replaceByMarco(value);
    }

    /**
     * �����������ֵ�����û�д˱�ǩ��������һ����
     * @param value
     */
    public void setReserved(String value)
    {
        this.addElement("Reserved", value);
    }

    /**
     * �޸��������һ������ֵ
     * @param attributeName
     * @param value
     */
    public void setAttributeValue(String attributeName, String value)
    {
        this.element.setAttribute(attributeName,value);
    }

    /**
     * �޸ĵ�ǰ������������
     * @param item Item �����������
     */
    public void modify(Item item)
    {
       this.setId(item.getId() ) ;
       this.setDescription(item.getDescription() ) ;
       this.setValue(item.getOrigValue() ) ;
       this.setReserved(item.getOrigReserved() ) ;
       Attribute[] atts = item.getAttributes();
       if(atts != null && atts.length >0){
           for(int i=0;i<atts.length ;i++){
               this.setAttributeValue(atts[i].getName(),atts[i].getOrigValue()) ;
           }
       }
    }
}
