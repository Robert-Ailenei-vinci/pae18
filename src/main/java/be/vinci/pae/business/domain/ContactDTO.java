package be.vinci.pae.business.domain;

public interface ContactDTO {
    int getId();
    String getState();
    void setState(String state);
    void setId(int id);
    int getUserId();
    void setUserId(int user);
    int getEntrepriseId();
    void setEntrepriseId(int entreprise);
    int getSchoolYearId();
    void setSchoolYearId(int schoolYearId);
    String getReasonForRefusal();
    void setReasonForRefusal(String reasonForRefusal);
    String getMeetingType();
    void setMeetingType(String meetingType);
}
