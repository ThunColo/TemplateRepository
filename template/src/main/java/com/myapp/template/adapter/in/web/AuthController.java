package com.myapp.template.adapter.in.web;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.template.usecase.port.in.LoginCommand;
import com.myapp.template.usecase.port.in.LoginResult;
import com.myapp.template.usecase.port.in.LoginUseCase;

/**
 * 認証関連のAPIエンドポイントを提供するコントローラークラス。ログインとログアウトの処理を担当する。
 *
 * @author nahaton 2026/07/19
 * @update nahaton 2026/07/19 HttpOnly Cookie方式の実装のため、処理を修正。
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final String COOKIE_NAME = "accessToken"; // JWTトークンを格納するCookieの名前

    private final LoginUseCase loginUseCase; // ログイン処理を行うユースケース

    /**
     * AuthControllerのコンストラクタ。LoginUseCaseを注入する。
     *
     * @param loginUseCase ログイン処理を行うユースケース
     */
    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    /**
     * ログイン処理を行うエンドポイント。リクエストボディに含まれるメールアドレスとパスワードを使用して認証を行い、成功した場合はJWTトークンをHttpOnly
     * Cookieとして返す。
     *
     * @param request ログインリクエストのDTO
     * @return ログイン成功時は200 OKとともにLoginResponseを返す。失敗時は適切なエラーレスポンスを返す。
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginCommand command = new LoginCommand(request.email(), request.password());
        LoginResult result = loginUseCase.login(command);

        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, result.token())
                .httpOnly(true)
                .secure(false) // 本番環境(HTTPS)では true にすること
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(LoginResponse.from(result));
    }

    /**
     * ログアウト処理を行うエンドポイント。クライアントに設定されているJWTトークンを無効化するため、HttpOnly Cookieを削除する。
     * 同名のCookieを空の値・有効期限0で上書きすることで、ブラウザにCookieを削除させる。
     * より厳密な運用ではトークンの即時失効機構(Redisなどでの管理)が必要になる場合あり。
     *
     * @return 204 No Contentを返す。
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
