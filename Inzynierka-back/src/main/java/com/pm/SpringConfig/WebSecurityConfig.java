package com.pm.SpringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.savedrequest.NullRequestCache;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // @formatter:off
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.cors()
                //.and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.GET,"/register/new").permitAll()
                .antMatchers(HttpMethod.POST,"/register/new").permitAll()
                .antMatchers(HttpMethod.POST,"/file/uploadNew").permitAll()
                .antMatchers(HttpMethod.GET,"/file/uploadNew").permitAll()
                .antMatchers(HttpMethod.POST,"/file/loadFile").permitAll()
                .antMatchers(HttpMethod.GET,"/python/a").permitAll()
                .antMatchers(HttpMethod.GET,"/content/**").permitAll()
                .antMatchers(HttpMethod.POST,"/content/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .requestCache()
                .requestCache(new NullRequestCache())
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
        //.and();
    }

}