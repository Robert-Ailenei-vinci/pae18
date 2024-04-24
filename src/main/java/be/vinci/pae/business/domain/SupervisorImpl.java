package be.vinci.pae.business.domain;

import java.util.Objects;

/**
 * Implementation of the SupervisorDTO interface representing a supervisor.
 */
public class SupervisorImpl implements Supervisor {

  private int supervisorId;
  private String lastName;
  private String firstName;
  private int entrepriseId;
  private String phoneNumber;
  private String email;
  private EntrepriseDTO entreprise;

  public int getSupervisorId() {
    return supervisorId;
  }

  public void setSupervisorId(int supervisorId) {
    this.supervisorId = supervisorId;
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

  public int getEntrepriseId() {
    return entrepriseId;
  }

  public void setEntrepriseId(int entrepriseId) {
    this.entrepriseId = entrepriseId;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public EntrepriseDTO getEntreprise() {
    return entreprise;
  }

  public void setEntreprise(EntrepriseDTO entreprise) {
    this.entreprise = entreprise;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SupervisorImpl that)) {
      return false;
    }
    return getEntrepriseId() == that.getEntrepriseId() && Objects.equals(getLastName(),
        that.getLastName()) && Objects.equals(getFirstName(), that.getFirstName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getLastName(), getFirstName(), getEntrepriseId());
  }
}
