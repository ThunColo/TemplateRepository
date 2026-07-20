package com.myapp.template.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.myapp.template.domain.exception.InvalidTokenException;
import com.myapp.template.usecase.port.out.AuthProvider;
import com.myapp.template.usecase.port.out.TokenPayload;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWTを使用して認証を行うためのフィルタークラス。 クエストのヘッダーからトークンを取り出し、検証し、成功すればSpring
 * Securityに「このリクエストは認証済み」と伝えるフィルターを作成する。
 *
 * @author nahaton 2026/07/19
 * @update nahaton 2026/07/19 Authorization ヘッダーではなく、Cookieからトークンを取り出すように変更。
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String COOKIE_NAME = "accessToken"; // JWTトークンを格納するCookieの名前

    private final AuthProvider authProvider; // JWTトークンの検証を行うためのプロバイダ

    /**
     * JwtAuthenticationFilterのコンストラクタ。AuthProviderを注入する。
     *
     * @param authProvider JWTトークンの検証を行うためのプロバイダ
     */
    public JwtAuthenticationFilter(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    /**
     * リクエストごとに呼び出されるフィルタ処理。リクエストのCookieからJWTトークンを取り出し、検証し、成功すればSpring
     * Securityに認証情報を設定する。
     *
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param filterChain フィルタチェーン
     * @throws ServletException サーブレット例外が発生した場合
     * @throws IOException 入出力例外が発生した場合
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = extractTokenFromCookie(request);

        if (token != null) {
            try {
                TokenPayload payload = authProvider.verifyToken(token);

                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(
                                payload.userId(), null, List.of()
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (InvalidTokenException e) {
                // トークンが無効でも、ここでは弾かない。
                // 後続の認可設定(SecurityConfig)で、保護対象のエンドポイントなら401が返る。
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * リクエストのCookieからJWTトークンを取り出す。指定されたCookie名に一致するCookieが存在すれば、その値を返す。
     * 存在しない場合はnullを返す。
     *
     * @param request HTTPリクエスト
     * @return JWTトークンの文字列、またはnull
     */
    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
