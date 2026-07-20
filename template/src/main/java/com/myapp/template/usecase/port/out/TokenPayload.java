package com.myapp.template.usecase.port.out;

/**
 * トークンの検証結果を表現するレコードクラス。 トークンの中に埋め込まれた情報を取り出した結果を保持する為の不変オブジェクト。
 *
 * @author nahaton 2026/07/19
 */
public record TokenPayload(
        Long userId, // ユーザーID
        String email // ユーザーのメールアドレス
        ) {

}
