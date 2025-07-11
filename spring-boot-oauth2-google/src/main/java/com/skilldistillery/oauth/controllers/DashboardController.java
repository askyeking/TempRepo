package com.skilldistillery.oauth.controllers;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal OAuth2User principal, Model model) {
        // Extract user details from OAuth2User
        String username = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        Map<String, Object> attributes = principal.getAttributes();

        // Add user details to the model
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("attribute", attributes);

        // Return the dashboard view
        return "dashboard";
    }
}