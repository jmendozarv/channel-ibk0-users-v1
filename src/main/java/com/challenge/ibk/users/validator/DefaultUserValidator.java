package com.challenge.ibk.users.validator;

import com.challenge.ibk.users.config.AppProperties;
import com.challenge.ibk.users.exception.BadRequestException;
import com.challenge.ibk.users.model.UserCreateRequest;
import com.challenge.ibk.users.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class DefaultUserValidator implements UserValidator {

  private final AppProperties appProperties;

  private static final Pattern DEFAULT_EMAIL_PATTERN =
      Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

  @Override
  public void validate(UserCreateRequest request) {
    if (request == null) {
      throw new BadRequestException(Messages.REQUEST_NULL);
    }

    if (request.getName() == null || request.getName().trim().isEmpty()) {
      throw new BadRequestException(Messages.NAME_REQUIRED);
    }

    if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
      throw new BadRequestException(Messages.EMAIL_REQUIRED);
    }

    String emailRegex = appProperties.getEmailRegex();
    Pattern emailPattern = emailRegex != null && !emailRegex.trim().isEmpty()
        ? Pattern.compile(emailRegex)
        : DEFAULT_EMAIL_PATTERN;

    if (!emailPattern.matcher(request.getEmail()).matches()) {
      throw new BadRequestException(Messages.EMAIL_INVALID);
    }

    String password = request.getPassword();
    String passwordRegex = appProperties.getPasswordRegex();
    if (password == null) {
      throw new BadRequestException(Messages.PASSWORD_MIN_LENGTH);
    }

    if (passwordRegex != null && !passwordRegex.trim().isEmpty()) {
      Pattern pwPattern = Pattern.compile(passwordRegex);
      if (!pwPattern.matcher(password).matches()) {
        throw new BadRequestException(Messages.PASSWORD_MIN_LENGTH);
      }
    } else {
      if (password.length() < 6) {
        throw new BadRequestException(Messages.PASSWORD_MIN_LENGTH);
      }
    }
  }
}

