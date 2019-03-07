package com.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:wuqi
 * @date:2019/3/8
 * @description:com.app.controller
 * @version:1.0
 */
@RestController(value = "/login")
public class LoginAction {

    @RequestMapping("/")
    public void login(){

    }
}
