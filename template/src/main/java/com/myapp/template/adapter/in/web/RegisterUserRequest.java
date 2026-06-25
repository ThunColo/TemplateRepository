package com.myapp.template.adapter.in.web;
/**
 * ユーザー登録のリクエストデータを表すクラス。ユーザーのメールアドレス、パスワード、名前を含む。
 * 
 * @author nahaton 2026/06/14
 */
public record RegisterUserRequest(
        String email,
        String password,
        String name
) {}