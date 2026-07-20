package com.myapp.template.adapter.in.web;

/**
 * ログインのリクエストボディを表すDTOクラス。
 *
 * @author nahaton 2026/06/25
 */
public record LoginRequest(
        String email, // ユーザーのメールアドレス
        String password // ユーザーのパスワード
        ) {

}
