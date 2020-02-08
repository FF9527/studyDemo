package com.app;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@RestController
@EnableAutoConfiguration
public class App 
{
    @RequestMapping("/")
    public String name(){
        return "Hello World!";
    }
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
//        SpringApplication app = new SpringApplication(App.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);
//         构建器模式SpringApplicationBuilder
    }
}
