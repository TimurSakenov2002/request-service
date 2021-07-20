package com.ustudy.requestservice.controllers;

import com.ustudy.requestservice.components.StringToDate;
import com.ustudy.requestservice.dto.SessionRequestDto;
import com.ustudy.requestservice.models.Auditory;
import com.ustudy.requestservice.models.RequestResult;
import com.ustudy.requestservice.models.SessionRequest;
import com.ustudy.requestservice.models.SessionType;
import com.ustudy.requestservice.repositories.SessionRequestRepository;
import com.ustudy.requestservice.repositories.SessionTypeRepository;
import com.ustudy.requestservice.repositories.UserRepository;
import com.ustudy.requestservice.services.AuditoryService;
import com.ustudy.requestservice.services.UsersDetailService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auditory")
@AllArgsConstructor
public class AuditoryController {
    private final AuditoryService auditoryService;
    private final SessionRequestRepository sessionRequestRepository;
    private final SessionTypeRepository sessionTypeRepository;
    private final UserRepository userRepository;


    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public String getAuditory(Model model, @PathVariable(name = "id") Long id) {
        Auditory auditory = null;
        try {
            auditory = auditoryService.getById(id);
            List<SessionRequest> sessionRequests = sessionRequestRepository.findAllByAuditory(auditory);
//            Date today = new Date();
//            sessionRequests = sessionRequests.stream().filter(e -> e.getStartDate().getDay() == today.getDay()).collect(Collectors.toList());

            model.addAttribute("requests", sessionRequests);
            model.addAttribute("types", sessionTypeRepository.findAll());
            model.addAttribute("auditory", auditory);
            model.addAttribute("form", new SessionRequestDto());
        } catch (Exception e) {
            model.addAttribute("auditory", new Auditory());
        }
        return "exact-auditory-page";
    }

    @PostMapping("/{id}")
    @Secured("ROLE_USER")
    public String addRequest(Principal principal,
                             @PathVariable(name = "id") Long id,
                             @ModelAttribute SessionRequestDto sessionRequestDto) {

        sessionRequestDto.setStartDate(StringToDate.removeT(sessionRequestDto.getStartDate()));
        sessionRequestDto.setEndDate(StringToDate.removeT(sessionRequestDto.getEndDate()));
        Auditory auditory;
        try {
            auditory = auditoryService.getById(id);
            SessionRequest sessionRequest = new SessionRequest(sessionRequestDto);

            sessionRequest.setAuditory(auditory);
            sessionRequest.setSessionType(sessionTypeRepository.getByName(sessionRequestDto.getSessionType()));
            sessionRequest.setUser(userRepository.getByUserDetailEmail(principal.getName()));

            sessionRequest.setDepartment(auditory.getDepartment());

            RequestResult requestResult=new RequestResult();
            requestResult.setSessionRequest(sessionRequest);
            sessionRequest.setRequestResult(requestResult);

            sessionRequestRepository.save(sessionRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/auditory/"+id;
    }


}
