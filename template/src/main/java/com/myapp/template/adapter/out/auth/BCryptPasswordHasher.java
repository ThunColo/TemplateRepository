package com.myapp.template.adapter.out.auth;

import com.myapp.template.usecase.port.out.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * BCryptを使用してパスワードをハッシュ化するクラス。
 * 
 * BCryptは、パスワードのハッシュ化に広く使用されているアルゴリズムで、セキュリティが高いとされている。
 * SpringのDI管理対象とすることで、UserInteractorのコンストラクタに自動で注入させる。
 * 
 * @author nahaton 2026/06/13
 */
@Component
public class BCryptPasswordHasher implements PasswordHasher {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * パスワードをBCryptでハッシュ化する。
     * 
     * @param rawPassword 生のパスワード
     * @return ハッシュ化されたパスワード
     */
    @Override
    public String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * 生のパスワードとハッシュ化されたパスワードが一致するかを検証する。
     * 
     * @param rawPassword    生のパスワード
     * @param hashedPassword ハッシュ化されたパスワード
     * @return 一致する場合はtrue、そうでない場合はfalse
     */
    @Override
    public boolean matches(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}