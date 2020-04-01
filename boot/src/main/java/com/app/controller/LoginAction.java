package com.app.controller;

import com.app.test.yaml.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:wuqi
 * @date:2019/3/8
 * @description:com.app.controller
 * @version:1.0
 */
@RestController
public class LoginAction {

    @Autowired
    private Person person;

    @RequestMapping("/")
    public void login(){

    }

    @RequestMapping("/hello")
    public Person name(){
        System.out.println(person);
        return person;
    }
}
