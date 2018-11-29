package com.jjxc.modules.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * class 类的使用：获取类的属性、方法、构造方法、类的相关信息
 * @author lc
 */
public class ReflectTest_1 {

    public static void main(String[] args) throws ClassNotFoundException{
        //forName(String className)返回与带有给定字符串名的类或接口相关联的 Class 对象。
        Class clazz = Class.forName("com.jjxc.modules.security.controller.RoleController");
        //getDeclaredFields();返回 Field 对象的一个数组，这些对象反映此 Class
        //对象所表示的类或接口所声明的所有字段，包括公共、保护、默认（包）访问和私有字段，但不包括继承的字段。
        Field[] filed = clazz.getDeclaredFields();
        System.out.println("---------------------显示类的属性----------------------------");
        for(Field f:filed){
            //getName()返回此 Field 对象表示的字段的名称
            //getType()返回一个 Class 对象，它标识了此 Field 对象所表示字段的声明类型。
            System.out.println(f.getName());
            System.out.println(f.getType());
        }
        System.out.println("---------------------显示类的方法----------------------------");
        //getDeclaredMethods() 返回一个 Method 对象，该对象反映此 Class 对象所表示的类或接口的指定已声明方法。
        Method[] methods = clazz.getDeclaredMethods();
        for(Method m:methods){
            System.out.println(m.getName());
        }
        System.out.println("---------------------显示类的构造方法----------------------------");
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for(Constructor c:constructors){
            System.out.println(c.getName());
        }
        System.out.println("---------------------获取类的相关信息----------------------------");
        System.out.println("类所在的包：" + clazz.getClass().getPackage());
        System.out.println("类名：" + clazz.getName());
        System.out.println("父类名称：" + clazz.getSuperclass().getName());
    }
}
