package com.app.aop;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

/**
 * @author:wuqi
 * @date:2020/3/2
 * @description:com.app.aop
 * @version:1.0
 */
@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class Main {

    public static void main(String[] args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.app.aop");
        context.refresh();
        Xxable a = (Xxable)context.getBean("a");
        B b = (B) context.getBean("b");
        a.xx();
        b.xx();
        System.out.println(a.getClass().getName());
        System.out.println(b.getClass().getName());
    }
}
