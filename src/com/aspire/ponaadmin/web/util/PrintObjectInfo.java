package com.aspire.ponaadmin.web.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <p>Title: ��ӡ������Ϣ�Ĺ��ù����� </p>
 * <p>Description: �ṩ��̬���߷���,��ӡ������������������ֵ�������Ϣ </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: aspire</p>
 * @author zhangrh
 * @version 1.0
 */
public class PrintObjectInfo{
    //��������ʶ��������

	/**
	 * ���췽��
	 */
	private PrintObjectInfo () {
		
	}

    /**
     * ���η���˽��
     */
    private static final int MODIFIER_PRIVATE = 2;

    /**
     * ���η�������
     */
    private static final int MODIFIER_PUBLIC = 1;

    /**
     * ����VOֵ��������Ժ�����ֵ�б�
     *
     * @return String ������������ֵ�б�
     * @param obj 
     */
    public static String printValueInfo(Object obj){
        Class objClass = obj.getClass();
        StringBuffer sb = new StringBuffer();
        //�������
        sb.append(objClass.getName()).append("{\r\n");
        //ȡ�����е�������
        Field[] field = objClass.getDeclaredFields();
        for(int i = 0; i < field.length; i++){
            //��������������ֵ����ӵ��ַ���
            sb.append(getFieldAndValue(field[i], obj));
        }
        sb.append("}End of ").append(objClass.getName());
        return sb.toString();
    }

    /**
     * ����VOֵ����ĳ�����Ե�����ֵ
     *
     * @return String ������:����ֵ
     * @param field
     * @param obj 
     */
    private static String getFieldAndValue(Field field, Object obj)

    {
        try{
            StringBuffer sb = new StringBuffer();
            //ȡ��������
            sb.append(field.getName()).append(":");

            //ȡ�ö�ȡ����ֵ��get��������
            String strMethodName = getGetterName(field.getName());

            Object valueObj = null; //����ִ��get�����ķ���ֵ����

            //�ж��������ͣ���Ӧ����ͬ�Ĵ���
            int i = field.getModifiers();
            if(i == MODIFIER_PRIVATE){
                //private�����ԣ���Ҫ��ȡget�����ķ�������ִ��get��������ȡֵ
                //��Ϊget����û�в�����Ҳ�����и�ֵ������ȡ���������ִ�з���ʱ��Ӧ�Ĳ���Ϊnull
                Method method = obj.getClass().getMethod(strMethodName, null);
                valueObj = method.invoke(obj, null);
            } else if(i == MODIFIER_PUBLIC){
                valueObj = field.get(obj);  //public�����Կ���ֱ��ȡֵ
            } else{
                return ""; //����ӡprotected������ֵ
            }

            //ȡ�ø����Զ�Ӧ�����͵�����
            String typeName = field.getType().getName();
            //��ִ��get�����õ��Ľ������String����ת��
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
            } else{ //���������Ϊ�������ࡢ�����ʱ�����ؿ�ֵ""
                //System.out.println("no such type!");
                return "";
            }

            sb.append("\r\n"); //ÿ����ֵ�Ժ�����ӻ��з�
            return sb.toString();
        } catch(Exception e){ //�����쳣ʱ���ؿ�ֵ""
            //e.printStackTrace();
            return "";
        }
    }

    /**
     * ���غ������Զ�Ӧ��get��������
     *
     * @return String ��ȡ����ֵ��get��������
     * @param AttrName 
     */
    private static String getGetterName(String AttrName){
        StringBuffer sb = new StringBuffer();
        sb.append("get").append(AttrName.substring(0, 1).toUpperCase()).append(
            AttrName.substring(1, AttrName.length()));
        return sb.toString();
    }
}
