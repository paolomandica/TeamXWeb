package com.jpl.teamx.security;

import com.jpl.teamx.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Optional.empty;
import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Resource
    private OAuth2RestOperations restTemplate;

    @Bean
    private TeamsAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new TeamsAuthenticationSuccessHandler();
    }

    protected AuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(authentication -> authentication);
        setAuthenticationSuccessHandler(authenticationSuccessHandler());
        // AbstractAuthenticationProcessingFilter requires an authentication manager.
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        final ResponseEntity<User> userInfoResponseEntity =
                restTemplate.getForEntity("https://www.googleapis.com/oauth2/v2/userinfo", User.class);
        return new PreAuthenticatedAuthenticationToken(
                userInfoResponseEntity.getBody(), empty(), NO_AUTHORITIES);
    }
}