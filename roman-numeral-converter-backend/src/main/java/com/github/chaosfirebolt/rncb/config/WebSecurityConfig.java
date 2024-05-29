package com.github.chaosfirebolt.rncb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.chaosfirebolt.rncb.config.filter.ConversionThrottlingFilter;
import com.github.chaosfirebolt.rncb.config.filter.RegistrationThrottlingFilter;
import com.github.chaosfirebolt.rncb.storage.RequestStorage;
import com.github.chaosfirebolt.rncb.storage.time.TimeRangeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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

import java.time.Clock;
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
		final String everythingAllowed = "*";
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of(everythingAllowed));
		configuration.setAllowedMethods(List.of(everythingAllowed));
		configuration.setAllowedHeaders(List.of(everythingAllowed));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	@Autowired
	public FilterRegistrationBean<RegistrationThrottlingFilter> registrationThrottlingFilter(Clock appClock, @Qualifier("reg-storage") RequestStorage requestStorage,
																																													 List<TimeRangeFactory> factories, ObjectMapper mapper) {
		FilterRegistrationBean<RegistrationThrottlingFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new RegistrationThrottlingFilter(appClock, requestStorage, factories, mapper));
		registrationBean.addUrlPatterns("/app/register");

		return registrationBean;
	}

	@Bean
	@Autowired
	public FilterRegistrationBean<ConversionThrottlingFilter> conversionThrottlingFilter(Clock appClock, @Qualifier("app-storage") RequestStorage requestStorage,
																																											 List<TimeRangeFactory> factories, ObjectMapper mapper) {
		FilterRegistrationBean<ConversionThrottlingFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new ConversionThrottlingFilter(appClock, requestStorage, factories, mapper));
		registrationBean.addUrlPatterns("/convert");

		return registrationBean;
	}
}
