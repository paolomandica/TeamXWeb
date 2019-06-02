package com.jpl.teamx.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;

@Configuration
@EnableWebSecurity(debug=true) // debug mode lets you see messages
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private OidcUserService oidcUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
        .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().oauth2Login()
                .loginPage("/custom-login")
                .redirectionEndpoint().baseUri("/teams")
                .and().userInfoEndpoint().oidcUserService(oidcUserService);
    }

}
