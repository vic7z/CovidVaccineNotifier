package com.vic.io.covidvaccination.Filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@Log4j2
public class CustomCorsFilter extends CorsFilter {

    public CustomCorsFilter(CorsConfigurationSource configSource) {
        super(configSource);
        log.info("CORSFilter init");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.addHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me,observe");

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}

