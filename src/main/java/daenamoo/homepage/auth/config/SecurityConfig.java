package daenamoo.homepage.auth.config;

import daenamoo.homepage.auth.filter.CustomLoginFilter;
import daenamoo.homepage.auth.filter.JwtAuthorizationFilter;
import daenamoo.homepage.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    // 인증이 필요하지 않은 URL 목록
    private final String[] allowedUrls = {
            "/",
            "/api/members/login",
            "/api/members/signup",
            "/auth/reissue",
            "/swagger-ui/**",
            "/v3/api-docs/**",
    };

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CORS 정책 설정
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));  // 여기서 CORS 설정 사용

        // CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // REST API 방식 로그인을 사용하기 때문에 Form 로그인 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);

        // HTTP 기본 인증 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable);

        // 세션을 사용하지 않음 (Stateless)
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 경로별 인가 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(allowedUrls).permitAll()  // 인증 필요 없는 URL 설정
                .anyRequest().authenticated()  // 나머지 경로는 인증 필요
        );

        // 로그아웃 설정
        http.logout(logout -> logout
                .logoutUrl("/api/members/logout")
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/members/logout"))
        );

        // 로그인 필터 추가
        CustomLoginFilter loginFilter = new CustomLoginFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        loginFilter.setFilterProcessesUrl("/api/members/login");

        // 필터 체인에 로그인 필터 등록
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        // JWT 인증 필터를 로그인 필터 이전에 등록
        http.addFilterBefore(new JwtAuthorizationFilter(jwtUtil), CustomLoginFilter.class);

        return http.build();
    }

    // CORS 설정을 빈으로 등록
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return CorsConfig.apiConfigurationSource();  // CorsConfig에 정의된 CORS 설정 사용
    }
}
