package com.acautomaton.aichat.filter;

import com.acautomaton.aichat.response.Response;
import com.acautomaton.aichat.utils.JwtUtil;
import com.acautomaton.aichat.utils.exception.IllegalException;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }


        String jwtToken = authorization.substring(7);
        try {
            JwtUtil.verify(jwtToken);
        }
        catch (TokenExpiredException e) {
            String result = new ObjectMapper().writeValueAsString(Response.jwtError("Token超时,请重新登录"));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(result);
            return;
        }
        catch (SignatureVerificationException e) {
            String result = new ObjectMapper().writeValueAsString(Response.jwtError("非法签名"));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(result);
            return;
        }
        catch (AlgorithmMismatchException e) {
            String result = new ObjectMapper().writeValueAsString(Response.jwtError("加密错误"));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(result);
            return;
        }
        catch (Exception e) {
            log.warn(e.toString());
            String result = new ObjectMapper().writeValueAsString(Response.jwtError("Token错误"));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(result);
            return;
        }

        try {
            String username = JwtUtil.decode(jwtToken, "username");
            if (!username.trim().isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.debug("解析出JWT username: " + username);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (!userDetails.isAccountNonLocked()) {
                    throw new IllegalException("账户被锁定");
                }
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        catch (UsernameNotFoundException e) {
            String result = new ObjectMapper().writeValueAsString(Response.jwtError("Token用户不存在"));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(result);
            return;
        }
        catch (IllegalException e) {
            String result = new ObjectMapper().writeValueAsString(Response.jwtError(e.getMessage()));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(result);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
