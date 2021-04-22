package com.soen487.rest.project.service.book.configuration;

import com.soen487.rest.project.service.book.interceptor.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // We don't need CSRF for this example
                .csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests().antMatchers("/resources/**", "/signup", "/login").permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated().and()
                // intercept the unauthenticated request and redirect to login page.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and();
//                // make sure we use stateless session; session won't be used to store user's state.
//                .sessionManagement()
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                // Add a filter to validate the tokens with every request
                httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
