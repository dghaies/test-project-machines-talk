/**
 * Implémentation de l'API utilisateur
 *
 * @author dghaies jihed
 */
package org.machinestalk.api.impl;

import org.machinestalk.api.UserApi;
import org.machinestalk.api.dto.UserDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.Address;
import org.machinestalk.domain.User;
import org.machinestalk.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserApiImpl implements UserApi {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserApiImpl(final UserService userService, final ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto register(final UserRegistrationDto userRegistrationDto) {
        User savedUser = userService.registerUser(userRegistrationDto);
        return convertToUserDto(savedUser);
    }

    @Override
    public Mono<UserDto> findUserById(long userId) {
        return userService.getById(userId)
                .map(this::convertToUserDto);
    }

    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(String.valueOf(user.getId()));

        // Convertir les adresses en chaînes formatées
        List<String> formattedAddresses = user.getAddresses().stream()
                .map(Address::toString)
                .collect(Collectors.toList());

        UserDto.UserInfos userInfos = new UserDto.UserInfos(
                user.getFirstName(),
                user.getLastName(),
                user.getDepartment().getName(),
                formattedAddresses
        );

        userDto.setUserInfos(userInfos);
        return userDto;
    }
}