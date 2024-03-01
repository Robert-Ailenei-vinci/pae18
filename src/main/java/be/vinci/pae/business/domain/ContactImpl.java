package be.vinci.pae.business.domain;

public class ContactImpl implements Contact {
    private int id;
    private String state; //add predefined states
    private int userId;
    private int entrepriseId;
    private int schoolYearId;
    private String reasonForRefusal;
    private String meetingType;

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
        this.state=state;
    }

    @Override
    public void setId(int id) {
        this.id=id;
    }

    @Override
    public int getEntrepriseId() {
        return entrepriseId;
    }

    @Override
    public void setEntrepriseId(int entrepriseId) {
        this.entrepriseId=entrepriseId;
    }

    @Override
    public int getSchoolYearId() {
        return schoolYearId;
    }

    @Override
    public void setSchoolYearId(int schoolYearId) {
        this.schoolYearId=schoolYearId;
    }
    @Override
    public String getMeetingType() {
        return meetingType;
    }

    @Override
    public void setMeetingType(String meetingType) {
        this.meetingType=meetingType;
    }
    @Override
    public String getReasonForRefusal() {
        return reasonForRefusal;
    }

    @Override
    public void setReasonForRefusal(String reasonForRefusal) {
        this.reasonForRefusal=reasonForRefusal;
    }
    @Override
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId=userId;
    }
}
