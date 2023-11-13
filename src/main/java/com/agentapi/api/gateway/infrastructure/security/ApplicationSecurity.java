package com.agentapi.api.gateway.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
@OpenAPIDefinition
@SecurityScheme(
	    name = "bearerAuth",
	    type = SecuritySchemeType.HTTP,
	    bearerFormat = "JWT",
	    scheme = "bearer"
	)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
	
    private JwtTokenProvider jwtTokenProviderService;
	
	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http
	      .authorizeRequests()
	        .antMatchers("/api/users/generate","/api/users/batch","/api/auth","/api/users/fetch").permitAll()
	        .anyRequest().authenticated();
	       
	    
	    http.csrf().disable()  // batch endpoint
	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    
	    http.apply(new JwtTokenFilterConfigurer(jwtTokenProviderService));
	  }
	  
	  
	    @Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/v3/api-docs/**")//
	                .antMatchers("/swagger-resources/**")//
	                .antMatchers("/swagger-ui.html")//
	                .antMatchers("/swagger-ui/**")
	                .antMatchers("/h2-console/**/**");
	    }
	  
	    @Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return authenticationManager();
	    }
	    

}
