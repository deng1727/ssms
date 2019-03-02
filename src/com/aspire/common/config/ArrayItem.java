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
// @CheckItem@ OPT-hecs-20040202 ���Ӻ궨�幦��
// @CheckItem@ OPT-hecs-20040212 �����޸�:������޸ĺ�,���������ļ��е�λ�ú�webҳ���е�λ�ñ��ֲ���
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
     * �����������ڴ����е�������ϵͳ�еõ�һ����ֵ������
     * @param element jdom��Element
     */
    public ArrayItem(Element element)
    {
        this.element = element;
    }

    /**
     * ������������һ���µĶ�ֵ�����
     * @param itemName ���������ơ�
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
     * ���ر�֮�б������������ֵ����
     * @return ArrayValue��������
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
     * ���ر�֮�б������������ֵ
     * @return String����
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
     * �޸ĵ�ǰ��ֵ�б�������
     * @param aItem ArrayItem Ŀ��ֵ
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
