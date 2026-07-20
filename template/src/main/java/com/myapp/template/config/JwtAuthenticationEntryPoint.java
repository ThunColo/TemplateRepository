package com.myapp.template.config;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Spring Securityの認証エントリポイント。未認証のユーザーが保護されたリソースにアクセスしようとした場合に呼び出される。
 *
 * @author nahaton 2026/07/19
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSONのシリアライズとデシリアライズを行うためのObjectMapper 

    /**
     * 未認証のユーザーが保護されたリソースにアクセスしようとした場合に、HTTPレスポンスに401 Unauthorizedを返す。
     *
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param authException 認証例外
     * @throws IOException 入出力例外が発生した場合
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> body = Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", HttpStatus.UNAUTHORIZED.value(),
                "error", "Unauthorized",
                "message", "Authentication is required to access this resource"
        );

        objectMapper.writeValue(response.getWriter(), body);
    }
}
