package com.jjxc.modules.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyHandler implements InvocationHandler {

    private Object tar;

    //绑定委托对象并返回代理类
    public Object bind(Object tar){
        this.tar = tar;
        return Proxy.newProxyInstance(
                tar.getClass().getClassLoader(),
                tar.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        //这里就可以进行所谓的AOP编程了
        //在调用具体函数方法前，执行功能处理
        System.out.println("函数方法执行前~~~~~~~~~~~");
        System.out.println("method:"+method);
        result = method.invoke(tar,args);
        System.out.println("函数方法执行后-----------");
        //在调用具体函数方法后，执行功能处理
        return result;
    }
}
