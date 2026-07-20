package com.myapp.template.usecase.interactor;

import com.myapp.template.domain.exception.AuthenticationFailedException;
import com.myapp.template.domain.model.User;
import com.myapp.template.usecase.port.in.LoginCommand;
import com.myapp.template.usecase.port.in.LoginResult;
import com.myapp.template.usecase.port.in.LoginUseCase;
import com.myapp.template.usecase.port.out.AuthProvider;
import com.myapp.template.usecase.port.out.PasswordHasher;
import com.myapp.template.usecase.port.out.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ログインユースケースの実装クラス。
 *
 * @author nahaton 2026/07/19
 */
@Service
@Transactional(readOnly = true)
public class LoginInteractor implements LoginUseCase {

    private final UserRepository userRepository; // ユーザーリポジトリ
    private final PasswordHasher passwordHasher; // パスワードハッシュ化ユーティリティ
    private final AuthProvider authProvider; // 認証プロバイダ

    /**
     * LoginInteractorクラスのコンストラクタ。依存するリポジトリやユーティリティを注入する。
     *
     * @param userRepository ユーザーリポジトリ
     * @param passwordHasher パスワードハッシュ化ユーティリティ
     * @param authProvider 認証プロバイダ
     */
    public LoginInteractor(UserRepository userRepository, PasswordHasher passwordHasher, AuthProvider authProvider) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.authProvider = authProvider;
    }

    /**
     * ユーザーのログインを処理する。
     * メールアドレスの検証結果とパスワードの検証結果を区別しない理由は、第三者によるメールアドレスの推測(ユーザー列挙攻撃)を防ぐためである。
     *
     * @param command ログインコマンド（ユーザーのメールアドレスとパスワードを含む）
     * @return ログイン結果（JWTトークン、ユーザーID、メールアドレス、名前を含む）
     */
    @Override
    public LoginResult login(LoginCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(AuthenticationFailedException::new);

        // パスワードが一致しない場合は認証失敗例外をスローする
        if (!passwordHasher.matches(command.rawPassword(), user.getPasswordHash())) {
            throw new AuthenticationFailedException();
        }

        String token = authProvider.issueToken(user.getId(), user.getEmail());
        return new LoginResult(token, user.getId(), user.getEmail(), user.getName());
    }
}
