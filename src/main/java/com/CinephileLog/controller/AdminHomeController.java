package com.CinephileLog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")       // 관리자 홈 (localhost:8080/admin)
public class AdminHomeController {

    @GetMapping
    public String home() {
        return "admin/home";
    }
}