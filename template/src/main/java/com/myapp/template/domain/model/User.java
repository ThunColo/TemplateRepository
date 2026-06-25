package com.myapp.template.domain.model;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * ユーザー情報を管理するクラス
 * 
 * @author nahaton 2026/06/01
 */
public class User {

    private final Long id;
    private final String email;
    private final String passwordHash;
    private final String name;
    private final OffsetDateTime createdAt; // ユーザーの作成日時
    private final OffsetDateTime updatedAt; // ユーザーの最終更新日時

    /**
     * ユーザーを初期化します。
     * 
     * @param id           ユーザーのID
     * @param email        ユーザーのメールアドレス
     * @param passwordHash ユーザーのパスワードハッシュ
     * @param name         ユーザーの名前
     * @param createdAt    ユーザーの作成日時
     * @param updatedAt    ユーザーの最終更新日時
     */
    public User(Long id, String email, String passwordHash, String name,
            OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.email = Objects.requireNonNull(email, "email must not be null");
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        if (!email.contains("@")) { // メールアドレスが"@"を含む場合
            throw new IllegalArgumentException("email format is invalid: " + email);
        }
        if (name.isBlank()) { // 名前が空白の場合
            throw new IllegalArgumentException("name must not be blank");
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getName() {
        return name;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}