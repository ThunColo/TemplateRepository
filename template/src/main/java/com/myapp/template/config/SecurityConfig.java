package com.myapp.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * ユーザーからのレスポンスデータを表すクラス。
 *
 * passwordHash はAPIレスポンスに含めてしまうとセキュリティ上のリスクがある。 よって外部に返すDTOで明示的に除外する。
 *
 * @author nahaton 2026/06/21
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // APIサーバーの場合は一般的にCSRF保護を無効にする
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); //  すべてのリクエストを許可する（認証なしでアクセス可能）

        return http.build();
    }
}
