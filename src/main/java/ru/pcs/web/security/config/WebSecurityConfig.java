package ru.pcs.web.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        }

    @Autowired
    @Qualifier(value = "userDetailService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/signUp").permitAll()
                .antMatchers("/profile").authenticated()
                .antMatchers("/images").authenticated()
                .antMatchers("/my-images").authenticated()
                .antMatchers("/search/**").authenticated()
                .antMatchers("/add/**").authenticated()
                .antMatchers("/changed").authenticated()
                .antMatchers("/accounts/**").hasAuthority("ADMIN")
                .and()
                .formLogin()
                .loginPage("/signIn")
                .defaultSuccessUrl("/profile")
                .failureUrl("/signIn?error")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll();
    }
}
