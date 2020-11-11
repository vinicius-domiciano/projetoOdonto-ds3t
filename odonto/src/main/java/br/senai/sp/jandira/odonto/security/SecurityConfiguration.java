package br.senai.sp.jandira.odonto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthService authService;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/login").permitAll()
			.antMatchers(
					HttpMethod.GET, 
					"/odonto/dentistas").hasAnyRole("USER, ADMIN")
			.antMatchers(
					HttpMethod.GET,
					"/odonto/especialidades").hasAnyRole("USER, ADMIN")
			.antMatchers(
					HttpMethod.GET, 
					"/odonto/especialidades/*").hasAnyRole("USER, ADMIN")
			.antMatchers(
					HttpMethod.GET, 
					"/odonto/dentistas/*").hasAnyRole("USER, ADMIN")
			.antMatchers(
					HttpMethod.PUT, 
					"/odonto/especialidades/*").hasAnyRole("USER, ADMIN")
			.antMatchers(
					HttpMethod.PUT,
					"/odonto/dentistas/*").hasAnyRole("USER, ADMIN")
			.antMatchers(
					HttpMethod.POST,
					"/odonto/dentistas").hasRole("ADMIN")
			.antMatchers(
					HttpMethod.POST,
					"/odonto/especialidades").hasRole("ADMIN")
			.antMatchers(
					HttpMethod.DELETE,
					"/odonto/dentistas/*").hasRole("ADMIN")
			.antMatchers(
					HttpMethod.DELETE,
					"/odonto/especialidades/*").hasRole("ADMIN")
			.antMatchers(
					HttpMethod.POST,
					"/odonto/upload").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and().httpBasic();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
	
		auth.userDetailsService(authService)
			.passwordEncoder(passEncoder);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("Vini"));
	}
	
}
