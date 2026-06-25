package com.myapp.template.adapter.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.template.usecase.port.in.DeleteUserUseCase;
import com.myapp.template.usecase.port.in.GetUserUseCase;
import com.myapp.template.usecase.port.in.ListUsersUseCase;
import com.myapp.template.usecase.port.in.RegisterUserCommand;
import com.myapp.template.usecase.port.in.RegisterUserUseCase;
import com.myapp.template.usecase.port.in.UpdateUserCommand;
import com.myapp.template.usecase.port.in.UpdateUserUseCase;

/**
 * HTTPリクエストをUseCaseに渡し、結果をHTTPレスポンスとして返すコントローラークラス。
 *
 * 各UseCaseインターフェースを個別に受け取る。
 * UseCase具象クラスを知らずに済むため、テストでモック化しやすく、Controllerが必要とする機能だけが明示的に表現される。
 *
 * @author nahaton 2026/06/25
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase; // ユーザー登録のユースケース    
    private final GetUserUseCase getUserUseCase; // ユーザー取得のユースケース
    private final ListUsersUseCase listUsersUseCase; // ユーザー一覧取得のユースケース
    private final UpdateUserUseCase updateUserUseCase; // ユーザー更新のユースケース
    private final DeleteUserUseCase deleteUserUseCase;  // ユーザー削除のユースケース

    /**
     * ユーザーコントローラーのコンストラクタ。各ユースケースを注入する。
     *
     * @param registerUserUseCase ユーザー登録のユースケース
     * @param getUserUseCase ユーザー取得のユースケース
     * @param listUsersUseCase ユーザー一覧取得のユースケース
     * @param updateUserUseCase ユーザー更新のユースケース
     * @param deleteUserUseCase ユーザー削除のユースケース
     *
     * @return ユーザーコントローラーのインスタンス
     */
    public UserController(
            RegisterUserUseCase registerUserUseCase,
            GetUserUseCase getUserUseCase,
            ListUsersUseCase listUsersUseCase,
            UpdateUserUseCase updateUserUseCase,
            DeleteUserUseCase deleteUserUseCase
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.listUsersUseCase = listUsersUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    /**
     * ユーザー登録のエンドポイント。リクエストボディからユーザー情報を受け取り、ユーザーを登録する。
     *
     * @param request ユーザー登録リクエスト
     * @return 登録されたユーザー情報を含むレスポンスエンティティ
     */
    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody RegisterUserRequest request) {
        RegisterUserCommand command = new RegisterUserCommand(
                request.email(), request.password(), request.name()
        );
        UserResponse response = UserResponse.from(registerUserUseCase.register(command));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * ユーザー取得のエンドポイント。指定されたIDのユーザー情報を取得する。
     *
     * @param id 取得するユーザーのID
     * @return 取得されたユーザー情報を含むレスポンスDTO
     */
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return UserResponse.from(getUserUseCase.getById(id));
    }

    /**
     * ユーザー一覧取得のエンドポイント。すべてのユーザー情報を取得する。
     *
     * @return ユーザー情報のリスト
     */
    @GetMapping
    public List<UserResponse> listAll() {
        return listUsersUseCase.listAll().stream()
                .map(UserResponse::from)
                .toList();
    }

    /**
     * ユーザー更新のエンドポイント。指定されたIDのユーザー情報を更新する。
     *
     * @param id 更新するユーザーのID
     * @param request 更新するユーザー情報を含むリクエストDTO
     * @return 更新されたユーザー情報を含むレスポンスDTO
     */
    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        UpdateUserCommand command = new UpdateUserCommand(id, request.name());
        return UserResponse.from(updateUserUseCase.update(command));
    }

    /**
     * ユーザー削除のエンドポイント。指定されたIDのユーザーを削除する。
     *
     * @param id 削除するユーザーのID
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        deleteUserUseCase.delete(id);
    }
}
