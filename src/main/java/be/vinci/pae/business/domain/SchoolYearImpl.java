package be.vinci.pae.business.domain;

public class SchoolYearImpl implements SchoolYear{
    private int id;
    private String yearFormat;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id=id;
    }

    @Override
    public String getYearFormat() {
        return yearFormat;
    }

    @Override
    public void setYearFormat(String yearFormat) {
        this.yearFormat=yearFormat;
    }
}
