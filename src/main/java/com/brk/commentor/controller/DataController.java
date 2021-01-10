package com.brk.commentor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DataController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
