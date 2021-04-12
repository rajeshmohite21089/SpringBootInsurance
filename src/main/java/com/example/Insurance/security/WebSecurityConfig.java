package com.example.Insurance.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	Environment environment;

	 @Bean public UserService userDetailsService() { return new
	 UserDetailsServiceImpl(); }
	 
	 @Bean public BCryptPasswordEncoder passwordEncoder() { return new
	 BCryptPasswordEncoder(); }
	 
	
	 @Autowired
	 private UserService UserService; 
	 private BCryptPasswordEncoder
	  bCryptPasswordEncoder;
	  
	  @Autowired public WebSecurityConfig( BCryptPasswordEncoder
	  bCryptPasswordEncoder) { this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	  
	  }
	 

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(bCryptPasswordEncoder);

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	private AuthenticationFilter getAutheticationFilter() throws Exception {
		// TODO Auto-generated method stub
		
		AuthenticationFilter authenticationFilter=new AuthenticationFilter(UserService,authenticationManager(),environment);
		//authenticationFilter.setAuthenticationManager(authenticationManager());
		authenticationFilter.setFilterProcessesUrl("/login");
		return authenticationFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
             System.out.println("configure");
		/*
		 * http.authorizeRequests().antMatchers("/**"). hasAnyAuthority("USER",
		 * "CREATOR", "EDITOR", "ADMIN") .antMatchers("/new").hasAnyAuthority("ADMIN",
		 * "CREATOR") .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
		 * .antMatchers("/delete/**").hasAuthority("ADMIN")
		 * .antMatchers("/h2-console/**").permitAll().anyRequest()
		 * .authenticated().and().formLogin().permitAll() .and().logout().permitAll()
		 * .and().exceptionHandling().accessDeniedPage("/403");
		 */

		http.cors();
		 http.authorizeRequests().
		 antMatchers("login").permitAll().
		 antMatchers("Insurance/login").permitAll().
		 antMatchers("Insurance/insurance").permitAll().
		 antMatchers("/login").permitAll().
		 antMatchers("/users").permitAll()
		 
					/*
					 * .antMatchers("/login"). hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
					 * .antMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
					 * .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
					 * .antMatchers("/delete/**").hasAuthority("ADMIN")
					 */
		  .anyRequest()
		  .authenticated().and().
		  addFilter(getAutheticationFilter())
		 .addFilter(new JWTAuthorizationFilter(authenticationManager(), environment))
		  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);;
		 
		http.cors();
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
	    configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept","Authorization"));
	    configuration.setAllowCredentials(true);
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
}
