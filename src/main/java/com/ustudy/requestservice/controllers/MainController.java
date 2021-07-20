package com.ustudy.requestservice.controllers;

import com.ustudy.requestservice.dto.RegistrationDto;
import com.ustudy.requestservice.models.User;
import com.ustudy.requestservice.models.UserDetail;
import com.ustudy.requestservice.repositories.UserDetailRepository;
import com.ustudy.requestservice.repositories.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class MainController {
    private PasswordEncoder passwordEncoder;
    private final UserDetailRepository userDetailRepository;
    private final UserRoleRepository userRoleRepository;

    @GetMapping

    public String getMainPage() {
        return "main-page";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login-page";
    }

    @GetMapping("/sign-up")
    public String getSignUpPage(Model model) {
        model.addAttribute("form", new RegistrationDto());
        return "signup-page";
    }

    @PostMapping("/sign-up")
    public String getSignUpPage(@ModelAttribute RegistrationDto registrationDto) throws ParseException {
        UserDetail userDetail = new UserDetail();
        userDetail.setEmail(registrationDto.getEmail());
        userDetail.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        User user=new User(registrationDto);
        user.setUserDetail(userDetail);

        userDetail.setUser(user);
        userDetail.setUserRole(userRoleRepository.getByName("USER"));
        userDetailRepository.save(userDetail);
        return "redirect:/login";
    }



}
