package be.vinci.pae.domain;

/**
 * User.
 */
public interface User {

  /**
   * return l'age de l'user.
   *
   * @return l'age de l'user.
   */
  Integer getAge();

  /**
   * change l'age du user.
   *
   * @param age nouvelle age du user.
   */
  void setAge(Integer age);

  /**
   * return si le user est marrié ou pas.
   *
   * @return si le user est marrié ou pas.
   */
  Boolean isMarried();

  /**
   * change l'etat de marriage du user.
   *
   * @param married le nouvelle etat de marriage du user.
   */
  void setMarried(Boolean married);

  /**
   * recupere le login.
   *
   * @return le login.
   */
  String getLogin();

  /**
   * change le login.
   *
   * @param login le nouveaux lohin.
   */
  void setLogin(String login);

  /**
   * recuprer l'id.
   *
   * @return l' id.
   */
  int getId();

  /**
   * change le id.
   *
   * @param id le nouvelle id.
   */
  void setId(int id);

  /**
   * get le mdp.
   *
   * @return le mdp.
   */
  String getPassword();

  /**
   * change le mdp.
   *
   * @param password le nv mdp.
   */
  void setPassword(String password);

  /**
   * retrun true si le mdp est le meme que celui de la db.
   *
   * @param password le mdp a verifier.
   * @return true si le meme false sinon.
   */
  boolean checkPassword(String password);

  /**
   * hash le mdp.
   *
   * @param password le mdp a hashe.
   * @return le mdp hashé.
   */
  String hashPassword(String password);
}
