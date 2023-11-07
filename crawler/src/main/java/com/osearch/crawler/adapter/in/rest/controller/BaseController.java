package com.osearch.crawler.adapter.in.rest.controller;

import com.osearch.crawler.adapter.in.rest.properties.RestProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

/**
 * The BaseController class handles the base URL ("/") endpoints.
 * This class redirects the user to the Swagger UI when accessing the base URL.
 */
@Controller
@ApiIgnore
@RequestMapping("/")
@RequiredArgsConstructor
public class BaseController {
    private final RestProperties properties;

    @GetMapping
    public String swaggerRedirect() {
        return "redirect:" + properties.getSwaggerUrl();
    }
}
