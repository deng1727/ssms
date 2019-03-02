package com.aspire.common.config;

/**
 * <p>Title: Poralt OAM</p>
 * <p>Description: This class contains all ArrayConfigItem attribute and
 *    all the operations.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author He Chengshou
 * @version 1.0
 */
// @CheckItem@ OPT-hecs-20040202 增加宏定义功能
// @CheckItem@ OPT-hecs-20040212 功能修改:配置项被修改后,它在配置文件中的位置和web页面中的位置保持不变
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

public class ArrayItem
    extends AbstractItem
    implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;

    public ArrayItem()
    {
        this.element = new Element("ArrayConfigItem");
    }

    /**
     * 构造器：用于从现有的配置子系统中得到一个多值配置项
     * @param element jdom的Element
     */
    public ArrayItem(Element element)
    {
        this.element = element;
    }

    /**
     * 构造器：构造一个新的多值配置项。
     * @param itemName 配置项名称。
     */
    public ArrayItem(String name)
    {
        this.element = new Element("ArrayConfigItem");
        this.element.addContent(new Element("Name"));
        this.element.getChild("Name").setText(name);
    }

    public void addArrayValue(ArrayValue av)
    {
        this.element.addContent(av.getElement());
    }

    /**
     * 返回本之列表配置项的所有值对象
     * @return ArrayValue对象数组
     */
    public ArrayValue[] getArrayValues()
    {
        List list = this.element.getChildren("ArrayValue");
        ArrayValue[] avs = new ArrayValue[list.size()];
        Iterator itrts = list.iterator();
        int i = 0;
        while (itrts.hasNext()) {
            Element ele = (Element) itrts.next();
            ArrayValue av = new ArrayValue(ele);
            avs[i] = av;
            i++;
        }
        return avs;
    }

    /**
     * 返回本之列表配置项的所有值
     * @return String数组
     */
    public String[] getValueList()
    {
        List list = this.element.getChildren("ArrayValue");
        String[] avs = new String[list.size()];
        Iterator itrts = list.iterator();
        int i = 0;
        while (itrts.hasNext()) {
            Element ele = (Element) itrts.next();
            String s = new ArrayValue(ele).getValue();
            avs[i] = s;
            i++;
        }
        return avs;
    }

    /**
     * 修改当前的值列表配置项
     * @param aItem ArrayItem 目标值
     */
    public void modify(ArrayItem aItem)
    {
        this.setDescription(aItem.getDescription());
        //set attributes
        Attribute[] atts = aItem.getAttributes();
        if (atts != null && atts.length > 0) {
            for (int i = 0; i < atts.length; i++) {
                this.addAttribute(atts[i].getName(), atts[i].getOrigValue());
            }
        }
        //Remove all old array values
        this.element.removeChildren("ArrayValue");
        //Add new array values
        ArrayValue[] arrValues = aItem.getArrayValues();
        ArrayValue tmpValue = null;
        if (arrValues != null && arrValues.length > 0) {
            for (int i = 0; i < arrValues.length; i++) {
                tmpValue = new ArrayValue();
                tmpValue.setId(arrValues[i].getId());
                tmpValue.setValue(arrValues[i].getOrigValue());
                if (arrValues[i].getOrigReserved() != null) {
                    tmpValue.setReserved(arrValues[i].getOrigReserved());
                }

                try {
                    this.addArrayValue(tmpValue);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
