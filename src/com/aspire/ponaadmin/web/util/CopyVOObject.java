

package com.aspire.ponaadmin.web.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * <p>Title: ����VO������Ϣ�Ĺ��ù����� </p>
 * <p>Description: �ṩ��̬���߷���,����������������������ֵ�������Ϣ </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: aspire</p>
 * @author ����
 * @version 1.0
 */

public class CopyVOObject
{

    // ��������ʶ��������

	/**
	 * Automatically generated method: CopyVOObject
	 */
	private CopyVOObject () {
		
	}

    /**
     * ���η��ţ�˽��
     */
    private static final int MODIFIER_PRIVATE = 2;

    /**
     * ���η��ţ�����
     */
    private static final int MODIFIER_PUBLIC = 1;

    /**
     * ����VOֵ��������Ժ�����ֵ�б�
     * 
     * @param obj Object��ֵ�����ʵ��
     * @param newObj Object����ֵ�����ʵ��
     */
    public static void copyVO(Object obj, Object newObj)
    {

        Class objClass = obj.getClass();
        // ȡ�����е�������
        Field[] field = objClass.getDeclaredFields();
        for (int i = 0; i < field.length; i++)
        {
            // ��������������ֵ����ӵ��ַ���
            getFieldAndValue(field[i], obj, newObj);
        }
    }

    /**
     * ����VOֵ����ĳ�����Ե�����ֵ
     *
     * @param field Field�����������
     * @param obj Object��ֵ�����ʵ�� 
     * @param newObj Object����ֵ�����ʵ��
     */
    private static void getFieldAndValue(Field field, Object obj,
                                           Object newObj)

    {

        try
        {
            StringBuffer sb = new StringBuffer();
            // ȡ��������
            sb.append(field.getName()).append(":");

            // ȡ�ö�ȡ����ֵ��get��������
            String strMethodName = getGetterName(field.getName());
            // ȡ�ö�ȡ����ֵ��get��������
            String strSetName = getSetterName(field.getName());
            Object valueObj = null; // ����ִ��get�����ķ���ֵ����

            // �ж��������ͣ���Ӧ����ͬ�Ĵ���
            int i = field.getModifiers();
            // ȡ�ø����Զ�Ӧ�����͵�����
            String typeName = field.getType().getName();
            // �����������͹���getMothed���������Class�������
            Class[] classTypes = getTypes(typeName);
            if (i == MODIFIER_PRIVATE)
            {
                // private�����ԣ���Ҫ��ȡget�����ķ�������ִ��get��������ȡֵ
                // ��Ϊget����û�в�����Ҳ�����и�ֵ������ȡ���������ִ�з���ʱ��Ӧ�Ĳ���Ϊnull
                Method method = obj.getClass().getMethod(strMethodName, null);
                valueObj = method.invoke(obj, null);
                Method newMethod = newObj.getClass().getMethod(strSetName,
                                                               classTypes);
                newMethod.invoke(newObj, new Object[] {valueObj});
            }
            else if (i == MODIFIER_PUBLIC)
            {
                valueObj = field.get(obj); // public�����Կ���ֱ��ȡֵ
                //field.getName() = valueObj;
            }
            else
            {
                valueObj = null; // ����ӡprotected������ֵ
            }

        }
        catch (Exception e)
        { // �����쳣ʱ���ؿ�ֵ""
            // e.printStackTrace();
            e.printStackTrace();
        }
    }

    /**
     * ���غ������Զ�Ӧ��get��������
     * 
     * @param AttrName String����������
     * @return String ��ȡ����ֵ��get��������
     */
    private static String getGetterName(String AttrName)
    {

        StringBuffer sb = new StringBuffer();
        sb.append("get")
          .append(AttrName.substring(0, 1).toUpperCase())
          .append(AttrName.substring(1, AttrName.length()));
        return sb.toString();
    }

    /**
     * ���غ������Զ�Ӧ��set��������
     *
     * @param AttrName 
     * @return String ��ȡ����ֵ��get��������
     */
    private static String getSetterName(String AttrName)
    {

        StringBuffer sb = new StringBuffer();
        sb.append("set")
          .append(AttrName.substring(0, 1).toUpperCase())
          .append(AttrName.substring(1, AttrName.length()));
        return sb.toString();
    }

    /**
     * �������getMethod��������������͵�Class����
     *
     * @param type 
     * @return Class[] ������������Ӧ��Class����
     */
    public static Class[] getTypes(String type)
    {

        if (type.equals("java.lang.String"))
        {
            return new Class[] { String.class };
        }
        else if (type.equals("int"))
        {
            return new Class[] { Integer.TYPE };
        }
        else if (type.equals("long"))
        {
            return new Class[] { Long.TYPE };
        }
        else if (type.equals("float"))
        {
            return new Class[] { Float.TYPE };
        }
        else if (type.equals("boolean"))
        {
            return new Class[] { Boolean.TYPE };
        }
        else if (type.equals("java.util.Date"))
        {
            return new Class[] { Date.class };
        }        
        else
        {
            return null;
        }

    }

//    /**
//     * @param args
//     */
//    public static void main(String[] args)
//    {
//
//        NodeVO oldVO = new NodeVO();
//        oldVO.setName("dfsdf");
//        oldVO.setSubmitDate(new Date(0));
//        oldVO.setCategory(50);
//        NodeVO newVO = new NodeVO();
//        printValueInfo(oldVO, newVO);
//        newVO.setCategory(60);
//        System.out.println(newVO.getName()+newVO.getSubmitDate()+newVO.getCategory());
//
//
//    }

}
