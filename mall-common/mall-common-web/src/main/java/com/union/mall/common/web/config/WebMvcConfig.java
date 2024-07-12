package com.union.mall.common.web.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.util.List;

/**
 * WebMvc configuration for customizing message converters.
 *
 * @author vanhung4499
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Configure custom message converters for Jackson serialization.
     *
     * @param converters List of HTTP message converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        // Allow unquoted field names in JSON as per Jackson ObjectMapper configuration
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        // Resolve precision loss issues for Long and BigInteger when passed to frontend (JS max integer precision is Math.pow(2,53))
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(jackson2HttpMessageConverter);
    }

}
