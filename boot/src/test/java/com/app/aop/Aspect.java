package com.app.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author:wuqi
 * @date:2020/3/2
 * @description:com.app.aop
 * @version:1.0
 */
@Component
@org.aspectj.lang.annotation.Aspect
public class Aspect {
    @Pointcut("execution(* xx())")
    public void pointCut(){

    }
    @Pointcut("this(Xxable)")
    public void pointCutThis(){

    }
    @Pointcut("target(Xxable)")
    public void pointCutTarget(){

    }
    @Pointcut("within(com.app.aop.*)")
    public void pointCutWithin(){

    }

    @Before("pointCutWithin()")
    public void beforeAdvice(){
        System.out.println("before advice");
    }

    @After("pointCutTarget()")
    public void afterAdvice(){
        System.out.println("after advice");
    }
}
