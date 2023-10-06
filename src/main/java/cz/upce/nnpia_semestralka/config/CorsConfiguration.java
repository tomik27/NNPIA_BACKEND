package cz.upce.nnpia_semestralka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//CORS chyby obvykle nastávají, když webový prohlížeč pokusí se provést požadavek na zdroje v jiné doméně a server nedovolí takový přístup
// Přidání této konfigurace by mělo pomoci zabránit takovým chybám tím, že povolí přístup k vašim serverovým zdrojům z jakékoli domény.
@Configuration
public class CorsConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowedOrigins("*");
            }
        };
    }
}
