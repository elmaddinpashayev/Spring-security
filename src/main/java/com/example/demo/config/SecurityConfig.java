package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin();
        http
                .authorizeHttpRequests().antMatchers("/dashboard")
                .hasRole("ADMIN")
                .and()
                .authorizeHttpRequests()
                .antMatchers("/index")
                .permitAll().and()
                .httpBasic()
                .and()
                .formLogin();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder =
//                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password("{noop}12345")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("{noop}1234")
                .roles("USER", "ADMIN");
    }

    @Bean
    public UserDetailsManager userDetailsManager(){
        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        UserDetails elmaddin= User.withUsername("elmaddin")
                .password("{noop}12345")
                .roles("ADMIN")
                .build();
        userDetailsManager.createUser(elmaddin);

             return userDetailsManager;
        }


}
