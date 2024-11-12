package br.dev.ferreiras.webcalculatorapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.ForceEagerSessionCreationFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    /**
     * Calculator Web API Security Configuration
     * endpoints below do not need to be authenticated
     */
    protected static final String[] WHITELIST = {

            "/swagger-ui/**", "/api-docs/**", "/swagger-docs/**",
            "/swagger-resources/**", "/actuator/**", "/api/v1/login", "/",
            "/api/v1/home", "api/v1/csrf", "/error", "/api/v1/error",
            "401-error/**", "404-error/**"
    };

    /**
     * @param httpSecurity handler to deal with the requests
     * @return object defining the security framework
     * @throws Exception RuntimeException
     * Session management from ALWAYS -> STATELESS so the token hash is checked at every exchange
     * between client and server
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception {

        httpSecurity
                .securityContext(contextConfig -> contextConfig.requireExplicitSave(false) )
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .addFilterBefore(new ForwardedHeaderFilter(), ForceEagerSessionCreationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(SecurityConfiguration.WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
