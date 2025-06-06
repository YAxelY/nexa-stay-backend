package com.nexastay.reviewservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

        @Value("${jwt.secret}")
        private String secretKey;

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) {
                String path = request.getServletPath();
                return path.equals("/api/reviews/latest") || path.startsWith("/api/reviews/room/");
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                        FilterChain filterChain)
                        throws ServletException, IOException {
                try {
                        String authHeader = request.getHeader("Authorization");
                        log.info("Processing request to: {} with auth header: {}",
                                        request.getRequestURI(),
                                        authHeader != null ? "present" : "missing");

                        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                                log.error("Missing or invalid Authorization header for request to: {}",
                                                request.getRequestURI());
                                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                                                "Missing or invalid Authorization header");
                                return;
                        }

                        String jwt = authHeader.substring(7);
                        log.debug("Attempting to validate JWT token for request to: {}",
                                        request.getRequestURI());

                        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

                        try {
                                Claims claims = Jwts.parserBuilder()
                                                .setSigningKey(key)
                                                .build()
                                                .parseClaimsJws(jwt)
                                                .getBody();

                                Long userId = claims.get("userId", Long.class);
                                String role = claims.get("role", String.class);
                                String email = claims.get("email", String.class);

                                log.info("JWT validation successful - User: {}, Role: {}, Email: {}, URI: {}",
                                                userId, role, email, request.getRequestURI());

                                if (userId == null) {
                                        log.error("Missing userId in JWT claims for request to: {}",
                                                        request.getRequestURI());
                                        sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                                                        "Invalid token: missing userId");
                                        return;
                                }

                                List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                                                new SimpleGrantedAuthority("ROLE_" + role));

                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                                userId.toString(), null, authorities);

                                // Add user details to the request attributes
                                request.setAttribute("userId", userId);
                                request.setAttribute("userEmail", email);
                                request.setAttribute("userRole", role);

                                SecurityContextHolder.getContext().setAuthentication(authentication);

                                log.info("Authentication successful - User: {}, Role: {}, URI: {}",
                                                userId, role, request.getRequestURI());
                                filterChain.doFilter(request, response);
                        } catch (Exception e) {
                                log.error("JWT validation failed for request to {}: {}",
                                                request.getRequestURI(), e.getMessage());
                                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                                                "Invalid or expired token");
                        }
                } catch (Exception e) {
                        log.error("Authentication error for request to {}: {}",
                                        request.getRequestURI(), e.getMessage());
                        sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                                        "Authentication failed");
                }
        }

        private void sendErrorResponse(HttpServletResponse response, int status, String message)
                        throws IOException {
                response.setStatus(status);
                response.setContentType("application/json");
                response.getWriter().write(String.format("{\"error\": \"%s\"}", message));
        }
}