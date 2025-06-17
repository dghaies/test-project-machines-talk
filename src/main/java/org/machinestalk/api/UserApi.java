/**
 * API REST pour la gestion des utilisateurs
 *
 * @author dghaies jihed
 */
package org.machinestalk.api;

import org.machinestalk.api.dto.UserDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public interface UserApi {

  /**
   * Register a new user.
   *
   * @param userRegistrationDto DTO that input data needed to register a new user.
   * @return registered user infos.
   */
  @PostMapping("/users/register")
  UserDto register(@RequestBody @NotNull @Valid UserRegistrationDto userRegistrationDto);

  /**
   * Get user details by id.
   *
   * @param userId user id
   * @return user infos
   */
  @GetMapping("/users/{userId}")
  Mono<UserDto> findUserById(@PathVariable("userId") long userId);
}