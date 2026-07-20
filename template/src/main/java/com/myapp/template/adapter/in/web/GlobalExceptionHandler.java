package com.myapp.template.adapter.in.web;

import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.myapp.template.domain.exception.AuthenticationFailedException;
import com.myapp.template.domain.exception.EmailAlreadyExistsException;
import com.myapp.template.domain.exception.InvalidTokenException;
import com.myapp.template.domain.exception.UserNotFoundException;

/**
 * グローバル例外ハンドラー。
 *
 * adapter層でドメイン例外をHTTPに変換することで、UseCase層やドメイン層はHTTPの存在を知らないという構造を維持している。
 *
 * @author nahaton 2026/06/25
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ユーザーが見つからない場合の例外を処理するハンドラメソッド。
     *
     * @param e UserNotFoundException
     * @return レスポンスエンティティ
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    /**
     * ユーザーのメールアドレスが既に存在する場合の例外を処理するハンドラメソッド。
     *
     * @param e EmailAlreadyExistsException
     * @return レスポンスエンティティ
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailExists(EmailAlreadyExistsException e) {
        return buildResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    /**
     * 不正な引数が渡された場合の例外を処理するハンドラメソッド。
     *
     * @param e IllegalArgumentException
     * @return レスポンスエンティティ
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException e) {
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * その他の例外を処理するハンドラメソッド。
     *
     * 本番環境ではスタックトレースを返してしまうとセキュリティリスクになる。
     * 想定外の例外が発生したときに詳細をクライアントに返さないための防御策として、500 Internal Server Errorを返す。
     *
     * @param e Exception
     * @reurn レスポンスエンティティ
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception e) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    /**
     * レスポンスエンティティを構築するユーティリティメソッド。
     *
     * @param status HTTPステータス
     * @param message エラーメッセージ
     */
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationFailed(AuthenticationFailedException e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    /**
     * レスポンスエンティティを構築するユーティリティメソッド。
     *
     * @param status HTTPステータス
     * @param message エラーメッセージ
     * @return レスポンスエンティティ
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidToken(InvalidTokenException e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    /**
     * レスポンスエンティティを構築するユーティリティメソッド。
     *
     * @param status HTTPステータス
     * @param message エラーメッセージ
     * @return レスポンスエンティティ
     */
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", OffsetDateTime.now(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        ));
    }
}
