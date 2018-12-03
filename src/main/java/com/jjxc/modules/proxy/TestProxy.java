package com.jjxc.modules.proxy;


public class TestProxy {
    public static void main(String[] args) {
        //1、静态代理测试
//        Subject subject = new SubjectProxy();
//        subject.doSomething();
//        System.out.println(subject.getClass().getName());
        //2、动态代理测试
        ProxyHandler proxy = new ProxyHandler();
        //绑定该类实现的所有接口
        Subject subject = (Subject) proxy.bind(new RealSubject());
        subject.doSomething();
        System.out.println(subject.getClass().getName());
    }
}
