package com.myapp.template.usecase.port.out;

/**
 * トークンの発行と検証を行うためのインターフェース。
 *
 * @author nahaton 2026/07/19
 */
public interface AuthProvider {

    // トークンを発行する
    String issueToken(Long userId, String email);

    // トークンを検証する
    TokenPayload verifyToken(String token);
}
