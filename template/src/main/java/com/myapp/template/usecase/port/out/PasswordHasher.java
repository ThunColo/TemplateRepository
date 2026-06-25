package com.myapp.template.usecase.port.out;

/**
 * パスワードハッシュ化のためのインターフェース。
 * 
 * @author nahaton 2026/06/13
 */
public interface PasswordHasher {

    /**
     * パスワードをハッシュ化する。
     * 
     * @param rawPassword 生のパスワード
     * @return ハッシュ化されたパスワード
     */
    String hash(String rawPassword);

    /**
     * 生のパスワードとハッシュ化されたパスワードが一致するかを検証する。
     * 
     * @param rawPassword    生のパスワード
     * @param hashedPassword ハッシュ化されたパスワード
     * @return 一致する場合はtrue、そうでない場合はfalse
     */
    boolean matches(String rawPassword, String hashedPassword);
}