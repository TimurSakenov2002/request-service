package com.ustudy.requestservice.controllers;

import com.ustudy.requestservice.models.RequestResult;
import com.ustudy.requestservice.models.Suggestion;
import com.ustudy.requestservice.services.RequestResultService;
import com.ustudy.requestservice.services.SuggestionService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/suggestion")
@Secured("ROLE_USER")
@AllArgsConstructor
public class SuggestionController {
    private final SuggestionService suggestionService;
    private final RequestResultService resultService;


    @GetMapping("/{id}")
    public String getSuggestion(Model model, Principal principal, @PathVariable(name = "id") Suggestion suggestion) {
        if (suggestion.getRequestResult().getSessionRequest().getUser().getUserDetail().getEmail().equals(principal.getName())) {
            model.addAttribute("suggestion", suggestion);
            return "exact-suggestion-page";
        } else {
            return "redirect:/error-404";
        }
    }

    @PostMapping("/accept/{id}")
    public String acceptSuggestion(Principal principal, @PathVariable(name = "id") Suggestion suggestion) {
        if (suggestion.getRequestResult().getSessionRequest().getUser().getUserDetail().getEmail().equals(principal.getName())) {
            suggestion.setAccepted(true);

            suggestion = suggestionService.save(suggestion);
            RequestResult requestResult = suggestion.getRequestResult();

            requestResult.setAccepted(true);
            requestResult.setIsSeen(true);

            requestResult.getSessionRequest().setStartDate(suggestion.getStartDate());
            requestResult.getSessionRequest().setEndDate(suggestion.getEndDate());

            requestResult.setSuggestion(null);

            resultService.save(requestResult);
            return "redirect:/requests/my";
        } else {
            return "redirect:/error-404";
        }
    }
    @PostMapping("/reject/{id}")
    public String rejectSuggestion(Principal principal, @PathVariable(name = "id") Suggestion suggestion) {
        if (suggestion.getRequestResult().getSessionRequest().getUser().getUserDetail().getEmail().equals(principal.getName())) {
            resultService.delete(suggestion.getRequestResult());
            return "redirect:/requests/my";
        } else {
            return "redirect:/error-404";
        }
    }
}
