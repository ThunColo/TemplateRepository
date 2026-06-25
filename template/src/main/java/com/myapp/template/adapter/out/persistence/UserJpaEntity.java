package com.myapp.template.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

/**
 * ユーザーエンティティクラス。JPAを使用してデータベースにマッピングされる。
 * 
 * @author nahaton 2026/06/14
 */
@Entity
@Table(name = "users")
public class UserJpaEntity {

    @Id // 主キー
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動採番されるID
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt; // ユーザーの作成日時

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt; // ユーザーの最終更新日時

    protected UserJpaEntity() {
        // JPA仕様のため必要
    }

    /**
     * コンストラクタ
     * 
     * @param id           ユーザーID（新規作成時はnull）
     * @param email        ユーザーのメールアドレス
     * @param passwordHash ユーザーのパスワードのハッシュ値
     * @param name         ユーザーの名前
     * @param createdAt    ユーザーの作成日時
     * @param updatedAt    ユーザーの最終更新日時
     * @return UserJpaEntityのインスタンス
     */
    public UserJpaEntity(Long id, String email, String passwordHash, String name,
            OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * ユーザーのIDを取得する
     * 
     * @return ユーザーID
     */
    public Long getId() {
        return id;
    }

    /**
     * ユーザーのメールアドレスを取得する
     * 
     * @return ユーザーのメールアドレス
     */
    public String getEmail() {
        return email;
    }

    /**
     * ユーザーのパスワードのハッシュ値を取得する
     * 
     * @return ユーザーのパスワードのハッシュ値
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * ユーザーの名前を取得する
     * 
     * @return ユーザーの名前
     */
    public String getName() {
        return name;
    }

    /**
     * ユーザーの作成日時を取得する
     * 
     * @return ユーザーの作成日時
     */
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * ユーザーの最終更新日時を取得する
     * 
     * @return ユーザーの最終更新日時
     */
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}