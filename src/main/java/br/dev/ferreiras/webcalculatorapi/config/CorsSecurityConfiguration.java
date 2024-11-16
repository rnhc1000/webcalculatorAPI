package br.dev.ferreiras.webcalculatorapi.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** This class deals with CORS configuration
 * @author ricardo@ferreiras.dev.br
 * @version 224.11.11.01
 * @since 11/2024
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class CorsSecurityConfiguration implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsFilterConfiguration() {

        return new WebMvcConfigurer() {

            /**
             *
             * @param corsRegistry inject dependency to check for authorized sockets
             */

            @Override
            public void addCorsMappings(@NonNull final CorsRegistry corsRegistry) {
                corsRegistry.addMapping("/**")
                    .allowedOrigins(
                        "http://192.168.15.11:8000",
                        "http://localhost:8000",
                        "http://127.0.0.1:8000",
                        "https://webcalculator.ferreiras.dev.br"
                    )
                    .allowedMethods(
                        "GET", "POST", "PUT", "DELETE",
                        "HEAD", "TRACE", "CONNECT")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600L);
            }
        };
    }
}
