package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JWTauthenticationverificationfilter extends BasicAuthenticationFilter {

    public JWTauthenticationverificationfilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }



    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String Token = request.getHeader(SecurityConstants.HEADER_STRING);
        if(Token != null) {
            String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .verify(Token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getSubject();


            if (user != null) {

                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());

            }

            return null;

        }

        return null;


    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        if(header ==  null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);


    }


}
