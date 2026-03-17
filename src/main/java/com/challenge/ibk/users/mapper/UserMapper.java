package com.challenge.ibk.users.mapper;

import com.challenge.ibk.users.entity.PhoneEntity;
import com.challenge.ibk.users.entity.UserEntity;
import com.challenge.ibk.users.model.PhoneRequest;
import com.challenge.ibk.users.model.PhoneResponse;
import com.challenge.ibk.users.model.UserCreateRequest;
import com.challenge.ibk.users.model.UserCreateResponse;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "modified", ignore = true)
  @Mapping(target = "lastLogin", ignore = true)
  @Mapping(target = "token", ignore = true)
  @Mapping(target = "isactive", ignore = true)
  @Mapping(target = "phones", source = "phones")
  UserEntity toEntity(UserCreateRequest request);

  List<PhoneEntity> toPhoneEntityList(List<PhoneRequest> phones);

  PhoneEntity toPhoneEntity(PhoneRequest phoneRequest);

  @Mapping(target = "lastLogin", source = "lastLogin")
  UserCreateResponse toResponse(UserEntity entity);

  PhoneResponse toPhoneResponse(PhoneEntity entity);

  List<PhoneResponse> toPhoneResponseList(List<PhoneEntity> entities);

  // Mapping helpers so MapStruct can convert between LocalDateTime and OffsetDateTime
  default OffsetDateTime map(LocalDateTime value) {
    return value == null ? null : value.atOffset(ZoneOffset.UTC);
  }

  default LocalDateTime map(OffsetDateTime value) {
    return value == null ? null : value.toLocalDateTime();
  }
}
