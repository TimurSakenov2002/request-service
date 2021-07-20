package com.ustudy.requestservice.controllers;

import com.ustudy.requestservice.models.Admin;
import com.ustudy.requestservice.models.Department;
import com.ustudy.requestservice.models.Province;
import com.ustudy.requestservice.repositories.AdminRepository;
import com.ustudy.requestservice.services.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/department")
@AllArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    private final AdminRepository adminRepository;

    @GetMapping("/{department}")
    public String getProvinceById(@PathVariable(name = "department") Long id, Model model) {
        boolean isAdmin = false;
        Province adminProvince = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                String currentUserName = authentication.getName();
                if (adminRepository.existsByUserDetailEmail(currentUserName)) {
                    final Admin admin = adminRepository.getByUserDetailEmail(currentUserName);
                    isAdmin = true;
                    adminProvince = admin.getProvince();
                }

            }

            Department department = departmentService.getById(id);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("province", adminProvince);
            model.addAttribute("departments", department);
        } catch (Exception e) {
            model.addAttribute("departments", new Department());
        }
        return "exact-department-page";
    }
}
