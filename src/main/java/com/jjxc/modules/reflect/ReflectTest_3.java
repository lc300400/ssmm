package com.jjxc.modules.reflect;

import com.jjxc.modules.security.entity.User;

import java.lang.reflect.Method;

/**
 * 使用反射动态执行方法
 * @author lc
 */
public class ReflectTest_3 {


    public static void main(String[] args) throws Exception {
        Class<?> clazz = User.class;
        Object obj = clazz.newInstance();

        Method method = clazz.getMethod("setName",String.class);
        Object result = method.invoke(obj,"lc300400");
        System.out.println("返回值：" + result);

        Method method1 = clazz.getMethod("getName");
        Object result1 = method1.invoke(obj);
        System.out.println("返回值1：" + result1);
    }

}
