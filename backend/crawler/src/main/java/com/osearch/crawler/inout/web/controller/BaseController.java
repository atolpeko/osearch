package com.osearch.crawler.inout.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/")
@ApiIgnore
public class BaseController {

    @GetMapping
    public String swaggerRedirect() {
        return "redirect:/swagger-ui/index.html";
    }
}
