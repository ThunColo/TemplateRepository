package com.myapp.template.domain.exception;

/**
 * 無効なトークンが使用された場合にスローされる例外クラス。
 *
 * @author nahaton 2026/07/19
 */
public class InvalidTokenException extends RuntimeException {

    /**
     * ユーザーが見つからない場合の例外を初期化する。
     *
     * @param message 例外メッセージ
     * @return InvalidTokenExceptionのインスタンス
     */
    public InvalidTokenException(String message) {
        super(message);
    }
}
