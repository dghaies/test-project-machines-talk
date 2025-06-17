package org.machinestalk.service;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UserService {

  /**
   * Register a new user.
   *
   * @param userRegistrationDto dto for user registration.
   * @return user entity.
   */
  User registerUser(@NotNull @Valid UserRegistrationDto userRegistrationDto);

  /**
   * Get user by its id.
   *
   * @param id user id.
   * @return user entity.
   */
  Optional<User> getById(long id);
}