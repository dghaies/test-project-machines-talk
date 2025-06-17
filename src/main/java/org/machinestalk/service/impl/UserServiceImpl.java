/**
 * Implémentation du service utilisateur
 *
 * @author dghaies jihed
 */
package org.machinestalk.service.impl;

import org.machinestalk.api.dto.AddressDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.Address;
import org.machinestalk.domain.Department;
import org.machinestalk.domain.User;
import org.machinestalk.repository.UserRepository;
import org.machinestalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User registerUser(final UserRegistrationDto userRegistrationDto) {
    // Créer le département
    Department department = new Department(userRegistrationDto.getDepartment());

    // Créer l'utilisateur
    User user = new User();
    user.setFirstName(userRegistrationDto.getFirstName());
    user.setLastName(userRegistrationDto.getLastName());
    user.setDepartment(department);

    // Créer les adresses
    Set<Address> addresses = new HashSet<>();

    // Adresse principale
    if (userRegistrationDto.getPrincipalAddress() != null) {
      addresses.add(convertToAddress(userRegistrationDto.getPrincipalAddress()));
    }

    // Adresse secondaire (optionnelle)
    if (userRegistrationDto.getSecondaryAddress() != null) {
      addresses.add(convertToAddress(userRegistrationDto.getSecondaryAddress()));
    }

    user.setAddresses(addresses);

    return userRepository.save(user);
  }

  @Override
  public Optional<User> getById(final long id) {
    Optional<User> user = userRepository.findById(id);
    return user;
  }

  private Address convertToAddress(AddressDto addressDto) {
    Address address = new Address();
    address.setStreetNumber(addressDto.getStreetNumber());
    address.setStreetName(addressDto.getStreetName());
    address.setCity(addressDto.getCity());
    address.setPostalCode(addressDto.getPostalCode());
    address.setCountry(addressDto.getCountry());
    return address;
  }
}