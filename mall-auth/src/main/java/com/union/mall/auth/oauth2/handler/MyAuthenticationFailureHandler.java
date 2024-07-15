package com.union.mall.auth.oauth2.handler;

import com.union.mall.common.core.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * Authentication Failure Handler
 *
 * @author vanhung4499
 */
@Slf4j
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * MappingJackson2HttpMessageConverter is an HTTP message converter provided by the Spring framework,
     * used to convert JSON data between HTTP requests/responses and Java objects.
     */
    private final HttpMessageConverter<Object> accessTokenHttpResponseConverter = new MappingJackson2HttpMessageConverter();


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        OAuth2Error error = ((OAuth2AuthenticationException) exception).getError();
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        Result result = Result.failed(error.getErrorCode());
        accessTokenHttpResponseConverter.write(result, null, httpResponse);
    }
}

