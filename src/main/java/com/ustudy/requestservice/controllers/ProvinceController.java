package com.ustudy.requestservice.controllers;

import com.ustudy.requestservice.models.Province;
import com.ustudy.requestservice.models.Region;
import com.ustudy.requestservice.services.ProvinceService;
import com.ustudy.requestservice.services.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/province")
@AllArgsConstructor
public class ProvinceController {
    private final ProvinceService provinceService;

    @GetMapping
    public String getProvinces(Model model) {
        CompletableFuture<List<Province>> allProvinces = provinceService.getAllProvinces();
        try {
            model.addAttribute("provinces", allProvinces.get());
        } catch (InterruptedException | ExecutionException e) {
            model.addAttribute("provinces", new ArrayList<>());
        }
        return "province-page";
    }
    @GetMapping("/{province}")
    public String getProvinceById(@PathVariable(name = "province")Long id,Model model){
        try {
            Province province=provinceService.getById(id);
            model.addAttribute("province",province);
        } catch (Exception e) {
            model.addAttribute("province",new Province());
        }
        return "exact-province-page";
    }



}
