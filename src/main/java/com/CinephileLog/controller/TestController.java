package com.CinephileLog.controller;

import com.CinephileLog.service.TestJpaService;
import com.CinephileLog.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final TestService testService;
    private final TestJpaService testJpaService;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("result", testService.test());
        model.addAttribute("jpaResult", testJpaService.getTestValue());
        return "index";
    }
}
