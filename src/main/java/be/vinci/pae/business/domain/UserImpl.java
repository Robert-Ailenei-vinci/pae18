package be.vinci.pae.business.domain;

import org.mindrot.jbcrypt.BCrypt;

/**
 * This class represents an implementation of the {@link User} interface.
 */
public class UserImpl implements User {

  private int id;
  private String email;
  private String password;
  private String role;
  private String lastName;
  private String firstName;
  private String phoneNum;
  private String registrationDate;

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
  }

  @Override
  public String getEmail() {
    return email;
  }


  @Override
  public void setEmail(String email) {
    this.email = email;
  }


  @Override
  public int getId() {
    return id;
  }


  @Override
  public void setId(int id) {
    this.id = id;
  }


  @Override
  public String getPassword() {
    return password;
  }


  @Override
  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }


  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }


  public String toString() {
    return "{id:" + id + ", email:" + email + ", password:" + password + "}";
  }

  @Override
  public String getRole() {
    return role;
  }

  @Override
  public void setRole(String role) {
    this.role = role;
  }

}
