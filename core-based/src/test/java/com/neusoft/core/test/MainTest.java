package com.neusoft.core.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext c = new ClassPathXmlApplicationContext("META-INF/spring/*.xml");
        // Ih p = LazyAutowiredProxy.getProxy(Ih.class, "abc");
        // p.say();
        // System.out.println(p.say2());

    }
}
