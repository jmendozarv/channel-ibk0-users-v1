package com.challenge.ibk.users.service;

import com.challenge.ibk.users.model.UserCreateRequest;
import com.challenge.ibk.users.model.UserCreateResponse;
import reactor.core.publisher.Mono;

public interface UserService {

  Mono<UserCreateResponse> register(UserCreateRequest request);
}
