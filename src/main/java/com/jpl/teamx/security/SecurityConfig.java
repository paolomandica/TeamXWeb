package com.jpl.teamx.security;

import com.jpl.teamx.oauth2.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity(debug=true) // debug mode lets you see messages
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private OidcUserService oidcUserService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
        .antMatchers("/custom-login",
                "/teams",
                "/teams/**",
                "/resources/**")
                .permitAll()
                .anyRequest().authenticated()
                .and().oauth2Login()
                .loginPage("/custom-login")
                .redirectionEndpoint().baseUri("/")
                .and().userInfoEndpoint().oidcUserService(oidcUserService);
                //.and()
                //.successHandler(customAuthenticationSuccessHandler)

    }

}
