package com.thinhnguyen.provider.telecom.config.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Component
public class TokenAuthorizationFilter extends OncePerRequestFilter {

    @Value("${app.token.http.request.header}")
    private String tokenHeader;

    @Value("${app.client.secret}")
    private String clientSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = httpServletRequest.getHeader(this.tokenHeader);
        String clientId = null;
        String token = null;
        String tokenPart = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.substring(7);
            Integer clientIndex = token.indexOf('.');
            if(clientIndex < 0) {
                logger.warn("Token Does Not Valid");
            } else {
                clientId = token.substring(0, clientIndex);
                tokenPart = token.substring(clientIndex + 1);
            }
        }
        if (clientId != null && tokenPart != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = new User(clientId, clientSecret, Arrays.asList(new SimpleGrantedAuthority("USER")));
            try {
                if(tokenPart.equals(signToken(clientId))){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (NoSuchAlgorithmException e) {
                logger.error("Cannot load the algorithm", e);
            } catch (InvalidKeyException e) {
                logger.warn("The secret key is invalid", e);
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public String signToken(String clientId) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(clientSecret.getBytes(), "HmacSHA256");
        sha256HMAC.init(secret_key);
        return Base64.encodeBase64URLSafeString(sha256HMAC.doFinal(clientId.getBytes()));
    }

}
