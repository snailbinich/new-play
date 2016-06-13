package com.izhaoyan.playground.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhaoyan on 2016/6/12.
 */
@RestController
public class HelloWorld {


    @RequestMapping("/")
    public String sayHello(HttpServletRequest request){

//        String name = request.getQueryString()

        return "haha";
    }
}
