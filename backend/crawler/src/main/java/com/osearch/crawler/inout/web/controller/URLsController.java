package com.osearch.crawler.inout.web.controller;

import com.osearch.crawler.inout.web.api.URLsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class URLsController implements URLsApi {

    @Override
    public Long count() {
        return 0L;
    }
}
