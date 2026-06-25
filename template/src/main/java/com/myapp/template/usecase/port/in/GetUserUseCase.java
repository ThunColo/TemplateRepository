package com.myapp.template.usecase.port.in;

import com.myapp.template.domain.model.User;

public interface GetUserUseCase {
    User getById(Long id);

}
