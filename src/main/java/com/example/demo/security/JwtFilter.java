package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component   // ✅ THIS IS THE FIX
public class JwtFilter extends OncePerRequestFilter {

    private static final List<String> PUBLIC_URLS = List.of(
            "/auth/",
            "/parcels/",
            "/rules/",
            "/claims/",
            "/evidence/",
            "/swagger-ui",
            "/v3/api-docs"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // ✅ Skip JWT check for public APIs
        for (String url : PUBLIC_URLS) {
            if (path.startsWith(url)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 🔐 For now, allow everything else (you can add JWT later)
        filterChain.doFilter(request, response);
    }
}
