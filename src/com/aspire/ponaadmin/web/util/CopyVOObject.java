

package com.aspire.ponaadmin.web.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * <p>Title: 复制VO对象信息的公用工具类 </p>
 * <p>Description: 提供静态工具方法,复制类对象的属性名、属性值等相关信息 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: aspire</p>
 * @author 张敏
 * @version 1.0
 */

public class CopyVOObject
{

    // 常量，标识方法类型

	/**
	 * Automatically generated method: CopyVOObject
	 */
	private CopyVOObject () {
		
	}

    /**
     * 修饰符号：私有
     */
    private static final int MODIFIER_PRIVATE = 2;

    /**
     * 修饰符号：公有
     */
    private static final int MODIFIER_PUBLIC = 1;

    /**
     * 返回VO值对象的属性和属性值列表
     * 
     * @param obj Object，值对象的实例
     * @param newObj Object，新值对象的实例
     */
    public static void copyVO(Object obj, Object newObj)
    {

        Class objClass = obj.getClass();
        // 取得所有的类属性
        Field[] field = objClass.getDeclaredFields();
        for (int i = 0; i < field.length; i++)
        {
            // 将属性名和属性值对添加到字符串
            getFieldAndValue(field[i], obj, newObj);
        }
    }

    /**
     * 返回VO值对象某个属性的属性值
     *
     * @param field Field，对象的属性
     * @param obj Object，值对象的实例 
     * @param newObj Object，新值对象的实例
     */
    private static void getFieldAndValue(Field field, Object obj,
                                           Object newObj)

    {

        try
        {
            StringBuffer sb = new StringBuffer();
            // 取得属性名
            sb.append(field.getName()).append(":");

            // 取得读取属性值的get方法名称
            String strMethodName = getGetterName(field.getName());
            // 取得读取属性值的get方法名称
            String strSetName = getSetterName(field.getName());
            Object valueObj = null; // 保存执行get方法的返回值对象

            // 判定方法类型，相应作不同的处理
            int i = field.getModifiers();
            // 取得该属性对应的类型的名称
            String typeName = field.getType().getName();
            // 根据属性类型构造getMothed方法所需的Class数组参数
            Class[] classTypes = getTypes(typeName);
            if (i == MODIFIER_PRIVATE)
            {
                // private的属性，需要获取get方法的方法对象并执行get方法进行取值
                // 因为get方法没有参数，也不进行赋值，所以取方法对象和执行方法时相应的参数为null
                Method method = obj.getClass().getMethod(strMethodName, null);
                valueObj = method.invoke(obj, null);
                Method newMethod = newObj.getClass().getMethod(strSetName,
                                                               classTypes);
                newMethod.invoke(newObj, new Object[] {valueObj});
            }
            else if (i == MODIFIER_PUBLIC)
            {
                valueObj = field.get(obj); // public的属性可以直接取值
                //field.getName() = valueObj;
            }
            else
            {
                valueObj = null; // 不打印protected的属性值
            }

        }
        catch (Exception e)
        { // 出现异常时返回空值""
            // e.printStackTrace();
            e.printStackTrace();
        }
    }

    /**
     * 返回和类属性对应的get方法名称
     * 
     * @param AttrName String，属性名称
     * @return String 读取属性值的get方法名称
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
     * 返回和类属性对应的set方法名称
     *
     * @param AttrName 
     * @return String 读取属性值的get方法名称
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
     * 构造调用getMethod方法所需参数类型的Class数组
     *
     * @param type 
     * @return Class[] 属性类型所对应的Class数组
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
