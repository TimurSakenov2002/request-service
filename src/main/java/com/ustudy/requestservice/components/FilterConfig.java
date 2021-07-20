package com.ustudy.requestservice.components;

import com.ustudy.requestservice.repositories.AdminRepository;
import com.ustudy.requestservice.repositories.ProvinceRepository;
import com.ustudy.requestservice.repositories.UserDetailRepository;
import com.ustudy.requestservice.repositories.UserRoleRepository;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    private final ProvinceRepository provinceRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserRoleRepository userRoleRepository;
    private final AdminRepository adminRepository;

    public FilterConfig(ProvinceRepository provinceRepository, UserDetailRepository userDetailRepository, UserRoleRepository userRoleRepository, AdminRepository adminRepository) {
        this.provinceRepository = provinceRepository;
        this.userDetailRepository = userDetailRepository;
        this.userRoleRepository = userRoleRepository;
        this.adminRepository = adminRepository;
    }

    // uncomment this and comment the @Component in the filter class definition to register only for a url pattern
     @Bean
    public FilterRegistrationBean<AdminFilter> loggingFilter() {
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AdminFilter(provinceRepository, userDetailRepository, userRoleRepository, adminRepository));

        registrationBean.addUrlPatterns("/admin/*");

        return registrationBean;

    }

}