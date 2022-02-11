package com.agentapi.api.gateway.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
	
    private JwtTokenProvider jwtTokenProviderService;
	
	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http
	      .authorizeRequests()
	        .antMatchers("/api/users/generate","/api/users/batch","/api/auth").permitAll()
	        .anyRequest().authenticated();
	       
	    
	    http.csrf().disable()  // batch endpoint
	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    
	    http.apply(new JwtTokenFilterConfigurer(jwtTokenProviderService));
	  }
	  
	  
	    @Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/v2/api-docs")//
	                .antMatchers("/swagger-resources/**")//
	                .antMatchers("/swagger-ui.html")//
	                .antMatchers("/configuration/**")//
	                .antMatchers("/webjars/**")//
	                .antMatchers("/public")
	                .and()
	                .ignoring()
	                .antMatchers("/h2-console/**/**");;
	    }
	  
	    @Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return authenticationManager();
	    }

}
