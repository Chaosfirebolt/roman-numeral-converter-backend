package com.github.chaosfirebolt.rncb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	@Autowired
	public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource customCorsConfig) throws Exception {
		http.authorizeHttpRequests(requests -> requests
						.requestMatchers("/convert").authenticated()
						.requestMatchers("/app/register").permitAll())
						.httpBasic(Customizer.withDefaults())
						.csrf(AbstractHttpConfigurer::disable)
						.cors(config -> config.configurationSource(customCorsConfig));
		return http.build();
	}

	@Bean("customCorsConfig")
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("*"));
		configuration.setAllowedMethods(List.of("*"));
		configuration.setAllowedHeaders(List.of("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
