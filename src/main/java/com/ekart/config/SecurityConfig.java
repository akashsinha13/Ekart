package com.ekart.config;

import com.ekart.filter.AuthenticationFilter;
import com.ekart.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    // use for authentication
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    // use for authorization and form login
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
//                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
//                .antMatchers("/api/v1/brands/**").hasAnyRole("ADMIN", "STAFF")
//                .antMatchers("/api/v1/category/**").hasAnyRole("ADMIN", "STAFF")
//                .antMatchers("/api/v1/sizes/**").hasAnyRole("ADMIN", "STAFF")
//                .antMatchers(HttpMethod.POST, "/api/v1/products/**").hasAnyRole("ADMIN", "STAFF")
//                .antMatchers(HttpMethod.PUT, "/api/v1/products/**").hasAnyRole("ADMIN", "STAFF")
                .antMatchers("/**").permitAll();

        http.addFilter(new AuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
