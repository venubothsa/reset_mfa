package com.credit.one.controllers;

import com.credit.one.model.ResetMFA;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/reset_mfa")
    public String showResetMfaForm(Model model) {
        model.addAttribute("resetMfa", new ResetMFA());
        return "reset_mfa";
    }

    @GetMapping("/process_reset_mfa")
    public String processResetFrom(ResetMFA resetMFA) {
        return "reset_mfa";
    }
}
