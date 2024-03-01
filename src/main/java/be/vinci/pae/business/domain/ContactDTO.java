package be.vinci.pae.business.domain;

import java.util.Date;

public interface ContactDTO {
    int getId();
    String getState();
    void setState(String state);
    void setId(int id);
    User getUser();
    void setUser(User user);
    Entreprise getEntreprise();
    void setEntreprise(Entreprise entreprise);
    SchoolYear getSchoolYear();
    void setSchoolYear(SchoolYear schoolYear);
    String getReasonForRefusal();
    void setReasonForRefusal(String reasonForRefusal);
    String getMeetingType();
    void setMeetingType(String meetingType);
}
