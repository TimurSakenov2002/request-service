package com.ustudy.requestservice.components;

import com.ustudy.requestservice.models.Admin;
import com.ustudy.requestservice.repositories.AdminRepository;
import com.ustudy.requestservice.repositories.ProvinceRepository;
import com.ustudy.requestservice.repositories.UserDetailRepository;
import com.ustudy.requestservice.repositories.UserRoleRepository;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
@Order(1)
public class AdminFilter implements Filter {
    private final ProvinceRepository provinceRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserRoleRepository userRoleRepository;
    private final AdminRepository adminRepository;

    private final String ADMIN_DEFAULT_PASSWORD = "Aqdwkk56s58";

    public AdminFilter(ProvinceRepository provinceRepository, UserDetailRepository userDetailRepository, UserRoleRepository userRoleRepository, AdminRepository adminRepository) {
        this.provinceRepository = provinceRepository;
        this.userDetailRepository = userDetailRepository;
        this.userRoleRepository = userRoleRepository;
        this.adminRepository = adminRepository;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        if (adminRepository.existsByUserDetailEmail(currentUserName)) {
            Long id=adminRepository.getByUserDetailEmail(currentUserName).getProvince().getId();
            servletRequest.setAttribute("province_id",id);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.sendRedirect("/error-403");
    }
}
