package com.yun.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description:    两个不同类属性Copy
 */
public class BeanCopyUtil {

    private static String getGetMethodName(String fieldName) {
        fieldName = replaceFirstCharToUpper(fieldName);
        return "get" + fieldName;
    }

    private static String getSetMethodName(String fieldName) {
        fieldName = replaceFirstCharToUpper(fieldName);
        return "set" + fieldName;
    }

    private static String replaceFirstCharToUpper(String fieldName) {
        char[] chars = new char[1];
        chars[0] = fieldName.charAt(0);
        String temp = new String(chars);
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            fieldName = fieldName.replaceFirst(temp, temp.toUpperCase());
        }
        return fieldName;
    }

    /**
     * @Author:         轻狂书生FS
     * @Description:    该方法接收两个参数，一个是复制的源对象——要复制的对象，一个是复制的目标对象——对象副本。
     *                  当然这个方法也可以在两个不同对象间使用，这时候只要目标对象和对象具有一个或多个相同类型及名称的属性，
     *                  那么就会把源对象的属性值赋给目标对象的属性
     * @params          SourceBean源对象，
     *                  TargetBean目标对象，
     * @return          TargetBean目标对象
     * @CreateDate:     2019/4/3 14:12
     * @Version:        1.0
     */
    public static <T> T getBean( T SourceBean,T TargetBean) throws Exception {
        if (TargetBean == null)
            return null;
        Field[] tFields = TargetBean.getClass().getDeclaredFields();
        Field[] sFields = SourceBean.getClass().getDeclaredFields();
        try {
            for (Field field : tFields) {
                String fieldName = field.getName();
                if (fieldName.equals("serialVersionUID"))
                    continue;
                if (field.getType() == Map.class)
                    continue;
                if (field.getType() == Set.class)
                    continue;
                if (field.getType() == List.class)
                    continue;
                for (Field sField : sFields) {
                    if (!sField.getName().equals(fieldName)) {
                        continue;
                    }
                    Class type = field.getType();
                    String setName = getSetMethodName(fieldName);
                    Method tMethod = TargetBean.getClass().getMethod(setName, new Class[] { type });
                    String getName = getGetMethodName(fieldName);
                    Method sMethod = SourceBean.getClass().getMethod(getName, null);
                    Object setterValue = sMethod.invoke(SourceBean, null);
                    tMethod.invoke(TargetBean, new Object[] { setterValue });
                }
            }
        } catch (Exception e) {
            throw new Exception("设置参数信息发生异常", e);
        }
        return TargetBean;
    }


    /**
     * @Author:         轻狂书生FS
     * @Description:    该方法接收三个参数，一个是复制的源对象——要复制的对象，一个是复制的目标对象——对象副本，一个是源对象与目标对象的属性映射关系
     *                  本方法是一层拷贝，对象属性不包括集合类型的情况
     *                  本方法有两种使用场景：
     *                  一种是两个对象属性名相同时，可用； 一种是属性名字不同是，通过映射关系，也可用。
     * @params          SourceBean源对象，
     *                  TargetBean目标对象，
     *                  map源对象与目标对象的属性映射关系，key：源对象属性值，value：目标对象属性值
     * @return          TargetBean目标对象
     * @CreateDate:     2019/4/3 14:37
     * @Version:        1.0
     */
    public static <T> T getCopyBean(T SourceBean,T TargetBean, Map<String,String> map) throws Exception {
        if (TargetBean == null)
            return null;
//        分别获取源对象和目标对象的属性
        Field[] tFields = TargetBean.getClass().getDeclaredFields();
        Field[] sFields = SourceBean.getClass().getDeclaredFields();

//        将目标对象的属性名字放到Set集合
        Set<String> tFieldNames = new HashSet<>();
        for (Field field:tFields) {
            tFieldNames.add(field.getName());
        }
        try {
//            遍历源对象属性
            for (Field field:sFields) {
                String fieldName = field.getName();
                if (fieldName.equals("serialVersionUID"))
                    continue;
                if (field.getType() == Map.class)
                    continue;
                if (field.getType() == List.class)
                    continue;
//                源对象的属性的名字是否在目标对象的属性值里面
                boolean isContains = tFieldNames.contains(fieldName);
                /**
                 * 如果存在则源对象的属性值直接赋给目标对象
                 * 如果不存在则通过映射关系将源对象的属性值赋给目标对象
                 */
                if (isContains) {
                    Class type = field.getType();
//                    获得目标对象属性的set方法
                    String setName = getSetMethodName(fieldName);
                    Method tMethod = TargetBean.getClass().getMethod(setName,new Class[]{type});
//                    获得源对象属性的get方法
                    String getName = getGetMethodName(fieldName);
                    Method sMethod = SourceBean.getClass().getMethod(getName,null);
//                    获得源对象的属性的属性值
                    Object setterValue = sMethod.invoke(SourceBean,null);
//                    将setterValue赋给目标对象
                    tMethod.invoke(TargetBean,new Object[]{setterValue});
                } else {
//                    判断map和map里面的key值是否为空
                    if ((null == map) || (null == map.get(fieldName)))
                        continue;
                    Class type = field.getType();
//                    通过映射关系map获得目标对象属性的set方法
                    String setName = getSetMethodName(map.get(fieldName));
                    Method tMethod = TargetBean.getClass().getMethod(setName,new Class[]{type});
//                    获得源对象属性的get方法
                    String getName = getGetMethodName(fieldName);
                    Method sMethod = SourceBean.getClass().getMethod(getName,null);
                    Object setterValue = sMethod.invoke(SourceBean,null);
                    tMethod.invoke(TargetBean,new Object[]{setterValue});
                }
            }
        } catch (Exception e) {
            throw new Exception("设置参数信息发生异常", e);
        }
        return TargetBean;
    }
}