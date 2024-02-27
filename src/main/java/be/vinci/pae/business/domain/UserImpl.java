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
  private String name;
  private String firstname;
  private String phone_num;
  private String inscriptionDate;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getPhone_num() {
    return phone_num;
  }

  public void setPhone_num(String phone_num) {
    this.phone_num = phone_num;
  }

  public String getInscriptionDate() {
    return inscriptionDate;
  }

  public void setInscriptionDate(String inscriptionDate) {
    this.inscriptionDate = inscriptionDate;
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
    if (role.equals("Etudiant") || role.equals("Professeur") || role.equals("Administratif")) {
      return role;
    } else {
      return null;
    }
  }

  @Override
  public void setRole(String role) {
    this.role = role;
  }

}
