package com.hnv99.mall.demo.config;

import com.hnv99.mall.demo.bo.AdminUserDetails;
import com.hnv99.mall.mapper.UmsAdminMapper;
import com.hnv99.mall.model.UmsAdmin;
import com.hnv99.mall.model.UmsAdminExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()// Configure permissions
//                .antMatchers("/").access("hasRole('TEST')")// This path requires the TEST role
//                .antMatchers("/brand/list").hasAuthority("TEST")// This path requires the TEST authority
                .antMatchers("/**").permitAll()
                .and()// Enable http-based authentication
                .httpBasic()
                .realmName("/")
                .and()// Configure the login page
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .and()// Configure the logout path
                .logout()
                .logoutSuccessUrl("/")
//                .and()// Remember password feature
//                .rememberMe()
//                .tokenValiditySeconds(60*60*24)
//                .key("rememberMeKey")
                .and()// Disable CSRF
                .csrf()
                .disable()
                .headers()// Remove X-Frame-Options
                .frameOptions()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Get login user information
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UmsAdminExample example = new UmsAdminExample();
                example.createCriteria().andUsernameEqualTo(username);
                List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(example);
                if (umsAdminList != null && !umsAdminList.isEmpty()) {
                    return new AdminUserDetails(umsAdminList.get(0));
                }
                throw new UsernameNotFoundException("Username or password is incorrect");
            }
        };
    }
}
