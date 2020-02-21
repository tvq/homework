package com.zedge.homework.http;

import com.zedge.homework.repositories.AuthRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TokenFilter extends GenericFilterBean {
    private AuthRepository repository;

    public TokenFilter(AuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = null;
        String bearerToken = ((HttpServletRequest) request).getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7, bearerToken.length());
        }

        if (token != null) {
            SecurityContextHolder.getContext()
                    .setAuthentication(repository.getAuthentication(token));
        }

        chain.doFilter(request, response);
    }
}
