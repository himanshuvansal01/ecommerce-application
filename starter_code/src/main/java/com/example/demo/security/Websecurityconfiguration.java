package com.example.demo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableWebSecurity
public class Websecurityconfiguration extends WebSecurityConfigurerAdapter {

    private Userdetailsserviceimplementor userdetailsserviceimplementor;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Websecurityconfiguration(Userdetailsserviceimplementor userdetailsserviceimplementor, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userdetailsserviceimplementor = userdetailsserviceimplementor;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userdetailsserviceimplementor).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource Source = new UrlBasedCorsConfigurationSource();
        Source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return Source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST,SecurityConstants.Sign_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTauthenticationfilter(authenticationManager()))
                .addFilter(new JWTauthenticationverificationfilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
