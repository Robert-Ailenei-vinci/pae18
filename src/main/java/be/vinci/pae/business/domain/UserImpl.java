package be.vinci.pae.business.domain;

import org.mindrot.jbcrypt.BCrypt;

/**
 * This class represents an implementation of the {@link User} interface.
 */
public class UserImpl implements User {

  private int id;
  private String email;
  private String password;
  private Integer age;
  private Boolean married;
  private String role;


  @Override
  public Integer getAge() {
    return age;
  }


  @Override
  public void setAge(Integer age) {
    this.age = age;
  }


  @Override
  public Boolean isMarried() {
    return married;
  }


  @Override
  public void setMarried(Boolean married) {
    this.married = married;
  }

  //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)


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
  public String setRole(String role) {
    return this.role = role;

  }

}
