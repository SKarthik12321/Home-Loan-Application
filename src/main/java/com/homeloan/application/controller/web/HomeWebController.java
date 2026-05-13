package com.homeloan.application.controller.web;

import com.homeloan.application.service.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Thymeleaf MVC controller for lightweight HTML views (no JWT; read-only listing for demos).
 */
@Controller
@RequiredArgsConstructor
public class HomeWebController {

    private final LoanApplicationService loanApplicationService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/ui/applications")
    public String applications(Model model) {
        model.addAttribute("loans", loanApplicationService.findAll());
        return "applications";
    }
}
