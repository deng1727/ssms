package com.aspire.common.config;

/**
 * <p>Title: Poralt OAM</p>
 * <p>Description: Portal OAM Program file</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author He Chengshou
 * @version 1.0
 */
// @CheckItem@ OPT-hecs-20040202 增加宏定义功能
// @CheckItem@ OPT-hecs-20040212 功能修改:配置项被修改后,它在配置文件中的位置和web页面中的位置保持不变
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
     * 构造器：用于从现有的配置子系统中得到一个配置项
     * @param element jdom的Element
     */
    public Item(org.jdom.Element element)
    {
        this.element = element;
    }

    /**
     * 构造器：构造一个新的配置项。
     * @param itemName 配置项名称。
     */
    public Item(String itemName)
    {
        this.element = new Element("ConfigItem");
        this.addElement("Name", itemName);
    }

    /**
     * 获取配置项的值，原始值
     * @return 配置项值
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
     * 获取配置项的值,进行宏替换后
     * @return 配置项值
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
     * 设置配置项的值，如果没有此标签，便增加一个。
     * @param value
     */
    public void setValue(String value)
    {
            this.addElement("Value", value);
    }

    /**
     * 得到配置项的序号
     * @return 序号
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
     * 设置配置项序号，如果没有此标签，便增加一个。
     * @param value
     */
    public void setId(String id)
    {
        this.addElement("Id", id);
    }

    /**
     * 得到本配置项的备用值。
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
     * 得到本配置项的备用值。宏替换后
     * @return 宏替换后的保留值
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
     * 设置配置项备用值，如果没有此标签，便增加一个。
     * @param value
     */
    public void setReserved(String value)
    {
        this.addElement("Reserved", value);
    }

    /**
     * 修改配置项的一个属性值
     * @param attributeName
     * @param value
     */
    public void setAttributeValue(String attributeName, String value)
    {
        this.element.setAttribute(attributeName,value);
    }

    /**
     * 修改当前的配置向内容
     * @param item Item 输入的新数据
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
