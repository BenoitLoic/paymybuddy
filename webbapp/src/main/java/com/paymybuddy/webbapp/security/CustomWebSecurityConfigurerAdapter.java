package com.paymybuddy.webbapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/V1/users").authenticated()
                .antMatchers("/","/V1/user").permitAll()
                .and()
                .formLogin().loginPage("/showLoginPage").loginProcessingUrl("/authenticateTheUser").permitAll() ;
    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/showLoginPage")
//                .loginProcessingUrl()
//                .permitAll();
//    }


    //TODO:
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        //BCryptPE permet de hasher le mdp. la valeur de hash par default est 10.
        return new BCryptPasswordEncoder();
    }
}
