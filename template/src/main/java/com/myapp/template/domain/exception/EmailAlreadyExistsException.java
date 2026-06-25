package com.myapp.template.domain.exception;

/**
 * メールアドレスが既に存在する場合にスローされる例外クラス
 * 
 * @author nahaton 2026/06/01
 */
public class EmailAlreadyExistsException extends RuntimeException {

    /**
     * メールアドレスが既に存在する場合の例外を初期化します。
     * 
     * @param email メールアドレス
     * @return メールアドレスが既に存在する場合の例外
     */
    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }

}
