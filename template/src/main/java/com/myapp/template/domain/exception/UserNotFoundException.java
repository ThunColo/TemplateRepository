package com.myapp.template.domain.exception;

/**
 * ユーザーが見つからない場合にスローされる例外クラス
 * 
 * @author nahaton 2026/06/01
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * ユーザーが見つからない場合の例外を初期化します。
     * 
     * @param userId ユーザーID
     * @return ユーザーが見つからない場合の例外
     */
    public UserNotFoundException(Long userId) {
        super("User not found with id: " + userId);
    }

}
