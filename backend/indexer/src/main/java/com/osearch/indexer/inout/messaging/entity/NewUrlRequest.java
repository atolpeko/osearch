package com.osearch.indexer.inout.messaging.entity;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewUrlRequest {

    @NotBlank
    private String url;

    @NotBlank
    private String content;

    @NotNull
    private Long loadTime;

    private List<String> nestedUrls;
}
