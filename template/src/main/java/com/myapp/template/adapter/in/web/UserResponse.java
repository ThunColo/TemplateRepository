package com.myapp.template.adapter.in.web;

import java.time.OffsetDateTime;

import com.myapp.template.domain.model.User;

/**
 * ユーザーからのレスポンスデータを表すクラス。
 *
 * passwordHash はAPIレスポンスに含めてしまうとセキュリティ上のリスクがある。 よって外部に返すDTOで明示的に除外する。
 *
 * @author nahaton 2026/06/21
 */
public record UserResponse(
        Long id,
        String email,
        String name,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
        ) {

    /**
     * ユーザードメインモデルからUserResponse DTOを作成するファクトリメソッド。
     *
     * @param user ユーザードメインモデル
     * @return ユーザードメインモデルから変換されたUserResponse DTO
     */
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
