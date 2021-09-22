package effetivo.restapijavaspringboot.api.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import effetivo.restapijavaspringboot.api.service.AuthUserDetailsService;

@Log4j2
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final AuthUserDetailsService authUserDetailsService;
    
    /**
     * BasicAuthenticationFilter
     * UsernamePasswordAuthenticationFilter
     * DefaultLoginPageGeneratingFilter
     * DefaultLogoutPageGeneratingFilter
     * FilterSecurityInterceptor
     * Authentication -> Authorization
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // POSTMAN CODE SAMPLE
        // X-XSRF-TOKEN
        // var xsrfCookie = postman.getResponseCookie("XSRF-TOKEN");
        // postman.setEnvironmentVariable("x-xsrf-token", xsrfCookie.value);

        http.csrf().disable()
                // csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .authorizeRequests()
                .antMatchers("/users/admin/**").hasRole("ADMIN")
                .antMatchers("/users/**").hasRole("USER")
                .antMatchers("/actuator/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoded {}", passwordEncoder.encode("123456789"));

        // in Memory
        auth.inMemoryAuthentication()
             .withUser("admin2")
             .password(passwordEncoder.encode("123456789"))
             .roles("USER", "ADMIN")
             .and()
             .withUser("usuario2")
             .password(passwordEncoder.encode("123456789"))
             .roles("USER");
        
        // in Database
        auth.userDetailsService(authUserDetailsService)
            .passwordEncoder(passwordEncoder);
    }
}
