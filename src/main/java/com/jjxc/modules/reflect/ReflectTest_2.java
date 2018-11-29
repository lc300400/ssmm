package com.jjxc.modules.reflect;

import java.lang.reflect.Field;

/**
 * 使用反射动态修改查询属性值
 * @author lc
 */
public class ReflectTest_2 {


    public static void main(String[] args) throws Exception {
        Class clazz = Class.forName("com.jjxc.modules.security.entity.User");
        Object obj = clazz.newInstance();
        //获得 User 类中的指定属性对应的Field对象（每个属性对应一个Field对象）
        Field field = clazz.getDeclaredField("username");
        //取消属性的访问权限控制，即使private 属性也可以进行访问
        field.setAccessible(true);
        //调用getter方法获取属性值
        System.out.println(field);
        System.out.println(field.get(obj));
        //调用setter方法给属性赋值
        field.set(obj,"hhh");
        //获取属性修改后的值
        System.out.println(field.get(obj));
    }

}
