package be.vinci.pae.business.domain;

public class StageImpl implements StageDTO {
  private int contact;
  private String signatureDate;
  private String internshipProject;
  private int supervisor;
  private int user;
  private int schoolYear;

  public int getContact() {
    return contact;
  }

  public void setContact(int contact) {
    this.contact = contact;
  }

  public String getSignatureDate() {
    return signatureDate;
  }

  public void setSignatureDate(String signatureDate) {
    this.signatureDate = signatureDate;
  }

  public String getInternshipProject() {
    return internshipProject;
  }

  public void setInternshipProject(String internshipProject) {
    this.internshipProject = internshipProject;
  }

  public int getSupervisor() {
    return supervisor;
  }

  public void setSupervisor(int supervisor) {
    this.supervisor = supervisor;
  }

  public int getUser() {
    return user;
  }

  public void setUser(int user) {
    this.user = user;
  }

  public int getSchoolYear() {
    return schoolYear;
  }

  public void setSchoolYear(int schoolYear) {
    this.schoolYear = schoolYear;
  }
}
