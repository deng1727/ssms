package com.aspire.ponaadmin.web.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <p>Title: 打印对象信息的公用工具类 </p>
 * <p>Description: 提供静态工具方法,打印类对象的属性名、属性值等相关信息 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: aspire</p>
 * @author zhangrh
 * @version 1.0
 */
public class PrintObjectInfo{
    //常量，标识方法类型

	/**
	 * 构造方法
	 */
	private PrintObjectInfo () {
		
	}

    /**
     * 修饰符：私有
     */
    private static final int MODIFIER_PRIVATE = 2;

    /**
     * 修饰符：公有
     */
    private static final int MODIFIER_PUBLIC = 1;

    /**
     * 返回VO值对象的属性和属性值列表
     *
     * @return String 属性名和属性值列表
     * @param obj 
     */
    public static String printValueInfo(Object obj){
        Class objClass = obj.getClass();
        StringBuffer sb = new StringBuffer();
        //输出类名
        sb.append(objClass.getName()).append("{\r\n");
        //取得所有的类属性
        Field[] field = objClass.getDeclaredFields();
        for(int i = 0; i < field.length; i++){
            //将属性名和属性值对添加到字符串
            sb.append(getFieldAndValue(field[i], obj));
        }
        sb.append("}End of ").append(objClass.getName());
        return sb.toString();
    }

    /**
     * 返回VO值对象某个属性的属性值
     *
     * @return String 属性名:属性值
     * @param field
     * @param obj 
     */
    private static String getFieldAndValue(Field field, Object obj)

    {
        try{
            StringBuffer sb = new StringBuffer();
            //取得属性名
            sb.append(field.getName()).append(":");

            //取得读取属性值的get方法名称
            String strMethodName = getGetterName(field.getName());

            Object valueObj = null; //保存执行get方法的返回值对象

            //判定方法类型，相应作不同的处理
            int i = field.getModifiers();
            if(i == MODIFIER_PRIVATE){
                //private的属性，需要获取get方法的方法对象并执行get方法进行取值
                //因为get方法没有参数，也不进行赋值，所以取方法对象和执行方法时相应的参数为null
                Method method = obj.getClass().getMethod(strMethodName, null);
                valueObj = method.invoke(obj, null);
            } else if(i == MODIFIER_PUBLIC){
                valueObj = field.get(obj);  //public的属性可以直接取值
            } else{
                return ""; //不打印protected的属性值
            }

            //取得该属性对应的类型的名称
            String typeName = field.getType().getName();
            //将执行get方法得到的结果进行String类型转换
            if(typeName.equals("java.lang.String")){
                sb.append((String) valueObj);
            } else if(typeName.equals("int")){
                sb.append(String.valueOf((Integer) valueObj));
            } else if(typeName.equals("long")){
                sb.append(String.valueOf((Long) valueObj));
            } else if(typeName.equals("float")){
                sb.append(String.valueOf((Float) valueObj));
            } else if(typeName.equals("boolean")){
                sb.append(String.valueOf((Boolean) valueObj));
            } else{ //如果类属性为其它的类、数组等时，返回空值""
                //System.out.println("no such type!");
                return "";
            }

            sb.append("\r\n"); //每个名值对后面添加换行符
            return sb.toString();
        } catch(Exception e){ //出现异常时返回空值""
            //e.printStackTrace();
            return "";
        }
    }

    /**
     * 返回和类属性对应的get方法名称
     *
     * @return String 读取属性值的get方法名称
     * @param AttrName 
     */
    private static String getGetterName(String AttrName){
        StringBuffer sb = new StringBuffer();
        sb.append("get").append(AttrName.substring(0, 1).toUpperCase()).append(
            AttrName.substring(1, AttrName.length()));
        return sb.toString();
    }
}
