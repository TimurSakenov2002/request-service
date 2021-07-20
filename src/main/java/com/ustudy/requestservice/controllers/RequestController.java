package com.ustudy.requestservice.controllers;

import com.ustudy.requestservice.models.RequestResult;
import com.ustudy.requestservice.models.SessionRequest;
import com.ustudy.requestservice.services.RequestResultService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/requests")
@Secured("ROLE_USER")
@AllArgsConstructor
public class RequestController {
    private final RequestResultService resultService;

    @GetMapping("/my")
    public String getMyRequests(Principal principal, Model model) {
        List<RequestResult> myRequestResults = resultService.getMyRequestResults(principal.getName());
        List<SessionRequest> sessionRequests = new ArrayList<>();
        myRequestResults.forEach(e -> sessionRequests.add(e.getSessionRequest()));
        model.addAttribute("requests", sessionRequests);
        return "my-requests-page";
    }

    @PostMapping("/{id}")
    public String removeRequest(Principal principal, @PathVariable(name = "id") RequestResult requestResult) {
        String username = requestResult.getSessionRequest().getUser().getUserDetail().getEmail();
        if (username.equals(principal.getName())) {
            resultService.delete(requestResult);
            return "redirect:/requests/my";
        } else {
            return "redirect:/error-405";
        }
    }
}
