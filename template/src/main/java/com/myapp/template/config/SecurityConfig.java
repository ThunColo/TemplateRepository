package com.myapp.template.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Securityの設定クラス。CORSの設定、CSRFの無効化、セッション管理の設定、認可ルールの設定を行う。 passwordHash
 * はAPIレスポンスに含めてしまうとセキュリティ上のリスクがある。 よって外部に返すDTOで明示的に除外する。
 *
 * @author nahaton 2026/06/21
 * @update nahaton 2026/07/19 フロントエンドの開発サーバーのアドレスを明示的に許可するように修正。
 * @update nahaton 2026/07/19 AuthenticationEntryPointを追加し、認証エラー時に401を返すように修正。
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter; // JWTの認証を行うためのフィルター
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // 未認証のユーザーが保護されたリソースにアクセスしようとした場合に呼び出されるエントリポイント

    /**
     * SecurityConfigクラスのコンストラクタ。JwtAuthenticationFilterとJwtAuthenticationEntryPointを注入する。
     *
     * @param jwtAuthenticationFilter JWTの認証を行うためのフィルター
     * @param jwtAuthenticationEntryPoint
     * 未認証のユーザーが保護されたリソースにアクセスしようとした場合に呼び出されるエントリポイント
     */
    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    /**
     * Spring Securityのフィルターチェーンを構築する。CORSの設定、CSRFの無効化、セッション管理の設定、認可ルールの設定を行う。
     *
     * @return SecurityFilterChain
     * @throws Exception フィルターチェーンの構築中に例外が発生した場合
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception
                        -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/users").permitAll()
                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * CORSの設定を行う。フロントエンドの開発サーバーのアドレスを明示的に許可する。
     *
     * @return CorsConfigurationSource
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
