package com.myapp.template.usecase.port.in;

import com.myapp.template.domain.model.User;

import java.util.List;

public interface ListUsersUseCase {
    List<User> listAll();
}