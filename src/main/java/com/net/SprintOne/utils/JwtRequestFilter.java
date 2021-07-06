package com.net.SprintOne.utils;

import com.net.SprintOne.dtos.CustomUserDetails;
import com.net.SprintOne.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        try {
            final String authorizationHeader = httpServletRequest.getHeader("Authorization");

            String username = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
//        } catch (ExpiredJwtException e) {
//            String isRefreshToken = httpServletRequest.getHeader("isRefreshToken");
//            String requestURL = httpServletRequest.getRequestURL().toString();
//
//            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
//                allowForRefreshToken(e, httpServletRequest);
//            }
//            e.printStackTrace();
//        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

//    private void allowForRefreshToken(ExpiredJwtException e, HttpServletRequest request) {
//        // create a UsernamePasswordAuthenticationToken with null values
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
//                UsernamePasswordAuthenticationToken(null, null, null);
//        // After setting the Authentication in the context, we specify that the current user is
//        // authenticated. So it passes the Spring Security Configurations successfully.
//        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//        // Set the claims so that in controller we will be using it to create new JWT
//        request.setAttribute("claims", e.getClaims());
//    }
}
