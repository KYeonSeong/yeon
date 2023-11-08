package com.gallery.gallery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class YourController {
    // 핸들러 메서드 추가
    @GetMapping("/your-url-path")
    public String yourGetMethod() {
        // 처리 로직 추가
        return "your-view-name"; // 뷰 이름 반환
    }

    
}
