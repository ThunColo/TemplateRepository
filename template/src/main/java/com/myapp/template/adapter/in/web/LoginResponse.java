package com.myapp.template.adapter.in.web;

import com.myapp.template.usecase.port.in.LoginResult;

/**
 * ログインのレスポンスボディを表すDTOクラス。
 *
 * @author nahaton 2026/07/19
 * @update nahaton 2026/07/19 HttpOnly Cookie方式の実装のため、tokenフィールドを削除。
 */
public record LoginResponse(
        Long userId, // ユーザーID
        String email, // ユーザーのメールアドレス
        String name // ユーザーの名前
        ) {

    /**
     * LoginResultオブジェクトからLoginResponseオブジェクトを生成する。
     *
     * @param result LoginResultオブジェクト
     * @return LoginResponseオブジェクト
     */
    public static LoginResponse from(LoginResult result) {
        return new LoginResponse(
                result.userId(),
                result.email(),
                result.name()
        );
    }
}
