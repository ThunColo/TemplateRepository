package com.myapp.template.usecase.port.in;

/**
 * 入力用のログインコマンドを表すレコードクラス。
 *
 * @author nahaton 2026/07/19
 */
/**
 * LoginCommandクラスのコンストラクタ。ユーザーが入力したログイン情報を保持する。
 *
 * @param email ユーザーのメールアドレス
 * @param rawPassword ユーザーが入力した生のパスワード
 */
public record LoginCommand(
        String email, // ユーザーのメールアドレス
        String rawPassword // ユーザーが入力した生のパスワード
        ) {

}
