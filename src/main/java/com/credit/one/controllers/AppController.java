package com.credit.one.controllers;

import com.credit.one.model.*;
import com.credit.one.services.impl.ResetMFAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class AppController {
    @Autowired
    private ResetMFAService resetMFAService;


    @GetMapping("/reset_mfa")
    public String showResetMfaForm(Model model) {
        model.addAttribute("resetMfa", new ResetMFA());
        return "reset_mfa";
    }


    @PostMapping(path = "/process_reset_mfa", produces = MediaType.TEXT_PLAIN_VALUE)
    public String processResetFrom(ResetMFA resetMFA, Model model) {
        if ((Objects.isNull(resetMFA.getGlobalPin()) || resetMFA.getGlobalPin().isEmpty()) && (Objects.isNull(resetMFA.getSsn()) || resetMFA.getSsn().isEmpty())) {
            model.addAttribute("errorMessage", "Please enter SSN or Global Pin");
            return "error";
        }
        List<FactorType> factorTypes = resetMFAService.getResetFactors(resetMFA);
        model.addAttribute("factorTypes", factorTypes.toArray());
        if (factorTypes.size() > 0) {
            // setConstant(model, factorTypes);
            return "reset_factors";
        }
        //model.addAttribute("errorMessage", "No factors are found to reset...");

        return "enroll";
    }

    @PostMapping(path = "/process_reset_factors", produces = MediaType.TEXT_PLAIN_VALUE)
    public String processResetFactors(@RequestParam("factors") String[] factors, Model model) {
        String s = resetMFAService.resetFactor(factors);
        model.addAttribute("errorMessage", s);
        // redirectAttributes.addFlashAttribute("message", s);
        //modelAndView.addObject("message",s);
        return "error";
    }

    @PostMapping(path = "/process_enroll_mfa", produces = MediaType.TEXT_PLAIN_VALUE)
    public String processEnrollMfa(EnrollMfa enrollMfa, Model model) {
        String s = resetMFAService.enrollMfa(enrollMfa);
        model.addAttribute("errorMessage", s);
        return "error";
    }

    @PostMapping(path = "/process_enroll_email", produces = MediaType.TEXT_PLAIN_VALUE)
    public String processEnrollEmail(EnrollEmail enrollMfa, Model model) {
        String s = resetMFAService.enrollEmail(enrollMfa);
        model.addAttribute("errorMessage", s);
        return "error";
    }

    @PostMapping(path = "/process_enroll_sms", produces = MediaType.TEXT_PLAIN_VALUE)
    public String processEnrollSms(EnrollMobile enrollMfa, Model model) {
        String s = resetMFAService.enrollSms(enrollMfa);
        model.addAttribute("errorMessage", s);
        return "error";
    }

    @GetMapping("/enroll_sms")
    public String enrollSms(Model model) {
        model.addAttribute("enrollSms", new EnrollMobile());
        return "enroll_sms";
    }

    @GetMapping("/enroll_email")
    public String enrollEmail(Model model) {
        model.addAttribute("enrollEmail", new EnrollEmail());
        return "enroll_email";
    }

    @GetMapping("/enroll_all")
    public String enrollAll(Model model) {
        model.addAttribute("enrollMfa", new EnrollMfa());
        return "enroll_all";
    }

    @GetMapping("/enroll")
    public String enroll(Model model) {
        return "enroll";
    }

    private void setConstant(Model model, List<FactorType> factorTypes) {
        ;
        model.addAttribute("factorTypeLabels", factorTypes.stream().map(FactorType::getLabel).collect(Collectors.toList()));
    }
}