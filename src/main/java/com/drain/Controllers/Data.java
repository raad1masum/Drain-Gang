package com.drain.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Data {
    @GetMapping("/data")
    public String data() {
        return "data";
    }
}