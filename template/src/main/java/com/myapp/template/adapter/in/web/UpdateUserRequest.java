package com.myapp.template.adapter.in.web;

/**
 * ユーザー更新のリクエストデータを表すクラス。ユーザーの名前を含む。
 *
 * @author nahaton 2026/06/14
 */
public record UpdateUserRequest(
        String name
        ) {

}
