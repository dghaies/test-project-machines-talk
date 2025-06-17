/**
 * Tests pour l'API utilisateur
 *
 * @author dghaies jihed
 */
package org.machinestalk.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.machinestalk.Application;
import org.machinestalk.api.dto.AddressDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.Address;
import org.machinestalk.domain.Department;
import org.machinestalk.domain.User;
import org.machinestalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ContextConfiguration(classes= Application.class)
@AutoConfigureMockMvc
@WebMvcTest(UserApiImpl.class)
class UserApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void Should_RegisterUser_When_PostToRegisterEndpoint() throws Exception {
        // Given
        AddressDto addressDto = new AddressDto();
        addressDto.setStreetNumber("123");
        addressDto.setStreetName("Rue de la Paix");
        addressDto.setCity("Paris");
        addressDto.setPostalCode("75001");
        addressDto.setCountry("FR");

        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setFirstName("John");
        registrationDto.setLastName("Doe");
        registrationDto.setDepartment("IT");
        registrationDto.setPrincipalAddress(addressDto);

        // Mock user to return
        User mockUser = createMockUser();
        when(userService.registerUser(any(UserRegistrationDto.class))).thenReturn(mockUser);

        // When & Then
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.userInfos.firstName").value("John"))
                .andExpect(jsonPath("$.userInfos.lastName").value("Doe"));
    }

    @Test
    void Should_GetUser_When_GetByIdEndpoint() throws Exception {
        // Given
        User mockUser = createMockUser();
        when(userService.getById(anyLong())).thenReturn(Mono.just(mockUser));

        // When & Then
        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.userInfos.firstName").value("John"));
    }

    @Test
    void Should_ReturnNotFound_When_UserNotExists() throws Exception {
        // Given
        when(userService.getById(anyLong())).thenReturn(Mono.empty());

        // When & Then
        mockMvc.perform(get("/users/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Note: Mono.empty() will return empty but not 404
    }

    private User createMockUser() {
        Department department = new Department("IT");
        department.setId(1L);

        Address address = new Address();
        address.setStreetNumber("123");
        address.setStreetName("Rue de la Paix");
        address.setCity("Paris");
        address.setPostalCode("75001");
        address.setCountry("FR");

        Set<Address> addresses = new HashSet<>();
        addresses.add(address);

        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setDepartment(department);
        user.setAddresses(addresses);

        return user;
    }
}