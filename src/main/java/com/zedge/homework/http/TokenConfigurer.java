package com.zedge.homework.http;

import com.zedge.homework.repositories.AuthRepository;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private AuthRepository repository;

    public TokenConfigurer(AuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        builder.addFilterBefore(new TokenFilter(repository), UsernamePasswordAuthenticationFilter.class);
    }
}
