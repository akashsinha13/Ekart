package com.ekart.listener;

import com.ekart.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    HttpServletRequest request;

    @Autowired
    LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

        // X-Forwarded-For: clientIpAddress, proxy1, proxy2
        final String xfHeader = request.getHeader("X-Forwarded-For");

        if (xfHeader == null) {
            loginAttemptService.loginSucceeded(request.getRemoteAddr());
        } else {
            loginAttemptService.loginSucceeded(xfHeader.split(",")[0]);
        }
    }
}
