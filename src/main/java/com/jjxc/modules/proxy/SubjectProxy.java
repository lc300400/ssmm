package com.jjxc.modules.proxy;

public class SubjectProxy implements Subject {

    RealSubject realSubject = new RealSubject();

    @Override
    public void doSomething() {
        realSubject.doSomething();
    }
}
