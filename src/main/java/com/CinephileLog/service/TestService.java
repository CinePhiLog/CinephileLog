package com.CinephileLog.service;

import com.CinephileLog.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {

    private final TestMapper testMapper;

    public int test(){
        return testMapper.test();
    }
}
