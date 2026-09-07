package com.totoo.web.controller.functions;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class test {
    @RequestMapping("/one")
    public String test1(){
        return "hello";
    }
}
