package com.myapp.template.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ユーザーデータの永続化を担当するJPAリポジトリインターフェース。 Spring Data JPAが実装を自動生成する。
 *
 * @author nahaton 2026/06/14
 */
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    /**
     * ユーザーのメールアドレスでユーザーを検索する
     *
     * @param email ユーザーのメールアドレス
     * @return ユーザーIDに対応するUserJpaEntityのOptional
     */
    Optional<UserJpaEntity> findByEmail(String email);

    /**
     * ユーザーのメールアドレスが既に存在するかどうかを確認する
     *
     * @param email ユーザーのメールアドレス
     * @return メールアドレスが既に存在する場合はtrue、存在しない場合はfalse
     */
    boolean existsByEmail(String email);
}
