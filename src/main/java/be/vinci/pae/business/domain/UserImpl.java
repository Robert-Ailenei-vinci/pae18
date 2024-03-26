package be.vinci.pae.business.domain;

import be.vinci.pae.exception.BadRequestException;
import be.vinci.pae.exception.BizException;
import java.util.Objects;
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
  private int version;
  private int schoolYearId;
  private SchoolYearDTO schoolYear;

  /**
   * Retrieves the school year ID of the user.
   *
   * @return the school year ID of the user
   */
  public int getSchoolYearId() {
    return schoolYearId;
  }

  @Override
  public void setSchoolYearId(int id) {
    this.schoolYearId = id;
  }

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
  public int getSchooYearId() {
    return schoolYearId;
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
  public SchoolYearDTO getSchoolYear() {
    return schoolYear;
  }

  @Override
  public void setSchoolYear(SchoolYearDTO schoolYear) {
    this.schoolYear = schoolYear;
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

  @Override
  public int getVersion() {
    return version;
  }

  @Override
  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public boolean checkIsStudent() {
    return Objects.equals(this.getRole(), "etudiant");
  }

  @Override
  public void checkExisitngUser(UserDTO userDTO) {

    if (userDTO != null) {
      throw new BizException("L'utilisateur existe déjà");
    }

  }

  @Override
  public void checkRegisterNotEmpty(UserDTO userDTO) {
    if (Objects.equals(userDTO.getEmail(), "") || Objects.equals(userDTO.getPassword(), "")
        || Objects.equals(userDTO.getLastName(), "") || Objects.equals(userDTO.getFirstName(), "")
        || Objects.equals(userDTO.getPhoneNum(), "") || Objects.equals(userDTO.getRole(), "")) {
      throw new BadRequestException("Tous les champs doivent etre remplis");
    }
  }

  public void checkRoleFromMail(String mail, UserDTO userDTO) {
    if (mail.endsWith("@student.vinci.be") && !userDTO.getRole().equals("etudiant")) {
      throw new BadRequestException("Role doit etre etudiant");
    }
    if (mail.endsWith("@vinci.be") &&
        !userDTO.getRole().equals("administratif") && !userDTO.getRole().equals("professeur")) {
      throw new BadRequestException("Role doit etre administratif ou professeur");
    }
  }

  public void checkMail(String email) {
    if (!email.endsWith("@vinci.be") && !email.endsWith("@student.vinci.be")) {
      throw new BadRequestException("Email doit finir avec @vinci.be ou @student.vinci.be");
    }
  }

  public void checkmailFromLnameAndFname(String email, String lastName, String firstName) {
    if (!email.startsWith(lastName.toLowerCase() + "." + firstName.toLowerCase())) {
      throw new BadRequestException(
          "Mail doit commencer avec ton nom et prenom en minuscule et séparé par un point");
    }
  }

}

