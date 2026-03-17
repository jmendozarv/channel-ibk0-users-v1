package com.challenge.ibk.users.service.impl;

import com.challenge.ibk.users.entity.UserEntity;
import com.challenge.ibk.users.exception.DuplicateEmailException;
import com.challenge.ibk.users.mapper.UserMapper;
import com.challenge.ibk.users.model.UserCreateRequest;
import com.challenge.ibk.users.model.UserCreateResponse;
import com.challenge.ibk.users.repository.UserRepository;
import com.challenge.ibk.users.validator.UserValidator;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @Mock
  private UserValidator userValidator;

  @InjectMocks
  private UserServiceImpl userService;

  private UserCreateRequest request;

  @BeforeEach
  void setUp() {
    request = new UserCreateRequest();
    request.setName("Juan Perez");
    request.setEmail("juan.perez@example.com");
    request.setPassword("Password1");
    request.setPhones(new ArrayList<>());
  }

  @Test
  void register_success() {
    // Arrange
    UserEntity toSave = new UserEntity();
    toSave.setPhones(new ArrayList<>());

    UserEntity saved = new UserEntity();
    saved.setPhones(new ArrayList<>());

    UserCreateResponse response = new UserCreateResponse();
    response.setEmail(request.getEmail());

    when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
    when(userMapper.toEntity(any(UserCreateRequest.class))).thenReturn(toSave);
    when(userRepository.save(any(UserEntity.class))).thenReturn(saved);
    when(userMapper.toResponse(saved)).thenReturn(response);

    // Act
    Mono<UserCreateResponse> result = userService.register(request);
    UserCreateResponse actual = result.block();

    // Assert
    assertNotNull(actual);
    assertEquals(request.getEmail(), actual.getEmail());
    verify(userValidator).validate(request);
    verify(userRepository).existsByEmail(request.getEmail());
    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  void register_duplicateEmail_throws() {
    when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

    DuplicateEmailException ex = assertThrows(DuplicateEmailException.class, () -> {
      userService.register(request).block();
    });

    assertNotNull(ex.getMessage());
    verify(userValidator).validate(request);
    verify(userRepository).existsByEmail(request.getEmail());
    verify(userRepository, never()).save(any());
  }

  @Test
  void register_validatorThrows_propagates() {
    doThrow(new IllegalArgumentException("invalid")).when(userValidator).validate(request);

    assertThrows(IllegalArgumentException.class, () -> userService.register(request).block());

    verify(userValidator).validate(request);
    verify(userRepository, never()).existsByEmail(any());
    verify(userRepository, never()).save(any());
  }
}

