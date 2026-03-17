package com.challenge.ibk.users.controller;

import com.challenge.ibk.users.api.UsuariosApi;
import com.challenge.ibk.users.model.UserCreateRequest;
import com.challenge.ibk.users.model.UserCreateResponse;
import com.challenge.ibk.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserController implements UsuariosApi {

  private final UserService userService;

  @Override
  public Mono<ResponseEntity<UserCreateResponse>> registerUser(
      Mono<UserCreateRequest> userCreateRequest, ServerWebExchange exchange) {
    return userCreateRequest
        .flatMap(userService::register)
        .map(response -> ResponseEntity
            .status(201)
            .body(response));
  }
}
