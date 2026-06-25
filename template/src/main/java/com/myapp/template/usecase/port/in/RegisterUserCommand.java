package com.myapp.template.usecase.port.in;

/**
 * 入力用のコマンドクラス。ユーザー登録のためのデータを保持する。
 * 
 * @author nahaton 2026/06/12
 */
public record RegisterUserCommand(
        String email,
        String rawPassword, // 生のパスワード
        String name) {
}
