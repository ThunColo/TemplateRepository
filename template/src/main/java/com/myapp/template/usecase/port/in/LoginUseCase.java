package com.myapp.template.usecase.port.in;

/**
 * ログインユースケースのインターフェース。ユーザーのログイン処理を定義する。
 *
 * @author nahaton 2026/07/19
 */
public interface LoginUseCase {

    /**
     * ユーザーのログインを処理する。
     *
     * @param command ログインコマンド（ユーザーのメールアドレスとパスワードを含む）
     * @return ログイン結果（JWTトークン、ユーザーID、メールアドレス、名前を含む）
     */
    LoginResult login(LoginCommand command);
}
