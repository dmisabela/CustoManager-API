package com.customanagerapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.customanagerapi.security.jwt.JwtAuthFilter;
import com.customanagerapi.security.jwt.JwtService;
import com.customanagerapi.service.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Lazy
	private UsuarioService usuarioService;
	
	@Autowired
	@Lazy
	private JwtService jwtService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, usuarioService);
	}
	
	public String[] verifyHasAnyRole(String endpoint) {
		
		String[] verifiedRoles = {""};
		
		//TODO: implementar consulta das permissions do endpoint após crud de perfis e permissões
		
		//Teste
		
		if (endpoint == "/users/update") {
			String[] roles = {"USER"};	
			verifiedRoles = roles;
		}
		else if (endpoint == "/users/get-all") {
			String[] roles = {"ADMIN"};		
			verifiedRoles = roles;
		}
		
		return verifiedRoles;				
	}
	
//	private CorsConfigurationSource configurationSource() {
//	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	      CorsConfiguration config = new CorsConfiguration();
//	      config.addAllowedOrigin("*");
//	      config.addAllowedHeader("*");
//	      config.addAllowedHeader("*");
//	      config.addAllowedMethod("*");
//	      source.registerCorsConfiguration("/**", config);
//	      return source;
//	    }


    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http
        .cors().and().csrf().disable()
            .authorizeRequests()
            	.antMatchers("/users/update")
            		.hasAnyRole(verifyHasAnyRole("/users/update"))
                .antMatchers("/users/get-all")
                    .hasAnyRole(verifyHasAnyRole("/users/get-all"))
                .antMatchers("/users/register")
                    .permitAll()
                 .antMatchers("/users/login")
                 	.permitAll()
                .anyRequest().authenticated()
            .and()
            	.sessionManagement()
            	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            	.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    	;
    }
    
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

}
