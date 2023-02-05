package com.jechoi.a.a.ui;

import com.jechoi.a.a.app.ElementInquire;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AApi {
    private final ElementInquire inquire;
    @GetMapping("/test")
    public String test(){
        return "success";
    }

    @GetMapping("/data")
    public Map<String, Object> getData(){
        return Map.of("result", this.inquire.run());
    }
}
