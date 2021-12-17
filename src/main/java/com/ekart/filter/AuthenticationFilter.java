package com.ekart.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.ekart.model.AuthUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.stream.Collectors;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginAttempt creds = new ObjectMapper().readValue(request.getInputStream(), LoginAttempt.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword());
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthUserDetails user = (AuthUserDetails) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("e-kart_Secret_Key".getBytes());

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15 min
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

//        ResponseCookie responseCookie = ResponseCookie.from("access_token", accessToken)
//                .secure(true)
//                .httpOnly(false)
//                .sameSite("None")
//                .build();
//        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        // Response body
        LoginResponse loginResponse = new LoginResponse(user.getName(), user.isEnabled(), user.getUsername(), accessToken);
        String userJsonString = new Gson().toJson(loginResponse);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(userJsonString);
        out.flush();

    }

    static class LoginAttempt {
        private String username;
        private String password;

        public LoginAttempt() {
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    class LoginResponse {
        private String name;
        private boolean isActive;
        private String email;
        private String token;

        public LoginResponse(String name, boolean isActive, String email, String token) {
            this.name = name;
            this.isActive = isActive;
            this.email = email;
            this.token = token;
        }
    }
}
