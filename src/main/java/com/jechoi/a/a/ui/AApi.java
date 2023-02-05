package com.jechoi.a.a.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AApi {
    @GetMapping("/test")
    public String test(){
        return "success";
    }
}
