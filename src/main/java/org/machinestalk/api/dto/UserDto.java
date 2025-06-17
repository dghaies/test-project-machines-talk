/**
 * DTO pour les données utilisateur
 *
 * @author dghaies jihed
 */
package org.machinestalk.api.dto;
import java.util.List;

public class UserDto {

  /** User id */
  private String id;

  private UserInfos userInfos;

  public UserDto() {
  }

  public UserDto(final String id, final UserInfos userInfos) {
    this.id = id;
    this.userInfos = userInfos;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public UserInfos getUserInfos() {
    return userInfos;
  }

  public void setUserInfos(final UserInfos userInfos) {
    this.userInfos = userInfos;
  }

  public static class UserInfos {

    private String firstName;
    private String lastName;
    private String department;
    private List<String> adresses;

    public UserInfos() {}

    public UserInfos(
            final String firstName,
            final String lastName,
            final String department,
            final List<String> adresses) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.department = department;
      this.adresses = adresses;
    }

    // Getters et Setters
    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public String getDepartment() {
      return department;
    }

    public void setDepartment(String department) {
      this.department = department;
    }

    public List<String> getAdresses() {
      return adresses;
    }

    public void setAdresses(List<String> adresses) {
      this.adresses = adresses;
    }
  }
}