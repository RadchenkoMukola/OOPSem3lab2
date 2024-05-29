package com.example.oopsem2labjavae2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@ControllerAdvice
public class PagesController {
    private final ApplicationContext context;

    @Autowired
    public PagesController(ApplicationContext context) {
        this.context = context;
    }

    @GetMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        return "index";
    }

    @GetMapping("/book-management")
    public String book_management(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        return "book-management";
    }

    @GetMapping("/logout")
    public void logout(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        model.addAttribute("request", request);
        context.getBean(com.example.oopsem2labjavae2.beans.Controller.class).logout(request);
        response.sendRedirect("/index");
    }
}
