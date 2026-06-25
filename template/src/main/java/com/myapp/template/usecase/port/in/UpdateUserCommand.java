package com.myapp.template.usecase.port.in;

/**
 * 名前の更新のためのコマンドクラス。ユーザーIDと新しい名前を保持する。
 * 
 * @author nahaton 2026/06/12
 */
public record UpdateUserCommand(
        Long id,
        String name) {

}
