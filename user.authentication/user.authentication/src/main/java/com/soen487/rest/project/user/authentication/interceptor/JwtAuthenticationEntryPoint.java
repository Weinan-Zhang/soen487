package com.soen487.rest.project.user.authentication.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soen487.rest.project.repository.core.configuration.ReturnCode;
import lombok.SneakyThrows;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        com.soen487.rest.project.repository.core.configuration.ServiceResponse serviceResponse = com.soen487.rest.project.repository.core.configuration.ServiceResponse.fail(ReturnCode.UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(serviceResponse));
    }
}
