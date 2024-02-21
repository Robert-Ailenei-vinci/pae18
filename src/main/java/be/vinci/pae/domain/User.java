package be.vinci.pae.domain;

/**
 * User
 */
public interface User {

  Integer getAge();

  void setAge(Integer age);

  Boolean isMarried();

  void setMarried(Boolean married);

  String getLogin();

  void setLogin(String login);

  int getId();

  void setId(int id);

  String getPassword();

  void setPassword(String password);

  boolean checkPassword(String password);

  String hashPassword(String password);
}
