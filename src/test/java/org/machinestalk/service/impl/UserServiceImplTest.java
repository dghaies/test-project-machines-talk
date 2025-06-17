/**
 * Tests pour UserServiceImpl
 *
 * @author dghaies jihed
 */
package org.machinestalk.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.machinestalk.api.dto.AddressDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.User;
import org.machinestalk.repository.UserRepository;
import org.machinestalk.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void Should_RegisterNewUser_When_RegisterUser() {
    // Given
    final AddressDto addressDto = new AddressDto();
    addressDto.setStreetNumber("20");
    addressDto.setStreetName("Rue de Voltaire");
    addressDto.setPostalCode("75015");
    addressDto.setCity("Paris");
    addressDto.setCountry("France");

    final UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
    userRegistrationDto.setFirstName("Jack");
    userRegistrationDto.setLastName("Sparrow");
    userRegistrationDto.setDepartment("RH");
    userRegistrationDto.setPrincipalAddress(addressDto);

    final User savedUser = new User();
    savedUser.setId(1L);
    savedUser.setFirstName("Jack");
    savedUser.setLastName("Sparrow");

    when(userRepository.save(any(User.class))).thenReturn(savedUser);

    // When
    final User result = userService.registerUser(userRegistrationDto);

    // Then
    assertNotNull(result);
    verify(userRepository, times(1)).save(any(User.class));
    assertEquals("Jack", result.getFirstName());
    assertEquals("Sparrow", result.getLastName());
  }

  @Test
  void Should_RetrieveUserByTheGivenId_When_GetById() {
    // Given
    final User user = new User();
    user.setId(12345L);
    user.setFirstName("Dupont");
    user.setLastName("Emilie");

    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    // When && Then
    StepVerifier.create(userService.getById(user.getId()))
            .assertNext(usr -> {
              assertNotNull(usr);
              assertEquals(user.getId(), usr.getId());
              assertEquals(user.getFirstName(), usr.getFirstName());
            })
            .verifyComplete();
  }
}