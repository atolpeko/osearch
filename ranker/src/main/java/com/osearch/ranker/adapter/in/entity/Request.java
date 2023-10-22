package com.osearch.ranker.adapter.in.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request class represents a request object coming from kafka.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private Long id;
}
