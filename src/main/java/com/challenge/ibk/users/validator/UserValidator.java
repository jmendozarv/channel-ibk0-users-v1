package com.challenge.ibk.users.validator;

import com.challenge.ibk.users.model.UserCreateRequest;

public interface UserValidator {
  void validate(UserCreateRequest request);
}

