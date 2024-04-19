package be.vinci.pae.business.domain;

/**
 * Implementation of the StageDTO interface representing a stage.
 */

public class StageImpl implements Stage {

  private int contactId;
  private String signatureDate;
  private String internshipProject;
  private int supervisorId;
  private int userId;
  private int schoolYearId;
  private ContactDTO contact;
  private UserDTO user;
  private SchoolYearDTO schoolYear;
  private SupervisorDTO supervisor;
  private int _version;

  @Override
  public int get_version() {
    return _version;
  }

  @Override
  public void set_version(int _version) {
    this._version = _version;
  }

  @Override
  public int getContactId() {
    return contactId;
  }

  @Override
  public void setContactId(int contactId) {
    this.contactId = contactId;
  }

  @Override
  public String getSignatureDate() {
    return signatureDate;
  }

  @Override
  public void setSignatureDate(String signatureDate) {
    this.signatureDate = signatureDate;
  }

  @Override
  public String getInternshipProject() {
    return internshipProject;
  }

  @Override
  public void setInternshipProject(String internshipProject) {
    this.internshipProject = internshipProject;
  }

  @Override
  public int getSupervisorId() {
    return supervisorId;
  }

  @Override
  public void setSupervisorId(int supervisorId) {
    this.supervisorId = supervisorId;
  }

  @Override
  public int getUserId() {
    return userId;
  }

  @Override
  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Override
  public int getSchoolYearId() {
    return schoolYearId;
  }

  @Override
  public void setSchoolYearId(int schoolYearId) {
    this.schoolYearId = schoolYearId;
  }

  public ContactDTO getContact() {
    return contact;
  }

  public void setContact(ContactDTO contact) {
    this.contact = contact;
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  public SchoolYearDTO getSchoolYear() {
    return schoolYear;
  }

  public void setSchoolYear(SchoolYearDTO schoolYear) {
    this.schoolYear = schoolYear;
  }

  public SupervisorDTO getSupervisor() {
    return supervisor;
  }

  public void setSupervisor(SupervisorDTO supervisor) {
    this.supervisor = supervisor;
  }
}
