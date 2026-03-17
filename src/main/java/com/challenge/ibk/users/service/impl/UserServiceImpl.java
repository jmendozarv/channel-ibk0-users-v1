package com.challenge.ibk.users.service.impl;

import com.challenge.ibk.users.entity.UserEntity;
import com.challenge.ibk.users.exception.DuplicateEmailException;
import com.challenge.ibk.users.util.Messages;
import com.challenge.ibk.users.mapper.UserMapper;
import com.challenge.ibk.users.model.UserCreateRequest;
import com.challenge.ibk.users.model.UserCreateResponse;
import com.challenge.ibk.users.repository.UserRepository;
import com.challenge.ibk.users.service.UserService;
import com.challenge.ibk.users.validator.UserValidator;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final UserValidator userValidator;

  @Override
  public Mono<UserCreateResponse> register(UserCreateRequest request) {
    return Mono.fromCallable(() -> createUser(request))
        .subscribeOn(Schedulers.boundedElastic());
  }

  private UserCreateResponse createUser(UserCreateRequest request) {

    userValidator.validate(request);

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new DuplicateEmailException(Messages.DUPLICATE_EMAIL);
    }
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

    UserEntity userEntity = userMapper.toEntity(request);
    userEntity.setId(UUID.randomUUID());
    userEntity.setCreated(now);
    userEntity.setModified(now);
    userEntity.setLastLogin(now);
    userEntity.setToken(UUID.randomUUID().toString());
    userEntity.setIsactive(Boolean.TRUE);

    if (userEntity.getPhones() != null) {
      userEntity.getPhones().forEach(phone -> phone.setUser(userEntity));
    }

    UserEntity savedUser = userRepository.save(userEntity);
    return userMapper.toResponse(savedUser);
  }
}
