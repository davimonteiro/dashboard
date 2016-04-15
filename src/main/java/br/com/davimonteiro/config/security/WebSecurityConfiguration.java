package br.com.davimonteiro.config.security;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Inject
	private CustomAuthenticationProvider authenticationProvider;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Override
	protected void configure(HttpSecurity http) throws Exception {
    	http
	        .httpBasic().realmName("app")
	        .and()
	            .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	            .requestMatchers().antMatchers("/oauth/authorize")
	        .and()
	            .authorizeRequests()
	            .antMatchers("/resources/**").permitAll()
	            .antMatchers("/login").permitAll()
	            .antMatchers("/oauth/authorize").authenticated()
	        .and()
	        	.csrf()
	        .and()
	        	.authenticationProvider(authenticationProvider);
	}

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}