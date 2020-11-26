package com.acme.couponsystem.db.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@PropertySource("classpath:application.properties")
public class RestConfiguration implements WebMvcConfigurer {

    private static Logger LOGGER = LoggerFactory.getLogger(RestConfiguration.class);

    @Value("${session.timeout-millis}")
    private long sessionTimeoutMs;

    @Bean(name = "tokens")
    public Map<String, ClientSession> tokensMap()
    {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/storage/**")
                .addResourceLocations("file:../storage/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException
                    {
                        Resource requestedResource = location.createRelative(resourcePath);
                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
                                : new ClassPathResource("/static/index.html");
                    }
                });
    }

    @Bean
    @ConditionalOnExpression("${rest.cors}")
    public WebMvcConfigurer corsConfigurer()
    {
        LOGGER.warn("Global cross origin is enabled");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
                registry
                        .addMapping("*")
                        .allowedOrigins("http://localhost:4200")
                        .allowCredentials(true);
            }
        };
    }

    public long getSessionTimeoutMs()
    {
        return sessionTimeoutMs;
    }

    public void setSessionTimeoutMs(long sessionTimeoutMs)
    {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }
}
