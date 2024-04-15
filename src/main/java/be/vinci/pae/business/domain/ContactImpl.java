package be.vinci.pae.business.domain;

import java.util.Objects;

/**
 * This class represents an implementation of the {@link Contact} interface.
 */
public class ContactImpl implements Contact {

  private int id;
  private String state; //add predefined states
  private int userId;
  private int entrepriseId;
  private int schoolYearId;
  private String reasonForRefusal;
  private String meetingType;
  private int version;
  private SchoolYearDTO schoolYearDTO;
  private EntrepriseDTO entreprise;
  private UserDTO user;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getState() {
    return state;
  }

  @Override
  public void setState(String state) {
    this.state = state;
  }

  @Override
  public int getEntrepriseId() {
    return entrepriseId;
  }

  @Override
  public void setEntrepriseId(int entrepriseId) {
    this.entrepriseId = entrepriseId;
  }

  @Override
  public int getSchoolYearId() {
    return schoolYearId;
  }

  @Override
  public void setSchoolYearId(int schoolYearId) {
    this.schoolYearId = schoolYearId;
  }

  @Override
  public String getMeetingType() {
    return meetingType;
  }

  @Override
  public void setMeetingType(String meetingType) {
    this.meetingType = meetingType;
  }

  @Override
  public String getReasonForRefusal() {
    return reasonForRefusal;
  }

  @Override
  public void setReasonForRefusal(String reasonForRefusal) {
    this.reasonForRefusal = reasonForRefusal;
  }

  @Override
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public EntrepriseDTO getEntreprise() {
    return entreprise;
  }

  public void setEntreprise(EntrepriseDTO entreprise) {
    this.entreprise = entreprise;
  }

  @Override
  public int getVersion() {
    return version;
  }

  @Override
  public void setVersion(int version) {
    this.version = version;
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  public SchoolYearDTO getSchoolYearDTO() {
    return schoolYearDTO;
  }

  public void setSchoolYearDTO(SchoolYearDTO schoolYearDTO) {
    this.schoolYearDTO = schoolYearDTO;
  }

  @Override
  public boolean stopFollowContact(int version) {
    if (state.equals("initie") || state.equals("rencontre")) {
      this.state = "stop follow";
      this.reasonForRefusal = "";
      this.meetingType = "";
      this.version = version;
      return true;
    }
    return false;
  }

  @Override
  public boolean refuseContact(String reasonForRefusal, int version) {
    if (state.equals("rencontre")) {
      this.state = "refuse";
      this.reasonForRefusal = reasonForRefusal;
      this.meetingType = "";
      this.version = version;
      return true;
    }
    return false;
  }

  @Override
  public boolean meetContact(String meetingType, int version) {
    if (state.equals("initie")) {
      this.state = "rencontre";
      this.meetingType = meetingType;
      this.reasonForRefusal = "";
      this.version = version;
      return true;
    }
    return false;
  }

  @Override
  public boolean checkUniqueUserEnterpriseSchoolYear(int wantedEntrepriseId,
      int wantedSchoolYearId) {
    return schoolYearId == wantedSchoolYearId
        && entrepriseId == wantedEntrepriseId;
  }

  @Override
  public boolean acceptContact(int version) {
    if (state.equals("rencontre")) {
      this.state = "accepte";
      this.version = version;
      return true;
    }
    return false;
  }

  @Override
  public boolean checkStateAccepted() {
    return Objects.equals(this.state, "accepte");
  }

  @Override
  public boolean cancelContact(int version) {
    this.version = version;
    this.state = "annule";
    return false;
  }
}
