package com.myapp.template.usecase.port.in;

import com.myapp.template.domain.model.User;

public interface UpdateUserUseCase {
    User update(UpdateUserCommand command);
}