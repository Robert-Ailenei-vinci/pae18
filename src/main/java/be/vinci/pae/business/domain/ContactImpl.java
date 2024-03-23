package be.vinci.pae.business.domain;

import java.util.List;

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
  private EntrepriseDTO entreprise;

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


  @Override
  public boolean stopFollowContact() {
    if (state.equals("initie") || state.equals("rencontre")) {
      this.state = "stop follow";
      this.reasonForRefusal = "";
      this.meetingType = "";
      return true;
    }
    return false;
  }

  @Override
  public boolean refuseContact(String reasonForRefusal) {
    if (state.equals("rencontre")) {
      this.state = "refuse";
      this.reasonForRefusal = reasonForRefusal;
      this.meetingType = "";
      return true;
    }
    return false;
  }

  @Override
  public boolean meetContact(String meetingType) {
    if (state.equals("initie")) {
      this.state = "rencontre";
      this.meetingType = meetingType;
      this.reasonForRefusal = "";
      return true;
    }
    return false;
  }

  @Override
  public boolean checkUniqueUserEnterpriseSchoolYear(List<ContactDTO> userContacts,
      EntrepriseDTO entrepriseDTO, SchoolYearDTO schoolYearDTO) {
    for (ContactDTO contact : userContacts
    ) {
      if (contact.getEntrepriseId() == entrepriseDTO.getId()
          && contact.getSchoolYearId() == schoolYearDTO.getId()) {
        return false;
      }
    }
    return true;
  }
}
