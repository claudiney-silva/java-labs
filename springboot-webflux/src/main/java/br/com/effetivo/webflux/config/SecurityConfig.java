package br.com.effetivo.webflux.config;

import br.com.effetivo.webflux.service.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        //@formatter:off
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                .pathMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                .pathMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "/products/**").hasRole("USER")
                .pathMatchers("/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/webjars/**")
                .permitAll()
                .anyExchange().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .build();
        //@formatter:on
    }

    /*
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("password"))
                .roles("USER", "ADMIN")
                .build();

        return new MapReactiveUserDetailsService(user, admin);
    }
    */

    @Bean
    ReactiveAuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    }
}