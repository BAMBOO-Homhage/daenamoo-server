package daenamoo.homepage.auth.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public static CorsConfigurationSource apiConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 데이터 교환이 가능한 URL 지정
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:8080",
                "http://localhost:3000",
                "http://3.39.231.129:8080"
        ));

        // 허용하는 HTTP METHOD 지정
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));

        // 허용하는 HTTP Headers 지정
        configuration.setAllowedHeaders(List.of(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE));
        configuration.setAllowCredentials(true); // credential TRUE

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
