/**
 * Tests pour UserRepository
 *
 * @author dghaies jihed
 */
package org.machinestalk.repository;

import org.machinestalk.domain.Address;
import org.machinestalk.domain.Department;
import org.machinestalk.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {
  Address address;
  User user;
  Department department;

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void init(){
    // Given
    address = new Address();
    address.setStreetNumber("22");
    address.setStreetName("Rue Voltaire");
    address.setPostalCode("75012");
    address.setCity("Paris");
    address.setCountry("France");

    department = new Department("IT");

    user = new User();
    user.setFirstName("Jack");
    user.setLastName("Sparrow");
    user.setDepartment(department);
    user.setAddresses(singleton(address));
  }

  @Test
  void Should_PersistNewUser_When_Save() {
    // when
    User result = userRepository.save(user);

    // Then
    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals(user.getFirstName(), result.getFirstName());
    assertEquals(user.getLastName(), result.getLastName());
  }

  @Test
  void Should_GetUserByItsId_When_FindById() {
    // Given
    User savedUser = userRepository.save(user);

    // When
    Optional<User> result = userRepository.findById(savedUser.getId());

    // Then
    assertTrue(result.isPresent());
    assertEquals(savedUser.getId(), result.get().getId());
  }

  @Test
  void Should_returnEmptyOptional_When_FindById_ForUserThatNotExistsByTheGivenId() {
    // When
    Optional<User> result = userRepository.findById(5678L);

    // Then
    assertFalse(result.isPresent());
  }
}