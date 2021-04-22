package com.soen487.rest.project.service.gateway.configuration;

import com.soen487.rest.project.service.gateway.filter.ErrorFilter;
import com.soen487.rest.project.service.gateway.filter.PostFilter;
import com.soen487.rest.project.service.gateway.filter.PreFilter;
import com.soen487.rest.project.service.gateway.filter.RouteFilter;
import org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulGatewayConfig {
    @Bean
    public PreFilter preFilter(){ return new PreFilter(); }

    @Bean
    PostFilter postFilter(){
        return new PostFilter();
    }

    @Bean
    ErrorFilter errorFilter(){
        return new ErrorFilter();
    }

    @Bean
    RouteFilter routeFilter(){
        return new RouteFilter();
    }
}
