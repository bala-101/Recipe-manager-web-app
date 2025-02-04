package com.example.recipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.recipe.service.AuthService;

import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        Model model,
        HttpServletResponse response
    ) {
        if (authService.validateUser(username, password)) {
            return "redirect:http://localhost:3000";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}
