package com.myapp.template.adapter.out.persistence;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.myapp.template.domain.model.User;
import com.myapp.template.usecase.port.out.UserRepository;

/**
 * ユーザーリポジトリの実装クラス。JPAを使用してデータベースにアクセスする。
 *
 * @author nahaton 2026/06/14
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository; // JPAリポジトリのインスタンス

    /**
     * ユーザーリポジトリの実装クラスのコンストラクタ
     *
     * @param jpaRepository JPAリポジトリのインスタンス
     * @return UserRepositoryImplのインスタンス
     */
    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    /**
     * ユーザーを保存する
     *
     * @param user 保存するユーザー
     * @return 保存されたユーザー
     */
    @Override
    public User save(User user) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime createdAt = user.getCreatedAt() != null ? user.getCreatedAt() : now;
        UserJpaEntity entity = new UserJpaEntity(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getName(),
                createdAt,
                now);
        UserJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    /**
     * ユーザーをIDで検索する
     *
     * @param id ユーザーID
     * @return ユーザーIDに対応するユーザーのOptional
     */
    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    /**
     * ユーザーをメールアドレスで検索する
     *
     * @param email メールアドレス
     * @return メールアドレスに対応するユーザーのOptional
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toDomain);
    }

    /**
     * ユーザーのリストを全件取得する
     *
     * @return 全ユーザーのリスト
     */
    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    /**
     * ユーザーのメールアドレスが既に存在するかどうかを確認する
     *
     * @param email ユーザーのメールアドレス
     * @return メールアドレスが既に存在する場合はtrue、存在しない場合はfalse
     */
    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    /**
     * ユーザーをIDで削除する
     *
     * @param id ユーザーID
     */
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    /**
     * ユーザーエンティティをドメインモデルに変換する
     *
     * @param entity ユーザーエンティティ
     * @return ドメインモデルのユーザー
     */
    private User toDomain(UserJpaEntity entity) {
        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
