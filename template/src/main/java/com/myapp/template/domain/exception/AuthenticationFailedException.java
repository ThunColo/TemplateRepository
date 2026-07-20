package com.myapp.template.domain.exception;

/**
 * 認証に失敗した場合にスローされる例外クラス。
 *
 * @author nahaton 2026/07/19
 */
public class AuthenticationFailedException extends RuntimeException {

    /**
     * AuthenticationFailedExceptionクラスのコンストラクタ。認証に失敗した場合の例外を初期化する。
     *
     * @param message 例外メッセージ
     */
    public AuthenticationFailedException() {
        super("Invalid email or password");
    }
}
