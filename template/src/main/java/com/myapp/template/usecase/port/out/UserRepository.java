package com.myapp.template.usecase.port.out;

import java.util.List;
import java.util.Optional;

import com.myapp.template.domain.model.User;
/**
* DBアクセスをするためのインターフェース
* @author nahaton 2026/06/12
*/
public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    boolean existsByEmail(String email);

    void deleteById(Long id);
}
