package com.test.service;

import org.springframework.stereotype.Service;

@Service
public class TestJpaService {
    public String getTestValue() {
        return "Hello JPA";
    }
}
