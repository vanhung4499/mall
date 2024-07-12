package com.union.mall.common.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.union.mall.common.core.result.Result;
import com.union.mall.common.core.result.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom token invalid exception
 *
 * @author vanhung4499
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        mapper.writeValue(response.getOutputStream(), Result.failed(ResultCode.TOKEN_INVALID));
    }
}
