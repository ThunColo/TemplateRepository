package com.myapp.template.usecase.interactor;

import com.myapp.template.domain.exception.EmailAlreadyExistsException;
import com.myapp.template.domain.exception.UserNotFoundException;
import com.myapp.template.domain.model.User;
import com.myapp.template.usecase.port.in.*;
import com.myapp.template.usecase.port.out.PasswordHasher;
import com.myapp.template.usecase.port.out.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ユーザーに関するユースケースの実装クラス。
 * 
 * @author nahaton 2026/06/01
 */
@Service
@Transactional
public class UserInteractor implements
        RegisterUserUseCase,
        GetUserUseCase,
        ListUsersUseCase,
        UpdateUserUseCase,
        DeleteUserUseCase {

    private final UserRepository userRepository; // 永続化のためのリポジトリ
    private final PasswordHasher passwordHasher; // パスワードのハッシュ化を担当するコンポーネント

    /**
     * コンストラクタ
     * 
     * @param userRepository ユーザーデータの永続化を担当するリポジトリ
     * @param passwordHasher パスワードのハッシュ化を担当するコンポ
     * 
     * @return UserInteractorのインスタンス
     */
    public UserInteractor(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    /**
     * ユーザーを登録する
     * 
     * @param command ユーザー登録のためのコマンド
     * @return 登録されたユーザー
     */
    @Override
    public User register(RegisterUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyExistsException(command.email());
        }
        String hashed = passwordHasher.hash(command.rawPassword());
        User newUser = new User(null, command.email(), hashed, command.name(), null, null);
        return userRepository.save(newUser);
    }

    /**
     * ユーザーをIDで取得する
     * 
     * @param id ユーザーID
     * @return ユーザーIDに対応するユーザー
     */
    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * ユーザーのリストを全件取得する
     * 
     * @return ユーザーのリスト
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> listAll() {
        return userRepository.findAll();
    }

    /**
     * ユーザーを更新する
     * 
     * @param command ユーザー更新のためのコマンド
     * @return 更新されたユーザー
     */
    @Override
    public User update(UpdateUserCommand command) {
        User existing = userRepository.findById(command.id())
                .orElseThrow(() -> new UserNotFoundException(command.id()));

        User updated = new User(
                existing.getId(),
                existing.getEmail(),
                existing.getPasswordHash(),
                command.name(),
                existing.getCreatedAt(),
                null);
        return userRepository.save(updated);
    }

    /**
     * ユーザーを削除する
     * 
     * @param id ユーザーIDー
     */
    @Override
    public void delete(Long id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}