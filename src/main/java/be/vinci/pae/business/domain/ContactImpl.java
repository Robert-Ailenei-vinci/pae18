package be.vinci.pae.business.domain;

import java.util.Date;

public class ContactImpl implements Contact {
    private int id;
    private String state; //add predefined states
    private User user;
    private Entreprise entreprise;
    private SchoolYear schoolYear;
    private String reasonForRefusal;
    private String MeetingType;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {

    }

    @Override
    public void setId(int id) {

    }

    @Override
    public Entreprise getEntreprise() {
        return entreprise;
    }

    @Override
    public void setEntreprise(Entreprise entreprise) {

    }

    @Override
    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    @Override
    public void setSchoolYear(SchoolYear schoolYear) {

    }
    @Override
    public String getMeetingType() {
        return MeetingType;
    }

    @Override
    public void setMeetingType(String meetingType) {

    }
    @Override
    public String getReasonForRefusal() {
        return reasonForRefusal;
    }

    @Override
    public void setReasonForRefusal(String reasonForRefusal) {

    }
    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {

    }
}
