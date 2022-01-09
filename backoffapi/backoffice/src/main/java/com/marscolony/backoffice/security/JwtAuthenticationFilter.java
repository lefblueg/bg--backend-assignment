package com.marscolony.backoffice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //get jwt from http
        var token = getJwtFromRequest(request);
        //validate token
        if(StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            //get username from token
            String username = tokenProvider.getUserNameFromJwt(token);

            //load user associated with the token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //set spring security
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        filterChain.doFilter(request,response);


    }

    private String getJwtFromRequest(HttpServletRequest request) {
        //<Bearer><space><token>
        var bearer = request.getHeader("Authorization");

        if( StringUtils.hasText(bearer)  && bearer.startsWith("Bearer ")){
            return bearer.substring(7, bearer.length());
        }

        return null;
    }
}
