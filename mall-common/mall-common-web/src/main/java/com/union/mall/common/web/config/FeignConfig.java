package com.union.mall.common.web.config;

import feign.RequestInterceptor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Feign related configuration class.
 * Configures DispatcherServlet to inherit RequestContext in child threads
 * and provides a RequestInterceptor to forward original headers before sending requests via Feign.
 *
 * @author vanhung4499
 */
@Configuration
public class FeignConfig {

    /**
     * Registers DispatcherServlet with thread context inheritable.
     *
     * @param servlet DispatcherServlet instance
     * @return ServletRegistrationBean for DispatcherServlet
     */
    @Bean
    public ServletRegistrationBean<DispatcherServlet> dispatcherRegistration(DispatcherServlet servlet) {
        servlet.setThreadContextInheritable(true);
        return new ServletRegistrationBean<>(servlet, "/**");
    }

    /**
     * Provides a RequestInterceptor to forward original headers before sending requests via Feign.
     *
     * @return RequestInterceptor instance
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return (template) -> {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        if (!"content-length".equalsIgnoreCase(name)) {
                            String values = request.getHeader(name);
                            template.header(name, values);
                        }
                    }
                }
            }
        };
    }

}
