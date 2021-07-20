package com.ustudy.requestservice.controllers;

import com.ustudy.requestservice.components.StringToDate;
import com.ustudy.requestservice.dto.RequestResultsWithConflicts;
import com.ustudy.requestservice.dto.SuggestionDto;
import com.ustudy.requestservice.dto.TimeDurationDto;
import com.ustudy.requestservice.models.*;
import com.ustudy.requestservice.repositories.AuditoryRepository;
import com.ustudy.requestservice.repositories.SessionRequestRepository;
import com.ustudy.requestservice.services.AuditoryService;
import com.ustudy.requestservice.services.RequestResultService;
import com.ustudy.requestservice.services.SuggestionService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
@AllArgsConstructor
public class AdminController {
    private final AuditoryRepository auditoryRepository;
    private final SessionRequestRepository requestRepository;
    private final RequestResultService requestResultService;
    private final AuditoryService auditoryService;

    @GetMapping
    public String getAdminPage(
            HttpServletRequest httpServletRequest,
            Model model) {
        Long province_id = (Long) httpServletRequest.getAttribute("province_id");
        return "redirect:/admin/province/" + province_id;
    }


    @GetMapping("/province/{id}")
    public String getAdminAuditory(@PathVariable(name = "id") Province province,
                                   HttpServletRequest httpServletRequest,
                                   Model model) {
        Long province_id = (Long) httpServletRequest.getAttribute("province_id");
        if (province.getId().equals(province_id)) {
            List<Auditory> auditories = auditoryService.getAlLByProvince(province);
            List<Long> auditoryReqs = new ArrayList<>();
            auditories.forEach(e -> auditoryReqs.add(requestRepository.countAllByAuditory(e)));

            model.addAttribute("date", StringToDate.dateToString(new Date()));
            model.addAttribute("auditoryList", auditories);
            model.addAttribute("reqs", auditoryReqs);
            return "admin-control-page";
        }
        return "redirect:/admin/department/" + province_id;
    }

    @GetMapping("/auditory")
    public String getAdminAuditoryPage(@RequestParam(name = "id") Long id,
                                       @RequestParam("date")
                                       @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
                                       HttpServletRequest httpServletRequest,
                                       Model model) {

        Auditory auditory = auditoryRepository.getById(id);

        Long province_id = (Long) httpServletRequest.getAttribute("province_id");
        if (auditory
                .getDepartment()
                .getRegion()
                .getProvince()
                .getId().equals(province_id)) {

            RequestResultsWithConflicts resultsWithConflicts
                    = requestResultService.getResultsByAuditoryAndDate(auditory, date);
            List<TimeDurationDto> maps = new ArrayList<>();
            resultsWithConflicts.getSessionRequests().forEach(e -> maps.add(computeDiff(e.getStartDate(), e.getEndDate())));

            model.addAttribute("date", date);
            model.addAttribute("nextDate", getNextDay(date));
            model.addAttribute("prevDate", getPreviousDay(date));

            model.addAttribute("form", new SuggestionDto());
            model.addAttribute("auditory", auditory);
            model.addAttribute("requests", resultsWithConflicts.getSessionRequests());
            model.addAttribute("conflicts", resultsWithConflicts.getSessionConflicts());
            model.addAttribute("intervals", maps);

            return "admin-auditory-page";
        }
        return "redirect:/admin/department/" + province_id;
    }

    @PostMapping("/auditory")
    public String addSuggestionToRequest(@RequestParam(name = "id") Long id,
                                         @RequestParam(name = "accepted") String accepted,
                                         @RequestParam(name = "result") Long requestResult,
//                                         @ModelAttribute Suggestion suggestion,
                                         HttpServletRequest httpServletRequest
    ) throws Exception {

        Auditory auditory = auditoryRepository.getById(id);

        Long province_id = (Long) httpServletRequest.getAttribute("province_id");
        if (auditory
                .getDepartment()
                .getRegion()
                .getProvince()
                .getId().equals(province_id)) {
            if (accepted.equals("true")) {
                requestResultService.acceptResult(requestResult);
            } else {
                requestResultService.rejectResult(requestResult);
            }
            return "redirect:/auditory/" + auditory.getId();
        }
        return "redirect:/admin/department/" + province_id;
    }

    @PostMapping("/suggestion/{auditory}/{result}")
    public String addSuggestionToRequestPost(@ModelAttribute SuggestionDto suggestion,
                                             HttpServletRequest httpServletRequest,
                                             @PathVariable(name = "auditory") Auditory auditory,
                                             @PathVariable(name = "result") RequestResult requestResult) {

        suggestion.setStartDate(StringToDate.removeT(suggestion.getStartDate()));
        suggestion.setEndDate(StringToDate.removeT(suggestion.getEndDate()));
        String date = StringToDate.dateToString(requestResult.getSessionRequest().getStartDate());

        System.out.println(suggestion);
        Long province_id = (Long) httpServletRequest.getAttribute("province_id");

        if (auditory.getDepartment().getRegion().getProvince().getId().equals(province_id)) {
            try {
                Suggestion s = dtoToEntity(suggestion, auditory, requestResult);
                requestResult.setSuggestion(s);
                requestResultService.save(requestResult);
            } catch (ParseException e) {
                return "redirect:/error";
            }
        }
        return "redirect:/admin/auditory/?id=" + auditory.getId() + "&date=" + date;
    }


    public Suggestion dtoToEntity(SuggestionDto suggestionDto, Auditory auditory, RequestResult requestResult) throws ParseException {
        Suggestion suggestion = new Suggestion();
        suggestion.setReason(suggestionDto.getReason());
        suggestion.setStartDate(StringToDate.stringToDateTime(suggestionDto.getStartDate()));
        suggestion.setEndDate(StringToDate.stringToDateTime(suggestionDto.getEndDate()));
        suggestion.setAuditory(auditory);
        suggestion.setRequestResult(requestResult);
        return suggestion;

    }

    public static TimeDurationDto computeDiff(Date date1, Date date2) {

        long diffInMillies = date2.getTime() - date1.getTime();

        //create the list
        List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.of(TimeUnit.HOURS, TimeUnit.MINUTES));
        Collections.reverse(units);

        //create the result map of TimeUnit and difference

        long milliesRest = diffInMillies;
        List<TimeDurationDto> timeDurationDtos = new ArrayList<>();
        TimeDurationDto timeDurationDto = new TimeDurationDto();
        for (TimeUnit unit : units) {

            //calculate difference in millisecond
            long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;

            //put the result in the map;
            if (timeDurationDto.getHour() == null) {
                timeDurationDto.setHour(diff);
            } else {
                timeDurationDto.setMinute(diff);
            }
        }


        return timeDurationDto;
    }

    public String getNextDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);  // number of days to add

        return sdf.format(c.getTime());
    }

    public String getPreviousDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);  // number of days to add

        return sdf.format(c.getTime());
    }

}
