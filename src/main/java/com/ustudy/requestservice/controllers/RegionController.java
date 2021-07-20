package com.ustudy.requestservice.controllers;

import com.ustudy.requestservice.models.Region;
import com.ustudy.requestservice.services.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/region")
@AllArgsConstructor
public class RegionController {

    private final RegionService regionService;


    @GetMapping("/{region}")
    public String getRegion(
            @PathVariable(name = "region") Long id,
            Model model) {
        try {
            Region region = regionService.getById(id);
            model.addAttribute("region", region);
        } catch (Exception e) {
            model.addAttribute("region", new Region());
        }
        return "exact-region-page";
    }
}
