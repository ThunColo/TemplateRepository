package com.myapp.template.usecase.port.in;

/**
 * ログイン結果を表すレコードクラス。
 *
 * @author nahaton 2026/07/19
 */
/**
 * LoginResultクラスのコンストラクタ。ログイン結果の情報を保持する。
 *
 * @param token 発行されたJWTトークン
 * @param userId ユーザーID
 * @param email ユーザーのメールアドレス
 * @param name ユーザーの名前
 */
public record LoginResult(
        String token, // 発行されたJWTトークン
        Long userId, // ユーザーID
        String email, // ユーザーのメールアドレス     
        String name // ユーザーの名前
        ) {

}
